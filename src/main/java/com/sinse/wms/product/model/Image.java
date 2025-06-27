package com.sinse.wms.product.model;

public class Image {
	private int image_id;
	private String image_data;
	private String ext;
	private String regDate;
	public int getImage_id() {
		return image_id;
	}
	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	public String getImage_data() {
		return image_data;
	}
	public void setImage_data(String image_data) {
		this.image_data = image_data;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
}
