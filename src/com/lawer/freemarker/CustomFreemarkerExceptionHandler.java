package com.lawer.freemarker;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class CustomFreemarkerExceptionHandler implements
		TemplateExceptionHandler {
	private static final Logger log = Logger
			.getLogger(CustomFreemarkerExceptionHandler.class);

	@Override
	public void handleTemplateException(TemplateException te, Environment env,
			Writer out) throws TemplateException {
		try {
			out.write("<span style=\"red\">[Error: " + te.getMessage() + "]</span>");
			log.warn("[Freemarker Error: " + te.getMessage() + "]");
		} catch (IOException e) {
			log.warn(e.getMessage());
		}

	}

}
