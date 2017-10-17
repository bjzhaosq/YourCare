package com.lawer.web.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.lawer.dao.ArticleDao;
import com.lawer.domain.Article;
import com.lawer.domain.Site;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.model.OrderFilter.OrderType;
import com.lawer.model.Page;
import com.lawer.model.tree.SiteTree;
import com.lawer.service.ArticleService;

/**
 * 点击链接跳转页面
 * Created by ${Zsq} on 2016/7/21.
 */

@Namespace("/link")
@ParentPackage("p2p-default")
public class HyperlinkAction extends BaseAction {
    private static Logger logger = Logger.getLogger(HyperlinkAction.class);

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDao articleDao;

    @Action(value = "/runxin", results = {
            @Result(name = "singlePage", type = "ftl", location = "/aboutus.html") ,
            @Result(name = "listPage", type = "ftl", location = "/newsList.html")})
    public String hyperlink() {
        String code = paramString("code");
        String style = paramString("style");
        String result = "singlePage";
        int pid = paramInt("pid");
        int id = paramInt("id");
        int siteId = paramInt("siteId");
        int page = paramInt("page");
        Site site_ = articleService.getSiteByCode(code);
        List<Site> list_ = articleService.getSubSiteList(site_.getSiteId());
	    if(list_.size()!=0&&list_!=null&&pid == 0){
	        	Site site2 = list_.get(0);
	        	code = site2.getCode();
	        	pid = site2.getPid();
	        	style = site2.getStyle();
	    }
        SiteTree siteTree = new SiteTree();
        if (StringUtils.isNotBlank(style)) {
            if("0".equals(style)){
            	Site site = articleService.getSiteByCode(code);
            	PageDataList xianList = null;
                SearchParam spa = SearchParam.getInstance();
         		spa.addPage(page, Page.ROWS);
         		spa.addParam("status", 1);
         		spa.addParam("site", site);
         		spa.addOrder(OrderType.DESC, "flag");
         		spa.addOrder(OrderType.DESC, "publish");
         		xianList = articleDao.findPageList(spa);
            	request.setAttribute("xianList", xianList.getList());
            	request.setAttribute("pageList", xianList.getPage());
            	
                result = "listPage";
            }
            if(pid == 0){
                //大菜单
                siteTree = articleService.getSiteTree(code);
            }else{
                //小菜单
                siteTree = articleService.getSiteTree(pid);
            }
        }else{
        	siteTree = articleService.getSiteTree(code);
        }
        
       
        
        Article article = articleService.getArticle(id);
        request.setAttribute("article", article);
        
        Site site = articleService.getSiteByCode(code);
        request.setAttribute("siteContent",site);

        //单页和列表页  banner图片
        Site s = (Site)siteTree.getChild().get(0).getModel();
        String bannerUrl = s.getContentName();

        request.setAttribute("bannerUrl",bannerUrl);
        request.setAttribute("siteTree",siteTree);
        return result;
    }

}
