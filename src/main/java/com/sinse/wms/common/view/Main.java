package com.sinse.wms.common.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.util.SideMenuHelper;
import com.sinse.wms.common.view.content.ContentHeader;
import com.sinse.wms.common.view.content.ExampleContentPage;
import com.sinse.wms.common.view.sidemenu.BaseSideMenu.SideMenuClickListener;
import com.sinse.wms.common.view.sidemenu.Menu;
import com.sinse.wms.common.view.sidemenu.SideBar;
import com.sinse.wms.common.view.sidemenu.SideMenuGroup;
import com.sinse.wms.common.view.toolbar.ToolBar;
import com.sinse.wms.common.view.toolbar.ToolBar.ToolBarListener;
import com.sinse.wms.statistic.view.StatisticPage;
import com.sinse.wms.inbound.view.InboundInspectionPage;
import com.sinse.wms.inbound.view.InboundRequestPage;
import com.sinse.wms.inbound.view.InboundStatusPage;
import com.sinse.wms.inventory.view.InventoryStatusPage;
import com.sinse.wms.membermanagement.view.MemberManagementPage;
import com.sinse.wms.outbound.view.OutboundInspectionPage;
import com.sinse.wms.outbound.view.OutboundRequestPage;
import com.sinse.wms.outbound.view.OutboundStatusPage;
import com.sinse.wms.report.view.ReportPage;

public class Main extends JFrame implements SideMenuClickListener, ToolBarListener {
	private ToolBar toolbar;
	private SideBar sidebar;
	private JPanel centerWrapper;
	private JPanel bodyWrapper;
	private ContentHeader header;
	private JPanel bodyContent;
	private CardLayout cardLayout;
	private SideMenuGroup[] sideMenuGroups;

	public Main() {
		setSize(Config.MAIN_WINDOW_WIDTH, Config.MAIN_WINDOW_HEIGHT);
		initToolbar();
		initSideBar();
		initHeader();
		initContents();

		this.bodyWrapper = new JPanel(new BorderLayout());
		this.bodyWrapper.add(header, BorderLayout.NORTH);
		this.bodyWrapper.add(bodyContent, BorderLayout.CENTER);
		this.centerWrapper = new JPanel(new BorderLayout());
		this.centerWrapper.add(sidebar, BorderLayout.WEST);
		this.centerWrapper.add(bodyWrapper, BorderLayout.CENTER);

		add(toolbar, BorderLayout.WEST);
		add(centerWrapper, BorderLayout.CENTER);

		setVisible(true);
		pack();
	}

	/**
	 * 툴바 초기화 함수
	 */
	private void initToolbar() {
		this.toolbar = new ToolBar();
	}

	/**
	 * 사이드메뉴바 초기화 함수
	 */
	private void initSideBar() {
		this.sideMenuGroups = new SideMenuGroup[] { SideMenuHelper.createSideMenu(Menu.IN_BOUND_STATUS, this),
				SideMenuHelper.createSideMenu(Menu.OUT_BOUND_STATUS, this),
				SideMenuHelper.createSideMenu(Menu.INVENTORY_STATUS, this),
				SideMenuHelper.createSideMenu(Menu.STATISTICS, this),
				SideMenuHelper.createSideMenu(Menu.USER_MANAGEMENT, this) };
		this.sidebar = new SideBar("사용자 페이지", sideMenuGroups);
	}

	/**
	 * 헤더 초기화 함수
	 */
	private void initHeader() {
		this.header = new ContentHeader("마케팅 1팀", "김범수");
	}

	/**
	 * 메뉴에 따라서 유저에게 보여지는 콘텐츠를 초기화 하는 함수
	 * 
	 * @see Menu
	 */
	private void initContents() {
		this.cardLayout = new CardLayout();
		this.bodyContent = new JPanel(cardLayout);
		this.bodyContent.add(new ExampleContentPage(Color.WHITE), Menu.MAIN.name());
		this.bodyContent.add(new InboundStatusPage(Color.RED), Menu.IN_BOUND_STATUS.name());
		this.bodyContent.add(new InboundRequestPage(Color.LIGHT_GRAY), Menu.IN_BOUND_REQUEST.name());
		this.bodyContent.add(new InboundInspectionPage(Color.PINK), Menu.IN_BOUND_INSPECTION.name());
		this.bodyContent.add(new OutboundStatusPage(Color.GRAY), Menu.OUT_BOUND_STATUS.name());
		this.bodyContent.add(new OutboundRequestPage(Color.MAGENTA), Menu.OUT_BOUND_REQUEST.name());
		this.bodyContent.add(new OutboundInspectionPage(Color.BLUE), Menu.OUT_BOUND_INSPECTION.name());
		this.bodyContent.add(new InventoryStatusPage(Color.YELLOW), Menu.INVENTORY_STATUS.name());
//		this.bodyContent.add(new ExampleContentPage(Color.ORANGE), Menu.STATISTICS.name());
		this.bodyContent.add(new StatisticPage(Color.WHITE), Menu.STATISTICS.name());
		this.bodyContent.add(new ReportPage(Color.white),Menu.REPORT.name()); //통계 및 보고서 현황 페이지
		this.bodyContent.add(new MemberManagementPage(), Menu.USER_MANAGEMENT.name());
	}

	/**
	 * 사이드 메뉴 클릭시 해당 인터페이스 함수로
	 * 
	 * @param Menu 가 파라미터로 호출됨
	 * @see SideMenuClickListener
	 */
	@Override
	public void onClick(Menu menu) {
		onChangeMenu(menu);
	}

	/**
	 * 메뉴가 변경되었을때 핸들링 함수를 호출하는 함수 사이드메뉴를 핸들링 하는 함수와 컨텐츠를 핸들링하는 함수를 호출한다.
	 * 
	 * @param menu
	 */
	private void onChangeMenu(Menu menu) {
		hangleSideMenu(menu);
		handleContent(menu);
	}

	/**
	 * 사이드메뉴의 레이아웃을 업데이트 하는 함수
	 * 
	 * @param menu
	 */
	private void hangleSideMenu(Menu menu) {
		for (int i = 0; i < sideMenuGroups.length; i++) {
			sideMenuGroups[i].onChangeMenu(menu);
		}
	}

	/**
	 * 컨텐츠를 교체하는 하는 함수
	 * 
	 * @param menu
	 */
	private void handleContent(Menu menu) {
		cardLayout.show(bodyContent, menu.name());
	}

	@Override
	public void onClickMyPage() {
		// TODO("툴바 사람 아이콘 클릭시 구현");
	}

	@Override
	public void onClickMessageBox() {
		// TODO("툴바 메시지 아이콘 클릭시 구현");
	}

	@Override
	public void onClickSearch() {
		// TODO("툴바 돋보기 아이콘 클릭시 구현");
	}

	@Override
	public void onClickInfo() {
		// TODO("툴바 돋보기 정보 아이콘 클릭시 구현");
	}

	@Override
	public void onClickSettings() {
		// TODO("툴바 돋보기 톱니바퀴 아이콘 클릭시 구현");
	}
}
