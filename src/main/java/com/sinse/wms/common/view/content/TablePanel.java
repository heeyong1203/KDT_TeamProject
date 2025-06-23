package com.sinse.wms.common.view.content;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.text.NumberFormat;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TablePanel extends JPanel {
	private DefaultTableModel model;
	private JTable table;
	private boolean editableFirstRow = false; // 첫 레코드 수정 가능 여부
	private boolean isStatusPage;
	
	
	/*------------------------------------------------
      테이블 렌더링 :	1) 숫자 → 천단위 콤마 
					2) 자료형 별 텍스트 정렬
					3) 체크박스, No 행 추가
	------------------------------------------------*/
	public TablePanel(DefaultTableModel tableModel, boolean isStatusPage, boolean editableFirstRow) {
		// 테이블 모델 생성
		this.model = tableModel;
		this.editableFirstRow = editableFirstRow;
		this.isStatusPage = isStatusPage;
		
		// 테이블 생성
		table = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int col) {
				if(row == 0 && !isStatusPage) {
					// 첫 번째 행: 체크박스(index 0), No(index 1) 항상 비활성화
					if (col == 0 || col == 1 || col ==4) return false;
					return editableFirstRow; // 나머지는 col : editableFirstRow값에 따라 결정됨
				}

				// 나머지 행: 체크박스만 편집 가능
				return col == 0;
			}
			
			@Override
			// 체크박스 생성
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0) return Boolean.class; // Boolean.class : 체크 박스
				return super.getColumnClass(columnIndex);
			}
		};
		
		// cell 렌더링 적용
		applyCellFormatting(table); 
		
		// scroll 생성
		JScrollPane scroll = new JScrollPane(table);

		// scroll 스타일 지정
		scroll.setPreferredSize(new Dimension(870, 570));

		// View Panel에 Scroll 붙이기
		setLayout(new BorderLayout());
		this.add(scroll, BorderLayout.CENTER);
	}

	/*------------------------------------------------
      테이블 셀 글씨 정렬 & 숫자 천단위 콤마 생성 함수
	------------------------------------------------*/
	private void applyCellFormatting(JTable table) {
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (table.getColumnClass(i) == Boolean.class) continue; // 체크박스 컬럼은 기본 렌더러 사용

			final int colIndex = i; // 현재 컬럼 인덱스를 담을 변수
			DefaultTableCellRenderer renderer;

			if (colIndex == 1) {
				renderer = new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, (!isStatusPage && row == 0) ? "" : value, isSelected,
									hasFocus, row, column);	
							setHorizontalAlignment(SwingConstants.CENTER);
							return this;							
					}
				};
			} else {
				renderer = new DefaultTableCellRenderer() {
					// 숫자 천 단위마다 콤마
					NumberFormat nf = NumberFormat.getNumberInstance();

					@Override 
					// 숫자 중앙 정렬
					protected void setValue(Object value) {
						if (value instanceof Number) { // 레코드 데이터가 숫자라면
							setHorizontalAlignment(SwingConstants.RIGHT); // 우측 정렬
							super.setValue(nf.format(value));	
						} 
						else { // 레코드 데이터가 숫자가 아니라면
							setHorizontalAlignment(SwingConstants.CENTER); // 중앙 정렬
							super.setValue(value);
						}
					}
				};
			}
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
	}
	
	/*------------------------------------------------
      모델 변경이 필요할 때 사용하는 함수
	------------------------------------------------*/
	public void setModel(DefaultTableModel model) {
		this.model = model;
		table.setModel(model);
		applyCellFormatting(table);
	}
	
	public JTable getTable() {
		return table;
	}
}