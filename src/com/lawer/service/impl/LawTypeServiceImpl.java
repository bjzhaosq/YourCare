package com.lawer.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.LawTypeDao;
import com.lawer.domain.LawType;
import com.lawer.model.SearchParam;
import com.lawer.service.LawTypeService;

@Service(value = "lawTypeService")
@Transactional
public class LawTypeServiceImpl extends BaseServiceImpl implements LawTypeService {

	private static Logger logger = Logger.getLogger(LawTypeServiceImpl.class);

	@Autowired
	private LawTypeDao lawTypeDao;
	
	@Override
	public List<LawType> findByParam(SearchParam param) {
		List<LawType> list = lawTypeDao.findByCriteria(param);
		return list;
	}

	@Override
	public LawType saveLawType(LawType lawType) {
		LawType save = lawTypeDao.save(lawType);
		return save;
	}


}
