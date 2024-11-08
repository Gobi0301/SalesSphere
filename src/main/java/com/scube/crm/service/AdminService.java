/**
 * 
 */
package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.AccessBo;
import com.scube.crm.bo.AccessLogBO;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.User;


public interface AdminService {

	AdminLoginBO authendicate(AdminLoginBO adminLoginBO) throws MySalesException;

	boolean sendClientMail(OpportunityBO employerRegisterBO) throws MySalesException;

	boolean addLoginStatus(String username) throws MySalesException;

	public boolean createAccessLog(AccessLogBO logBO);

	boolean editLoginStatus(LoginStatusVO loginStatusVO);

	AdminUserBO createuser(AdminUserBO adminBO) throws MySalesException;

	boolean findByEmail(String emailAddress) throws MySalesException;

	boolean findByMobilenoEmployee(AdminUserBO adminBO) throws MySalesException;

	List<AdminUserBO> retrieveUser(AdminUserBO adminuserBO) throws MySalesException;

	List<AdminUserBO> searchEmployeeList(AdminUserBO adminBO) throws MySalesException;

	boolean userStatus(AdminUserBO userBO) throws MySalesException;

	AdminUserBO retrieveusers(long userId) throws MySalesException, Exception;

	AdminUserBO updateuser(AdminUserBO adminBO) throws MySalesException;

	long employeesCount(AdminLoginBO adminLoginBO);

	long campaignCount(AdminLoginBO adminLoginBO);

	long customerCount(AdminLoginBO adminLoginBO);

	long leadsCount(AdminLoginBO adminLoginBO);

	long getCurrentActionCustomers(AdminLoginBO adminLoginBO);

	long productCount(AdminLoginBO adminLoginBO);

	PrivilegesBO savePrivileges(PrivilegesBO privilegesbo) throws MySalesException;

	List<PrivilegesBO> retrivePrivileges(List<PrivilegesBO> privilegesbolist) throws MySalesException;

	PrivilegesBO deletePrivilege(PrivilegesBO bo) throws MySalesException;


	PrivilegesBO editPrivileges(PrivilegesBO bo) throws MySalesException;

	PrivilegesBO updatePrivilege(PrivilegesBO privilegebo) throws MySalesException;

	List<PrivilegesBO> searchPrivilegename(PrivilegesBO bo) throws MySalesException;
	
	List<RoleBO> retrieveRoleprivilege(List<RoleBO> rolebolist);
	
	List<PrivilegesBO> getprivileges(List<PrivilegesBO> rolebolist) throws MySalesException;
	
	List<RoleBO> roleprivileges(List<RoleBO> listrolebo) throws MySalesException;
	
	RoleBO getroleprivilege(RoleBO rolebo) throws MySalesException;
	
	RoleBO removeRole(RoleBO rolebo) throws MySalesException;

	List<AdminUserBO> retrieveUser(List<AdminUserBO> listUserBo) throws MySalesException;
	
	AdminUserBO retriveUserByName(AdminUserBO bo) throws Exception;

	RoleBO getPrivilegesbyId(RoleBO rolebo) throws MySalesException;

	RoleBO createRolePrivileges(RoleBO borole) throws MySalesException;

	RoleBO deleteRolePrivilege(RoleBO rolebo) throws MySalesException;

	List<PrivilegesBO> listOfPrivileges() throws MySalesException;

	List<AccessBo> listOfAccess() throws MySalesException;

	PrivilegesBO privilegeAccess(PrivilegesBO privlegeBO) throws MySalesException;

	List<PrivilegesBO> listOfPrvielegeAccess() throws MySalesException;

	List<PrivilegesBO> editPriviegeAccess(PrivilegesBO privilegeBO) throws MySalesException;

	PrivilegesBO updatePrivilegeAccess(PrivilegesBO privilegeBO) throws MySalesException;

	PrivilegesBO deletePrivilegeAccess(PrivilegesBO privilegeBO) throws MySalesException;

	List<AdminUserBO> searchUserName(AdminUserBO adminBO) throws MySalesException;

	List<RoleBO> searchRoleByName(RoleBO roleSearchBOObj) throws MySalesException;

	List<PrivilegesBO> searchByPrivilegeName(PrivilegesBO privilegeSearchBO) throws MySalesException;

	long getListOfadminUser(AdminUserBO adminUserBO) throws MySalesException;

	List<AdminUserBO> retrieveUserByPagination(AdminUserBO listUserBo) throws MySalesException;

	AdminLoginBO getEmployeeObjectByName(AdminLoginBO userBO) throws MySalesException;

	long countOfUsers(long companyId) throws MySalesException;

	List<AdminUserBO> listOfUsersByPagination(AdminUserBO adminUserBO) throws MySalesException;

	long countOfUsersBySearch(AdminUserBO adminUserBO) throws MySalesException;

	List<AdminUserBO> listOfUsersBySearchPagination(AdminUserBO adminUserBO) throws MySalesException;

	long contactCounts(AdminLoginBO adminLoginBO);

	long accountCount(AdminLoginBO adminLoginBO);
	
	long opportunityCount(AdminLoginBO adminLoginBO) throws MySalesException;

	boolean checkEmailAddress(String emailAddress)throws MySalesException;

	boolean checkMobileNo(String mobileNo)throws MySalesException;

	boolean deleteProfile(long deletedId) throws MySalesException;

	List<AdminUserBO> findEmployeesBySkills(long skillsIds);

	boolean checkPrivilegeName(String privilegename);

	List<AdminUserBO> retrieveEmployeeByPagination(AdminUserBO listUserBo)throws MySalesException;

	List<InventoryBO> listOfProduct(long companyId);

	long companyCount(AdminLoginBO adminLoginBO);
	
	long countOfPrivilege(PrivilegesBO privilegesBO);
	
	List<PrivilegesBO>  listOfPrivilegeByPagination(PrivilegesBO privilegesbolist);

	/*
	 * long roleCount(RoleBO rolebo)throws MySalesException;
	 * 
	 * List<RoleBO> getRoleList(RoleBO roleBo)throws MySalesException;
	 */

	   List<PrivilegesBO> listOfUsersByPagination(PrivilegesBO privilegesBO);

		long countOfprivilege(long companyId);

		long countSearchPrivilege(PrivilegesBO privilegeBO);
		
		long countOfPrivilegesBySearch(PrivilegesBO privbo);

		long salesCount(AdminLoginBO adminLoginBO);
	
}
