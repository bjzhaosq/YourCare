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
import com.lawer.model.Page;
import com.lawer.model.PageDataList;
import com.lawer.model.PowerListModel;
import com.lawer.model.SearchParam;
import com.lawer.model.OrderFilter.OrderType;
import com.lawer.model.SearchFilter.Operator;
import com.lawer.service.PowerListService;
import com.lawer.service.PowerService;
import com.lawer.service.UserTypeService;
import com.lawer.util.NumberUtils;
import com.lawer.util.StringUtils;

@Namespace("/role")
@ParentPackage("p2p-default")
public class RoleAction extends BaseAction{
	
	private static Logger logger = Logger.getLogger(RoleAction.class);

	private UserType userType;
	
	private List<Integer> powerListIds = new ArrayList<Integer>();
	
	@Autowired
	private PowerListService powerListService;
	@Autowired
	private UserTypeService userTypeService;
	@Autowired
	private PowerService powerService;
	
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public List<Integer> getPowerListIds() {
		return powerListIds;
	}

	public void setPowerListIds(List<Integer> powerListIds) {
		this.powerListIds = powerListIds;
	}

	/**
	 * 跳转权限页面
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 */
	@Action(value="showRole",
			results={@Result(name="showRole", type="ftl",location="/roleType_M.html")
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
		
		PageDataList<UserType> list = userTypeService.findUserTypeByParam(param);
		List<PowerListModel> plm_list = new ArrayList<PowerListModel>();
		PowerListModel plm =null;
		List<UserType> list2 = list.getList();
		if(list2!=null && !list2.isEmpty()){
			SearchParam param2;
			List<Power> power_list;
			for (UserType u : list2) {
				plm = new PowerListModel();
				Integer id = u.getId();
				plm.setId(id);
				plm.setName(u.getName());
				param2 = SearchParam.getInstance();
				param2.addParam("userTypeId", id);
				power_list = powerService.findPower(param2);
				plm.setList(power_list);
				plm_list.add(plm);
				
			}
			request.setAttribute("list",plm_list);
			request.setAttribute("pages",list.getPage());
		}
		return "showRole";
	}
	
	/**
	 * 添加权限页面
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 */
	@Action(value="newlyRole",
			results={@Result(name="newlyPower", type="ftl",location="/roleType_M_newly.html")
	})
	public String newlyPower(){
		
		return "newlyPower";
	}
	
	/**
	 * 添加权限
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 */
	@Action(value="saveNewlyRole",
			results={@Result(name="saveNewlyRole", type="redirect",location="/role/showRole.html")
	})
	public String saveNewlyPower(){
		userType.setStatus("1");
		userType.setAddtime(new Date());
		userTypeService.saveNewlyPower(userType);
		return "saveNewlyRole";
	}
	
	/**
	 * 添加权限角色绑定页面
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 * @throws Exception 
	 */
	@Action(value="updateRolePower",
			results={@Result(name="updateRolePower", type="ftl",location="/roleType_M_fp.html")
	})
	public String updateRolePower() throws Exception{
		Integer id = paramInt("id");
		String checked = "";
		UserType u = userTypeService.findUserTypeById(id);
		if(null != u){
			//查询该角色绑定的权限
			List<PowerList>  list= powerListService.findPowerListByUserTypeId(id);
			//查询所有的权限
			List<PowerList> poList = powerListService.findAllPowerList();
			if(null != list && list.size() >0){
				
				for(PowerList p : list){
					checked = checked + "," + p.getId() ;
				}
				
			}
			request.setAttribute("name", u.getName());
			request.setAttribute("checked", checked);
			request.setAttribute("poList", poList);
			request.setAttribute("id", id);
		}else{
			request.setAttribute("msg", "参数错误");
			return ERROR;
		}
		return "updateRolePower";
	}
	
	/**
	 * 添加角色绑定权限
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 */
	@Action(value="saveRoleAndPower",
			results={@Result(name="saveRoleAndPower", type="redirect",location="/role/showRole.html")
	})
	public String saveRoleAndPower(){
		Integer userTypeId=NumberUtils.getInt(request.getParameter("userTypeId"));
		//查询角色信息
		UserType u = userTypeService.findUserTypeById(userTypeId);
		if(null == u){
			request.setAttribute("msg", "选择的该角色不存在，绑定权限失败");
			return ERROR;
		}
		powerService.updateRoleAndPower(powerListIds, userTypeId);
		return "saveRoleAndPower";	
	}
	
	/**
	 * 删除角色
	 * @Date 2017年7月19日 下午2:07:33
	 * @return
	 */
	@Action(value="deleteRoleAndPower",
			results={@Result(name="deleteRoleAndPower", type="redirect",location="/role/showRole.html")
	})
	public String deleteRoleAndPower(){
		Integer userTypeId=NumberUtils.getInt(request.getParameter("userTypeId"));
		UserType u = userTypeService.findUserTypeById(userTypeId);
		if(null == u){
			request.setAttribute("msg", "删除角色失败");
			return ERROR;
		}
		//删除绑定权限
		powerService.deleteRoleandPower(userTypeId);
		//删除角色
		userTypeService.deleteUserTypeById(userTypeId);
		return "deleteRoleAndPower";	
	}
}
