package com.sinse.wms.io.approve;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.io.view.IoFilterPanel;
import com.sinse.wms.io.view.IoTableModel;
import com.sinse.wms.product.model.IoRequest;
import com.sinse.wms.product.model.RequestStatus;
import com.sinse.wms.product.repository.IoRequestDAO;
import com.sinse.wms.product.repository.RequestStatusDAO;

public class IoRequestApprovalController {
	private IoFilterPanel filterPanel;
    private IoRequestDAO ioRequestDAO;
    private RequestStatusDAO statusDAO;

    public IoRequestApprovalController(IoFilterPanel filterPanel) {
        this.filterPanel = filterPanel;
        this.ioRequestDAO = new IoRequestDAO();
        this.statusDAO = new RequestStatusDAO();
	}

    /*-------------------------------------------------------------
     * 선택된 행들 상태를 변경하고 DB에 업데이트합니다. (요청→검수요청→승인 +반려)
     ------------------------------------------------------------*/
    public void approveRequest() {
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;												// 현 진행상태가 승인 버튼인지에 대한 플래그 변수 (최종 승인 날짜를 확인하기 위함)
        
        try {
            con = dbManager.getConnetion();
            con.setAutoCommit(false); 										// 트랜잭션 시작

            IoTableModel model = (IoTableModel) filterPanel.getTableLayout().getTable().getModel(); 
            List<IoRequest> selectedList = model.getSelectedRows(); 		// 체크박스 선택된 IoRequest(레코드 인스턴스)를 리스트로 담기

            if (selectedList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "체크된 요청 항목이 없습니다.");
                return;
            }

            for (IoRequest io : selectedList) {
            	String currentStatus = io.getStatus().getStatus_name();
            	
            	// request_at 은 버튼 클릭 시점으로 항상 변경
            	io.setRequest_at(new java.sql.Date(System.currentTimeMillis()));
            	
                if (currentStatus.equals("요청")) {
                    io.setStatus(statusDAO.findByName("검수요청")); 			// 요청 → 검수요청
                    io.setApproved_at(null);
                    ioRequestDAO.update(io, con, false);
                    
                } else if (currentStatus.equals("검수요청")) {
                    io.setStatus(statusDAO.findByName("승인"));				// 검수요청 → 승인
                    io.setApproved_at(new java.sql.Date(System.currentTimeMillis()));
                    ioRequestDAO.update(io, con, true);
                } else {
                    continue; 												// 승인 상태거나 무시할 상태
                }
                              
                System.out.println("상태 업데이트 완료: " + io.getStatus().getStatus_name());
            }
            con.commit(); 													// 모두 성공하면 커밋
            model.clearSelections(); 										// 체크박스를 초기화
            JOptionPane.showMessageDialog(null, "상태가 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback(); 										// 에러 시 롤백
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "승인이 실패하였습니다 : "+e.getMessage());
        } 
    }
    
    public void denyRequests() {
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;

        try {
            con = dbManager.getConnetion();
            con.setAutoCommit(false); 											 // 트랜잭션 시작

            IoTableModel model = (IoTableModel) filterPanel.getTableLayout().getTable().getModel();
            List<IoRequest> selectedList = model.getSelectedRows();

            if (selectedList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "체크된 요청 항목이 없습니다.");
                return;
            }

            RequestStatus deniedStatus = statusDAO.findByName("반려");

            for (IoRequest io : selectedList) {
                io.setStatus(deniedStatus); 									 // 상태를 "반려"로 변경
                io.setApproved_at(null);    									 // 승인일자 없음
                io.setRequest_at(new java.sql.Date(System.currentTimeMillis())); // 요청일자 갱신

                ioRequestDAO.update(io, con, false);
                System.out.println("반려 처리 완료: " + io.getIoRequest_id());
            }

            con.commit();
            model.clearSelections();
            JOptionPane.showMessageDialog(null, "요청이 반려되었습니다.");

        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "반려 처리에 실패하였습니다 : " + e.getMessage());
        } 
    }
}