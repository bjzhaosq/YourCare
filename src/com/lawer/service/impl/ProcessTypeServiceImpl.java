package com.lawer.service.impl;

import com.lawer.dao.ProcessTypeDao;
import com.lawer.domain.ProcessType;
import com.lawer.model.SearchParam;
import com.lawer.service.ProcessTypeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "processTypeService")
@Transactional
public class ProcessTypeServiceImpl extends BaseServiceImpl implements ProcessTypeService {

	private static Logger logger = Logger.getLogger(ProcessTypeServiceImpl.class);

	@Autowired
	private ProcessTypeDao processTypeDao;
	
	
	@Override
	public List<ProcessType> findAllByParam(SearchParam param) {
		List<ProcessType> list = processTypeDao.findByCriteria(param);
		return list;
	}
	@Override
	public List<ProcessType> findPyList() {
		return processTypeDao.findPyList();
	}

	@Override
	public ProcessType findPyById(Integer id) {
		return processTypeDao.find(id);
	}


}
