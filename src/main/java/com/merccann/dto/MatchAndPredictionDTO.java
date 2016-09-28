package com.merccann.dto;

import java.util.Date;

import com.merccann.League;

import lombok.Data;

@Data
public class MatchAndPredictionDTO {
	private String matchId;
	private String visitorTeamId;
	private League league;
	private String visitorTeamName;
	private String homeTeamId;
	private String homeTeamName;
	private Date matchStartTime;
	private String predictedWinnerId;
	private String predictedHomeScore;
	private String predictedVisitorScore;
}
