package com.lawer.service.impl;

import com.lawer.dao.UserDao;
import com.lawer.domain.User;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.service.UserService;
import com.lawer.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	
	/**
	 * 手机登陆、用户名登陆
	 * @param inputName
	 * @return
	 */
	@Override
	public User loginWithPhoneEmailName(String inputName, String password){
		User u = null;
		// 密码MD5加密+
/*		MD5 md5 = new MD5();
		String ps = md5.getMD5ofStr(password);*/

		if(StringUtils.isMobile(inputName)){
			//手机登陆
			u = userDao.getUserByInputNameAndPassword(inputName, password, "phone");
		}else{
			//用户名登陆
			u = userDao.getUserByInputNameAndPassword(inputName, password, "userName");
		}
		return u;
	}

	@Override
	public PageDataList<User> findUserByParam(SearchParam param) {
		PageDataList<User> list = userDao.findPageList(param);
		return list;
	}

	@Override
	public void savaUser(User user) {
		userDao.save(user);
	}

	@Override
	public User findUserById(Integer id) {
		User user = userDao.find(id);
		return user;
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public List<User> findAllUserByParam(SearchParam param) {
		List<User> list = userDao.findByCriteria(param);
		return list;
	}

	@Override
	public void saveNewUser(User u) {
		userDao.merge(u);
	}

}
