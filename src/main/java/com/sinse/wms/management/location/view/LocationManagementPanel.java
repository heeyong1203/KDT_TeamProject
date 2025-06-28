package com.sinse.wms.management.location.view;

import com.sinse.wms.common.exception.LocationDeleteException;
import com.sinse.wms.management.BaseEtcTablePanel;
import com.sinse.wms.product.model.Location;
import com.sinse.wms.product.repository.LocationDAO;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.List;

public class LocationManagementPanel extends BaseEtcTablePanel<Location,LocationDAO> {
	public LocationManagementPanel() {
		super(new LocationDAO());
	}

	@Override
	protected void init() {
		setModifyButtonEnable(false);
		setDeleteButtonEnable(false);
	}

	@Override
	protected List<Location> getData() {
		return this.currentDAO.selectAll();
	}

	@Override
	protected TableModel getTableModel() {
		LocationTableModel model = new LocationTableModel();
		model.setLocations(this.data);
		return model;
	}

	@Override
	protected String getTitle() {
		return "창고위치 관리";
	}

	@Override
	protected void onSearch() {
		String input = this.getInput();
		this.data = this.currentDAO.selectByNameLike(input);
		setModel();
	}

	@Override
	protected void onAdd() {
		LocationDialog dialog = new LocationDialog("창고 위치 관리");
		dialog.setOnComplete(()->{
			refresh();
		});
		dialog.setVisible(true);
	}

	@Override
	protected void onModify() {
		LocationDialog dialog = new LocationDialog("창고 위치 관리", this.selectedData);
		dialog.setOnComplete(()->{
			refresh();
		});
		dialog.setVisible(true);
	}

	@Override
	protected void onDelete() {
		if(this.selectedData == null) {
			return;
		}
		int result = JOptionPane.showConfirmDialog(null, "\"" + this.selectedData.getLocation_name() + "\"" + " 를 삭제하시겠습니까?");
		if (result != JOptionPane.YES_OPTION) {
			return;
		}
		try {
			this.currentDAO.delete(this.selectedData.getLocation_id());
			JOptionPane.showMessageDialog(null, "삭제 완료 돠었습니다.");
		} catch (LocationDeleteException e) {
			JOptionPane.showMessageDialog(null, "삭제 실패 돠었습니다.");
		}
		refresh();
	}
}
