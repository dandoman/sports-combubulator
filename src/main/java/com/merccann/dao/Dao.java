package com.merccann.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Dao {

	protected <E> E getSingleResultById(List<E> resultList, String id) {
		if (resultList == null) {
			return null;
		}
		if(resultList.size() > 1) {
			log.error("Found more than one entity for id " + id);
		}
		return resultList.stream().findFirst().orElse(null);
	}
	
	protected <E> List<E> getAsList(List<E> resultList) {
		if (resultList == null) {
			return new ArrayList<>();
		}
		return resultList;
	}
}