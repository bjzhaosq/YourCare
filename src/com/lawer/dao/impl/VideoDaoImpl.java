package com.lawer.dao.impl;

import com.lawer.dao.VideoDao;
import com.lawer.domain.Video;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository(value = "videoDao")
public class VideoDaoImpl extends ObjectDaoImpl<Video> implements VideoDao {

	private static Logger logger = Logger.getLogger(VideoDaoImpl.class);

	@Override
	public Video findVideoById(int id) {
		String sql="from Video where id = "+id;
		List<Video> list = null;
		try{
			Query query  = em.createQuery(sql);
			list  = query.getResultList();
		}catch(Exception e){
			logger.error(e);
			return null;
		}
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
