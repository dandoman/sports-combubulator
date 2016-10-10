package com.merccann.request;

import lombok.Data;

@Data
public class UpdateMatchScoreRequest {
	private String matchId;
	private int finalHomeScore;
	private int finalAwayScore;
}
