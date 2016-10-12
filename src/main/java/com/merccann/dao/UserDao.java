package com.merccann.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.merccann.dao.mapper.UserDataMapper;
import com.merccann.dto.UserDTO;

import lombok.Setter;

public class UserDao extends Dao {
	@Setter
	@Autowired
	private UserDataMapper dataMapper; 
	
	public void createUser(String username, String hashedPassword, String passwordSalt, String emailAddress, String visitorId) {
		dataMapper.createUser(username, hashedPassword, passwordSalt, emailAddress, visitorId);
	}
	
	public UserDTO getUserByUsername(String userName) {
		return getSingleResultById(dataMapper.getUserByUsername(userName), userName);
	}
	
	public UserDTO getUserByVisitorId(String visitorId) {
		return getSingleResultById(dataMapper.getUserByVisitorId(visitorId), visitorId);
	}
}
