package com.sinse.wms.management.product.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.view.button.OutLineButton;
import com.sinse.wms.membermanagement.view.MemberManagementTableModel;
import com.sinse.wms.product.model.Category;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.repository.CategoryDAO;

public class ProductTablePanel extends JPanel {
	private JPanel p_buttonWrapper;
	private JPanel p_searchWrapper;
	private OutLineButton obt_productAdd;
	private OutLineButton obt_productModify;
	private OutLineButton obt_productDelete;
	private OutLineButton obt_productSearch;
	private JTextField tf_productSearch;
	private JTable tb_product;
	private JScrollPane sp_product_scroll;
	private JComboBox<String> cb_tableColumnField;

	private Function<String, Void> tableColumnFieldComboBoxListener = null;
	private Function<Product, Void> productListener = null;
	private Function<String, Void> searchListener = null;

	private ProductManagementTableModel tableModel;

	public ProductTablePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		this.tf_productSearch = new JTextField(20) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (getText().isEmpty()) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setBackground(Color.LIGHT_GRAY);
					g2.drawString("상품이름, 코드 검색", 5, 17);
					g2.dispose();
				}
			}
		};
		this.tf_productSearch.setMaximumSize(new Dimension(300, 30));
		this.obt_productSearch = new OutLineButton("검색", 10, 1, Color.BLACK, Color.WHITE);
		this.obt_productSearch.setMaximumSize(new Dimension(100, 30));
		this.obt_productSearch.addActionListener(e -> {
			onClickSearch();
		});
		this.cb_tableColumnField = new JComboBox<String>();
		this.cb_tableColumnField.setMaximumSize(new Dimension(100, 30));
		this.cb_tableColumnField.setBackground(this.getBackground());
		this.cb_tableColumnField.addActionListener(e -> {
			onClickCategory();
		});

		this.p_searchWrapper = new JPanel();
		this.p_searchWrapper.setBackground(this.getBackground());
		this.p_searchWrapper.setMaximumSize(new Dimension(900, 30));
		this.p_searchWrapper.setLayout(new BoxLayout(this.p_searchWrapper, BoxLayout.X_AXIS));
		this.p_searchWrapper.add(this.cb_tableColumnField);
		this.p_searchWrapper.add(Box.createHorizontalStrut(10));
		this.p_searchWrapper.add(this.tf_productSearch);
		this.p_searchWrapper.add(Box.createHorizontalStrut(10));
		this.p_searchWrapper.add(this.obt_productSearch);

		this.tableModel = new ProductManagementTableModel();
		this.tb_product = new JTable(this.tableModel);
		this.tb_product.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tb_product.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClickProductTable();
			}
		});
		this.sp_product_scroll = new JScrollPane(this.tb_product);

		this.p_buttonWrapper = new JPanel();
		this.p_buttonWrapper.setLayout(new BoxLayout(this.p_buttonWrapper, BoxLayout.X_AXIS));
		this.p_buttonWrapper.setBackground(getBackground());
		this.obt_productAdd = new OutLineButton("등록", 10, 1, Color.BLACK, Color.WHITE);
		this.obt_productModify = new OutLineButton("수정", 10, 1, Color.BLACK, Color.WHITE);
		this.obt_productModify.setEnabled(false);
		this.obt_productDelete = new OutLineButton("삭제", 10, 1, Color.BLACK, Color.WHITE);
		this.obt_productDelete.setEnabled(false);
		this.p_buttonWrapper.add(Box.createHorizontalGlue());
		this.p_buttonWrapper.add(obt_productAdd);
		this.p_buttonWrapper.add(Box.createHorizontalStrut(10));
		this.p_buttonWrapper.add(obt_productModify);
		this.p_buttonWrapper.add(Box.createHorizontalStrut(10));
		this.p_buttonWrapper.add(obt_productDelete);
		add(this.p_searchWrapper);
		add(Box.createVerticalStrut(10));
		add(this.sp_product_scroll);
		add(Box.createVerticalStrut(10));
		add(this.p_buttonWrapper);
	}

	public void setTableColumnFieldComboBox(List<String> categories) {
		Vector<String> categoriesName = categories.stream()
				.collect(Collectors.toCollection(() -> new Vector<String>()));
		this.cb_tableColumnField.setModel(new DefaultComboBoxModel<>(categoriesName));
		this.cb_tableColumnField.updateUI();
	}

	public void setProducts(List<Product> products) {
		this.tableModel.setProducts(products);
		this.tb_product.updateUI();
	}

	public void setOnSearchProduct(Function<String, Void> listener) {
		this.searchListener = listener;
	}

	public void setOnSelectedColumnFieldComboBox(Function<String, Void> listener) {
		this.tableColumnFieldComboBoxListener = listener;
	}

	public void setOnSelectedProduct(Function<Product, Void> listener) {
		this.productListener = listener;
	}

	public void setOnClickAdd(Runnable runnable) {
		this.obt_productAdd.addActionListener(e -> {
			runnable.run();
		});
	}

	public void setOnClickModify(Runnable runnable) {
		this.obt_productModify.addActionListener(e -> {
			runnable.run();
		});
	}

	public void setOnClickDelete(Runnable runnable) {
		this.obt_productDelete.addActionListener(e -> {
			runnable.run();
		});
	}

	private void onClickSearch() {
		if (this.searchListener == null) {
			return;
		}
		this.searchListener.apply(tf_productSearch.getText().trim());
	}

	private void onClickCategory() {
		if (this.tableColumnFieldComboBoxListener == null) {
			return;
		}

		int selectedIndex = this.cb_tableColumnField.getSelectedIndex();
		String selectedName = this.cb_tableColumnField.getItemAt(selectedIndex);
		this.tableColumnFieldComboBoxListener.apply(selectedName);
	}

	private void onClickProductTable() {
		if (this.productListener == null) {
			return;
		}

		int index = this.tb_product.getSelectedRow();
		if (index == -1) {
			this.productListener.apply(null);
			this.obt_productModify.setEnabled(false);
			this.obt_productDelete.setEnabled(false);
			return;
		}

		int modelRowIndex = this.tb_product.convertRowIndexToModel(index);
		if (this.tb_product.getModel() instanceof ProductManagementTableModel) {
			Product selectdProduct = ((ProductManagementTableModel) this.tb_product.getModel())
					.getProductAt(modelRowIndex);
			this.productListener.apply(selectdProduct);
			if (selectdProduct != null) {
				this.obt_productModify.setEnabled(true);
				this.obt_productDelete.setEnabled(true);
			}
		}
	}
}
