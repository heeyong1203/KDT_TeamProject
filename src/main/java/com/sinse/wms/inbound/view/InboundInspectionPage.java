package com.sinse.wms.inbound.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.exception.InvalidFormException;
import com.sinse.wms.common.view.button.OutLineButton;
import com.sinse.wms.common.view.content.BaseContentPage;
import com.sinse.wms.common.view.content.FilterPanelResult;
import com.sinse.wms.common.view.content.LabeledComboBox;
import com.sinse.wms.common.view.content.TablePanel;
import com.sinse.wms.inbound.repository.InboundDAO;
import com.sinse.wms.product.model.IoRequest;
import com.sinse.wms.product.repository.IoRequestDAO;

public class InboundInspectionPage extends BaseContentPage {
	private LabeledComboBox[] filters;
    private IoRequestDAO dao;
    private TablePanel table;
    private DefaultTableModel filterModel;
    private DefaultTableModel tableModel;
    List<IoRequest> rawData; // 전체 데이터
    private String io_request_type = "입고";
    private String status_name = "검수요청";
    
    private OutLineButton bt_reload, bt_regist, bt_registAll, bt_approved, bt_denied;

    public InboundInspectionPage(Color color) {
    	setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30)); // 레이아웃 스타일 설정
    	dao = new IoRequestDAO(); // DAO 객체 생성
    	
    	// 1. 콤보박스 초기화를 위한 데이터 먼저 조회
        List<String> emptyFilters = Arrays.asList(null, null, null, null, null, null);
        rawData = dao.selectByFilter(io_request_type, status_name, emptyFilters); // 초기 rawData 조회
        
        // 2. 콤보박스 필터 모델 생성
        filterModel = IoViewHelper.toFilterModel(rawData, Config.FILTER_COLUMNS); // 콤보박스 필터 모델
        
        // 3. 필터 패널 생성
        FilterPanelResult result = LabeledComboBox.createFilterPanel(filterModel, Config.FILTER_COLUMNS, new Dimension(80, 30), new Dimension(185, 30));
        this.filters = result.filters; // 콤보박스 배열 저장
        add(result.panel); // 필터 패널 붙이기
        
        // 4. 필터 적용하여 다시 rawData 조회
        rawData = dao.selectByFilter(io_request_type, status_name, IoFilterHelper.getFilterValues(filters));
        
        // 5. 테이블 모델 생성 및 0번째 입력 행 추가
        tableModel = IoViewHelper.toTableModel(rawData, Config.TABLE_COLUMNS, status_name, true); // 테이블 모델 
        Object[] emptyRow = new Object[tableModel.getColumnCount()]; // 0번째 입력행
    	
    	// 6. 테이블 생성하기 (첫 레코드 수정 가능: true)
    	table = new TablePanel(tableModel, false, true);
    	add(table);
    	    
    	// 7. 버튼 부착
    	add(createButtons()); 
        
    	// 8. 버튼 이벤트 처리
        bt_reload.addMouseListener(new MouseAdapter() {
        	public void mouseReleased(MouseEvent e) {
        		reload();
        	}
        });
    }
        
    /*------------------------------------------------
      버튼 생성 함수
    ------------------------------------------------*/
    private JPanel createButtons() {
	  	
	  	JPanel p_bt = new JPanel();
	  	p_bt.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
	  	p_bt.setPreferredSize(new Dimension(Config.CONTENT_BODY_WIDTH-110, 35));
	  	p_bt.setOpaque(false);
	
	  	bt_reload = new OutLineButton("단일등록", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);
		bt_regist = new OutLineButton("단일등록", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);
		bt_registAll = new OutLineButton("일괄등록", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);
		bt_approved = new OutLineButton("요청수락", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);
		bt_denied = new OutLineButton("요청거절", 107, 35, 5, 1, Config.PRIMARY_COLOR, Color.WHITE);
		
		p_bt.add(bt_reload); // 버튼 붙이기
		p_bt.add(bt_regist);
		p_bt.add(bt_registAll);
		p_bt.add(bt_approved);
		p_bt.add(bt_denied);

		add(p_bt);
    
		return p_bt;
    }
    
    /*------------------------------------------------
      테이블 다시 보여주기 함수
    ------------------------------------------------*/
    private void reload() {
        // 위 조건으로 dao 재호출
    	rawData = dao.selectByFilter(io_request_type, status_name, IoFilterHelper.getFilterValues(filters)); 
        
        // 새로운 테이블 모델 생성
    	tableModel = IoViewHelper.toTableModel(rawData, Config.TABLE_COLUMNS, status_name, true); // 테이블 모델
    	Object[] emptyRow = new Object[tableModel.getColumnCount()]; // 0번째 입력행
        
        // 기존 테이블에서 새 모델로 변경
    	table.setModel(tableModel);
    	updateUI();
  }
    
}
