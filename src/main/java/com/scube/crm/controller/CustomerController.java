package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.ClientBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.AdminService;
import com.scube.crm.service.CampaignService;
import com.scube.crm.service.CustomerService;
import com.scube.crm.service.ProductService;
import com.scube.crm.utils.DateHelper;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.utils.UserRoles;

@Controller
@Scope("session")
@SessionAttributes("/admin")
public class CustomerController extends ControllerUtils implements Serializable {

	ClientBO clientSearch;
	private static final long serialVersionUID = -5857977996611066292L;
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(CustomerController.class);
	@Autowired
	private CustomerService customerService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CampaignService campaignService;
	@Autowired
	ServletContext servletContext;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ProductService productService;

	ClientBO clientBO;
	List<ClientBO> profileList;
	List<AdminUserBO> adminEmployeeList;
	List<ClientBO> recordList;
	List<InventoryBO> productBOList = new ArrayList<>();
	List<ClientBO> clientBOList = new ArrayList<>();

	// Customer - create Part
	@RequestMapping(value = "/create-customers", method = RequestMethod.GET)
	public String createEmployer(HttpServletRequest request, Model model)
			throws SerialException, MySalesException, SQLException, NumberFormatException, FileNotFoundException {
		ClientBO clientBO = new ClientBO();
		AdminLoginBO adminLoginBO = new AdminLoginBO();

		AdminUserBO adminuserBO = new AdminUserBO();

		InventoryBO inventoryBO = new InventoryBO();

		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		// adminLoginBO.setUserType(userType);
		adminLoginBO.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBO);
		
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			inventoryBO.setCompanyId(companyId);
			adminuserBO.setCompanyId(companyId);
			
		}
		/*
		 * if (0 < loginId && !userType.contains("ROLE_ADMIN")) { List<AdminUserBO>
		 * userBOList = new ArrayList<AdminUserBO>(); adminLoginBO.setId(loginId);
		 * clientBO.setLoginBO(adminLoginBO); long companyId =
		 * getUserSecurity().getCompanyId(); adminuserBO.setCompanyId(companyId);
		 * userBOList = adminService.retrieveUser(adminuserBO);
		 * model.addAttribute("userBOList", userBOList); }
		 */
		if (0 < loginId && !userType.contains("ROLE_ADMIN") ) {
			List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
			userBOList = adminService.retrieveUserByPagination(adminuserBO);
			model.addAttribute("userBOList", userBOList);
		}
		/*
		 * if (0 < loginId && !userType.contains("ROLE_ADMIN")) { List<AdminUserBO> user
		 * = new ArrayList<>(); AdminUserBO userBOList =
		 * campaignService.retrieveParticularUser(loginId); user.add(userBOList);
		 * model.addAttribute("userBOList", user); }
		 */
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
			userBOList = adminService.retrieveUser(adminuserBO);
			
			if (null != userBOList && 0 != userBOList.size()) {
				model.addAttribute("userBOList", userBOList);
			}

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId();
				inventoryBO.setCompanyId(companyId);
			}
			
			if (0 < loginId && userType.contains("ROLE_ADMIN")) {
				productBOList = productService.listOfProductName(inventoryBO);
				if (null != productBOList && !productBOList.isEmpty() && 0 < productBOList.size()) {
					model.addAttribute("productList", productBOList);
				}
			}

		}
		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			inventoryBO.setCompanyId(companyId);
		}
		
		if (0 < loginId && userType.contains("ROLE_COMPANY")) {
			productBOList = productService.listOfProductName(inventoryBO);
			if (null != productBOList && !productBOList.isEmpty() && 0 < productBOList.size()) {
				model.addAttribute("productList", productBOList);
			}
		}
		model.addAttribute("type", "add");
		model.addAttribute("updateProfile", new ClientBO());
		model.addAttribute("createProfile", new ClientBO());
		model.addAttribute("searchjobseeker", new ClientBO());
		return "create-customers";
	}

	// Customer - create Part II
	@RequestMapping(value = "/create-customers", method = RequestMethod.POST)
	public String createEmployer(@Valid @ModelAttribute("createProfile") ClientBO profileBO, BindingResult result,
			HttpServletRequest request, Model model) throws MySalesException {
		LOGGER.entry();
		long id=getUserSecurity().getLoginId();
		profileBO.setCreatedBy(id);
		try {

			boolean isValidation = false;

			AdminUserBO userBO = new AdminUserBO();
			long userLoginId = getUserSecurity().getLoginId();
			profileBO.setEmployerId(userLoginId);
			profileBO.setCreatedBy(userLoginId);
			profileBO.setModifiedBy(userLoginId);
			// uservalidation
			if (result.hasErrors()) {
				return "create-customers";
			}
			if (null != profileBO.getUserName()) {
				long adminId = Long.parseLong(profileBO.getUserName());
				profileBO.setAdminId(adminId);
			}
			profileBO.setDelete(false);
			profileBO.setActive(true);
			profileBO.setCreated(DateHelper.beginningOfDay(new Date()));
			profileBO.setModified(DateHelper.beginningOfDay(new Date()));

			// uservalidation
			String userId = profileBO.getName();
			userBO.setName(userId);
			profileBO.setAdminUserBO(userBO);

			List<InventoryBO> productServiceList = new ArrayList<>();
			List<String> productStringList = new ArrayList<String>(
					Arrays.asList(profileBO.getProductServieBO().getServiceName().split(",")));
			for (String string : productStringList) {
				long serviceId = 0;
				InventoryBO productServiceBO = new InventoryBO();
				serviceId = Long.parseLong(string);
				productServiceBO.setServiceId(serviceId);
				productServiceList.add(productServiceBO);
			}

			productServiceList = productService.listOfServiceId(productServiceList);
			if (null != productServiceList && !productServiceList.isEmpty() && 0 < productServiceList.size()) {
				profileBO.setProductServiceList(productServiceList);
			}

		if (result.hasErrors() || isValidation) {
				if (customerService.findEmployerEmail(profileBO.getEmailAddress())) {
				result.rejectValue("emailAddress", "Validate.EmailAddress");
			}

			// check mobile no

			if (customerService.mobileNoVerification(profileBO.getMobileNo())) {
				result.rejectValue("mobileNo", "Validate.AvailableMobileNo");
			}

				ClientBO employerRegister = new ClientBO();
				// paginations(employerRegister, request, model);
				model.addAttribute("type", "add");
				model.addAttribute("updateProfile", new ClientBO());
				model.addAttribute("searchjobseeker", employerRegister);
				// model.addAttribute("createProfile", profileBO);
				return "create-customers";
			}
			if (0 != profileBO.getAdminId()) {
				if (0 < getUserSecurity().getCompanyId()) {

					long companyId = getUserSecurity().getCompanyId(); // company based create condition
					profileBO.setCompanyId(companyId);
				}

				profileBO = customerService.createCustomer(profileBO);
			}
			if (profileBO.getId() > 0) {

				model.addAttribute("successMessage", messageSource.getMessage("Customer.Creation", null, null));
			} else {
				model.addAttribute("errorMessage", messageSource.getMessage("Customer.Creation", null, null));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("Add Customer has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		model.addAttribute("searchjobseeker", new ClientBO());
		return "redirect:/view-customers";
	}

	// Customer - View Part
	@RequestMapping(value = "/view-customers", method = RequestMethod.GET)
	public String viewCustomers(Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException, MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId = getUserSecurity().getCompanyId();

			AdminLoginBO adminLoginBO = new AdminLoginBO();
			String paging = null;
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}
			
			// adminLoginBO.setUserType(userType);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			clientBO = new ClientBO();
			if (null != userType && !userType.contains(UserRoles.ROLE_ADMIN.getRole())) {
				clientBO.setAssignedTo(loginId);
				adminLoginBO.setId(loginId);
				// adminLoginBO.setUserType(userType);
				clientBO.setLoginBO(adminLoginBO);
			}

			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			ClientBO clientBOObj = new ClientBO();
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				clientBOObj.setCompanyId(companyId);
			}

			customerPagination(clientBOObj, paging, model, request);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("View Customer has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		model.addAttribute("searchjobseeker", new ClientBO());
		return "view-customers";

	}

	private void customerPagination(ClientBO customerBO, String paging, Model model, HttpServletRequest request)
			throws MySalesException {
		HttpSession session = request.getSession();
		ClientBO ccontactBO = (ClientBO) session.getAttribute("customerobjectsearch");
		
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId = getUserSecurity().getCompanyId();
		
		long countOfCustomer = 0;
		long totalOfCustomerCount = 0;
		int page = 1;
		int maxRecord = 10;

		if (null != paging) {
			page = Integer.parseInt(paging);
		}
		String num = request.getParameter("number");
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			customerBO.setCompanyId(companyId);
		}
		if (null != ccontactBO) {

			if (null != num) {
				session.invalidate();
				// view pagination
				countOfCustomer = customerService.countOfCustomer(customerBO);
				if (0 < countOfCustomer) {
					totalOfCustomerCount = countOfCustomer;

				}else {
					model.addAttribute("errorMessage", "Record not Found!");
}

				List<ClientBO> customerBOList = new ArrayList<>();

				int startingRecordOfCustomer = paginationPageValues(page, maxRecord);
				customerBO.setRecordIndex(startingRecordOfCustomer);
				customerBO.setMaxRecord(maxRecord);
				customerBOList = customerService.listOfCustomerByPagination(customerBO);
				if (null != customerBOList && !customerBOList.isEmpty() && 0 < customerBOList.size()) {
					model.addAttribute("listClients", customerBOList);
					model.addAttribute("listClients", PaginationClass.paginationLimitedRecords(page, customerBOList,
							maxRecord, totalOfCustomerCount));
				}

			} else {
				// customer
				List<ClientBO> customerBOList = new ArrayList<>();
				countOfCustomer = customerService.countOfCountBySearch(ccontactBO);

				if (0 < countOfCustomer) {
					totalOfCustomerCount = countOfCustomer;
					model.addAttribute("countOfCustomer", countOfCustomer);
				}
				else {
					model.addAttribute("errorMessage", "Record not Found!");
				}
				int startingValueOfCustomer = paginationPageValues(page, maxRecord);
				ccontactBO.setRecordIndex(startingValueOfCustomer);
				ccontactBO.setMaxRecord(maxRecord);

				customerBOList = customerService.retriveCustomer(ccontactBO);
				if (null != customerBOList && !customerBOList.isEmpty() && 0 < customerBOList.size()) {
					model.addAttribute("listClients", customerBOList);
					model.addAttribute("listClients", PaginationClass.paginationLimitedRecords(page, customerBOList,
							maxRecord, totalOfCustomerCount));
					// model.addAttribute("searchjobseeker",clientBO);
				}else {
					model.addAttribute("errorMessage", "Record not Found!");
				}
			}
		} else {

			// view pagination
			countOfCustomer = customerService.countOfCustomer(customerBO);
			if (0 < countOfCustomer) {
				totalOfCustomerCount = countOfCustomer;
				model.addAttribute("countOfCustomer", countOfCustomer);
			}
			else {
				model.addAttribute("errorMessage", "Record not Found!");
			}
			List<ClientBO> customerBOList = new ArrayList<>();

			int startingRecordOfCustomer = paginationPageValues(page, maxRecord);
			customerBO.setRecordIndex(startingRecordOfCustomer);
			customerBO.setMaxRecord(maxRecord);

			customerBOList = customerService.listOfCustomerByPagination(customerBO);
			if (null != customerBOList && !customerBOList.isEmpty() && 0 < customerBOList.size()) {
				model.addAttribute("listClients", customerBOList);
				model.addAttribute("listClients", PaginationClass.paginationLimitedRecords(page, customerBOList,
						maxRecord, totalOfCustomerCount));
			}else {
				model.addAttribute("errorMessage", "Record not Found!");
			}

		}

	}

	// Paging values Part
	private int paginationPageValues(int page, int maxRecord) {
		int recordOfPage = 0;
		if (page == 1) {
			recordOfPage = 0;
		} else {
			recordOfPage = (page - 1) * maxRecord + 1;
			recordOfPage = recordOfPage - 1;
		}
		return recordOfPage;
	}

	// Customer - Search part
	@RequestMapping(value = "/search-client", method = RequestMethod.GET)
	public String searchClient(@ModelAttribute("searchjobseeker") ClientBO registerBO, BindingResult result,
			HttpServletRequest request, Model model) throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		String paging = null;
		long countOfCustomer = 0;
		long totalCountOfCustomer = 0;
		int page = 1;
		int maxRecord = 10;
		try {
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}
			if (null != paging) {
				page = Integer.parseInt(paging);
			}

			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				registerBO.setCompanyId(companyId);
			}
			HttpSession session = request.getSession();
			session.setAttribute("customerobjectsearch", registerBO);

			AdminLoginBO adminLoginBO = new AdminLoginBO();
			long userId = getUserSecurity().getLoginId();
			adminLoginBO.setId(userId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
		

			if (null != registerBO) {
				countOfCustomer = customerService.countOfCountBySearch(registerBO);
			}
			if (0 < countOfCustomer) {
				totalCountOfCustomer = countOfCustomer;
				model.addAttribute("countOfCustomer", countOfCustomer);
			}

			int startingValueOfCustomer = paginationPageValues(page, maxRecord);
			registerBO.setRecordIndex(startingValueOfCustomer);
			registerBO.setMaxRecord(maxRecord);
			List<ClientBO> customerBOList = new ArrayList<>();
			customerBOList = this.customerService.retriveCustomer(registerBO);
			if (null != customerBOList && !customerBOList.isEmpty() && 0 < customerBOList.size()) {
				model.addAttribute("listClients", PaginationClass.paginationLimitedRecords(page, customerBOList,
						maxRecord, totalCountOfCustomer));

			}else {
				model.addAttribute("errorMessage", "NO Records Found");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("Search Customer has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-customers";
	}

	// Customer - Edit Part
	@RequestMapping(value = "/edit-customers", method = RequestMethod.GET)
	public String editClient(HttpServletRequest request, Model model)
			throws SerialException, MySalesException, SQLException, NumberFormatException, FileNotFoundException {
		LOGGER.entry();
		try {
			HttpSession session = request.getSession();
			ClientBO registerBO = new ClientBO();
			final String id = request.getParameter("id");
			final String ids = request.getParameter("id");
			long employerId = 0;
			long cutomerId=0;
			if (null != id) {
				employerId = Long.parseLong(id);
				cutomerId=Long.parseLong(ids);
				registerBO.setId(employerId);
				registerBO.setId(cutomerId);
				
			}
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			session.setAttribute("jobprofileId", employerId);
			registerBO = this.customerService.retriveCustomerById(registerBO);

			model.addAttribute("userId", registerBO.getLoginBO().getId());
			model.addAttribute("userName", registerBO.getUserName());
			model.addAttribute("serviceIdList", registerBO.getServiceIdList());
			AdminUserBO adminuserBO = new AdminUserBO();
			InventoryBO inventoryBO = new InventoryBO();
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				inventoryBO.setCompanyId(companyId);
				adminuserBO.setCompanyId(companyId);
			}

			productBOList = productService.listOfProductName(inventoryBO);
			if (null != productBOList && !productBOList.isEmpty() && 0 < productBOList.size()) {
				model.addAttribute("productList", productBOList);
				model.addAttribute("serviceNameList", registerBO.getProductServiceList());
				
			}

			for (InventoryBO productBO : registerBO.getProductServiceList()) {
				model.addAttribute("serviceId", productBO.getServiceId());
				model.addAttribute("serviceNames", productBO.getServiceName());
			}

			model.addAttribute("id", registerBO.getLoginBO().getId());
			model.addAttribute("updateProfile", registerBO);
			model.addAttribute("update", "update");
			ClientBO employer = new ClientBO();
			model.addAttribute("searchjobseeker", employer);
			clientBOList = this.customerService.retriveCustomer(clientBO);
			model.addAttribute("listClients", clientBOList);
			
			if (0 < loginId && !userType.contains("ROLE_ADMIN") ) {
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
				userBOList = adminService.retrieveUserByPagination(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}
			if (0 < loginId && userType.contains("ROLE_ADMIN")) {
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
				userBOList = adminService.retrieveUser(adminuserBO);
				
				if (null != userBOList && 0 != userBOList.size()) {
					model.addAttribute("userBOList", userBOList);
				}
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Customer has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-customers";
	}

	// Customer - Edit update part
	@RequestMapping(value = "/edit-customers", method = RequestMethod.POST)
	public String jobseekerProfileView(@Valid @ModelAttribute("updateProfile") ClientBO registerBO,
			BindingResult result, HttpServletRequest request, Model model) throws MySalesException {
		LOGGER.entry();
		try {
			HttpSession session = request.getSession();

			long userId = 0;
			userId = getUserSecurity().getLoginId();
			registerBO.setEmployerId(userId);

			if (null == registerBO.getMobileNo()) {
				registerBO.setMobileNo(null);
			}
			

			if (result.hasErrors()) {
				return "edit-customers";
			}

			if (null == request.getParameter("search") && null == request.getParameter("report")) {
				AdminLoginBO loginBO = new AdminLoginBO();
				long id = 0;
				if (null != registerBO && 0< registerBO.getLoginBO().getId()) {
					loginBO.setId(registerBO.getLoginBO().getId());
					id = getUserSecurity().getLoginId();
					registerBO.setModifiedBy(id);

				} else {
					id = getUserSecurity().getLoginId();
					loginBO.setId(id);
					registerBO.setModifiedBy(id);
				}

				registerBO.setLoginBO(loginBO);

				registerBO.setEmployerId(id);

				/*
				 * List<InventoryBO> productServiceList = new ArrayList<>();
				 * 
				 * if (null != registerBO&& null != registerBO.getServiceIdList() ) { for (long
				 * productServiceId : registerBO.getServiceIdList()) { InventoryBO productBO =
				 * new InventoryBO(); productBO.setServiceId(productServiceId);
				 * productServiceList.add(productBO); } }
				 * 
				 * productServiceList = productService.listOfServiceId(productServiceList); if
				 * (null != productServiceList && !productServiceList.isEmpty() && 0 <
				 * productServiceList.size()) {
				 * registerBO.setProductServiceList(productServiceList); }
				 */
				
				List<InventoryBO> productServiceList = new ArrayList<>();
				List<String> productStringList = new ArrayList<String>(
						Arrays.asList(registerBO.getProductServieBO().getServiceName().split(",")));
				for (String string : productStringList) {
					long serviceId = 0;
					InventoryBO productServiceBO = new InventoryBO();
					serviceId = Long.parseLong(string);
					productServiceBO.setServiceId(serviceId);
					productServiceList.add(productServiceBO);
				}

				productServiceList = productService.listOfServiceId(productServiceList);
				if (null != productServiceList && !productServiceList.isEmpty() && 0 < productServiceList.size()) {
					registerBO.setProductServiceList(productServiceList);
				}

				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				registerBO.setCompanyId(companyId);

				registerBO = customerService.updateEmployer(registerBO);
				if (null != registerBO.getResponse() && !registerBO.getResponse().isEmpty()) {
					model.addAttribute("successMessage","Customer Updated Successfully");
					registerBO = new ClientBO();
				} else {
					model.addAttribute("infoMessage", "Updated Failed");
				}
			}

			else {
				if (null != registerBO.getFirstName() && !registerBO.getFirstName().isEmpty()) {
					model.addAttribute("firstName", registerBO.getFirstName());
				}

				if (null != registerBO.getEmailAddress() && !registerBO.getEmailAddress().isEmpty()) {
					model.addAttribute("emailAddress", registerBO.getEmailAddress());
				}
				if (null != registerBO.getMobileNo() && !registerBO.getMobileNo().isEmpty()) {
					model.addAttribute("mobileNo", registerBO.getMobileNo());
				}
				if (null != registerBO.getCompanyName() && !registerBO.getCompanyName().isEmpty()) {
					model.addAttribute("companyName", registerBO.getCompanyName());
				}

				if (null != registerBO.getIndustryType() && !registerBO.getIndustryType().isEmpty()) {
					model.addAttribute("industryType", registerBO.getCompanyName());
				}
			}

			if (null != request.getParameter("report") || null != request.getParameter("updateReport")) {
				long reportId = 0;
				if (null != registerBO.getUserName() && !registerBO.getUserName().isEmpty()) {
					String userInfo[] = registerBO.getUserName().split("-");
					reportId = Long.parseLong(userInfo[1]);
					registerBO.setAdminId(reportId);
				}
				if (null != registerBO.getProcess() && registerBO.getProcess().equalsIgnoreCase("create")
						&& !registerBO.getProcess().isEmpty()) {
					registerBO.setCreatedBy(reportId);
				}
				if (null != registerBO.getProcess() && registerBO.getProcess().equalsIgnoreCase("edit")
						&& !registerBO.getProcess().isEmpty()) {
					registerBO.setModifiedBy(reportId);
				}
				session.setAttribute("reportSearchBO", registerBO);

				AdminUserBO adminuserBO = new AdminUserBO();

				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
				userBOList = adminService.retrieveUser(adminuserBO);
				List<String> userList = new ArrayList<String>();
				if (null != userBOList && 0 != userBOList.size()) {
					for (AdminUserBO userlist : userBOList) {
						userList.add(userlist.getName() + "-" + userlist.getId());
					}

					model.addAttribute("userBOList", userList);
				}

				model.addAttribute("reportSearch", "report");
				model.addAttribute("updateProfile", registerBO);

			} else {
				model.addAttribute("updateProfile", new ClientBO());
			}
			ClientBO profile = new ClientBO();
			model.addAttribute("search", profile);
			int page = 1;
            if (null != request.getParameter("paging")) {
				String paging = request.getParameter("paging");
				page = Integer.valueOf(paging);
			}
			registerBO.setPagination("pagination");
			if (null == registerBO.getMobileNo()) {
				registerBO.setMobileNo(null);
			}
			if (registerBO.getAdminId() == 0) {
				registerBO.setAdminId(userId);

			}
			if (null != registerBO.getCustomersList() && 0 != registerBO.getCustomersList().size()) {
				profileList = registerBO.getCustomersList();
			} else {
				model.addAttribute("infoMessage", "No Record Found");
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit-update Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit-update Customer has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		model.addAttribute("searchjobseeker", new ClientBO());
		return "redirect:/view-customers";
	}

	// Customer - delete part
	@RequestMapping(value = "/delete-customers", method = RequestMethod.GET)
	public String jobseekerDeleteProfile(Model model, HttpServletRequest request)
			throws MySalesException, FileNotFoundException, NumberFormatException, SerialException, SQLException {

		LOGGER.entry();
		try {
			long deletedId = 0;
			if (null != request.getParameter("id")) {
				String id = request.getParameter("id");
				deletedId = Long.parseLong(id);
			}

			ClientBO deleteProfile = new ClientBO();
			deleteProfile.setId(deletedId);
			deleteProfile.setDelete(true);
			deleteProfile = customerService.deleteProfile(deleteProfile);
			if (deleteProfile.getsNo() > 0) {
					model.addAttribute("successMessage", request.getParameter("successMessage"));
					model.addAttribute("successMessage", messageSource.getMessage("Customer.Delete", null, null));
									}
					else  {
					model.addAttribute("errorMessage", request.getParameter("errorMessage"));
				}
			
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Customer has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-customers";
	}

	// Customer tracking status create part II
	@RequestMapping(value = { "/customer-tracking-status" }, method = RequestMethod.POST)
	public String registerBO(@RequestParam String description, String date, String nextAppointmentDate,String timeSlot,String endTimeSlot, long id,
			Model model, HttpServletRequest request) {
		LOGGER.entry();
		try {
			ClientBO clientBO = new ClientBO();

			long loginId = getUserSecurity().getLoginId();
			String status = "Inprocess";
			clientBO.setId(id);
			clientBO.setDescription(description);
			clientBO.setDate(date);
			clientBO.setNextAppointmentDate(nextAppointmentDate);
			clientBO.setTimeSlot(timeSlot);
			clientBO.setEndTimeSlot(endTimeSlot);
			clientBO.setAdminId(loginId);
			clientBO.setStatus(status);
			clientBO = customerService.saveTracking(clientBO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add Customer Tracking has failed:" + ex.getMessage());
			}
			LOGGER.info("Add Customer Tracking has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/customer-tracking-status?id=" + id;
	}

	// Customer tracking status create part
	@RequestMapping(value = "/customer-tracking-status", method = RequestMethod.GET)
	public String viewEmployerStatus(Model model, HttpServletRequest request)
			throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {

			AdminLoginBO adminLoginBO = new AdminLoginBO();
			long loginId = getUserSecurity().getLoginId();
			adminLoginBO.setId(loginId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			long customerId = 0;
			long userId = 0;
			List<ClientBO> registerBOList = new ArrayList<ClientBO>();
			ClientBO registerBO = new ClientBO();

			if (null != getUserSecurity().getLoginId()) {
				userId = (long) getUserSecurity().getLoginId();
				registerBO.setEmployerId(userId);
			}
			if (null != request.getParameter("reports")) {
				model.addAttribute("reports", request.getParameter("reports"));
			}

			if (null == clientBO.getMobileNo()) {
				registerBO.setMobileNo(null);
			}
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				registerBO.setCompanyId(companyId);
			}

			if (null != request.getParameter("id")) {
				final String id = request.getParameter("id");
				customerId = Long.parseLong(id);
				registerBO.setId(customerId);
				//clientBo.setId(customerId);
			}

			clientBOList = customerService.retriveCustomer(registerBO);

			if (null != clientBOList && !clientBOList.isEmpty()) {
				registerBOList = clientBOList;
				registerBO = customerService.retriveCustomerById(registerBO);
				model.addAttribute("viewemployer", registerBO);
				for (ClientBO clientBO : registerBOList) {
					model.addAttribute("clientBOList", clientBO.getCustomerUpdateVOList());
				}

			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add Customer Tracking has failed:" + ex.getMessage());
			}
			LOGGER.info("Add Customer Tracking has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "customer-tracking-status";
	}
	@RequestMapping(value = "/check_email", method = RequestMethod.GET)
	@ResponseBody
	public boolean check(@RequestParam String email)throws MySalesException {
		LOGGER.entry();
		boolean emailAddressCheck = false;
		long id=getUserSecurity().getLoginId();
		try {
			emailAddressCheck = customerService.checkemail(email,id);
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
	

	
	@RequestMapping(value = "/check_contact", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkEmailAddress(@RequestParam String contact)throws MySalesException {
		LOGGER.entry();
		boolean checkContactNumber = false;
		long id=getUserSecurity().getLoginId();
		try {
			checkContactNumber = customerService.checkcontactNumber(contact,id);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkContactNumber has failed:" + ex.getMessage());
			}
			LOGGER.info("checkContactNumber has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkContactNumber;
	}
	@RequestMapping(value = "/check_mobile", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkMobileNumberdress(@RequestParam String mobile)throws MySalesException {
		LOGGER.entry();
		boolean checkMobileNumberdress = false;
		long id=getUserSecurity().getLoginId();
		try {
			checkMobileNumberdress = customerService.checkmobileNumber(mobile,id);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkMobileNumber has failed:" + ex.getMessage());
			}
			LOGGER.info("checkMobileNumber has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkMobileNumberdress;
	}
}