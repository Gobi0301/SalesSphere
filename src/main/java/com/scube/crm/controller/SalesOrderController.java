package com.scube.crm.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import com.scube.crm.bo.ClientBO;
import com.scube.crm.bo.GstBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.bo.PaymentBO;
import com.scube.crm.bo.PlotBO;
import com.scube.crm.bo.PriceBookBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.bo.SalesOrderProductsBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.AccountsService;
import com.scube.crm.service.GstService;
import com.scube.crm.service.OpportunityService;
import com.scube.crm.service.PriceBookService;
import com.scube.crm.service.SalesOrderService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.vo.PriceBookVO;

@Controller
public class SalesOrderController extends ControllerUtils {

	@Autowired
	OpportunityService opportunityService;
	@Autowired
	private SalesOrderService salesOrderService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	private PriceBookService priceBookService; // PriceBook Service
	@Autowired
	AccountsService accountsService;
	
	@Autowired
	GstService gstService;
	
	String price = null;;
	String productName = null;
	Integer productIds;
	long productId;
	String grandTotal = null;
	Long overAllgrandTotal = (long) 0;
	private long totalInvoiceAmount=0;
	long employerId;
	long gstId;
	String cgst = null;
	String sgst = null;

	String salesOrderNo;
	List<OpportunityBO> clientBO;
	List<SalesOrderProductsBO> salesOrderProductBO = null;
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(SalesOrderController.class);

	@RequestMapping(value = "/create-sales-order", method = RequestMethod.GET)
	public String createSalesOrder(HttpServletRequest request, Model model, HttpSession session)
			throws MySalesException {
		LOGGER.entry();
		price = null;
		;
		productName = null;
		grandTotal = null;
		overAllgrandTotal = (long) 0;
		this.totalInvoiceAmount=0;
		productIds = null;
		employerId = 0;
		salesOrderProductBO = new ArrayList<SalesOrderProductsBO>();
		AdminUserBO adminuserBO = new AdminUserBO();
		LeadsBO leadsBO = new LeadsBO();
		InventoryBO serviceBO = new InventoryBO();
		AccountBO accountBO = new AccountBO();
		ClientBO clientBO = new ClientBO();
		PriceBookBO priceBookbo = new PriceBookBO();
		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			leadsBO.setCompanyId(companyId);
			serviceBO.setCompanyId(companyId);
			adminuserBO.setCompanyId(companyId);
			accountBO.setCompanyId(companyId);
			clientBO.setCompanyId(companyId);
			priceBookbo.setCompanyId(companyId);
		}
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		model.addAttribute("salesOrderBO", new SalesOrderBO());
		salesOrderNo = opportunityService.getSalesOrderNo(clientBO);
		if (null != salesOrderNo) {
			model.addAttribute("salesno", salesOrderNo);
		}

		try {
			Map<Integer, String> accountList = salesOrderService.retriveAccounts(accountBO);
			model.addAttribute("accountList", accountList);

			List<InventoryBO> productServiceBO = new ArrayList<>();
			productServiceBO = opportunityService.getProductList(serviceBO);
			if (null == productServiceBO) {
				return "create-sales-orders";

			}
			GstBO gst = opportunityService.getGstValues();

			if (null == gst) {
				return "create-sales-orders";

			}

			model.addAttribute("cgst", gst.getCgst());
			model.addAttribute("sgst", gst.getSgst());
			model.addAttribute("productBOList", productServiceBO);
			// PriceBook List

			List<PriceBookBO> pricebook = new ArrayList<PriceBookBO>();
			pricebook = priceBookService.reteriveprice(priceBookbo);

//			for(PriceBookBO ob:pricebook) {
//				
//				session.setAttribute("pricebookid", ob.getPriceBookId());
//				
//				
//				pricebook.add(ob);
//				
//			}

			if (null == pricebook) {
				return "create-sales-orders";
			}
			model.addAttribute("pricebooksList", pricebook);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "create-sales-orders";

	}

	@RequestMapping(value = "/create-sales-order", method = RequestMethod.POST)
	public String createSalesOrder(@Valid @ModelAttribute("salesOrderBO") SalesOrderBO salesOrderBOS,
			BindingResult bindingResult, Model model, HttpServletRequest request, HttpSession session)
			throws IOException {
		long loginId = getUserSecurity().getLoginId();

		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}

		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // Company create
			salesOrderBOS.setCompanyId(companyId);
		}

		try {
			if (null != salesOrderProductBO && salesOrderProductBO.size() > 0) {

				salesOrderBOS.setSalesOrderProductBO(salesOrderProductBO);
				salesOrderBOS.setCreatedBy(loginId);
				salesOrderBOS.setModifiedBy(loginId);
				Date date = new Date();
				salesOrderBOS.setCreated(date);
				long id = opportunityService.createSalesOrder(salesOrderBOS);
				if (0 < id) {
					model.addAttribute("successMessage", messageSource.getMessage("Create-Sales", null, null));
				} else {
					model.addAttribute("errorMessage", messageSource.getMessage("Sales-Error", null, null));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return "redirect:/view-sales-order";

	}

	@RequestMapping(value = "/convert-sales-order", method = RequestMethod.GET)
	public String convertSalesOrder(HttpServletRequest request, Model model) throws Exception {

		LOGGER.entry();
		List<InventoryBO> productServiceBO = new ArrayList<>();
		PriceBookBO priceBookbo = new PriceBookBO();
		salesOrderProductBO = new ArrayList<SalesOrderProductsBO>();
		final String id = request.getParameter("id");
		// long employerId = 0;
		OpportunityBO registerBO = new OpportunityBO();
		SalesOrderBO salesOrderBO = new SalesOrderBO();
		AdminUserBO adminuserBO = new AdminUserBO();
		LeadsBO leadsBO = new LeadsBO();
		InventoryBO serviceBO = new InventoryBO();
		ClientBO clientBO = new ClientBO();
		AccountBO accountBO = new AccountBO();
		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			leadsBO.setCompanyId(companyId);
			serviceBO.setCompanyId(companyId);
			adminuserBO.setCompanyId(companyId);
			clientBO.setCompanyId(companyId);
			accountBO.setCompanyId(companyId);
			priceBookbo.setCompanyId(companyId);
		}
		if (null != id) {
			employerId = Long.parseLong(id);
			registerBO.setId(employerId);
		}
		salesOrderNo = opportunityService.getSalesOrderNo(clientBO);
		if (null != salesOrderNo) {
			salesOrderBO.setSalesOrderNo(salesOrderNo);
		}

		productServiceBO = opportunityService.getProductList(serviceBO);
		if (null == productServiceBO && productServiceBO.size() == 0) {
			return "create-sales-orders";
		}
		List<PriceBookBO> pricebook = new ArrayList<PriceBookBO>();
		pricebook = priceBookService.reteriveprice(priceBookbo);
		if (null == pricebook) {
			return "create-sales-orders";
		}

		Map<Integer, String> accountList = salesOrderService.retriveAccounts(accountBO);
		model.addAttribute("accountList", accountList);
		model.addAttribute("productBOList", productServiceBO);
		model.addAttribute("pricebooksList", pricebook);
		model.addAttribute("salesOrderBO", salesOrderBO);

		return "create-sales-orders";

	}

	@RequestMapping(value = "/getPriceBookDetails", method = RequestMethod.GET)
	@ResponseBody
	public PriceBookBO getprice(@RequestParam String pricebook_Id, HttpSession session) {
		PriceBookBO priceBookBO = new PriceBookBO();
		Integer pricebook_Ids = Integer.valueOf(pricebook_Id); // What is staff ID?, How to debug the ajax call?
		productIds = pricebook_Ids; // Why it is assigned as instance?
		priceBookBO = opportunityService.getPriceBookPrice(pricebook_Ids);

		if (null != priceBookBO) {
			price = String.valueOf(priceBookBO.getPrice());
			productName = priceBookBO.getPriceBookName();
		}

		return priceBookBO;

	}

	@RequestMapping(value = "/getProductGst", method = RequestMethod.GET)
	@ResponseBody
	public GstBO getProductGst(@RequestParam String product_Id, HttpSession session) {
		GstBO gstBO = new GstBO();
		long product_Ids = Long.parseLong(product_Id); // What is staff ID?, How to debug the ajax call?
		productId = product_Ids; // Why it is assigned as instance?
 		gstBO = opportunityService.getProductGst(product_Ids);

		if (null != gstBO) {
			sgst = String.valueOf(gstBO.getSgst());
			cgst = gstBO.getCgst();
		}

		return gstBO;
	}

	@RequestMapping(value = "/getTotalDetails", method = RequestMethod.GET)
	@ResponseBody
	public SalesOrderBO getTotalPrice(@RequestParam String quantityId, HttpSession session) {
		SalesOrderBO salesOrderBO = new SalesOrderBO();
		double quantityValue = Double.valueOf(quantityId);
		double priceValue = Double.valueOf(price);
		double totalPrice = priceValue * quantityValue;
		salesOrderBO.setTotalInvoice((long) totalPrice);
		return salesOrderBO;
	}

	@RequestMapping(value = "/getGrandTotalDetails", method = RequestMethod.GET)
	@ResponseBody
	public SalesOrderBO getGrandTotalPrice(@RequestParam String quantityId, HttpSession session) {
		SalesOrderBO salesOrderBO = new SalesOrderBO();
		salesOrderBO.setGrandTotal(overAllgrandTotal);
		GstBO gstBO = new GstBO();
		double cGstFinalRate=0;
		double sGstFinalRate=0;
		double totalInvoiceAmount=0;
		if(null!=salesOrderProductBO && 0<salesOrderProductBO.size()) {
			for( SalesOrderProductsBO salesOrderProductBO: salesOrderProductBO) {
				GstBO gstBo=salesOrderProductBO.getGstBO();
				int quantity = salesOrderProductBO.getQuantity();
				double productPrice = salesOrderProductBO.getPrice();
				if(0<gstBo.getGstId()) {
					GstBO gst = gstService.getGst(gstBo.getGstId());
					if(null!=gst) {
						String removepercentageCgst = gst.getCgst().replace("%", "");
						double cGst = Double.valueOf(removepercentageCgst);
						String removepercentageSgst = gst.getSgst().replace("%", "");
						double sGst = Double.valueOf(removepercentageSgst);
						double gstRate=cGst + sGst;						
						double percentage = 100;
						
						double gstAmountSingleProduct=(productPrice*gstRate)/percentage;
						double totalGstAmountForQuantity=gstAmountSingleProduct*quantity;
						totalInvoiceAmount=totalGstAmountForQuantity+(productPrice*quantity);
					}
				}
			}
//			gstBO.setCgst(String.valueOf(cGstFinalRate));
//			gstBO.setSgst(String.valueOf(sGstFinalRate));
//			salesOrderBO.setGstBO(gstBO);
			this.totalInvoiceAmount=this.totalInvoiceAmount + (long) totalInvoiceAmount;
			salesOrderBO.setTotalInvoice((long) this.totalInvoiceAmount);
		}
		
		return salesOrderBO;
	}

	@RequestMapping(value = "/addAgreement", method = RequestMethod.GET)
	@ResponseBody
	public List<SalesOrderProductsBO> addAgreementDetails(Model model, HttpServletRequest request,
			@RequestParam Integer pricebook, String price, String quantityId, String totalId, long productId, long gstId) {
		HttpSession session = request.getSession();
		SalesOrderProductsBO salesOrderProduct = new SalesOrderProductsBO();
		GstBO gst =new GstBO();
		gst.setGstId(gstId);
		
		SalesOrderBO salesOrder = new SalesOrderBO();
		salesOrder.setGstBO(gst);
		salesOrder.setQuantity(Long.valueOf(quantityId));
		salesOrder.setPrice(Double.valueOf(price));
		salesOrder.setTotalPrice(Long.valueOf(totalId));
		salesOrder.setGrandTotal(Long.valueOf(totalId));

		salesOrderProduct.setGstBO(gst);
		salesOrderProduct.setQuantity(Integer.valueOf(quantityId));
		salesOrderProduct.setPrice(Double.valueOf(price));
		salesOrderProduct.setQuantityPrice(Double.valueOf(totalId));
		InventoryBO productServiceBO = new InventoryBO();
		productServiceBO.setServiceId(productId);
		salesOrderProduct.setProduct(productServiceBO);
		PriceBookBO priceBookBO = new PriceBookBO();
		priceBookBO.setPriceBookId(pricebook);
		priceBookBO.setPriceBookName(productName);
		;
		salesOrderProduct.setPriceBookBo(priceBookBO);
		salesOrderProductBO.add(salesOrderProduct);
		grandTotal = String.valueOf(salesOrder.getGrandTotal());
		
		overAllgrandTotal = (long) (overAllgrandTotal + (Double.valueOf(grandTotal)));
		session.setAttribute("grandTotal", grandTotal);
		
		return salesOrderProductBO;
	}

	@RequestMapping(value = "/search-customer", method = RequestMethod.POST)
	public String getSeperateCustomer(@Valid @ModelAttribute("salesOrderBO") SalesOrderBO salesOrderBO,
			HttpServletRequest req, Model model) throws MySalesException, SerialException, SQLException {
		SalesOrderBO salesOrder = new SalesOrderBO();
		List<InventoryBO> productServiceBO = new ArrayList<>();
		ClientBO clientBO = new ClientBO();
		AdminUserBO adminuserBO = new AdminUserBO();
		LeadsBO leadsBO = new LeadsBO();
		InventoryBO serviceBO = new InventoryBO();

		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			leadsBO.setCompanyId(companyId);
			serviceBO.setCompanyId(companyId);
			adminuserBO.setCompanyId(companyId);
			clientBO.setCompanyId(companyId);

		}
		salesOrderNo = opportunityService.getSalesOrderNo(clientBO);
		if (null != salesOrderNo) {
			model.addAttribute("salesno", salesOrderNo);
		}

		productServiceBO = opportunityService.getProductList(serviceBO);
		if (null != productServiceBO) {
			model.addAttribute("productBOList", productServiceBO);
		}
		return "create-sales-orders";

	}

	@RequestMapping(value = "/view-sales-order", method = RequestMethod.GET)
	public String retriveSalesOrders(HttpServletRequest request, Model model) throws Exception {
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();

		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}

		// Common for both search and view
		SalesOrderBO salesOrderBO = new SalesOrderBO();
		AccountBO accountBO = new AccountBO();
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) { // Company
			salesOrderBO.setCompanyId(companyId);
			accountBO.setCompanyId(companyId);

		}
		// Search
		model.addAttribute("searchObj", salesOrderBO);

		// Foreign Key DropDown List
		Map<Integer, String> accountList = salesOrderService.retriveAccounts(accountBO);
		model.addAttribute("accountList", accountList);

		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			/*if (request.getParameter("errorMessage").equals("No Records Found")) {
				model.addAttribute("searchObj", new SalesOrderBO());
				return "view-sales-orders";

			}*/
		}
		if(null!=request.getParameter("searchElement")) {
			String salesOrderNo = request.getParameter("searchElement");
			salesOrderBO.setSalesOrderNo(salesOrderNo);
			model.addAttribute("searchElement", request.getParameter("searchElement"));
		}

		String paging = null;
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
		}

		// view
		model.addAttribute("view-sales-order", salesOrderBO);// Should be same as URL in list at jsp
		salesOrderPagination(salesOrderBO, paging, request, model);
		model.addAttribute("searchObj", new SalesOrderBO());
		return "view-sales-orders";

	}

	private void salesOrderPagination(SalesOrderBO salesOrderBO, String paging, HttpServletRequest request, Model model)
			throws Exception {
		long count = 0;
		long viewCount = 0;
		int page = 1;
		int maxRecord = 10;

		if (null != paging) {
			page = Integer.parseInt(paging);
		}
		if(null!=salesOrderBO.getSalesOrderNo()&& !salesOrderBO.getSalesOrderNo().isEmpty()) {
			model.addAttribute("searchElement", salesOrderBO.getSalesOrderNo());
		}
		count = salesOrderService.salesCount(salesOrderBO);
		if (count != 0) {
			viewCount = count;
			model.addAttribute("viewCount", viewCount);
		}else {
			model.addAttribute("errorMessage", "Record Not Found!");
		}

		int startingRecordIndex = paginationPageValues(page, maxRecord);
		salesOrderBO.setRecordIndex(startingRecordIndex);
		salesOrderBO.setMaxRecord(maxRecord);
		salesOrderBO.setPagination("pagination");

		List<SalesOrderBO> salesOrderlist = new ArrayList<>();
		salesOrderlist = salesOrderService.retriveSalesOrders(salesOrderBO);
		if (salesOrderlist != null && salesOrderlist.size() > 0 && !salesOrderlist.isEmpty()) {
			model.addAttribute("listSales",
					PaginationClass.paginationLimitedRecords(page, salesOrderlist, maxRecord, viewCount));
		}else {
			model.addAttribute("errorMessage", "Record Not Found!");
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

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@Valid Model model, @ModelAttribute("searchObj") SalesOrderBO salesOrderBO,
			BindingResult result, HttpServletRequest request) throws MySalesException, Exception {
		SalesOrderController.LOGGER.entry();
		if (result.hasErrors()) {
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

		if (null != request.getParameter("page")) {
			String paging = request.getParameter("page");
			page = Integer.parseInt(paging);
		}
		if(null!=salesOrderBO.getSalesOrderNo()&& !salesOrderBO.getSalesOrderNo().isEmpty()) {
			model.addAttribute("searchElement", salesOrderBO.getSalesOrderNo());
		}

		/* Search based on Foreign Key */
		if (salesOrderBO != null && salesOrderBO.getAccountBO().getAccountId() != null
				&& 0 < salesOrderBO.getAccountBO().getAccountId()) {
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

		count = salesOrderService.searchCount(salesOrderBO);
		if (0 != count && count > 0) {
			searchCount = count;
			model.addAttribute("count", count);

		} else {
			model.addAttribute("errorMessage", "No Records Found");
			/* To keeping the Foreign Key List constantly Across all Pages */
			Map<Integer, String> accountList = salesOrderService.retriveAccounts(accountBO);
			model.addAttribute("accountList", accountList);
			return "view-sales-orders";
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		salesOrderBO.setRecordIndex(startingRecordIndex);
		salesOrderBO.setMaxRecord(maxRecord);
		salesOrderBO.setPagination("pagination");
		List<SalesOrderBO> salesOrderBOList = new ArrayList<SalesOrderBO>();
		salesOrderBOList = salesOrderService.search(salesOrderBO);

		/*
		 * Maintain the current value of Foreign Key across all pages When the record is
		 * available
		 */
		if (salesOrderBO.getSalesOrderNo() == null) {
			AccountBO aBO = new AccountBO();
			aBO.setAccountId(salesOrderBOList.get(0).getAccountBO().getAccountId());
			aBO.setAccountName(salesOrderBOList.get(0).getAccountBO().getAccountName());
			salesOrderBO.setAccountBO(aBO);
		}

		/*
		 * Maintain the current value of Foreign Key across all pages When the record is
		 * Unavailable
		 */
		else {
			AccountBO aBO = new AccountBO();
			aBO.setAccountId(salesOrderBO.getAccountBO().getAccountId());
			salesOrderBO.setAccountBO(aBO);
		}

		model.addAttribute("listSalesSearch", salesOrderBOList);

		if (salesOrderBOList != null && salesOrderBOList.size() > 0 && !salesOrderBOList.isEmpty()) {
			model.addAttribute("listSalesSearch",
					PaginationClass.paginationLimitedRecords(page, salesOrderBOList, maxRecord, searchCount));
		} else {
			model.addAttribute("errorMessage", "No Records Found");
		}

		/* To keeping the Foreign Key List constantly Across all Pages */
		Map<Integer, String> accountList = salesOrderService.retriveAccounts(accountBO);
		model.addAttribute("accountList", accountList);

		SalesOrderController.LOGGER.exit();
		return "view-sales-orders";

	}

	@RequestMapping(value = "/view-sales-order-list", method = RequestMethod.GET)
	public String viewSalesOrderlist(HttpServletRequest request, Model model) {
		long salesOrderId = 0;
		List<SalesOrderBO> salesOrderlist = new ArrayList<>();
		SalesOrderBO salesOrder = new SalesOrderBO();
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		if (null != request.getParameter("salesid")) {
			model.addAttribute("salesid", request.getParameter("salesid"));
		}
		if (null != request.getParameter("salesno")) {
			final String salesOrderNo = request.getParameter("salesno");
			salesOrderId = Long.parseLong(salesOrderNo);
		}
		if (0 < getUserSecurity().getCompanyId()) { // CompanyId
			long CompanyId = getUserSecurity().getCompanyId();
			salesOrder.setCompanyId(CompanyId);
		}
		SalesOrderBO salesOrderBO = salesOrderService.getSalesOrder(salesOrderId);
		if (null != salesOrderBO) {

			model.addAttribute("overAlltotal", salesOrderBO.getTotalInvoice());
			model.addAttribute("grandTotal", salesOrderBO.getGrandTotal());
//			model.addAttribute("cgstValue", salesOrderBO.getGstBO().getCgstValue());
//			model.addAttribute("sgstValue", salesOrderBO.getGstBO().getSgstValue());
//			model.addAttribute("sgst", salesOrderBO.getGstBO().getSgst());
//			model.addAttribute("cgst", salesOrderBO.getGstBO().getCgst());
			model.addAttribute("salesId", salesOrderBO.getSalesOrderId());
			model.addAttribute("accountBO", salesOrderBO.getAccountBO());
			model.addAttribute("salesNo", salesOrderBO.getSalesOrderNo());
			model.addAttribute("date", salesOrderBO.getDate());
			if (null != salesOrderBO.getInvoiceName()) {
				model.addAttribute("invoiceName", salesOrderBO.getInvoiceName());
			}
			model.addAttribute("particularSalesList", salesOrderBO.getSalesOrderProductBO());
		}
		return "view-particular-sales-orders";
	}

	@RequestMapping(value = "/createinvoice", method = RequestMethod.GET)
	public String viewinvoicelist(HttpServletRequest request, Model model, HttpSession session) {
		long salesOrderId = 0;
		List<SalesOrderBO> salesOrderlist = new ArrayList<>();
		SalesOrderBO salesOrder = new SalesOrderBO();
		if (null != request.getParameter("salesno")) {

			final String salesOrderNo = request.getParameter("salesno");
			salesOrderId = Long.parseLong(salesOrderNo);
			salesOrder.setSalesOrderId(salesOrderId);
			model.addAttribute("salesno", salesOrderNo);
		}
		if (0 < getUserSecurity().getCompanyId()) { // CompanyId
			long CompanyId = getUserSecurity().getCompanyId();
			salesOrder.setCompanyId(CompanyId);
		}

		salesOrderlist = salesOrderService.getSalesOrderList(salesOrder); // We can send 'Salesorder' number instead of
		// 'SalesOrder' object
		if (null != salesOrderlist && !salesOrderlist.isEmpty() && salesOrderlist.size() > 0) {
			SalesOrderBO sales = salesOrderlist.get(0);
			long size = salesOrderlist.size();
			SalesOrderBO overAllVaue = salesOrderlist.get((int) (size - 1));
			
			// Call the method to calculate invoice values
	   //     overAllVaue = salesOrderService.calculateInvoiceValues(overAllVaue);
			
	        /*	 // Add the calculated values to the model
	        model.addAttribute("overAlltotal", overAllVaue.getTotalInvoice());
	        model.addAttribute("grandTotal", overAllVaue.getGrandTotal());
          //  model.addAttribute("sgstValue", overAllVaue.getSgstValue());
	      //  model.addAttribute("cgstValue", overAllVaue.getCgstValue()); */
			
	        
	/*     // Calculate CGST and SGST
	        double cgstValue = salesOrderService.calculateCGST(overAllVaue);
	        double sgstValue = salesOrderService.calculateSGST(overAllVaue);
	        
	     // Calculate Total with GST
	        double totalPriceWithGst = overAllVaue.getGrandTotal() + cgstValue + sgstValue; */

	        // Add the calculated values to the model
	       // model.addAttribute("overAlltotal", totalPriceWithGst);
	    //    model.addAttribute("grandTotal", overAllVaue.getGrandTotal());
	       // model.addAttribute("sgstValue", sgstValue);
	       // model.addAttribute("cgstValue", cgstValue);   
	    


			model.addAttribute("overAlltotal", overAllVaue.getTotalInvoice());
			model.addAttribute("grandTotal", overAllVaue.getGrandTotal());
			model.addAttribute("salesOrderno", overAllVaue.getSalesOrderNo());
			session.setAttribute("salesOrderId", overAllVaue.getSalesOrderId());
//			model.addAttribute("cgstValue", overAllVaue.getGstBO().getCgstValue());
//			model.addAttribute("sgstValue", overAllVaue.getGstBO().getSgstValue());
//			model.addAttribute("sgst", overAllVaue.getGstBO().getSgst());
//			model.addAttribute("cgst", overAllVaue.getGstBO().getCgst());
			// model.addAttribute("client", sales.getClientBO());
//			model.addAttribute("sales", overAllVaue.getProduct());
			model.addAttribute("date", sales.getDate());
			model.addAttribute("quantity", overAllVaue.getQuantity());
			model.addAttribute("unitprice", overAllVaue.getPrice());
			model.addAttribute("particularSalesList", overAllVaue.getSalesOrderProductBO());
			model.addAttribute("accountBO", overAllVaue.getAccountBO());

		}
		boolean status = salesOrderService.getPaymentStatus(salesOrder);
		model.addAttribute("paymentStatus", status);
		return "view-invoice";
	}

	// Nivetha

	@RequestMapping(value = "/paymentmode", method = RequestMethod.GET)
	public String paymentmodelist(HttpServletRequest request, Model model) {

		PaymentBO paymentbo = new PaymentBO();
		String salesOrderNo = request.getParameter("salesno");
		model.addAttribute("salesno", salesOrderNo);
		paymentbo.setSalesOrderNo(salesOrderNo);

		model.addAttribute("paymentbo", paymentbo);
		return "payment-mode";
	}

	@RequestMapping(value = "/paymentmode", method = RequestMethod.POST)
	public String paymentmodelist(@Valid @ModelAttribute PaymentBO paymentbo, HttpServletRequest req, Model model,
			HttpSession session) throws MySalesException, SerialException, SQLException {

		try {

			long id = (long) session.getAttribute("salesOrderId");
			paymentbo.setSalesOrderId(id);
			PaymentBO paymentBO = salesOrderService.savePayment(paymentbo);
			if (paymentBO != null) {
				model.addAttribute("successMessage", "Payment Obtained Successfully");
				return "redirect:/view-sales-order";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("errorMessage", "Payment cannot be Obtained");
		return "redirect:/view-sales-order";

	}

	@RequestMapping(value = "/getAccountProfile", method = RequestMethod.GET)
	@ResponseBody
	public AccountBO getAccountProfile(@RequestParam String accountId) throws Exception {
		AccountBO accountBO = new AccountBO();
		// long accId = Long.parseLong(accountId);
		int accId = Integer.parseInt(accountId);
		accountBO = salesOrderService.getProfile(accId);
		if (accountBO != null) {
			String email = accountBO.getEmail();
			long contactNo = accountBO.getContactNo();
			String type = accountBO.getType();
			String industry = accountBO.getIndustry();
			String city = accountBO.getCity();
			String state = accountBO.getState();
			String country = accountBO.getCountry();
			return accountBO;
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/AjaxViewPricebook", method = RequestMethod.GET)
	public List<PriceBookBO> getPricebook(HttpServletRequest request, HttpServletResponse response) {
		List<PriceBookVO> pricebookLists = new ArrayList<>();
		List<PriceBookBO> pricebookList = new ArrayList<>();
		System.out.println("ajaxcid::" + request.getParameter("product"));
		String product = request.getParameter("product");
		long serviceId = Long.parseLong(product);
		try {

			List<PriceBookBO> pricebook = new ArrayList<PriceBookBO>();
			pricebookList = priceBookService.getPricebook(serviceId);

			// pricebookLists = salesOrderService.getPricebook(serviceId);

		} catch (Exception e) {

			e.printStackTrace();
			/*
			 * if(LOGGER.isInfoEnabled()) { LOGGER.info("FROM INFO: Exception \t"+e); }
			 * if(LOGGER.isDebugEnabled()) { LOGGER.debug("FROM DEBUG : Exception \t"+e); }
			 */
		}
		return pricebookList;
	}

	@ResponseBody
	@RequestMapping(value = "/get-product-pricebook", method = RequestMethod.GET)
	public List<PriceBookBO> getListPricebook(HttpServletRequest request, HttpServletResponse response) {
		List<PriceBookVO> pricebookLists = new ArrayList<>();
		List<PriceBookBO> pricebookList = new ArrayList<>();
		System.out.println("ajaxcid::" + request.getParameter("serviceId"));
		String product = request.getParameter("serviceId");
		long serviceId = Long.parseLong(product);
		try {

			List<PriceBookBO> pricebook = new ArrayList<PriceBookBO>();
			pricebookList = priceBookService.getPricebook(serviceId);

			// pricebookLists = salesOrderService.getPricebook(serviceId);

		} catch (Exception e) {

			e.printStackTrace();
			/*
			 * if(LOGGER.isInfoEnabled()) { LOGGER.info("FROM INFO: Exception \t"+e); }
			 * if(LOGGER.isDebugEnabled()) { LOGGER.debug("FROM DEBUG : Exception \t"+e); }
			 */
		}
		return pricebookList;
	}
}
