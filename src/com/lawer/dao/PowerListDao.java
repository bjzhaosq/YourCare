package com.lawer.dao;

import java.util.List;

import com.lawer.domain.PowerList;

public interface PowerListDao extends BaseDao<PowerList>{
	
	public void deletePowerListById(Integer powerListId);
	
	public List<PowerList> findPowerListByUserTypeId(Integer userTypeId);
	
	public List<PowerList> findAllPowerList();


}
