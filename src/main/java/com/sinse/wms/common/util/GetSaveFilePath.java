package com.sinse.wms.common.util;

import java.io.File;

import javax.swing.JFileChooser;

public class GetSaveFilePath {
	/*------------------------------------------------------------------------
	 * 파일 다운로드 시 사용자가 어디에 문서를 다운받을건지 JFileChooser를 띄워서 경로 받기
		사용법: String path = GetSaveFilePath.saveFilePath();
	 * ------------------------------------------------------------------------ */
	public static String saveFilePath() {
    	JFileChooser fileChooser = new JFileChooser();
    	
    	String userHome = System.getProperty("user.home");
    	File downloadDir = new File(userHome, "Downloads");
    	fileChooser.setCurrentDirectory(downloadDir);
    	
    	fileChooser.setDialogTitle("파일 저장 위치 선택");
    	int userSelection = fileChooser.showSaveDialog(null);

    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    	    File fileToSave = fileChooser.getSelectedFile();
    	    return fileToSave.getAbsolutePath();
    	}
    	return null;
    }
}
