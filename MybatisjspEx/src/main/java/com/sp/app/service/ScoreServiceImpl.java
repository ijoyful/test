package com.sp.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sp.app.mapper.ScoreMapper;
import com.sp.app.model.Score;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreServiceImpl implements ScoreService {
	private final ScoreMapper scoreMapper;
	
	@Override
	public void insertScore(Score dto) throws Exception {
		try {
			scoreMapper.insertScore(dto);
		} catch (Exception e) {
			log.info("insertScore : ", e);
			
			throw e;
		}
		
	}

	@Override
	public void updateScore(Score dto) throws Exception {
		try {
			scoreMapper.updateScore(dto);
		} catch (Exception e) {
			log.info("updateScore : ", e);

			throw e;
		}
	}

	@Override
	public void deleteScore(String hak) throws Exception {
		try {
			scoreMapper.deleteScore(hak);
		} catch (Exception e) {
			log.info("deleteScore : ", e);

			throw e;
		}
	}

	@Override
	public void deleteScore(List<String> list) throws Exception {
		try {
			scoreMapper.deleteListScore(list);
		} catch (Exception e) {
			log.info("deleteScore(다중삭제) : ", e);

			throw e;
		}
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = scoreMapper.dataCount(map);
		} catch (Exception e) {
			log.info("dataCount : ", e);
		}
		
		return result;
	}

	@Override
	public List<Score> listScore(Map<String, Object> map) {
		List<Score> list = null;
		
		try {
			list = scoreMapper.listScore(map);
		} catch (Exception e) {
			log.info("listScore : ", e);
		}
		
		return list;
	}

	@Override
	public Score findById(String hak) {
		Score dto = null;
		try {
			dto = scoreMapper.findById(hak);
		} catch (Exception e) {
			log.info("findById : ", e);
		}

		return dto;
	}

	@Override
	public List<Score> findByAll() {
		List<Score> list = null;
		
		try {
			list = scoreMapper.findByAll();
		} catch (Exception e) {
			log.info("findByAll : ", e);
		}
		
		return list;
	}
}
