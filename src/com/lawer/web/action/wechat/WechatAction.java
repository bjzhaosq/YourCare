package com.lawer.web.action.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lawer.domain.User;
import com.lawer.domain.WechatUser;
import com.lawer.service.UserService;
import com.lawer.service.WechatUserService;
import com.lawer.util.AesCBCUtils;
import com.lawer.util.Constant;
import com.lawer.util.HTTPParam;
import com.lawer.util.HTTPSend;
import com.lawer.web.action.BaseAction;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Date;

@Namespace("/wechat")
@ParentPackage("p2p-default")
public class WechatAction extends BaseAction {
	private static Logger logger = Logger.getLogger(WechatAction.class);

	@Autowired
	private WechatUserService wechatUserService;

	@Autowired
	private UserService userService;

	@Action(value = "/getWechatSession")
	public void getWechatSession(){
		JSONObject json = new JSONObject();
		if(!checkParam("code")){
			setCode(json, "请求参数错误");
			printJson(json.toString());
			return;
		}
		String code = paramString("code");
		ArrayList<HTTPParam> httpParams = new ArrayList<>();
		httpParams.add(new HTTPParam("appid", com.lawer.util.Constant.WECHAT_APPID));
		httpParams.add(new HTTPParam("secret", com.lawer.util.Constant.WECHAT_SECRET));
		httpParams.add(new HTTPParam("js_code", code));
		httpParams.add(new HTTPParam("grant_type", com.lawer.util.Constant.WECHAT_GRANT_TYPE));



		try {
			String s = HTTPSend.sendPost(com.lawer.util.Constant.WECHAT_URL, httpParams);
			JSONObject o = (JSONObject) JSONObject.parse(s);
			Integer errcode = (Integer) o.get("errcode");
			if(null != errcode){
				//为空
				String errmsg = (String) o.get("errmsg");
				logger.info(errcode+"-----------"+errmsg);
				setCode(json,"登陆失败");
				printJson(json.toString());
				return;
			}else{
				//{"session_key":"Atq2QSlgvuHIbyLU1txSYw==","expires_in":7200,"openid":"oNrEZ0f-z8hF8KkmmtVHi_eT_Hec"}
				String session_key = (String) o.get("session_key");
				Integer expires_in = (Integer) o.get("expires_in");
				String openid = (String) o.get("openid");

				WechatUser wechatUser = wechatUserService.findWechatUserByOpenid(openid);
				String rdSession = "";

				if(null == wechatUser){
					rdSession = get3rdSession();
					WechatUser w = new WechatUser();
					w.setOpenid(openid);
					w.setExpires_in(expires_in);
					w.setSession_key(session_key);
					w.setRd_session(rdSession);
					w.setAddtime(new Date());
					w.setStatus("1");
					wechatUserService.saveWechatUser(w);
				}else{
					if(!session_key.equals(wechatUser.getSession_key())){
						wechatUser.setSession_key(session_key);
					}
					if(expires_in != wechatUser.getExpires_in()){
						wechatUser.setExpires_in(expires_in);
					}
					rdSession = wechatUser.getRd_session();
				}

				setCode(json,"");
				json.put("wechatSession",rdSession);
				logger.info(json.toString());
				printJson(json.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
			setCode(json,"登陆失败");
			printJson(json.toString());
			return;
		}

	}

	/**
	 * linux中执行命令
	 * @param cmd
	 * @return
	 */
	public static String exec(String cmd) {
		StringBuffer sb = new StringBuffer();
		try {
			String[] cmdA = { "/bin/sh", "-c", cmd };
			Process process = Runtime.getRuntime().exec(cmdA);
			LineNumberReader br = new LineNumberReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			logger.info("3rd_session:"+sb.toString());
		} catch (Exception e) {
			//如果本地测试，会报空指针异常，所以为了不让报错，索性返回有值即可
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 得到3rd_session登录效验(key)
	 * @return
	 */
	public String get3rdSession(){
		return exec("head -n 80 /dev/urandom | tr -dc A-Za-z0-9 | head -c 168");
	}


	/**
	 * 登陆
	 * @Date 2017年7月18日 下午5:14:53
	 */
	@Action(value = "goLogin")
	public void goLogin() {
		logger.info("wechat gologin");
		JSONObject json = new JSONObject();
		if(!checkParam("value")){
			setCode(json, "请求参数错误");
			printJson(json.toString());
			return;
		}
		try {
			String value = paramString("value");
			String suibian = AesCBCUtils.getInstance().decrypt(value,"utf-8", Constant.SKEY,Constant.IV);
			JSONObject j = (JSONObject) JSON.parse(suibian);

			User u = null;
			String username = (String) j.get("username");
			String password = (String) j.get("password");
			u = userService.loginWithPhoneEmailName(username, password);
			if (u == null) {
				sendMsg("1", "用户不存在或密码错误！");
			}else{
				session.put(com.lawer.util.Constant.SESSION_USER, u);
				request.getSession().setAttribute(com.lawer.util.Constant.SESSION_USER, u);
				sendMsg("0","登陆成功");
			}
		} catch (Exception e) {
			setCode(json, "请求错误");
			printJson(json.toString());
			return;
		}

	}

	private void sendMsg(String code,String msg){
		JSONObject json = new JSONObject();
		logger.info(msg);
		json.put("msg",msg);
		json.put("code",code);
		printJson(json.toString());
	}
}
