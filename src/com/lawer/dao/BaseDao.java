package com.lawer.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface BaseDao<T> {
	public T save(T entity);
	
	public void save(Collection<T> ts);
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public T merge(T entity);
	/**
	 * 
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * 
	 * @param entity
	 */
	public void updateWithRefresh(T entity);
	
	public boolean update(Collection<T> ts);
	/**
	 * 
	 * @param entity
	 */
	public void lock(T entity);
	/**
	 * 
	 * @param entityids
	 */
	public void delete(Serializable[] entityids) ;
	/**
	 * 
	 * @param entityids
	 */
	public void delete(Serializable entityids) ;
	
	public void delete(Collection<T> c) ;

	public void clear();
	
	public void detach(Collection<T> ts);

	public void detach(Serializable entityid) ;

	public void flush() ;
	
	
    public T find(Class<T> entityClass, Object id) ;
    public T find(Serializable entityId);
    public T findWithLock(Serializable entityId);
	
    public List<T> findAll() ;
    
    public List<T> findByCriteria(SearchParam param);
    
    public T findByCriteriaForUnique(SearchParam param);
    
    public List<T> findByCriteria(SearchParam param,int start,int limit);
    
    public int countByCriteria(SearchParam param);
    /**
     *
     * @param value
     * @return
     */
    public List<T> findByProperty(String property,Object value);
    /**
     *
     * @param value
     * @return
     */
    public T findByPropertyForUnique(String property,Object value);
    
    public PageDataList<T> findPageList(SearchParam param);
    
    public List<T> findAllListBySql(String datasql,SearchParam param,Map<String,Object> paramMap,Class clazz);
    
    public PageDataList<T> findPageListBySql(String datasql,String countsql,SearchParam param,Map<String,Object> paramMap,Class clazz);
    
    
	/**
	 * 
	 * @param param
	 * @return
	 */
    public PageDataList<T> findAllPageList(SearchParam param);
    
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public RowMapper getBeanMapper(Class clazz);
}
