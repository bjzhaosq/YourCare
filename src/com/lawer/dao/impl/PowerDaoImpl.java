package com.lawer.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.codec.language.bm.Lang;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.PowerDao;
import com.lawer.domain.Power;
import com.lawer.domain.PowerList;
import com.lawer.domain.UserType;

@Repository(value = "powerDao")
public class PowerDaoImpl extends ObjectDaoImpl<Power> implements PowerDao {

	private static Logger logger = Logger.getLogger(PowerDaoImpl.class);

	@Override
	public void deletePowerAndRole(Integer powerListId) {
		String sql = "from Power where powerListId = "+powerListId;
		Query query = em.createQuery(sql);
    	List<Power> list = query.getResultList();
    	if(null != list && list.size()>0){
    		this.delete(list);
    	}
	}

	@Override
	public void addPowerAndRole(List<Integer> userTypeIds, Integer powerListId) {
		PowerList powerList = new PowerList();
		powerList.setId(powerListId);
		for(Integer i : userTypeIds){
			UserType userType = new UserType();
			userType.setId(i);
			Power power = new Power();
			power.setPowerListId(powerList);
			power.setUserTypeId(userType);
			power.setStatus("1");
			power.setAddtime(new Date());
			this.merge(power);
		}
		
	}

	@Override
	public void deleteRoleAndPower(Integer userTypeId) {
		String sql = "from Power where userTypeId = "+userTypeId;
		Query query = em.createQuery(sql);
    	List<Power> list = query.getResultList();
    	if(null != list && list.size()>0){
    		this.delete(list);
    	}
		
	}

	@Override
	public void addRoleAndPower(List<Integer> powerListIds, Integer userTypeId) {
		UserType userType = new UserType();
		userType.setId(userTypeId);
		for (Integer i : powerListIds) {
			PowerList powerList = new PowerList();
			powerList.setId(i);
			Power power = new Power();
			power.setPowerListId(powerList);
			power.setUserTypeId(userType);
			power.setStatus("1");
			power.setAddtime(new Date());
			this.merge(power);
		}
	}


	
}
