package com.lawer.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.UserDao;
import com.lawer.domain.User;

@Repository(value = "userDao")
public class UserDaoImpl extends ObjectDaoImpl<User> implements UserDao {

	private static Logger logger = Logger.getLogger(UserDaoImpl.class);


	/**
	 * 根据 用户名，email,phone  ps  查询用户
	 * @param username
	 * @param payPassword
	 * @param type
	 * @return
	 */
	@Override
	public User getUserByInputNameAndPassword(String inputName, String payPassword,String type) {
		String jpql = "";
		if("userName".equals(type)){//用户名 登陆
			jpql = "from User where username = ?1 and password = ?2";
		}
		if("phone".equals(type)){//手机号 登陆
			jpql = "from User where phone = ?1 and password = ?2";
		}
		
		Query query = em.createQuery(jpql);
		query.setParameter(1, inputName);
		query.setParameter(2, payPassword);
		List list = query.getResultList();
		if (list != null && list.size() >= 1) {
			return (User) list.get(0);
		} else {
			return null;
		}
	}
}
