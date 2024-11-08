package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.AccountsService;
import com.scube.crm.service.AdminService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.Company;
import com.scube.crm.vo.User;

@Controller
public class AccountsController extends ControllerUtils implements Serializable {

	private static final long serialVersionUID = -5857977996611066292L;

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(AccountsController.class);
	@Autowired
	private AccountsService accountsService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AdminService adminService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ACCOUNT_MANAGER','ROLE_ACCOUNT_TEAM')")
	@RequestMapping(value = "/create-account", method = RequestMethod.GET)
	public String createaccount(Model model, HttpServletRequest request) throws MySalesException, MalformedURLException,
			NumberFormatException, FileNotFoundException, SerialException, SQLException {

		AccountsController.LOGGER.entry();
		AdminUserBO adminuserBO = new AdminUserBO();
		List<String> userType = getUserSecurity().getRole();
		long loginId = getUserSecurity().getLoginId();
		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		User user = new User();
		Company company = new Company();
		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			company.setCompanyId(companyId);
			user.setCompany(company);
			adminuserBO.setCompanyId(companyId);
		}
		try {

			if (0 < loginId && userType.contains("ROLE_ADMIN")) {
				List<User> userBOList = accountsService.retrieveUser(user);
				model.addAttribute("userBOList", userBOList);
			}
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				List<AdminUserBO> userBOList = adminService.retrieveUserByPagination(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}
			AccountBO account = new AccountBO();

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
			model.addAttribute("account", account);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add account has failed:" + ex.getMessage());
			}
			LOGGER.info("Add account has failed:" + ex.getMessage());
		}
		AccountsController.LOGGER.exit();
		return "create-account";
	}

	@PreAuthorize("hasAnyRole('ADMIN','ROLE_ACCOUNT_MANAGER','ROLE_ACCOUNT_TEAM')")
	@RequestMapping(value = "/create-account", method = RequestMethod.POST)
	public String createaccount(@ModelAttribute("account") AccountBO accountBO, BindingResult result,
			HttpServletRequest request, Model model, HttpSession session)
			throws MySalesException, MalformedURLException {
		AccountsController.LOGGER.exit();
		long id=getUserSecurity().getLoginId();
		accountBO.setCreatedBy(id);
		AdminUserBO adminUserBO = new AdminUserBO();
		try {

			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				accountBO.setCompanyId(companyId);
			}

			long loginId = getUserSecurity().getLoginId();
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			if (null != accountBO) {
				String adminId;
				long userId1 = 0;
				adminId = accountBO.getAssignedTo().getName();

				if (null != adminId) {
					userId1 = Long.parseLong(adminId);
					adminUserBO.setUserId(userId1);
					accountBO.setAssignedTo(adminUserBO);
				}
			}
			accountBO.setIsDeleted(false);
			accountBO.setCreatedBy(loginId);
			
			/*
			 * if (null != accountBO.getEmail() && !accountBO.getEmail().isEmpty() ) {
			  
			  User user = new User(); Company company = new Company(); if (0 <
			  getUserSecurity().getCompanyId()) { long companyId =
			  getUserSecurity().getCompanyId(); // company based create condition
			  company.setCompanyId(companyId); user.setCompany(company); } List<User>
			  userBOList = accountsService.retrieveUser(user);
			  model.addAttribute("userBOList", userBOList);
			  model.addAttribute("errorMessage", messageSource.getMessage("error.email",
			  null, null)); return "create-account"; 
			 */
			
		/*	if (0< accountBO.getContactNo() && accountsService.findByContactNo(accountBO.getContactNo())) {
				model.addAttribute("errorMessage", messageSource.getMessage("error.mobile", null, null));
				User user = new User();
				Company company = new Company();
				if (0 < getUserSecurity().getCompanyId()) {
					long companyId = getUserSecurity().getCompanyId(); // company based create condition
					company.setCompanyId(companyId);
					user.setCompany(company);
				}
				List<User> userBOList = accountsService.retrieveUser(user);
				model.addAttribute("userBOList", userBOList);
				//model.addAttribute("errorMessage", messageSource.getMessage("error.email", null, null));
				return "create-account";
			}  */
			
			accountBO = accountsService.createAccounts(accountBO);
			
			if (0 < accountBO.getAccountId() && null != request.getParameter("origin")
					&& null != request.getParameter("leadId") && !request.getParameter("origin").isEmpty()
					&& !request.getParameter("leadId").isEmpty()) {

				model.addAttribute("origin", accountBO.getOrigin());
				model.addAttribute("leadId", accountBO.getLeadId());
				return "redirect:/create-opportunity";
			} else if (0 < accountBO.getAccountId()) {
				model.addAttribute("successMessages", "Account has been created successfully");
				return "redirect:/view-accounts";
			} else {
				model.addAttribute("errorMessages", "Faild to create account!");
				return "redirect:/create-account";
			}
			
	
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create account has failed:" + ex.getMessage());
			}
			LOGGER.info("create account has failed:" + ex.getMessage());
			model.addAttribute("errorMessages", "Faild to create account");
		} finally {
			AccountsController.LOGGER.exit();
		}
		return "redirect:/view-accounts";

	}

	@RequestMapping(value = "/view-accounts", method = RequestMethod.GET)
	public String viewAccounts(HttpServletRequest request, Model model, HttpSession session) throws MySalesException {

		AccountsController.LOGGER.entry();
		try {
			String paging = null;
			User user = new User();
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId=getUserSecurity().getCompanyId();
			if (null != request.getParameter("successMessages")) {
				model.addAttribute("successMessages", request.getParameter("successMessages"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}

		
			//User user = new User();
			AccountBO accountBO = new AccountBO();
			Company company = new Company();
			
			if (0 < companyId && !userType.contains("ROLE_ADMIN")) {
				// company based create condition
				company.setCompanyId(companyId);
				user.setCompany(company);
				accountBO.setCompanyId(companyId);
			}
			
			List<AccountBO> userBOList = accountsService.retrieveUser(accountBO);
			if(null!= userBOList) {
				model.addAttribute("userBOList", userBOList);
			}
			
			accountPagination(accountBO, paging, request, model);
			model.addAttribute("searchAccount", new AccountBO());

		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("view account details: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view account details: Exception \t" + e);
			}
		} finally {
			AccountsController.LOGGER.exit();
		}
		return "view-accounts";
	}

	private void accountPagination(AccountBO accountBO, String paging, HttpServletRequest request, Model model)
			throws MySalesException, Exception {
		
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
		count = accountsService.accountCount(accountBO);
		if (0 != count) {
			totalCount = count;
			model.addAttribute("totalCount",totalCount);
		} else {
			model.addAttribute("errorMessage", "Record not Found!");
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		accountBO.setRecordIndex(startingRecordIndex);
		accountBO.setMaxRecord(maxRecord);
		accountBO.setPagination("pagination");
		List<AccountBO> accountList = new ArrayList<AccountBO>();
		accountList = accountsService.viewAccount(accountBO);
		if (null != accountList && !accountList.isEmpty() && accountList.size() > 0) {
			model.addAttribute("accountlist",
					PaginationClass.paginationLimitedRecords(page, accountList, maxRecord, totalCount));
		}
		if (null != accountList && !accountList.isEmpty() && accountList.size() > 0) {
			model.addAttribute("successMessage", "successfully created account");
		} else {
			model.addAttribute("errorMessage", "No Records Found");
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

	@RequestMapping(value = "/search-account", method = RequestMethod.POST)
	public String searchAccounts(@Valid @ModelAttribute("searchAccount") AccountBO accountBO, BindingResult result,
			HttpServletRequest request, Model model, HttpSession session)
			throws MySalesException, SerialException, SQLException {
		User users = new User();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}

		long count = 0;
		long totalaccountCount = 0;
		int page = 1;
		int maxRecord = 10;

		

		String paging = null;
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
			page = Integer.parseInt(paging);
		}

		
		if (null != accountBO.getId()) {
			
			accountBO.setId(accountBO.getId());
		}
		Company company = new Company();
		if (0 < companyId && !userType.contains("ROLE_ADMIN")) {
			accountBO.setCompanyId(companyId);
			company.setCompanyId(companyId);
			users.setCompany(company);
		}
		
		List<User> userBOLists = accountsService.retrieveUser(users);
		model.addAttribute("userBOList", userBOLists);
		
		count = accountsService.searchAccountCount(accountBO);
		
		if (0 != count) {
			totalaccountCount = count;
			model.addAttribute("count", count);
		}
		
		else {
			model.addAttribute("errorMessage", "No Records Found");
			return "view-accounts";
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		accountBO.setRecordIndex(startingRecordIndex);
		accountBO.setMaxRecord(maxRecord);
		accountBO.setPagination("pagination");

		List<AccountBO> accountSearch = new ArrayList<AccountBO>();
		accountSearch = accountsService.searchAccounts(accountBO);
		if (null != accountBO.getId()) {
			
			AdminUserBO user = new AdminUserBO();
			user.setName(accountSearch.get(0).getAssignedTo().getName());
			user.setId(accountSearch.get(0).getAssignedTo().getId());
			accountBO.setAssignedTo(user);
		} else {
			
			AdminUserBO user = new AdminUserBO();
			user.setName(accountSearch.get(0).getAssignedTo().getName());
			user.setId(accountSearch.get(0).getAssignedTo().getId());
			accountBO.setAssignedTo(user);
		}
		model.addAttribute("accountlist", accountSearch);
		User user = new User();
		if (0 < companyId && !userType.contains("ROLE_ADMIN")) {
			company.setCompanyId(companyId);
			user.setCompany(company);
		}
		List<AccountBO> userBOList = accountsService.retrieveUser(accountBO);
		if(null!= userBOList) {
			model.addAttribute("userBOList", userBOList);
		}
		if (null != accountSearch && !accountSearch.isEmpty() && accountSearch.size() > 0) {

			model.addAttribute("accountlist",
					PaginationClass.paginationLimitedRecords(page, accountSearch, maxRecord, totalaccountCount));
		} else {
			model.addAttribute("errorMessage", "No Records Found");
			
			return "redirect:/view-accounts";
		}
		return "view-accounts";

	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/view-account-details")
	public String viewaccountsdetails(Model model, HttpServletRequest request,
			@ModelAttribute("listAccounts") AccountVO accountVO) throws MySalesException {
		AccountsController.LOGGER.entry();
		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				accountVO.setCompanyId(companyId);
			}
			
			String ids = request.getParameter("accountId");
			Integer id = Integer.parseInt(ids);
			accountVO.setAccountId(id);
			
			accountVO = accountsService.retrieveaccountdetails(accountVO);
			model.addAttribute("accountdetails", accountVO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("viewaccountdetails has failed:" + ex.getMessage());

				LOGGER.info("viewaccountdetails has failed:" + ex.getMessage());
			}
		} finally {
			AccountsController.LOGGER.exit();
		}

		return "view-account-details";

	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/edit-account", method = RequestMethod.GET)
	public String editAccounts(Model model, HttpServletRequest request, HttpServletResponse response)
			throws MySalesException {
		AccountsController.LOGGER.entry();
		AccountBO accountBO = new AccountBO();
		AdminUserBO adminuserBO = new AdminUserBO();
		List<String> userType = getUserSecurity().getRole();
		long loginId = getUserSecurity().getLoginId();
		long companyId = getUserSecurity().getCompanyId();
		User user = new User();
		try {
			String ids = request.getParameter("accountId");
			Integer id = Integer.parseInt(ids);
			accountBO.setAccountId(id);
			accountBO = accountsService.editRetrieve(accountBO);
			model.addAttribute("accountBO", accountBO);
			accountBO.setContactId(accountBO.getContactId());
			
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				adminuserBO.setCompanyId(companyId);
			}
			if (0 < loginId && userType.contains("ROLE_ADMIN")) {
				List<User> userBOList = accountsService.retrieveUser(user);
				model.addAttribute("userBOList", userBOList);
			}
			
			
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				List<AdminUserBO> userBOList = adminService.retrieveUserByPagination(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("account retrive  failed:" + e.getMessage());
			}
			LOGGER.info("account retrive  failed:" + e.getMessage());
		} finally {
			AccountsController.LOGGER.exit();
		}
		return "edit-accounts";
	}

	@RequestMapping(value = "/edit-accounts", method = RequestMethod.POST)
	public String editUpdateAccount(@Valid @ModelAttribute("accountBO") AccountBO account, BindingResult result,
			HttpServletRequest request, HttpServletResponse response, Model model) throws MySalesException {
		AccountsController.LOGGER.entry();
		AdminUserBO adminUserBO = new AdminUserBO();
		try {
			if (result.hasErrors()) {
				return "edit-accounts";
			}

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				account.setCompanyId(companyId);
			}

			long loginId = getUserSecurity().getLoginId();
						if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			String adminId;
			long userId1 = 0;
			adminId = account.getAssignedTo().getName();
			if (null != adminId) {
				userId1 = Long.parseLong(adminId);
				adminUserBO.setUserId(userId1);
				account.setAssignedTo(adminUserBO);
			}

			Boolean status;
			account.setCreatedBy(loginId);

			status = accountsService.updateAccount(account);
			if (status = true) {
				model.addAttribute("successMessages", "Account has been updated successfully!.");
				return "redirect:/view-accounts";
			} else {
				model.addAttribute("errorMessage", "Error");

			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit has failed:" + e.getMessage());
			}
			LOGGER.info("Edit has failed:" + e.getMessage());
		} finally {
			AccountsController.LOGGER.exit();
		}
		return "redirect:/view-accounts";
	}

	@RequestMapping(value = "/delete-account", method = RequestMethod.GET)
	public String deleteLeads(HttpServletRequest request, HttpServletResponse response, Model model)
			throws MySalesException {
		AccountsController.LOGGER.entry();
		AccountBO accountBO=new AccountBO();
		Boolean status;
		try {
			
			if (null != request.getParameter("accountId")) {
				String ids = request.getParameter("accountId");
				Integer id = Integer.parseInt(ids);
				accountBO.setAccountId(id);
			}
			
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				accountBO.setCompanyId(companyId);
			}
			status = accountsService.deleteAccount(accountBO);

			if (status = true) {
				model.addAttribute("successMessages", "Account has been deleted successfully!.");
				return "redirect:/view-accounts";
			} else {
				return "redirect:/view-accounts";
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete has failed:" + e.getMessage());
			}
			LOGGER.info("Delete has failed:" + e.getMessage());
		} finally {
			AccountsController.LOGGER.exit();
		}
		return "redirect:/view-accounts";

	}

	@RequestMapping(value = "/check_emails", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkemail(@RequestParam String emails)throws MySalesException {
		LOGGER.entry();
		boolean checkemail = false;
		long companyId=getUserSecurity().getCompanyId();
		try {
			checkemail = accountsService.checkemails(emails,companyId);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkemail;
	}
	
	@RequestMapping(value = "/check_accountcontact", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkaccountContact(@RequestParam String contact)throws MySalesException {
		LOGGER.entry();
		boolean checkaccountContact = false;
		long companyId=getUserSecurity().getCompanyId();
		try {
			checkaccountContact = accountsService.checkContactNumber(contact,companyId);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckContactNumber has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckContactNumber has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkaccountContact;
	}
}
