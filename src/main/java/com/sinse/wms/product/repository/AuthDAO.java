package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Auth;

public class AuthDAO {
	DBManager dbManager = DBManager.getInstance();

	// 전체 권한 목록 조회
	public List<Auth> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Auth> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			String sql = "SELECT * FROM auth";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Auth auth = new Auth();
				auth.setAuth_id(rs.getInt("auth_id"));
				auth.setAuth_name(rs.getString("auth_name"));
				auth.setAuth_flag(rs.getInt("auth_flag"));
				list.add(auth);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 권한 추가
	public void insert(Auth auth) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "INSERT INTO auth(auth_name, auth_flag) VALUES(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, auth.getAuth_name());
			pstmt.setInt(2, auth.getAuth_flag());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 권한 수정
	public void update(Auth auth) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "UPDATE auth SET auth_name = ?, auth_flag = ? WHERE auth_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, auth.getAuth_name());
			pstmt.setInt(2, auth.getAuth_flag());
			pstmt.setInt(3, auth.getAuth_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 권한 삭제
	public void delete(int authId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "DELETE FROM auth WHERE auth_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, authId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}
}
