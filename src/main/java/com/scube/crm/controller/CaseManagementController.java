package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scube.crm.bo.CaseManagementBO;
import com.scube.crm.bo.ClientBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.CaseManagementService;
import com.scube.crm.service.CustomerService;
import com.scube.crm.service.ProductService;
import com.scube.crm.utils.PaginationClass;

@Controller
public class CaseManagementController extends ControllerUtils implements Serializable {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(CaseManagementController.class);

	private static final long serialVersionUID = 1L;

	@Autowired
	CaseManagementService service;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	List<ClientBO> clientList = new ArrayList<ClientBO>();

	@RequestMapping(value = "/create-casemanagement", method = RequestMethod.GET)
	public String getCreateCase(Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		CaseManagementBO caseBO = new CaseManagementBO();
		InventoryBO serviceBO = new InventoryBO();
		ClientBO customerBo = new ClientBO();

		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			caseBO.setCompanyId(companyId);
			customerBo.setCompanyId(companyId);
			serviceBO.setCompanyId(companyId);
		}

		List<InventoryBO> productBOList = new ArrayList<>();
		productBOList = productService.listOfProductByPagination(serviceBO);
		model.addAttribute("productlist", productBOList);

		List<ClientBO> customerBOList = new ArrayList<>();
		customerBOList = customerService.listOfCustomerByPagination(customerBo);
		model.addAttribute("listClients", customerBOList);
		model.addAttribute("caseBO", caseBO);

		return "create-casemanagement";

	}

	@RequestMapping(value = "/create-casemanagement", method = RequestMethod.POST)
	public String postCreateCase(@Valid @ModelAttribute("caseBO") CaseManagementBO caseBO, BindingResult bindingResult,
			Model model, HttpServletRequest request, HttpSession session) {
		LOGGER.entry();
		try {
			if (bindingResult.hasErrors()) {
				return "create-casemanagement";
			}
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				caseBO.setCompanyId(companyId);
			}
			caseBO = service.createCase(caseBO);
			if (null != caseBO) {
				model.addAttribute("successMessage", "case Successfully created");
				model.addAttribute("caseBO", caseBO);
			}
			return "redirect:/view-casemanagement";
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add CaseManagement has failed:" + ex.getMessage());
			}
			LOGGER.info("Add CaseManagement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-casemanagement";
	}

	@RequestMapping(value = "view-casemanagement", method = RequestMethod.GET)
	public String viewCase(Model model, HttpServletRequest request, HttpSession session) throws Exception {

		if (null != request.getParameter("successMessage")) {
			model.addAttribute("errormessage", request.getParameter("successMessage"));
		}
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId = getUserSecurity().getCompanyId();
			String paging = null;
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}
			CaseManagementBO caseBO = new CaseManagementBO();
			InventoryBO serviceBO = new InventoryBO();
			ClientBO customerBo = new ClientBO();

			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				caseBO.setCompanyId(companyId);
				serviceBO.setCompanyId(companyId);
				customerBo.setCompanyId(companyId);
			}
			if (null != request.getParameter("searchElement")) {
				String caseOrigin = request.getParameter("searchElement");
				caseBO.setCaseOrigin(caseOrigin);
				model.addAttribute("searchElement", request.getParameter("searchElement"));
			}

			/*
			 * List<InventoryBO> productBOList = new ArrayList<>(); productBOList =
			 * productService.listOfProductByPagination(serviceBO);
			 * model.addAttribute("productlist", productBOList);
			 * 
			 * List<ClientBO> customerBOList = new ArrayList<>(); customerBOList =
			 * customerService.listOfCustomerByPagination(customerBo);
			 * model.addAttribute("listClients", customerBOList);
			 */
			casemanagementpagination(caseBO, paging, request, model);
			model.addAttribute("searchCase", caseBO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View CaseManagement has failed:" + ex.getMessage());
			}
			LOGGER.info("View CaseManagement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "view-casemanagement";
	}

	private void casemanagementpagination(CaseManagementBO caseBO, String paging, HttpServletRequest request,
			Model model) {
		long count = 0;
		long totalCount = 0;
		int page = 1;
		int maxRecord = 10;

		if (null != paging) {
			page = Integer.parseInt(paging);
		}
		count = service.searchcount(caseBO);
		if (0 != count) {
			totalCount = count;
			model.addAttribute("totalCount", totalCount);
		} else {
			model.addAttribute("errorMessage", "Record not Found!");
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		caseBO.setRecordIndex(startingRecordIndex);
		caseBO.setMaxRecord(maxRecord);
		caseBO.setPagination("pagination");
		List<CaseManagementBO> caseList = new ArrayList<CaseManagementBO>();
		caseList = service.getListCase(caseBO);
		if (null != caseList && !caseList.isEmpty() && caseList.size() > 0) {
			model.addAttribute("caseLists",
					PaginationClass.paginationLimitedRecords(page, caseList, maxRecord, totalCount));
		} else {
			model.addAttribute("errorMessage", "Record not Found!");
		}

	}

	@RequestMapping(value = "/search-case", method = RequestMethod.POST)
	public String searchCaseManagement(Model model, @ModelAttribute("searchCase") CaseManagementBO caseBO,
			HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId = getUserSecurity().getCompanyId();
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			long count = 0;
			long totalsearchCount = 0;
			int page = 1;
			int maxRecord = 10;

			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				caseBO.setCompanyId(companyId);
			}
			if (null != caseBO.getCaseOrigin() && !caseBO.getCaseOrigin().isEmpty()) {
				model.addAttribute("searchElement", caseBO.getCaseOrigin());
			}
			count = service.searchcount(caseBO);
			if (0 != count && count > 0) {
				totalsearchCount = count;
				model.addAttribute("totalsearchCount", totalsearchCount);
			} else {
				model.addAttribute("errorMessage", "Record not Found!");
				return "view-casemanagement";
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			caseBO.setRecordIndex(startingRecordIndex);
			caseBO.setMaxRecord(maxRecord);
			caseBO.setPagination("pagination");
			List<CaseManagementBO> caseList = new ArrayList<CaseManagementBO>();
			caseList = service.searchCase(caseBO);
			if (caseList != null && caseList.size() > 0 && !caseList.isEmpty()) {
				model.addAttribute("caseLists",
						PaginationClass.paginationLimitedRecords(page, caseList, maxRecord, totalsearchCount));
			} else {
				model.addAttribute("errorMessage", "Record not Found!");
			}

			return "view-casemanagement";
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search CaseManagement has failed:" + ex.getMessage());
			}
			LOGGER.info("Search CaseManagement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-casemanagement";
	}

	private int paginationPageValues(int pageid, int maxRecord) {
		int pageRecords = 0;
		if (pageid == 1) {
			pageRecords = 0;
		} else {
			pageRecords = (pageid - 1) * maxRecord + 1;
			pageRecords = pageRecords - 1;
		}
		return pageRecords;

	}

	@RequestMapping(value = "edit-casemanagement", method = RequestMethod.GET)
	public String getEditCase(Model model, HttpServletRequest request, @RequestParam("caseId") int caseId)
			throws FileNotFoundException, MySalesException {
		LOGGER.entry();
		try {
			CaseManagementBO caseBO = new CaseManagementBO();
			InventoryBO serviceBO = new InventoryBO();
			ClientBO customerBo = new ClientBO();

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId();
				caseBO.setCompanyId(companyId);
				serviceBO.setCompanyId(companyId);
				customerBo.setCompanyId(companyId);
			}
			caseBO.setCaseId(caseId);

			List<InventoryBO> productBOList = new ArrayList<>();
			productBOList = productService.listOfProductByPagination(serviceBO);
			model.addAttribute("productlist", productBOList);

			List<ClientBO> customerBOList = new ArrayList<>();
			customerBOList = customerService.listOfCustomerByPagination(customerBo);
			model.addAttribute("listClients", customerBOList);
			if (0 < caseBO.getCaseId()) {
				caseBO = service.getCaseValue(caseBO);
			}

			model.addAttribute("caseBOs", caseBO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit CaseManagement has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit CaseManagement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit_casemanagement";
	}

	@RequestMapping(value = "/edit-casemanagement", method = RequestMethod.POST)
	public String postEditCase(@Valid @ModelAttribute("caseBO") CaseManagementBO caseBO, BindingResult bindingResult,
			Model model, HttpServletRequest request, HttpSession session) throws IOException {
		LOGGER.entry();
		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId();
				caseBO.setCompanyId(companyId);
			}
			boolean status = service.caseValueUpdate(caseBO);
			if (status) {
				model.addAttribute("successMessage", "casemanagement Updated Successfully");
				// model.addAttribute("caseBO", caseBO);
				return "redirect:/view-casemanagement";
			} else {
				model.addAttribute("errorMessage", "Doesnot Exists");
			}
			model.addAttribute("caseBO", caseBO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit-update CaseManagement has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit-update CaseManagement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit_casemanagement";

	}

	@RequestMapping(value = "/delete-casemanagement", method = RequestMethod.GET)
	public String getDeleteMethod(@RequestParam("caseId") int caseId, Model model) {
		LOGGER.entry();
		try {
			int count = service.deleteCaseValues(caseId);
			if (count > 0) {
				model.addAttribute("successMessage", "Deleted Successfully");
			} else {
				model.addAttribute("errorMessage", "Deleted not Successfully");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete CaseManagement has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete CaseManagement has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-casemanagement";
	}

	@ResponseBody
	@RequestMapping(value = "/getCustomerProfile", method = RequestMethod.GET)
	public List<ClientBO> getProductProfile(@RequestParam String customerId) throws Exception {
		System.out.println(customerId);
		long id = Long.parseLong(customerId);

		clientList = customerService.getCustomerProfile(id);

		if (null != clientList && !clientList.isEmpty() && 0 < clientList.size()) {

		}
		return clientList;

	}

	@ResponseBody
	@RequestMapping(value = "/getProduct", method = RequestMethod.GET)
	public String getProduct(@RequestParam("serviceids") String serviceId) throws Exception {
		long i = Integer.parseInt(serviceId);
		String warrantyDate = null;
		for (ClientBO Clientbo : clientList) {
			if (Clientbo.getProductServieBO().getServiceId() == i) {
				warrantyDate = Clientbo.getWarrantyDate();
				try {
					LocalDate date = LocalDate.parse(warrantyDate).plusYears(1);
					String d = date.toString();
					System.out.println(d);
					return d;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return null;
	}

}
