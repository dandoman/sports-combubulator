package com.merccann.api;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.merccann.League;
import com.merccann.dao.TeamDao;
import com.merccann.dto.TeamDTO;
import com.merccann.exception.BadArgsException;
import com.merccann.logic.MatchLogic;
import com.merccann.request.CreateMatchRequest;

import io.swagger.annotations.ApiOperation;
import lombok.Setter;

@Controller
@RequestMapping("/api/admin")
public class AdminAPI {

	@Autowired
	@Setter
	private MatchLogic matchLogic;
	
	//Not great to have a dao here given the convention we've established. Make sure to put it in the logic layer when appropriate
	@Autowired
	@Setter
	private TeamDao teamDao;
	
	//Quick n dirty. This should really be in the db...
	public static final String hashed = "db0bb689968739b5e32b84859773f83e6cfe32ce2e42436c7a9d1a0caa936a9a";
	
	@ApiOperation(value = "createMatch")
	@RequestMapping(value = "/match", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public void createMatch(@RequestBody CreateMatchRequest r, @CookieValue(value = "admin-id", required = true) String adminId) {
		if(!hashed.equals(hash(adminId))) {
			throw new BadArgsException("Wrong Admin Id");
		}
		
		matchLogic.createMatch(r.getAwayTeamId(), r.getHomeTeamId(), r.getStartTime(), r.getLeague());
	}
	
	@ApiOperation(value = "getTeams")
	@RequestMapping(value = "/teams", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<TeamDTO> getTeams(@RequestParam(value = "league", required = true) League league) {
		return teamDao.getTeams(league);
	}
	
	private String hash(String saltedPassword) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(saltedPassword.getBytes("UTF-8"));
			return Hex.encodeHexString(md.digest());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException("Encoding error", e);
		}
	}
}
