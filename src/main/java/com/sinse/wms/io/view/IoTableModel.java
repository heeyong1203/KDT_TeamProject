package com.sinse.wms.io.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinse.wms.common.util.TableModel;
import com.sinse.wms.product.model.IoRequest;
import com.sinse.wms.product.model.ProductSnapshot;
import com.sinse.wms.product.repository.ProductSnapshotDAO;


public class IoTableModel extends TableModel {
    private List<IoRequest> data;
    private String[] columns;
    private List<Boolean> selected;
    private Map<Integer, ProductSnapshot> snapshotMap;

    public IoTableModel(List<IoRequest> data, String status_type) {
        this.data = data;
        this.selected = new ArrayList<>();
        this.snapshotMap = new HashMap<>();

        for (IoRequest io : data) {
            selected.add(false);
            ProductSnapshot snap = new ProductSnapshotDAO().findByPk(io.getIoRequest_id());
            if (snap != null) snapshotMap.put(io.getIoRequest_id(), snap);
        }
        
        this.columns = ("입고".equals(status_type))
            ? new String[] { "선택", "No", "품목명", "품목코드", "진행상태", "상품단가", "입고요청수량", "현재재고수량" }
            : new String[] { "선택", "No", "품목명", "품목코드", "진행상태", "상품단가", "출고요청수량", "현재재고수량" };
        }

    @Override public int getRowCount() { return data.size(); }

    @Override public int getColumnCount() { return columns.length; }

    @Override public String getColumnName(int column) { return columns[column]; }
    
    @Override
    public Object getValueAt(int row, int col) {
    	IoRequest io = data.get(row);
        ProductSnapshot snap = snapshotMap.get(io.getIoRequest_id());
        
        switch (col) {
            case 0 : return selected.get(row); // 체크박스 상태
            case 1 : return row + 1 ; // no 1번부터
            case 2 : return io.getProduct().getProduct_name();   // 상품명
            case 3 : return io.getProduct().getProduct_code();  // 상품 코드
            case 4 : return io.getStatus().getStatus_name();    // 진행 상태
            case 5 : return (snap != null) ? snap.getProduct_price() + "원" : 0; // product_snapshot의 단가
            case 6 :
                if ("승인".equals(io.getStatus().getStatus_name()) || "반려".equals(io.getStatus().getStatus_name())) {
                    return 0;
                }
                return io.getQuantity() + " " + io.getProduct().getUnit().getUnit_name();// 요청 수량 + 단위
            case 7 : return io.getProduct().getProduct_stock()+" "+io.getProduct().getUnit().getUnit_name(); // 재고량 + 단위
            default : return null;
        }
    }
    
    @Override
    public void setValueAt(Object isChecked, int row, int col) {
        if (col == 0 && row < selected.size()) {
            selected.set(row, (Boolean) isChecked);
            fireTableCellUpdated(row, col); // 셀 값 변경을 테이블에 반영하는 메소드
        }
    }
    
    @Override public boolean isCellEditable(int row, int col) { return col == 0; } // 체크박스만 수정 가능
    @Override public Class<?> getColumnClass(int col) { return col == 0 ? Boolean.class : Object.class; } // 컬럼이 0이면 체크박스

    public IoRequest getRowIo(int row) { return data.get(row); }
    
    public List<IoRequest> getSelectedRows() { // 체크박스로 선택된 레코드 선택
        List<IoRequest> selectedRows = new ArrayList<>();
        for (int i = 0; i < selected.size(); i++) {
            if (Boolean.TRUE.equals(selected.get(i))) {
                selectedRows.add(data.get(i));
            }
        }
        return selectedRows;
    }

    public boolean isSelected(int row) { return selected.get(row); } // 체크박스가 선택되었는지 확인하는 메소드

    public void clearSelections() { // 체크박스를 초기화
        for (int i = 0; i < selected.size(); i++) {
            selected.set(i, false);
        }
        fireTableDataChanged();
    }
}
