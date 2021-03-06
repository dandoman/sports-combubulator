package com.merccann.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.merccann.League;
import com.merccann.dao.mapper.provider.PredictionQueryProvider;
import com.merccann.dto.MatchAndPredictionDTO;
import com.merccann.dto.PredictionDTO;

public interface PredictionDataMapper {
	@Insert("INSERT INTO predictions(visitor_id, match_id, victorious_team_id, visitor_team_score, home_team_score) "
			+ "VALUES (#{visitorId}, #{matchId}, #{victoriousTeamId}, #{visitorScore}, #{homeScore})")
	public int createPrediction(@Param("visitorId") String visitorId, @Param("matchId") String matchId,
			@Param("victoriousTeamId") String victoriousTeamId, @Param("visitorScore") Integer visitorScore,
			@Param("homeScore") Integer homeScore);

	@Select("SELECT visitor_id as visitorId, match_id as matchId, victorious_team_id as victoriousTeamId, "
			+ "visitor_team_score as visitorTeamScore, home_team_score as homeTeamScore FROM predictions WHERE match_id = #{matchId}")
	public List<PredictionDTO> getPredicitonsByMatchId(@Param("matchId") String matchId);

	@Select("SELECT visitor_id as visitorId, match_id as matchId, victorious_team_id as victoriousTeamId, "
			+ "visitor_team_score as visitorTeamScore, home_team_score as homeTeamScore FROM predictions WHERE match_id = #{matchId} AND visitor_id = #{visitorId}")
	public List<PredictionDTO> getPredictionByMatchAndVisitor(@Param("matchId") String matchId,
			@Param("visitorId") String visitorId);

	@Delete("DELETE FROM predictions WHERE visitor_id = #{visitorId} AND match_id = #{matchId}")
	public int deletePrediction(@Param("matchId") String matchId, @Param("visitorId") String visitorId);

	@SelectProvider(type = PredictionQueryProvider.class, method = "getPredicitonsByMatchIds")
	public List<PredictionDTO> getPredicitonsByMatchIds(@Param("matchIds") List<String> matchIds);

	@Select("SELECT m.id as matchId, m.visitor_team_id as visitorTeamId, m.sport_league_abbrv as league, "
			+ "(SELECT team_name FROM teams WHERE id = m.visitor_team_id) as visitorTeamName, "
			+ "(SELECT team_name FROM teams WHERE id = m.home_team_id) as homeTeamName, "
			+ "m.home_team_id as homeTeamId, m.final_home_score as finalHomeScore, m.final_away_score as finalAwayScore, m.match_start_time as matchStartTime, p.victorious_team_id as predictedWinnerId, "
			+ "p.visitor_team_score as predictedVisitorScore, p.home_team_score as predictedHomeScore "
			+ "FROM predictions p INNER JOIN matches m ON m.id = p.match_id " + "WHERE p.visitor_id = #{visitorId} ORDER BY m.match_start_time DESC")
	public List<MatchAndPredictionDTO> getPredictionsForVisitor(@Param("visitorId") String visitorId);
}
