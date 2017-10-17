package com.lawer.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.PowerListDao;
import com.lawer.domain.Power;
import com.lawer.domain.PowerList;
import com.lawer.domain.UserType;

@Repository(value = "powerListDao")
public class PowerListDaoImpl extends ObjectDaoImpl<PowerList> implements PowerListDao {

	private static Logger logger = Logger.getLogger(PowerListDaoImpl.class);

	@Override
	public void deletePowerListById(Integer powerListId) {
		String sql = "from PowerList where id = "+powerListId;
		Query query = em.createQuery(sql);
    	List<PowerList> list = query.getResultList();
    	if(null != list && list.size()>0){
    		this.delete(list);
    	}
		
	}

	@Override
	public List<PowerList> findPowerListByUserTypeId(Integer userTypeId) {
		String sql =" select l.* from power_list l "
				  + " LEFT JOIN power p ON p.power_list_id = l.id "
				  + " LEFT JOIN user_type u ON u.id = p.user_type_id "
				  + " where p.status = 1 "
				  + " and l.status = 1 "
				  + " and u.id = "+userTypeId;
		
		List<PowerList> list = new ArrayList<PowerList>();
		Map<String, Object> param = new HashMap<String, Object>();
		list = getNamedParameterJdbcTemplate().query(sql, param, getBeanMapper(PowerList.class));
		return list;
	}

	@Override
	public List<PowerList> findAllPowerList() {
		String sql = "from PowerList where status = 1";
		Query query = em.createQuery(sql);
    	List<PowerList> list = query.getResultList();
    	if(null != list && list.size()>0){
    		return list;
    	}
		return null;
	}


	
}
