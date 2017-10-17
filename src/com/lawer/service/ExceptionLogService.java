package com.lawer.service;

import com.lawer.domain.ExceptionLog;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface ExceptionLogService {
	public ExceptionLog find(long id);
	
	public void save(ExceptionLog item);
	
	public PageDataList<ExceptionLog> page(SearchParam param);
}
