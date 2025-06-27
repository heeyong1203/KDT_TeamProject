package com.sinse.wms.io.util;

import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;

import com.sinse.wms.io.view.IoComboBoxLayout;
import com.sinse.wms.io.view.IoFilterPanel;
import com.sinse.wms.io.view.IoTableModel;
import com.sinse.wms.product.model.IoRequest;
import com.sinse.wms.product.repository.IoRequestDAO;

public class IoSelectController {
    private IoFilterPanel view; // 테이블, 콤보박스를 가진 IoFilterPanel을 이용하여 각 테이블, 콤보박스 호출 가능
    private IoRequestDAO dao;
    private String ioRequestType; // 입고-출고
    private String statusName;    // 요청-검수요청-승인-반려

    public IoSelectController(IoFilterPanel view, String ioRequestType, String statusName) {
        this.view = view;
        this.ioRequestType = ioRequestType;
        this.statusName = statusName;
        this.dao = new IoRequestDAO();

        bindComboBoxEvents(); // 콤보박스 선택 시 테이블 갱신
        loadTable();          // 초기 테이블 데이터 로드
    }

    private void bindComboBoxEvents() {
        view.getP_filters().getCb_company().addActionListener(e -> loadTable());
        view.getP_filters().getCb_dept().addActionListener(e -> loadTable());
        view.getP_filters().getCb_member().addActionListener(e -> loadTable());
        view.getP_filters().getCb_product_code().addActionListener(e -> loadTable());
        view.getP_filters().getCb_product_name().addActionListener(e -> loadTable());
        view.getP_filters().getCb_status().addActionListener(e -> loadTable());
    }

    private void loadTable() {
        List<String> filters = getSelectedFilters();
        List<IoRequest> result = dao.selectByFilter(ioRequestType, statusName, filters);
        IoTableModel model = new IoTableModel(result, ioRequestType);
        
        view.getTableLayout().getTable().setModel(model);
        view.getTableLayout().applyRenderers(); // 스타일 적용
    }

    private List<String> getSelectedFilters() {
        IoComboBoxLayout combo = view.getP_filters();
        return Arrays.asList(
            getValid(combo.getCb_company()),
            getValid(combo.getCb_dept()),
            getValid(combo.getCb_member()),
            getValid(combo.getCb_product_code()),
            getValid(combo.getCb_product_name()),
            getValid(combo.getCb_status())
        );
    }

    private String getValid(JComboBox<String> cb) {
        String selected = (String) cb.getSelectedItem();
        return (selected != null && !selected.contains("선택")) ? selected : "";
    }
}