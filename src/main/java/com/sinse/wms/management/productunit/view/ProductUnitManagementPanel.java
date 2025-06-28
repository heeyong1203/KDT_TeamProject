package com.sinse.wms.management.productunit.view;

import com.sinse.wms.common.exception.ProductUnitSelectException;
import com.sinse.wms.management.BaseEtcTablePanel;
import com.sinse.wms.product.model.ProductUnit;
import com.sinse.wms.product.repository.ProductUnitDAO;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.List;

public class ProductUnitManagementPanel extends BaseEtcTablePanel<ProductUnit,ProductUnitDAO> {

	public ProductUnitManagementPanel() {
		super(new ProductUnitDAO());
	}

	@Override
	protected void init() {
		setModifyButtonEnable(false);
		setDeleteButtonEnable(false);
	}

	@Override
	protected List<ProductUnit> getData() {
		return this.currentDAO.selectAll();
	}

	@Override
	protected TableModel getTableModel() {
		ProductUnitTableModel model = new ProductUnitTableModel();
		model.setProductUnits(this.data);
		return model;
	}

	@Override
	protected String getTitle() {
		return "단위 관리";
	}

	@Override
	protected void onSearch() {
		String input = this.getInput();
		this.data = this.currentDAO.selectByNameLike(input);
		setModel();
	}

	@Override
	protected void onAdd() {
		ProductUnitDialog dialog = new ProductUnitDialog("단위 관리");
		dialog.setOnComplete(()->{
			refresh();
		});
		dialog.setVisible(true);
	}

	@Override
	protected void onModify() {
		ProductUnitDialog dialog = new ProductUnitDialog("단위 관리", this.selectedData);
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
		int result = JOptionPane.showConfirmDialog(null, "\""+this.selectedData.getUnit_name()+"\"" + " 를 삭제하시겠습니까?");
		if (result != JOptionPane.YES_OPTION) {
			return;
		}
		try {
			this.currentDAO.delete(this.selectedData.getUnit_id());
			JOptionPane.showMessageDialog(null, "삭제 완료 돠었습니다.");
		} catch (ProductUnitSelectException e) {
			JOptionPane.showMessageDialog(null, "삭제 실패 돠었습니다.");
		}
		refresh();
	}
}
