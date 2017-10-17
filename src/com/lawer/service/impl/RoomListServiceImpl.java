package com.lawer.service.impl;

import com.lawer.dao.RoomListDao;
import com.lawer.domain.RoomList;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.service.RoomListService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "roomListService")
@Transactional
public class RoomListServiceImpl extends BaseServiceImpl implements RoomListService {

	private static Logger logger = Logger.getLogger(RoomListServiceImpl.class);

	@Autowired
	private RoomListDao roomListDao;

	@Override
	public void saveRoomList(RoomList roomList) {
		roomListDao.save(roomList);

	}

	@Override
	public PageDataList<RoomList> findRoomListByPage(SearchParam param) {
		return roomListDao.findPageList(param);
	}
}
