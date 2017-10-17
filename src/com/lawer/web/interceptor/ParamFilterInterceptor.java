package com.lawer.web.interceptor;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.blogspot.radialmind.html.HTMLParser;
import com.blogspot.radialmind.xss.XSSFilter;
import com.opensymphony.xwork2.ActionInvocation;
import com.lawer.util.StringUtils;

public class ParamFilterInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = -6325242223825713099L;
	private static final Logger logger = Logger.getLogger(ParamFilterInterceptor.class);
	private static final XSSFilter xssFilter=new XSSFilter();
	public void init() {
		super.init();
	}

	/**
	 * 任何请求的参数
	 * 都要经过安全级别的过滤
	 */
	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		Enumeration names=request.getParameterNames();
		boolean hasDeniedText=false;//默认不包含有
		boolean hasDeniedXss=false;//默认不包含有
		String illegalParam="";
		while(names.hasMoreElements()){
			String name=(String)names.nextElement();
			String[] values=request.getParameterValues(name);
			for(int i=0;i<values.length;i++){
				illegalParam = values[i];
				//先对请求进行转码，然后转成大写
				String needCheckParams = StringUtils.UrlDecoder(values[i]).toUpperCase();
				//富文本特殊处理
				if(StringUtils.isNull(name).equals("content")){
					hasDeniedText=isAttack(needCheckParams);
					hasDeniedXss=fliterXSS(StringUtils.isNull(values[i]));
				}else{
					hasDeniedText=isAttack(needCheckParams);
					hasDeniedXss=fliterXSS(StringUtils.isNull(values[i]));
				}
				if(hasDeniedText) break;//文本校验
				if(hasDeniedXss) break;//文本校验
			}
			if(hasDeniedText) break;
			if(hasDeniedXss) break;
		}
		if(hasDeniedText){//文本过滤
			logger.info("存在非法字符:" + illegalParam);
			return "error";
		}
		if(hasDeniedXss){//xss过滤
			logger.info("存在非法字符:" + illegalParam);
			return "error";
		}
		String result = ai.invoke();
		return result;
	}
	/**
	 * 检查是否已经存在xss的字符
	 * @param text
	 * @return 存在返回true
	 */
	public boolean fliterXSS(String text){
		StringBuffer sb=new StringBuffer("<html>").append(text).append("</html>");
		StringReader reader = new StringReader(sb.toString());
	    StringWriter writer = new StringWriter();
	    
	    StringReader noReader = new StringReader(sb.toString());
	    StringWriter noWriter = new StringWriter();
	    try {
	        HTMLParser.process( reader, writer,xssFilter , true );
	        HTMLParser.process( noReader, noWriter,null , true );
	    } catch (Exception e) {
	    	logger.info("xssFilter,error," + text);
	    }
	    
	    String filterStr = writer.toString();
	    String noFilterStr = noWriter.toString();
	    
	    return !filterStr.equals(noFilterStr);
	}
	
	/**
	 * @author Administrator,返回true就是包含非法字符，返回false就是不包含非法字符
	 * 系统内容过滤规则
	 * 1、包含  『 and 1 特殊字符 』， 特殊字符指>,<,=, in , like 字符
	 * 2、『 /特殊字符/ 』，特殊字符指 *字符
	 * 3、『<特殊字符 script 』特殊字符指空字符
	 * 4、『 EXEC 』
	 * 5、『 UNION SELECT』
	 * 5、『 UPDATE SET』
	 * 5、『 INSERT INTO VALUES』
	 * 5、『 SELECT或DELETE FROM』
	 * 5、『CREATE或ALTER或DROP或TRUNCATE TABLE或DATABASE』
	 */
	public static boolean isAttack(String input){
		String getfilter="\\b(AND|OR)\\b.+?(>|<|=|\\bIN\\b|\\bLIKE\\b)|\\/\\*.+?\\*\\/|<\\s*SCRIPT\\b|\\bEXEC\\b|UNION.+?SELECT|UPDATE.+?SET|INSERT\\s+INTO.+?VALUES|(SELECT|DELETE).+?FROM|(CREATE|ALTER|DROP|TRUNCATE)\\s+(TABLE|DATABASE)|\\bON.+\\b\\s*=|\\bJAVASCRIPT\\s*:\\b";
		Pattern pat = Pattern.compile(getfilter);
		Matcher mat = pat.matcher(input);
		boolean rs = mat.find(); 
		return rs;
	}	
}

