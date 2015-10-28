package com.lixh.basecode.http;

import java.io.Serializable;

/**
 * 返回的json基类，自己实现时要继承此基类
 * 
 * @author Administrator
 * 
 */
public class BaseResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String msg;
	protected Object data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BaseResult [status=" + status + ", msg=" + msg + ", data="
				+ data + "]";
	}

}
