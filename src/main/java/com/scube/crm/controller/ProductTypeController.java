package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

import com.scube.crm.bo.ProductTypesBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.ProductTypeService;
import com.scube.crm.utils.PaginationClass;

@Controller
@Scope("session")
public class ProductTypeController extends ControllerUtils {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(ProductTypeController.class);

	@Autowired
	ProductTypeService service;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/create_producttypes", method = RequestMethod.GET)
	public String getproducttypes(Model model, HttpServletRequest request, HttpSession session) throws Exception {

		model.addAttribute("productTypes", new ProductTypesBO());

		return "create_producttypes";

	}

	@RequestMapping(value = "/create_producttypes", method = RequestMethod.POST)
	public String postproducttypes(@Valid @ModelAttribute("productTypes") ProductTypesBO producttypesbo,
			BindingResult result, HttpServletRequest request, HttpSession session, Model model) throws Exception {
		LOGGER.entry();
		long id=getUserSecurity().getLoginId();
		producttypesbo.setCreatedBy(id);
		try {
			if (result.hasErrors()) {
				return "create_producttypes";
			}

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // Company create
				producttypesbo.setCompanyId(companyId);
			}

			long ids = service.createProducttypes(producttypesbo);
			if (0 < ids) {
				model.addAttribute("successMessage","ProductTypes Created Successfully ");
			} else {
				model.addAttribute("Error Message", "Record is not created");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create ProductTypes has failed:" + ex.getMessage());
			}
			LOGGER.info("Create ProductTypes has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "redirect:/view_producttypes";

	}

	/*
	 * @RequestMapping(value = "/view_producttypes", method = RequestMethod.GET)
	 * public String viewproducttypes(HttpServletRequest request, HttpSession
	 * session, Model model) throws Exception { LOGGER.entry(); ProductTypesBO
	 * ProductTypesBO=new ProductTypesBO(); try {
	 * 
	 * 
	 * long totalSearchCount = 0; int page = 1; int maxRecord = 10; String paging =
	 * null;
	 * 
	 * if (null != request.getParameter("page")) { paging =
	 * request.getParameter("page"); page = Integer.parseInt(paging); }
	 * 
	 * long companyId = getUserSecurity().getCompanyId(); long loginId =
	 * getUserSecurity().getLoginId(); List<String> userType =
	 * getUserSecurity().getRole();
	 * 
	 * ProductTypesBO producttypesbo = new ProductTypesBO();
	 * 
	 * if (0 < loginId && !userType.contains("ROLE_ADMIN")){
	 * producttypesbo.setCompanyId(companyId); }
	 * productTypesPagination(ProductTypesBO, paging, model, request);
	 * model.addAttribute("searchProducttypes",new ProductTypesBO());
	 * 
	 * // String page=request.getParameter("d-49216-p"); if (null !=
	 * request.getParameter("successMessage")) {
	 * model.addAttribute("successMessage", request.getParameter("successMessage"));
	 * } if (null != request.getParameter("errorMessage")) {
	 * model.addAttribute("errorMessage", request.getParameter("errorMessage")); }
	 * 
	 * // List<ProductTypesBO> listtypes = service.selectAlltypes(producttypesbo);
	 * // if (null != listtypes && listtypes.size() > 0) { //
	 * model.addAttribute("producttypesList", listtypes); // }else { //
	 * model.addAttribute("errorMessage", "No Record Found!"); // return
	 * "view_producttypes"; // }
	 * 
	 * 
	 * } catch (Exception ex) { if (LOGGER.isDebugEnabled()) {
	 * LOGGER.debug("View ProductTypes has failed:" + ex.getMessage()); }
	 * LOGGER.info("View ProductTypes has failed:" + ex.getMessage()); } finally {
	 * LOGGER.exit(); } return "view_producttypes";
	 * 
	 * }
	 */
	/*
	 * private void ProductTypesBOPagination(ProductTypesBO productTypesBO, String
	 * paging, Model model, HttpServletRequest request) { LOGGER.entry();
	 * 
	 * try { long countOfproductTypes = 0; long countOfproductTypesBOlist = 0; int
	 * page = 1; int maxRecord = 10; long companyId = 0; List<ProductTypesBO>
	 * ProductTypesBOList=new ArrayList<>();
	 * 
	 * if (0 < getUserSecurity().getCompanyId()) {
	 * 
	 * companyId = getUserSecurity().getCompanyId(); // company based create
	 * condition }
	 * 
	 * if (null != paging) { page = Integer.parseInt(paging); } countOfproductTypes
	 * = service.countOfproductTypes(productTypesBO); if (0 < countOfproductTypes) {
	 * countOfproductTypesBOlist = countOfproductTypes;
	 * model.addAttribute("countOfproductTypesBOlist", countOfproductTypesBOlist);
	 * 
	 * } int startinRecordOfUsers = paginationPageValues(page, maxRecord);
	 * productTypesBO.setRecordIndex(startinRecordOfUsers);
	 * productTypesBO.setMaxRecord(maxRecord);
	 * productTypesBO.setCompanyId(companyId); ProductTypesBOList =
	 * service.listproductTypesBOByPagination(productTypesBO); if (null !=
	 * ProductTypesBOList && !ProductTypesBOList.isEmpty()) {
	 * model.addAttribute("producttypesList",
	 * PaginationClass.paginationLimitedRecords(page, ProductTypesBOList, maxRecord,
	 * countOfproductTypes));
	 * 
	 * } } catch (Exception ex) { if (LOGGER.isDebugEnabled()) {
	 * LOGGER.debug("userPagination UserRoles has failed:" + ex.getMessage()); }
	 * LOGGER.info("userPagination UserRoles has failed:" + ex.getMessage()); }
	 * finally { LOGGER.exit(); }
	 */

	@RequestMapping(value = "/view_producttypes", method = RequestMethod.GET)
	public String viewproducttypes(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		LOGGER.entry();
		ProductTypesBO producttypesbo=new ProductTypesBO();
		try {
		long loginId=getUserSecurity().getLoginId();
		List<String> userType=getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId(); 
		String paging=null;
		if(null!=request.getParameter("page")) {
			paging=request.getParameter("page");
		}
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) { 
			producttypesbo.setCompanyId(companyId); 
			}
		if(null!=request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if(null!=request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}	
		productTypesPagination(producttypesbo, paging, model, request);
		model.addAttribute("searchProducttypes",new ProductTypesBO());
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view productTypes has failed:" + ex.getMessage());
			}
			LOGGER.info("view productTypes has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return "view_producttypes";
	}
	private void productTypesPagination(ProductTypesBO producttypesbo,String paging,Model model,HttpServletRequest request)throws IOException, MySalesException{
	       
		long count=0;
		int page=1;
		int maxRecord=10;
		long totalCount=0;
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		if(null!=paging) {
			page=Integer.parseInt(paging);
		}
		count = service.retrieveCount(producttypesbo);
		if(0<count) {
			totalCount=count;
			model.addAttribute("totalCount",totalCount);
		}else {
			model.addAttribute("errorMessage", "No Record Found!");
		}
		int startingRecordIndex=paginationPageValues(page,maxRecord);
		producttypesbo.setRecordIndex(startingRecordIndex);
		producttypesbo.setMaxRecord(maxRecord);
		producttypesbo.setPagination("pagination");
		List<ProductTypesBO> listtypes = service.listOfProductByPagination(producttypesbo);
		if(null!=listtypes&&!listtypes.isEmpty()&&0<listtypes.size()) {
			model.addAttribute("listtypes", PaginationClass.paginationLimitedRecords(page, listtypes, maxRecord, totalCount));
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


	@RequestMapping(value = "/search-producttypes", method = RequestMethod.POST)
	public String searchProducttypes(@ModelAttribute("searchProducttypes") ProductTypesBO producttypesbo,
			HttpServletRequest request, HttpSession session, Model model)
			throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		List<ProductTypesBO> listtype= new ArrayList<ProductTypesBO>();
		long count = 0;
		long totalSearchCount = 0;
		int page = 1;
		int maxRecord = 10;
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			producttypesbo.setCompanyId(companyId);
		}
		String paging = null;
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
			page = Integer.parseInt(paging);
		}
		count = service.retrieveCount(producttypesbo);
		if (0 != count) {
			totalSearchCount = count;
			model.addAttribute("totalSearchCount",totalSearchCount);
		}else {
			model.addAttribute("errorMessage", "No Records Found");
			return "view_producttypes";
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		producttypesbo.setRecordIndex(startingRecordIndex);
		producttypesbo.setMaxRecord(maxRecord);
		producttypesbo.setPagination("pagination");
		listtype = service.listOfProductByPagination(producttypesbo);
				if (null != listtype && listtype.size() > 0) {
			model.addAttribute("listtypes",PaginationClass.paginationLimitedRecords(page, listtype, maxRecord, totalSearchCount));
		}else {
			model.addAttribute("errorMessage", "No Record Found!");
		}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search producttypes has failed:" + ex.getMessage());
			}
			LOGGER.info("Search producttypes has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view_producttypes";

	}

		


	/*
	 * private int paginationPageValues(int page, int maxRecord) { int pageRecords =
	 * 0; if (page == 1) { pageRecords = 0; } else { pageRecords = (page - 1) *
	 * maxRecord + 1; pageRecords = pageRecords - 1;
	 * 
	 * } return pageRecords; }
	 */
	@RequestMapping(value = "/update_producttypes", method = RequestMethod.GET)
	public String editproducttypes(@RequestParam ("id")long productTypeId ,HttpServletRequest request, HttpSession session, Model model) throws Exception {
	 
		try {
			ProductTypesBO productTypeBo = new ProductTypesBO();

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // Company create
				productTypeBo.setCompanyId(companyId);
			}
			
			productTypeBo.setProductTypesId(productTypeId);
			if (0 < productTypeBo.getProductTypesId()) {
				productTypeBo = service.getTypeId(productTypeId);
			}
			if (null != productTypeBo) {
				model.addAttribute("productTypeBo", productTypeBo);
		
			}else {
				model.addAttribute("productTypeBo", productTypeId);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit ProductType has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit ProductType has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit_producttypes";

	}

	@RequestMapping(value = "/update_producttypes", method = RequestMethod.POST)
	public String updateproducttypes(@Valid @ModelAttribute("productTypeBo") ProductTypesBO productTypeBo,
			BindingResult bindingResult, Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException, MySalesException {
		LOGGER.entry();
        //ProductTypesBO status;
		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // Company create
				productTypeBo.setCompanyId(companyId);
			}

			productTypeBo = service.updateType(productTypeBo);
			if (null!=productTypeBo) {
				model.addAttribute("successMessage", "Update Successfully");
				return "redirect:/view_producttypes";
			} else {
				model.addAttribute("errorMessage", "Does Not Exists");
			}
			model.addAttribute("productTypeBo", productTypeBo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update ProductType has failed:" + ex.getMessage());
			}
			LOGGER.info("Update ProductType has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit_producttypes";

	}

	@RequestMapping(value = "/delete_producttypes", method = RequestMethod.GET)
	public String deleteproducttypes(@RequestParam("productTypesId") long productTypesId, HttpServletRequest request,
			HttpSession session, Model model) throws Exception {

		LOGGER.entry();
		try {
			ProductTypesBO producttypesbo = new ProductTypesBO();
			producttypesbo.setProductTypesId(productTypesId);
			if (null != producttypesbo) {
				boolean value = service.deleteProductTypes(producttypesbo);
				if (value == true) {
					System.out.println("ProductType deleted successfully");

					model.addAttribute("successMessage", "ProductTypes Deleted Successfully ");

					model.addAttribute(" errorMessage", " ProductTypes Doesn't Deleted");

					return "redirect:/view_producttypes";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete ProductTypes has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete ProductTypes has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view_producttypes";

	}
	@RequestMapping(value = "/check_productTypes", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkProductTypes(@RequestParam String productTypes)throws Exception {
	
		boolean productNameCheck = false;
		long id=getUserSecurity().getLoginId();
		try {
			productNameCheck = service.checkProductTypes(productTypes,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productNameCheck;
	}
	
}
