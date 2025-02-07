package com.sp.app.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import com.sp.app.common.MyExcelView;
import com.sp.app.common.PaginateUtil;
import com.sp.app.model.Score;
import com.sp.app.pdf.ScorePdfView;
import com.sp.app.service.ScoreService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/escore/*")
public class EScoreController {
	private final ScoreService service;
	private final PaginateUtil paginateUtil;
	private final MyExcelView excelView;
	
	@GetMapping(value = "main")
	public String handleList(
			@RequestParam(name = "page", defaultValue = "1") int current_page,
			@RequestParam(name = "schType", defaultValue = "hak") String schType,
			@RequestParam(name = "kwd", defaultValue = "") String kwd,
			Model model,
			HttpServletRequest req) throws Exception {

		try {
			kwd = URLDecoder.decode(kwd, "utf-8");
			
			int size = 10;
			int dataCount, total_page;
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schType", schType);
			map.put("kwd", kwd);
			
			dataCount = service.dataCount(map);
			total_page = paginateUtil.pageCount(dataCount, size);
			
			current_page = Math.min(current_page, total_page);
			
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			map.put("offset", offset);
			map.put("size", size);
			
			List<Score> list = service.listScore(map);
			
			String cp = req.getContextPath();
			String listUrl = cp + "/escore/main";
			
			if(! kwd.isBlank()) {
				listUrl += "?schType=" + schType + "&kwd="
						+ URLEncoder.encode(kwd, "utf-8");
			}
			
			String paging = paginateUtil.paging(current_page,
					total_page, listUrl);
			
			model.addAttribute("list", list);
			model.addAttribute("dataCount", dataCount);
			model.addAttribute("page", current_page);
			model.addAttribute("total_page", total_page);
			model.addAttribute("size", size);
			model.addAttribute("paging", paging);
			
			model.addAttribute("schType", schType);
			model.addAttribute("kwd", kwd);
			
		} catch (Exception e) {
			log.info("handleList : ", e);
		}
		
		return "escore/list";
	}

	@GetMapping("write")
	public String handleWriteForm(Model model) throws Exception {
		
		model.addAttribute("mode", "write");
		
		return "escore/write";
	}
	
	@PostMapping("write")
	public String handleWriteSubmit(Score dto, Model model) throws Exception {
		try {
			service.insertScore(dto);
			
			return "redirect:/escore/main";
		} catch (DuplicateKeyException e) {
			// 기본키 중복에 의한 제약 조건 예외
			model.addAttribute("message", "학번 중복으로 등록이 실패했습니다.");
		} catch (DataIntegrityViolationException e) {
			// 데이터형식 오류, 참조키, NOT NULL 등의 제약 조건 위반
			model.addAttribute("message", "제약 조건 위반으로 등록이 실패 했습니다.");
		} catch (Exception e) {
			model.addAttribute("message", "등록이 실패 했습니다.");
		}

		model.addAttribute("mode", "write");
		return "escore/write";
	}

	@GetMapping("update")
	public String handleUpdateForm(@RequestParam(name = "hak") String hak, 
			@RequestParam(name = "page") String page, 
			Model model) throws Exception {
		
		try {
			Score dto = Objects.requireNonNull(service.findById(hak));
			
			model.addAttribute("dto", dto);
			model.addAttribute("page", page);
			model.addAttribute("mode", "update");

			return "escore/write";
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/escore/main?page=" + page;
	}

	@PostMapping("update")
	public String handleUpdateSubmit(Score dto, 
			@RequestParam(name = "page") String page) throws Exception {

		try {
			service.updateScore(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/escore/main?page=" + page;
	}

	@GetMapping("delete")
	public String handleDelete(@RequestParam(name = "hak") String hak, 
			@RequestParam(name = "page") String page) throws Exception {

		try {
			service.deleteScore(hak);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/escore/main?page=" + page;
	}
	
	@GetMapping("excel")
	public View excel(Map<String, Object> model) throws Exception {
		List<Score> list = service.findByAll();
		
		String sheetName = "성적처리";
		List<String> columnLabels = new ArrayList<String>();
		List<Object[]> columnValues = new ArrayList<Object[]>();
		
		columnLabels.add("학번");
		columnLabels.add("이름");
		columnLabels.add("국어");
		columnLabels.add("영어");
		columnLabels.add("수학");
		columnLabels.add("총점");
		columnLabels.add("평균");
		
		for(Score dto : list) {
			columnValues.add(new Object[]{dto.getHak(), dto.getName(), 
					dto.getKor(), dto.getEng(), dto.getMat(), 
					dto.getTot(), dto.getAve()});
		}
		
		model.put("filename", "score.xlsx"); // 저장할 파일 이름
		model.put("sheetName", sheetName); // 시트이름
		model.put("columnLabels", columnLabels); // 타이틀
		model.put("columnValues", columnValues); // 값
		
		return excelView;  // 엑셀 파일 다운 로드
	}
	
	@GetMapping("pdf")
	public View pdf(Map<String, Object> model) throws Exception {
		List<Score> list = service.findByAll();
		
		List<String> columnLabels = new ArrayList<String>();
		List<Object[]> columnValues = new ArrayList<Object[]>();
		
		columnLabels.add("학번");
		columnLabels.add("이름");
		columnLabels.add("국어");
		columnLabels.add("영어");
		columnLabels.add("수학");
		columnLabels.add("총점");
		columnLabels.add("평균");
		
		for(Score dto : list) {
			columnValues.add(new Object[]{dto.getHak(), dto.getName(), 
					dto.getKor(), dto.getEng(), dto.getMat(), 
					dto.getTot(), dto.getAve()});
		}
		
		model.put("filename", "score.pdf"); // 저장할 파일 이름
		model.put("columnLabels", columnLabels); // 타이틀
		model.put("columnValues", columnValues); // 값
		
		return new ScorePdfView();
	}
	
	@GetMapping("print")
	public String print(Model model) throws Exception {
		List<Score> list = service.findByAll();
		
		model.addAttribute("list", list);
		
		return "escore/print";
	}
	

}
