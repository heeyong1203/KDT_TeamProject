package com.sinse.wms.product.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.ProductSnapshot;

public class ProductSnapshotDAO {
	DBManager dbManager = DBManager.getInstance();
	
	// 스냅샷 id로 스냅샷 조회
		public ProductSnapshot findByPk(int pk) {
			ProductSnapshot snap = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = "SELECT * FROM product_snapshot WHERE product_snapshot_id = ?";
			
			Connection con = dbManager.getConnetion();
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, pk);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					snap = new ProductSnapshot();
					snap.setProduct_snapshot_id(rs.getInt("product_snapshot_id"));
					snap.setProduct_name(rs.getString("product_name"));
					snap.setProduct_price(rs.getInt("product_price"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbManager.release(pstmt, rs);
			}
			return snap;
		}  
	
	// 전체 스냅샷 조회
	public List<ProductSnapshot> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProductSnapshot> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			String sql = "SELECT * FROM product_snapshot";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductSnapshot snap = new ProductSnapshot();
				snap.setProduct_snapshot_id(rs.getInt("product_snapshot_id"));
				snap.setProduct_name(rs.getString("product_name"));
				snap.setProduct_price(rs.getInt("product_price"));
				list.add(snap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 스냅샷 추가
	public void insert(ProductSnapshot snap, Connection con) {
		PreparedStatement pstmt = null;

		try {
			String sql = "INSERT INTO product_snapshot(product_snapshot_id, product_name, product_price) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, snap.getProduct_snapshot_id());
			pstmt.setString(2, snap.getProduct_name());
			pstmt.setInt(3, snap.getProduct_price());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 스냅샷 수정
	public void update(ProductSnapshot snap) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "UPDATE product_snapshot SET product_name = ?, product_price = ? WHERE product_snapshot_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, snap.getProduct_name());
			pstmt.setInt(2, snap.getProduct_price());
			pstmt.setInt(3, snap.getProduct_snapshot_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 스냅샷 삭제
	public void delete(int snapshotId, Connection con) {	
		PreparedStatement pstmt = null;

		try {
			String sql = "DELETE FROM product_snapshot WHERE product_snapshot_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, snapshotId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}
}
