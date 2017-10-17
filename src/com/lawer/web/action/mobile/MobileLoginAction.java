package com.lawer.web.action.mobile;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.lawer.context.Constant;
import com.lawer.domain.User;
import com.lawer.model.SearchParam;
import com.lawer.service.UserService;
import com.lawer.util.StringUtils;
import com.lawer.web.action.BaseAction;
import com.opensymphony.xwork2.ActionContext;

@Namespace("/mobile/login")
@ParentPackage("p2p-default")
public class MobileLoginAction extends BaseAction {
	private static Logger logger = Logger.getLogger(MobileLoginAction.class);

	@Autowired
	private UserService userService;
	
	
	
	/**
	 * 登录页
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/login", results = { @Result(name = "success", type = "ftl", location = "/mobile/mobile_login.html") })
	public String login() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 登录
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/goLogin")
	public void goLogin() {
		logger.info("gologin");
		JSONObject json = new JSONObject();
		if(!checkParam("username","password")){
			setCode(json, "请求参数错误");
			printJson(json.toString());
			return;
		}
		User u = null;
		String username = paramString("username");
		String password = paramString("password");
		u = userService.loginWithPhoneEmailName(username, password);
		if (u == null) {
			sendMsg("1", "用户不存在或密码错误！");
		}else{
			session.put(Constant.SESSION_USER, u);
			request.getSession().setAttribute(Constant.SESSION_USER, u);
			sendMsg("0","登陆成功");
		}
	}
	
	private void sendMsg(String code,String msg){
		JSONObject json = new JSONObject();
		logger.info(msg);
		json.put("msg",msg);
		json.put("code",code);
		printJson(json.toString());
	}
	
	@Action("loginout")
	public String logout() throws Exception {
		Map session = (Map) ActionContext.getContext().getSession();
		request.getSession().removeAttribute(Constant.SESSION_USER);
		session.put("session_user", null);
		return LOGIN;
	}
	
	
	
}
