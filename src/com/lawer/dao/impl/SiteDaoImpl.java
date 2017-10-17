package com.lawer.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.lawer.domain.User;
import com.lawer.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.SiteDao;
import com.lawer.domain.Site;

@Repository("siteDao")
public class SiteDaoImpl extends ObjectDaoImpl<Site> implements SiteDao {
	
	private static Logger logger = Logger.getLogger(SiteDaoImpl.class);
	public List getSubSiteList(long pid) {
		return null;
	}
	@Override
	public Site getSiteByCode(String code) {
		String sql = "from Site where code=?1 and status = 1";
		Query query  = em.createQuery(sql);
		query.setParameter(1, code);
		//Site site = (Site)query.getSingleResult();
		List list  = query.getResultList();
		if(list.size()>=1){
			return (Site)list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<Site> getSiteByPid(int pid, String style) {
		String sql="from Site where pid = "+pid+" and status = 1 ";
		if(!StringUtils.isBlank(style)){
			sql = sql + " and style = '"+style+"'";
		}
		List<Site> list = null;
		try{
			Query query  = em.createQuery(sql);
			list  = query.getResultList();
		}catch(Exception e){
			logger.error(e);
			return null;
		}
		return list;
	}

	@Override
	public List getSiteList() {
		String sql="from Site where pid = 0 and status = 1 order by sort asc";
		List list = null;
		try{
			Query query  = em.createQuery(sql);
			list  = query.getResultList();
		}catch(Exception e){
			logger.error(e);
			return null;
		}		
		return list;
	}
	@Override
	public List getSubSiteList(int pid) {
		String sql="from Site where pid=? and status = 1  order by sort asc ";
		List list= null;
		try{
			Query query  = em.createQuery(sql);
			query.setParameter(1, pid);
			list  = query.getResultList();
		}catch(Exception e){
			logger.error(e);
			return null;
		}	
		return list;
	}
	@Override
	public List getAllSubSiteList(int pid) {
		String sql="from Site where pid=? and status = 1  order by sort asc ";
		List list= null;
		try{
			Query query  = em.createQuery(sql);
			query.setParameter(1, pid);
			list  = query.getResultList();
		}catch(Exception e){
			logger.error(e);
			return null;
		}	
		return list;
	}

	@Override
	public List getAllSubSiteList(int pid, User user) {
		String sql="from Site where pid=? and status = 1 and userId =? order by sort asc ";
		List list= null;
		try{
			Query query  = em.createQuery(sql);
			query.setParameter(1, pid);
			query.setParameter(2, user.getId());
			list  = query.getResultList();
		}catch(Exception e){
			logger.error(e);
			return null;
		}
		return list;
	}


}

