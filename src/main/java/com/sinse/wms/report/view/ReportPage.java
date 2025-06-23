package com.sinse.wms.report.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

import com.sinse.wms.common.StockRecord;
import com.sinse.wms.common.util.CalendarPopUp;
import com.sinse.wms.common.util.ExcelFileExport;
import com.sinse.wms.common.util.PdfFileExport;
import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.product.repository.StockDAO;

public class ReportPage extends BaseContentPage {
    JPanel p_filter;
    JPanel p_filter_north;
    JPanel p_filter_south;

    JToggleButton bt_inbound, bt_outbound, bt_changing_stock;
    JToggleButton bt_product, bt_amount, bt_unit, bt_price, bt_result;
    JButton bt_search;
    JButton bt_pdf, bt_excel, bt_print;

    JTextField tf_startDate, tf_endDate;

    JTable table;
    DefaultTableModel tableModel;

    public ReportPage(Color color) {
        setBackground(color);
        setLayout(new BorderLayout());

        initFilterPanel();
        setupTable();
        setupBottomButtons();
        registerListeners();
    }

    private void initFilterPanel() {
        p_filter = new JPanel(new BorderLayout());
        p_filter.setPreferredSize(new Dimension(870, 150));
        p_filter.setOpaque(false);
        p_filter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        p_filter_north = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        p_filter_north.setOpaque(false);

        bt_inbound = new JToggleButton("입고");
        bt_outbound = new JToggleButton("출고");
        bt_changing_stock = new JToggleButton("재고 변경");
        Dimension btSize = new Dimension(100, 50);
        for (JToggleButton btn : new JToggleButton[]{bt_inbound, bt_outbound, bt_changing_stock}) {
            btn.setPreferredSize(btSize);
            p_filter_north.add(btn);
        }

        tf_startDate = new JTextField(8);
        tf_endDate = new JTextField(8);
        JLabel lb_start = new JLabel("시작일");
        JLabel lb_end = new JLabel("종료일");

        p_filter_north.add(lb_start);
        p_filter_north.add(tf_startDate);
        p_filter_north.add(lb_end);
        p_filter_north.add(tf_endDate);

        ButtonGroup group = new ButtonGroup();
        group.add(bt_inbound);
        group.add(bt_outbound);
        group.add(bt_changing_stock);

        CalendarPopUp calendarPopUp = new CalendarPopUp();
        calendarPopUp.addCalendarPopup(tf_startDate);
        calendarPopUp.addCalendarPopup(tf_endDate);

        p_filter_south = new JPanel(new BorderLayout());
        p_filter_south.setOpaque(false);

        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        togglePanel.setOpaque(false);
        Dimension toggleSize = new Dimension(100, 50);

        bt_product = new JToggleButton("품목");
        bt_amount = new JToggleButton("수량");
        bt_unit = new JToggleButton("단가");
        bt_price = new JToggleButton("금액");
        bt_result = new JToggleButton("검수 결과");

        for (JToggleButton btn : new JToggleButton[]{bt_product, bt_amount, bt_unit, bt_price, bt_result}) {
            btn.setPreferredSize(toggleSize);
            togglePanel.add(btn);
        }

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        searchPanel.setOpaque(false);
        bt_search = new JButton("조회하기");
        bt_search.setPreferredSize(new Dimension(120, 50));
        searchPanel.add(bt_search);

        p_filter_south.add(togglePanel, BorderLayout.WEST);
        p_filter_south.add(searchPanel, BorderLayout.EAST);

        p_filter.add(p_filter_north, BorderLayout.NORTH);
        p_filter.add(p_filter_south, BorderLayout.SOUTH);
        add(p_filter, BorderLayout.NORTH);
    }

    public void setupTable() {
        String[] cols = {"품목", "수량", "단가", "금액", "검수 결과", "일자"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(870, 400));
        add(scroll, BorderLayout.CENTER);
    }

    private void setupBottomButtons() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setOpaque(false);

        bt_pdf = new JButton("PDF 저장");
        bt_excel = new JButton("엑셀 저장");
        bt_print = new JButton("프린트");

        bottomPanel.add(bt_pdf);
        bottomPanel.add(bt_excel);
        bottomPanel.add(bt_print);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void registerListeners() {
        ActionListener listener = e -> {
            String type = "";
            if (bt_inbound.isSelected()) type = "입고";
            else if (bt_outbound.isSelected()) type = "출고";
            else if (bt_changing_stock.isSelected()) type = "변경";

            String start = tf_startDate.getText();
            String end = tf_endDate.getText();

            List<StockRecord> result = new StockDAO().selectRequestByTypeAndDate(type, start, end);
            updateTable(result);
        };

        bt_inbound.addActionListener(listener);
        bt_outbound.addActionListener(listener);
        bt_changing_stock.addActionListener(listener);
        bt_search.addActionListener(listener);

        bt_pdf.addActionListener(e -> {
            String[] titles = {"품목", "수량", "단가", "금액", "검수 결과", "일자"};
            PdfFileExport.exportToPdf(titles, tableModel, "/Users/bag-yusin/KDT_TeamProject/src/main/resources/testfiles", "재고 리포트 PDF");
        });

        bt_excel.addActionListener(e -> {
            String[] titles = {"품목", "수량", "단가", "금액", "검수 결과", "일자"};
            List<String> titleList = Arrays.asList(titles); 
            ExcelFileExport.exportToExcel(titleList, tableModel, "report");
        });
    }

    private void updateTable(List<StockRecord> records) {
        tableModel.setRowCount(0);

        
        if (records == null || records.isEmpty()) {
            JOptionPane.showMessageDialog(this, "조회된 결과가 없습니다.", "알림", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 결과가 있을 때만 테이블 채움
        for (StockRecord r : records) {
            tableModel.addRow(new Object[]{
                r.getProductName(),
                r.getAmount(),
                r.getUnitPrice(),
                r.getTotalPrice(),
                r.getCheckResult(),
                r.getDate()
            });
        }
    }
}
