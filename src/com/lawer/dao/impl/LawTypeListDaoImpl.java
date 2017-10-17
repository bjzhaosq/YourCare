package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.LawTypeDao;
import com.lawer.dao.LawTypeListDao;
import com.lawer.domain.LawType;
import com.lawer.domain.LawTypeList;

@Repository(value = "LawTypeListDao")
public class LawTypeListDaoImpl extends ObjectDaoImpl<LawTypeList> implements LawTypeListDao {

	private static Logger logger = Logger.getLogger(LawTypeListDaoImpl.class);


	
}
