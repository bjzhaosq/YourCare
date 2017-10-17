package com.lawer.service;

import java.util.List;

import com.lawer.domain.LawType;
import com.lawer.model.SearchParam;

public interface LawTypeService {

	public List<LawType> findByParam(SearchParam param);

	public LawType saveLawType(LawType lawType);
	
	
}
