package com.sinse.wms.inbound.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.button.OutLineButton;
import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.io.approve.IoRequestApprovalController;
import com.sinse.wms.io.util.IoSelectController;
import com.sinse.wms.io.view.IoFilterPanel;
import com.sinse.wms.product.model.IoRequest;

public class InboundInspectionPage extends BaseContentPage {
	private String ioRequestType = "입고";
	private String status_type = "검수요청";

	private IoFilterPanel filterPanel;
	private OutLineButton bt_load, bt_approved, bt_denied;

	public InboundInspectionPage(Color color) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30)); // 레이아웃 스타일 설정

		List<IoRequest> emptyData = new ArrayList<>();
		filterPanel = new IoFilterPanel(emptyData, status_type); // 콤보박스와 테이블을 합친 레이아웃 클래스
		add(filterPanel); // 화면에 부착
		add(createButtons()); // 버튼 부착

		// 조회(select) 이벤트 구현
		bt_load.addActionListener(e -> {
			new IoSelectController(filterPanel, ioRequestType, status_type);
		});

		// 승인(update) 이벤트 구현
		bt_approved.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				new IoRequestApprovalController(filterPanel).approveRequest();
			}
		});

		// 거절(update) 이벤트 구현
		bt_denied.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				new IoRequestApprovalController(filterPanel).denyRequests();
			}
		});
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
		bt_approved = new OutLineButton("요청수락", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);
		bt_denied = new OutLineButton("요청거절", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);

		p_bt.add(bt_load); // 버튼 붙이기
		p_bt.add(bt_approved);
		p_bt.add(bt_denied);

		add(p_bt);

		return p_bt;
	}
}
