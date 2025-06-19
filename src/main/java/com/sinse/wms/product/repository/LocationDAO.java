package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Location;

public class LocationDAO {
	DBManager dbManager = DBManager.getInstance();

	// 전체 위치 목록 조회
	public List<Location> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Location> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM location");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Location location = new Location();
				location.setLocation_id(rs.getInt("location_id"));
				location.setLocation_name(rs.getString("location_name"));
				list.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 위치 추가
	public void insert(Location location) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO location(location_name) VALUES(?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, location.getLocation_name());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 위치 수정
	public void update(Location location) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE location SET location_name = ? WHERE location_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, location.getLocation_name());
			pstmt.setInt(2, location.getLocation_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 위치 삭제
	public void delete(int locationId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM location WHERE location_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, locationId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}
}
