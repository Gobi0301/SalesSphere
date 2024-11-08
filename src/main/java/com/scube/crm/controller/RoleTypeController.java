package com.scube.crm.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.RoleTypeService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.utils.UserRoles;




@Controller
@Scope("session")
@SessionAttributes("/admin")
public class RoleTypeController extends ControllerUtils implements Serializable  {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(RoleTypeController.class);
	
	@Autowired

	private RoleTypeService roletypeService;

	@Autowired

	private MessageSource messageSource;

	List<RoleBO> rolelist=new ArrayList<>();

	@RequestMapping(value="/role-user-type",method = RequestMethod.GET)
	public String getRoleType(Model model,HttpServletRequest request,HttpServletResponse response) throws MySalesException {
		String role=request.getParameter("sucessmessage");
		if(null!=request.getParameter("sucessmessage")&&null!=role){
			model.addAttribute("sucessmessage", request.getParameter("sucessmessage"));
		}
		if (null != request.getParameter("errorMessage")) {
			model.addAttribute("errorMessage", request.getParameter("errorMessage"));
		}
		RoleBO roleBOobj=new RoleBO();
		model.addAttribute("roleobj",roleBOobj);
		//getViewRole(model,request,response);
		return "role-user-type";

	}

	@RequestMapping(value="/check_roleName",method =RequestMethod.POST )
	public String postRoleType(@Valid @ModelAttribute("roleobj") RoleBO roleBOobj,BindingResult result,HttpServletRequest request,Model model) throws MySalesException{
		LOGGER.entry();
		try {
		if(result.hasErrors()) {

			return "role-user-type"; 
		}
		if(roleBOobj.getRoleName().isEmpty()) {

			model.addAttribute("createerrormessage",messageSource.getMessage("Role-Creation-Error", null, null));

		}

		if(null!=roleBOobj.getRoleName()) {
		roleBOobj=roletypeService.getroletype(roleBOobj);
		}

		if(null!=roleBOobj) {

			model.addAttribute("successMessage","Create Role Name Successfully");
			//model.addAttribute("rolename",roleBOobj.getRoleName());
			return "redirect:/view-role";

		}
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role creation has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role creation  has failed:" + ne.getMessage() + ne);
		}
		return "redirect:/view-role";

	}

	/*
	 * @RequestMapping(value="/view-role",method = RequestMethod.GET) public String
	 * getViewRole(Model model,HttpServletRequest request,HttpServletResponse
	 * response)throws MySalesException { LOGGER.entry(); try {
	 * rolelist=roletypeService.viewrole();
	 * 
	 * String role=request.getParameter("successMessage");
	 * if(null!=request.getParameter("successMessage")&&null!=role){
	 * model.addAttribute("successMessage", request.getParameter("successMessage"));
	 * }
	 * 
	 * if(null!=rolelist && rolelist.size()>0 && !rolelist.isEmpty()) {
	 * model.addAttribute("listroleobj", rolelist); model.addAttribute("searchObj",
	 * new RoleBO());
	 * 
	 * 
	 * model.addAttribute("viewinfomessage", messageSource.getMessage("Role-View",
	 * null, null));
	 * 
	 * } }catch (final NullPointerException ne) { ne.printStackTrace(); if
	 * (LOGGER.isDebugEnabled()) { LOGGER.debug("Role view has failed:" +
	 * ne.getMessage() + ne); } LOGGER.info("Role view  has failed:" +
	 * ne.getMessage() + ne); } return "view-role";
	 * 
	 * }
	 */

	@RequestMapping(value="/view-role",method = RequestMethod.GET)
	public String getViewRole(Model model,HttpServletRequest request,HttpServletResponse response)throws MySalesException {
		LOGGER.entry();
		 RoleBO roleBO = new RoleBO();
		try {
			String paging=null;
			if(null!=request.getParameter("page")) {
				paging=request.getParameter("page");
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
				roleBO.setAdminLoginBO(adminLoginBO);
			}
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) { 
				roleBO.setCompanyId(companyId); 
				}
			
			if(null!=request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if(null!=request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			if(null!=request.getParameter("searchRoleName")) {
				String firstName = request.getParameter("searchRoleName");
				roleBO.setRoleName(firstName);
				model.addAttribute("searchRoleName", request.getParameter("searchRoleName"));
			}
			rolePagination(roleBO, paging, model, request);
			
			model.addAttribute("searchObj", roleBO);
			
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role view has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role view  has failed:" + ne.getMessage() + ne);
		}
		return "view-role";

	}
	@RequestMapping(value="/edit-role-type",method = RequestMethod.GET)
	public String geteditRole( @RequestParam("roleId")long id, Model model,HttpServletRequest request) {
		try {
			long loginid = getUserSecurity().getLoginId();
			if (0 == loginid) {
				return "redirect:/admin-sign-in";
			}

			if (null != request.getParameter("roleId")) {
				RoleBO bo = new RoleBO();

				String roleId = request.getParameter("roleId");
				long rolesId = Long.parseLong(roleId);
				bo.setRoleId(rolesId);

				bo = roletypeService.getRoleid(bo);

				if (null != bo) {
					model.addAttribute("editroleobj", bo);
				}

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editRole has failed:" + ex.getMessage());
			}
			LOGGER.info("editROle has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-role";
	}
	@RequestMapping(value="/edit-role-type",method = RequestMethod.POST)
	public String posteditrole(@ModelAttribute("editroleobj") RoleBO roleBOob,BindingResult result,HttpServletRequest request,HttpServletResponse response,Model model) throws MySalesException {
		LOGGER.entry();
		try {
		
		if(result.hasErrors()) {

			return "redirect:/view-role";
		}
		
		roleBOob=roletypeService.posteditrole(roleBOob);

		if(null!=roleBOob) {
			model.addAttribute("successMessage","Update Role Name Successfully" );
			System.out.println("message");
			RoleBO roleBOobj=new RoleBO();
			model.addAttribute("roleobj",roleBOobj);
			return "redirect:/view-role";
		}
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role edit post has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role edit post  has failed:" + ne.getMessage() + ne);
		}
		return "redirect:/view-role";

	}
	@RequestMapping(value="/delete-role-type",method =RequestMethod.GET )
	public String deleteroletype(Model model,@RequestParam("roleId")long id,HttpServletRequest request) throws MySalesException {
		LOGGER.entry();
		try {
			if (null != request.getParameter("roleId")) {
				String roleId = request.getParameter("roleId");
				long roleid = Long.parseLong(roleId);

				RoleBO bo = new RoleBO();
				bo.setRoleId(roleid);
				
				int status = roletypeService.deleteroletype(bo);
				if (status>0) {
					model.addAttribute("successMessage", "Role Name Deleted Successfully!!");
					return "redirect:/view-role";
				}

				else {
					model.addAttribute("infoMessagemessage", "deleted failed");

				}
			}
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role delete has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role delete has failed:" + ne.getMessage() + ne);
		}
		return "redirect:/view-role";
	}

	/*
	 * @RequestMapping(value="search-role",method=RequestMethod.POST) public String
	 * searchRoleName(@Valid Model model,@ModelAttribute("searchObj")RoleBO
	 * roleBO,BindingResult result,HttpServletRequest request,HttpServletResponse
	 * response)throws MySalesException { LOGGER.entry(); try { List<RoleBO>
	 * roleBOList=new ArrayList<>();
	 * 
	 * if(null!=roleBO.getRoleName()) {
	 * roleBOList=roletypeService.searchRoleName(roleBO); }
	 * 
	 * if(null!=roleBOList&&!roleBOList.isEmpty()&&0<roleBOList.size()) { RoleBO
	 * roleBOobj=new RoleBO(); model.addAttribute("roleobj",roleBOobj);
	 * model.addAttribute("listroleobj", roleBOList);
	 * model.addAttribute("searchObj",roleBO ); } else {
	 * model.addAttribute("infoMessage","No Record Found"); RoleBO roleBOobj=new
	 * RoleBO(); model.addAttribute("roleobj",roleBOobj); return "role-user-type"; }
	 * }catch (final NullPointerException ne) { ne.printStackTrace(); if
	 * (LOGGER.isDebugEnabled()) { LOGGER.debug("Role Search has failed:" +
	 * ne.getMessage() + ne); } LOGGER.info("Role search has failed:" +
	 * ne.getMessage() + ne); } return "role-user-type"; }
	 */
	
	@RequestMapping(value="search-role",method=RequestMethod.POST)
	public String searchRoleName(@Valid Model model,@ModelAttribute("searchObj")RoleBO roleBO,BindingResult result,HttpServletRequest request,HttpServletResponse response)throws MySalesException {
		LOGGER.entry();
		try {
			long companyId = getUserSecurity().getCompanyId(); // company based create condition
			long adminId=getUserSecurity().getLoginId();
			List<String> userType=getUserSecurity().getRole();
			AdminLoginBO adminLoginBO=new AdminLoginBO();
			//adminLoginBO.setUserType(userType);
			adminLoginBO.setId(adminId);
			adminLoginBO.setFirstName(getUserSecurity().getName());
			model.addAttribute("userType", adminLoginBO);
			
			RoleBO bo = new RoleBO();
			bo.setRoleName(roleBO.getRoleName());
			bo.setId(adminId);
			 if(null!=roleBO.getRoleName()&& !roleBO.getRoleName().isEmpty()) {
	                model.addAttribute("searchRoleName", roleBO.getRoleName()); 
	            }
			long countOfRole=0;
			long totalSearchCount=0;
			int page=1;
			int maxRecord=10;
			String paging = null;
			
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
				page = Integer.parseInt(paging);
			}
			if (0 < adminId && !userType.contains("ROLE_ADMIN")){
				bo.setCompanyId(companyId);
			}
			if(null!=bo) {
				countOfRole=roletypeService.countOfRoleBySearch(bo);
			}
			if(0<countOfRole) {
				totalSearchCount=countOfRole;
				model.addAttribute("totalSearchCount", totalSearchCount);
			}
			
			int startingValueOfRole=paginationPageValues(page, maxRecord);
			bo.setRecordIndex(startingValueOfRole);
			bo.setMaxRecord(maxRecord);
			
			List<RoleBO> roleBOList=new ArrayList<>();
			roleBOList=roletypeService.listOfRoleByPagination(bo);
			
			if(null!=roleBOList && !roleBOList.isEmpty() && 0<roleBOList.size()) {
				model.addAttribute("roleBOList", roleBOList);
				model.addAttribute("roleBOList", PaginationClass.paginationLimitedRecords(page, roleBOList, maxRecord, totalSearchCount));
			}else {
				model.addAttribute("errorMessage", "No Record Found!");
			}
			
			
		}catch (Exception ex) {
			ex.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role Search has failed:" + ex.getMessage());
			}
			LOGGER.info("Role search has failed:" + ex.getMessage());
		}
	return "view-role";
	}

	private void rolePagination(RoleBO roleBO,String paging,Model model,HttpServletRequest request){
		  
		long countOfRole=0;
		int page=1;
		int maxRecord=10;
		long totalCountOfRole=0;
		
		if(null!=paging) {
			page=Integer.parseInt(paging);
		}
		countOfRole=roletypeService.countOfRole(roleBO);
		if(0<countOfRole) {
			totalCountOfRole=countOfRole;
			model.addAttribute("totalCountOfRole", totalCountOfRole);
		}else {
			model.addAttribute("errorMessage", "No Record Found!");
		}
		int startingRecordfRole=paginationPageValues(page,maxRecord);
		roleBO.setRecordIndex(startingRecordfRole);
		roleBO.setMaxRecord(maxRecord);
		List<RoleBO> roleBOList=new ArrayList<>();
		roleBOList=roletypeService.listOfRoleByPagination(roleBO);
		
		if(null!=roleBOList&&!roleBOList.isEmpty()&&0<roleBOList.size()) {
			model.addAttribute("roleBOList", roleBOList);
			model.addAttribute("roleBOList", PaginationClass.paginationLimitedRecords(page, roleBOList, maxRecord, totalCountOfRole));
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

	@RequestMapping(value = "/check_roleName", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkRoleName(@RequestParam String roleName)throws Exception {
	
		boolean roleNameCheck = false;
		
		try {
			roleNameCheck = roletypeService.checkRoleName(roleName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleNameCheck;
	}
	
}
