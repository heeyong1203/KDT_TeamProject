package com.sinse.wms.common.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 * TextFiled를 눌렀을 때 자동으로 yyyy-mm-dd 형식으로 입력되어질 수 있게함.
 * 스피너 또는 직접적인 텍스트 필드 입력으로 날짜를 입력할 수 있음
 * 
 * @author 박유신
 */

public class CalendarPopUp {

    // 텍스트 필드에 달력 팝업 연결
    public void addCalendarPopup(JTextField targetField) {
        targetField.setEditable(false); // 직접 입력 막기
        targetField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showCalendarPopup(targetField);
            }
        });
    }

    // 팝업 보여주기
    public void showCalendarPopup(JTextField targetField) {
        JPopupMenu popup = new JPopupMenu();
        JPanel calendarPanel = new JPanel(new BorderLayout());
        JCalendar calendar = new JCalendar(targetField, popup);
        calendarPanel.add(calendar, BorderLayout.CENTER);
        popup.add(calendarPanel);
        popup.show(targetField, 0, targetField.getHeight());
    }

    // 달력 내부 클래스
    private static class JCalendar extends JPanel {
        public JCalendar(JTextField target, JPopupMenu popup) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);

            // 날짜 선택 스피너
            JSpinner daySpinner = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1));
            JSpinner monthSpinner = new JSpinner(new SpinnerNumberModel(month + 1, 1, 12, 1));
            JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(year, 2000, 2100, 1));

            // 연도 스피너 포맷 조정
            JFormattedTextField yearField = ((JSpinner.NumberEditor) yearSpinner.getEditor()).getTextField();
            NumberFormatter formatter = new NumberFormatter(new DecimalFormat("####"));
            formatter.setMinimum(2000);
            formatter.setMaximum(2100);
            formatter.setAllowsInvalid(false); // 잘못된 값 막기
            formatter.setCommitsOnValidEdit(true); // 값이 유효하면 즉시 반영
            yearField.setFormatterFactory(new DefaultFormatterFactory(formatter));

            // 선택 버튼
            JButton selectBtn = new JButton("선택");

            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            add(yearSpinner); add(new JLabel("년"));
            add(monthSpinner); add(new JLabel("월"));
            add(daySpinner); add(new JLabel("일"));
            add(selectBtn);

            // 버튼 클릭 시 날짜 적용
            selectBtn.addActionListener(e -> {
                int y = (int) yearSpinner.getValue();
                int m = (int) monthSpinner.getValue();
                int d = (int) daySpinner.getValue();
                Calendar selected = Calendar.getInstance();
                selected.set(y, m - 1, d);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                target.setText(sdf.format(selected.getTime()));
                popup.setVisible(false);
            });
        }
    }
}
