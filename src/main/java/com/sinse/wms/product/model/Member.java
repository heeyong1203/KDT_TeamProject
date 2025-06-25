package com.sinse.wms.product.model;

import java.sql.Date;

public class Member {
	private int member_id;
	private String member_password;
	private String member_email;
	private String member_name;
	private Date memberhiredate;
	private Dept dept; //dept_id
	private Auth auth; //auth_id;
	private JobGrade jobGrade; //job_grade
	private boolean dormant;
	
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getMember_password() {
		return member_password;
	}
	public void setMember_password(String member_password) {
		this.member_password = member_password;
	}
	public String getMember_email() {
		return member_email;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public Date getMemberhiredate() {
		return memberhiredate;
	}
	public void setMemberhiredate(Date memberhiredate) {
		this.memberhiredate = memberhiredate;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public Auth getAuth() {
		return auth;
	}
	public void setAuth(Auth auth) {
		this.auth = auth;
	}
	
	public JobGrade getJobGrade() {
		return jobGrade;
	}
	public void setJobGrade(JobGrade jobGrade) {
		this.jobGrade = jobGrade;
	}
	public boolean isDormant() {
		return dormant;
	}
	public void setDormant(boolean dormant) {
		this.dormant = dormant;
	}
}
