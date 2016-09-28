package com.merccann.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface PredictionDataMapper {
	@Insert("INSERT INTO predictions(visitor_id, match_id, victorious_team_id, visitor_team_score, home_team_score) "
			+ "VALUES (#{visitorId}, #{matchId}, #{victoriousTeamId}, #{visitorScore}, #{homeScore})")
	public int createVisitor(@Param("visitorId") String visitorId, @Param("matchId") String matchId,
			@Param("victoriousTeamId") String victoriousTeamId, @Param("visitorScore") Integer visitorScore,
			@Param("homeScore") Integer homeScore);
}
