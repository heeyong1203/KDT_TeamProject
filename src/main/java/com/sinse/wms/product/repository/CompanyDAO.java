package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Company;
import com.sinse.wms.product.model.Member;

public class CompanyDAO {
	DBManager dbManager = DBManager.getInstance();
	
	// comapny명으로 company 조회
	public Company findByName(String name) {
		Company company = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM Company WHERE company_name = ?";
		
		Connection con = dbManager.getConnetion();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				company.setCompany_id(rs.getInt("company_id"));
				company.setCompany_name(rs.getString("company_name"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}  
	
	// select
	public List<Company> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Company> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM company");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Company company = new Company();
				company.setCompany_id(rs.getInt("company_id"));
				company.setCompany_name(rs.getString("company_name"));
				list.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// insert
	public void insert(Company company) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO company(company_name) VALUES(?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, company.getCompany_name());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	//update
	public void update(Company company) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE company SET company_name = ? WHERE company_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, company.getCompany_name());
			pstmt.setInt(2, company.getCompany_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// delete
	public void delete(int companyId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM company WHERE company_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, companyId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}
}