package com.sinse.wms.management;

import java.awt.GridLayout;

import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.management.category.view.CategoryManagementPanel;
import com.sinse.wms.management.company.view.CompanyManagementPanel;
import com.sinse.wms.management.location.view.LocationManagementPanel;
import com.sinse.wms.management.productunit.view.ProductUnitManagementPanel;

public class EtcManagementPage extends BaseContentPage {
	private CategoryManagementPanel categoryManagementPanel;
	private CompanyManagementPanel companyManagementPanel;
	private ProductUnitManagementPanel productUnitManagementPanel;
	private LocationManagementPanel locationManagementPanel;

	public EtcManagementPage() {
		setLayout(new GridLayout(2, 2, 10, 10));

		this.categoryManagementPanel = new CategoryManagementPanel();
		this.companyManagementPanel = new CompanyManagementPanel();
		this.productUnitManagementPanel = new ProductUnitManagementPanel();
		this.locationManagementPanel = new LocationManagementPanel();

		this.categoryManagementPanel.setModel();
		this.companyManagementPanel.setModel();
		this.productUnitManagementPanel.setModel();
		this.locationManagementPanel.setModel();

		add(this.categoryManagementPanel);
		add(this.companyManagementPanel);
		add(this.productUnitManagementPanel);
		add(this.locationManagementPanel);
	}
}
