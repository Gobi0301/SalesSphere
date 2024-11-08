package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.ApproveProcurementBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.ProcurementBO;
import com.scube.crm.bo.RejectProcurementBO;
import com.scube.crm.bo.SupplierBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.ProcurementService;
import com.scube.crm.service.ProductService;
import com.scube.crm.service.SupplierService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.utils.UserRoles;

@Controller
@Scope("session")
@SessionAttributes("/admin")
public class ProcurementController extends ControllerUtils implements Serializable {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(ProcurementController.class);

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProductService productService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private ProcurementService service;

	@RequestMapping(value = "/create-procurement", method = RequestMethod.GET)
	public String getProcurement(Model model, HttpServletRequest request, HttpSession session)
			throws MySalesException, NumberFormatException, FileNotFoundException, SerialException, SQLException {
		LOGGER.entry();
		try {
			ProcurementBO bo = new ProcurementBO();
			SupplierBO supplierBO = new SupplierBO();
			InventoryBO serviceBO = new InventoryBO();

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				bo.setCompanyId(companyId);
				supplierBO.setCompanyId(companyId);
				serviceBO.setCompanyId(companyId);
			}

			List<SupplierBO> supplierList = new ArrayList<SupplierBO>();

			supplierList = supplierService.listSupplier(supplierBO);
			model.addAttribute("supplierLists", supplierList);
			model.addAttribute("procurementBO", bo);
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			AdminLoginBO adminLoginBo = new AdminLoginBO();
			// adminLoginBo.setUserType(userType);
			adminLoginBo.setId(loginId);
			adminLoginBo.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBo);
			if (0 < loginId && userType.contains("ROLE_ADMIN")) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(loginId);
				bo.setAdminLoginBO(adminLoginBO);
			}
			if (0 < loginId && userType.contains("ROLE_ADMIN") )  {

				List<InventoryBO> productBOList = new ArrayList<>();
				productBOList = productService.listOfProductByPagination(serviceBO);
				model.addAttribute("productlist", productBOList);
			}
			if (0 < loginId && userType.contains("ROLE_COMPANY") )  {

				List<InventoryBO> productBOList = new ArrayList<>();
				productBOList = productService.listOfProductByPagination(serviceBO);
				model.addAttribute("productlist", productBOList);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create-procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("create-procurement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "create-procurement";
	}

	@RequestMapping(value = "/create-procurement", method = RequestMethod.POST)
	public String postProcurement(@Valid @ModelAttribute("procurementBO") ProcurementBO bo, BindingResult bindingResult,
			Model model, HttpServletRequest request, HttpSession session) throws IOException {
		LOGGER.entry();
		try {
			SupplierBO supplierBO = new SupplierBO();
			InventoryBO serviceBO = new InventoryBO();
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				bo.setCompanyId(companyId);
				supplierBO.setCompanyId(companyId);
				serviceBO.setCompanyId(companyId);
			}
			if (bindingResult.hasErrors()) {
				List<SupplierBO> supplierList = new ArrayList<SupplierBO>();

				supplierList = supplierService.listSupplier(supplierBO);
				model.addAttribute("supplierLists", supplierList);
				List<InventoryBO> productBOList = new ArrayList<>();
				productBOList = productService.listOfProductByPagination(serviceBO);
				model.addAttribute("productlist", productBOList);
				return "create-procurement";
			}
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				bo.setCompanyId(companyId);
			}

			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			if (0 < loginId && userType.contains("ROLE_ADMIN")) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(loginId);
				bo.setAdminLoginBO(adminLoginBO);
			}

			bo.setCreatedBy(loginId);

			bo = service.insertProcurement(bo);
			if (null != bo) {
				model.addAttribute("successMessage", "procurement Successfully created");
				model.addAttribute("procurementBO", bo);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Add Procurement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-procurement";
	}

	@RequestMapping(value = "view-procurement", method = RequestMethod.GET)
	public String viewProcurement(Model model, HttpServletRequest request, HttpSession session) throws Exception {
		LOGGER.entry();
		String paging = null;
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("errormessage", request.getParameter("successMessage"));
		}
		try {
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}

			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}

			ProcurementBO bo = new ProcurementBO();

			InventoryBO serviceBO = new InventoryBO();
			SupplierBO supplierBO = new SupplierBO();
			//ProductBO productBO = new ProductBO();
			
			if(null!=request.getParameter("searchElement")&&!request.getParameter("searchElement").isEmpty()) {
				String serviceId = request.getParameter("searchElement");
				serviceBO.setServiceId(Integer.parseInt(serviceId));//;Integer.parseInt()
				bo.setProductServiceBO(serviceBO);
				model.addAttribute("searchElement", request.getParameter("searchElement"));
			}
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
					bo.setCompanyId(companyId);
					supplierBO.setCompanyId(companyId);
					serviceBO.setCompanyId(companyId);
			}
			List<InventoryBO> productBOList = new ArrayList<>();
			productBOList = productService.listOfProductByPagination(serviceBO);
			model.addAttribute("productlist", productBOList);

			List<SupplierBO> supplierList = new ArrayList<SupplierBO>();

			supplierList = supplierService.listSupplier(supplierBO);
			model.addAttribute("supplierLists", supplierList);
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			// adminLoginBO.setUserType(userType);
			adminLoginBO.setId(loginId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			if (0 < loginId && userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				adminLoginBO.setId(loginId);
				// adminLoginBO.setUserType(userType);
				bo.setAdminLoginBO(adminLoginBO);
			} else {
				model.addAttribute("errormessage", "Procurement List not Found");
			}
			procurementPagination(bo, paging, request, model);
			model.addAttribute("searchProcurement", new ProcurementBO());

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("View Procurement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-procurement";
	}

	@RequestMapping(value = "edit-procurement", method = RequestMethod.GET)
	public String getEditProcurement(Model model, HttpServletRequest request,
			@RequestParam("procurementId") int procurementId) throws FileNotFoundException, MySalesException {
		LOGGER.entry();
		try {
			ProcurementBO bo = new ProcurementBO();
			InventoryBO serviceBO = new InventoryBO();
			SupplierBO supplierBO = new SupplierBO();
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				bo.setCompanyId(companyId);
				supplierBO.setCompanyId(companyId);
				serviceBO.setCompanyId(companyId);
			}

			bo.setProcurementId(procurementId);
			if (0 < bo.getProcurementId()) {
				bo = service.getProcurementValues(bo);
			}

			if (null != bo) {

				List<InventoryBO> productBOList = new ArrayList<>();
				productBOList = productService.listOfProductByPagination(serviceBO);
				model.addAttribute("productlist", productBOList);

				List<SupplierBO> supplierList = new ArrayList<SupplierBO>();
				supplierList = supplierService.listSupplier(supplierBO);
				model.addAttribute("supplierLists", supplierList);

				model.addAttribute("bo", bo);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Procurement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-procurement";
	}

	@RequestMapping(value = "/edit-procurement", method = RequestMethod.POST)
	public String postEditProcurement(@Valid @ModelAttribute("procurementBO") ProcurementBO bo,
			BindingResult bindingResult, Model model, HttpServletRequest request, HttpSession session)
			throws IOException {
		LOGGER.entry();
		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId();
				bo.setCompanyId(companyId);
			}
			boolean status = service.procurementValueUpdate(bo);
			if (status) {
				model.addAttribute("successMessage", "Procurement Updated Successfully");
				return "redirect:/view-procurement";
			}

			else {
				model.addAttribute("errorMessage", "Doesnot Exists");
			}
			model.addAttribute("procurementBO", bo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit-update procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit-update procurement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-procurement";
	}

	@RequestMapping(value = "/delete-Procurement", method = RequestMethod.GET)
	public String getDeleteMethod(@RequestParam("procurementId") int procurementId, Model model) {
		LOGGER.entry();
		try {
			int count = service.deleteProcurementValues(procurementId);
			if (count > 0) {
				model.addAttribute("successMessage", "Deleted Successfully");
			} else {
				model.addAttribute("errorMessage", "Deleted Not Successfully");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Procurement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-procurement";
	}

	@RequestMapping(value = "/approve-procurement", method = RequestMethod.GET)
	public String getApproveProcurement(Model model, HttpServletRequest request, HttpSession session)
			throws MySalesException, NumberFormatException, FileNotFoundException, SerialException, SQLException {
		LOGGER.entry();
		try {
			ApproveProcurementBO approveBO = new ApproveProcurementBO();
			String procurementId = request.getParameter("procurementId");
			long id = Long.parseLong(procurementId);
			ProcurementBO bo = new ProcurementBO();
			bo.setProcurementId(id);
			bo = service.selectParticularView(bo);
			approveBO.setProcurementBO(bo);
			model.addAttribute("expectedDate",bo.getExpectedDate());
			model.addAttribute("supplierId",bo.getSupplierBO().getSupplierId());
			model.addAttribute("serviceId",bo.getProductServiceBO().getServiceId());			
			 model.addAttribute("procurementId",procurementId);
			model.addAttribute("approveBO", approveBO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Approve Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Approve Procurement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "approve-procurement";

	}

	@RequestMapping(value = "/approve-procurement", method = RequestMethod.POST)
	public String postApproveProcurement(@Valid @ModelAttribute("approveBO") ApproveProcurementBO approveBO,
			BindingResult bindingResult, Model model, HttpServletRequest request, HttpSession session)
			throws IOException {
		LOGGER.entry();
		try {
			String serviceName=request.getParameter("serviceId");
			String supplierName=request.getParameter("supplierId");
			String expectDate=request.getParameter("expectedDate");
			String procurement=request.getParameter("procurementId");
			ProcurementBO bo = new ProcurementBO();
			if(null !=procurement) {
				long procurementId  = Long.parseLong(procurement);
				bo.setProcurementId(procurementId);
				}
			approveBO.setProcurementBO(bo);
			approveBO.setExpectedDate(expectDate);
			InventoryBO inventoryBO = new InventoryBO ();
			SupplierBO supplierBO = new SupplierBO();
			if(null !=serviceName) {
			long serviceId = Long.parseLong(serviceName);
			inventoryBO.setServiceId(serviceId);
			}if(null !=supplierName) {
				long supplierId = Long.parseLong(supplierName);
			supplierBO.setSupplierId(supplierId);
			}
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				bo.setCompanyId(companyId);
				approveBO.setCompanyId(companyId);
			}			
			approveBO.setProductServiceBO(inventoryBO);
			approveBO.setSupplierBO(supplierBO);
			approveBO = service.approveProcurement(approveBO);
			if (null != approveBO) {
				model.addAttribute("successMessage", "Successfully Approved");
				model.addAttribute("approveBOs", approveBO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Approve post Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Approve post Procurement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-procurement";
	}

	@RequestMapping(value = "/reject-procurement", method = RequestMethod.GET)
	public String getRejectProcurement(Model model, HttpServletRequest request, HttpSession session)
			throws MySalesException, NumberFormatException, FileNotFoundException, SerialException, SQLException {
		String procurementId = request.getParameter("procurementId");
		long id = Long.parseLong(procurementId);
		ProcurementBO bo = new ProcurementBO();
		RejectProcurementBO rejectBO = new RejectProcurementBO();
		bo.setProcurementId(id);
		rejectBO.setProcurementBO(bo);
		bo = service.selectParticularView(bo);
		model.addAttribute("expectedDate",bo.getExpectedDate());
		model.addAttribute("supplierId",bo.getSupplierBO().getSupplierId());
		model.addAttribute("serviceId",bo.getProductServiceBO().getServiceId());			
		 model.addAttribute("procurementId",procurementId);
		model.addAttribute("rejectBO", rejectBO);

		model.addAttribute("rejectBO", rejectBO);

		return "reject-procurement";
	}

	@RequestMapping(value = "/reject-procurement", method = RequestMethod.POST)
	public String postReject(@Valid @ModelAttribute("rejectBO") RejectProcurementBO bo1, BindingResult bindingResult,
			Model model, HttpServletRequest request, HttpSession session) throws IOException {
		LOGGER.entry();
		try {String serviceName=request.getParameter("serviceId");
		String supplierName=request.getParameter("supplierId");
		String procurement=request.getParameter("procurementId");
		ProcurementBO bo = new ProcurementBO();
		if(null !=procurement) {
			long procurementId  = Long.parseLong(procurement);
			bo.setProcurementId(procurementId);
			}
		bo1.setProcurementBO(bo);
		InventoryBO inventoryBO = new InventoryBO ();
		SupplierBO supplierBO = new SupplierBO();
		if(null !=serviceName) {
		long serviceId = Long.parseLong(serviceName);
		inventoryBO.setServiceId(serviceId);
		}if(null !=supplierName) {
			long supplierId = Long.parseLong(supplierName);
		supplierBO.setSupplierId(supplierId);
		}
		bo1.setProductServiceBO(inventoryBO);
		bo1.setSupplierBO(supplierBO);
		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			bo.setCompanyId(companyId);
			bo1.setCompanyId(companyId);
		}	
			bo1 = service.createReject(bo1);
			if (null != bo1) {
				model.addAttribute("successMessage", " Successfully rejected");
				model.addAttribute("rejectBO", bo1);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Reject Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Reject Procurement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-procurement";
	}

	@RequestMapping(value = "/particular-view-procurement", method = RequestMethod.GET)
	public String getParticularViewProcurement(Model model, HttpServletRequest request,
			@RequestParam("procurementId") int procurementId) throws FileNotFoundException, MySalesException {
		LOGGER.entry();
		try {
			ProcurementBO bo2 = new ProcurementBO();
            
			bo2 = service.selectParticularView(bo2);
			model.addAttribute("particularlist", bo2);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("particular-view-procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("particular-view-procurement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "particular-view-procurement";
	}

	// Ajax Method
	@RequestMapping(value = "/getProductProfile", method = RequestMethod.GET)
	@ResponseBody
	public ProcurementBO getProductProfile(@RequestParam String procurementId) throws Exception {
		ProcurementBO procurementBO = new ProcurementBO();
		LOGGER.entry();
		try {
			long id = Long.parseLong(procurementId);

			procurementBO = service.getProfile(id);

			if (procurementBO != null) {
				String quantityOfProducts = procurementBO.getQuantityOfProducts();
				int totalCost = procurementBO.getTotalCost();

				return procurementBO;

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getProductProfile has failed:" + ex.getMessage());
			}
			LOGGER.info("getProductProfile has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return null;

	}

	@RequestMapping(value = "/procurement-tracking-status", method = RequestMethod.GET)
	public String viewActivityStatus(Model model, HttpServletRequest request)
			throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		ProcurementBO bo2 = new ProcurementBO();
		
		model.addAttribute("procurementBO", bo2);
		try {
			long procurementId = 0;

			if (null != request.getParameter("procurementId")) {
				final String id = request.getParameter("procurementId");
				procurementId = Long.parseLong(id);
				bo2.setProcurementId(procurementId);
				
			}
			if (0 < getUserSecurity().getCompanyId()) { // CompanyId
				long companyId = getUserSecurity().getCompanyId();
				bo2.setCompanyId(companyId);
			}
			bo2 = service.selectParticularView(bo2);
			if (null != bo2) {
				// leadsBOList = leadsBO.getLeadsList();
				model.addAttribute("particularlist", bo2);
				model.addAttribute("procurementBOLists", bo2.getProcurementactivityList());
			} else {
				model.addAttribute("particularlist", new LeadsBO());
				model.addAttribute("leadsBOList", null);
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("procurement-tracking-status has failed:" + ex.getMessage());
			}
			LOGGER.info("procurement-tracking-status has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		
		return "procurement-tracking-status";
	}

	@RequestMapping(value = "/procurement-tracking-status", method = RequestMethod.POST)
	public String registerBO(@ModelAttribute("procurementBO") ProcurementBO procurementBO,long procurementId, String createdDate,String modifyDate,String description ,String endTimeSlot, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ProcurementBO bo = new ProcurementBO();
		if (0 < getUserSecurity().getCompanyId()) { // CompanyId
			long companyId = getUserSecurity().getCompanyId();
			bo.setCompanyId(companyId);
		}
		bo.setCreatedDate(createdDate);
		bo.setModifyDate(modifyDate);
		bo.setDescription(description);
		bo.setProcurementId(procurementId);
		bo.setEndTimeSlot(endTimeSlot);
		bo = service.saveTracking(bo);

		return "redirect:/procurement-tracking-status?procurementId=" + bo.getProcurementId();

	}

	@RequestMapping(value = "/search-procurement", method = RequestMethod.POST)
	public String searchProcurement(@ModelAttribute("searchProcurement") ProcurementBO procurementBO2,
			HttpServletRequest request, HttpSession session, Model model)
			throws MySalesException, SerialException, SQLException {
	
		List<ProcurementBO> procurementList = new ArrayList<>();
		long count = 0;
		long totalaccountCount = 0;
		int page = 1;
		int maxRecord = 10;
		InventoryBO serviceBO = new InventoryBO();
		InventoryBO productBO = new InventoryBO();
		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId();
			productBO.setCompanyId(companyId);
			procurementBO2.setCompanyId(companyId);
		}
		if(null!=procurementBO2.getProductServiceBO().getServiceName()&& !procurementBO2.getProductServiceBO().getServiceName().isEmpty()) {
			model.addAttribute("searchElement", procurementBO2.getProductServiceBO().getServiceName());
		}
		
		
		if (null != procurementBO2.getProductServiceBO()
				&& null != procurementBO2.getProductServiceBO().getServiceName()
				&& !procurementBO2.getProductServiceBO().getServiceName().isEmpty()) {

			String serviceId = procurementBO2.getProductServiceBO().getServiceName();
			
			Integer productId = Integer.parseInt(serviceId);
			productBO.setServiceId(productId);

			procurementBO2.setProductServiceBO(productBO);
		}
		if(productBO.getServiceId()==0)
		{
			 return "redirect:/view-procurement";
		}

		List<InventoryBO> productBOList = new ArrayList<>();
		productBOList = productService.listOfProductByPagination(productBO);
		long procurementtotalCount = 0;
		procurementtotalCount=productBOList.size();
		model.addAttribute("productlist", productBOList);
		String status =request.getParameter("procurementStatus");
		if(status != null && status.equalsIgnoreCase("Approved")) {
			List<ApproveProcurementBO> approvelist = new ArrayList<>();
			
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
			
			
			approvelist=service.getapprovedprocurementlist(companyId);
			long totalCount = 0;
			totalCount=approvelist.size();
			model.addAttribute("approvedCount",totalCount);
			if(approvelist.size()>0&&null!=approvelist&&!approvelist.isEmpty()) {
			model.addAttribute("approvedprocurementList",
					PaginationClass.paginationLimitedRecords(page, approvelist, maxRecord, totalCount));
		}else {
			model.addAttribute("errorMessage", "No Records Found");
		}
			 return "view-procurement"; 
		}else if(status != null && status.equalsIgnoreCase("Rejected")) {
			List<RejectProcurementBO> rejectedlist = new ArrayList<>();
			long companyId = getUserSecurity().getCompanyId();
			rejectedlist=service.getrejectedprocurementlist(companyId);
			long totalCount = 0;
			totalCount=rejectedlist.size();
			model.addAttribute("rejectCount",totalCount);
			if(rejectedlist.size()>0&&null!=rejectedlist&&!rejectedlist.isEmpty()) {
			model.addAttribute("rejectedprocurementList",
					PaginationClass.paginationLimitedRecords(page, rejectedlist, maxRecord, totalCount));
			}else {
				model.addAttribute("errorMessage", "No Records Found");
			}
			 return "view-procurement"; 
		}
		
		String paging = null;
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
			page = Integer.parseInt(paging);
		}
		count = service.procurment(procurementBO2);
		if (0 != count) {
			totalaccountCount = count;
			
		}

		else {
			model.addAttribute("errorMessage", "No Records Found");
			return "view-procurement";
		}
		
		
		procurementList = service.searchByValue(procurementBO2);
	
			if (null != procurementList && !procurementList.isEmpty() && procurementList.size() > 0) {
				model.addAttribute("procurementtotalCount", procurementList.size());
				model.addAttribute("procurementList",
						PaginationClass.paginationLimitedRecords(page, procurementList, maxRecord, totalaccountCount));
				model.addAttribute("searchProcurement", new ProcurementBO());
				return "view-procurement";
		} 
			  else { model.addAttribute("errorMessage", "No Records Found"); 
			  return "view-procurement"; }
			 
	}

	private void procurementPagination(ProcurementBO procurementBO2, String paging, HttpServletRequest request,
			Model model) throws IOException, MySalesException {

		// BaseBO baseBO=new BaseBO();
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
		count = service.procurment(procurementBO2);
		if (0 != count) {
			totalCount = count;
			model.addAttribute("totalCount", totalCount);
		} else {
			model.addAttribute("errorMessage", "Record not Found!");
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		procurementBO2.setRecordIndex(startingRecordIndex);
		procurementBO2.setMaxRecord(maxRecord);
		procurementBO2.setPagination("pagination");
		List<ProcurementBO> procurementBOList = new ArrayList<>();
		procurementBOList = service.getListProcurement(procurementBO2);
		if (null != procurementBOList && !procurementBOList.isEmpty() && procurementBOList.size() > 0) {
			model.addAttribute("procurementList",
					PaginationClass.paginationLimitedRecords(page, procurementBOList, maxRecord, totalCount));
		}
		else {
			model.addAttribute("errorMessage", "No Records Found");
		}
	}

	private int paginationPageValues(int pageid, int recordPerPage) {
		int pageRecords = 0;
		if (pageid == 1) {
			pageRecords = 0;
		} else {
			pageRecords = (pageid - 1) * recordPerPage;
		}
		return pageRecords;
	}

}
