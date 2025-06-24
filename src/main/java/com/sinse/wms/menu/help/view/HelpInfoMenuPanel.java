package com.sinse.wms.menu.help.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HelpInfoMenuPanel extends JPanel{
	public HelpInfoMenuPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        contentPanel.add(createSection("▣ 소프트웨어 정보", new String[]{
                "버전: v1.0.0",
                "빌드 날짜: 2025년 6월 XX일",
                "라이선스: KDT Sinse team 3"
        }));

        contentPanel.add(createSection("▣ 최근 업데이트", new String[]{
                "• 도움말 기능 추가",
                "• UI 일부 개선 및 오류 수정"
        }));

        contentPanel.add(createSection("▣ 다음 예정 업데이트", new String[]{
        		"• 사용자 기능 추가",
                "• 다국어 지원 (예정)"
        }));

        contentPanel.add(createSection("▣ 고객 지원 및 문의", new String[]{
                "이메일: support@google.com",
                "전화: 02-1234-5678 (평일 9시~18시)",
                "FAQ: 도움말 > FAQ 탭을 참고해주세요"
        }));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createSection(String title, String[] lines) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(8));

        for (String line : lines) {
            JLabel lineLabel = new JLabel(line);
            lineLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
            lineLabel.setForeground(Color.DARK_GRAY);
            lineLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(lineLabel);
        }

        return panel;
    }
}
