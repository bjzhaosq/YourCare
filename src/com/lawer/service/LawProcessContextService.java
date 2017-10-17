package com.lawer.service;

import com.lawer.domain.LawProcessContext;
import com.lawer.model.SearchParam;

import java.util.List;

public interface LawProcessContextService {

    public void saveLpc(LawProcessContext lawProcessContext);

    public void updateLpc(LawProcessContext lawProcessContext);

    public LawProcessContext findlpc(Integer lawProcessContextId);

	public List<LawProcessContext> find(SearchParam param);

}
