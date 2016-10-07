package com.merccann.logic;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.merccann.dao.MatchDao;
import com.merccann.dao.PredictionDao;
import com.merccann.dto.MatchDTO;
import com.merccann.dto.PredictionDTO;
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
		
		if(StringUtils.isEmpty(victoriousTeamId)) {
			//Must determine victor by score
			if(homeTeamScore == null || visitorTeamScore == null){
				throw new BadArgsException("If no winner prediction is provided explicitly, you must provide a predicted score for each team");
			} else {
				if(homeTeamScore > visitorTeamScore){
					victoriousTeamId = match.getHomeTeamId();
				} else if(homeTeamScore < visitorTeamScore) {
					victoriousTeamId = match.getVisitorTeamId();
				} else {
					throw new BadArgsException("You cannot predict a tie game");
				}
			}
		}
		
		if(!match.getHomeTeamId().equals(victoriousTeamId) && !match.getVisitorTeamId().equals(victoriousTeamId)) {
			throw new BadArgsException(victoriousTeamId + " team id does not belong to match " + matchId);
		}
		
		if(DateTime.now().isAfter(match.getMatchStartTime().getTime())) {
			throw new BadArgsException("Match has started, cannot make prediction now");
		}
		
		if(homeTeamScore == null && visitorTeamScore == null) {
			predictionDao.createPrediction(visitorId, matchId, victoriousTeamId, visitorTeamScore, homeTeamScore);
			return;
		}
		
		if(homeTeamScore != null && visitorTeamScore != null) {
			if(homeTeamScore < 0 || visitorTeamScore < 0) {
				throw new BadArgsException("Visitor and home team scores must be positive integers");
			}
			predictionDao.createPrediction(visitorId, matchId, victoriousTeamId, visitorTeamScore, homeTeamScore);
			return;
		}
		
		throw new BadArgsException("One of visitor or home team score was undefined");
	}

	public void updatePrediciton(String matchId, String victoriousTeamId, Integer homeTeamScore,
			Integer visitorTeamScore, String visitorId) {
		
		PredictionDTO prediction = predictionDao.getPredicitonForVisitorAndMatch(matchId, visitorId);
		if(prediction == null || visitorId == null) {
			throw new BadArgsException("No existing prediction found to update");
		}
		predictionDao.deletePrediction(prediction.getVisitorId(), prediction.getMatchId());
		createPrediciton(matchId, victoriousTeamId, homeTeamScore, visitorTeamScore, visitorId);
	}
}
