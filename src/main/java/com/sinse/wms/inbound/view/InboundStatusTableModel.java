package com.sinse.wms.inbound.view;
// 현재 dummy data를 채울 JTable의 TableModel

import javax.swing.table.AbstractTableModel;

public class InboundStatusTableModel extends AbstractTableModel {
	private String[] columnNames = {
    		"입출고 구분 PK", "입출고 구분", "상품 FK", "요청 수량", "적재 위치 FK", "요청 사원명", "요청 사유", 
				"처리 상태", "요청 시간", "입출고 예정일", "요청 승인자", "승인 시간", "비고"
    };

    private Object[][] data;

    public InboundStatusTableModel() {
        int rowCount = 15;
        data = new Object[rowCount][columnNames.length];
        for (int i = 0; i < rowCount; i++) {
            data[i][0] = false; // Select (체크박스용)
            data[i][1] = "Row" + (i + 1);
            data[i][2] = "Data Col 2";
            data[i][3] = "Data Col 3";
            data[i][4] = "Data Col 4";
            data[i][5] = "Data Col 5";
            data[i][6] = "Data Col 6";
        }
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col); // JTable에게 값이 바뀌었다고 알림
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 0; // 첫 번째 열(체크박스)만 수정 가능
    }

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return super.getColumnClass(columnIndex);
	}
}
