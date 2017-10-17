package com.lawer.web.action;

import com.lawer.domain.User;
import com.lawer.domain.UserType;
import com.lawer.model.OrderFilter.OrderType;
import com.lawer.model.Page;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchFilter.Operator;
import com.lawer.model.SearchParam;
import com.lawer.service.UserService;
import com.lawer.service.UserTypeService;
import com.lawer.util.NumberUtils;
import com.lawer.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Namespace("/user")
@ParentPackage("p2p-default")
public class UserAction extends BaseAction{
	private static Logger logger = Logger.getLogger(UserAction.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserTypeService userTypeService;
	
	private User user;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 人员管理页面
	 * @return
	 */
	@Action(value="showLawer",
			results={@Result(name="showLawer", type="ftl",location="/law_firm_M.html")
	})
	public String showLawer(){
		
		Map<String, Object> extraparam = new HashMap<String, Object>();
		int page = paramInt("page");
		
		String name = paramString("name");
		String jobNumber = paramString("jobNumber");
		String userType = paramString("userType");
		
		SearchParam param = SearchParam.getInstance();
		if(!StringUtils.isBlank(name)){
			param.addParam("username",Operator.LIKE, name);
		}
		if(!StringUtils.isBlank(jobNumber)){
			param.addParam("jobNumber",Operator.LIKE, jobNumber);
		}
		if(!StringUtils.isBlank(userType)){
			int typeId = NumberUtils.getInt(userType);
			param.addParam("userTypeId",typeId);
			request.setAttribute("type",userType);
		}
		param.addParam("status", 1);
		param.addOrder(OrderType.DESC, "id");
		
		param.addPage(page+1, Page.ROWS);
		extraparam.put("page", page);
		
		PageDataList<User> list = userService.findUserByParam(param);
		
		SearchParam param1 = SearchParam.getInstance();
		List<UserType> userTypeList = userTypeService.findAllByParam(param1);
		request.setAttribute("userTypeList",userTypeList);
		
		if(list!=null&&list.getList()!=null){
			request.setAttribute("list",list.getList());
			request.setAttribute("pages",list.getPage());
		}
		
		return "showLawer";
	}
	
	/**
	 * 新增跳转
	 * @return
	 */
	@Action(value="newlyLawer",
			results={@Result(name="newlyLawer", type="ftl",location="/law_firm_infor.html")
	})
	public String newlyLawer(){
		SearchParam param1 = SearchParam.getInstance();
		List<UserType> userTypeList = userTypeService.findAllByParam(param1);
		request.setAttribute("userTypeList",userTypeList);
		return "newlyLawer";
	}
	
	/**
	 * 人员添加
	 * @return
	 */
	@Action(value="savelyLawer",
			results={@Result(name="savelyLawer", type="redirect",location="/user/showLawer.html")
	})
	public String savelyLawer(){
		if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())){
			request.setAttribute("msg", "请填写用户名和密码");
			return ERROR;
		}
		if(StringUtils.isBlank(user.getJobNumber())){
			request.setAttribute("msg", "请填写工号");
			return ERROR;
		}
		if(StringUtils.isBlank(user.getPhone())){
			request.setAttribute("msg", "请填写联系方式");
			return ERROR;
		}

		user.setAddtime(new Date());
		user.setStatus("1");
		userService.saveNewUser(user);
		return "savelyLawer";
	}
	
	/**
	 * 人员删除
	 * @return
	 */
	@Action(value="dellyLawer",
			results={@Result(name="dellyLawer", type="redirect",location="/user/showLawer.html")
	})
	public String dellyLawer(){
		Integer id = paramInt("id");
		User user = userService.findUserById(id);
		user.setStatus("0");
		userService.update(user);
		return "dellyLawer";
	}
	
	/**
	 * 人员修改
	 * @return
	 */
	@Action(value="reviselyLawer",
			results={@Result(name="reviselyLawer", type="ftl",location="/law_firm_infor.html")
	})
	public String reviselyLawer(){
		Integer id = paramInt("id");
		User user = userService.findUserById(id);
		request.setAttribute("userInfo",user);
		
		SearchParam param1 = SearchParam.getInstance();
		List<UserType> userTypeList = userTypeService.findAllByParam(param1);
		request.setAttribute("userTypeList",userTypeList);
		
		return "reviselyLawer";
	}
}
