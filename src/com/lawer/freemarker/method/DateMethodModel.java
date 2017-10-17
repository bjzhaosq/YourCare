package com.lawer.freemarker.method;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lawer.dao.LinkageDao;
import com.lawer.domain.Linkage;
import com.lawer.util.NumberUtils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class DateMethodModel implements TemplateMethodModel {
	private static Logger logger = Logger.getLogger(DateMethodModel.class);
	@Autowired
	LinkageDao linkageDao;
	
	public DateMethodModel(){
		super();
	}

	public DateMethodModel(LinkageDao linDao){
		this.linkageDao = linDao;
	}
	public Object exec(List args) throws TemplateModelException {
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null == args) {
			return "";
		}else {
			if (1 == args.size()) { //一个的参数这里处理里  返回秒数
				try {
					if("now".equals(args.get(0))){  //返回当前时间的秒数 
						return System.currentTimeMillis()/1000;
					}else if("nowDate".equals(args.get(0))){
						return new Date();
					}else{ // 返回给定时间的秒数
						Date dateDiff = sdf.parse(args.get(0).toString());
						return dateDiff.getTime()/1000;
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					logger.error(e);
					return "";
				}
			}else if (2 == args.size()) {  //两个的参数 这里处理      计算标的剩余时间  大于0 没过期， 小于0  就过期了
				// arg0= 初审的时间， arg1=有效的天数 （根据validTime 关联 linkage 查询）
				 try {
					Date verifyTime = sdf.parse(args.get(0).toString());
				    int lid = Integer.parseInt(args.get(1).toString());
				    Linkage linkage = linkageDao.getLinkageById(lid);
				    int days = NumberUtils.getInt(linkage.getValue());
				    long currTime = System.currentTimeMillis();
				    return   (verifyTime.getTime() + days*24*60*60*1000 -currTime)/1000; 
					
				} catch (ParseException e) {
					return "";
				}
			}else if (3 == args.size()) { // args[0]:time1,args[1]:time2,args[2]:type;
				try {
					Date time1 = sdf.parse(args.get(0).toString());
					Date time2 = sdf.parse(args.get(1).toString());
					String compareType = args.get(2).toString();
					if("diff".equals(compareType)){
						 return (time1.getTime() - time2.getTime())/1000;
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(e.getMessage());
				}
				return 0;
			}else{ // 其他情况处理
				return "";
			}
		}
	}
	
}

