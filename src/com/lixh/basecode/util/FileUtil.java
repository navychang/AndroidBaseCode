package com.lixh.basecode.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.lixh.basecode.app.BaseApplication;

public class FileUtil {
	static String message = null;

	/***
	 * ��ȡ��Ŀ�ļ�
	 * 
	 * @return
	 */
	public static File getDir() {
		String packname = BaseApplication.getInstance().getPackageName();
		String name = packname.substring(packname.lastIndexOf(".") + 1,
				packname.length());
		File dir = null;
		if ((!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))) {
			dir = BaseApplication.getInstance().getCacheDir();
		} else {
			dir = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + name);
		}
		dir.mkdirs();
		return dir;
	}

	/**
	 * ��ȡ��Ŀ�����ļ�
	 * 
	 * @return
	 */
	public static String getCacheDir() {
		File file = new File(getDir().getAbsolutePath() + "/cache");
		if (!file.exists()) {
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}

	/**
	 * ��ȡ��Ŀʹ�ù����в�����ͼƬ�ļ�
	 * 
	 * @return
	 */
	public static String getImageDir() {
		File file = new File(getDir().getAbsolutePath() + "/image");
		file.mkdirs();
		return file.getAbsolutePath();
	}

	/**
	 * �Ƿ����SDcard
	 * 
	 * @return
	 */
	public static boolean isExistsSdcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * ��ȡ��Ŀʹ�õ����ݿ�
	 * 
	 * @return
	 */
	public static String getdbDir() {
		File file = new File(getDir().getAbsolutePath() + "/db");
		file.mkdirs();
		return file.getAbsolutePath();
	}

	/**
	 * uriװ���ļ�
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static File uriToFile(Activity context, Uri uri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor actualimagecursor = context.managedQuery(uri, proj, null, null,
				null);
		int actual_image_column_index = actualimagecursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		actualimagecursor.moveToFirst();
		String img_path = actualimagecursor
				.getString(actual_image_column_index);
		File file = new File(img_path);
		return file;
	}

	/**
	 * д���ļ�
	 * 
	 * @param in
	 * @param file
	 */
	public static void write(InputStream in, File file) {
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			while (in.read(buffer) > -1) {
				out.write(buffer);
			}
			out.flush();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * д���ļ�
	 * 
	 * @param in
	 * @param file
	 */
	public static void write(String in, File file, boolean append) {
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file, append);
			fw.write(in);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����
	 * 
	 * @param file
	 * @return
	 */
	public static String read(File file) {
		if (!file.exists()) {
			return "";
		}
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			StringBuffer buffer = new StringBuffer();
			String s;
			while ((s = br.readLine()) != null) {
				buffer.append(s);
			}
			return buffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * ���ַ�Ϊ��λ��ȡ�ļ���������д�ı������ֵ����͵��ļ�
	 * 
	 * @param fileDir
	 *            �ļ�Ŀ¼
	 * @param fileName
	 *            �ļ���
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static boolean wirteFileByChars(String fileDir, String fileName,
			FileInputStream is) throws IOException {
		boolean isSuccess = false;
		if (isExistsSdcard()) {// �ж�SDcard�Ƿ����
			File directory = new File(fileDir);// Ŀ¼
			if (!directory.exists()) {// ��������ڣ�������Ŀ¼
				directory.mkdirs();
			}
			File file = new File(fileDir, fileName);
			if (!file.exists()) // ��������ڣ��������ļ�
				file.createNewFile();
			PrintWriter pWriter = null;
			if (is != null) {
				pWriter = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream(file)));
				BufferedReader bReader = new BufferedReader(
						new InputStreamReader(is));
				String lineContent;
				while ((lineContent = bReader.readLine()) != null) {
					pWriter.println(lineContent);
				}
				pWriter.flush();
				bReader.close();
				pWriter.close();
				isSuccess = true;
			}
		}
		return isSuccess;
	}

	/**
	 * ���ַ�Ϊ��λ��ȡ�ļ���������д�ı������ֵ����͵��ļ�
	 * 
	 * @param fileDir
	 *            �ļ�Ŀ¼
	 * @param fileName
	 *            �ļ���
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static boolean wirteFileByChars(String fileDir, String fileName,
			String content) throws IOException {
		boolean isSuccess = false;
		if (isExistsSdcard()) {// �ж�SDcard�Ƿ����
			File directory = new File(fileDir);// Ŀ¼
			if (!directory.exists()) {// ��������ڣ�������Ŀ¼
				directory.mkdirs();
			}
			File file = new File(fileDir, fileName);
			if (!file.exists()) // ��������ڣ��������ļ�
				file.createNewFile();
			PrintWriter pWriter = null;
			if (content != null) {
				pWriter = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream(file)));
				pWriter.write(content, 0, content.length());
				pWriter.flush();
				pWriter.close();
				isSuccess = true;
			}
		}
		return isSuccess;
	}

	/**
	 * ���ַ�Ϊ��λ��ȡ�ļ��������ڶ��ı������ֵ����͵��ļ�
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @return
	 * @throws IOException
	 */
	public static String readFileByChars(String filePath) throws IOException {
		if (isExistsSdcard()) {// �ж�SDcard�Ƿ����
			File direstory = new File(filePath);
			if (direstory.exists()) {
				BufferedReader bReader = new BufferedReader(
						new InputStreamReader(new FileInputStream(direstory)));
				StringBuffer sb = new StringBuffer();
				String lineContent;
				while ((lineContent = bReader.readLine()) != null) {
					sb.append(lineContent);
				}
				bReader.close();
				return sb.toString();
			}
		}
		return null;
	}

	/**
	 * ���ֽ�Ϊ��λд�ļ��������ڶ��������ļ�����ͼƬ��������Ӱ����ļ�
	 * 
	 * @param fileDir
	 *            �ļ�Ŀ¼
	 * @param fileName
	 *            �ļ���
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static boolean wirteFileByBytes(String fileDir, String fileName,
			InputStream is) throws IOException {
		boolean isSuccess = false;
		if (isExistsSdcard()) {// �ж�SDcard�Ƿ����
			File directory = new File(fileDir);// Ŀ¼
			if (!directory.exists()) {// ��������ڣ�������Ŀ¼
				directory.mkdirs();
			}
			File file = new File(fileDir, fileName);
			if (!file.exists()) // ��������ڣ��������ļ�
				file.createNewFile();
			FileOutputStream fileOutPutStream = null;
			if (is != null) {
				fileOutPutStream = new FileOutputStream(file);
				byte[] b = new byte[1024];
				int size = -1;
				while ((size = is.read(b)) != -1) {
					fileOutPutStream.write(b, 0, size);
				}
				fileOutPutStream.flush();
				is.close();
				fileOutPutStream.close();
				isSuccess = true;
			}
		}
		return isSuccess;
	}

	/**
	 * ���ֽ�Ϊ��λд�ļ��������ڶ��������ļ�����ͼƬ��������Ӱ����ļ�
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static byte[] readFileByBytes(String filePath) throws IOException {
		if (isExistsSdcard()) {// �ж�SDcard�Ƿ����
			File direstory = new File(filePath);
			if (direstory.exists()) {
				FileInputStream fileInputStream = new FileInputStream(direstory);
				ByteBuffer buffer = ByteBuffer.allocate(fileInputStream
						.available());
				byte[] b = new byte[1024];
				int size = -1;
				while ((size = fileInputStream.read(b)) != -1) {
					buffer.put(b, 0, size);
				}
				return buffer.array();
			}
		}
		return null;
	}

	/**
	 * ��ͼƬд��SDcard��
	 * 
	 * @param fileName
	 * @param mBitmap
	 * @return
	 */
	public static boolean wirteBitmap(String fileDir, String fileName,
			Bitmap mBitmap) {
		if (isExistsSdcard()) {// �ж�SDcard�Ƿ����
			File directory = new File(fileDir);// Ŀ¼
			if (!directory.exists()) {// ��������ڣ�������Ŀ¼
				directory.mkdirs();
			}
			File file = new File(fileDir + fileName);// �ļ�
			if (!file.exists()) {
				try {
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(file));// ���ļ���д������
					boolean isSuccess = mBitmap.compress(
							Bitmap.CompressFormat.PNG, 100, stream);
					if (!isSuccess) {
						message = "����ʧ�ܣ�";
					} else {
						message = "����ɹ���";
					}
					stream.flush();
					stream.close();
					return isSuccess;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				message = "�ѱ��棡";
				return false;
			}
		} else {
			message = "SDcard�����ڣ�";
		}
		return false;
	}

	/**
	 * ��ȡSDcard��ָ��Ŀ¼�µ�ָ����׺���ļ�·��
	 * 
	 * @param path
	 *            Ŀ¼
	 * @param postfix
	 *            ��׺
	 * @return
	 */
	public static ArrayList<String> readFiles(String dir, String postfix) {

		if (isExistsSdcard()) {// �ж�SDcard�Ƿ����
			File directory = new File(dir);// Ŀ¼
			if (!directory.exists()) {
				message = "Ŀǰû��ͼƬ��";
				return null;
			}
			File[] files = directory.listFiles();
			ArrayList<String> filesPath = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				String path = files[i].getAbsolutePath();
				if (path.endsWith(postfix)) {
					filesPath.add(path);
				}
			}
			return filesPath;
		} else {
			message = "SDcard�����ڣ�";
		}
		return null;
	}

	/**
	 * ɾ��ָ���ļ�
	 */
	public static boolean deleteFile(String filePath) {
		if (isExistsSdcard()) {// �ж�SDcard�Ƿ����
			File directory = new File(filePath);
			if (directory.exists()) {
				return directory.delete();
			}
		}
		return false;
	}

	/**
	 * ɾ��ָ���ļ�
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		dir.delete();

		return true;
	}

	/**
	 * ɾ��ָ��Ŀ¼�е������ļ�
	 */
	public static boolean deleteDirFile(String delPath) {
		if (isExistsSdcard()) {// �ж�SDcard�Ƿ����
			File directory = new File(delPath);
			File[] files = directory.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirFile(files[i].getAbsolutePath());
					files[i].delete();
				} else {
					files[i].delete();
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * �ж��ļ��Ƿ����
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isExistsFile(String filePath) {
		if (isExistsSdcard()) {// �ж�SDcard�Ƿ����
			File directory = new File(filePath);
			if (directory.exists()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ���ش˳���·������ʾ���ļ����һ�α��޸ĵ�ʱ��
	 * 
	 * @param filePath
	 * @return
	 */
	public static long lastModified(String filePath) {
		if (isExistsSdcard()) {// �ж�SDcard�Ƿ����
			File directory = new File(filePath);
			if (directory.exists()) {
				return directory.lastModified();
			}
		}
		return System.currentTimeMillis();
	}

}
