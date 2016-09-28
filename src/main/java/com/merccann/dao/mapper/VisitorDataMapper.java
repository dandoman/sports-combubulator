package com.merccann.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.merccann.dto.VisitorDTO;

public interface VisitorDataMapper {
	@Insert("INSERT INTO visitors(id, ip_address) VALUES (#{id},#{ipAddress})")
	public int createVisitor(@Param("id") String id, @Param("ipAddress") String ipAddress);
	
	@Select("SELECT id, ip_address as ipAddress FROM visitors WHERE id = #{id}")
	public List<VisitorDTO> getVisitorById(@Param("id") String id);
	
	@Select("SELECT id, ip_address as ipAddress FROM visitors WHERE ip_address = #{ip}")
	public List<VisitorDTO> getVisitorByIp(@Param("ip") String ip);
}
