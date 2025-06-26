package com.sinse.wms.product.model;

public class Auth {
	private int auth_id;
	private String auth_name;
	private int auth_flag;
	public int getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(int auth_id) {
		this.auth_id = auth_id;
	}
	public String getAuth_name() {
		return auth_name;
	}
	public void setAuth_name(String auth_name) {
		this.auth_name = auth_name;
	}
	public int getAuth_flag() {
		return auth_flag;
	}
	public void setAuth_flag(int auth_flag) {
		this.auth_flag = auth_flag;
	}
}