package com.lawer.service;

import java.util.List;

import com.lawer.domain.Purview;
import com.lawer.domain.UserType;

public interface AuthService {
	public List getPurviewByUserid(long user_id);
	public List getPurviewByPid(int pid);
	public Purview getPurview(long id);
	public  List<Purview> getAllPurview();
	public void addPurview(Purview purview);	
	public void delPurview(long id);	
	public void modifyPurview(Purview purview);
	public void addUserTypePurviews(List purviewid,long user_type_id) ;
	public List<Purview> getAllCheckedPurview(long type_id);
	public List getAllUserType();
	public UserType getUserType(long type_id);
	public void addUserType(UserType userType);
	public void delUserType(long type_id);	
	public void modifyUserType(UserType userType);
}
