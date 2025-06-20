package com.sinse.wms.common.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sinse.wms.common.util.DBManager;

public class MemberFinder {

	/**
	 * 이름, 이메일, 부서명이 일치하는 회원의 아이디(이메일)를 반환
	 * 
	 * @param name  이름
	 * @param email 이메일
	 * @param dept  부서명
	 * @return 일치하는 회원의 이메일 (아이디 역할), 없으면 null
	 */
	public static String findMemberId(String name, String dept) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DBManager db = DBManager.getInstance();
		String foundEmail = null;

		try {
			con = db.getConnetion();
			String sql = "SELECT member_email FROM member WHERE member_name=? AND dept_id = (SELECT dept_id FROM dept WHERE dept_name=?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, dept);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				foundEmail = rs.getString("member_email");
			}

		} catch (Exception e) {
			e.printStackTrace(); // 혹은 로깅 처리
		} finally {
			db.release(pstmt, rs);
		}

		return foundEmail;
	}

	public static String fineMemberPwd(String name, String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DBManager db = DBManager.getInstance();
		String foundPwd = null;
		try {
			con = db.getConnetion();
			String sql = "SELECT member_password FROM member WHERE member_name=? AND member_email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				foundPwd = rs.getString("member_password");
			}

		} catch (Exception e) {
			e.printStackTrace(); // 혹은 로깅 처리
		} finally {
			db.release(pstmt, rs);
		}

		return foundPwd;
	}
}
