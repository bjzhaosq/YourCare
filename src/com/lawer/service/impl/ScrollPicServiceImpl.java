package com.lawer.service.impl;

import com.lawer.dao.ScrollPicDao;
import com.lawer.service.ScrollPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图片管理逻辑层
 * Created by ${Zsq} on 2016/7/20.
 */
@Service(value = "scrollPicService")
@Transactional
public class ScrollPicServiceImpl implements ScrollPicService {

    @Autowired
    private ScrollPicDao scrollPicDao;

    @Override
    public List findScrollPicByTypeId(long typeId) {
        return scrollPicDao.findScrollPicByTypeId(typeId);
    }
}
