package com.sinse.wms.management.category.view;

import com.sinse.wms.common.exception.CategoryInsertException;
import com.sinse.wms.common.exception.CategoryUpdateException;
import com.sinse.wms.common.exception.CompanyInsertException;
import com.sinse.wms.common.exception.CompanyUpdateException;
import com.sinse.wms.management.BaseEtcDialog;
import com.sinse.wms.product.model.Category;
import com.sinse.wms.product.repository.CategoryDAO;

import javax.swing.*;

public class CategoryDialog extends BaseEtcDialog<Category> {
	private CategoryDAO categoryDAO = new CategoryDAO();

	public CategoryDialog(String titleName) {
		super(titleName);
	}

	public CategoryDialog(String titleName, Category data) {
		super(titleName, data);
	}

	@Override
	protected String getDefaultTextFieldData() {
		return this.data.getCategory_name();
	}

	@Override
	protected void insert() {
		if (isInvalid()) {
			JOptionPane.showMessageDialog(null, "값을 입력해 주세요.");
			return;
		}
		try {
			Category insertCategory = new Category();
			insertCategory.setCategory_name(this.tf_input.getText().trim());
			categoryDAO.insert(insertCategory);
			JOptionPane.showMessageDialog(null, "등록 완료 되었습니다.");
			disposeWithComplete();
		} catch (CategoryInsertException e) {
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
			Category updateCategory = new Category();
			updateCategory.setCategory_id(this.data.getCategory_id());
			updateCategory.setCategory_name(this.tf_input.getText().trim());
			categoryDAO.update(updateCategory);
			JOptionPane.showMessageDialog(null, "수정 완료 되었습니다.");
			disposeWithComplete();
		} catch (CategoryUpdateException e) {
			JOptionPane.showMessageDialog(null, "수정 실패 되었습니다.");
		}
	}

	private boolean isInvalid() {
		return this.tf_input.getText().trim().isEmpty();
	}

}
