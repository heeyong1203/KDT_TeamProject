package com.sinse.wms.io.regist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import com.sinse.wms.common.util.ChangeFormToDate;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.io.regist.view.IoRegistPageLayout;
import com.sinse.wms.product.model.IoRequest;
import com.sinse.wms.product.model.Location;
import com.sinse.wms.product.model.Member;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.model.ProductSnapshot;
import com.sinse.wms.product.model.ProductUnit;
import com.sinse.wms.product.model.RequestStatus;
import com.sinse.wms.product.repository.IoRequestDAO;
import com.sinse.wms.product.repository.LocationDAO;
import com.sinse.wms.product.repository.MemberDAO;
import com.sinse.wms.product.repository.ProductDAO;
import com.sinse.wms.product.repository.ProductSnapshotDAO;
import com.sinse.wms.product.repository.RequestStatusDAO;

public class IoRegistPageController {
	private IoRegistPageLayout view;
	private String pageIoType;
	private ProductDAO productDAO = new ProductDAO();
	private LocationDAO locationDAO = new LocationDAO();
	private MemberDAO memberDAO = new MemberDAO();
	private ProductSnapshotDAO productSnapshotDAO = new ProductSnapshotDAO();
	
	public IoRegistPageController(IoRegistPageLayout view, String pageIoType) {

		this.view = view;
		this.pageIoType = pageIoType;
		setCombo();
		setUnitLabel();

		// 등록 버튼 이벤트 구현
		view.getBt_regist().addActionListener(e -> regist());
		
		// 취소 버튼 이벤트 구현
		view.getBt_cancel().addActionListener(e -> cancel());
	}
	
	private void regist() {
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		try {
			con = dbManager.getConnetion();
			con.setAutoCommit(false);
			
			// 유효성 검사, DB처리
			IoRequest ioRequest = new IoRequest();
			IoRequestDAO ioRequestDAO = new IoRequestDAO();
			
			// ioRequest 모델 인스턴스 세팅하기
			
	        /*-- Expected_Date --*/ 
	        String dateText = view.getT_expected_date().getText();
	        if (dateText == null || dateText.trim().isEmpty()) {
	        	throw new IllegalArgumentException(" 예정일자를 선택해주세요.\n"+"ex) 2025-06-30");
	        }
	        ioRequest.setExpected_date(ChangeFormToDate.formChange(dateText));
			
			/*-- request_type --*/
			String io_request_type = view.getCb_type().getSelectedItem().toString();
			if ("타입을 선택하세요.".equals(io_request_type)) {
				throw new IllegalArgumentException("입출고 타입을 선택해주세요.");
			} 
			
			if("입고".equals(pageIoType) || "출고".equals(pageIoType)) {
				if(!io_request_type.equals(pageIoType)) {
					throw new IllegalArgumentException("타입을 " + pageIoType + "로 선택해주세요."); // 입고
				} else {
					ioRequest.setIoRequest_type(io_request_type);
				}
			}else {
				throw new IllegalArgumentException("알 수 없는 입출고 타입입니다: " + pageIoType);

			}
	        
	        /*-- product --*/
	        String selectedProductName = view.getCb_product().getSelectedItem().toString();
	        if ("상품을 선택하세요.".equals(selectedProductName)) {
				throw new IllegalArgumentException("상품을 선택해주세요.");
			}
	        Product product = productDAO.findByName(selectedProductName);
	        ioRequest.setProduct(product);
	        
	        /*-- location --*/
	        String selectedLocationName = view.getCb_location().getSelectedItem().toString();
	        if ("위치를 선택하세요.".equals(selectedLocationName)) {
	        	throw new IllegalArgumentException("위치를 선택해주세요.");
	        }
	        Location location = locationDAO.findByName(selectedLocationName);
	        ioRequest.setLocation(location);
	        
	        /*-- quantity --*/
	        String quantityText = view.getT_quantity().getText();
	        int stock = product.getProduct_stock();
	        if (quantityText == null || quantityText.trim().isEmpty()) {
	        	throw new IllegalArgumentException("수량을 입력해주세요.");
	        }
	        int quantity;
	        try {
	            quantity = Integer.parseInt(quantityText);
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("수량은 숫자만 입력해주세요.");
	        }
	        
	        if("출고".equals(pageIoType)) {
	        	if(quantity>stock) {
	        		throw new IllegalArgumentException("출고수량이 재고량보다 많습니다.");
	        	} ioRequest.setQuantity(quantity); 	        	
	        } else if ("입고".equals(pageIoType)) {
	        	ioRequest.setQuantity(quantity);
	        } else {
	            throw new IllegalArgumentException("알 수 없는 입출고 타입입니다.");
	        }
	        
	        /*-- request_reason --*/
	        String reason = view.getArea_registReason().getText();
	        if (reason==null || reason.trim().isEmpty()) {
	        	throw new IllegalArgumentException("사유를 입력해주세요.");
	        }
	        ioRequest.setRequest_reason(reason);
	        
	        /*-- requester --*/
	        String selectedRequesterName = view.getCb_requester().getSelectedItem().toString();
	        if ("담당자를 선택하세요.".equals(selectedRequesterName)) {
	        	throw new IllegalArgumentException("등록 요청인을 선택해주세요.");
	        }
	        Member requester = memberDAO.findByName(selectedRequesterName); 
	        ioRequest.setRequest_member_id(requester);
	        
	        /*-- approver --*/
	        String selectedApproverName = view.getCb_approver().getSelectedItem().toString();
	        if ("담당자를 선택하세요.".equals(selectedApproverName)) {
	        	throw new IllegalArgumentException("승인 관리인을 선택해주세요.");
	        }
	        Member approver = memberDAO.findByName(selectedApproverName); 
	        ioRequest.setMember(approver);
	        
	        
	        /*-- status : 페이지별 고정값 (요청, 검수요청: insert // 승인, 반려: 관리자에 의한 update) --*/ 
	        String selectedStatus_type = view.getStatus_type();
	        RequestStatusDAO statusDAO = new RequestStatusDAO();
	        RequestStatus status = statusDAO.findByName(selectedStatus_type);
	        ioRequest.setStatus(status); // status는 모델이 아닌 int값을 매개변수로 원함..
	        
	        /*-- request_at --*/
	        java.sql.Date now = ChangeFormToDate.nowDate();
	        ioRequest.setRequest_at(now);
	        
	        /*-- Approve Date 일단 null로 고정값입력 추후 승인 발생 시 null 값에서 update하는 쿼리문 이용 --*/
	        ioRequest.setApproved_at(null);
	        
	        /*-- remark --*/
	        String remark = view.getArea_remark().getText();
	        ioRequest.setRemark(remark);
	        
	        /*-- 등록! --*/
	        ioRequestDAO.insert(ioRequest, con);
	        
	        /*--- 등록 완료와 동시에 상품 가격 history 등록 ---*/
	        ProductSnapshot snap = new ProductSnapshot();
	        snap.setProduct_snapshot_id(ioRequest.getIoRequest_id());
	        snap.setProduct_name(selectedProductName);
	        snap.setProduct_price(product.getProduct_price());
	        productSnapshotDAO.insert(snap, con);
	                
			con.commit();// 모두 성공했을 시
			JOptionPane.showMessageDialog(null, "등록이 완료되었습니다.");
			view.dispose();
			view = null;
			
		} catch (Exception e) {
			try {
				if(con != null) con.rollback();
			} catch (SQLException rollbackEx){
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "등록에 실패하셨습니다 : "+e.getMessage());
		} 
	}
	
	private void cancel() {
		// 화면 닫기
		view.dispose();
		view = null;
	}
	
	public void setCombo() {
		ProductDAO productDAO = new ProductDAO(); 
		LocationDAO locationDAO = new LocationDAO();
		MemberDAO memberDAO = new MemberDAO(); 
		List<String> productNames = new ArrayList<>();
		List<String> locationNames = new ArrayList<>();
		List<String> memberNames = new ArrayList<>();
		
		productNames.add("상품을 선택하세요.");
		locationNames.add("위치를 선택하세요.");
		memberNames.add("담당자를 선택하세요.");
		
		productNames.addAll(productDAO.selectProductNames());
		locationNames.addAll(locationDAO.selectLocationNames());
		memberNames.addAll(memberDAO.selectMemberNames());
		
		view.setTypeItems(Arrays.asList("타입을 선택하세요.", "입고", "출고"));
		view.setProductItems(productNames);
		view.setLocationItems(locationNames);
		view.setRequesterItems(memberNames);
		view.setApproverItems(memberNames);

	}
	
	public void setUnitLabel() {
		view.getCb_product().addActionListener(e -> {
		    String productName = view.getCb_product().getSelectedItem().toString();
		    // System.out.println("[DEBUG] 선택한 상품명: " + productName);
		    if (!"상품을 선택하세요.".equals(productName)) {
		        Product product = productDAO.findByName(productName);
		        // System.out.println("[DEBUG] product: " + product);
		        // System.out.println("[DEBUG] unit: " + (product != null ? product.getUnit() : "null"));
		        if (product != null && product.getUnit() != null) {
		            ProductUnit unit = product.getUnit();
		            view.getLa_unit().setText(unit.getUnit_name());
		        } else {
		            view.getLa_unit().setText(""); // 혹시 null일 경우 대비
		        }
		    } else {
		        view.getLa_unit().setText(""); // 기본 안내 문구일 때는 비우기
		    }
		});
	}
}
