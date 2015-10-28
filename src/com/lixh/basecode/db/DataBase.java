package com.lixh.basecode.db;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.lixh.basecode.bean.BasicInfoModel;
import com.lixh.basecode.util.FileUtil;

public class DataBase {
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	private Context mContext;

	public DataBase(Context context, String dbDirs) {
		mContext = context;
		dbHelper = new DatabaseHelper(context, dbDirs, null,
				Constant.DATABASE_VERSION);
	}

	public void open() {
		if (FileUtil.isExistsSdcard()) {
			// 判断数据库是否存在
			if (!isExistDB()) {// 如果不存在将其写入目录中
				InputStream inputStream;
				try {
					inputStream = mContext.getAssets().open(
							Constant.DATABASE_NAME);
					FileUtil.wirteFileByBytes(Constant.DATABASE_PATH,
							Constant.DATABASE_NAME, inputStream);
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			db = SQLiteDatabase.openOrCreateDatabase(Constant.DATABASE_PATH
					+ Constant.DATABASE_NAME, null);

		}
	}

	// 获得省份数据
	@SuppressLint("NewApi")
	public List<BasicInfoModel> getSysProvince(int city_type) {
		if (db == null)
			return null;
		Cursor u = db.rawQuery("SELECT * FROM City WHERE city_type="
				+ city_type, null);
		int count = u.getCount();
		if (count == 0) {
			u.close();
			return null;
		}
		List<BasicInfoModel> list = new ArrayList<BasicInfoModel>();
		u.moveToFirst();
		for (int i = 0; i < count; i++) {
			BasicInfoModel spm = new BasicInfoModel();
			spm.setCity_id(String.valueOf(u.getInt(u.getColumnIndex("city_id"))));
			spm.setParent_id(u.getString(u.getColumnIndex("parent_id")));
			spm.setCity_nm(u.getString(u.getColumnIndex("city_nm")));
			spm.setCity_type(u.getString(u.getColumnIndex("city_type")));
			list.add(spm);
			u.moveToNext();
		}
		u.close();
		return list;
	}

	public HashMap<String, List<BasicInfoModel>> getCity(int city_type,
			int city_id) {
		HashMap<String, List<BasicInfoModel>> map = new HashMap<String, List<BasicInfoModel>>();
		if (db == null)
			return null;
		Cursor u = db.rawQuery("SELECT * FROM City WHERE city_type="
				+ city_type + " and parent_id=" + city_id, null);
		int count = u.getCount();
		if (count == 0) {
			u.close();
			return null;
		}
		List<BasicInfoModel> list = new ArrayList<BasicInfoModel>();
		u.moveToFirst();
		for (int i = 0; i < count; i++) {
			BasicInfoModel spm = new BasicInfoModel();
			spm.setCity_id(String.valueOf(u.getInt(u.getColumnIndex("city_id"))));
			spm.setParent_id(u.getString(u.getColumnIndex("parent_id")));
			spm.setCity_nm(u.getString(u.getColumnIndex("city_nm")));
			spm.setCity_type(u.getString(u.getColumnIndex("city_type")));
			list.add(spm);
			u.moveToNext();
		}
		map.put(city_id + "", list);
		u.close();
		return map;
	}

	public HashMap<String, List<BasicInfoModel>> getCitynew(int city_type,
			List<BasicInfoModel> province_list) {
		if (db == null)
			return null;
		HashMap<String, List<BasicInfoModel>> map = new HashMap<String, List<BasicInfoModel>>();
		for (int i = 0; i < province_list.size(); i++) {

			Cursor u = db.rawQuery("SELECT * FROM City WHERE city_type="
					+ city_type + " and parent_id="
					+ province_list.get(i).getCity_id(), null);
			int count = u.getCount();
			if (count == 0) {
				u.close();
				return null;
			}
			List<BasicInfoModel> list = new ArrayList<BasicInfoModel>();
			u.moveToFirst();
			for (int j = 0; j < count; j++) {
				BasicInfoModel spm = new BasicInfoModel();
				spm.setCity_id(String.valueOf(u.getInt(u
						.getColumnIndex("city_id"))));
				spm.setParent_id(u.getString(u.getColumnIndex("parent_id")));
				spm.setCity_nm(u.getString(u.getColumnIndex("city_nm")));
				spm.setCity_type(u.getString(u.getColumnIndex("city_type")));
				list.add(spm);
				u.moveToNext();
			}
			map.put(province_list.get(i).getCity_id() + "", list);
			u.close();
		}

		return map;
	}

	public String getLocation(String provinceID, String cityID, String areaID) {
		if (db == null)
			return null;
		Cursor u = db.rawQuery(
				"SELECT * FROM City WHERE city_id=" + provinceID, null);
		if (u.getCount() == 0) {
			return null;
		}
		u.moveToFirst();
		Cursor u1 = db.rawQuery("SELECT * FROM City WHERE city_id=" + cityID,
				null);
		if (u1.getCount() == 0) {
			return null;
		}
		u1.moveToFirst();
		Cursor u2 = db.rawQuery("SELECT * FROM City WHERE city_id=" + areaID,
				null);
		if (u2.getCount() == 0) {
			return null;
		}
		u2.moveToFirst();
		String location = u.getString(u.getColumnIndex("city_nm"))
				+ u1.getString(u1.getColumnIndex("city_nm"))
				+ u2.getString(u2.getColumnIndex("city_nm"));
		u.close();
		u1.close();
		u2.close();
		return location;
	}

	public HashMap<String, List<BasicInfoModel>> getCount(int city_type,
			HashMap<String, List<BasicInfoModel>> citymap) {
		if (db == null)
			return null;
		HashMap<String, List<BasicInfoModel>> map = new HashMap<String, List<BasicInfoModel>>();
		for (String key : citymap.keySet()) {

			for (int i = 0; i < citymap.get(key).size(); i++) {

				Cursor u = db.rawQuery(
						"SELECT * FROM City WHERE city_type=" + city_type
								+ " and parent_id="
								+ citymap.get(key).get(i).getCity_id(), null);
				int count = u.getCount();
				if (map.size() == 0 && count == 0) {
					u.close();
					return null;
				}
				List<BasicInfoModel> list = new ArrayList<BasicInfoModel>();
				u.moveToFirst();
				for (int j = 0; j < count; j++) {
					BasicInfoModel spm = new BasicInfoModel();
					spm.setCity_id(String.valueOf(u.getInt(u
							.getColumnIndex("city_id"))));
					spm.setParent_id(u.getString(u.getColumnIndex("parent_id")));
					spm.setCity_nm(u.getString(u.getColumnIndex("city_nm")));
					spm.setCity_type(u.getString(u.getColumnIndex("city_type")));
					list.add(spm);
					u.moveToNext();
				}
				map.put(citymap.get(key).get(i).getCity_id() + "", list);
				u.close();
			}
		}
		return map;
	}

	public void close() {
		db.close();
		dbHelper.close();
	}

	// 判断数据库是否存在
	private boolean isExistDB() {
		return FileUtil.isExistsFile(Constant.DATABASE_FILEPATH);
	}

	/**
	 * 数据库助手类
	 * 
	 * @author Administrator
	 * 
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {

			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			if (db == null)
				return;
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			onCreate(db);
		}

	}

	/**
	 * 常量类
	 * 
	 * @author Administrator
	 * 
	 */
	private static class Constant {
		private static final String DATABASE_NAME = "city.sqlite3";// 数据库名称
		public static final String DATABASE_PATH = FileUtil.getdbDir() + "/";
		public static final String DATABASE_FILEPATH = (new StringBuilder())
				.append(DATABASE_PATH).append(DATABASE_NAME).toString();
		private static final int DATABASE_VERSION = 1;// 版本号
	}
}
