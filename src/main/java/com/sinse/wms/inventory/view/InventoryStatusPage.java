package com.sinse.wms.inventory.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.util.ExcelFileExport;
import com.sinse.wms.common.util.GetSaveFilePath;
import com.sinse.wms.common.util.PdfFileExport;
import com.sinse.wms.common.view.button.OutLineButton;
import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.inventory.model.InventoryTableModel;
import com.sinse.wms.io.view.LabeledComboBox;
import com.sinse.wms.product.model.Company;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.repository.CompanyDAO;
import com.sinse.wms.product.repository.ProductDAO;
import com.sinse.wms.product.repository.RequestStatusDAO;

public class InventoryStatusPage extends BaseContentPage {
    JPanel p_wrapper;
    LabeledComboBox[] filters;
    
    InventoryTableModel tableModel;
    JTable table;
    JScrollPane scroll;
    JPanel p_bottom;	//버튼들을 모아둘 패널
    OutLineButton bt_search;
    OutLineButton bt_excel;
    OutLineButton bt_pdf;
    
    CompanyDAO companyDAO;
    RequestStatusDAO requestStatusDAO;
    ProductDAO productDAO;
    
    Product product = new Product();
    
    final int COMPANY = 0;
    final int PRODUCTCODE = 1;
    final int PRODUCTNAME = 2;
    
    //테이블에 들어갈 컬럼명 정의
    List<String> columns = new ArrayList(Arrays.asList("No", "상품명", "품목코드", "현재 재고량", "적정재고량", "주문 필요여부"));

    public InventoryStatusPage(Color color) {
    	// ... 기본 스타일 설정 ...
    	setBackground(color);
    	
    	/*---------------------------------------------------------------------------
    	 * 		생성
    	 *--------------------------------------------------------------------------- */
        p_wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
        tableModel = new InventoryTableModel(columns, product);
        table = new JTable(tableModel);
        scroll = new JScrollPane(table);
        p_bottom = new JPanel();
        bt_search = new OutLineButton("조회하기", 10, 1, Config.PRIMARY_COLOR, Color.WHITE);
        bt_excel = new OutLineButton("excel 다운받기", 10, 1, Config.PRIMARY_COLOR,  Color.WHITE);
        bt_pdf = new OutLineButton("pdf 다운받기", 10, 1, Config.PRIMARY_COLOR,  Color.WHITE);
        
        companyDAO = new CompanyDAO();
        requestStatusDAO = new RequestStatusDAO();
        productDAO = new ProductDAO();
        
        /*---------------------------------------------------------------------------
    	 * 		스타일 지정
    	 *--------------------------------------------------------------------------- */
        p_wrapper.setPreferredSize(new Dimension(870, 80));
        p_wrapper.setOpaque(false);

        // 라벨 및 JComboBox 사이즈 지정
        Dimension labelSize = new Dimension(60, 30);
        Dimension comboSize = new Dimension(200, 30);
        
        //테이블, 버튼 레이아웃 사이즈 지정
        scroll.setPreferredSize(new Dimension(Config.CONTENT_BODY_WIDTH - Config.CONTENT_BODY_BORDER_WIDTH * 2-20, 650));
        scroll.setBackground(Color.gray);
        p_bottom.setPreferredSize(new Dimension(Config.CONTENT_BODY_WIDTH - Config.CONTENT_BODY_BORDER_WIDTH * 2-20, 50));
        p_bottom.setBackground(new Color(0, 0, 0, 0));	//배경 투명
        
        /*---------------------------------------------------------------------------
    	 * 		이벤트 연결
    	 *--------------------------------------------------------------------------- */
        List<Company> companyList = companyDAO.selectAll();
        String[] companyArray = new String[companyList.size()+1];
        for(int i=0; i<companyList.size(); i++) {
        	companyArray[i+1] = companyList.get(i).getCompany_name();
        }
        List<String> productNameList = productDAO.selectProductNames();
        String[] productNameArray = new String[productNameList.size()+1];
        for(int i=0; i<productNameList.size(); i++) {
        	productNameArray[i+1] = productNameList.get(i);
        }
        List<String> productCodeList = productDAO.selectProductCodes();
        String[] productCodeArray = new String[productCodeList.size()+1];
        for(int i=0; i<productCodeList.size(); i++) {
        	productCodeArray[i+1] = productCodeList.get(i);
        }

        // 라벨 및 JComboBox 묶음 지정
        filters = new LabeledComboBox[] {
            new LabeledComboBox("거래처", companyArray, labelSize, comboSize),
            new LabeledComboBox("품목코드", productCodeArray, labelSize, comboSize),
            new LabeledComboBox("품목명", productNameArray, labelSize, comboSize)
        };

        //조회 버튼 이벤트
  		bt_search.addActionListener(e->{
  			Company company = new Company();
  			
  			if (!"".equals(filters[COMPANY].getSelectedItem())) {
  			    company.setCompany_name(filters[COMPANY].getSelectedItem());
  			    product.setCompany(company);
  			}
  			if(!"".equals(filters[PRODUCTCODE].getSelectedItem())) {
  				product.setProduct_code(filters[PRODUCTCODE].getSelectedItem());
  			}
  			if(!"".equals(filters[PRODUCTNAME].getSelectedItem())) {
  				product.setProduct_name(filters[PRODUCTNAME].getSelectedItem());
  			}
  			tableModel.setProduct(product);
  		});
  		
  		//엑셀 버튼 이벤트
  		bt_excel.addActionListener(e->{
  			String path = GetSaveFilePath.saveFilePath();		//사용자에게 저장할 파일 경로 받기
  			String msg = ExcelFileExport.exportToExcel(columns, tableModel, path);		//excel export
  			JOptionPane.showMessageDialog(this, msg);		//결과 메시지 띄우기
  		});
  		
  		//pdf 버튼 이벤트
  		bt_pdf.addActionListener(e->{
  			String path = GetSaveFilePath.saveFilePath();		//사용자에게 저장할 파일 경로 받기
  			String msg = PdfFileExport.exportToPdf(columns, tableModel, path, "재고 테이블");		//pdf export
  			JOptionPane.showMessageDialog(this, msg);		//결과 메시지 띄우기
  			
  		});
        
  		/*---------------------------------------------------------------------------
    	 * 		부착
    	 *--------------------------------------------------------------------------- */
        for (LabeledComboBox filter : filters) {
            p_wrapper.add(filter);
        }

        add(p_wrapper);
        add(scroll);
        p_bottom.add(bt_search);
        p_bottom.add(bt_excel);
        p_bottom.add(bt_pdf);
        add(p_bottom);
    }
}
