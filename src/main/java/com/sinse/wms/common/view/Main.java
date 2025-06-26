package com.sinse.wms.common.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.util.SideMenuHelper;
import com.sinse.wms.common.view.content.ContentHeader;
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
import com.sinse.wms.main.view.MainPage;
import com.sinse.wms.membermanagement.view.MemberManagementPage;
import com.sinse.wms.menu.help.view.HelpMenu;
import com.sinse.wms.menu.search.view.SearchMenu;
import com.sinse.wms.menu.setting.view.SettingMenu;
import com.sinse.wms.mypage.view.Mypage;
import com.sinse.wms.outbound.view.OutboundInspectionPage;
import com.sinse.wms.outbound.view.OutboundRequestPage;
import com.sinse.wms.outbound.view.OutboundStatusPage;
import com.sinse.wms.product.model.Member;
import com.sinse.wms.report.view.ReportPage;
import com.sinse.wms.statistic.view.StatisticPage;
import com.sinse.wms.inbound.view.InboundInspectionPage;
import com.sinse.wms.inbound.view.InboundRequestPage;
import com.sinse.wms.inbound.view.InboundStatusPage;
import com.sinse.wms.inventory.view.InventoryStatusPage;
import com.sinse.wms.outbound.view.OutboundInspectionPage;
import com.sinse.wms.outbound.view.OutboundRequestPage;
import com.sinse.wms.outbound.view.OutboundStatusPage;

public class Main extends JFrame implements SideMenuClickListener, ToolBarListener {
    private ToolBar toolbar;
    private SideBar sidebar;
    private JPanel centerWrapper;
    private JPanel bodyWrapper;
    private ContentHeader header;
    private JPanel bodyContent;
    private CardLayout cardLayout;
    private SideMenuGroup[] sideMenuGroups;
    Member m; // 로그인한 사용자 정보 받아옴

    public Main(Member m) {
        this.m = m;
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

        toolbar.setToolBarListener(this);

        setVisible(true);
        pack();
    }

    /**
     * 툴바 초기화 함수
     */
    private void initToolbar() {
        this.toolbar = new ToolBar();
        this.toolbar.setToolBarListener(this);
    }

    /**
     * 사이드메뉴바 초기화 함수
     */
    private void initSideBar() {
        this.sideMenuGroups = new SideMenuGroup[] {
            SideMenuHelper.createSideMenu(Menu.IN_BOUND_STATUS, this),
            SideMenuHelper.createSideMenu(Menu.OUT_BOUND_STATUS, this),
            SideMenuHelper.createSideMenu(Menu.INVENTORY_STATUS, this),
            SideMenuHelper.createSideMenu(Menu.STATISTICS, this),
            SideMenuHelper.createSideMenu(Menu.USER_MANAGEMENT, this)
        };
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
		this.bodyContent.add(new MainPage(Color.WHITE,m), Menu.MAIN.name());
		this.bodyContent.add(new InboundStatusPage(Color.RED), Menu.IN_BOUND_STATUS.name());
		this.bodyContent.add(new InboundRequestPage(Color.LIGHT_GRAY), Menu.IN_BOUND_REQUEST.name());
		this.bodyContent.add(new InboundInspectionPage(Color.PINK), Menu.IN_BOUND_INSPECTION.name());
		this.bodyContent.add(new OutboundStatusPage(Color.GRAY), Menu.OUT_BOUND_STATUS.name());
		this.bodyContent.add(new OutboundRequestPage(Color.MAGENTA), Menu.OUT_BOUND_REQUEST.name());
		this.bodyContent.add(new OutboundInspectionPage(Color.BLUE), Menu.OUT_BOUND_INSPECTION.name());
		this.bodyContent.add(new InventoryStatusPage(Color.YELLOW), Menu.INVENTORY_STATUS.name());
		this.bodyContent.add(new StatisticPage(), Menu.STATISTICS.name());
		this.bodyContent.add(new ReportPage(Color.white), Menu.REPORT.name()); // 통계 및 보고서 현황 페이지
		this.bodyContent.add(new MemberManagementPage(), Menu.USER_MANAGEMENT.name());
		this.bodyContent.add(new Mypage(Color.white,m), Menu.MY_PAGE.name());	
		this.bodyContent.add(new HelpMenu(), Menu.HELP.name());	//도움말 페이지
		this.bodyContent.add(new SettingMenu(), Menu.SETTING.name());	//환경설정 페이지
	}

    /**
     * 사이드 메뉴 클릭시 해당 인터페이스 함수로
     *
     * @param menu 가 파라미터로 호출됨
     * @see SideMenuClickListener
     */
    @Override
    public void onClick(Menu menu) {
        onChangeMenu(menu);
    }

    /**
     * 메뉴가 변경되었을때 핸들링 함수를 호출하는 함수
     * 사이드메뉴를 핸들링 하는 함수와 컨텐츠를 핸들링하는 함수를 호출한다.
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
	
	/**
	 * 검색 후 페이지를 이동하는 함수
	 */
	public void openMenuByKey(String key) {
        try {
            Menu menu = Menu.valueOf(key);
            onChangeMenu(menu);  // 화면 전환 로직 호출
        } catch (IllegalArgumentException e) {
            System.err.println("유효하지 않은 메뉴 키: " + key);
        }
    }

    @Override
    public void onClickMyPage() {
        System.out.println("마이페이지클릭"); // 클릭 이벤트 수신
        this.cardLayout.show(this.bodyContent, Menu.MY_PAGE.name());
    }

    @Override
    public void onClickMessageBox() {
        // TODO("툴바 메시지 아이콘 클릭시 구현");
    }

	@Override
	public void onClickSearch() {
		//한 번만 실행
		SearchMenu search = SearchMenu.getInstance(this);
		search.setVisible(true);
	}

	@Override
	public void onClickInfo() {
		this.cardLayout.show(this.bodyContent, Menu.HELP.name());
		
	}

	@Override
	public void onClickSettings() {
		this.cardLayout.show(this.bodyContent, Menu.SETTING.name());
	}
}
