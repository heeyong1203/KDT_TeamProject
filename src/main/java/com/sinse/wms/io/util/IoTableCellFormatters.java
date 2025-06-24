package com.sinse.wms.io.util;

import java.text.NumberFormat;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class IoTableCellFormatters {
	// IoTableLayout에서 적용
	
    /*------------------------------------ 
     * 숫자 우측 정렬 + 콤마 && 텍스트 중앙 정렬	*
	------------------------------------*/
    public static DefaultTableCellRenderer commonAlign() {
        return new DefaultTableCellRenderer() {
            private final NumberFormat nf = NumberFormat.getNumberInstance(); // 천 단위 콤마표시
            @Override
            protected void setValue(Object value) {
                setHorizontalAlignment(SwingConstants.RIGHT);				  // 숫자는 우측 정렬
                if (value instanceof Number) {
                    super.setValue(nf.format(value));
                } else {
                	setHorizontalAlignment(SwingConstants.CENTER); 			  // 텍스트는 중앙 정렬
                    super.setValue(value);
                }
            }
        };
    }

    /*------------------------------------ 
     * 콤마 없는 숫자 중앙 정렬 // No 전용		*
	------------------------------------*/
    public static DefaultTableCellRenderer centerAlignNumber() {
        return new DefaultTableCellRenderer() {{
            setHorizontalAlignment(SwingConstants.CENTER);
        }};
    }
}