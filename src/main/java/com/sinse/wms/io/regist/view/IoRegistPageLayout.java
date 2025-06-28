package com.sinse.wms.io.regist.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.util.CalendarPopUp;
import com.sinse.wms.common.view.button.OutLineButton;

public class IoRegistPageLayout extends JDialog {
	private JLabel la_type, la_product, la_location, la_quantity, la_unit_title, la_unit, la_registReason, la_expected_date, la_requester, la_approver, la_remark;
	private JComboBox<String> cb_type, cb_product, cb_location, cb_requester, cb_approver;
	public JLabel getLa_unit() {
		return la_unit;
	}

	public void setLa_unit(JLabel la_unit) {
		this.la_unit = la_unit;
	}

	private JTextField t_quantity, t_expected_date;
	private JTextArea area_registReason, area_remark;
	private JScrollPane scroll_registReason, scroll_remark;
	private OutLineButton bt_regist, bt_cancel;
	private String status_type;
	

	public IoRegistPageLayout(String status_type) { // status_type : 페이지에서 매개변수로 받기 : 요청, 검수요청
		this.status_type = status_type;
		Dimension d = new Dimension(120, 30);
		Dimension d2 = new Dimension(150, 30);
		
		la_expected_date = new JLabel("예정 일자");
		la_type = new JLabel("입출고 타입");
		la_product = new JLabel("상품 선택");
		la_location = new JLabel("창고 선택");
		la_quantity = new JLabel("수량 입력");
		la_unit_title = new JLabel("수량 단위");
		la_unit = new JLabel("");
		la_registReason = new JLabel("입출고 사유");
		la_requester = new JLabel("등록 요청인");
		la_approver = new JLabel("승인 관리인");
		la_remark = new JLabel("비고");
		
		cb_type = new JComboBox<>();
		cb_product = new JComboBox<>();
		cb_location = new JComboBox<>();
		cb_requester = new JComboBox<>();
		cb_approver = new JComboBox<>();
		t_expected_date = new JTextField();
		t_quantity = new JTextField(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(getText().isEmpty()) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setBackground(Color.LIGHT_GRAY);
					g2.drawString("수량을 입력하세요", 5, 17);
					g2.dispose();
				}
			}
		};
		area_registReason = new JTextArea(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(getText().isEmpty()) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setBackground(Color.LIGHT_GRAY);
					g2.drawString("사유를 입력하세요", 2, 15);
					g2.dispose();
				}
			}
		};
		area_remark = new JTextArea(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(getText().isEmpty()) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setBackground(Color.LIGHT_GRAY);
					g2.drawString("비고", 3, 15);
					g2.dispose();
				}
			}
		};

		scroll_registReason = new JScrollPane(area_registReason);
		scroll_remark = new JScrollPane(area_remark);
		CalendarPopUp calenderPopUp = new CalendarPopUp();
		
		bt_regist = new OutLineButton("등록", 107, 30, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);
		bt_cancel = new OutLineButton("취소", 107, 30, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);
		
		la_type.setPreferredSize(d);
		la_product.setPreferredSize(d);
		la_location.setPreferredSize(d);
		la_quantity.setPreferredSize(d);
		la_registReason.setPreferredSize(d);
		la_expected_date.setPreferredSize(d);
		la_requester.setPreferredSize(d);
		la_approver.setPreferredSize(d);		
		la_remark.setPreferredSize(d);		
		cb_type.setPreferredSize(d2);
		cb_product.setPreferredSize(d2);
		cb_location.setPreferredSize(d2);
		la_unit_title.setPreferredSize(d);
		la_unit.setPreferredSize(d2);
		la_unit.setBackground(Color.WHITE);
		la_unit.setBorder(new LineBorder(Color.GRAY));
		cb_requester.setPreferredSize(d2);
		cb_approver.setPreferredSize(d2);
		t_expected_date.setPreferredSize(d2);
		t_quantity.setPreferredSize(d2);
		scroll_registReason.setPreferredSize(new Dimension(150, 120));
		scroll_remark.setPreferredSize(d2);
		
		add(la_expected_date);
		calenderPopUp.addCalendarPopup(t_expected_date);
		add(t_expected_date);
		add(la_type);
		add(cb_type);
		add(la_product);
		add(cb_product);
		add(la_location);
		add(cb_location);
		add(la_quantity);
		add(t_quantity);
		add(la_unit_title);
		add(la_unit);
		add(la_registReason);
		add(scroll_registReason);
		add(la_requester);
		add(cb_requester);
		add(la_approver);
		add(cb_approver);
		add(la_remark);
		add(scroll_remark);
		add(bt_regist);
		add(bt_cancel);

		setTitle("등록");
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		setPreferredSize(new Dimension(350, 520));
		setSize(new Dimension(350, 520));
		setVisible(true);
	}
	
	public JLabel getLa_type() {
		return la_type;
	}

	public void setLa_type(JLabel la_type) {
		this.la_type = la_type;
	}

	public JLabel getLa_product() {
		return la_product;
	}

	public void setLa_product(JLabel la_product) {
		this.la_product = la_product;
	}

	public JLabel getLa_location() {
		return la_location;
	}

	public void setLa_location(JLabel la_location) {
		this.la_location = la_location;
	}

	public JLabel getLa_quantity() {
		return la_quantity;
	}

	public void setLa_quantity(JLabel la_quantity) {
		this.la_quantity = la_quantity;
	}

	public JLabel getLa_registReason() {
		return la_registReason;
	}

	public void setLa_registReason(JLabel la_registReason) {
		this.la_registReason = la_registReason;
	}

	public JLabel getLa_expected_date() {
		return la_expected_date;
	}

	public void setLa_expected_date(JLabel la_expected_date) {
		this.la_expected_date = la_expected_date;
	}

	public JLabel getLa_requester() {
		return la_requester;
	}

	public void setLa_requester(JLabel la_requester) {
		this.la_requester = la_requester;
	}

	public JLabel getLa_approver() {
		return la_approver;
	}

	public void setLa_approver(JLabel la_approver) {
		this.la_approver = la_approver;
	}

	public JLabel getLa_remark() {
		return la_remark;
	}

	public void setLa_remark(JLabel la_remark) {
		this.la_remark = la_remark;
	}

	public JScrollPane getScroll_registReason() {
		return scroll_registReason;
	}

	public void setScroll_registReason(JScrollPane scroll_registReason) {
		this.scroll_registReason = scroll_registReason;
	}

	public JScrollPane getScroll_remark() {
		return scroll_remark;
	}

	public void setScroll_remark(JScrollPane scroll_remark) {
		this.scroll_remark = scroll_remark;
	}

	public void setCb_type(JComboBox<String> cb_type) {
		this.cb_type = cb_type;
	}

	public void setCb_product(JComboBox<String> cb_product) {
		this.cb_product = cb_product;
	}

	public void setCb_location(JComboBox<String> cb_location) {
		this.cb_location = cb_location;
	}

	public void setCb_requester(JComboBox<String> cb_requester) {
		this.cb_requester = cb_requester;
	}

	public void setCb_approver(JComboBox<String> cb_approver) {
		this.cb_approver = cb_approver;
	}

	public void setT_quantity(JTextField t_quantity) {
		this.t_quantity = t_quantity;
	}

	public void setT_expected_date(JTextField t_expected_date) {
		this.t_expected_date = t_expected_date;
	}

	public void setArea_registReason(JTextArea area_registReason) {
		this.area_registReason = area_registReason;
	}

	public void setArea_remark(JTextArea area_remark) {
		this.area_remark = area_remark;
	}

	public void setBt_regist(OutLineButton bt_regist) {
		this.bt_regist = bt_regist;
	}

	public void setBt_cancel(OutLineButton bt_cancel) {
		this.bt_cancel = bt_cancel;
	}

	public void setStatus_type(String status_type) {
		this.status_type = status_type;
	}

	public void setTypeItems(List<String> items) {
		cb_type.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));
	}
	
	public void setProductItems(List<String> items) {
		cb_product.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));
	}
	
	public void setLocationItems(List<String> items) {
		cb_location.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));
	}
	
	public void setRequesterItems(List<String> items) {
		cb_requester.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));
	}
	
	public void setApproverItems(List<String> items) {
		cb_approver.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));
	}
	
	public OutLineButton getBt_regist() {
		return bt_regist;
	}
	
	public OutLineButton getBt_cancel() {
		return bt_cancel;
	}
	
	public JComboBox<String> getCb_type() {
		return cb_type;
	}
	
	public JComboBox<String> getCb_product() {
		return cb_product;
	}
	
	public JComboBox<String> getCb_location() {
		return cb_location;
	}
	
	public JComboBox<String> getCb_requester() {
		return cb_requester;
	}
	
	public JComboBox<String> getCb_approver() {
		return cb_approver;
	}
	
	public JTextField getT_quantity() {
		return t_quantity;
	}
	
	public JTextArea getArea_registReason() {
		return area_registReason;
	}
	
	public JTextField getT_expected_date() {
		return t_expected_date;
	}
	
	public JTextArea getArea_remark() {
		return area_remark;
	}
	
	public String getStatus_type() {
		return status_type;
	}
}
