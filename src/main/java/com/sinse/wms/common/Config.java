package com.sinse.wms.common;

import java.awt.Color;

public class Config {
	
	public static final int TOOLBAR_WIDTH = 100;
	public static final int TOOLBAR_HEIGHT = 960;
	public static final int TOOLBAR_ICON_WIDTH = 50;
	public static final int TOOLBAR_ICON_HEIGHT = 50;
	public static final String TOOLBAR_MY_PAGE_ICON_NAME = "person_icon.png";
	public static final String TOOLBAR_MESSAGE_ICON_NAME = "message_icon.png";
	public static final String TOOLBAR_SEARCH_ICON_NAME = "search_icon.png";
	public static final String TOOLBAR_INFO_ICON_NAME = "info_icon.png";
	public static final String TOOLBAR_SETTING_ICON_NAME = "setting_icon.png";
	
	public static final int MAIN_WINDOW_WIDTH = 1440;
	public static final int MAIN_WINDOW_HEIGHT = 960;
	
	public static final int SIDEMENU_TITLE_WIDTH = 350;
	public static final int SIDEMENU_TITLE_HEIGHT = 200;
	public static final int SIDEMENU_ICON_WIDTH = 75;
	public static final int SIDEMENU_ICON_HEIGHT = 75;
	public static final int TOP_MENU_WIDTH = 350;
	public static final int TOP_MENU_HEIGHT = 100;
	public static final int TOP_MENU_IMAGE_VIEW_WIDTH = 100;
	public static final int TOP_MENU_IMAGE_VIEW_HEIGHT = 100;
	public static final int TOP_MENU_NAME_WIDTH = 250;
	public static final int TOP_MENU_NAME_HEIGHT = 100;
	
	public static final int CONTENT_HEADER_WIDTH = 990;
	public static final int CONTENT_HEADER_HEIGHT = 100;
	public static final int CONTENT_HEADER_BORDER_WIDTH = 70;
	public static final int CONTENT_HEADER_BORDER_HEIGHT = 20;
	public static final int CONTENT_HEADER_BORDER_RADIUS = 70;
	public static final int CONTENT_HEADER_BORDER_STROKE_WIDTH = 1;
	public static final int CONTENT_HEADER_PROFILE_IMAGE_WIDTH = 40;
	public static final int CONTENT_HEADER_PROFILE_IMAGE_HEIGHT = 40;
	public static final String CONTENT_DEFAULT_PROFILE_IMAGE = "default_avatar_icon.png";
	
	public static final int CONTENT_BODY_WIDTH = 990;
	public static final int CONTENT_BODY_HEIGHT = 860;
	public static final int CONTENT_BODY_BORDER_WIDTH = 45;
	public static final int CONTENT_BODY_BORDER_HEIGHT = 20;
	public static final int CONTENT_BODY_BORDER_RADIUS = 70;
	
	public static final Color PRIMARY_COLOR = new Color(38, 124, 181);
	public static final Color TOP_MENU_SELECT_BG_COLOR = new Color(149, 199, 233);
	public static final Color TOP_MENU_UNSELECT_FONT_COLOR = new Color(138, 138, 138);
	
	public static final String APP_DEFAULT_FONT = "Dialog";
	
	public static final String DEFAULT_FILTER_TEXT_SUFFIX = "를 선택하세요";
	
	// 페이지 테이블 및 콤보박스 관련
	public static final String[] FILTER_COLUMNS = { "거래처", "부서명", "사원명", "품목코드", "품목명", "진행상태" };
    public static final String[] TABLE_COLUMNS = { "선택", "No", "품목명", "품목코드", "진행상태", "상품단가", "입고수량", "현재재고수량" };
    public static final String[] CURRENCY_UNITS = {"원", "¥", "€", "$"};
    public static final String[] QUANTITY_UNITS = {"개", "박스", "묶음", "건", "EA", "팔레트"};
	
	
	//DATABASE CONFIG
	public static final String URL = "jdbc:mysql://158.247.242.82:3306/sinse_wms";
	public static final String USER = "sinse3";
	public static final String PWD = "sinse3!@#";
	
}
