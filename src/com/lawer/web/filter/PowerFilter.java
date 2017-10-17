package com.lawer.web.filter;

import com.lawer.context.Constant;
import com.lawer.domain.Power;
import com.lawer.domain.User;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.model.tree.SiteTree;
import com.lawer.service.ArticleService;
import com.lawer.service.PowerService;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 菜单栏获取
 * Created by ${Zsq} on 2016/7/19.
 */
public class PowerFilter implements Filter {
    private final static Logger logger=Logger.getLogger(PowerFilter.class);
    
    List<String> list = new ArrayList<String>();
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	String url = filterConfig.getInitParameter("url");
    	String[] split = url.split(",");
    	for(int i= 0;i<split.length;i++){
    		list.add(split[i]);
    	}
    }

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String servletPath = httpServletRequest.getServletPath();

		if (checkUrl(servletPath)) {
//			System.out.println("这些请求都不过滤" + servletPath);
			filterChain.doFilter(request, response);
			return;
		}

		// 权限控制
		logger.info("权限控制:" + servletPath);
		//
		String[] split = servletPath.split("/");
		ServletContext context = httpServletRequest.getSession().getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		PowerService powerService = (PowerService) ctx.getBean("powerService");
		User user = (User) httpServletRequest.getSession().getAttribute(Constant.SESSION_USER);
		
		SearchParam param = new SearchParam();
		param.addParam("status", "1");
		param.addParam("userTypeId", user.getUserTypeId());
		param.addParam("powerListId.nid", split[1]);
		PageDataList<Power> findAll = powerService.findAll(param);
		if(null != findAll && null != findAll.getList() && findAll.getList().size()>0){
			filterChain.doFilter(request, response);
			return;
		}else{
			httpServletRequest.setAttribute("msg", "您没有该权限");
			httpServletRequest.getRequestDispatcher("/404.html").forward(request, response);
		}
		
		
	}

    @Override
    public void destroy() {
    	list = null ;
    }
    
    private boolean checkUrl(String servletPath){
    	for(String url : list){
    		if(servletPath.startsWith(url)){
    			return true;
    		}
    	}
    	return false;
    }
    
}
