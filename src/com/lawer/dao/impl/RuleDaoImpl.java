package com.lawer.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lawer.dao.RuleDao;
import com.lawer.domain.Rule;

@Repository(value="ruleDao")
public class RuleDaoImpl extends ObjectDaoImpl<Rule> implements RuleDao {

    @Override
	public Rule getRuleByNid(String nid){
		String sql = " from Rule where nid = ?1 ";
		Query query = em.createQuery(sql);
		query.setParameter(1, nid);
		List<Rule> list = query.getResultList();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}