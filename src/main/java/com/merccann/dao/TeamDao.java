package com.merccann.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.merccann.League;
import com.merccann.dao.mapper.TeamDataMapper;
import com.merccann.dto.TeamDTO;

import lombok.Setter;

public class TeamDao extends Dao {

	@Setter
	@Autowired
	private TeamDataMapper dataMapper; 
	
	public List<TeamDTO> getTeams(League league) {
		return getAsList(dataMapper.getTeams(league));
	}
}
