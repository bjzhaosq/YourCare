package com.lawer.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lawer.dao.ExceptionLogDao;
import com.lawer.domain.ExceptionLog;
import com.lawer.model.PageDataList;
import com.lawer.model.SearchFilter.Operator;
import com.lawer.model.SearchParam;
import com.lawer.service.ExceptionLogService;

@Service(value="exceptionLogService")
@Transactional(propagation = Propagation.REQUIRED)
public class ExceptionLogServiceImpl extends BaseServiceImpl implements ExceptionLogService {

	@Autowired
	private ExceptionLogDao exceptionLogDao;
	
	@Override
	public ExceptionLog find(long id) {
		return this.exceptionLogDao.find(id);
	}
	
	
	@Override
	public void save(ExceptionLog item) {
		SearchParam param=SearchParam.getInstance();
		if(StringUtils.isNotBlank(item.getClassName())){
			param.addParam("className", Operator.EQ, item.getClassName());
		}
		if(StringUtils.isNotBlank(item.getMethodName())){
			param.addParam("methodName", Operator.EQ, item.getMethodName());
		}
		if(StringUtils.isNotBlank(item.getMessage())){
			String msgStr = item.getMessage();
			param.addParam("message", Operator.LIKE, msgStr.length()<100?msgStr:msgStr.substring(0, 100));
		}
		int count = this.exceptionLogDao.countByCriteria(param);
		if(count==0){//避免重复记录，撑爆数据库\
	        String message = null;
			if(item!=null){
				message = item.getMessage();
			}
			if(message!=null&&message.length() > 65535){
				item.setMessage(message.substring(0,65535));
			}
			this.exceptionLogDao.save(item);
		}
	}

	@Override
	public PageDataList<ExceptionLog> page(SearchParam param) {
		return this.exceptionLogDao.findPageList(param);
	}

}
