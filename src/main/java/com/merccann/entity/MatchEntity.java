package com.merccann.entity;

import java.sql.Date;

import com.merccann.League;

import lombok.Data;

@Data
public class MatchEntity {
	private String matchId;
	private String visitorTeamId;
	private League league;
	private String visitorTeamName;
	private String homeTeamId;
	private String homeTeamName;
	private Date matchStartTime;
}
