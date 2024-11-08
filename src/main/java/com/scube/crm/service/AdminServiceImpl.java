package com.scube.crm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AccessBo;
import com.scube.crm.bo.AccessLogBO;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.bo.SkillsBO;
import com.scube.crm.dao.AdminDAO;
import com.scube.crm.dao.RoleTypeDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.model.EmailModel;
import com.scube.crm.utils.EncryptAndDecrypt;
import com.scube.crm.utils.SendEmailServiceImpl;
import com.scube.crm.utils.SuccessMsg;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.AccessVo;
import com.scube.crm.vo.Company;
import com.scube.crm.vo.EmailAccess;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.PrivilegesVO;
import com.scube.crm.vo.RolePrivileges;
import com.scube.crm.vo.RolePrivilegesVO;
import com.scube.crm.vo.RolesVO;
import com.scube.crm.vo.SkillsVO;
import com.scube.crm.vo.User;
import com.scube.crm.vo.WorkItemVO;

/**
 * Owner : Scube Technologies Created Date: Nov-22-2014 Created by :
 * Sathishkumar.s Description : JobSeekerServiceImpl Class is a Class which is
 * responsible for access the data from Controller then convert it into
 * persistent Object then sent it into DAO class. Reviewed by :
 * 
 * 
 */

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(AdminServiceImpl.class);

	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private SalesOrderService salesOrderService;

	static boolean isApproval = true;
	EmailModel model = new EmailModel();

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SendEmailServiceImpl emailManager;

	@Autowired
	private RoleTypeService roleTypeService;

	@Autowired
	private RoleTypeDao roleTypeDAO;

	@Override
	public AdminLoginBO authendicate(AdminLoginBO adminLoginBO) throws MySalesException {
		AdminServiceImpl.LOGGER.entry();

		final AdminLoginBO adminLogin = new AdminLoginBO();
		try {
			final User user = this.adminDAO.authendicate("emailAddress", adminLoginBO.getEmailAddress());

			/*
			 * if (null != adminLoginVO) { final String password =
			 * EncryptAndDecrypt.decrypt( adminLoginVO.getPassword(),
			 * EncryptAndDecrypt.TOKEN);
			 */
			if (adminLoginBO.getPassword().equals(user.getPassword())) {
				if (user.isActive()) {
					BeanUtils.copyProperties(user, adminLogin);
					String userName = adminLoginBO.getEmailAddress();
					addLoginStatus(userName);
					adminLogin.setActive(true);
				} else {
					adminLogin.setActive(false);
				}
			} else {
				adminLogin.setActive(false);
			}
			// adminLogin.setPassword(password);

			/* */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return adminLogin;
	}

	@Override
	public boolean editLoginStatus(LoginStatusVO loginStatusVO) {
		adminDAO.editLoginStatus(loginStatusVO);
		return false;

	}

	@Override
	public boolean addLoginStatus(String username) throws MySalesException {

		LoginStatusVO loginStatus = new LoginStatusVO();
		loginStatus.setUserName(username);
		loginStatus.setType("Admin");
		loginStatus.setStatus(true);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Date date = cal.getTime();
		loginStatus.setStartTime(date);
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.MONTH, 1);
		Date date1 = cal.getTime();
		loginStatus.setEndTime(date1);
		loginStatus.setActivity("login");
		adminDAO.addLoginStatus(loginStatus);

		return false;
	}

	@Override
	public boolean createAccessLog(AccessLogBO logBO) {
		boolean accessStatus = false;
		try {
			AccessLogVO logVO = new AccessLogVO();
			logVO.setAccessId(logBO.getAccessId());
			logVO.setAccessDate(logBO.getAccessDate());
			logVO.setClientIP(logBO.getClientIP());
			logVO.setSessionId(logBO.getSessionId());
			accessStatus = adminDAO.createAccessLog(logVO);
		} catch (Exception e) {

		}
		return accessStatus;
	}

	@Override
	public AdminUserBO createuser(AdminUserBO adminBO) throws MySalesException {
		LOGGER.entry();
		User adminVO = new User();
		try {
			BeanUtils.copyProperties(adminBO, adminVO);
			adminVO.setActive(true);
			adminVO.setisDelete(false);
			adminVO.setConfirmpassword(EncryptAndDecrypt.encrypt(adminBO.getConfirmPassword()));
			adminVO.setPassword(EncryptAndDecrypt.encrypt(adminBO.getPassword()));
			// adminVO.setPrimarySkill(adminBO.getPrimarySkill());
			Company company = new Company();// companyId based on id
			company.setCompanyId(adminBO.getCompanyId());
			adminVO.setCompany(company);
			adminVO.setCreatedBy(adminBO.getCreatedBy());

			if (null != adminBO.getServiceName()) {
				long serviceId = 0;
				serviceId = Long.parseLong(adminBO.getServiceName());
				InventoryVO productServiceVO = new InventoryVO();
				productServiceVO.setServiceId(serviceId);
				adminVO.setProductServiceVO(productServiceVO);
			}
			if (null != adminBO && null != adminBO.getSkillsBO()
					&& !(adminBO.getSkillsBO().getDescriptions().isEmpty())) {

				List<SkillsVO> SkillsServiceList = new ArrayList<>();
				List<String> skillsStringList = new ArrayList<String>(
						Arrays.asList(adminBO.getSkillsBO().getDescriptions().split(",")));

				for (String string : skillsStringList) {
					SkillsVO skillsVO = new SkillsVO();
					Long skillsId = Long.parseLong(string);
					skillsVO.setSkillsId(skillsId);
					SkillsServiceList.add(skillsVO);
				}
				adminVO.setSkillsListVO(SkillsServiceList);

			}

			long id = adminDAO.createuser(adminVO);
			if (id > 0) {
				adminBO.setId(id);
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add user has failed:" + ex.getMessage());
			}
			LOGGER.info("Add user has failed:" + ex.getMessage());
		}

		return adminBO;

	}

	@Override
	public List<AdminUserBO> retrieveUser(AdminUserBO adminuserBO) throws MySalesException {
		LOGGER.entry();
		List<AdminUserBO> BOList = new ArrayList<AdminUserBO>();
		try {
			User user = new User();
			Company company = new Company(); // company

			if (null != adminuserBO && null != adminuserBO.getCompanyId()) {

				company.setCompanyId(adminuserBO.getCompanyId());
				user.setCompany(company); // companyId

			}
			List<User> VOList = adminDAO.retrieveUser(user);
			int count = 1;
			if (null != VOList && !VOList.isEmpty()) {
				int data;
				for (User vo : VOList) {
					data = count++;
					adminuserBO = new AdminUserBO();
					adminuserBO.setId(vo.getId());
					adminuserBO.setActive(vo.isActive());
					adminuserBO.setsNo(data);
					adminuserBO.setName(vo.getName());
					adminuserBO.setMobileNo(vo.getMobileNo());
					adminuserBO.setPassword(vo.getPassword());
					adminuserBO.setEmailAddress(vo.getEmailAddress());
					adminuserBO.setConfirmPassword(vo.getConfirmpassword());
					if (vo.isActive()) {
						adminuserBO.setStatus("Active");
					} else {
						adminuserBO.setStatus("De-Active");
					}
					BOList.add(adminuserBO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("retrieve user has failed:" + ex.getMessage());
			}
			LOGGER.info("retrieve user has failed:" + ex.getMessage());
		}
		return BOList;
	}

	@Override
	public AdminUserBO updateuser(AdminUserBO adminBO) throws MySalesException {
		LOGGER.entry();
		try {
			User loginVO = new User();
			if (0 < adminBO.getUserId()) {
				loginVO.setId(adminBO.getUserId());
			}
			loginVO = adminDAO.retrieveusers(loginVO);
			loginVO.setMobileNo(adminBO.getMobileNo());
			loginVO.setName(adminBO.getName());
			loginVO.setEmailAddress(adminBO.getEmailAddress());
			loginVO.setPassword(EncryptAndDecrypt.encrypt(adminBO.getPassword()));
			loginVO.setConfirmpassword(EncryptAndDecrypt.encrypt(adminBO.getConfirmPassword()));

			loginVO.setModified(new Date());

			loginVO.setId(adminBO.getUserId());

			Company company = new Company(); // company based id updation
			company.setCompanyId(adminBO.getCompanyId());
			loginVO.setCompany(company);

			if (null != adminBO) {
				InventoryVO productVO = new InventoryVO();
				productVO.setServiceId(adminBO.getProductServiceBO().getServiceId());
				productVO.setServiceName(adminBO.getProductServiceBO().getServiceName());
				loginVO.setProductServiceVO(productVO);
			}

			List<SkillsVO> skillsVOlist = new ArrayList<>();
			if (null != adminBO) {
				for (SkillsBO skillsBO : adminBO.getSkillsListBO()) {
					SkillsVO skillsVO = new SkillsVO();
					skillsVO.setSkillsId(skillsBO.getSkillsId());
					skillsVOlist.add(skillsVO);
					loginVO.setSkillsListVO(skillsVOlist);
				}
			}

			loginVO = adminDAO.updateuser(loginVO);

			if (0 != loginVO.getId()) {
				adminBO.setId(loginVO.getId());
			} else {
				adminBO = new AdminUserBO();
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update user has failed:" + ex.getMessage());
			}
			LOGGER.info("Update user has failed:" + ex.getMessage());
		}
		return adminBO;

	}

	private List<RolesVO> getUserRoles(long userId) throws MySalesException {

		List<RolesVO> userRoles = adminDAO.getUserRoles(userId);
		return userRoles;

	}

	public static Date getDateWithoutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	@Override
	public boolean userStatus(AdminUserBO userBO) throws MySalesException {
		LOGGER.entry();
		boolean loginChanged = false;
		try {
			User userVO = new User();

			if (0 != userBO.getId()) {
				userVO.setId(userBO.getId());
				userVO.setActive(userBO.isActive());
				userVO = adminDAO.userStatus(userVO);
				if (userVO.isActive()) {
					loginChanged = true;
				} else {
					loginChanged = false;
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("UserStatus has failed:" + ex.getMessage());
			}
			LOGGER.info("UserStatus has failed:" + ex.getMessage());
		}
		return loginChanged;
	}

	@Override
	public AdminUserBO retrieveusers(long userId) throws MySalesException {
		LOGGER.entry();
		AdminUserBO adminBO = new AdminUserBO();
		InventoryBO serviceBO = new InventoryBO();
		try {
			User userVO = new User();
			if (0 < userId) {
				userVO.setId(userId);
			}
			userVO = adminDAO.retrieveusers(userVO);
			List<Long> skillsIdList = adminDAO.getSkillIdList(userVO);
			if (null != userVO.getName()) {
				adminBO.setUserId(userVO.getId());
				adminBO.setName(userVO.getName());
				adminBO.setSkillsIds(skillsIdList);
				adminBO.setEmailAddress(userVO.getEmailAddress());
				adminBO.setConfirmPassword(EncryptAndDecrypt.decrypt(userVO.getConfirmpassword()));
				adminBO.setPassword(EncryptAndDecrypt.decrypt(userVO.getPassword()));
				adminBO.setMobileNo(userVO.getMobileNo());
				adminBO.setPrimarySkill(userVO.getPrimarySkill());
			}
			if (null != userVO.getProductServiceVO() && null != userVO.getProductServiceVO().getServiceName()) {
				serviceBO.setServiceId(userVO.getProductServiceVO().getServiceId());
				serviceBO.setServiceName(userVO.getProductServiceVO().getServiceName());
				adminBO.setProductServiceBO(serviceBO);
			}

			List<Long> roleListIds = adminDAO.getroleIdList(userVO);
			if (null != roleListIds && !roleListIds.isEmpty() && 0 < roleListIds.size()) {
				adminBO.setRoleIdList(roleListIds);
				adminBO.setName(userVO.getName());
				adminBO.setId(userVO.getId());
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("retrieve user has failed:" + ex.getMessage());
			}
			LOGGER.info("retrieve user has failed:" + ex.getMessage());
		}
		return adminBO;
	}

	@Override
	public boolean findByEmail(String emailAddress) throws MySalesException {
		LOGGER.entry();
		try {
			final User loginVO = adminDAO.findByEmail("emailAddress", emailAddress);

			if (loginVO != null) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Find email user has failed:" + ex.getMessage());
			}
			LOGGER.info("Find email user has failed:" + ex.getMessage());
		}

		return false;
	}

	@Override
	public long campaignCount(AdminLoginBO adminLoginBO) {
		return adminDAO.campaignCount(adminLoginBO);
	}

	@Override
	public boolean findByMobilenoEmployee(AdminUserBO adminBO) throws MySalesException {
		LOGGER.entry();
		User userVO = new User();
		try {
			if (null != adminBO.getMobileNo()) {
				userVO.setMobileNo(adminBO.getMobileNo());
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Find mobile user has failed:" + ex.getMessage());
			}
			LOGGER.info("Find mobile user has failed:" + ex.getMessage());
		}
		return adminDAO.findByMobilenoEmployee(userVO);
	}

	@Override
	public List<AdminUserBO> searchEmployeeList(AdminUserBO adminBO) throws MySalesException {
		LOGGER.entry();
		List<AdminUserBO> adminUserBOList = new ArrayList<AdminUserBO>();
		try {
			List<User> adminUserList = new ArrayList<User>();
			adminUserList = adminDAO.searchEmployeeList(adminBO);
			int sno = 1;
			for (User user : adminUserList) {
				AdminUserBO adminUserBO = new AdminUserBO();
				adminUserBO.setId(user.getId());
				adminUserBO.setEmailAddress(user.getEmailAddress());
				adminUserBO.setConfirmPassword(user.getConfirmpassword());
				adminUserBO.setPassword(user.getPassword());
				adminUserBO.setMobileNo(user.getMobileNo());
				adminUserBO.setsNo(sno++);

				AdminLoginBO adminLoginVO = null;
				adminUserBOList.add(adminUserBO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search user has failed:" + ex.getMessage());
			}
			LOGGER.info("Search user has failed:" + ex.getMessage());
		}
		return adminUserBOList;
	}

	@Override
	public long employeesCount(AdminLoginBO adminLoginBO) {
		return adminDAO.employeesCount(adminLoginBO);
	}

	@Override
	public long leadsCount(AdminLoginBO adminLoginBO) {
		return adminDAO.leadsCount(adminLoginBO);
	}

	@Override
	public long customerCount(AdminLoginBO adminLoginBO) {
		return adminDAO.customerCount(adminLoginBO);
	}

	@Override
	public boolean sendClientMail(OpportunityBO employerRegisterBO) throws MySalesException {
		LOGGER.entry();
		boolean alertStatus = false;
		try {
			EmailModel model = new EmailModel();

			final String[] toaddress = employerRegisterBO.getEmailAddress().split(",");
			final String subject = messageSource.getMessage("Validate.RegisterConfirm", null, null);
			for (int i = 0; i < toaddress.length; i++) {
				String bodycontent = "employerVerificationMail";
				model.setUrl("www.myjobkart.com/find-jobs.html?companyId=");
				model.setFirstname(employerRegisterBO.getFirstName());
				model.setEmail(employerRegisterBO.getEmailAddress());
				alertStatus = emailManager.sendEmail(toaddress[i], subject, bodycontent, model);
			}
			if (alertStatus) {
				EmailAccess accessVO = new EmailAccess();
				accessVO.setDate(new Date());
				accessVO.setEmailAddress(employerRegisterBO.getEmailAddress());
				// accessVO.setMailedBy(employerRegisterBO.getEmployerId());
				accessVO.setStatus(alertStatus);
				accessVO.setMailTO(employerRegisterBO.getId());
				List<EmailAccess> accessVOList = new ArrayList<EmailAccess>();
				accessVOList.add(accessVO);

				if (null != accessVOList || accessVOList.size() > 0) {
					adminDAO.saveEmailList(accessVOList);
				}
			}

		} catch (final Exception ex) {
		}

		return alertStatus;
	}

	public long getCurrentActionCustomers(AdminLoginBO adminLoginBO) {
		return adminDAO.getCurrentActionCustomers(adminLoginBO);

	}

	@Override
	public long productCount(AdminLoginBO adminLoginBO) {
		return adminDAO.productCount(adminLoginBO);
	}

	@Override
	public PrivilegesBO savePrivileges(PrivilegesBO privilegesbo) throws MySalesException {
		LOGGER.entry();
		PrivilegesBO privilegesBo = new PrivilegesBO();
		try {
			PrivilegesVO privilegesvo = new PrivilegesVO();
			privilegesvo.setPrivilegesName(privilegesbo.getPrivilegename());
			privilegesvo = adminDAO.savePrivileges(privilegesvo);
			privilegesBo.setPrivilegename(privilegesvo.getPrivilegesName());
			privilegesBo.setPrivilegeId(privilegesvo.getPrivilegeId());
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createprivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("createprivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegesBo;

	}

	@Override
	public List<PrivilegesBO> retrivePrivileges(List<PrivilegesBO> privilegesbolist) throws MySalesException {
		return adminDAO.retrivePrivileges(privilegesbolist);
	}

	@Override
	public PrivilegesBO deletePrivilege(PrivilegesBO bo) throws MySalesException {
		LOGGER.entry();
		try {
			int result = 0;
			PrivilegesVO vo = new PrivilegesVO();
			vo.setPrivilegeId(bo.getPrivilegeId());
			vo.setDeleted(bo.getDeleted());
			result = adminDAO.deletePrivilege(vo);
			if (0 != result) {
				bo.setResponse(SuccessMsg.DELETE_SUCCESS);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deletePrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("deletePrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return bo;
	}

	@Override
	public PrivilegesBO editPrivileges(PrivilegesBO bo) throws MySalesException {
		LOGGER.entry();
		PrivilegesBO bo1 = new PrivilegesBO();
		try {
			PrivilegesVO vo = new PrivilegesVO();
			vo.setPrivilegeId(bo.getPrivilegeId());
			vo = adminDAO.editPrivileges(vo);
			bo1.setPrivilegeId(vo.getPrivilegeId());
			bo1.setPrivilegename(vo.getPrivilegesName());
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editPrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("editPrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return bo1;
	}

	@Override
	public PrivilegesBO updatePrivilege(PrivilegesBO privilegebo) throws MySalesException {
		LOGGER.entry();
		try {
			PrivilegesVO vo = new PrivilegesVO();
			vo.setPrivilegeId(privilegebo.getPrivilegeId());
			vo.setPrivilegesName(privilegebo.getPrivilegename());
			vo = adminDAO.updatePrivileges(vo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("updatePrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("updatePrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegebo;
	}

	@Override
	public List<PrivilegesBO> searchPrivilegename(PrivilegesBO bo) throws MySalesException {
		LOGGER.entry();
		List<PrivilegesBO> listbo = new ArrayList<PrivilegesBO>();
		try {
			List<PrivilegesVO> listvo = new ArrayList<PrivilegesVO>();
			PrivilegesVO vo = new PrivilegesVO();
			vo.setPrivilegesName(bo.getPrivilegename());
			listvo = adminDAO.searchPrivileges(vo);

			if (null != listvo && listvo.size() > 0) {
				int count = 0;
				for (PrivilegesVO privilegevo : listvo) {
					PrivilegesBO privilegeBO = new PrivilegesBO();
					int sno = ++count;
					privilegeBO.setsNo(sno);
					privilegeBO.setPrivilegename(privilegevo.getPrivilegesName());
					privilegeBO.setPrivilegeId(privilegevo.getPrivilegeId());
					listbo.add(privilegeBO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchPrivilegename has failed:" + ex.getMessage());
			}
			LOGGER.info("searchPrivilegename has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return listbo;
	}

	@Override
	public List<RoleBO> retrieveRoleprivilege(List<RoleBO> rolebolist) {
		return adminDAO.retriveRolePrivilege(rolebolist);
	}

	@Override
	public List<PrivilegesBO> getprivileges(List<PrivilegesBO> rolebolist) throws MySalesException {
		LOGGER.entry();
		List<PrivilegesBO> bolist = new ArrayList<PrivilegesBO>();
		try {
			PrivilegesVO vo = new PrivilegesVO();
			for (PrivilegesBO bo : rolebolist) {
				PrivilegesVO privilegevo = new PrivilegesVO();
				vo.setPrivilegeId(bo.getPrivilegeId());
				privilegevo = adminDAO.getprivilege(vo);
				PrivilegesBO privilegebo = new PrivilegesBO();
				privilegebo.setPrivilegeId(privilegevo.getPrivilegeId());
				privilegebo.setPrivilegename(privilegevo.getPrivilegesName());
				bolist.add(privilegebo);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getprivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("getprivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return bolist;

	}

	@Override
	public List<RoleBO> roleprivileges(List<RoleBO> listrolebo) throws MySalesException {
		LOGGER.entry();
		try {
			RolePrivileges roleprivileges = new RolePrivileges();

			List<PrivilegesVO> rolevolist = new ArrayList<PrivilegesVO>();
			if (null != listrolebo && listrolebo.size() > 0) {
				for (RoleBO bo : listrolebo) {
					RolePrivileges roleprivilege = new RolePrivileges();
					RolePrivilegesVO vo = new RolePrivilegesVO(roleprivileges);
					PrivilegesVO privilegevo = new PrivilegesVO();
					RolesVO rolevo = new RolesVO();
					rolevo.setRoleId(bo.getRoleId());

					privilegevo.setPrivilegeId(bo.getPrivilegeId());
					int roleid = (int) rolevo.getRoleId();
					int privilegeid = (int) privilegevo.getPrivilegeId();
					roleprivilege.setRoleId(roleid);
					roleprivilege.setPrivilegeId(privilegeid);
					vo.setId(roleprivilege);

					vo = adminDAO.assignRolePrivilege(vo);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("roleprivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("roleprivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return listrolebo;
	}

	@Override
	public RoleBO getroleprivilege(RoleBO rolebo) throws MySalesException {
		LOGGER.entry();
		RoleBO borole = new RoleBO();
		try {
			RolesVO rolevo = new RolesVO();
			PrivilegesVO privilegevo = new PrivilegesVO();
			int roleid = (int) rolebo.getRoleId();
			int privilegeid = (int) rolebo.getPrivilegeId();
			RolePrivileges roleprivilege = new RolePrivileges();
			roleprivilege.setRoleId(roleid);
			roleprivilege.setPrivilegeId(privilegeid);
			RolePrivilegesVO roleprivilegevo = new RolePrivilegesVO();
			roleprivilegevo.setId(roleprivilege);
			roleprivilegevo = adminDAO.getroleprivilege(roleprivilegevo);
			long rolesid = (long) roleprivilegevo.getId().getRoleId();
			long privilegesid = (long) roleprivilegevo.getId().getPrivilegeId();
			RoleBO rolesbo = new RoleBO();
			borole.setRoleId(rolesid);
			borole = roleTypeService.getrolebyid(borole);
			rolesbo.setRoleId(borole.getRoleId());
			rolesbo.setRoleName(borole.getRoleName());
			rolesbo.setPrivilegeId(privilegesid);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getroleprivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("getroleprivilege has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return borole;
	}

	@Override
	public RoleBO removeRole(RoleBO rolebo) throws MySalesException {
		LOGGER.entry();
		try {
			RolePrivilegesVO assignvo = new RolePrivilegesVO();
			RolesVO roletypevo = new RolesVO();
			int roleid = (int) rolebo.getRoleId();

			roletypevo.setRoleId(roleid);
			assignvo.getId().setRoleId((int) roletypevo.getRoleId());
			assignvo = adminDAO.removeRole(assignvo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("removeRole has failed:" + ex.getMessage());
			}
			LOGGER.info("removeRole has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return rolebo;
	}

	/*
	 * @Override public List<AdminUserBO> retrieveUser(List<AdminUserBO>
	 * listUserBo)throws MySalesException { LOGGER.entry(); List<AdminUserBO>
	 * adminUserBOList = new ArrayList<AdminUserBO>(); try { adminUserBOList =
	 * adminDAO.retrieveUser(adminUserBOList); }catch (Exception ex) { if
	 * (LOGGER.isDebugEnabled()) { LOGGER.debug("retrieve has failed:" +
	 * ex.getMessage()); } LOGGER.info("retrieve  has failed:" + ex.getMessage()); }
	 * finally { LOGGER.exit(); } return adminUserBOList;
	 * 
	 * }
	 */

	@Override
	public List<AdminUserBO> retrieveUser(List<AdminUserBO> listUserBo) throws MySalesException {
		LOGGER.entry();
		List<AdminUserBO> adminUserBOList = new ArrayList<AdminUserBO>();
		Long value = listUserBo.get(0).getCompanyId();
		AdminUserBO newuser = new AdminUserBO();
		newuser.setCompanyId(value);
		adminUserBOList.add(newuser);
		try {
			adminUserBOList = adminDAO.retrieveUser(adminUserBOList);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("retrieve has failed:" + ex.getMessage());
			}
			LOGGER.info("retrieve  has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return adminUserBOList;

	}

	@Override
	public AdminUserBO retriveUserByName(AdminUserBO bo) throws Exception {
		LOGGER.entry();
		AdminUserBO userbo = new AdminUserBO();
		try {
			User user = new User();
			user.setName(bo.getName());
			user = adminDAO.retriveUserByname(user);
			userbo.setId(user.getId());
			userbo.setName(user.getName());
			userbo.setMobileNo(user.getMobileNo());
			userbo.setPassword(user.getPassword());
			userbo.setEmailAddress(user.getEmailAddress());
			userbo.setConfirmPassword(user.getConfirmpassword());
			userbo.setCompanyId(user.getCompany().getCompanyId());
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("retriveUserByName has failed:" + ex.getMessage());
			}
			LOGGER.info("retriveUserByName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return userbo;
	}

	@Override
	public RoleBO getPrivilegesbyId(RoleBO rolebo) throws MySalesException {
		LOGGER.entry();
		RoleBO role = new RoleBO();
		try {
			PrivilegesVO privilegesvo = new PrivilegesVO();
			privilegesvo.setPrivilegeId(rolebo.getPrivilegeId());
			privilegesvo = adminDAO.getprivilegeById(privilegesvo);
			role.setPrivilegeId(privilegesvo.getPrivilegeId());
			role.setPrivilegename(privilegesvo.getPrivilegesName());
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getPrivilegesbyId has failed:" + ex.getMessage());
			}
			LOGGER.info("getPrivilegesbyId has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return role;
	}

	@Override
	public RoleBO createRolePrivileges(RoleBO listrolebo) throws MySalesException {
		LOGGER.entry();
		try {
			RolesVO rolevo = new RolesVO();
			List<PrivilegesVO> privilegesvolist = new ArrayList<PrivilegesVO>();

			for (PrivilegesBO privilegebo : listrolebo.getPrivilegesbolist()) {
				PrivilegesVO vo = new PrivilegesVO();
				vo.setPrivilegeId(privilegebo.getPrivilegeId());
				vo.setPrivilegesName(privilegebo.getPrivilegename());
				privilegesvolist.add(vo);
			}

			rolevo.setRoleId(listrolebo.getRoleId());
			rolevo.setRoleName(listrolebo.getRoleName());
			rolevo.setListPrivilegesvo(privilegesvolist);
			rolevo.setActive(true);
			rolevo = roleTypeDAO.createRolePrivileges(rolevo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createRolePrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("createRolePrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return listrolebo;
	}

	@Override
	public RoleBO deleteRolePrivilege(RoleBO rolebo) throws MySalesException {
		LOGGER.entry();
		try {
			RolesVO rolevo = new RolesVO();
			List<RolesVO> roleVOList = new ArrayList<>();
			rolevo.setRoleId(rolebo.getRoleId());
			roleVOList = roleTypeDAO.getRolelist(rolevo);
			if (null != roleVOList && !roleVOList.isEmpty() && 0 < roleVOList.size()) {
				for (RolesVO rolesVO : roleVOList) {
					for (PrivilegesVO privilegesVO : rolesVO.getListPrivilegesvo()) {
						rolevo = roleTypeDAO.deleteRolePrivileges(rolevo.getRoleId(), privilegesVO.getPrivilegeId());
						System.out.println("Deleted Successfully");
					}
				}
			}

			rolevo.setActive(false);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deleterolePrivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("deleterolePrivilege has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return rolebo;
	}

	@Override
	public List<PrivilegesBO> listOfPrivileges() throws MySalesException {
		LOGGER.entry();
		List<PrivilegesBO> privilegeListBO = new ArrayList<>();
		try {
			PrivilegesVO privVO = new PrivilegesVO();
			List<PrivilegesVO> privilegeListVO = new ArrayList<>();

			privVO.setDeleted(false);
			if (false == privVO.getisDeleted()) {
				privilegeListVO.add(privVO);
				privilegeListVO = adminDAO.listOfPrivileges(privilegeListVO);

				if (null != privilegeListVO && !privilegeListVO.isEmpty() && 0 < privilegeListVO.size()) {
					for (PrivilegesVO privilegesVO : privilegeListVO) {
						PrivilegesBO privBO = new PrivilegesBO();
						privBO.setPrivilegeId(privilegesVO.getPrivilegeId());
						privBO.setPrivilegename(privilegesVO.getPrivilegesName());
						privilegeListBO.add(privBO);

					}
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfPrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfPrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeListBO;
	}

	@Override
	public List<AccessBo> listOfAccess() throws MySalesException {
		LOGGER.entry();
		List<AccessBo> accessBOlist = new ArrayList<>();
		try {
			AccessVo accessVO = new AccessVo();

			List<AccessVo> accessVOlist = new ArrayList<>();
			accessVOlist = adminDAO.listOfAccess();

			if (null != accessVOlist && !accessVOlist.isEmpty() && 0 < accessVOlist.size()) {
				for (AccessVo accessVo2 : accessVOlist) {
					AccessBo accessBO = new AccessBo();
					accessBO.setAccessId(accessVo2.getAccessId());
					accessBO.setAccessName(accessVo2.getAccessName());
					accessBOlist.add(accessBO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return accessBOlist;
	}

	@Override
	public PrivilegesBO privilegeAccess(PrivilegesBO privlegeBO) throws MySalesException {
		LOGGER.entry();
		try {
			PrivilegesVO privilegeVo = new PrivilegesVO();
			List<AccessVo> accessVOlist = new ArrayList<>();
			if (null != privlegeBO) {
				for (AccessBo accessBO : privlegeBO.getAccessBOlist()) {
					AccessVo accessvo = new AccessVo();
					accessvo.setAccessId(accessBO.getAccessId());
					accessvo.setAccessName(accessBO.getAccessName());
					accessVOlist.add(accessvo);
					privilegeVo.setAccessList(accessVOlist);
				}
				if (null != privlegeBO) {
					for (PrivilegesBO privilegeBO : privlegeBO.getPrivilegeslis()) {
						privilegeVo.setPrivilegeId(privilegeBO.getPrivilegeId());
						privilegeVo.setPrivilegesName(privilegeBO.getPrivilegename());
					}
				}
			}

			if (null != privilegeVo) {
				privilegeVo = adminDAO.privilegeAccess(privilegeVo);
			}
			if (0 < privilegeVo.getPrivilegeId()) {
				privlegeBO.setPrivilegeId(privilegeVo.getPrivilegeId());
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("privilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("privilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privlegeBO;
	}

	@Override
	public List<PrivilegesBO> listOfPrvielegeAccess() throws MySalesException {
		LOGGER.entry();
		List<PrivilegesBO> privilegeBOList = new ArrayList<>();
		try {
			List<PrivilegesVO> privilegeVOList = new ArrayList<>();
			List<AccessBo> accessList = new ArrayList<>();
			privilegeVOList = adminDAO.listOfPrivilegeAccess();

			if (null != privilegeVOList && !privilegeVOList.isEmpty() && 0 < privilegeVOList.size()) {
				int count = 0;
				for (PrivilegesVO privilege : privilegeVOList) {
					PrivilegesBO privilegeBO = new PrivilegesBO();
					int data = ++count;
					privilegeBO.setsNo(data);
					privilegeBO.setPrivilegeId(privilege.getPrivilegeId());
					privilegeBO.setPrivilegename(privilege.getPrivilegesName());
					privilegeBOList.add(privilegeBO);
				}

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfPrvielegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfPrvielegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeBOList;
	}

	@Override
	public List<PrivilegesBO> editPriviegeAccess(PrivilegesBO privilegeBO) throws MySalesException {
		LOGGER.entry();
		List<PrivilegesBO> privilegeBOList = new ArrayList<>();
		try {
			PrivilegesVO privilegeVO = new PrivilegesVO();

			List<PrivilegesVO> privilegeVOList = new ArrayList<>();
			List<AccessBo> accessBOList1 = new ArrayList<>();
			if (0 < privilegeBO.getPrivilegeId()) {
				privilegeVO.setPrivilegeId(privilegeBO.getPrivilegeId());
			}
			privilegeVOList = adminDAO.editPrivilegeAccess(privilegeVO);
			List<Long> privilegeIdList = adminDAO.geAccessIdListIdList(privilegeVO);

			if (null != privilegeVOList && !privilegeVOList.isEmpty() && 0 < privilegeVOList.size()) {
				for (PrivilegesVO privilegeVO1 : privilegeVOList) {
					privilegeBO.setPrivilegeId(privilegeVO1.getPrivilegeId());
					privilegeBO.setPrivilegename(privilegeVO1.getPrivilegesName());
					privilegeBOList.add(privilegeBO);
					for (AccessVo accessVO : privilegeVO1.getAccessList()) {
						AccessBo accessBO = new AccessBo();
						PrivilegesBO privilegeBO1 = new PrivilegesBO();
						accessBO.setAccessId(accessVO.getAccessId());
						accessBO.setAccessName(accessVO.getAccessName());
						accessBOList1.add(accessBO);
						privilegeBO1.setAccessIds(privilegeIdList);
						privilegeBO1.setAccessBOlist(accessBOList1);
						privilegeBOList.add(privilegeBO1);
					}
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editPriviegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("editPriviegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeBOList;
	}

	@Override
	public PrivilegesBO updatePrivilegeAccess(PrivilegesBO privilegeBO) throws MySalesException {
		LOGGER.entry();
		try {
			PrivilegesVO privilegeVO = new PrivilegesVO();
			List<AccessVo> accessVOlist = new ArrayList<>();

			if (0 < privilegeBO.getPrivilegeId()) {
				privilegeVO.setPrivilegeId(privilegeBO.getPrivilegeId());
				privilegeVO.setPrivilegesName(privilegeBO.getPrivilegename());
			}
			if (null != privilegeBO) {
				for (AccessBo accessbo2 : privilegeBO.getAccessBOlist()) {
					AccessVo accessVO = new AccessVo();
					accessVO.setAccessId(accessbo2.getAccessId());
					accessVOlist.add(accessVO);
					privilegeVO.setAccessList(accessVOlist);
				}
			}

			if (null != privilegeVO) {
				privilegeVO = adminDAO.updatePrivilegeAccess(privilegeVO);
			}
			if (0 < privilegeVO.getPrivilegeId()) {
				privilegeBO.setPrivilegeId(privilegeVO.getPrivilegeId());
				System.out.println("Sucessfully Updated");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("updatePrivilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("updatePrivilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeBO;
	}

	@Override
	public PrivilegesBO deletePrivilegeAccess(PrivilegesBO privilegeBO) throws MySalesException {
		LOGGER.entry();
		try {
			List<PrivilegesVO> privilegeVOList = new ArrayList<>();
			List<AccessVo> accessVOlist = new ArrayList<>();

			PrivilegesVO privilegeVO = new PrivilegesVO();
			if (0 < privilegeBO.getPrivilegeId()) {
				privilegeVO.setPrivilegeId(privilegeBO.getPrivilegeId());
			}

			privilegeVOList = adminDAO.getPrivilegeId(privilegeVO);
			if (null != privilegeVOList && !privilegeVOList.isEmpty() && 0 < privilegeVOList.size()) {
				for (PrivilegesVO privilegesVO : privilegeVOList) {
					for (AccessVo AccessVOObj : privilegesVO.getAccessList()) {
						privilegeVO = adminDAO.deletePrivilegeAccess(privilegeVO.getPrivilegeId(),
								AccessVOObj.getAccessId());
						System.out.println("Sucessfully deleted");
					}
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deletePrivilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("deletePrivilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeBO;
	}

	@Override
	public List<AdminUserBO> searchUserName(AdminUserBO adminBO) throws MySalesException {
		LOGGER.entry();
		List<AdminUserBO> adminBOList = new ArrayList<>();
		try {
			User userVO = new User();
			List<User> listUserVO = new ArrayList<>();

			if (null != adminBO.getName()) {
				userVO.setName(adminBO.getName());
			}
			userVO.setRecordIndex(adminBO.getRecordIndex());
			userVO.setMaxRecord(adminBO.getMaxRecord());

			listUserVO = adminDAO.searchUserName(userVO);
			if (null != listUserVO && !listUserVO.isEmpty() && 0 < listUserVO.size()) {
				int count = 0;
				for (User userVOObj : listUserVO) {
					int sNo = ++count;
					AdminUserBO adminBOObj = new AdminUserBO();
					adminBOObj.setsNo(sNo);
					adminBOObj.setUserId(userVOObj.getId());
					adminBOObj.setName(userVOObj.getName());
					adminBOList.add(adminBOObj);

				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchUserName has failed:" + ex.getMessage());
			}
			LOGGER.info("searchUserName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return adminBOList;
	}

	@Override
	public List<RoleBO> searchRoleByName(RoleBO roleSearchBOObj) throws MySalesException {
		LOGGER.entry();
		List<RoleBO> roleBOList = new ArrayList<>();
		try {
			List<RolesVO> roleVOList = new ArrayList<>();
			RolesVO roleVOObj = new RolesVO();
			if (null != roleSearchBOObj.getRoleName()) {
				roleVOObj.setRoleName(roleSearchBOObj.getRoleName());
			}
			roleVOList = adminDAO.searchRoleName(roleVOObj);
			if (null != roleVOList && !roleVOList.isEmpty() && 0 < roleVOList.size()) {
				int count = 0;
				for (RolesVO rolesVO : roleVOList) {
					RoleBO roleBO = new RoleBO();
					int sNo = ++count;
					roleBO.setsNo(sNo);
					roleBO.setRoleId(rolesVO.getRoleId());
					roleBO.setRoleName(rolesVO.getRoleName());
					roleBOList.add(roleBO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchByRoleName has failed:" + ex.getMessage());
			}
			LOGGER.info("searchByRoleName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return roleBOList;
	}

	@Override
	public List<PrivilegesBO> searchByPrivilegeName(PrivilegesBO privilegeSearchBO) throws MySalesException {
		LOGGER.entry();
		List<PrivilegesBO> privilegeBOList = new ArrayList<>();
		try {
			PrivilegesVO privilegeVO = new PrivilegesVO();
			List<PrivilegesVO> privilegeVOList = new ArrayList<>();

			if (null != privilegeSearchBO.getPrivilegename()) {
				privilegeVO.setPrivilegesName(privilegeSearchBO.getPrivilegename());
			}

			privilegeVOList = adminDAO.searchByPrivilegeName(privilegeVO);
			if (null != privilegeVOList && !privilegeVOList.isEmpty() && 0 < privilegeVOList.size()) {
				int count = 0;
				for (PrivilegesVO privilegesVOObj : privilegeVOList) {
					int sNo = ++count;
					PrivilegesBO privilegeBOobj = new PrivilegesBO();
					privilegeBOobj.setsNo(sNo);
					privilegeBOobj.setPrivilegeId(privilegesVOObj.getPrivilegeId());
					privilegeBOobj.setPrivilegename(privilegesVOObj.getPrivilegesName());
					privilegeBOList.add(privilegeBOobj);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchByPrivilegeName has failed:" + ex.getMessage());
			}
			LOGGER.info("searchByPrivilegeName has failed:" + ex.getMessage());
		} /*
			 * finally { LOGGER.exit(); }
			 */

		return privilegeBOList;
	}

	@Override
	public long getListOfadminUser(AdminUserBO adminUserBO) throws MySalesException {
		LOGGER.entry();
		User user = new User();
		try {

			user.setisDelete(adminUserBO.getisDelete());
			if (null != adminUserBO.getCompanyId()) { // company based retrieve condition
				Company company = new Company();
				company.setCompanyId(adminUserBO.getCompanyId());
				user.setCompany(company);
			}
			if (null != adminUserBO.getName() && !adminUserBO.getName().isEmpty()) {
				user.setName(adminUserBO.getName());
			}
			if (null != adminUserBO.getEmailAddress()) {
				user.setEmailAddress(adminUserBO.getEmailAddress());
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Getlistadmin User has failed:" + ex.getMessage());
			}
			LOGGER.info("Getlistadmin User has failed:" + ex.getMessage());
		}
		return adminDAO.getListOfadminUser(user);
	}

	@Override
	public List<AdminUserBO> retrieveUserByPagination(AdminUserBO listUserBo) throws MySalesException {
		LOGGER.entry();
		List<AdminUserBO> adminUserBOList = new ArrayList<AdminUserBO>();
		try {
			List<User> userVOList = new ArrayList<User>();

			listUserBo.setisDelete(listUserBo.getisDelete());
			listUserBo.setMaxRecord(listUserBo.getMaxRecord());
			listUserBo.setRecordIndex(listUserBo.getRecordIndex());
			listUserBo.setCompanyId(listUserBo.getCompanyId());

			if (null != listUserBo.getSkillsBO()) {
				SkillsBO skillsVo = new SkillsBO();
				skillsVo.setSkillsId(listUserBo.getSkillsBO().getSkillsId());
				skillsVo.setDescriptions(listUserBo.getSkillsBO().getDescriptions());
				listUserBo.setSkillsBO(skillsVo);
			}
			userVOList = adminDAO.retrieveUserByPagination(listUserBo);

			if (null != userVOList && !userVOList.isEmpty()) {
				int sNo = listUserBo.getRecordIndex();
				for (User vo : userVOList) {
					AdminUserBO adminuserBO = new AdminUserBO();
					adminuserBO.setId(vo.getId());
					List<SkillsBO> skillsListBO = new ArrayList<SkillsBO>();
					for (SkillsVO skill : vo.getSkillsListVO()) {
						SkillsBO skillsBO = new SkillsBO();
						skillsBO.setDescriptions(skill.getDescriptions());
						skillsListBO.add(skillsBO);
					}
					adminuserBO.setSkillsListBO(skillsListBO);
					adminuserBO.setActive(vo.isActive());
					adminuserBO.setsNo(++sNo);
					adminuserBO.setName(vo.getName());
					adminuserBO.setMobileNo(vo.getMobileNo());
					adminuserBO.setPassword(vo.getPassword());
					adminuserBO.setPrimarySkill(vo.getPrimarySkill());
					adminuserBO.setEmailAddress(vo.getEmailAddress());
					adminuserBO.setConfirmPassword(vo.getConfirmpassword());

					if (vo.isActive()) {
						adminuserBO.setStatus("Active");
					} else {
						adminuserBO.setStatus("De-Active");
					}
					adminUserBOList.add(adminuserBO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("retrive User Pagination  has failed:" + ex.getMessage());
			}
			LOGGER.info(" retrive User Pagination User has failed:" + ex.getMessage());
		}
		return adminUserBOList;
	}

	@Override
	public AdminLoginBO getEmployeeObjectByName(AdminLoginBO userBO) throws MySalesException {
		LOGGER.entry();
		try {
			User userVO = new User();
			if (null != userBO.getName()) {
				userVO.setName(userBO.getName());
			}
			userVO = adminDAO.getEmployeeObjectByName(userVO);
			if (null != userVO) {
				userBO.setId(userVO.getId());
				userBO.setName(userVO.getName());
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getEmployeeObjectByName has failed:" + ex.getMessage());
			}
			LOGGER.info("getEmployeeObjectByName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return userBO;
	}

	@Override
	public long countOfUsers(long companyId) throws MySalesException {
		LOGGER.entry();
		try {
			long countOfUsers = 0;

			User userVO = new User();
			userVO.setisDelete(false);
			countOfUsers = adminDAO.countOfUsers(userVO, companyId);
			if (0 < countOfUsers) {
				return countOfUsers;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("countOfUsers has failed:" + ex.getMessage());
			}
			LOGGER.info("countOfUsers has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return 0;
	}

	@Override
	public List<AdminUserBO> listOfUsersByPagination(AdminUserBO adminUserBO) throws MySalesException {
		LOGGER.entry();
		List<AdminUserBO> adminUserBOList = new ArrayList<>();
		try {
			List<User> userVOList = new ArrayList<>();
			User userVO = new User();
			userVO.setRecordIndex(adminUserBO.getRecordIndex());
			userVO.setMaxRecord(adminUserBO.getMaxRecord());
			Company comp = new Company();
			comp.setCompanyId(adminUserBO.getCompanyId());
			userVO.setCompany(comp);
			userVOList = adminDAO.listOfUsersByPagination(userVO);

			if (null != userVOList && !userVOList.isEmpty() && 0 < userVOList.size()) {
				int sNo = adminUserBO.getRecordIndex() + 1;
				for (User user : userVOList) {
					AdminUserBO adminBO = new AdminUserBO();

					adminBO.setId(user.getId());
					adminBO.setName(user.getName());
					adminBO.setMobileNo(user.getMobileNo());
					adminBO.setEmailAddress(user.getEmailAddress());
					adminBO.setsNo(sNo);
					++sNo;
					adminUserBOList.add(adminBO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfUsersByPagination has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfUsersByPagination has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return adminUserBOList;
	}

	@Override
	public long countOfUsersBySearch(com.scube.crm.bo.AdminUserBO adminUserBO) throws MySalesException {
		LOGGER.entry();
		try {
			User userVO = new User();
			long countOfUsers = 0;

			if (null != adminUserBO.getName()) {
				userVO.setName(adminUserBO.getName());
			}
			countOfUsers = adminDAO.countOfUsersBySearch(userVO);
			if (0 < countOfUsers) {
				return countOfUsers;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfUsersBySearchPagination has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfUsersBySearchPagination has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return 0;
	}

	@Override
	public List<AdminUserBO> listOfUsersBySearchPagination(AdminUserBO adminUserBO) throws MySalesException {
		LOGGER.entry();
		List<AdminUserBO> adminUserBOList = new ArrayList<>();
		try {
			List<User> userVOList = new ArrayList<>();
			User userVO = new User();

			if (null != adminUserBO.getName()) {
				userVO.setName(adminUserBO.getName());
				userVO.setRecordIndex(adminUserBO.getRecordIndex());
				userVO.setMaxRecord(adminUserBO.getMaxRecord());
			}

			userVOList = adminDAO.listOfUsersBySearchPagination(userVO);
			if (null != userVOList && !userVOList.isEmpty() && 0 < userVOList.size()) {
				for (User user : userVOList) {
					AdminUserBO adminBO = new AdminUserBO();
					adminBO.setId(user.getId());
					adminBO.setName(user.getName());
					adminUserBOList.add(adminBO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfUsersBySearchPagination has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfUsersBySearchPagination has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return adminUserBOList;
	}

	@Override
	public long contactCounts(AdminLoginBO adminLoginBO) {
		// TODO Auto-generated method stub
		return adminDAO.contactCounts(adminLoginBO);
	}

	@Override
	public long accountCount(AdminLoginBO adminLoginBO) {
		// TODO Auto-generated method stub
		return adminDAO.accountCount(adminLoginBO);
	}

	@Override
	public long opportunityCount(AdminLoginBO adminLoginBO) throws MySalesException {
		return adminDAO.opportunityCount(adminLoginBO);
	}

	@Override
	public boolean checkEmailAddress(String emailAddress) throws MySalesException {
		LOGGER.entry();
		boolean checkEmailId = false;
		try {
			checkEmailId = adminDAO.checkEmailAddress(emailAddress);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("checkEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkEmailId;
	}

	@Override
	public boolean checkMobileNo(String mobileNo) throws MySalesException {
		LOGGER.entry();
		boolean checkmobileNo = false;
		try {
			checkmobileNo = adminDAO.checkMobileNo(mobileNo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkMobileNo has failed:" + ex.getMessage());
			}
			LOGGER.info("checkMobileNo has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkmobileNo;
	}

	@Override
	public boolean deleteProfile(long deletedId) throws MySalesException {
		LOGGER.entry();
		boolean status = false;
		try {
			status = adminDAO.deleteEmployee(deletedId);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deleted User has failed:" + ex.getMessage());
			}
			LOGGER.info("Deleted User has failed:" + ex.getMessage());
		}
		return status;
	}

	@Override
	public List<AdminUserBO> findEmployeesBySkills(long skillsIds) {
		List<AdminUserBO> adminUserBOList = new ArrayList<>();
		List<User> userBoList = adminDAO.findEmployeesBySkills(skillsIds);

		if (null != userBoList && !userBoList.isEmpty()) {
			for (User vo : userBoList) {
				AdminUserBO adminuserBO = new AdminUserBO();
				adminuserBO.setId(vo.getId());
				adminuserBO.setName(vo.getName());
				List<SkillsBO> skillsListBO = new ArrayList<SkillsBO>();
				for (SkillsVO skill : vo.getSkillsListVO()) {
					SkillsBO skillsBO = new SkillsBO();
					skillsBO.setDescriptions(skill.getDescriptions());
					skillsListBO.add(skillsBO);
				}

				adminuserBO.setSkillsListBO(skillsListBO);
				adminUserBOList.add(adminuserBO);
			}
		}
		return adminUserBOList;
	}

	@Override
	public boolean checkPrivilegeName(String privilegename) {
		LOGGER.entry();
		boolean privilegeNameCheck = false;
		try {
			privilegeNameCheck = adminDAO.checkPrivilegeName(privilegename);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("privilegeNameCheck has failed:" + ex.getMessage());
			}
			LOGGER.info("privilegeNameCheck has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeNameCheck;
	}

	@Override
	public List<AdminUserBO> retrieveEmployeeByPagination(AdminUserBO listUserBo) throws MySalesException {
		LOGGER.entry();
		List<AdminUserBO> adminUserBOList = new ArrayList<AdminUserBO>();
		try {
			List<User> userVOList = new ArrayList<User>();

			listUserBo.setisDelete(listUserBo.getisDelete());
			listUserBo.setMaxRecord(listUserBo.getMaxRecord());
			listUserBo.setRecordIndex(listUserBo.getRecordIndex());
			listUserBo.setCompanyId(listUserBo.getCompanyId());

			if (null != listUserBo.getSkillsBO()) {
				SkillsBO skillsVo = new SkillsBO();
				skillsVo.setSkillsId(listUserBo.getSkillsBO().getSkillsId());
				skillsVo.setDescriptions(listUserBo.getSkillsBO().getDescriptions());
				listUserBo.setSkillsBO(skillsVo);
			}
			userVOList = adminDAO.retrieveEmployeeByPagination(listUserBo);

			if (null != userVOList && !userVOList.isEmpty()) {
				int sNo = listUserBo.getRecordIndex();
				for (User vo : userVOList) {
					AdminUserBO adminuserBO = new AdminUserBO();
					adminuserBO.setId(vo.getId());
					List<SkillsBO> skillsListBO = new ArrayList<SkillsBO>();
					for (SkillsVO skill : vo.getSkillsListVO()) {
						SkillsBO skillsBO = new SkillsBO();
						skillsBO.setDescriptions(skill.getDescriptions());
						skillsListBO.add(skillsBO);
					}
					adminuserBO.setSkillsListBO(skillsListBO);
					adminuserBO.setActive(vo.isActive());
					adminuserBO.setsNo(++sNo);
					adminuserBO.setName(vo.getName());
					adminuserBO.setMobileNo(vo.getMobileNo());
					adminuserBO.setPassword(vo.getPassword());
					adminuserBO.setPrimarySkill(vo.getPrimarySkill());
					adminuserBO.setEmailAddress(vo.getEmailAddress());
					adminuserBO.setConfirmPassword(vo.getConfirmpassword());

					if (vo.isActive()) {
						adminuserBO.setStatus("Active");
					} else {
						adminuserBO.setStatus("De-Active");
					}
					adminUserBOList.add(adminuserBO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("retrive User Pagination  has failed:" + ex.getMessage());
			}
			LOGGER.info(" retrive User Pagination User has failed:" + ex.getMessage());
		}
		return adminUserBOList;
	}

	@Override
	public List<InventoryBO> listOfProduct(long companyId) {
		List<InventoryBO> productBOList = new ArrayList<>();
		List<InventoryVO> productVOList = new ArrayList<>();

		productVOList = adminDAO.listOfProduct(companyId);
		if (null != productVOList && !productVOList.isEmpty() && 0 < productVOList.size()) {
			for (InventoryVO productServiceVO : productVOList) {
				InventoryBO productServiceBO = new InventoryBO();
				productServiceBO.setServiceId(productServiceVO.getServiceId());
				productServiceBO.setServiceName(productServiceVO.getServiceName());
				productServiceBO.setServiceSpecification(productServiceVO.getServiceSpecification());
				double fs = productServiceVO.getFees();
				productServiceBO.setFees(fs);
				productServiceBO.setStartDate(productServiceVO.getStartDate());
				productServiceBO.setStartDate(productServiceVO.getEndDate());
				productBOList.add(productServiceBO);
			}
		}

		return productBOList;
	}

	@Override
	public long companyCount(AdminLoginBO adminLoginBO) {
		return adminDAO.companyCount(adminLoginBO);
	}

	@Override
	public long countOfPrivilege(PrivilegesBO privilegesBO) {
		// TODO Auto-generated method stub
		long countOfPrivilege = 0;
		try {
			PrivilegesVO privilegesVO = new PrivilegesVO();
			privilegesVO.setDeleted(false);
			privilegesVO.setPrivilegesName(privilegesBO.getPrivilegename());
			// privilegesVO.setCompanyId(privilegesBO.getCompanyId());
			countOfPrivilege = adminDAO.countOfPrivilege(privilegesVO);
			if (0 != countOfPrivilege) {
				return countOfPrivilege;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfUsersBySearchPagination has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfUsersBySearchPagination has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return 0;
	}


	@Override
	public List<PrivilegesBO> listOfPrivilegeByPagination(PrivilegesBO privilegesBO) {
		// TODO Auto-generated method stub
		PrivilegesVO privilegesVO = new PrivilegesVO();
		List<PrivilegesBO> privilegesBOList = new ArrayList<>();
		try {
			List<PrivilegesVO> privilegesVOList = new ArrayList<>();
			// privilegesVO.setCompanyId(privilegesBO.getCompanyId());
			privilegesVO.setRecordIndex(privilegesBO.getRecordIndex());
			privilegesVO.setMaxRecord(privilegesBO.getMaxRecord());
			privilegesVO.setPrivilegesName(privilegesBO.getPrivilegename());

			privilegesVOList = adminDAO.listOfPrivilegeByPagination(privilegesVO);

			if (null != privilegesVOList && !privilegesVOList.isEmpty()) {

				int sno = privilegesBO.getRecordIndex();
				for (PrivilegesVO vo : privilegesVOList) {
					PrivilegesBO bo = new PrivilegesBO();
					bo.setId(vo.getPrivilegeId());
					bo.setsNo(++sno);
					bo.setPrivilegename(vo.getPrivilegesName());
					privilegesBOList.add(bo);

				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("retrive User Pagination  has failed:" + ex.getMessage());
			}
			LOGGER.info(" retrive User Pagination User has failed:" + ex.getMessage());
		}
		return privilegesBOList;
	}

	/*
	 * @Override public long roleCount(RoleBO rolebo) throws MySalesException { //
	 * TODO Auto-generated method stub long count = 0; try { RolesVO roleVo = new
	 * RolesVO(); roleVo.setRoleName(rolebo.getRoleName()); if(null !=
	 * rolebo.getRoleName() && !rolebo.getRoleName().isEmpty()) {
	 * roleVo.setRoleName(rolebo.getRoleName()); } count =
	 * roleTypeDAO.roleCount(roleVo); }catch (Exception ex) { ex.printStackTrace();
	 * } return count; }
	 * 
	 * 
	 * @Override public List<RoleBO> getRoleList(RoleBO roleBo) throws
	 * MySalesException { // TODO Auto-generated method stub List<RoleBO> roleboList
	 * = new ArrayList<>();
	 * 
	 * try { List<RolesVO> rolevoList = new ArrayList<>(); RolesVO rolevo = new
	 * RolesVO(); rolevo.setRoleName(roleBo.getRoleName()); rolevo.setActive(true);
	 * rolevo.setMaxRecord(roleBo.getMaxRecord());
	 * rolevo.setRecordIndex(roleBo.getRecordIndex()); rolevoList =
	 * roleTypeDAO.getRoleList(rolevo);
	 * 
	 * if(null != rolevoList && rolevoList.size() > 0 && !rolevoList.isEmpty()) {
	 * int sNo = roleBo.getRecordIndex(); for(RolesVO roleVo:rolevoList) { RoleBO
	 * rolebo = new RoleBO(); rolebo.setsNo(++sNo);
	 * rolebo.setRoleId(roleVo.getRoleId());
	 * rolebo.setRoleName(roleVo.getRoleName()); rolebo.setActive(true);
	 * roleboList.add(rolebo); } }
	 * 
	 * }catch(Exception e) { e.printStackTrace(); } return roleboList; }
	 */

	@Override
	public long countOfprivilege(long companyId) {
		LOGGER.entry();
		try {
			long countOfprivilege = 0;
			PrivilegesVO privilegesVO = new PrivilegesVO();

			privilegesVO.setDeleted(false);
			countOfprivilege = adminDAO.countOfprivilegesVO(privilegesVO);
			if (0 < countOfprivilege) {
				return countOfprivilege;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("countOfprivilegesVO has failed:" + ex.getMessage());
			}
			LOGGER.info("countOfprivilegesVO has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return 0;
	}

	@Override
	public long countSearchPrivilege(PrivilegesBO privilegeBO) {
		LOGGER.entry();
		long countOfPrivilege = 0;
		try {
			PrivilegesVO privilegesVO = new PrivilegesVO();
			if (null != privilegeBO.getPrivilegename() && !privilegeBO.getPrivilegename().isEmpty()) {
				privilegesVO.setPrivilegesName(privilegeBO.getPrivilegename());
			}

			countOfPrivilege = adminDAO.countOfPrivilegeBySearch(privilegesVO);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search countOfPrivilege has failed: " + ex.getMessage());
			}
			LOGGER.info("Search countOfPrivilege has failed: " + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return countOfPrivilege;
	}

	@Override
	public List<PrivilegesBO> listOfUsersByPagination(PrivilegesBO privilegesBO) {
		LOGGER.entry();
		List<PrivilegesBO> privilegesBOList = new ArrayList<>();
		try {
			// Initialize PrivilegesVO object
			PrivilegesVO privilegesVO = new PrivilegesVO();

			// Set pagination details from PrivilegesBO
			privilegesVO.setRecordIndex(privilegesBO.getRecordIndex());
			privilegesVO.setMaxRecord(privilegesBO.getMaxRecord());
			// privilegesVO.setPrivilegeId(privilegesBO.getPrivilegeId());
			if (null != privilegesBO.getPrivilegename()) {
				privilegesVO.setPrivilegesName(privilegesBO.getPrivilegename());
			}
			// Retrieve paginated list of users from DAO
			List<PrivilegesVO> privilegesVOList = adminDAO.listOfPrivilegeAccess(privilegesVO);

			if (privilegesVOList != null && !privilegesVOList.isEmpty()) {
				int sNo = privilegesBO.getRecordIndex() + 1;
				for (PrivilegesVO vo : privilegesVOList) {
					PrivilegesBO bo = new PrivilegesBO();

					// Map user details to PrivilegesBO
					bo.setPrivilegeId(vo.getPrivilegeId());
					bo.setPrivilegename(vo.getPrivilegesName());
					bo.setsNo(sNo);
					++sNo;

					// Add PrivilegesBO to the list
					privilegesBOList.add(bo);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfUsersByPagination has failed: " + ex.getMessage());
			}
			LOGGER.error("listOfUsersByPagination has failed: " + ex.getMessage(), ex);
		} finally {
			LOGGER.exit();
		}
		return privilegesBOList;
	}

	@Override
	public long countOfPrivilegesBySearch(PrivilegesBO privbo) {
		try {
			PrivilegesVO privilegesVO = new PrivilegesVO();
			long countOfPrivileges = 0;

			if (null != privbo.getPrivilegename()) {
				privilegesVO.setPrivilegesName(privbo.getPrivilegename());
			}
			countOfPrivileges = adminDAO.countOfPrivilegesBySearch(privilegesVO);
			if (0 < countOfPrivileges) {
				return countOfPrivileges;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfUsersBySearchPagination has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfUsersBySearchPagination has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return 0;
	}

	@Override
	public long salesCount(AdminLoginBO adminLoginBO) {
		// TODO Auto-generated method stub		
		return adminDAO.salesCount(adminLoginBO);
	}

}