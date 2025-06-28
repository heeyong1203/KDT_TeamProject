package com.sinse.wms.product.repository;

import com.sinse.wms.common.exception.*;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.ProductUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductUnitDAO {
	DBManager dbManager = DBManager.getInstance();

	// 전체 단위 목록 가져오기
	public List<ProductUnit> selectAll() throws  ProductUnitSelectException {
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
			throw new ProductUnitSelectException("창고 위치 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 이름으로 검색
	public List<ProductUnit> selectByNameLike(String productUnitName) throws ProductUnitSelectException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProductUnit> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT unit_id, unit_name FROM product_unit WHERE unit_name LIKE ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, productUnitName + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductUnit unit = new ProductUnit();
				unit.setUnit_id(rs.getInt("unit_id"));
				unit.setUnit_name(rs.getString("unit_name"));
				list.add(unit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductUnitSelectException("창고 위치 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 한 건 입력
	public void insert(ProductUnit unit) throws ProductUnitInsertException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO product_unit(unit_name) VALUES(?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, unit.getUnit_name());
			int result = pstmt.executeUpdate();
			if(result == 0) {
				throw  new ProductUnitInsertException("창고 구역 등록 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductUnitInsertException("창고 구역 등록 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 한 건 수정
	public void update(ProductUnit unit) throws ProductUnitUpdateException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE product_unit SET unit_name = ? WHERE unit_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, unit.getUnit_name());
			pstmt.setInt(2, unit.getUnit_id());
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new ProductUnitUpdateException("단위 업데이트 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductUnitUpdateException("단위 업데이트 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 한 건 삭제
	public void delete(int unitId) throws ProductUnitDeleteException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM product_unit WHERE unit_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, unitId);
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new ProductUnitDeleteException("단위 삭제 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductUnitDeleteException("단위 삭제 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}
}
