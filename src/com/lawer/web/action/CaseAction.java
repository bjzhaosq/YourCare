package com.lawer.web.action;

import com.alibaba.fastjson.JSONObject;
import com.lawer.context.Constant;
import com.lawer.domain.*;
import com.lawer.model.OrderFilter.OrderType;
import com.lawer.model.Page;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchFilter.Operator;
import com.lawer.model.SearchParam;
import com.lawer.service.*;
import com.lawer.util.NumberUtils;
import com.lawer.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.*;

@Namespace("/case")
@ParentPackage("p2p-default")
public class CaseAction extends BaseAction {

	private static Logger logger = Logger.getLogger(CaseAction.class);

	@Autowired
	private LawCaseService lawCaseService;
	@Autowired
	private LawTypeListService lawTypeListService;
	@Autowired
	private LawTypeService lawTypeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private LawAccessoryService lawAccessoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProcessTypeService processTypeService;
	@Autowired
	private LawerCaseService lawerCaseService;
	@Autowired
	private LawerHelpCaseService lawerHelpCaseService;
	@Autowired
	private LawProcessService lawProcessService;
	
	private File[] file;	
	private String[] fileFileName;
	private int[] usernameId_x;
	private List<LawProcess> lawProcessList;

	public File[] getFile() {
		return file;
	}
	public void setFile(File[] file) {
		this.file = file;
	}
	public String[] getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public int[] getUsernameId_x() {
		return usernameId_x;
	}
	public void setUsernameId_x(int[] usernameId_x) {
		this.usernameId_x = usernameId_x;
	}
	
	public List<LawProcess> getLawProcessList() {
		return lawProcessList;
	}
	public void setLawProcessList(List<LawProcess> lawProcessList) {
		this.lawProcessList = lawProcessList;
	}
	/**
	 * 案件页面首页
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/case_M", results = { @Result(name = "success", type = "ftl", location = "/case_M.html") })
	public String case_M() throws Exception {
		Map<String, Object> extraparam = new HashMap<String, Object>();
		int page = paramInt("page");
		
		String status = paramString("status");
		String caseName = paramString("caseName");
		String caseNumber = paramString("caseNumber");
		String customer = paramString("customer");
		String statusOne = paramString("statusOne");
		String statusTwo = paramString("statusTwo");
		String username = paramString("username");
		
		SearchParam param = SearchParam.getInstance();
		if(StringUtils.isBlank(status)){
			status = "1";
		}
		if(status.equals("2")){
			param.addOrFilter("status", "2","3");
		}else{
			param.addParam("status",status);
		}

		if(!StringUtils.isBlank(caseName)){
			param.addParam("caseName",Operator.LIKE, caseName);
		}
		if(!StringUtils.isBlank(caseNumber)){
			param.addParam("caseNumber",Operator.LIKE, caseNumber);
		}
		if(!StringUtils.isBlank(customer)){
			param.addParam("customerId.username",Operator.LIKE, customer);
		}
		
		SearchParam param2 = SearchParam.getInstance();
		List<LawType> list2;
		if(statusOne!=""){
			param2.addParam("statusOne", statusOne);
		}
		if(statusTwo!=""){
			param2.addParam("statusTwo", statusTwo);
		}
		if(statusOne!=""||statusTwo!=""){
			list2 = lawTypeService.findByParam(param2);
			if(list2!=null&&list2.size()>0){
				param.addOrFilter("lawTypeId", list2.toArray());
			}
		}
		
		param.addOrder(OrderType.DESC, "addtime");
		
		param.addPage(page+1, Page.ROWS);
		extraparam.put("page", page);
		
		PageDataList<LawCase> list = lawCaseService.findAllByParam(param);
		if(list!=null&&list.getList()!=null){
			request.setAttribute("list",list.getList());
			request.setAttribute("pages",list.getPage());
		}
		
		//案件类型
		SearchParam param3 = SearchParam.getInstance();
		param3.addParam("status", "1");
		List<LawTypeList> typeList = lawTypeListService.findAll(param3);
		
		//委托人
		if(!StringUtils.isBlank(username)){
			param3.addParam("username",Operator.LIKE, username);
		}
		List<Customer> customerList = customerService.findAll(param3);
		
		request.setAttribute("status",status);
		request.setAttribute("typeList",typeList);
		request.setAttribute("customer",customerList);
		
		return SUCCESS;
	}
	
	/**
	 * 新增跳转
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/addCase", results = { @Result(name = "success", type = "ftl", location = "/case_M_newly.html") })
	public String addCase() throws Exception {
		SearchParam param = SearchParam.getInstance();
		param.addParam("status", "1");
		List<LawTypeList> typeList = lawTypeListService.findAll(param);

		String username = paramString("username");
		if(!StringUtils.isBlank(username)){
			param.addParam("username",Operator.LIKE, username);
		}
		
		List<Customer> customerList = customerService.findAll(param);

		request.setAttribute("typeList",typeList);
		request.setAttribute("customer",customerList);
		
		
		String caseId = paramString("caseId");
		if(!StringUtils.isBlank(caseId)){
			//编辑
			SearchParam param2 = SearchParam.getInstance();
			param2.addParam("id", caseId);
			param2.addParam("status", "1");
			LawCase lawCase = null;
			PageDataList<LawCase> findAllByParam = lawCaseService.findAllByParam(param2);
			if(findAllByParam!=null&&findAllByParam.getList()!=null){
				lawCase = findAllByParam.getList().get(0);
			}else{
				request.setAttribute("msg", "存储异常");
				return ERROR;
			}
			
			SearchParam param3 = SearchParam.getInstance();
			param3.addParam("lawCaseId", lawCase);
			param3.addParam("status", "1");
			List<LawAccessory> lawAccessoryList = lawAccessoryService.findLawAccessory(param3);
			
			for (LawAccessory la : lawAccessoryList) {
				String name = la.getName();
				String fileExt = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
				la.setRemark(fileExt);
			}
			
			request.setAttribute("lawCase",lawCase);
			request.setAttribute("lawAccessory",lawAccessoryList);
		}
		return SUCCESS;
	}
	
	/**
	 * 保存案件
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/saveCase", results = { @Result(name = "success", type = "redirect", location = "/case/case_M.html") })
	public String saveCase() throws Exception {
		
		String caseNumber = paramString("caseNumber");
		if(StringUtils.isBlank(caseNumber)){
			request.setAttribute("msg", "请填写案件编号");
			return ERROR;
		}
		
		String caseName = paramString("caseName");
		if(StringUtils.isBlank(caseName)){
			request.setAttribute("msg", "请填写案件名称");
			return ERROR;
		}
		String statusOne = paramString("statusOne");
		String statusTwo = paramString("statusTwo");
		if(StringUtils.isBlank(statusOne)||StringUtils.isBlank(statusTwo)){
			request.setAttribute("msg", "请选择案件类型");
			return ERROR;
		}
		
		Integer customerId = paramInt("customerId");
		String content = paramString("content");
		
		String caseId = paramString("caseId");
		LawCase lawCase =null;
		if(!StringUtils.isBlank(caseId)){
			//编辑保存
			SearchParam param = SearchParam.getInstance();
			param.addParam("id", caseId);
			PageDataList<LawCase> findAllByParam = lawCaseService.findAllByParam(param);
			if(findAllByParam!=null&&findAllByParam.getList()!=null){
				lawCase = findAllByParam.getList().get(0);
			}else{
				request.setAttribute("msg", "保存异常");
				return ERROR;
			}
			//新案件编号是否重复
			if(!lawCase.getCaseNumber().equals(caseNumber)){
				SearchParam param2 = SearchParam.getInstance();
				param2.addParam("caseNumber", caseNumber);
				PageDataList<LawCase> LawCaseList = lawCaseService.findAllByParam(param2);
				if(LawCaseList!=null&&LawCaseList.getList()!=null){
					request.setAttribute("msg", "案件编号重复,请重新填写");
					return ERROR;
				}
			}
			
		}else{
			//新增
			SearchParam param2 = SearchParam.getInstance();
			param2.addParam("caseNumber", caseNumber);
			PageDataList<LawCase> LawCaseList = lawCaseService.findAllByParam(param2);
			if(LawCaseList!=null&&LawCaseList.getList()!=null){
				request.setAttribute("msg", "案件编号重复,请重新填写");
				return ERROR;
			}
			//保存
			lawCase = new LawCase();
			
		}
		lawCase.setCaseName(caseName);
		lawCase.setCaseNumber(caseNumber);
		lawCase.setStatus("1");
		lawCase.setAddtime(new Date());
		lawCase.setContent(content);

		//委托人
		if(customerId!=0){
			Customer cus = new Customer();
			cus.setId(customerId);
			lawCase.setCustomerId(cus);
		}
		
		//保存类型
		LawType lawTypeId = saveLawType(statusOne, statusTwo);
		lawCase.setLawTypeId(lawTypeId);
		
		LawCase case1 = null;
		if(!StringUtils.isBlank(caseId)){
			//编辑
			lawCaseService.updateLawCase(lawCase);
			//保存附件
			saveLawAccessory(lawCase);
		}else{
			//新增
			case1 = lawCaseService.saveLawCase(lawCase);

			saveLawAccessory(case1);
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 保存附件
	 * @param lawCase
	 * @return
	 * @throws Exception
	 */
	public boolean saveLawAccessory(LawCase lawCase) throws Exception {
		if(lawCase==null){
			return false;
		}
		List<LawAccessory> lawAccessoryList = new ArrayList<LawAccessory>();
		LawAccessory la;
		if(file!=null&&file.length>0){
			for (int i = 0;i<file.length;i++) {
				la = new LawAccessory();
				String fileName = fileFileName[i];
				String url = this.moveFile(lawCase, file[i],fileName);
				la.setLawCaseId(lawCase);
				la.setName(fileName);
				la.setStatus("1");
				la.setUrl(url);
				la.setAddtime(new Date());
				lawAccessoryList.add(la);
			}
		}
		
		lawAccessoryService.saveLawAccessory(lawAccessoryList);
		
		return true;
	}
	
	/**
	 * 保存类型
	 * @return
	 * @throws Exception
	 */
	public LawType saveLawType(String statusOne,String statusTwo) throws Exception {
		SearchParam param = SearchParam.getInstance();
		param.addParam("statusOne", statusOne);
		param.addParam("statusTwo", statusTwo);
		LawType lawType;
		List<LawType> lawTypeList = lawTypeService.findByParam(param);
		if(lawTypeList!=null && lawTypeList.size()>0){
			lawType= lawTypeList.get(0);
			return lawType;
		}else{
			lawType = new LawType();
			lawType.setStatusOne(statusOne);
			lawType.setStatusTwo(statusTwo);
			lawType.setAddtime(new Date());
			LawType lawType2 = lawTypeService.saveLawType(lawType);
			return lawType2;
		}
		
	}
	
	/**
	 * 编辑页面删除附件
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/delLawAccessory", results = { @Result(name = "success", type = "ftl", location = "/case_M_newly.html") })
	public String delLawAccessory() throws Exception {
		
		String lawAccessoryId = paramString("lawAccessoryId");
		SearchParam param = SearchParam.getInstance();
		param.addParam("id", lawAccessoryId);
		param.addParam("status", "1");
		//查询对应的附件
		List<LawAccessory> lawAccessoryList = lawAccessoryService.findLawAccessory(param);
		LawAccessory la = lawAccessoryList.get(0);
		
		la.setStatus("0");
		
		lawAccessoryService.updateLawAccessory(la);
		
		return SUCCESS;
	}
	
	/**
	 * 案件审批页面跳转
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/case_sp", results = { @Result(name = "success", type = "ftl", location = "/case_sp.html") })
	public String case_sp() throws Exception {
		
		String username = paramString("username");
		String caseId = paramString("caseId");
		
		SearchParam param = SearchParam.getInstance();
		param.addParam("status", "1");
		//律师列表
		List<User> userList = userService.findAllUserByParam(param);
		request.setAttribute("userList",userList);
		
		
		//委托人
		if(!StringUtils.isBlank(username)){
			param.addParam("username",Operator.LIKE, username);
		}
		List<Customer> customerList = customerService.findAll(param);
		request.setAttribute("customer",customerList);
		
		//案件信息和附件
		SearchParam param2 = SearchParam.getInstance();
		param2.addParam("status", "1");
		param2.addParam("id", caseId);
		
		PageDataList<LawCase> findAllByParam = lawCaseService.findAllByParam(param2);
		LawCase lawCase = null;
		if(findAllByParam!=null&&findAllByParam.getList()!=null){
			lawCase = findAllByParam.getList().get(0);
		}
		
		//案件类型
		List<LawTypeList> typeList = lawTypeListService.findAll(param);
		request.setAttribute("typeList",typeList);
		
		SearchParam param3 = SearchParam.getInstance();
		param3.addParam("status", "1");
		param3.addParam("lawCaseId", lawCase);
		List<LawAccessory> lawAccessoryList = lawAccessoryService.findLawAccessory(param3);
		
		for (LawAccessory la : lawAccessoryList) {
			String name = la.getName();
			String fileExt = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
			la.setRemark(fileExt);
		}
		request.setAttribute("lawCase",lawCase);
		request.setAttribute("lawAccessory",lawAccessoryList);
		
		
		return SUCCESS;
	}
	
	//查询案件信息和附件
	public void findCaseAccessory(String caseId) throws Exception {
		SearchParam param2 = SearchParam.getInstance();
		param2.addParam("status", "1");
		param2.addParam("id", caseId);
		
		PageDataList<LawCase> findAllByParam = lawCaseService.findAllByParam(param2);
		LawCase lawCase = null;
		if(findAllByParam!=null&&findAllByParam.getList()!=null){
			lawCase = findAllByParam.getList().get(0);
		}
		SearchParam param3 = SearchParam.getInstance();
		param3.addParam("status", "1");
		param3.addParam("lawCaseId", lawCase);
		List<LawAccessory> lawAccessoryList = lawAccessoryService.findLawAccessory(param3);
		
		for (LawAccessory la : lawAccessoryList) {
			String name = la.getName();
			String fileExt = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
			la.setRemark(fileExt);
		}
		request.setAttribute("lawCase",lawCase);
		request.setAttribute("lawAccessory",lawAccessoryList);
		
	}
	
	//选择委托人列表
	public void customers(String username) throws Exception {
		SearchParam param = SearchParam.getInstance();
		param.addParam("status", "1");
		if(!StringUtils.isBlank(username)){
			param.addParam("username",Operator.LIKE, username);
		}
		List<Customer> customerList = customerService.findAll(param);
		request.setAttribute("customer",customerList);
	}
	
	//查询案件类型
	public void findTypeList() throws Exception {
		SearchParam param = SearchParam.getInstance();
		param.addParam("status", "1");
		List<LawTypeList> typeList = lawTypeListService.findAll(param);
		request.setAttribute("typeList",typeList);
	}
	
	/**
	 * 保存审批
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/caseSP", results = { @Result(name = "success", type = "redirect", location = "/case/case_M.html") })
	public String caseSP() throws Exception {
		
		String advice = paramString("advice");
		String caseId = paramString("caseId");
		//String usernameId_w = paramString("usernameId_w");
		String usernameId_d = paramString("usernameId_d");//代理律师
		
		SearchParam param = SearchParam.getInstance();
		param.addParam("status", "1");
		param.addParam("id", caseId);
		PageDataList<LawCase> LawCaseList = lawCaseService.findAllByParam(param);
		LawCase lawCase = null;
		if(LawCaseList!=null&&LawCaseList.getList()!=null){
			lawCase = LawCaseList.getList().get(0);
		}else{
			request.setAttribute("msg", "无法获取案件信息,保存失败");
			return ERROR;
		}
		User u = new User();
		LawerCase lrc = null;
		if(!StringUtils.isBlank(usernameId_d)){
			u.setId(NumberUtils.getInt(usernameId_d));
			LawerCase lawerCase = new LawerCase();
			lawerCase.setAddtime(new Date());
			lawerCase.setStatus("1");
			lawerCase.setLawCaseId(lawCase);
			lawerCase.setCaseView(advice);
			lawerCase.setUserId(u);
			lrc = lawerCaseService.save(lawerCase);//保存代理律师
		}
		
		if(lrc == null){
			request.setAttribute("msg", "代理律师存储异常");
			return ERROR;
		}
		LawerHelpCase lawerHelpCase = null;
		List<LawerHelpCase> list = new ArrayList<LawerHelpCase>();
		if(usernameId_x!= null &&usernameId_x.length>0){
			
			for (int i = 0; i < usernameId_x.length; i++) {
				int userId = usernameId_x[i];
				User user = new User();
				user.setId(userId);
				lawerHelpCase = new LawerHelpCase();
				lawerHelpCase.setAddtime(new Date());
				lawerHelpCase.setStatus("1");
				lawerHelpCase.setLawrCaseId(lrc);	//
				lawerHelpCase.setUserId(user);
				list.add(lawerHelpCase);
			}
			
			lawerHelpCaseService.save(list);//保存协助律师
		}
		
		lawCase.setStatus("2");
		lawCaseService.updateLawCase(lawCase);//更新案件状态为2
		

		return SUCCESS;
	}
	
	
	/**
	 * 案件进度页面
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/caseSchedule", results = { @Result(name = "success", type = "ftl", location = "/case_progress.html") })
	public String caseSchedule() throws Exception {
		String caseId = paramString("caseId");
		if(StringUtils.isBlank(caseId)){
			request.setAttribute("msg", "参数错误");
			return ERROR;
		}
		SearchParam param = SearchParam.getInstance();
		param.addParam("status",Operator.NOTEQ ,"4");//4为删除状态
		
		//进度类型
		List<ProcessType> processTypeList = processTypeService.findAllByParam(param);
		List<User> list = userService.findAllUserByParam(param);
		
		SearchParam param2 = SearchParam.getInstance();
		param2.addParam("id", caseId);
		PageDataList<LawCase> findAllByParam = lawCaseService.findAllByParam(param2);
		LawCase lawCase = null;
		if(findAllByParam!=null&&findAllByParam.getList()!=null){
			lawCase = findAllByParam.getList().get(0);
		}
		
		param.addParam("lawCaseId.id", caseId);
		List<LawProcess> lawProcessList = lawProcessService.findAllByParam(param);
		
		request.setAttribute("userList",list);
		request.setAttribute("lawProcessList",lawProcessList);
		request.setAttribute("processTypeList",processTypeList);
		request.setAttribute("lawCase",lawCase);
		return SUCCESS;
	}
	
	/**
	 * 保存案件进度
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/saveSchedule", results = { @Result(name = "success", type = "redirect", location = "/case/case_M.html?status=2") })
	public String saveSchedule() throws Exception {
		String caseId = paramString("caseId");
		if(lawProcessList!= null && lawProcessList.size()>0){
			for (LawProcess lpl : lawProcessList) {
				if(StringUtils.isBlank(lpl.getName())){
					request.setAttribute("msg", "请填写工作内容");
					return ERROR;
				}
				if(lpl.getDeadline()==null){
					request.setAttribute("msg", "请选择截止日期");
					return ERROR;
				}
				if(lpl.getUserId().getId()==null){
					request.setAttribute("msg", "请选择律师");
					return ERROR;
				}
			}
			
			lawProcessService.save(lawProcessList);
			
			SearchParam param = SearchParam.getInstance();
			param.addParam("id", caseId);
			PageDataList<LawCase> list = lawCaseService.findAllByParam(param);
			LawCase lawCase= null;
			if(list!=null&&list.getList()!=null){
				lawCase = list.getList().get(0);
			}else{
				request.setAttribute("msg", "案件信息获取异常");
				return ERROR;
			}
			lawCase.setStatus("3");	//设置案件状态为终审
			lawCaseService.updateLawCase(lawCase);
		}
		
		return SUCCESS;
	}
	
	
	@Action(value = "findUser")
	public void findUser() {
		
		String username = paramString("username");
		SearchParam param = SearchParam.getInstance();
		param.addParam("status", "1");
		if(!StringUtils.isBlank(username)){
			param.addParam("username",Operator.LIKE, username);
		}
		List<User> list = userService.findAllUserByParam(param);

		JSONObject json = new JSONObject();
		json.put("code",0);
		json.put("userList",list);
		printJson(json.toString());
	}
	
	@Action(value = "findCustomer")
	public void findCustomer() {
		
		SearchParam param = SearchParam.getInstance();
		String username = paramString("username");
		if(!StringUtils.isBlank(username)){
			param.addParam("username",Operator.LIKE, username);
		}
		param.addParam("status", "1");
		List<Customer> customerList = customerService.findAll(param);

		JSONObject json = new JSONObject();
		json.put("code",0);
		json.put("customer",customerList);
		printJson(json.toString());
	}
	
	/**
	 * 删除进度
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/delSchedule", results = { @Result(name = "success", type = "redirect", location = "/case/caseSchedule.html") })
	public String delSchedule() throws Exception {
		
		String progressId = paramString("progressId");
		String status = paramString("status");
		if(StringUtils.isBlank(status)){
			request.setAttribute("msg", "参数错误");
			return ERROR;
		}
		if(!StringUtils.isBlank(status)&&!status.equals("1")){
			request.setAttribute("msg", "该项目不可删除");
			return ERROR;
		}
		LawProcess lawProcess = null;;
		if(!StringUtils.isBlank(progressId)){
			lawProcess = lawProcessService.findLpById(NumberUtils.getInt(progressId));
		}
		lawProcess.setStatus("4");
		lawProcessService.updateLp(lawProcess);
		
		return SUCCESS;
	}
	
	
	/**
	 * 终审跳转
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/case_zs", results = { @Result(name = "success", type = "ftl", location = "/case_zs.html") })
	public String case_zs() throws Exception {
		String caseId = paramString("caseId");
		if(StringUtils.isBlank(caseId)){
			request.setAttribute("msg", "参数错误");
			return ERROR;
		}
		SearchParam param = SearchParam.getInstance();
		param.addParam("id", caseId);
		
		PageDataList<LawCase> list = lawCaseService.findAllByParam(param);
		LawCase lawCase = null;
		if(list!=null&&list.getList()!=null){
			lawCase = list.getList().get(0);
		}
		
		Map<String, Object> extraparam = new HashMap<String, Object>();
		int page = paramInt("page");
		
		SearchParam param2 = SearchParam.getInstance();
		param2.addParam("status",Operator.NOTEQ ,"4");
		param2.addParam("lawCaseId.id", caseId);
		param2.addPage(page+1, Page.ROWS);
		extraparam.put("page", page);
		param2.addOrder(OrderType.DESC, "addtime");
		
		PageDataList<LawProcess> list2 = lawProcessService.findPageList(param2);
		
		if(list2!=null&&list2.getList()!=null){
			request.setAttribute("list",list2.getList());
			request.setAttribute("pages",list2.getPage());
		}
		for (LawAccessory la : lawCase.getLawAccessories()) {
			String name = la.getName();
			String fileExt = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
			la.setRemark(fileExt);
		}
		
		request.setAttribute("lawCase",lawCase);
		return SUCCESS;
	}
	
	/**
	 * 保存意见
	 * @return
	 * @throws Exception
	 */
	/*@Action(value = "/saveComment")
	public void saveComment() throws Exception {
		JSONObject jsonObject = new JSONObject();
        String code = Constant.STATUS_YES;
        String msg = "success";
        String comment = paramString("comment");
        Integer caseId = paramInt("caseId");
        
        SearchParam param = SearchParam.getInstance();
		param.addParam("id", caseId);
        PageDataList<LawCase> pageDataList = lawCaseService.findAllByParam(param);
        LawCase lawCase = null;
        if(pageDataList!=null&&pageDataList.getList()!=null){
        	lawCase = pageDataList.getList().get(0);
        }
        if(null != lawCase){
        	lawCase.setContentZs(comment);
        	lawCase.setStatus("4");
        	lawCaseService.saveLawCase(lawCase);
        }else{
            code = Constant.STATUS_NO;
            msg = "获取案件信息异常";
        }
        
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        printJson(jsonObject.toString());

	}*/
	@Action(value = "/saveComment2", results = { @Result(name = "success", type = "redirect", location = "/case/case_M.html?status=3") })
	public String saveComment2() throws Exception {
		
		String comment = paramString("comment");
        Integer caseId = paramInt("caseId");
        
        SearchParam param = SearchParam.getInstance();
		param.addParam("id", caseId);
        PageDataList<LawCase> pageDataList = lawCaseService.findAllByParam(param);
        LawCase lawCase = null;
        if(pageDataList!=null&&pageDataList.getList()!=null){
        	lawCase = pageDataList.getList().get(0);
        }
		
        if(null != lawCase){
        	lawCase.setContentZs(comment);
        	lawCase.setStatus("4");
        	lawCaseService.saveLawCase(lawCase);
        }
        
        return SUCCESS;
		
	}
	
	
	
	
	
	
	
	/**
	 * 查看案件详情
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/viewDetails", results = { @Result(name = "success", type = "ftl", location = "/viewDetails.html") })
	public String viewDetails() throws Exception {
		
		Integer caseId = paramInt("caseId");
		SearchParam param2 = SearchParam.getInstance();
		param2.addParam("id", caseId);
        PageDataList<LawCase> lawCaseList = lawCaseService.findAllByParam(param2);
        LawCase lawCase = null;
        if(null != lawCaseList && null != lawCaseList.getList() && lawCaseList.getList().size()>0){
            lawCase = lawCaseList.getList().get(0);
        }
        
        if(null == lawCase){
        	request.setAttribute("msg", "参数错误");
        	return ERROR;
        }
        
        Map<String, Object> extraparam = new HashMap<String, Object>();
		int page = paramInt("page");
		SearchParam param = SearchParam.getInstance();
		param.addParam("status",Operator.NOTEQ ,"4");
		param.addParam("lawCaseId", lawCase);
		param.addPage(page+1, Page.ROWS);
		extraparam.put("page", page);
		param.addOrder(OrderType.DESC, "addtime");
		//查询进度任务
		PageDataList<LawProcess> list = lawProcessService.findPageList(param);
		
		if(list!=null&&list.getList()!=null){
			request.setAttribute("list",list.getList());
			request.setAttribute("pages",list.getPage());
		}
		
		for (LawAccessory la : lawCase.getLawAccessories()) {
			String name = la.getName();
			String fileExt = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
			la.setRemark(fileExt);
		}
		request.setAttribute("lawCase",lawCase);
		
		return SUCCESS;
	}
	
}
