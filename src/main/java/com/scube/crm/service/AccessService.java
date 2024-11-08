package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.AccessBo;
import com.scube.crm.exception.MySalesException;

public interface AccessService {

	public AccessBo createSaveAccess(AccessBo accessBo)throws MySalesException;
	
	public List<AccessBo> viewAccess()throws MySalesException;
	
	public AccessBo updateAccessValue(AccessBo accessBo)throws MySalesException;
	
	public int deleteAccess(AccessBo accessBo) throws MySalesException;
	
	public List<AccessBo> getAccessList(AccessBo accessbo) throws MySalesException;
	
	public boolean checkAccessName(String accessName)throws MySalesException;

	public long countOfAccess(AccessBo accessBO);

	public List<AccessBo> listOfAccessByPagination(AccessBo accessBO);

	public long countOfAccessBySearch(AccessBo bo);

	public AccessBo getAccessId(AccessBo bo);

	

}
