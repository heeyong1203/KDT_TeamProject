package com.sinse.wms.menu.help.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.content.BaseContentPage;

public class HelpMenu extends BaseContentPage{
	JTabbedPane tab;
	
	HelpGuideMenuPanel p_guide;
	HelpFQAMenuPanel p_fqa;
	HelpInfoMenuPanel p_info;
	
	public HelpMenu() {
		//생성
		tab = new JTabbedPane();
		p_guide = new HelpGuideMenuPanel();
		p_fqa = new HelpFQAMenuPanel();
		p_info = new HelpInfoMenuPanel();
		
		//스타일
		tab.setPreferredSize(new Dimension(Config.CONTENT_BODY_WIDTH - Config.CONTENT_BODY_BORDER_WIDTH * 2-20,
				(Config.CONTENT_BODY_HEIGHT - Config.CONTENT_BODY_BORDER_HEIGHT * 2-30)));
		tab.setBackground(Color.WHITE);
		tab.setOpaque(false);
		tab.setBorder(null); 
		
		//부착
		tab.add("기능별 가이드",p_guide);
		tab.add("FQA", p_fqa);
		tab.add("기타 정보",p_info);
		
		add(tab);
	}
}
