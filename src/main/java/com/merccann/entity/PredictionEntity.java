package com.merccann.entity;

import java.util.Date;

import lombok.Data;

@Data
public class PredictionEntity {
	private String visitorId;
	private String matchId;
	private String visitorTeamId;
	private String visitorTeamName;
	private String homeTeamId;
	private String homeTeamName;
	private String victoriousTeamId;
	private Integer homeTeamScore;
	private Integer visitorTeamScore;
	private Date createdAt;
}
