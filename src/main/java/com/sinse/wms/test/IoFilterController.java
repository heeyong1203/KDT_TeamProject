package com.sinse.wms.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import com.sinse.wms.product.model.IoRequest;
import com.sinse.wms.product.repository.IoRequestDAO;

public class IoFilterController {
    private IoComboBoxLayout comboPanel;
    private JTable table;
    private IoRequestDAO dao;
    private String ioRequestType;   // 예: "입고" 또는 "출고"
    private String statusName;      // 예: "요청", "검수요청", "승인", "반려"

    public IoFilterController(IoComboBoxLayout comboPanel, JTable table, String ioRequestType, String statusName) {
        this.comboPanel = comboPanel;
        this.table = table;
        this.ioRequestType = ioRequestType;
        this.statusName = statusName;
        this.dao = new IoRequestDAO();
    }

    public void loadTable() {
        List<String> filters = getSelectedFilterValues();
        List<IoRequest> result = dao.selectByFilter(ioRequestType, statusName, filters);
        IoTableModel model = new IoTableModel(result, ioRequestType);
        table.setModel(model);
    }
    
    private List<String> getSelectedFilterValues() {
        List<String> filters = new ArrayList<>();
        
        String company = (String) comboPanel.getCb_company().getSelectedItem();
        String dept = (String) comboPanel.getCb_dept().getSelectedItem();
        String member = (String) comboPanel.getCb_member().getSelectedItem();
        String productCode = (String) comboPanel.getCb_product_code().getSelectedItem();
        String productName = (String) comboPanel.getCb_product_name().getSelectedItem();
        String status = (String) comboPanel.getCb_status().getSelectedItem();

        filters.add(isValid(company) ? company : null);
        filters.add(isValid(dept) ? dept : null);
        filters.add(isValid(member) ? member : null);
        filters.add(isValid(productCode) ? productCode : null);
        filters.add(isValid(productName) ? productName : null);
        filters.add(isValid(status) ? status : null);

        return filters;
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty() && !value.contains("선택");
    }

    public void setIoRequestType(String ioRequestType) {
        this.ioRequestType = ioRequestType;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}