package com.scube.crm.controller;

import java.io.FileNotFoundException;
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
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.ProductTypesBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.ProductService;
import com.scube.crm.service.ProductTypeService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.utils.UserRoles;

@Controller
@Scope("session")
@SessionAttributes("/admin")
public class ProductServiceController extends ControllerUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(AdminController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	ProductTypeService service;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/create-productservice", method = RequestMethod.GET)
	public String getCreateService(Model model, HttpServletRequest request, HttpSession session) throws Exception {

		
		ProductTypesBO producttypesbo=new ProductTypesBO();
		if(0<getUserSecurity().getCompanyId()) {
			long companyId=getUserSecurity().getCompanyId();
			producttypesbo.setCompanyId(companyId);
		}
		List<ProductTypesBO> listtypes = service.selectAlltypes(producttypesbo);
		model.addAttribute("producttypesList", listtypes);

		InventoryBO productServiceBO = new InventoryBO();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		AdminLoginBO adminLoginBO = new AdminLoginBO();
		// adminLoginBO.setUserType(userType);
		adminLoginBO.setId(loginId);
		adminLoginBO.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBO);
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			adminLoginBO.setId(loginId);
			productServiceBO.setAdminLoginBO(adminLoginBO);
		}
		model.addAttribute("productServiceBO", productServiceBO);
		return "create-productservice";

	}

	@RequestMapping(value = "/create-productservice", method = RequestMethod.POST)
	public String postCreateServices(@Valid @ModelAttribute("productServiceBO") InventoryBO productServiceBO,
			BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws FileNotFoundException {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}

		if (bindingResult.hasErrors()) {
			return "create-productservice";
		}
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			adminLoginBO.setId(loginId);
			productServiceBO.setAdminLoginBO(adminLoginBO);
		}
		if(0< getUserSecurity().getCompanyId()) {
			   long companyId=getUserSecurity().getCompanyId(); // Company create
			   productServiceBO.setCompanyId(companyId);
		}
		
		productServiceBO.setCreatedBy(loginId);
		if (null != productServiceBO) {
			InventoryBO productBO = productService.createServices(productServiceBO);
			if (0 != productBO.getServiceId()) {
				model.addAttribute("successMessage", messageSource.getMessage("Product.Creation", null, null));
				return "redirect:/view-productservice";
			}
		}
		return "create-productservice";
	}

	@RequestMapping(value = "view-productservice", method = RequestMethod.GET)
	public String viewService(Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException {
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		String paging = null;

		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
		}
		
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		
		ProductTypesBO productTypesbO = new ProductTypesBO();
		InventoryBO inventoryBO = new InventoryBO();
		InventoryBO serviceBO = new InventoryBO();
		if(null!=request.getParameter("searchElement")) {
			String serviceName = request.getParameter("searchElement");
			inventoryBO.setServiceName(serviceName);
			model.addAttribute("searchElement", request.getParameter("searchElement"));
		}
		if(null!=request.getParameter("searchProductType") && !request.getParameter("searchProductType").isEmpty()) {
			String productTypes = request.getParameter("searchProductType");
			productTypesbO.setProductTypesId(Long.parseLong(productTypes));
			serviceBO.setProductTypesbO(productTypesbO);
			model.addAttribute("searchProductType", request.getParameter("searchProductType"));
		}
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			productTypesbO.setCompanyId(companyId);
		}
		// ProductTypes Retrieve
		List<ProductTypesBO> listtypes = service.selectAlltypes(productTypesbO);
		model.addAttribute("producttypesList", listtypes);

		if(null!=request.getParameter("searchElement")) {
			String serviceName = request.getParameter("searchElement");
			serviceBO.setServiceName(serviceName);
			model.addAttribute("searchElement", request.getParameter("searchElement"));
		}
		AdminLoginBO adminLoginBO = new AdminLoginBO();
		// adminLoginBO.setUserType(userType);
		adminLoginBO.setId(loginId);
		adminLoginBO.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBO);
		if (0 < loginId && userType.contains(UserRoles.ROLE_ADMIN.toString())) {
			adminLoginBO.setId(loginId);
			// adminLoginBO.setUserType(userType);
			serviceBO.setAdminLoginBO(adminLoginBO);
		}
		//if (0 < loginId && userType.contains(UserRoles.ROLE_COMPANY.toString())) {
		/*
		 * if (0 < loginId ) { adminLoginBO.setId(loginId); //
		 * adminLoginBO.setUserType(userType); serviceBO.setAdminLoginBO(adminLoginBO);
		 * serviceBO.setCompanyId(loginId); }
		 */
		
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			serviceBO.setCompanyId(companyId);
		}
//		serviceList = productService.listservice(serviceBO);

//		if (null != serviceList && !serviceList.isEmpty() && serviceList.size() > 0) {
			// model.addAttribute("serviceList", serviceList);
			model.addAttribute("searchProduct", new InventoryBO());
//		}
		model.addAttribute("searchProduct", new InventoryBO());
		

		// Pagination
		productPagination(serviceBO, paging, session, request, model);

		// model.addAttribute("serviceBO", serviceBO);
		return "view-productservice";
	}

	private void productPagination(InventoryBO productServiceBO, String paging, HttpSession session,
			HttpServletRequest request, Model model) {

		long countOfProduct = 0;
		long totalCountOfProduct = 0;
		int page = 1;
		int maxRecord =10;

		if (null != paging) {
			page = Integer.parseInt(paging);
		}

		countOfProduct = productService.getCountOfProduct(productServiceBO);
		if (0 < countOfProduct) {
			totalCountOfProduct = countOfProduct;
			model.addAttribute("totalCountOfProduct",totalCountOfProduct);
		}

		int startingRecordOfProduct = paginationPageValues(page, maxRecord);
		productServiceBO.setRecordIndex(startingRecordOfProduct);
		productServiceBO.setMaxRecord(maxRecord);
		List<InventoryBO> productBOList = new ArrayList<>();
		productBOList = productService.listOfProductByPagination(productServiceBO);
		if (null != productBOList && !productBOList.isEmpty() && 0 < productBOList.size()) {
			// model.addAttribute("serviceList", productBOList);
			model.addAttribute("serviceList",
					PaginationClass.paginationLimitedRecords(page, productBOList, maxRecord, totalCountOfProduct));
		}
		else {
			model.addAttribute("errorMessage", "No Record Found!");
		}

	}

	private int paginationPageValues(int page, int maxRecord) {
		// TODO Auto-generated method stub

		int recordOfPage = 0;
		if (page == 1) {
			recordOfPage = 0;
		} else {
			recordOfPage = (page - 1) * maxRecord + 1;
			recordOfPage = recordOfPage - 1;
		}

		return recordOfPage;
	}

	@RequestMapping(value = "/search-productservice", method = RequestMethod.POST)
	public String searchProduct(@ModelAttribute("searchProduct") InventoryBO productServiceBO,
			HttpServletRequest request, HttpSession session, Model model)
			throws MySalesException, SerialException, SQLException {
		
		long adminId = getUserSecurity().getLoginId();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		
		AdminLoginBO adminLoginBO = new AdminLoginBO();
		// adminLoginBO.setUserType(userType);
		adminLoginBO.setId(adminId);
		adminLoginBO.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBO);
		productServiceBO.setId(adminId);
		// InventoryBO productServiceBo=new InventoryBO();
		// productServiceBo.setServiceName(productServiceBO.getServiceName());
		List<InventoryBO> serviceList = new ArrayList<InventoryBO>();
		// productTypes search

		String producttypesIds = productServiceBO.getProductTypesbO().getProductTypes();
		long id = 0;
		if (null != producttypesIds && !producttypesIds.isEmpty()) {
			id = Long.parseLong(producttypesIds);
		}
		ProductTypesBO bo = new ProductTypesBO();
		bo.setProductTypesId(id);
		productServiceBO.setProductTypesbO(bo);

		long totalsearchConutOfProduct = 0;
		long countOfProduct = 0;
		int page = 1;
		int maxRecord = 10;
		
		ProductTypesBO productTypesbO = new ProductTypesBO();
		
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {				// Company
			productServiceBO.setCompanyId(companyId);
			productTypesbO.setCompanyId(companyId);
		}
		if(null!=productServiceBO.getServiceName()&& !productServiceBO.getServiceName().isEmpty()) {
			model.addAttribute("searchElement", productServiceBO.getServiceName());
		}
		if(null!=producttypesIds&& !producttypesIds.isEmpty()) {
			model.addAttribute("searchProductType", producttypesIds);
		}
		countOfProduct = productService.countOfProductBySearch(productServiceBO);
		if (0 < countOfProduct) {
			totalsearchConutOfProduct = countOfProduct;
			model.addAttribute("searchproductcount", totalsearchConutOfProduct);
		}else {
			model.addAttribute("errorMessage", "No Record Found!");
			return "view-productservice";
		}

		int startinRecordOfProduct = paginationPageValues(page, maxRecord);
		productServiceBO.setRecordIndex(startinRecordOfProduct);
		productServiceBO.setMaxRecord(maxRecord);

		serviceList = productService.listservice(productServiceBO);

		if (productServiceBO.getServiceName() == null) {
			ProductTypesBO productTypesBo = new ProductTypesBO();
			productTypesBo.setProductTypes(productServiceBO.getProductTypesbO().getProductTypes());
			productTypesBo.setId(productServiceBO.getProductTypesbO().getProductTypesId());
			productServiceBO.setProductTypesbO(productTypesBo);
		}

		// ProductTypes Dropdown
		
		List<ProductTypesBO> listtypes = service.selectAlltypes(productTypesbO);
		model.addAttribute("producttypesList", listtypes);

		if (null != serviceList && 0 < serviceList.size()) {
			model.addAttribute("serviceList", serviceList);
			model.addAttribute("serviceList",
					PaginationClass.paginationLimitedRecords(page, serviceList, maxRecord, totalsearchConutOfProduct));
			model.addAttribute("searchProduct", productServiceBO);
			return "view-productservice";
		} else {
			model.addAttribute("errorMessage", "No Records Found");	
			return "redirect:/view-productservice";
		}
	}

	@RequestMapping(value = "edit-productservice", method = RequestMethod.GET)
	public String editProduct(Model model, HttpServletRequest request, @RequestParam("serviceId") Integer serviceId)
			throws FileNotFoundException, MySalesException {
		LOGGER.entry();
		InventoryBO productServiceBO = new InventoryBO();
		ProductTypesBO productTypesBO=new ProductTypesBO();
		if(0<getUserSecurity().getCompanyId()) {	//COMPANYID
			long companyId=getUserSecurity().getCompanyId();
			productTypesBO.setCompanyId(companyId);
		}
		
		// product Types
				List<ProductTypesBO> listtypes = service.selectAlltypes(productTypesBO);
				model.addAttribute("producttypesList", listtypes);
		
		long adminId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		AdminLoginBO adminLoginBO = new AdminLoginBO();
		// adminLoginBO.setUserType(userType);
		adminLoginBO.setId(adminId);
		adminLoginBO.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBO);
		productServiceBO.setId(adminId);
		productServiceBO.setServiceId(serviceId);
		if (0 < productServiceBO.getServiceId()) {
			productServiceBO = productService.getServiceObject(productServiceBO);
		}
		
		if (null != productServiceBO) {
			model.addAttribute("productBO", productServiceBO);
			model.addAttribute("productTypesId",productServiceBO.getProductTypesbO().getProductTypesId());
			model.addAttribute("productTypes",productServiceBO.getProductTypesbO().getProductTypes());
		}
		return "edit-productservice";
	}

	@RequestMapping(value = "edit-productservice", method = RequestMethod.POST)
	public String editProduct(@Valid @ModelAttribute("productBO") InventoryBO productServiceBO,
			BindingResult bindingResult, Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();

		if (bindingResult.hasErrors()) {
			return "edit-productservice";
		}
		if (0 < loginId && userType.contains(UserRoles.ROLE_ADMIN.toString())) {
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			adminLoginBO.setId(loginId);
			productServiceBO.setAdminLoginBO(adminLoginBO);
		}
		if(0<getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId();     // Company Based Update
			productServiceBO.setCompanyId(companyId);
		}
		boolean status = productService.serviceUpdate(productServiceBO);
		if (status) {
			model.addAttribute("successMessage", messageSource.getMessage("Product.Update", null, null));
			return "redirect:/view-productservice";
		} else {
			model.addAttribute("errorMessage", "Doesnot Exists");
		}
		model.addAttribute("productBO", productServiceBO);
		return "edit-productservice";
	}

	@RequestMapping(value = "/delete-productservice", method = RequestMethod.GET)
	public String deleteProduct(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam("serviceId") Integer serviceId) throws FileNotFoundException {
		LOGGER.entry();
		InventoryBO productServiceBO = new InventoryBO();
		productServiceBO.setServiceId(serviceId);
		try {
			/* productServiceBO =productService.getLeads(serviceId); */
			if (null != productServiceBO) {
				Boolean status = productService.deleteService(productServiceBO);
				if (status) {
					model.addAttribute("successMessage", messageSource.getMessage("Product.Delete", null, null));
					return "redirect:/view-productservice";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/view-productservice";
	}

	@RequestMapping(value = "/product-tracking-status", method = RequestMethod.GET)
	public String viewEmployerStatus(Model model, HttpServletRequest request)
			throws MySalesException, SerialException, SQLException {

		try {
			long serviceId = 0;
			InventoryBO inventoryBO = null;
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			long userId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			// adminLoginBO.setUserType(userType);
			adminLoginBO.setId(userId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);

			if (null != request.getParameter("serviceId")) {
				final String id = request.getParameter("serviceId");
				serviceId = Long.valueOf(id);
			}

			inventoryBO = productService.getProducts(serviceId);
			if (null != inventoryBO) {
				model.addAttribute("viewProducts", inventoryBO);

			} else {
				model.addAttribute("viewProducts", new InventoryBO());
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tracking status has failed:" + ex.getMessage());
			}
			LOGGER.info("Tracking status has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "product-tracking-status";
	}

	@RequestMapping(value = "/check_productName", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkProductName(@RequestParam String serviceName)throws Exception {
	
		boolean productNameCheck = false;
		long id=getUserSecurity().getLoginId();
		try {
			productNameCheck = productService.checkProductName(serviceName,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productNameCheck;
	}
}
