package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.AccessVo;
import com.scube.crm.vo.EmailAccess;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.PrivilegesVO;
import com.scube.crm.vo.RolePrivilegesVO;
import com.scube.crm.vo.RolesVO;
import com.scube.crm.vo.User;

/**
 * @author Administrator
 * @param <T>
 * 
 */
public interface AdminDAO {

	User authendicate(String string, String emailAddress) throws MySalesException;

	User authendicate(String emailAddress) throws MySalesException;
	
	LoginStatusVO editLoginStatus(LoginStatusVO loginStatusVO);
	
	long addLoginStatus(LoginStatusVO loginStatus);
	
	 public boolean createAccessLog(AccessLogVO logVO);

	 long createuser(User adminVO) throws MySalesException;
	 
	 User retrieveusers(User userVO) throws MySalesException;
	 
	 List<User> retrieveUser(User user)throws MySalesException;
	 
	 User userStatus(User userVO)throws MySalesException;
	 
	 
	 
	 User getuserId(AdminUserBO adminBO) throws MySalesException;
	 
	 User updateuser(User loginVO) throws MySalesException;
	 
	 User findByEmail(String string, String emailAddress);
	 
	 EmailAccess saveEmailList(List<EmailAccess> accessVOList);
	 
	 long campaignCount(AdminLoginBO adminLoginBO);
	 
	 long leadsCount(AdminLoginBO adminLoginBO);
	 
	 long customerCount(AdminLoginBO adminLoginBO);
	 
	 long employeesCount(AdminLoginBO adminLoginBO);
	 
	 List<User> searchEmployeeList(AdminUserBO adminBO);
	 
	 boolean findByMobilenoEmployee(User userVO);  

	List<String> getImageName();

	boolean findEmployerEmail(String emailAddress);

	boolean mobileNoVerification(String mobileNo);

	long getCurrentActionCustomers(AdminLoginBO adminLoginBO);

	long productCount(AdminLoginBO adminLoginBO);

	PrivilegesVO savePrivileges(PrivilegesVO privilegesvo) throws MySalesException;

	List<PrivilegesBO> retrivePrivileges(List<PrivilegesBO> privilegesbolist) throws MySalesException;

	int deletePrivilege(PrivilegesVO vo) throws MySalesException;


	PrivilegesVO editPrivileges(PrivilegesVO vo) throws MySalesException;

	PrivilegesVO updatePrivileges(PrivilegesVO vo) throws MySalesException;

	List<PrivilegesVO> searchPrivileges(PrivilegesVO vo) throws MySalesException;
	
	List<RoleBO> retriveRolePrivilege(List<RoleBO> listrolebo);

	PrivilegesVO getprivilege(PrivilegesVO vo) throws MySalesException;
	
	RolePrivilegesVO assignRolePrivilege(RolePrivilegesVO vo) throws MySalesException;


	RolePrivilegesVO getroleprivilege(RolePrivilegesVO roleprivilegevo) throws MySalesException;
	
	RolePrivilegesVO removeRole(RolePrivilegesVO assignvo) throws MySalesException;

	List<AdminUserBO> retrieveUser(List<AdminUserBO> bOList);  

	User retriveUserByname(User user) throws Exception;

	List<User> retriveuserById(User user);

	PrivilegesVO getprivilegeById(PrivilegesVO privilegesvo) throws MySalesException;

	List<PrivilegesVO> listOfPrivileges(List<PrivilegesVO> privilegeListVO) throws MySalesException;

	List<AccessVo> listOfAccess() throws MySalesException;

	PrivilegesVO privilegeAccess(PrivilegesVO privilegeVo) throws MySalesException;

	List<PrivilegesVO> listOfPrivilegeAccess() throws MySalesException;

	List<PrivilegesVO> editPrivilegeAccess(PrivilegesVO privilegeVO) throws MySalesException;

	PrivilegesVO updatePrivilegeAccess(PrivilegesVO privilegeVO) throws MySalesException;

	PrivilegesVO deletePrivilegeAccess(long privilegeId,long accessId) throws MySalesException;

	List<Long> geAccessIdListIdList(PrivilegesVO privilegeVO) throws MySalesException;

	List<RolesVO> getUserRoles(long userId) throws MySalesException;

	List<Long> getroleIdList(User userVO);

    List<PrivilegesVO> getPrivilegeId(PrivilegesVO privilegeVO) throws MySalesException;

	List<User> searchUserName(User userVO) throws MySalesException;

	List<RolesVO> searchRoleName(RolesVO roleVOObj) throws MySalesException;

	List<PrivilegesVO> searchByPrivilegeName(PrivilegesVO privilegeVO) throws MySalesException;

	long getListOfadminUser(User user) throws MySalesException;

	List<User> retrieveUserByPagination(AdminUserBO listUserBo) throws MySalesException;

	User getEmployeeObjectByName(User userVO) throws MySalesException;

	long countOfUsers(User userVO, long companyId) throws MySalesException;

	List<User> listOfUsersByPagination(User userVO) throws MySalesException;

	long countOfUsersBySearch(User userVO) throws MySalesException;

	List<User> listOfUsersBySearchPagination(User userVO) throws MySalesException;

	long contactCounts(AdminLoginBO adminLoginBO);

	long accountCount(AdminLoginBO adminLoginBO);

	long opportunityCount(AdminLoginBO adminLoginBO) throws MySalesException;

	boolean checkEmailAddress(String emailAddress)throws MySalesException;

	boolean checkMobileNo(String mobileNo)throws MySalesException;

	boolean deleteEmployee(long deletedId) throws MySalesException;

	User getEmployee(long userId);

	List<Long> getSkillIdList(User userVO);


	List<User> findEmployeesBySkills(long skillsIds);

	boolean checkPrivilegeName(String privilegename);

	List<User> retrieveEmployeeByPagination(AdminUserBO adminuserBO)throws MySalesException;

	List<InventoryVO> listOfProduct(long companyId);
	
	long countOfPrivilege(PrivilegesVO privilegesVO);
	
	List<PrivilegesVO> listOfPrivilegeByPagination(PrivilegesVO privilegesVO);

long roleCount(RolesVO roleVo)throws MySalesException;
	
	List<RolesVO> getRoleList(RolesVO roleVo) throws MySalesException;

	long countOfprivilegesVO(PrivilegesVO privilegesVO);

	long countOfPrivilegeBySearch(PrivilegesVO privilegesVO);
	
List<PrivilegesVO> listOfPrivilegeAccess(PrivilegesVO PrivilegesVO) throws MySalesException;
	
long companyCount(AdminLoginBO adminLoginBO);

long countOfPrivilegesBySearch(PrivilegesVO privilegesVO);

User retrieveusersemail(User loginVOemail);

long salesCount(AdminLoginBO adminLoginBO);


}
