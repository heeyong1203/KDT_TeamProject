package com.sinse.wms.common.view.content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;

import com.sinse.wms.common.Config;

public class LabeledComboBox extends JPanel {
	private JLabel label;
	private JComboBox<Object> comboBox;
		
	public LabeledComboBox(String labelText, String[] items, Dimension labelSize, Dimension comboSize) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		label = new JLabel(labelText);
        label.setPreferredSize(labelSize);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        comboBox = new JComboBox<>(items);
        comboBox.setPreferredSize(comboSize);
        setOpaque(false);
		
        add(label);
        add(comboBox);
	}
	
	public String getSelectedItem() {
		return (String) comboBox.getSelectedItem();
	}
	
	public static FilterPanelResult createFilterPanel(TableModel model, String[] columnNames, Dimension labelSize, Dimension comboSize) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 7));
		panel.setPreferredSize(new Dimension(870, 80));
		panel.setOpaque(false);
		
		LabeledComboBox[] boxes = new LabeledComboBox[columnNames.length];
		
		// 특정 모델의 각 컬럼명에 대한 인덱스 추출
		for (int i = 0; i < columnNames.length; i++) {
			String columnName = columnNames[i];
			int colIndex = getColumnIndex(model, columnName);
			
			LabeledComboBox box = null; 
			// ComboBox 첫 번째 항목으로 디폴트 선택 항목 추가 (컬럼명을 선택하세요)
			if (colIndex != -1) {
				Set<String> items = new LinkedHashSet<>();
				items.add(columnName+"를 선택하세요"); 
			
				// ComboBox 선택 항목으로 해당 컬럼명에 대한 테이블 레코드 값 추가
				for (int row=0; row<model.getRowCount(); row++) {
					Object value = model.getValueAt(row, colIndex);
					if (value != null) items.add(value.toString());
				}
				box = new LabeledComboBox(columnName, items.toArray(new String[0]), labelSize, comboSize);
				panel.add(box);
			} else {
				// 데이터가 없더라도 콤보박스 생성
				box = new LabeledComboBox(columnName, new String[] {columnName + Config.DEFAULT_FILTER_TEXT_SUFFIX}, labelSize, comboSize);
				panel.add(box);
			}

			boxes[i] = box; // null 방지용 무조건 대입
		}
		
		return new FilterPanelResult(panel, boxes); // panel과 콤보박스를 직접 선택 가능
	} 
	
	/*----------------------------------
	  tableModel 내 특정 ColumnIndex 찾기 
	----------------------------------*/
	private static int getColumnIndex(TableModel model, String ColumnName) {
		for (int i = 0; i < model.getColumnCount(); i++) {
			if(model.getColumnName(i).equals(ColumnName)) { // tableModel의 컬럼명 = 컬럼명 문자열인 경우 
				return i; // 인덱스 반환
			}
		}
		return -1;
	}
}