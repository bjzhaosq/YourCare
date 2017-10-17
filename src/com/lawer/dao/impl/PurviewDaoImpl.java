package com.lawer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawer.dao.PurviewDao;
import com.lawer.dao.UserDao;
import com.lawer.dao.UserTypepurviewDao;
import com.lawer.domain.Purview;
import com.lawer.domain.UserTypepurview;

@Repository
public class PurviewDaoImpl extends ObjectDaoImpl<Purview> implements PurviewDao {
	private Logger logger=Logger.getLogger(PurviewDaoImpl.class);
	
}
