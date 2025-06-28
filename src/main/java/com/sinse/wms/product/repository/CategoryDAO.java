package com.sinse.wms.product.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.exception.CategoryDeleteException;
import com.sinse.wms.common.exception.CategoryInsertException;
import com.sinse.wms.common.exception.CategorySelectException;
import com.sinse.wms.common.exception.CategoryUpdateException;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Category;

public class CategoryDAO {
	DBManager dbManager = DBManager.getInstance();

	// 전체 카테고리 조회
	public List<Category> selectAll() throws CategorySelectException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Category> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM category");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Category category = new Category();
				category.setCategory_id(rs.getInt("category_id"));
				category.setCategory_name(rs.getString("category_name"));
				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CategorySelectException("카테고리 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
	// 이름으로 카테고리 조회
	public List<Category> selectByNameLike(String categoryName) throws CategorySelectException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Category> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT category_id, category_name FROM category WHERE category_name LIKE ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, categoryName + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Category category = new Category();
				category.setCategory_id(rs.getInt("category_id"));
				category.setCategory_name(rs.getString("category_name"));
				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CategorySelectException("카테고리 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 카테고리 추가
	public void insert(Category category) throws CategoryInsertException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO category(category_name) VALUES(?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, category.getCategory_name());
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new CategoryInsertException("카테고리 등록 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CategoryInsertException("카테고리 등록 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 카테고리 수정
	public void update(Category category) throws CategoryUpdateException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE category SET category_name = ? WHERE category_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, category.getCategory_name());
			pstmt.setInt(2, category.getCategory_id());
			int result = pstmt.executeUpdate();
			if(result == 0) {
				throw new CategoryUpdateException("카테고리 업데이트 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CategoryUpdateException("카테고리 업데이트 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 카테고리 삭제
	public void delete(int categoryId) throws CategoryDeleteException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM category WHERE category_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, categoryId);
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new CategoryDeleteException("카테고리 삭제 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CategoryDeleteException("카테고리 삭제 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}
}