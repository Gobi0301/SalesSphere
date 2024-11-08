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
import com.scube.crm.bo.PlotBO;
import com.scube.crm.bo.SlaBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.SlaService;
import com.scube.crm.utils.PaginationClass;

@Controller
public class SlaController extends ControllerUtils {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(SlaController.class);
	@Autowired
	SlaService slaService;

	@RequestMapping(value = "/create-sla", method = RequestMethod.GET)
	public String CreateSla(Model model, HttpServletRequest request, HttpServletResponse response)
			throws MySalesException {

		SlaBO slaBO = new SlaBO();

		model.addAttribute("slaBO", slaBO);

		return "create-sla";

	}

	@RequestMapping(value = "/create-sla", method = RequestMethod.POST)
	public String createSla(@ModelAttribute("slaBO") SlaBO slaBO, Model model) throws IOException, MySalesException {
		LOGGER.entry();

		try {
			if(0<getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId();   // Company create
				slaBO.setCompanyId(companyId);
			}
			slaBO = slaService.save(slaBO);
			if (null != slaBO) {
				model.addAttribute("successMessage", "Sla Created Successfully");
				model.addAttribute("slaBO", slaBO);
				return "redirect:/view-sla";

			} else {
				model.addAttribute("Error Message", "Sla is not created");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create Sla has failed:" + ex.getMessage());
			}
			LOGGER.info("Create Sla has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "redirect:/view-sla";

	}

	@RequestMapping(value = "/view-sla", method = RequestMethod.GET)
	public String viewSla(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		String paging = null;
		SlaBO slaBO = new SlaBO();
		if (null != request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			if(request.getParameter("errorMessage").equals("No Records Found")) {
				model.addAttribute("searchSla", new SlaBO ());
				return "view-plot";
				
			}
		}
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
		}

		AdminLoginBO adminLoginBo = new AdminLoginBO();
		AdminUserBO adminuserBO = new AdminUserBO();

		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			adminuserBO.setCompanyId(companyId);
			slaBO.setCompanyId(companyId);

		}
		if(null!=request.getParameter("searchElement")) {
			String slaCode = request.getParameter("searchElement");
			slaBO.setSlaCode(slaCode);
			model.addAttribute("searchElement", request.getParameter("searchElement"));
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
		model.addAttribute("slaBO", slaBO);
		slaPagination(slaBO, paging, model, session, request);
		model.addAttribute("searchSla", slaBO);
		return "view-sla";

	}

	private void slaPagination(SlaBO slaBO, String paging, Model model, HttpSession session,
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
			
			count = slaService.retrieveCount(slaBO);
			if (0 != count) {
				totalCount = count;
				model.addAttribute("totalCount",totalCount);
			} else {
				model.addAttribute("errorMessage", "Record not Found!");
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			slaBO.setRecordIndex(startingRecordIndex);
			slaBO.setMaxRecord(maxRecord);
			slaBO.setPagination("pagination");
			List<SlaBO> slaList = slaService.findAll(slaBO);
			if (null != slaList && !slaList.isEmpty() && slaList.size() > 0) {
				model.addAttribute("slaList",
						PaginationClass.paginationLimitedRecords(page, slaList, maxRecord, totalCount));
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Sla Pagination has failed:" + ex.getMessage());
			}
			LOGGER.info("Sla Pagination has failed:" + ex.getMessage());
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

	@RequestMapping(value = "/search-sla", method = RequestMethod.POST)
	public String searchSla(@ModelAttribute("searchSla") SlaBO slaBO, HttpServletRequest request, HttpSession session,
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

			if (null != slaBO.getSlaCode()) {
				model.addAttribute("slaCode", slaBO.getSlaCode());
			}
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				slaBO.setCompanyId(companyId);
			}
			if(null!=slaBO.getSlaCode() && !slaBO.getSlaCode().isEmpty()) {
				model.addAttribute("searchElement", slaBO.getSlaCode() );
			}
			count = slaService.retrieveCount(slaBO);
			if (0 != count) {
				totalSearchCount = count;
				model.addAttribute("count", count);
			}

			int startingRecordIndex = paginationPageValues(page, maxRecord);
			slaBO.setRecordIndex(startingRecordIndex);
			slaBO.setMaxRecord(maxRecord);
			slaBO.setPagination("pagination");

			List<SlaBO> slaList = slaService.findAll(slaBO); // search service call...
			if (null != slaList && !slaList.isEmpty() && slaList.size() > 0) {
				model.addAttribute("slaList",
						PaginationClass.paginationLimitedRecords(page, slaList, maxRecord, totalSearchCount));
			} else {
				model.addAttribute("errorMessage", "No Records Found");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search slaList has failed:" + ex.getMessage());
			}
			LOGGER.info("Search slaList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-sla";

	}

	@RequestMapping(value = "/edit-sla", method = RequestMethod.GET)
	public String editSla(@RequestParam("slaId") long slaId, HttpServletRequest request, HttpSession session,
			Model model) throws Exception {

		try {
			SlaBO slaBO = new SlaBO();

			// AdminLoginBO adminLoginBo = new AdminLoginBO();
			AdminUserBO adminuserBO = new AdminUserBO();

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				adminuserBO.setCompanyId(companyId);
				slaBO.setCompanyId(companyId);

			}

			slaBO.setSlaId(slaId);

			slaBO = slaService.findById(slaBO);

			if (null != slaBO) {
				model.addAttribute("slaBO", slaBO);

			} else {
				model.addAttribute("slaBO", slaBO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Sla has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Sla has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-sla";

	}

	@RequestMapping(value = "/edit-sla", method = RequestMethod.POST)
	public String editSla(@ModelAttribute("slaBO") SlaBO slaBO, BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpSession session) throws FileNotFoundException, MySalesException {
		LOGGER.entry();

		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				slaBO.setCompanyId(companyId);
			}
			slaBO = slaService.update(slaBO);
			if (null != slaBO) {
				model.addAttribute("successMessage", "Update Successfully");
				model.addAttribute("slaBO", slaBO);
				return "redirect:/view-sla";
			} else {
				model.addAttribute("errorMessage", "Does Not Exists");
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update Sla has failed:" + ex.getMessage());
			}
			LOGGER.info("Update Sla has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-sla";

	}

	@RequestMapping(value = "/delete-sla", method = RequestMethod.GET)
	public String deleteSla(@RequestParam("slaId") long slaId, HttpServletRequest request, HttpSession session,
			Model model) throws Exception {

		LOGGER.entry();
		try {
			SlaBO slaBO = new SlaBO();
			slaBO.setSlaId(slaId);
			if (null != slaBO) {
				boolean value = slaService.delete(slaBO);
				if (value == true) {
					System.out.println("Sla deleted successfully");

					model.addAttribute("successMessage", "Sla Deleted Successfully ");

					return "redirect:/view-sla";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Sla has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Sla has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-Sla";

	}
	
	@RequestMapping(value = "/check_slaCode", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkSlaCode(@RequestParam String slaCode)throws MySalesException {
		LOGGER.entry();
		boolean slaCodeCheck = false;
		try {
			slaCodeCheck = slaService.checkSlaCodeCheck(slaCode);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("slaCodeCheck has failed:" + ex.getMessage());
			}
			LOGGER.info("slaCodeCheck has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return slaCodeCheck;
	}
	
}
