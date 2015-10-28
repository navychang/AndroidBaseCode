package com.lixh.basecode.ImageLoding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageManager {
	// 加载图片的地方：
	public static ImageLoader imageLoader = ImageLoader.getInstance();

	// 使用DisplayImageOptions.Builder()创建DisplayImageOptions

	public static DisplayImageOptions getImageOptions(int StubImage,
			int ImageForEmptyUri, int showImageOnFail) {
		return new DisplayImageOptions.Builder().showStubImage(StubImage) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(ImageForEmptyUri) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(showImageOnFail) // 设置图片加载或解码过程中发生错误显示的图片
				// .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.build();
	}

	/**
	 * 
	 * @param image
	 *            ImageView
	 * @param imgUrl
	 *            图片地址
	 * @param StubImage
	 *            设置图片下载期间显示的图片
	 * @param ImageForEmptyUri
	 *            置图片Uri为空或是错误的时候显示的图片
	 * @param showImageOnFail
	 *            设置图片加载或解码过程中发生错误显示的图片
	 */
	public static void displayImage(ImageView image, String imgUrl,
			int StubImage, int ImageForEmptyUri, int showImageOnFail) {
		imageLoader.displayImage(imgUrl, image,
				getImageOptions(StubImage, ImageForEmptyUri, showImageOnFail));
	}

	public static void displayImage(ImageView image, String imgUrl,
			int StubImage, SimpleImageLoadingListener listener) {
		imageLoader.displayImage(imgUrl, image,
				getImageOptions(StubImage, StubImage, StubImage), listener);
	}

	/**
	 * 
	 * @param image
	 *            ImageView
	 * @param imgUrl
	 *            图片地址
	 * @param StubImage
	 *            设置图片下载期间显示的图片
	 * @param ImageForEmptyUri
	 *            置图片Uri为空或是错误的时候显示的图片
	 * @param showImageOnFail
	 *            设置图片加载或解码过程中发生错误显示的图片
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 */
	public static void displayImage(ImageView image, String imgUrl,
			int StubImage, int ImageForEmptyUri, int showImageOnFail,
			int width, int height) {
		imageLoader.loadImage(imgUrl, new ImageSize(width, height),
				getImageOptions(StubImage, ImageForEmptyUri, showImageOnFail),
				null);
	}

	/**
	 * Base64转 Bitmap
	 * 
	 * @param data
	 * @return
	 */
	public static Bitmap getImage(String data) {
		byte[] decodedString = Base64.decode(data, Base64.DEFAULT);

		Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		return bmp;
	}
}
