package com.merccann.logic;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.merccann.dao.MatchDao;
import com.merccann.dao.PredictionDao;
import com.merccann.dto.MatchDTO;
import com.merccann.exception.BadArgsException;

import lombok.Setter;

public class PredictionLogic {
	
	@Autowired
	@Setter
	private PredictionDao predictionDao;
	
	@Autowired
	@Setter
	private MatchDao matchDao;

	public void createPrediciton(String matchId, String victoriousTeamId, Integer homeTeamScore,
			Integer visitorTeamScore, String visitorId) {
		
		MatchDTO match = matchDao.getMatchById(matchId);
		if(match == null) {
			throw new BadArgsException("No match with id " + matchId + " found");
		}
		if(!match.getHomeTeamId().equals(victoriousTeamId) && !match.getVisitorTeamId().equals(victoriousTeamId)) {
			throw new BadArgsException(victoriousTeamId + " team id does not belong to match " + matchId);
		}
		
		if(DateTime.now().isAfter(match.getMatchStartTime().getTime())) {
			throw new BadArgsException("Match has started, cannot make prediction now");
		}
		
		if(homeTeamScore == null && visitorTeamScore == null) {
			predictionDao.createPrediction(visitorId, matchId, victoriousTeamId, visitorTeamScore, homeTeamScore);
		}
		
		if(homeTeamScore != null && visitorTeamScore != null) {
			if(homeTeamScore < 0 || visitorTeamScore < 0) {
				throw new BadArgsException("Visitor and home team scores must be positive integers");
			}
			predictionDao.createPrediction(visitorId, matchId, victoriousTeamId, visitorTeamScore, homeTeamScore);
		}
		
		throw new BadArgsException("One of visitor or home team score was undefined");
	}
}
