package com.edmodo.cropper;

import java.io.ByteArrayOutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.lixh.basecode.R;
import com.lixh.basecode.activity.BaseActivity;
import com.lixh.basecode.base.TitleBarView;
import com.lixh.basecode.config.BaseConfig;
import com.lixh.basecode.util.Alert;
import com.lixh.basecode.util.ScreenUtil;

/**
 * 
 * @Title: Crop_Image.java
 * @Package com.edmodo.cropper
 * @Description: 裁剪
 * @author lixh
 * @date 2015年8月15日 下午1:03:47
 * @version V1.0
 */
public class Crop_Image extends BaseActivity {
	Bundle mBundle;
	String url;
	Bitmap bitmap;
	CropImageView cropImageView;
	Bitmap croppedImage;
	int Ridiox;
	int Ridioy;

	@Override
	public void initView() {
		cropImageView = (CropImageView) findViewById(R.id.CropImageView);
		mBundle = getIntent().getExtras();
		url = (String) mBundle.get(BaseConfig.IMAGEURL);
		try {
			Ridiox = mBundle.getInt("RatioX", 50);
			Ridioy = mBundle.getInt("Ratioy", 50);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		initData();
	}

	public void initData() {

		bitmap = BitmapFactory.decodeFile(url);
		if (bitmap == null) {
			Alert.displayToastForLengthShort(this, "图片无效");
			this.finish();
			return;
		}
		bitmap = small(bitmap);
		cropImageView.setImageBitmap(bitmap);
		cropImageView.setFixedAspectRatio(true);
		cropImageView.setAspectRatio(Ridiox, Ridioy);
	}

	private Bitmap small(Bitmap bitmap) {

		float scalex = (float) (ScreenUtil.screenWidth - 60)
				/ bitmap.getWidth();
		Matrix matrix = new Matrix();

		matrix.postScale(scalex, scalex); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}

	public void onBtnCrop() {
		croppedImage = cropImageView.getCroppedImage();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] bitmapByte = baos.toByteArray();
		Intent intent = new Intent();
		Bundle mBundle = new Bundle();
		mBundle.putByteArray("data", bitmapByte);
		intent.putExtras(mBundle);
		setResult(RESULT_OK, intent);
		this.finish();
	}

	//
	@Override
	public int getLayoutById() {
		return R.layout.crop_image;
	}

	//
	@Override
	public void setTitleBar() {
		topbar.setTitle("裁剪").setRightTVBtn("完成", 0, new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBtnCrop();

			}
		}).setTitleStyle(TitleBarView.RIGHT_TV_STYLE);

	}

}