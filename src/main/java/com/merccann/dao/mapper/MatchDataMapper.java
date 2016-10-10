package com.merccann.dao.mapper;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.merccann.League;
import com.merccann.dto.MatchAndPredictionDTO;
import com.merccann.dto.MatchDTO;

public interface MatchDataMapper {

	@Select("SELECT m.id as matchId, m.final_home_score as finalHomeScore, m.final_away_score as finalAwayScore, m.visitor_team_id as visitorTeamId, m.sport_league_abbrv as league, "
			+ "(SELECT team_name FROM teams WHERE id = m.visitor_team_id) as visitorTeamName, "
			+ "(SELECT team_name FROM teams WHERE id = m.home_team_id) as homeTeamName, "
			+ "m.home_team_id as homeTeamId, m.match_start_time as matchStartTime, p.victorious_team_id as predictedWinnerId, "
			+ "p.visitor_team_score as predictedVisitorScore, p.home_team_score as predictedHomeScore "
			+ "FROM matches m LEFT JOIN predictions p ON m.id = p.match_id AND p.visitor_id = #{visitorId} "
			+ "WHERE m.match_start_time >= #{startDate} AND m.match_start_time <= #{endDate} AND sport_league_abbrv = #{league} ORDER BY m.match_start_time DESC")
	public List<MatchAndPredictionDTO> getMatchesByDate(@Param("startDate") Timestamp startDate,
			@Param("endDate") Timestamp endDate, @Param("league") League league, @Param("visitorId") String visitorId);

	@Select("SELECT m.id as matchId, m.final_home_score as finalHomeScore, m.final_away_score as finalAwayScore, m.visitor_team_id as visitorTeamId, m.sport_league_abbrv as league, "
			+ "(SELECT team_name FROM teams WHERE id = m.visitor_team_id) as visitorTeamName, "
			+ "(SELECT team_name FROM teams WHERE id = m.home_team_id) as homeTeamName, "
			+ "m.home_team_id as homeTeamId, m.match_start_time as matchStartTime, p.victorious_team_id as predictedWinnerId, "
			+ "p.visitor_team_score as predictedVisitorScore, p.home_team_score as predictedHomeScore "
			+ "FROM matches m LEFT JOIN predictions p ON m.id = p.match_id AND p.visitor_id = #{visitorId} "
			+ "WHERE m.id = #{id} ORDER BY m.match_start_time DESC")
	public List<MatchAndPredictionDTO> getMatchAndPredictionById(@Param("id") String id,
			@Param("visitorId") String visitorId);

	@Select("SELECT m.id as matchId, m.final_home_score as finalHomeScore, m.final_away_score as finalAwayScore, m.visitor_team_id as visitorTeamId, m.sport_league_abbrv as league, "
			+ "(SELECT team_name FROM teams WHERE id = m.visitor_team_id) as visitorTeamName, "
			+ "(SELECT team_name FROM teams WHERE id = m.home_team_id) as homeTeamName, "
			+ "m.home_team_id as homeTeamId, m.match_start_time as matchStartTime FROM matches m WHERE m.id = #{matchId} ORDER BY m.match_start_time DESC")
	public List<MatchDTO> getMatchById(@Param("matchId") String matchId);

	@Insert("INSERT INTO matches(id,sport_league_abbrv,visitor_team_id,home_team_id,match_start_time) "
			+ "VALUES (#{id},#{league},#{awayTeamId},#{homeTeamId}, #{startTime})")
	public int createMatch(@Param("id") String id, @Param("homeTeamId") String homeTeamId,
			@Param("awayTeamId") String awayTeamId, @Param("startTime") Date startTime, @Param("league") League league);

	@Select("SELECT m.id as matchId, m.final_home_score as finalHomeScore, m.final_away_score as finalAwayScore, m.visitor_team_id as visitorTeamId, m.sport_league_abbrv as league, "
			+ "(SELECT team_name FROM teams WHERE id = m.visitor_team_id) as visitorTeamName, "
			+ "(SELECT team_name FROM teams WHERE id = m.home_team_id) as homeTeamName, "
			+ "m.home_team_id as homeTeamId, m.match_start_time as matchStartTime FROM matches m WHERE m.sport_league_abbrv = #{league} AND m.final_away_score IS NULL "
			+ "AND m.final_home_score IS NULL ORDER BY m.match_start_time DESC")
	public List<MatchDTO> getIncompleteMatches(@Param("league") League league);

	@Update("UPDATE matches SET final_away_score = #{finalAwayScore}, final_home_score = #{finalHomeScore} WHERE id = #{matchId}")
	public int updateMatchScore(@Param("matchId") String matchId, @Param("finalAwayScore") int finalAwayScore, @Param("finalHomeScore") int finalHomeScore);
}
