package com.lawer.dao;

import com.lawer.domain.ScrollPic;

import java.util.List;

public interface ScrollPicDao extends BaseDao<ScrollPic> {

    public List findScrollPicByTypeId(long typeId);
}
