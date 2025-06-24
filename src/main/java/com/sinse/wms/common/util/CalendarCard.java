package com.sinse.wms.common.util;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.*;

public class CalendarCard extends JPanel {
    private JPanel dayPanel;
    private JLabel titleLabel;
    private YearMonth currentMonth;

    public CalendarCard() {
        currentMonth = YearMonth.now();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // 🔹 상단: 월 이동 버튼 + 월 라벨
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton prevBtn = new JButton("〈");
        JButton nextBtn = new JButton("〉");
        prevBtn.setFocusPainted(false);
        nextBtn.setFocusPainted(false);
        prevBtn.setBorderPainted(false);
        nextBtn.setBorderPainted(false);
        prevBtn.setContentAreaFilled(false);
        nextBtn.setContentAreaFilled(false);

        prevBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        nextBtn.setFont(new Font("SansSerif", Font.BOLD, 16));

        prevBtn.addActionListener((ActionEvent e) -> {
            currentMonth = currentMonth.minusMonths(1);
            updateCalendar();
        });

        nextBtn.addActionListener((ActionEvent e) -> {
            currentMonth = currentMonth.plusMonths(1);
            updateCalendar();
        });

        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        topPanel.add(prevBtn, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(nextBtn, BorderLayout.EAST);
        topPanel.setBackground(Color.WHITE);

        // 🔹 요일 헤더 (일~토)
        JPanel weekPanel = new JPanel(new GridLayout(1, 7));
        String[] days = {"일", "월", "화", "수", "목", "금", "토"};
        for (int i = 0; i < 7; i++) {
            JLabel lbl = new JLabel(days[i], SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
            lbl.setForeground(i == 0 ? Color.RED : Color.DARK_GRAY);
            weekPanel.add(lbl);
        }
        weekPanel.setBackground(new Color(245, 245, 245));

        // 🔹 날짜 표시 패널 (6행 7열)
        dayPanel = new JPanel(new GridLayout(6, 7)); // 중요!
        dayPanel.setBackground(Color.WHITE);

        // 🔹 달력 전체 묶음
        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBackground(Color.WHITE);
        calendarPanel.add(weekPanel, BorderLayout.NORTH);
        calendarPanel.add(dayPanel, BorderLayout.CENTER);

        // 🔹 조립
        add(topPanel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);

        updateCalendar(); // 최초 그리기
    }

    private void updateCalendar() {
        dayPanel.removeAll();

        LocalDate today = LocalDate.now();
        LocalDate firstDay = currentMonth.atDay(1);
        int daysInMonth = currentMonth.lengthOfMonth();
        int firstIndex = firstDay.getDayOfWeek().getValue() % 7; // 일=0

        titleLabel.setText(currentMonth.getYear() + "년 " + currentMonth.getMonthValue() + "월");

        // 앞쪽 빈칸
        for (int i = 0; i < firstIndex; i++) {
            dayPanel.add(new JLabel(""));
        }

        // 날짜 표시
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = currentMonth.atDay(day);
            JLabel lbl = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
            lbl.setOpaque(true);
            lbl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            if (currentDate.equals(today)) {
                lbl.setBackground(Color.RED);
                lbl.setForeground(Color.WHITE);
            } else {
                lbl.setBackground(Color.WHITE);
                lbl.setForeground(Color.BLACK);
            }

            dayPanel.add(lbl);
        }

        // 부족한 칸은 공백으로 채움 (총 42칸 유지)
        int totalCells = 42;
        int filled = firstIndex + daysInMonth;
        for (int i = filled; i < totalCells; i++) {
            dayPanel.add(new JLabel(""));
        }

        revalidate();
        repaint();
    }
}
