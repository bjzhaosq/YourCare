package com.lawer.web.filter;

import com.lawer.domain.User;
import com.lawer.util.Constant;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单栏获取
 * Created by ${Zsq} on 2016/7/19.
 */
public class LoginFilter implements Filter {
    private final static Logger logger=Logger.getLogger(LoginFilter.class);
    
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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String servletPath = httpServletRequest.getServletPath();

		if (checkUrl(servletPath)) {
			filterChain.doFilter(request, response);
			return;
		}

		//判断是否登陆
//		System.out.println("登陆判断:"+servletPath);
		User user = (User) httpServletRequest.getSession().getAttribute(Constant.SESSION_USER);
		if(null == user){
			((HttpServletResponse)response).sendRedirect("/login/login.html");
			return;
//			String ua = (String) httpServletRequest.getSession().getAttribute(Constant.LAWYER_UA);
//			if(!StringUtils.isBlank(ua) && "pc".equals(ua)){
//				((HttpServletResponse)response).sendRedirect("/login/login.html");
//				return;
//			}else{
//				((HttpServletResponse)response).sendRedirect("/mobile/login/login.html");
//				return;
//			}

		}
		filterChain.doFilter(request, response);
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
