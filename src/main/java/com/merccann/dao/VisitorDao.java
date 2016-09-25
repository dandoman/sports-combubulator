package com.merccann.dao;

import java.util.UUID;

import com.merccann.entity.VisitorEntity;

public class VisitorDao {
	
	public VisitorEntity createNewVisitor(String visitorId, String ipAddress) {
		VisitorEntity v = new VisitorEntity();
		v.setId(UUID.randomUUID().toString());
		return v;
	}
	
	public VisitorEntity getVisitorById(String visitorId) {
		VisitorEntity v = new VisitorEntity();
		v.setId(visitorId);
		return v;
	}
	
	public VisitorEntity getVisitorByIpAddress(String ipAddress) {
		String id = "127.0.0.1".equals(ipAddress) ? "4822118a-9855-45b6-beba-7ca2df1e56b2" : UUID.randomUUID().toString();
		VisitorEntity v = new VisitorEntity();
		v.setId(id);
		return v;
	}
}	
