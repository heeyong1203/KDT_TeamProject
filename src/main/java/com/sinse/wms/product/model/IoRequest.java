package com.sinse.wms.product.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class IoRequest {
	private int IoRequest_id;
	private String IoRequest_type;
	private Product product; //product_id;
	private int quantity;
	private Location location; //location_id;
	private Member request_member_id;
	private String request_reason;
	private RequestStatus status; //ststus_id;
	private Date request_at;
	private Date expected_date;
	private Member  member; //approve_member_id;
	private Date approve_at;
	private String remark;
	public int getIoRequest_id() {
		return IoRequest_id;
	}
	public void setIoRequest_id(int ioRequest_id) {
		IoRequest_id = ioRequest_id;
	}
	public String getIoRequest_type() {
		return IoRequest_type;
	}
	public void setIoRequest_type(String ioRequest_type) {
		IoRequest_type = ioRequest_type;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Member getRequest_member_id() {
		return request_member_id;
	}
	public void setRequest_member_id(Member request_member_id) {
		this.request_member_id = request_member_id;
	}
	public String getRequest_reason() {
		return request_reason;
	}
	public void setRequest_reason(String request_reason) {
		this.request_reason = request_reason;
	}
	public RequestStatus getStatus() {
		return status;
	}
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	public Date getRequest_at() {
		return request_at;
	}
	public void setRequest_at(Date request_at) {
		this.request_at = request_at;
	}
	public Date getExpected_date() {
		return expected_date;
	}
	public void setExpected_date(Date expected_date) {
		this.expected_date = expected_date;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Date getApprove_at() {
		return approve_at;
	}
	public void setApprove_at(Date approve_at) {
		this.approve_at = approve_at;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
