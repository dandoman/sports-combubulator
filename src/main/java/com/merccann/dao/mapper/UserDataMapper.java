package com.merccann.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.merccann.dto.UserDTO;

public interface UserDataMapper {

	@Insert("INSERT INTO users(username, hashed_password, password_salt, email_address, visitor_id) "
			+ "VALUES (#{username}, #{hashedPassword}, #{passwordSalt}, #{emailAddress}, #{visitorId})")
	public int createUser(@Param("username") String username, @Param("hashedPassword") String hashedPassword,
			@Param("passwordSalt") String passwordSalt, @Param("emailAddress") String emailAddress,
			@Param("visitorId") String visitorId);
	
	@Select("SELECT u.visitor_id as visitorId, u.username as username, u.hashed_password as passwordHash, u.password_salt as passwordSalt, "
			+ "u.email_address as emailAddress, u.created_at as createdAt FROM users u WHERE u.username = #{username}")
	public List<UserDTO> getUserByUsername(@Param("username") String username);
	
	@Select("SELECT u.visitor_id as visitorId, u.username as username, u.hashed_password as passwordHash, u.password_salt as passwordSalt, "
			+ "u.email_address as emailAddress, u.created_at as createdAt FROM users u WHERE u.visitor_id = #{visitorId}")
	public List<UserDTO> getUserByVisitorId(@Param("visitorId") String visitorId);
	
}
