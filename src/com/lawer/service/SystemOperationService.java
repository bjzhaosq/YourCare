package com.lawer.service;

import java.util.List;

import com.lawer.domain.SystemOperation;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface SystemOperationService {
	public SystemOperation find(int id);
	
	public void save(SystemOperation item);
	
	public List<SystemOperation> list(SystemOperation item);
	
	public PageDataList<SystemOperation> page(SearchParam param);

	public void update(SystemOperation item);
	/**
	 * 查询所有操作类型 ，level = 2
	 * @return
	 */
	public List<SystemOperation> getAllOperationType();
}
