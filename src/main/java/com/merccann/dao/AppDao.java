package com.merccann.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.merccann.dao.mapper.ApiDataMapper;
import com.merccann.entity.Entity;

import lombok.Setter;

public class AppDao {
	
	private static final int PAGE_SIZE = 10;
	
	@Setter
	@Autowired
	private ApiDataMapper apiDataMapper;  

	public Entity createEntity(String name, String description) {
		String id = UUID.randomUUID().toString();
		apiDataMapper.createEntity(id, name, description);
		return getEntityById(id);
	}

	public List<Entity> getPageOfEntities(int pageNo) {
		return apiDataMapper.getEntityByPage(pageNo * PAGE_SIZE);
	}

	public Entity getEntityById(String id) {
		return apiDataMapper.getEntityById(id).stream().findFirst().orElse(null);
	}

}
