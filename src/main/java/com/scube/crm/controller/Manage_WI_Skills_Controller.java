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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.WorkItemSLABO;
import com.scube.crm.bo.WorkItemSkillsBO;
import com.scube.crm.bo.SkillsBO;
import com.scube.crm.bo.WorkItemBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.WorkItemSkillsService;
import com.scube.crm.service.SkillsService;
import com.scube.crm.service.WorkItemService;
import com.scube.crm.utils.PaginationClass;

@Controller
public class Manage_WI_Skills_Controller extends ControllerUtils implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -935510208810267870L;
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(Manage_WI_Skills_Controller.class);


	@Autowired
	private WorkItemSkillsService workItemSkillsService;

	@Autowired
	WorkItemService workItemService;

	@Autowired
	SkillsService skillsService;

	@RequestMapping(value = "/create-manage_WI_Skills", method = RequestMethod.GET)
	public String Create(Model model, HttpServletRequest request, HttpServletResponse response)
	throws MySalesException {
	 

	long loginId=getUserSecurity().getLoginId();
	List<String> userType = getUserSecurity().getRole();
	WorkItemBO workItemBO = new WorkItemBO();
	SkillsBO slaBO = new SkillsBO();
	AdminUserBO listUserBo = new AdminUserBO();


	if(0<getUserSecurity().getCompanyId()) { //CompanyId
	long CompanyId=getUserSecurity().getCompanyId();
	listUserBo.setCompanyId(CompanyId);
	}
	 
	WorkItemSkillsBO manageBO = new WorkItemSkillsBO();
	AdminLoginBO adminLoginBo = new AdminLoginBO();
	AdminUserBO adminuserBO=new AdminUserBO();

	if (0 < getUserSecurity().getCompanyId() && !userType.contains("ROLE_ADMIN")) {
	long companyId = getUserSecurity().getCompanyId(); // company based create condition
	adminuserBO.setCompanyId(companyId);
	manageBO.setCompanyId(companyId);
	workItemBO.setCompanyId(companyId);
	slaBO.setCompanyId(companyId);
	}
	
	List<WorkItemBO> slaLists = workItemService.findAllWorkitem(workItemBO);
	model.addAttribute("slaLists",slaLists);

	List<SkillsBO> slaList = skillsService.findAll(slaBO);
	model.addAttribute("slaList",slaList);
	model.addAttribute("manageBO", manageBO);
	adminLoginBo.setId(loginId);
	adminLoginBo.setFirstName(getUserSecurity().getName());
	model.addAttribute("userType", adminLoginBo);

	return "create-manage_WI_Skills";
	}

	@RequestMapping(value = "/create-manage_WI_Skills", method = RequestMethod.POST)
	public String create(@ModelAttribute("manageBO") WorkItemSkillsBO manageBO, Model model)
	throws IOException, MySalesException {
	LOGGER.entry();

	try {
		
		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			manageBO.setCompanyId(companyId);
			}

	manageBO = workItemSkillsService.save(manageBO);
	if (null != manageBO) {
	model.addAttribute("successMessage", "manage_WI_Skills Created Successfully");
	model.addAttribute("manageBO", manageBO);
	return "redirect:/view-manage_WI_Skills";

	} else {
	model.addAttribute("Error Message", "manage_WI_Skills is not created");
	}
	} catch (Exception ex) {
	if (LOGGER.isDebugEnabled()) {
	LOGGER.debug("Create manage_WI_Skills has failed:" + ex.getMessage());
	}
	LOGGER.info("Create manage_WI_Skills has failed:" + ex.getMessage());
	} finally {
	LOGGER.exit();
	}

	return "redirect:/view-manage_WI_Skills";
	}

	@RequestMapping(value = "/view-manage_WI_Skills", method = RequestMethod.GET)
	public String viewTask(HttpServletRequest request, HttpSession session, Model model) throws Exception {
	LOGGER.entry();
	long loginId = getUserSecurity().getLoginId();
	List<String> userType = getUserSecurity().getRole();
	long companyId=getUserSecurity().getCompanyId();
	String paging = null;
	WorkItemSkillsBO manageBO = new WorkItemSkillsBO();
		/*
		 * if (null != request.getParameter("successMessage")) {
		 * model.addAttribute("successMessage", request.getParameter("successMessage"));
		 * } if (null != request.getParameter("errorMessage")) {
		 * model.addAttribute("errorMessage", request.getParameter("errorMessage")); }
		 */
	if (null != request.getParameter("page")) {
	paging = request.getParameter("page");
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
	return "view-manage_WI_Skills";

	}

	private void managePagination(WorkItemSkillsBO manageBO, String paging, Model model, HttpSession session,
	HttpServletRequest request) {

	LOGGER.entry();
	try {
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
	long count = 0;
	long totalCount = 0;
	int page = 1;
	int maxRecord = 10;
	String pages=request.getParameter("d-49216-p");
	
	WorkItemBO workItemBO=new WorkItemBO();

	if (null != request.getParameter("successMessage")&&null==pages) {
	model.addAttribute("successMessage", request.getParameter("successMessage"));
	}
	if (null != request.getParameter("errorMessage")&&null==pages) {
	model.addAttribute("errorMessage", request.getParameter("errorMessage"));
	}
	
//	if(null!=request.getParameter("searchElement")&& ! request.getParameter("searchElement").isEmpty()) {
//		String workItemId = request.getParameter("searchElement");
//		Integer id = Integer.parseInt(workItemId);
//		workItemBO.setWorkItemId(id);
//
//		manageBO.setWorkitemBO(workItemBO);
//		model.addAttribute("searchElement", request.getParameter("searchElement"));
//	}
	
	if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
		workItemBO.setCompanyId(companyId);
		manageBO.setCompanyId(companyId);

		}
	
//	List<WorkItemBO> slaLists = workItemService.findAll(workItemBO);
//	model.addAttribute("slaLists",slaLists);
	
	List<WorkItemSkillsBO> slaLists = workItemSkillsService.findAllList(manageBO);
	model.addAttribute("slaLists",slaLists);

	if (null != paging) {
	page = Integer.parseInt(paging);
	}
	
	
	count = workItemSkillsService.retrieveCount(manageBO);
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
	List<WorkItemSkillsBO> manageList = workItemSkillsService.findAll(manageBO);
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
	@RequestMapping(value = "/search-manage_WI_Skills", method = RequestMethod.POST)
	public String searchSla(@ModelAttribute("searchManage") WorkItemSkillsBO manageBO, HttpServletRequest request, HttpSession session,
			Model model) throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId=getUserSecurity().getCompanyId();
			WorkItemBO workItemBO=new WorkItemBO();
			
			long count = 0;
			long totalSearchCount = 0;
			int page = 1;
			int maxRecord = 10;
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				workItemBO.setCompanyId(companyId);
				manageBO.setCompanyId(companyId);

				}
			 
			if(null!=manageBO.getWorkitemBO() && null != manageBO.getWorkitemBO().getWorkItem()) {
				
				model.addAttribute("searchElement",manageBO.getWorkitemBO().getWorkItem());
			}
			
			if (null != manageBO.getWorkitemBO() 
					&& null != manageBO.getWorkitemBO().getWorkItem()
					&& !manageBO.getWorkitemBO().getWorkItem().isEmpty()) {

				String workItemId = manageBO.getWorkitemBO().getWorkItem();

				Integer id = Integer.parseInt(workItemId);
				workItemBO.setWorkItemId(id);

				manageBO.setWorkitemBO(workItemBO);
			}
			count = workItemSkillsService.retrieveCount(manageBO);
			if (0 != count) {
				totalSearchCount = count;
				model.addAttribute("totalSearchCount",totalSearchCount);
			}

			int startingRecordIndex = paginationPageValues(page, maxRecord);
			manageBO.setRecordIndex(startingRecordIndex);
			manageBO.setMaxRecord(maxRecord);
			manageBO.setPagination("pagination");
			
			List<WorkItemSkillsBO> slaLists = workItemSkillsService.findAllList(manageBO);
			model.addAttribute("slaLists",slaLists);
			
//			List<WorkItemBO> slaLists = workItemService.findAll(workItemBO);
//			model.addAttribute("slaLists",slaLists);

			List<WorkItemSkillsBO> manageList = workItemSkillsService.findAll(manageBO); // search service call...
			if (null != manageList && !manageList.isEmpty() && manageList.size() > 0) {
				model.addAttribute("manageList", manageList);
				model.addAttribute("manageList",
						PaginationClass.paginationLimitedRecords(page, manageList, maxRecord, totalSearchCount));
				return "view-manage_WI_Skills";
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
		
		return "redirect:/view-manage_WI_Skills";

	}
	private void getListOfSkill(Model model, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.entry();
		try {
			SkillsBO skillsBO=new SkillsBO();
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				skillsBO.setCompanyId(companyId);
				}
			
			List<SkillsBO> skillsListBO = skillsService.findAll(skillsBO);
		if (null != skillsListBO && !skillsListBO.isEmpty() && 0 < skillsListBO.size()) {

			model.addAttribute("SkillsListBO", skillsListBO);

		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getListOfAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("getListOfAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		
	}
	@RequestMapping(value = "/edit-manage_WI_Skills", method = RequestMethod.GET)
	public String edit(@RequestParam("manageId") long manageId, HttpServletRequest request, HttpSession session,
	Model model,HttpServletResponse response) throws Exception {

	try {
		getListOfSkill(model, request, response);
	WorkItemSkillsBO manageBO = new WorkItemSkillsBO();
	WorkItemBO workItemBO = new WorkItemBO();
	SkillsBO slaBO = new SkillsBO();
	//AdminLoginBO adminLoginBo = new AdminLoginBO();
	AdminUserBO adminuserBO=new AdminUserBO();

	if (0 < getUserSecurity().getCompanyId()) {
	long companyId = getUserSecurity().getCompanyId(); // company based create condition
	adminuserBO.setCompanyId(companyId);
	manageBO.setCompanyId(companyId);
	workItemBO.setCompanyId(companyId);
	}

	manageBO.setManageId(manageId);

	manageBO = workItemSkillsService.findById(manageBO);

	if (null != manageBO) {
		workItemBO.setWorkItemId(manageBO.getWorkitemBO().getWorkItemId());
	List<WorkItemBO> slaLists = workItemService.findAll(workItemBO);
	model.addAttribute("slaLists",slaLists);

	

	model.addAttribute("manageBO", manageBO);

	} else {
	model.addAttribute("manageBO", manageBO);
	}
	} catch (Exception ex) {
	if (LOGGER.isDebugEnabled()) {
	LOGGER.debug("Edit manage_WI_Skills has failed:" + ex.getMessage());
	}
	LOGGER.info("Edit manage_WI_Skills has failed:" + ex.getMessage());
	} finally {
	LOGGER.exit();
	}
	return "edit-manage_WI_Skills";

	}
	

	@RequestMapping(value = "/edit-manage_WI_Skills", method = RequestMethod.POST)
	public String edit(@ModelAttribute("manageBO") WorkItemSkillsBO manageBO, BindingResult bindingResult,
	Model model, HttpServletRequest request, HttpSession session)
	throws FileNotFoundException, MySalesException {
	LOGGER.entry();

	try {
		
		if (0 < getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			manageBO.setCompanyId(companyId);
			}

		List<SkillsBO> skillsBoList = new ArrayList<>();
		if (null != manageBO && null != manageBO.getSkillsIds() && manageBO.getSkillsIds().size() > 0) {
			if (null != manageBO) {
				 for (Long id : manageBO.getSkillsIds()) {
					 SkillsBO skillsBO = new SkillsBO();
					 skillsBO.setSkillsId(id);
					skillsBoList.add(skillsBO); 
 }}

			if (null != skillsBoList && !skillsBoList.isEmpty() && 0 < skillsBoList.size()) {
			manageBO.setSkillsListBO(skillsBoList);
			}
				 }

	manageBO = workItemSkillsService.update(manageBO);
	if (null != manageBO) {
	model.addAttribute("successMessage", "Update Successfully");
	model.addAttribute("manageBO", manageBO);
	return "redirect:/view-manage_WI_Skills";
	} else {
	model.addAttribute("errorMessage", "Does Not Exists");
	}

	} catch (Exception ex) {
	if (LOGGER.isDebugEnabled()) {
	LOGGER.debug("Update manage_WI_Skills has failed:" + ex.getMessage());
	}
	LOGGER.info("Update manage_WI_Skills has failed:" + ex.getMessage());
	} finally {
	LOGGER.exit();
	}
	return "redirect:/view-manage_WI_Skills";

	}
	@RequestMapping(value = "/delete-manage_WI_Skills", method = RequestMethod.GET)
	public String deleteProject(@RequestParam("manageId") long manageId, HttpServletRequest request, HttpSession session,
	Model model) throws Exception {

	LOGGER.entry();
	try {
	WorkItemSkillsBO manageBO = new WorkItemSkillsBO();
	manageBO.setManageId(manageId);
	if (null != manageBO) {
	boolean value = workItemSkillsService.delete(manageBO);
	if (value == true) {
	System.out.println(" manage_WI_Skills deleted successfully");

	model.addAttribute("successMessage", " manage_WI_Skills Deleted Successfully ");

	return "redirect:/view-manage_WI_Skills";
	}
	}
	} catch (Exception ex) {
	if (LOGGER.isDebugEnabled()) {
	LOGGER.debug("Delete  manage_WI_Skills has failed:" + ex.getMessage());
	}
	LOGGER.info("Delete  manage_WI_Skills has failed:" + ex.getMessage());
	} finally {
	LOGGER.exit();
	}
	return "redirect:/view-manage_WI_Skills";

	}



	}

