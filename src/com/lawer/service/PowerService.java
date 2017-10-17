package com.lawer.service;

import java.util.List;

import com.lawer.domain.Power;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface PowerService {
	
	public void updatePowerandRole(List<Integer> userTypeIds,Integer powerListId);
	
	public void deletePowerandRole(Integer powerListId);

	public List<Power> findPower(SearchParam param);
	
	
	public void updateRoleAndPower(List<Integer> powerListIds, Integer userTypeId);
	
	public void deleteRoleandPower(Integer userTypeId);
	
	public PageDataList<Power> findAll(SearchParam param);

}
