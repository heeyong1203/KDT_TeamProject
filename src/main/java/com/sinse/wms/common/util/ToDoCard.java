package com.sinse.wms.common.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 메인 페이지에 추가 될 to do list 기능
 * 
 * + 버튼을 누르면 자그마한 프레임창이 뜨고 정보를 입력한다.
 * 데이터는 메모리상에 저장한다
 * @author 박유신
 */

public class ToDoCard extends JPanel {
    private JPanel taskPanel;
    private List<JCheckBox> taskCheckboxes = new ArrayList<>();

    public ToDoCard() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("할 일 목록", SwingConstants.LEFT);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(title, BorderLayout.NORTH);

        taskPanel = new JPanel(new GridLayout(0, 1));
        taskPanel.setOpaque(false);
        add(taskPanel, BorderLayout.CENTER);

        JButton addButton = new JButton("+ 할 일 추가");
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> showAddTaskDialog());
        add(addButton, BorderLayout.SOUTH);
    }

    private void showAddTaskDialog() {
        JTextField taskField = new JTextField(15);
        JComboBox<String> priorityBox = new JComboBox<>(new String[]{"A", "B", "C"});
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("할 일 내용:"));
        panel.add(taskField);
        panel.add(new JLabel("우선순위:"));
        panel.add(priorityBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "새 할 일 추가", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String text = taskField.getText().trim();
            String priority = (String) priorityBox.getSelectedItem();
            if (!text.isEmpty()) {
                addTask(text, priority);
            }
        }
    }

    private void addTask(String content, String priority) {
        JCheckBox checkbox = new JCheckBox("⭐ " + content + "  ⬆ " + priority);
        checkbox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        checkbox.setBackground(Color.WHITE);
        checkbox.addItemListener(e -> {
            if (checkbox.isSelected()) {
                checkbox.setForeground(Color.GRAY);
            } else {
                checkbox.setForeground(Color.BLACK);
            }
        });

        taskCheckboxes.add(checkbox);
        taskPanel.add(checkbox);
        revalidate();
        repaint();
    }
}
