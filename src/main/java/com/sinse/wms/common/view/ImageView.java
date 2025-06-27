package com.sinse.wms.common.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JPanel;

import com.sinse.wms.common.util.ImageUtil;

public class ImageView extends JPanel {
	private Image image = null;
	private int width = 0;
	private int height = 0;
	private String source = null;
	private File sourceFile = null;
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
	};

	public ImageView() {
		this(null, 0, 0);
	}

	public ImageView(int width, int height) {
		this(null, width, height);
	}

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
		this.width = width;
		this.height = height;
		changeImage();
	}

	private void changeImage() {
		this.setPreferredSize(getMaximumSize());
		this.setMinimumSize(getMaximumSize());
		if (source != null) {
			image = ImageUtil.getImage(source, width, height);
		} else if (sourceFile != null) {
			image = ImageUtil.getImage(sourceFile, width, height);
		}
		revalidate();
		repaint();
	}

	/**
	 * 로컬 파일 이름 문자열로 이미지를 표시
	 * @param source 표시할 로컬 이미지 파일 이름 ex) "some-image.png"
	 */
	public void setSource(String source) {
		this.sourceFile = null;
		this.source = source;
		changeImage();
	}

	/**
	 * 파일로 이미지를 표시
	 * @param sourceFile 표시할 이미지 파일
	 */
	public void setSource(File sourceFile) {
		this.source = null;
		this.sourceFile = sourceFile;
		changeImage();
	}

	/**
	 * Base64로 인코딩된 이미지 데이터를 이미지로 표시
	 * @param base64
	 */
	public void setImageFromBase64(String base64) {
		this.source = null;
		this.sourceFile = null;
		image = ImageUtil.getImageFromBase64(base64);
		changeImage();
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(this.width, this.height);
	}

	/**
	 * 클릭리스너 등록하는 함수 클릭리스너를 등록하게 되면 커서가 손가락 모양으로 변경 된다. {@code null} 값을 입력하게 된다면 손가락
	 * 모양이 사라진다.
	 * 
	 * @param listener
	 * @see ImageViewClickListener
	 */
	public void setOnClickListener(ImageViewClickListener listener) {
		this.listener = listener;
		if (listener != null) {
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			this.addMouseListener(adapter);
		} else {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			if (adapter != null) {
				this.removeMouseListener(adapter);
			}
		}
	}

	/**
	 * 뷰에 표시되고 있는 이미지객체 반환
	 * @return 표시되고 있는 Image
	 */
	public Image getImage() {
		return this.image;
	}

	/**
	 * 이미지 확장자를 반환하는 함수
	 * @return 이미지 확장자명 ex) "png"
	 */
	public String getImageExtension() {
		if (source != null) {
			String[] fileNameSplit = source.split("\\.");
			int length = fileNameSplit.length;
			return fileNameSplit[length - 1];
		} else if (sourceFile != null) {
			String[] fileNameSplit = sourceFile.getName().split("\\.");
			int length = fileNameSplit.length;
			return fileNameSplit[length - 1];
		} else {
			return null;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
			g.drawImage(image, 0, 0, width, height, this);
			g.dispose();
		} else {
			super.paintComponent(g);
		}
	}

	public interface ImageViewClickListener {
		void onClick();
	}
}
