package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.LawerCaseDao;
import com.lawer.domain.LawerCase;

@Repository(value = "lawerCaseDao")
public class LawerCaseDaoImpl extends ObjectDaoImpl<LawerCase> implements LawerCaseDao {

	private static Logger logger = Logger.getLogger(LawerCaseDaoImpl.class);


	
}
