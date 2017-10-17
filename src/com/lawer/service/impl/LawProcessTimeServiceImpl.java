package com.lawer.service.impl;

import com.lawer.dao.LawProcessTimeDao;
import com.lawer.domain.LawProcessTime;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.service.LawProcessTimeService;

@Service(value = "lawProcessTimeService")
@Transactional
public class LawProcessTimeServiceImpl extends BaseServiceImpl implements LawProcessTimeService {

	private static Logger logger = Logger.getLogger(LawProcessTimeServiceImpl.class);

	@Autowired
	private LawProcessTimeDao lawProcessTimeDao;

	@Override
	public void savelpt(LawProcessTime lawProcessTime) {
		lawProcessTimeDao.save(lawProcessTime);
	}
}
