package com.lawer.web.action.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.lawer.domain.LawProcess;
import com.lawer.domain.LawProcessContext;
import com.lawer.domain.LawProcessTime;
import com.lawer.domain.LawProcessType;
import com.lawer.domain.ProcessType;
import com.lawer.domain.User;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchFilter.Operator;
import com.lawer.model.SearchParam;
import com.lawer.model.TodoDetailStatus;
import com.lawer.service.LawCaseService;
import com.lawer.service.LawProcessContextService;
import com.lawer.service.LawProcessService;
import com.lawer.service.LawProcessTimeService;
import com.lawer.service.ProcessTypeService;
import com.lawer.util.DateUtils;
import com.lawer.util.StringUtils;
import com.lawer.web.action.BaseAction;

@Namespace("/mobile/todo")
@ParentPackage("p2p-default")
public class  MobileToDoAction extends BaseAction {
	private static Logger logger = Logger.getLogger(MobileToDoAction.class);

	@Autowired
	private LawCaseService lawCaseService;
	@Autowired
	private LawProcessService lawProcessService;
	@Autowired
	private ProcessTypeService processTypeService;
	@Autowired
	private LawProcessTimeService lawProcessTimeService;
	@Autowired
	private LawProcessContextService lawProcessContextService;

	private LawProcess lawProcess;

	private ProcessType processType;

	private LawProcessContext lawProcessContext;

	private List<TodoDetailStatus> todolist = new ArrayList<TodoDetailStatus>();

	public List<TodoDetailStatus> getTodolist() {
		return todolist;
	}

	public void setTodolist(List<TodoDetailStatus> todolist) {
		this.todolist = todolist;
	}

	public LawProcessContext getLawProcessContext() {
		return lawProcessContext;
	}

	public void setLawProcessContext(LawProcessContext lawProcessContext) {
		this.lawProcessContext = lawProcessContext;
	}

	public LawProcess getLawProcess() {
		return lawProcess;
	}

	public void setLawProcess(LawProcess lawProcess) {
		this.lawProcess = lawProcess;
	}

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}

	@Action(value = "/showTodoDetail", results = { @Result(name = "success", type = "ftl", location = "/mobile/mobile_todo_workList.html") })
	public String showTodoDetail() throws Exception {
		Integer id = paramInt("id");
		lawProcess = lawProcessService.findLpById(id);
		if(null == lawProcess){
			request.setAttribute("msg", "参数错误");
			return MOBILE_ERROR;
		}else{
			if("0".equals(lawProcess.getStatus())){
				request.setAttribute("msg", "该条数据不可以查看");
				return MOBILE_ERROR;
			}
		}
		return SUCCESS;
	}

	@Action(value = "/showTodoDetailFor", results = { @Result(name = "success", type = "ftl", location = "/mobile/mobile_todo_workList.html") })
	public String showTodoDetailFor() throws Exception {
		Integer id = paramInt("id");
		lawProcess = lawProcessService.findLpById(id);
		if(null == lawProcess){
			request.setAttribute("msg", "参数错误");
			return MOBILE_ERROR;
		}else{
			if("1".equals(lawProcess.getStatus()) || "2".equals(lawProcess.getStatus())){
				request.setAttribute("msg", "该条数据不可以查看");
				return MOBILE_ERROR;
			}
		}
		return SUCCESS;
	}

	@Action(value = "/newlyTodoDetail", results = { @Result(name = "success", type = "ftl", location = "/mobile/mobile_comment.html") })
	public String newlyTodoDetail() throws Exception {
		Integer id = paramInt("typeId");
		Integer lpId = paramInt("lpId");

		lawProcess = lawProcessService.findLpById(lpId);
		if(null != lawProcess){
			Date deadline = lawProcess.getDeadline();
			if(deadline.before(new Date())){
				request.setAttribute("msg", "该待办任务已经过期");
				return MOBILE_ERROR;
			}
			if("0".equals(lawProcess.getStatus()) || "3".equals(lawProcess.getStatus())){
				request.setAttribute("msg", "该数据不可以编辑");
				return MOBILE_ERROR;
			}
			request.setAttribute("lpId",lpId);
			processType = processTypeService.findPyById(id);
			if(null == processType){
				request.setAttribute("msg", "参数错误");
				return MOBILE_ERROR;
			}else{
				if("0".equals(processType.getStatus())){
					request.setAttribute("msg", "该条数据不可以新增");
					return MOBILE_ERROR;
				}
			}
		}else{
			request.setAttribute("msg", "参数错误");
			return MOBILE_ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 保存开始工作
	 */
	@Action(value = "/saveTodoStartWork")
	public void saveTodoStartWork(){
		JSONObject json = new JSONObject();
		if(!checkParam("lptypeId","time","order","content","lpId")){
			setCode(json, "请求参数错误");
			printJson(json.toString());
			return;
		}
		Integer lptype = paramInt("lptypeId");
		int order = paramInt("order");
		String time = paramString("time");
		String content = paramString("content");
		int lpId = paramInt("lpId");

		//保存 lawprocessContent
		LawProcess lp = lawProcessService.findLpById(lpId);
		if(null != lp) {
			if (!"2".equals(lp.getStatus())) {
				lp.setStatus("2");
				lawProcessService.updateLp(lp);
			}
			Date date = new Date();
			if(date.after(lp.getDeadline())){
				//超时
				lp.setStatus("0");//未完成
				lawProcessService.updateLp(lp);
				setCode(json, "该任务已过期");
				printJson(json.toString());
				return;
			}
			LawProcessContext lpc = new LawProcessContext();
			lpc.setContext(content);
			lpc.setStatus("1");
			lpc.setAddtime(new Date());
			lpc.setStartTime(new Date());
			lpc.setLawProcessId(lp);
			lawProcessContextService.saveLpc(lpc);

			//保存lawprocessTime
			LawProcessTime lpt = new LawProcessTime();
			LawProcessType lawProcessType = new LawProcessType();
			lawProcessType.setId(lptype);
			lpt.setLawProcessTypeId(lawProcessType);
			lpt.setLawProcessContextId(lpc);
			lpt.setStartTime(DateUtils.getDate2(time));
			lpt.setStatus("1");
			lpt.setAddtime(new Date());
			lawProcessTimeService.savelpt(lpt);

			setCode(json, "");
			json.put("lpcId",lpc.getId());
			printJson(json.toString());
		}else{
			logger.info("saveTodoStartWork保存失败，lawprocess 为空");
			setCode(json, "保存失败");
			printJson(json.toString());
		}

	}

	/**
	 * 保存开始工作
	 */
	@Action(value = "/saveTodoWork")
	public void saveTodoWork(){
		JSONObject json = new JSONObject();
		if(!checkParam("lptypeId","time","order","content","lpId","lpcId")){
			setCode(json, "请求参数错误");
			printJson(json.toString());
			return;
		}
		Integer lptype = paramInt("lptypeId");
		int order = paramInt("order");
		String time = paramString("time");
		String content = paramString("content");
		Integer lpId = paramInt("lpId");
		Integer lpcId = paramInt("lpcId");

		//保存 lawprocessContent
		LawProcess lp = lawProcessService.findLpById(lpId);
		if(null != lp) {
			if (!"2".equals(lp.getStatus())) {
				lp.setStatus("2");
				lawProcessService.updateLp(lp);
			}
			Date date = new Date();
			if(date.after(lp.getDeadline())){
				//超时
				lp.setStatus("0");//未完成
				lawProcessService.updateLp(lp);
				setCode(json, "该任务已过期");
				printJson(json.toString());
				return;
			}
			LawProcessContext lpc = lawProcessContextService.findlpc(lpcId);
			lpc.setContext(content);
			lpc.setStartTime(new Date());
			lawProcessContextService.updateLpc(lpc);

			//保存lawprocessTime
			LawProcessTime lpt = new LawProcessTime();
			LawProcessType lawProcessType = new LawProcessType();
			lawProcessType.setId(lptype);
			lpt.setLawProcessTypeId(lawProcessType);
			lpt.setLawProcessContextId(lpc);
			lpt.setStartTime(DateUtils.getDate2(time));
			lpt.setStatus("1");
			lpt.setAddtime(new Date());
			lawProcessTimeService.savelpt(lpt);

			setCode(json, "");
			printJson(json.toString());
		}else{
			logger.info("saveTodoStartWork保存失败，lawprocess 为空");
			setCode(json, "保存失败");
			printJson(json.toString());
		}

	}

	/**
	 * 保存开始工作
	 */
	@Action(value = "/submitTodoWork")
	public void submitTodoWork(){
		JSONObject json = new JSONObject();
		if(!checkParam("lpId","lpcId")){
			setCode(json, "请求参数错误");
			printJson(json.toString());
			return;
		}
		Integer lpId = paramInt("lpId");
		Integer lpcId = paramInt("lpcId");

		//保存 lawprocessContent
		LawProcess lp = lawProcessService.findLpById(lpId);
		if(null != lp) {
			Date date = new Date();
			if(date.after(lp.getDeadline())){
				//超时
				lp.setStatus("0");//未完成
				lawProcessService.updateLp(lp);
				setCode(json, "该任务已过期");
				printJson(json.toString());
				return;
			}
			LawProcessContext lpc = lawProcessContextService.findlpc(lpcId);
			String msg = "提交失败";
			if(null != lp.getProcessType().getLawProcessTypes() && lp.getProcessType().getLawProcessTypes().size()>0){
				if(null != lpc.getLawProcessTimes() && lpc.getLawProcessTimes().size()>0){
					if((lp.getProcessType().getLawProcessTypes().size()) != (lpc.getLawProcessTimes().size())){
						setCode(json, "该任务未完成不可以提交");
						printJson(json.toString());
						return;
					}else{
						lpc.setStatus("2");
						lpc.setStartTime(new Date());
						lawProcessContextService.updateLpc(lpc);
						msg = "";
					}
				}
			}


			setCode(json, msg);
			printJson(json.toString());
		}else{
			logger.info("saveTodoStartWork保存失败，lawprocess 为空");
			setCode(json, "保存失败");
			printJson(json.toString());
		}

	}

	/**
	 * 编辑TODO任务
	 * @return
	 */
	@Action(value = "/editTodoListDetail", results = { @Result(name = "success", type = "ftl", location = "/mobile/mobile_comment_detail.html") })
	public String editTodoListDetail(){
		if(!checkParam("typeId","lpId","lpcId")){
			request.setAttribute("msg", "参数错误");
			return MOBILE_ERROR;
		}
		Integer typeId = paramInt("typeId");
		Integer lpId = paramInt("lpId");
		Integer lpcId = paramInt("lpcId");

		//查询所有的任务状态，待办任务项
		lawProcess = lawProcessService.findLpById(lpId);
		if(null != lawProcess){
			Date deadline = lawProcess.getDeadline();
			if(deadline.before(new Date())){
				request.setAttribute("msg", "该待办任务已经过期");
				return MOBILE_ERROR;
			}
			if("0".equals(lawProcess.getStatus()) || "3".equals(lawProcess.getStatus())){
				request.setAttribute("msg", "该数据不可以编辑");
				return MOBILE_ERROR;
			}
			request.setAttribute("lpId",lpId);
			processType = processTypeService.findPyById(typeId);
			if(null == processType){
				request.setAttribute("msg", "参数错误");
				return MOBILE_ERROR;
			}else{
				if("0".equals(processType.getStatus())){
					request.setAttribute("msg", "该条数据不可以新增");
					return MOBILE_ERROR;
				}
				//查找已经保存过得状态
				lawProcessContext = lawProcessContextService.findlpc(lpcId);
				if(null != lawProcessContext){
					//查看已经保存的状态
					if(null != processType.getLawProcessTypes() && processType.getLawProcessTypes().size()>0){
						for(LawProcessType lpt : processType.getLawProcessTypes()){
							TodoDetailStatus todoDetailStatus = new TodoDetailStatus();
							todoDetailStatus.setName(lpt.getName());
							todoDetailStatus.setOrder(lpt.getOrder());
							todoDetailStatus.setLptId(lpt.getId());
							if(null != lawProcessContext.getLawProcessTimes() && lawProcessContext.getLawProcessTimes().size()>0){
								for(LawProcessTime lptime : lawProcessContext.getLawProcessTimes()){
									if(lptime.getLawProcessTypeId().getId() == lpt.getId()){
										todoDetailStatus.setStatus("1");
										todoDetailStatus.setTime(lptime.getStartTime());
									}
								}
							}
							if( lpt.getOrder() == (lawProcessContext.getLawProcessTimes().size()+1)){
								todoDetailStatus.setStatus("2");
							}
							if(null == todoDetailStatus.getStatus()){
								todoDetailStatus.setStatus("0");
							}
							todolist.add(todoDetailStatus);
							request.setAttribute("size",processType.getLawProcessTypes().size());
							request.setAttribute("chosesize",lawProcessContext.getLawProcessTimes().size());
						}
					}else{
						request.setAttribute("msg", "待办任务 状态查询错误");
						return MOBILE_ERROR;
					}

				}else{
					request.setAttribute("msg", "参数错误");
					return MOBILE_ERROR;
				}
			}
		}else{
			request.setAttribute("msg", "参数错误");
			return MOBILE_ERROR;
		}

		return SUCCESS;
	}


	/**
	 * 待办事项页
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/detailList", results = { @Result(name = "success", type = "ftl", location = "/mobile/mobile_todo_detailList.html") })
	public String detailList() throws Exception {
		
        Map<String, Object> extraparam = new HashMap<String, Object>();
		int page = paramInt("page");
		
		SearchParam param = SearchParam.getInstance();
		
		param.addPage(page+1, 5);
		extraparam.put("page", page);
		
		param.addParam("status",Operator.NOTEQ ,"4");
		
		//获取当前登录用户
		User sessionUser = getSessionUser();
		param.addParam("userId", sessionUser);
//		param.addOrder(OrderType.DESC, "addtime");
		param.addOrder("deadline");
		PageDataList<LawProcess> list = lawProcessService.findPageList(param);
		
		if(list!=null&&list.getList()!=null){
			request.setAttribute("list",list.getList());
			request.setAttribute("pages",list.getPage());
		}
		
		return SUCCESS;
	}
	
	/**
	 * 处理页面跳转
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/todoDetailList", results = { @Result(name = "success", type = "ftl", location = "/mobile/mobile_showDetailList.html") })
	public String todoDetail() throws Exception {
		Integer id = paramInt("id");
		lawProcess = lawProcessService.findLpById(id);
		if(null == lawProcess){
			request.setAttribute("msg", "参数错误");
			return MOBILE_ERROR;
		}else{
			if("0".equals(lawProcess.getStatus())){
				request.setAttribute("msg", "该条数据不可以查看");
				return MOBILE_ERROR;
			}
		}
		return SUCCESS;
	}
	/**
	 * 查看页面跳转
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/showDetailList", results = { @Result(name = "success", type = "ftl", location = "/mobile/mobile_showDetailList.html") })
	public String showDetailList() throws Exception {
		Integer id = paramInt("id");
		lawProcess = lawProcessService.findLpById(id);
		if(null == lawProcess){
			request.setAttribute("msg", "参数错误");
			return MOBILE_ERROR;
		}else{
//			if("1".equals(lawProcess.getStatus()) || "2".equals(lawProcess.getStatus())){
//				request.setAttribute("msg", "该条数据不可以查看");
//				return MOBILE_ERROR;
//			}
		}
		request.setAttribute("status","1");
		return SUCCESS;
	}
	
	/**
	 * 待办事项最终提交
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/saveDetailList", results = { @Result(name = "success", type = "redirect", location = "/mobile/detailList.html")  })
	public String saveDetailList() throws Exception {
		Integer id = paramInt("id");
		lawProcess = lawProcessService.findLpById(id);
		if(null == lawProcess){
			request.setAttribute("msg", "参数错误");
			return MOBILE_ERROR;
		}else{
			if("0".equals(lawProcess.getStatus())){
				return "success";
			}
			//判断是否有未完成的工作进度
			List<LawProcessContext> lawProcessContextsList = lawProcess.getLawProcessContexts();
			for (LawProcessContext lpc : lawProcessContextsList) {
				 //1 未完成
				 if("1".equals(lpc.getStatus())){
					request.setAttribute("msg", "有工作进度未完成,提交失败");
					return MOBILE_ERROR;
				 }
			}
			
			//判断截止日期
			Date deadline = lawProcess.getDeadline();
			Date date = new Date();
			if(date.after(deadline)){
				//超时
				lawProcess.setStatus("0");//未完成
				lawProcessService.updateLp(lawProcess);
				request.setAttribute("msg", "此任务已超时");
				return MOBILE_ERROR;
			}
			//未超时
			lawProcess.setStatus("3");//已提交
			lawProcessService.updateLp(lawProcess);
		}
		request.setAttribute("status","1");
		return SUCCESS;
	}
	
	/**
	 * 进度详请页面跳转
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/showComment", results = { @Result(name = "success", type = "ftl", location = "/mobile/mobile_showComment.html")  })
	public String showComment() throws Exception {
		String id = paramString("id");
		if(StringUtils.isBlank(id)){
			request.setAttribute("msg", "参数错误");
			return MOBILE_ERROR;
		}
		SearchParam param = SearchParam.getInstance();
		param.addParam("id", id);
		List<LawProcessContext> list = lawProcessContextService.find(param);
		List<LawProcessType> lawProcessTypes = null;
		LawProcessContext lawProcessContext = null;
		if(null!= list && list.size()>0){
			lawProcessContext = list.get(0);
			//判断进度状态
			if(lawProcessContext.getStatus().equals("2")){ 
				lawProcessTypes = lawProcessContext.getLawProcessId().getProcessType().getLawProcessTypes();
			}else{
				request.setAttribute("msg", "任务不存在或未完成");
				return MOBILE_ERROR;
			}
		}
		// TODO 超过截止日期走查看方法
		if(null!= lawProcessTypes){
			request.setAttribute("lawProcessTypesList",lawProcessTypes);
		}
		if(null!= lawProcessContext){
			request.setAttribute("lawProcessContext",lawProcessContext);
		}
		
		return SUCCESS;
	}
}
