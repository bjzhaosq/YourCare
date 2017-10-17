package com.lawer.dao;

import com.lawer.domain.Site;
import com.lawer.domain.User;

import java.util.List;

public interface SiteDao extends BaseDao<Site>{

	public Site getSiteByCode(String code);
	public List getSiteList(); 	
	public List getSubSiteList(int pid);
	
	public List getAllSubSiteList(int pid);

	public List getAllSubSiteList(int pid, User user);

	public List<Site> getSiteByPid(int pid,String style);
}

