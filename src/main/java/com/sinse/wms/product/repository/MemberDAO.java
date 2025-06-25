
package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.exception.MemberDeleteException;
import com.sinse.wms.common.exception.MemberInsertException;
import com.sinse.wms.common.exception.MemberUpdateException;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Auth;
import com.sinse.wms.product.model.Dept;
import com.sinse.wms.product.model.JobGrade;
import com.sinse.wms.product.model.Member;

public class MemberDAO {
	DBManager dbManager = DBManager.getInstance();

	// 로그인 기능 구현을 위한 특정 회원 조회
	// 조건 : member_email 와 memeber_Pwd가 같은 회원
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
			String sql = "SELECT m.member_id, m.member_password, m.member_email, m.member_name, m.member_hiredate, m.dormant, d.dept_id, d.dept_name, a.auth_id, a.auth_name, a.auth_flag, j.job_grade_id, j.job_grade_name FROM member m INNER JOIN dept d ON m.dept_id=d.dept_id INNER JOIN auth a ON m.auth_id=a.auth_id INNER JOIN job_grade j ON m.job_grade_id=j.job_grade_id";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Member m = new Member();
				m.setMember_id(rs.getInt("member_id"));
				m.setMember_password(rs.getString("member_password"));
				m.setMember_email(rs.getString("member_email"));
				m.setMember_name(rs.getString("member_name"));
				m.setMemberhiredate(rs.getDate("member_hiredate"));
				m.setDormant(rs.getBoolean("dormant"));
				Dept d = new Dept();
				d.setDept_id(rs.getInt("dept_id"));
				d.setDept_name(rs.getString("dept_name"));
				m.setDept(d);

				Auth a = new Auth();
				a.setAuth_id(rs.getInt("auth_id"));
				a.setAuth_name(rs.getString("auth_name"));
				a.setAuth_flag(rs.getInt("auth_flag"));
				m.setAuth(a);

				JobGrade j = new JobGrade();
				j.setJobGradeId(rs.getInt("job_grade_id"));
				j.setJobGradeName(rs.getString("job_grade_name"));
				m.setJobGrade(j);

				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 검색 조회
	public List<Member> selectByNameOrEmail(String keyword) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Member> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			String sql = "SELECT m.member_id, m.member_password, m.member_email, m.member_name, m.member_hiredate, m.dormant, d.dept_id, d.dept_name, a.auth_id, a.auth_name, a.auth_flag, j.job_grade_id, j.job_grade_name FROM member m INNER JOIN dept d ON m.dept_id=d.dept_id INNER JOIN auth a ON m.auth_id=a.auth_id INNER JOIN job_grade j ON m.job_grade_id=j.job_grade_id WHERE m.member_name LIKE ? OR m.member_email LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Member m = new Member();
				m.setMember_id(rs.getInt("member_id"));
				m.setMember_password(rs.getString("member_password"));
				m.setMember_email(rs.getString("member_email"));
				m.setMember_name(rs.getString("member_name"));
				m.setMemberhiredate(rs.getDate("member_hiredate"));
				m.setDormant(rs.getBoolean("dormant"));
				Dept d = new Dept();
				d.setDept_id(rs.getInt("dept_id"));
				d.setDept_name(rs.getString("dept_name"));
				m.setDept(d);

				Auth a = new Auth();
				a.setAuth_id(rs.getInt("auth_id"));
				a.setAuth_name(rs.getString("auth_name"));
				a.setAuth_flag(rs.getInt("auth_flag"));
				m.setAuth(a);

				JobGrade j = new JobGrade();
				j.setJobGradeId(rs.getInt("job_grade_id"));
				j.setJobGradeName(rs.getString("job_grade_name"));
				m.setJobGrade(j);

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
	public void insert(Member m) throws MemberInsertException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();
		try {
			String sql = "INSERT INTO member(member_password, member_email, member_name, dept_id, auth_id, job_grade_id) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getMember_password());
			pstmt.setString(2, m.getMember_email());
			pstmt.setString(3, m.getMember_name());
			pstmt.setInt(4, m.getDept().getDept_id());
			pstmt.setInt(5, m.getAuth().getAuth_id());
			pstmt.setInt(6, m.getJobGrade().getJobGradeId());
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new MemberInsertException("멤버 등록 실패 result : 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MemberInsertException("멤버 등록 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 회원 수정
	public void update(Member m) throws MemberUpdateException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append(
					"UPDATE member SET member_email = ?, member_name = ?, memberhiredate = ?, dept_id = ?, auth_id = ? ");
			if (m.getMember_password().length() != 0) {
				sql.append(", member_password = ? ");
			}
			sql.append("WHERE member_id = ?");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, m.getMember_email());
			pstmt.setString(2, m.getMember_name());
			pstmt.setDate(3, m.getMemberhiredate());
			pstmt.setInt(4, m.getDept().getDept_id());
			pstmt.setInt(5, m.getAuth().getAuth_id());
			if (m.getMember_password().length() != 0) {
				pstmt.setString(6, m.getMember_password());
				pstmt.setInt(7, m.getMember_id());
			} else {
				pstmt.setInt(6, m.getMember_id());
			}
			int result = pstmt.executeUpdate();
			if(result == 0) {
				throw new MemberUpdateException("멥버 수정 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MemberUpdateException("멤버 수정 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}

	// 회원 삭제
	public void delete(int memberId) throws MemberDeleteException {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnetion();

		try {
			String sql = "DELETE FROM member WHERE member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			int result = pstmt.executeUpdate();
			if(result == 0) {
				throw new MemberDeleteException("멤버 삭제 실패 result 0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MemberDeleteException("멤버 삭제 실패", e);
		} finally {
			dbManager.release(pstmt);
		}
	}
}
