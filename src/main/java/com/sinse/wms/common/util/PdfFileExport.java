package com.sinse.wms.common.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfFileExport {
	/*------------------------------------------------------------------------
	 * pdf 파일 다운로드
	 *	pdf 파일로 현재 보고 있는 table을 export한다.
		사용법: PdfFileExport.exportToPdf(String 형식의 컬럼배열, tableModel, path, pdf 내용 상단에 들어갈 제목)
	 * ------------------------------------------------------------------------ */
	public static String exportToPdf(String[] title, TableModel tableModel, String path, String pageTitle) {
		String pdfFilePath = UniqueFileName.getUniqueFileName(path, "pdf");
		String resultMsg = null;
		
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
			document.open();
			
			//한글 설정
			String fontPath = "C:/Windows/Fonts/malgun.ttf";
			BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font koreanFont = new Font(baseFont, 12, Font.NORMAL);

			
			//제목 스타일 지정
			Paragraph pagetitle = new Paragraph(pageTitle, koreanFont);
			pagetitle.setAlignment(Element.ALIGN_CENTER);
			document.add(pagetitle);
			document.add(new Paragraph("\n"));
			
			//테이블 생성
			PdfPTable table = new PdfPTable(title.length);
			
			//헤더 추가
			for(int i=0; i<title.length; i++) {
				String column = title[i];
				
				PdfPCell headerCell = new PdfPCell(new Phrase(column, koreanFont));
				headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(headerCell);
			}
			
			//데이터 추가
			for(int rowIdx=0; rowIdx<tableModel.getRowCount(); rowIdx++) {
				for(int colIdx=0; colIdx<tableModel.getColumnCount(); colIdx++) {
					table.addCell(tableModel.getValueAt(rowIdx, colIdx).toString());
				}
			}
			document.add(table);
			document.close();
			resultMsg = "Pdf 파일이 저장되었습니다.";
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			resultMsg = "Pdf 파일을 저장할 수 없습니다.";
		} catch (DocumentException e) {
			e.printStackTrace();
			resultMsg = "Pdf 파일을 저장할 수 없습니다.";
		} catch (IOException e) {
			e.printStackTrace();
			resultMsg = "Pdf 파일을 저장할 수 없습니다.";
		}
		
		return resultMsg;
	}
	
	/*------------------------------------------------------------------------
	 * pdf 파일 다운로드
	 *	pdf 파일로 현재 보고 있는 table을 export한다.
		사용법: PdfFileExport.exportToPdf(String 형식의 컬럼리스트, tableModel, path, pdf 내용 상단에 들어갈 제목)
	 * ------------------------------------------------------------------------ */
	public static String exportToPdf(List<String> title, TableModel tableModel, String path, String pageTitle) {
		String pdfFilePath = UniqueFileName.getUniqueFileName(path, "pdf");
		String resultMsg = null;
		
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
			document.open();
			
			//한글 설정
			String fontPath = "C:/Windows/Fonts/malgun.ttf";
			BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font koreanFont = new Font(baseFont, 12, Font.NORMAL);
			
			//제목 스타일 지정
			Paragraph pagetitle = new Paragraph(pageTitle, koreanFont);
			pagetitle.setAlignment(Element.ALIGN_CENTER);
			document.add(pagetitle);
			document.add(new Paragraph("\n"));
			
			//테이블 생성
			PdfPTable table = new PdfPTable(title.size());
			
			//헤더 추가
			for(int i=0; i<title.size(); i++) {
				String column = title.get(i);
				
				PdfPCell headerCell = new PdfPCell(new Phrase(column, koreanFont));
				headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(headerCell);
			}
			
			//데이터 추가
			for(int rowIdx=0; rowIdx<tableModel.getRowCount(); rowIdx++) {
				for(int colIdx=0; colIdx<tableModel.getColumnCount(); colIdx++) {
					String cellValue = tableModel.getValueAt(rowIdx, colIdx).toString();
			        PdfPCell dataCell = new PdfPCell(new Phrase(cellValue, koreanFont)); // 폰트 적용!
			        table.addCell(dataCell);
				}
			}
			document.add(table);
			document.close();
			resultMsg = "Pdf 파일이 저장되었습니다.";
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			resultMsg = "Pdf 파일을 저장할 수 없습니다.";
		} catch (DocumentException e) {
			e.printStackTrace();
			resultMsg = "Pdf 파일을 저장할 수 없습니다.";
		} catch (IOException e) {
			e.printStackTrace();
			resultMsg = "Pdf 파일을 저장할 수 없습니다.";
		}
		
		return resultMsg;
	}
	
	
	//동적 테이블 용 pdf 출력
	public static String exportToPdf(String[] title, DefaultTableModel tableModel, String path, String pageTitle) {
		String pdfFilePath = UniqueFileName.getUniqueFileName(path, "pdf");
		String resultMsg = null;
		
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
			document.open();
			
			//한글 설정
			String fontPath = "C:/Windows/Fonts/malgun.ttf";
			BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font koreanFont = new Font(baseFont, 12, Font.NORMAL);

			
			//제목 스타일 지정
			Paragraph pagetitle = new Paragraph(pageTitle, koreanFont);
			pagetitle.setAlignment(Element.ALIGN_CENTER);
			document.add(pagetitle);
			document.add(new Paragraph("\n"));
			
			//테이블 생성
			PdfPTable table = new PdfPTable(title.length);
			
			//헤더 추가
			for(int i=0; i<title.length; i++) {
				String column = title[i];
				
				PdfPCell headerCell = new PdfPCell(new Phrase(column, koreanFont));
				headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(headerCell);
			}
			
			//데이터 추가
			for(int rowIdx=0; rowIdx<tableModel.getRowCount(); rowIdx++) {
				for(int colIdx=0; colIdx<tableModel.getColumnCount(); colIdx++) {
					table.addCell(tableModel.getValueAt(rowIdx, colIdx).toString());
				}
			}
			document.add(table);
			document.close();
			resultMsg = "Pdf 파일이 저장되었습니다.";
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			resultMsg = "Pdf 파일을 저장할 수 없습니다.";
		} catch (DocumentException e) {
			e.printStackTrace();
			resultMsg = "Pdf 파일을 저장할 수 없습니다.";
		} catch (IOException e) {
			e.printStackTrace();
			resultMsg = "Pdf 파일을 저장할 수 없습니다.";
		}
		
		return resultMsg;
	}
}
