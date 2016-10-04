package com.merccann.dao.mapper.provider;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Log4j2
public class PredictionQueryProvider {
	//If passed an id, it will return that specific data, else, it returns all data
	public String getPredicitonsByMatchIds(Map params) {
		final List<String> matchIds = (List<String>) params.get("matchIds");
		String matchIdsAsString = String.join(", ", matchIds.stream().map(m -> "'" + m + "'").collect(Collectors.toList()));
		
		String sql = new SQL() {{
			SELECT("visitor_id as visitorId, match_id as matchId, victorious_team_id as victoriousTeamId, "
			+ "visitor_team_score as visitorTeamScore, home_team_score as homeTeamScore");
			FROM("predictions");
			WHERE("match_id IN (" + matchIdsAsString + ")");
		}}.toString();
		
		log.info("QUERY STRING: " + sql);
		return sql;
	}
}
