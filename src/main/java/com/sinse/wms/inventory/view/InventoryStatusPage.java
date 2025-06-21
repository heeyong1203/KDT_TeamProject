package com.sinse.wms.inventory.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
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
import com.sinse.wms.common.view.content.LabeledComboBox;
import com.sinse.wms.inventory.model.InventoryTableModel;

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
    
    //테이블에 들어갈 컬럼명 정의
    List<String> columns = new ArrayList(Arrays.asList("No", "상품명", "품목코드", "현재 재고량", "적정재고량", "주문 필요여부"));

    public InventoryStatusPage(Color color) {
    	// ... 기본 스타일 설정 ...
    	setBackground(color);
    	
    	/*---------------------------------------------------------------------------
    	 * 		생성
    	 *--------------------------------------------------------------------------- */
        p_wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
        tableModel = new InventoryTableModel(columns);
        table = new JTable(tableModel);
        scroll = new JScrollPane(table);
        p_bottom = new JPanel();
        bt_search = new OutLineButton("조회하기", 10, 1, Config.PRIMARY_COLOR, Color.WHITE);
        bt_excel = new OutLineButton("excel 다운받기", 10, 1, Config.PRIMARY_COLOR,  Color.WHITE);
        bt_pdf = new OutLineButton("pdf 다운받기", 10, 1, Config.PRIMARY_COLOR,  Color.WHITE);
        
        /*---------------------------------------------------------------------------
    	 * 		스타일 지정
    	 *--------------------------------------------------------------------------- */
        p_wrapper.setPreferredSize(new Dimension(870, 80));
        p_wrapper.setOpaque(false);

        // 라벨 및 JComboBox 사이즈 지정
        Dimension labelSize = new Dimension(80, 30);
        Dimension comboSize = new Dimension(230, 30);
        Dimension comboSize2 = new Dimension(150, 30);
        
        //테이블, 버튼 레이아웃 사이즈 지정
        scroll.setPreferredSize(new Dimension(Config.CONTENT_BODY_WIDTH - Config.CONTENT_BODY_BORDER_WIDTH * 2-20, 500));
        scroll.setBackground(Color.gray);
        p_bottom.setPreferredSize(new Dimension(Config.CONTENT_BODY_WIDTH - Config.CONTENT_BODY_BORDER_WIDTH * 2-20, 50));
        p_bottom.setBackground(new Color(0, 0, 0, 0));	//배경 투명
        
        /*---------------------------------------------------------------------------
    	 * 		이벤트 연결
    	 *--------------------------------------------------------------------------- */
        // 라벨 및 JComboBox 묶음 지정
        filters = new LabeledComboBox[] { // new String[]{} 대신 데이터 배열 넣어주시면 될 것 같습니다.
            new LabeledComboBox("거래처", new String[]{"A사", "B사", "C사"}, labelSize, comboSize),
            new LabeledComboBox("부서명", new String[]{"영업1팀", "영업2팀", "마케팅1팀", "마케팅2팀"}, labelSize, comboSize2),
            new LabeledComboBox("사원명", new String[]{"이경규", "김국진", "강호동", "유재석", "전현무", "장도연"}, labelSize, comboSize2),
            new LabeledComboBox("품목코드", new String[]{"ST-20001", "ST-20002", "ST-20003", "ST-20004", "ST-20005", "ST-20006"}, labelSize, comboSize),
            new LabeledComboBox("품목명", new String[]{"A4 복사용지 80g", "3단 서류함", "유성 네임펜", "고무줄 500g"}, labelSize, comboSize2),
            new LabeledComboBox("진행상태", new String[]{"요청", "대기", "승인", "반려"}, labelSize, comboSize2)
        };

        //조회 버튼 이벤트
  		bt_search.addActionListener(e->{
  			
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
