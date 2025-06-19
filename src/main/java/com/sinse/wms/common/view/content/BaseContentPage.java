package com.sinse.wms.common.view.content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.sinse.wms.common.Config;

/**
 * 실질 적인 화면 단위 클래스 화면을 추가하려면 이 클래스를 상속 받아서 화면을 만들면 된다.
 * {@code public }
 */
public abstract class BaseContentPage extends JPanel {
	protected Color backgoundColor = Color.WHITE;	
	
	public BaseContentPage() {
		setOpaque(false);
		setPreferredSize(new Dimension(Config.CONTENT_BODY_WIDTH, Config.CONTENT_BODY_HEIGHT));
		setBorder(30, 60, 30, 60);
		setVisible(false);
	}
	
	/**
	 * 안쪽 여백공간을 설정하는 함수
	 * @param top 위쪽
	 * @param left 왼쪽
	 * @param bottom 아래
	 * @param right 오른쪽
	 */
	public void setBorder(int top, int left, int bottom, int right) {
		setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
		revalidate();
		repaint();
	}
	
	
	/**
	 * 백그라운드 컬러를 변경하는 함수
	 * @param color
	 */
	protected void setBackgroundColor(Color color) {
		this.backgoundColor = color;
		revalidate();
		repaint();
	}
	
	
	/**
	 * 둥근 사각형을 그리도록 오버라이드한 함수
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(backgoundColor);
		g2.fillRoundRect(Config.CONTENT_BODY_BORDER_WIDTH, Config.CONTENT_BODY_BORDER_HEIGHT,
				Config.CONTENT_BODY_WIDTH - Config.CONTENT_BODY_BORDER_WIDTH * 2,
				Config.CONTENT_BODY_HEIGHT - Config.CONTENT_BODY_BORDER_HEIGHT * 2, Config.CONTENT_HEADER_BORDER_RADIUS,
				Config.CONTENT_HEADER_BORDER_RADIUS);
		g2.dispose();
		super.paintComponent(g);
	}
}
