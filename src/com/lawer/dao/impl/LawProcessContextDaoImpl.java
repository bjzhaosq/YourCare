package com.lawer.dao.impl;

import com.lawer.dao.LawProcessContextDao;
import com.lawer.domain.LawProcessContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "LawProcessContextDao")
public class LawProcessContextDaoImpl extends ObjectDaoImpl<LawProcessContext> implements LawProcessContextDao {

	private static Logger logger = Logger.getLogger(LawProcessContextDaoImpl.class);

}
