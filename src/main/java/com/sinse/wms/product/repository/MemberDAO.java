
package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Auth;
import com.sinse.wms.product.model.Dept;
import com.sinse.wms.product.model.Member;

public class MemberDAO {
	DBManager dbManager = DBManager.getInstance();

	
	//로그인 기능 구현을 위한 특정 회원 조회
	//조건 : member_email 와 memeber_Pwd가 같은 회원
	public Member selectToLogin(String id, String pwd) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Member m = null;

	    con = dbManager.getConnetion();

	    try {
	        String sql = "SELECT * FROM member WHERE member_email = ? AND member_password = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, id);
	        pstmt.setString(2, pwd);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            m = new Member();
	            m.setMember_id(rs.getInt("member_id"));
	            m.setMember_email(rs.getString("member_email"));
	            m.setMember_password(rs.getString("member_password"));
	            m.setMember_name(rs.getString("member_name"));
	            m.setMemberhiredate(rs.getDate("member_hiredate"));

	            Dept d = new Dept();
	            d.setDept_id(rs.getInt("dept_id"));
	            m.setDept(d);

	            Auth a = new Auth();
	            a.setAuth_id(rs.getInt("auth_id"));
	            m.setAuth(a);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        dbManager.release(pstmt, rs);
	    }

	    return m;
	}
	

	// 전체 회원 조회
	public List<Member> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Member> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			String sql = "SELECT * FROM member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Member m = new Member();
				m.setMember_id(rs.getInt("member_id"));
				m.setMember_password(rs.getString("member_password"));
				m.setMember_email(rs.getString("member_email"));
				m.setMember_name(rs.getString("member_name"));
				m.setMemberhiredate(rs.getDate("memberhiredate"));

				Dept d = new Dept();
				d.setDept_id(rs.getInt("dept_id"));
				m.setDept(d);

				Auth a = new Auth();
				a.setAuth_id(rs.getInt("auth_id"));
				m.setAuth(a);

				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 회원 추가
	public void insert(Member m) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "INSERT INTO member(member_password, member_email, member_name, memberhiredate, dept_id, auth_id) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getMember_password());
			pstmt.setString(2, m.getMember_email());
			pstmt.setString(3, m.getMember_name());
			pstmt.setDate(4, m.getMemberhiredate());
			pstmt.setInt(5, m.getDept().getDept_id());
			pstmt.setInt(6, m.getAuth().getAuth_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 회원 수정
	public void update(Member m) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "UPDATE member SET member_password = ?, member_email = ?, member_name = ?, memberhiredate = ?, dept_id = ?, auth_id = ? WHERE member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getMember_password());
			pstmt.setString(2, m.getMember_email());
			pstmt.setString(3, m.getMember_name());
			pstmt.setDate(4, m.getMemberhiredate());
			pstmt.setInt(5, m.getDept().getDept_id());
			pstmt.setInt(6, m.getAuth().getAuth_id());
			pstmt.setInt(7, m.getMember_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 회원 삭제
	public void delete(int memberId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "DELETE FROM member WHERE member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
	}
}
