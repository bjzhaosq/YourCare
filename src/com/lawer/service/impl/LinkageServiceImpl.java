package com.lawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.LinkageDao;
import com.lawer.domain.Linkage;
import com.lawer.service.LinkageService;

@Service(value="linkageService")
@Transactional
public class LinkageServiceImpl implements LinkageService {

	@Autowired
	LinkageDao linkageDao;
	@Override
	public Linkage getLinkageById(int id) {
		return linkageDao.getLinkageById(id);
	}

}
