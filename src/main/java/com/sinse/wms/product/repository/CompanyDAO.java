package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.exception.CompanyDeleteException;
import com.sinse.wms.common.exception.CompanyInsertException;
import com.sinse.wms.common.exception.CompanySelectException;
import com.sinse.wms.common.exception.CompanyUpdateException;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Company;

public class CompanyDAO {
	DBManager dbManager = DBManager.getInstance();
	
	// comapny명으로 company 조회
	public Company findByName(String name) throws CompanySelectException{
		Company company = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM company WHERE company_name = ?";
		
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
			throw new CompanySelectException("거래처 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return company;
	}
	
	// select
	public List<Company> selectAll() throws CompanySelectException {
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
			throw new CompanySelectException("거래처 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	public List<Company> selectByNameLike(String companyName) throws CompanySelectException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Company> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM company WHERE company_name LIKE ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, companyName + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Company company = new Company();
				company.setCompany_id(rs.getInt("company_id"));
				company.setCompany_name(rs.getString("company_name"));
				list.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CompanySelectException("거래처 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// insert
	public void insert(Company company) throws CompanyInsertException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO company(company_name) VALUES(?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, company.getCompany_name());
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new CompanyInsertException("거래처 등록 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CompanyInsertException("거래처 등록 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}

	//update
	public void update(Company company) throws CompanyUpdateException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE company SET company_name = ? WHERE company_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, company.getCompany_name());
			pstmt.setInt(2, company.getCompany_id());
			int result = pstmt.executeUpdate();
			if(result == 0) {
				throw new CompanyUpdateException("거래처 업데이트 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CompanyUpdateException("거래처 업데이트 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}

	// delete
	public void delete(int companyId) throws CompanyDeleteException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM company WHERE company_id = ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, companyId);
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new CompanyDeleteException("거래처 삭제 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CompanyDeleteException("거래처 삭제 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}
}