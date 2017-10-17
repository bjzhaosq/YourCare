package com.lawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.RuleDao;
import com.lawer.domain.Rule;
import com.lawer.service.RuleService;

@Transactional
@Service(value="ruleService")
public class RuleServiceImpl extends BaseServiceImpl implements RuleService {

	@Autowired
	RuleDao ruleDao;
	
	@Override
	public Rule getRuleByNid(String nid){
		return ruleDao.getRuleByNid(nid);
	}

}
