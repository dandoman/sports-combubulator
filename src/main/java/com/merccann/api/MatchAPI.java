package com.merccann.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.merccann.League;
import com.merccann.request.CreatePredictionRequest;
import com.merccann.view.MatchView;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/api/match")
@Log4j2
public class MatchAPI {

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
			@CookieValue(value = "user-id", required = false) String userId) {
		String ipAddress = request.getRemoteAddr();
		if(StringUtils.isEmpty(userId)){
			//See if we can pull up an existing id from ip address, if not, create new one and set cookie
		} 
		log.info("we got it");
	}
}
