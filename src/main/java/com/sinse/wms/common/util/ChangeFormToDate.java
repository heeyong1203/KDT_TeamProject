package com.sinse.wms.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.sql.Date;

import javax.swing.JOptionPane;

public class ChangeFormToDate {

	// 텍스트로 입력한 날짜를 sql문 형태의 Date형으로 변환
	public static Date formChange(String dateText) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date utilDate = sdf.parse(dateText);
			Date sqlDate = new java.sql.Date(utilDate.getTime());
			return sqlDate;
		} catch (ParseException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "날짜를 yyyy-mm-dd 형태로 입력해주세요");
			return null;
		} 
	}
	
	// 현재 날짜만 반환
	public static Date nowDate(){
		return new Date(System.currentTimeMillis());
	}
}
