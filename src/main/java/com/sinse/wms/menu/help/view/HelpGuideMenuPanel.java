package com.sinse.wms.menu.help.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class HelpGuideMenuPanel extends JPanel {
	public HelpGuideMenuPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("📘 기능별 도움말 가이드");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(20, 20, 10, 20));

        // 내용을 담는 패널 (스크롤)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        // 섹션 추가
        contentPanel.add(createSection("입고", new String[][]{
                {"입고 현황", "• 상단 콤보박스를 통해 조건 조회 가능\n• 체크박스로 삭제 가능\n• 엑셀 및 PDF로 내보내기 가능"},
                {"입고 요청", "• 단일 등록 및 일괄 등록 기능\n• 요청 수락/거절 기능"},
                {"입고 검수", "• 단일 등록 및 일괄 등록 기능\n• 요청 수락/거절 기능"}
        }));

        contentPanel.add(createSection("출고", new String[][]{
                {"출고 현황", "• 입고와 동일한 인터페이스 및 기능 제공"}
        }));

        contentPanel.add(createSection("재고 현황", new String[][]{
                {"재고 조회", "• 상단 콤보박스로 조건 조회\n• 엑셀 / PDF export 기능"}
        }));

        contentPanel.add(createSection("통계 및 보고서", new String[][]{
                {"시각화 통계", "• 입출고량을 일/주/월 단위로 막대그래프 제공\n• 카테고리별 재고/출고 시각화\n• Top 5 품목 통계\n• 창고 포화도 그래프"},
                {"보고서", "• 다양한 필터 및 기간 선택 기능\n• 프린트 / Export 지원 (입고, 출고, 재고 등)"}
        }));

        contentPanel.add(createSection("사용자 관리", new String[][]{
                {"사용자 관리", "• 사용자 목록 조회 및 다이얼로그 기반 등록/수정/삭제"}
        }));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 부드럽게

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

	private JPanel createSection(String sectionTitle, String[][] subSections) {
	    JPanel sectionPanel = new JPanel();
	    sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
	    sectionPanel.setBackground(new Color(250, 250, 250));
	    sectionPanel.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createEmptyBorder(10, 0, 20, 0),
	            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220))
	    ));

	    JLabel sectionLabel = new JLabel("🔹 " + sectionTitle);
	    sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
	    sectionLabel.setBorder(new EmptyBorder(10, 0, 5, 0));
	    sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT); //왼쪽 정렬 적용
	    sectionPanel.add(sectionLabel);

	    for (String[] sub : subSections) {
	        sectionPanel.add(createSubSection(sub[0], sub[1]));
	    }

	    return sectionPanel;
	}

    private JPanel createSubSection(String title, String description) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel("• " + title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JTextArea textArea = new JTextArea(description);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(textArea, BorderLayout.CENTER);

        return panel;
    }
}
