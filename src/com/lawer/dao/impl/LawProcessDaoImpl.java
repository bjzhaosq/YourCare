package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.LawProcessDao;
import com.lawer.domain.LawProcess;

@Repository(value = "lawProcessDao")
public class LawProcessDaoImpl extends ObjectDaoImpl<LawProcess> implements LawProcessDao {

	private static Logger logger = Logger.getLogger(LawProcessDaoImpl.class);


	
}
