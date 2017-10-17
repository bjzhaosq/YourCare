package com.lawer.web.action.mobile;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import com.lawer.web.action.BaseAction;

@Namespace("/mobile/menu")
@ParentPackage("p2p-default")
public class MobileMenuAction extends BaseAction {
	private static Logger logger = Logger.getLogger(MobileMenuAction.class);

	
	/**
	 * 菜单页
	 * @return
	 * @throws Exception
	 */
	@Action(value = "/menuList", results = { @Result(name = "success", type = "ftl", location = "/mobile/mobile_menu.html") })
	public String menuList() throws Exception {
		return SUCCESS;
	}
	
	
	
}
