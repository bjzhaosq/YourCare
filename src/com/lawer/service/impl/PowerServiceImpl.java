package com.lawer.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.PowerDao;
import com.lawer.domain.Power;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.service.PowerService;

@Service(value = "powerService")
@Transactional
public class PowerServiceImpl extends BaseServiceImpl implements PowerService {

	private static Logger logger = Logger.getLogger(PowerServiceImpl.class);
	
	@Autowired
	private PowerDao powerDao;

	/**
	 * 权限绑定角色
	 */
	@Override
	public void updatePowerandRole(List<Integer> userTypeIds, Integer powerListId) {
		powerDao.deletePowerAndRole(powerListId);
		powerDao.addPowerAndRole(userTypeIds, powerListId);
	}

	@Override
	public void deletePowerandRole(Integer powerListId) {
		powerDao.deletePowerAndRole(powerListId);
	}

	@Override
	public List<Power> findPower(SearchParam param) {
		List<Power> list = powerDao.findByCriteria(param);
		return list;
	}

	/**
	 * 角色绑定权限
	 */
	@Override
	public void updateRoleAndPower(List<Integer> powerListIds, Integer userTypeId) {
		powerDao.deleteRoleAndPower(userTypeId);
		powerDao.addRoleAndPower(powerListIds, userTypeId);
	}

	@Override
	public void deleteRoleandPower(Integer userTypeId) {
		powerDao.deleteRoleAndPower(userTypeId);
		
	}

	@Override
	public PageDataList<Power> findAll(SearchParam param) {
		return powerDao.findAllPageList(param);
	}
	
}
