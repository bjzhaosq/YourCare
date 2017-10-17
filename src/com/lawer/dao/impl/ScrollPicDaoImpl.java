package com.lawer.dao.impl;

import org.springframework.stereotype.Repository;

import com.lawer.dao.ScrollPicDao;
import com.lawer.domain.ScrollPic;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository(value="scrollPicDao")
public class ScrollPicDaoImpl extends ObjectDaoImpl<ScrollPic> implements ScrollPicDao  {
    @Override
    public List findScrollPicByTypeId(long typeId) {
        String sql="from ScrollPic where typeId=?1 and status = 1 order by sort asc ";
        List list= new ArrayList();
        try{
            Query query  = em.createQuery(sql);
            query.setParameter(1, typeId);
            list  = query.getResultList();
        }catch(Exception e){
            logger.error(e);
            return null;
        }
        return list;
    }
}
