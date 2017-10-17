package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.LawTypeDao;
import com.lawer.domain.LawType;

@Repository(value = "lawTypeDao")
public class LawTypeDaoImpl extends ObjectDaoImpl<LawType> implements LawTypeDao {

	private static Logger logger = Logger.getLogger(LawTypeDaoImpl.class);


	
}
