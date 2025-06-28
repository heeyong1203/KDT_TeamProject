package com.sinse.wms.common.view.sidemenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * @see SideTopMenu
 * @see SideSubMenu
 * 
 *      두 메뉴를 합치기 위한 클래스
 */
public class SideMenuGroup extends JPanel {
	private SideTopMenu topMenu;
	private List<SideSubMenu> subMenus;
	private JPanel topMenuWrapper;
	private JPanel subMenuWrapper;

	public SideMenuGroup(SideTopMenu topMenu, List<SideSubMenu> subMenus) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		initTopMenu(topMenu);
		initSubMenu(subMenus);
		add(topMenuWrapper);
		add(subMenuWrapper);
	}

	private void initTopMenu(SideTopMenu topMenu) {
		this.topMenu = topMenu;
		this.topMenuWrapper = new JPanel(new BorderLayout());
		this.topMenuWrapper.setMaximumSize(topMenu.getMinimumSize());
		topMenuWrapper.add(topMenu);
	}

	private void initSubMenu(List<SideSubMenu> subMenus) {
		this.subMenus = subMenus;
		this.subMenuWrapper = new JPanel();
		this.subMenuWrapper.setLayout(new BoxLayout(subMenuWrapper, BoxLayout.Y_AXIS));
		this.subMenuWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.subMenuWrapper.setVisible(false);

		if (this.subMenus != null) {
			for (SideSubMenu subMenu : this.subMenus) {
				subMenuWrapper.add(subMenu);
			}
		}
	}

	/**
	 * 파리미터로 주입된 메뉴가 @see SideMenuGroup#subMenus 에 존재하는지 판단하는 함수
	 * 
	 * @param menu
	 * @return 메뉴 존재 유/무
	 */
	private boolean hasSubMenu(Menu menu) {
		if (subMenus == null) {
			return false;
		} else {
			for (int i = 0; i < subMenus.size(); i++) {
				if (subMenus.get(i).getMenu() == menu) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * 선택된 메뉴가 변경시 호출 되는 함수 파라미터로 주입되는 메뉴에 따라서 UI 업데이트를 처리한다.
	 * 
	 * @param menu
	 */

	public void onChangeMenu(Menu menu) {
		boolean hasMenu = menu == topMenu.getMenu() || hasSubMenu(menu);
		topMenu.selectedMenu(hasMenu);
		subMenuWrapper.setVisible(hasMenu);

		if (hasMenu) {
			for (int i = 0; i < subMenus.size(); i++) {
				SideSubMenu subMenu = subMenus.get(i);
				Menu sMenu = subMenu.getMenu();
				subMenus.get(i).selectedMenu(sMenu == menu);
			}
		}

	}
}
