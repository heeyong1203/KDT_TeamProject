package com.sinse.wms.management.company.view;

import javax.swing.JOptionPane;

import com.sinse.wms.common.exception.CompanyInsertException;
import com.sinse.wms.common.exception.CompanyUpdateException;
import com.sinse.wms.management.BaseEtcDialog;
import com.sinse.wms.product.model.Company;
import com.sinse.wms.product.repository.CompanyDAO;

public class CompanyDialog extends BaseEtcDialog<Company> {
	private CompanyDAO companyDAO = new CompanyDAO();

	public CompanyDialog(String titleName) {
		super(titleName);
	}

	public CompanyDialog(String titleName, Company data) {
		super(titleName, data);
	}

	@Override
	protected String getDefaultTextFieldData() {
		return this.data.getCompany_name();
	}

	@Override
	protected void insert() {
		if (isInvalid()) {
			JOptionPane.showMessageDialog(null, "값을 입력해 주세요.");
			return;
		}
		try {
			Company insertCompany = new Company();
			insertCompany.setCompany_name(this.tf_input.getText().trim());
			companyDAO.insert(insertCompany);
			JOptionPane.showMessageDialog(null, "등록 완료 되었습니다.");
			disposeWithComplete();
		} catch (CompanyInsertException e) {
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
			Company updateCompany = new Company();
			updateCompany.setCompany_id(this.data.getCompany_id());
			updateCompany.setCompany_name(this.tf_input.getText().trim());
			companyDAO.update(updateCompany);
			JOptionPane.showMessageDialog(null, "수정 완료 되었습니다.");
			disposeWithComplete();
		} catch (CompanyUpdateException e) {
			JOptionPane.showMessageDialog(null, "수정 실패 되었습니다.");
		}
	}

	private boolean isInvalid() {
		return this.tf_input.getText().trim().isEmpty();
	}

}
