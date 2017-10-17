package com.lawer.service;

import java.util.List;

import com.lawer.domain.UserType;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface UserTypeService {
	
	public List<UserType> findUserTypeByPowerListId(long id);
	
	public List<UserType> findUserType();

	public void saveNewlyPower(UserType userType);
	
	public UserType findUserTypeById(Integer userTypeId);
	
	public void deleteUserTypeById(Integer userTypeId);

	public PageDataList<UserType> findUserTypeByParam(SearchParam param);

	public List<UserType> findAllByParam(SearchParam param);
}
