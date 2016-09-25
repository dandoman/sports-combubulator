package com.merccann.api;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.merccann.dao.AppDao;
import com.merccann.entity.Entity;
import com.merccann.request.CreateEntityRequest;
import com.merccann.response.EntityByIdResponse;
import com.merccann.view.EntityView;

import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/api/entity")
@Log4j2
public class AppAPI {

	@Autowired
	@Setter
	private AppDao appDao;

	@ApiOperation(value = "createEntity")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public EntityView createEntity(@RequestBody CreateEntityRequest r) {
		log.info ("Received request to create entity: " + r);
		Entity createEntity = appDao.createEntity(r.getName(), r.getDescription());
		return EntityView.fromEntity(createEntity);
	}

	@ApiOperation(value = "getEntities")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<EntityView> getEntities(@RequestParam(value = "page", required = false) Integer page) {
		int pageNo = (page == null) ? 0 : page;
		return appDao.getPageOfEntities(pageNo).stream().map(EntityView::fromEntity).collect(Collectors.toList());
	}

	@ApiOperation(value = "getEntityById")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public EntityByIdResponse getEntityById(@PathVariable("id") String id) {
		Entity entityById = appDao.getEntityById(id);
		EntityByIdResponse response = new EntityByIdResponse();
		response.setEntity(EntityView.fromEntity(entityById));
		response.setGeneratedAt(new Date());
		return response;
	}
}
