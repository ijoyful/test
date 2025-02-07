package com.sp.app.pdf;

import java.awt.Color;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
- 그래들(Gradle) : PDF 라이브러리 추가, AbstractPdfView 는 2.1.7 만 지원
  implementation 'com.lowagie:itext:2.1.7'
*/

public class ScorePdfView extends AbstractPdfView {
	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String filename =(String)model.get("filename");
		
		List<String> columnLabels = (List<String>)model.get("columnLabels");
		List<Object[]> columnValues = (List<Object[]>)model.get("columnValues");
		
		// filename이 한글이면 인코딩 필요
		filename = URLEncoder.encode(filename, "utf-8");
		response.setContentType("application/ms-pdf");
		response.setHeader("Content-disposition", "attachment; filename=" + filename);
		
		BaseFont baseFont = BaseFont.createFont(
				"c:\\windows\\fonts\\malgun.ttf",
				BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
		
		Font font = new Font(baseFont);
		font.setSize(20);
		font.setStyle(Font.BOLD);
		
		// 문장 표현
		Paragraph p = new Paragraph("성처적리", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		
		font.setSize(10);
		font.setStyle(Font.NORMAL);
		
		PdfPTable table= new PdfPTable(columnLabels.size());
		table.setWidths(new int[] {50, 100, 80, 80, 80, 80, 80});
		table.setSpacingBefore(15);
		
		PdfPCell cell;
		for(int  i = 0; i<columnLabels.size(); i++) {
			cell = new PdfPCell(new Paragraph(columnLabels.get(i), font));
			cell.setBackgroundColor(new Color(211,244,250));
			cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
			cell.setFixedHeight(25);
			cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
			
			table.addCell(cell);
		}
		
		for(int i = 0; i < columnValues.size(); i++) {
			Object[] values = columnValues.get(i);
			
			for(int col = 0; col < values.length; col ++) {
				cell = new PdfPCell(new Paragraph(values[col].toString(), font));
			}
		}
		
		document.add(table);
	}
}
