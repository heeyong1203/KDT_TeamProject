package com.sinse.wms.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinse.wms.common.Config;

public class DBManager {

	private static DBManager instance;
	
	private Connection con;
	
	
	private DBManager() {
		try {
			
	
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(Config.URL, Config.USER, Config.PWD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DBManager getInstance() {
		if(instance==null) {
			instance = new DBManager();
		}
		return instance;
	}

	public Connection getConnetion() {
		return con;
	}
	
	
	public void release(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void release(PreparedStatement pstmt) { //DML (insert ,update, delete)
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	public void release(PreparedStatement pstmt, ResultSet rs) { //select
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}	
	public void release(Connection con,  PreparedStatement pstmt, ResultSet rs) { 
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}	
}

