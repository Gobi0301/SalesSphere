package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;

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
import com.scube.crm.bo.WorkItemSLABO;
import com.scube.crm.bo.SlaBO;
import com.scube.crm.bo.TaskManagementBO;
import com.scube.crm.bo.WorkItemBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.WorkItemSLAService;
import com.scube.crm.service.SlaService;
import com.scube.crm.service.WorkItemService;
import com.scube.crm.utils.PaginationClass;

@Controller
public class Manage_WI_SLA_Controller extends ControllerUtils {

	
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(Manage_WI_SLA_Controller.class);
	
	
	   @Autowired
	   WorkItemSLAService workItemSLAService;
	
	   @Autowired
	   WorkItemService workItemService;
	
	   @Autowired
	   SlaService slaService;
	
	
	@RequestMapping(value = "/create-manage_WI_SLA", method = RequestMethod.GET)
	public String Create(Model model, HttpServletRequest request, HttpServletResponse response)
			throws MySalesException {
 
		
		long loginId=getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		 WorkItemBO workItemBO = new WorkItemBO();
		 SlaBO slaBO = new SlaBO();
		AdminUserBO listUserBo = new AdminUserBO();
		
		 
			if(0<getUserSecurity().getCompanyId()) {			//CompanyId
				long CompanyId=getUserSecurity().getCompanyId();
				listUserBo.setCompanyId(CompanyId);
			}
 
			WorkItemSLABO manageBO = new WorkItemSLABO();
		AdminLoginBO adminLoginBo = new AdminLoginBO();
		AdminUserBO adminuserBO=new AdminUserBO();
		
		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			adminuserBO.setCompanyId(companyId);
			manageBO.setCompanyId(companyId);
			workItemBO.setCompanyId(companyId);
			slaBO.setCompanyId(companyId);
		}
		
		 List<WorkItemBO> slaLists = workItemService.findAll(workItemBO);
		 model.addAttribute("slaLists",slaLists);
		 
		 List<SlaBO> slaList = slaService.findAll(slaBO);
			model.addAttribute("slaList",slaList);
		model.addAttribute("manageBO", manageBO);
		adminLoginBo.setId(loginId);
		adminLoginBo.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBo);
		
		return "create-manage_WI_SLA";
	}
	
	@RequestMapping(value = "/create-manage_WI_SLA", method = RequestMethod.POST)
	public String create(@ModelAttribute("manageBO") WorkItemSLABO manageBO, Model model)
			throws IOException, MySalesException {
		LOGGER.entry();

		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				manageBO.setCompanyId(companyId);
			}
			manageBO = workItemSLAService.save(manageBO);
			if (null != manageBO) {
				model.addAttribute("successMessage", "WI_SLA Created Successfully");
				model.addAttribute("manageBO", manageBO);
				return "redirect:/view-manage_WI_SLA";

			} else {
				model.addAttribute("Error Message", "WI_SLA is not created");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create manage_WI_SLA has failed:" + ex.getMessage());
			}
			LOGGER.info("Create manage_WI_SLA has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "redirect:/view-manage_WI_SLA";
	}
	
	@RequestMapping(value = "/view-manage_WI_SLA", method = RequestMethod.GET)
	public String viewTask(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		String paging = null;
		WorkItemSLABO manageBO = new WorkItemSLABO();
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
		}
		if(null!=request.getParameter("searchElement")) {
			String slaCode = request.getParameter("searchElement");
			manageBO.setSlaCode(slaCode);
			model.addAttribute("searchElement", request.getParameter("searchElement"));
		}
		
		AdminLoginBO adminLoginBo = new AdminLoginBO();
		AdminUserBO adminuserBO=new AdminUserBO();
		
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			adminuserBO.setCompanyId(companyId);
			manageBO.setCompanyId(companyId);
			
		}
		// adminLoginBo.setUserType(userType);
		adminLoginBo.setId(loginId);
		adminLoginBo.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBo);
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			adminLoginBO.setId(loginId);
		}
		
		// pagination
		model.addAttribute("manageBO", manageBO);
		managePagination(manageBO, paging, model, session, request);
		model.addAttribute("searchManage", manageBO);
		return "view-manage_WI_SLA";

	}

	private void managePagination(WorkItemSLABO manageBO, String paging, Model model, HttpSession session,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

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
			count = workItemSLAService.retrieveCount(manageBO);
			if (0 != count) {
				totalCount = count;
				model.addAttribute("totalCount",totalCount);
			} else {
				model.addAttribute("errorMessage", "Record not Found!");
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			manageBO.setRecordIndex(startingRecordIndex);
			manageBO.setMaxRecord(maxRecord);
			manageBO.setPagination("pagination");
			List<WorkItemSLABO> manageList = workItemSLAService.findAll(manageBO);
			if (null != manageList && !manageList.isEmpty() && manageList.size() > 0) {
				model.addAttribute("manageList",
						PaginationClass.paginationLimitedRecords(page, manageList, maxRecord, totalCount));
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("manageList Pagination has failed:" + ex.getMessage());
			}
			LOGGER.info("manageList Pagination has failed:" + ex.getMessage());
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
	@RequestMapping(value = "/search-manage_WI_SLA", method = RequestMethod.POST)
	public String searchSla(@ModelAttribute("searchManage") WorkItemSLABO manageBO, HttpServletRequest request, HttpSession session,
			Model model) throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId=getUserSecurity().getCompanyId();
			long count = 0;
			long totalSearchCount = 0;
			int page = 1;
			int maxRecord = 10;

			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				manageBO.setCompanyId(companyId);			
			}
			if(null!=manageBO.getSlaCode() && !manageBO.getSlaCode().isEmpty()) {
				model.addAttribute("searchElement", manageBO.getSlaCode() );
			}
			count = workItemSLAService.retrieveCount(manageBO);
			if (0 != count) {
				totalSearchCount = count;
				model.addAttribute("count", count);
			}

			int startingRecordIndex = paginationPageValues(page, maxRecord);
			manageBO.setRecordIndex(startingRecordIndex);
			manageBO.setMaxRecord(maxRecord);
			manageBO.setPagination("pagination");
			List<WorkItemSLABO> manageList = workItemSLAService.findAll(manageBO); // search service call...
			if (null != manageList && !manageList.isEmpty() && manageList.size() > 0) {
				model.addAttribute("manageList",
						PaginationClass.paginationLimitedRecords(page, manageList, maxRecord, totalSearchCount));
			} else {
				model.addAttribute("errorMessage", "No Records Found");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search manageList has failed:" + ex.getMessage());
			}
			LOGGER.info("Search manageList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-manage_WI_SLA";

	}
	
	
	@RequestMapping(value = "/edit-manage_WI_SLA", method = RequestMethod.GET)
	public String edit(@RequestParam("manageId") long manageId, HttpServletRequest request, HttpSession session,
			Model model) throws Exception {

		try {
			WorkItemSLABO manageBO = new WorkItemSLABO();
			 WorkItemBO workItemBO = new WorkItemBO();
			 SlaBO slaBO = new SlaBO();
			//AdminLoginBO adminLoginBo = new AdminLoginBO();
			AdminUserBO adminuserBO=new AdminUserBO();
			
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				adminuserBO.setCompanyId(companyId);
				manageBO.setCompanyId(companyId);
				workItemBO.setCompanyId(companyId);
				slaBO.setCompanyId(companyId);
			}
			
			manageBO.setManageId(manageId);

			manageBO = workItemSLAService.findById(manageBO);

			if (null != manageBO) {
				
				List<WorkItemBO> slaLists = workItemService.findAll(workItemBO);
				 model.addAttribute("slaLists",slaLists);
				 
				 List<SlaBO> slaList = slaService.findAll(slaBO);
					model.addAttribute("slaList",slaList);
				
				model.addAttribute("manageBO", manageBO);

			} else {
				model.addAttribute("manageBO", manageBO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit manage_WI_SLA has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit manage_WI_SLA has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-manage_WI_SLA";

	}
	@RequestMapping(value = "/edit-manage_WI_SLA", method = RequestMethod.POST)
	public String edit(@ModelAttribute("manageBO") WorkItemSLABO manageBO, BindingResult bindingResult,
			Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException, MySalesException {
		LOGGER.entry();

		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				manageBO.setCompanyId(companyId);
			}
			manageBO = workItemSLAService.update(manageBO);
			if (null != manageBO) {
				model.addAttribute("successMessage", "Update Successfully");
				model.addAttribute("manageBO", manageBO);
				return "redirect:/view-manage_WI_SLA";
			} else {
				model.addAttribute("errorMessage", "Does Not Exists");
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update manage_WI_SLA has failed:" + ex.getMessage());
			}
			LOGGER.info("Update manage_WI_SLA has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-manage_WI_SLA";

	}
	@RequestMapping(value = "/delete-manage_WI_SLA", method = RequestMethod.GET)
	public String deleteProject(@RequestParam("manageId") long manageId, HttpServletRequest request, HttpSession session,
			Model model) throws Exception {

		LOGGER.entry();
		try {
			WorkItemSLABO manageBO = new WorkItemSLABO();
			manageBO.setManageId(manageId);
			if (null != manageBO) {
				boolean value = workItemSLAService.delete(manageBO);
				if (value == true) {
					System.out.println(" manage_WI_SLA deleted successfully");

					model.addAttribute("successMessage", " manage_WI_SLA Deleted Successfully ");

					return "redirect:/view-manage_WI_SLA";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete  manage_WI_SLA has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete  manage_WI_SLA has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view- manage_WI_SLA";

	}
	@RequestMapping(value = "/check_workItemslaCode", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkSlaCode(@RequestParam String slaCode)throws MySalesException {
		LOGGER.entry();
		boolean slaCodeCheck = false;
		try {
			slaCodeCheck = workItemSLAService.checkWISLACode(slaCode);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("WI_SLA CodeCheck has failed:" + ex.getMessage());
			}
			LOGGER.info("WI_SLA CodeCheck has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return slaCodeCheck;
	}
	
}
