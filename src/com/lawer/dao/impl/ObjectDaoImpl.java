package com.lawer.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;





import com.lawer.dao.BaseDao;
import com.lawer.exception.BussinessException;
import com.lawer.model.Page;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.util.ReflectUtils;

public class ObjectDaoImpl<T> implements BaseDao<T> {

private static final long serialVersionUID = 7433224241393375192L;
	
	@PersistenceContext
	protected EntityManager em;
	
	@Autowired
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	protected Class<T> entityClass = ReflectUtils.getSuperClassGenricType(super.getClass());
	Logger logger = Logger.getLogger(ObjectDaoImpl.class);

	public T save(T entity) {
		em.persist(entity);
		return entity;
	}

	public void save(Collection<T> ts) {
		for (T t : ts) {
			save(t);
		}
	}
	
	/**
	 * @param entity
	 */
	public T merge(T entity) {
		T t = em.merge(entity);
		return  t;
		
	}
	
	/**
	 * @param entity
	 */
	public void update(T entity) {
		em.merge(entity);
	}
	
	/**
	 * @param entity
	 */
	public void updateWithRefresh(T entity) {
		em.merge(entity);
	}
	
	public boolean update(Collection<T> ts) {
		try {
			for (T t : ts) {
				if(t!=null){
					em.merge(t);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			String message = "数据库更新对象出错！";
			logger.error(message);
			throw new BussinessException(message);
		}
	}

	@Override
	public void lock(T entity) {
		em.lock(entity, LockModeType.PESSIMISTIC_WRITE);
	}
	/**
	 * @param entityids
	 */
	public void delete(Serializable[] entityids) {
		for (Object id : entityids)
			this.em.remove(this.em.getReference(this.entityClass, id));
	}
	/**
	 * @param entityids
	 */
	public void delete(Serializable entityids) {
		delete(new Serializable[]{entityids});
	}

	@Override
	public void delete(Collection<T> ts) {
		for (T c : ts)
			this.em.remove(c);
	}

	public void clear() {
		em.clear();
	}
	@Override
	public void detach(Collection<T> ts) {
		for (T c : ts)
			this.em.detach(c);
	}
	@Override
	public void detach(Serializable entityid) {
		em.detach(entityid);
	}

	public void flush() {
		em.flush();
	}
	

	public T find(Class<T> entityClass, Object id) { 
        return getEntityManager().find(entityClass, id); 
    } 
 
    public T find(Serializable entityId){
    	if (entityId == null) throw new RuntimeException(this.entityClass.getName() + ":传入的entityId不能为空");
    	return this.em.find(this.entityClass, entityId);
    }
    
	public T findWithLock(Serializable entityId) {
		T o=find(entityId);
		em.lock(o, LockModeType.PESSIMISTIC_WRITE);
		return null;
	}

    public List<T> findAll() { 
        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass); 
        cq.select(cq.from(entityClass)); 
        return getEntityManager().createQuery(cq).getResultList(); 
    } 

	// --------------------- DataBaseTool -------------------

	public EntityManager getEntityManager() {
		return em;
	}

	protected boolean isEmpty(String string) {
		return null == string || string.trim().equals("") ? true : false;
	}

	public List<T> findByCriteria(SearchParam param) { 
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		Root<T> from = query.from(entityClass);
		Predicate[] p=param.bySearchFilter( entityClass, builder, query,from);
		Order[] orders=param.orderBy(builder, from);
		query.select(from).where(p);
		if(orders!=null&&orders.length>0){query.orderBy(orders);}
		TypedQuery<T> typedQuery = em.createQuery(query);
		List<T> results = typedQuery.getResultList();
		return results;
	 }
	
	@Override
	public T findByCriteriaForUnique(SearchParam param) {
		List<T> list=findByCriteria(param);
		if(list==null||list.size()<1){
			return null;
		}
		return list.get(0);
	}

	public List<T> findByCriteria(SearchParam param,int start,int limit) { 
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		Root<T> from = query.from(entityClass);
		Predicate[] p=param.bySearchFilter(entityClass, builder, query,from);
		Order[] orders=param.orderBy(builder, from);
		query.select(from).where(p);
		if(orders!=null&&orders.length>0){query.orderBy(orders);}
		TypedQuery<T> typedQuery = em.createQuery(query);
		typedQuery.setFirstResult(start).setMaxResults(limit);
		List<T> results = typedQuery.getResultList();
		return results;
	 }
	
	public List<T> findAllByCriteria(SearchParam param) { 
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		Root<T> from = query.from(entityClass);
		Predicate[] p=param.bySearchFilter(entityClass, builder, query,from);
		Order[] orders=param.orderBy(builder, from);
		query.select(from).where(p);
		if(orders!=null&&orders.length>0){query.orderBy(orders);}
		TypedQuery<T> typedQuery = em.createQuery(query);
		List<T> results = typedQuery.getResultList();
		return results;
	 }
	
	
	
	@Override
	public int countByCriteria(SearchParam param) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root root = query.from(entityClass);
		query.select(root);
		Predicate[] p=param.bySearchFilter(entityClass, builder, query,root);
		query.where(p);
		query.select(builder.countDistinct(root)); 
		int count = em.createQuery(query).getSingleResult().intValue();
		return count;
	}

	@Override
	public List<T> findByProperty(String property,Object value) {
		SearchParam param=new SearchParam();
		param.addParam(property, value);
		return findByCriteria(param);
	}

	@Override
	public T findByPropertyForUnique(String property,Object value) {
		List<T> list=findByProperty(property,value);
		if(list==null||list.size()<1){
			return null;
		}
		return list.get(0);
	}

	@Override
	public PageDataList<T> findPageList(SearchParam param) { 
		int count=countByCriteria(param);
		if(param.getPage()==null){
			param.addPage(count, 1, Page.ROWS);
		}
		param.addPage(count, param.getPage().getCurrentPage(), param.getPage().getPernum());
		List<T> list=findByCriteria(param, param.getPage().getStart(), param.getPage().getPernum());
		return new PageDataList(param.getPage(),list);
	}
	
	/**
	 * @param param
	 * @return
	 */
	@Override
	public PageDataList<T> findAllPageList(SearchParam param){
		List<T> list=	findAllByCriteria(param);
		return new PageDataList(param.getPage(),list);
	}
	
	public List<T> findAllListBySql(String datasql,SearchParam param,Map<String,Object> paramMap,Class clazz) { 
		if(datasql.indexOf("where")<0){
			datasql=datasql+" where 1=1 ";
		}
		datasql= datasql +param.bySearchSqlFilter().toString()+param.byOrderSqlFilter().toString() + param.byGroupBySqlFilter()  ;
		logger.info("findAllListBySql: "+datasql);
		List<T> list =  getNamedParameterJdbcTemplate().query(datasql, paramMap, getBeanMapper(clazz));
		return list;
	}
	
	public PageDataList<T> findPageListBySql(String datasql,String countsql,SearchParam param,Map<String,Object> paramMap,Class clazz){
		if(datasql.indexOf("where")<0){
			datasql=datasql+" where 1=1 ";
		}
		if(countsql.indexOf("where")<0){
			countsql=countsql+" where 1=1 ";
		}
		countsql= countsql + param.bySearchSqlFilter().toString();
		int count= getNamedParameterJdbcTemplate().queryForObject(countsql, new BeanPropertySqlParameterSource(Integer.class),Integer.class);
		if(param.getPage()==null){
			param.addPage(count, 1, Page.ROWS);
		}
		param.addPage(count, param.getPage().getCurrentPage(), param.getPage().getPernum());
		datasql= datasql +param.bySearchSqlFilter().toString()+ param.byGroupBySqlFilter() +param.byOrderSqlFilter().toString()   ;
		datasql += " limit " + param.getPage().getStart() + "," + param.getPage().getPernum();
		//logger.info("findPageListBySql:" + datasql);
		List<T> list =  getNamedParameterJdbcTemplate().query(datasql, paramMap, getBeanMapper(clazz));
		return new PageDataList(param.getPage(),list);
	} 

	@Override
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return this.namedParameterJdbcTemplate;
	}

	/**
	 * @param clazz
	 * @return
	 */
	@Override
	public RowMapper getBeanMapper(Class clazz){
		BeanPropertyRowMapper m=new BeanPropertyRowMapper(clazz);
		m.setPrimitivesDefaultedForNullValue(true);
		return m;
	}

}
