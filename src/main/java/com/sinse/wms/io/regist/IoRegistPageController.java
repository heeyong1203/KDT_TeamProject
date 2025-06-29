package com.sinse.wms.io.regist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import com.sinse.wms.common.util.ChangeFormToDate;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.inbound.regist.view.IoRegistPageLayout;
import com.sinse.wms.product.model.IoRequest;
import com.sinse.wms.product.model.Location;
import com.sinse.wms.product.model.Member;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.model.RequestStatus;
import com.sinse.wms.product.repository.IoRequestDAO;
import com.sinse.wms.product.repository.LocationDAO;
import com.sinse.wms.product.repository.MemberDAO;
import com.sinse.wms.product.repository.ProductDAO;
import com.sinse.wms.product.repository.RequestStatusDAO;

public class IoRegistPageController {
	private IoRegistPageLayout view;
	
	public IoRegistPageController(IoRegistPageLayout view) {
		this.view = view;
		setCombo();
		
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
			if (io_request_type.equals("타입을 선택하세요.")) {
				throw new IllegalArgumentException("입출고 타입을 선택해주세요.");
			} else if(!io_request_type.equals("입고")) {
				throw new IllegalArgumentException("타입을 입고로 선택해주세요.");					
			}
	        ioRequest.setIoRequest_type(io_request_type);
	        
	        /*-- product --*/
	        String selectedProductName = view.getCb_product().getSelectedItem().toString();
	        if (selectedProductName.equals("상품을 선택하세요.")) {
				throw new IllegalArgumentException("상품을 선택해주세요.");
			}
	        ProductDAO productDAO = new ProductDAO();
	        Product product = productDAO.findByName(selectedProductName);
	        ioRequest.setProduct(product);
	        
	        /*-- location --*/
	        String selectedLocationName = view.getCb_location().getSelectedItem().toString();
	        if (selectedLocationName.equals("위치를 선택하세요.")) {
	        	throw new IllegalArgumentException("위치를 선택해주세요.");
	        }
	        LocationDAO locationDAO = new LocationDAO();
	        Location location = locationDAO.findByName(selectedLocationName);
	        ioRequest.setLocation(location);
	        
	        /*-- quantity --*/
	        String quantityText = view.getT_quantity().getText();
	        if (quantityText == null || quantityText.trim().isEmpty()) {
	        	throw new IllegalArgumentException("수량을 입력해주세요.");
	        }
	        int quantity;
	        try {
	            quantity = Integer.parseInt(quantityText);
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("수량은 숫자만 입력해주세요.");
	        }
	        ioRequest.setQuantity(quantity); 
	        
	        /*-- request_reason --*/
	        String reason = view.getArea_registReason().getText();
	        if (reason==null || reason.trim().isEmpty()) {
	        	throw new IllegalArgumentException("사유를 입력해주세요.");
	        }
	        ioRequest.setRequest_reason(reason);
	        
	        /*-- requester --*/
	        String selectedRequesterName = view.getCb_requester().getSelectedItem().toString();
	        if (selectedRequesterName.equals("담당자를 선택하세요.")) {
	        	throw new IllegalArgumentException("등록 요청인을 선택해주세요.");
	        }
	        MemberDAO MemberDAO = new MemberDAO();
	        Member requester = MemberDAO.findByName(selectedRequesterName); 
	        ioRequest.setRequest_member_id(requester);
	        
	        /*-- approver --*/
	        String selectedApproverName = view.getCb_approver().getSelectedItem().toString();
	        if (selectedApproverName.equals("담당자를 선택하세요.")) {
	        	throw new IllegalArgumentException("승인 관리인을 선택해주세요.");
	        }
	        Member approver = MemberDAO.findByName(selectedApproverName); 
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
}
