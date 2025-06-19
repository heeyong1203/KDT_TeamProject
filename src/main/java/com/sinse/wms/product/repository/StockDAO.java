package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Location;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.model.Stock;



public class StockDAO {
	DBManager dbManager = DBManager.getInstance();

	// 전체 재고 조회
	public List<Stock> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Stock> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM stock");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Stock stock = new Stock();
				stock.setStock_id(rs.getInt("stock_id"));
				stock.setStock_quantity(rs.getInt("stock_quantity"));

				Product p = new Product();
				p.setProduct_id(rs.getInt("product_id"));
				stock.setProduct(p);

				Location l = new Location();
				l.setLocation_id(rs.getInt("location_id"));
				stock.setLocation(l);

				list.add(stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 재고 추가
	public void insert(Stock stock) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO stock(stock_quantity, product_id, location_id) VALUES(?, ?, ?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, stock.getStock_quantity());
			pstmt.setInt(2, stock.getProduct().getProduct_id());
			pstmt.setInt(3, stock.getLocation().getLocation_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 재고 수정
	public void update(Stock stock) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE stock SET stock_quantity = ?, product_id = ?, location_id = ? ");
			sql.append("WHERE stock_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, stock.getStock_quantity());
			pstmt.setInt(2, stock.getProduct().getProduct_id());
			pstmt.setInt(3, stock.getLocation().getLocation_id());
			pstmt.setInt(4, stock.getStock_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 재고 삭제
	public void delete(int stockId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "DELETE FROM stock WHERE stock_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, stockId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}
}