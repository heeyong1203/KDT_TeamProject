package com.sinse.wms.management.productunit.view;

import com.sinse.wms.product.model.ProductUnit;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductUnitTableModel extends AbstractTableModel {
	private List<ProductUnit> productUnits = new ArrayList<>();

	public void setProductUnits(List<ProductUnit> productUnits) {
		this.productUnits.clear();
		this.productUnits.addAll(productUnits);
	}

	@Override
	public int getRowCount() {
		return this.productUnits.size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int column) {
		return "이름";
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProductUnit company = this.productUnits.get(rowIndex);
		return company.getUnit_name();
	}

}
