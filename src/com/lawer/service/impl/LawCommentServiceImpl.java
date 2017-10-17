package com.lawer.service.impl;

import com.lawer.dao.LawCommentDao;
import com.lawer.domain.LawComment;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.service.LawCommentService;

@Service(value = "lawCommentService")
@Transactional
public class LawCommentServiceImpl extends BaseServiceImpl implements LawCommentService {

	private static Logger logger = Logger.getLogger(LawCommentServiceImpl.class);

	@Autowired
	private LawCommentDao lawCommentDao;

	@Override
	public void saveLawComment(LawComment lawComment) {
		lawCommentDao.save(lawComment);
	}
}
