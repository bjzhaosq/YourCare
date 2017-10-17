package com.lawer.dao;

import com.lawer.domain.Rule;

public interface RuleDao extends BaseDao<Rule> {
	/**
	 * 
	 * @param nid
	 * @return
	 */
	public Rule getRuleByNid(String nid);
	
}
