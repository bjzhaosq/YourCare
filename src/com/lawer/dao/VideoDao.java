package com.lawer.dao;

import com.lawer.domain.Video;

public interface VideoDao extends BaseDao<Video>{

	public Video findVideoById(int id);

}
