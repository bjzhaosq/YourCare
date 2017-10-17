package com.lawer.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.ArticleDao;
import com.lawer.domain.Article;

@Repository(value = "articleDao")
public class ArticleDaoImpl extends ObjectDaoImpl<Article> implements ArticleDao {

	private static Logger logger = Logger.getLogger(ArticleDaoImpl.class);

	@Override
	public List getArticleList(int start, int pernum) {
		String sql = "select a from Article a left join a.site "
				+ "order by a.sort asc, a.publish desc,a.id desc,a.addtime desc";
		Query query = em.createQuery(sql);
		query.setFirstResult(start).setMaxResults(pernum);
		List list = query.getResultList();
		return list;
	}

	@Override
	public int countArticle() {
		String sql = "select a.id from Article a left join a.site";
		Query query = em.createQuery(sql);
		int total = query.getResultList().size();
		return total;
	}

	@Override
	public Article getArticle(long id) {
		String sql = "FROM Article  where site_id=?1 ORDER BY addtime desc";
		Query query = em.createQuery(sql).setParameter(1, id);
		List<Article> list = query.getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Article> getArticleListBySiteId(long id) {
		String sql = "FROM Article where site_id=?1 AND flag !='h' ORDER BY addtime DESC LIMIT 1,4";
		Query query = em.createQuery(sql).setParameter(1, id);
		List list = query.getResultList();
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

	@Override
	public Article getArticleByFlag(long id) {
		String sql = "FROM Article WHERE site_id=?1 AND flag='h'";
		Query query = em.createQuery(sql).setParameter(1, id);
		List<Article> list = query.getResultList();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return this.getArticle(id);
		}
	}


	@Override
	public List<Article> getArticle2(long id) {
		String sql = "FROM Article WHERE site_id=?1";
		Query query = em.createQuery(sql).setParameter(1, id);
		List list = query.getResultList();
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
}