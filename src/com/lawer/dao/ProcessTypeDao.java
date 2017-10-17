package com.lawer.dao;

import com.lawer.domain.ProcessType;

import java.util.List;

public interface ProcessTypeDao extends BaseDao<ProcessType>{


    public List<ProcessType> findPyList();
}
