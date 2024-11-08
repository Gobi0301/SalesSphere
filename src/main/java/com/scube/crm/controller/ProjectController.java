package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scube.crm.bo.ProjectBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.ProjectService;
import com.scube.crm.utils.PaginationClass;

@Controller
public class ProjectController extends ControllerUtils {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(ProjectController.class);
	@Autowired
	private MessageSource messageSource;
	@Autowired
	ProjectService projectService;
	
	@RequestMapping(value = "/create-project", method = RequestMethod.GET)
	public String CreateProject (Model model, HttpServletRequest request,HttpServletResponse response)
			throws MySalesException {
		
		ProjectBO projectBO = new ProjectBO();
		model.addAttribute("projectBO", projectBO);
		
		return "create-project";
	 }
	
	@RequestMapping(value = "/create-project", method = RequestMethod.POST)
	public String createCompany(@ModelAttribute("projectBO") ProjectBO projectBO, Model model) throws IOException,MySalesException {
		LOGGER.entry();

		try {
			 

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // Company create
				projectBO.setCompanyId(companyId);
			}

			projectBO = projectService.createProject(projectBO);
				if  (null!=projectBO) {
					model.addAttribute("successMessage", "Project Created Successfully");
					model.addAttribute("projectBO", projectBO);
					return "redirect:/view-project";
				
			} else {
				model.addAttribute("Error Message", "Project is not created");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create Project has failed:" + ex.getMessage());
			}
			LOGGER.info("Create Project has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "redirect:/view-project";
	}
	
	
	@RequestMapping(value = "/view-project", method = RequestMethod.GET)
	public String viewProject(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		LOGGER.entry();
		String paging=null;
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		if(null!=request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if(null!=request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
		}
		try {
			
			ProjectBO projectBO = new ProjectBO();
			
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				projectBO.setCompanyId(companyId);
			}
			
			if(null!=request.getParameter("searchElement")) {
				String projectName = request.getParameter("searchElement");
				projectBO.setProjectName(projectName);;
				model.addAttribute("searchElement", request.getParameter("searchElement"));
			}

			/*
			 * List<ProjectBO> projectList = projectService.selectAllProjects(projectBO); if
			 * (null != projectList && projectList.size() > 0) { //
			 * model.addAttribute("projectList", projectList); }
			 */
			/*
			 * List<ProjectBO> projectList = projectService.selectAllProjects(projectBO); if
			 * (null != projectList && !projectList.isEmpty() && projectList.size() > 0) {
			 * model.addAttribute("projectList",projectList); }
			 */
			projectPagination(projectBO, paging, request, model);
			model.addAttribute("searchProject", new ProjectBO());
		
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View projectList has failed:" + ex.getMessage());
			}
			LOGGER.info("View projectList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-project";

	}
	
	private void projectPagination(ProjectBO projectBO, String paging, HttpServletRequest request, Model model)
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
			count = projectService.projectCount(projectBO);
			if (0<count) {
				totalCount = count;
				model.addAttribute("totalprojectCount", totalCount);
			} else {
				model.addAttribute("errorMessage", "Record not Found!");
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			projectBO.setRecordIndex(startingRecordIndex);
			projectBO.setMaxRecord(maxRecord);
			projectBO.setPagination("pagination");
			List<ProjectBO> projectList = projectService.selectAllProjects(projectBO);
			if (null != projectList && !projectList.isEmpty() && projectList.size() > 0) {
				model.addAttribute("projectList",
						PaginationClass.paginationLimitedRecords(page, projectList, maxRecord, totalCount));
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Supplier Pagination has failed:" + ex.getMessage());
			}
			LOGGER.info("Supplier Pagination has failed:" + ex.getMessage());
		}finally {
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

	@RequestMapping(value = "/edit-project", method = RequestMethod.GET)
	public String editProject(@RequestParam ("projectId")long projectId ,HttpServletRequest request, HttpSession session, Model model) throws Exception {
	 
		try {
			ProjectBO projectBO = new ProjectBO();

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // Company create
				projectBO.setCompanyId(companyId);
			}
			
				projectBO = projectService.getProjectById(projectId);
			 
			if (null != projectBO) {
				model.addAttribute("projectBO", projectBO);
		
			}else {
				model.addAttribute("projectBO", projectBO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit project has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit project has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-project";

	}

	@RequestMapping(value = "/edit-project", method = RequestMethod.POST)
	public String editProject(@ModelAttribute("projectBO") ProjectBO projectBO,
			BindingResult bindingResult, Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException, MySalesException {
		LOGGER.entry();

		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // Company create
				projectBO.setCompanyId(companyId);
			}

			projectBO = projectService.updateProject(projectBO);
			if (null!=projectBO) {
				model.addAttribute("successMessage", "Update Successfully");
				model.addAttribute("projectBO", projectBO);
				return "redirect:/view-project";
			} else {
				model.addAttribute("errorMessage", "Does Not Exists");
			}
			
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update project has failed:" + ex.getMessage());
			}
			LOGGER.info("Update projectBO has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-project";

	}

	@RequestMapping(value = "/delete-Project", method = RequestMethod.GET)
	public String deleteProject(@RequestParam("projectId") long projectId, HttpServletRequest request,
			HttpSession session, Model model) throws Exception {

		LOGGER.entry();
		try {
			ProjectBO projectBO = new ProjectBO();
			projectBO.setProjectId(projectId);
			if (null != projectBO) {
				boolean value = projectService.deleteProject(projectBO);
				if (value == true) {
					System.out.println("Project deleted successfully");

					model.addAttribute("successMessage", "Project Deleted Successfully ");

					model.addAttribute(" errorMessage", " Project Doesn't Deleted");

					return "redirect:/view-project";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Project has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Project has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-project";

	}
	
	@RequestMapping(value = "/search-project", method = RequestMethod.POST)
	public String searchSupplier( @Valid @ModelAttribute("searchProject") ProjectBO projectBO,BindingResult result,
			HttpServletRequest request,HttpSession session, Model model) throws MySalesException,SerialException, SQLException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId=getUserSecurity().getCompanyId();
			
			if (result.hasErrors()) {

				return "view_supplier";
			}
			List<ProjectBO> projectList = new ArrayList<>();

			long count = 0;
			long totalCount = 0;
			int page = 1;
			int maxRecord = 10;


			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				
				projectBO.setCompanyId(companyId);
			}
			String paging = null;
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
				page = Integer.parseInt(paging);
			}
			if(null!=projectBO.getProjectName() && !projectBO.getProjectName().isEmpty()) {
				model.addAttribute("searchElement", projectBO.getProjectName());
			}
			count = projectService.projectCount(projectBO);
			if (0 != count) {
				totalCount = count;
				model.addAttribute("totalsearchcount", totalCount);
			}
			
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			projectBO.setRecordIndex(startingRecordIndex);
			projectBO.setMaxRecord(maxRecord);
			projectBO.setPagination("pagination");

			List<ProjectBO> projectLists = projectService.selectAllProjects(projectBO); //search service call...
			if (null != projectLists && !projectLists.isEmpty() && projectLists.size() > 0) {
				model.addAttribute("projectList",
						PaginationClass.paginationLimitedRecords(page, projectLists, maxRecord, totalCount));
			}
			else {
				model.addAttribute("errorMessage", "No Records Found");
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search projectList has failed:" + ex.getMessage());
			}
			LOGGER.info("Search projectList has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return "view-project";

	}
	
	@RequestMapping(value = "/project-tracker-status", method = RequestMethod.GET)
	public String viewProject(Model model, HttpServletRequest request, HttpSession session) {
		LOGGER.entry();
		try {
			
			
			int projectId = 0;
			ProjectBO projectBO = new ProjectBO();
			 

			if (null != request.getParameter("projectId")) {
				String id = request.getParameter("projectId");
				projectId = Integer.valueOf(id);
				projectBO.setProjectId(projectId);
			}
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			projectBO = projectService.viewProject(projectBO);
			if (null != projectBO) {
				model.addAttribute("viewProject", projectBO);
				model.addAttribute("projectBOLists", projectBO.getProjectactivityList());
			}
			else {
				model.addAttribute("ProjectBO", new ProjectBO());
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("project tracking status has failed:" + ex.getMessage());
			}
			LOGGER.info("Project tracking status has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		
		return "project-tracker-status";
	}
	@RequestMapping(value = {"/project-tracker-status"}, method = RequestMethod.POST)
	public String registerBO(@RequestParam("Amenities") String amenities,@RequestParam("nearByLocalities") String nearByLocalities,@RequestParam("projectId") long projectId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.entry();
		ProjectBO bo = new ProjectBO();
		bo.setAmenities(amenities);
		bo.setProjectId(projectId);
		bo.setNearByLocalities(nearByLocalities);
		
		bo = projectService.saveTracking(bo);
		if (null!=bo) {
			model.addAttribute("successMessage", "Project Status Updated Successfully");
			
			return "redirect:/project-tracker-status?projectId=" + bo.getProjectId();
		} else {
			model.addAttribute("errorMessage", "Project Tracking Status Not Updated");
		}
			
		return "redirect:/project-tracker-status?projectId=" + bo.getProjectId();	 
			 
	}
}


