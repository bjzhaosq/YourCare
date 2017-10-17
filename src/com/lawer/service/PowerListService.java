package com.lawer.service;

import java.util.List;

import com.lawer.domain.PowerList;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface PowerListService {
	
	public void saveNewlyPower(PowerList P);
	
	public PowerList findPowerListById(Integer powerListId);
	
	public void delelePowerList(Integer powerListId);
	
	public List<PowerList> findPowerListByUserTypeId(Integer userTypeId);
	
	public List<PowerList> findAllPowerList();

	public PageDataList<PowerList> findPowerList(SearchParam param);
}
