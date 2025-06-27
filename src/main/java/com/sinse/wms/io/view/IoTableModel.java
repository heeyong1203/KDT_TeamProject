package com.sinse.wms.io.view;

import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.util.TableModel;
import com.sinse.wms.product.model.IoRequest;


public class IoTableModel extends TableModel {
	private List<IoRequest> data;
    private String[] columns;     // { "선택", "No", "품목명", "품목코드", "진행상태", "상품단가", "입고수량", "현재재고수량" };
    private List<Boolean> selected; // 체크박스 상태 저장


    public IoTableModel(List<IoRequest> data, String status_type) {
        this.data = data;
        
        this.selected = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            selected.add(false); // 초기값 false
        }
        
        if ("입고".equals(status_type)) {
            this.columns = new String[] { "선택", "No", "품목명", "품목코드", "진행상태", "상품단가", "입고요청수량", "현재재고수량" };
        } else {
            this.columns = new String[] { "선택", "No", "품목명", "품목코드", "진행상태", "상품단가", "출고요청수량", "현재재고수량" };

        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int row, int col) {
        IoRequest io = data.get(row);

        switch (col) {
            case 0 : return selected.get(row); // 체크박스 상태
            case 1 : return row + 1 ; // no 1번부터
            case 2 : return io.getProduct().getProduct_name();   // 상품명
            case 3 : return io.getProduct().getProduct_code();  // 상품 코드
            case 4 : return io.getStatus().getStatus_name();    // 진행 상태
            case 5 : return io.getProduct().getProduct_price()+"원"; // 단가
            case 6 : if("승인".equals(io.getStatus().getStatus_name()) || "반려".equals(io.getStatus().getStatus_name())) {
            		 	return 0;
            		 } else return io.getQuantity()+" "+io.getProduct().getUnit().getUnit_name();// 요청 수량
            case 7 : return io.getProduct().getProduct_stock()+" "+io.getProduct().getUnit().getUnit_name(); // 재고량
            default : return null;
        }
    }
    
    @Override
    public void setValueAt(Object isChecked, int row, int col) {
        if (col == 0 && row < selected.size()) {
            selected.set(row, (Boolean) isChecked);
            fireTableCellUpdated(row, col); // UI 반영
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 0; // 체크박스만 수정 가능
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return col == 0 ? Boolean.class : Object.class; // 컬럼이 0이면 체크박스
    }

	/*---------------------------------------- 
	 * 선택된 row를 외부에서 가져오기 위한 메서드 추가	*	
	----------------------------------------*/
    public IoRequest getRowIo(int row) { 
        return data.get(row);
    }
    
    public List<IoRequest> getSelectedRows() { // 체크박스로 선택된 레코드 선택
        List<IoRequest> selectedRows = new ArrayList<>();
        for (int i = 0; i < selected.size(); i++) {
            if (Boolean.TRUE.equals(selected.get(i))) {
                selectedRows.add(data.get(i));
            }
        }
        return selectedRows;
    }

    public boolean isSelected(int row) { // 체크박스가 선택되었는지 확인하는 메소드
        return selected.get(row);
    }

    public void clearSelections() { // 체크박스를 초기화
        for (int i = 0; i < selected.size(); i++) {
            selected.set(i, false);
        }
        fireTableDataChanged();
    }
}
