package com.sinse.wms.common.view.button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

import com.sinse.wms.common.Config;

/**
 * 외곽선을 적용 할 수 있는 커스텀 버튼
 * 
 */
public class OutLineButton extends FillButton {
	private Color borderColor = Color.BLACK;

	public OutLineButton(String name) {
		this(name, 0, 0, 0, 1, null, null);
	}

	public OutLineButton(String name, int radius) {
		this(name, 0, 0, radius, 1, null, null);
	}

	public OutLineButton(String name, Color backgroundColor) {
		this(name, 0, 0, 0, 1, null, backgroundColor);
	}

	public OutLineButton(String name, int radius, Color backgroundColor) {
		this(name, 0, 0, radius, 1, null, backgroundColor);
	}

	public OutLineButton(String name, int radius, Color borderColor, Color backgroundColor) {
		this(name, 0, 0, radius, 1, borderColor, backgroundColor);
	}

	public OutLineButton(String name, int radius, int borderWidth, Color borderColor, Color backgroundColor) {
		this(name, 0, 0, radius, borderWidth, borderColor, backgroundColor);
	}

	public OutLineButton(String name, int width, int height) {
		this(name, width, height, 0, 1, null, null);
	}

	public OutLineButton(String name, int width, int height, int radius, int borderWidth, Color borderColor,
			Color backgroundColor) {
		super(name, width, height, radius, backgroundColor);
		if (borderColor != null) {
			this.borderColor = borderColor;
		}
		this.borderWidth = borderWidth;
	}
	
	/**
	 * 보더라인 그리는 함수
	 */
	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(this.borderColor);
		g2.setStroke(new BasicStroke(this.borderWidth));
		g2.drawRoundRect(this.borderWidth / 2, this.borderWidth / 2, getWidth() - this.borderWidth,
				getHeight() - this.borderWidth, this.radius, this.radius);
		g2.dispose();
	}
}
