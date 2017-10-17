package com.lawer.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.UserTypeDao;
import com.lawer.domain.UserType;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.service.UserTypeService;

@Service(value = "userTypeService")
@Transactional
public class UserTypeServiceImpl extends BaseServiceImpl implements UserTypeService {

	private static Logger logger = Logger.getLogger(UserTypeServiceImpl.class);
	
	@Autowired
	private UserTypeDao userTypeDao;

	@Override
	public List<UserType> findUserTypeByPowerListId(long id) {
		return userTypeDao.findUserTypeByPowerListId(id);
	}

	@Override
	public List<UserType> findUserType() {
		return userTypeDao.findUserType();
	}

	@Override
	public void saveNewlyPower(UserType userType) {
		userTypeDao.save(userType);
	}

	@Override
	public UserType findUserTypeById(Integer userTypeId) {
		return userTypeDao.find(userTypeId);
	}

	@Override
	public void deleteUserTypeById(Integer userTypeId) {
	    userTypeDao.deleteUserTypeById(userTypeId);
	}

	@Override
	public PageDataList<UserType> findUserTypeByParam(SearchParam param) {
		PageDataList<UserType> list = userTypeDao.findPageList(param);
		return list;
	}

	@Override
	public List<UserType> findAllByParam(SearchParam param) {
		List<UserType> list = userTypeDao.findByCriteria(param);
		return list;
	}

	
}
