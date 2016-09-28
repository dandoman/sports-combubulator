package com.merccann.dao;

import java.util.List;

import com.merccann.dto.PredictionDTO;

public class PredictionDao extends Dao {

	public void createPrediction(String visitorId, String matchId, String victoriousTeamId, Integer visitorScore, Integer homeScore) {
		
	}
	
	public List<PredictionDTO> getPredicitonsForMatch(String matchId) {
		return null;
	}
}
