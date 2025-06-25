package com.sinse.wms.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinse.wms.common.Config;

public class DBManager {

    private static DBManager instance;

    private DBManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    // 💡 매번 새로운 커넥션을 생성
    public Connection getConnetion() {
		return con;
	}


    // 💨 DML용 release
    public void release(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 💨 SELECT용 release
    public void release(PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        release(pstmt);
    }

    // 💨 전체 해제
    public void release(Connection con, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close(); // 💥 여기서만 커넥션 닫기
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void release(Connection con) {
        if (con != null) {
            try {
                con.close(); // 💥 꼭 닫아야 누수 방지됨
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
