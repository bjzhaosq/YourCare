package com.lawer.service.impl;

import com.lawer.dao.ArticleDao;
import com.lawer.dao.ScrollPicDao;
import com.lawer.dao.SiteDao;
import com.lawer.domain.Article;
import com.lawer.domain.ScrollPic;
import com.lawer.domain.Site;
import com.lawer.domain.User;
import com.lawer.model.OrderFilter.OrderType;
import com.lawer.model.Page;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchFilter.Operator;
import com.lawer.model.SearchParam;
import com.lawer.model.tree.SiteTree;
import com.lawer.model.tree.Tree;
import com.lawer.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	ArticleDao articleDao;
	@Autowired
	SiteDao siteDao;
	@Autowired
	ScrollPicDao scrollPicDao;

	@Override
	public Site getSiteByCode(String code) {
		return siteDao.getSiteByCode(code);
	}

	public Site getSiteById(int id) {
		return siteDao.find(id);
	}

	public SiteTree getSiteTree() {
		List<Site> list = siteDao.getSiteList();
		SiteTree tree = new SiteTree(null, new ArrayList<Tree>());
		for (Site s : list) {
			SiteTree secTree = new SiteTree(s, new ArrayList<Tree>());
			List<Site> sublist = siteDao.getAllSubSiteList(s.getSiteId());
			for (Site ss : sublist) {
				SiteTree subTree = new SiteTree(ss, null);
				secTree.addChild(subTree);
			}
			tree.addChild(secTree);
		}
		return tree;
	}

	public SiteTree getSiteTree(User user) {
		List<Site> list = siteDao.getSiteList();
		SiteTree tree = new SiteTree(null, new ArrayList<Tree>());
		for (Site s : list) {
			SiteTree secTree = new SiteTree(s, new ArrayList<Tree>());
			List<Site> sublist = siteDao.getAllSubSiteList(s.getSiteId(),user);
			for (Site ss : sublist) {
				SiteTree subTree = new SiteTree(ss, null);
				secTree.addChild(subTree);
			}
			tree.addChild(secTree);
		}
		return tree;
	}

	public SiteTree getSiteTree(String code) {
		Site site = siteDao.getSiteByCode(code);
		SiteTree tree = new SiteTree(null, new ArrayList<Tree>());
		SiteTree secTree = new SiteTree(site, new ArrayList<Tree>());
		List<Site> sublist = siteDao.getAllSubSiteList(site.getSiteId());
		for (Site ss : sublist) {
			SiteTree subTree = new SiteTree(ss, null);
			secTree.addChild(subTree);
		}
		tree.addChild(secTree);
		return tree;
	}

	public SiteTree getSiteTree(int id) {
		Site site = siteDao.find(id);
		SiteTree tree = new SiteTree(null, new ArrayList<Tree>());
		SiteTree secTree = new SiteTree(site, new ArrayList<Tree>());
		List<Site> sublist = siteDao.getAllSubSiteList(site.getSiteId());
		for (Site ss : sublist) {
			SiteTree subTree = new SiteTree(ss, null);
			secTree.addChild(subTree);
		}
		tree.addChild(secTree);
		return tree;
	}

	public void modifySite(Site site) {
		siteDao.update(site);
	}

	public void addSite(Site site) {
		siteDao.save(site);
	}

	public void delSite(int siteId) {
		siteDao.delete(siteId);
	}

	public PageDataList getArticleList(int page) {
		int total = articleDao.countArticle();
		Page p = new Page(total, page, 20);
		List list = articleDao.getArticleList(p.getStart(), p.getPernum());
		return new PageDataList(p, list);
	}

	public void addArticle(Article a, String files) {
		Article art = articleDao.merge(a);
	}

	public void delArticle(int id) {
		articleDao.delete(id);
	}

	public void modifyArticle(Article a, String files) {
		articleDao.update(a);
	}

	public Article getArticle(int id) {
		return articleDao.find(id);
	}


	@Override
	public Article getArticleByEname(String ename) {
		return articleDao.findByPropertyForUnique("ename", ename);
	}

	@Override
	public PageDataList getArticleList(Site site, int page, int row) {
		
		SearchParam param = SearchParam.getInstance()
									.addParam("site", site)
									.addPage(0, page, row)
									.addParam("status", 1)
									//.addOrder(OrderType.ASC, "sort")
									.addOrder(OrderType.DESC, "flag")
									.addOrder(OrderType.DESC, "publish");
			PageDataList<Article> list = articleDao.findPageList(param);
			return list;
	}

	@Override
	public boolean checkSiteCode(String code) {
		Site site = siteDao.getSiteByCode(code);
		if (site == null) {
			return true;
		} else {
			return false;
		}
	}

	public List getSubSiteList(int pid) {
		return siteDao.getSubSiteList(pid);
	}

	public List<Site> getSubSitePageDataList(SearchParam param) {
		return siteDao.findByCriteria(param);
	}

	@Override
	public PageDataList getArticleList(SearchParam param) {
		return articleDao.findPageList(param);
	}

	@Override
	public PageDataList<ScrollPic> getScrollPicList(SearchParam param) {
		return scrollPicDao.findPageList(param);
	}

	@Override
	public void delScrollPic(long id) {
		scrollPicDao.delete(id);
	}

	@Override
	public void modifyScrollPic(ScrollPic sp) {
		scrollPicDao.merge(sp);
	}

	@Override
	public ScrollPic getScrollPicListById(long id) {
		return scrollPicDao.find(id);
	}

	@Override
	public void addScrollPic(ScrollPic sp) {
		scrollPicDao.save(sp);
	}

	@Override
	public List<Article> getArticleList2(SearchParam param) {
		return articleDao.findByCriteria(param);
	}

	@Override
	public void modifyArticle(Article a) {
		articleDao.merge(a);
	}

	@Override
	public PageDataList<Article> getArticleListByFlag(Site site) {
		PageDataList mediaFirst = null;
		if(site!=null){			
			SearchParam spFirstParam = SearchParam.getInstance();
			spFirstParam.addPage(0, 1);
			spFirstParam.addParam("status", 1);
			spFirstParam.addParam("site", site);
			spFirstParam.addOrFilter("flag", "t", "h");
			spFirstParam.addOrder(OrderType.DESC, "publish");
			mediaFirst = articleDao.findPageList(spFirstParam);
			if(mediaFirst.getList().size()==0){
				SearchParam sp = SearchParam.getInstance();
				sp.addPage(0, 1);
				sp.addParam("status", 1);
				sp.addParam("site", site);
				sp.addParam("flag",Operator.NOTEQ,"t");
				sp.addParam("flag",Operator.NOTEQ,"h");
				sp.addOrder(OrderType.DESC, "publish");
				mediaFirst = articleDao.findPageList(sp);
			}
		}
		return mediaFirst;
	}

	@Override
	public PageDataList<Article> getArticleListByFlagSite(Site site) {
		PageDataList mediaList = null;
		PageDataList mediaFirst = null;
		if(site!=null){			
			SearchParam spFirstParam = SearchParam.getInstance();
			spFirstParam.addPage(0, 1);
			spFirstParam.addParam("status", 1);
			spFirstParam.addParam("site", site);
			spFirstParam.addOrFilter("flag", "t", "h");
			spFirstParam.addOrder(OrderType.DESC, "publish");
			mediaFirst = articleDao.findPageList(spFirstParam);
			if(mediaFirst.getList().size()==0){
				SearchParam sp = SearchParam.getInstance();
				sp.addPage(0, 1);
				sp.addParam("status", 1);
				sp.addParam("site", site);
				sp.addParam("flag",Operator.NOTEQ,"t");
				sp.addParam("flag",Operator.NOTEQ,"h");
				sp.addOrder(OrderType.DESC, "publish");
				mediaFirst = articleDao.findPageList(sp);
			}
			
			SearchParam sp = SearchParam.getInstance();
			sp.addPage(0, 4);
			sp.addParam("status", 1);
			sp.addParam("site", site);
			sp.addParam("flag",Operator.NOTEQ,"t");
			sp.addParam("flag",Operator.NOTEQ,"h");
			sp.addOrder(OrderType.DESC, "publish");
			mediaList = articleDao.findPageList(sp);
			if(mediaList.getList().size()>0 && mediaFirst.getList().size()>0){
				if(mediaList.getList().get(0).equals(mediaFirst.getList().get(0))){
					SearchParam spa = SearchParam.getInstance();
					spa.addPage(0, 5);
					spa.addParam("status", 1);
					spa.addParam("site", site);
					spa.addParam("flag",Operator.NOTEQ,"t");
					spa.addParam("flag",Operator.NOTEQ,"h");
					spa.addOrder(OrderType.DESC, "publish");
					mediaList = articleDao.findPageList(spa);
					mediaList.getList().remove(0);
				}
			}
			
		}
		return mediaList;
	}

	@Override
	public List<Site> getSiteByPid(int pid, String style) {
		return siteDao.getSiteByPid(pid,style);
	}


}
