package com.merccann.request;

import lombok.Data;

@Data
public class CreatePredictionRequest {
	private String matchId;
	private String victoriousTeamId;
	private Integer homeTeamScore;
	private Integer visitorTeamScore;
}
