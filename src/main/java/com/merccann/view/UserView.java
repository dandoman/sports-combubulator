package com.merccann.view;


import lombok.Data;

@Data
public class UserView {
	private MatchView match;
	private Integer awayFinalScore;
	private Integer homeFinalScore;
}
