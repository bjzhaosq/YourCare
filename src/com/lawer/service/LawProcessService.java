package com.lawer.service;

import java.util.List;

import com.lawer.domain.LawProcess;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface LawProcessService {
	
	public LawProcess findLpById(Integer id);

	public void updateLp(LawProcess lawProcess);

	public void save(List<LawProcess> lawProcessList);

	public List<LawProcess> findAllByParam(SearchParam param);

	public PageDataList<LawProcess> findPageList(SearchParam param);

	public LawProcess save(LawProcess lawProcess);
	
	
}
