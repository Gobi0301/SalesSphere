package com.scube.crm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.BaseBO;
import com.scube.crm.bo.PlotBO;
import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.AdminService;
import com.scube.crm.service.RoleTypeService;
import com.scube.crm.service.UserRoleService;
import com.scube.crm.utils.PaginationClass;

@Controller
public class UserRolesController extends ControllerUtils {
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(UserRolesController.class);
	@Autowired
	private AdminService adminService;

	@Autowired
	private UserRoleService userroleService;
	@Autowired
	private RoleTypeService roleTypeService;

	List<AdminUserBO> adminBOList = new ArrayList<>();
	AdminUserBO adminSearchObj = new AdminUserBO();
	int page = 1;
	long totalCountOfusers = 0;

	/*
	 * @RequestMapping(value="/create-user-roles",method=RequestMethod.GET) public
	 * String createUserRoles(Model model ,HttpServletRequest request,HttpSession
	 * session) throws MySalesException{ LOGGER.entry(); AdminUserBO userrolebo =
	 * new AdminUserBO(); List<AdminUserBO> listUserBo = new
	 * ArrayList<AdminUserBO>(); long loginId = getUserSecurity().getLoginId();
	 * List<String> userType = getUserSecurity().getRole();
	 * 
	 * if (0 < loginId && userType.contains("ROLE_COMPANY")) { long companyId =
	 * getUserSecurity().getCompanyId(); // company based create condition ((BaseBO)
	 * listUserBo).setCompanyId(companyId);
	 * 
	 * }
	 * 
	 * try {
	 * 
	 * String paging=null;
	 * 
	 * if(null!=request.getParameter("page")) { paging=request.getParameter("page");
	 * } if (null != request.getParameter("successmessage")) {
	 * model.addAttribute("successmessage", request.getParameter("successmessage"));
	 * } if (null != request.getParameter("errorMessage")) {
	 * model.addAttribute("errorMessage", request.getParameter("errorMessage")); }
	 * getUser(model, request, null);
	 * 
	 * 
	 * getuserroles(model,request, null);
	 * 
	 * 
	 * List<AdminUserBO> adminBO=(List<AdminUserBO>)
	 * session.getAttribute("listUserBo");
	 * 
	 * if(null!=adminBO&&!adminBO.isEmpty()&&0<adminBO.size()) {
	 * model.addAttribute("listUserBo", adminBO);
	 * model.addAttribute("listUserBo",PaginationClass.paginationLimitedRecords(
	 * page, adminBO, adminSearchObj.getMaxRecord(),totalCountOfusers));
	 * model.addAttribute("searchObj", adminSearchObj);
	 * model.addAttribute("userRoles", userrolebo);
	 * session.removeAttribute("listUserBo");
	 * 
	 * }
	 * 
	 * listUserBo=adminService.retrieveUser(listUserBo);
	 * model.addAttribute("listUserBos", listUserBo);
	 * model.addAttribute("userRoles", userrolebo); model.addAttribute("searchObj",
	 * new AdminUserBO()); userPagination(userrolebo, paging, model, session,
	 * request); }catch (Exception ex) { if (LOGGER.isDebugEnabled()) {
	 * LOGGER.debug("Create UserRoles has failed:" + ex.getMessage()); }
	 * LOGGER.info("Create UserRoles has failed:" + ex.getMessage()); } finally {
	 * LOGGER.exit(); }
	 * 
	 * return "create-user-roles"; }
	 */
	
	@RequestMapping(value="/create-user-roles",method=RequestMethod.GET)
	public String createUserRoles(Model model ,HttpServletRequest request,HttpSession session) throws MySalesException{
		LOGGER.entry();
		AdminUserBO userrolebo = new AdminUserBO();
		List<AdminUserBO> listUserBo = new ArrayList<AdminUserBO>();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		
		 if (0 < loginId && userType.contains("ROLE_COMPANY")) { 
			 long companyId = getUserSecurity().getCompanyId(); // company based create condition ((BaseBO)
			 userrolebo.setCompanyId(companyId);
			 listUserBo.add(userrolebo);
		  }
		
		try {
		
		String paging=null;
		if(null!=request.getParameter("searchElement")) {
			String firstName = request.getParameter("searchElement");
			userrolebo.setName(firstName);
			model.addAttribute("searchElement", request.getParameter("searchElement"));
			
		}
		
		if(null!=request.getParameter("page")) {
			paging=request.getParameter("page");
		}
		if (null != request.getParameter("successmessage")) {
			model.addAttribute("successmessage", request.getParameter("successmessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		getUser(model, request, null);
		
		
		getuserroles(model,request, null);
		

		List<AdminUserBO> adminBO=(List<AdminUserBO>) session.getAttribute("listUserBo");
		
			if(null!=adminBO&&!adminBO.isEmpty()&&0<adminBO.size()) {
				model.addAttribute("listUserBo", adminBO);
				model.addAttribute("listUserBo",PaginationClass.paginationLimitedRecords(page, adminBO, adminSearchObj.getMaxRecord(),totalCountOfusers));
				model.addAttribute("searchObj", adminSearchObj);
				model.addAttribute("userRoles", userrolebo);
				session.removeAttribute("listUserBo");
				
			}
			
		listUserBo=adminService.retrieveEmployeeByPagination(userrolebo );
		model.addAttribute("listUserBos", listUserBo);		
		model.addAttribute("userRoles", userrolebo);
		model.addAttribute("searchObj", userrolebo);
		userPagination(userrolebo, paging, model, session, request);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create UserRoles has failed:" + ex.getMessage());
			}
			LOGGER.info("Create UserRoles has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		
	return "create-user-roles";
	}
	@RequestMapping(value = "/create-user-roles", method = RequestMethod.POST)
	public String createUserRoles(@ModelAttribute("userRoles") AdminUserBO adminUserBO, Model model) throws Exception {
		LOGGER.entry();
		try {
			AdminUserBO userRolesBo = new AdminUserBO();
			String username = adminUserBO.getName();
			long userid = Long.parseLong(username);
			userRolesBo.setUserId(userid);
			List<RoleBO> roleblist = new ArrayList<RoleBO>();
			List<String> rolenamelist = new ArrayList<String>(Arrays.asList(adminUserBO.getRoleName().split(",")));
			if (null != adminUserBO) {
				for (String str : rolenamelist) {
					RoleBO bo = new RoleBO();
					int roleid = Integer.parseInt(str);
					bo.setRoleId(roleid);
					roleblist.add(bo);
				}
			}
			roleblist = roleTypeService.getroleList(roleblist);
			userRolesBo = adminService.retrieveusers(userRolesBo.getUserId());

			userRolesBo.setListrlebo(roleblist);

			userRolesBo = userroleService.createUserRoles(userRolesBo);
			if (userRolesBo.getId() > 0) {
				model.addAttribute("successmessage", "Create UserRole Successfully");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create post UserRoles has failed:" + ex.getMessage());
			}
			LOGGER.info("Create post UserRoles has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "redirect:/create-user-roles";
	}

	@RequestMapping(value = "/edit-user-role", method = RequestMethod.GET)
	public String updateUserRole(Model model, HttpServletRequest request) throws Exception {
		LOGGER.entry();
		try {
			getUser(model, request, null);
			getuserroles(model, request, null);

			String userid = request.getParameter("userId");
			if (null != userid && !userid.isEmpty()) {
				long id = Long.parseLong(userid);
				AdminUserBO bo = new AdminUserBO();
				bo.setId(id);
				bo = adminService.retrieveusers(id);
				model.addAttribute("useredit", bo);
				return "edit-user-role";
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Get UserRoles has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Get  UserRoles has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-user-role";
	}

	@RequestMapping(value = "/edit-user-role", method = RequestMethod.POST)
	public String updateUserRole(@ModelAttribute("useredit") AdminUserBO adminuserBO, Model model) throws Exception {
		LOGGER.entry();
		try {
			AdminUserBO bo = new AdminUserBO();
			bo.setName(adminuserBO.getName());
			bo = adminService.retriveUserByName(bo);

			List<RoleBO> roleblist = new ArrayList<RoleBO>();

			if (null != adminuserBO) {
				for (Long id : adminuserBO.getRoleIdList()) {
					RoleBO rolebo = new RoleBO();
					rolebo.setRoleId(id);
					roleblist.add(rolebo);
				}
			}
			roleblist = roleTypeService.getroleList(roleblist);
			bo.setListrlebo(roleblist);

			bo = userroleService.updateUserRoles(bo);
			if (bo.getId() > 0) {
				model.addAttribute("successmessage", "Update UserRole Successfully");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit post UserRoles has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit post UserRoles has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "redirect:/create-user-roles";

	}

	@RequestMapping(value = "/delete-user-role", method = RequestMethod.GET)
	public String deleteUserRole(Model model, HttpServletRequest request) {
		String userId = request.getParameter("userid");
		if (null != userId) {
			long id = Long.parseLong(userId);
			AdminUserBO userbo = new AdminUserBO();
			userbo.setId(id);
			userroleService.deleteUserRole(userbo);
		}
		return "redirect:/create-user-roles";

	}

	public void getuserroles(Model model, HttpServletRequest request, HttpServletResponse response)
			throws MySalesException {
		List<RoleBO> listbo = new ArrayList<RoleBO>();

		listbo = roleTypeService.viewrole();
		model.addAttribute("listbo", listbo);
	}

	public void getUser(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.entry();
		List<AdminUserBO> listuser = new ArrayList<AdminUserBO>();
		AdminUserBO adminuserBO = new AdminUserBO();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			adminuserBO.setCompanyId(companyId);
		}
		try {

			listuser = adminService.retrieveUser(adminuserBO);
			if (null != listuser && listuser.size() > 0) {
				model.addAttribute("listuser", listuser);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getUser UserRoles has failed:" + ex.getMessage());
			}
			LOGGER.info("getUser UserRoles has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
	}

	@RequestMapping(value = "search-user", method = RequestMethod.POST)
	public String searchByPrivilegeName(@Valid @ModelAttribute("searchObj") AdminUserBO adminBO, BindingResult result,
			HttpServletResponse response, HttpServletRequest request, Model model, HttpSession session)
			throws Exception {
		LOGGER.entry();
		try {
			long countOfUsers = 0;
			int maxRecord = 10;

			AdminUserBO adminUserBO = new AdminUserBO();
			model.addAttribute("userRoles", adminUserBO);

			if (null != adminBO.getName()) {
				adminUserBO.setName(adminBO.getName());
				model.addAttribute("searchElement",adminBO.getName());
			}

			if (null != adminUserBO.getName()) {
				countOfUsers = adminService.countOfUsersBySearch(adminUserBO);
			}

			if (0 < countOfUsers) {
				totalCountOfusers = countOfUsers;
				model.addAttribute("countOfUsers", countOfUsers);
			}

			int startingValueOfUser = paginationPageValues(page, maxRecord);
			adminSearchObj.setRecordIndex(startingValueOfUser);
			adminSearchObj.setMaxRecord(maxRecord);

			/* adminUserBOList=adminService.listOfUsersBySearchPagination(adminUserBO); */

			if (null != adminBO.getName()) {
				adminSearchObj.setName(adminBO.getName());
			}
			List<RoleBO> listbo = new ArrayList<RoleBO>();

			listbo = roleTypeService.viewrole();
			model.addAttribute("listbo", listbo);

			if (null != adminSearchObj.getName() && !adminSearchObj.getName().isEmpty()) {
				adminBOList = adminService.searchUserName(adminSearchObj);
				if (null != adminBOList && !adminBOList.isEmpty() && 0 < adminBOList.size()) {
					session.setAttribute("listUserBo", adminBOList);
					model.addAttribute("searchObj", adminSearchObj);
					model.addAttribute("listUserBo", adminBOList);
					model.addAttribute("listUserBo",
							PaginationClass.paginationLimitedRecords(page, adminBOList, maxRecord, totalCountOfusers));
				} else {
					model.addAttribute("errorMessage", "No Records Found");

				}
			} else {
				return "redirect:/create-user-roles";
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search UserRoles has failed:" + ex.getMessage());
			}
			LOGGER.info("Search UserRoles has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		getUser(model, request, response);
		getuserroles(model, request, response);
		return "create-user-roles";
	}

	private void userPagination(AdminUserBO adminUserBO, String paging, Model model, HttpSession session,
			HttpServletRequest request) throws Exception {
		LOGGER.entry();
		try {
			long countOfUsers = 0;
			long totalCountOfUsers = 0;
			int page = 1;
			int maxRecord = 10;
			long companyId = 0;
			List<String> userType = getUserSecurity().getRole();
			List<AdminUserBO> adminBOList = new ArrayList<>();
			if (0 < getUserSecurity().getCompanyId()&& !userType.contains("ROLE_ADMIN")) {

				companyId = getUserSecurity().getCompanyId(); // company based create condition
			}

			if (null != paging) {
				page = Integer.parseInt(paging);
			}
			countOfUsers = adminService.countOfUsers(companyId);
			if (0 < countOfUsers) {
				totalCountOfUsers = countOfUsers;
				model.addAttribute("totalCountOfUsers", totalCountOfUsers);

			}
			int startinRecordOfUsers = paginationPageValues(page, maxRecord);
			adminUserBO.setRecordIndex(startinRecordOfUsers);
			adminUserBO.setMaxRecord(maxRecord);
			adminUserBO.setCompanyId(companyId);
			adminBOList = adminService.listOfUsersByPagination(adminUserBO);
			if (null != adminBOList && !adminBOList.isEmpty() && 0 < adminBOList.size()) {
				model.addAttribute("listUserBo", adminBOList);
				model.addAttribute("listUserBo",
						PaginationClass.paginationLimitedRecords(page, adminBOList, maxRecord, totalCountOfUsers));
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("userPagination UserRoles has failed:" + ex.getMessage());
			}
			LOGGER.info("userPagination UserRoles has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

	}

	private int paginationPageValues(int page, int maxRecord) {

		int recordsOfPage = 0;
		if (page == 1) {
			recordsOfPage = 0;
		} else {
			recordsOfPage = (page - 1) * maxRecord + 1;
			recordsOfPage = recordsOfPage - 1;
		}
		return recordsOfPage;
	}

}