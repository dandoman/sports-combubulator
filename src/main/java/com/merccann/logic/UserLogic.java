package com.merccann.logic;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.merccann.dao.UserDao;
import com.merccann.dto.UserDTO;
import com.merccann.exception.BadArgsException;

import lombok.Setter;

public class UserLogic {
	@Autowired
	@Setter
	private UserDao userDao;
	
	public UserDTO createUser(String username, String password, String visitorId, String emailAddress) {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(visitorId)) {
			throw new BadArgsException("Username and password must be provided");
		}
		UserDTO existingUserByUsername = userDao.getUserByUsername(username);
		UserDTO existingUserByVisitorId = userDao.getUserByVisitorId(visitorId);
		if(existingUserByUsername != null) {
			throw new BadArgsException("User with username " + username + " already exists!");
		}
		if(existingUserByVisitorId != null) {
			throw new BadArgsException("User already exists with this session");
		}

		username = username.trim();
		
		String salt = UUID.randomUUID().toString();
		String passwordHash = hash(password + salt);
		userDao.createUser(username, passwordHash, salt, emailAddress, visitorId);
		return userDao.getUserByUsername(username);
	}
	
	public boolean login(String username, String password) {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new BadArgsException("Must provide both username and password");
		}
		
		UserDTO userByUsername = userDao.getUserByUsername(username);
		return userByUsername.getPasswordHash().equals(hash(password + userByUsername.getPasswordSalt()));
	}
	
	private String hash(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(value.getBytes("UTF-8"));
			return Hex.encodeHexString(md.digest());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException("Encoding error", e);
		}
	}
}
