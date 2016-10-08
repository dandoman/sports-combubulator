package com.merccann.dto;

import com.merccann.League;

import lombok.Data;

@Data
public class TeamDTO {
	private String id;
	private String name;
	private League league;
}
