package com.merccann.dto;

import lombok.Data;

@Data
public class PredictionDTO {
	private String visitorId;
	private String matchId;
	private String victoriousTeamId;
	private Integer homeTeamScore;
	private Integer visitorTeamScore;
}
