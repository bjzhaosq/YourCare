package com.lawer.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lawer.dao.SystemDao;
import com.lawer.domain.SystemConfig;

@Repository
public class SystemDaoImpl extends ObjectDaoImpl<SystemConfig> implements SystemDao  {

	@Override
	public List getsystem() {
			String jpql = "from SystemConfig where status = ?1";
			Query query = em.createQuery(jpql);
			query.setParameter(1, "1");
			return query.getResultList();
	}

	@Override
	public void updateSystemById(List<SystemConfig> list) {
		update(list);
		
	}

	@Deprecated
	public List getSystemListBySytle(int i) {
		String jpql = "from System where status = ?1";
		Query query = em.createQuery(jpql);
		query.setParameter(1, i);
		return query.getResultList();
	}

	@Override
	public void addSystemConfig(SystemConfig systemConfig) {
		save(systemConfig);
	}

	@Override
	public List getAllowIp() {
		// TODO Auto-generated method stub
		return null;
	}

}
