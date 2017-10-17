package com.lawer.web.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lawer.context.Constant;
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

@Namespace("/dossier")
@ParentPackage("p2p-default")
public class DossierAction extends BaseAction {

	private static Logger logger = Logger.getLogger(DossierAction.class);

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
    private LawCommentService lawCommentService;

	

	/**
     * 案件页面首页
     * @return
     * @throws Exception
     */
    @Action(value = "/showDossier", results = { @Result(name = "showDossier", type = "ftl", location = "/dossier_M.html") })
    public String showDossier() throws Exception {
    	Map<String, Object> extraparam = new HashMap<String, Object>();
		int page = paramInt("page");
		
		String caseNumber = paramString("caseNumber");
		String caseName = paramString("caseName");
		
		SearchParam param = SearchParam.getInstance();
		param.addParam("status","4");//终审通过
		
		if(!StringUtils.isBlank(caseName)){
			param.addParam("caseName",Operator.LIKE , caseName);
		}
		if(!StringUtils.isBlank(caseNumber)){
			param.addParam("caseNumber",Operator.LIKE , caseNumber);
		}
		
		param.addPage(page+1, Page.ROWS);
		extraparam.put("page", page);
		param.addOrder(OrderType.DESC, "addtime");
		
		PageDataList<LawCase> list = lawCaseService.findAllByParam(param);
		
		if(list!=null&&list.getList()!=null){
			request.setAttribute("list",list.getList());
			request.setAttribute("pages",list.getPage());
		}
		
		
        return "showDossier";
    }

    /**
     * 卷宗评阅
     * @return
     * @throws Exception
     */
    @Action(value = "/lookDossier", results = { @Result(name = "lookDossier", type = "ftl", location = "/dossier_M_py.html") })
    public String lookDossier() throws Exception {
        Integer caseId = paramInt("caseId");
        LawCase lawCase = findLawCase(caseId);
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
		
        return "lookDossier";
    }

    /**
     * 查询LawCase
     * @param caseId
     * @return
     */
    public LawCase findLawCase(Integer caseId){
        LawCase lawCase = new LawCase();
        SearchParam param = SearchParam.getInstance();
        param.addParam("id", caseId);
        PageDataList<LawCase> lawCaseList = lawCaseService.findAllByParam(param);
        if(null != lawCaseList && null != lawCaseList.getList() && lawCaseList.getList().size()>0){
             lawCase = lawCaseList.getList().get(0);
             return lawCase;
        }else{
            return null;
        }
    }

    /**
     * 案件进度管理-进度详情
     * @return
     * @throws Exception
     */
    @Action(value = "/processDetail")
    public void processDetail() throws Exception {
        Integer id = paramInt("id");
        LawProcess  lp= lawProcessService.findLpById(id);
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        String code = Constant.STATUS_YES;
        String msg = "success";
        if(null != lp){
            if(null != lp.getLawProcessContexts() && lp.getLawProcessContexts().size()>0){
                List<LawProcessContext> list = lp.getLawProcessContexts();
                for (LawProcessContext lpc : list) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("addtime",lpc.getAddtime());
                    jsonObject.put("context",lpc.getContext());
                    array.add(jsonObject);
                }
            }else{
                code = Constant.STATUS_NO;
                msg = "该工作项没有工作内容详情";
            }
        }else{
            code = Constant.STATUS_NO;
            msg = "error";
        }
        json.put("code",code);
        json.put("msg",msg);
        json.put("list",array);
        printJson(json.toString());
    }

    /**
     * 提交评论
     * @throws Exception
     */
    @Action(value = "/saveComment")
    public void saveComment() throws Exception {
        JSONObject jsonObject = new JSONObject();
        String code = Constant.STATUS_YES;
        String msg = "success";
        String comment = paramString("comment");
        Integer caseId = paramInt("caseId");
        LawCase lawCase = findLawCase(caseId);
        LawComment lawComment = new LawComment();
        if(!"".equals(comment)){
            if(null != lawCase){
                lawComment.setRemark(comment);
                lawComment.setUserId((User) session.get(Constant.SESSION_USER));
                lawComment.setPid(0);
                lawComment.setStatus("1");
                lawComment.setLawCaseId(lawCase);
                lawComment.setAddtime(new Date());
                lawCommentService.saveLawComment(lawComment);
            }else{
                code = Constant.STATUS_NO;
                msg = "无法获取到案件信息，无法保存评论";
            }
        }else{
            code = Constant.STATUS_NO;
            msg = "提交评论为空，不可以保存";
        }
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        jsonObject.put("lawc",lawComment);
        printJson(jsonObject.toString());
    }
}
