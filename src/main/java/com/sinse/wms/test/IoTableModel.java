package com.sinse.wms.test;

import java.util.List;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.util.TableModel;
import com.sinse.wms.product.model.IoRequest;

public class IoTableModel extends TableModel {
	private final List<IoRequest> data;
    private final String[] columns;
    // { "선택", "No", "품목명", "품목코드", "진행상태", "상품단가", "입고수량", "현재재고수량" };

    public IoTableModel(List<IoRequest> data, String status_type) {
        this.data = data;
        if ("입고".equals(status_type)) {
            this.columns = new String[] { "선택", "No", "품목명", "품목코드", "진행상태", "상품단가", "입고수량", "현재재고수량" };
        } else {
            this.columns = new String[] { "선택", "No", "품목명", "품목코드", "진행상태", "상품단가", "출고수량", "현재재고수량" };
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
        Object value = null;
        
        switch (col) {
            case 0 : value = false; break; // 체크박스 논리값
            case 1 : value = row + 1 ; break; // no 1번부터
            case 2 : value = io.getProduct().getProduct_name(); break; // 상품명
            case 3 : value = io.getProduct().getProduct_code(); break; // 상품 코드
            case 4 : value = io.getStatus().getStatus_name(); break; // 진행 상태
            case 5 : value = io.getProduct().getProduct_price(); break; // 단가
            case 6 : value = io.getQuantity(); break; // 입고 수량
            case 7 : value = io.getProduct().getProduct_stock(); break; // 재고량
        }
        return value;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 0; // 체크박스만 수정 가능
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return col == 0 ? Boolean.class : Object.class;
    }

    // 선택된 로우를 외부에서 가져오기 위한 메서드 추가도 가능
    public IoRequest getRowIo(int row) {
        return data.get(row);
    }
}
