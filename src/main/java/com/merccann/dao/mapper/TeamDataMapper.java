package com.merccann.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.merccann.League;
import com.merccann.dto.TeamDTO;

public interface TeamDataMapper {
	@Select("SELECT t.id as id, t.team_name as name, (select league_abrv from sports where id = t.sport_id) as league "
			+ "FROM teams t WHERE t.sport_id = (select id from sports where league_abrv = #{league})")
	public List<TeamDTO> getTeams(@Param("league") League league);
}
