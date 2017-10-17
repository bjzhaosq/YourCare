package com.lawer.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.LawAccessoryDao;
import com.lawer.dao.LawCaseDao;
import com.lawer.domain.LawAccessory;
import com.lawer.model.SearchParam;
import com.lawer.service.LawAccessoryService;

@Service(value = "lawAccessoryService")
@Transactional
public class LawAccessoryServiceImpl extends BaseServiceImpl implements LawAccessoryService {

	private static Logger logger = Logger.getLogger(LawAccessoryServiceImpl.class);

	@Autowired
	private LawAccessoryDao lawAccessoryDao;
	
	@Override
	public void saveLawAccessory(List<LawAccessory> list) {
		lawAccessoryDao.save(list);
		
	}

	@Override
	public List<LawAccessory> findLawAccessory(SearchParam param) {
		List<LawAccessory> list = lawAccessoryDao.findByCriteria(param);
		return list;
	}

	@Override
	public void updateLawAccessory(LawAccessory lawAccessory) {
		lawAccessoryDao.update(lawAccessory);
	}


}
