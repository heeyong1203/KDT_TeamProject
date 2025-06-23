package com.sinse.wms.common.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileExport {
	/*------------------------------------------------------------------------
	 * 엑셀 export하기
	 *	엑셀 파일로 현재 보고 있는 table을 export한다.
		사용법: ExcelFileExport.exportToExcel(String 배열 형식의 컬럼, tableModel, 확장자를 뺀 경로);
	 * ------------------------------------------------------------------------ */
	public static String exportToExcel(String[] title, TableModel tableModel, String path) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("download");
		String resultMsg = null;
		
		//헤더 행 생성
		Row headerRow = sheet.createRow(0);
		for(int i=0; i<title.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(title[i]);
		}
		
		//데이터 추가
		for(int rowIdx=0; rowIdx<tableModel.getRowCount(); rowIdx++) {
			Row row = sheet.createRow(rowIdx+1);
			for(int colIdx=0; colIdx<tableModel.getColumnCount(); colIdx++) {
				row.createCell(colIdx).setCellValue(tableModel.getValueAt(rowIdx, colIdx).toString());
			}
		}
		
		String excelFilePath = UniqueFileName.getUniqueFileName(path, "xlsx");
				
		//파일 다운받기
		try (FileOutputStream fo = new FileOutputStream(excelFilePath)){
			workbook.write(fo);
			workbook.close();
			resultMsg = "엑셀 파일 저장 완료!";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			resultMsg = "엑셀 파일을 저장할 수 없습니다.";
		} catch (IOException e) {
			e.printStackTrace();
			resultMsg = "엑셀 파일을 저장할 수 없습니다.";
		}
		return resultMsg;
	}
	
	/*------------------------------------------------------------------------
	 * 엑셀 export하기
	 *	엑셀 파일로 현재 보고 있는 table을 export한다.
		사용법: ExcelFileExport.exportToExcel(String> 리스트 형식의 컬럼, tableModel, 확장자를 뺀 경로);
	 * ------------------------------------------------------------------------ */
	public static String exportToExcel(List<String> title, TableModel tableModel, String path) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("download");
		String resultMsg = null;
		
		//헤더 행 생성
		Row headerRow = sheet.createRow(0);
		for(int i=0; i<title.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(title.get(i));
		}
		
		//데이터 추가
		for(int rowIdx=0; rowIdx<tableModel.getRowCount(); rowIdx++) {
			Row row = sheet.createRow(rowIdx+1);
			for(int colIdx=0; colIdx<tableModel.getColumnCount(); colIdx++) {
				row.createCell(colIdx).setCellValue(tableModel.getValueAt(rowIdx, colIdx).toString());
			}
		}
		
		String excelFilePath = UniqueFileName.getUniqueFileName(path, "xlsx");
				
		//파일 다운받기
		try (FileOutputStream fo = new FileOutputStream(excelFilePath)){
			workbook.write(fo);
			workbook.close();
			resultMsg = "엑셀 파일 저장 완료!";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			resultMsg = "엑셀 파일을 저장할 수 없습니다.";
		} catch (IOException e) {
			e.printStackTrace();
			resultMsg = "엑셀 파일을 저장할 수 없습니다.";
		}
		return resultMsg;
	}
	
	//동적테이블 용 pdf 추출
	public static String exportToExcel(List<String> title, DefaultTableModel tableModel, String path) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("download");
		String resultMsg = null;
		
		//헤더 행 생성
		Row headerRow = sheet.createRow(0);
		for(int i=0; i<title.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(title.get(i));
		}
		
		//데이터 추가
		for(int rowIdx=0; rowIdx<tableModel.getRowCount(); rowIdx++) {
			Row row = sheet.createRow(rowIdx+1);
			for(int colIdx=0; colIdx<tableModel.getColumnCount(); colIdx++) {
				row.createCell(colIdx).setCellValue(tableModel.getValueAt(rowIdx, colIdx).toString());
			}
		}
		
		String excelFilePath = UniqueFileName.getUniqueFileName(path, "xlsx");
				
		//파일 다운받기
		try (FileOutputStream fo = new FileOutputStream(excelFilePath)){
			workbook.write(fo);
			workbook.close();
			resultMsg = "엑셀 파일 저장 완료!";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			resultMsg = "엑셀 파일을 저장할 수 없습니다.";
		} catch (IOException e) {
			e.printStackTrace();
			resultMsg = "엑셀 파일을 저장할 수 없습니다.";
		}
		return resultMsg;
	}
}
