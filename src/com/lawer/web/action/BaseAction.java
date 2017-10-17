package com.lawer.web.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.lawer.context.Constant;
import com.lawer.domain.LawCase;
import com.lawer.domain.SystemLog;
import com.lawer.domain.SystemOperation;
import com.lawer.domain.User;
import com.lawer.exception.BussinessException;
import com.lawer.exception.ManageBussinessException;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.service.RuleService;
import com.lawer.service.SystemLogService;
import com.lawer.service.SystemOperationService;
import com.lawer.service.UserService;
import com.lawer.util.DateUtils;
import com.lawer.util.IPUtils;
import com.lawer.util.NumberUtils;
import com.lawer.util.StringUtils;
import com.lawer.util.jcaptcha.CaptchaServiceSingleton;
import com.lawer.web.interceptor.SimplePropertyFilter;
import com.octo.captcha.service.CaptchaServiceException;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;

@Scope("prototype")
public class BaseAction implements ServletRequestAware,ServletResponseAware,SessionAware,ServletContextAware {

	private final static Logger logger=Logger.getLogger(BaseAction.class);
	
	public final static String SUCCESS="success";
	public final static String ERROR="error";
	public final static String MOBILE_ERROR="mobile_error";
	public final static String FAIL="fail";
	public final static String OK="ok";
	public final static String MSG="msg";
	public final static String ADMINMSG="adminmsg";
	public final static String NOTFOUND="notfound";
	public final static String LOGIN="login";	
	public final static String REGISTER="register";
	public final static String MEMBER = "member";
	public final static String INDEX = "index";
	
	protected Map<String, Object> session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext context;
	
	protected String actionType;
	
	@Autowired
	private UserService userService;
	@Autowired
	private SystemLogService systemLogService;
	@Autowired
	private SystemOperationService systemOperationService;
	@Autowired
	private RuleService ruleService;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}
	@Override
	public void setServletContext(ServletContext context) {
		this.context=context;
	}
	
	@Action("404")
	public String notFound(){
		return NOTFOUND;
	}
	
	public String getActionType() {
		return StringUtils.isNull(actionType);
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	/**
	 * 是否开启验证码
	 * @return
	 */
	public boolean isOpenValidCode(){
		/*if("1".equals(Global.getValue("open_valid_code"))){
			return true;
		}else{
			return false;
		}*/
		return true;
	}
	
	public boolean isSession(){
		User sessionUser=this.getSUser();
		if(sessionUser==null) return false;
		return true;
	}
	
	/**
	 * 检验参数
	 * @Date 2016年1月13日 上午9:28:50
	 * @param params  必须输入的参数
	 * @return 全部有返回true  否则返回false
	 */
	protected boolean checkParam(String... params){
		for(String param : params){
			if(request.getParameter(param)==null){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 封装获取Session中的用户对象
	 * user 已经是当前数据库最新的数据
	 * @return
	 */
	protected User getSessionUser(){
		User user =  (User) session.get(Constant.SESSION_USER);
		if(user!=null){
//			user = userService.getUserById(user.getId());
		}
		return user;
	}
	/**
	 * 封装获取Session中的用户对象
	 * user 已经是当前数据库最新的数据
	 * @return
	 */
	protected User getSUser(){
		User user =  (User) session.get(com.lawer.util.Constant.SESSION_USER);
		if(user!=null){
//			user = userService.getUserById(user.getId());
		}
		return user;
	}
	/**
	 * 封装获取Session中的用户对象
	 * @return
	 */
	/*
	protected User getAuthUser(){
		User user = (User) session.get(Constant.AUTH_USER);
		if(user !=null){
//			user = userService.getUserById(user.getUserId());
		}
		return user;
	}*/
	
	
	 /**
	    * map里边包含jpa对象会报错，要过滤一次。
	    * @return
	    */
   protected String getStringOfJpaMap(Map<String,Object> map){
	   SimplePropertyFilter spf = new SimplePropertyFilter();
		SerializeWriter sw = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(sw);
		serializer.getPropertyFilters().add(spf);
		serializer.write(map);
		return sw.toString();
   }
	
	/**
	 * json字符串输出
	 * 
	 * @param json json字符
	 * @throws IOException 异常
	 */
	protected void printJson(Object obj) throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(JSON.toJSON(obj));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	   protected void printJson(String json){
		   try {
			   HttpServletResponse response = ServletActionContext.getResponse();
			   response.setContentType("application/json;charset=UTF-8");
			   PrintWriter out = response.getWriter();
			   out.print(json);
			   out.flush();   
			   out.close();
			} catch (Exception e) {
				logger.info(e);
				new BussinessException("发送json数据，失败！！");
			}
	   }
	
	
   protected int paramInt(String str){
		return NumberUtils.getInt(request.getParameter(str));
   }
   
   protected long paramLong(String str){
	   return NumberUtils.getLong(request.getParameter(str));
   }
   
   protected double paramDouble(String str){
	   return NumberUtils.getDouble(request.getParameter(str));
   }
   
   protected String paramString(String str){
	   return StringUtils.isNull(request.getParameter(str));
   }
   
   protected void export(String infile,String downloadFile) throws Exception{
		File inFile = new File(infile);
		InputStream ins = new BufferedInputStream(new FileInputStream(infile));
		byte[] buffer = new byte[ins.available()];
		ins.read(buffer);
		ins.close();
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
		response.setCharacterEncoding("UTF-8");
		response.reset();
		String aa = request.getHeader("User-Agent").toLowerCase();
		if(aa.contains("firefox")){
			response.addHeader("Content-Disposition", "attachment;filename="+ new String((downloadFile).getBytes("utf-8"),"ISO8859-1"));;
		}else{
			response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode((downloadFile),"utf-8"));
		}
		response.addHeader("Content-Length", "" + inFile.length());
		OutputStream ous = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		ous.write(buffer);
		ous.flush();
		ous.close();
	}
	
	
	
	/**
	 * 提示消息
	 * @param msg
	 * @param url
	 */
	protected void message(String msg,String url){
		String urltext="";
		if(!StringUtils.isBlank(url)){
			urltext="<a href="+request.getContextPath()+url+" >返回上一页</a>";
			request.setAttribute("backurl",urltext);
		}else{
			urltext="<a href='javascript:history.go(-1)'>返回上一页</a>";
		}
		message(msg, url, urltext);
	}
	
	protected void message(String msg){
		this.message(msg, getMsgUrl());
	}
	
	
	/**
	 * 提示消息
	 * @param msg
	 * @param url
	 * @param text
	 */
	protected void message(String msg,String url,String text){
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("rsmsg",msg);
		String urltext="<a href="+request.getContextPath()+url+" >"+text+"></a>";
		request.setAttribute("backurl",urltext);
	}
	
	protected void setMsgUrl(String url){
		String msgurl=(String)session.get("msgurl");
		String query=request.getQueryString();
		if(!StringUtils.isBlank(query)){
			url=url+"?"+query;
		}
		msgurl=url;
		session.put("msgurl", msgurl);
	}
	
	
	protected String getMsgUrl(){
		String msgurl="";
		Object o=null;
		if((o=session.get("msgurl"))!=null){
			msgurl=(String)o;
		}
		return msgurl;
	}

	protected void setPageUrl() {
		request.setAttribute("pageUrl", request.getRequestURL().toString());		
	}
	
   protected void setPageAttribute(PageDataList list,SearchParam param){	
	   request.setAttribute("page", list.getPage());
	   request.setAttribute("list", list.getList());
	   request.setAttribute("param", param.toMap());
	   setPageUrl();
   }
   /**
    * 可以添加额外的搜索条件
    * @param list
    * @param param
    * @param extraParams
    */
   protected void setPageAttribute(PageDataList list,SearchParam param,Map<String, Object> extraParams){
	   Map<String, Object> toMap = param.toMap();
	   if(extraParams != null && extraParams.size()>0){
		   toMap.putAll(extraParams);
	   }
	   request.setAttribute("page", list.getPage());
	   request.setAttribute("list", list.getList());
	   request.setAttribute("param", toMap);
	   setPageUrl();
   }
   
   /**
    * 只添加额外的搜索条件
    * @param list
    * @param param
    * @param extraParams
    */
   protected void setPageAttribute(PageDataList list,Map<String, Object> extraParams){
	   request.setAttribute("page", list.getPage());
	   request.setAttribute("list", list.getList());
	   request.setAttribute("param", extraParams);
	   setPageUrl();
   }
   
   protected String upload(File upload,String fileName,String destDir,String destFileName) throws Exception {
	   	if(upload==null) return "";
		logger.info("文件："+upload);
		logger.info("文件名："+fileName);
		String destFileUrl=destDir+"/"+destFileName;
		String destfilename=ServletActionContext.getServletContext().getRealPath(destDir)+"/"+destFileName;
		logger.info(destfilename);
		File imageFile=null;
		imageFile = new File(destfilename);
		FileUtils.copyFile(upload, imageFile);
		return destFileUrl;
   }
   
	/**
	 * 存储图片
	 * 
	 * @param scrollPic
	 */
	public String moveFile(LawCase lawCase,File file,String fileName) {
	    String sep = File.separator;
	    String filePath = "";
		String dataPath = ServletActionContext.getServletContext().getRealPath("/data");// \law\src\main\webapp\data
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");// \law\src\main\webapp
		Date d1 = new Date();
		String upfiesDir = null;
		upfiesDir = dataPath + sep + "upfiles" + sep + "lawCaseImgs" + sep;
		String destfilename1 = upfiesDir + DateUtils.dateStr2(d1)+ sep+ lawCase.getCaseNumber() + sep + generateUploadFilename(lawCase.getCaseNumber(), fileName);
		filePath = destfilename1;
		filePath = this.truncatUrl(filePath, contextPath);
		logger.info("destfilename1="+destfilename1);
		File imageFile1 = null;
		try {
			imageFile1 = new File(destfilename1);
			FileUtils.copyFile(file, imageFile1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return filePath;
	}
   
	/**
	 * 截取url
	 * 
	 * @param old
	 * @param truncat
	 * @return
	 */
	private String truncatUrl(String old, String truncat) {
		 String sep = File.separator;
		String url = "";
		url = old.replace(truncat, "");
		url = url.replace(sep, "/");
		return url;
	}
	
   protected String generateUploadFilename(){
	   User u=getSessionUser();
	   String timeStr=DateUtils.dateStr3(new Date());
	   if(u==null) return timeStr;
	   return u.getId()+timeStr;
   }
   
   protected String generateUploadFilename(String fileName){
	  String suffix = null;
	  if (fileName != null) {
		  int last = fileName.lastIndexOf('.');
		  suffix = fileName.substring(last);
	  }
	  return generateUploadFilename()+suffix;
   }
   
   /**
    * 生成名称
    * @Date 2017年7月26日 下午4:01:47
    * @param caseNumber
    * @param fileName
    * @return
    */
   protected String generateUploadFilename(String caseNumber,String fileName){
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); 
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");  
       String newFileName = caseNumber + "_" + df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt; 
	  return newFileName;
   }
   
   protected String getAllParams(boolean safety) {
		StringBuffer ps = new StringBuffer();
		Enumeration<?> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameter = (String) parameterNames.nextElement();
			String value = request.getParameter(parameter);
			if(org.apache.commons.lang3.StringUtils.isNotBlank(value)){
				if(!safety || (safety && !parameter.contains("password") && !parameter.contains("pwd"))){//安全性
					ps.append(parameter + "=" + value);
					if (parameterNames.hasMoreElements()) {
						ps.append("&");
					}
				}
			}
		}
		return ps.toString();
	}
   
   /**
	 * 获取http请求的实际IP
	 * @return
	 */
	protected String getRequestIp(){
		String realip=IPUtils.getRemortIP(request);
		return realip;
	}
	
	/**
	 * 校验校验码是否正确  
	 * @param valid
	 * @return
	 */
	protected boolean checkValidImg(String valid){
		if(isOpenValidCode()){
			boolean b=false;
			try {
				b= CaptchaServiceSingleton.getInstance().validateResponseForID(request.getSession().getId(), valid.toLowerCase());
			} catch (CaptchaServiceException e) {
				logger.debug(e.getMessage());
				b=false;
			}
			return b;
		}else{//本地测试不开通
			return true;
		}
		
	}
	
	protected void checkValidImgWithUrl(String backUrl){
		if(isOpenValidCode()){
			boolean b = false;		
			String validCode = paramString("validCode");
			if(StringUtils.isBlank(validCode)) {
				throw new BussinessException("温馨提示：请输入验证码！",backUrl);
			}
			try {
				b = CaptchaServiceSingleton.getInstance()
						    .validateResponseForID(request.getSession().getId(), validCode.toLowerCase());
			} catch (CaptchaServiceException e) {			
				throw new BussinessException("温馨提示：您输入的验证码不正确，请重新输入！",backUrl);			
			}
			if(!b) {
				throw new BussinessException("温馨提示：您输入的验证码不正确，请重新输入！",backUrl);	
			}
		}
	} 
	
	protected void checkAdminValidImgWithUrl(String backUrl){
		if(isOpenValidCode()){
			boolean b = false;		
			String validCode = paramString("validCode");
			if(StringUtils.isBlank(validCode)) {
				throw new ManageBussinessException("温馨提示：请输入验证码！",backUrl);
			}
			try {
				b = CaptchaServiceSingleton.getInstance()
						    .validateResponseForID(request.getSession().getId(), validCode.toLowerCase());
			} catch (CaptchaServiceException e) {			
				throw new ManageBussinessException("温馨提示：您输入的验证码不正确，请重新输入！",backUrl);			
			}
			if(!b) {
				throw new ManageBussinessException("温馨提示：您输入的验证码不正确，请重新输入！",backUrl);	
			}
		}
	} 
	
	/**
	 * 校验校验码是否正确  
	 * @param valid
	 * @return
	 */
	protected void checkValidImg(){
		if(isOpenValidCode()){
			boolean b = false;		
			String validCode = paramString("validCode");
			if(StringUtils.isBlank(validCode)) {
				throw new BussinessException("温馨提示：请输入验证码！");
			}
			try {
				b = CaptchaServiceSingleton.getInstance().validateResponseForID(request.getSession().getId(), validCode.toLowerCase());
			} catch (CaptchaServiceException e) {			
				throw new BussinessException("温馨提示：您输入的验证码不正确，请重新输入！");			
			}
			if(!b) {
				throw new BussinessException("温馨提示：您输入的验证码不正确，请重新输入！");	
			}
		}
	} 
	
	
	/**
	 * 校验校验码是否正确  
	 * @param valid
	 * @return
	 */
	protected String checkValidImgReturnJson(){
		if(isOpenValidCode()){
			boolean b = false;		
			String validCode = paramString("validCode");
			if(StringUtils.isBlank(validCode)) {
				String errorMsg = "温馨提示：请输入验证码！";
				logger.info(errorMsg);
				return errorMsg;
			}
			try {
				b = CaptchaServiceSingleton.getInstance().validateResponseForID(request.getSession().getId(), validCode.toLowerCase());
			} catch (CaptchaServiceException e) {			
				String errorMsg = "温馨提示：您输入的验证码不正确，请重新输入！";
				logger.info(errorMsg);
				return errorMsg;
			}
			if(!b) {
				String errorMsg = "温馨提示：您输入的验证码不正确，请重新输入！";
				logger.info(errorMsg);
				return errorMsg;
			}
		}
		return "";
	} 
	
	/**
	 * 校验校验码是否正确  
	 * @param valid
	 * @return
	 */
	protected void checkAdminValidImg(){
		if(isOpenValidCode()){
			boolean b = false;		
			String validCode = paramString("validCode");
			if(StringUtils.isBlank(validCode)) {
				throw new ManageBussinessException("温馨提示：请输入验证码！");
			}
			try {
				b = CaptchaServiceSingleton.getInstance().validateResponseForID(request.getSession().getId(), validCode.toLowerCase());
			} catch (CaptchaServiceException e) {			
				throw new ManageBussinessException("温馨提示：您输入的验证码不正确，请重新输入！");			
			}
			if(!b) {
				throw new ManageBussinessException("温馨提示：您输入的验证码不正确，请重新输入！");	
			}
		}
	}
	
	/**
	 * 记录系统日志
	 * @param user
	 * @param systemOperationId
	 * @param ip
	 * @param params
	 * @param remark
	 */
	public void systemLogAdd(User user, int systemOperationId, String remark){
		SystemOperation systemOperation = this.systemOperationService.find(systemOperationId);
		if(systemOperation!=null){
			SystemLog item = new SystemLog();
			item.setUser(user);
			item.setSystemOperation(systemOperation);
			item.setAddTime(new Date());
			item.setIp(getRequestIp());
			String params = getAllParams(true);
			if(org.apache.commons.lang3.StringUtils.isNotBlank(params)){
				params=request.getRequestURI()+"?"+params;
			}else{
				params=request.getRequestURI();
			}
			try {
				if(org.apache.commons.lang.StringUtils.isNotBlank(params) && params.getBytes("Unicode").length > 512){
					params = org.apache.commons.lang3.StringUtils.substring(params, 0, 20);
				}
				if(org.apache.commons.lang.StringUtils.isNotBlank(remark) && remark.getBytes("Unicode").length > 255){
					remark = org.apache.commons.lang3.StringUtils.substring(remark, 0, 10);
				}
			} catch (Exception e) {
				logger.error(e);
			}
			item.setParams(params);
			item.setRemark(remark);
			this.systemLogService.save(item);
		}
	}
	
	/**
	 * 设置请求失败或成功的信息
	 * @Date 2016年1月12日 上午9:20:00
	 * @param errorMsg null或者空表示成功  其他表示失败
	 */
	protected void setCode(JSONObject json,String errorMsg){
		if(StringUtils.isEmpty(errorMsg)){
			json.put("code", 0);
			json.put("msg", SUCCESS);
		}else{
			json.put("code", 1);
			json.put("msg", errorMsg);

		}
	}
	
}
