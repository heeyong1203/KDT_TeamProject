package com.sinse.wms.management.product.view;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.sinse.wms.product.model.Product;

public class ProductManagementTableModel extends AbstractTableModel {
	private List<Product> products = new ArrayList<>();

	public ProductManagementTableModel() {

	}

	public ProductManagementTableModel(List<Product> products) {
		this.products.addAll(products);
	}

	public void setProducts(List<Product> products) {
		this.products.clear();
		this.products = products;
	}
	
	public Product getProductAt(int rowIndex) {
		return this.products.get(rowIndex);
	}

	@Override
	public int getRowCount() {
		return this.products.size();
	}

	@Override
	public int getColumnCount() {
		return 9;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "상품이름";
		case 1:
			return "카테고리";
		case 2:
			return "가격";
		case 3:
			return "상품 코드";
		case 4:
			return "안전재고 수량";
		case 5:
			return "창고 구역";
		case 6:
			return "거래처";
		case 7:
			return "단위";
		default:
			return "등록일";
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Product product = this.products.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return product.getProduct_name();
		case 1:
			return product.getCategory().getCategory_name();
		case 2:
			NumberFormat format = NumberFormat.getNumberInstance();
			try {
				return format.format(product.getProduct_price());
			} catch (Exception e) {
				return "ERROR";
			}
		case 3:
			return product.getProduct_code();
		case 4:
			return product.getProduct_stock();
		case 5:
			return product.getLocation().getLocation_name();
		case 6:
			return product.getCompany().getCompany_name();
		case 7:
			return product.getUnit().getUnit_name();
		default:
			return product.getRegdate();
		}
	}

}
