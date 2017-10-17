package com.lawer.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.CustomerDao;
import com.lawer.dao.LawCaseDao;
import com.lawer.domain.LawCase;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.service.LawCaseService;

@Service(value = "lawCaseService")
@Transactional
public class LawCaseServiceImpl extends BaseServiceImpl implements LawCaseService {

	private static Logger logger = Logger.getLogger(LawCaseServiceImpl.class);

	@Autowired
	private LawCaseDao lawCaseDao;
	
	@Override
	public PageDataList<LawCase> findAllByParam(SearchParam param) {
		PageDataList<LawCase> list = lawCaseDao.findPageList(param);
		return list;
	}

	@Override
	public LawCase saveLawCase(LawCase lawCase) {
		LawCase save = lawCaseDao.save(lawCase);
		return save;
	}

	@Override
	public void updateLawCase(LawCase lawCase) {
		lawCaseDao.update(lawCase);
	}

}
