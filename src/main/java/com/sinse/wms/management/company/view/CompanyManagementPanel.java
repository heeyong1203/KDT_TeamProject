package com.sinse.wms.management.company.view;

import com.sinse.wms.common.exception.CompanyDeleteException;
import com.sinse.wms.management.BaseEtcTablePanel;
import com.sinse.wms.product.model.Company;
import com.sinse.wms.product.repository.CompanyDAO;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.List;

public class CompanyManagementPanel extends BaseEtcTablePanel<Company,CompanyDAO> {

	public CompanyManagementPanel() {
		super(new CompanyDAO());
	}

	@Override
	protected void init() {
		setModifyButtonEnable(false);
		setDeleteButtonEnable(false);
	}

	@Override
	protected List<Company> getData() {
		return this.currentDAO.selectAll();
	}

	@Override
	protected TableModel getTableModel() {
        CompanyTableModel model = new CompanyTableModel();
        model.setCompanies(this.data);
        return model;
	}

	@Override
	protected String getTitle() {
		return "거래처 관리";
	}

	@Override
	protected void onSearch() {
		String input = this.getInput();
		this.data = this.currentDAO.selectByNameLike(input);
		setModel();
	}

	@Override
	protected void onAdd() {
		CompanyDialog dialog = new CompanyDialog("거래처 관리");
		dialog.setOnComplete(()->{
			refresh();
		});
		dialog.setVisible(true);
	}

	@Override
	protected void onModify() {
		CompanyDialog dialog = new CompanyDialog("거래처 관리", this.selectedData);
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
		int result = JOptionPane.showConfirmDialog(null, "\"" + this.selectedData.getCompany_name() + "\"" + " 를 삭제하시겠습니까?");
		if (result != JOptionPane.YES_OPTION) {
			return;
		}
		try {
			this.currentDAO.delete(this.selectedData.getCompany_id());
			JOptionPane.showMessageDialog(null, "삭제 완료 돠었습니다.");
		} catch (CompanyDeleteException e) {
			JOptionPane.showMessageDialog(null, "삭제 실패 돠었습니다.");
		}
		refresh();
	}
}
