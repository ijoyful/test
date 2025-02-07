package com.sp.app.common;

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

	}

	// sheet 생성
	/**
	 * @param workbook
	 * @param sheetIdx  : sheet 인덱스(0부터 시작)
	 * @param sheetName
	 * @return
	 */
	protected Sheet createSheet(Workbook workbook, int sheetIdx, String sheetName) {
		return null;
	}

	// 제목(첫번째 row)
	protected void createColumnLabel(Sheet sheet, List<String> columnLabels) {

	}

	// 내용(두번째 row 부터)
	protected void createColumnValue(Sheet sheet, List<Object[]> columnValues) {
	}
}
