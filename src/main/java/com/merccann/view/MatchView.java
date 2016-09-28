package com.merccann.view;

import com.merccann.dto.MatchDTO;

import lombok.Data;

@Data
public class MatchView {
	private MatchDTO match;
	private String predictedWinnerId;
	private String predictedHomeScore;
	private String predictedVisitorScore;
}
