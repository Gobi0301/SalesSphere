package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.scube.crm.bo.AccessBo;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.CampaignBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.bo.SkillsBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.AdminService;
import com.scube.crm.service.ProductService;
import com.scube.crm.service.RoleTypeService;
import com.scube.crm.service.SkillsService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.utils.UserRoles;
import com.scube.crm.vo.Company;
import com.scube.crm.vo.LoginStatusVO;

@Controller
@Scope("session")
@SessionAttributes("/admin")
public class AdminController extends ControllerUtils implements Serializable {

	private static final long serialVersionUID = -5857977996611066292L;

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@Autowired
	private ProductService productService;

	@Autowired
	private RoleTypeService roletypeservice;

	@Autowired
	SkillsService skillsService;

	OpportunityBO reteriveprofile;
	List<OpportunityBO> profileList;
	List<CampaignBO> pagecampaignlist;
	List<LeadsBO> pageLeadsList;
	List<AdminUserBO> adminEmployeeList;
	List<OpportunityBO> recordList;
	OpportunityBO employer = new OpportunityBO();
	List<PrivilegesBO> listbo = new ArrayList<PrivilegesBO>();
	List<AccessBo> accessBOList = new ArrayList<>();
	List<PrivilegesBO> privilegeList = new ArrayList<>();
	RoleBO roleSearchBOObj = new RoleBO();
	List<RoleBO> roleSearchList = new ArrayList<>();
	PrivilegesBO privilegeSearchBO = new PrivilegesBO();
	List<PrivilegesBO> privilegeSearchList = new ArrayList<>();
	List<InventoryBO> productServiceList = new ArrayList<>();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) throws FileNotFoundException {

		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		    if (!(auth instanceof AnonymousAuthenticationToken))
		    {
		        return "redirect:logout";
		    }
		return "redirect:admin-sign-in";
	}

	@RequestMapping(value = "/admin-sign-in", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) throws FileNotFoundException {
		model.addAttribute("adminLogin", new AdminLoginBO());
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		    if (!(auth instanceof AnonymousAuthenticationToken))
		    {
		        return "redirect:logout";
		    }
		return "admin-sign-in";
	}

	@RequestMapping(value = "/send-mail", method = RequestMethod.GET)
	public String sendMail(Model model, HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {
			long userLoginId = 0;
			boolean mailStatus = false;
			userLoginId = getUserSecurity().getLoginId();

			if (null != request.getParameter("emailAddress")) {
				String jobseekerName = request.getParameter("firstName");
				String emailAddress = request.getParameter("emailAddress");
				String sendId = request.getParameter("opportunityId");
				OpportunityBO employerRegisterBO = new OpportunityBO();
				employerRegisterBO.setFirstName(jobseekerName);
				employerRegisterBO.setId(Long.parseLong(sendId));
				mailStatus = adminService.sendClientMail(employerRegisterBO);
				if (mailStatus) {
					model.addAttribute("successmessage", "Mail has been sent Successfully");
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("sendMail has failed:" + ex.getMessage());
			}
			LOGGER.info("sendMail has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/profile-view";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/create-employees", method = RequestMethod.GET)
	public String usercreate(Model model, HttpServletRequest request) throws MySalesException, MalformedURLException,
			NumberFormatException, FileNotFoundException, SerialException, SQLException {

		long loginId = getUserSecurity().getLoginId();
		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		InventoryBO serviceBO = new InventoryBO();
		AdminLoginBO adminLoginBO = new AdminLoginBO();
		adminLoginBO.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBO);
		AdminUserBO bo = new AdminUserBO();
		SkillsBO slaBO = new SkillsBO();

		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId();
			serviceBO.setCompanyId(companyId);
			bo.setCompanyId(companyId);
			slaBO.setCompanyId(companyId);
		}

		int page = 1;
		final String paging = request.getParameter("page");
		if (null != paging) {
			page = Integer.parseInt(paging);
		}
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if (null != request.getParameter("infoMessage")) {
			model.addAttribute("infoMessage", request.getParameter("infoMessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		if (null != request.getParameter("functionType") && !request.getParameter("functionType").isEmpty()) {
			model.addAttribute("functionType", request.getParameter("functionType"));
		} else {
			model.addAttribute("functionType", "add");
		}
		List<SkillsBO> slaList = skillsService.findAll(slaBO);
		model.addAttribute("slaList", slaList);
		/*
		 * if (slaBO.getDescriptions() == null || slaBO .getDescriptions().isEmpty()) {
		 * result.rejectValue("skillsBO.descriptions", "error.descriptions",
		 * "You must select at least one skill."); }
		 */
		List<InventoryBO> productBOList = new ArrayList<>();
		productBOList = productService.listOfProductByPagination(serviceBO);
		model.addAttribute("productListObj", productBOList);
		model.addAttribute("user", bo);
		return "create-employees";

	}

	public void getListOfProduct(Model model, HttpServletRequest req) {
		productServiceList = productService.listOfProduct();
		if (null != productServiceList && !productServiceList.isEmpty() && 0 < productServiceList.size()) {
			model.addAttribute("productListObj", productServiceList);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/create-employees", method = RequestMethod.POST)
	public String usercreate(@Valid @ModelAttribute("user") AdminUserBO adminBO, BindingResult result,
			HttpServletRequest request, Model model) throws MySalesException {
		LOGGER.entry();
		long userId = 0;
		try {
			long loginId = getUserSecurity().getLoginId();
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}

			model.addAttribute("functionType", "add");
			if (result.hasErrors()) {
				return "create-employees";
			}
			
			if (0 < getUserSecurity().getCompanyId()) {

				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				adminBO.setCompanyId(companyId);
			}

			if (!adminBO.getPassword().equals(adminBO.getConfirmPassword())) {
				result.rejectValue("confirmPassword", "Validate.Password");

				if (0 != userId) {
					model.addAttribute("infoMessage", "password-confirmpassword Verify");
				}
				return "create-employees";
			}
			if (adminService.findByEmail(adminBO.getEmailAddress())) {
				model.addAttribute("user", adminBO);
				model.addAttribute("errorMessage", "This account already exist");
			}
//			if (adminService.findByMobilenoEmployee(adminBO)) {
//				result.rejectValue("errorMessage", "Validate.AvailableMobileNo");
//				model.addAttribute("errorMobileMessage", "This mobileNo is already exist");
//				return "create-employees";
//

			adminBO = adminService.createuser(adminBO);

			if (0 != adminBO.getId()) {
				model.addAttribute("successMessage", messageSource.getMessage("Create-Employee", null, null));
			} else {
				model.addAttribute("errorMessage", adminBO.getErrorMessage());
				adminBO.setErrorMessage(null);
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add user has failed:" + ex.getMessage());
			}
			LOGGER.info("Add user has failed:" + ex.getMessage());
		}
		model.addAttribute("user", adminBO);
		model.addAttribute("functionType", "add");
		return "redirect:/view-employees";
	}

	@RequestMapping(value = "view-employees", method = RequestMethod.GET)
	public String viewEmployees(Model model, HttpServletRequest request, HttpSession session) throws MySalesException {
		LOGGER.entry();
		try {
			String paging = null;
			model.addAttribute("searchEmployees", new AdminUserBO());

			AdminUserBO listUserBo = new AdminUserBO();
			
			if(null!=request.getParameter("searchElement")) {
				String firstName = request.getParameter("searchElement");
				listUserBo.setName(firstName);
				model.addAttribute("searchElement", request.getParameter("searchElement"));
			}
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			/*
			 * long companyId = getUserSecurity().getCompanyId(); // company id get if (0 <
			 * companyId) { listUserBo.setCompanyId(companyId); }
			 */
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			if (0 < loginId && userType.contains("ROLE_COMPANY")) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				listUserBo.setCompanyId(companyId);
				
			}
			employePagination(listUserBo, paging, model, session, request);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View User has failed:" + ex.getMessage());
			}
			LOGGER.info("View User has failed:" + ex.getMessage());
		}
		return "view-employees";
	}

	private void employePagination(AdminUserBO listUserBo, String paging, Model model, HttpSession session,
			HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {

			long count = 0;
			int page = 1;
			int maxRecord = 10;
			long totalUserRecordcount = 0;

			SkillsBO skillsBO = new SkillsBO();
			if (null != paging) {
				page = Integer.parseInt(paging);
			}
			listUserBo.setisDelete(false);
			if (null != listUserBo) {
				count = adminService.getListOfadminUser(listUserBo);
			}
			if (0 < count) {
				totalUserRecordcount = count;
				model.addAttribute("totalUserRecordcount", totalUserRecordcount);
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			listUserBo.setRecordIndex(startingRecordIndex);
			listUserBo.setMaxRecord(maxRecord);
			listUserBo.setPagination("pagination");

			if (null != listUserBo.getSkillsBO() && null != listUserBo.getSkillsBO().getDescriptions()
					&& !listUserBo.getSkillsBO().getDescriptions().isEmpty()) {

				String skillsId = listUserBo.getSkillsBO().getDescriptions();

				Integer id = Integer.parseInt(skillsId);
				skillsBO.setSkillsId(id);
				listUserBo.setSkillsBO(skillsBO);
			}
			List<SkillsBO> skillsListBO = skillsService.findAll(skillsBO);

			model.addAttribute("SkillsListBO", skillsListBO);

			List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
			userBOList = adminService.retrieveEmployeeByPagination(listUserBo);
			if (0 < count) {
				totalUserRecordcount = count;
				model.addAttribute("totalUserRecordcount", totalUserRecordcount);
			}

			if (null != userBOList && !userBOList.isEmpty() && userBOList.size() > 0) {
				model.addAttribute("userBOLists", userBOList);

				model.addAttribute("userBOLists",
						PaginationClass.paginationLimitedRecords(page, userBOList, maxRecord, totalUserRecordcount));
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pagination User has failed:" + ex.getMessage());
			}
			LOGGER.info("Pagination User has failed:" + ex.getMessage());
		}
	}

	private int paginationPageValues(int pageid, int recordPerPage) {

		int pageRecords = 0;
		if (pageid == 1) {
			pageRecords = 0;
		} else {
			pageRecords = (pageid - 1) * recordPerPage + 1;
			pageRecords = pageRecords - 1;

		}
		return pageRecords;
	}

	@RequestMapping(value = "/search-employees", method = RequestMethod.POST)
	public String searchEmployees(@Valid @ModelAttribute("searchEmployees") AdminUserBO listUserBo,
			BindingResult result, HttpServletRequest request, Model model, HttpSession session)
			throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {
			SkillsBO skillsBO = new SkillsBO();
			long loginId = getUserSecurity().getLoginId();
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}

			if (0 < getUserSecurity().getCompanyId()) {

				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				listUserBo.setCompanyId(companyId);
			}

			long count = 0;
			long totalUserRecordcount = 0;
			int page = 1;
			int maxRecord = 0;
			String record = "10";
			if (null != record) {
				maxRecord = Integer.parseInt(record);
			}
			if(null!=listUserBo.getName() && !listUserBo.getName().isEmpty()) {
				model.addAttribute("searchElement", listUserBo.getName());	
			}
			/*
			 * if (null != listUserBo.getSkillsBO() && null !=
			 * listUserBo.getSkillsBO().getDescriptions()
			 * &&!listUserBo.getSkillsBO().getDescriptions().isEmpty()) { String skillsId =
			 * listUserBo.getSkillsBO().getDescriptions(); Integer id =
			 * Integer.parseInt(skillsId); skillsBO.setSkillsId(id); skillsBO =
			 * skillsService.findById(skillsBO); listUserBo.setSkillsBO(skillsBO);
			 * model.addAttribute("searchElement", skillsBO.getDescriptions());
			 * 
			 * }
			 */
			listUserBo.setisDelete(false);
			if (null != listUserBo) {
				count = adminService.getListOfadminUser(listUserBo);
			}
			if (0 != count) {
				totalUserRecordcount = count;
				model.addAttribute("totalUserRecordcount", totalUserRecordcount);
			}
			// Search pagination

			int startingRecordIndex = paginationPageValues(page, maxRecord);
			listUserBo.setRecordIndex(startingRecordIndex);
			listUserBo.setMaxRecord(maxRecord);
			listUserBo.setPagination("pagination");

			 
			  
			/*
			 * List<SkillsBO> skillsList = skillsService.findAll(skillsBO);
			 * model.addAttribute("skillsList",skillsList);
			 */
			

			if (null != listUserBo.getSkillsBO() && null != listUserBo.getSkillsBO().getDescriptions()
					&& !listUserBo.getSkillsBO().getDescriptions().isEmpty()) {

				String skillsId = listUserBo.getSkillsBO().getDescriptions();

				Integer id = Integer.parseInt(skillsId);
				skillsBO.setSkillsId(id);
				listUserBo.setSkillsBO(skillsBO);
			}
			List<SkillsBO> skillsListBO = skillsService.findAll(skillsBO);

			model.addAttribute("SkillsListBO", skillsListBO);
			List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
			userBOList = adminService.retrieveUserByPagination(listUserBo);
			if (null != userBOList && !userBOList.isEmpty() && userBOList.size() > 0) {
				model.addAttribute("userBOLists", userBOList);
				model.addAttribute("userBOLists",
						PaginationClass.paginationLimitedRecords(page, userBOList, maxRecord, totalUserRecordcount));
				model.addAttribute("searchEmployees", listUserBo);
			} else {
				model.addAttribute("errorMessage", "No Items Found");
				return "view-employees";
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search User has failed:" + ex.getMessage());
			}
			LOGGER.info("search User has failed:" + ex.getMessage());
		}
		return "view-employees";

	}

	@RequestMapping(value = "/active-deactive-user", method = RequestMethod.GET)
	public String activedeactiveuser(Model model, HttpServletRequest request)
			throws MySalesException, FileNotFoundException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			String status = request.getParameter("status");
			String statusvalue[] = status.split(",");
			String userstatus = statusvalue[0];
			long userId = Long.valueOf(statusvalue[1]);

			AdminUserBO userBO = new AdminUserBO();
			userBO.setId(userId);
			if (userstatus.equals("Active")) {
				userBO.setActive(false);
			} else {
				userBO.setActive(true);
			}
			boolean useractivestatus = adminService.userStatus(userBO);
			if (useractivestatus) {
				model.addAttribute("successMessage", "Employee user activated successfully");
			} else {
				model.addAttribute("successMessage", "Employee user de-Activated successfully");
			}
		} catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("user status has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("user status has failed:" + ne.getMessage() + ne);
		}
		return "redirect:/view-employees";
	}

	@RequestMapping(value = "/delete-employees", method = RequestMethod.GET)
	public String deleteUser(Model model, HttpServletRequest request) throws MySalesException, FileNotFoundException {
		LOGGER.entry();
		try {
			AdminController.LOGGER.entry();
			long loginId = getUserSecurity().getLoginId();

			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			String id = null;
			long deletedId = 0;
			if (request.getParameter("id") != null) {
				id = request.getParameter("id");
				deletedId = Long.parseLong(id);
			}
			AdminUserBO deleteProfile = new AdminUserBO();
			deleteProfile.setId(deletedId);
			deleteProfile.setisDelete(true);
			boolean status = adminService.deleteProfile(deletedId);

			if (status) {
				model.addAttribute("successMessage", messageSource.getMessage("Delete-Employee", null, null));
				return "redirect:/view-employees";
			}

			AdminController.LOGGER.exit();
			model.addAttribute("functionType", "add");
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deleteUser has failed:" + ex.getMessage());
			}
			LOGGER.info("deleteUser has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-employees";
	}

	public void getListOfSkill(Model model, HttpServletRequest req, HttpServletResponse res) throws MySalesException {
		LOGGER.entry();
		try {
			SkillsBO skillsBO = new SkillsBO();
			List<SkillsBO> skillsListBO = skillsService.findAll(skillsBO);
			if (null != skillsListBO && !skillsListBO.isEmpty() && 0 < skillsListBO.size()) {

				model.addAttribute("SkillsListBO", skillsListBO);

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getListOfAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("getListOfAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
	}

	@RequestMapping(value = "/edit-employees", method = RequestMethod.GET)
	public String edituser(Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response)
			throws Exception {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId= 0;
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			// getListOfSkill(model, request, response);
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			adminLoginBO.setFirstName(getUserSecurity().getName());
			final String id = request.getParameter("id");
			final long userId = Long.parseLong(id);
			InventoryBO serviceBO = new InventoryBO();
			AdminUserBO userBO = new AdminUserBO();
			if (0 < loginId) {
				userBO = adminService.retrieveusers(userId);
			}
			SkillsBO slaBO = new SkillsBO();

			if (0 < getUserSecurity().getCompanyId()) {
				 companyId = getUserSecurity().getCompanyId();

				slaBO.setCompanyId(companyId);
			}

			List<SkillsBO> slaList = skillsService.findAll(slaBO);
			model.addAttribute("SkillsListBO", slaList);
			
			if (0 < loginId && userType.contains("ROLE_ADMIN") ) {
				List<InventoryBO> productBOList = new ArrayList<>();
				productBOList = productService.listOfProductByPagination(serviceBO);
				model.addAttribute("productListObj", productBOList);
			}
			
			if (0 < loginId && !userType.contains("ROLE_ADMIN") ) {
				serviceBO.setCompanyId(companyId);
				List<InventoryBO> productBOList = new ArrayList<>();
				productBOList = productService.listOfProductByPagination(serviceBO);
				model.addAttribute("productListObj", productBOList);
			}
			// For Product Retrieve
			//getListOfProduct(model, request);

			if (null != userBO.getProductServiceBO() && 0 < userBO.getProductServiceBO().getServiceId()) {
				model.addAttribute("serviceId", userBO.getProductServiceBO().getServiceId());
			}
			if (null != userBO.getProductServiceBO() && null != userBO.getProductServiceBO().getServiceName()) {
				model.addAttribute("serviceName", userBO.getProductServiceBO().getServiceName());

			}

			if (0 < userId) {
				session.setAttribute("updateUser", reteriveprofile);
				model.addAttribute("functionType", "edit");
				model.addAttribute("editUser", userBO);
				return "edit-employees";
			}
		} catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("user Edit has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("user Edit has failed:" + ne.getMessage() + ne);
		}
		return "edit-employees";
	}

	private void getListOfProduct(Model model, HttpServletRequest request, long companyId) {
		productServiceList = adminService.listOfProduct(companyId);
		if (null != productServiceList && !productServiceList.isEmpty() && 0 < productServiceList.size()) {
			model.addAttribute("productListObj", productServiceList);
		}
		
	}

	@RequestMapping(value = "/edit-employees", method = RequestMethod.POST)
	public String edit(@Valid @ModelAttribute("updateuser") AdminUserBO adminBO, BindingResult result,
			HttpServletRequest request, Model model) throws MySalesException {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		InventoryBO productBO = new InventoryBO();
		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		try {
			long userId = 0;

			if (0 != getUserSecurity().getLoginId()) {
				userId = (Long) getUserSecurity().getLoginId();

			}
			model.addAttribute("functionType", "edit");

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId();
				adminBO.setCompanyId(companyId);
			}
			if (null != adminBO.getServiceName()) { // product Dropdown

				Long productServiceId = Long.parseLong(adminBO.getServiceName());
				productBO.setServiceId(productServiceId);
				productBO = productService.retriveServiceById(productBO);
				if (null != productBO) {
					adminBO.setProductServiceBO(productBO);
				}
			}
			List<SkillsBO> skillsBoList = new ArrayList<>();
			if (null != adminBO && null != adminBO.getSkillsIds() && adminBO.getSkillsIds().size() > 0) {

				if (null != adminBO) {
					for (Long id : adminBO.getSkillsIds()) {
						SkillsBO skillsBO = new SkillsBO();
						skillsBO.setSkillsId(id);
						skillsBoList.add(skillsBO);
					}
				}
				if (null != skillsBoList && !skillsBoList.isEmpty() && 0 < skillsBoList.size()) {
					adminBO.setSkillsListBO(skillsBoList);
				}
			}

			adminBO = adminService.updateuser(adminBO);
			if (0 != adminBO.getId()) {
				model.addAttribute("successMessage", messageSource.getMessage("Update-Employee", null, null));
			} else {
				model.addAttribute("errorMessages", adminBO.getErrorMessage());
				adminBO.setErrorMessage(null);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit adminuser has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit adminuser has failed:" + ex.getMessage());
		}
		model.addAttribute("functionType", "add");
		return "redirect:/view-employees";

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request) throws MySalesException, MalformedURLException {
		final HttpSession session = request.getSession();
		String emailId = (String) session.getAttribute("lstatusemailId");
		LoginStatusVO loginStatusVO = new LoginStatusVO();
		loginStatusVO.setUserName(emailId);
		adminService.editLoginStatus(loginStatusVO);
		session.invalidate();
		return "redirect:/admin-sign-in";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) throws MySalesException, MalformedURLException {
		return "redirect:/admin-sign-in";
	}

	/*
	 * @RequestMapping(value = "admin-home", method = RequestMethod.GET) public
	 * String adminHome(Model model, HttpServletRequest request, HttpSession
	 * session) throws FileNotFoundException, MySalesException { long loginId = 0;
	 * List<String> userTypes = null; AdminLoginBO adminLoginBO = new
	 * AdminLoginBO(); if (null != getUserSecurity()) { loginId =
	 * getUserSecurity().getLoginId(); userTypes = getUserSecurity().getRole(); } if
	 * (0 < getUserSecurity().getCompanyId()) { long companyId =
	 * getUserSecurity().getCompanyId(); Company company = new Company();
	 * company.setCompanyId(companyId); adminLoginBO.setCompany(company); } if (0 ==
	 * loginId && null == userTypes) { return "redirect:/admin-sign-in"; } long
	 * userId = getUserSecurity().getLoginId(); List<String> userType =
	 * getUserSecurity().getRole(); adminLoginBO.setId(userId);
	 * adminLoginBO.setFirstName(getUserSecurity().getName());
	 * model.addAttribute("userType", adminLoginBO);
	 * 
	 * if (null != userType && userType.contains(UserRoles.ROLE_ADMIN.getRole())) {
	 * adminLoginBO.setUserType(UserRoles.ROLE_ADMIN.getRole());
	 * adminLoginBO.setEmployeeCount(adminService.employeesCount(adminLoginBO));
	 * adminLoginBO.setCampaignCount(adminService.campaignCount(adminLoginBO));
	 * adminLoginBO.setProductCount(adminService.productCount(adminLoginBO));
	 * adminLoginBO.setCustomerCount(adminService.customerCount(adminLoginBO));
	 * adminLoginBO.setLeadsCount(adminService.leadsCount(adminLoginBO));
	 * adminLoginBO.setAccountCount(adminService.accountCount(adminLoginBO));
	 * adminLoginBO.setContactCount(adminService.contactCounts(adminLoginBO));
	 * adminLoginBO.setOpportunityCount(adminService.opportunityCount(adminLoginBO))
	 * ; }
	 * 
	 * if (null != userType && userType.contains(UserRoles.ROLE_COMPANY.getRole()))
	 * { adminLoginBO.setUserType(UserRoles.ROLE_ADMIN.getRole());
	 * adminLoginBO.setEmployeeCount(adminService.employeesCount(adminLoginBO));
	 * adminLoginBO.setCampaignCount(adminService.campaignCount(adminLoginBO));
	 * adminLoginBO.setProductCount(adminService.productCount(adminLoginBO));
	 * adminLoginBO.setCustomerCount(adminService.customerCount(adminLoginBO));
	 * adminLoginBO.setLeadsCount(adminService.leadsCount(adminLoginBO));
	 * adminLoginBO.setAccountCount(adminService.accountCount(adminLoginBO));
	 * adminLoginBO.setContactCount(adminService.contactCounts(adminLoginBO));
	 * adminLoginBO.setOpportunityCount(adminService.opportunityCount(adminLoginBO))
	 * ; } if (null != userType &&
	 * userType.contains(UserRoles.ROLE_LEAD_MANAGER.getRole())) {
	 * adminLoginBO.setUserType(UserRoles.ROLE_ADMIN.getRole());
	 * adminLoginBO.setEmployeeCount(adminService.employeesCount(adminLoginBO));
	 * adminLoginBO.setCampaignCount(adminService.campaignCount(adminLoginBO));
	 * adminLoginBO.setProductCount(adminService.productCount(adminLoginBO));
	 * adminLoginBO.setCustomerCount(adminService.customerCount(adminLoginBO));
	 * adminLoginBO.setLeadsCount(adminService.leadsCount(adminLoginBO));
	 * adminLoginBO.setAccountCount(adminService.accountCount(adminLoginBO));
	 * adminLoginBO.setContactCount(adminService.contactCounts(adminLoginBO));
	 * adminLoginBO.setOpportunityCount(adminService.opportunityCount(adminLoginBO))
	 * ; }
	 * 
	 * if (null != userType &&
	 * (userType.contains(UserRoles.ROLE_CAMPAIGN_MANAGER.getRole()) ||
	 * (userType.contains(UserRoles.ROLE_CAMPAIGN_TEAM.getRole())))) {
	 * 
	 * adminLoginBO.setCampaignCount(adminService.campaignCount(adminLoginBO));
	 * adminLoginBO.setLeadsCount(adminService.leadsCount(adminLoginBO)); }
	 * 
	 * if (null != userType &&
	 * (userType.contains(UserRoles.ROLE_SALES_MANAGER.getRole()) ||
	 * userType.contains(UserRoles.ROLE_SALES_TEAM.getRole()))) {
	 * adminLoginBO.setCampaignCount(adminService.campaignCount(adminLoginBO));
	 * adminLoginBO.setCustomerCount(adminService.customerCount(adminLoginBO));
	 * adminLoginBO.setLeadsCount(adminService.leadsCount(adminLoginBO)); } if (null
	 * != userType && (userType.contains(UserRoles.ROLE_CONTACT_MANAGER.getRole())
	 * || userType.contains(UserRoles.ROLE_CONTACT_TEAM.getRole()))) {
	 * 
	 * adminLoginBO.setContactCount(adminService.contactCounts(adminLoginBO)); }
	 * 
	 * if (null != userType &&
	 * (userType.contains(UserRoles.ROLE_ACCOUNT_MANAGER.getRole()) ||
	 * userType.contains(UserRoles.ROLE_ACCOUNT_MANAGER.getRole()))) {
	 * adminLoginBO.setAccountCount(adminService.accountCount(adminLoginBO)); }
	 * 
	 * if (null != userType &&
	 * (userType.contains(UserRoles.ROLE_OPPORTUNITY_MANAGER.getRole()) ||
	 * userType.contains(UserRoles.ROLE_OPPORTUNITY_TEAM.getRole()))) {
	 * adminLoginBO.setOpportunityCount(adminService.opportunityCount(adminLoginBO))
	 * ; }
	 * 
	 * if (null != adminLoginBO) { model.addAttribute("adminDashboardCount",
	 * adminLoginBO); } return "admin-home"; }
	 */

	@RequestMapping(value = "admin-home", method = RequestMethod.GET)
	public String adminHome(Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException, MySalesException {
		long loginId = 0;
		List<String> userTypes = null;
		AdminLoginBO adminLoginBO = new AdminLoginBO();
		if (null != getUserSecurity()) {
			loginId = getUserSecurity().getLoginId();
			userTypes = getUserSecurity().getRole();
		}
		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId();
			Company company = new Company();
			company.setCompanyId(companyId);
			adminLoginBO.setCompany(company);
			
		}
		if (0 == loginId && null == userTypes) {
			return "redirect:/admin-sign-in";
		}
		long userId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		adminLoginBO.setId(userId);
		adminLoginBO.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBO);
		

		if (null != userType && userType.contains(UserRoles.ROLE_ADMIN.getRole())) {
			adminLoginBO.setUserType(UserRoles.ROLE_ADMIN.getRole());
			Company companynew = new Company();
			//company.setCompanyId(companyId);
			adminLoginBO.setCompany(companynew);
			adminLoginBO.setEmployeeCount(adminService.employeesCount(adminLoginBO));
			adminLoginBO.setCampaignCount(adminService.campaignCount(adminLoginBO));
			adminLoginBO.setProductCount(adminService.productCount(adminLoginBO));
			adminLoginBO.setCustomerCount(adminService.customerCount(adminLoginBO));
			adminLoginBO.setLeadsCount(adminService.leadsCount(adminLoginBO));
			adminLoginBO.setCompanyCount(adminService.companyCount(adminLoginBO));
			adminLoginBO.setAccountCount(adminService.accountCount(adminLoginBO));
			adminLoginBO.setContactCount(adminService.contactCounts(adminLoginBO));
			adminLoginBO.setOpportunityCount(adminService.opportunityCount(adminLoginBO));
			adminLoginBO.setSalesCount(adminService.salesCount(adminLoginBO));
		}

		if (null != userType && userType.contains(UserRoles.ROLE_COMPANY.getRole())) {
			adminLoginBO.setUserType(UserRoles.ROLE_COMPANY.getRole());
			adminLoginBO.setEmployeeCount(adminService.employeesCount(adminLoginBO));
			adminLoginBO.setCampaignCount(adminService.campaignCount(adminLoginBO));
			adminLoginBO.setProductCount(adminService.productCount(adminLoginBO));
			adminLoginBO.setCustomerCount(adminService.customerCount(adminLoginBO));
			adminLoginBO.setLeadsCount(adminService.leadsCount(adminLoginBO));
			adminLoginBO.setCompanyCount(adminService.companyCount(adminLoginBO));
			adminLoginBO.setAccountCount(adminService.accountCount(adminLoginBO));
			adminLoginBO.setContactCount(adminService.contactCounts(adminLoginBO));
			adminLoginBO.setOpportunityCount(adminService.opportunityCount(adminLoginBO));
			adminLoginBO.setSalesCount(adminService.salesCount(adminLoginBO));
		}
		if (null != userType && userType.contains(UserRoles.ROLE_LEAD_MANAGER.getRole())) {
			adminLoginBO.setUserType(UserRoles.ROLE_LEAD_MANAGER.getRole());
			adminLoginBO.setEmployeeCount(adminService.employeesCount(adminLoginBO));
			adminLoginBO.setCampaignCount(adminService.campaignCount(adminLoginBO));
			adminLoginBO.setProductCount(adminService.productCount(adminLoginBO));
			adminLoginBO.setCustomerCount(adminService.customerCount(adminLoginBO));
			adminLoginBO.setLeadsCount(adminService.leadsCount(adminLoginBO));
			adminLoginBO.setCompanyCount(adminService.companyCount(adminLoginBO));
			adminLoginBO.setAccountCount(adminService.accountCount(adminLoginBO));
			adminLoginBO.setContactCount(adminService.contactCounts(adminLoginBO));
			adminLoginBO.setOpportunityCount(adminService.opportunityCount(adminLoginBO));
			adminLoginBO.setSalesCount(adminService.salesCount(adminLoginBO));
		}

		if (null != userType && (userType.contains(UserRoles.ROLE_CAMPAIGN_MANAGER.getRole())
				|| (userType.contains(UserRoles.ROLE_CAMPAIGN_TEAM.getRole())))) {

			adminLoginBO.setCampaignCount(adminService.campaignCount(adminLoginBO));
			adminLoginBO.setLeadsCount(adminService.leadsCount(adminLoginBO));
		}

		if (null != userType && (userType.contains(UserRoles.ROLE_SALES_MANAGER.getRole())
				|| userType.contains(UserRoles.ROLE_SALES_TEAM.getRole()))) {
			adminLoginBO.setCampaignCount(adminService.campaignCount(adminLoginBO));
			adminLoginBO.setCustomerCount(adminService.customerCount(adminLoginBO));
			adminLoginBO.setLeadsCount(adminService.leadsCount(adminLoginBO));
		}
		if (null != userType && (userType.contains(UserRoles.ROLE_CONTACT_MANAGER.getRole())
				|| userType.contains(UserRoles.ROLE_CONTACT_TEAM.getRole()))) {

			adminLoginBO.setContactCount(adminService.contactCounts(adminLoginBO));
		}

		if (null != userType && (userType.contains(UserRoles.ROLE_ACCOUNT_MANAGER.getRole())
				|| userType.contains(UserRoles.ROLE_ACCOUNT_MANAGER.getRole()))) {
			adminLoginBO.setAccountCount(adminService.accountCount(adminLoginBO));
		}

		if (null != userType && (userType.contains(UserRoles.ROLE_OPPORTUNITY_MANAGER.getRole())
				|| userType.contains(UserRoles.ROLE_OPPORTUNITY_TEAM.getRole()))) {
			adminLoginBO.setOpportunityCount(adminService.opportunityCount(adminLoginBO));
		}

		if (null != adminLoginBO) {
			model.addAttribute("adminDashboardCount", adminLoginBO);
		}
		return "admin-home";
	}
	
	@RequestMapping(value = "/create-privileges", method = RequestMethod.GET)
	public String createprivileges(Model model, HttpServletRequest request, HttpServletResponse response)
			throws MySalesException {
		LOGGER.entry();
		try {
			long loginid = getUserSecurity().getLoginId();
			if (0 == loginid) {
				return "redirect:/admin-sign-in";
			}
			if (null != request.getParameter("successmessages")) {
				model.addAttribute("successMessage", request.getParameter("successmessages"));
			}
			/* PrivilegesBO privbo = new PrivilegesBO(); */
			PrivilegesBO privilegesbo = new PrivilegesBO();
			model.addAttribute("privileges", privilegesbo);
			/*
			 * getPrivileges(model, request); model.addAttribute("searchprivileges",
			 * privbo);
			 */
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createprivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("createprivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "create-privileges";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/create-privileges", method = RequestMethod.POST)
	public String createprivileges(Model model, @Valid @ModelAttribute("privileges") PrivilegesBO privilegesbo,
			BindingResult result) throws MySalesException {
		LOGGER.entry();
		try {
			long loginid = getUserSecurity().getLoginId();
			if (0 == loginid) {
				return "redirect:/admin-sign-in";
			}

			if (result.hasErrors()) {
				return "create-privileges";
			}

			privilegesbo = adminService.savePrivileges(privilegesbo);

			if (0 != privilegesbo.getPrivilegeId()) {
				model.addAttribute("successMessage", "Privilege has been created successfully");
				return "redirect:/view-privileges";
			} else {
				model.addAttribute("errorMessage", "Privileges no created");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createprivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("createprivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-privileges";
	}

	/*
	 * @PreAuthorize("hasRole('ADMIN')")
	 * 
	 * @RequestMapping(value = "/view-privileges", method = RequestMethod.GET)
	 * public String getPrivileges(Model model, HttpServletRequest request) throws
	 * MySalesException { LOGGER.entry(); try { long loginid =
	 * getUserSecurity().getLoginId(); if (0 == loginid) { return
	 * "redirect:/admin-sign-in"; } final String paging =
	 * request.getParameter("page"); if (null != paging) { int page =
	 * Integer.parseInt(paging);
	 * 
	 * }
	 * 
	 * List<PrivilegesBO> privilegesbolist = new ArrayList<PrivilegesBO>();
	 * privilegesbolist = adminService.retrivePrivileges(privilegesbolist);
	 * 
	 * model.addAttribute("privilegeslist", privilegesbolist);
	 * model.addAttribute("searchprivileges", new PrivilegesBO()); } catch
	 * (Exception ex) { if (LOGGER.isDebugEnabled()) {
	 * LOGGER.debug("viewPrivileges has failed:" + ex.getMessage()); }
	 * LOGGER.info("viewPrivileges has failed:" + ex.getMessage()); } finally {
	 * LOGGER.exit(); } return "view-privileges"; }
	 */
	
	@PreAuthorize("hasRole('ADMIN',)")
	@RequestMapping(value = "/view-privileges", method = RequestMethod.GET)
	public String getPrivileges(Model model, HttpServletRequest request,HttpSession session ) throws MySalesException {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		String paging = null;
		model.addAttribute("searchPrivileges", new PrivilegesBO());
		PrivilegesBO privilegesBO=new PrivilegesBO();
		 
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		if(null!=request.getParameter("searchPrivilegesName")) {
			String firstName = request.getParameter("searchPrivilegesName");
			privilegesBO.setPrivilegename(firstName);
			model.addAttribute("searchPrivilegesName", request.getParameter("searchPrivilegesName"));
		}
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
		}
		 
		
		// pagination
		model.addAttribute("privilegesBO", privilegesBO);
		
		privilegesPagination(privilegesBO, paging, model, session, request);
		model.addAttribute("searchPrivileges", privilegesBO);
		return "view-privileges";

	}
	
	private void privilegesPagination(PrivilegesBO privilegesBO, String paging, Model model, HttpSession session,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

		LOGGER.entry();
		try {
			long count = 0;
			long totalCount = 0;
			int page = 1;
			int maxRecord = 10;
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}

			if (null != paging) {
				page = Integer.parseInt(paging);
			}
			
			count=adminService.countOfPrivilege(privilegesBO);
			if(0<count)
			{
				totalCount=count;
				
				model.addAttribute("totalCountOfPrivileges", totalCount);
			}else {
				model.addAttribute("errorMessage", "No Record Found!");
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			privilegesBO.setRecordIndex(startingRecordIndex);
			privilegesBO.setMaxRecord(maxRecord);
			privilegesBO.setPagination("pagination");
			List<PrivilegesBO> privilegesbolist = new ArrayList<PrivilegesBO>();
			privilegesbolist = adminService.listOfPrivilegeByPagination( privilegesBO);
			if(null!=privilegesbolist&&!privilegesbolist.isEmpty())
			{
			
			 model.addAttribute("privilegeslists", PaginationClass.paginationLimitedRecords(page, privilegesbolist, maxRecord, 	totalCount));
			}else {
				model.addAttribute("errorMessage", "No Record Found!");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view Privileges has failed:" + ex.getMessage());
			}
			LOGGER.info("view Privileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/search-privileges", method = RequestMethod.POST)
	public String searchPrivileges(@Valid @ModelAttribute("searchprivileges") PrivilegesBO bo, BindingResult results,
			Model model) throws MySalesException {
		LOGGER.entry();
        try {
             long companyId = getUserSecurity().getCompanyId();
             
            PrivilegesBO privbo = new PrivilegesBO();
            
            privbo.setPrivilegename(bo.getPrivilegename());
            privbo.setCompanyId(companyId);
            long loginid = getUserSecurity().getLoginId();
            if (0 == loginid) {
                return "view-privileges";
            }
            long countOfPrivileges=0;
            long totalSearchCount=0;
            int page=1;
            int maxRecord=10;
            String paging = null;
            
            
           countOfPrivileges=adminService.countOfPrivilegesBySearch(privbo);
            if (null !=privbo.getPrivilegename()) {
                model.addAttribute("privilegesName", privbo.getPrivilegename());
            }
            if(null!=privbo.getPrivilegename()&& !privbo.getPrivilegename().isEmpty()) {
                model.addAttribute("searchPrivilegesName", privbo.getPrivilegename()); 
            }
            if(0<countOfPrivileges) {
            totalSearchCount=countOfPrivileges;
            model.addAttribute("totalSearchCount", totalSearchCount);
        }
//            List<PrivilegesBO> bolist = new ArrayList<PrivilegesBO>();
//            listbo = adminService.searchPrivilegename(privbo);
            
             int startingValueOfRole=paginationPageValues(page, maxRecord);
                privbo.setRecordIndex(startingValueOfRole);
                privbo.setMaxRecord(maxRecord);
               // privbo.setPagination("Pagination");)
                
                
                List<PrivilegesBO> privilegesbolist = new ArrayList<PrivilegesBO>();
                privilegesbolist = adminService.listOfPrivilegeByPagination(privbo);
                if(null!=privilegesbolist&&!privilegesbolist.isEmpty()){
                	//model.addAttribute("privilegeslist", privilegesbolist);
                    model.addAttribute("privilegeslists",  PaginationClass.paginationLimitedRecords(page,  privilegesbolist, maxRecord, totalSearchCount));
                    model.addAttribute("searchPrivileges", privbo);
                    PrivilegesBO privilegesbo = new PrivilegesBO();
                    model.addAttribute("privileges", privilegesbo);
                    return "view-privileges";
                }else {
                	PrivilegesBO privilegesbo = new PrivilegesBO();
                    model.addAttribute("privileges", privilegesbo);
                    model.addAttribute("errorMessage", "No Record Found!");
                    return "view-privileges";
                }
                
				/*
				 * if (null != privilegesbolist && privilegesbolist.size() > 0) {
				 * 
				 * 
				 * model.addAttribute("searchprivileges", privbo); PrivilegesBO privilegesbo =
				 * new PrivilegesBO(); model.addAttribute("privileges", privilegesbo); return
				 * "view-privileges"; } else { PrivilegesBO privilegesbo = new PrivilegesBO();
				 * model.addAttribute("privileges", privilegesbo);
				 * model.addAttribute("errorMessage", "no record found"); return
				 * "view-privileges"; }
				 */
        } catch (Exception ex) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("viewPrivileges has failed:" + ex.getMessage());
            }
            LOGGER.info("viewPrivileges has failed:" + ex.getMessage());
        } finally {
            LOGGER.exit();
        }
        return null;
    }


	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/edit-privileges", method = RequestMethod.GET)
	public String editPrivileges(Model model, HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {
			long loginid = getUserSecurity().getLoginId();
			if (0 == loginid) {
				return "redirect:/admin-sign-in";
			}

			if (null != request.getParameter("id")) {
				PrivilegesBO bo = new PrivilegesBO();

				String id = request.getParameter("id");
				long privilegeid = Long.parseLong(id);
				bo.setPrivilegeId(privilegeid);

				bo = adminService.editPrivileges(bo);

				if (null != bo) {
					model.addAttribute("privilegebo", bo);
				}

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editPrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("editPrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-privileges";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/edit-privileges", method = RequestMethod.POST)
	public String updatePrivileges(@ModelAttribute("privilegebo") PrivilegesBO bo, Model model, BindingResult result,
			HttpServletRequest request, BindingResult results) throws MySalesException {
		LOGGER.entry();
		try {
			if (results.hasErrors()) {
				return "edit-privileges";
			}
			long loginid = getUserSecurity().getLoginId();
			if (0 == loginid) {
				return "redirect:/admin-sign-in";
			}

			if (null != bo) {
				bo = adminService.updatePrivilege(bo);

			}

			if (0 != bo.getPrivilegeId()) {
				model.addAttribute("successMessage", messageSource.getMessage("Update-Privileges", null, null));
				PrivilegesBO privilegesbo = new PrivilegesBO();
				model.addAttribute("privileges", privilegesbo);
				return "redirect:/view-privileges";
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("updatePrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("updatePrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:view-privileges";
	}

	@RequestMapping(value = "/delete-privileges", method = RequestMethod.GET)
	public String deletePrivileges(Model model, HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {
			if (null != request.getParameter("id")) {
				String id = request.getParameter("id");
				long privilegeid = Long.parseLong(id);

				PrivilegesBO bo = new PrivilegesBO();
				bo.setPrivilegeId(privilegeid);
				bo.setDeleted(true);
				bo = adminService.deletePrivilege(bo);
				if (null != bo.getResponse()) {
					model.addAttribute("successmessages", messageSource.getMessage("Delete-Privileges", null, null));
					return "redirect:/create-privileges";
				}

				else {
					model.addAttribute("infoMessagemessage", "deleted failed");

				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deletePrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("deletePrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-privileges";
	}

	@RequestMapping(value = "/role-privileges", method = RequestMethod.GET)
	public String asignRolePrivilege(Model model, HttpServletRequest request, HttpSession session)
			throws MySalesException {
		LOGGER.entry();
		try {
			RoleBO rolebo = new RoleBO();
			List<PrivilegesBO> privilegesbolist = new ArrayList<PrivilegesBO>();
			List<RoleBO> rolebolist = new ArrayList<RoleBO>();
			long loginid = getUserSecurity().getLoginId();
			if (0 == loginid) {

				return "redirect:/admin-sign-in";

			}
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}

			final String paging = request.getParameter("page");
			if (null != paging) {
				int page = Integer.parseInt(paging);
			}
			
			if(null!=request.getParameter("searchElement")) {
				String roleName = request.getParameter("searchElement");
				//roleBo.setAccessName(accessName);;
				rolebo.setRoleName(roleName);
				model.addAttribute("searchElement", request.getParameter("searchElement"));
			}
			getPrivilege(model, request);
			getrolePrivileges(model, request);

//			List<RoleBO> roleSearchBO = (List<RoleBO>) session.getAttribute("roleSearch");
//
//			if (null != roleSearchBO && !roleSearchBO.isEmpty() && 0 < roleSearchBO.size()) {
//				model.addAttribute("rolebolists", roleSearchBO);
//				model.addAttribute("searchRoleObj", roleSearchBOObj);
//				model.addAttribute("roletype", rolebo);
//				session.removeAttribute("roleSearch");
//				return "role-privileges";
//			} else {
				rolebolist = roletypeservice.viewrole();
//			}
			/*
			 * if (null != rolebolist && rolebolist.size() > 0) {
			 * model.addAttribute("roleList", rolebolist); model.addAttribute("rolebolists",
			 * rolebolist); }
			 */
			model.addAttribute("searchRoleObj", new RoleBO());
			model.addAttribute("roletype", rolebo);
			rolePrivilegePagination(rolebo,paging,request,model);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("RolePrivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("RolePrivilege has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "role-privileges";
	}

	
	private void rolePrivilegePagination(RoleBO rolebo, String paging,
			  HttpServletRequest request, Model model) { // TODO Auto-generated method stub
			  LOGGER.entry(); try { 
				  long count = 0; 
				  long totalCount = 0; 
				  int page = 1; 
				  int maxRecord = 10;
			  
			  if (null != request.getParameter("successMessage")) {
			  model.addAttribute("successMessage", request.getParameter("successMessage"));
			  } 
			  if (null != request.getParameter("errorMessage")) {
			  model.addAttribute("errorMessage", request.getParameter("errorMessage")); }
			  
			  if (null != paging) { 
				  page = Integer.parseInt(paging); }
			  count =  roletypeservice.roleCount(rolebo);
			  if(0 < count) { 
				  totalCount = count;
			  model.addAttribute("totalRoleCount", totalCount); }
			  else {
			  model.addAttribute("errorMessage", "Record Not Found"); 
			  } 
			  int startingRecordIndex = paginationPageValues(page,maxRecord);
			  rolebo.setRecordIndex(startingRecordIndex); 
			  rolebo.setMaxRecord(maxRecord);
			  rolebo.setPagination("Pagination");
			
			  List<RoleBO> roleList = roletypeservice.getRoleList(rolebo); if(null !=
			  roleList &&!roleList.isEmpty() && roleList.size() > 0) {
			  model.addAttribute("roleboList", roleList); model.addAttribute("roleboList",
			  PaginationClass.paginationLimitedRecords(page, roleList, maxRecord,
			  totalCount)); }
			 
			  }catch(Exception ex) { 
				  if (LOGGER.isDebugEnabled()) {
			  LOGGER.debug("Role-Privilege Pagination has failed:" + ex.getMessage()); }
			  LOGGER.info("Role-privilege Pagination has failed:" + ex.getMessage());
			  }finally { LOGGER.exit(); }
			  
			  }
	private void getrolePrivileges(Model model, HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {
			List<RoleBO> rolebo = new ArrayList<RoleBO>();
			rolebo = roletypeservice.getroleLists(rolebo);
			model.addAttribute("rolebo", rolebo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getrolePrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("getrolePrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
	}

	@RequestMapping(value = "/role-privileges", method = RequestMethod.POST)
	public String assignprivilegeRole(@ModelAttribute("roletype") RoleBO rolebo, Model model,
			HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {
			RoleBO borole = new RoleBO();
			borole.setPrivilegename(rolebo.getPrivilegename());
			borole.setRoleId(Long.parseLong(rolebo.getRoleName()));
			rolebo.setRoleId((Long.parseLong(rolebo.getRoleName())));
			List<PrivilegesBO> privilegebolist = new ArrayList<PrivilegesBO>();
			List<String> privilegenamelist = new ArrayList<String>(Arrays.asList(borole.getPrivilegename().split(",")));
			if (null != privilegenamelist) {
				for (String str : privilegenamelist) {
					PrivilegesBO bo = new PrivilegesBO();
					long privilegeId = Long.parseLong(str);
					bo.setPrivilegeId(privilegeId);
					privilegebolist.add(bo);
				}

			}
			borole = roletypeservice.getrolebyid(borole);
			// borole = roletypeservice.getRoleid(borole);
			privilegebolist = adminService.getprivileges(privilegebolist);
			borole.setPrivilegesbolist(privilegebolist);

			final String paging = request.getParameter("page");
			if (null != paging) {
				int page = Integer.parseInt(paging);
			}

			borole = adminService.createRolePrivileges(borole);
			if (null != borole) {
				model.addAttribute("successMessage", messageSource.getMessage("create-role-privillage", null, null));
				return "redirect:/role-privileges";
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("rolePrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("rolePrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/role-privileges";
	}

	@RequestMapping(value = "/edit-role-privileges", method = RequestMethod.GET)
	public String editRolePrivilege(Model model, HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {

			RoleBO rolesbo = new RoleBO();
			String id = request.getParameter("roleId");
			List<RoleBO> rolebolist = new ArrayList<RoleBO>();
			rolebolist = roletypeservice.viewrole();
			if (null != rolebolist && rolebolist.size() > 0) {

				rolebolist = roletypeservice.viewrole();
				if (null != rolebolist && rolebolist.size() > 0) {

					model.addAttribute("roleList", rolebolist);
				}
				getPrivilege(model, request);
				if (null != request.getParameter("roleId")) {
					long roleid = Long.parseLong(id);
					rolesbo.setRoleId(roleid);
					rolesbo = roletypeservice.getrolebyid(rolesbo);
				}
				model.addAttribute("rolesbo", rolesbo);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editRolePrivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("editRolePrivilege has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-role-privileges";
	}

	@RequestMapping(value = "/edit-role-privileges", method = RequestMethod.POST)
	public String editRolePrivileges(@ModelAttribute("rolebo") RoleBO rolebo, Model model) throws MySalesException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			List<PrivilegesBO> privilegebolist = new ArrayList<PrivilegesBO>();
			// List<String> privilegenamelist = new
			// ArrayList<String>(Arrays.asList(rolebo.getPrivilegename().split(",")));
			if (null != rolebo && null != rolebo.getPrivilegeIds() && rolebo.getPrivilegeIds().size() > 0) {
				for (Long id : rolebo.getPrivilegeIds()) {
					PrivilegesBO bo = new PrivilegesBO();
					bo.setPrivilegeId(id);
					privilegebolist.add(bo);
				}
			}
			rolebo.setPrivilegesbolist(privilegebolist);
			rolebo = adminService.createRolePrivileges(rolebo);
			model.addAttribute("successMessage", "Role Privilege profile has been updated successfully");
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editRolePrivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("editRolePrivilege has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/role-privileges";
	}

	@RequestMapping(value = "/delete-role-privileges", method = RequestMethod.GET)
	public String deleterolePrivilege(Model model, HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {
			RoleBO rolebo = new RoleBO();
			if (null != request.getParameter("roleId")) {
				String id = request.getParameter("roleId");
				long roleid = Long.parseLong(id);
				rolebo.setRoleId(roleid);
			}
			rolebo = adminService.deleteRolePrivilege(rolebo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deleterolePrivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("deleterolePrivilege has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/role-privileges";

	}

	public void getPrivilege(Model model, HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {
			List<PrivilegesBO> listprivilgebo = new ArrayList<PrivilegesBO>();
			listprivilgebo = adminService.retrivePrivileges(listprivilgebo);
			if (null != listprivilgebo) {
				model.addAttribute("listprivilege", listprivilgebo);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getPrivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("getPrivilege has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
	}

	public void getRolePrivilege(Model model, HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {
			List<RoleBO> listrolesbo = new ArrayList<RoleBO>();
			List<RoleBO> rolebolist = new ArrayList<RoleBO>();
			int count = 1;
			rolebolist = roletypeservice.viewrole();
			if (null != rolebolist && rolebolist.size() > 0) {
				rolebolist = adminService.retrieveRoleprivilege(rolebolist);
				if (null != rolebolist) {
					for (RoleBO rolebo : rolebolist) {
						int data = count++;
						RoleBO bo = new RoleBO();
						bo.setRoleId(rolebo.getRoleId());
						RoleBO rolesbo = new RoleBO();
						rolesbo.setPrivilegeId(rolebo.getPrivilegeId());

						bo = roletypeservice.getrolebyid(bo);
						RoleBO role = new RoleBO();
						role.setRoleId(bo.getRoleId());
						role.setRoleName(bo.getRoleName());

						rolesbo = adminService.getPrivilegesbyId(rolesbo);
						role.setPrivilegeId(rolesbo.getPrivilegeId());
						role.setPrivilegename(rolesbo.getPrivilegename());
						role.setsNo(data);
						listrolesbo.add(role);
					}
				}
				model.addAttribute("rolebolist", listrolesbo);
				model.addAttribute("searchroleprivilege", rolebolist);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getRolePrivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("getRolePrivilege has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
	}

	@RequestMapping(value = "create-privilege-access", method = RequestMethod.GET)
	public String createPrivilegeAccess(Model model, HttpServletRequest req, HttpServletResponse res,
			HttpSession session) throws MySalesException {
		LOGGER.entry();
		if (null != req.getParameter("successMessage")) {
			model.addAttribute("successMessage", req.getParameter("successMessage"));
		}
		try {
			PrivilegesBO privBO = new PrivilegesBO();
			getListOfPrivilegs(model, req, res);
			getListOfAccess(model, req, res);
			List<PrivilegesBO> privilegeBOList = (List<PrivilegesBO>) session.getAttribute("privilegeSearchList");
			if (null != privilegeBOList && !privilegeBOList.isEmpty() && 0 < privilegeBOList.size()) {
				model.addAttribute("privilegeBOlist", privilegeBOList);
				model.addAttribute("privilegeSerachObj", privilegeSearchBO);
				model.addAttribute("privilegeBO", privBO);
				session.removeAttribute("privilegeSearchList");
				return "create-privilege-access";
			}
			viewPrivilegeAccess(model, req, res);
			model.addAttribute("privilegeBO", privBO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createPrivilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("createPrivilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "create-privilege-access";
	}

	public void getListOfPrivilegs(Model model, HttpServletRequest req, HttpServletResponse res)
			throws MySalesException {
		LOGGER.entry();
		try {
			privilegeList = adminService.listOfPrivileges();
			if (null != privilegeList && !privilegeList.isEmpty() && 0 < privilegeList.size()) {
				model.addAttribute("privilegeList", privilegeList);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getListOfPrivilegs has failed:" + ex.getMessage());
			}
			LOGGER.info("getListOfPrivilegs has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
	}

	public void getListOfAccess(Model model, HttpServletRequest req, HttpServletResponse res) throws MySalesException {
		LOGGER.entry();
		try {
			accessBOList = adminService.listOfAccess();
			if (null != accessBOList && !accessBOList.isEmpty() && 0 < accessBOList.size()) {

				model.addAttribute("accessBO", accessBOList);

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getListOfAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("getListOfAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
	}

	@RequestMapping(value = "create-privilege-access", method = RequestMethod.POST)
	public String privilegeAccess(@Valid Model model, @ModelAttribute("privilegeBO") PrivilegesBO privlegeBO,
			BindingResult result, HttpServletRequest req, HttpServletResponse res) throws MySalesException {
		LOGGER.entry();
		try {
			if (result.hasErrors()) {
				return "create-privilege-access";
			}
			List<AccessBo> accessBO1List = new ArrayList<>();
			List<String> accessListObj = new ArrayList<>(Arrays.asList(privlegeBO.getAccessName().split(",")));

			List<PrivilegesBO> privilegeList1 = new ArrayList<>();
			if (null != privlegeBO) {

				if (null != accessListObj) {

					for (String accessBo : accessListObj) {
						AccessBo accessBOobj = new AccessBo();
						long accessId = Long.parseLong(accessBo);
						accessBOobj.setAccessId(accessId);
						for (AccessBo accessBO : accessBOList) {
							if (accessBO.getAccessId() == accessBOobj.getAccessId()) {
								accessBO1List.add(accessBO);
								privlegeBO.setAccessBOlist(accessBO1List);
							}

						}

					}

				}
				if (null != privlegeBO) {
					PrivilegesBO privilegeBOobj = new PrivilegesBO();
					Long privilegeId = Long.parseLong(privlegeBO.getPrivilegename());
					privilegeBOobj.setPrivilegeId(privilegeId);

					for (PrivilegesBO privilegesBO : privilegeList) {
						if (privilegesBO.getPrivilegeId() == privilegeBOobj.getPrivilegeId()) {
							privilegeList1.add(privilegesBO);
							privlegeBO.setPrivilegeslis(privilegeList1);

						}

					}

				}
			}

			if (null != privlegeBO) {
				privlegeBO = adminService.privilegeAccess(privlegeBO);
			}

			if (0 < privlegeBO.getPrivilegeId()) {
				System.out.println("Successfully Created");
			}
			if (null != privlegeBO) {
				model.addAttribute("successMessage", "privilege access has been created successfully");
			}
			model.addAttribute("privilegeBO", privlegeBO);
			model.addAttribute("privilegeList", privilegeList);
			model.addAttribute("accessBO", accessBOList);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("privilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("privilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/create-privilege-access";
	}

	@RequestMapping(value = "view-privilege-access", method = RequestMethod.GET)
	public String viewPrivilegeAccess(Model model, HttpServletRequest request, HttpServletResponse response)
			throws MySalesException {
		LOGGER.entry();
		PrivilegesBO privilegesBO = new PrivilegesBO();

		try {

			long totalSearchCount = 0;
			int page = 1;
			int maxRecord = 10;
			String paging = null;

			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
				page = Integer.parseInt(paging);
			}

			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId = getUserSecurity().getCompanyId();

			AdminLoginBO adminLoginBO = new AdminLoginBO();
			adminLoginBO.setId(loginId);
			adminLoginBO.setFirstName(getUserSecurity().getName());

			// Add userType to model
			model.addAttribute("userType", adminLoginBO);

			// Set PrivilegesBO fields based on user role
			if (loginId > 0 && userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				privilegesBO.setPrivilegeId(companyId);
			} else if (loginId > 0) {
				privilegesBO.setCompanyId(companyId);
			}

			// Add success and error messages to model if present in request
			if (request.getParameter("successMessage") != null) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (request.getParameter("errorMessage") != null) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}

			// Handle GST pagination and add to model
			privilegesBOPagination(privilegesBO, paging, model, request);

			// Add a new PrivilegesBO object to the model for search functionality
			model.addAttribute("privilegeSerachObj", new PrivilegesBO());
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view GST has failed: " + ex.getMessage());
			}
			LOGGER.error("view GST has failed: " + ex.getMessage(), ex);
		} finally {
			LOGGER.exit();
		}

		return "view-privilege-access";
	}

	private void privilegesBOPagination(PrivilegesBO privilegesBO, String paging, Model model,
			HttpServletRequest request) {

		LOGGER.entry();
		try {
			long countOfprivilege = 0;
			long privilegeBOlist = 0;
			int page = 1;
			int maxRecord = 10;
			long companyId = 0;
			List<PrivilegesBO> PrivilegesBO = new ArrayList<>();
			if (0 < getUserSecurity().getCompanyId()) {

				companyId = getUserSecurity().getCompanyId(); // company based create condition
			}

			if (null != paging) {
				page = Integer.parseInt(paging);
			}
			countOfprivilege = adminService.countOfprivilege(companyId);
			if (0 < countOfprivilege) {
				privilegeBOlist = countOfprivilege;
				model.addAttribute("privilegeBOlist", privilegeBOlist);

			}
			int startinRecordOfUsers = paginationPageValues(page, maxRecord);
			privilegesBO.setRecordIndex(startinRecordOfUsers);
			privilegesBO.setMaxRecord(maxRecord);
			privilegesBO.setCompanyId(companyId);
			PrivilegesBO = adminService.listOfUsersByPagination(privilegesBO);
			if (null != PrivilegesBO && !PrivilegesBO.isEmpty() && 0 < PrivilegesBO.size()) {
				model.addAttribute("listPrivilegesBO",
						PaginationClass.paginationLimitedRecords(page, PrivilegesBO, maxRecord, countOfprivilege));
				model.addAttribute("listPrivileges", PrivilegesBO);
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


	@RequestMapping(value = "edit-privilege-access", method = RequestMethod.GET)
	public String editPRivilegeAccess(Model model, @RequestParam("privilegeId") long privilegeId,
			HttpServletRequest request, HttpServletResponse response) throws MySalesException {
		LOGGER.entry();
		try {
			PrivilegesBO privilegesBO = new PrivilegesBO();

			getPrivilege(model, request);
			getListOfAccess(model, request, response);
			if (null != privilegesBO) {
				privilegesBO.setPrivilegeId(privilegeId);
				privilegesBO = roletypeservice.getPrivilegebyid(privilegesBO);
				model.addAttribute("editPrivilegeAccessObj", privilegesBO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editPRivilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("editPRivilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-privilege-access";
	}

	@RequestMapping(value = "edit-privilege-access", method = RequestMethod.POST)
	public String updatePrivilegeAccess(@Valid Model model,
			@ModelAttribute("editPrivilegeAccessObj") PrivilegesBO privilegeBO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws MySalesException {
		LOGGER.entry();
		try {
			if (result.hasErrors()) {

				return "edit-privilege-access";
			}

			List<AccessBo> accessBOList2 = new ArrayList<>();
			if (null != privilegeBO && null != privilegeBO.getAccessIds() && privilegeBO.getAccessIds().size() > 0) {

				if (null != privilegeBO) {
					for (Long id : privilegeBO.getAccessIds()) {
						AccessBo accessBO = new AccessBo();
						accessBO.setAccessId(id);
						accessBOList2.add(accessBO);
					}
				}
				if (null != accessBOList2 && !accessBOList2.isEmpty() && 0 < accessBOList2.size()) {
					privilegeBO.setAccessBOlist(accessBOList2);
				}
				privilegeBO = adminService.updatePrivilegeAccess(privilegeBO);
				if (0 < privilegeBO.getPrivilegeId()) {
					model.addAttribute("successMessage", "Privillege Name Update Successfully");

					return "redirect:/create-privilege-access";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("updatePrivilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("updatePrivilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/create-privilege-access";
	}

	@RequestMapping(value = "delete-privilege-access", method = RequestMethod.GET)
	public String deletePrivilegeAccess(Model model, @RequestParam("privilegeId") long privilegeId,
			HttpServletRequest req, HttpServletResponse res) throws MySalesException {
		LOGGER.entry();
		try {
			PrivilegesBO privilegeBO = new PrivilegesBO();

			if (0 < privilegeId) {
				privilegeBO.setPrivilegeId(privilegeId);
			}

			privilegeBO = adminService.deletePrivilegeAccess(privilegeBO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deletePrivilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("deletePrivilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/create-privilege-access";
	}

	@RequestMapping(value = "search-role-Name", method = RequestMethod.POST)
	public String searchByRoleName(@Valid @ModelAttribute("searchRoleObj") RoleBO roleBO, BindingResult result,
			HttpServletRequest req, HttpServletResponse res, Model model, HttpSession session) throws MySalesException {
		LOGGER.entry();
		try {
			
			RoleBO rolebo = new RoleBO();
			rolebo.setRoleName(roleBO.getRoleName());
			

			long count = 0;
			long totalCount = 0;
			int page = 1;
			int maxRecord = 10;
			
			String paging = null;
			
			if (null != req.getParameter("page")) {
				paging = req.getParameter("page");
				page = Integer.parseInt(paging);
			}
			
			if(null != rolebo.getRoleName() && !rolebo.getRoleName().isEmpty()) {
				model.addAttribute("searchElement",rolebo.getRoleName());
			}
			
			count = roletypeservice.roleCount(rolebo);
			
			if(0 != count) {
				totalCount = count;
				model.addAttribute("totalSearchCount", totalCount);
			}
			
			int startingRecordIndex = paginationPageValues(page,maxRecord);
			rolebo.setRecordIndex(startingRecordIndex);
			rolebo.setMaxRecord(maxRecord);
			
			List<RoleBO> getrole = new ArrayList<>();
			getrole = roletypeservice.listOfRoleByPagination(rolebo);
			
			if(null != getrole && getrole.size() > 0 && !getrole.isEmpty()) {
				model.addAttribute("roleboList", getrole);
				model.addAttribute("roleboList", PaginationClass.paginationLimitedRecords(page, getrole,maxRecord,totalCount));
			}else {
				model.addAttribute("errorMessage", "No Records Found");
			}
			
			  List<RoleBO> rolebolist = roletypeservice.viewrole(); if (null != rolebolist
			  && rolebolist.size() > 0) { model.addAttribute("roleList", rolebolist); } if
			  (null != roleBO) { roleSearchBOObj.setRoleName(roleBO.getRoleName()); } if
			  (null != roleSearchBOObj && !roleSearchBOObj.getRoleName().isEmpty()) {
			  List<RoleBO> roleSearch = adminService.searchRoleByName(roleSearchBOObj); if
			  (null != roleSearch && !roleSearch.isEmpty() && 0 < roleSearch.size()) {
			  model.addAttribute("rolebolists",roleSearch);
			  //session.setAttribute("roleSearch", roleSearch);
			  model.addAttribute("searchRoleObj", roleSearchBOObj); } else {
			  model.addAttribute("errorMessage", "No Records Found"); } } else {
			 
			  return "redirect:/role-privileges"; }
			 

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchByRoleName has failed:" + ex.getMessage());
			}
			LOGGER.info("searchByRoleName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		RoleBO rolebo = new RoleBO();
		getPrivilege(model, req);
		getrolePrivileges(model, req);
		model.addAttribute("roletype", rolebo);
		return "role-privileges";
	}

	@RequestMapping(value = "search-privilege-name", method = RequestMethod.POST)
	public String searchByPrivilegeName(@Valid @ModelAttribute("privilegeSerachObj") PrivilegesBO privilegeBO,
			BindingResult result, HttpServletResponse response, HttpServletRequest request, Model model,
			HttpSession session) throws MySalesException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			long companyId = getUserSecurity().getCompanyId();
			List<String> userType = getUserSecurity().getRole();
			List<PrivilegesBO> PrivilegesBO = new ArrayList<>();

			long countOfPrivilege = 0;
			long totalCountOfprivilege = 0;
			int page = 1;
			int maxRecord = 10;
			String paging = null;

			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
				page = Integer.parseInt(paging);
			}

			if (0 < loginId && userType.contains("ROLE_ADMIN")) { // Employee
				privilegeBO.setCompanyId(companyId);
			}
				

				if (null != privilegeBO) {
					countOfPrivilege = adminService.countSearchPrivilege(privilegeBO);

					if (0 < countOfPrivilege) {
						totalCountOfprivilege = countOfPrivilege;
						model.addAttribute("totalCountOfprivilege", totalCountOfprivilege);
					}

					int startinRecordOfUsers = paginationPageValues(page, maxRecord);
					privilegeBO.setRecordIndex(startinRecordOfUsers);
					privilegeBO.setMaxRecord(maxRecord);
					privilegeBO.setCompanyId(companyId);
					PrivilegesBO = adminService.listOfUsersByPagination(privilegeBO);

					if (null != PrivilegesBO && !PrivilegesBO.isEmpty() && 0 < PrivilegesBO.size()) {
						model.addAttribute("listPrivilegesBO", PaginationClass.paginationLimitedRecords(page,
								PrivilegesBO, maxRecord, countOfPrivilege));
						//model.addAttribute("listPrivilegesBO", PrivilegesBO);
					}

					/*
					 * if (null != privilegeBO.getPrivilegename() &&
					 * !privilegeBO.getPrivilegename().isEmpty()) { List<PrivilegesBO>
					 * privilegeSearch = adminService.searchByPrivilegeName(privilegeBO); if (null
					 * != privilegeSearch && !privilegeSearch.isEmpty()) {
					 * model.addAttribute("privilegeBOlist", privilegeSearch);
					 * model.addAttribute("privilegeSerachObj", privilegeBO); } else {
					 * model.addAttribute("errorMessage", "no record found"); }
					 * 
					 * }
					 */

						else {
						return "redirect:/view-privilege-access";
					}
				}
			

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchByPrivilegeName has failed: " + ex.getMessage());
			}
			LOGGER.info("searchByPrivilegeName has failed: " + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		
		return "view-privilege-access";
	}


	@RequestMapping(value = "/check_emailAddress", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkEmailAddress(@RequestParam String emailAddress) throws MySalesException {
		LOGGER.entry();
		boolean emailAddressCheck = false;
		try {
			emailAddressCheck = adminService.checkEmailAddress(emailAddress);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("checkEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return emailAddressCheck;
	}

	@RequestMapping(value = "/check_mobileNo", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkMobileNo(@RequestParam String mobileNo) throws MySalesException {
		LOGGER.entry();
		boolean mobileNoCheck = false;
		try {
			mobileNoCheck = adminService.checkMobileNo(mobileNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mobileNoCheck;
	}

	@RequestMapping(value = "/check_privilege", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkAccessName(@RequestParam String privilegename) throws Exception {
		LOGGER.entry();
		boolean privilegeNameCheck = false;
		try {
			privilegeNameCheck = adminService.checkPrivilegeName(privilegename);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkPrivilegeName has failed:" + ex.getMessage());
			}
			LOGGER.info("checkPrivilegeName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeNameCheck;
	}

}
