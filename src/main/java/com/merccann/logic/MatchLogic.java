package com.merccann.logic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.merccann.League;
import com.merccann.dao.MatchDao;
import com.merccann.dao.PredictionDao;
import com.merccann.dto.MatchAndPredictionDTO;
import com.merccann.dto.MatchDTO;
import com.merccann.dto.PredictionDTO;
import com.merccann.exception.BadArgsException;
import com.merccann.view.MatchView;

import lombok.Setter;

public class MatchLogic {

	@Autowired
	@Setter
	private MatchDao matchDao;

	@Autowired
	@Setter
	private PredictionDao predictionDao;

	// Due to the many db calls to get the predicitons, we'll need to optimize
	// this in the future
	public List<MatchView> getMatches(Date startDate, Date endDate, League league, String visitorId) {
		startDate = (startDate == null) ? DateTime.now().minusDays(3).toDate() : startDate;
		endDate = (endDate == null) ? DateTime.now().plusDays(3).toDate() : endDate;
		if(new DateTime(startDate).getYear() < 2011 || new DateTime(endDate).getYear() > 2100) {
			throw new BadArgsException("Must provide reasonable dates");
		}
		List<MatchAndPredictionDTO> matches = matchDao.getMatchAndPredicitonByDates(startDate,endDate,league,visitorId);
		List<PredictionDTO> predicitonsForMatches = predictionDao.getPredicitonsForMatches(matches.stream().map(m -> m.getMatchId()).collect(Collectors.toList()));
		Map<UUID,List<PredictionDTO>> predicitonsByMatchId = predicitonsForMatches.stream().collect(Collectors.groupingBy(m -> UUID.fromString(m.getMatchId())));
		
		return matches.stream().map(match -> {
			List<PredictionDTO> predictions = predicitonsByMatchId.get(UUID.fromString(match.getMatchId())); //Trap, but I'd rather be hashing UUIDs than strings
			return buildMatchView(match, predictions);
		}).collect(Collectors.toList());
	}

	public MatchView getMatch(String id, String visitorId) {
		MatchAndPredictionDTO match = matchDao.getMatchAndPredictionsById(id, visitorId);
		List<PredictionDTO> predictions = predictionDao.getPredicitonsForMatch(match.getMatchId());
		return buildMatchView(match, predictions);
	}
	
	private MatchView buildMatchView(MatchAndPredictionDTO match, List<PredictionDTO> predictions) {
		Map<String, List<PredictionDTO>> predictionsByTeamId = predictions.stream().collect(Collectors.groupingBy(i -> i.getVictoriousTeamId()));
		int homeWinPredicitons = 0;
		int awayWinPredicitons = 0;

		for(Map.Entry<String, List<PredictionDTO>> entry : predictionsByTeamId.entrySet()) {
			if(entry.getKey().equals(match.getHomeTeamId())) {
				homeWinPredicitons = entry.getValue().size();
			} else {
				awayWinPredicitons = entry.getValue().size();
			}
		}
		double [] homeScoreArray = predictions.stream().map(x -> x.getHomeTeamScore()).filter(x -> x != null).map(x -> x * 1.0).mapToDouble(Double::doubleValue).toArray();
		double [] visitorScoreArray = predictions.stream().map(x -> x.getVisitorTeamScore()).filter(x -> x != null).map(x -> x * 1.0).mapToDouble(Double::doubleValue).toArray();
		Percentile percentile = new Percentile();
		double homeScoreMedian = percentile.evaluate(homeScoreArray, 50);
		double visitorScoreMedian = percentile.evaluate(visitorScoreArray, 50);
		
		return MatchView.fromMatchAndPredictionDTO(match, Math.round(homeScoreMedian), Math.round(visitorScoreMedian), homeWinPredicitons, awayWinPredicitons);
	}
}
