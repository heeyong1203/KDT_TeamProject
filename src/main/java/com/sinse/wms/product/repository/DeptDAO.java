package com.sinse.wms.product.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Dept;

public class DeptDAO {
	DBManager dbManager = DBManager.getInstance();

	// 전체 부서 목록 조회
	public List<Dept> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Dept> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM dept");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Dept dept = new Dept();
				dept.setDept_id(rs.getInt("dept_id"));
				dept.setDept_name(rs.getString("dept_name"));
				list.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 부서 추가
	public void insert(Dept dept) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "INSERT INTO dept(dept_name) VALUES(?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dept.getDept_name());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 부서 수정
	public void update(Dept dept) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "UPDATE dept SET dept_name = ? WHERE dept_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dept.getDept_name());
			pstmt.setInt(2, dept.getDept_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 부서 삭제
	public void delete(int deptId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "DELETE FROM dept WHERE dept_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, deptId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}
}
