package com.sinse.wms.common.util;

import java.io.File;

public class UniqueFileName {
	
	/*------------------------------------------------------------------------
	 * 유일한 파일 이름 지정
	 * 
	 * 설명: Excel 및 Pdf 파일 저장 시 저장하려는 디렉토리에 같은 파일이 있으면 덮어씌워지므로
	 *        파일 맨 뒤에 숫자를 붙여 유니크한 파일 이름을 생성한다.
	 * 사용방법: String fileName = UniqueFileName.getUniqueFileName("파일 디렉토리", "파일 이름", "확장자");
	 * ------------------------------------------------------------------------ */
	public static String getUniqueFileName(String directory, String baseName, String extension) {
		int count = 1;
		File file = new File(directory, baseName +"."+ extension);
		
		while(file.exists()) {		//만약 파일이 존재하면 번호 증가
			file = new File(directory, baseName+"("+count+")"+"."+extension);
			count++;
		}
		return file.getAbsolutePath();
	}
	
	
	/*------------------------------------------------------------------------ 
	 * 유일한 파일 이름 지정
	 * 설명: Excel 및 Pdf 파일 저장 시 저장하려는 디렉토리에 같은 파일이 있으면 덮어씌워지므로
	 *        파일 맨 뒤에 숫자를 붙여 유니크한 파일 이름을 생성한다.
	 * 사용방법: String fileName = UniqueFileName.getUniqueFileName("이름을 포함한 경로", "확장자"); 
	 *------------------------------------------------------------------------  */
	public static String getUniqueFileName(String path, String extension) {
		int count = 1;
		File file = new File(path+"."+ extension);
		
		while(file.exists()) {
			file = new File(path+"("+count+")"+"."+extension);
			count++;
		}
		return file.getAbsolutePath();
	}
}
