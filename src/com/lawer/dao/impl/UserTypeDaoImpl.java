package com.lawer.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.UserTypeDao;
import com.lawer.domain.PowerList;
import com.lawer.domain.UserType;

@Repository
public class UserTypeDaoImpl  extends ObjectDaoImpl<UserType> implements UserTypeDao  {
    Logger logger = Logger.getLogger(UserTypeDaoImpl.class);

    @Override
	public List getAllUserType() {
    	String sql ="from UserType order by sort asc";
    	Query query = em.createQuery(sql);
    	List list = query.getResultList();
    	return list;
	}
    
    public List getUserTypepurviewsByUserTypeId(long user_type_id) {
    	String sql ="from UserTypepurview where user_type_id=?1";
    	Query query = em.createQuery(sql);
    	query.setParameter(1, user_type_id);
    	List list = query.getResultList();
    	return list;
    }

	@Override
	public List<UserType> findUserTypeByPowerListId(long id) {
		String sql =" select u.* from user_type u "
				  + " LEFT JOIN power p ON p.user_type_id = u.id "
				  + " LEFT JOIN power_list l ON l.id = p.power_list_id "
				  + " where p.status = 1 "
				  + " and u.status = 1 "
				  + " and l.id = "+id;
		
    	List<UserType> list = new ArrayList<UserType>();
    	Map<String, Object> param = new HashMap<String, Object>();
		list = getNamedParameterJdbcTemplate().query(sql, param, getBeanMapper(UserType.class));
    	return list;
	}

	@Override
	public List<UserType> findUserType() {
		String sql ="from UserType where status = 1";
    	Query query = em.createQuery(sql);
    	List list = query.getResultList();
    	return list;
	}

	@Override
	public void deleteUserTypeById(Integer userTypeId) {
		String sql = "from UserType where id = "+userTypeId;
		Query query = em.createQuery(sql);
    	List<UserType> list = query.getResultList();
    	if(null != list && list.size()>0){
    		this.delete(list);
    	}
		
	}
    

}
