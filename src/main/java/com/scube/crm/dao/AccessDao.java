package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.AccessBo;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.AccessVo;

public interface AccessDao {

	public AccessVo accessCreateValue(AccessVo acccessVo)throws MySalesException;

	public List<AccessVo> viewAccessDao()throws MySalesException;

	public AccessVo updateDao(AccessVo accessVo)throws MySalesException;

	public AccessVo deleteAccess(AccessVo accessvo)throws MySalesException;

	public List<AccessVo> searchAccessValue(AccessVo accessvo)throws MySalesException;

	public boolean checkAccessName(String accessName)throws MySalesException;

	public long countOfAccess(AccessVo accessVo);

	public List<AccessVo> listOfAccessByPagination(AccessVo accessVo);

	public long countOfAccessBySearch(AccessVo accessVo);

	public AccessVo getAccessId(AccessVo accessVO);


}
