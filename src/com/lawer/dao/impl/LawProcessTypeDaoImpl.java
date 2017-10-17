package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.LawProcessTypeDao;
import com.lawer.domain.LawProcessType;

@Repository(value = "lawProcessTypeDao")
public class LawProcessTypeDaoImpl extends ObjectDaoImpl<LawProcessType> implements LawProcessTypeDao {

	private static Logger logger = Logger.getLogger(LawProcessTypeDaoImpl.class);


	
}
