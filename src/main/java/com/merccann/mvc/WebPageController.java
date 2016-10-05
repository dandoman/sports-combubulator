package com.merccann.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.merccann.logic.VisitorVerificationLogic;

import lombok.Setter;

@Controller
public class WebPageController {
	private static final int COOKIE_MAX_AGE_SECONDS = 250 * 24 * 60 * 60;
	@Autowired
	@Setter
	private VisitorVerificationLogic visitorVerificationLogic;

	@RequestMapping(value = "/")
	public String root(@CookieValue(value = "visitor-id", required = false) String visitorId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		return "redirect:index.html";
	}
	
	@RequestMapping(value = "/index.html")
	public String index(@CookieValue(value = "visitor-id", required = false) String visitorId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(visitorId)) {
			visitorVerificationLogic.resolveVisitorAndSetCookie(request, response, visitorId, COOKIE_MAX_AGE_SECONDS);
		}
		return "index";
	}
	
	@RequestMapping(value = "/nfl")
	public String nfl(@CookieValue(value = "visitor-id", required = false) String visitorId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(visitorId)) {
			visitorVerificationLogic.resolveVisitorAndSetCookie(request, response, visitorId, COOKIE_MAX_AGE_SECONDS);
		}
		return "nfl";
	}

}
