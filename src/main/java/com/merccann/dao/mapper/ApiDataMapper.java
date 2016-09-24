package com.merccann.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.merccann.entity.Entity;

public interface ApiDataMapper {
	
	@Insert("INSERT INTO entities(id, name, description) VALUES (#{id},#{name},#{description})")
	public int createEntity(@Param("id") String id, @Param("name") String name, @Param("description") String description);
	
	@Select("SELECT id, name, description, created_at as createdAt FROM entities ORDER BY id asc LIMIT 10 OFFSET #{offset}")
	public List<Entity> getEntityByPage(@Param("offset") int offset);
	
	@Select("SELECT id, name, description, created_at as createdAt FROM entities WHERE id = #{id}")
	public List<Entity> getEntityById(@Param("id") String id);
}
