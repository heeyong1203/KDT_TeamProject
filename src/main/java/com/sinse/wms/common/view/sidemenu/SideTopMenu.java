package com.sinse.wms.common.view.sidemenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.ImageView;

public class SideTopMenu extends BaseSideMenu {
	private ImageView imageView = null;
	private JLabel nameLabel;

	public SideTopMenu(Menu menu, String name, String imagePath, SideMenuClickListener listenr) {
		super(menu, name, listenr);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setMinimumSize(getSize());
		this.setBorder(new EmptyBorder(12, 12, 12, 12));
		initImage(imagePath);
		initName();
		add(imageView);
		add(Box.createHorizontalStrut(12));
		add(nameLabel);
		repaint();
	}

	private void initImage(String imagePath) {
		this.imageView = new ImageView(imagePath, Config.SIDEMENU_ICON_WIDTH, Config.SIDEMENU_ICON_HEIGHT);
		this.imageView.setPreferredSize(getImageSize());
	}

	private void initName() {
		this.nameLabel = new JLabel(this.name);
		this.nameLabel.setPreferredSize(getNameSize());
		this.nameLabel.setFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 30));
		this.nameLabel.setForeground(Config.TOP_MENU_UNSELECT_FONT_COLOR);
	}

	private Dimension getImageSize() {
		return new Dimension(Config.TOP_MENU_IMAGE_VIEW_WIDTH, Config.TOP_MENU_IMAGE_VIEW_HEIGHT);
	}

	private Dimension getNameSize() {
		return new Dimension(Config.TOP_MENU_NAME_WIDTH, Config.TOP_MENU_NAME_HEIGHT);
	}

	@Override
	public Dimension getSize() {
		return new Dimension(Config.TOP_MENU_WIDTH, Config.TOP_MENU_HEIGHT);
	}

	@Override
	protected void selectedMenu(boolean isSeleced) {
		if (isSeleced) {
			this.nameLabel.setForeground(Config.PRIMARY_COLOR);
			this.nameLabel.setFont(new Font(Config.APP_DEFAULT_FONT, Font.BOLD, 30));
			setBackground(Config.TOP_MENU_SELECT_BG_COLOR);
		} else {
			this.nameLabel.setForeground(Config.TOP_MENU_UNSELECT_FONT_COLOR);
			this.nameLabel.setFont(new Font(Config.APP_DEFAULT_FONT, Font.PLAIN, 30));
			setBackground(null);
		}
	}
}
