package com.lawer.service.impl;

import java.util.List;

import com.lawer.dao.LawProcessDao;
import com.lawer.domain.LawProcess;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.service.LawProcessService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "lawProcessService")
@Transactional
public class LawProcessServiceImpl extends BaseServiceImpl implements LawProcessService {

	private static Logger logger = Logger.getLogger(LawProcessServiceImpl.class);

	@Autowired
	private LawProcessDao lawProcessDao;

	@Override
	public LawProcess findLpById(Integer id) {
		return lawProcessDao.find(id);
	}

	@Override
	public void updateLp(LawProcess lawProcess) {
		lawProcessDao.update(lawProcess);
	}

	@Override
	public void save(List<LawProcess> lawProcessList) {
		lawProcessDao.save(lawProcessList);
	}

	@Override
	public List<LawProcess> findAllByParam(SearchParam param) {
		List<LawProcess> list = lawProcessDao.findByCriteria(param);
		return list;
	}

	@Override
	public PageDataList<LawProcess> findPageList(SearchParam param) {
		PageDataList<LawProcess> list = lawProcessDao.findPageList(param);
		return list;
	}

	@Override
	public LawProcess save(LawProcess lawProcess) {
		LawProcess save = lawProcessDao.save(lawProcess);
		return save;
	}
}
