package com.sinse.wms.test;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.SwingConstants;


public class IoComboBoxLayout extends JPanel {
	private JLabel la_company, la_dept, la_member, la_product_code, la_product_name, la_status;
	private JComboBox<String> cb_company, cb_dept, cb_member, cb_product_code, cb_product_name, cb_status;
	private JPanel p_filter;
	
	public IoComboBoxLayout() {
		// 생성
		cb_company = new JComboBox<>();
		cb_dept = new JComboBox<>();
		cb_member = new JComboBox<>();
		cb_product_code = new JComboBox<>();
		cb_product_name = new JComboBox<>();
		cb_status = new JComboBox<>();
		la_company = new JLabel("거래처");
		la_dept = new JLabel("부서명");
		la_member = new JLabel("사원명");
		la_product_code = new JLabel("품목코드");
		la_product_name = new JLabel("품목명");
		la_status = new JLabel("진행상태");
		
		// 스타일
		Dimension labelSize = new Dimension(80, 30);
		Dimension comboSize = new Dimension(185, 30);
		la_company.setPreferredSize(labelSize);
		la_company.setHorizontalAlignment(SwingConstants.RIGHT);
		la_dept.setPreferredSize(labelSize);
		la_dept.setHorizontalAlignment(SwingConstants.RIGHT);
		la_member.setPreferredSize(labelSize);
		la_member.setHorizontalAlignment(SwingConstants.RIGHT);
		la_product_code.setPreferredSize(labelSize);
		la_product_code.setHorizontalAlignment(SwingConstants.RIGHT);
		la_product_name.setPreferredSize(labelSize);
		la_product_name.setHorizontalAlignment(SwingConstants.RIGHT);
		la_status.setPreferredSize(labelSize);
		la_status.setHorizontalAlignment(SwingConstants.RIGHT);
		cb_company.setPreferredSize(comboSize);
		cb_dept.setPreferredSize(comboSize);
		cb_member.setPreferredSize(comboSize);
		cb_product_code.setPreferredSize(comboSize);
		cb_product_name.setPreferredSize(comboSize);
		cb_status.setPreferredSize(comboSize);
        		
        // 부착
		setPreferredSize(new Dimension(870, 80));
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 7));
		add(la_company);
		add(cb_company);
		add(la_dept);
		add(cb_dept);
		add(la_member);
		add(cb_member);
		add(la_product_code);
		add(cb_product_code);
		add(la_product_name);
		add(cb_product_name);
		add(la_status);
		add(cb_status);
		setOpaque(false);
	}

	public JLabel getLa_company() {
		return la_company;
	}

	public void setLa_company(JLabel la_company) {
		this.la_company = la_company;
	}

	public JLabel getLa_dept() {
		return la_dept;
	}

	public void setLa_dept(JLabel la_dept) {
		this.la_dept = la_dept;
	}

	public JLabel getLa_member() {
		return la_member;
	}

	public void setLa_member(JLabel la_member) {
		this.la_member = la_member;
	}

	public JLabel getLa_product_code() {
		return la_product_code;
	}

	public void setLa_product_code(JLabel la_product_code) {
		this.la_product_code = la_product_code;
	}

	public JLabel getLa_product_name() {
		return la_product_name;
	}

	public void setLa_product_name(JLabel la_product_name) {
		this.la_product_name = la_product_name;
	}

	public JLabel getLa_status() {
		return la_status;
	}

	public void setLa_status(JLabel la_status) {
		this.la_status = la_status;
	}

	public JComboBox<String> getCb_company() {
		return cb_company;
	}

	public void setCb_company(JComboBox<String> cb_company) {
		this.cb_company = cb_company;
	}

	public JComboBox<String> getCb_dept() {
		return cb_dept;
	}

	public void setCb_dept(JComboBox<String> cb_dept) {
		this.cb_dept = cb_dept;
	}

	public JComboBox<String> getCb_member() {
		return cb_member;
	}

	public void setCb_member(JComboBox<String> cb_member) {
		this.cb_member = cb_member;
	}

	public JComboBox<String> getCb_product_code() {
		return cb_product_code;
	}

	public void setCb_product_code(JComboBox<String> cb_product_code) {
		this.cb_product_code = cb_product_code;
	}

	public JComboBox<String> getCb_product_name() {
		return cb_product_name;
	}

	public void setCb_product_name(JComboBox<String> cb_product_name) {
		this.cb_product_name = cb_product_name;
	}

	public JComboBox<String> getCb_status() {
		return cb_status;
	}

	public void setCb_status(JComboBox<String> cb_status) {
		this.cb_status = cb_status;
	}	
	
}
