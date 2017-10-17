package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.ProcessTypeDao;
import com.lawer.domain.ProcessType;

import javax.persistence.Query;
import java.util.List;

@Repository(value = "processTypeDao")
public class ProcessTypeDaoImpl extends ObjectDaoImpl<ProcessType> implements ProcessTypeDao {

	private static Logger logger = Logger.getLogger(ProcessTypeDaoImpl.class);


	@Override
	public List<ProcessType> findPyList() {
		String sql = "from ProcessType where status = '1'";
		Query query = em.createQuery(sql);
		List<ProcessType> list = query.getResultList();
		if(null != list && list.size()>0){
			return list;
		}
		return null;
	}
}
