package com.sinse.wms.management.product.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.common.util.ImageUtil;
import com.sinse.wms.common.view.ImageView;
import com.sinse.wms.common.view.ImageView.ImageViewClickListener;
import com.sinse.wms.common.view.button.OutLineButton;
import com.sinse.wms.product.model.Category;
import com.sinse.wms.product.model.Company;
import com.sinse.wms.product.model.Image;
import com.sinse.wms.product.model.Location;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.model.ProductUnit;
import com.sinse.wms.product.repository.CategoryDAO;
import com.sinse.wms.product.repository.ImageDAO;
import com.sinse.wms.product.repository.LocationDAO;
import com.sinse.wms.product.repository.ProductDAO;
import com.sinse.wms.product.repository.ProductUnitDAO;

public class ProductDialog extends JDialog {
	private ImageView iv_productImage;
	private JLabel la_productNameTitle;
	private JLabel la_productCategoryTitle;
	private JLabel la_productPriceTitle;
	private JLabel la_productCodeTitle;
	private JLabel la_productStockTitle;
	private JLabel la_productLocationTitle;
	private JLabel la_productCompanyTitle;
	private JLabel la_productUnitTitle;
	private JLabel la_productDescriptionTitle;

	private JTextField tf_productName;
	private JComboBox<String> cb_productCategory;
	private JTextField tf_productPrice;
	private JTextField tf_productCode;
	private JTextField tf_productStock;
	private JComboBox<String> cb_productLocation;
	private JLabel la_productCompany;
	private OutLineButton obt_searchCompany;
	private JComboBox<String> cb_productUnit;
	private JTextArea ta_productDescription;
	private JScrollPane sp_productDescription;
	private OutLineButton obt_submit;
	private Dimension labelSize = new Dimension(100, 30);
	private Dimension inputSize = new Dimension(200, 30);

	private CategoryDAO categoryDAO = new CategoryDAO();
	private LocationDAO locationDAO = new LocationDAO();
	private ProductUnitDAO productUnitDAO = new ProductUnitDAO();
	private ImageDAO imageDAO = new ImageDAO();
	private ProductDAO productDAO = new ProductDAO();

	private List<Category> categories = null;
	private List<Location> locations = null;
	private List<ProductUnit> productUnits = null;

	private JFileChooser imageChooser = null;
	private Product product = null;
	private Company selectedCompany = null;
	private boolean changedImage = false;

	private Runnable completeRunnable = null;

	public ProductDialog(Frame parent) {
		this(parent, null);
	}

	public ProductDialog(Frame parent, Product product) {
		super(parent);
		this.product = product;
		if (this.product != null) {
			setTitle("상품 등록");
		} else {
			setTitle("상품 수정");
		}
		setSize(400, 850);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지파일을 선택해주세요.", "jpg", "jpeg", "png");
		this.imageChooser = new JFileChooser();
		this.imageChooser.setFileFilter(filter);
		this.imageChooser.setAcceptAllFileFilterUsed(false);
		this.iv_productImage = new ImageView(300, 300);
		this.iv_productImage.setSource("image-plus-icon.png");
		this.iv_productImage.setOnClickListener(new ImageViewClickListener() {

			@Override
			public void onClick() {
				int result = imageChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					changedImage = true;
					File selectedFile = imageChooser.getSelectedFile();
					iv_productImage.setSource(selectedFile);
				}
			}
		});
		this.iv_productImage.setTransferHandler(new TransferHandler() {

			@Override
			public boolean canImport(TransferSupport support) {
				return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
			}

			@Override
			public boolean importData(TransferSupport support) {
				if (!canImport(support)) {
					return false;
				}
				try {
					List<File> files = (List<File>) support.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : files) {
						String mimeType = Files.probeContentType(file.toPath());
						if (mimeType != null && mimeType.startsWith("image/")) {
							changedImage = true;
							iv_productImage.setSource(file);
						} else {
							JOptionPane.showMessageDialog(ProductDialog.this, "지원되지 않는 형식의 파일입니다.");
						}
					}
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		});
		this.la_productNameTitle = new JLabel("상품이름 : ", SwingConstants.RIGHT);
		this.la_productCategoryTitle = new JLabel("카테고리 : ", SwingConstants.RIGHT);
		this.la_productPriceTitle = new JLabel("가격 : ", SwingConstants.RIGHT);
		this.la_productCodeTitle = new JLabel("상품코드 : ", SwingConstants.RIGHT);
		this.la_productStockTitle = new JLabel("안전재고 수량 : ", SwingConstants.RIGHT);
		this.la_productLocationTitle = new JLabel("창고 구역 : ", SwingConstants.RIGHT);
		this.la_productCompanyTitle = new JLabel("거래처 : ", SwingConstants.RIGHT);
		this.la_productUnitTitle = new JLabel("단위 : ", SwingConstants.RIGHT);
		this.la_productDescriptionTitle = new JLabel("상품설명 : ", SwingConstants.RIGHT);

		this.tf_productName = new JTextField();
		this.cb_productCategory = new JComboBox<String>();
		this.tf_productPrice = new JTextField();
		this.tf_productCode = new JTextField();
		this.tf_productStock = new JTextField();
		this.cb_productLocation = new JComboBox<String>();
		this.la_productCompany = new JLabel();
		this.obt_searchCompany = new OutLineButton("검색");
		this.obt_searchCompany.addActionListener(e -> {
			CompanySearchDialog searchCompanyDialog = new CompanySearchDialog();
			searchCompanyDialog.setSelectedCompany(new Function<Company, Void>() {

				@Override
				public Void apply(Company company) {
					selectedCompany = company;
					la_productCompany.setText(company.getCompany_name());
					searchCompanyDialog.dispose();
					return null;
				}
			});
			searchCompanyDialog.setVisible(true);
		});
		this.cb_productUnit = new JComboBox<String>();
		this.ta_productDescription = new JTextArea();
		this.ta_productDescription.setLineWrap(true);
		this.ta_productDescription.setWrapStyleWord(true);
		this.sp_productDescription = new JScrollPane(this.ta_productDescription);
		this.sp_productDescription.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.obt_submit = new OutLineButton("등록");
		if (this.product != null) {
			this.obt_submit.setText("수정");
		}

		this.obt_submit.addActionListener(e -> {
			if (this.product == null) {
				insertProduct();
			} else {
				updateProduct();
			}
		});

		add(this.iv_productImage);
		add(Box.createVerticalStrut(30));
		addRow(this.la_productNameTitle, this.tf_productName);
		add(Box.createVerticalStrut(10));
		addRow(this.la_productCategoryTitle, this.cb_productCategory);
		add(Box.createVerticalStrut(10));
		addRow(this.la_productPriceTitle, this.tf_productPrice);
		add(Box.createVerticalStrut(10));
		addRow(this.la_productCodeTitle, this.tf_productCode);
		add(Box.createVerticalStrut(10));
		addRow(this.la_productStockTitle, this.tf_productStock);
		add(Box.createVerticalStrut(10));
		addRow(this.la_productLocationTitle, this.cb_productLocation);
		add(Box.createVerticalStrut(10));
		JPanel companyRow = new JPanel();
		companyRow.setLayout(new BoxLayout(companyRow, BoxLayout.X_AXIS));
		this.la_productCompanyTitle.setPreferredSize(this.labelSize);
		this.la_productCompanyTitle.setMaximumSize(this.labelSize);
		Dimension companyInputSize = new Dimension(140, 30);
		Dimension companyButtonSize = new Dimension(60, 30);
		this.la_productCompany.setPreferredSize(companyInputSize);
		this.la_productCompany.setMaximumSize(companyInputSize);
		this.obt_searchCompany.setPreferredSize(companyButtonSize);
		this.obt_searchCompany.setMaximumSize(companyButtonSize);
		companyRow.add(this.la_productCompanyTitle);
		companyRow.add(this.la_productCompany);
		companyRow.add(this.obt_searchCompany);
		add(companyRow);
		add(Box.createVerticalStrut(10));
		addRow(this.la_productUnitTitle, this.cb_productUnit);
		add(Box.createVerticalStrut(10));
		Dimension descriptionInputSize = new Dimension(200, 70);
		JPanel descriptionRow = new JPanel();
		this.la_productDescriptionTitle.setPreferredSize(this.labelSize);
		this.la_productDescriptionTitle.setMaximumSize(this.labelSize);
		this.sp_productDescription.setPreferredSize(descriptionInputSize);
		this.sp_productDescription.setMaximumSize(descriptionInputSize);
		descriptionRow.add(this.la_productDescriptionTitle);
		descriptionRow.add(this.sp_productDescription);
		add(descriptionRow);
		add(Box.createVerticalStrut(10));
		add(this.obt_submit);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				initCategories();
				initLocations();
				initProductUnits();
				setProduct();
			}
		});
	}

	private void setProduct() {
		if (this.product == null) {
			return;
		}
		if (this.product.getImage_id() != 0) {
			try {
				Image image = imageDAO.select(this.product.getImage_id());
				iv_productImage.setImageFromBase64(image.getImage_data());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.tf_productName.setText(this.product.getProduct_name());
		setCategoryComboBox();
		this.tf_productPrice.setText(String.valueOf(this.product.getProduct_price()));
		this.tf_productCode.setText(this.product.getProduct_code());
		this.tf_productStock.setText(String.valueOf(this.product.getProduct_stock()));
		setLocationComboBox();
		this.la_productCompany.setText(this.product.getCompany().getCompany_name());
		setProductUnitComboBox();
		this.ta_productDescription.setText(this.product.getProduct_description());
		this.selectedCompany = this.product.getCompany();

	}

	private void setCategoryComboBox() {
		if (this.product == null) {
			return;
		}
		String categoryName = this.product.getCategory().getCategory_name();
		for (int i = 0; i < categories.size(); i++) {
			if (this.categories.get(i).getCategory_name().equals(categoryName)) {
				this.cb_productCategory.setSelectedIndex(i + 1);
				break;
			}
		}
	}

	private void setLocationComboBox() {
		if (this.product == null) {
			return;
		}
		String locationName = this.product.getLocation().getLocation_name();
		for (int i = 0; i < this.locations.size(); i++) {
			if (this.locations.get(i).getLocation_name().equals(locationName)) {
				this.cb_productLocation.setSelectedIndex(i + 1);
				break;
			}
		}
	}

	private void setProductUnitComboBox() {
		if (this.product == null) {
			return;
		}
		String productUnitName = this.product.getUnit().getUnit_name();
		for (int i = 0; i < this.productUnits.size(); i++) {
			if (this.productUnits.get(i).getUnit_name().equals(productUnitName)) {
				this.cb_productUnit.setSelectedIndex(i + 1);
				break;
			}
		}
	}

	private void addRow(Component label, Component input) {
		JPanel row = new JPanel();
		row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
		label.setPreferredSize(labelSize);
		label.setMaximumSize(labelSize);
		input.setPreferredSize(inputSize);
		input.setMaximumSize(inputSize);
		row.add(label);
		row.add(input);
		this.add(row);
	}

	private void initCategories() {
		try {
			this.categories = this.categoryDAO.selectAll();
			Vector<String> categoryNames = Stream
					.concat(Stream.of("카테고리를 선택해주세요"), this.categories.stream().map(c -> c.getCategory_name()))
					.collect(Collectors.toCollection(() -> new Vector<>()));
			this.cb_productCategory.setModel(new DefaultComboBoxModel<>(categoryNames));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initLocations() {
		try {
			this.locations = this.locationDAO.selectAll();
			Vector<String> locationNames = Stream
					.concat(Stream.of("창고구역을 선택해주세요"), this.locations.stream().map(ㅣ -> ㅣ.getLocation_name()))
					.collect(Collectors.toCollection(() -> new Vector<>()));
			this.cb_productLocation.setModel(new DefaultComboBoxModel<>(locationNames));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initProductUnits() {
		try {
			this.productUnits = this.productUnitDAO.selectAll();
			Vector<String> productUnitNames = Stream
					.concat(Stream.of("단위를 선택해주세요"), this.productUnits.stream().map(pu -> pu.getUnit_name()))
					.collect(Collectors.toCollection(() -> new Vector<>()));
			this.cb_productUnit.setModel(new DefaultComboBoxModel<>(productUnitNames));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insertProduct() {
		List<String> invalidates = getInvalidates();
		if (!invalidates.isEmpty()) {
			String script = String.join(",", invalidates) + " 입력을 확인해 주세요.";
			JOptionPane.showMessageDialog(null, script);
			return;
		}
		Product product = new Product();
		product.setProduct_code(this.tf_productCode.getText());
		product.setProduct_name(this.tf_productName.getText());
		product.setProduct_description(this.ta_productDescription.getText());
		product.setProduct_price(Integer.parseInt(this.tf_productPrice.getText().trim()));
		product.setProduct_stock(Integer.parseInt(this.tf_productStock.getText().trim()));
		product.setCategory(getSelectedCategory());
		product.setCompany(this.selectedCompany);
		product.setUnit(getSelectedProductUnit());
		product.setLocation(getSelectedLocation());

		try {
			DBManager.getInstance().getConnetion().setAutoCommit(false);
			int pkKey = -2;
			if (changedImage) {
				String encodeStringData = ImageUtil.encodeImageToBase64(this.iv_productImage);
				String ext = this.iv_productImage.getImageExtension();
				if (encodeStringData == null || ext == null) {
					throw new NullPointerException("encodeStringData is Null");
				}

				Image image = new Image();
				image.setImage_data(encodeStringData);
				image.setExt(ext);
				pkKey = imageDAO.insert(image);
			}

			if (pkKey == -1) {
				DBManager.getInstance().getConnetion().rollback();
			} else {
				if (pkKey != -2) {
					product.setImage_id(pkKey);
				}
				productDAO.insert(product);
				DBManager.getInstance().getConnetion().commit();
			}
			JOptionPane.showMessageDialog(null, "상품 등록에 성공했습니다.");
			if (this.completeRunnable != null) {
				this.completeRunnable.run();
			}
			this.dispose();
		} catch (SQLException | RuntimeException e) {
			try {
				DBManager.getInstance().getConnetion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "상품 등록에 실패했습니다.");
		} finally {
			try {
				DBManager.getInstance().getConnetion().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateProduct() {
		List<String> invalidates = getInvalidates();
		if (!invalidates.isEmpty()) {
			String script = String.join(",", invalidates) + " 입력을 확인해 주세요.";
			JOptionPane.showMessageDialog(null, script);
			return;
		}

		// 선택한 프로덕트 복사본 생성
		Product product = new Product();
		product.setProduct_id(this.product.getProduct_id());
		product.setProduct_code(this.product.getProduct_code());
		product.setProduct_name(this.product.getProduct_name());
		product.setProduct_description(this.product.getProduct_description());
		product.setProduct_price(this.product.getProduct_price());
		product.setProduct_stock(this.product.getProduct_stock());
		product.setCategory(this.product.getCategory());
		product.setCompany(this.product.getCompany());
		product.setUnit(this.product.getUnit());
		product.setLocation(this.product.getLocation());
		product.setImage_id(this.product.getImage_id());

		product.setProduct_code(this.tf_productCode.getText());
		product.setProduct_name(this.tf_productName.getText());
		product.setProduct_description(this.ta_productDescription.getText());
		product.setProduct_price(Integer.parseInt(this.tf_productPrice.getText()));
		product.setProduct_stock(Integer.parseInt(this.tf_productStock.getText()));
		product.setCategory(getSelectedCategory());
		product.setCompany(this.selectedCompany);
		product.setUnit(getSelectedProductUnit());
		product.setLocation(getSelectedLocation());

		try {
			DBManager.getInstance().getConnetion().setAutoCommit(false);
			int pkKey = -2;
			if (changedImage) {
				String encodeStringData = ImageUtil.encodeImageToBase64(this.iv_productImage);
				String ext = this.iv_productImage.getImageExtension();
				if (encodeStringData == null || ext == null) {
					throw new NullPointerException("encodeStringData is Null");
				}

				Image image = new Image();
				image.setImage_data(encodeStringData);
				image.setExt(ext);
				pkKey = imageDAO.insert(image);
			}

			if (pkKey == -1) {
				DBManager.getInstance().getConnetion().rollback();
			} else {
				if (pkKey != -2) {
					product.setImage_id(pkKey);
				}
				productDAO.update(product);
				DBManager.getInstance().getConnetion().commit();
			}
			JOptionPane.showMessageDialog(null, "상품 수정에 성공했습니다.");
			if (this.completeRunnable != null) {
				this.completeRunnable.run();
			}
			this.dispose();
		} catch (SQLException | RuntimeException e) {
			try {
				DBManager.getInstance().getConnetion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "상품 수정에 실패했습니다.");
		} finally {
			try {
				DBManager.getInstance().getConnetion().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private List<String> getInvalidates() {
		List<String> invalidates = new ArrayList<>();

		if (this.tf_productName.getText().trim().isEmpty()) {
			invalidates.add("상품 이름");
		}
		if (this.cb_productCategory.getSelectedIndex() == 0) {
			invalidates.add("카테고리");
		}
		if (this.tf_productPrice.getText().trim().isEmpty()) {
			invalidates.add("가격");
		}
		if (this.tf_productCode.getText().trim().isEmpty()) {
			invalidates.add("상품코드");
		}
		if (this.tf_productStock.getText().isEmpty()) {
			invalidates.add("창고구역");
		}
		if (this.cb_productLocation.getSelectedIndex() == 0) {
			invalidates.add("창고구역");
		}
		if (this.selectedCompany == null) {
			invalidates.add("거래처");
		}
		if (this.cb_productUnit.getSelectedIndex() == 0) {
			invalidates.add("단위");
		}
		try {
			Integer.parseInt(this.tf_productPrice.getText().trim());
		} catch (Exception e) {
			invalidates.add("가격 숫자");
		}
		try {
			Integer.parseInt(this.tf_productStock.getText().trim());
		} catch (Exception e) {
			invalidates.add("안전재고 수량 숫자");
		}

		return invalidates;
	}

	private Category getSelectedCategory() {
		int index = this.cb_productCategory.getSelectedIndex() - 1;
		return this.categories.get(index);
	}

	private Location getSelectedLocation() {
		int index = this.cb_productLocation.getSelectedIndex() - 1;
		return this.locations.get(index);
	}

	private ProductUnit getSelectedProductUnit() {
		int index = this.cb_productUnit.getSelectedIndex() - 1;
		return this.productUnits.get(index);
	}

	public void setOnComplete(Runnable runnable) {
		this.completeRunnable = runnable;
	}
}
