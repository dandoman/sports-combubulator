package com.merccann.logic;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.merccann.dao.VisitorDao;
import com.merccann.entity.VisitorEntity;

import lombok.Setter;

public class VisitorVerificationLogic {

	@Autowired
	@Setter
	private VisitorDao visitorDao;

	
	/**
	 * @param resolvedIpAddress never null
	 * @param visitorId may be null
	 */
	public VisitorEntity resolveVisitor(String resolvedIpAddress, String visitorId) {
		VisitorEntity visitorByIpAddress = visitorDao.getVisitorByIpAddress(resolvedIpAddress);
		if (StringUtils.isEmpty(visitorId) && visitorByIpAddress == null) {
			return visitorDao.createNewVisitor(UUID.randomUUID().toString(), resolvedIpAddress);
		}
		if(StringUtils.isEmpty(visitorId) && visitorByIpAddress != null) {
			return visitorByIpAddress;
		}

		VisitorEntity visitorById = visitorDao.getVisitorById(visitorId);
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

}
