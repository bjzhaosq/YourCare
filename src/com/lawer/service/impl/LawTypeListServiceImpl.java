package com.lawer.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.LawCaseDao;
import com.lawer.dao.LawTypeListDao;
import com.lawer.domain.LawTypeList;
import com.lawer.model.SearchParam;
import com.lawer.service.LawTypeListService;
import com.lawer.service.LawTypeService;

@Service(value = "lawTypeListService")
@Transactional
public class LawTypeListServiceImpl extends BaseServiceImpl implements LawTypeListService {

	private static Logger logger = Logger.getLogger(LawTypeListServiceImpl.class);

	@Autowired
	private LawTypeListDao lawTypeListDao;
	
	@Override
	public List<LawTypeList> findAll(SearchParam param) {
		List<LawTypeList> list = lawTypeListDao.findByCriteria(param);
		return list;
	}

}
