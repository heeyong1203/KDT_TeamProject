package com.sinse.wms.management.company.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.sinse.wms.product.model.Company;

public class CompanyTableModel extends AbstractTableModel {
	private List<Company> companies = new ArrayList<>();

	public void setCompanies(List<Company> companies) {
		this.companies.clear();
		this.companies.addAll(companies);
	}

	@Override
	public int getRowCount() {
		return this.companies.size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int column) {
		return "거래처";
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Company company = this.companies.get(rowIndex);
		return company.getCompany_name();
	}

}
