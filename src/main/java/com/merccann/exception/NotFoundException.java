package com.merccann.exception;

public class NotFoundException extends RuntimeException{
	private static final long serialVersionUID = -1396008430343027603L;

	public NotFoundException(String message){
		super(message);
	}
}