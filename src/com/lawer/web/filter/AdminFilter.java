package com.lawer.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lawer.context.Global;
import com.lawer.domain.User;
import com.lawer.util.Constant;
import com.lawer.util.StringUtils;

public class AdminFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession();
		String servletPath = getServletPath(httpServletRequest);
		String username = StringUtils.isNull(httpServletRequest
				.getParameter("username"));
		String adminUrl = Global.getString("admin_url");
		User user = (User) session.getAttribute(Constant.AUTH_USER);

		if (!StringUtils.isBlank(adminUrl) && servletPath.startsWith(adminUrl)) {
			request.setAttribute("source", "fliter");
			
			httpServletRequest.getRequestDispatcher(
					"/admin/r1X2zRXKGacq.html").forward(request, response);
		} else if (user == null) {
			if (("/admin/auth.html".equals(servletPath) || "/admin/resetadminpwd.html"
					.equals(servletPath)) && !StringUtils.isBlank(username)) {
				chain.doFilter(request, response);
			} else {
				httpServletResponse.sendRedirect(httpServletRequest
						.getContextPath() + "/notfound.html");
			}
		} else {
			chain.doFilter(request, response);
		}
		
	}
       
	protected String getServletPath(HttpServletRequest request){
        	String servletPath=request.getRequestURI();
    		return servletPath;
    	}

}
