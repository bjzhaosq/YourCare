package com.lawer.web.action;

import com.lawer.context.Constant;
import com.lawer.domain.Customer;
import com.lawer.domain.LawCase;
import com.lawer.domain.LawType;
import com.lawer.domain.LawTypeList;
import com.lawer.model.OrderFilter.OrderType;
import com.lawer.model.Page;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchFilter.Operator;
import com.lawer.model.SearchParam;
import com.lawer.service.CustomerService;
import com.lawer.service.LawCaseService;
import com.lawer.service.LawTypeListService;
import com.lawer.service.LawTypeService;
import com.lawer.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Namespace("/")
@ParentPackage("p2p-default")
public class IndexAction extends BaseAction {

	private static Logger logger = Logger.getLogger(IndexAction.class);

	@Autowired
	private LawTypeListService lawTypeListService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private LawCaseService lawCaseService;
	@Autowired
	private LawTypeService lawTypeService;


//	@Action(value = "index", results = { @Result(name = "success", type = "ftl", location = "/index.html"),
//			                             @Result(name = "mobile_index", type = "redirect", location = "/mobile/menu/meunList.html")})
//	public String index() throws Exception {
//		String ua = (String) request.getSession().getAttribute(Constant.LAWYER_UA);
//		if(!StringUtils.isBlank(ua) && "mobile".equals(ua)){
//			return "mobile_index";
//		}
//		Map<String, Object> extraparam = new HashMap<String, Object>();
//		int page = paramInt("page");
//		SearchParam param = SearchParam.getInstance();
//
//		String caseName = paramString("caseName");
//		String caseNumber = paramString("caseNumber");
//		String customer = paramString("customer");
//		String statusOne = paramString("statusOne");
//		String statusTwo = paramString("statusTwo");
//		String username = paramString("username");
//
//		if(!StringUtils.isBlank(caseName)){
//			param.addParam("caseName",Operator.LIKE, caseName);
//		}
//		if(!StringUtils.isBlank(caseNumber)){
//			param.addParam("caseNumber",Operator.LIKE, caseNumber);
//		}
//		if(!StringUtils.isBlank(customer)){
//			param.addParam("customerId.username",Operator.LIKE, customer);
//		}
//		SearchParam param2 = SearchParam.getInstance();
//		List<LawType> list2;
//		if(statusOne!=""){
//			param2.addParam("statusOne", statusOne);
//		}
//		if(statusTwo!=""){
//			param2.addParam("statusTwo", statusTwo);
//		}
//		if(statusOne!=""||statusTwo!=""){
//			list2 = lawTypeService.findByParam(param2);
//			if(list2!=null&&list2.size()>0){
//				param.addOrFilter("lawTypeId", list2.toArray());
//			}
//		}
//
//		//首页案件信息查询
//		param.addOrder(OrderType.DESC, "addtime");
//		param.addPage(page+1, Page.ROWS);
//		extraparam.put("page", page);
//		param.addParam("status", Operator.NOTEQ, "0");	//0:无效
//		PageDataList<LawCase> list = lawCaseService.findAllByParam(param);
//		if(list!=null&&list.getList()!=null){
//			request.setAttribute("list",list.getList());
//			request.setAttribute("pages",list.getPage());
//		}
//
//		//案件类型查询
//		SearchParam param3 = SearchParam.getInstance();
//		param3.addParam("status", "1");
//		List<LawTypeList> typeList = lawTypeListService.findAll(param3);
//		request.setAttribute("typeList",typeList);
//
//		//委托人列表
//		if(!StringUtils.isBlank(username)){
//			param3.addParam("username",Operator.LIKE, username);
//		}
//		List<Customer> customerList = customerService.findAll(param3);
//		request.setAttribute("customer",customerList);
//
//
//
//		return SUCCESS;
//	}
//
//
}
