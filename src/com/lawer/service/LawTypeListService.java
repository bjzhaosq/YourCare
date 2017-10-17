package com.lawer.service;

import java.util.List;

import com.lawer.domain.LawTypeList;
import com.lawer.domain.User;
import com.lawer.domain.UserType;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface LawTypeListService {

	public List<LawTypeList> findAll(SearchParam param);
}
