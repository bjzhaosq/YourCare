package com.lawer.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lawer.context.Global;
import com.lawer.model.SystemInfo;
import com.lawer.service.SystemService;

public class WebConfigContextListener implements ServletContextListener {
	private static Logger logger=Logger.getLogger(WebConfigContextListener.class);
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context=event.getServletContext();
		ApplicationContext ctx= WebApplicationContextUtils.getRequiredWebApplicationContext(context);

		SystemService systemService=(SystemService)ctx.getBean("systemService");

		SystemInfo info = systemService.getSystemInfo();
		Global.SYSTEMINFO = info;
		setWebConfig(context,info);
		
		//检查系统、数据库版本是否一致！
	    checkVersion();

	}

	private void setWebConfig(ServletContext context, SystemInfo info){

		String[] webinfo=Global.SYSTEMNAME;
		for(String s:webinfo){
			logger.info(s+":"+info.getValue(s));
			context.setAttribute(s, info.getValue(s));
		}
		context.setAttribute("webroot", context.getContextPath());
	}
	
	/**
	 * 校验系统版本
	 */
	public void checkVersion(){
		String dbVersion=Global.getVersion();
		String sysVersion=Global.VERSION;
		logger.info("数据库版本："+dbVersion);
		logger.info("系统版本:"+sysVersion);
		if(!Global.getVersion().equals(Global.VERSION)){
			throw new RuntimeException("数据库版本与系统版本不一致，请更新数据库！");
		}
	}

}
