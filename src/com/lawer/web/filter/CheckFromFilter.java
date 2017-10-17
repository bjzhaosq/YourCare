package com.lawer.web.filter;

import com.lawer.context.Constant;
import com.lawer.util.CheckMobile;
import com.lawer.util.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckFromFilter implements Filter {
    private final static Logger logger=Logger.getLogger(CheckFromFilter.class);

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

        HttpSession session = httpServletRequest.getSession();
        if(null == session.getAttribute(Constant.LAWYER_UA)){
            String userAgent = httpServletRequest.getHeader("User-Agent").toLowerCase();
            if(StringUtils.isBlank(userAgent)){
                userAgent = "";
            }
            if(CheckMobile.check(userAgent)){
//                logger.info("mobile");
                session.setAttribute(Constant.LAWYER_UA,"mobile");
            }else{
                session.setAttribute(Constant.LAWYER_UA,"pc");
            }

        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

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
