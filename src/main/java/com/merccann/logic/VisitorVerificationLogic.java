package com.merccann.logic;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.merccann.dao.VisitorDao;
import com.merccann.dto.VisitorDTO;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class VisitorVerificationLogic {

	@Autowired
	@Setter
	private VisitorDao visitorDao;

	
	/**
	 * @param resolvedIpAddress never null
	 * @param visitorId may be null
	 */
	public VisitorDTO resolveVisitor(String resolvedIpAddress, String visitorId) {
		VisitorDTO visitorByIpAddress = visitorDao.getVisitorByIpAddress(resolvedIpAddress);
		if (StringUtils.isEmpty(visitorId) && visitorByIpAddress == null) {
			return visitorDao.createNewVisitor(UUID.randomUUID().toString(), resolvedIpAddress);
		}
		if(StringUtils.isEmpty(visitorId) && visitorByIpAddress != null) {
			return visitorByIpAddress;
		}

		VisitorDTO visitorById = visitorDao.getVisitorById(visitorId);
		if(visitorById == null && visitorByIpAddress == null) {
			//For whatever reason, visitorId cookie gave us a value we have never seen
			return visitorDao.createNewVisitor(UUID.randomUUID().toString(), resolvedIpAddress);
		} else if(visitorById != null && visitorByIpAddress == null) {
			//Visitor navigated to the site using their laptop on another network I guess
			return visitorById;
		} else if(visitorById == null && visitorByIpAddress != null) {
			//I guess that the user may have nuked their cookies, give them their old id
			return visitorByIpAddress;
		} else {
			return visitorById;
		}
	}
	
	public VisitorDTO resolveVisitorAndSetCookie(HttpServletRequest request, HttpServletResponse response,
			String visitorIdCookieValue, int cookieMaxAge) {
		String forwardedIpAddress = request.getHeader("X-FORWARDED-FOR");
		String ipAddress = request.getRemoteAddr();
		log.info("IP Address: " + ipAddress);
		log.info("Forwarded IP Address: " + forwardedIpAddress);
		
		if (StringUtils.isEmpty(forwardedIpAddress) && StringUtils.isEmpty(ipAddress)) {
			throw new RuntimeException("Can't extract IP address from request");
		}

		//Always use forwarded ip address if it's there
		String resolvedIpAddress = StringUtils.isEmpty(forwardedIpAddress) ? ipAddress : forwardedIpAddress;
		VisitorDTO visitor = resolveVisitor(resolvedIpAddress, visitorIdCookieValue);
		Cookie cookie = new Cookie("visitor-id", visitor.getId());
		cookie.setPath("/");
		cookie.setMaxAge(cookieMaxAge);
		response.addCookie(cookie);
		return visitor;
	}

}
