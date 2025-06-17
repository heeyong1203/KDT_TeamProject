package com.sinse.wms.common.view.sidemenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sinse.wms.common.Config;

public class SideBar extends JPanel {
	private JLabel title;
	private JPanel menuWrapper;
	private SideMenuGroup[] menuGroups = null;

	public SideBar() {
		this("", null);
	}

	public SideBar(String titleName, SideMenuGroup[] menuGroups) {
		this.setLayout(new BorderLayout(0, 0));
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		this.menuGroups = menuGroups;
		this.menuWrapper = new JPanel();
		this.menuWrapper.setLayout(new BoxLayout(menuWrapper, BoxLayout.Y_AXIS));
		this.menuWrapper.setBorder(BorderFactory.createEmptyBorder());
		this.menuWrapper.setPreferredSize(new Dimension(350, 760));
		initTitle(titleName);
		initMenus();
		add(this.title, BorderLayout.NORTH);
		add(this.menuWrapper, BorderLayout.CENTER);
		setSize(350, 960);
		setVisible(true);
	}
	
	private void initTitle(String titleName) {
		this.title = new JLabel(titleName, SwingConstants.CENTER);
		this.title.setFont(new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 45));
		this.title.setForeground(Config.PRIMARY_COLOR);
		this.title.setPreferredSize(new Dimension(Config.SIDEMENU_TITLE_WIDTH, Config.SIDEMENU_TITLE_HEIGHT));
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void initMenus() {
		if (menuGroups != null) {
			for (int i = 0; i < menuGroups.length; i++) {
				this.menuWrapper.add(menuGroups[i]);
			}
		}
	}

}
