package com.sinse.wms.io.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

import com.sinse.wms.product.model.IoRequest;

public class IoFilterPanel extends JPanel {
	private IoComboBoxModel comboModel;
	private IoTableModel ioTableModel;
	private IoComboBoxLayout p_filters;
	private IoTableLayout tableLayout;
	private final String statusName;
    private final List<IoRequest> data;

	public IoFilterPanel(List<IoRequest> data, String statusName) {
		this.statusName = statusName;
        this.data = data;
        		
		IoComboBox();  // 내부에서 p_filters 생성 및 콤보 채우기
	    IoTable();     // 내부에서 tableLayout 생성

	    setLayout(new BorderLayout());
	    setOpaque(false);
	    
		add(p_filters, BorderLayout.NORTH);
		add(tableLayout, BorderLayout.CENTER);		
	}
	
	private IoComboBoxLayout IoComboBox() {
		// 콤보박스 껍데기 생성
		p_filters = new IoComboBoxLayout();

		// 콤보박스 모델 객체 생성
		comboModel = new IoComboBoxModel();
		comboModel.setTableCombo();

		// 콤보박스 아이템 연결
		p_filters.getCb_product_name().setModel(new DefaultComboBoxModel<>(comboModel.getProductNames().toArray(new String[0])));
		p_filters.getCb_product_code().setModel(new DefaultComboBoxModel<>(comboModel.getProductCodes().toArray(new String[0])));
		p_filters.getCb_member().setModel(new DefaultComboBoxModel<>(comboModel.getMemberNames().toArray(new String[0])));
		p_filters.getCb_dept().setModel(new DefaultComboBoxModel<>(comboModel.getDeptNames().toArray(new String[0])));
		p_filters.getCb_company().setModel(new DefaultComboBoxModel<>(comboModel.getCompanyNames().toArray(new String[0])));
		p_filters.getCb_status().setModel(new DefaultComboBoxModel<>(comboModel.getStatusNames().toArray(new String[0])));

		return p_filters;
	}
	
	private IoTableLayout IoTable() {
		ioTableModel = new IoTableModel(data, statusName);
		tableLayout = new IoTableLayout(ioTableModel);
		return tableLayout;
	}

	public IoComboBoxModel getComboModel() {
		return comboModel;
	}

	public void setComboModel(IoComboBoxModel comboModel) {
		this.comboModel = comboModel;
	}

	public IoTableModel getIoTableModel() {
		return ioTableModel;
	}

	public void setIoTableModel(IoTableModel ioTableModel) {
		this.ioTableModel = ioTableModel;
	}

	public IoComboBoxLayout getP_filters() {
		return p_filters;
	}

	public void setP_filters(IoComboBoxLayout p_filters) {
		this.p_filters = p_filters;
	}

	public IoTableLayout getTableLayout() {
		return tableLayout;
	}

	public void setTableLayout(IoTableLayout tableLayout) {
		this.tableLayout = tableLayout;
	}
}
