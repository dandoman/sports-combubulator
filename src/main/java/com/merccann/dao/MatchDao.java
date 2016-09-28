package com.merccann.dao;

import java.util.Date;
import java.util.List;

import com.merccann.League;
import com.merccann.dto.MatchAndPredictionDTO;
import com.merccann.dto.MatchDTO;

public class MatchDao extends Dao{
	
	public List<MatchDTO> getMatchesByDateRange(League sport, Date start, Date end) {
		return null;
	}

	public MatchDTO getMatchById(String matchId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MatchAndPredictionDTO> getMatchAndPredicitonByDates(Date startDate, Date endDate, League league, String visitorId) {
		// TODO Auto-generated method stub
		return null;
	}
}
