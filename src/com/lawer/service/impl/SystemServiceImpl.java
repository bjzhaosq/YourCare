package com.lawer.service.impl;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.lawer.context.Global;
import com.lawer.dao.SystemDao;
import com.lawer.domain.SystemConfig;
import com.lawer.model.SystemInfo;
import com.lawer.service.SystemService;

@Service(value="systemService")
@Transactional
public class SystemServiceImpl implements SystemService {
	
	private static Logger logger = Logger.getLogger(SystemServiceImpl.class);
	
	@Autowired
	private SystemDao systemDao;
	
	@Override
	public SystemInfo getSystemInfo() {
		SystemInfo info = new SystemInfo(); 
		List list = systemDao.getsystem();
		for (int i = 0; i < list.size(); i++) {
			SystemConfig sys = (SystemConfig) list.get(i);
			info.addConfig(sys);
		}
		return info;
	}

	@Override
	public List getSystemInfoForList() {		
		return systemDao.getsystem();
	}

	/**
	 * 根据模块显示系统设置信息
	 * @return
	 */
	@Override
	public List getSystemInfoForListBysytle(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSystemInfo(List list) {
		systemDao.update(list);
	}
	
	/**
	 * 
	 * @param url
	 * url 为网站根目录路径
	 */
	@Override
	public void clean(String url) {
		
	}

	@Override
	@Transactional
	public void updateSystem(long id) {
		SystemConfig sc=new SystemConfig();
    	sc=systemDao.find(5);
    	sc.setName(sc.getName()+"Test");
    	sc.setValue("sdfsdfsd");
	}
	
	/**
	 * 更新 系统config
	 */
	@Override
	public void updateSystemInfo(){
		
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		ServletContext context = wac.getServletContext();
		
		SystemInfo info = getSystemInfo();
		Global.SYSTEMINFO = info;
		setWebConfig(context,info);
	}
	
	private void setWebConfig(ServletContext context, SystemInfo info){

		String[] webinfo=Global.SYSTEMNAME;
		for(String s:webinfo){
			logger.debug(s+":"+info.getValue(s));
			context.setAttribute(s, info.getValue(s));
		}
		context.setAttribute("webroot", context.getContextPath());
	}

}
