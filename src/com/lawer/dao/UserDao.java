package com.lawer.dao;

import com.lawer.domain.User;

public interface UserDao extends BaseDao<User>{

	public User getUserByInputNameAndPassword(String inputName, String payPassword,String type);
}
