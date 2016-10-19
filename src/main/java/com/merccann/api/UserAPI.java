package com.merccann.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.merccann.dto.MatchAndPredictionDTO;
import com.merccann.dto.VisitorDTO;
import com.merccann.logic.MatchLogic;
import com.merccann.logic.PredictionLogic;
import com.merccann.logic.UserLogic;
import com.merccann.logic.VisitorVerificationLogic;
import com.merccann.request.CreateUserRequest;
import com.merccann.view.UserView;

import io.swagger.annotations.ApiOperation;
import lombok.Setter;

@Controller
@RequestMapping("/api/user")
public class UserAPI {
	private static final int COOKIE_MAX_AGE_SECONDS = 250 * 24 * 60 * 60;

	@Autowired
	@Setter
	private VisitorVerificationLogic visitorVerificationLogic;

	@Autowired
	@Setter
	private MatchLogic matchLogic;

	@Autowired
	@Setter
	private UserLogic userLogic;

	@ApiOperation(value = "getMatch")
	@RequestMapping(method = RequestMethod.GET, value = "/predictions")
	@ResponseBody
	@Transactional
	public List<UserView> getMatch(HttpServletRequest request, HttpServletResponse response,
			@CookieValue(value = "visitor-id", required = false) String visitorId) {
		VisitorDTO visitor = visitorVerificationLogic.resolveVisitorAndSetCookie(request, response, visitorId,
				COOKIE_MAX_AGE_SECONDS);

		return matchLogic.getPredictionsForUser(visitor.getId());
	}

	@ApiOperation(value = "createUser")
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public void createUser(CreateUserRequest r, @CookieValue(value = "visitor-id", required = true) String visitorId) {
		userLogic.createUser(r.getUsername(), r.getPassword(), visitorId, r.getEmailAddress());
	}
}
