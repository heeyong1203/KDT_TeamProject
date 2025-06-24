package com.sinse.wms.inbound.view;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.content.LabeledComboBox;
import com.sinse.wms.product.model.IoRequest;

public class IoFilterHelper {

    /**
     * LabeledComboBox 배열에서 필터값들을 읽어와서 List<String>으로 반환
     */
    public static List<String> getFilterValues(LabeledComboBox[] filters) {
        List<String> values = new ArrayList<>();
        for (LabeledComboBox combo : filters) {
            String value = combo.getSelectedItem();
            if (value != null && value.contains(Config.DEFAULT_FILTER_TEXT_SUFFIX)) {
                values.add(null);
            } else {
                values.add(value);
            }
        }
        return values;
    }
}