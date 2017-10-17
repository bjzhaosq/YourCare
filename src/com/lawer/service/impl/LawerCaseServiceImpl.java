package com.lawer.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.LawerCaseDao;
import com.lawer.dao.ProcessTypeDao;
import com.lawer.domain.LawerCase;
import com.lawer.service.LawerCaseService;

@Service(value = "lawerCaseService")
@Transactional
public class LawerCaseServiceImpl extends BaseServiceImpl implements LawerCaseService {

	private static Logger logger = Logger.getLogger(LawerCaseServiceImpl.class);

	@Autowired
	private LawerCaseDao lawerCaseDao;
	
	@Override
	public LawerCase save(LawerCase lrc) {
		LawerCase lawerCase = lawerCaseDao.save(lrc);
		return lawerCase;
	}


}
