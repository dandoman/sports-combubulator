package com.merccann.view;


import lombok.Data;

@Data
public class UserView {
	private MatchView matchData;
	private Integer awayFinalScore;
	private Integer homeFinalScore;
}
