package com.sinse.wms.inbound.view;

import java.io.ObjectInputStream.GetField;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.sinse.wms.product.model.IoRequest;

public class IoViewHelper {
	/*------------------------------------------------
      현황 테이블용 모델 생성
  	------------------------------------------------*/
	public static DefaultTableModel toTableModel(List<IoRequest> list, String[] columnNames) {
        return toTableModel(list, columnNames, null, false);
    }
	
	/*------------------------------------------------
      요청 및 검수 테이블용 모델 생성 - 오버로딩
  	------------------------------------------------*/
    public static DefaultTableModel toTableModel(List<IoRequest> list, String[] columnNames, String defaultStatus, boolean includeEmptyRow) {
    	Vector<String> colNames = new Vector<>(); // 컬럼명을 담아둘 vector
		for (String col : columnNames) colNames.add(col); // 컬럼명 vector에 column 하나씩 담기
		
		Vector<Vector<Object>> data = new Vector<>(); // 데이터들을 담는 vector (배열로 치면 이중 배열)
		int no = 0; // no행 데이터 초기값
		
		// 레코드 입력 가능한 비어 있는 행 추가
		if(includeEmptyRow) {
			Vector<Object> emptyRow = new Vector<>(); 
			emptyRow.add(null); // 선택 체크박스
			emptyRow.add(0); // no는 0
			for(int i = 2; i < columnNames.length; i++) {
				if("진행상태".equals(columnNames[i])) {
					emptyRow.add(defaultStatus); // 기본 상태 지정
				} else {
					emptyRow.add("");
				}
			}
			//emptyRow.get(1).setOpaque(false); 이렇게 하면 체크박스 안보일 줄 알았는데...
			data.add(emptyRow);
		}
		
		// 실제 데이터 행 추가
		for (IoRequest io : list) {
			Vector<Object> row = new Vector<>();
			row.add(false); // 체크박스
			row.add(++no); // no
			for(int i = 2; i < columnNames.length; i++) {
				row.add(getFieldValue(io, columnNames[i]));
			}
			data.add(row);
		}
		
		return new DefaultTableModel(data, colNames) {
			@Override
			public Class<?> getColumnClass(int colIndex) {
				if (colIndex == 0) return Boolean.class; // 첫 열은 체크박스
				return Object.class; // 나머지 열은 체크박스 아닌 원본 출력...
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 0; // 체크박스만 수정 가능
			}
		};
    }
    
	/*------------------------------------------------
      콤보박스 필터용 모델 생성 (필터 조회용)
  	------------------------------------------------*/
	public static DefaultTableModel toFilterModel(List<IoRequest> list, String[] columnNames) {
		Vector<String> colNames = new Vector<>(); // 컬럼명을 담아둘 vector
		for (String col : columnNames) colNames.add(col); // 컬럼명 vector에 column 하나씩 담기
		
		Vector<Vector<Object>> data = new Vector<>(); 
				
		// DB 데이터 추가
		for (IoRequest io : list) {
			Vector<Object> row = new Vector<>(); 
			for (String col : columnNames) {
				row.add(getFieldValue(io, col)); // 입출고 필드값으로 변환하여 데이터 꺼내 담기
			}
			data.add(row);
		}
		return new DefaultTableModel(data, colNames);
	}
	
	
	/*------------------------------------------------
      UI 컬럼명 → IoRequest의 필드값으로 변환
  	------------------------------------------------*/
	public static Object getFieldValue(IoRequest io, String uiName) {
		try {
			switch (uiName) {
				case "거래처": return safe(io.getProduct().getCompany().getCompany_name());
				case "부서명": return safe(io.getRequest_member_id().getDept().getDept_name());
				case "사원명": return safe(io.getRequest_member_id().getMember_name());
				case "품목코드": return safe(io.getProduct().getProduct_code());
				case "품목명": return safe(io.getProduct().getProduct_name());
				case "진행상태": return safe(io.getStatus().getStatus_name());
				case "상품단가": return io.getProduct().getProduct_price();
				case "입고수량": return io.getQuantity();
				case "현재재고수량": return io.getProduct().getProduct_stock();
				default: return "";
			}
		} catch (Exception e) {
			System.err.println("[getFieldValue 오류] " + uiName + ": " + e.getMessage());
			return "";
		}
	}
	
	private static String safe(String value) {
		return value == null ? "" : value;
	}
}