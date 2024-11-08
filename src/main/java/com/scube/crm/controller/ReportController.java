package com.scube.crm.controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.CampaignBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.CampaignService;
import com.scube.crm.service.LeadsService;
import com.scube.crm.service.OpportunityService;
import com.scube.crm.service.SalesOrderService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.utils.UserRoles;

@Controller
@Scope("session")
@SessionAttributes("/report")
public class ReportController extends ControllerUtils implements Serializable {


	private static final long serialVersionUID = -5857977996611066292L;

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(CampaignController.class);

	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired
	OpportunityService opportunityService;
	
	

	@Autowired
	private CampaignService campaignService;
	@Autowired
	private LeadsService leadsService;

	@Autowired
	ServletContext servletContext;

	OpportunityBO clientBO;
	List<OpportunityBO> profileList;
	List<CampaignBO> pagecampaignlist;
	List<LeadsBO> pageLeadsList;
	List<AdminUserBO> adminEmployeeList;
	List<OpportunityBO> recordList;
	OpportunityBO employer = new OpportunityBO();
	List<OpportunityBO> clientBOList=new ArrayList<>();


	@RequestMapping(value = "/view-leads-reports", method = RequestMethod.GET)
	public String viewLeads(Model model, HttpServletRequest request, HttpSession session) throws Exception {
		LOGGER.entry();
		try {
			LeadsBO leadsBO = new LeadsBO();
			CampaignBO campaignBO = new CampaignBO();
			String paging = null;
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			List<LeadsBO> pageLeads = new ArrayList<LeadsBO>();
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			LeadsBO leadsBOObj = new LeadsBO();
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}
			// adminLoginBO.setUserType(userType);
			leadsBO.setUserId(loginId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);

			if(0<getUserSecurity().getCompanyId()) {    // companyId
				long companyId=getUserSecurity().getCompanyId();
				leadsBO.setCompanyId(companyId);
				campaignBO.setCompanyId(companyId);
				leadsBOObj.setCompanyId(companyId);
			}

			if (0 < loginId && userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				adminLoginBO.setId(loginId);
				// adminLoginBO.setUserType(userType);
				leadsBO.setAdminLoginBO(adminLoginBO);
				pageLeads = leadsService.getListLeads(leadsBO);
			}
			if (0 < loginId && !userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				adminLoginBO.setId(loginId);
				// adminLoginBO.setUserType(userType);
				leadsBO.setAdminLoginBO(adminLoginBO);
				pageLeads = leadsService.getListLeads(leadsBO);

			}
			model.addAttribute("listLeads", pageLeads);
			model.addAttribute("searchLeads", new LeadsBO());
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			model.addAttribute("leadsBO", leadsBO);
			// campaign search dd

			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				adminLoginBO.setId(loginId);
				// adminLoginBO.setUserType(userType);
				campaignBO.setAdminLoginBO(adminLoginBO);
				List<CampaignBO> pagecampaignlist = campaignService.listOfCampaigns(campaignBO);
				if (null != pagecampaignlist && 0 < pagecampaignlist.size()) {
					model.addAttribute("listcampaign", pagecampaignlist);
				}
			}
			if (0 < loginId && userType.contains("ROLE_ADMIN")) {
				adminLoginBO.setId(loginId);
				// adminLoginBO.setUserType(userType);
				campaignBO.setAdminLoginBO(adminLoginBO);
				List<CampaignBO> pagecampaignlist = campaignService.listOfCampaigns(campaignBO);
				if (null != pagecampaignlist && 0 < pagecampaignlist.size() && !pagecampaignlist.isEmpty()) {
					model.addAttribute("listcampaign", pagecampaignlist);
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
		return "view-leads-reports";

	}

	private void leadsPagination(LeadsBO leadsBO, String paging, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		LeadsBO leadsBOi = (LeadsBO) session.getAttribute("leadsbOLists");
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
				model.addAttribute("totalleadlist", totalOfLeads);
			}else {
				model.addAttribute("errorMessage", "Record Not Found!");
			}
			int startingRecordOfLeads = paginationPageValues(page, maxRecord);
			leadsBO.setRecordIndex(startingRecordOfLeads);
			leadsBO.setMaxRecord(maxRecord);
			leadsBOList = leadsService.getListOfLeadsByPagination(leadsBO);
			if (null != leadsBOList && !leadsBOList.isEmpty() && 0 < leadsBOList.size()) {
				model.addAttribute("listLeads", leadsBOList);
				model.addAttribute("listLeads",
						PaginationClass.paginationLimitedRecords(page, leadsBOList, maxRecord, totalOfLeads));
			}else {
				model.addAttribute("errorMessage", "Record Not Found!");
			}
		}
	}

	private int paginationPageValuess(int pageNo, int maxOfRecord) {

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
	@RequestMapping(value = "/search-leadss", method = RequestMethod.GET)
	public String searchLeads( @ModelAttribute("searchLeads") LeadsBO leadsBO,
			HttpServletRequest request, Model model) throws MySalesException, SerialException, SQLException {
		LOGGER.entry();

		HttpSession session=request.getSession();
		session.setAttribute("leadsbOLists", leadsBO);
		try {
			List<LeadsBO> pageLeads = new ArrayList<LeadsBO>();
			long loginId = getUserSecurity().getLoginId();
			long companyId = getUserSecurity().getCompanyId();
			List<String> userType = getUserSecurity().getRole();
			CampaignBO campaignBO = new CampaignBO(); 
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				adminLoginBO.setId(loginId);
				// adminLoginBO.setUserType(userType);
				campaignBO.setAdminLoginBO(adminLoginBO);
				campaignBO.setCompanyId(companyId);
				leadsBO.setCompanyId(companyId);
				List<CampaignBO> pagecampaignlist = campaignService.listOfCampaigns(campaignBO);
				if (null != pagecampaignlist && 0 < pagecampaignlist.size()) {
					model.addAttribute("listcampaign", pagecampaignlist);
				}
			}
			
			long countOfLeads=0;
			long totalCountOfLeads=0;
			int maxRecord=10;
			int page=1;
			if(null!=leadsBO.getCampaignBO()&& null!=leadsBO.getCampaignBO().getCampaignName() 
					&& !leadsBO.getCampaignBO().getCampaignName().isEmpty()) 
			{ 
				String campaign=leadsBO.getCampaignBO().getCampaignName();
				int campaignId=Integer.parseInt(campaign);
				campaignBO.setCampaignId(campaignId); 
				leadsBO.setCampaignBO(campaignBO);
			}
			if(leadsBO!=null) {
				countOfLeads=leadsService.countOfLeadsBySearch(leadsBO);
			}
			if(0!=countOfLeads) {
				totalCountOfLeads=countOfLeads;
				model.addAttribute("totalsearchleads", totalCountOfLeads);
			}else {
				model.addAttribute("errorMessage", "Record Not Found!");
			}

			int stratingRecordOfLeads=paginationPageValues(page, maxRecord);
			leadsBO.setRecordIndex(stratingRecordOfLeads);
			leadsBO.setMaxRecord(maxRecord);
			leadsBO.setPagination("pagination");

			List<LeadsBO> leadsList=new ArrayList<LeadsBO>();

			leadsList=leadsService.getListOfLeadsByPagination(leadsBO);
			model.addAttribute("listLeads", leadsList);
			model.addAttribute("searchLeads", leadsBO);
			model.addAttribute("listLeads", PaginationClass.paginationLimitedRecords(page, leadsList, maxRecord, totalCountOfLeads));
			return "view-leads-reports";
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Search lead has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return "view-leads-reportss";
	}	






	@RequestMapping(value = "/view-opportunitie" , method = RequestMethod.GET)
	public String viewOpportunity(Model model, HttpServletRequest request) throws IOException, MySalesException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();

			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			AdminUserBO adminuserBO=new AdminUserBO();
			LeadsBO leadsBO = new LeadsBO();
			InventoryBO serviceBO = new InventoryBO();
			OpportunityBO opportunityBO = new OpportunityBO();
			
			
			if(0<getUserSecurity().getCompanyId()) {
				long companyId=getUserSecurity().getCompanyId();  //company based create condition
				opportunityBO.setCompanyId(companyId); 
				leadsBO.setCompanyId(companyId);
				serviceBO.setCompanyId(companyId);
				adminuserBO.setCompanyId(companyId);
				}
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			if (null != request.getParameter("status")) {
				model.addAttribute("status", request.getParameter("status"));
			}
			String paging = null;
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}
			
			// Common for both "Search" and "View"
			
			
			// Search
			List<OpportunityBO> opList = new ArrayList<OpportunityBO>();
			model.addAttribute("opList", opList);
			model.addAttribute("searchObj", opportunityBO);
			List<InventoryBO> prodList = opportunityService.getProductList(serviceBO);
			model.addAttribute("prodList", prodList);
			
			// View Part
			model.addAttribute("view-opportunities", opportunityBO);
			opportunityPagination(opportunityBO, paging, request, model);
	        }catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("View Opporunity has failed:" + ex.getMessage());
				}
				LOGGER.info("View Opporunity has failed:" + ex.getMessage());
			} finally {
				LOGGER.exit();
			}

			return "view-opportunities";
	}

	private void opportunityPagination(OpportunityBO opportunityBO , String paging , HttpServletRequest request, Model model) throws IOException, MySalesException {
		long count = 0;
		long viewCount = 0;
		int page = 1;
		int maxRecord = 10;

		if (null != paging) {
			page = Integer.parseInt(paging);
		}

		count = opportunityService.oppocount(opportunityBO);
		if (count != 0) {
			viewCount = count;
			model.addAttribute("viewCount",viewCount);
		}else {
			model.addAttribute("errorMessage", "Record Not Found!");
		}

		int startingRecordIndex = paginationPageValues(page, maxRecord);
		opportunityBO.setRecordIndex(startingRecordIndex);
		opportunityBO.setMaxRecord(maxRecord);
		opportunityBO.setPagination("pagination");

		List<OpportunityBO> opportunityBOList = new ArrayList<OpportunityBO>();
		opportunityBOList = opportunityService.view(opportunityBO);
		if (opportunityBOList != null && opportunityBOList.size() > 0 && !opportunityBOList.isEmpty()) {
			model.addAttribute("oppoList",
					PaginationClass.paginationLimitedRecords(page, opportunityBOList, maxRecord, viewCount));
		}else {
			model.addAttribute("errorMessage", "Record Not Found!");
		}
	}

	private int paginationPageValues(int pageid, int recordPerPage) {
		int pageRecords = 0;
		if(pageid == 1) {
			pageRecords = 0;
		}
		else {
			pageRecords = (pageid - 1) * recordPerPage;
		}
		return pageRecords;
	}

	@RequestMapping(value="search-opportunitie",method=RequestMethod.POST)
	public String search(@Valid Model model,@ModelAttribute("searchObj")OpportunityBO opportunityBO,BindingResult result , HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
        try {
        if (result.hasErrors()) {
            return "redirect:/admin-sign-in";
        }
        long loginId = getUserSecurity().getLoginId();

        if (0 == loginId) {
            return "redirect:/admin-sign-in";
        }
        String pName = "";
        Long sid = 0l;
        long count = 0;
        long searchCount = 0;
        int page = 1;
        int maxRecord = 10;

        if (null != request.getParameter("page")) {
            String paging = request.getParameter("page");
            page = Integer.parseInt(paging);
        }

        if (opportunityBO != null && !opportunityBO.getProductService().getServiceName().isEmpty()) {
            pName = opportunityBO.getProductService().getServiceName();
            sid = Long.parseLong(pName);
            InventoryBO pBO = new InventoryBO();
            pBO.setServiceId(sid);
            opportunityBO.setProductService(pBO);
        }
        if(0<getUserSecurity().getCompanyId()) {
			long companyId=getUserSecurity().getCompanyId();  //company based create condition
			opportunityBO.setCompanyId(companyId); 
			}
        count = opportunityService.searchCount(opportunityBO);
        if (0 != count && count > 0) {
            searchCount = count;
            model.addAttribute("searchCount", searchCount);
        } else {
            model.addAttribute("errorMessage", "No Records Found");
           // model.remove("opportunityBOList");
//            return "view-opportunities";
        }
        AdminUserBO adminuserBO=new AdminUserBO();
        LeadsBO leadsBO = new LeadsBO();
        InventoryBO serviceBO = new InventoryBO();
        
        
        if(0<getUserSecurity().getCompanyId()) {
            long companyId=getUserSecurity().getCompanyId();  //company based create condition
            leadsBO.setCompanyId(companyId);
            serviceBO.setCompanyId(companyId);
            adminuserBO.setCompanyId(companyId);
            }
        int startingRecordIndex = paginationPageValues(page, maxRecord);
        opportunityBO.setRecordIndex(startingRecordIndex);
        opportunityBO.setMaxRecord(maxRecord);
        opportunityBO.setPagination("pagination");
        List<OpportunityBO> opportunityBOList = new ArrayList<OpportunityBO>();
        opportunityBOList = opportunityService.search(opportunityBO);
        if (opportunityBO.getSalesStage() == null && opportunityBO.getFirstName() == null) {
            InventoryBO psBO = new InventoryBO();
            psBO.setServiceName(opportunityBOList.get(0).getProductService().getServiceName());
            psBO.setServiceId(opportunityBOList.get(0).getProductService().getServiceId());
            opportunityBO.setProductService(psBO);
        } else {
            InventoryBO pBO = new InventoryBO();
            pBO.setServiceName(pName);
            opportunityBO.setProductService(pBO);
        }

//        model.addAttribute("oppoLists", opportunityBOList);
        List<InventoryBO> prodList = opportunityService.getProductList(serviceBO);
        model.addAttribute("prodList", prodList);

        if (opportunityBOList != null && opportunityBOList.size() > 0 && !opportunityBOList.isEmpty()) {
            model.addAttribute("oppoLists",
                    PaginationClass.paginationLimitedRecords(page, opportunityBOList, maxRecord, searchCount));
        } else {
            model.addAttribute("errorMessage", "No Records Found");
//            model.remove("opportunityBOList");
        }
        }catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search Opporunity has failed:" + ex.getMessage());
			}
			LOGGER.info("Search Opporunity has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

        return "view-opportunities";
	}
	

	@RequestMapping(value="/report-salesorder", method = RequestMethod.GET)
	public String retriveSalesOrders(HttpServletRequest request,Model model) throws Exception {
		long loginId = getUserSecurity().getLoginId();

		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}

		if(null!=request.getParameter("successMessage")) {
			model.addAttribute("successMessage",request.getParameter("successMessage"));
		}
		if(null!=request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage",request.getParameter("errorMessage"));
		}
		String paging = null;
		if(null != request.getParameter("page")) {
			paging = request.getParameter("page");
		}
		//Common for both search and view
		SalesOrderBO salesOrderBO = new SalesOrderBO();

		AccountBO accountBO=new AccountBO();
		if(0<getUserSecurity().getCompanyId()) {
			long companyId=getUserSecurity().getCompanyId();
			salesOrderBO.setCompanyId(companyId);
			accountBO.setCompanyId(companyId);
		}
		//Search
		model.addAttribute("searchObj", salesOrderBO);
		Map<Integer , String> accountList = salesOrderService.retriveAccounts(accountBO);
		model.addAttribute("accountList", accountList);

		//view
		model.addAttribute("view-sales-order", salesOrderBO);// Should be same as URL in list
		salesOrderPagination(salesOrderBO , paging , request, model);


		return "sales-report";

	}
	private void salesOrderPagination(SalesOrderBO salesOrderBO , String paging , HttpServletRequest request, Model model) throws Exception {
		long count = 0;
		long viewCount = 0;
		int page = 1;
		int maxRecord = 10;

		if(null != paging) {
			page = Integer.parseInt(paging);
		}
		count = salesOrderService.salesCount(salesOrderBO);
		if(count != 0) {
			viewCount = count;
			model.addAttribute("listsalesorder",viewCount);
		}else {
			model.addAttribute("errorMessage", "Record Not Found!");
		}

		int startingRecordIndex = paginationPageValues(page , maxRecord);
		salesOrderBO.setRecordIndex(startingRecordIndex);
		salesOrderBO.setMaxRecord(maxRecord);
		salesOrderBO.setPagination("pagination");

		List <SalesOrderBO> salesOrderList=new ArrayList<>();
		salesOrderList=salesOrderService.retriveSalesOrders(salesOrderBO);
		if(salesOrderList != null && salesOrderList.size() > 0 && !salesOrderList.isEmpty()) {
			model.addAttribute("listSales",PaginationClass.paginationLimitedRecords(page, salesOrderList, maxRecord, viewCount));
		}else {
			model.addAttribute("errorMessage", "Record Not Found!");
		}
	}
	
	@RequestMapping(value="/sales-search",method=RequestMethod.POST)
	public String search(@Valid Model model,@ModelAttribute("searchObj")SalesOrderBO salesOrderBO,BindingResult result , HttpServletRequest request) throws MySalesException, Exception {
		LOGGER.entry();
		if(result.hasErrors()) {
			return "redirect:/admin-sign-in";
		}
		long loginId = getUserSecurity().getLoginId();

		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		long count = 0;
		long searchCount = 0;
		int page = 1;
		int maxRecord = 10;

		if(null != request.getParameter("page")) {
			String paging = request.getParameter("page");
			page = Integer.parseInt(paging);
		}
		
		/* Search based on Foreign Key */
		if(salesOrderBO != null && salesOrderBO.getAccountBO().getAccountId() != null && 0 < salesOrderBO.getAccountBO().getAccountId() ) {
			AccountBO aBO = new AccountBO();
			aBO.setAccountId(salesOrderBO.getAccountBO().getAccountId());
			salesOrderBO.setAccountBO(aBO);
		}
		AccountBO accountBO = new AccountBO();
		if (0 < getUserSecurity().getCompanyId()) { // Company
			long companyId = getUserSecurity().getCompanyId();
			salesOrderBO.setCompanyId(companyId);
			accountBO.setCompanyId(companyId);
		}
		count  = salesOrderService.searchCount(salesOrderBO);
		if(0 != count && count > 0) {
			searchCount = count;
			model.addAttribute("searchcountsalesorder", searchCount);
		}
		else {
			model.addAttribute("errorMessage", "No Records Found");

			return "sales-report";
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		salesOrderBO.setRecordIndex(startingRecordIndex);
		salesOrderBO.setMaxRecord(maxRecord);
		salesOrderBO.setPagination("pagination");
		List<SalesOrderBO> salesOrderBOList = new ArrayList<SalesOrderBO>();
		salesOrderBOList = salesOrderService.search(salesOrderBO);
		
		/*Maintain the current value of Foreign Key across all pages When the record is available*/
		if(salesOrderBO.getSalesOrderNo() == null) {
			AccountBO aBO = new AccountBO();
			aBO.setAccountId(salesOrderBOList.get(0).getAccountBO().getAccountId());
			aBO.setAccountName(salesOrderBOList.get(0).getAccountBO().getAccountName());
			salesOrderBO.setAccountBO(aBO);
		}
		
		/*Maintain the current value of Foreign Key across all pages When the record is Unavailable*/
		else {
			AccountBO aBO = new AccountBO();
			aBO.setAccountId(salesOrderBO.getAccountBO().getAccountId());
			salesOrderBO.setAccountBO(aBO);
		}
		model.addAttribute("listSalesSearch", salesOrderBOList);
		if(salesOrderBOList != null && salesOrderBOList.size() > 0 && !salesOrderBOList.isEmpty()) {
			model.addAttribute("listSalesSearch",PaginationClass.paginationLimitedRecords(page, salesOrderBOList, maxRecord, searchCount));
		}else {
			model.addAttribute("errorMessage", "No Records Found");
		}
		//AccountBO accountBO=new AccountBO();
		/* To keeping the dynamic search bar constantly Across Pages  */
		Map<Integer , String> accountList = salesOrderService.retriveAccounts(accountBO);
		model.addAttribute("accountList", accountList);
		LOGGER.exit();
		return "sales-report";
	}
	}
