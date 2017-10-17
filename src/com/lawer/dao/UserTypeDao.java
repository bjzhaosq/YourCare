package com.lawer.dao;

import java.util.List;

import com.lawer.domain.UserType;

public interface UserTypeDao extends BaseDao<UserType> {
	
	public List getAllUserType();
	public List getUserTypepurviewsByUserTypeId(long user_type_id);
	
	public List<UserType> findUserTypeByPowerListId(long id);
	
	public List<UserType> findUserType();
	
	public void deleteUserTypeById(Integer userTypeId);
}
