package com.sinse.wms.product.model;

public class ProductSnapshot {
	private int product_snapshot_id;  // or private IoRequest ioRequest;
	private String product_name;
	private int product_price;

	public int getProduct_snapshot_id() {
		return product_snapshot_id;
	}
	public void setProduct_snapshot_id(int product_snapshot_id) {
		this.product_snapshot_id = product_snapshot_id;
	}

	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getProduct_price() {
		return product_price;
	}
	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}
}
