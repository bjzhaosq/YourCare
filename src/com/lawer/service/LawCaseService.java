package com.lawer.service;

import com.lawer.domain.LawCase;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface LawCaseService {

	public PageDataList<LawCase> findAllByParam(SearchParam param);

	public LawCase saveLawCase(LawCase lawCase);

	public void updateLawCase(LawCase lawCase);
	
	
	
}
