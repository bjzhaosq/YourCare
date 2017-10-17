package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.LawCommentDao;
import com.lawer.domain.LawComment;

@Repository(value = "lawCommentDao")
public class LawCommentDaoImpl extends ObjectDaoImpl<LawComment> implements LawCommentDao {

	private static Logger logger = Logger.getLogger(LawCommentDaoImpl.class);


	
}
