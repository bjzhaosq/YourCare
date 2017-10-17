package com.lawer.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.CustomerDao;
import com.lawer.dao.ExceptionLogDao;
import com.lawer.dao.UserDao;
import com.lawer.dao.UserTypeDao;
import com.lawer.domain.Customer;
import com.lawer.domain.User;
import com.lawer.domain.UserType;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.service.CustomerService;
import com.lawer.service.UserService;
import com.lawer.util.MD5;
import com.lawer.util.StringUtils;

@Service(value = "customerService")
@Transactional
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {

	private static Logger logger = Logger.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public void saveCustomer(Customer cus) {
		customerDao.save(cus);
	}
	
	@Override
	public PageDataList<Customer> findAllByPage(SearchParam param) {
		PageDataList<Customer> list = customerDao.findPageList(param);
		return list;
	}

	@Override
	public void delCustomer(SearchParam param) {

		List<Customer> list = customerDao.findByCriteria(param);
		for (Customer customer : list) {
			customer.setStatus("0");
		}
		boolean update = customerDao.update(list);
		
	}

	@Override
	public PageDataList<Customer> findCustomerByParam(SearchParam param) {
		PageDataList<Customer> pageList = customerDao.findPageList(param);
		return pageList;
	}

	@Override
	public void updateCustomer(Customer cus) {
		customerDao.update(cus);
	}

	@Override
	public List<Customer> findAll(SearchParam param) {
		List<Customer> list = customerDao.findByCriteria(param);
		return list;
	}

}
