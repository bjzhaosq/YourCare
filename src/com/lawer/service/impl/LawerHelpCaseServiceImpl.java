package com.lawer.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.LawerCaseDao;
import com.lawer.dao.LawerHelpCaseDao;
import com.lawer.dao.ProcessTypeDao;
import com.lawer.domain.LawerCase;
import com.lawer.domain.LawerHelpCase;
import com.lawer.service.LawerCaseService;
import com.lawer.service.LawerHelpCaseService;

@Service(value = "lawerHelpCaseService")
@Transactional
public class LawerHelpCaseServiceImpl extends BaseServiceImpl implements LawerHelpCaseService {

	private static Logger logger = Logger.getLogger(LawerHelpCaseServiceImpl.class);

	
	@Autowired
	private LawerHelpCaseDao lawerHelpCaseDao;
	
	@Override
	public void save(List<LawerHelpCase> list) {
		lawerHelpCaseDao.save(list);
	}


}
