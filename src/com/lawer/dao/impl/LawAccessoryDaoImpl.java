package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.LawAccessoryDao;
import com.lawer.domain.LawAccessory;

@Repository(value = "lawAccessoryDao")
public class LawAccessoryDaoImpl extends ObjectDaoImpl<LawAccessory> implements LawAccessoryDao {

	private static Logger logger = Logger.getLogger(LawAccessoryDaoImpl.class);


	
}
