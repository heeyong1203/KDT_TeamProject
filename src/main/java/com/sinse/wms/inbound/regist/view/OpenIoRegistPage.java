package com.sinse.wms.inbound.regist.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import com.sinse.wms.io.regist.IoRegistPageController;

public class OpenIoRegistPage {
	private IoRegistPageLayout registPage = null;
	
	// 등록 시 새 화면(JDialog) 띄우기
	public OpenIoRegistPage(String status_name) {
		if(registPage == null) {
			registPage = new IoRegistPageLayout(status_name); // 중복 방지
			registPage.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					registPage = null;
				}
			});
			registPage.setLocationRelativeTo(null);
		} else {
			JOptionPane.showMessageDialog(registPage, "이미 등록 페이지가 열려있습니다."); // 중복 창 알림
			// 기존 창을 앞으로 가져오기
		      registPage.setAlwaysOnTop(true);
		      registPage.setAlwaysOnTop(false);
		      registPage.toFront();
		      registPage.requestFocus();
		}
		
		IoRegistPageController controller = new IoRegistPageController(registPage); // 취소 선택 시 등록 창 꺼짐
	}
}
