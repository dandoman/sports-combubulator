package com.merccann.request;

import lombok.Data;

@Data
public class CreateUserRequest {
	private String username;
	private String password;
	private String emailAddress;
}
