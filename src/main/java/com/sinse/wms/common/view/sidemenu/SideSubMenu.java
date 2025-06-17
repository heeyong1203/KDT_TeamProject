package com.sinse.wms.common.view.sidemenu;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import com.sinse.wms.common.Config;

public class SideSubMenu extends BaseSideMenu {
	private JLabel nameLabel;

	public SideSubMenu(Menu menu, String name, SideMenuClickListener listenr) {
		super(menu, name, listenr);
		this.nameLabel = new JLabel(this.name);
		nameLabel.setFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 13));
		this.setMaximumSize(getSize());
		add(nameLabel);
	}

	@Override
	public Dimension getSize() {
		return new Dimension(350, 30);
	}

	@Override
	protected void selectedMenu(boolean isSeleced) {
		if (isSeleced) {
			nameLabel.setFont(new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 14));
		} else {
			nameLabel.setFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 13));
		}

	}
}
