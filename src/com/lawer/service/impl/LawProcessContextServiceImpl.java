package com.lawer.service.impl;

import com.lawer.dao.LawProcessContextDao;
import com.lawer.domain.LawProcessContext;
import com.lawer.model.SearchParam;
import com.lawer.service.LawProcessContextService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "LawProcessContextService")
@Transactional
public class LawProcessContextServiceImpl extends BaseServiceImpl implements LawProcessContextService {

	private static Logger logger = Logger.getLogger(LawProcessContextServiceImpl.class);

	@Autowired
	private LawProcessContextDao lawProcessContextDao;

	@Override
	public void saveLpc(LawProcessContext lawProcessContext) {
		lawProcessContextDao.save(lawProcessContext);
	}

	@Override
	public void updateLpc(LawProcessContext lawProcessContext) {
		lawProcessContextDao.update(lawProcessContext);
	}

	@Override
	public LawProcessContext findlpc(Integer lawProcessContextId) {
		return lawProcessContextDao.find(lawProcessContextId);
	}

	@Override
	public List<LawProcessContext> find(SearchParam param) {
		List<LawProcessContext> list = lawProcessContextDao.findByCriteria(param);
		return list;
	}
}
