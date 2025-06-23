package com.sinse.wms.common.view.content;

import javax.swing.JPanel;

public class FilterPanelResult {
	public JPanel panel;
	public LabeledComboBox[] filters;
	
	public FilterPanelResult(JPanel panel, LabeledComboBox[] filters) {
		this.panel = panel; // 반환할 콤보박스가 붙은 패널
		this.filters = filters; // 반환할 콤보박스 배열
	}
}