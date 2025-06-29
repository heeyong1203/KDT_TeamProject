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
	                setHorizontalAlignment(SwingConstants.RIGHT);				  // 숫자는 우측 정렬
	                if (value instanceof Number) {
	                    super.setValue(nf.format(value));
	                } else {
	                	setHorizontalAlignment(SwingConstants.CENTER); 			  // 텍스트는 중앙 정렬
	                    super.setValue(value);
	                }
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
                    double val = ((Number) value).doubleValue();
                    super.setValue(val == 0.0 ? "- " : nf.format(val) + "원");

                } else if (value instanceof String) {
                    String str = ((String) value).replaceAll("[^\\d.,-]", "");  // '숫자', '소수점', '단위콤마', 0을 의미하는 '-'만 남기기
                    try {
                        double parsed = Double.parseDouble(str);
                        super.setValue(parsed == 0.0 ? "- " : nf.format(parsed) + "원");
                    } catch (NumberFormatException e) {
                        super.setValue(value.toString());  // 숫자 변환 실패 시 원문 그대로
                    }

                } else if (value == null) {
                    super.setValue("- ");

                } else {
                    super.setValue(value.toString());
                }
            }
        };
    }
}