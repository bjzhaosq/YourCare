package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.LawerCaseDao;
import com.lawer.dao.LawerHelpCaseDao;
import com.lawer.domain.LawerCase;
import com.lawer.domain.LawerHelpCase;

@Repository(value = "lawerHelpCaseDao")
public class LawerHelpCaseDaoImpl extends ObjectDaoImpl<LawerHelpCase> implements LawerHelpCaseDao {

	private static Logger logger = Logger.getLogger(LawerHelpCaseDaoImpl.class);


	
}
