package com.lawer.dao;

import java.util.List;

import com.lawer.domain.Power;

public interface PowerDao extends BaseDao<Power>{

	public void deletePowerAndRole(Integer powerListId);
	
	public void addPowerAndRole(List<Integer> userTypeIds,Integer powerListId);
	
	public void deleteRoleAndPower(Integer userTypeId);
	
	public void addRoleAndPower(List<Integer> powerListIds, Integer userTypeId);
}
