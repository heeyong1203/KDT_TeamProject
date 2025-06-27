package com.sinse.wms.management.product.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.sinse.wms.common.exception.ImageSelectException;
import com.sinse.wms.common.view.ImageView;
import com.sinse.wms.product.model.Image;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.repository.ImageDAO;

public class ProductInfoPanel extends JPanel {
	private ImageView iv_productImage;
	private JPanel p_infoWrapper;
	private JLabel la_productNameTitle;
	private JLabel la_productCategoryTitle;
	private JLabel la_productPriceTitle;
	private JLabel la_productCodeTitle;
	private JLabel la_productStockTitle;
	private JLabel la_productLocationTitle;
	private JLabel la_productCompanyTitle;
	private JLabel la_productUnitTitle;
	private JLabel la_productDescriptionTitle;

	private JLabel la_productName;
	private JLabel la_productCategory;
	private JLabel la_productPrice;
	private JLabel la_productCode;
	private JLabel la_productStock;
	private JLabel la_productLocation;
	private JLabel la_productCompany;
	private JLabel la_productUnit;

	private JTextArea ta_productDescription;
	private JScrollPane sp_productDescriptionScroll;

	private Dimension titleSize = new Dimension(100, 25);
	private Dimension valueSize = new Dimension(100, 25);
	private GridBagConstraints gbc = new GridBagConstraints();

	private boolean modifyMode = false;

	public ProductInfoPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(20, 100, 0, 100));
		this.iv_productImage = new ImageView(200, 200);
		this.iv_productImage.setSource("image-default-icon.png");

		this.p_infoWrapper = new JPanel();
		this.p_infoWrapper.setLayout(new GridBagLayout());
		this.p_infoWrapper.setBackground(Color.WHITE);
		this.p_infoWrapper.setPreferredSize(new Dimension(700, 300));

		this.la_productNameTitle = new JLabel("상품이름 : ", SwingConstants.RIGHT);
		setTitleSize(this.la_productNameTitle);
		this.la_productCategoryTitle = new JLabel("카테고리 : ", SwingConstants.RIGHT);
		setTitleSize(this.la_productCategoryTitle);
		this.la_productPriceTitle = new JLabel("가격 : ", SwingConstants.RIGHT);
		setTitleSize(this.la_productPriceTitle);
		this.la_productCodeTitle = new JLabel("상품코드 : ", SwingConstants.RIGHT);
		setTitleSize(this.la_productCodeTitle);
		this.la_productStockTitle = new JLabel("안전재고 수량 : ", SwingConstants.RIGHT);
		setTitleSize(this.la_productStockTitle);
		this.la_productLocationTitle = new JLabel("창고 구역 : ", SwingConstants.RIGHT);
		setTitleSize(this.la_productLocationTitle);
		this.la_productCompanyTitle = new JLabel("거래처 : ", SwingConstants.RIGHT);
		setTitleSize(this.la_productCompanyTitle);
		this.la_productUnitTitle = new JLabel("단위 : ", SwingConstants.RIGHT);
		setTitleSize(this.la_productUnitTitle);
		this.la_productDescriptionTitle = new JLabel("상품 설명");
		setTitleSize(this.la_productDescriptionTitle);

		this.la_productName = new JLabel("이름");
		setValueSize(this.la_productName);
		this.la_productCategory = new JLabel("카테고리");
		setValueSize(this.la_productCategory);
		this.la_productPrice = new JLabel("가격");
		setValueSize(this.la_productPrice);
		this.la_productCode = new JLabel("상품코드");
		setValueSize(this.la_productCode);
		this.la_productStock = new JLabel("안전재고수량");
		setValueSize(this.la_productStock);
		this.la_productLocation = new JLabel("창고 위치");
		setValueSize(this.la_productLocation);
		this.la_productCompany = new JLabel("거래처");
		setValueSize(this.la_productCompany);
		this.la_productUnit = new JLabel("단위");
		setValueSize(this.la_productUnit);
		this.ta_productDescription = new JTextArea();
		this.ta_productDescription.setLineWrap(true);
		this.ta_productDescription.setWrapStyleWord(true);
		this.ta_productDescription.setEditable(false);
		this.sp_productDescriptionScroll = new JScrollPane(this.ta_productDescription);
		this.sp_productDescriptionScroll.setBorder(null);
		this.sp_productDescriptionScroll.setViewportBorder(null);

		this.gbc.weightx = 0.3;
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.gbc.anchor = GridBagConstraints.EAST;
		this.p_infoWrapper.add(this.la_productNameTitle, this.gbc);
		this.gbc.gridy = 1;
		this.p_infoWrapper.add(this.la_productCategoryTitle, this.gbc);
		this.gbc.gridy = 2;
		this.p_infoWrapper.add(this.la_productPriceTitle, this.gbc);
		this.gbc.gridy = 3;
		this.p_infoWrapper.add(this.la_productCodeTitle, this.gbc);
		this.gbc.gridy = 4;
		this.p_infoWrapper.add(this.la_productStockTitle, this.gbc);
		this.gbc.gridy = 5;
		this.p_infoWrapper.add(this.la_productLocationTitle, this.gbc);
		this.gbc.gridy = 6;
		this.p_infoWrapper.add(this.la_productCompanyTitle, this.gbc);
		this.gbc.gridy = 7;
		this.p_infoWrapper.add(this.la_productUnitTitle, this.gbc);
		this.gbc.gridy = 9;
		this.p_infoWrapper.add(this.la_productDescriptionTitle, this.gbc);

		this.gbc.insets = new Insets(0, 10, 0, 20);
		this.gbc.weightx = 0.7;
		this.gbc.gridx = 1;
		this.gbc.gridy = 0;
		this.gbc.fill = GridBagConstraints.HORIZONTAL;
		this.p_infoWrapper.add(this.la_productName, this.gbc);
		this.gbc.gridy = 1;
		this.p_infoWrapper.add(this.la_productCategory, this.gbc);
		this.gbc.gridy = 2;
		this.p_infoWrapper.add(this.la_productPrice, this.gbc);
		this.gbc.gridy = 3;
		this.p_infoWrapper.add(this.la_productCode, this.gbc);
		this.gbc.gridy = 4;
		this.p_infoWrapper.add(this.la_productStock, this.gbc);
		this.gbc.gridy = 5;
		this.p_infoWrapper.add(this.la_productLocation, this.gbc);
		this.gbc.gridy = 6;
		this.p_infoWrapper.add(this.la_productCompany, this.gbc);
		this.gbc.gridy = 7;
		this.p_infoWrapper.add(this.la_productUnit, this.gbc);

		this.gbc.gridx = 0;
		this.gbc.gridy = 10;
		this.gbc.weighty = 1;
		this.gbc.fill = GridBagConstraints.BOTH;
		this.gbc.gridheight = 3;
		this.gbc.gridwidth = 2;
		this.gbc.insets = new Insets(0, 70, 0, 20);
		this.p_infoWrapper.add(this.sp_productDescriptionScroll, this.gbc);

		add(this.iv_productImage);
		add(this.p_infoWrapper);
		init();
	}

	public void init() {
		this.la_productName.setText(null);
		this.la_productCategory.setText(null);
		this.la_productPrice.setText(null);
		this.la_productCode.setText(null);
		this.la_productStock.setText(null);
		this.la_productLocation.setText(null);
		this.la_productCompany.setText(null);
		this.la_productUnit.setText(null);
		this.ta_productDescription.setText(null);
		this.iv_productImage.setSource("image-default-icon.png");
	}

	public void setProduct(Product product) {
		if (product != null) {
			this.la_productName.setText(product.getProduct_name());
			this.la_productCategory.setText(product.getCategory().getCategory_name());
			this.la_productPrice.setText(String.valueOf(product.getProduct_price()));
			this.la_productCode.setText(product.getProduct_code());
			this.la_productStock.setText(String.valueOf(product.getProduct_stock()));
			this.la_productLocation.setText(product.getLocation().getLocation_name());
			this.la_productCompany.setText(product.getCompany().getCompany_name());
			this.la_productUnit.setText(product.getUnit().getUnit_name());
			this.ta_productDescription.setText(product.getProduct_description());
		} else {
			this.la_productName.setText(null);
			this.la_productCategory.setText(null);
			this.la_productPrice.setText(null);
			this.la_productCode.setText(null);
			this.la_productStock.setText(null);
			this.la_productLocation.setText(null);
			this.la_productCompany.setText(null);
			this.la_productUnit.setText(null);
			this.ta_productDescription.setText(null);
		}
	}

	public void setTitleSize(Component component) {
		component.setMinimumSize(titleSize);
		component.setPreferredSize(titleSize);
		component.setMaximumSize(titleSize);
	}

	public void setValueSize(Component component) {
		component.setMinimumSize(valueSize);
		component.setPreferredSize(valueSize);
		component.setMaximumSize(valueSize);
	}

	public void setProductImage(Image image) {
		if (image == null) {
			this.iv_productImage.setSource("image-missing-icon.png");
			return;
		}

		try {
			this.iv_productImage.setImageFromBase64(image.getImage_data());
		} catch (NullPointerException | ImageSelectException e) {
			e.printStackTrace();
			this.iv_productImage.setSource("image-missing-icon.png");
		}
	}
}
