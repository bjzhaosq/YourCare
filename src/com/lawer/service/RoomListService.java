package com.lawer.service;

import com.lawer.domain.RoomList;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface RoomListService {

	public void saveRoomList(RoomList roomList);

	public PageDataList<RoomList> findRoomListByPage(SearchParam param);

}
