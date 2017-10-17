package com.lawer.web.interceptor;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apache.struts2.dispatcher.ServletRedirectResult;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class RedirectMessageInterceptor extends MethodFilterInterceptor {
	private static final long serialVersionUID = -1847557437429753540L;

	public static String fieldErrorsSessionKey = "RedirectMessageInterceptor_FieldErrors";
	public static String actionErrorsSessionKey = "RedirectMessageInterceptor_ActionErrors";
	public static String actionMessagesSessionKey = "RedirectMessageInterceptor_ActionMessages";

	public String doIntercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if (action instanceof ValidationAware) {
			before(invocation, (ValidationAware) action);
		}

		String result = invocation.invoke();

		if (action instanceof ValidationAware) {
			after(invocation, (ValidationAware) action);
		}
		return result;
	}

	/**
	 * Retrieve the errors and messages from the session and add them to the
	 * action.
	 */
	@SuppressWarnings("unchecked")
	protected void before(ActionInvocation invocation,
			ValidationAware validationAware) throws Exception {
		Map session = invocation.getInvocationContext().getSession();

		Collection<String> actionErrors = (Collection) session
				.get(actionErrorsSessionKey);
		Collection<String> actionMessages = (Collection) session
				.get(actionMessagesSessionKey);
		Map<String, String> fieldErrors = (Map) session
				.get(fieldErrorsSessionKey);

		session.remove(actionErrorsSessionKey);
		session.remove(actionMessagesSessionKey);
		session.remove(fieldErrorsSessionKey);

		if (actionErrors != null && actionErrors.size() > 0) {
			for (String error : actionErrors) {
				validationAware.addActionError(error);
			}
		}

		if (actionMessages != null && actionMessages.size() > 0) {
			for (String message : actionMessages) {
				validationAware.addActionMessage(message);
			}
		}

		if (fieldErrors != null && fieldErrors.size() > 0) {
			for (Map.Entry<String, String> entry : fieldErrors.entrySet()) {
				validationAware.addFieldError(entry.getKey(), entry.getValue());
			}
		}

	}

	/**
	 * If the result is a redirect then store error and messages in the session.
	 */
	@SuppressWarnings("unchecked")
	protected void after(ActionInvocation invocation,
			ValidationAware validationAware) throws Exception {
		Result result = invocation.getResult();

		if (result instanceof ServletRedirectResult
				|| result instanceof ServletActionRedirectResult) {
			ActionContext actionContext = invocation.getInvocationContext();
			HttpServletRequest request = (HttpServletRequest) actionContext
					.get(StrutsStatics.HTTP_REQUEST);

			/*
			 * If the session doesn't already exist then it's too late to create
			 * one as the response has already been committed.
			 * 
			 * This is really only to handle the 'unusual' case of a browser
			 * refresh after the session has expired. In this case the messages
			 * are lost :(
			 */
			HttpSession session = request.getSession(false);
			if (session != null) {
				Collection actionErrors = validationAware.getActionErrors();
				if (actionErrors != null && actionErrors.size() > 0) {
					session.setAttribute(actionErrorsSessionKey, actionErrors);
				}

				Collection actionMessages = validationAware.getActionMessages();
				if (actionMessages != null && actionMessages.size() > 0) {
					session.setAttribute(actionMessagesSessionKey,
							actionMessages);
				}

				Map fieldErrors = validationAware.getFieldErrors();
				if (fieldErrors != null && fieldErrors.size() > 0) {
					session.setAttribute(fieldErrorsSessionKey, fieldErrors);
				}
			}
		}
	}
}

