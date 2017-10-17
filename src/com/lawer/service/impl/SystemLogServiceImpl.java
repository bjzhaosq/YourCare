package com.lawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.SystemLogDao;
import com.lawer.domain.SystemLog;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.service.SystemLogService;

@Service(value="systemLogService")
@Transactional(propagation = Propagation.REQUIRED)
public class SystemLogServiceImpl extends BaseServiceImpl implements SystemLogService {

	@Autowired
	private SystemLogDao systemLogDao;
	
	@Override
	public SystemLog find(long id) {
		return this.systemLogDao.find(id);
	}
	
	
	@Override
	public void save(SystemLog item) {
		this.systemLogDao.save(item);
	}

	@Override
	public PageDataList<SystemLog> page(SearchParam param) {
		return this.systemLogDao.findPageList(param);
	}

	
}
