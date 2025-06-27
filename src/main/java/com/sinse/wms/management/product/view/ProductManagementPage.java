package com.sinse.wms.management.product.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.exception.ImageSelectException;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.product.model.Category;
import com.sinse.wms.product.model.Image;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.repository.CategoryDAO;
import com.sinse.wms.product.repository.ImageDAO;
import com.sinse.wms.product.repository.ProductDAO;

public class ProductManagementPage extends BaseContentPage {
	private ProductInfoPanel infoPanel;
	private ProductTablePanel tablePanel;

	private CategoryDAO categoryDAO = new CategoryDAO();
	private ProductDAO productDAO = new ProductDAO();
	private ImageDAO imageDAO = new ImageDAO();

	private String selectedTableColumnField = "상품이름";
	private Product selectedProduct = null;
	private Map<String, String> columns = new LinkedHashMap<>();
	private String previousSearchInput = "";

	public ProductManagementPage() {
		columns.put("상품이름", "p.product_name");
		columns.put("카테고리", "ca.category_name");
		columns.put("가격", "p.product_price");
		columns.put("상품코드", "p.product_code");
		columns.put("안전재고수량", "p.product_stock");
		columns.put("창고구역", "l.location_name");
		columns.put("거래처", "c.company_name");

		setLayout(new BorderLayout());
		this.infoPanel = new ProductInfoPanel();
		this.infoPanel.setPreferredSize(new Dimension(Config.CONTENT_BODY_WIDTH, 400));
		this.tablePanel = new ProductTablePanel();
		this.tablePanel.setOnSearchProduct(new Function<String, Void>() {
			@Override
			public Void apply(String input) {
				previousSearchInput = input;
				searchProduct();
				return null;
			}
		});
		this.tablePanel.setOnSelectedColumnFieldComboBox(new Function<String, Void>() {

			@Override
			public Void apply(String tableColumnField) {
				selectedTableColumnField = tableColumnField;
				return null;
			}
		});
		this.tablePanel.setOnSelectedProduct(new Function<Product, Void>() {

			@Override
			public Void apply(Product product) {
				selectedProduct = product;
				infoPanel.setProduct(product);
				if (product.getImage_id() == 0) {
					infoPanel.setProductImage(null);
					return null;
				}
				try {
					Image image = imageDAO.select(product.getImage_id());
					infoPanel.setProductImage(image);
				} catch (NullPointerException | ImageSelectException e) {
					infoPanel.setProductImage(null);
					e.printStackTrace();
				}
				return null;
			}
		});
		this.tablePanel.setOnClickAdd(() -> {
			ProductDialog dialog = new ProductDialog(JOptionPane.getFrameForComponent(this));
			dialog.setOnComplete(() -> {
				searchProduct();
			});
			dialog.setVisible(true);
		});
		this.tablePanel.setOnClickModify(() -> {
			ProductDialog dialog = new ProductDialog(JOptionPane.getFrameForComponent(this), this.selectedProduct);
			dialog.setOnComplete(() -> {
				searchProduct();
			});
			dialog.setVisible(true);
		});
		this.tablePanel.setOnClickDelete(() -> {
			int result = JOptionPane.showConfirmDialog(this,
					"\"" + this.selectedProduct.getProduct_name() + "\" 을 정말 삭제 하시겠습니까?");
			if (result == JOptionPane.YES_OPTION) {
				deleteSelectedProduct();
				String column = columns.getOrDefault(selectedTableColumnField, "p.product_name");
				List<Product> products = productDAO.selectByTableColumn(column, previousSearchInput);
				tablePanel.setProducts(products);
			}
		});
		add(this.infoPanel, BorderLayout.NORTH);
		add(this.tablePanel, BorderLayout.CENTER);
		initTableColumnFieldComboBox();
		initProduct();
	}

	public void refresh() {
		this.infoPanel.init();
		initTableColumnFieldComboBox();
		initProduct();
	}

	private void initTableColumnFieldComboBox() {
		this.tablePanel.setTableColumnFieldComboBox(new ArrayList<String>(columns.keySet()));
	}

	private void initProduct() {
		List<Product> products = this.productDAO.selectAll();
		this.tablePanel.setProducts(products);
	}

	private void searchProduct() {
		String column = columns.getOrDefault(selectedTableColumnField, "p.product_name");
		List<Product> products = productDAO.selectByTableColumn(column, previousSearchInput);
		tablePanel.setProducts(products);
	}

	private void deleteSelectedProduct() {
		try {
			DBManager.getInstance().getConnetion().setAutoCommit(false);
			this.productDAO.delete(this.selectedProduct.getProduct_id());
			if (this.selectedProduct.getImage_id() != 0) {
				this.imageDAO.delete(this.selectedProduct.getImage_id());
			}
			JOptionPane.showMessageDialog(null, "상품 삭제가 되었습니다");
			this.infoPanel.init();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				DBManager.getInstance().getConnetion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "상품 삭제에 실패 했습니다");
		} finally {
			try {
				DBManager.getInstance().getConnetion().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
