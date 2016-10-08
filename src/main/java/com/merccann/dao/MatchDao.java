package com.merccann.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.merccann.League;
import com.merccann.dao.mapper.MatchDataMapper;
import com.merccann.dao.mapper.PredictionDataMapper;
import com.merccann.dto.MatchAndPredictionDTO;
import com.merccann.dto.MatchDTO;

import lombok.Setter;

public class MatchDao extends Dao{
	
	@Setter
	@Autowired
	private MatchDataMapper dataMapper; 

	public MatchDTO getMatchById(String matchId) {
		return getSingleResultById(dataMapper.getMatchById(matchId), matchId);
	}

	public List<MatchAndPredictionDTO> getMatchAndPredicitonByDates(Date startDate, Date endDate, League league, String visitorId) {
		return getAsList(dataMapper.getMatchesByDate(new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()), league, visitorId));
	}

	public MatchAndPredictionDTO getMatchAndPredictionsById(String id, String visitorId) {
		return getSingleResultById(dataMapper.getMatchAndPredictionById(id, visitorId), id);
	}
	
	public void createMatch(String id, String homeTeamId, String awayTeamId, Date startTime, League league) {
		dataMapper.createMatch(id, homeTeamId, awayTeamId, startTime, league);
	}
}
