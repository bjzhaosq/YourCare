package com.lawer.service;

import com.lawer.domain.Video;

public interface VideoService {

	public Video findVideoById(int id);

	public void saveVideo(Video video);
}
