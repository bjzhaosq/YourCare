package com.lawer.service;

import java.util.List;

import com.lawer.model.SystemInfo;

public interface SystemService {
	public SystemInfo getSystemInfo();
	
	public List getSystemInfoForList();
	/**
	 * 根据模块显示系统设置信息
	 * @return
	 */
	public List getSystemInfoForListBysytle(int i);
	/**
	 * 
	 * @param list<SystemConfig>
	 */
	public void updateSystemInfo(List list);

	/**
	 *
	 * @param url
	 * url 为网站根目录路径
	 */
	public void clean(String url);
	
	public void updateSystem(long id);
	
	/**
	 * 更新 系统config
	 */
	void updateSystemInfo();
}
