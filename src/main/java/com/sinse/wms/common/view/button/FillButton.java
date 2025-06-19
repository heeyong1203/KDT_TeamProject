package com.sinse.wms.common.view.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
/**
 * 커스텀 버튼
 * 
 */
public class FillButton extends JButton {
	protected int radius = 0;						//버튼 둥글기
	protected int width = 0;						//버튼 넓이
	protected int height = 0;						//버튼 높이
	protected int borderWidth = 0;					//보더라인 굵기 
	protected Color backgroundColor = Color.WHITE;	//버튼 배경 색상

	public FillButton(String name) {
		this(name, 0, 0, 0, null);
	}

	public FillButton(String name, int radius) {
		this(name, 0, 0, radius, null);
	}

	public FillButton(String name, Color backgroundColor) {
		this(name, 0, 0, 0, backgroundColor);
	}

	public FillButton(String name, int radius, Color backgroundColor) {
		this(name, 0, 0, radius, backgroundColor);
	}

	public FillButton(String name, int width, int height) {
		this(name, width, height, 0, null);
	}

	public FillButton(String name, int width, int height, int radius, Color backgroundColor) {
		this.width = width;
		this.height = height;
		this.radius = radius;
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setOpaque(false);
		setText(name);

		Dimension size = null;
		if (width != 0 && height != 0) {
			size = new Dimension(width, height);
		}
		setPreferredSize(size);

		if (backgroundColor != null) {
			this.backgroundColor = backgroundColor;
		}
	}
	
	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
		revalidate();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//버튼 눌렀을시 색상 어둡게 처리
		if (getModel().isPressed()) {
			g2.setColor(this.backgroundColor.darker());
		} else {
			g2.setColor(this.backgroundColor);
		}
		//사각형 그리기
		g2.fillRoundRect(this.borderWidth / 2, this.borderWidth / 2, getWidth() - this.borderWidth,
				getHeight() - this.borderWidth, this.radius, this.radius);
		g2.dispose();
		super.paintComponent(g);
	}
}
