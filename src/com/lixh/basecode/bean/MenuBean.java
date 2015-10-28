package com.lixh.basecode.bean;

import java.io.Serializable;

public abstract class MenuBean implements Serializable {
	private static final long serialVersionUID = -2531998813388857088L;
	private int colorRes;
	private String ext;
	private String id;
	private String menuName;
	private int resId;
	private int index;

	public MenuBean(String menuName, int resId) {
		this.menuName = menuName;
		this.resId = resId;
	}

	public MenuBean(String id, String menuName) {
		this.id = id;
		this.menuName = menuName;
	}

	public MenuBean(String paramString, int paramInt1, int paramInt2) {
		this.menuName = paramString;
		this.resId = paramInt1;
		this.colorRes = paramInt2;
	}

	public MenuBean(String id, String menuName, int resId) {
		this.id = id;
		this.menuName = menuName;
		this.resId = resId;
	}

	public MenuBean(String id, String menuName, int resId, int colorRes) {
		this.id = id;
		this.menuName = menuName;
		this.resId = resId;
		this.colorRes = colorRes;
	}

	public MenuBean(int id, int resId) {
		this.setIndex(id);
		this.resId = resId;
	}

	public int getColorRes() {
		return this.colorRes;
	}

	public String getExt() {
		return this.ext;
	}

	public String getId() {
		return this.id;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public int getResId() {
		return this.resId;
	}

	public void setColorRes(int paramInt) {
		this.colorRes = paramInt;
	}

	public void setExt(String paramString) {
		this.ext = paramString;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public void setMenuName(String paramString) {
		this.menuName = paramString;
	}

	public void setResId(int paramInt) {
		this.resId = paramInt;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}