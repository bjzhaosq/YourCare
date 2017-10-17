package com.lawer.web.filter;

import com.lawer.model.tree.SiteTree;
import com.lawer.service.ArticleService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 菜单栏获取
 * Created by ${Zsq} on 2016/7/19.
 */
public class MenuFilter implements Filter {
    private final static Logger logger=Logger.getLogger(MenuFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getServletPath();
        String[] urls = url.split("/");
        List<String> list = Arrays.asList(urls);
        if(!list.contains("admin")){
            ServletContext context = httpServletRequest.getSession().getServletContext();
            ApplicationContext ctx= WebApplicationContextUtils.getRequiredWebApplicationContext(context);
            ArticleService articleService = (ArticleService) ctx.getBean("articleService");
            SiteTree siteTree = articleService.getSiteTree();
            request.setAttribute("site", siteTree);
            logger.info("菜单初始化完成");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
