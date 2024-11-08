package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;

import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.CampaignBO;
import com.scube.crm.bo.CompanyBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.bo.PlotBO;
import com.scube.crm.bo.ProjectBO;
import com.scube.crm.bo.TaskManagementBO;
import com.scube.crm.bo.WorkItemBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.AdminService;
import com.scube.crm.service.CampaignService;
import com.scube.crm.service.CompanyService;
import com.scube.crm.service.LeadsService;
import com.scube.crm.service.OpportunityService;
import com.scube.crm.service.ProductService;
import com.scube.crm.service.ProjectService;
import com.scube.crm.service.TaskManagementService;
import com.scube.crm.service.WorkItemService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.utils.UserRoles;

@Controller
@Scope("session")
@SessionAttributes("/admin")
public class LeadsController extends ControllerUtils implements Serializable {

	private static final long serialVersionUID = -5857977996611066292L;

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(LeadsController.class);

	@Autowired
	private LeadsService leadsService;

	@Autowired
	TaskManagementService taskService;

	@Autowired
	WorkItemService workItemService;

	@Autowired
	private CampaignService campaignService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@Autowired
	private ProductService productService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private OpportunityService opportunityService;

	@Autowired
	ProjectService projectService;

	OpportunityBO reteriveprofile;
	List<OpportunityBO> profileList;
	List<CampaignBO> pagecampaignlist;
	List<LeadsBO> pageLeadsList;
	List<AdminUserBO> adminEmployeeList;
	List<OpportunityBO> recordList;
	OpportunityBO employer = new OpportunityBO();
	List<InventoryBO> productServiceList = new ArrayList<>();

	// Leads Create Part I
	@RequestMapping(value = "create-leads", method = RequestMethod.GET)
	public String createLeads(Model model, HttpServletRequest request) throws FileNotFoundException, MySalesException {

		LOGGER.entry();
		LeadsBO leadsBO = new LeadsBO();
		model.addAttribute("leadsBO", leadsBO);
		try {
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			AdminUserBO adminuserBO = new AdminUserBO();
			CampaignBO campaignBO = new CampaignBO();
			InventoryBO serviceBO = new InventoryBO();
			ProjectBO projectBO = new ProjectBO();

			long loginId = getUserSecurity().getLoginId();
			String industryType = getUserSecurity().getIndustryType();
			List<String> userType = getUserSecurity().getRole();
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId();
				serviceBO.setCompanyId(companyId);
				campaignBO.setCompanyId(companyId);
				adminuserBO.setCompanyId(companyId);
				projectBO.setCompanyId(companyId);
			}

			if (0 < loginId && userType.contains("ROLE_ADMIN") || industryType.equalsIgnoreCase("realEstate")) {
				adminLoginBO.setId(loginId);
				leadsBO.setAdminLoginBO(adminLoginBO);
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
				userBOList = adminService.retrieveUser(adminuserBO);

				if (null != userBOList && 0 != userBOList.size()) {
					model.addAttribute("userBOList", userBOList);
				}
			}
			if (0 < loginId && userType.contains("ROLE_ADMIN") || industryType.equalsIgnoreCase("realEstate")) {
				adminLoginBO.setId(loginId);
				leadsBO.setAdminLoginBO(adminLoginBO);
			}
			if (0 < loginId && userType.contains("ROLE_ADMIN") || industryType.equalsIgnoreCase("realEstate")) {
				// List<AdminUserBO> user = new ArrayList<>();
				// AdminUserBO userBOList = campaignService.retrieveParticularUser(loginId);
				// user.add(userBOList);
				// model.addAttribute("userBOList", user);
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
				userBOList = adminService.retrieveUser(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}

			if (0 < loginId && userType.contains("ROLE_COMPANY") || industryType.equalsIgnoreCase("realEstate")) {
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
				userBOList = adminService.retrieveUserByPagination(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}

			if (0 < loginId && userType.contains("ROLE_LEAD_MANAGER")) {
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
				userBOList = adminService.retrieveUserByPagination(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}

			// Drop Down

			if (0 < loginId && userType.contains("ROLE_ADMIN") || industryType.equalsIgnoreCase("realEstate")) {
				adminLoginBO.setId(loginId);
				campaignBO.setAdminLoginBO(adminLoginBO);
				List<CampaignBO> pagecampaignlist = campaignService.listOfCampaigns(campaignBO);
				if (null != pagecampaignlist && 0 < pagecampaignlist.size()) {
					model.addAttribute("listcampaign", pagecampaignlist);
				}
			}
			if (0 < loginId && userType.contains("ROLE_ADMIN") || industryType.equalsIgnoreCase("realEstate")) {
				adminLoginBO.setId(loginId);
				campaignBO.setAdminLoginBO(adminLoginBO);
				List<CampaignBO> pagecampaignlist = campaignService.listOfCampaigns(campaignBO);
				if (null != pagecampaignlist && 0 < pagecampaignlist.size()) {
					model.addAttribute("listcampaign", pagecampaignlist);
				}
			}
			if (0 < loginId && userType.contains("ROLE_COMPANY") || industryType.equalsIgnoreCase("realEstate")) {
				adminLoginBO.setId(loginId);
				campaignBO.setAdminLoginBO(adminLoginBO);
				List<CampaignBO> pagecampaignlist = campaignService.listOfCampaigns(campaignBO);
				if (null != pagecampaignlist && 0 < pagecampaignlist.size()) {
					model.addAttribute("listcampaign", pagecampaignlist);
				}
			}

			if (0 < loginId && userType.contains("ROLE_LEAD_MANAGER")) {
				adminLoginBO.setId(loginId);
				campaignBO.setAdminLoginBO(adminLoginBO);
				List<CampaignBO> pagecampaignlist = campaignService.listOfCampaigns(campaignBO);
				if (null != pagecampaignlist && 0 < pagecampaignlist.size()) {
					model.addAttribute("listcampaign", pagecampaignlist);
				}
			}

			// retrieve products
			if (0 < loginId && userType.contains("ROLE_ADMIN") || industryType.equalsIgnoreCase("realEstate")) {
				adminLoginBO.setId(loginId);
				campaignBO.setAdminLoginBO(adminLoginBO);
				List<InventoryBO> productBOList = new ArrayList<>();
				productBOList = productService.listOfProductByPagination(serviceBO);
				model.addAttribute("productListObj", productBOList);
			}
			// if (industryType.equalsIgnoreCase("realEstate")) {
			if (0 < loginId && userType.contains("ROLE_ADMIN") || industryType.equalsIgnoreCase("realEstate")) {
				List<ProjectBO> projectBOList = new ArrayList<>();
				projectBOList = projectService.selectAllProjects(projectBO);
				model.addAttribute("projectBOList", projectBOList);

			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Create lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "create-leads";
	}

	public void getListOfProduct(Model model, HttpServletRequest req) {
		productServiceList = productService.listOfProduct();
		if (null != productServiceList && !productServiceList.isEmpty() && 0 < productServiceList.size()) {
			model.addAttribute("productListObj", productServiceList);
		}
	}

	// Leads Create Part II
	@RequestMapping(value = "create-leads", method = RequestMethod.POST)
	public String createLeads(@Valid @ModelAttribute("leadsBO") LeadsBO leadsBO, BindingResult result, Model model,
			HttpServletRequest request) throws FileNotFoundException {
		LOGGER.entry();
		try {
			CampaignBO campaignBO = new CampaignBO();
			ProjectBO projectBO = new ProjectBO();
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			if (result.hasErrors()) {
				return "create-leads";
			}
			if (0 < getUserSecurity().getCompanyId()) { // companyId
				long companyId = getUserSecurity().getCompanyId();
				leadsBO.setCompanyId(companyId);
				campaignBO.setCompanyId(companyId);
				projectBO.setCompanyId(companyId);
			}

			if (0 < loginId && userType.contains("ROLE_ADMIN")) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(loginId);
				leadsBO.setAdminLoginBO(adminLoginBO);
			}
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(loginId);
				leadsBO.setAdminLoginBO(adminLoginBO);
			}
			if (0 < loginId && !userType.contains("ROLE_COMPANY")) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(loginId);
				leadsBO.setAdminLoginBO(adminLoginBO);
			}
			long leadsID = leadsService.saveLeads(leadsBO);
			if (0 != leadsID) {
				model.addAttribute("successMessage", messageSource.getMessage("Lead.Creation", null, null));
				return "redirect:/view-leads";
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Add lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "create-leads";
	}

	// Leads View Part
	@RequestMapping(value = "view-leads", method = RequestMethod.GET)
	public String viewLeads(Model model, HttpServletRequest request, HttpSession session) throws Exception {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId = getUserSecurity().getCompanyId();
		try {
			LeadsBO leadsBO = new LeadsBO();
			CampaignBO campaignBO = new CampaignBO();
			String paging = null;
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			List<LeadsBO> pageLeads = new ArrayList<LeadsBO>();
			LeadsBO leadsBOObj = new LeadsBO();
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}
			leadsBO.setUserId(loginId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);

			if (null != request.getParameter("searchElement")) {
				String firstName = request.getParameter("searchElement");
				leadsBOObj.setFirstName(firstName);
				model.addAttribute("searchElement", request.getParameter("searchElement"));
			}
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) { // companyId
				leadsBO.setCompanyId(companyId);
				campaignBO.setCompanyId(companyId);
				leadsBOObj.setCompanyId(companyId);
			}

			if (0 < loginId && userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				adminLoginBO.setId(loginId);
				leadsBO.setAdminLoginBO(adminLoginBO);
				pageLeads = leadsService.getListLeads(leadsBO);
			}
			/*
			 * if (0 < loginId && !userType.contains(UserRoles.ROLE_ADMIN.toString())) {
			 * adminLoginBO.setId(loginId); leadsBO.setAdminLoginBO(adminLoginBO); pageLeads
			 * = leadsService.getListLeads(leadsBO);
			 * 
			 * }
			 */
			model.addAttribute("listLeads", pageLeads);
			model.addAttribute("searchLeads", new LeadsBO());
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
				/*
				 * if(request.getParameter("errorMessage").equals("No Records Found")) {
				 * model.addAttribute("searchLeads", new LeadsBO()); return "view-leads";
				 * 
				 * }
				 */
			}

			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}

			model.addAttribute("leadsBO", leadsBO);

			// campaign search dd

			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				adminLoginBO.setId(loginId);
				campaignBO.setAdminLoginBO(adminLoginBO);
				leadsBO.setAdminLoginBO(adminLoginBO);
				List<LeadsBO> pageleadsBOlist = leadsService.getListLeadsDropDown(leadsBO);
				if (null != pageleadsBOlist && 0 < pageleadsBOlist.size()) {
					model.addAttribute("listcampaign", pageleadsBOlist);
				}
			}
			if (0 < loginId && userType.contains("ROLE_ADMIN")) {
				adminLoginBO.setId(loginId);
				campaignBO.setAdminLoginBO(adminLoginBO);
				leadsBO.setAdminLoginBO(adminLoginBO);
				List<LeadsBO> pageleadsBOlist = leadsService.getListLeadsDropDown(leadsBO);
				if (null != pageleadsBOlist && 0 < pageleadsBOlist.size()) {
					model.addAttribute("listcampaign", pageleadsBOlist);
				}

			}
			leadsPagination(leadsBOObj, paging, model, request);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View lead has failed:" + ex.getMessage());
			}
			LOGGER.info("View lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-leads";

	}

	private void leadsPagination(LeadsBO leadsBO, String paging, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		LeadsBO leadsBOi = (LeadsBO) session.getAttribute("leadsbOLists");
		session.removeAttribute("leadsbOLists");
		long countOfLeads = 0;
		long totalOfLeads = 0;
		int page = 1;
		int maxRecord = 10;
		int totalOfLeadsCount = 0;
		List<LeadsBO> leadsBOList = new ArrayList<>();
		if (null != paging) {
			page = Integer.parseInt(paging);
		}

		String num = request.getParameter("number");

		if (null != leadsBOi) {

			if (null != num) {

				session.invalidate();
				countOfLeads = leadsService.getcountOfLeads(leadsBO);
				if (0 < countOfLeads) {
					totalOfLeads = countOfLeads;
					model.addAttribute("totalOfLeads", totalOfLeads);
				}
				int startingRecordOfLeads = paginationPageValues(page, maxRecord);
				leadsBO.setRecordIndex(startingRecordOfLeads);
				leadsBO.setMaxRecord(maxRecord);
				leadsBOList = leadsService.getListOfLeadsByPagination(leadsBO);
				if (null != leadsBOList && !leadsBOList.isEmpty() && 0 < leadsBOList.size()) {
					model.addAttribute("listLeads", leadsBOList);
					model.addAttribute("listLeads",
							PaginationClass.paginationLimitedRecords(page, leadsBOList, maxRecord, totalOfLeads));
				}
			} else {
				CampaignBO campaignBO = new CampaignBO();
				if (null != leadsBOi.getCampaignBO() && null != leadsBOi.getCampaignBO().getCampaignName()
						&& !leadsBOi.getCampaignBO().getCampaignName().isEmpty()) {
					String campaign = leadsBOi.getCampaignBO().getCampaignName();
					int campaignId = Integer.parseInt(campaign);
					campaignBO.setCampaignId(campaignId);
					leadsBOi.setCampaignBO(campaignBO);
				}
				if (leadsBOi != null) {
					countOfLeads = leadsService.countOfLeadsBySearch(leadsBOi);
				}
				if (0 != countOfLeads) {
					totalOfLeadsCount = (int) countOfLeads;
					model.addAttribute("totalOfLeadsCount", totalOfLeadsCount);
				}
				if (totalOfLeadsCount == 0) {
					// Handle case where there are no records found
					page = 1; // Set page number to 1 when there are no records
				}
				int stratingRecordOfLeads = paginationPageValues(page, maxRecord);
				leadsBOi.setRecordIndex(stratingRecordOfLeads);
				leadsBOi.setMaxRecord(maxRecord);
				leadsBOi.setPagination("pagination");

				List<LeadsBO> leadsList = new ArrayList<LeadsBO>();

				leadsList = leadsService.getListOfLeadsByPagination(leadsBOi);
				model.addAttribute("listLeads", leadsList);
				model.addAttribute("searchLeads", leadsBOi);
				model.addAttribute("listLeads",
						PaginationClass.paginationLimitedRecords(page, leadsList, maxRecord, totalOfLeadsCount));
			}

		}

		else {

			countOfLeads = leadsService.getcountOfLeads(leadsBO);
			if (0 < countOfLeads) {
				totalOfLeads = countOfLeads;
				model.addAttribute("totalOfLeads", totalOfLeads);
			}
			if (countOfLeads == 0) {
				// Handle case where there are no records found
				page = 1; // Set page number to 1 when there are no records
			}
			int startingRecordOfLeads = paginationPageValues(page, maxRecord);
			leadsBO.setRecordIndex(startingRecordOfLeads);
			leadsBO.setMaxRecord(maxRecord);
			leadsBOList = leadsService.getListOfLeadsByPagination(leadsBO);
			if (null != leadsBOList && !leadsBOList.isEmpty() && 0 < leadsBOList.size()) {
				model.addAttribute("listLeads", leadsBOList);
				model.addAttribute("listLeads",
						PaginationClass.paginationLimitedRecords(page, leadsBOList, maxRecord, totalOfLeads));
			} else {
				model.addAttribute("errorMessage", "Record not Found!");

			}
		}
	}

	private int paginationPageValues(int pageNo, int maxOfRecord) {

		int recordsOfPage = 0;
		if (pageNo == 1) {
			recordsOfPage = 0;
		} else {
			recordsOfPage = (pageNo - 1) * maxOfRecord + 1;
			recordsOfPage = recordsOfPage - 1;
		}
		return recordsOfPage;
	}

	// Leads Search part
	@RequestMapping(value = "/search-leads", method = RequestMethod.POST)
	public String searchLeads(@ModelAttribute("searchLeads") LeadsBO leadsBO, HttpServletRequest request, Model model)
			throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId = getUserSecurity().getCompanyId();
		try {
			long countOfLeads = 0;
			long totalCountOfLeads = 0;
			int maxRecord = 10;
			int page = 1;

			CampaignBO campaignBO = new CampaignBO();

			if (null != leadsBO.getCampaignBO() && null != leadsBO.getCampaignBO().getCampaignName()
					&& !leadsBO.getCampaignBO().getCampaignName().isEmpty()) {
				String campaign = leadsBO.getCampaignBO().getCampaignName();
				int campaignId = Integer.parseInt(campaign);
				campaignBO.setCampaignId(campaignId);
				leadsBO.setCampaignBO(campaignBO);
			}

			if (0 < loginId && !userType.contains("ROLE_ADMIN")) { // companyId
				leadsBO.setCompanyId(companyId);
				campaignBO.setCompanyId(companyId);
			}
			if (null != leadsBO.getFirstName() && !leadsBO.getFirstName().isEmpty()) {
				model.addAttribute("searchElement", leadsBO.getFirstName());
			}
			if (leadsBO != null) {
				countOfLeads = leadsService.countOfLeadsBySearch(leadsBO);
			}
			if (0 != countOfLeads) {
				totalCountOfLeads = countOfLeads;
				model.addAttribute("countOfLeads", countOfLeads);
			}

			// campaigndropdown
			List<LeadsBO> pageleadsBOlist = leadsService.getListLeadsDropDown(leadsBO);
			if (null != pageleadsBOlist && 0 < pageleadsBOlist.size()) {
				model.addAttribute("listcampaign", pageleadsBOlist);
			}
//			List<CampaignBO> pagecampaignlist = campaignService.listOfCampaigns(campaignBO);
//			if (null != pagecampaignlist && 0 < pagecampaignlist.size() && !pagecampaignlist.isEmpty()) {
//				model.addAttribute("listcampaign", pagecampaignlist);
//			}

			int stratingRecordOfLeads = paginationPageValues(page, maxRecord);
			leadsBO.setRecordIndex(stratingRecordOfLeads);
			leadsBO.setMaxRecord(maxRecord);
			leadsBO.setPagination("pagination");

			List<LeadsBO> leadsList = new ArrayList<LeadsBO>();

			leadsList = leadsService.getListOfLeadsByPagination(leadsBO);
			if (null != leadsList && !leadsList.isEmpty() && leadsList.size() > 0) {
				// model.addAttribute("listLeads", leadsList);
				model.addAttribute("searchLeads", leadsBO);
				model.addAttribute("listLeads",
						PaginationClass.paginationLimitedRecords(page, leadsList, maxRecord, totalCountOfLeads));

			} else {
				model.addAttribute("errorMessage", "No Records Found");

				return "view-leads";
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Search lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-leads";
	}

	// Leads to opportunity converting part
	@RequestMapping(value = "/convert-opportunity", method = RequestMethod.GET)
	public String convertOpportunity(Model model, HttpServletRequest request)
			throws FileNotFoundException, MySalesException {
		LOGGER.entry();
		List<Map<?, ?>> leadsMapList = new ArrayList<>();
		String origin = null;
		Integer leadsId = null;
		long companyId = 0;
		try {
			if (null != request.getParameter("leadsId")) {
				leadsId = Integer.valueOf(request.getParameter("leadsId"));
				origin = request.getParameter("origin");

				AdminLoginBO adminLoginBO = new AdminLoginBO();
				long userId = getUserSecurity().getLoginId();
				List<String> userType = getUserSecurity().getRole();
				// adminLoginBO.setUserType(userType);
				adminLoginBO.setId(userId);
				adminLoginBO.setFirstName(getUserSecurity().getName());
				model.addAttribute("userType", adminLoginBO);

				AdminUserBO adminuserBO = new AdminUserBO();
				if (0 < getUserSecurity().getCompanyId()) { // companyId
					companyId = getUserSecurity().getCompanyId();
					adminuserBO.setCompanyId(companyId);

				}

				// retrive Users
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
				userBOList = adminService.retrieveUser(adminuserBO);

				if (null != userBOList && 0 != userBOList.size()) {
					model.addAttribute("userBOList", userBOList);
				}

				leadsMapList = leadsService.getLeadsForOpportunityConversion(leadsId, companyId);
			}
			if (origin.equals("convertopportunity") && null != leadsMapList && leadsMapList.size() > 0) {
				model.addAttribute("origin", origin);
				model.addAttribute("leadId", leadsId);
				model.addAttribute("account", leadsMapList.get(0).get("account"));
				System.out.println(leadsMapList.get(0).get("account"));
				return "create-account";

			} else {
				model.addAttribute("errorMessage", "Lead To opportunity conversion failed");
				return "redirect:/view-leads";
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Opportunity converting has failed:" + ex.getMessage());
			}
			LOGGER.info("Opportunity converting has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-leads";

	}

	public String insertAccountConvertOpportunity() {

		return null;

	}

	// Leads Edit Part
	@RequestMapping(value = "edit-leads", method = RequestMethod.GET)
	public String editLeads(Model model, HttpServletRequest request, @RequestParam("leadsId") Integer leadsId)
			throws FileNotFoundException, MySalesException {
		LOGGER.entry();
		try {
			LeadsBO leadsBO = new LeadsBO();
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			AdminUserBO adminuserBO = new AdminUserBO();
			CampaignBO campaignBO = new CampaignBO();
			ProjectBO projectBO = new ProjectBO();
			if (0 < getUserSecurity().getCompanyId()) { // companyId
				long companyId = getUserSecurity().getCompanyId();
				leadsBO.setCompanyId(companyId);
				campaignBO.setCompanyId(companyId);
				adminuserBO.setCompanyId(companyId);
				projectBO.setCompanyId(companyId);
			}
			leadsBO.setLeadsId(leadsId);

			long loginId = getUserSecurity().getLoginId();
			String industryType = getUserSecurity().getIndustryType();
			List<String> userType = getUserSecurity().getRole();
			// adminLoginBO.setUserType(userType);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			if (0 < loginId && !userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				List<AdminUserBO> userBOList = adminService.retrieveUserByPagination(adminuserBO);
				leadsBO = leadsService.getLeads(leadsBO); // Leads retriew
				model.addAttribute("userBOList", userBOList);
			}

			if (null != leadsBO) {
				if (industryType.equalsIgnoreCase("realEstate")) {
					List<ProjectBO> projectBOList = new ArrayList<>();
					projectBOList = projectService.selectAllProjects(projectBO);
					model.addAttribute("projectBOList", projectBOList);
					model.addAttribute("leadsBO", leadsBO);
				}
			}
			if (0 < loginId && userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
				leadsBO = leadsService.getLeads(leadsBO); // Leads retriew
				model.addAttribute("userBOList", userBOList);
				if (0 != leadsBO.getAdminLoginBO().getId() && null != leadsBO.getAdminLoginBO().getName()
						&& null != leadsBO.getProductServiceBO().getServiceName()) {
					model.addAttribute("usrId", leadsBO.getAdminLoginBO().getId());
					model.addAttribute("userName", leadsBO.getAdminLoginBO().getName());
				}
			}
			// For Product Retrieve
			getListOfProduct(model, request);

			List<CampaignBO> pagecampaignlist = new ArrayList<CampaignBO>();
			if (0 < loginId && !userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				adminLoginBO.setId(loginId);
				// adminLoginBO.setUserType(userType);
				campaignBO.setAdminLoginBO(adminLoginBO);
				pagecampaignlist = campaignService.listOfCampaigns(campaignBO);
			}
			if (0 < loginId && userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				adminLoginBO.setId(loginId);
				// adminLoginBO.setUserType(userType);
				campaignBO.setAdminLoginBO(adminLoginBO);
				pagecampaignlist = campaignService.listOfCampaigns(campaignBO);
			}
			if (null != pagecampaignlist && 0 < pagecampaignlist.size() && !pagecampaignlist.isEmpty()) {
				model.addAttribute("listcampaign", pagecampaignlist);
			}
			if (null != leadsBO) {
				model.addAttribute("leadsBO", leadsBO);
				if (null != leadsBO.getAdminLoginBO() && null != leadsBO.getAdminLoginBO().getName()) {
					model.addAttribute("userName", leadsBO.getAdminLoginBO().getName());
					leadsBO.setUserName(leadsBO.getAdminLoginBO().getName());
				}
				if (null != leadsBO.getProductServiceBO() && 0 < leadsBO.getProductServiceBO().getServiceId()) {
					model.addAttribute("serviceId", leadsBO.getProductServiceBO().getServiceId());
				}
				if (null != leadsBO.getProductServiceBO() && null != leadsBO.getProductServiceBO().getServiceName()) {
					model.addAttribute("serviceName", leadsBO.getProductServiceBO().getServiceName());

				}
				if (0 < loginId && userType.contains("ROLE_ADMIN") || industryType.equalsIgnoreCase("realEstate")) {
					List<ProjectBO> projectBOList = new ArrayList<>();
					projectBOList = projectService.selectAllProjects(projectBO);
					model.addAttribute("projectBOList", projectBOList);

				}

				if (null != leadsBO.getCampaignBO() && null != leadsBO.getCampaignBO().getCampaignName()) {
					model.addAttribute("campaignName", leadsBO.getCampaignBO().getCampaignName());
					model.addAttribute("edit", "leadsEdit");
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-leads";
	}

	// Leads Edit Update Part
	@RequestMapping(value = "edit-leads", method = RequestMethod.POST)
	public String editLeads(@Valid @ModelAttribute("leadsBO") LeadsBO leadsBO, BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpSession session) throws MySalesException, FileNotFoundException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			if (bindingResult.hasErrors()) {
				return "edit-leads";
			}
			InventoryBO productBO = new InventoryBO();
			CampaignBO campaignBO = new CampaignBO();
			ProjectBO projectBO = new ProjectBO();
			if (0 < getUserSecurity().getCompanyId()) { // companyId
				long companyId = getUserSecurity().getCompanyId();
				leadsBO.setCompanyId(companyId);
				productBO.setCompanyId(companyId);
				campaignBO.setCompanyId(companyId);
			}
			if (null != leadsBO && null != leadsBO.getAdminLoginBO()
					&& null != leadsBO.getAdminLoginBO().getFirstName()) {
				String adminId = leadsBO.getAdminLoginBO().getFirstName();
				long userId = 0;
				if (null != adminId) {
					userId = Long.parseLong(adminId);
				}
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(userId);
				leadsBO.setAdminLoginBO(adminLoginBO);
			}

			if (null != leadsBO.getProductServiceBO() && !leadsBO.getProductServiceBO().getServiceName().isEmpty()) { // product
																														// Dropdown

				Long productServiceId = Long.parseLong(leadsBO.getServiceName());
				productBO.setServiceId(productServiceId);
				productBO = productService.retriveServiceById(productBO);
				if (null != productBO) {
					leadsBO.setProductServiceBO(productBO);
				}
			}

			// if (null != leadsBO.getCampaignBO().getCampaignName()) { // campaign int
			// int campaignId = Integer.parseInt(leadsBO.getCampaignBO().getCampaignName());
			// campaignBO.setCampaignId(campaignId);
			// campaignBO = campaignService.retriveCampaignById(campaignBO);
			// }
			if (null != leadsBO.getProjectBO() && !leadsBO.getProjectBO().getProjectName().isEmpty()) { // project int
				int projectId = Integer.parseInt(leadsBO.getProjectBO().getProjectName());
				projectBO.setProjectId(projectId);
				leadsBO.setProjectBO(projectBO);
			}
			// if (null != campaignBO) {
			// leadsBO.setCampaignBO(campaignBO);
			// }

			leadsBO.setIsDelete(false);
			boolean status = leadsService.updateLeads(leadsBO);
			if (status) {
				model.addAttribute("successMessage", messageSource.getMessage("Lead.Update", null, null));
				model.addAttribute("leadsBO", leadsBO);
				return "redirect:/view-leads";
			} else {
				model.addAttribute("errorMessage", "Doesnot Exists");
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Update lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-leads";
	}

	// Leads delete part
	@RequestMapping(value = "/delete-leads", method = RequestMethod.GET)
	public String deleteLeads(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam("leadsId") Integer leadsId) throws FileNotFoundException {
		LOGGER.entry();
		LeadsBO leadsBO = new LeadsBO();
		try {
			if (0 < getUserSecurity().getCompanyId()) { // companyId
				long companyId = getUserSecurity().getCompanyId();
				leadsBO.setCompanyId(companyId);

			}
			leadsBO = leadsService.getLeads(leadsBO);
			if (null != leadsBO) {
				boolean status = leadsService.deleteLeads(leadsId);
				if (status) {
					model.addAttribute("successMessage", messageSource.getMessage("Lead.Delete", null, null));
					return "redirect:/view-leads";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-leads";
	}

	// Leads Tracking status page
	@RequestMapping(value = "/leads-tracking-status", method = RequestMethod.GET)
	public String viewEmployerStatus(Model model, HttpServletRequest request)
			throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {

			LeadsBO leadsBO = new LeadsBO();
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			long userId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			// adminLoginBO.setUserType(userType);
			adminLoginBO.setId(userId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			if (null != request.getParameter("reports")) {
				model.addAttribute("reports", request.getParameter("reports"));
			}

			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}

			if (null != request.getParameter("leadsId")) {
				String id = request.getParameter("leadsId");
				int leadsId = Integer.parseInt(id);
				leadsBO.setLeadsId(leadsId);

			}
			// List<String> userType = getUserSecurity().getRole();
			if (0 < getUserSecurity().getCompanyId() && userType.contains(UserRoles.ROLE_COMPANY.toString())) { // companyId
				long companyId = getUserSecurity().getCompanyId();
				leadsBO.setCompanyId(companyId);

			}
			leadsBO  = leadsService.getLeads(leadsBO);
			if (null != leadsBO) {
				// leadsBOList = leadsBO.getLeadsList();
				model.addAttribute("viewleads", leadsBO);
				// model.addAttribute("leadsBOList", leadsBO.getLeadsUpdateVOList());
				model.addAttribute("leadsBOList", leadsBO.getLeadupdateList());
				// Get the task management list and assign serial numbers
				List<TaskManagementBO> taskManagementList = leadsBO.getTaskManagementBOList();
				if (taskManagementList != null) {
					int serialNo = 1;
					for (TaskManagementBO task : taskManagementList) {
						task.setsNo(serialNo++);
					}

					model.addAttribute("taskManagementList", leadsBO.getTaskManagementBOList());
				}

			} else {
				model.addAttribute("viewleads", new LeadsBO());
				model.addAttribute("leadsBOList", null);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tracking status has failed:" + ex.getMessage());
			}
			LOGGER.info("Tracking status has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "leads-tracking-status";
	}

	// Leads Tracking status update part
	@RequestMapping(value = { "/leads-tracking-status" }, method = RequestMethod.POST)
	public String registerBO(@RequestParam String description, String date, String nextAppointmentDate, long leadsId,
			String timeSlot, String endTmieSlot, Model model, HttpServletRequest request) {
		LOGGER.entry();
		LeadsBO leadsBO = new LeadsBO();
		long loginId = getUserSecurity().getLoginId();
		try {
			if (0 < getUserSecurity().getCompanyId()) { // companyId added
				long companyId = getUserSecurity().getCompanyId();
				leadsBO.setCompanyId(companyId);
			}

			String status = "Inprocess";
			leadsBO.setLeadsId(leadsId);
			leadsBO.setDescription(description);
			leadsBO.setDate(date);
			leadsBO.setTimeSlot(timeSlot);

			leadsBO.setNextAppointmentDate(nextAppointmentDate);
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			adminLoginBO.setId(loginId);
			leadsBO.setAdminLoginBO(adminLoginBO);
			leadsBO.setStatus(status);
			leadsBO = leadsService.saveTracking(leadsBO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tracking status has failed:" + ex.getMessage());
			}
			LOGGER.info("Tracking status has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/leads-tracking-status?leadsId=" + leadsId;
	}

	@RequestMapping(value = "upload-leads", method = RequestMethod.GET)
	public String uploadLeads(Model model, HttpServletRequest request) {
		LeadsBO leadsBO = new LeadsBO();
		model.addAttribute("leadsOBJ", leadsBO);
		return "upload-leads";
	}

	@RequestMapping(value = "/upload-leads", method = RequestMethod.POST)
	public String uploadLeads(@ModelAttribute("leadsOBJ") LeadsBO leadsBO,
			@RequestParam("uploadleads") MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			CompanyBO companyBO = new CompanyBO();
			CampaignBO campaignBO = new CampaignBO();
			InventoryBO serviceBO = new InventoryBO();
			AdminLoginBO userBO = new AdminLoginBO();
			ProjectBO projectBO = new ProjectBO();

			XSSFWorkbook workBook = new XSSFWorkbook(uploadFile.getInputStream());
			XSSFSheet workBookSheet = workBook.getSheetAt(0);

			Iterator<Row> rowIterator = workBookSheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() >= 1) {
					LeadsBO leadsBO1 = new LeadsBO();

					Iterator<Cell> cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:

							if (0 < getUserSecurity().getCompanyId()) { // companyId
								long companyId = getUserSecurity().getCompanyId();
								leadsBO1.setCompanyId(companyId);
							}

							if (null == leadsBO1.getEmailAddress()) {
								leadsBO1.setEmailAddress(cell.getStringCellValue());
								break;
							}

							if (null == leadsBO1.getFirstName()) {
								leadsBO1.setFirstName(cell.getStringCellValue());
								break;
							}
							if (null == leadsBO1.getIndustryType()) {
								leadsBO1.setIndustryType(cell.getStringCellValue());
								break;
							}

							if (null == leadsBO1.getLastName()) {
								leadsBO1.setLastName(cell.getStringCellValue());
								break;
							}

							if (null == leadsBO1.getWebsite()) {
								leadsBO1.setWebsite(cell.getStringCellValue());
								break;
							}

							if (null == leadsBO1.getSalutation()) {
								leadsBO1.setSalutation(cell.getStringCellValue());
								break;
							}

							if (null == leadsBO1.getStreet()) {
								leadsBO1.setStreet(cell.getStringCellValue());
								break;
							}

							if (null == leadsBO1.getState()) {
								leadsBO1.setState(cell.getStringCellValue());
								break;
							}

							if (null == leadsBO1.getDistrict()) {
								leadsBO1.setDistrict(cell.getStringCellValue());
								break;
							}
							if (null == leadsBO1.getDesignation()) {
								leadsBO1.setDesignation(cell.getStringCellValue());
								break;
							}
							if (null == leadsBO1.getDescription()) {
								leadsBO1.setDescription(cell.getStringCellValue());
								break;
							}
							if (null == leadsBO1.getCountry()) {
								leadsBO1.setCountry(cell.getStringCellValue());
								break;
							}
							if (null == leadsBO1.getCity()) {
								leadsBO1.setCity(cell.getStringCellValue());
								break;
							}

							if (null == leadsBO1.getCampaignBO()) {
								campaignBO.setCampaignName(cell.getStringCellValue());
								campaignBO = campaignService.getCampaignObjectByName(campaignBO);
								if (0 < campaignBO.getCampaignId()) {
									leadsBO1.setCampaignBO(campaignBO);
								}
								break;
							}

							if (null == leadsBO1.getAdminLoginBO()) {
								userBO.setName(cell.getStringCellValue());
								userBO = adminService.getEmployeeObjectByName(userBO);
								if (0 < userBO.getId()) {
									leadsBO1.setAdminLoginBO(userBO);
								}
								break;
							}

							if (null == leadsBO1.getStatus()) {
								leadsBO1.setStatus(cell.getStringCellValue());
								break;
							}

							if (null == leadsBO1.getProductServiceBO()) {
								serviceBO.setServiceName(cell.getStringCellValue());
								serviceBO = productService.getProductObjectByName(serviceBO);
								if (null != serviceBO) {
									leadsBO1.setProductServiceBO(serviceBO);
								}
								break;
							}

							if (null == leadsBO1.getProjectBO()) {
								projectBO.setProjectName(cell.getStringCellValue());
								projectBO = projectService.getProjectObjectByName(projectBO);

								if (null != projectBO) {
									leadsBO1.setProjectBO(projectBO);
								}
								break;
							}

						case Cell.CELL_TYPE_NUMERIC:

							if (null == leadsBO1.getFax()) {
								long faxNo = (long) (cell.getNumericCellValue());
								String fax = String.valueOf(faxNo);
								if (null != fax) {
									leadsBO1.setFax(fax);
								}
								break;
							}

							if (null == leadsBO1.getRating()) {
								long rating = (long) (cell.getNumericCellValue());
								String rating1 = String.valueOf(rating);
								if (null != rating1) {
									leadsBO1.setRating(rating1);
								}
								break;
							}

							if (null == leadsBO1.getContactNo()) {
								long contactNo = (long) cell.getNumericCellValue();
								String contactNum = String.valueOf(contactNo);
								if (null != contactNum) {
									leadsBO1.setContactNo(contactNum);
								}

								break;
							}

							if (0 == leadsBO1.getPostalCode()) {
								int postalCode = (int) (cell.getNumericCellValue());
								leadsBO1.setPostalCode(postalCode);
								break;
							}

							if (0 == leadsBO1.getNoOfEmployees()) {
								int noOfEmployees = (int) (cell.getNumericCellValue());
								leadsBO1.setNoOfEmployees(noOfEmployees);
								break;
							}

							if (null == leadsBO1.getMobileNo()) {
								long mobileNo = (long) cell.getNumericCellValue();
								String mobileNum = String.valueOf(mobileNo);
								if (null != mobileNum) {
									leadsBO1.setMobileNo(mobileNum);
								}

								break;
							}

							// if (null == leadsBO1.getAnnualRevenue()) {
							// Double annualRevenue = (Double) (cell.getNumericCellValue());
							// leadsBO1.setAnnualRevenue(annualRevenue);
							// break;
							// }
							if (null == leadsBO1.getAnnualRevenue()) {
								Double annualRevenue = (Double) (cell.getNumericCellValue());

								Long annualRevenueLong = annualRevenue.longValue();
								leadsBO1.setAnnualRevenue(annualRevenueLong);

							}

						}
					}
					leadsBO1 = leadsService.saveLeadsByExcelSheet(leadsBO1);
					if (0 < leadsBO1.getLeadsId()) {
						System.out.println("wonderful");
					}
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Upload has failed:" + ex.getMessage());
			}
			LOGGER.info("Upload  has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "redirect:/view-leads";

	}

	@RequestMapping(value = "/check_leadsEmail", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkEmailAddress(@RequestParam String emailAddress) throws MySalesException {
		LOGGER.entry();
		boolean emailAddressCheck = false;
		long companyId = getUserSecurity().getCompanyId();
		try {
			emailAddressCheck = leadsService.checkEmailAddress(emailAddress, companyId);
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

	public OpportunityService getOpportunityService() {
		return opportunityService;
	}

	public void setOpportunityService(OpportunityService opportunityService) {
		this.opportunityService = opportunityService;
	}

	@RequestMapping(value = "/edit-taskleads", method = RequestMethod.GET)
	public String editTask(@RequestParam("taskId") long taskId, HttpServletRequest request, HttpSession session,
			Model model) throws Exception {
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		try {
			TaskManagementBO taskBO = new TaskManagementBO();
			WorkItemBO workItemBO = new WorkItemBO();
			// AdminLoginBO adminLoginBo = new AdminLoginBO();
			AdminUserBO adminuserBO = new AdminUserBO();
			ProjectBO projectBO = new ProjectBO();
			LeadsBO leadsBO = new LeadsBO();
			AdminUserBO listUserBo = new AdminUserBO();

			if (0 < getUserSecurity().getCompanyId() && !userType.contains("ROLE_ADMIN")) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				adminuserBO.setCompanyId(companyId);
				taskBO.setCompanyId(companyId);
				projectBO.setCompanyId(companyId);
				leadsBO.setCompanyId(companyId);
				listUserBo.setCompanyId(companyId);
				workItemBO.setCompanyId(companyId);
			}

			taskBO.setTaskId(taskId);

			taskBO = taskService.findById(taskBO);

			if (null != taskBO) {

				if (0 < loginId && userType.contains("ROLE_ADMIN")) {
					// List<AdminUserBO> userBOList = opportunityService.retrieveUser();
					List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
					model.addAttribute("userBOList", userBOList);
				}

				if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
					List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
					userBOList = adminService.retrieveUserByPagination(adminuserBO);
					model.addAttribute("userBOList", userBOList);
				}

				List<ProjectBO> projectBOList = projectService.selectAllProjects(projectBO);
				model.addAttribute("projectBOList", projectBOList);

				List<WorkItemBO> slaList = workItemService.findAll(workItemBO);
				model.addAttribute("slaList", slaList);

				List<LeadsBO> leadList = opportunityService.retrieveLeads(leadsBO);
				model.addAttribute("leadList", leadList);
				/*
				 * List<LeadsBO> leadsBOList = new ArrayList<>(); leadsBOList =
				 * leadsService.getListLeads(leadsBO); model.addAttribute("leadsBOList",
				 * leadsBOList);
				 */

				model.addAttribute("taskBO", taskBO);

			} else {
				model.addAttribute("taskBO", taskBO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-taskleads";

	}

	@RequestMapping(value = "/edit-taskleads", method = RequestMethod.POST)
	public String editTask(@ModelAttribute("taskBO") TaskManagementBO taskBO, BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpSession session) throws FileNotFoundException, MySalesException {
		LOGGER.entry();

		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				taskBO.setCompanyId(companyId);
			}
			taskBO = taskService.update(taskBO);
			if (null != taskBO) {
				long leadsId = Long.parseLong(taskBO.getLeadsBO().getFirstName());
				model.addAttribute("successMessage", "Update Successfully");
				model.addAttribute("taskBO", taskBO);
				return "redirect:/leads-tracking-status?leadsId=" + leadsId;
			} else {
				model.addAttribute("errorMessage", "Does Not Exists");
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update task has failed:" + ex.getMessage());
			}
			LOGGER.info("Update task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/leads-tracking-status";

	}

	@RequestMapping(value = "/delete-taskleads", method = RequestMethod.GET)
	public String deleteProject(@RequestParam("taskId") long taskId, @RequestParam("entityId") long entityId,
			HttpServletRequest request, HttpSession session, Model model) throws Exception {

		LOGGER.entry();
		try {
			TaskManagementBO taskBO = new TaskManagementBO();
			taskBO.setTaskId(taskId);
			if (null != taskBO) {
				boolean value = taskService.delete(taskBO);
				if (value == true) {
					System.out.println("Task deleted successfully");

					model.addAttribute("successMessage", "Task Deleted Successfully ");

					return "redirect:/leads-tracking-status?leadsId=" + entityId;
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-task";

	}

}