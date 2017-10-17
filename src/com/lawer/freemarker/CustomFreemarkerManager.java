package com.lawer.freemarker;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lawer.dao.LinkageDao;
import com.lawer.dao.SiteDao;
import com.lawer.freemarker.directive.LinkageDirectiveModel;
import com.lawer.freemarker.directive.SiteDirectiveModel;
import com.lawer.freemarker.method.DateMethodModel;
import com.lawer.freemarker.method.DateRollMethodModel;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class CustomFreemarkerManager extends FreemarkerManager {
	private static Logger logger = Logger.getLogger(CustomFreemarkerManager.class);
	@Override  
    protected Configuration createConfiguration(ServletContext servletContext)  
        throws TemplateException { 
	    Configuration cfg = super.createConfiguration(servletContext);  
        ApplicationContext ctx= WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		try {
			 SiteDao siteDao = (SiteDao) ctx.getBean("siteDao");
			 cfg.setSharedVariable("siteDirect", new SiteDirectiveModel(siteDao));
			 LinkageDao linkageDao = (LinkageDao)ctx.getBean("linkageDao");
			 //新增自定义标签
		     cfg.setSharedVariable("linkage", new LinkageDirectiveModel(linkageDao));
			 cfg.setSharedVariable("dateformat", new DateMethodModel(linkageDao) );  
		     cfg.setSharedVariable("dateroll", new DateRollMethodModel() ); 
		} catch (Exception e) {
			logger.error(e);
		}
        return cfg;  
	}
	
	@Override
	public synchronized Configuration getConfiguration(
			ServletContext servletContext) {
		Configuration cfg = super.getConfiguration(servletContext);  
        cfg.setTemplateExceptionHandler(new CustomFreemarkerExceptionHandler());
        cfg.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
		return cfg;
	} 

}
