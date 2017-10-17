package com.lawer.web.action;

import com.alibaba.fastjson.JSONObject;
import com.lawer.context.Constant;
import com.lawer.domain.LawCase;
import com.lawer.domain.LawProcess;
import com.lawer.domain.LawerHelpCase;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchParam;
import com.lawer.service.LawCaseService;
import com.lawer.util.DateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.*;

@Namespace("/case")
@ParentPackage("p2p-default")
public class GenerateDocAction extends BaseAction{

    private static Logger logger = Logger.getLogger(GenerateDocAction.class);
    @Autowired
    private LawCaseService lawCaseService;

    @Action(value = "/generateDoc")
    public void generateDoc(){
        Integer caseId = paramInt("caseId");
        LawCase lawCase = findLawCase(caseId);
        JSONObject object = new JSONObject();
        String code = Constant.STATUS_YES;
        String msg = Constant.MSG_SUCCESS;
        String path = "";
        if(null != lawCase){
            try{
                path = generateDocing(lawCase);
            }catch (Exception e){
                code = Constant.STATUS_NO;
                logger.error(e.getMessage());
                msg = "生成失败";
            }
        }else{
            code = Constant.STATUS_NO;
            msg = "生成失败";
        }
        object.put("code",code);
        object.put("msg",msg);
        if(!"".equals(path)){
            object.put("path",path);
        }
        printJson(object.toString());
    }

    public String generateDocing(LawCase lawCase) throws Exception {
        String path = generateDocPath(lawCase);
        //Configuration用于读取ftl文件
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");

        //指定路径的第一种方式(根据某个类的相对路径指定)
        configuration.setClassForTemplateLoading(GenerateDocAction.class,"/com/lawer/template");

        // 输出文档路径及名称
        File outFile = new File(path);
        if(!outFile.exists()){
            outFile.mkdirs();
        }
        String fileName = lawCase.getCaseNumber()+".doc";
        File f = new File(path,fileName);
        //以utf-8的编码读取ftl文件
        Template t =  configuration.getTemplate("test2.ftl","utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "utf-8"),10240);
        t.process(generateData(lawCase), out);
        out.close();
        return truncatUrl(path+File.separator+fileName);
    }

    public String generateDocPath(LawCase lawCase){
        String sep = File.separator;
        String filePath = "";
        String dataPath = ServletActionContext.getServletContext().getRealPath("/data");// \law\src\main\webapp\data
        String contextPath = ServletActionContext.getServletContext().getRealPath("/");// \law\src\main\webapp
        Date d1 = new Date();
        String upfiesDir = null;
        upfiesDir = dataPath + sep + "upfiles" + sep + "generateDoc" + sep;
        String destfilename1 = upfiesDir + DateUtils.dateStr2(d1);
        return destfilename1;
    }

    private String truncatUrl(String path) {
        String truncat = ServletActionContext.getServletContext().getRealPath("/");// \law\src\main\webapp
        String sep = File.separator;
        String url = "";
        url = path.replace(truncat, "");
        url = url.replace(sep, "/");
        return url;
    }

    public Map<String,Object> generateData(LawCase lawCase){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("caseNumber", lawCase.getCaseNumber());
        dataMap.put("caseName", lawCase.getCaseName());
        dataMap.put("lawType", lawCase.getLawTypeId().getStatusOne()+"-"+lawCase.getLawTypeId().getStatusTwo());
        dataMap.put("customerName", lawCase.getCustomerId().getUsername());

        String lawerHelpName = "无";
        String lawyerName = "无";
        if(null != lawCase.getLawerCases() && lawCase.getLawerCases().size()>0){
            lawyerName = lawCase.getLawerCases().get(0).getUserId().getUsername();
            if(null != lawCase.getLawerCases().get(0).getLawerHelpCases() && lawCase.getLawerCases().get(0).getLawerHelpCases().size()>0){
                for(LawerHelpCase l : lawCase.getLawerCases().get(0).getLawerHelpCases()){
                    lawerHelpName = l.getUserId().getUsername() + " ";
                }
            }
        }

        dataMap.put("lawerName", lawyerName);
        dataMap.put("lawerHelpName", lawerHelpName);
//        dataMap.put("context", null != lawCase.getContent()?lawCase.getContent():"无");
        List<Map<String, Object>> list = new ArrayList<>();
        //案件进度list
        List<LawProcess> lps = lawCase.getLawProcesses();
        if(null != lps && lps.size()>0){
            for(LawProcess l : lps){
                HashMap<String, Object> map = new HashMap<>();
                map.put("name",l.getName());
                map.put("time","0");
                map.put("endtime",DateUtils.dateStr4(l.getDeadline()));
                map.put("lawer",l.getUserId().getUsername());
                map.put("status",getStatus(l.getStatus()));
                list.add(map);
            }
        }else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name","无");
            map.put("time","无");
            map.put("endtime","无");
            map.put("lawer","无");
            map.put("status","无");
            list.add(map);
        }
        dataMap.put("list",list);
        return dataMap;
    }

    public String getStatus(String status){
        String s = "";
        if("0".equals(status)){
            s = "未完成";
        }else if("1".equals(status)){
            s = "未开始";
        }else if("2".equals(status)){
            s = "进行中";
        }else if("3".equals(status)){
            s = "已提交";
        }
        return s;
    }

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

}
