package com.lawer.service;

import java.util.List;

import com.lawer.domain.Customer;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;

public interface CustomerService {

	public void saveCustomer(Customer cus);

	public PageDataList<Customer> findAllByPage(SearchParam param);

	public void delCustomer(SearchParam param);

	public PageDataList<Customer> findCustomerByParam(SearchParam param);

	public void updateCustomer(Customer cus);

	public List<Customer> findAll(SearchParam param);
	
	
	
}
