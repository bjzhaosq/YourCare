package com.lawer.service;

import java.util.List;

import com.lawer.domain.LawAccessory;
import com.lawer.model.SearchParam;

public interface LawAccessoryService {


	void saveLawAccessory(List<LawAccessory> list);

	List<LawAccessory> findLawAccessory(SearchParam param);

	void updateLawAccessory(LawAccessory lawAccessory);
	
}
