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
	// ����ͼƬ�ĵط���
	public static ImageLoader imageLoader = ImageLoader.getInstance();

	// ʹ��DisplayImageOptions.Builder()����DisplayImageOptions

	public static DisplayImageOptions getImageOptions(int StubImage,
			int ImageForEmptyUri, int showImageOnFail) {
		return new DisplayImageOptions.Builder().showStubImage(StubImage) // ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(ImageForEmptyUri) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageOnFail(showImageOnFail) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				// .cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisk(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
				.build();
	}

	/**
	 * 
	 * @param image
	 *            ImageView
	 * @param imgUrl
	 *            ͼƬ��ַ
	 * @param StubImage
	 *            ����ͼƬ�����ڼ���ʾ��ͼƬ
	 * @param ImageForEmptyUri
	 *            ��ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
	 * @param showImageOnFail
	 *            ����ͼƬ���ػ��������з���������ʾ��ͼƬ
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
	 *            ͼƬ��ַ
	 * @param StubImage
	 *            ����ͼƬ�����ڼ���ʾ��ͼƬ
	 * @param ImageForEmptyUri
	 *            ��ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
	 * @param showImageOnFail
	 *            ����ͼƬ���ػ��������з���������ʾ��ͼƬ
	 * @param width
	 *            ��
	 * @param height
	 *            ��
	 */
	public static void displayImage(ImageView image, String imgUrl,
			int StubImage, int ImageForEmptyUri, int showImageOnFail,
			int width, int height) {
		imageLoader.loadImage(imgUrl, new ImageSize(width, height),
				getImageOptions(StubImage, ImageForEmptyUri, showImageOnFail),
				null);
	}

	/**
	 * Base64ת Bitmap
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
