package com.lawer.service;

import com.lawer.domain.User;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

import java.util.List;

public interface UserService {
	
	public User loginWithPhoneEmailName(String inputName, String password);

	public PageDataList<User> findUserByParam(SearchParam param);

	public void savaUser(User user);

	public User findUserById(Integer id);

	public void update(User user);

	public List<User> findAllUserByParam(SearchParam param);

	public void saveNewUser(User u);
	
	
}
