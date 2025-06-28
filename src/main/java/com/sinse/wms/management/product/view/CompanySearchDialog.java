package com.sinse.wms.management.product.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sinse.wms.common.exception.CompanySelectException;
import com.sinse.wms.common.view.button.OutLineButton;
import com.sinse.wms.management.company.view.CompanyTableModel;
import com.sinse.wms.product.model.Company;
import com.sinse.wms.product.repository.CompanyDAO;

public class CompanySearchDialog extends JDialog {
	private JPanel p_searchbarWrapper;
	private JTextField tf_inputCompany;
	private OutLineButton obt_search;
	private OutLineButton obt_select;
	private JScrollPane sp_companyTableScroll;
	private JTable tb_company;
	private CompanyTableModel tableModel;
	private CompanyDAO companyDAO = new CompanyDAO();
	private Function<Company, Void> selectListener;
	private List<Company> companies;

	public CompanySearchDialog() {
		setSize(200, 700);
		setLayout(new BorderLayout());
		this.p_searchbarWrapper = new JPanel();
		this.p_searchbarWrapper.setLayout(new BoxLayout(this.p_searchbarWrapper, BoxLayout.X_AXIS));

		this.tf_inputCompany = new JTextField();
		this.obt_search = new OutLineButton("검색");
		this.obt_search.addActionListener(e -> {
			searchCompany();
		});
		this.obt_select = new OutLineButton("선택완료");
		this.obt_select.addActionListener(e -> {
			selectedCompany();
		});
		this.tableModel = new CompanyTableModel();
		this.tb_company = new JTable(this.tableModel);
		this.sp_companyTableScroll = new JScrollPane(this.tb_company);

		this.p_searchbarWrapper.add(this.tf_inputCompany);
		this.p_searchbarWrapper.add(this.obt_search);

		add(this.p_searchbarWrapper, BorderLayout.NORTH);
		add(this.sp_companyTableScroll, BorderLayout.CENTER);
		add(this.obt_select, BorderLayout.SOUTH);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				companies = companyDAO.selectAll();
				updateTable();
			}
		});
		
		setLocationRelativeTo(null);
	}

	public void setSelectedCompany(Function<Company, Void> listener) {
		this.selectListener = listener;
	}

	private void searchCompany() {
		String input = this.tf_inputCompany.getText();
		try {
			this.companies = this.companyDAO.selectByNameLike(input);
			updateTable();
		} catch (CompanySelectException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "검색중 오류가 발생하였습니다.");
		}
	}

	private void selectedCompany() {
		int selectedIndex = this.tb_company.getSelectedRow();
		if (selectedIndex != -1) {
			int rowIndex = this.tb_company.convertRowIndexToModel(selectedIndex);
			this.selectListener.apply(this.companies.get(rowIndex));
		} else {
			JOptionPane.showMessageDialog(null, "거래처를 선택해 주세요");
		}
	}

	private void updateTable() {
		tableModel.setCompanies(companies);
		tb_company.updateUI();
	}
}
