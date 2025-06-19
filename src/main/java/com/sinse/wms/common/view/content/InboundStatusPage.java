package com.sinse.wms.common.view.content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.sinse.wms.common.Config;

public class InboundStatusPage extends BaseContentPage {
	JPanel p_wrapper;
	JPanel p_client, p_dept, p_emp, p_product_code, p_product_name, p_progress_status;
	JLabel la_client, la_dept, la_emp, la_product_code, la_product_name, la_progress_status;
	JComboBox<String> cb_client, cb_dept, cb_emp, cb_product_code, cb_product_name, cb_progress_status;
	String[] clients = {"A사", "B사", "C사"};
	String[] dept = {"영업1팀", "영업2팀", "마케팅1팀", "마케팅2팀"};
	String[] emp = {"이경규" , "김국진", "강호동", "유재석", "전현무", "장도연"};
	String[] product_codes= {"ST-20001", "ST-20002", "ST-20003", "ST-20004", "ST-20005", "ST-20006"};
	String[] product_names = {"A4 복사용지 80g", "3단 서류함", "유성 네임펜", "고무줄 500g"};
	String[] progress_status = {"요청", "대기", "승인", "반려"};
	
	JTable table;
	
	public InboundStatusPage(Color color) {
		//-----상위 검색 툴 -----//
		// 생성
		p_wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
		p_client = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		p_dept = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		p_emp = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		p_product_code = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		p_product_name = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		p_progress_status = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		la_client = new JLabel("거래처");
		la_dept = new JLabel("부서명");
		la_emp = new JLabel("사원명");
		la_product_code = new JLabel("품목코드");
		la_product_name = new JLabel("품목명");
		la_progress_status = new JLabel("진행상태");
		cb_client = new JComboBox<>(clients);
		cb_dept = new JComboBox<>(dept);
		cb_emp = new JComboBox<>(emp);
		cb_product_code = new JComboBox<>(product_codes);
		cb_product_name = new JComboBox<>(product_names);
		cb_progress_status = new JComboBox<>(progress_status);
		
		// 스타일
		setPreferredSize(new Dimension(Config.CONTENT_BODY_WIDTH - Config.CONTENT_BODY_BORDER_WIDTH * 2-30, Config.CONTENT_BODY_HEIGHT - Config.CONTENT_BODY_BORDER_HEIGHT * 2-100));
		setBackground(color);
		p_wrapper.setPreferredSize(new Dimension(870, 80));
		p_wrapper.setBackground(Color.yellow);
		Dimension d1 = new Dimension(335, 30);
		Dimension d2 = new Dimension(250, 30);
		Dimension d3 = new Dimension(100,30);
		Dimension d4 = new Dimension(235,30);
		Dimension d5 = new Dimension(150,30);
		p_client.setPreferredSize(d1);
		p_dept.setPreferredSize(d2);
		p_emp.setPreferredSize(d2);
		p_product_code.setPreferredSize(d1);
		p_product_name.setPreferredSize(d2);
		p_progress_status.setPreferredSize(d2);
		la_client.setPreferredSize(d3);
		la_dept.setPreferredSize(d3);
		la_emp.setPreferredSize(d3);
		la_product_code.setPreferredSize(d3);
		la_product_name.setPreferredSize(d3);
		la_progress_status.setPreferredSize(d3);
		cb_client.setPreferredSize(d4);
		cb_dept.setPreferredSize(d5);
		cb_emp.setPreferredSize(d5);
		cb_product_code.setPreferredSize(d4);
		cb_product_name.setPreferredSize(d5);
		cb_progress_status.setPreferredSize(d5);
		la_client.setHorizontalAlignment(SwingConstants.CENTER);
		la_dept.setHorizontalAlignment(SwingConstants.CENTER);
		la_emp.setHorizontalAlignment(SwingConstants.CENTER);
		la_product_code.setHorizontalAlignment(SwingConstants.CENTER);
		la_product_name.setHorizontalAlignment(SwingConstants.CENTER);
		la_progress_status.setHorizontalAlignment(SwingConstants.CENTER);
		p_client.setOpaque(false);
		p_dept.setOpaque(false);
		p_emp.setOpaque(false);
		p_product_code.setOpaque(false);
		p_product_name.setOpaque(false);
		p_progress_status.setOpaque(false);
		
		// 조립
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		p_client.add(la_client);
		p_client.add(cb_client);
		p_dept.add(la_dept);
		p_dept.add(cb_dept);
		p_emp.add(la_emp);
		p_emp.add(cb_emp);
		p_product_code.add(la_product_code);
		p_product_code.add(cb_product_code);
		p_product_name.add(la_product_name);
		p_product_name.add(cb_product_name);
		p_progress_status.add(la_progress_status);
		p_progress_status.add(cb_progress_status);
		p_wrapper.add(p_client);
		p_wrapper.add(p_dept);
		p_wrapper.add(p_emp);
		p_wrapper.add(p_product_code);
		p_wrapper.add(p_product_name);
		p_wrapper.add(p_progress_status);
		add(p_wrapper);
		add(table);

		

	}
	
	// 테이블 더미데이터 모델
	public void DummyDataModel() {
		
	}
	
	
	// 테이블
	public JTable createTable() {
		table = new JTable();
		table.setPreferredSize(new Dimension((int)p_wrapper.getPreferredSize().getWidth(), 570));
		
		return table;
	}
}
