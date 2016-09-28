package com.merccann.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.merccann.League;
import com.merccann.dto.VisitorDTO;
import com.merccann.logic.MatchLogic;
import com.merccann.logic.PredictionLogic;
import com.merccann.logic.VisitorVerificationLogic;
import com.merccann.request.CreatePredictionRequest;
import com.merccann.view.MatchView;

import io.swagger.annotations.ApiOperation;
import lombok.Setter;

@Controller
@RequestMapping("/api/match")
public class MatchAPI {

	private static final int COOKIE_MAX_AGE_SECONDS = 250 * 24 * 60 * 60;

	@Autowired
	@Setter
	private VisitorVerificationLogic visitorVerificationLogic;

	@Autowired
	@Setter
	private PredictionLogic predicitonLogic;
	
	@Autowired
	@Setter
	private MatchLogic matchLogic;

	@ApiOperation(value = "getMatches")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<MatchView> getMatches(@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso=ISO.DATE) Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(iso=ISO.DATE) Date endDate,
			@RequestParam(value = "league", required = true) League league, HttpServletRequest request,
			HttpServletResponse response, @CookieValue(value = "visitor-id", required = false) String visitorId) {
		VisitorDTO visitor = resolveVisitorAndSetCookie(request, response, visitorId); //This may be problematic long run due to so many db reads. WIll probably need to eventually set up redis and read from there
		return matchLogic.getMatches(startDate, endDate, league, visitor.getId());
	}

	@ApiOperation(value = "createPrediction")
	@RequestMapping(value = "/prediction", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public void createPrediction(@RequestBody CreatePredictionRequest r, HttpServletRequest request,
			HttpServletResponse response, @CookieValue(value = "visitor-id", required = false) String visitorId) {
		VisitorDTO visitor = resolveVisitorAndSetCookie(request, response, visitorId);
		predicitonLogic.createPrediciton(r.getMatchId(), r.getVictoriousTeamId(), r.getHomeTeamScore(), r.getVisitorTeamScore(), visitor.getId());
	}

	private VisitorDTO resolveVisitorAndSetCookie(HttpServletRequest request, HttpServletResponse response,
			String visitorIdCookieValue) {
		String forwardedIpAddress = request.getHeader("X-FORWARDED-FOR");
		String ipAddress = request.getRemoteAddr();
		if (StringUtils.isEmpty(forwardedIpAddress) && StringUtils.isEmpty(ipAddress)) {
			throw new RuntimeException("Can't extract IP address from request");
		}

		String resolvedIpAddress = StringUtils.isEmpty(forwardedIpAddress) ? ipAddress : forwardedIpAddress;
		VisitorDTO visitor = visitorVerificationLogic.resolveVisitor(resolvedIpAddress, visitorIdCookieValue);
		Cookie cookie = new Cookie("visitor-id", visitor.getId());
		cookie.setPath("/");
		cookie.setMaxAge(COOKIE_MAX_AGE_SECONDS);
		response.addCookie(cookie);
		return visitor;
	}
}
