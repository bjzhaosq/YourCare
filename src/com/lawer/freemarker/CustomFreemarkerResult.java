package com.lawer.freemarker;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.apache.struts2.views.freemarker.FreemarkerResult;
import org.apache.struts2.views.util.ResourceUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;
import com.lawer.context.Global;
import com.lawer.util.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class CustomFreemarkerResult extends FreemarkerResult {	
	 private static final long serialVersionUID = -3778230771704661631L;

	    protected ActionInvocation invocation;
	    protected Configuration configuration;
	    protected ObjectWrapper wrapper;
	    protected FreemarkerManager freemarkerManager;

	    protected String location;
	    private static final String PARENT_TEMPLATE_WRITER = CustomFreemarkerResult.class.getName() +  ".parentWriter";

	    public CustomFreemarkerResult() {
	        super();
	    }

	    public CustomFreemarkerResult(String location) {
	        super(location);
	    }
	    
	    @Inject
	    public void setFreemarkerManager(FreemarkerManager mgr) {
	        this.freemarkerManager = mgr;
	    }
	    
	    public void doExecute(String locationArg, ActionInvocation invocation) throws IOException, TemplateException {
	        String htmldir=Global.getValue("ftl_dir");
	        if(!StringUtils.isBlank(htmldir)){
	        	locationArg=htmldir+locationArg;
	        }
	    	this.location = locationArg;
	        this.invocation = invocation;
	        this.configuration = getConfiguration();
	        configuration.setNumberFormat("#.##");
	        this.wrapper = getObjectWrapper();
	       // configuration.setTemplateExceptionHandler(new CustomFreemarkerExceptionHandler());
	        ActionContext ctx = invocation.getInvocationContext();
	        HttpServletRequest req = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);

	        if (!locationArg.startsWith("/")) {
	            String base = ResourceUtil.getResourceBase(req);
	            locationArg = base + "/" + locationArg;
	        }

	        Template template = configuration.getTemplate(locationArg, deduceLocale());
	        TemplateModel model = createModel();

	        if (preTemplateProcess(template, model)) {
	            try {
	                Writer writer = getWriter();
	                if (isWriteIfCompleted() || configuration.getTemplateExceptionHandler() == TemplateExceptionHandler.RETHROW_HANDLER) {
	                    CharArrayWriter parentCharArrayWriter = (CharArrayWriter) req.getAttribute(PARENT_TEMPLATE_WRITER);
	                    boolean isTopTemplate = false;
	                    if (isTopTemplate = (parentCharArrayWriter == null)) {
	                        parentCharArrayWriter = new CharArrayWriter();
	                    
	                        req.setAttribute(PARENT_TEMPLATE_WRITER, parentCharArrayWriter);
	                    }

	                    try {
	                        template.process(model, parentCharArrayWriter);

	                        if (isTopTemplate) {
	                            parentCharArrayWriter.flush();
	                            parentCharArrayWriter.writeTo(writer);
	                        }
	                    } finally {
	                        if (isTopTemplate && parentCharArrayWriter != null) {
	                            req.removeAttribute(PARENT_TEMPLATE_WRITER);
	                            parentCharArrayWriter.close();
	                        }
	                    }
	                } else {
	                    template.process(model, writer);
	                }
	            } finally {
	                postTemplateProcess(template, model);
	            }
	        }
	    }
	    
	    protected Configuration getConfiguration() throws TemplateException {
	    	return freemarkerManager.getConfiguration(ServletActionContext.getServletContext());
	    }
	    
	    protected ObjectWrapper getObjectWrapper() {
	        return configuration.getObjectWrapper();
	    }
	    protected Locale deduceLocale() {
	        if (invocation.getAction() instanceof LocaleProvider) {
	            return ((LocaleProvider) invocation.getAction()).getLocale();
	        } else {
	            return configuration.getLocale();
	        }
	    }
	    protected TemplateModel createModel() throws TemplateModelException {
	        ServletContext servletContext = ServletActionContext.getServletContext();
	        HttpServletRequest request = ServletActionContext.getRequest();
	        HttpServletResponse response = ServletActionContext.getResponse();
	        ValueStack stack = ServletActionContext.getContext().getValueStack();

	        Object action = null;
	        if(invocation!= null ) action = invocation.getAction(); //Added for NullPointException
	        return freemarkerManager.buildTemplateModel(stack, action, servletContext, request, response, wrapper);
	    }
}
