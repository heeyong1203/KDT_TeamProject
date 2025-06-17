package com.sinse.wms.common.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.sinse.wms.common.util.ImageUtil;

public class ImageView extends JPanel {
	private Image image = null;
	private int width = 0;
	private int height = 0;
	private String source = null;
	private Dimension size = new Dimension(width, height);
	private ImageViewClickListener listener = null;
	private MouseAdapter adapter = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if (listener != null) {
				listener.onClick();
			}
		}
	};;

	public ImageView(String source, int width, int height) {
		this.width = width;
		this.height = height;
		this.source = source;
		setOpaque(false);
		setSize(width, height);
	}

	public void setSize(int width, int height) {
		size.setSize(width, height);
		this.setMaximumSize(size);
		changeImage();
	}

	private void changeImage() {
		this.setMaximumSize(getMaximumSize());
		image = ImageUtil.getImage(source, width, height);
		updateUI();
	}

	/**
	 * 클릭리스너 등록하는 함수 클릭리스너를 등록하게 되면 커서가 손가락 모양으로 변경 된다. {@code null} 값을 입력하게 된다면 손가락
	 * 모양이 사라진다.
	 * 
	 * @param listener
	 * @see ImageViewClickListener
	 */
	public void setOnClickListener(ImageViewClickListener listener) {
		if (listener != null) {
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			this.addMouseListener(adapter);
		} else {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			if (adapter != null) {
				this.removeMouseListener(adapter);
				adapter = null;
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, width, height, this);
		}
	}

	public interface ImageViewClickListener {
		void onClick();
	}
}
