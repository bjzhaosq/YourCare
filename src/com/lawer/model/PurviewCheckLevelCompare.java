package com.lawer.model;

import java.util.Comparator;

public class PurviewCheckLevelCompare implements Comparator<PurviewCheckSet> {

	@Override
	public int compare(PurviewCheckSet o1, PurviewCheckSet o2) {
		
         int result = (o1.getModel().getPurview().getId()>o2.getModel().getPurview().getId()) ? 1 
        		 : (o1.getModel().getPurview().getId()==o2.getModel().getPurview().getId() ? 0 : -1);
         return result;
	}
	
}
