package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.LawProcessTimeDao;
import com.lawer.domain.LawProcessTime;

@Repository(value = "lawProcessTimeDao")
public class LawProcessTimeDaoImpl extends ObjectDaoImpl<LawProcessTime> implements LawProcessTimeDao {

	private static Logger logger = Logger.getLogger(LawProcessTimeDaoImpl.class);


	
}
