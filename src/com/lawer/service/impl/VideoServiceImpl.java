package com.lawer.service.impl;

import com.lawer.dao.VideoDao;
import com.lawer.domain.Video;
import com.lawer.service.VideoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "videoService")
@Transactional
public class VideoServiceImpl extends BaseServiceImpl implements VideoService {

	private static Logger logger = Logger.getLogger(VideoServiceImpl.class);

	@Autowired
	private VideoDao videoDao;


	@Override
	public Video findVideoById(int id) {
		return videoDao.findVideoById(id);
	}

	@Override
	public void saveVideo(Video video) {
		videoDao.save(video);
	}
}
