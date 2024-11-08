package com.scube.crm.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scube.crm.bo.AccessBo;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.AccessService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.utils.UserRoles;


@Controller
public class AccessController extends ControllerUtils implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(AccessController.class);

	@Autowired	
	private AccessService accessService;
	
	
	@Autowired	
	MessageSource messageSorce;
	
	List<AccessBo> getaccessbo=new ArrayList<AccessBo>();

	
	@RequestMapping(value="/create-access" ,method =RequestMethod.GET )
	
	public String accessValue(Model model,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws MySalesException{
		AccessBo accessBo=new AccessBo();		
		model.addAttribute("accessBo", accessBo);
		viewAccess(model,request,session);
		if(null!=request.getParameter("sucessMessage")){
			model.addAttribute("sucessMessage", request.getParameter("sucessMessage"));
		}
		if(null!=request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage",request.getParameter("errorMessage"));
		}
		return "create-access";		
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/create-access",method =RequestMethod.POST )
	public String createAccessValue(@Valid @ModelAttribute("accessBo") AccessBo accessBo,BindingResult result,HttpServletRequest request,HttpServletResponse response,Model model) throws MySalesException{
		LOGGER.entry();
		try {
		if(result.hasErrors()) {
			return "create-access";
			
		}
		long loginId = getUserSecurity().getLoginId();
		//String userType = getUserSecurity().getRole();
		if (0 == loginId) {
			return "redirect:/admin-sign-in";
		}
		accessBo =accessService.createSaveAccess(accessBo);
		if(0 <accessBo.getAccessId()) {
			model.addAttribute("successMessage",messageSorce.getMessage("Access-creation", null, null) );
			return "redirect:/view-access";		
		}	
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createAccessValue has failed:" + ex.getMessage());
			}
			LOGGER.info("createAccessValue has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-access";
		
	}
	
//	@PreAuthorize("hasRole('ADMIN')")
//	@RequestMapping(value = "/view-access",method = RequestMethod.GET)	
//	public String viewAccess(Model model,HttpServletRequest request,HttpSession session) throws MySalesException{
//		LOGGER.entry();
//		try {
//		getaccessbo=accessService.viewAccess();
//		if (null != getaccessbo) {
//			model.addAttribute("accessbolist", getaccessbo);
//			model.addAttribute("viewAccess",new AccessBo());
//		}
//		if(null!=request.getParameter("successMessage")) {
//			model.addAttribute("successMessage", request.getParameter("successMessage"));
//		}
//		if(null!=request.getParameter("errorMessage")) {
//			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
//		}	
//		}catch (Exception ex) {
//			if (LOGGER.isDebugEnabled()) {
//				LOGGER.debug("viewAccess has failed:" + ex.getMessage());
//			}
//			LOGGER.info("viewAccess has failed:" + ex.getMessage());
//		} finally {
//			LOGGER.exit();
//		}
//		return "view-access";
//		
//	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/view-access",method = RequestMethod.GET)	
	public String viewAccess(Model model,HttpServletRequest request,HttpSession session) throws MySalesException{
		LOGGER.entry();
		AccessBo accessBO = new AccessBo();
		try {
			String paging=null;
			if(null!=request.getParameter("page")) {
				paging=request.getParameter("page");
			}
			if(null!=request.getParameter("searchAccessName")) {
				String firstName = request.getParameter("searchAccessName");
				accessBO.setAccessName(firstName);
				model.addAttribute("searchAccessName", request.getParameter("searchAccessName"));
			}
			if(null!=request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if(null!=request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			long loginId=getUserSecurity().getLoginId();
			List<String> userType=getUserSecurity().getRole();
			long companyId=getUserSecurity().getCompanyId();
			
			AdminLoginBO adminLoginBO=new AdminLoginBO();
			adminLoginBO.setId(loginId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			
			model.addAttribute("userType", adminLoginBO);
			if (0 < loginId && userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				adminLoginBO.setId(loginId);
				accessBO.setAdminLoginBO(adminLoginBO);
			}
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) { 
				accessBO.setCompanyId(companyId); 
			}
			
			accessPagination(accessBO, paging, model, request);
			
			model.addAttribute("viewAccess", accessBO);
			
			
		
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view Access has failed:" + ex.getMessage());
			}
			LOGGER.info("view Access has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-access";
		
	}
	
	
	private void accessPagination(AccessBo accessBO, String paging, Model model, HttpServletRequest request) {
	
		long countOfAccess=0;
		int page=1;
		int maxRecord=10;
		long totalCountOfAccess=0;
		
		if(null!=paging) {
			page=Integer.parseInt(paging);
		}
		countOfAccess=accessService.countOfAccess(accessBO);
		if(0<countOfAccess) {
			totalCountOfAccess=countOfAccess;
			model.addAttribute("totalCountOfAccess", totalCountOfAccess);
		}else {
			model.addAttribute("errorMessage", "No Record Found!");
		}
		int startingRecordfRole=paginationPageValues(page,maxRecord);
		accessBO.setRecordIndex(startingRecordfRole);
		accessBO.setMaxRecord(maxRecord);
		List<AccessBo> accessBOList=new ArrayList<>();
		accessBOList=accessService.listOfAccessByPagination(accessBO);
		
		if(null!=accessBOList && !accessBOList.isEmpty() && 0<accessBOList.size()) {
			model.addAttribute("accessBOList", accessBOList);
			model.addAttribute("accessBOList", PaginationClass.paginationLimitedRecords(page, accessBOList, maxRecord, totalCountOfAccess));
		}else {
			model.addAttribute("errorMessage", "No Record Found!");
		}
	
	}


	private int paginationPageValues(int page, int maxRecord) {
		
		int recordsOfPage=0;
		if(page==1) {
			recordsOfPage=0;
		}
		else {
			recordsOfPage=(page-1)*maxRecord+1;
			recordsOfPage=recordsOfPage-1;
		}
		return recordsOfPage;
	}


	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/edit-access",method =RequestMethod.GET )
	public String editAccess(@RequestParam("accessId")long accessId,Model model,HttpServletRequest request)throws MySalesException {
		LOGGER.entry();
		try {
			long loginid = getUserSecurity().getLoginId();
		    if (0 == loginid) {
			   return "redirect:/admin-sign-in";
		     }

		    if (null != request.getParameter("accessId")) {
			AccessBo bo = new AccessBo();

			String accessid = request.getParameter("accessId");
			long accessIds = Long.parseLong(accessid);
			bo.setAccessId(accessIds);

			bo = accessService.getAccessId(bo);

			if (null != bo) {
				model.addAttribute("editAccessBo", bo);
			}

		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("editAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-access";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/edit-access",method =RequestMethod.POST )
	public String editUpdateValue(@ModelAttribute("editAccessBo")AccessBo accessBo,BindingResult result,HttpServletRequest request,HttpServletResponse response,Model model)throws MySalesException {
		LOGGER.entry();
		try {
		if(result.hasErrors()) {
			return "edit-access";
		}
		
		accessBo=accessService.updateAccessValue(accessBo);
		if(0 <accessBo.getAccessId() ) {
			model.addAttribute("successMessage","Access Name Update Successfully");

			return "redirect:/view-access";		
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editUpdateValue has failed:" + ex.getMessage());
			}
			LOGGER.info("editUpdateValue has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-access";		
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/delete-access",method = RequestMethod.GET)
	public String deleteAccessValue(Model model,@RequestParam("accessId")long accessId,HttpServletRequest request) throws MySalesException{
		LOGGER.entry();
		try {
			if (null != request.getParameter("accessId")) {
				String accessid = request.getParameter("accessId");
				long accessIds = Long.parseLong(accessid);

				AccessBo bo = new AccessBo();
				bo.setAccessId(accessIds);
				
				
				int status = accessService.deleteAccess(bo);
				if (status>0) {
					model.addAttribute("successMessage", "Access Name Deleted Successfully!!");
					return "redirect:/view-access";
				}

				else {
					model.addAttribute("infoMessagemessage", "deleted failed");

				}
			}	
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Access delete has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Access delete has failed:" + ne.getMessage() + ne);
		}
		return "redirect:/view-access";
		
	}
	
	
//	@PreAuthorize("hasRole('ADMIN')")
//	@RequestMapping(value="/search-access" ,method = RequestMethod.POST)
//	public String searchAccess(@ModelAttribute("viewAccess")AccessBo accessBos,Model model,HttpServletRequest request)throws MySalesException {
//		LOGGER.entry();
//		try {
//		AccessBo accessbo=new AccessBo();
//		accessbo.setAccessName(accessBos.getAccessName());
//		
//		List<AccessBo> getaccessbo=new ArrayList<AccessBo>();
//		getaccessbo=accessService.getAccessList(accessbo);
//		if(null !=getaccessbo && 0< getaccessbo.size()) {
//			AccessBo accessBo=new AccessBo();		
//			model.addAttribute("accessBo", accessBo);
//			model.addAttribute("accessbolist", getaccessbo);
//			model.addAttribute("viewAccess", accessBo);
//			return "create-access";		
//			
//		}
//		else {
//			model.addAttribute("errorMessage", "no record found");
//			AccessBo accessBo=new AccessBo();		
//			model.addAttribute("accessBo", accessBo);
//			return "create-access";		
//		}
//		}catch (Exception ex) {
//			if (LOGGER.isDebugEnabled()) {
//				LOGGER.debug("searchAccess has failed:" + ex.getMessage());
//			}
//			LOGGER.info("searchAccess has failed:" + ex.getMessage());
//		} finally {
//			LOGGER.exit();
//		}
//		return null;
//	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/search-access" ,method = RequestMethod.POST)
	public String searchAccess(@ModelAttribute("viewAccess")AccessBo accessBos,Model model,HttpServletRequest request)throws MySalesException {
		LOGGER.entry();
		try {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			long adminId=getUserSecurity().getLoginId();
			List<String> userType=getUserSecurity().getRole();
			
			AdminLoginBO adminLoginBO=new AdminLoginBO();
			adminLoginBO.setId(adminId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			
			AccessBo bo = new AccessBo();
			bo.setAccessName(accessBos.getAccessName());
			bo.setAccessId(adminId);
			
			long countOfAccess=0;
			long totalSearchCount=0;
			int page=1;
			int maxRecord=10;
			String paging = null;
			 if(null!=accessBos.getAccessName()&& !accessBos.getAccessName().isEmpty()) {
	                model.addAttribute("searchAccessName", accessBos.getAccessName()); 
	            }
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
				page = Integer.parseInt(paging);
			}
			if (0 < adminId && !userType.contains("ROLE_ADMIN")){
				bo.setCompanyId(companyId);
			}
			if(null!=bo) {
				countOfAccess=accessService.countOfAccessBySearch(bo);
			}
			if(0<countOfAccess) {
				totalSearchCount=countOfAccess;
				model.addAttribute("totalSearchCount", totalSearchCount);
			}
			
			int startingValueOfAccess=paginationPageValues(page, maxRecord);
			bo.setRecordIndex(startingValueOfAccess);
			bo.setMaxRecord(maxRecord);
			
			List<AccessBo> accessBOList = new ArrayList<>();
			accessBOList=accessService.listOfAccessByPagination(bo);
			
			if(null!=accessBOList && !accessBOList.isEmpty() && 0<accessBOList.size()) {
				model.addAttribute("accessBOList", accessBOList);
				model.addAttribute("accessBOList", PaginationClass.paginationLimitedRecords(page, accessBOList, maxRecord, totalSearchCount));
			}else {
				model.addAttribute("errorMessage", "No Record Found!");
			}
			
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("searchAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-access";
	}
	
	
	@RequestMapping(value = "/check_accessName", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkAccessName(@RequestParam String accessName)throws Exception {
		LOGGER.entry();
		boolean accessNameCheck = false;
		try {
			accessNameCheck = accessService.checkAccessName(accessName);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkAccessName has failed:" + ex.getMessage());
			}
			LOGGER.info("checkAccessName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return accessNameCheck;
	}

}
