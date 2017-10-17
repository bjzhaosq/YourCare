package com.lawer.model;

import java.util.List;

public class SearchFilter {
	public enum Operator {
		EQ,NOTEQ, LIKE, GT, LT, GTE, LTE,AND,OR,PROPERTY_EQ,PROPERTY_NOTEQ,PROPERTY_GT,PROPERTY_LT,DATE_LTE_TIMES,DATE_GT_TIMES,IS,ISNOT
	}

	public Object fieldName;
	public Object value;
	public Operator operator;
	/**
	 * ����
	 */
	public long times;
	public List<SearchFilter> listFilters; 

	public SearchFilter(Object fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}
	
	public SearchFilter(Object fieldName, Operator operator, Object value,long times) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
		this.times = times;
	}

	public List<SearchFilter> getListFilters() {
		return listFilters;
	}

	public void setListFilters(List<SearchFilter> listFilters) {
		this.listFilters = listFilters;
	}
}
