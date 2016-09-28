package com.merccann.logic;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.merccann.League;
import com.merccann.dao.MatchDao;
import com.merccann.dto.MatchAndPredictionDTO;
import com.merccann.exception.BadArgsException;
import com.merccann.view.MatchView;

import lombok.Setter;

public class MatchLogic {

	@Autowired
	@Setter
	private MatchDao matchDao;
	
	public List<MatchView> getMatches(Date startDate, Date endDate, League league, String visitorId) {
		startDate = (startDate == null) ? DateTime.now().minusDays(3).toDate() : startDate;
		endDate = (endDate == null) ? DateTime.now().plusDays(3).toDate() : endDate;
		if(new DateTime(startDate).getYear() < 2011 || new DateTime(endDate).getYear() > 2100) {
			throw new BadArgsException("Must provide reasonable dates");
		}
		List<MatchAndPredictionDTO> matches = matchDao.getMatchAndPredicitonByDates(startDate,endDate,league,visitorId);		
		return matches.stream().map(MatchView::fromMatchAndPredictionDTO).collect(Collectors.toList());
	}
}
