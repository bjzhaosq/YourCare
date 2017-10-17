package com.lawer.dao.impl;

import com.lawer.dao.WechatUserDao;
import com.lawer.domain.WechatUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository(value = "wechatUserDao")
public class WechatUserDaoImpl extends ObjectDaoImpl<WechatUser> implements WechatUserDao {

	private static Logger logger = Logger.getLogger(WechatUserDaoImpl.class);

	@Override
	public WechatUser findWechatUserByOpenid(String openid) {
		String sql = "from WechatUser where openid = '"+openid+"'";
		Query query = em.createQuery(sql);
		List<WechatUser> list = query.getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
