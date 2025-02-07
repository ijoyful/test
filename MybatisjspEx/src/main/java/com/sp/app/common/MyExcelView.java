package com.sp.app.common;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
  - 그래들(Gradle) : 엑셀 라이브러리 추가(버전 명시)
    implementation 'org.apache.poi:poi:5.4.0'
    implementation 'org.apache.poi:poi-ooxml:5.4.0'
*/

@SuppressWarnings("unchecked")
@Service("excelView")
public class MyExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String filename =(String)model.get("filename");
		String sheetName =(String)model.get("sheetName");
		
		List<String> columnLabels = (List<String>)model.get("columnLabels");
		List<Object[]> columnValues = (List<Object[]>)model.get("columnValues");
		
		// filename이 한글이면 인코딩 필요
		filename = URLEncoder.encode(filename, "utf-8");
		response.setContentType("application/ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + filename);
		
		Sheet sheet = createSheet(workbook, 0, sheetName);
		
		if(columnLabels != null) {
			createColumnLabel(sheet, columnLabels);
		}
		
		if(columnValues != null) {
			createColumnValue(sheet, columnValues);
		}
	}

	// sheet 생성
	/**
	 * @param workbook
	 * @param sheetIdx  : sheet 인덱스(0부터 시작)
	 * @param sheetName
	 * @return
	 */
	protected Sheet createSheet(Workbook workbook, int sheetIdx, String sheetName) {
		Sheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetIdx, sheetName);
		
		return sheet;
	}

	// 제목(첫번째 row)
	protected void createColumnLabel(Sheet sheet, List<String> columnLabels) {
		Row labelRow = sheet.createRow(0);
		
		Cell cell;
		for(int idx = 0; idx < columnLabels.size(); idx++) {
	         sheet.setColumnWidth(idx, 256 * 15);
	         
	         cell = labelRow.createCell(idx);
	         cell.setCellValue(columnLabels.get(idx));
	      }
	}

	// 내용(두번째 row 부터)
	protected void createColumnValue(Sheet sheet, List<Object[]> columnValues) {
		Row row;
		Cell cell;
		
		for(int idx = 0; idx < columnValues.size();idx++) {
			row = sheet.createRow(idx + 1);
			
			Object[] values = columnValues.get(idx);
			
			for(int col = 0; col<values.length;col++) {
				try {
					cell = row.createCell(col);
					
					if(values[col] instanceof Byte) cell.setCellValue((Byte)values[col]);
					else if(values[col] instanceof Short) cell.setCellValue((Short)values[col]);
					else if(values[col] instanceof Integer) cell.setCellValue((Integer)values[col]);
					else if(values[col] instanceof Long) cell.setCellValue((Long)values[col]);
					else if(values[col] instanceof Float) cell.setCellValue((Float)values[col]);
					else if(values[col] instanceof Double) cell.setCellValue((Double)values[col]);
					else if(values[col] instanceof Character) cell.setCellValue((Character)values[col]);
					else if(values[col] instanceof Boolean) cell.setCellValue((Boolean)values[col]);
					else if(values[col] instanceof String) cell.setCellValue((String)values[col]);
					else cell.setCellValue(values[col].toString()); 
					
					
				} catch (Exception e) {

				}
			}
		}
	}
}
