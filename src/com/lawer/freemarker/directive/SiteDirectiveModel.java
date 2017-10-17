package com.lawer.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.lawer.dao.SiteDao;
import com.lawer.domain.Site;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class SiteDirectiveModel implements TemplateDirectiveModel {
	
	private static Logger logger = Logger.getLogger(SiteDirectiveModel.class);
	
    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String CLASS="class";
    private static final String PID="pid";
    private static final String DEFUALT="default";
    
	private SiteDao dao;
	
	public SiteDirectiveModel(SiteDao dao) {
		super();
		this.dao = dao;
	}

	public SiteDirectiveModel() {
		super();
	}

	@Override
	public void execute(Environment env, Map map, TemplateModel[] loopVars, 
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Iterator it = map.entrySet().iterator();
		String name="",id="",clazz="",value="",pid="",defaultValue="";
        while (it.hasNext()) {
            Map.Entry entry = (Entry) it.next();
            String paramName = entry.getKey().toString();
            TemplateModel paramValue = (TemplateModel) entry.getValue();
            logger.debug("name:"+paramName);
            logger.debug("r:"+paramValue);
            if (paramName.equals(NAME)) {
            	name=paramValue.toString();
            }else if (paramName.equals(ID)) {
                id=paramValue.toString();
            }else if (paramName.equals(CLASS)) {
            	clazz=paramValue.toString();
            }else if (paramName.equals(PID)) {
            	pid = paramValue.toString();
            }else if(paramName.equals(DEFUALT)) {
            	defaultValue=paramValue.toString();
            }
        }
        String result=html(name,id,defaultValue,pid);
        Writer out = env.getOut();
        out.write(result);
	}
	
	/**
	 * 
	 * @return  返回拼装出来的html字符串
	 */
	private String html(String name,String id,String value,String pid){
		List<Site> list=new ArrayList<Site>();
		
		list=dao.getSubSiteList(0);
		StringBuffer sb=new StringBuffer();
		sb.append("<select name=\"").append(name);
		sb.append("\" id=\"").append(id);
		sb.append("\">");
		
		//
		
		if("0".equals(pid)){//如果pid为0显示变成  =>根栏目
			sb.append("<option value=\"0\">根栏目</option>");
		}else{
			sb.append("<option value=\"0\">请选择</option>");
		}
		for(Site s:list){
			sb.append(option(s.getSiteId(),s.getName(),value));
			if(!"0".equals(pid)){//如果pid为0只选取一级菜单
				List<Site> subList=dao.getSubSiteList(s.getSiteId());
				for(Site ss:subList){
					sb.append(option(ss.getSiteId(),"-"+ss.getName(),value));
				}
			}			
		}
		sb.append("</select>");
		return sb.toString();
	}
	
	private String option(long id,String name,String value){
		StringBuffer sb=new StringBuffer();
		sb.append("<option value=\"").append(id).append("\"");
		if(value.equals(id+"")){
			sb.append(" selected=\"selected\" ");
		}
		sb.append(">")
		.append(name)
		.append("</option>");
		return sb.toString();
	}
	
}

