package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.dao.AdminDAO;
import com.scube.crm.dao.AdminDAOImpl;
import com.scube.crm.dao.UserRoleDAO;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.utils.EncryptAndDecrypt;
import com.scube.crm.vo.Company;
import com.scube.crm.vo.RolesVO;
import com.scube.crm.vo.User;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService{
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(AdminDAOImpl.class);

	@Autowired
	private AdminDAO adminDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
		@Override
	 public AdminUserBO createUserRoles(AdminUserBO adminuserlist) throws Exception {
		 LOGGER.entry();
			
		 List<RolesVO> listrolevo = new ArrayList<RolesVO>();
		 User uservo = adminDAO.getEmployee(adminuserlist.getUserId());
		 for(RoleBO bo :adminuserlist.getListrlebo()){
			 RolesVO vo = new RolesVO();
			 vo.setRoleId(bo.getRoleId());
			 vo.setRoleName(bo.getRoleName());
			 listrolevo.add(vo);
		 }
		 if(null!=adminuserlist && 0<adminuserlist.getUserId()) {
			 uservo.setName(adminuserlist.getName());
			 uservo.setId(adminuserlist.getUserId());
			 uservo.setEmailAddress(adminuserlist.getEmailAddress());
			 //uservo.setPassword(adminuserlist.getPassword());
			 uservo.setConfirmpassword(EncryptAndDecrypt.encrypt(adminuserlist.getConfirmPassword()));
			 uservo.setPassword(EncryptAndDecrypt.encrypt(adminuserlist.getPassword()));
			 //uservo.setConfirmpassword(adminuserlist.getConfirmPassword());
			 uservo.setMobileNo(adminuserlist.getMobileNo());
		 }
		 uservo.setListRoletypeVo(listrolevo);
		 boolean active=false;
		 active=adminuserlist.isActive();
		 if(active==false){
			 uservo.setActive(true);
		 }
		 else{
			 uservo.setActive(true);
		 }
		 uservo = userRoleDAO.createUserRole(uservo);
		 AdminUserBO bo = new AdminUserBO();
		 bo.setId(uservo.getId());
		 return bo;
	 }

	 @Override
	 public void deleteUserRole(AdminUserBO userbo) {

		 User user = new User();
		 List<User> userListVO=new ArrayList<>();
		 user.setId(userbo.getId()); 
		 userListVO=adminDAO.retriveuserById(user);
		 if(null!=userListVO&&!userListVO.isEmpty()&&0<userListVO.size()) {

			 for (User user2 : userListVO) {
				 for (RolesVO roleVO : user2.getListRoletypeVo()) {  
					 user=userRoleDAO.deleteUserRole(user.getId(),roleVO.getRoleId());
					 System.out.println("Successfully deleted");
				 }
			 }

		 }
		 user.setActive(false);	

	 }

	 @Override
	 public AdminUserBO updateUserRoles(AdminUserBO bo) throws Exception {
		 
		 List<RolesVO> listrolevo = new ArrayList<RolesVO>();
		 User uservo = new User();
		 for(RoleBO roleBo :bo.getListrlebo()){
			 RolesVO vo = new RolesVO();
			 vo.setRoleId(roleBo.getRoleId());
			 vo.setRoleName(roleBo.getRoleName());
			 listrolevo.add(vo);
		 }
		 if(null!=bo && 0<bo.getId()) {
			 uservo.setName(bo.getName());
			 uservo.setId(bo.getId());
			 uservo.setEmailAddress(bo.getEmailAddress());
			 uservo.setPassword(bo.getPassword());
			 Company company=new Company();
			 company.setCompanyId(bo.getCompanyId());
			 uservo.setCompany(company);
			 //uservo.setConfirmpassword(EncryptAndDecrypt.encrypt(bo.getConfirmPassword()));
			 //uservo.setPassword(EncryptAndDecrypt.encrypt(bo.getPassword()));
			 uservo.setConfirmpassword(bo.getConfirmPassword());
			 uservo.setMobileNo(bo.getMobileNo());
		 }
		 uservo.setListRoletypeVo(listrolevo);
		 boolean active=false;
		 active=bo.isActive();
		 if(active==false){
			 uservo.setActive(true);
		 }
		 else{
			 uservo.setActive(true);
		 }
		 uservo = userRoleDAO.updateUserRole(uservo);
		 bo.setId(uservo.getId());
		 return bo;
	 }

}
