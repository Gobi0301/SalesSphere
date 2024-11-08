package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;
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

import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.PriceBookBO;
import com.scube.crm.bo.SupplierBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.AdminService;
import com.scube.crm.service.PriceBookService;
import com.scube.crm.service.ProductService;
import com.scube.crm.service.SupplierService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SupplierVO;
import com.scube.crm.vo.User;

@Controller
public class PriceBookController extends ControllerUtils implements Serializable {

	private static final long serialVersionUID = -5857977996611066292L;

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(PriceBookController.class);

	@Autowired
	private PriceBookService priceBookService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ProductService productService;

	@Autowired
	private SupplierService supplierService;


	@RequestMapping(value="/create-pricebook", method = RequestMethod.GET)
	public String createpricebook(Model model, HttpServletRequest request) throws
	MySalesException, NumberFormatException, FileNotFoundException, SerialException, SQLException{
		PriceBookController.LOGGER.entry();
		List<String> userType = getUserSecurity().getRole();
		long loginId = getUserSecurity().getLoginId();
		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		InventoryBO productServiceBO=new InventoryBO();
		SupplierBO supplierBO = new SupplierBO();
		AdminUserBO adminuserBO=new AdminUserBO();
		
		if(0<getUserSecurity().getCompanyId()) {			//CompanyId
			long CompanyId=getUserSecurity().getCompanyId();
			productServiceBO.setCompanyId(CompanyId);
			supplierBO.setCompanyId(CompanyId);
			adminuserBO.setCompanyId(CompanyId);
		}
		try {
			// Product Drop down
			
			List<InventoryBO> productBOList=new ArrayList<>();
			productBOList=productService.listOfProductByPagination(productServiceBO);
			model.addAttribute("productList", productBOList);

		//	Supplier Drop Down
				List<SupplierBO> serviceList = new ArrayList<SupplierBO>();
		serviceList = supplierService.listSupplier(supplierBO);
		model.addAttribute("supplierLists", serviceList);

			PriceBookBO priceBookBO =new  PriceBookBO();
			

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
			model.addAttribute("priceBookBO", priceBookBO);

			List<String> userTypes = getUserSecurity().getRole();

			if (0 < loginId && userTypes.contains("ROLE_ADMIN")) {
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
				userBOList = adminService.retrieveUserByPagination(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}
			if (0 < loginId && userTypes.contains("ROLE_COMPANY")) {
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
				userBOList = adminService.retrieveUserByPagination(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add pricebook has failed:" + ex.getMessage());
			}
			LOGGER.info("Add pricebook has failed:" + ex.getMessage());
		}
		PriceBookController.LOGGER.exit();
		return "create-pricebook";

	}

	@RequestMapping(value = "/create-pricebook", method = RequestMethod.POST)
	public String createpricebook(@ModelAttribute("priceBookBO")  PriceBookBO priceBookBO, BindingResult result,
			HttpServletRequest request, Model model,HttpSession session) throws MySalesException, MalformedURLException {
		PriceBookController.LOGGER.entry();
		try {
			if (result.hasErrors()) {
				return "create-pricebook";
			}
			// user
			String priceBookId=priceBookBO.getPriceBookOwner();
			long ownerId=0;
			ownerId = Integer.parseInt(priceBookId);

			User user= new User();
			user.setId(ownerId);

			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			long loginId = getUserSecurity().getLoginId();
			// String userType=getUserSecurity().getRole();
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			priceBookBO.setUser(user);
			priceBookBO.setIsDeleted(false);
			priceBookBO.setCreatedBy(loginId);
			if(0<getUserSecurity().getCompanyId()) {			//CompanyId
				long CompanyId=getUserSecurity().getCompanyId();
				priceBookBO.setCompanyId(CompanyId);
				
			}
			priceBookBO = priceBookService.createPriceBook(priceBookBO);
			if(0<priceBookBO.getPriceBookId()) {
				model.addAttribute("successMessage", "PriceBook has been created successfully");
				return "redirect:/view-pricebook"; //successMessage
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create pricebook has failed:" + ex.getMessage());
			}
			LOGGER.info("create pricebook has failed:" + ex.getMessage());
			model.addAttribute("errorMessages", "Faild to create account");
		} finally {
			PriceBookController.LOGGER.exit();
		}
		return "create-pricebook";

	}

	@RequestMapping(value = "/view-pricebook", method = RequestMethod.GET)
	public String viewAccounts(HttpServletRequest request, Model model, HttpSession session) {
		AdminUserBO adminuserBO=new AdminUserBO();
		PriceBookBO priceBookbo = new PriceBookBO();
		PriceBookController.LOGGER.entry();
		try {
			String paging = null;
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId=getUserSecurity().getCompanyId();
			
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			
			if(null!=request.getParameter("searchElement")) {
				String priceBookName = request.getParameter("searchElement");
				priceBookbo.setPriceBookName(priceBookName);
				model.addAttribute("searchElement", request.getParameter("searchElement"));
			}
			
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}
			
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {	//company
				priceBookbo.setCompanyId(companyId);
				adminuserBO.setCompanyId(companyId);
			}
			/*
			 * List<PriceBookBO> pricebook = new ArrayList<PriceBookBO>();
			 * pricebook=priceBookService.reteriveprice(priceBookbo); if (null != pricebook
			 * &&!pricebook.isEmpty()&& pricebook.size() > 0) {
			 * model.addAttribute("pricebook", pricebook ); }else {
			 * model.addAttribute("errorMessage", "No Records Found"); }
			 */
			
			pricebookpagination(priceBookbo, paging, request, model);
			model.addAttribute("searchPrice",priceBookbo);
			List<AdminUserBO> priceBO = adminService.retrieveUser(adminuserBO);
			model.addAttribute("priceBo",priceBO);


		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("view pricebook details: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view pricebook details: Exception \t" + e);
			}
		} finally {
			PriceBookController.LOGGER.exit();
		}
		return "view-pricebook";
	}

	private void pricebookpagination(PriceBookBO priceBookbo , String paging, HttpServletRequest request, Model model)
			throws MySalesException, Exception {
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		long count = 0;
		long totalCount = 0;
		int page = 1;
		int maxRecord = 10;

		if (null != paging) {
			page = Integer.parseInt(paging);
		}
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {	//company
			priceBookbo.setCompanyId(companyId);
		}
		count=priceBookService.reterivepricebook(priceBookbo);
		if (0 != count) {
			totalCount = count;
			model.addAttribute("totalpricebookcount", totalCount);
		} else {
			model.addAttribute("errorMessage", "Record not Found!");
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		priceBookbo.setRecordIndex(startingRecordIndex);
		priceBookbo.setMaxRecord(maxRecord);
		priceBookbo.setPagination("pagination");
		List<PriceBookBO> pricebook = new ArrayList<PriceBookBO>();
		pricebook=priceBookService.reteriveprice(priceBookbo);
		if (null != pricebook &&!pricebook.isEmpty()&& pricebook.size() > 0) {
			model.addAttribute("pricebook",
					PaginationClass.paginationLimitedRecords(page, pricebook, maxRecord, totalCount));
		}else {
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

	@RequestMapping(value="/search-pricebook", method = RequestMethod.POST)
	public String searchpricebook(@Valid Model model,@ModelAttribute("searchPrice")PriceBookBO priceBO,BindingResult result , HttpServletRequest request)throws MySalesException{
		PriceBookController.LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}

		long count = 0;
		long totalsearchCount = 0;
		int page = 1;
		int maxRecord = 10;

		String paging = null;
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
			page = Integer.parseInt(paging);
		}
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {	
			priceBO.setCompanyId(companyId);
		}
		if(null!=priceBO.getPriceBookName() && !priceBO.getPriceBookName().isEmpty()) {
			model.addAttribute("searchElement", priceBO.getPriceBookName());
			
		}
		
		count = priceBookService.searchcount(priceBO);
		if(0 != count && count > 0) {
			totalsearchCount = count;
			model.addAttribute("searchpricebookcount", totalsearchCount);
		}
		else {
			model.addAttribute("errorMessage", "No Records Found");
			return "view-pricebook";
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		priceBO.setRecordIndex(startingRecordIndex);
		priceBO.setMaxRecord(maxRecord);
		priceBO.setPagination("pagination");
		List<PriceBookBO> priceBookBOList = new ArrayList<PriceBookBO>();
		priceBookBOList = priceBookService.search(priceBO);
		if(priceBookBOList != null && priceBookBOList.size() > 0 && !priceBookBOList.isEmpty()) {
			model.addAttribute("pricebook",PaginationClass.paginationLimitedRecords(page, priceBookBOList, maxRecord, totalsearchCount));
		}else {
			model.addAttribute("errorMessage", "No Records Found");
		}

		return "view-pricebook";
	}



	@RequestMapping(value="/view-pricebook-details", method = RequestMethod.GET)
	public String viewpricebookdetails(Model model, HttpServletRequest request,PriceBookVO priceBookVO) 
			throws MySalesException{
		PriceBookController.LOGGER.entry();
		try {
			String pricebookId = request.getParameter("priceBookId");
			Integer id = Integer.parseInt(pricebookId);
			priceBookVO.setPriceBookId(id);
			priceBookVO=priceBookService.reterivepricebookdetails(priceBookVO);
			model.addAttribute("priceBookVO", priceBookVO);

		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("view pricebook details: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view pricebook details: Exception \t" + e);
			}
		} finally {
			PriceBookController.LOGGER.exit();
		}

		return "view-pricebook-details";

	}

	@RequestMapping(value="/edit-pricebook", method = RequestMethod.GET)
	public String editpricebook(Model model, HttpServletRequest request,PriceBookVO priceBookVO) 
			throws MySalesException{
		PriceBookController.LOGGER.entry();
		try {

			String pricebookId = request.getParameter("priceBookId");
			Integer id = Integer.parseInt(pricebookId);
			List<String> userType = getUserSecurity().getRole();
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			AdminUserBO adminuserBO=new AdminUserBO();
			InventoryBO productServiceBO=new InventoryBO();
			SupplierBO supplierBO = new SupplierBO();
			if(0 < getUserSecurity().getCompanyId()) {	//company
				long companyId=getUserSecurity().getCompanyId();
				adminuserBO.setCompanyId(companyId);
				productServiceBO.setCompanyId(companyId);
				supplierBO.setCompanyId(companyId);
				model.addAttribute("companyId", companyId);
				}
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			
			
			if (0 < id && userType.contains("ROLE_ADMIN")) {
				List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}
			
			if (0 < id && userType.contains("ROLE_COMPANY")) {
				List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
				userBOList = adminService.retrieveUserByPagination(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}
 			
			// Product Drop down
			List<InventoryBO> productBOList=new ArrayList<>();
			productBOList=productService.listOfProductByPagination(productServiceBO);
			model.addAttribute("productList", productBOList);			
						
            //Supplier Drop Down
			
			List<SupplierBO> serviceList = new ArrayList<SupplierBO>();
			serviceList = supplierService.listSupplier(supplierBO);
			model.addAttribute("supplierLists", serviceList);
			
			priceBookVO.setPriceBookId(id);
			
			priceBookVO=priceBookService.editpricebooks(priceBookVO);
			model.addAttribute("priceBookVO", priceBookVO);
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("edit retrieve: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("edit retrieve: Exception \t" + e);
			}
		} finally {
			PriceBookController.LOGGER.exit();
		}

		return "edit-pricebook";

	}

	@RequestMapping(value="/edit-pricebook", method = RequestMethod.POST)
	public String updatepricebook(@Valid Model model,@ModelAttribute("priceBookVO")PriceBookVO priceBookVO,
			BindingResult result, HttpServletRequest request,HttpServletResponse response) 
					throws MySalesException{
		PriceBookController.LOGGER.entry();
		try {
			if (result.hasErrors()) { 
				return "edit-accounts";
			}

			long loginId = getUserSecurity().getLoginId();
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			Boolean status ;
			if (null!=priceBookVO&&null!=priceBookVO.getProductservicevo().getServiceName()) {
				long id =Long.parseLong(priceBookVO.getProductservicevo().getServiceName());
				InventoryVO inventory=new InventoryVO();
				inventory.setServiceId(id);
				priceBookVO.setProductservicevo(inventory);
			}
			if (null!=priceBookVO&&null!=priceBookVO.getSuppliervo().getSupplierName()) {
				long id =Long.parseLong(priceBookVO.getSuppliervo().getSupplierName());
				SupplierVO supplier=new SupplierVO();
				supplier.setSupplierId(id);;
				priceBookVO.setSuppliervo(supplier);
			}
			if(null!=(request.getParameter("companyId"))) {
				long id =Long.parseLong(request.getParameter("companyId"));
				priceBookVO.setCompanyId(id);
			}
			priceBookVO.setCreatedBy(loginId);
			priceBookVO.setModifiedBy(loginId);
			priceBookVO.setIsDeleted(false);
			status=priceBookService.updatepricebook(priceBookVO);
			if(status=true) {
				model.addAttribute("successMessage", "update pricebook successfully");
			} else {
				model.addAttribute("errorMessage", "Error");

			}

		}catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("update pricebook has been failed: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("update pricebook  has been failed: Exception \t" + e);
			}
		} finally {
			PriceBookController.LOGGER.exit();
		}

		return "redirect:/view-pricebook";
	}

	@RequestMapping(value="/delete-pricebook", method = RequestMethod.GET)
	public String deletepricebook(Model model, HttpServletRequest request,PriceBookVO priceBookVO) 
			throws MySalesException{
		PriceBookController.LOGGER.entry();
		Boolean status;
		try {
			String pricebookId = request.getParameter("priceBookId");
			Integer id = Integer.parseInt(pricebookId);
			priceBookVO.setPriceBookId(id);
			status=priceBookService.deletepricebook(priceBookVO);
			//	model.addAttribute("priceBookVO", priceBookVO);
			if(status=true) {
				model.addAttribute("successMessage", "Delete pricebook successfully");
			} else {
				model.addAttribute("errorMessage", "Error");

			}

		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Delete pricebook: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete pricebook: Exception \t" + e);
			}
		} finally {
			PriceBookController.LOGGER.exit();
		}

		return "redirect:/view-pricebook";

	}
	@ResponseBody
	@RequestMapping(value = "/AjaxViewSupplier", method = RequestMethod.GET)
	public List<SupplierBO> getSupplier(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String json = null;
		List<SupplierVO> sublist = new ArrayList<SupplierVO>();
		System.out.println("ajaxcid::" + request.getParameter("product"));
		SupplierVO bo;
		String product = request.getParameter("product");
		long serviceId = Long.parseLong(product);
		ArrayList<SupplierBO> supplierList =new ArrayList<>();
		try {

			supplierList = priceBookService.getSupplier(serviceId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("FROM INFO: Exception \t"+e);

			}
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("FROM DEBUG : Exception \t"+e);
			}
		}
		return supplierList;
	}
	@RequestMapping(value = "/getSupplierDetails", method = RequestMethod.GET)
	@ResponseBody
	public SupplierBO getEmployeeDetails(@RequestParam String supplierId, HttpSession session) {
		SupplierBO supplierBO = new SupplierBO();
		Long supplierIds = Long.valueOf(supplierId); // What is Supplier ID?, How to debug the ajax call?
		//productIds = product; // Why it is assigned as instance?
		supplierBO = priceBookService.getSupplierPrice(supplierIds);


		return supplierBO;

	}

	
}
