package com.lawer.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.PowerListDao;
import com.lawer.domain.PowerList;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.service.PowerListService;

@Service(value = "powerListService")
@Transactional
public class PowerListServiceImpl extends BaseServiceImpl implements PowerListService {

	private static Logger logger = Logger.getLogger(PowerListServiceImpl.class);

	@Autowired
	private PowerListDao powerListDao;
	
	@Override
	public void saveNewlyPower(PowerList P) {
		powerListDao.save(P);
	}

	@Override
	public PowerList findPowerListById(Integer powerListId) {
		return powerListDao.find(powerListId);
	}

	@Override
	public void delelePowerList(Integer powerListId) {
		powerListDao.deletePowerListById(powerListId);
	}

	@Override
	public List<PowerList> findPowerListByUserTypeId(Integer userTypeId) {
		return powerListDao.findPowerListByUserTypeId(userTypeId);
	}

	@Override
	public List<PowerList> findAllPowerList() {
		return powerListDao.findAllPowerList();
	}

	@Override
	public PageDataList<PowerList> findPowerList(SearchParam param) {
		PageDataList<PowerList> list = powerListDao.findPageList(param);
		return list;
	}

	
}
