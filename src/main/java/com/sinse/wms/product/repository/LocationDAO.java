package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.exception.LocationDeleteException;
import com.sinse.wms.common.exception.LocationInsertException;
import com.sinse.wms.common.exception.LocationSelectException;
import com.sinse.wms.common.exception.LocationUpdateException;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Location;
import com.sinse.wms.product.model.Product;

public class LocationDAO {
	DBManager dbManager = DBManager.getInstance();
	
	// 위치명으로 위치테이블 조회
	public Location findByName(String name) {
		Location location = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM location WHERE location_name = ?";
		
		Connection con = dbManager.getConnetion();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				location = new Location();
				location.setLocation_id(rs.getInt("location_id"));
				location.setLocation_name(rs.getString("location_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return location;
	}  
	
	// 위치명 조회
	public List<String> selectLocationNames() {
	    List<String> names = new ArrayList<>();
	    String sql = "SELECT DISTINCT location_name FROM location";

	    Connection con = dbManager.getConnetion();
	    try (
	         PreparedStatement pstmt = con.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            names.add(rs.getString("location_name"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return names;
	}
	
	// 전체 위치 목록 조회
	public List<Location> selectAll() throws LocationSelectException {
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
			throw new LocationSelectException("창고 위치 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 이름으로 위치 찾기
	public List<Location> selectByNameLike(String locationName) throws LocationSelectException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Location> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT location_id, location_name FROM location WHERE location_name LIKE ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, locationName + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Location location = new Location();
				location.setLocation_id(rs.getInt("location_id"));
				location.setLocation_name(rs.getString("location_name"));
				list.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LocationSelectException("창고 위치 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 위치 추가
	public void insert(Location location) throws LocationInsertException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO location(location_name) VALUES(?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, location.getLocation_name());
			int result = pstmt.executeUpdate();
			if(result == 0) {
				throw new LocationInsertException("창고 위치 등록 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LocationInsertException("창고 위치 등록 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 위치 수정
	public void update(Location location) throws LocationUpdateException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE location SET location_name = ? WHERE location_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, location.getLocation_name());
			pstmt.setInt(2, location.getLocation_id());
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new LocationUpdateException("창고 위치 업데이트 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LocationUpdateException("창고 위치 업데이트 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 위치 삭제
	public void delete(int locationId) throws LocationDeleteException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM location WHERE location_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, locationId);
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new LocationDeleteException("창고 위치 삭제 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LocationDeleteException("창고 위치 삭제 실패");
		} finally {
			dbManager.release(pstmt);
		}
	}
}
