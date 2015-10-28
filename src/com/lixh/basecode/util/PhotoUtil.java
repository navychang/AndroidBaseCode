package com.lixh.basecode.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.edmodo.cropper.Crop_Image;
import com.lixh.basecode.config.BaseConfig;
import com.lixh.basecode.widget.CircleImageView;

/**
 * 图片拍照裁剪
 * 
 * @author 小红
 * 
 */
public class PhotoUtil {
	int Ridiox;
	int Ridioy;
	Activity activity;
	String tempPhoto = "temp.jpg";

	public PhotoUtil(Activity activity, int Ridiox, int Ridioy) {
		this.activity = activity;
		this.Ridiox = Ridiox;
		this.Ridioy = Ridioy;
	}

	public void showPhtoes() {
		String[] s = { "相机拍摄", "手机相册" };
		Builder builder = new Builder(activity);
		builder.setItems(s, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					if (!FileUtil.isExistsSdcard()) {
						Alert.displayToastForLengthShort(activity,
								"SD卡不存在，不能拍照");

						return;
					}
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
							.fromFile(new File(FileUtil.getImageDir(),
									"temp.jpg")));
					activity.startActivityForResult(
							intent,
							BaseConfig.IntentRequestCodes.PHOTOINTENTREQUESTCODE);

					break;
				case 1:
					Intent intent1 = null;
					if (Build.VERSION.SDK_INT < 19) {
						intent1 = new Intent(Intent.ACTION_GET_CONTENT);
						intent1.setType("image/*");

					} else {
						intent1 = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					}
					activity.startActivityForResult(intent1,
							BaseConfig.IntentRequestCodes.PHOTOZOOMQUESTCODE);
					break;
				}
			}
		}).create().show();
	}

	/**
	 * 根据图库图片uri发送图片
	 * 
	 * @param selectedImage
	 */
	protected String sendPicByUri(Uri selectedImage) {
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			cursor = null;

			if (picturePath == null || picturePath.equals("null")) {

				return null;
			}
			return picturePath;
		} else {
			File file = new File(selectedImage.getPath());
			if (!file.exists()) {
				return null;

			}
			return file.getAbsolutePath();
		}

	}

	public void startPhotoZoom(Uri fileUrl) {
		Intent intent = new Intent(activity, Crop_Image.class);
		String picturePath = null;
		if (fileUrl != null) {
			picturePath = sendPicByUri(fileUrl);
			if (picturePath == null) {
				Toast toast = Toast.makeText(activity, "找不见图片",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				intent.putExtra(BaseConfig.IMAGEURL, picturePath);
				intent.putExtra("RatioX", Ridiox);
				intent.putExtra("Ratioy", Ridioy);
				activity.startActivityForResult(intent,
						BaseConfig.IntentRequestCodes.PHOTORESOULTQUESTCODE);
			}
		}

	}
	public File setPicToView(CircleImageView imagView, String photoName,
			Intent picdata) {
		Bundle extras = picdata.getExtras();
		File newFileName = null;
		if (extras != null) {
			InputStream is = new ByteArrayInputStream(
					extras.getByteArray("data"));
			Bitmap photo = BitmapFactory.decodeStream(is);
			Bitmap bm2 = null;
			if (photo != null) {
				FileOutputStream b = null;

				if (FileUtil.isExistsFile(tempPhoto)) {
					FileUtil.deleteDir(new File(FileUtil.getImageDir(),
							tempPhoto));// 删除那张自定义的临时图片
				}
				if (FileUtil.isExistsFile(photoName)) {
					FileUtil.deleteDir(new File(FileUtil.getImageDir(),
							photoName));// 删除那张自定义的touxiang临时图片
				}
				bm2 = ImageUtil.zoomBitmap(photo, ScreenUtil.screenWidth,
						ScreenUtil.screenWidth * Ridioy / Ridiox);
				newFileName = new File(FileUtil.getImageDir(), photoName);
				try {
					b = new FileOutputStream(newFileName);
					bm2.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文??100是参??
					imagView.setImageBitmap(bm2);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return newFileName;
	}
	public File setPicToView(ImageView imagView, String photoName,
			Intent picdata) {
		Bundle extras = picdata.getExtras();
		File newFileName = null;
		if (extras != null) {
			InputStream is = new ByteArrayInputStream(
					extras.getByteArray("data"));
			Bitmap photo = BitmapFactory.decodeStream(is);
			Bitmap bm2 = null;
			if (photo != null) {
				FileOutputStream b = null;

				if (FileUtil.isExistsFile(tempPhoto)) {
					FileUtil.deleteDir(new File(FileUtil.getImageDir(),
							tempPhoto));// 删除那张自定义的临时图片
				}
				if (FileUtil.isExistsFile(photoName)) {
					FileUtil.deleteDir(new File(FileUtil.getImageDir(),
							photoName));// 删除那张自定义的touxiang临时图片
				}
				bm2 = ImageUtil.zoomBitmap(photo, ScreenUtil.screenWidth,
						ScreenUtil.screenWidth * Ridioy / Ridiox);
				newFileName = new File(FileUtil.getImageDir(), photoName);
				try {
					b = new FileOutputStream(newFileName);
					bm2.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文??100是参??
					imagView.setImageBitmap(bm2);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return newFileName;
	}
}
