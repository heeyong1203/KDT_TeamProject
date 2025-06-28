package com.sinse.wms.management.location.view;

import com.sinse.wms.common.exception.CompanyInsertException;
import com.sinse.wms.common.exception.CompanyUpdateException;
import com.sinse.wms.common.exception.LocationInsertException;
import com.sinse.wms.common.exception.LocationUpdateException;
import com.sinse.wms.management.BaseEtcDialog;
import com.sinse.wms.product.model.Location;
import com.sinse.wms.product.repository.LocationDAO;

import javax.swing.*;

public class LocationDialog extends BaseEtcDialog<Location> {
    private LocationDAO locationDAO = new LocationDAO();

    public LocationDialog(String titleName) {
        super(titleName);
    }

    public LocationDialog(String titleName, Location data) {
        super(titleName, data);
    }

    @Override
    protected String getDefaultTextFieldData() {
        return this.data.getLocation_name();
    }

    @Override
    protected void insert() {
        if (isInvalid()) {
            JOptionPane.showMessageDialog(null, "값을 입력해 주세요.");
            return;
        }
        try {
            Location insertLocation = new Location();
            insertLocation.setLocation_name(this.tf_input.getText().trim());
            locationDAO.insert(insertLocation);
            JOptionPane.showMessageDialog(null, "등록 완료 되었습니다.");
            disposeWithComplete();
        } catch (LocationInsertException e) {
            JOptionPane.showMessageDialog(null, "등록 실패 되었습니다.");
        }
    }

    @Override
    protected void update() {
        if (isInvalid()) {
            JOptionPane.showMessageDialog(null, "값을 입력해 주세요.");
            return;
        }
        try {
            Location updateLocation = new Location();
            updateLocation.setLocation_id(this.data.getLocation_id());
            updateLocation.setLocation_name(this.tf_input.getText().trim());
            locationDAO.update(updateLocation);
            JOptionPane.showMessageDialog(null, "수정 완료 되었습니다.");
            disposeWithComplete();
        } catch (LocationUpdateException e) {
            JOptionPane.showMessageDialog(null, "수정 실패 되었습니다.");
        }
    }

    private boolean isInvalid() {
        return this.tf_input.getText().trim().isEmpty();
    }

}
