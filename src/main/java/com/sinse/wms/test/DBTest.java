package com.sinse.wms.test;

import java.sql.Connection;

import com.sinse.wms.common.util.DBManager;

public class DBTest {
	public static void main(String[] args) {
		Connection con = DBManager.getInstance().getConnetion();
		if (con != null) {
			System.out.println("DB 연결 성공!");
		} else {
			System.out.println("DB 연결 실패...");
		}
	}
}
