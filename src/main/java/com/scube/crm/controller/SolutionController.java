package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.SolutionBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.AdminService;
import com.scube.crm.service.ProductService;
import com.scube.crm.service.SolutionService;
import com.scube.crm.utils.PaginationClass;

@Controller
public class SolutionController  extends ControllerUtils {
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(SolutionController.class);

	@Autowired
	private SolutionService solutionService;
	@Autowired
	private ProductService productService;
	@Autowired
	private AdminService adminService;

	@Autowired
	private MessageSource message;

	@RequestMapping(value = "create_solution", method = RequestMethod.GET)
	public String createSolution(Model model) throws FileNotFoundException, MySalesException {
		LOGGER.entry();
		model.addAttribute("solutionBO", new SolutionBO());
		try {

		List<InventoryBO> productBOList = new ArrayList<>();
		InventoryBO inventoryBo = new InventoryBO();
		 if(0<getUserSecurity().getCompanyId()) { 
			  long  companyId=getUserSecurity().getCompanyId(); //company based create condition
			  inventoryBo.setCompanyId(companyId);  
		 }
            productBOList = productService.listOfProductName(inventoryBo);
        model.addAttribute("productList", productBOList);
		AdminUserBO adminuserBO=new AdminUserBO();
		List<String> userType = getUserSecurity().getRole();
		if(0<getUserSecurity().getCompanyId() && !userType.contains("ROLE_ADMIN") ) {  //list issue in soluion owner
		  long  companyId=getUserSecurity().getCompanyId(); //company based create condition
	    adminuserBO.setCompanyId(companyId);
	}
		List<AdminUserBO> userBOList = new ArrayList<>();
		userBOList = adminService.retrieveUser(adminuserBO);


			if (null != userBOList && 0 != userBOList.size()) {
				model.addAttribute("userBOList", userBOList);
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create Solution has failed:" + ex.getMessage());
			}
			LOGGER.info("Create Solution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "create_solution";
	}

	@RequestMapping(value = "create_solution", method = RequestMethod.POST)
	public String createSolution(@Valid @ModelAttribute("solutionBO") SolutionBO solution, BindingResult result,
			HttpServletRequest request, Model model) throws MySalesException {
		LOGGER.entry();
		try {

			if (result.hasErrors()) {

				InventoryBO inventoryBO = new InventoryBO();
				if (0 < getUserSecurity().getCompanyId()) {
					long companyId = getUserSecurity().getCompanyId(); // company based create condition
					inventoryBO.setCompanyId(companyId);
				}
				List<InventoryBO> productBOList = new ArrayList<>();
				productBOList = productService.listOfProductName(inventoryBO);
				model.addAttribute("productList", productBOList);

				AdminUserBO adminuserBO = new AdminUserBO();
				if (0 < getUserSecurity().getCompanyId()) {
					long companyId = getUserSecurity().getCompanyId();
					adminuserBO.setCompanyId(companyId);
				}
				List<AdminUserBO> userBOList = new ArrayList<>();
				userBOList = adminService.retrieveUser(adminuserBO);

				if (null != userBOList && 0 != userBOList.size()) {
					model.addAttribute("userBOList", userBOList);
				}
				return "create_solution";
			}
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId();
				solution.setCompanyId(companyId);
			}

			solution = solutionService.createSolution(solution);
			if (null != solution) {
				model.addAttribute("successMessage", message.getMessage("Solution.Create", null, null));

				return "redirect:/view_solution";
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create Solution has failed:" + ex.getMessage());
			}
			LOGGER.info("Create Solution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view_solution";

	}

	@RequestMapping(value = "view_solution", method = RequestMethod.GET)
	public String listOfSolution(Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		String paging = null;
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
			SolutionBO bo = new SolutionBO();
			if (0 < loginId && !userType.contains("ROLE_ADMIN"))  {
				bo.setCompanyId(companyId);
			}
			solutionPagination(bo, paging, request, model);
			model.addAttribute("solution", new SolutionBO());
		
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View Solution has failed:" + ex.getMessage());
			}
			LOGGER.info("View Solution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "view_solution";
		}
	private void solutionPagination(SolutionBO bo, String paging, HttpServletRequest request, Model model)
			throws IOException, MySalesException {

		// BaseBO baseBO=new BaseBO();
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
		count = solutionService.Solcount(bo);
		if (0 != count) {
			totalCount = count;
			model.addAttribute("totalCount",totalCount);
		} else {
			model.addAttribute("errorMessage", "Record not Found!");
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		bo.setRecordIndex(startingRecordIndex);
		bo.setMaxRecord(maxRecord);
		bo.setPagination("pagination");
		List<SolutionBO> solutionBOList = new ArrayList<>();
		solutionBOList = solutionService.listOfSolution(bo);
		if (null != solutionBOList && !solutionBOList.isEmpty() && solutionBOList.size() > 0) {
			model.addAttribute("solutionBOList",
					PaginationClass.paginationLimitedRecords(page, solutionBOList, maxRecord, totalCount));
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
	
	@RequestMapping(value = "search_solution", method = RequestMethod.POST)
	public String searchSolution(@ModelAttribute("solution") SolutionBO bo, HttpServletRequest request, Model model)
			throws MySalesException {
		LOGGER.entry();
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		long companyId=getUserSecurity().getCompanyId();
		List<SolutionBO> boList = new ArrayList<SolutionBO>();
       try { 		
       long count = 0;
		long totalSearchCount = 0;
		int page = 1;
		int maxRecord = 10;

		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
			bo.setCompanyId(companyId);
			}

		String paging = null;
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
			page = Integer.parseInt(paging);
		}

		count = solutionService.Solcount(bo);
		if (0 != count) {
			totalSearchCount = count;
			model.addAttribute("totalSearchCount", totalSearchCount);
		}

		else {
			model.addAttribute("errorMessage", "No Records Found");
			return "view_solution";
		}
		int startingRecordIndex = paginationPageValues(page, maxRecord);
		bo.setRecordIndex(startingRecordIndex);
		bo.setMaxRecord(maxRecord);
		bo.setPagination("pagination");

		boList = solutionService.listOfSolution(bo);
		
		if (null != boList && !boList.isEmpty() && boList.size() > 0) {
			model.addAttribute("solutionBOList",
					PaginationClass.paginationLimitedRecords(page, boList, maxRecord, totalSearchCount));
		} else {
			model.addAttribute("errorMessage", "No Records Found");
		}
       }catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("search solution has failed:" + ex.getMessage());
			}
			LOGGER.info("search solution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		
		return "view_solution";
	}
	
	@RequestMapping(value = "edit_solution", method = RequestMethod.GET)
	public String editSolution(@RequestParam("solutionId") int solutionId, Model model) throws MySalesException {
		LOGGER.entry();
		try {
		SolutionBO solution = new SolutionBO();
		AdminUserBO adminuserBO=new AdminUserBO();
		List<AdminUserBO> userBOList = new ArrayList<>();
		solution.setSolutionId(solutionId);
		
		if (0 < solutionId) {
			solution = solutionService.editSolution(solution);
		}
		if (null != solution) {
			InventoryBO inventoryBO = new InventoryBO();
			List<String> userType = getUserSecurity().getRole();
			 if(0<getUserSecurity().getCompanyId() && !userType.contains("ROLE_ADMIN")) { 
				  long  companyId=getUserSecurity().getCompanyId(); //company based  condition
				  inventoryBO.setCompanyId(companyId); 
				  adminuserBO.setCompanyId(companyId);
			 }

			List<InventoryBO> productBOList = new ArrayList<>();
			productBOList = productService.listOfProductName(inventoryBO);
			if (null != productBOList && 0 != productBOList.size()) {
				model.addAttribute("productList", productBOList);
			}
			
			userBOList = adminService.retrieveUser(adminuserBO);

			if (null != userBOList && 0 != userBOList.size()) {
				model.addAttribute("userBOList", userBOList);
			}

			model.addAttribute("solutionbo", solution);
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Solution has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Solution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit_solution";

	}

	@RequestMapping(value = "edit_solution", method = RequestMethod.POST)
	public String editSolution(@Valid @ModelAttribute("solutionbo") SolutionBO sbo, BindingResult result, Model model,
			HttpServletRequest request, HttpSession session) throws IOException {
		LOGGER.entry();
		try {
			if (result.hasErrors()) {
				InventoryBO inventoryBO = new InventoryBO();
				 if(0<getUserSecurity().getCompanyId()) { 
					  long  companyId=getUserSecurity().getCompanyId(); //company based  condition
					  inventoryBO.setCompanyId(companyId); 
				 }

				List<InventoryBO> productBOList = new ArrayList<>();
				productBOList = productService.listOfProductName(inventoryBO);
					model.addAttribute("productList", productBOList);
					
				AdminUserBO adminuserBo = new AdminUserBO();
				if (0 < getUserSecurity().getCompanyId()) {
					long companyId = getUserSecurity().getCompanyId();
					adminuserBo.setCompanyId(companyId);
					
					
				}
				List<AdminUserBO>userBoList = new ArrayList<>(); 			
				userBoList = adminService.retrieveUser(adminuserBo);

				if (null != userBoList && 0 != userBoList.size()) {
					model.addAttribute("userBOList", userBoList);
				}	
						return "edit_solution";
			}

			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				sbo.setCompanyId(companyId);
			}
			sbo = solutionService.updateSolution(sbo);
			model.addAttribute("solutionbo", sbo);
			model.addAttribute("successMessage", message.getMessage("Solution.Update", null, null));
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Solution has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Solution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return "redirect:/view_solution";
		
	}

	@RequestMapping(value = "delete_solution", method = RequestMethod.GET)
	public String deleteSolution(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam("solutionId") Integer solutionId) throws FileNotFoundException {
		LOGGER.entry();
		SolutionBO solutionBo = new SolutionBO();
		try {
			if (0 < getUserSecurity().getCompanyId()) {
				long companyId = getUserSecurity().getCompanyId(); // company based create condition
				solutionBo.setCompanyId(companyId);
			}
			solutionBo.setSolutionId(solutionId);

			if (null != solutionBo) {
				boolean status = solutionService.deleteSolution(solutionBo);
				if (status = true) {

					model.addAttribute("successMessage", message.getMessage("Solution.Delete", null, null));

				}
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Solution has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Solution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view_solution";
	}

	@RequestMapping(value = "solution_tracking_status", method = RequestMethod.GET)
	public String viewSolution(Model model, HttpServletRequest request, HttpSession session) {
		LOGGER.entry();
		try {
			int solutionId = 0;
			SolutionBO solutionBo = null;
			AdminUserBO userBo = new AdminUserBO();

			if (null != request.getParameter("solutionId")) {
				String id = request.getParameter("solutionId");
				solutionId = Integer.valueOf(id);
			}

			solutionBo = solutionService.viewSoltion(solutionId);
			if (null != solutionBo) {
				model.addAttribute("viewsolution", solutionBo);

			} else {
				model.addAttribute("viewsolution", new SolutionBO());
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("solution tracking status has failed:" + ex.getMessage());
			}
			LOGGER.info("solution tracking status has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		
		return "solution_tracking_status";
	}

}
