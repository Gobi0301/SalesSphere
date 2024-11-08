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
import org.springframework.web.bind.annotation.ResponseBody;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.PlotBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.PlotService;
import com.scube.crm.utils.PaginationClass;

@Controller
public class PlotController extends ControllerUtils {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(PlotController.class);
	@Autowired
	private MessageSource messageSource;
	@Autowired
	PlotService plotService;
	
	@RequestMapping(value = "/create-plot", method = RequestMethod.GET)
	public String createCompany(Model model, HttpServletRequest request, HttpServletResponse response)
			throws MySalesException {
		PlotBO plotBO = new PlotBO();
		if(0<getUserSecurity().getCompanyId()) { 
			long  companyId=getUserSecurity().getCompanyId(); //company based create condition
			plotBO.setCompanyId(companyId); 
		}
		model.addAttribute("Plot", plotBO);
		return "create-plot";
	}
	
	@RequestMapping(value = "/create-plot", method = RequestMethod.POST)
	public String createPlot(@ModelAttribute("Plot") PlotBO plotBO, Model model) throws IOException,MySalesException {
		LOGGER.entry();
		long id=getUserSecurity().getLoginId();
		plotBO.setCreatedBy(id);
		try {
		long loginId = getUserSecurity().getLoginId();
		List<String> userType = getUserSecurity().getRole();
		if (0 == loginId) {
			return "redirect:/create-plot";
		}
		if(0<getUserSecurity().getCompanyId()) { 
			long  companyId=getUserSecurity().getCompanyId(); //company based create condition
			plotBO.setCompanyId(companyId); 
		}
		      plotBO = plotService.createPlot(plotBO);
				if (null!=plotBO) {
					model.addAttribute("successMessage", messageSource.getMessage("plotnumber.Creation", null, null));
					return "redirect:/view-plot";
				}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create Plot has failed:" + ex.getMessage());
			}
			LOGGER.info("Create Plot has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-plot";
	}
	
	@RequestMapping(value = "view-plot", method = RequestMethod.GET)
	public String listPlot(Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId=getUserSecurity().getCompanyId();
		String paging=null;
		PlotBO plotBO = new PlotBO();
		if(null!=request.getParameter("successMessage")) {
			model.addAttribute("successMessage", request.getParameter("successMessage"));
		}
		if(null!=request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		if(null!=request.getParameter("searchElement")) {
			String plotNumbers = request.getParameter("searchElement");
			plotBO.setPlotNumbers(plotNumbers);
			model.addAttribute("searchElement", request.getParameter("searchElement"));
		}
		if (null != request.getParameter("page")) {
			paging = request.getParameter("page");
		}

		if (0 < loginId && !userType.contains("ROLE_ADMIN")) { 
			plotBO.setCompanyId(companyId);
		}
			plotPagination(plotBO, paging, request, model);
			model.addAttribute("searchPlot", new PlotBO());
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View plot has failed:" + ex.getMessage());
			}
			LOGGER.info("View plot has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}

		return "view-plot";

	}

	private void plotPagination(PlotBO plotBO, String paging, HttpServletRequest request, Model model)
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
			count = plotService.PlotCount(plotBO);
			if (0 != count) {
				totalCount = count;
				model.addAttribute("totalCount", totalCount);
			} else {
				model.addAttribute("errorMessage", "No Record Found!");
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			plotBO.setRecordIndex(startingRecordIndex);
			plotBO.setMaxRecord(maxRecord);
			plotBO.setPagination("pagination");
			List<PlotBO> plotLists = new ArrayList<>();
			plotLists = plotService.listPlot(plotBO);
			if (null != plotLists && !plotLists.isEmpty() && plotLists.size() > 0) {
				model.addAttribute("plotLists",
						PaginationClass.paginationLimitedRecords(page, plotLists, maxRecord, totalCount));
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("plot Pagination has failed:" + ex.getMessage());
			}
			LOGGER.info("plot Pagination has failed:" + ex.getMessage());
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

	@RequestMapping(value = "/search-plot", method = RequestMethod.POST)
	public String searchSupplier( @Valid @ModelAttribute("searchPlot") PlotBO plotBO,BindingResult result,
			HttpServletRequest request,HttpSession session, Model model) throws MySalesException,SerialException, SQLException {
		LOGGER.entry();
		
		try {
			if (result.hasErrors()) {
				return "view-plot";
			}
			List<String> userType = getUserSecurity().getRole();
			long loginId = getUserSecurity().getLoginId();
			long companyId = getUserSecurity().getCompanyId();
			
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				plotBO.setCompanyId(companyId);
			}
			List<PlotBO> plotLists=new ArrayList<>();

			long count = 0;
			long totalCount = 0;
			int page = 1;
			int maxRecord = 10;

			String paging = null;
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
				page = Integer.parseInt(paging);
			}
			if(null!=plotBO.getPlotNumbers()&& !plotBO.getPlotNumbers().isEmpty()) {
				model.addAttribute("searchElement", plotBO.getPlotNumbers());
			}
			count = plotService.PlotCount(plotBO);
			if (0 != count) {
				totalCount = count;
				model.addAttribute("count",count);
			}else {
				model.addAttribute("errorMessage", "No Record Found!");
				return "view-plot";
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);
			plotBO.setRecordIndex(startingRecordIndex);
			plotBO.setMaxRecord(maxRecord);
			plotBO.setPagination("pagination");

			plotLists = plotService.listPlot(plotBO); //search service call...
			if (null != plotLists && !plotLists.isEmpty() && plotLists.size() > 0) {
				model.addAttribute("plotLists",
						PaginationClass.paginationLimitedRecords(page, plotLists, maxRecord, totalCount));
			}
			else {
				model.addAttribute("errorMessage", "No Record Found!");
				return "view-plot";
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search plot has failed:" + ex.getMessage());
			}
			LOGGER.info("Search plot has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return "view-plot";
	}
	
	@RequestMapping(value =  "edit-plot" , method = RequestMethod.GET)
	public String editSupplier(@RequestParam("plotId") Long plotId, Model model) {
		LOGGER.entry();
		try {
			PlotBO plotBo = new PlotBO();
			//int id;
			plotBo.setPlotId(plotId);

			if (0 < plotId) {
				plotBo = plotService.selectPlot(plotBo);
			}
			
			if (null != plotBo) {
				model.addAttribute("plotBo", plotBo);
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit plot has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit plot has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return "edit-plot";
	}
	
	@RequestMapping(value = "edit-plot", method = RequestMethod.POST)
	public String editPlot(@Valid @ModelAttribute("plotBo") PlotBO plotBO, BindingResult bindingResult,
			Model model, HttpServletRequest request, HttpSession session) throws IOException {
		LOGGER.entry();
		try {
			if (0 < getUserSecurity().getCompanyId()) { // CompanyId
				long CompanyId = getUserSecurity().getCompanyId();
				plotBO.setCompanyId(CompanyId);
			}
			plotBO = plotService.PlotUpdate(plotBO);
			model.addAttribute("plotBo", plotBO);
			model.addAttribute("successMessage", messageSource.getMessage("plotnumber.Update", null, null));
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit-update plot has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit-update plot has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return "redirect:/view-plot";
	}

	@RequestMapping(value = "delete-plot", method = RequestMethod.GET)
	public String deleteSupplier(Model model, HttpServletRequest request, HttpSession session,@RequestParam("plotId") Long plotId)
			throws FileNotFoundException {
		LOGGER.entry();
		try {
			PlotBO plotBo=new PlotBO();
			plotBo.setPlotId(plotId);
			if(null!=plotBo) {
				boolean value =plotService.deletePlot(plotBo);
				if(value==true) {
					System.out.println("plot deleted successfully");
					model.addAttribute("successMessage", messageSource.getMessage("plotnumber.Delete", null, null));
						return  "redirect:/view-plot";  
				}else {
						model.addAttribute("errorMessage", "plot Doesn't Deleted");
				}
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete plot has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete plot has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return "redirect:/view-plot";
	}
	
	@RequestMapping(value = "/check_plotNumbers", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkPlotNumbers(@RequestParam String plotNumbers)throws MySalesException {
		LOGGER.entry();
		boolean plotNumberCheck = false;
		long companyId=getUserSecurity().getCompanyId();
		try {
			plotNumberCheck = plotService.checkPlotNumbers(plotNumbers,companyId);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkPlotNumbers has failed:" + ex.getMessage());
			}
			LOGGER.info("checkPlotNumbers has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return plotNumberCheck;
	}
}
