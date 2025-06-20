package com.sinse.wms.inventory.model;

import java.util.List;

import com.sinse.wms.common.util.TableModel;
import com.sinse.wms.product.model.Stock;
import com.sinse.wms.product.repository.StockDAO;

public class InventoryTableModel extends TableModel{
	StockDAO stockDAO;
	List<Stock> list;
	List<String> column;
	
	//생성자
	public InventoryTableModel(List<String> column) {
		stockDAO = new StockDAO();
		list = stockDAO.selectProductQuantity();
		this.column = column;
	}
	
	//몇개의 행인지 확인
	@Override
	public int getRowCount() {
		return list.size();
	}
	
	//몇개의 컬럼인지 확인
	@Override
	public int getColumnCount() {
		return column.size();
	}
	
	//컬럼 지정
	@Override
	public String getColumnName(int col) {
		return column.get(col);
	}
	
	//값 지정하기
	@Override
	public Object getValueAt(int row, int col) {
		Stock stock = list.get(row);
		String value = null;
		int count = 1;
		
		switch(col){
			case 0: value =Integer.toString(count); count++; break;
			case 1: value = stock.getProduct().getProduct_name(); break;
			case 2: value = stock.getProduct().getProduct_code(); break;
			case 3: value = Integer.toString(stock.getStock_quantity()); break;
			case 4: value = Integer.toString(stock.getProduct().getProduct_stock()); break;
			case 5: value = (stock.getStock_quantity() <= stock.getProduct().getProduct_stock())?"주문 필요":"적정"; break;
		}
		return value;
	}
}
