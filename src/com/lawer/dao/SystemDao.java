package com.lawer.dao;

import java.util.List;

import com.lawer.domain.SystemConfig;

public interface SystemDao extends BaseDao<SystemConfig>{
	public List getsystem();
	
	/**
	 * 更新SystemConfig信息
	 * @param list
	 * 
	 */
	public void updateSystemById(List<SystemConfig> list);
	
	
	/**
	 * 根据模块获取系统设置
	 * @param i
	 * @return
	 */
	public List getSystemListBySytle(int i);
	
	public void addSystemConfig(SystemConfig systemConfig);
	
	public List getAllowIp();
}

