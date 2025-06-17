package com.sinse.wms.common.view.content;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.ImageView;

public class ContentHeader extends JPanel {
	private int radius = 70;
	private JPanel infoWrapper;
	private ImageView profileImage;
	private JLabel infoLabel;
	
	public ContentHeader(String deptoName, String userName) {
		this(deptoName, userName, "");
	}

	public ContentHeader(String deptoName, String userName, String profileImageUrl) {
		setBackground(Color.WHITE);
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(Config.CONTENT_HEADER_WIDTH, Config.CONTENT_HEADER_HEIGHT));
		initProfile(deptoName, userName, profileImageUrl);
		add(Box.createHorizontalStrut(670));
		add(infoWrapper);
	}

	private void initProfile(String deptoName, String userName, String profileImageUrl) {
		infoWrapper = new JPanel();
		infoWrapper.setLayout(new BoxLayout(infoWrapper, BoxLayout.X_AXIS));
		infoWrapper.setOpaque(false);
		infoLabel = new JLabel(deptoName + " \n" + userName + " 님", SwingConstants.CENTER);
		try {
			//TODO("사용자 이미지를 URL로 불러와서 설정하는 로직이 필요");
			profileImage = new ImageView(Config.CONTENT_DEFAULT_PROFILE_IMAGE, Config.CONTENT_HEADER_PROFILE_IMAGE_WIDTH, Config.CONTENT_HEADER_PROFILE_IMAGE_HEIGHT);
		} catch (Exception e) {
			profileImage = new ImageView(Config.CONTENT_DEFAULT_PROFILE_IMAGE, Config.CONTENT_HEADER_PROFILE_IMAGE_WIDTH, Config.CONTENT_HEADER_PROFILE_IMAGE_HEIGHT);
		}
		
		infoWrapper.add(profileImage);
		infoWrapper.add(Box.createHorizontalStrut(10));
		infoWrapper.add(infoLabel);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.WHITE);
		g2.fillRoundRect(Config.CONTENT_HEADER_BORDER_WIDTH, Config.CONTENT_HEADER_BORDER_HEIGHT, Config.CONTENT_HEADER_WIDTH - Config.CONTENT_HEADER_BORDER_WIDTH * 2, Config.CONTENT_HEADER_HEIGHT - Config.CONTENT_HEADER_BORDER_HEIGHT * 2, Config.CONTENT_HEADER_BORDER_RADIUS, Config.CONTENT_HEADER_BORDER_RADIUS);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(Config.CONTENT_HEADER_BORDER_STROKE_WIDTH));
		g2.drawRoundRect(Config.CONTENT_HEADER_BORDER_WIDTH, Config.CONTENT_HEADER_BORDER_HEIGHT, Config.CONTENT_HEADER_WIDTH - Config.CONTENT_HEADER_BORDER_WIDTH * 2, Config.CONTENT_HEADER_HEIGHT - Config.CONTENT_HEADER_BORDER_HEIGHT * 2, Config.CONTENT_HEADER_BORDER_RADIUS, Config.CONTENT_HEADER_BORDER_RADIUS);
		g2.dispose();

		super.paintComponent(g);
	}

}
