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
import com.scube.crm.bo.SkillsBO;
import com.scube.crm.bo.SlaBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.SkillsService;
import com.scube.crm.service.SlaService;
import com.scube.crm.utils.PaginationClass;

@Controller
public class SkillsController extends ControllerUtils {

	
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(SkillsController.class);
	@Autowired
	SkillsService skillsService;
	
	
	
	@RequestMapping(value = "/create-skills", method = RequestMethod.GET)
	public String CreateSkills(Model model, HttpServletRequest request, HttpServletResponse response)
			throws MySalesException {

		SkillsBO skillsBO = new SkillsBO();

		model.addAttribute("skillsBO",skillsBO);

		return "create-skills";

	}

	@RequestMapping(value = "/create-skills", method = RequestMethod.POST)
	public String createSla(@ModelAttribute("skillsBO") SkillsBO skillsBO, Model model) throws IOException, MySalesException {
		LOGGER.entry();

		try {
			
			if (0 < getUserSecurity().getCompanyId()) { // CompanyId
				long CompanyId = getUserSecurity().getCompanyId();
				skillsBO.setCompanyId(CompanyId);
			}

			skillsBO = skillsService.save(skillsBO);
			if (null != skillsBO) {
				model.addAttribute("successMessage", "skills Created Successfully");
				model.addAttribute("skillsBO", skillsBO);
				return "redirect:/view-skills";

			} else {
				model.addAttribute("Error Message", "skills is not created");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Create skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "redirect:/view-skills";

	}

	@RequestMapping(value = "/view-skills", method = RequestMethod.GET)
	public String viewSkills(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		String paging = null;
		SkillsBO skillsBO = new SkillsBO();
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
		}
		
		if(null!=request.getParameter("searchSkillCode")) {
			String skillCode = request.getParameter("searchSkillCode");
			skillsBO.setSkillsCode(skillCode);
			model.addAttribute("searchSkillCode", request.getParameter("searchSkillCode"));
		}

		AdminLoginBO adminLoginBo = new AdminLoginBO();
		AdminUserBO adminuserBO = new AdminUserBO();

		if (0 < loginId && !userType.contains("ROLE_ADMIN")){
			adminuserBO.setCompanyId(companyId);
			skillsBO.setCompanyId(companyId);
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
		model.addAttribute("skillsBO", skillsBO);
		skillsPagination(skillsBO, paging, model, session, request);
		model.addAttribute("searchSkills", skillsBO);
		return "view-skills";

	}

	private void skillsPagination(SkillsBO skillsBO, String paging, Model model, HttpSession session,
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
			if(null!=skillsBO.getSkillsCode()&& !skillsBO.getSkillsCode().isEmpty()) {
				model.addAttribute("searchSkillCode", skillsBO.getSkillsCode()); 
			}
			count = skillsService.retrieveCount(skillsBO);
			if (0 != count) {
				totalCount = count;
				model.addAttribute("totalCount", totalCount);
			} else {
				model.addAttribute("errorMessage", "Record not Found!");
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			skillsBO.setRecordIndex(startingRecordIndex);
			skillsBO.setMaxRecord(maxRecord);
			skillsBO.setPagination("pagination");
			List<SkillsBO> skillsList = skillsService.findAll(skillsBO);
			if (null != skillsList && !skillsList.isEmpty() && skillsList.size() > 0) {
				model.addAttribute("skillsList",
						PaginationClass.paginationLimitedRecords(page, skillsList, maxRecord, totalCount));
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Skills Pagination has failed:" + ex.getMessage());
			}
			LOGGER.info("Skills Pagination has failed:" + ex.getMessage());
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

	@RequestMapping(value = "/search-skills", method = RequestMethod.POST)
	public String searchSkills(@ModelAttribute("searchSkills") SkillsBO skillsBO, HttpServletRequest request, HttpSession session,
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

			if (null != skillsBO.getSkillsCode()) {
				model.addAttribute("slaCode", skillsBO.getSkillsCode());
			}
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				skillsBO.setCompanyId(companyId);
			}
			//Search next page purpose...
			if(null!=skillsBO.getSkillsCode()&& !skillsBO.getSkillsCode().isEmpty()) {
				model.addAttribute("searchSkillCode", skillsBO.getSkillsCode()); 
			}
			count = skillsService.retrieveCount(skillsBO);
			if (0 != count) {
				totalSearchCount = count;
				model.addAttribute("totalSearchCount",totalSearchCount);
			}

			int startingRecordIndex = paginationPageValues(page, maxRecord);
			skillsBO.setRecordIndex(startingRecordIndex);
			skillsBO.setMaxRecord(maxRecord);
			skillsBO.setPagination("pagination");

			List<SkillsBO> skillsList = skillsService.findAll(skillsBO); // search service call...
			if (null != skillsList && !skillsList.isEmpty() && skillsList.size() > 0) {
				model.addAttribute("skillsList",
						PaginationClass.paginationLimitedRecords(page, skillsList, maxRecord, totalSearchCount));
			} else {
				model.addAttribute("errorMessage", "No Records Found");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search skillsList has failed:" + ex.getMessage());
			}
			LOGGER.info("Search skillsList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-skills";

	}

	@RequestMapping(value = "/edit-skills", method = RequestMethod.GET)
	public String editSkills(@RequestParam("skillsId") long skillsId, HttpServletRequest request, HttpSession session,
			Model model) throws Exception {

		try {
			SkillsBO skillsBO = new SkillsBO();

			// AdminLoginBO adminLoginBo = new AdminLoginBO();
			AdminUserBO adminuserBO = new AdminUserBO();

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				adminuserBO.setCompanyId(companyId);
				skillsBO.setCompanyId(companyId);

			}

			skillsBO.setSkillsId(skillsId);

			skillsBO = skillsService.findById(skillsBO);

			if (null != skillsBO) {
				model.addAttribute("skillsBO", skillsBO);

			} else {
				model.addAttribute("skillsBO", skillsBO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-skills";

	}

	@RequestMapping(value = "/edit-skills", method = RequestMethod.POST)
	public String editSkills(@ModelAttribute("skillsBO") SkillsBO skillsBO, BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpSession session) throws FileNotFoundException, MySalesException {
		LOGGER.entry();

		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				skillsBO.setCompanyId(companyId);
			}
			skillsBO = skillsService.update(skillsBO);
			if (null != skillsBO) {
				model.addAttribute("successMessage", "Update Successfully");
				model.addAttribute("skillsBO", skillsBO);
				return "redirect:/view-skills";
			} else {
				model.addAttribute("errorMessage", "Does Not Exists");
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Update Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-skills";

	}

	@RequestMapping(value = "/delete-skills", method = RequestMethod.GET)
	public String deleteProject(@RequestParam("skillsId") long skillsId, HttpServletRequest request, HttpSession session,
			Model model) throws Exception {

		LOGGER.entry();
		try {
			SkillsBO skillsBO = new SkillsBO();
			skillsBO.setSkillsId(skillsId);
			if (null != skillsBO) {
				boolean value = skillsService.delete(skillsBO);
				if (value == true) {
					System.out.println("Skills deleted successfully");

					model.addAttribute("successMessage", "Skills Deleted Successfully ");

					return "redirect:/view-skills";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-skills";

	}
	
	@RequestMapping(value = "/check_skillcode", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkSkillCode(@RequestParam String skillcode)throws MySalesException {
		LOGGER.entry();
		boolean checkSkillCode = false;
		
			long companyId=getUserSecurity().getCompanyId();  //company based create condition
			
		try {
			checkSkillCode = skillsService.checkskillCode(skillcode,companyId);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckSkillCode has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckSkillCode has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkSkillCode;
	}
	
	@RequestMapping(value = "/check_skillname", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkSkillName(@RequestParam String skillname)throws MySalesException {
		LOGGER.entry();
		boolean checkSkillName = false;
		long companyId=getUserSecurity().getCompanyId();  //company based create condition
		try {
			checkSkillName = skillsService.checkskillName(skillname,companyId);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckSkillName has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckSkilName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkSkillName;
	}
}
 
