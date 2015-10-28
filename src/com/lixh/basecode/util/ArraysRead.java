package com.lixh.basecode.util;

import android.content.Context;

public class ArraysRead {
	/**
	 * 
	 * @param mContext
	 * @param arraysID
	 * @return 读取 arrays.xml的数组
	 */
	public static String[] getArrays(Context mContext, int arraysID) {
		String title[] = mContext.getResources().getStringArray(arraysID);
		return title;
	}

	/**
	 * @param messageArrary
	 * @param cutentText
	 */
	public static int getCuttentPosision(String[] messageArrary,
			String cutentText) {
		int position = 0;
		for (int i = 0; i < messageArrary.length; i++) {
			if (cutentText.equals(messageArrary[i])) {
				position = i;
			}
		}
		return position;
	}
}
