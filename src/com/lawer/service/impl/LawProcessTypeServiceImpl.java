package com.lawer.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.service.LawProcessTypeService;

@Service(value = "lawProcessTypeService")
@Transactional
public class LawProcessTypeServiceImpl extends BaseServiceImpl implements LawProcessTypeService {

	private static Logger logger = Logger.getLogger(LawProcessTypeServiceImpl.class);
	
	

}
