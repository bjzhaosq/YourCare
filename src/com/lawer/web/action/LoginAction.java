package com.lawer.web.action;

import com.alibaba.fastjson.JSONObject;
import com.lawer.domain.User;
import com.lawer.service.UserService;
import com.lawer.util.Constant;
import com.opensymphony.xwork2.ActionContext;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


@Namespace("/login")
@ParentPackage("p2p-default")
public class LoginAction extends BaseAction{
	private static Logger logger = Logger.getLogger(LoginAction.class);
	
	@Autowired
	private UserService userService;
	
	private User user;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	
	@Action("login")
	public String login(){
		if(isSession()){
			return INDEX;
		}
		return LOGIN;
	}
	
	/**
	 * 登陆
	 * @Date 2017年7月18日 下午5:14:53
	 */
	@Action(value = "goLogin")
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
