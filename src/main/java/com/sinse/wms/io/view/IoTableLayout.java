package com.sinse.wms.io.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.sinse.wms.common.util.TableModel;
import com.sinse.wms.io.util.IoTableCellFormatters;

public class IoTableLayout extends JPanel {
    private JTable table;
    private JScrollPane scroll;

    public IoTableLayout(IoTableModel model) {
        setLayout(new BorderLayout());
        setOpaque(false);
        
        table = new JTable(model);
        
        table.getColumnModel().getColumn(0).setCellRenderer(table.getDefaultRenderer(Boolean.class));
        table.getColumnModel().getColumn(0).setCellEditor(table.getDefaultEditor(Boolean.class));
        applyRenderers();        
        
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(870, 570));
        add(scroll, BorderLayout.CENTER);
    }
    
    public void applyRenderers() {
        TableModel model = this.getTableModel();
        for (int i = 1; i < model.getColumnCount(); i++) {
            String colName = model.getColumnName(i);
            if ("No".equals(colName)) {
                table.getColumnModel().getColumn(i).setCellRenderer(IoTableCellFormatters.centerAlignNumber());
            } else if(colName.contains("단가")){
            	table.getColumnModel().getColumn(i).setCellRenderer(IoTableCellFormatters.priceAlign());
            } else {
                table.getColumnModel().getColumn(i).setCellRenderer(IoTableCellFormatters.commonAlign());
            }
            table.updateUI();
        }
    }

    public JTable getTable() {
        return table;
    }

    public void setTableModel(IoTableModel model) {
        table.setModel(model);
    }

    public TableModel getTableModel() {
        return (TableModel) table.getModel();
    }
}
