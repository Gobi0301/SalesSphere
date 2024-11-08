package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.AdminService;
import com.scube.crm.service.LeadsService;
import com.scube.crm.service.OpportunityService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.vo.User;

@Controller
@Scope("session")
@SessionAttributes("/admin")
public class OpportunityController extends ControllerUtils implements Serializable {

	@Autowired
	OpportunityService opportunityService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LeadsService leadsService;
	
	@Autowired
	private MessageSource message;

	private static final long serialVersionUID = -5857977996611066292L;
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(OpportunityController.class);

	@RequestMapping(value = "/create-opportunity", method = RequestMethod.GET)
	public String createOpportunity(HttpServletRequest request, Model model)
			throws SerialException, MySalesException, SQLException, NumberFormatException, FileNotFoundException {
		OpportunityController.LOGGER.entry();
		OpportunityBO opportunityBO = new OpportunityBO();
		try {
		long companyId=0;

		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		} else {
			model.addAttribute("opportunityBO", opportunityBO);
		}
         
		AdminUserBO adminuserBO=new AdminUserBO();
		LeadsBO leadsBO = new LeadsBO();
		InventoryBO serviceBO = new InventoryBO();
		AccountBO   accountBO=new AccountBO();
		
		if(0<getUserSecurity().getCompanyId()) {
			 companyId=getUserSecurity().getCompanyId();  //company based create condition
			opportunityBO.setCompanyId(companyId); 
			leadsBO.setCompanyId(companyId);
			serviceBO.setCompanyId(companyId);
			adminuserBO.setCompanyId(companyId);
			accountBO.setCompanyId(companyId);
			
			}
		
		// lead to opportunity convert part
		if (null != request.getParameter("origin") && null != request.getParameter("leadId")) {
			Integer leadId = Integer.valueOf(request.getParameter("leadId"));

			List<Map<?, ?>> leadConversionList = leadsService.getLeadsForOpportunityConversion(leadId, companyId);
			List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
			List<InventoryBO> prodList = opportunityService.getProductList(serviceBO);
			Map<Integer, String> accountList = opportunityService.retriveAccounts(accountBO);
			List<LeadsBO> leadList = opportunityService.retrieveLeads(leadsBO);
			if (leadConversionList.size() > 0 && null != leadConversionList) {
				model.addAttribute("userBOList", userBOList);
				model.addAttribute("opportunityBO", leadConversionList.get(1).get("opportunity"));
				model.addAttribute("prodList", prodList);
				model.addAttribute("accountList", accountList);
				model.addAttribute("leadList", leadList);
				model.addAttribute("origin", request.getParameter("origin"));
				model.addAttribute("leadId", request.getParameter("leadId"));
				return "create-opportunity";
			}
		}

		AdminLoginBO adminLoginBO = new AdminLoginBO();
		
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		adminLoginBO.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBO);
		
		
		
		
//user retrive...
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			// List<AdminUserBO> userBOList = opportunityService.retrieveUser();
			List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
			model.addAttribute("userBOList", userBOList);
		}
		if (0 < loginId && userType.contains("ROLE_COMPANY")) {
			// List<AdminUserBO> userBOList = opportunityService.retrieveUser();
			List<AdminUserBO> userBOList = adminService.retrieveUserByPagination(adminuserBO);
			model.addAttribute("userBOList", userBOList);
		}
//leads retrive...
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			List<LeadsBO> leadList = opportunityService.retrieveLeads(leadsBO);
			model.addAttribute("leadList", leadList);
		}
		if (0 < loginId && userType.contains("ROLE_COMPANY")) {
			List<LeadsBO> leadList = opportunityService.retrieveLeads(leadsBO);
			model.addAttribute("leadList", leadList);
		}
//product retrive....
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			List<InventoryBO> prodList = opportunityService.getProductList(serviceBO);
			model.addAttribute("prodList", prodList);
		}
		if (0 < loginId && userType.contains("ROLE_COMPANY")) {
			List<InventoryBO> prodList = opportunityService.getProductList(serviceBO);
			model.addAttribute("prodList", prodList);
		}
//Accounts retrive...
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {	
			Map<Integer, String> accountList = opportunityService.retriveAccounts(accountBO);
			model.addAttribute("accountList", accountList);
		}
		if (0 < loginId && userType.contains("ROLE_COMPANY")) {	
			Map<Integer, String> accountList = opportunityService.retriveAccounts(accountBO);
			model.addAttribute("accountList", accountList);
		}
		  }catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Create Opporunity has failed:" + ex.getMessage());
				}
				LOGGER.info("Create Opporunity has failed:" + ex.getMessage());
			} finally {
				LOGGER.exit();
			}

		return "create-opportunity";
	}

	@RequestMapping(value = "/create-opportunity", method = RequestMethod.POST)
	public String createOpportunity(@Valid @ModelAttribute("opportunityBO") OpportunityBO opportunityBO, Model model,
			BindingResult result, HttpServletRequest request) throws IOException, MySalesException {
		OpportunityController.LOGGER.entry();
try {
		if (result.hasErrors()) {
			return "redirect:/admin-sign-in";
		}
		long loginId = getUserSecurity().getLoginId();
		// String userType=getUserSecurity().getRole();
		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		if(0<getUserSecurity().getCompanyId()) {
			 
			long companyId=getUserSecurity().getCompanyId();  //company based create condition
			opportunityBO.setCompanyId(companyId); 
			}

		if (null != opportunityBO && 0 < opportunityBO.getLeads().getLeadsId()) {
			LeadsBO leads = new LeadsBO();
			long leadsId = opportunityBO.getLeads().getLeadsId();
			leads.setLeadsId(leadsId);
			opportunityBO.setLeads(leads);
		}
		if (null != opportunityBO && 0 < opportunityBO.getUser().getId()) {
			User user = new User();
			user.setId(opportunityBO.getUser().getId());
			opportunityBO.setUser(user);
		}

		if (null != opportunityBO && 0 < opportunityBO.getProductService().getServiceId()) {
			InventoryBO productService = new InventoryBO();
			productService.setServiceId(opportunityBO.getProductService().getServiceId());
			opportunityBO.setProductService(productService);
			opportunityBO.setIsDelete(false);

		}

		if (null != opportunityBO && 0 < opportunityBO.getAccountBO().getAccountId()) {
			AccountBO accountBO = new AccountBO();
			accountBO.setAccountId(opportunityBO.getAccountBO().getAccountId());
			opportunityBO.setAccountBO(accountBO);
		}
		opportunityBO.setCreatedBy(loginId);
		opportunityBO.setModifiedBy(loginId);

		Long status = opportunityService.create(opportunityBO);

		if (status > 0) {
			if (null != request.getParameter("origin") && null != request.getParameter("leadId")
					&& !request.getParameter("origin").isEmpty() && !request.getParameter("leadId").isEmpty()) {// leads
																												// to
																												// opportunity
																												// conversion
																												// part
				model.addAttribute("successMessage", "Lead To Opportunity Conversion is  Successfull");
				LeadsBO leadsBO = new LeadsBO();
				leadsBO.setIsActive(false);
				leadsBO.setIsDelete(true);
				String leadId = request.getParameter("leadId");
				leadsBO.setLeadsId(Long.valueOf(leadId));
				leadsService.updateOpportunityConversionLeads(leadsBO);
			} else {
			
				model.addAttribute("successMessage",message.getMessage("opportunity.create",null,null));
			}
		} else {
			model.addAttribute("errorMessage", "Oppportunity Creation Failed");
		}
         }catch (Exception ex) {
	       if (LOGGER.isDebugEnabled()) {
	      	LOGGER.debug("Create Opporunity has failed:" + ex.getMessage());
	     }
	         LOGGER.info("Create Opporunity has failed:" + ex.getMessage());
         } finally {
	        LOGGER.exit();
}


		return "redirect:/view-opportunities";
	}

	@RequestMapping(value = "/view-opportunities", method = RequestMethod.GET)
	public String viewOpportunity(Model model, HttpServletRequest request) throws IOException, MySalesException {
		OpportunityController.LOGGER.entry();
        try {
        	long loginId = getUserSecurity().getLoginId();
    		List<String> userType = getUserSecurity().getRole();
    		long companyId=getUserSecurity().getCompanyId();

		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		AdminUserBO adminuserBO=new AdminUserBO();
		LeadsBO leadsBO = new LeadsBO();
		InventoryBO serviceBO = new InventoryBO();
		OpportunityBO opportunityBO = new OpportunityBO();
		
		
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
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
		if(null!=request.getParameter("searchElement")) {
			String firstName = request.getParameter("searchElement");
			opportunityBO.setFirstName(firstName);
			model.addAttribute("searchElement", request.getParameter("searchElement"));
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

	private void opportunityPagination(OpportunityBO opportunityBO, String paging, HttpServletRequest request,
			Model model) throws IOException, MySalesException {
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
		}
		else {
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

	@RequestMapping(value = "search-opportunities", method = RequestMethod.POST)
    public String search(@Valid ModelMap model, @ModelAttribute("searchObj") OpportunityBO opportunityBO,
            BindingResult result, HttpServletRequest request) throws MySalesException {
        OpportunityController.LOGGER.entry();
        try {
        	if (null != request.getParameter("status")) {
				model.addAttribute("status", request.getParameter("status"));
			}
        if (result.hasErrors()) {
            return "redirect:/admin-sign-in";
        }
        long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();

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
        if (0 < loginId && !userType.contains("ROLE_ADMIN")){
			opportunityBO.setCompanyId(companyId); 
			}
        if(null!=opportunityBO.getFirstName()&& !opportunityBO.getFirstName().isEmpty()) {
			model.addAttribute("searchElement", opportunityBO.getFirstName());
		}
        count = opportunityService.searchCount(opportunityBO);
        if (0 != count && count > 0) {
            searchCount = count;
            model.addAttribute("searchCount", searchCount);
        } else {
            model.addAttribute("errorMessage", "No Records Found");
            model.remove("opportunityBOList");
//            return "view-opportunities";
        }
        AdminUserBO adminuserBO=new AdminUserBO();
        LeadsBO leadsBO = new LeadsBO();
        InventoryBO serviceBO = new InventoryBO();
        
        
        if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
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

	@RequestMapping(value = "/view-opportunity-details", method = RequestMethod.GET)
	public String viewOpportunityDetails(Model model, HttpServletRequest request) throws Exception {
		OpportunityController.LOGGER.entry();
try {
		long loginId = getUserSecurity().getLoginId();

		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		String oId = request.getParameter("opportunityId");
		long id = 0;
		if (oId != null) {
			id = Long.parseLong(oId);
		}
		OpportunityBO profile = new OpportunityBO();
		profile = opportunityService.getById(id);
		if (profile != null) {
			model.addAttribute("profile", profile);
		}
         }catch (Exception ex) {
	       if (LOGGER.isDebugEnabled()) {
		     LOGGER.debug("view Opportunity Details has failed:" + ex.getMessage());
	    }
	         LOGGER.info("view Opportunity Details has failed:" + ex.getMessage());
        } finally {
	         LOGGER.exit();
}
		return "view-opportunity-details";
	}

	@RequestMapping(value = "/edit-opportunity", method = RequestMethod.GET)
	public String editOpportunity(Model model, HttpServletRequest request) throws Exception {
		OpportunityController.LOGGER.entry();
		try {
		AdminUserBO adminuserBO=new AdminUserBO();
		LeadsBO leadsBO = new LeadsBO();
		InventoryBO serviceBO = new InventoryBO();
		OpportunityBO opportunityBO = new OpportunityBO();
		OpportunityBO editable = new OpportunityBO();
		AccountBO accountBO=new AccountBO ();
		if(0<getUserSecurity().getCompanyId()) {
			long companyId=getUserSecurity().getCompanyId();  //company based create condition
			leadsBO.setCompanyId(companyId);
			serviceBO.setCompanyId(companyId);
			adminuserBO.setCompanyId(companyId);
			opportunityBO.setCompanyId(companyId);
			accountBO.setCompanyId(companyId);
			editable.setCompanyId(companyId);
			}
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		AdminLoginBO adminLoginBO = new AdminLoginBO();
		adminLoginBO.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBO);
//user retrive..
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			// List<AdminUserBO> userBOList = opportunityService.retrieveUser();
			List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
			model.addAttribute("userBOList", userBOList);
		}
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			// List<AdminUserBO> userBOList = opportunityService.retrieveUser();
			List<AdminUserBO> userBOList = adminService.retrieveUserByPagination(adminuserBO);
			model.addAttribute("userBOList", userBOList);
		}
//Leads retrive...
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			List<LeadsBO> leadList = opportunityService.retrieveLeads(leadsBO);
			model.addAttribute("leadList", leadList);
		}
		if (0 < loginId && userType.contains("ROLE_COMPANY")) {
			List<LeadsBO> leadList = opportunityService.retrieveLeads(leadsBO);
			model.addAttribute("leadList", leadList);
		}
//products retrive....
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			List<InventoryBO> prodList = opportunityService.getProductList(serviceBO);
			model.addAttribute("prodList", prodList);
		}
		if (0 < loginId && userType.contains("ROLE_COMPANY")) {
			List<InventoryBO> prodList = opportunityService.getProductList(serviceBO);
			model.addAttribute("prodList", prodList);
		}
//Accounts retrive...
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			Map<Integer, String> accountList = opportunityService.retriveAccounts(accountBO);
			model.addAttribute("accountList", accountList);
		}
		if (0 < loginId && userType.contains("ROLE_COMPANY")) {
			Map<Integer, String> accountList = opportunityService.retriveAccounts(accountBO);
			model.addAttribute("accountList", accountList);
		}
		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		String oId = request.getParameter("opportunityId");
		long id = 0;
		if (oId != null) {
			id = Long.parseLong(oId);
		}

		
		editable = opportunityService.getById(id);
		if (editable != null) {
			model.addAttribute("editable", editable);
		}
		 }catch (Exception ex) {
		       if (LOGGER.isDebugEnabled()) {
			     LOGGER.debug("Edit Opportunity Details has failed:" + ex.getMessage());
		    }
		         LOGGER.info("Edit Opportunity Details has failed:" + ex.getMessage());
	        } finally {
		         LOGGER.exit();
	        }
		return "edit-opportunity";
	}

	@RequestMapping(value = "/edit-opportunity", method = RequestMethod.POST)
	public String updateOpportunity(@Valid @ModelAttribute("editable") OpportunityBO editable, Model model,
			HttpServletRequest request, BindingResult result) throws IOException, MySalesException {
		OpportunityController.LOGGER.entry();
		try {
		if(0< getUserSecurity().getCompanyId()) {
			 long companyId = getUserSecurity().getCompanyId();
			 editable.setCompanyId(companyId);
			 }
		if (result.hasErrors()) {
			return "redirect:/admin-sign-in";
		}
		long loginId = getUserSecurity().getLoginId();

		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		
		String oId = request.getParameter("opportunityId");
		long id = 0;
		if (oId != null) {
			id = Long.parseLong(oId);
		}

		if (editable != null) {
			editable.setCreatedBy(loginId);
			editable.setModifiedBy(loginId);
			if (null!=editable.getLeads() &&0 < editable.getLeads().getLeadsId()) {
				LeadsBO leads = new LeadsBO();
				long leadsId = editable.getLeads().getLeadsId();
				leads.setLeadsId(leadsId);
				editable.setLeads(leads);
			}

			if (0 < editable.getUser().getId()) {
				User user = new User();
				user.setId(editable.getUser().getId());
				editable.setUser(user);
			}

			if (0 < editable.getProductService().getServiceId()) {
				InventoryBO productService = new InventoryBO();
				productService.setServiceId(editable.getProductService().getServiceId());
				editable.setProductService(productService);
			}
			if (0 < editable.getAccountBO().getAccountId()) {
				AccountBO accountBO = new AccountBO();
				accountBO.setAccountId(editable.getAccountBO().getAccountId());
				editable.setAccountBO(accountBO);

				editable.setIsDelete(false);
			}

			boolean status = opportunityService.update(editable);
			if (status) {
				model.addAttribute("successMessage", "Oppportunity is Updated Successfully");
			}
		}
		 }catch (Exception ex) {
		       if (LOGGER.isDebugEnabled()) {
			     LOGGER.debug("Edit Opportunity Details has failed:" + ex.getMessage());
		    }
		         LOGGER.info("Edit Opportunity Details has failed:" + ex.getMessage());
	        } finally {
		         LOGGER.exit();
	        }

		return "redirect:/view-opportunities";

	}

	@RequestMapping(value = "/delete-opportunity", method = RequestMethod.GET)
	public String deleteOpportunity(Model model, HttpServletRequest request) throws IOException, MySalesException {
		OpportunityController.LOGGER.entry();
        try {
		long loginId = getUserSecurity().getLoginId();

		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}

		String oId = request.getParameter("opportunityId");

		long id = 0;
		if (oId != null) {
			id = Long.parseLong(oId);
		}

		int status = opportunityService.delete(id);
		if (0 < status) {
			model.addAttribute("successMessage", "Oppportunity is Deleted Successfully");
		}
        }catch (Exception ex) {
		       if (LOGGER.isDebugEnabled()) {
			     LOGGER.debug("Delete Opportunity has failed:" + ex.getMessage());
		    }
		         LOGGER.info("Delete Opportunity has failed:" + ex.getMessage());
	        } finally {
		         LOGGER.exit();
	        }
		return "redirect:/view-opportunities";
	}
	
	
	@RequestMapping(value = "/getAssignedTo", method = RequestMethod.GET)
	@ResponseBody
  	public  AdminUserBO getAssignedTo(@RequestParam("accountId")String accountId, HttpServletRequest request, HttpSession session,
			Model model) {
		Long Id =Long.parseLong(accountId);
		AdminUserBO account = new AdminUserBO();
		account.setId(Id);
		//retrieveusers
		try {
			AdminUserBO result =adminService.retrieveusers(Id);
			return result;
		} catch (MySalesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
  		
  	}

	@RequestMapping(value = "/opportunity-tracking-status", method = RequestMethod.GET)
	public String viewCampaignStatus(Model model, HttpServletRequest request, OpportunityBO profile)
			throws MySalesException, SerialException, SQLException {
		OpportunityController.LOGGER.entry();
        try {
		long opportunityId = 0;
		 long count = 0;
	        long activityCount = 0;
		if (null != request.getParameter("opportunityId")) {
			String id = request.getParameter("opportunityId");
			opportunityId = (long) Integer.parseInt(id);
			profile.setOpportunityId(opportunityId);
		}
		if(0< getUserSecurity().getCompanyId()) {
			 long companyId = getUserSecurity().getCompanyId();
			 profile.setCompanyId(companyId);
			 }
		profile = opportunityService.getById(profile.getOpportunityId());
		
		  count = opportunityService.activityCount(profile); //count for activityList service call..
		  if (0 != count && count > 0) {
			  activityCount = count;
	            model.addAttribute("activityCount", activityCount);
	        }
		if (null != profile) {
			// leadsBOList = leadsBO.getLeadsList();
			model.addAttribute("profile", profile);
			// model.addAttribute("leadsBOList", leadsBO.getLeadsUpdateVOList());
			model.addAttribute("opportunityBOLists", profile.getOpportunityactivityList());
		} else {
			model.addAttribute("profile", new OpportunityBO());
			model.addAttribute("opportunityBOLists", null);
		}
        }catch (Exception ex) {
		       if (LOGGER.isDebugEnabled()) {
			     LOGGER.debug("opportunity tracking statushas failed:" + ex.getMessage());
		    }
		         LOGGER.info("opportunity tracking statushas failed:" + ex.getMessage());
	        } finally {
		         LOGGER.exit();
	        }
		return "opportunity-tracking-status";
	}

	@RequestMapping(value = { "/opportunity-tracking-status" }, method = RequestMethod.POST)
	public String registerBO(@RequestParam String description, String convertedDate,String timeSlot,String endTimeSlot, String followupDate,
			long opportunityId, Model model, HttpServletRequest request) throws IOException {
		OpportunityController.LOGGER.entry();
        try {
		OpportunityBO opportunityBO = new OpportunityBO();
		opportunityBO.setOpportunityId(opportunityId);
		opportunityBO.setDescription(description);
		opportunityBO.setConvertedDate(convertedDate);
		opportunityBO.setFollowupDate(followupDate);
		opportunityBO.setTimeSlot(timeSlot);
		opportunityBO.setEndTimeSlot(endTimeSlot);
		if(0< getUserSecurity().getCompanyId()) {
			 long companyId = getUserSecurity().getCompanyId();
			 opportunityBO.setCompanyId(companyId);
			 }
		opportunityBO = opportunityService.saveTracking(opportunityBO);
        }catch (Exception ex) {
		       if (LOGGER.isDebugEnabled()) {
			     LOGGER.debug("opportunity tracking statushas failed:" + ex.getMessage());
		    }
		         LOGGER.info("opportunity tracking statushas failed:" + ex.getMessage());
	        } finally {
		         LOGGER.exit();
	        }
		return "redirect:/opportunity-tracking-status?opportunityId=" + opportunityId;

	}

}