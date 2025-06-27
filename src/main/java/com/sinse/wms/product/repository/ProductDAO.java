package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.exception.ProductDeleteException;
import com.sinse.wms.common.exception.ProductSelectException;
import com.sinse.wms.common.exception.ProductUpdateException;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Category;
import com.sinse.wms.product.model.Company;
import com.sinse.wms.product.model.Location;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.model.ProductUnit;

public class ProductDAO {
	DBManager dbManager = DBManager.getInstance();

	// 품목명으로 품목 조회
	public Product findByName(String name) {
		Product product = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT p.*, u.unit_id, u.unit_name ");		
		sql.append("FROM product p ");		
		sql.append("LEFT JOIN product_unit u ON p.unit_id = u.unit_id ");		
		sql.append("WHERE p.product_name = ?");		
		
		Connection con = dbManager.getConnetion();
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				product = new Product();
				product.setProduct_id(rs.getInt("product_id"));
				product.setProduct_code(rs.getString("product_code"));
				product.setProduct_name(rs.getString("product_name"));
				product.setProduct_description(rs.getString("product_description"));
				product.setProduct_price(rs.getInt("product_price"));
				product.setProduct_stock(rs.getInt("product_stock"));
				product.setRegdate(rs.getDate("product_regdate"));
				
				ProductUnit unit = new ProductUnit();
                unit.setUnit_id(rs.getInt("unit_id"));
                unit.setUnit_name(rs.getString("unit_name"));
                product.setUnit(unit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	// 품목명 조회
	public List<String> selectProductNames() {
		List<String> names = new ArrayList<>();
		String sql = "SELECT DISTINCT product_name FROM product";

		Connection con = dbManager.getConnetion();
		try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				names.add(rs.getString("product_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return names;
	}

	// 품목코드 조회
	public List<String> selectProductCodes() {
		List<String> codes = new ArrayList<>();
		String sql = "SELECT DISTINCT product_code FROM product";

		Connection con = dbManager.getConnetion();
		try (PreparedStatement pstmt = con.prepareStatement(sql);

				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				codes.add(rs.getString("product_code"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return codes;
	}

	// 전체 상품 조회
	public List<Product> selectAll() throws ProductSelectException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Product> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append(
					"SELECT p.product_id, p.product_code, p.product_name, p.product_description, p.product_price, p.product_stock, p.product_regdate, p.image_id, ca.category_id, ca.category_name, c.company_id, c.company_name , pu.unit_id, pu.unit_name, l.location_id , l.location_name FROM product p INNER JOIN category ca ON p.category_id = ca.category_id INNER JOIN location l ON p.location_id = l.location_id INNER JOIN product_unit pu ON p.unit_id = pu.unit_id LEFT JOIN company c ON p.company_id = c.company_id");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setProduct_id(rs.getInt("product_id"));
				p.setProduct_code(rs.getString("product_code"));
				p.setProduct_name(rs.getString("product_name"));
				p.setProduct_description(rs.getString("product_description"));
				p.setProduct_price(rs.getInt("product_price"));
				p.setProduct_stock(rs.getInt("product_stock"));
				p.setRegdate(rs.getDate("product_regdate"));
				p.setImage_id(rs.getInt("image_id"));

				// 연관 객체는 PK만 설정 (조인은 추후 확장 가능)
				Category c = new Category();
				c.setCategory_id(rs.getInt("category_id"));
				c.setCategory_name(rs.getString("category_name"));
				p.setCategory(c);

				Company comp = new Company();
				comp.setCompany_id(rs.getInt("company_id"));
				comp.setCompany_name(rs.getString("company_name"));
				p.setCompany(comp);

				Location loc = new Location();
				loc.setLocation_id(rs.getInt("location_id"));
				loc.setLocation_name(rs.getString("location_name"));
				p.setLocation(loc);

				ProductUnit u = new ProductUnit();
				u.setUnit_id(rs.getInt("unit_id"));
				u.setUnit_name(rs.getString("unit_name"));
				p.setUnit(u);

				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductSelectException("상품 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	public List<Product> selectByTableColumn(String columnName, String input) throws ProductSelectException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Product> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append(
					"SELECT p.product_id, p.product_code, p.product_name, p.product_description, p.product_price, p.product_stock, p.product_regdate, p.image_id, ca.category_id, ca.category_name, c.company_id, c.company_name , pu.unit_id, pu.unit_name, l.location_id , l.location_name FROM product p INNER JOIN category ca ON p.category_id = ca.category_id INNER JOIN location l ON p.location_id = l.location_id INNER JOIN product_unit pu ON p.unit_id = pu.unit_id LEFT JOIN company c ON p.company_id = c.company_id");
			sql.append(" WHERE ");
			sql.append(columnName);
			sql.append(" LIKE ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, input + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setProduct_id(rs.getInt("product_id"));
				p.setProduct_code(rs.getString("product_code"));
				p.setProduct_name(rs.getString("product_name"));
				p.setProduct_description(rs.getString("product_description"));
				p.setProduct_price(rs.getInt("product_price"));
				p.setProduct_stock(rs.getInt("product_stock"));
				p.setRegdate(rs.getDate("product_regdate"));
				p.setImage_id(rs.getInt("image_id"));

				// 연관 객체는 PK만 설정 (조인은 추후 확장 가능)
				Category c = new Category();
				c.setCategory_id(rs.getInt("category_id"));
				c.setCategory_name(rs.getString("category_name"));
				p.setCategory(c);

				Company comp = new Company();
				comp.setCompany_id(rs.getInt("company_id"));
				comp.setCompany_name(rs.getString("company_name"));
				p.setCompany(comp);

				Location loc = new Location();
				loc.setLocation_id(rs.getInt("location_id"));
				loc.setLocation_name(rs.getString("location_name"));
				p.setLocation(loc);

				ProductUnit u = new ProductUnit();
				u.setUnit_id(rs.getInt("unit_id"));
				u.setUnit_name(rs.getString("unit_name"));
				p.setUnit(u);

				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 상품 추가
	public void insert(Product p) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append(
					"INSERT INTO product(product_code, product_name, product_description, product_price, product_stock, category_id, company_id, location_id, unit_id, image_id) ");
			sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, p.getProduct_code());
			pstmt.setString(2, p.getProduct_name());
			pstmt.setString(3, p.getProduct_description());
			pstmt.setInt(4, p.getProduct_price());
			pstmt.setInt(5, p.getProduct_stock());
			pstmt.setInt(6, p.getCategory().getCategory_id());
			pstmt.setInt(7, p.getCompany().getCompany_id());
			pstmt.setInt(8, p.getLocation().getLocation_id());
			pstmt.setInt(9, p.getUnit().getUnit_id());
			pstmt.setInt(10, p.getImage_id());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 상품 수정
	public void update(Product p) throws ProductUpdateException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE product SET product_code = ?, product_name = ?, product_description = ?, ");
			sql.append("product_price = ?, product_stock = ?, ");
			sql.append("category_id = ?, company_id = ?, location_id = ?, unit_id = ?, image_id = ? ");
			sql.append("WHERE product_id = ?");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, p.getProduct_code());
			pstmt.setString(2, p.getProduct_name());
			pstmt.setString(3, p.getProduct_description());
			pstmt.setInt(4, p.getProduct_price());
			pstmt.setInt(5, p.getProduct_stock());
			pstmt.setInt(6, p.getCategory().getCategory_id());
			pstmt.setInt(7, p.getCompany().getCompany_id());
			pstmt.setInt(8, p.getLocation().getLocation_id());
			pstmt.setInt(9, p.getUnit().getUnit_id());
			pstmt.setInt(10, p.getImage_id());
			pstmt.setInt(11, p.getProduct_id());

			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new ProductUpdateException("상품 수정 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductUpdateException("상품 수정 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 상품 삭제
	public void delete(int productId) throws ProductDeleteException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "DELETE FROM product WHERE product_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productId);
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new ProductDeleteException("상품 삭제 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductDeleteException("상품 삭제 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}
}
