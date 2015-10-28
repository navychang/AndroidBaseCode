package com.lixh.basecode.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * FastJson����
 * @author zjy
 *
 */
public class FastJsonUtils {
	
	public FastJsonUtils() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ʹ��JSON���߰�����ת����json����
	 * @param value �ǶԽ����ļ��ϵ�����
	 */
	public static String createJsonString(Object value) {
		String str = JSON.toJSONString(value);
		return str;
	}

	/**
	 * �Ե���javabean���н���
	 * @param <T>
	 * @param json Ҫ������json�ַ���
	 * @param cls 
	 * @return
	 */
	public static <T>T getObject(String json,Class<T> cls){
		T t = null;
		try {
			t = JSON.parseObject(json,cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * ��list���ͽ��н���
	 * @param <T>
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getListObject(String json,Class<T> cls){
		List<T> list = new ArrayList<T>();
		try {
			list = JSON.parseArray(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ��MapString�������ݽ��н���
	 * @param json
	 * @return
	 */
	public static Map<String, String> getMapStr(String json){
		Map<String, String> mapStr = new HashMap<String, String>();
		try {
			mapStr = JSON.parseObject(json, new TypeReference<Map<String, String>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapStr;
	}
	
	/**
	 * ��MapObject�������ݽ��н���
	 * @param json
	 * @return
	 */
	public static Map<String, Object> getMapObj(String json){
		Map<String, Object> mapStr = new HashMap<String, Object>();
		try {
			mapStr = JSON.parseObject(json, new TypeReference<Map<String, Object>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapStr;
	}
	
	/**
	 * ��listmap���ͽ��н���
	 * @param json
	 * @return
	 */
	public static List<Map<String, Object>> getListMap(String json){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = JSON.parseObject(json,new TypeReference<List<Map<String, Object>>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
