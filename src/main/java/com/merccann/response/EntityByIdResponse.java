package com.merccann.response;

import java.util.Date;

import com.merccann.view.EntityView;

import lombok.Data;

@Data
public class EntityByIdResponse {
	private EntityView entity;
	private Date generatedAt;
}
