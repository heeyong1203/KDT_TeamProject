package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.common.exception.JobGradeSelectAllException;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.JobGrade;

public class JobGradeDAO {
	private DBManager dbManager = DBManager.getInstance();

	public List<JobGrade> selectAll() throws JobGradeSelectAllException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<JobGrade> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM job_grade");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JobGrade jobGrade = new JobGrade();
				jobGrade.setJobGradeId(rs.getInt("job_grade_id"));
				jobGrade.setJobGradeName(rs.getString("job_grade_name"));
				list.add(jobGrade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
            throw new JobGradeSelectAllException("직급 조회 실패", e);
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

}
