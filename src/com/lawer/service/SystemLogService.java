package com.lawer.service;

import com.lawer.domain.SystemLog;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface SystemLogService {
	public SystemLog find(long id);
	
	public void save(SystemLog item);
	
	public PageDataList<SystemLog> page(SearchParam param);
}
