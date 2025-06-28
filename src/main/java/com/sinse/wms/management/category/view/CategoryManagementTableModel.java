package com.sinse.wms.management.category.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.sinse.wms.product.model.Category;

public class CategoryManagementTableModel extends AbstractTableModel {
	private List<Category> categories = new ArrayList<>();

	public CategoryManagementTableModel() {
	
	}

	public void setCategories(List<Category> categories) {
		this.categories.clear();
		this.categories.addAll(categories);
	}

	public Category getCategory(int index) {
		return categories.get(index);
	}

	@Override
	public int getRowCount() {
		return this.categories.size();
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
		Category category = this.categories.get(rowIndex);
		return category.getCategory_name();
	}

}
