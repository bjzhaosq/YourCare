package com.lawer.service;

import com.lawer.domain.Article;
import com.lawer.domain.ScrollPic;
import com.lawer.domain.Site;
import com.lawer.domain.User;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.model.tree.SiteTree;

import java.util.List;

public interface ArticleService {
	public Site getSiteByCode(String code);
	public Site getSiteById(int id);
	public List getSubSiteList(int pid);
	public SiteTree getSiteTree();
	public SiteTree getSiteTree(User user);
	public SiteTree getSiteTree(String code);
	public SiteTree getSiteTree(int id);
	public void modifySite(Site site);
	public void addSite(Site site);
	public void delSite(int siteId);
	public PageDataList getArticleList(int page);
	public Article getArticle(int id);
	public void addArticle(Article a,String files);
	public void delArticle(int id);
	public void modifyArticle(Article a,String url);
	public Article getArticleByEname(String ename);
	public PageDataList getArticleList(Site site,int page,int row);
	public boolean checkSiteCode(String code);
	public List<Site> getSubSitePageDataList(SearchParam param);
	public PageDataList getArticleList(SearchParam param);
	public PageDataList<ScrollPic>  getScrollPicList(SearchParam param);
	public void delScrollPic(long id);
	public void modifyScrollPic(ScrollPic sp);
	public ScrollPic getScrollPicListById(long id);
	public void addScrollPic(ScrollPic sp);
	public List<Article> getArticleList2(SearchParam param);
	public void modifyArticle(Article a);
	public PageDataList<Article> getArticleListByFlag(Site site);
	public PageDataList<Article> getArticleListByFlagSite(Site site);
	public List<Site> getSiteByPid(int pid, String style);
}
