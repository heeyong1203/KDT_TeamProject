
package com.sinse.wms.product.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Member;
import com.sinse.wms.product.model.RequestStatus;

public class RequestStatusDAO {
	DBManager dbManager = DBManager.getInstance();
	
	// 진행상태 조회
	public RequestStatus findByName(String name) {
		RequestStatus status = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM request_status WHERE status_name = ?";
		
		Connection con = dbManager.getConnetion();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				status = new RequestStatus();
				status.setStatus_id(rs.getInt("status_id"));
				status.setStatus_name(rs.getString("status_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}  
	
	// 전체 상태 목록 조회
	public List<RequestStatus> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RequestStatus> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM request_status");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				RequestStatus status = new RequestStatus();
				status.setStatus_id(rs.getInt("status_id"));
				status.setStatus_name(rs.getString("status_name"));
				list.add(status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 상태 추가
	public void insert(RequestStatus status) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "INSERT INTO request_status(status_id, status_name) VALUES(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, status.getStatus_id());
			pstmt.setString(2, status.getStatus_name());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 상태 수정
	public void update(RequestStatus status) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "UPDATE request_status SET status_name = ? WHERE status_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status.getStatus_name());
			pstmt.setInt(2, status.getStatus_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 상태 삭제
	public void delete(int statusId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "DELETE FROM request_status WHERE status_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, statusId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}
}