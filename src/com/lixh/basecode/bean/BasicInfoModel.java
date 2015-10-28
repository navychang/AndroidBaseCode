package com.lixh.basecode.bean;

public class BasicInfoModel {
	private String city_id;
	private String parent_id;
	private String city_nm;
	private String city_type;

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getCity_nm() {
		return city_nm;
	}

	public void setCity_nm(String city_nm) {
		this.city_nm = city_nm;
	}

	public String getCity_type() {
		return city_type;
	}

	public void setCity_type(String city_type) {
		this.city_type = city_type;
	}
}
