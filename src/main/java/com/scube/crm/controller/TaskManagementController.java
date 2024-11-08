package com.scube.crm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.ProjectBO;

import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.LeadsBO;

import com.scube.crm.bo.TaskManagementBO;
import com.scube.crm.bo.WorkItemBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;

import com.scube.crm.service.AdminService;
import com.scube.crm.service.ProductService;
import com.scube.crm.service.ProjectService;

import com.scube.crm.service.AdminService;
import com.scube.crm.service.LeadsService;
import com.scube.crm.service.OpportunityService;
import com.scube.crm.service.ProductService;

import com.scube.crm.service.TaskManagementService;
import com.scube.crm.service.WorkItemService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.vo.TaskTrackingStatusVO;

@Controller
public class TaskManagementController extends ControllerUtils {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(TaskManagementController.class);

	public static final String fileuploads = "C:\\usr\\local\\mysales\\taskmanagement\\";

	@Autowired
	TaskManagementService taskService;

	@Autowired
	private LeadsService leadsService;
	
	@Autowired
	OpportunityService opportunityService;

	@Autowired
	private AdminService adminService;

	@Autowired
	WorkItemService workItemService;

	@Autowired
	ProjectService projectService;

	List<AdminUserBO> adminEmployeeList;

	@RequestMapping(value = "/create-task", method = RequestMethod.GET)
	public String CreateTask(Model model, HttpServletRequest request, HttpServletResponse response)
			throws MySalesException {

		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();

		AdminUserBO listUserBo = new AdminUserBO();
		ProjectBO projectBO = new ProjectBO();
		LeadsBO leadsBO = new LeadsBO();
		AdminUserBO adminuserBO = new AdminUserBO();
		WorkItemBO workItemBO = new WorkItemBO();
		if (0 < getUserSecurity().getCompanyId()) { // CompanyId
			long CompanyId = getUserSecurity().getCompanyId();
			listUserBo.setCompanyId(CompanyId);
			projectBO.setCompanyId(CompanyId);
			leadsBO.setCompanyId(CompanyId);
			//adminuserBO.setCompanyId(CompanyId);
			workItemBO.setCompanyId(CompanyId);
		}

		TaskManagementBO taskBO = new TaskManagementBO();
		AdminLoginBO adminLoginBo = new AdminLoginBO();
		
		if (0 < getUserSecurity().getCompanyId() && !userType.contains("ROLE_ADMIN")) {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			adminuserBO.setCompanyId(companyId);
			taskBO.setCompanyId(companyId);
		}
		model.addAttribute("taskBO", taskBO);
		// adminLoginBo.setUserType(userType);
		adminLoginBo.setId(loginId);
		adminLoginBo.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBo);
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			adminLoginBO.setId(loginId);
			taskBO.setAdminLoginBO(adminLoginBO);

		}
		List<WorkItemBO> slaList = workItemService.findAll(workItemBO);
		model.addAttribute("slaList", slaList);

		List<ProjectBO> projectBOList = new ArrayList<>();
		projectBOList = projectService.selectAllProjects(projectBO);
		model.addAttribute("projectBOList", projectBOList);

		//List<LeadsBO> leadsBOList = new ArrayList<>();

		if (0 < loginId && userType.contains("ROLE_COMPANY")) {
			List<LeadsBO> leadList = opportunityService.retrieveLeads(leadsBO);
			model.addAttribute("leadList", leadList);
		}

		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			// List<AdminUserBO> userBOList = opportunityService.retrieveUser();
			List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
			model.addAttribute("userBOList", userBOList);
		}
		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
			userBOList = adminService.retrieveUserByPagination(adminuserBO);
			model.addAttribute("userBOList", userBOList);
		}

		return "create-task";
	}

	@RequestMapping(value = "/create-task", method = RequestMethod.POST)
	public String createTask(@ModelAttribute("taskBO") TaskManagementBO taskBO, Model model)
			throws IOException, MySalesException {
		LOGGER.entry();
		AdminUserBO adminUserBO = new AdminUserBO();
		WorkItemBO workItemBO = new WorkItemBO();
		ProjectBO projectBO = new ProjectBO();
		LeadsBO leadsBO = new LeadsBO();
		
		try {
			long adminId = Long.parseLong(taskBO.getAdminUserBO().getName());
			long workItemId = Long.parseLong(taskBO.getWorkItemBO().getWorkItem());
			long projectId = Long.parseUnsignedLong(taskBO.getProjectBo().getProjectName());
			long leadsId = Long.parseLong(taskBO.getLeadsBO().getFirstName());
			if (0 != adminId) {
				adminUserBO.setUserId(adminId);
				taskBO.setAdminUserBO(adminUserBO);
			}
			if (0 != workItemId) {
				workItemBO.setWorkItemId(workItemId);
				taskBO.setWorkItemBO(workItemBO);
			}
			if (0 != projectId) {
				projectBO.setProjectId(projectId);
				taskBO.setProjectBo(projectBO);
			}
			if (0 != leadsId) {
				leadsBO.setLeadsId(leadsId);
				taskBO.setLeadsBO(leadsBO);
			}
			if (0 < getUserSecurity().getCompanyId()) { // CompanyId
				long CompanyId = getUserSecurity().getCompanyId();
				taskBO.setCompanyId(CompanyId);
			}

			taskBO = taskService.saveNewTask(taskBO);
			if (0 != taskBO.getTaskId()) {
				model.addAttribute("successMessage", "Task Created Successfully");
				model.addAttribute("taskBO", taskBO);
				return "redirect:/view-task";

			} else {
				model.addAttribute("Error Message", "Task is not created");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Create Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "redirect:/view-Task";
	}

	@RequestMapping(value = "/view-task", method = RequestMethod.GET)
	public String viewTask(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		String paging = null;
		TaskManagementBO taskBO = new TaskManagementBO();
		WorkItemBO workItemBO = new WorkItemBO();
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
		}
		if(null!=request.getParameter("searchLeadName")) {
			String leadName = request.getParameter("searchLeadName");
			taskBO.setLeadName(leadName);
			model.addAttribute("searchLeadName", request.getParameter("searchLeadName"));
		}
		AdminLoginBO adminLoginBo = new AdminLoginBO();
		AdminUserBO adminuserBO = new AdminUserBO();

		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			adminuserBO.setCompanyId(companyId);
			taskBO.setCompanyId(companyId);
			workItemBO.setCompanyId(companyId);

		}
		// adminLoginBo.setUserType(userType);
		adminLoginBo.setId(loginId);
		adminLoginBo.setFirstName(getUserSecurity().getName());
		model.addAttribute("userType", adminLoginBo);
		if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			AdminLoginBO adminLoginBO = new AdminLoginBO();
			adminLoginBO.setId(loginId);
			taskBO.setAdminLoginBO(adminLoginBO);
		}

		List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
		model.addAttribute("userBOList", userBOList);

		List<WorkItemBO> slaList = workItemService.findAll(workItemBO);
		model.addAttribute("slaList", slaList);

		// pagination
		model.addAttribute("taskBO", taskBO);
		taskPagination(taskBO, paging, model, session, request);
		model.addAttribute("searchTask",taskBO);
		return "view-task";

	}

	private void taskPagination(TaskManagementBO taskBO, String paging, Model model, HttpSession session,
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
			//Search next page purpose...
			if(null!=taskBO.getLeadName()&& !taskBO.getLeadName().isEmpty()) {
				model.addAttribute("searchLeadName", taskBO.getLeadName()); 
			}
			count = taskService.retrieveCount(taskBO);
			if (0 != count) {
				totalCount = count;
				model.addAttribute("totalCount", totalCount);
			} else {
				model.addAttribute("errorMessage", "Record not Found!");
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			taskBO.setRecordIndex(startingRecordIndex);
			taskBO.setMaxRecord(maxRecord);
			taskBO.setPagination("pagination");
			List<TaskManagementBO> taskList = taskService.findAll(taskBO);
			if (null != taskList && !taskList.isEmpty() && taskList.size() > 0) {
				model.addAttribute("taskList",
						PaginationClass.paginationLimitedRecords(page, taskList, maxRecord, totalCount));
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Task Pagination has failed:" + ex.getMessage());
			}
			LOGGER.info("Task Pagination has failed:" + ex.getMessage());
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

	@RequestMapping(value = "/search-task", method = RequestMethod.POST)
	public String searchTask(@ModelAttribute("searchTask") TaskManagementBO taskBO, HttpServletRequest request,
			HttpSession session, Model model) throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId=getUserSecurity().getCompanyId();
			
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				taskBO.setCompanyId(companyId);
			}
			long count = 0;
			long totalSearchCount = 0;
			int page = 1;
			int maxRecord = 10;

			if (null != taskBO.getLeadName()) {
				model.addAttribute("leadName", taskBO.getLeadName());
			}
			//Search next page purpose...
			if(null!=taskBO.getLeadName()&& !taskBO.getLeadName().isEmpty()) {
				model.addAttribute("searchLeadName", taskBO.getLeadName()); 
			}
			count = taskService.retrieveCount(taskBO);
			if (0 != count) {
				totalSearchCount = count;
				model.addAttribute("totalSearchCount", totalSearchCount);
			}

			int startingRecordIndex = paginationPageValues(page, maxRecord);
			taskBO.setRecordIndex(startingRecordIndex);
			taskBO.setMaxRecord(maxRecord);
			taskBO.setPagination("pagination");

			List<TaskManagementBO> taskList = taskService.findAll(taskBO); // search service call...
			if (null != taskList && !taskList.isEmpty() && taskList.size() > 0) {
				model.addAttribute("taskList",
						PaginationClass.paginationLimitedRecords(page, taskList, maxRecord, totalSearchCount));
			} else {
				model.addAttribute("errorMessage", "No Records Found");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search taskList has failed:" + ex.getMessage());
			}
			LOGGER.info("Search taskList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-task";

	}

	@RequestMapping(value = "/edit-task", method = RequestMethod.GET)
	public String editTask(@RequestParam("taskId") long taskId, HttpServletRequest request, HttpSession session,
			Model model) throws Exception {
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		try {
			TaskManagementBO taskBO = new TaskManagementBO();
			WorkItemBO workItemBO = new WorkItemBO();
			// AdminLoginBO adminLoginBo = new AdminLoginBO();
			AdminUserBO adminuserBO = new AdminUserBO();
			ProjectBO projectBO = new ProjectBO();
			LeadsBO leadsBO = new LeadsBO();
			AdminUserBO listUserBo = new AdminUserBO();

			if (0 < getUserSecurity().getCompanyId()&&!userType.contains("ROLE_ADMIN")) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				adminuserBO.setCompanyId(companyId);
				taskBO.setCompanyId(companyId);
				projectBO.setCompanyId(companyId);
				leadsBO.setCompanyId(companyId);
				listUserBo.setCompanyId(companyId);
				workItemBO.setCompanyId(companyId);
			}

			taskBO.setTaskId(taskId);

			taskBO = taskService.findById(taskBO);

			if (null != taskBO) {
				
				if (0 < loginId && userType.contains("ROLE_ADMIN")) {
					// List<AdminUserBO> userBOList = opportunityService.retrieveUser();
					List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
					model.addAttribute("userBOList", userBOList);
				}

				if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
					List<AdminUserBO> userBOList = new ArrayList<AdminUserBO>();
					userBOList = adminService.retrieveUserByPagination(adminuserBO);
					model.addAttribute("userBOList", userBOList);
				}

				List<ProjectBO> projectBOList = projectService.selectAllProjects(projectBO);
				model.addAttribute("projectBOList", projectBOList);

				List<WorkItemBO> slaList = workItemService.findAll(workItemBO);
				model.addAttribute("slaList", slaList);
				
				List<LeadsBO> leadList = opportunityService.retrieveLeads(leadsBO);
				model.addAttribute("leadList", leadList);
				/*
				 * List<LeadsBO> leadsBOList = new ArrayList<>(); leadsBOList =
				 * leadsService.getListLeads(leadsBO); model.addAttribute("leadsBOList",
				 * leadsBOList);
				 */

				model.addAttribute("taskBO", taskBO);

			} else {
				model.addAttribute("taskBO", taskBO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-task";

	}

	@RequestMapping(value = "/edit-task", method = RequestMethod.POST)
	public String editTask(@ModelAttribute("taskBO") TaskManagementBO taskBO, BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpSession session) throws FileNotFoundException, MySalesException {
		LOGGER.entry();

		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				taskBO.setCompanyId(companyId);
			}
			taskBO = taskService.update(taskBO);
			if (null != taskBO) {
				model.addAttribute("successMessage", "Update Successfully");
				model.addAttribute("taskBO", taskBO);
				return "redirect:/view-task";
			} else {
				model.addAttribute("errorMessage", "Does Not Exists");
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update task has failed:" + ex.getMessage());
			}
			LOGGER.info("Update task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-task";

	}

	@RequestMapping(value = "/task-tracker-status", method = RequestMethod.GET)
	public String view(Model model, HttpServletRequest request, HttpSession session, TaskManagementBO taskBO) {
		LOGGER.entry();

		try {

			int taskId = 0;

			if (null != request.getParameter("taskId")) {
				String id = request.getParameter("taskId");
				taskId = Integer.valueOf(id);
				taskBO.setTaskId(taskId);
			}
			taskBO = taskService.findById(taskBO);
			if (null != taskBO) {
				model.addAttribute("tasklist", taskBO);
				model.addAttribute("taskBOList", taskBO.getTaskupdateList());
			} else {
				model.addAttribute("tasklists", new TaskManagementBO());

				model.addAttribute("taskBOList", null);
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Task tracking status has failed:" + ex.getMessage());
			}
			LOGGER.info("Task tracking status has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "task-tracker-status";
	}

	@RequestMapping(value = "/task-tracker-status", method = RequestMethod.POST)
	public String registerBO(@RequestParam("uploadTask") MultipartFile uploadFile, long taskId, String descriptions,
			String date, String report, Model model, HttpServletRequest request, HttpServletResponse response)
					throws IOException {

		String campaignfileName = null;
		String activityid;

		TaskManagementBO bo = new TaskManagementBO();
		bo.setTaskId(taskId);
		bo.setDescriptions(descriptions);
		bo.setReport(report);
		bo.setDate(date);

		long count = taskService.retrieveCount();
		if (0 < count) {
			long Id = count + 1;
			activityid = String.valueOf(Id);
		} else {
			long Id = 1;
			activityid = String.valueOf(Id);
		}
		if (null != uploadFile) {
			String fileName = uploadFile.getOriginalFilename();
			String[] words = fileName.split("\\.");
			int length = words.length;
			System.out.println(length);
			String format = words[length - 1];
			String image = activityid + "." + format;
			if (!fileName.isEmpty()) {
				campaignfileName = fileuploads + activityid + "." + format;
				writeImageFile(campaignfileName, uploadFile);
				bo.setUploadfile(image);

			}
			bo = taskService.saveTracking(bo);

			return "redirect:/task-tracker-status?taskId=" + bo.getTaskId();

		}
		return null;
	}

	private void writeImageFile(String campaignfileName, MultipartFile uploadFile) throws IOException {
		File file = new File(campaignfileName);
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(uploadFile.getBytes());

	}

	@RequestMapping(value = "taskDownload", method = RequestMethod.GET)
	public String getDownload(@RequestParam("taskTrackingId") long taskId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		ModelAndView model = new ModelAndView();
		String filelocation = null;
		TaskTrackingStatusVO activityVO = taskService.taskTrackingStatus(taskId);
		if (null != activityVO) {
			filelocation = "C:\\usr\\local\\mysales\\taskmanagement\\" + activityVO.getUploadfile();
		}

		String downloadPath = filelocation;
		File file = new File(downloadPath);
		InputStream is = new FileInputStream(file);
		response.setContentType("application/pdf");

		response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");

		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		os.flush();
		os.close();
		is.close();
		model.addObject("errorMessage", "file Download Successfully!");

		return "redirect:/task-tracker-status?taskId";

	}

	@RequestMapping(value = "/delete-task", method = RequestMethod.GET)
	public String deleteProject(@RequestParam("taskId") long taskId, HttpServletRequest request, HttpSession session,
			Model model) throws Exception {

		LOGGER.entry();
		try {
			TaskManagementBO taskBO = new TaskManagementBO();
			taskBO.setTaskId(taskId);
			if (null != taskBO) {
				boolean value = taskService.delete(taskBO);
				if (value == true) {
					System.out.println("Task deleted successfully");

					model.addAttribute("successMessage", "Task Deleted Successfully ");

					return "redirect:/view-task";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-task";

	}

}
