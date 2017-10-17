package com.lawer.web.action.wechat;

import com.alibaba.fastjson.JSONObject;
import com.lawer.domain.WechatPhone;
import com.lawer.service.WechatPhoneService;
import com.lawer.util.HTTPSend;
import com.lawer.web.action.BaseAction;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Namespace("/wechat")
@ParentPackage("p2p-default")
public class WechatPhoneAction111 extends BaseAction {
	private static Logger logger = Logger.getLogger(WechatPhoneAction111.class);

	private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153,186".split(",");
	private static String getTel() {
		int index=getNum(0,telFirst.length-1);
		String first=telFirst[index];
		String second=String.valueOf(getNum(1,888)+10000).substring(1);
		String third=String.valueOf(getNum(1,9100)+10000).substring(1);
		return first+second+third;
	}


	public static int getNum(int start,int end) {
		return (int)(Math.random()*(end-start+1)+start);
	}

	@Autowired
	private WechatPhoneService wechatPhoneService;

	@Action(value = "/tjmz")
	public void tjmz(){
		JSONObject json = new JSONObject();
		List<WechatPhone> wechatPhones = new ArrayList<>();
		List<Map<String, String>> name = name();
		for(Map<String, String> m :name){
			WechatPhone w = new WechatPhone();
			w.setName(m.get("name"));
			w.setTelephone(m.get("p"));
			w.setAddtime(new Date());
			w.setStatus("1");
			wechatPhones.add(w);
		}
		wechatPhoneService.saveWechatPhone(wechatPhones);
		setCode(json,"");
		printJson(json.toString());

	}

	public List<Map<String,String>> name(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String url = "http://www.qmsjmfb.com/";
		String content = HTTPSend.sendGet(url, "");
		Document parse = Jsoup.parse(content);
		Elements li = parse.getElementsByTag("li");
		System.out.println();
		for(Element e : li){
			HashMap<String, String> m = new HashMap<>();
			m.put("name",e.text());
			m.put("p",getTel());
			list.add(m);
		}
		return list;
	}

	public static void main(String[] args) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String url = "http://www.qmsjmfb.com/";
		String content = HTTPSend.sendGet(url, "");
		Document parse = Jsoup.parse(content);
		Elements li = parse.getElementsByTag("li");
		System.out.println();
		for(Element e : li){
			HashMap<String, String> m = new HashMap<>();
			m.put("name",e.text());
			m.put("p",getTel());
			list.add(m);
		}
		System.out.println(list);
	}



}
