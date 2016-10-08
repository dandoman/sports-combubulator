package com.merccann.request;

import java.util.Date;

import com.merccann.League;

import lombok.Data;

@Data
public class CreateMatchRequest {
	private String homeTeamId;
	private String awayTeamId;
	private Date startTime;
	private League league;
}
