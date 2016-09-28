package com.merccann.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.merccann.dao.mapper.PredictionDataMapper;
import com.merccann.dto.PredictionDTO;

import lombok.Setter;

public class PredictionDao extends Dao {

	@Setter
	@Autowired
	private PredictionDataMapper dataMapper; 
	
	public void createPrediction(String visitorId, String matchId, String victoriousTeamId, Integer visitorScore, Integer homeScore) {
		int res = dataMapper.createPrediction(visitorId, matchId, victoriousTeamId, visitorScore, homeScore);
		if(res == 0) {
			throw new RuntimeException("Error creating prediction");
		}
	}
	
	public List<PredictionDTO> getPredicitonsForMatch(String matchId) {
		return getAsList(dataMapper.getPredicitonsByMatchId(matchId));
	}
}
