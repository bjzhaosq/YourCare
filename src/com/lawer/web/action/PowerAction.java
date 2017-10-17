package com.lawer.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.lawer.domain.Power;
import com.lawer.domain.PowerList;
import com.lawer.domain.UserType;
import com.lawer.model.OrderFilter.OrderType;
import com.lawer.model.Page;
import com.lawer.model.PageDataList;
import com.lawer.model.PowerListModel;
import com.lawer.model.SearchFilter.Operator;
import com.lawer.model.SearchParam;
import com.lawer.service.PowerListService;
import com.lawer.service.PowerService;
import com.lawer.service.UserTypeService;
import com.lawer.util.NumberUtils;
import com.lawer.util.StringUtils;

@Namespace("/power")
@ParentPackage("p2p-default")
public class PowerAction extends BaseAction{
	
	private static Logger logger = Logger.getLogger(PowerAction.class);

	private PowerList powerList;
	
	private List<Integer> userTypeIds = new ArrayList<Integer>();
	
	@Autowired
	private PowerListService powerListService;
	@Autowired
	private UserTypeService userTypeService;
	@Autowired
	private PowerService powerService;
	
	
	public PowerList getPowerList() {
		return powerList;
	}

	public void setPowerList(PowerList powerList) {
		this.powerList = powerList;
	}
	
	public List<Integer> getUserTypeIds() {
		return userTypeIds;
	}

	public void setUserTypeIds(List<Integer> userTypeIds) {
		this.userTypeIds = userTypeIds;
	}
	/**
	 * 跳转权限页面
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 */
	@Action(value="showPower",
			results={@Result(name="showPower", type="ftl",location="/property_M.html")
	})
	public String showPower(){
		Map<String, Object> extraparam = new HashMap<String, Object>();
		int page = paramInt("page");
		
		String name = paramString("name");
		
		SearchParam param = SearchParam.getInstance();
		if(!StringUtils.isBlank(name)){
			param.addParam("name",Operator.LIKE, name);
		}
		param.addParam("status", 1);
		param.addOrder(OrderType.DESC, "addtime");
		
		param.addPage(page+1, Page.ROWS);
		extraparam.put("page", page);
		
		PageDataList<PowerList> list = powerListService.findPowerList(param);
		
		List<PowerListModel> plm_list = new ArrayList<PowerListModel>();
		PowerListModel plm =null;
		List<PowerList> list2 = list.getList();
		if(list2!=null && !list2.isEmpty()){
			SearchParam param2;
			List<Power> power_list;
			for (PowerList powerList : list2) {
				plm = new PowerListModel();
				Integer id = powerList.getId();
				plm.setId(id);
				plm.setName(powerList.getName());
				param2 = SearchParam.getInstance();
				param2.addParam("powerListId", id);
				power_list = powerService.findPower(param2);
				plm.setList(power_list);
				plm_list.add(plm);
			}
			
			request.setAttribute("list",plm_list);
			request.setAttribute("pages",list.getPage());
		}
		return "showPower";
	}
	
	/**
	 * 添加权限页面
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 */
	@Action(value="newlyPower",
			results={@Result(name="newlyPower", type="ftl",location="/property_M_newly.html")
	})
	public String newlyPower(){
		
		return "newlyPower";
	}
	
	/**
	 * 添加权限
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 */
	@Action(value="saveNewlyPower",
			results={@Result(name="saveNewlyPower", type="redirect",location="/power/showPower.html")
	})
	public String saveNewlyPower(){
		powerList.setStatus("1");
		powerList.setAddtime(new Date());
		powerListService.saveNewlyPower(powerList);
		return "saveNewlyPower";
	}
	
	/**
	 * 添加权限角色绑定页面
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 * @throws Exception 
	 */
	@Action(value="updatePowerRole",
			results={@Result(name="updatePowerRole", type="ftl",location="/property_fp.html")
	})
	public String updatePowerRole() throws Exception{
		Integer id = paramInt("id");
		String checked = "";
		PowerList  p= powerListService.findPowerListById(id);
		if(null != p){
			//查询该权限绑定的角色
			List<UserType> list = userTypeService.findUserTypeByPowerListId(id);
			//查询所有的角色
			List<UserType> userTypeList = userTypeService.findUserType();
			if(null != list && list.size() >0){
				
				for(UserType u : list){
					checked = checked + "," + u.getId() ;
				}
				
			}
			request.setAttribute("name", p.getName());
			request.setAttribute("checked", checked);
			request.setAttribute("userTypeList", userTypeList);
			request.setAttribute("id", id);
		}else{
			request.setAttribute("msg", "参数错误");
			return ERROR;
		}
		return "updatePowerRole";
	}
	
	/**
	 * 添加权限
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 */
	@Action(value="savePowerAndRole",
			results={@Result(name="savePowerAndRole", type="redirect",location="/power/showPower.html")
	})
	public String savePowerAndRole(){
		Integer powerListId=NumberUtils.getInt(request.getParameter("powerListId"));
		PowerList plist = powerListService.findPowerListById(powerListId);
		if(null == plist){
			request.setAttribute("msg", "选择的该权限不存在，绑定角色失败");
			return ERROR;
		}
		powerService.updatePowerandRole(userTypeIds, powerListId);
		return "savePowerAndRole";	
	}
	
	/**
	 * 添加权限
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 */
	@Action(value="deletePowerAndRole",
			results={@Result(name="deletePowerAndRole", type="redirect",location="/power/showPower.html")
	})
	public String deletePowerAndRole(){
		Integer powerListId=NumberUtils.getInt(request.getParameter("powerListId"));
		PowerList plist = powerListService.findPowerListById(powerListId);
		if(null == plist){
			request.setAttribute("msg", "选择的该权限不存在，删除角色权限失败");
			return ERROR;
		}
		powerService.deletePowerandRole(powerListId);
		powerListService.delelePowerList(powerListId);
		return "deletePowerAndRole";	
	}
}
