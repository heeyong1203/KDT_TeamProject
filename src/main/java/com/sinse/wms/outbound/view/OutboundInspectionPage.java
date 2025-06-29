package com.sinse.wms.outbound.view;

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
import com.sinse.wms.io.util.IoFilterController;
import com.sinse.wms.io.view.IoFilterPanel;
import com.sinse.wms.product.model.IoRequest;

public class OutboundInspectionPage extends BaseContentPage {
	private String ioRequestType = "출고";
	private String statusName = "검수요청";

	private IoFilterPanel filterPanel;
	private OutLineButton bt_load, bt_approved, bt_denied;
	private IoFilterController controller;

    public OutboundInspectionPage(Color color) {
    	setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30)); 	// 레이아웃 스타일 설정

		List<IoRequest> emptyData = new ArrayList<>();
		filterPanel = new IoFilterPanel(emptyData, statusName); // 콤보박스와 테이블을 합친 레이아웃 클래스
	    add(filterPanel);                  						// 화면에 부착
		add(createButtons()); 									// 버튼 부착
		
		// 조회 이벤트 구현
		bt_load.addActionListener(e->{
			controller = new IoFilterController(filterPanel.getP_filters(), filterPanel.getTableLayout(), ioRequestType, statusName);
			controller.loadTable();
		});

        // 승인, 거절 이벤트 구현
        bt_approved.addMouseListener(new MouseAdapter() {
        	public void mouseReleased(MouseEvent e) {
        		new IoRequestApprovalController(filterPanel).approveRequest();
        	}
        });
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

	/*------------------------------------------------
	  입고요청 승인
	private void approve() {    	
		// 선택한 레코드 List에 담기
		List<IoRequest> selectedRow = new ArrayList();
		for (int i = 0; i < ioTableModel.getRowCount(); i++) {
			Boolean checked = (Boolean) ioTableModel.getValueAt(i, 0);
			if(Boolean.TRUE.equals(checked)) {
				selectedRow.add(rawData.get(i));
				System.out.println("내가 선택한 레코드는 "+i+"번 째 레코드");
			}
		}
	}
	------------------------------------------------*/
}
