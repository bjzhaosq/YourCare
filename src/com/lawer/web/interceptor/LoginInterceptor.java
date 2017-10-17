package com.lawer.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;

public class LoginInterceptor extends BaseInterceptor {

	/**
	 * 禁止url直接登录
	 */
	private static final long serialVersionUID = -7739341521339118442L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		String method = ServletActionContext.getRequest().getMethod();
		if (!"post".equalsIgnoreCase(method)) {
			message("非正常提交方式！", null);
			return "msg";
		}

		return invocation.invoke();
	}

}
