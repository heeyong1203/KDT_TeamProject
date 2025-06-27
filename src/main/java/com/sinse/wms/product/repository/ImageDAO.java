package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sinse.wms.common.exception.ImageDeleteException;
import com.sinse.wms.common.exception.ImageInsertException;
import com.sinse.wms.common.exception.ImageSelectException;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Image;

public class ImageDAO {
	private DBManager dbManager = DBManager.getInstance();

	// 이미지 조회
	public Image select(int image_id) throws ImageSelectException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnetion();
		Image image = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM image WHERE image_id=? LIMIT 1");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, image_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				image = new Image();
				image.setImage_id(rs.getInt("image_id"));
				image.setImage_data(rs.getString("image_data"));
				image.setExt(rs.getString("image_ext"));
				image.setRegDate(rs.getString("reg_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ImageSelectException("이미지 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return image;
	}

	// 이미지 추가
	public int insert(Image image) throws ImageInsertException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "INSERT INTO image(image_data, image_ext) VALUES(?,?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, image.getImage_data());
			pstmt.setString(2, image.getExt());
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new ImageInsertException("이미지 등록 실패 result 0");
			}
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ImageInsertException("이미지 등록 실패", e);
		} finally {
			dbManager.release(pstmt);
		}

		return -1;
	}

	// 이미지 삭제
	public void delete(int image_id) throws ImageDeleteException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "DELETE FROM image WHERE image_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, image_id);
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new ImageDeleteException("이미지 삭제 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ImageDeleteException("이미지 삭제 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}
}
