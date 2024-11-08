package com.scube.crm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.CampaignBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.PlotBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.AdminService;
import com.scube.crm.service.CampaignService;
import com.scube.crm.service.ProductService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.utils.RoundDecimalValue;
import com.scube.crm.utils.UserRoles;
import com.scube.crm.vo.ActivityVO;

@Controller
@Scope("session")
@SessionAttributes("/admin")
public class CampaignController extends ControllerUtils implements Serializable {

	private static final long serialVersionUID = -5857977996611066292L;

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(CampaignController.class);

	public static final String fileuploads = "C:\\usr\\local\\mysales\\campaign\\";
	@Autowired
	private CampaignService campaignService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ProductService productService;
	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	List<CampaignBO> pagecampaignlist;
	List<LeadsBO> pageLeadsList;
	List<AdminUserBO> adminEmployeeList;
	CampaignBO campaignBOSearch = new CampaignBO();

	// Campaign Create Part I
	@PreAuthorize("hasAnyRole('ADMIN','CAMPAIGN_MANGER')")
	@RequestMapping(value = "/create-campaign", method = RequestMethod.GET)
	public String getCampaign(Model model, HttpServletRequest request, HttpSession session)
			throws MySalesException, NumberFormatException, FileNotFoundException, SerialException, SQLException {
		CampaignBO campaign = new CampaignBO();
		model.addAttribute("campaignBO", campaign);
		AdminLoginBO adminLoginBO = new AdminLoginBO();
		long loginId = getUserSecurity().getLoginId();

		List<String> userType = getUserSecurity().getRole();
		adminLoginBO.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBO);
		// As an admin Role restrictions, get products and campaign owners

		InventoryBO serviceBO = new InventoryBO();
		AdminUserBO listUserBo = new AdminUserBO();
		// retrieve products
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			List<InventoryBO> productBOList = new ArrayList<>();
			productBOList = productService.listOfProductByPagination(serviceBO);
			model.addAttribute("productList", productBOList);
		}else {
			// Comapany Based List
			if (0 < getUserSecurity().getCompanyId()) { // CompanyId
				long CompanyId = getUserSecurity().getCompanyId();
				serviceBO.setCompanyId(CompanyId);
				listUserBo.setCompanyId(CompanyId);
				
			}
			List<InventoryBO> productBOList = new ArrayList<>();
			productBOList = productService.listOfProductByPagination(serviceBO);
			model.addAttribute("productList", productBOList);
		}

		// retrieve campaign owners
		if (0 < loginId && userType.contains("ROLE_ADMIN")) { // Employee
			List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
			userBOList = adminService.retrieveUser(listUserBo);
			model.addAttribute("userBOList", userBOList);
		}
		// except admin Role
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
			userBOList = adminService.retrieveUserByPagination(listUserBo);
			if (null != userBOList && 0 != userBOList.size()) {
				model.addAttribute("userBOList", userBOList);
			}
			/*
			 * InventoryBO serviceBO1 = new InventoryBO(); List<InventoryBO> serviceList =
			 * productService.listservice(serviceBO1); model.addAttribute("productList",
			 * serviceList);
			 */
		}
		return "create-campaign";
	}

	// Campaign Create Part - II
	@RequestMapping(value = "/create-campaign", method = RequestMethod.POST)
	public String postCampaign(@Valid @ModelAttribute("campaignBO") CampaignBO campaignBO, BindingResult bindingResult,
			Model model, HttpServletRequest request, HttpSession session) throws IOException {
		LOGGER.entry();
		
//			if (bindingResult.hasErrors()) {
//				return "create-campaign";
//			}
			
		try {
				
			if (0 < getUserSecurity().getCompanyId()) { // CompanyId
				long CompanyId = getUserSecurity().getCompanyId();
				campaignBO.setCompanyId(CompanyId);
			}
			String campignId = campaignBO.getCampaignOwner();
			long ownerId = 0;
			ownerId = Long.parseLong(campignId);
			// userLoginId
			long loginId = (long) getUserSecurity().getLoginId();
			if (0 < loginId) {
				campaignBO.setCreatedBy(loginId); // LoginId added
			}
			String userType = getUserSecurity().getRole().toString();

			if (0 < loginId && !userType.equalsIgnoreCase("admin")) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(loginId);
				campaignBO.setAdminLoginBO(adminLoginBO);
			} else {
				if (null != campaignBO.getCampaignOwner()) {
					String adminId = campaignBO.getCampaignOwner();
					long userId = 0;
					if (null != adminId) {
						userId = Long.parseLong(adminId);
					}
					AdminLoginBO adminLoginBO = new AdminLoginBO();
					adminLoginBO.setId(userId);
					campaignBO.setAdminLoginBO(adminLoginBO);
				}
			}
			if (null != campaignBO) {
				
				AdminUserBO userBOList = campaignService.retrieveParticularUser(ownerId);
				campaignBO.setCampaignOwner(userBOList.getName());
				campaignBO.setUserId(userBOList.getId());
				campaignBO.setisDelete(false);

				campaignBO.setExpectedRevenue(campaignBO.getExpectedRevenue());
				campaignBO.setBudgetedCost(campaignBO.getBudgetedCost());
				campaignBO.setExpectedResponse(campaignBO.getExpectedResponse());
				campaignBO = campaignService.saveCompaign(campaignBO);

				if (0 != campaignBO.getCampaignId()) {
					model.addAttribute("successMessage", messageSource.getMessage("Campaign.Creation", null, null));
					return "redirect:/view-campaign";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add Campaign has failed:" + ex.getMessage());
			}
			LOGGER.info("Add Campaign has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-campaign";
	}
     
	// Campaign View Part
	@RequestMapping(value = "view-campaign", method = RequestMethod.GET)
	public String viewCampaign(Model model, HttpServletRequest request, HttpSession session) throws Exception {
		LOGGER.entry();
		try {
			String paging = null;
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
				if(request.getParameter("errorMessage").equals("No Records Found")) {
					model.addAttribute("searchCampaign", new CampaignBO());
					return "view-campaign";
					
				}
			}
			CampaignBO campaignBo = new CampaignBO();
			
			CampaignBO campaignBo1 = new CampaignBO();

			InventoryBO serviceBO = new InventoryBO();
			long loginId = getUserSecurity().getLoginId();
			long companyId = getUserSecurity().getCompanyId();
			List<String> userType = getUserSecurity().getRole();
			
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				campaignBo.setCompanyId(companyId);
				campaignBo1.setCompanyId(companyId);
			}
			serviceBO.setCompanyId(companyId);
			// particular view products
			if (null != request.getParameter("serviceId")) {
				// InventoryBO inventory=new InventoryBO();
				long serviceId = Long.parseLong(request.getParameter("serviceId"));
				model.addAttribute("serviceId", serviceId);
				if (0 < serviceId) {
					serviceBO.setServiceId(serviceId);
					campaignBo.setProductServiceBO(serviceBO);

					List<CampaignBO> pagecampaignlist = new ArrayList<CampaignBO>();
					pagecampaignlist = campaignService.listOfCampaigns(campaignBo);
					if (null != pagecampaignlist && !pagecampaignlist.isEmpty() && pagecampaignlist.size() > 0) {
						model.addAttribute("campaignlistsProducts", pagecampaignlist);
						model.addAttribute("searchCampaign", new CampaignBO());
						return "view-campaign";
					}
				}
			}

			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			} else {
				campaignBOSearch = null;
			}

			// retrieve products
			// InventoryBO serviceBO=new InventoryBO();
//			List<InventoryBO> serviceList = productService.listOfProductByPagination(serviceBO);
			// List<InventoryBO> serviceList = productService.listservice(serviceBO);
			
			List<CampaignBO> productList = campaignService.findAllProducts(campaignBo1);
		
			List<CampaignBO> uniqueProducts = productList.stream()
				    .collect(Collectors.toMap(
				        product -> product.getProductServiceBO().getServiceName(), 
				        product -> product,  
				        (existing, replacement) -> existing 
				    ))
				    .values().stream()
				    .collect(Collectors.toList());
			
			model.addAttribute("productList", uniqueProducts);

			// Search bar
			model.addAttribute("searchCampaign", new CampaignBO());
			// pagination
			campaignPagination(campaignBo, paging, model, session, request);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View Campaign has failed:" + ex.getMessage());
			}
			LOGGER.info("View Campaign has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-campaign";
	}

	// View Pagination Part
	private void campaignPagination(CampaignBO campaignBo, String paging, Model model, HttpSession session,
			HttpServletRequest request) throws Exception {
		long count = 0;
		long totalcampaigncount = 0;
		int page = 1;
		int maxRecord = 10;
		long totalcampaignRecordcount = 0;
		if (null != paging) {
			page = Integer.parseInt(paging);
		}
		// Search next page purpose
		if (null != campaignBOSearch) {
			if (StringUtils.isNotEmpty(campaignBOSearch.getCampaignName())) {
				campaignBo.setCampaignName(campaignBOSearch.getCampaignName());
			}
			if (StringUtils.isNotEmpty(campaignBOSearch.getCampaignMode())) {
				campaignBo.setCampaignMode(campaignBOSearch.getCampaignMode());
			}
			if (null != campaignBOSearch.getProductServiceBO()
					&& StringUtils.isNotEmpty(campaignBOSearch.getProductServiceBO().getServiceName())
					&& campaignBOSearch.getProductServiceBO().getServiceName().isEmpty()) {
				InventoryBO productServiceBO = new InventoryBO();
				String serviceId = campaignBOSearch.getProductServiceBO().getServiceName().trim(); // trim() handles
																									// white spaces
				Integer productId = Integer.parseInt(serviceId);
				productServiceBO.setServiceId(productId);
				campaignBo.setProductServiceBO(productServiceBO);
			} else {
				InventoryBO productServiceBO = new InventoryBO();
				long serviceId = campaignBOSearch.getProductServiceBO().getServiceId();
				productServiceBO.setServiceId(serviceId);
				campaignBo.setProductServiceBO(productServiceBO);
			}
		}
		// records counting call
		if (null != campaignBo) {
			// count=campaignService.getListOfCampaign(campaignBo);
			count = campaignService.campaignforObject(campaignBo);
		}
		if (0 != count) {
			totalcampaignRecordcount = count;
			model.addAttribute("totalcampaignRecordcount", totalcampaignRecordcount);
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		campaignBo.setRecordIndex(startingRecordIndex);
		campaignBo.setMaxRecord(maxRecord);
		campaignBo.setPagination("pagination");

		AdminLoginBO adminLoginBO = new AdminLoginBO();
		long loginId = (long) getUserSecurity().getLoginId();
		if (0 < loginId) {
			campaignBo.setCreatedBy(loginId); // LogiId added in createdby
//			adminLoginBO.setId(loginId);
//			campaignBo.setAdminLoginBO(adminLoginBO);
		}

		if (null != campaignBo) {
			// Total campaign list purpose call
			pagecampaignlist = campaignService.listOfCampaigns(campaignBo);
		}
		if (null != pagecampaignlist && !pagecampaignlist.isEmpty() && pagecampaignlist.size() > 0) {
			model.addAttribute("campaignlists", pagecampaignlist);

			List<CampaignBO> campaignLists = new ArrayList<CampaignBO>();
			totalcampaigncount = totalcampaignRecordcount;
			campaignLists.addAll(pagecampaignlist);
			model.addAttribute("campaignlists",
					PaginationClass.paginationLimitedRecords(page, campaignLists, maxRecord, totalcampaigncount));
		}else {
			model.addAttribute("errorMessage","No Record Found!!");
		}
	}

	// pagination page values common method
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

	// Campaign Search Part
	@RequestMapping(value = "/search-campaign", method = RequestMethod.POST)
	public String searchCampaign(@ModelAttribute("searchCampaign") CampaignBO campaignBO, HttpServletRequest request,
			HttpSession session, Model model) throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {
			long count = 0;
			long totalcampaignRecordcount = 0;
			int page = 1;
			int maxRecord = 0;
			String record = "10";
			if (null != record) {
				maxRecord = Integer.parseInt(record);
			}
			InventoryBO productBO = new InventoryBO();
			
			CampaignBO campaignBo1 = new CampaignBO();

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId();
				campaignBO.setCompanyId(companyId);
				productBO.setCompanyId(companyId);
				
				campaignBo1.setCompanyId(companyId);
			}
			// service string to int Id value conversion

			if (null != campaignBO.getProductServiceBO() && null != campaignBO.getProductServiceBO().getServiceName()
					&& !campaignBO.getProductServiceBO().getServiceName().isEmpty()) {
				String serviceId = campaignBO.getProductServiceBO().getServiceName();
				Integer productId = Integer.parseInt(serviceId);
				productBO.setServiceId(productId);
				campaignBO.setProductServiceBO(productBO);
			}
			campaignBOSearch = campaignBO;
			if (null != campaignBO) {
				count = campaignService.campaignforObject(campaignBO);
			}
			if (0 != count) {
				totalcampaignRecordcount = count;
				model.addAttribute("totalcampaignRecordcount", totalcampaignRecordcount);
			}
			// drop down
	//		List<InventoryBO> serviceList = productService.listservice(productBO);
			
			List<CampaignBO> productList = campaignService.findAllProducts(campaignBo1);
			
			List<CampaignBO> uniqueProducts = productList.stream()
				    .collect(Collectors.toMap(
				        product -> product.getProductServiceBO().getServiceName(), 
				        product -> product,  
				        (existing, replacement) -> existing 
				    ))
				    .values().stream()
				    .collect(Collectors.toList());
			
			model.addAttribute("productList", uniqueProducts);
			// Search pagination
			List<CampaignBO> pagecampaignlist = new ArrayList<CampaignBO>();

			int startingRecordIndex = paginationPageValues(page, maxRecord);
			campaignBO.setRecordIndex(startingRecordIndex);
			campaignBO.setMaxRecord(maxRecord);
			campaignBO.setPagination("pagination");
			pagecampaignlist = campaignService.listOfCampaigns(campaignBO);
			if (null != pagecampaignlist && !pagecampaignlist.isEmpty() && pagecampaignlist.size() > 0) {
				model.addAttribute("campaignlists", PaginationClass.paginationLimitedRecords(page, pagecampaignlist,
						maxRecord, totalcampaignRecordcount));
				
			}else {
				model.addAttribute("errorMessage", "No Records Found");
				return "view-campaign";
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search Campaign has failed:" + ex.getMessage());
			}
			LOGGER.info("Search Campaign has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-campaign";
	}

	// Campaign Edit Part I
	@RequestMapping(value = "edit-campaign", method = RequestMethod.GET)
	public String editCampaign(Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException, MySalesException {
		LOGGER.entry();
		try {
			CampaignBO campaignBO = new CampaignBO();
			InventoryBO serviceBO = new InventoryBO();
			AdminUserBO listUserBo = new AdminUserBO();
			if (0 < getUserSecurity().getCompanyId()) { // CompanyId
				long companyId = getUserSecurity().getCompanyId();
				serviceBO.setCompanyId(companyId);
				listUserBo.setCompanyId(companyId);
			}

			String campaignId = request.getParameter("campaignid");
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			// adminLoginBO.setUserType(userType);
			adminLoginBO.setId(loginId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			if (0 < loginId && !userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				// List<AdminUserBO> userBOList = adminService.retrieveUser();
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>(); // Employee
				userBOList = adminService.retrieveUserByPagination(listUserBo);
				model.addAttribute("userBOList", userBOList);
				model.addAttribute("usrId", campaignBO.getUserId());
				model.addAttribute("userName", campaignBO.getUserName());
				campaignBO = campaignService.getObject(campaignId);
			}
			if (0 < loginId && userType.contains((UserRoles.ROLE_ADMIN.toString()))) {
				// List<AdminUserBO> userBOList = adminService.retrieveUser();
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>(); // Employee
				userBOList = adminService.retrieveUserByPagination(listUserBo);
				model.addAttribute("userBOList", userBOList);
				model.addAttribute("usrId", campaignBO.getUserId());
				model.addAttribute("userName", campaignBO.getUserName());
				campaignBO = campaignService.getObject(campaignId);
			}
			if (0 < loginId && userType.contains((UserRoles.ROLE_ADMIN.toString()))) {
				// InventoryBO serviceBO=new InventoryBO();
				// List<InventoryBO> serviceList = productService.listservice(serviceBO);
				List<InventoryBO> serviceList = new ArrayList<>();
				serviceList = productService.listOfProductByPagination(serviceBO);
				if (null != serviceList && 0 < serviceList.size()) {
					model.addAttribute("productList", serviceList);
				}
			}
			if (0 < loginId && !userType.contains(UserRoles.ROLE_ADMIN.toString())) {

				// List<InventoryBO> serviceList = productService.listservice(serviceBO);
				List<InventoryBO> serviceList = new ArrayList<>();
				serviceList = productService.listOfProductByPagination(serviceBO);
				if (null != serviceList && 0 < serviceList.size()) {
					model.addAttribute("productList", serviceList);
				}
			}
			if (null != campaignBO) {
				model.addAttribute("campaignBO", campaignBO);
				if (null != campaignBO.getCampaignOwner() && !campaignBO.getCampaignOwner().isEmpty()) {
					model.addAttribute("userName", campaignBO.getCampaignOwner());
					model.addAttribute("serviceId", campaignBO.getProductServiceBO().getServiceId());
					model.addAttribute("serviceName", campaignBO.getProductServiceBO().getServiceName());
				} else {
					model.addAttribute("userName", campaignBO.getUserName());
				}
				model.addAttribute("edit", "campaginEdit");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Campaign has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Campaign has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-campaign";
	}

	// Campaign Edit Part II
	@RequestMapping(value = "edit-campaign", method = RequestMethod.POST)
	public String editCampaign(@Valid @ModelAttribute("campaignBO") CampaignBO campaignBO, BindingResult bindingResult,
			Model model, HttpServletRequest request, HttpSession session) throws FileNotFoundException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();

			if (0 < getUserSecurity().getCompanyId()) { // CompanyId
				long companyId = getUserSecurity().getCompanyId();
				campaignBO.setCompanyId(companyId);
			}

			List<String> userType = getUserSecurity().getRole();
			/*
			 * if (bindingResult.hasErrors()) { return "edit-campaign"; }
			 */
			if (0 < loginId && !userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(loginId);
				campaignBO.setAdminLoginBO(adminLoginBO);
			} else {
				long adminId = loginId;
				long userId = 0;
				if (0 != adminId) {
					userId = loginId;
				}
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(userId);
				campaignBO.setAdminLoginBO(adminLoginBO);
			}
			campaignBO.setExpectedRevenue(campaignBO.getExpectedRevenue());
			campaignBO.setBudgetedCost(campaignBO.getBudgetedCost());
			campaignBO.setExpectedResponse(campaignBO.getExpectedResponse());
			boolean status = campaignService.updateCampaign(campaignBO);
			if (status) {
				model.addAttribute("successMessage", messageSource.getMessage("Campaign.Update", null, null));
				return "redirect:/view-campaign";
			} else {
				model.addAttribute("errorMessage", "Doesnot Exists");
			}

			model.addAttribute("campaignBO", campaignBO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit-update Campaign has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit-update Campaign has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-campaign";
	}

	// Campaign Delete Part
	@RequestMapping(value = "delete-campaign", method = RequestMethod.GET)
	public String deleteCampaign(Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException {
		LOGGER.entry();
		try {
			String campaignId = request.getParameter("campaignid");
			CampaignBO campaignBO = campaignService.getObject(campaignId);
			if (null != campaignBO) {
				boolean status = campaignService.deleteCampaign(campaignId);
				if (status) {
					model.addAttribute("successMessage", messageSource.getMessage("Campaign.Delete", null, null));
					return "redirect:/view-campaign";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Campaign has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Campaign has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-campaign";
	}

	@RequestMapping(value = "/view-campaign-details", method = RequestMethod.GET)
	public String viewCampaignDetails(Model model, HttpServletRequest request, HttpSession session,
			@ModelAttribute("campaignlists") CampaignBO campaignBO) {

		LOGGER.entry();
		try {

			if (null != request.getParameter("campaignid")) {
				String id = request.getParameter("campaignid");
				int campaignId = Integer.parseInt(id);
				campaignBO.setCampaignId(campaignId);
			}
			if (0 < getUserSecurity().getCompanyId()) { // CompanyId
				long companyId = getUserSecurity().getCompanyId();
				campaignBO.setCompanyId(companyId);
			}

			campaignBO = campaignService.viewCampaignDetails(campaignBO);
			if (0 != campaignBO.getCampaignId()) {
				model.addAttribute("campaignlists", campaignBO);
			}
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("view campaign details: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view campaign details: Exception \t" + e);
			}
		} finally {
			LOGGER.exit();
		}
		return "view-campaign-details";
	}

	@RequestMapping(value = "/campaign-tracking-status", method = RequestMethod.GET)
	public String viewCampaignStatus(Model model, HttpServletRequest request, CampaignBO campaignBO)
			throws MySalesException, SerialException, SQLException {
		if (null != request.getParameter("campaignid")) {
			String id = request.getParameter("campaignid");
			int campaignId = Integer.parseInt(id);
			campaignBO.setCampaignId(campaignId);
		}
		if (0 < getUserSecurity().getCompanyId()) { // CompanyId
			long companyId = getUserSecurity().getCompanyId();
			campaignBO.setCompanyId(companyId);
		}
		campaignBO = campaignService.viewCampaignDetails(campaignBO);
		if (null != campaignBO) {
			// leadsBOList = leadsBO.getLeadsList();
			model.addAttribute("campaignlists", campaignBO);
			// model.addAttribute("leadsBOList", leadsBO.getLeadsUpdateVOList());
			model.addAttribute("campaignBOLists", campaignBO.getCampaignactivityList());
			model.addAttribute("taskManagementList", campaignBO.getTaskManagementBOList());
			
		} else {
			model.addAttribute("campaignlists", new CampaignBO());
			model.addAttribute("campaignBOLists", null);
		}
		return "campaign-tracking-status";
	}

	// Campaign Tracking status update part
	@RequestMapping(value = { "/campaign-tracking-status" }, method = RequestMethod.POST)
	public String registerBO(@RequestParam("uploadcampaign") MultipartFile uploadFile, String description, String date,
			int campaignId, String timeSlot, String endTimeSlot, Model model, HttpServletRequest request)
			throws IOException {

		String campaignfileName = null;
		String activityid;

		CampaignBO campaignBO = new CampaignBO();
		long loginId = getUserSecurity().getLoginId();
		String status = "Inprocess";
		campaignBO.setCampaignId(campaignId);
		campaignBO.setDescription(description);
		campaignBO.setDate(date);
		campaignBO.setTimeSlot(timeSlot);
		campaignBO.setEndTimeSlot(endTimeSlot);

		AdminLoginBO adminLoginBO = new AdminLoginBO();
		adminLoginBO.setId(loginId);
		campaignBO.setAdminLoginBO(adminLoginBO);
		campaignBO.setStatus(status);

		long count = campaignService.retriveCount();
		if (0 < count) {
			long Id = count + 1;
			activityid = String.valueOf(Id);
		} else {
			long Id = 1;
			activityid = String.valueOf(Id);
		}
		if (null != uploadFile) {
			String fileName = uploadFile.getOriginalFilename();
			String[] words = fileName.split("\\.");
			int length = words.length;
			System.out.println(length);
			String format = words[length - 1];
			String image = activityid + "." + format;
			if (!fileName.isEmpty()) {
				campaignfileName = fileuploads + activityid + "." + format;
				writeImageFile(campaignfileName, uploadFile);
				campaignBO.setUploadfile(image);

			}
			if (0 < getUserSecurity().getCompanyId()) { // CompanyId
				long companyId = getUserSecurity().getCompanyId();
				campaignBO.setCompanyId(companyId);
			}
			campaignBO = campaignService.saveTracking(campaignBO);
			return "redirect:/campaign-tracking-status?campaignId=" + campaignId;

		}
		return null;

	}

	private void writeImageFile(String campaignfileName, MultipartFile uploadFile) throws IOException {
		File file = new File(campaignfileName);
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(uploadFile.getBytes());

	}

	@RequestMapping(value = "statusDownload", method = RequestMethod.GET)
	public String getResumeDownload(@RequestParam("activityid") long updatecampaignId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		ModelAndView model = new ModelAndView();
		String filelocation = null;
		ActivityVO activityVO = campaignService.campaignTrackingStatus(updatecampaignId);
		if (null != activityVO) {
			filelocation = "C:\\usr\\local\\mysales\\campaign\\" + activityVO.getUploadfile();
		}

		String downloadPath = filelocation;
		File file = new File(downloadPath);
		InputStream is = new FileInputStream(file);
		response.setContentType("application/pdf");

		response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");

		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		os.flush();
		os.close();
		is.close();
		model.addObject("errorMessage", "file Download Successfully!");

		return "redirect:/campaign-tracking-status?campaignId";

	}
}