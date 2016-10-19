package com.merccann.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.merccann.dao.mapper.VisitorDataMapper;
import com.merccann.dto.VisitorDTO;

import lombok.Setter;

public class VisitorDao extends Dao {
	
	@Setter
	@Autowired
	private VisitorDataMapper dataMapper; 
	
	public VisitorDTO createNewVisitor(String visitorId, String ipAddress) {
		VisitorDTO v = new VisitorDTO();
		v.setId(visitorId);
		v.setIpAddress(ipAddress);
		int res = dataMapper.createVisitor(visitorId, ipAddress);
		if(res == 0) {
			throw new RuntimeException("Error creating visitor");
		}
		return v;
	}
	
	public VisitorDTO getVisitorById(String visitorId) {
		return getSingleResultById(dataMapper.getVisitorById(visitorId), visitorId);
		
	}
	
	public List<VisitorDTO> getVisitorsByIpAddress(String ipAddress) {
		return getAsList(dataMapper.getVisitorByIp(ipAddress));
	}
}	
