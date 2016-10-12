package com.merccann.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {
	private String visitorId;
	private String userName;
	private String passwordHash;
	private String passwordSalt;
	private String emailAddress;
	private Date createdAt;
}
