package com.lawer.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lawer.dao.CustomerDao;
import com.lawer.domain.Customer;

@Repository(value = "customerDao")
public class CustomerDaoImpl extends ObjectDaoImpl<Customer> implements CustomerDao {

	private static Logger logger = Logger.getLogger(CustomerDaoImpl.class);


	
}
