package com.sinse.wms.inbound.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.util.ExcelFileExport;
import com.sinse.wms.common.util.GetSaveFilePath;
import com.sinse.wms.common.view.button.OutLineButton;
import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.io.delete.IoDeleteController;
import com.sinse.wms.io.util.IoSelectController;
import com.sinse.wms.io.view.IoFilterPanel;
import com.sinse.wms.product.model.IoRequest;

public class InboundStatusPage extends BaseContentPage {
	private String ioRequestType = "입고";
	private String status_type = "현황";

	private IoFilterPanel filterPanel;
	private OutLineButton bt_load, bt_delete, bt_export;

	public InboundStatusPage(Color color) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30)); // 레이아웃 스타일 설정

		List<IoRequest> emptyData = new ArrayList<>();
		filterPanel = new IoFilterPanel(emptyData, status_type); // 콤보박스와 테이블을 합친 레이아웃 클래스
		add(filterPanel); // 화면에 부착
		add(createButtons()); // 버튼 부착

		// 조회(select) 이벤트 구현
		bt_load.addActionListener(e -> {
			new IoSelectController(filterPanel, ioRequestType, status_type);
		});

		// 삭제(delete) 이벤트 구현
		bt_delete.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					new IoDeleteController(filterPanel).delete();
				}
			}
		});

		// 내보내기(export) 이벤트 구현
		bt_export.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				String path = GetSaveFilePath.saveFilePath();
				List<String> columns = new ArrayList<>();
				for (int i = 0; i < filterPanel.getIoTableModel().getColumnCount(); i++) {
					columns.add(filterPanel.getIoTableModel().getColumnName(i));
				}
				String msg = ExcelFileExport.exportToExcel(columns, filterPanel.getIoTableModel(), path);
				JOptionPane.showMessageDialog(null, msg);
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
		bt_delete = new OutLineButton("삭제", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);
		bt_export = new OutLineButton("내보내기", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);

		p_bt.add(bt_load); // 버튼 붙이기
		p_bt.add(bt_delete);
		p_bt.add(bt_export);

		add(p_bt);

		return p_bt;
	}
}