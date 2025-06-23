package com.sinse.wms.report.controller;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ReportTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        String status = table.getValueAt(row, 3).toString(); // 진행상태

        if (status.equals("입고 반려")) {
            comp.setForeground(Color.RED);
        } else if (status.equals("입고 대기")) {
            comp.setForeground(new Color(0, 128, 0)); // 초록
        } else {
            comp.setForeground(Color.BLACK);
        }

        return comp;
    }
}
