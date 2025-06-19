package com.sinse.wms.inventory.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.common.view.content.LabeledComboBox;

public class InventoryStatusPage extends BaseContentPage {
    JPanel p_wrapper;
    LabeledComboBox[] filters;

    public InventoryStatusPage(Color color) {
        // ... 기본 스타일 설정 ...

        p_wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
        p_wrapper.setPreferredSize(new Dimension(870, 80));
        p_wrapper.setOpaque(false);

        // 라벨 및 JComboBox 사이즈 지정
        Dimension labelSize = new Dimension(80, 30);
        Dimension comboSize = new Dimension(230, 30);
        Dimension comboSize2 = new Dimension(150, 30);

        // 라벨 및 JComboBox 묶음 지정
        filters = new LabeledComboBox[] { // new String[]{} 대신 데이터 배열 넣어주시면 될 것 같습니다.
            new LabeledComboBox("거래처", new String[]{"A사", "B사", "C사"}, labelSize, comboSize),
            new LabeledComboBox("부서명", new String[]{"영업1팀", "영업2팀", "마케팅1팀", "마케팅2팀"}, labelSize, comboSize2),
            new LabeledComboBox("사원명", new String[]{"이경규", "김국진", "강호동", "유재석", "전현무", "장도연"}, labelSize, comboSize2),
            new LabeledComboBox("품목코드", new String[]{"ST-20001", "ST-20002", "ST-20003", "ST-20004", "ST-20005", "ST-20006"}, labelSize, comboSize),
            new LabeledComboBox("품목명", new String[]{"A4 복사용지 80g", "3단 서류함", "유성 네임펜", "고무줄 500g"}, labelSize, comboSize2),
            new LabeledComboBox("진행상태", new String[]{"요청", "대기", "승인", "반려"}, labelSize, comboSize2)
        };

        for (LabeledComboBox filter : filters) {
            p_wrapper.add(filter);
        }

        add(p_wrapper);
    }
}
