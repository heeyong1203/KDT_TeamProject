package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.ProductUnit;

public class ProductUnitDAO {
	DBManager dbManager = DBManager.getInstance();

	// 전체 단위 목록 가져오기
	public List<ProductUnit> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProductUnit> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM product_unit");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductUnit unit = new ProductUnit();
				unit.setUnit_id(rs.getInt("unit_id"));
				unit.setUnit_name(rs.getString("unit_name"));
				list.add(unit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 한 건 입력
	public void insert(ProductUnit unit) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO product_unit(unit_name) VALUES(?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, unit.getUnit_name());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 한 건 수정
	public void update(ProductUnit unit) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE product_unit SET unit_name = ? WHERE unit_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, unit.getUnit_name());
			pstmt.setInt(2, unit.getUnit_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 한 건 삭제
	public void delete(int unitId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM product_unit WHERE unit_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, unitId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}
}
