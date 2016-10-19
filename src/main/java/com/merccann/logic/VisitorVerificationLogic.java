package com.merccann.logic;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.merccann.dao.VisitorDao;
import com.merccann.dto.VisitorDTO;
import com.merccann.exception.BadArgsException;

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
	private VisitorDTO resolveVisitor(String resolvedIpAddress, String visitorId) {
		List<VisitorDTO> visitorsByIpAddress = visitorDao.getVisitorsByIpAddress(resolvedIpAddress);
		if (StringUtils.isEmpty(visitorId) && visitorsByIpAddress.isEmpty()) {
			return visitorDao.createNewVisitor(UUID.randomUUID().toString(), resolvedIpAddress);
		}
		if(StringUtils.isEmpty(visitorId) && !visitorsByIpAddress.isEmpty()) {
			if(visitorsByIpAddress.size() >= 4) {
				log.error("Too many different sessions associated with this IP address: " + resolvedIpAddress);
				return visitorsByIpAddress.get(0); //This sucks, but at least we have some protection against someone create a ton of accounts
			} else {
				return visitorDao.createNewVisitor(UUID.randomUUID().toString(), resolvedIpAddress);
			}
		}

		VisitorDTO visitorById = visitorDao.getVisitorById(visitorId);
		if(visitorById == null && visitorsByIpAddress.isEmpty()) {
			//For whatever reason, visitorId cookie gave us a value we have never seen
			return visitorDao.createNewVisitor(UUID.randomUUID().toString(), resolvedIpAddress);
		} else if(visitorById != null && visitorsByIpAddress.isEmpty()) {
			//Visitor navigated to the site using their laptop on another network I guess
			return visitorById;
		} else if(visitorById == null && !visitorsByIpAddress.isEmpty()) {
			if(visitorsByIpAddress.size() >= 4) {
				log.error("Too many different sessions associated with this IP address: " + resolvedIpAddress);
				return visitorsByIpAddress.get(0); //This sucks, but at least we have some protection against someone create a ton of accounts
			} else {
				return visitorDao.createNewVisitor(UUID.randomUUID().toString(), resolvedIpAddress);
			}
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
