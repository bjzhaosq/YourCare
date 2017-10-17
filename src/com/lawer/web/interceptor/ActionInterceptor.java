package com.lawer.web.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.lawer.context.Global;
import com.lawer.domain.ExceptionLog;
import com.lawer.exception.BussinessException;
import com.lawer.exception.ManageBussinessException;
import com.lawer.service.ExceptionLogService;
import com.lawer.util.IPUtils;
import com.lawer.util.StringUtils;

public class ActionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1008901298342362080L;
	private static final Logger log = Logger.getLogger(ActionInterceptor.class);

	@Autowired
	private ExceptionLogService exceptionLogService;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		String ip=IPUtils.getRemortIP(request);
		Global.ipThreadLocal.set(ip);
		log.debug("Set Ip:"+ip);
	    String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();    
        if(null == userAgent){    
        	userAgent = "";    
        }
		String actionName = invocation.getInvocationContext().getName();
		String className = invocation.getAction().getClass().getName();
		try {
			String result = invocation.invoke();
			return result;
		} catch (BussinessException e) {
			log.error("BussinessException className :"+className);
			log.error("BussinessException methodName:"+actionName);
			log.error("BussinessException message   :", e);
			String urlback = "<a href='javascript:history.go(-1)'>返回上一页</a>" ;
			if(!StringUtils.isBlank(e.getBackUrl())){
				if(!StringUtils.isBlank(e.getText())){
					urlback = "<a href='" + e.getBackUrl() + "'>" + e.getText()+">></a>";
				}else{
					urlback = "<a href='" + e.getBackUrl() + "'>" + "返回上一页</a>";
				}
			}
			request.setAttribute("backurl",urlback);
			request.setAttribute("rsmsg",e.getMessage());
			//saveExceptionLog(ip ,className , actionName , e ,1);
			return "msg";  // 这里要分  前台  和  后台
		} catch (ManageBussinessException e) {
			log.error("ManageBussinessException className :"+className);
			log.error("ManageBussinessException methodName:"+actionName);
			log.error("ManageBussinessException message   :", e);
			String urlback = "<a href='javascript:history.go(-1)'>返回上一页</a>" ;
			if(!StringUtils.isBlank(e.getBackUrl())){
				urlback ="<a href='" +  e.getBackUrl() +"'>返回上一页</a>";
			}
			request.setAttribute("backurl",urlback);
			request.setAttribute("rsmsg",e.getMessage());
			//saveExceptionLog(ip ,className , actionName , e ,2);
			return "adminmsg";  //  后台
		}catch (ParseException e) {
			log.error("ParseException className :"+className);
			log.error("ParseException methodName:"+actionName);
			log.error("ParseException message   :", e);
			String urlback = "<a href='javascript:history.go(-1)'>返回上一页</a>" ;
			request.setAttribute("backurl",urlback);
			request.setAttribute("rsmsg","页面显示异常，联系管理员！");
			saveExceptionLog(ip ,className , actionName , e ,3);
			return "msg";  // 这里要分  前台  和  后台
		} catch (Exception e) {
			log.error(e);
			log.error("Exception className :"+className);
			log.error("Exception methodName:"+actionName);
			log.error("Exception message   :", e);
			String urlback = "<a href='javascript:history.go(-1)'>返回上一页</a>" ;
			request.setAttribute("backurl",urlback);
			request.setAttribute("rsmsg","系统异常联系管理员！");
			saveExceptionLog(ip ,className , actionName , e ,0);
			return "msg";  // 这里要分  前台  和  后台
		}
	}
	
	public void saveExceptionLog(String ip ,String className , String methodName , Exception e ,int type){
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		
		ExceptionLog item = new ExceptionLog();
		item.setIp(ip);
		item.setAddTime(new Date());
		item.setClassName(className);
		item.setMethodName(methodName);
		item.setMessage(sw.toString());
		item.setType(type);
		
		this.exceptionLogService.save(item);
	}

}
