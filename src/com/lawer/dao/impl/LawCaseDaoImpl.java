package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.LawCaseDao;
import com.lawer.domain.LawCase;

@Repository(value = "lawCaseDao")
public class LawCaseDaoImpl extends ObjectDaoImpl<LawCase> implements LawCaseDao {

	private static Logger logger = Logger.getLogger(LawCaseDaoImpl.class);


	
}
