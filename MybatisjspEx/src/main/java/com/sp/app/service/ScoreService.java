package com.sp.app.service;

import java.util.List;
import java.util.Map;

import com.sp.app.model.Score;

public interface ScoreService {
	public void insertScore(Score dto) throws Exception;
	public void updateScore(Score dto) throws Exception;
	public void deleteScore(String hak) throws Exception;
	public void deleteScore(List<String> list) throws Exception;
	
	public int dataCount(Map<String, Object> map);
	public List<Score> listScore(Map<String, Object> map);
	public Score findById(String hak);
	public List<Score> findByAll();
}
