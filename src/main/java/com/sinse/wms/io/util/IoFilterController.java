package com.sinse.wms.io.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import com.sinse.wms.io.view.IoComboBoxLayout;
import com.sinse.wms.io.view.IoTableLayout;
import com.sinse.wms.io.view.IoTableModel;
import com.sinse.wms.product.model.IoRequest;
import com.sinse.wms.product.repository.IoRequestDAO;

public class IoFilterController {
    private IoComboBoxLayout comboPanel;
    private IoTableLayout tableLayout;
    private IoRequestDAO dao;
    private String ioRequestType;   // 예: "입고" 또는 "출고"
    private String statusName;      // 예: "요청", "검수요청", "승인", "반려"

    public IoFilterController(IoComboBoxLayout comboPanel, IoTableLayout tableLayout, String ioRequestType, String statusName) {
        this.comboPanel = comboPanel;
        this.tableLayout = tableLayout;
        this.ioRequestType = ioRequestType;
        this.statusName = statusName;
        this.dao = new IoRequestDAO();
    }

    public void loadTable() {
        List<String> filters = getSelectedFilterValues();
        System.out.println("조회 필터: " + filters);
        List<IoRequest> result = dao.selectByFilter(ioRequestType, statusName, filters);
        IoTableModel model = new IoTableModel(result, ioRequestType);
        
        tableLayout.getTable().setModel(model);
        tableLayout.applyRenderers();       
    }
    
    private List<String> getSelectedFilterValues() {
        List<String> filters = new ArrayList<>();
        
        String company = (String) comboPanel.getCb_company().getSelectedItem();
        String dept = (String) comboPanel.getCb_dept().getSelectedItem();
        String member = (String) comboPanel.getCb_member().getSelectedItem();
        String productCode = (String) comboPanel.getCb_product_code().getSelectedItem();
        String productName = (String) comboPanel.getCb_product_name().getSelectedItem();
        String status = (String) comboPanel.getCb_status().getSelectedItem();

        filters.add(isValid(company) ? company : "");
        filters.add(isValid(dept) ? dept : "");
        filters.add(isValid(member) ? member : "");
        filters.add(isValid(productCode) ? productCode : "");
        filters.add(isValid(productName) ? productName : "");
        filters.add(isValid(status) ? status : "");

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