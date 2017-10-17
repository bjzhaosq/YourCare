package com.lawer.web.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhlabs.image.OpacityFilter;
import com.lawer.domain.Customer;
import com.lawer.model.Page;
import com.lawer.model.PageDataList;
import com.lawer.model.OrderFilter.OrderType;
import com.lawer.model.SearchFilter.Operator;
import com.lawer.model.SearchParam;
import com.lawer.service.CustomerService;
import com.lawer.util.StringUtils;

@Namespace("/customer")
@ParentPackage("p2p-default")
public class CustomerAction extends BaseAction {

	private static Logger logger = Logger.getLogger(CustomerAction.class);

	@Autowired
	private CustomerService customerService;
	
	/**
	 * 新增客户
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/saveCustomer", results = { @Result(name = "success", type = "redirect", location = "/customer/customer_M.html") })
	public String saveCustomer() throws Exception {
		String username = paramString("username");
		if(StringUtils.isBlank(username)){
			request.setAttribute("msg", "请填写客户名");
			return ERROR;
		}
		String id = paramString("id");
		String usertype = paramString("usertype");
		String phone = paramString("phone");
		String address = paramString("address");
		
		Customer cus;
		if(StringUtils.isBlank(id)){
			//id 为空;  新增
			cus = new Customer();
		}else{
			//修改
			SearchParam param = SearchParam.getInstance();
			param.addParam("id", id);
			PageDataList<Customer> list = customerService.findCustomerByParam(param);
			cus = list.getList().get(0);
		}
		cus.setUsername(username);
		cus.setType(usertype);
		cus.setPhone(phone);
		cus.setAddress(address);
		cus.setAddtime(new Date());
		cus.setStatus("1");//0无效,1有效
		
		if(StringUtils.isBlank(id)){
			customerService.saveCustomer(cus);
		}else{
			customerService.updateCustomer(cus);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 客户管理页显示
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/customer_M", results = { @Result(name = "success", type = "ftl", location = "/customer_M.html") })
	public String customer() throws Exception {
		Map<String, Object> extraparam = new HashMap<String, Object>();
		int page = paramInt("page");
		
		SearchParam param = SearchParam.getInstance();
		param.addParam("status", 1);
		
		param.addPage(page+1, Page.ROWS);
		extraparam.put("page", page);
		param.addOrder(OrderType.DESC, "addtime");
		
		PageDataList<Customer> list = customerService.findAllByPage(param);
		
		if(list!=null&&list.getList()!=null){
			request.setAttribute("list",list.getList());
			request.setAttribute("pages",list.getPage());
		}
		
		return SUCCESS;
	}
	
	/**
	 * 删除客户
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/delCustomer", results = { @Result(name = "success", type = "redirect", location = "/customer/customer_M.html") })
	public String delCustomer() throws Exception {
		String str = paramString("id");
		if(StringUtils.isBlank(str)){
			return SUCCESS;
		}
		/*String[] allId = str.split(",");
		Integer all[] = new Integer[allId.length];
		for (int i = 0; i < allId.length; i++) {
			all[i] = new Integer(allId[i].trim());
		}*/
		
		SearchParam param = SearchParam.getInstance();
		//param.addParam("id",Operator , str);
		//param.addOrFilter("id", all);
		param.addParam("id", str);
		customerService.delCustomer(param);
		
		return SUCCESS;
	}
	
	/**
	 * 修改客户信息
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/reviseCustomer", results = { @Result(name = "success", type = "ftl", location = "/customer_M_newly.html") })
	public String reviseCustomer() throws Exception {
		String id = paramString("id");
		SearchParam param = SearchParam.getInstance();
		param.addParam("id", id);
		
		PageDataList<Customer> pageList = customerService.findCustomerByParam(param);
		List<Customer> list = pageList.getList();
		
		if(list!=null && !list.isEmpty()){
			request.setAttribute("cus",list.get(0));
		}
		
		return SUCCESS;
	}
	
	/**
	 * 查询客户信息
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/findCustomer", results = { @Result(name = "success", type = "ftl", location = "/customer_M.html") })
	public String findCustomer() throws Exception {
		Map<String, Object> extraparam = new HashMap<String, Object>();
		int page = paramInt("page");
		
		String username = paramString("username");
		String type = paramString("usertype");
		SearchParam param = SearchParam.getInstance();
		if(!StringUtils.isBlank(username)){
			param.addParam("username", username);
		}
		if(!StringUtils.isBlank(type)){
			param.addParam("type", type);
		}
		param.addParam("status", "1");
		
		param.addPage(page+1, Page.ROWS);
		extraparam.put("page", page);
		
		PageDataList<Customer> list = customerService.findCustomerByParam(param);
		
		if(list!=null&&list.getList()!=null){
			request.setAttribute("list",list.getList());
			request.setAttribute("pages",list.getPage());
		}
		request.setAttribute("type",type);
		
		
		return SUCCESS;
	}
	
	/**
	 * 新增客户页面跳转
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/addCustomer", results = { @Result(name = "success", type = "ftl", location = "/customer_M_newly.html") })
	public String addCustomer() throws Exception {
		
		return SUCCESS;
	}
	
	
}
