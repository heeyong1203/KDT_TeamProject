package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinse.wms.common.StockRecord;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Location;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.model.Stock;



public class StockDAO {
	DBManager dbManager = DBManager.getInstance();
	public List<StockRecord> selectRequestByTypeAndDate(String type, String startDate, String endDate) {
	    List<StockRecord> list = new ArrayList<>();
	    String sql = "SELECT r.io_request_type, r.request_at, r.quantity, p.product_name, l.location_name FROM io_request r JOIN product p ON r.product_id = p.product_id JOIN location l ON r.location_id = l.location_id WHERE r.io_request_type = ? AND r.request_at BETWEEN ? AND ?";

	    try (
	        Connection con = dbManager.getConnetion();
	        PreparedStatement pstmt = con.prepareStatement(sql);
	    ) {
	        pstmt.setString(1, type); // 예: "입고", "출고", "변경"
	        pstmt.setString(2, startDate);
	        pstmt.setString(3, endDate);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            StockRecord record = new StockRecord();
	            record.setProductName(rs.getString("product_name"));
	            record.setAmount(rs.getInt("quantity"));
	            record.setDate(rs.getString("request_at"));
	            record.setCheckResult(rs.getString("io_request_type")); // 간단히 구분 저장
	            // 필요 시 location 등 추가
	            list.add(record);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}


	
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

	//상품별 총합계 테이블 조회
	public List<Stock> selectProductQuantity(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Stock> list = new ArrayList<>();
		
		con = dbManager.getConnetion();
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT p.product_id, p.product_name, p.product_code, p.product_stock, SUM(s.stock_quantity) as total"
					+ " FROM product p LEFT JOIN stock s ON p.product_id=s.product_id"
					+ " GROUP BY p.product_id, p.product_name, p.product_stock");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Stock stock = new Stock();
				Product product = new Product();
				
				stock.setStock_quantity(rs.getInt("total"));
				
				product.setProduct_id(rs.getInt("p.product_id"));
				product.setProduct_name(rs.getString("p.product_name"));
				product.setProduct_code(rs.getString("p.product_code"));
				product.setProduct_stock(rs.getInt("p.product_stock"));
				stock.setProduct(product);
				
				list.add(stock);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
	
	//카테고리 별 총 재고 수량 파악
	public List<Map<String, Integer>> selectTotalStockByCategory(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List <Map<String, Integer>> list = new ArrayList<>();
		
		con = dbManager.getConnetion();
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select c.category_name, sum(s.stock_quantity) as total_stock"
					+ " from stock s join product p on s.product_id = p.product_id"
					+ " join category c on p.category_id = c.category_id"
					+ " group by c.category_name"
					+ " having sum(s.stock_quantity)>0"
					+ " order by total_stock desc limit 10");
			pstmt = con.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Integer> map = new HashMap<>();
				map.put(rs.getString("c.category_name"), rs.getInt("total_stock"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}

		return list;
	}
	
	//재고 창고별 상품 총 수량 파악
	public List<Stock> selectTotalStockByLocation(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Stock> list = new ArrayList<>();
		
		con = dbManager.getConnetion();
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select l.location_name, sum(s.stock_quantity)"
					+ " from stock s inner join location l on s.location_id = l.location_id"
					+ " group by location_name order by sum(s.stock_quantity) desc limit 5");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Stock stock = new Stock();
				Location location = new Location();
				
				location.setLocation_name(rs.getString("l.location_name"));
				stock.setLocation(location);
				
				stock.setStock_quantity(rs.getInt("sum(s.stock_quantity)"));
				list.add(stock);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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