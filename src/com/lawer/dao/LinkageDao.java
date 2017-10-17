package com.lawer.dao;

import java.util.List;

import com.lawer.domain.Linkage;

public interface LinkageDao extends BaseDao<Linkage> {
	/**
	 * 
	 * @param typeid
	 * @param type
	 * @return
	 */
	public List<Linkage> getLinkageByTypeid(int typeid,String type);

	public List<Linkage> getLinkageByNid(String nid,String type);
	
	public Linkage getLinkageByValue(String nid,String value);

	public Linkage getLinkageById(int id);
	
}
