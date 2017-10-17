package com.lawer.dao.impl;

import com.lawer.dao.WechatPhoneDao;
import com.lawer.domain.WechatPhone;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository(value = "wechatPhoneDao")
public class WechatPhoneDaoImpl extends ObjectDaoImpl<WechatPhone> implements WechatPhoneDao {

	private static Logger logger = Logger.getLogger(WechatPhoneDaoImpl.class);

	@Override
	public List<WechatPhone> findWechatPhone() {
		String sql="from WechatPhone where status = 1";
		List<WechatPhone> list = null;
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
	public WechatPhone findWechatPhoneByName(String name) {
		String sql="from WechatPhone where name =?1 and status = 1";
		List<WechatPhone> list = null;
		try{
			Query query  = em.createQuery(sql);
			query.setParameter(1,name);
			list  = query.getResultList();
		}catch(Exception e){
			logger.error(e);
			return null;
		}

		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
