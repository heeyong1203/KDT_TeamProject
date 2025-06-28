package com.sinse.wms.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.sinse.wms.common.util.TableModel;

public class IoTableLayout extends JPanel {
    private JTable table;
    private JScrollPane scroll;

    public IoTableLayout(TableModel model) {
        setLayout(new BorderLayout());
        setOpaque(false);

        table = new JTable(model);
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(870, 570));

        add(scroll, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }

    public void setTableModel(TableModel model) {
        table.setModel(model);
    }

    public TableModel getTableModel() {
        return (TableModel) table.getModel();
    }
}
