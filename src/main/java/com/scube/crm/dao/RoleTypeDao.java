package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.RoleBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.PrivilegesVO;
import com.scube.crm.vo.RolesVO;

public interface RoleTypeDao {

	RolesVO getroletype(RolesVO roleVOobj) throws MySalesException;

	List<RoleBO> viewrole() throws MySalesException;

	/* RoleTypeVO geteditrole(long id); */

	RolesVO posteditrole(RolesVO roleVOobj) throws MySalesException;

	RolesVO deletepost(RolesVO roleVOobj) throws MySalesException;
	
	RolesVO getroleid(RolesVO roletypevo) throws MySalesException;

	List<RolesVO> getRolelist(RolesVO rolevo) throws MySalesException;

	RolesVO createRolePrivileges(RolesVO rolevo) throws MySalesException;

	List<RolesVO> viewrole(List<RolesVO> rolesvo) throws MySalesException;

	RolesVO deleteRolePrivileges(long roleId,long privilegeId) throws MySalesException;

	List<RolesVO> searchRoleName(RolesVO roleVO) throws MySalesException;

	List<Long> getPrivelegeIdList(RolesVO rolevo) throws MySalesException;

	PrivilegesVO getPrivilegelist(PrivilegesVO privilegesVO) throws MySalesException;

	List<Long> getPrivelegeIdList(PrivilegesVO privilegesVO) throws MySalesException;

	boolean checkRoleName(String roleName)throws MySalesException;

	RolesVO isRoletypeExists(RolesVO roleVOobj);
	
	long countOfRole(RolesVO rolesVO);

	List<RolesVO> listOfRoleByPagination(RolesVO rolesVO);

	long countOfRoleBySearch(RolesVO rolesVO);
	
	long roleCount(RolesVO roleVo)throws MySalesException;
	
	List<RolesVO> getRoleList(RolesVO roleVo) throws MySalesException;

}
