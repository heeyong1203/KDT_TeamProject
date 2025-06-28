package com.sinse.wms.common.util;

import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.view.Main;
import com.sinse.wms.common.view.sidemenu.Menu;
import com.sinse.wms.common.view.sidemenu.SideMenuGroup;
import com.sinse.wms.common.view.sidemenu.SideSubMenu;
import com.sinse.wms.common.view.sidemenu.SideTopMenu;
import com.sinse.wms.common.view.sidemenu.BaseSideMenu.SideMenuClickListener;

public class SideMenuHelper {

	public static SideMenuGroup createSideMenu(Menu menu, SideMenuClickListener listener) {
		String topMenuName = "";
		String imageName = "";
		List<SideSubMenu> subMenus = new ArrayList<>();
		switch (menu) {
		case IN_BOUND_STATUS:
			topMenuName = "입고";
			imageName = "in_coming_icon.png";
			subMenus.add(new SideSubMenu(Menu.IN_BOUND_STATUS, "입고현황", listener));
			subMenus.add(new SideSubMenu(Menu.IN_BOUND_REQUEST, "입고요청", listener));
			subMenus.add(new SideSubMenu(Menu.IN_BOUND_INSPECTION, "입고검수", listener));
			break;
		case OUT_BOUND_STATUS:
			topMenuName = "출고";
			imageName = "out_going_icon.png";
			subMenus.add(new SideSubMenu(Menu.OUT_BOUND_STATUS, "출고현황", listener));
			subMenus.add(new SideSubMenu(Menu.OUT_BOUND_REQUEST, "출고요청", listener));
			subMenus.add(new SideSubMenu(Menu.OUT_BOUND_INSPECTION, "출고검수", listener));
			break;

		case INVENTORY_STATUS:
			topMenuName = "재고 현황";
			imageName = "inventory_icon.png";
			break;

		case STATISTICS:
			topMenuName = "통계 및 보고서";
			imageName = "graph_icon.png";
			subMenus.add(new SideSubMenu(Menu.STATISTICS, "통계", listener));
			subMenus.add(new SideSubMenu(Menu.REPORT, "보고서", listener));
			break;

		case USER_MANAGEMENT:
			topMenuName = "관리";
			imageName = "management_icon.png";
			subMenus.add(new SideSubMenu(Menu.USER_MANAGEMENT, "사용자 관리", listener));
			subMenus.add(new SideSubMenu(Menu.PRODUCT_MANAGEMENT, "상품 관리", listener));
			subMenus.add(new SideSubMenu(Menu.CATEGORY_COMPANY_UNIT_LOCATION_MANAGEMENT, "기타 관리", listener));
			break;
		default:
			throw new IllegalArgumentException("Not TopMenu");
		}
		SideTopMenu topMenu = new SideTopMenu(menu, topMenuName, imageName, listener);
		return new SideMenuGroup(topMenu, subMenus);
	}
}
