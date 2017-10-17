package com.lawer.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.JSON;
import com.lawer.util.NumberUtils;
import com.lawer.util.StringUtils;

/**
 * The persistent class for the credit database table.
 * 封装了获取key 的方法
 */

@Entity
@Table(name="rule")
public class Rule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String name;

	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	private String nid;

	private String remark;

	/**
	 * 特别注意： rule_check格式为如下，：{"a":"b","c":"d"};不能包含如下：{"a":{"b":"c","c":"d"}};如果要做请重新写方法
	 */
	@Column(name="rule_check")
	private String ruleCheck;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRuleCheck() {
		return ruleCheck;
	}

	public void setRuleCheck(String ruleCheck) {
		this.ruleCheck = ruleCheck;
	}

	//v1.8.0.4_u4  TGPROJECT-353  qinjun  2014-07-03   start
	/**
	 * ruleCheck配置为a:'0-3-0.1',b:'3-6-0.95-0.4'格式json数据
	 * 根据'0-3-0.1'前两个数值来判断区间返回第index位置的值
	 * 如传入0-3区间值,2返回0.1传入3-6区间值,3返回0.4
	 * @param checkValue  判断值 
	 * @param index 获取指定位置参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public double getValueByDiyCheckValue(int checkValue,int index){
		Map<String, String>	map =(Map<String, String>) JSON.parse(this.getRuleCheck());
		Set<String> mapSet = map.keySet();
		for (String key : mapSet) {
			String value = map.get(key);
			Double[] values = NumberUtils.getDoubles(value.split("-"));
			if(checkValue >= values[0] && checkValue < values[1]){
				return values[index];
			}
		}
		return 0;
	}
	//v1.8.0.4_u4  TGPROJECT-353  qinjun  2014-07-03   end

	/**
	 * 根据JSON key 提取对象
	 * @param key JSON Key
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getValueByKey(String key) {
		if (key == null) { return null; }
		Map<String, Object>	map = null;
		try {
			map=(Map<String, Object>) JSON.parse(this.getRuleCheck());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(map==null) map=new HashMap<String, Object>();
		Object obj = map.get(key);
		return obj;
	}

	/**
	 * 根据JSON key 提取Double类型值
	 * @param key JSON Key
	 * @return Double
	 */
	public Double getValueDoubleByKey(String key) {
		return NumberUtils.getDouble(StringUtils.isNull(getValueByKey(key)));
	}

	/**
	 * 根据JSON key 提取Integer类型值
	 * @param key JSON Key
	 * @return Integer
	 */
	public int getValueIntByKey(String key) {
		return NumberUtils.getInt(StringUtils.isNull(getValueByKey(key)));
	} 

	/**
	 * 根据JSON key 提取String类型值
	 * @param key JSON Key
	 * @return String
	 */
	public String getValueStrByKey(String key) {
		return StringUtils.isNull(getValueByKey(key));
	} 

	/**
	 * 根据JSON key 提取rule对象
	 * @param key JSON Key
	 * @return Object
	 */
	public Rule getRuleByKey(String key) {
		if (key == null) { return null; }
		String json=getValueStrByKey(key);
		Rule model=new Rule();
		model.setRuleCheck(json);
		return model;
	}
	/**
	 * 根据JSON key 提取List
	 * @param key JSON Key
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List getValueListByKey(String key) { 	
		Object obj = this.getValueByKey(key);
		if(obj==null) return new ArrayList();
		List list =new ArrayList();
		try {
			list = (List) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	} 
}
