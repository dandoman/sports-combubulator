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
	
	public List<MatchDTO> getMatchesByDateRange(League sport, Date start, Date end) {
		return null;
	}

	public MatchDTO getMatchById(String matchId) {
		return getSingleResultById(dataMapper.getMatchById(matchId), matchId);
	}

	public List<MatchAndPredictionDTO> getMatchAndPredicitonByDates(Date startDate, Date endDate, League league, String visitorId) {
		return getAsList(dataMapper.getMatchesByDate(new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()), league, visitorId));
	}
}
