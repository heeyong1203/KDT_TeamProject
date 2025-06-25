package com.sinse.wms.common.view.toolbar;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.ImageView;

public class ToolBar extends JPanel {
	private ToolBarListener listener = null;
	private ImageView myPage;
	private ImageView messageBox;
	private ImageView search;
	private ImageView info;
	private ImageView settings;

	public ToolBar() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(Config.TOOLBAR_WIDTH, Config.TOOLBAR_HEIGHT));
		setBackground(Config.PRIMARY_COLOR);
		setBorder(new EmptyBorder(55, 0, 55, 0));
		initMenu();
	}
	
	/**
	 * 메뉴 초기화 함수
	 */
	private void initMenu() {
		myPage = new ImageView(Config.TOOLBAR_MY_PAGE_ICON_NAME, Config.TOOLBAR_ICON_WIDTH, Config.TOOLBAR_ICON_HEIGHT);
		messageBox = new ImageView(Config.TOOLBAR_MESSAGE_ICON_NAME, Config.TOOLBAR_ICON_WIDTH, Config.TOOLBAR_ICON_HEIGHT);
		search = new ImageView(Config.TOOLBAR_SEARCH_ICON_NAME, Config.TOOLBAR_ICON_WIDTH, Config.TOOLBAR_ICON_HEIGHT);
		info = new ImageView(Config.TOOLBAR_INFO_ICON_NAME, Config.TOOLBAR_ICON_WIDTH, Config.TOOLBAR_ICON_HEIGHT);
		settings = new ImageView(Config.TOOLBAR_SETTING_ICON_NAME, Config.TOOLBAR_ICON_WIDTH, Config.TOOLBAR_ICON_HEIGHT);

		add(myPage);
		myPage.setOnClickListener(() -> {
			System.out.println("마이페이지 아이콘 클릭됨");// 확인용
			if (this.listener != null) {
				this.listener.onClickMyPage();
			}
		});

		add(Box.createVerticalStrut(50));
		
		add(messageBox);
		messageBox.setOnClickListener(() -> {
			if (this.listener != null) {
				this.listener.onClickMessageBox();
			}
		});
		add(Box.createVerticalStrut(50));

		add(search);
		search.setOnClickListener(() -> {
			if (this.listener != null) {
				this.listener.onClickSearch();
			}
		});
		add(Box.createVerticalStrut(400));
		
		add(info);
		info.setOnClickListener(() -> {
			if (this.listener != null) {
				this.listener.onClickInfo();
			}
		});
		add(Box.createVerticalStrut(50));
		
		add(settings);
		settings.setOnClickListener(() -> {
			if (this.listener != null) {
				this.listener.onClickSettings();
			}
		});
	}
	
	/**
	 * 툴바 메뉴 클릭시 호출 될 인터페이스 설정 함수
	 * 
	 * @see ToolBarListener
	 * @param listener
	 */
	public void setToolBarListener(ToolBarListener listener) {
		this.listener = listener;
	}
	
	/**
	 * 툴바 메뉴 클릭시 호출하는 인터페이스
	 * @see Main 에 구현되어있음
	 */
	public interface ToolBarListener {
		/**
		 * 마이페이지 클릭시
		 */
		void onClickMyPage();

		/**
		 * 메시지박스 클릭시
		 */
		void onClickMessageBox();

		/**
		 * 돋보기 클릭시
		 */
		void onClickSearch();

		/**
		 * 정보 클릭시
		 */
		void onClickInfo();

		/**
		 * 톱니바퀴 클릭시
		 */
		void onClickSettings();
	}
}
