package com.lawer.service;


import com.lawer.domain.ProcessType;
import com.lawer.model.SearchParam;

import java.util.List;
public interface ProcessTypeService {

	List<ProcessType> findAllByParam(SearchParam param);
	
    public List<ProcessType> findPyList();

    public ProcessType findPyById(Integer id);
	
}
