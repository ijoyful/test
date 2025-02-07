package com.sp.app.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sp.app.model.Score;

@Mapper
public interface ScoreMapper {
	public void insertScore(Score dto) throws SQLException;
	public void updateScore(Score dto) throws SQLException;
	public void deleteScore(String hak) throws SQLException;
	public void deleteListScore(List<String> list) throws SQLException;
	
	public int dataCount(Map<String, Object> map);
	public List<Score> listScore(Map<String, Object> map);
	public Score findById(String hak);
	public List<Score> findByAll();
}
