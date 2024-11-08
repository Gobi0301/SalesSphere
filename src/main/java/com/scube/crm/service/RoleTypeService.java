package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.RolesVO;

public interface RoleTypeService {

	RoleBO getroletype(RoleBO roleBOObj) throws MySalesException;

	List<RoleBO> viewrole() throws MySalesException  ;

	/* RoleBO geteditrole(String roleId); */

	RoleBO posteditrole(RoleBO roleBOobj) throws MySalesException;

	int deleteroletype(RoleBO rolebo) throws MySalesException;
	
	RoleBO getRoleid(RoleBO borole) throws MySalesException;
	
	List<RoleBO> getroleList(List<RoleBO> roleblist) throws MySalesException;

	RoleBO getrolebyid(RoleBO rolebo) throws MySalesException;

	RolesVO createRolePrivileges(RolesVO rolevo);

	List<RoleBO> getroleLists(List<RoleBO> rolebo) throws MySalesException;

	List<RoleBO> searchRoleName(RoleBO roleBO) throws MySalesException;

	PrivilegesBO getPrivilegebyid(PrivilegesBO privilegesBO) throws MySalesException;

	boolean checkRoleName(String roleName)throws MySalesException;

	RoleBO isRoletypeExists(RoleBO roleBOobj);
	
	long countOfRole(RoleBO roleBO);

	List<RoleBO> listOfRoleByPagination(RoleBO roleBO);

	long countOfRoleBySearch(RoleBO bo);

	 long roleCount(RoleBO rolebo)throws MySalesException;
	   
	   List<RoleBO> getRoleList(RoleBO roleBo)throws MySalesException;



}
