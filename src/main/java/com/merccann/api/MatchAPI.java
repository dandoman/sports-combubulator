package com.merccann.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.merccann.League;
import com.merccann.dao.AppDao;
import com.merccann.entity.VisitorEntity;
import com.merccann.logic.VisitorVerificationLogic;
import com.merccann.request.CreatePredictionRequest;
import com.merccann.view.MatchView;

import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/api/match")
@Log4j2
public class MatchAPI {

	private static final int COOKIE_MAX_AGE_SECONDS = 250 * 24 * 60 * 60;

	@Autowired
	@Setter
	private VisitorVerificationLogic visitorVerificationLogic;

	@ApiOperation(value = "getMatches")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<MatchView> getMatches(@RequestParam(value = "startDate", required = false) Date startDate,
			@RequestParam(value = "endDate", required = false) Date endDate,
			@RequestParam(value = "league", required = true) League league) {
		return new ArrayList<>();
	}

	@ApiOperation(value = "createPrediction")
	@RequestMapping(value = "/prediction", method = RequestMethod.POST)
	@ResponseBody
	public void createPrediction(@RequestBody CreatePredictionRequest r, HttpServletRequest request,
			HttpServletResponse response, @CookieValue(value = "visitor-id", required = false) String visitorId) {
		String forwardedIpAddress = request.getHeader("X-FORWARDED-FOR");
		String ipAddress = request.getRemoteAddr();
		if (StringUtils.isEmpty(forwardedIpAddress) && StringUtils.isEmpty(ipAddress)) {
			throw new RuntimeException("Can't extract IP address from request");
		}

		String resolvedIpAddress = StringUtils.isEmpty(forwardedIpAddress) ? ipAddress : forwardedIpAddress;
		VisitorEntity visitor = visitorVerificationLogic.resolveVisitor(resolvedIpAddress, visitorId);
		Cookie cookie = new Cookie("visitor-id", visitor.getId());
		cookie.setPath("/");
		cookie.setMaxAge(COOKIE_MAX_AGE_SECONDS);
		response.addCookie(cookie);
	}
}
