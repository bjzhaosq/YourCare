package com.lawer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.lawer.util.StringUtils;

public class PurviewCheckSet {
	private List<PurviewCheck> purviewList;
	private PurviewCheck model;
	private Set childs;

	public PurviewCheckSet(List purviewList) {
		super();
		this.purviewList = purviewList;
	}

	public PurviewCheckSet(PurviewCheck model, Set childs) {
		super();
		this.model = model;
		this.childs = childs;
	}

	public PurviewCheckSet() {
		super();
	};

	public PurviewCheck getModel() {
		return model;
	}

	public void setModel(PurviewCheck model) {
		this.model = model;
	}

	public Set getChilds() {
		return childs;
	}

	public void setChilds(Set childs) {
		this.childs = childs;
	}

	public boolean hasList(){
		if(purviewList==null||purviewList.size()<1){
			return false;
		}
		return true;
	}
	
	public boolean contains(String url){
		if(!hasList()||StringUtils.isBlank(url)){
			return false;
		}
		for(PurviewCheck p:purviewList){
			if(StringUtils.isNull(p.getPurview().getUrl()).equals(url)){
				return true;
			}
		}
		return false;
	}
	
	public Set toSet(){
		childs=new TreeSet(new PurviewCheckLevelCompare());
		List<PurviewCheck> firstList=new ArrayList<PurviewCheck>();
		List<PurviewCheck> secList=new ArrayList<PurviewCheck>();
		List<PurviewCheck> thirdList=new ArrayList<PurviewCheck>();
		
		if(purviewList==null) return childs;
		
		for(PurviewCheck p:purviewList){
			switch(p.getPurview().getLevel()){
				case 1:firstList.add(p);break;
				case 2:secList.add(p);break;
				case 3:thirdList.add(p);break;
			}
		}
		PurviewCheckSet ps=null;
		for(PurviewCheck p1:firstList){
			Set set1=new TreeSet(new PurviewCheckLevelCompare());
			for(PurviewCheck p2:secList){
				if(p2.getPurview().getPid()==p1.getPurview().getId()){
					Set set2=new TreeSet(new PurviewCheckLevelCompare());
					for(PurviewCheck p3:thirdList){
						if(p3.getPurview().getPid()==p2.getPurview().getId()){
							PurviewCheckSet ps3=new PurviewCheckSet(p3,null);
							set2.add(ps3);
						}
					}
					PurviewCheckSet ps2=new PurviewCheckSet(p2,set2);
					set1.add(ps2);
				}
			}
			ps=new PurviewCheckSet(p1,set1);
			childs.add(ps);
		}
		return childs;
	}
}
