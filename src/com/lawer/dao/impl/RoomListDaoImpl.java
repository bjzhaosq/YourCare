package com.lawer.dao.impl;

import com.lawer.dao.RoomListDao;
import com.lawer.domain.RoomList;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "RoomListDao")
public class RoomListDaoImpl extends ObjectDaoImpl<RoomList> implements RoomListDao {

	private static Logger logger = Logger.getLogger(RoomListDaoImpl.class);



	
}
