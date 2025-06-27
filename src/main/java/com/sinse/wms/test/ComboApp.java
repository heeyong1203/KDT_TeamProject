package com.sinse.wms.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.button.OutLineButton;

public class ComboApp extends JFrame {
	
	private String ioRequestType = "입고";
	private String statusName = "현황";
	
	private IoComboBoxModel comboModel;
	private IoTableModel ioTableModel;
	IoComboBoxLayout p_filters;
	IoTableLayout tableLayout;
	private OutLineButton bt_load, bt_delete, bt_export;
	private IoFilterController controller;

	public ComboApp() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30)); // 레이아웃 스타일 설정
		setTitle("콤보박스 테스트"); // Page인 경우 삭제
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Page인 경우 삭제
		setSize(Config.CONTENT_BODY_WIDTH, Config.CONTENT_BODY_HEIGHT); // Page인 경우 삭제
		setLocationRelativeTo(null); // Page인 경우 삭제

		JPanel p = new JPanel(); // Page인 경우 삭제
		p.setPreferredSize(new Dimension(Config.CONTENT_BODY_WIDTH, Config.CONTENT_BODY_HEIGHT)); // Page인 경우 삭제

		p.add(IoComboBox()); // ← 핵심: 콤보박스 레이아웃 부착
		p.add(IoTable());
		p.add(createButtons()); // 버튼 부착
		add(p); // 패널을 프레임에 부착 // 페이지인 경우 삭제

		
		// 조회 이벤트 구현
		bt_load.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				controller = new IoFilterController(p_filters, tableLayout.getTable(), ioRequestType, statusName);
				controller.loadTable();
			}
		});

		setVisible(true); // 항상 맨 마지막에 호출
	}

	public IoComboBoxLayout IoComboBox() {
		// 콤보박스 껍데기 생성
		p_filters = new IoComboBoxLayout();

		// 콤보박스 모델 객체 생성
		comboModel = new IoComboBoxModel();
		comboModel.setTableCombo();

		// 콤보박스 아이템 연결
		p_filters.getCb_product_name().setModel(new DefaultComboBoxModel<>(comboModel.getProductNames().toArray(new String[0])));
		p_filters.getCb_product_code().setModel(new DefaultComboBoxModel<>(comboModel.getProductCodes().toArray(new String[0])));
		p_filters.getCb_member().setModel(new DefaultComboBoxModel<>(comboModel.getMemberNames().toArray(new String[0])));
		p_filters.getCb_dept().setModel(new DefaultComboBoxModel<>(comboModel.getDeptNames().toArray(new String[0])));
		p_filters.getCb_company().setModel(new DefaultComboBoxModel<>(comboModel.getCompanyNames().toArray(new String[0])));
		p_filters.getCb_status().setModel(new DefaultComboBoxModel<>(comboModel.getStatusNames().toArray(new String[0])));

		return p_filters;
	}

	public IoTableLayout IoTable() {
		tableLayout = new IoTableLayout(ioTableModel);

		return tableLayout;
	}

	/*------------------------------------------------
	버튼 생성 함수
	------------------------------------------------*/
	private JPanel createButtons() {

		JPanel p_bt = new JPanel();
		p_bt.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		p_bt.setPreferredSize(new Dimension(Config.CONTENT_BODY_WIDTH - 110, 35));
		p_bt.setOpaque(false);

		bt_load = new OutLineButton("조회", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);
		bt_delete = new OutLineButton("삭제", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);
		bt_export = new OutLineButton("내보내기", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);

		p_bt.add(bt_load); // 버튼 붙이기
		p_bt.add(bt_delete);
		p_bt.add(bt_export);

		add(p_bt);

		return p_bt;
	}

	public static void main(String[] args) {
		new ComboApp();
	}
}
