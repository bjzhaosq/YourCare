package com.lawer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawer.dao.UserDao;
import com.lawer.dao.UserTypeDao;
import com.lawer.dao.UserTypepurviewDao;
import com.lawer.domain.Purview;
import com.lawer.domain.User;
import com.lawer.domain.UserType;
import com.lawer.domain.UserTypepurview;
import com.lawer.model.SearchParam;

@Repository
public class UserTypepurviewDaoImpl  extends ObjectDaoImpl<UserTypepurview> implements UserTypepurviewDao {
    Logger logger = Logger.getLogger(UserTypepurviewDaoImpl.class);

}

