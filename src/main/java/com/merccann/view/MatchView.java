package com.merccann.view;

import java.util.Date;

import com.merccann.League;
import com.merccann.dto.MatchAndPredictionDTO;
import com.merccann.dto.MatchDTO;

import lombok.Data;

@Data
public class MatchView {
	private MatchDTO match;
	private String predictedWinnerId;
	private String predictedHomeScore;
	private String predictedVisitorScore;
	
	public static MatchView fromMatchAndPredictionDTO(MatchAndPredictionDTO dto) {
		MatchView view = new MatchView();
		MatchDTO matchDTO = new MatchDTO();
		
		matchDTO.setHomeTeamId(dto.getHomeTeamId());
		matchDTO.setHomeTeamName(dto.getHomeTeamName());
		matchDTO.setLeague(dto.getLeague());
		matchDTO.setMatchId(dto.getMatchId());
		matchDTO.setMatchStartTime(dto.getMatchStartTime());
		matchDTO.setVisitorTeamId(dto.getVisitorTeamId());
		matchDTO.setVisitorTeamName(dto.getVisitorTeamName());
		
		view.setMatch(matchDTO);
		view.setPredictedHomeScore(dto.getPredictedHomeScore());
		view.setPredictedVisitorScore(dto.getPredictedVisitorScore());
		view.setPredictedWinnerId(dto.getPredictedWinnerId());
		return view;
	}
}
