package com.sinse.wms.common.view.sidemenu;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class BaseSideMenu extends JPanel {
	/**
	 * 고유 메뉴를 할당하기 위한 변수
	 * 
	 * @see Menu
	 */
	private Menu menu;

	/**
	 * 화면에 표시할 메뉴이름
	 */
	protected String name;

	/**
	 * 메뉴 클릭시 호출 할 인터페이스
	 * 
	 * @see SideMenuClickListener
	 */
	private SideMenuClickListener listener = null;

	public BaseSideMenu(Menu menu) {
		this(menu, "", null);
	}

	public BaseSideMenu(Menu menu, String neme) {
		this(menu, neme, null);
	}

	public BaseSideMenu(Menu menu, SideMenuClickListener listenr) {
		this(menu, "", listenr);
	}

	public BaseSideMenu(Menu menu, String neme, SideMenuClickListener listenr) {
		this.setBorder(BorderFactory.createEmptyBorder());
		this.menu = menu;
		this.name = neme;
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (BaseSideMenu.this.listener != null) {
					BaseSideMenu.this.listener.onClick(menu);
				}
			}
		});
		this.listener = listenr;
		this.setPreferredSize(getSize());
	}

	/**
	 * 현재 메뉴가 선택 되었을시에 처리 되어야하는 로직을 정의해야하는 추상 함수
	 * 
	 * @param isSelected
	 */
	abstract protected void selectedMenu(boolean isSelected);

	/**
	 * 현재 클래스에 설정된 메뉴를 반환하는 함수
	 * 
	 * @return Menu
	 */
	public Menu getMenu() {
		return this.menu;
	}

	/**
	 * 메뉴 클릭 시 호출 될 인터페이스를 설정하는 함수
	 * 
	 * @param listener
	 */
	public void setMenuClickListener(SideMenuClickListener listener) {
		this.listener = listener;
	}

	/**
	 * 현재 사이즈를 반환 할 수 있도록 정의하는 추상 함수 목적 : @see BaseSideMenu 생성자에서 호출하는
	 * JPanel#setPreferredSize() 에서 사용하기 위함
	 */
	public abstract Dimension getSize();
	
	public interface SideMenuClickListener {
		void onClick(Menu menu);
	}
}
