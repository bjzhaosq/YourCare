package com.lawer.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;

public class UserLogInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = -6325242223825713099L;
	public void init() {
		super.init();
	}

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		String result = ai.invoke();
		return result;
	}
}
