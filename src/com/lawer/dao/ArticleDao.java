package com.lawer.dao;

import java.util.List;

import com.lawer.domain.Article;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface ArticleDao extends BaseDao<Article> {

	public List getArticleList(int start, int pernum);

	public int countArticle();

	public Article getArticle(long id);
	
	public Article getArticleByFlag(long id);
	
	public List<Article> getArticleListBySiteId(long id);
	
	public List<Article> getArticle2(long id);
	
	

}
