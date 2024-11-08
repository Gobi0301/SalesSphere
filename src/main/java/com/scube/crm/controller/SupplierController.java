package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.SupplierBO;
import com.scube.crm.bo.SupplierProductBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.ProductService;
import com.scube.crm.service.SupplierService;
import com.scube.crm.utils.PaginationClass;

@Controller

public class SupplierController extends ControllerUtils implements Serializable {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(SupplierController.class);

	private static final long serialVersionUID = 1L;

	@Autowired
	private SupplierService supplierService;
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "create-supplier", method = RequestMethod.GET)
	public String createSupplier(Model model) throws FileNotFoundException {

		model.addAttribute("SupplierBO", new SupplierBO());
		InventoryBO inventoryBO = new InventoryBO();
		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			inventoryBO.setCompanyId(companyId);

		}

		List<InventoryBO> productBOList = new ArrayList<>();
		productBOList = productService.listOfProductName(inventoryBO);
		model.addAttribute("productList", productBOList);

		return "create-supplier";

	}

	@RequestMapping(value = "create-supplier", method = RequestMethod.POST)
    public String createSupplier(@Valid @ModelAttribute("SupplierBO") SupplierBO supplier, BindingResult result,
            HttpServletRequest request, Model model) throws MySalesException {
        LOGGER.entry();
        long id = getUserSecurity().getLoginId();
        supplier.setCreatedBy(id);
        try {
            if (result.hasErrors()) {
                return "create-supplier";
            }
            if (0 < getUserSecurity().getCompanyId()) {
                long companyId = getUserSecurity().getCompanyId(); // company based create condition
                supplier.setCompanyId(companyId);

            }
            if(null != supplier.getProductServiceBO()) {
            List<InventoryBO> productServiceList = new ArrayList<>();
            List<String> productStringList = new ArrayList<String>(
                    Arrays.asList(supplier.getProductServiceBO().getServiceName().split(",")));
            for (String string : productStringList) {
                long serviceId = 0;
                InventoryBO productServiceBO = new InventoryBO();
                serviceId = Long.parseLong(string);
                productServiceBO.setServiceId(serviceId);
                productServiceList.add(productServiceBO);
            }
            productServiceList = productService.listOfServiceId(productServiceList);
            /*
             * if(null!=productServiceList&&!productServiceList.isEmpty()&&0<
             * productServiceList.size()) {
             * 
             * supplier.setProductServiceLisBO(productServiceList); }
             */
            }
            supplier = supplierService.createSupplier(supplier);
            if (null != supplier) {
                model.addAttribute("successMessage", "Supplier Profile has been Created successfully ");
            } else {
                model.addAttribute(" errorMessage", " Supplier Doesn't Created");
                return "redirect:/create-supplier";
            }

        } catch (Exception ex) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Add Supplier has failed:" + ex.getMessage());
            }
            LOGGER.info("Add Supplier has failed:" + ex.getMessage());
        } finally {
            LOGGER.exit();
        }
        return "redirect:/view_supplier";

    }
	@RequestMapping(value = "view_supplier", method = RequestMethod.GET)
	public String listSupplier(Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException {
		LOGGER.entry();
		String paging = null;
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId = getUserSecurity().getCompanyId();
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
		}

		try {
			SupplierBO supplierBO = new SupplierBO();
			InventoryBO serviceBO = new InventoryBO();

			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				supplierBO.setCompanyId(companyId);
				serviceBO.setCompanyId(companyId);
			}

			supplierPagination(supplierBO, paging, request, model);
			model.addAttribute("searchSupplier", new SupplierBO());
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View Supplier has failed:" + ex.getMessage());
			}
			LOGGER.info("View Supplier has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "view_supplier";

	}

	private void supplierPagination(SupplierBO supplierBO, String paging, HttpServletRequest request, Model model)
			throws IOException, MySalesException {
		LOGGER.entry();
		try {
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
			if (null != request.getParameter("searchElement")) {
				String supplierName = request.getParameter("searchElement");
				supplierBO.setSupplierName(supplierName);
				model.addAttribute("searchElement", request.getParameter("searchElement"));
			}
			count = supplierService.supplierCount(supplierBO);
			if (0 != count) {
				totalCount = count;
				if (0 < totalCount) {
					model.addAttribute("totalCount", totalCount);
				}
			} else {
				model.addAttribute("errorMessage", "No Record Found!");
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			supplierBO.setRecordIndex(startingRecordIndex);
			supplierBO.setMaxRecord(maxRecord);
			supplierBO.setPagination("pagination");
			List<SupplierBO> supplierLists = new ArrayList<>();
			supplierLists = supplierService.listSupplier(supplierBO);
			if (null != supplierLists && !supplierLists.isEmpty() && supplierLists.size() > 0) {
				model.addAttribute("supplierLists",
						PaginationClass.paginationLimitedRecords(page, supplierLists, maxRecord, totalCount));
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Supplier Pagination has failed:" + ex.getMessage());
			}
			LOGGER.info("Supplier Pagination has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
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

	@RequestMapping(value = "edit_supplier", method = RequestMethod.GET)
	public String editSupplier(@RequestParam("supplierId") Long supplierId, Model model) {
		LOGGER.entry();
		try {
			SupplierBO supplierB = new SupplierBO();
			int id;
			supplierB.setSupplierId(supplierId);

			if (0 < supplierId) {
				supplierB = supplierService.selectsupplier(supplierB);
			}
			InventoryBO inventoryBO = new InventoryBO();
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				inventoryBO.setCompanyId(companyId);

			}
			if (null != supplierB) {
				List<InventoryBO> productBOList = new ArrayList<>();
				productBOList = productService.listOfProductName(inventoryBO);
				model.addAttribute("productList", productBOList);
				model.addAttribute("supplierBo", supplierB);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Supplier has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Suppliere has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit_supplier";
	}

	@RequestMapping(value = "edit_supplier", method = RequestMethod.POST)
	public String editSupplier(@Valid @ModelAttribute("supplierBo") SupplierBO sbo, BindingResult bindingResult,
			Model model, HttpServletRequest request, HttpSession session) throws IOException {
		LOGGER.entry();
		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId();
				sbo.setCompanyId(companyId);
			}

			sbo = supplierService.supplierValueUpdate(sbo);

			model.addAttribute("supplierBO", sbo);
			model.addAttribute("successMessage", "supplier profile has been updated successfully");
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit-update Supplier has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit-update Supplier has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view_supplier";
	}

	@RequestMapping(value = "delete_supplier", method = RequestMethod.GET)
	public String deleteSupplier(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam("supplierId") Long supplierId) throws FileNotFoundException {
		LOGGER.entry();
		try {
			SupplierBO supplierBo = new SupplierBO();
			supplierBo.setSupplierId(supplierId);
			if (null != supplierBo) {
				boolean value = supplierService.deleteSupplier(supplierBo);
				if (value == true) {
					System.out.println("supplier deleted successfully");

					model.addAttribute("successMessage", " supplier profile has been deleted successfully ");

					model.addAttribute(" errorMessage", " Supplier Doesn't Deleted");

					return "redirect:/view_supplier";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Supplier has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Supplier has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view_supplier";

	}

	@RequestMapping(value = "/supplier_tracking_status", method = RequestMethod.GET)
	public String viewSupplier(Model model, HttpServletRequest request, @RequestParam("supplierId") Long supplierId)
			throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {
			SupplierBO supplierB = new SupplierBO();
			supplierB.setSupplierId(supplierId);

			supplierB = supplierService.selectsupplier(supplierB);
			model.addAttribute("viewsupplier", supplierB);
			//model.addAttribute("supplierProductLists", supplierB.getSupplierproductList());
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Supplier Tracking Status has failed:" + ex.getMessage());
			}
			LOGGER.info("Supplier Tracking Status has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "supplier_tracking_status";
	}


	@RequestMapping(value="/supplier_product",method=RequestMethod.GET)
	public String addProduct(Model model,HttpServletRequest request)
			throws MySalesException, SerialException,SQLException{
		LOGGER.entry();
		try {
			
			
			InventoryBO inventoryBO = new InventoryBO();
			if(0<getUserSecurity().getCompanyId()) { 
				long  companyId=getUserSecurity().getCompanyId(); //company based create condition;
				inventoryBO.setCompanyId(companyId); 
			}
		
			String id = request.getParameter("supplierId");
			long supplierId = Long.parseLong(id);
			SupplierProductBO supplierProductBO = new SupplierProductBO();
			supplierProductBO.setSupplierId(supplierId);
			 
			 
				List<InventoryBO> productBOList=new ArrayList<>();
				productBOList=productService.listOfProductName(inventoryBO);
				model.addAttribute("productList", productBOList);
				model.addAttribute("SupplierProductBO",supplierProductBO);
				
			
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Adding Product has failed:" + ex.getMessage());
			}
			LOGGER.info("Adding Product has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return "supplier_product";
	}
	
	@RequestMapping(value="/supplier_product",method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("SupplierProductBO") SupplierProductBO supplierproductBO, BindingResult bindingResult,
			Model model, HttpServletRequest request, HttpSession session) throws IOException {
		LOGGER.entry();	
		
		long supplierId=supplierproductBO.getSupplierId();
		try {
		
			 if(0< getUserSecurity().getCompanyId()) 
			 { 
				 long companyId=getUserSecurity().getCompanyId(); 
				 supplierproductBO.setCompanyId(companyId); 
				 }
	
			  List<InventoryBO> productServiceList=new ArrayList<>();
				List<String> productStringList=new ArrayList<String>(Arrays.asList(supplierproductBO.getProductServiceBO().getServiceName().split(",")));
				for (String string : productStringList) {
					long serviceId=0;
					InventoryBO productServiceBO=new InventoryBO();
					serviceId=Long.parseLong(string);
					productServiceBO.setServiceId(serviceId);
					productServiceList.add(productServiceBO);
				}
				productServiceList=productService.listOfServiceId(productServiceList);
				
		      if(null!=productServiceList&&!productServiceList.isEmpty()&&0<productServiceList.size()) {
			  supplierproductBO.setProductServiceListBO(productServiceList); 
			  }
		  	
			  supplierproductBO= supplierService.addProduct(supplierproductBO);
			  if (null !=  supplierproductBO) {
					model.addAttribute("successMessage", "Supplier Product has been Created successfully ");
				} else {
					model.addAttribute("errorMessage", " Supplier Product not Created");
					return "redirect:/supplier_tracking_status?supplierId="+supplierId;
				}
			}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Adding product has failed:" + ex.getMessage());
			}
			LOGGER.info("Adding product has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return "redirect:/supplier_tracking_status?supplierId="+supplierId;
		
	}  

	@RequestMapping(value = "/search-supplier", method = RequestMethod.POST)
	public String searchSupplier(@Valid @ModelAttribute("searchSupplier") SupplierBO supplierBO, BindingResult result,
			HttpServletRequest request, HttpSession session, Model model)
			throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {
			if (result.hasErrors()) {

				return "view_supplier";
			}
			List<SupplierBO> supplierLists = new ArrayList<>();
			long companyId = getUserSecurity().getCompanyId();
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long count = 0;
			long totalSearchCount = 0;
			int page = 1;
			int maxRecord = 10;

			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				supplierBO.setCompanyId(companyId);
			}
			String paging = null;
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
				page = Integer.parseInt(paging);
			}
			if (null != supplierBO.getSupplierName() && !supplierBO.getSupplierName().isEmpty()) {
				model.addAttribute("searchElement", supplierBO.getSupplierName());
			}

			count = supplierService.supplierCount(supplierBO);
			if (0 != count) {
				totalSearchCount = count;
				model.addAttribute("totalSearchCount", totalSearchCount);
			} else {
				model.addAttribute("errorMessage", "No Records Found");
				return "redirect:/view_supplier";
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			supplierBO.setRecordIndex(startingRecordIndex);
			supplierBO.setMaxRecord(maxRecord);
			supplierBO.setPagination("pagination");

			supplierLists = supplierService.listSupplier(supplierBO); // search service call...
			if (null != supplierLists && !supplierLists.isEmpty() && supplierLists.size() > 0) {
				model.addAttribute("supplierLists",
						PaginationClass.paginationLimitedRecords(page, supplierLists, maxRecord, totalSearchCount));
			} else {
				model.addAttribute("errorMessage", "No Records Found");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search Supplier has failed:" + ex.getMessage());
			}
			LOGGER.info("Search Supplier has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view_supplier";

	}

	@RequestMapping(value = "/check_supplieremail", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkSupplierEmail(@RequestParam String emails) throws Exception {
		LOGGER.entry();
		boolean SupplierEmailCheck = false;
		long id = getUserSecurity().getLoginId();
		try {
			SupplierEmailCheck = supplierService.checksupplieremails(emails, id);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckSupplierEmail has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckSupplierEmail has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return SupplierEmailCheck;
	}
}
