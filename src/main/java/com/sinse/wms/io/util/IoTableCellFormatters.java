package com.sinse.wms.io.util;

import java.text.NumberFormat;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.sinse.wms.io.regist.IoRegistPageController;

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
            	if (value instanceof Number) {
                    Number num = (Number) value;
                    setHorizontalAlignment(SwingConstants.RIGHT);
                    if (num.doubleValue() == 0.0) {
                        super.setValue("- "); // ← 여기서 0을 대체
                    } else {
                        super.setValue(nf.format(num));
                    }
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
    
    /*------------------------------------ 
     * 화페 단위가 들어가더라도 우측 정렬		*
	------------------------------------*/
    public static DefaultTableCellRenderer priceAlign() {
        return new DefaultTableCellRenderer() {
            private final NumberFormat nf = NumberFormat.getNumberInstance();
            @Override
            protected void setValue(Object value) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                if (value instanceof Number) {
                    Number num = (Number) value;
                    if (num.doubleValue() == 0.0) {
                        super.setValue("- ");
                    } else {
                        super.setValue(nf.format(num) + "원");
                    }
                } else if (value == null) {
                    super.setValue("- ");
                } else {
                    // 예외적으로 숫자 아닌데 뭔가 표시할 값이 온 경우
                    super.setValue(value.toString());
                }
            }
        };
    }
}