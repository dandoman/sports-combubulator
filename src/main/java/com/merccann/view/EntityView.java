package com.merccann.view;

import com.merccann.entity.Entity;

import lombok.Data;

@Data
public class EntityView {
	
	private String id;
	private String name;
	private String description;

	public static EntityView fromEntity(Entity entity) {
		if(entity == null) {
			return null;
		}
		EntityView entityView = new EntityView();
		entityView.setId(entity.getId());
		entityView.setDescription(entity.getDescription());
		entityView.setName(entity.getName());
		return entityView;
	}
}
