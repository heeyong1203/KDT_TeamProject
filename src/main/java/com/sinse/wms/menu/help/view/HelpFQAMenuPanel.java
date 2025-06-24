package com.sinse.wms.menu.help.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpFQAMenuPanel extends JPanel{
	public HelpFQAMenuPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 좌우 여백

        // FAQ 항목 추가
        contentPanel.add(createFAQItem(
                "Q. 상품을 등록하려면 어떻게 하나요?",
                "A. [입고 요청] 또는 [출고 요청] 탭에서 단일 등록 또는 일괄 등록 버튼을 클릭하여 상품 정보를 등록할 수 있습니다."
        ));
        contentPanel.add(createFAQItem(
                "Q. 재고 수량은 언제 업데이트되나요?",
                "A. 입고 검수가 완료되거나 출고가 확정된 이후 자동으로 업데이트됩니다."
        ));
        contentPanel.add(createFAQItem(
                "Q. 요청 상태가 '대기'에서 바뀌지 않아요.",
                "A. 요청 승인/거절 작업이 수행되지 않은 상태입니다. 담당자가 처리하면 상태가 변경됩니다."
        ));
        contentPanel.add(createFAQItem(
                "Q. 엑셀 내보내기는 어디서 할 수 있나요?",
                "A. [입고 현황], [출고 현황], [재고 현황], [보고서] 탭 하단의 '엑셀' 버튼을 클릭하면 가능합니다."
        ));
        contentPanel.add(createFAQItem(
                "Q. 내 정보는 어떻게 확인 하나요?",
                "A. 왼쪽 상단의 사람모양 아이콘을 선택하여 마이 페이지로 이동하신 후 확인하실 수 있습니다."
        ));
        contentPanel.add(createFAQItem(
                "Q. 프로그램이 강제로 종료되었어요.",
                "A. 최신 Java 버전과 DB 연결 설정을 확인해주세요. 문제가 반복되면 관리자에게 문의 바랍니다."
        ));


        // 스크롤 가능하도록
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도 조절

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createFAQItem(String question, String answer) {
    	JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel arrowLabel = new JLabel("▶");
        arrowLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        arrowLabel.setForeground(Color.DARK_GRAY);

        JLabel qLabel = new JLabel(question);
        qLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        qLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.X_AXIS));
        questionPanel.setBackground(Color.WHITE);
        questionPanel.add(arrowLabel);
        questionPanel.add(Box.createRigidArea(new Dimension(8, 0)));
        questionPanel.add(qLabel);
        questionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea aLabel = new JTextArea(answer);
        aLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        aLabel.setEditable(false);
        aLabel.setLineWrap(true);
        aLabel.setWrapStyleWord(true);
        aLabel.setBackground(Color.WHITE);
        aLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 0));
        aLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        aLabel.setVisible(false);

        // 높이를 텍스트 길이에 맞게 자동 조절
        aLabel.setRows(Math.max(2, answer.length() / 40));
        aLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, aLabel.getPreferredSize().height));

        questionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean isVisible = aLabel.isVisible();
                aLabel.setVisible(!isVisible);
                arrowLabel.setText(isVisible ? "▶" : "▼");
                itemPanel.revalidate();
                itemPanel.repaint();
            }
        });

        itemPanel.add(questionPanel);
        itemPanel.add(aLabel);
        return itemPanel;
    }
}
