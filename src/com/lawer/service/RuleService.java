package com.lawer.service;

import com.lawer.domain.Rule;
import com.lawer.domain.User;

public interface RuleService {
	/**
	 * 获取对应类型的规则
	 * @param nid
	 * @return
	 */
	public Rule getRuleByNid(String nid);
}
