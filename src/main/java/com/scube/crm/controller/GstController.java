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
import com.scube.crm.bo.CampaignBO;
import com.scube.crm.bo.GstBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.GstService;
import com.scube.crm.service.OpportunityService;
import com.scube.crm.service.ProductService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.utils.UserRoles;
import com.scube.crm.vo.InventoryVO;

@Controller
@Scope("session")
@SessionAttributes("/admin")
public class GstController extends ControllerUtils implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(GstController.class);

	@Autowired
	private GstService gstService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private OpportunityService opportunityService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/create-gst",method=RequestMethod.GET )
	public String createGst(Model model,HttpServletRequest request,HttpSession session) throws MySalesException{
		GstBO gstBO=new GstBO();
		InventoryBO serviceBO = new InventoryBO();
		long loginId=getUserSecurity().getLoginId();		
		List<String> userType = getUserSecurity().getRole();
		AdminLoginBO adminLoginBo=new AdminLoginBO();
		//adminLoginBo.setUserType(userType);
		adminLoginBo.setId(loginId);
		adminLoginBo.setFirstName(getUserSecurity().getName());
		serviceBO.setCompanyId(loginId);
		model.addAttribute("userType", adminLoginBo);
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			adminLoginBO.setId(loginId);
			gstBO.setAdminLoginBO(adminLoginBO);
			serviceBO.setAdminLoginBO(adminLoginBO);
		}
		List<InventoryBO> productServiceBO = new ArrayList<>();
		long companyId=getUserSecurity().getCompanyId();
		serviceBO.setCompanyId(companyId);
		productServiceBO = opportunityService.getProductList(serviceBO);
		model.addAttribute("productBOList", productServiceBO);
		
		model.addAttribute("gstBO",gstBO);
		
		return "create-gst";
	}

	@RequestMapping(value="/create-gst" , method=RequestMethod.POST)
	public String createGstValues(@Valid @ModelAttribute("gstBO")GstBO gstBO,BindingResult bindingResult,
			Model model,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws FileNotFoundException,MySalesException{
		LOGGER.entry();
		try {
		if(bindingResult.hasErrors()){
			return "create-gst";
		}
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		if(0<getUserSecurity().getCompanyId()) {
			long companyId=getUserSecurity().getCompanyId();  //company based create condition
			gstBO.setCompanyId(companyId); 
			}
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			adminLoginBO.setId(loginId);
			gstBO.setAdminLoginBO(adminLoginBO);
		}
	
		gstBO.setCreatedBy(loginId);
		
		if(null!=gstBO){
				/*
				 * String serviceName=gstBO.getProduct().getServiceName(); long
				 * id=gstBO.getCompanyId(); if(!productService.checkProductName(serviceName,id))
				 */{
			GstBO productBO=gstService.createGstValues(gstBO);
			if(0!=productBO.getGstId()){
				model.addAttribute("successMessage",messageSource.getMessage("Gst.Creation",null,null));
				return "redirect:/view-gst";
			}
		}
		}
		}catch (Exception ex) {
			ex.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Create GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return "redirect:/view-gst";
	}

	@RequestMapping(value ="view-gst", method = RequestMethod.GET)
    public String viewService(Model model, HttpServletRequest request,HttpSession session) throws FileNotFoundException,MySalesException{
        LOGGER.entry();
        GstBO gstBO=new GstBO();
        InventoryBO serviceBO = new InventoryBO();
        try {
        String paging=null;
        if(null!=request.getParameter("page")) {
            paging=request.getParameter("page");
        }
        List<GstBO> gstList=new ArrayList<GstBO>();
        long loginId=getUserSecurity().getLoginId();
        List<String> userType=getUserSecurity().getRole();
        long companyId=getUserSecurity().getCompanyId(); 
        AdminLoginBO adminLoginBO=new AdminLoginBO();
        //adminLoginBO.setUserType(userType);
        adminLoginBO.setId(loginId);
        adminLoginBO.setFirstName(getUserSecurity().getName());
        model.addAttribute("userType", adminLoginBO);
        if (0 < loginId && userType.contains(UserRoles.ROLE_ADMIN.toString())) {
            adminLoginBO.setId(loginId);
            gstBO.setAdminLoginBO(adminLoginBO);
        }
        if (0 < loginId &&! userType.contains("ROLE_ADMIN")) { 
            gstBO.setCompanyId(companyId); 
            serviceBO.setCompanyId(companyId);
        }
		serviceBO.setCompanyId(companyId);
		// particular view products
		if (null != request.getParameter("serviceId")) {
			// InventoryBO inventory=new InventoryBO();
			long serviceId = Long.parseLong(request.getParameter("serviceId"));
			model.addAttribute("serviceId", serviceId);
			if (0 < serviceId) {
				serviceBO.setServiceId(serviceId);
				gstBO.setProduct(serviceBO);

				List<GstBO> gstServiceList = new ArrayList<GstBO>();
				gstServiceList = gstService.getListGst(gstBO);
				if (null != gstServiceList && !gstServiceList.isEmpty() && gstServiceList.size() > 0) {
					model.addAttribute("gstServiceList", gstServiceList);
					model.addAttribute("searchGst", new GstBO());
					return "view-gst";
				}
			}
		}
        
			/*
			 * if(null!=request.getParameter("searchElement")) { String Name =
			 * request.getParameter("searchElement");
			 * 
			 * //gstBO.setProduct(gstBO.getProduct()zzz(accessName));
			 * model.addAttribute("searchElement", request.getParameter("searchElement")); }
			 */
		InventoryBO productBO = new InventoryBO();
		  if ((null != gstBO.getProduct()) && (null != gstBO.getProduct().getServiceName())
	        	    && !gstBO.getProduct().getServiceName().isEmpty()) {
	        	    String serviceId = gstBO.getProduct().getServiceName();
	        	    Integer productId = Integer.parseInt(serviceId);
	        	    productBO.setServiceId(productId);
	        	    gstBO.setProduct(productBO);
	        	}
        
		 //Drop Down
        List<InventoryBO> serviceList = productService.listservice(productBO);
		model.addAttribute("productList", serviceList);
        
    /*    gstList = gstService.getListGst(gstBO);

        if (null != gstList) {
            model.addAttribute("gstList", gstList);
            model.addAttribute("searchGst",new GstBO());
        }*/
        if(null!=request.getParameter("successMessage")) {
            model.addAttribute("successMessage", request.getParameter("successMessage"));
        }
        if(null!=request.getParameter("errorMessage")) {
            model.addAttribute("errorMessage", request.getParameter("errorMessage"));
        }    
        gstPagination(gstBO, paging, model, request);
        model.addAttribute("searchGst",new GstBO());
        
        }
        catch (Exception ex) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("view GST has failed:" + ex.getMessage());
            }
            LOGGER.info("view GST has failed:" + ex.getMessage());
        }finally {
            LOGGER.exit();
        }
        return "view-gst";
    }
	private void gstPagination(GstBO gstBO,String paging,Model model,HttpServletRequest request){
       
		long countOfGST=0;
		int page=1;
		int maxRecord=10;
		long totalCountOfGST=0;

		if(null!=paging) {
			page=Integer.parseInt(paging);
		}

		countOfGST=gstService.countOfGst(gstBO);
		if(0<countOfGST) {
			totalCountOfGST=countOfGST;
			model.addAttribute("totalCountOfGST", totalCountOfGST);
		}else {
			model.addAttribute("errorMessage", "No Record Found!");
		}
		int startingRecordfGst=paginationPageValues(page,maxRecord);
		gstBO.setRecordIndex(startingRecordfGst);
		gstBO.setMaxRecord(maxRecord);

		List<GstBO> gstBOList=new ArrayList<>();
		gstBOList=gstService.listOfGstByPagination(gstBO);
		if(null!=gstBOList&&!gstBOList.isEmpty()&&0<gstBOList.size()) {
			model.addAttribute("gstList", gstBOList);
			model.addAttribute("gstList", PaginationClass.paginationLimitedRecords(page, gstBOList, maxRecord, totalCountOfGST));
		}else {
			model.addAttribute("errorMessage", "No Record Found!");
		}
	}


	private int paginationPageValues(int page, int maxRecord) {
		// TODO Auto-generated method stub

		int recordsOfPage=0;
		if(page==1) {
			recordsOfPage=0;
		}
		else {
			recordsOfPage=(page-1)*maxRecord+1;
			recordsOfPage=recordsOfPage-1;

		}

		return recordsOfPage;
	}
	@RequestMapping(value = "/search-gst", method = RequestMethod.POST)
    public String searchGst( @ModelAttribute("searchGst") GstBO gstBO,
            HttpServletRequest request,HttpSession session, Model model) throws MySalesException, SerialException, SQLException {
        LOGGER.entry();
        try {
        long companyId = getUserSecurity().getCompanyId(); // company based create condition
        long adminId=getUserSecurity().getLoginId();
        List<String> userType=getUserSecurity().getRole();
        AdminLoginBO adminLoginBO=new AdminLoginBO();
        InventoryBO productBO = new InventoryBO();
        
        
        //adminLoginBO.setUserType(userType);
        adminLoginBO.setId(adminId);
        adminLoginBO.setFirstName(getUserSecurity().getName());
        model.addAttribute("userType", adminLoginBO);
        
        GstBO gstBo=new GstBO();
        gstBo.setSgst(gstBO.getSgst());
        gstBo.setCgst(gstBO.getCgst());
        gstBo.setStartDate(gstBO.getStartDate());
        gstBo.setId(adminId);
        long countOfGst=0;
        long totalSearchCount=0;
        int page=1;
        int maxRecord=10;
        String paging = null;
        
        if (null != request.getParameter("page")) {
            paging = request.getParameter("page");
            page = Integer.parseInt(paging);
        }
        long loginId = getUserSecurity().getLoginId();
		if (0 < loginId ) {
			gstBO.setCompanyId(companyId);
		}
//        if(null != gstBo.getProduct().getServiceName() && !gstBo.getProduct().getServiceName().isEmpty()) {
//            model.addAttribute("searchElement", gstBo.getProduct().getServiceName());
//        }
        if ((null != gstBO.getProduct()) && (null != gstBO.getProduct().getServiceName())
        	    && !gstBO.getProduct().getServiceName().isEmpty()) {
        	    String serviceId = gstBO.getProduct().getServiceName();
        	    Integer productId = Integer.parseInt(serviceId);
        	    productBO.setServiceId(productId);
        	    gstBO.setProduct(productBO);
        	}

        if(null!=gstBo) {
            countOfGst=gstService.countOfGstBySearch(gstBo);
        }
        if(0<countOfGst) {
            totalSearchCount=countOfGst;
            model.addAttribute("totalSearchCount", totalSearchCount);
        }
        //Drop Down
        List<InventoryBO> serviceList = productService.listservice(productBO);
		model.addAttribute("productList", serviceList);

        int startingValueOfGst=paginationPageValues(page, maxRecord);
        gstBo.setRecordIndex(startingValueOfGst);
        gstBo.setMaxRecord(maxRecord);

        /*List<GstBO> gstList=new ArrayList<GstBO>();
        
        gstList = gstService.getListGst(gstBo);
        if (null != gstList && 0 < gstList.size()) {
            model.addAttribute("gstList", gstList);
            model.addAttribute("gstList", PaginationClass.paginationLimitedRecords(page, gstList, maxRecord, totalSearchCount));
            model.addAttribute("searchGst",gstBo);
            return "view-gst";
        }else {
            model.addAttribute("errorMessage","No Records Found");
            return "view-gst";
        }*/
        List<GstBO> gstBOList=new ArrayList<>();
        gstBOList=gstService.listOfGstByPagination(gstBo);
        if(null!=gstBOList&&!gstBOList.isEmpty()&&0<gstBOList.size()) {
            model.addAttribute("gstList", PaginationClass.paginationLimitedRecords(page, gstBOList, maxRecord, totalSearchCount));
        }else {
            model.addAttribute("errorMessage", "No Record Found!");
        }
        }catch (Exception ex) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Search GST has failed:" + ex.getMessage());
            }
            LOGGER.info("Search GST has failed:" + ex.getMessage());
        }finally {
            LOGGER.exit();
        }
        return "view-gst";
    }


	@RequestMapping(value = "edit-gst", method = RequestMethod.GET)
	public String editGstValues(Model model, HttpServletRequest request,@RequestParam("gstId") Integer gstId)
			throws FileNotFoundException,MySalesException {
		LOGGER.entry();
		try {
		GstBO gstBO=new GstBO();
		InventoryBO serviceBO = new InventoryBO();
		long adminId=getUserSecurity().getLoginId();	
		AdminLoginBO adminLoginBO=new AdminLoginBO();
		if(0<getUserSecurity().getCompanyId()) {
			long companyId=getUserSecurity().getCompanyId();  //company based  condition
			gstBO.setCompanyId(companyId); 
			serviceBO.setCompanyId(companyId);
			}
		adminLoginBO.setId(adminId);
		adminLoginBO.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBO);
		List<InventoryBO> productServiceBO = new ArrayList<>();
		productServiceBO = opportunityService.getProductList(serviceBO);
		model.addAttribute("productBOList", productServiceBO);
		
			gstBO.setGstId(gstId);
			if(0<gstBO.getGstId()){
				gstBO=gstService.getGstValues(gstBO);
			}
			if (null != gstBO) {
				model.addAttribute("gstBO", gstBO);
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
			return "edit-gst";
		}

		@RequestMapping(value = "edit-gst", method = RequestMethod.POST)
		public String editGstValues(@Valid @ModelAttribute("gstBO") GstBO gstBO, BindingResult bindingResult,
				Model model, HttpServletRequest request, HttpSession session) throws FileNotFoundException,MySalesException {
			LOGGER.entry();
			long loginId=getUserSecurity().getLoginId();
			try {
			List<String> userType=getUserSecurity().getRole();
			if(bindingResult.hasErrors()){
				return "edit-gst";
			}
			if(0<getUserSecurity().getCompanyId()) {
				long companyId=getUserSecurity().getCompanyId();  //company based create condition
				gstBO.setCompanyId(companyId); 
				}
			if (0 < loginId && userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(loginId);
				gstBO.setAdminLoginBO(adminLoginBO);
			} 
			if (0 < loginId &&! userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(loginId);
				gstBO.setAdminLoginBO(adminLoginBO);
			} 
			boolean status =gstService.gstValueUpdate(gstBO);
			if (status) {
				model.addAttribute("successMessage",messageSource.getMessage("Gst.Update",null,null));
				return "redirect:/view-gst";
			}
			else {
				model.addAttribute("errorMessage", "Doesnot Exists");
			}
			
			}catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Update GST has failed:" + ex.getMessage());
				}
				LOGGER.info("Update GST has failed:" + ex.getMessage());
			}finally {
				LOGGER.exit();
			}
			model.addAttribute("gstBO", gstBO);
			InventoryBO serviceBO = new InventoryBO();
			AdminLoginBO adminLoginBO=new AdminLoginBO();
			if(0<getUserSecurity().getCompanyId()) {
				long companyId=getUserSecurity().getCompanyId();  //company based  condition
				gstBO.setCompanyId(companyId); 
				serviceBO.setCompanyId(companyId);
				}
			adminLoginBO.setId(loginId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			List<InventoryBO> productServiceBO = new ArrayList<>();
			productServiceBO = opportunityService.getProductList(serviceBO);
			model.addAttribute("productBOList", productServiceBO);
			model.addAttribute("errorMessage", "Failed to Update");
			return "edit-gst";
		}

		@RequestMapping(value = "/delete-gst", method = RequestMethod.GET)
		public String deleteGstValues(Model model, HttpServletRequest request, HttpSession session,@RequestParam("gstId") Integer gstId)
				throws FileNotFoundException,MySalesException {
			LOGGER.entry();
			GstBO gstBO=new GstBO();
			gstBO.setGstId(gstId);
			try {
				if (null != gstBO) {
					Boolean status =gstService.deleteGstValues(gstBO);
					if (status) {
						model.addAttribute("successMessage", messageSource.getMessage("Gst.Delete",null,null));
						return "redirect:/view-gst";
					}
				}
			} catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deleted GST has failed:" + ex.getMessage());
				}
				LOGGER.info("Deleted GST has failed:" + ex.getMessage());
			}finally {
				LOGGER.exit();
			}
			return "redirect:/view-gst";
		}	
		
		@RequestMapping(value = "/check_sgst", method = RequestMethod.GET)
		@ResponseBody
		public boolean checkSgstValue(@RequestParam String sgst)throws Exception {
		
			boolean sgstCheck = false;
			
			try {
				sgstCheck = gstService.checkSgstValue(sgst);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sgstCheck;
		}
		
		@RequestMapping(value = "/check_cgst", method = RequestMethod.GET)
		@ResponseBody
		public boolean checkCgstValue(@RequestParam String cgst)throws Exception {
		
			boolean cgstCheck = false;
			
			try {
				cgstCheck = gstService.checkCgstValue(cgst);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return cgstCheck;
		}
		
		@RequestMapping(value = "/check_product", method = RequestMethod.GET)
		@ResponseBody
		public boolean checkProductTypes(@RequestParam String product)throws Exception {
		
			boolean productNameCheck = false;
			long id=getUserSecurity().getCompanyId();
			try {
				Long productId=Long.parseLong(product);
				productNameCheck = gstService.checkProduct(productId,id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return productNameCheck;
		}


	}
