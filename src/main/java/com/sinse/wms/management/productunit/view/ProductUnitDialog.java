package com.sinse.wms.management.productunit.view;

import com.sinse.wms.common.exception.CompanyInsertException;
import com.sinse.wms.common.exception.CompanyUpdateException;
import com.sinse.wms.common.exception.ProductUnitInsertException;
import com.sinse.wms.common.exception.ProductUnitUpdateException;
import com.sinse.wms.management.BaseEtcDialog;
import com.sinse.wms.product.model.ProductUnit;
import com.sinse.wms.product.repository.ProductUnitDAO;

import javax.swing.*;

public class ProductUnitDialog extends BaseEtcDialog<ProductUnit> {
	private ProductUnitDAO productUnitDAO = new ProductUnitDAO();

	public ProductUnitDialog(String titleName) {
		super(titleName);
	}

	public ProductUnitDialog(String titleName, ProductUnit data) {
		super(titleName, data);
	}

	@Override
	protected String getDefaultTextFieldData() {
		return this.data.getUnit_name();
	}

	@Override
	protected void insert() {
		if (isInvalid()) {
			JOptionPane.showMessageDialog(null, "값을 입력해 주세요.");
			return;
		}
		try {
			ProductUnit insertProductUnit = new ProductUnit();
			insertProductUnit.setUnit_name(this.tf_input.getText().trim());
			productUnitDAO.insert(insertProductUnit);
			JOptionPane.showMessageDialog(null, "등록 완료 되었습니다.");
			disposeWithComplete();
		} catch (ProductUnitInsertException e) {
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
			ProductUnit updateProductUnit = new ProductUnit();
			updateProductUnit.setUnit_id(this.data.getUnit_id());
			updateProductUnit.setUnit_name(this.tf_input.getText().trim());
			productUnitDAO.update(updateProductUnit);
			JOptionPane.showMessageDialog(null, "수정 완료 되었습니다.");
			disposeWithComplete();
		} catch (ProductUnitUpdateException e) {
			JOptionPane.showMessageDialog(null, "수정 실패 되었습니다.");
		}
	}

	private boolean isInvalid() {
		return this.tf_input.getText().trim().isEmpty();
	}

}
