package com.merccann.logic;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.merccann.League;
import com.merccann.dao.MatchDao;
import com.merccann.dto.MatchAndPredictionDTO;
import com.merccann.view.MatchView;

import lombok.Setter;

public class MatchLogic {

	@Autowired
	@Setter
	private MatchDao matchDao;
	
	public List<MatchView> getMatches(Date startDate, Date endDate, League league, String visitorId) {
		List<MatchAndPredictionDTO> matches = matchDao.getMatchAndPredicitonByDates(startDate,endDate,league,visitorId);
		return null;
	}

}
