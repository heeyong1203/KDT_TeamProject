package com.sinse.wms.management.category.view;

import com.sinse.wms.common.exception.CategoryDeleteException;
import com.sinse.wms.management.BaseEtcTablePanel;
import com.sinse.wms.product.model.Category;
import com.sinse.wms.product.repository.CategoryDAO;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.List;

public class CategoryManagementPanel extends BaseEtcTablePanel<Category, CategoryDAO> {

	public CategoryManagementPanel() {
		super(new CategoryDAO());
	}

	@Override
	protected void init() {
		setModifyButtonEnable(false);
		setDeleteButtonEnable(false);
	}

	@Override
	protected List<Category> getData() {
		return this.currentDAO.selectAll();
	}

	@Override
	protected TableModel getTableModel() {
		CategoryManagementTableModel model = new CategoryManagementTableModel();
		model.setCategories(this.data);
		return model;
	}

	@Override
	protected String getTitle() {
		return "카테고리 관리";
	}

	@Override
	protected void onSearch() {
		String input = this.getInput();
		this.data = this.currentDAO.selectByNameLike(input);
		setModel();
	}

	@Override
	protected void onAdd() {
		CategoryDialog dialog = new CategoryDialog("카테고리 관리");
		dialog.setOnComplete(()->{
			refresh();
		});
		dialog.setVisible(true);
	}

	@Override
	protected void onModify() {
		CategoryDialog dialog = new CategoryDialog("카테고리 관리", this.selectedData);
		dialog.setOnComplete(()->{
			refresh();
		});
		dialog.setVisible(true);
	}

	@Override
	protected void onDelete() {
		if (this.selectedData == null) {
			return;
		}
		int result = JOptionPane.showConfirmDialog(null, "\"" + this.selectedData.getCategory_name() + "\"" + " 를 삭제하시겠습니까?");
		if (result != JOptionPane.YES_OPTION) {
			return;
		}
		try {
			this.currentDAO.delete(this.selectedData.getCategory_id());
			JOptionPane.showMessageDialog(null, "삭제 완료 돠었습니다.");
		} catch (CategoryDeleteException e) {
			JOptionPane.showMessageDialog(null, "삭제 실패 돠었습니다.");
		}
		refresh();
	}
}
