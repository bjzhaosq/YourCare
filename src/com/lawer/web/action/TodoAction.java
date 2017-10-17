package com.lawer.web.action;

import com.lawer.dao.ProcessTypeDao;
import com.lawer.domain.*;
import com.lawer.model.OrderFilter.OrderType;
import com.lawer.model.Page;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchFilter.Operator;
import com.lawer.model.SearchParam;
import com.lawer.service.*;
import com.lawer.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.*;

@Namespace("/todo")
@ParentPackage("p2p-default")
public class TodoAction extends BaseAction {

	private static Logger logger = Logger.getLogger(TodoAction.class);

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
    private LawProcessService lawProcessService;
    @Autowired
    private ProcessTypeDao processTypeDao;
    @Autowired
    private LawProcessContextService lawProcessContextService;

    private LawProcess lawProcess;

    public LawProcess getLawProcess() {
        return lawProcess;
    }

    public void setLawProcess(LawProcess lawProcess) {
        this.lawProcess = lawProcess;
    }

    /**
	 * 案件页面首页
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/showTodo", results = { @Result(name = "showTodo", type = "ftl", location = "/todo_M.html") })
	public String showTodo() throws Exception {
        //查询案件类型
        List<ProcessType> pyList = processTypeDao.findPyList();
        request.setAttribute("pyList",pyList);
        String caseNumber = paramString("caseNumber");
        String caseType = paramString("caseType");
        String status = paramString("status");
        Map<String, Object> extraparam = new HashMap<String, Object>();
		int page = paramInt("page");
		
		SearchParam param = SearchParam.getInstance();
		
		param.addPage(page+1, Page.ROWS);
		extraparam.put("page", page);
		
		if(!StringUtils.isBlank(caseNumber)){
			param.addParam("lawCaseId.caseNumber",Operator.LIKE , caseNumber);
		}
		
		if(!StringUtils.isBlank(caseType)){
			param.addParam("processType.id", caseType);
		}
		
		if(!StringUtils.isBlank(status)){
			param.addParam("status", status);
		}else{
			param.addParam("status",Operator.NOTEQ ,"4");
		}
		
		//获取当前登录用户
		User sessionUser = getSessionUser();
		param.addParam("userId", sessionUser);
		
		param.addOrder(OrderType.DESC, "addtime");
		PageDataList<LawProcess> list = lawProcessService.findPageList(param);
		
		if(list!=null&&list.getList()!=null){
			request.setAttribute("list",list.getList());
			request.setAttribute("pages",list.getPage());
		}
        
        return "showTodo";
	}

	/**
	 * 案件进度管理-待办事项
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/updateTodo", results = { @Result(name = "updateTodo", type = "ftl", location = "/todo_pro_cl.html") })
	public String updateTodo() throws Exception {
        Integer id = paramInt("id");
        lawProcess = lawProcessService.findLpById(id);
        if(null == lawProcess){
            request.setAttribute("msg", "参数错误");
            return ERROR;
        }else{
            if("0".equals(lawProcess.getStatus()) || "3".equals(lawProcess.getStatus())){
                request.setAttribute("msg", "该条数据不可以进行编辑了");
                return ERROR;
            }
        }

        return "updateTodo";
	}

    /**
     * 保存待办任务
     * @return
     * @throws Exception
     */
    @Action(value = "/saveTodo", results = { @Result(name = "saveTodo", type = "redirect", location = "/todo/showTodo.html") })
    public String saveTodo() throws Exception {
        Integer id = paramInt("id");
        String context = paramString("context");
        LawProcess lp = lawProcessService.findLpById(id);
        if(null != lp){
            if(!"2".equals(lp.getStatus())){
                lp.setStatus("2");
                lawProcessService.updateLp(lp);
            }
            LawProcessContext lpc = new LawProcessContext();
            lpc.setContext(context);
            lpc.setStatus("1");
            lpc.setAddtime(new Date());
            lpc.setLawProcessId(lp);
            lawProcessContextService.saveLpc(lpc);
        }else{
            request.setAttribute("msg", "保存失败");
            return ERROR;
        }


        return "saveTodo";
    }

    /**
     * 提交待办任务
     * @return
     */
    @Action(value = "/submitTodo", results = { @Result(name = "submitTodo", type = "redirect", location = "/todo/showTodo.html") })
    public String submitTodo(){
        Integer id = paramInt("id");
        LawProcess lp = lawProcessService.findLpById(id);
        if(null != lp){
            if("2".equals(lp.getStatus())){
            lp.setStatus("3");
            lawProcessService.updateLp(lp);
            }else if("0".equals(lp.getStatus()) || "1".equals(lp.getStatus())){
                request.setAttribute("msg", "此数据不可以进行提交");
                return ERROR;
            }else{
                request.setAttribute("msg", "不可以重复提交");
                return ERROR;
            }
        }else{
            request.setAttribute("msg", "提交失败");
            return ERROR;
        }
        return "submitTodo";
    }

}
