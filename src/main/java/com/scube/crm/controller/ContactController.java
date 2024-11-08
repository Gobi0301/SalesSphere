package com.scube.crm.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.ContactBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.security.MySalesUser;
import com.scube.crm.service.AdminService;
import com.scube.crm.service.ContactService;
import com.scube.crm.service.OpportunityService;
import com.scube.crm.utils.PaginationClass;

@Controller
public class ContactController  extends ControllerUtils implements Serializable{



	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(ContactController.class);
	

	
	@Autowired
	OpportunityService opportunityService;
	
	
	@Autowired
	private ContactService contactService;
	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "/create-contact", method = RequestMethod.GET)
	public String createContacts(HttpServletRequest request, Model model) throws MySalesException {
		LOGGER.entry();
		List<String> userType = getUserSecurity().getRole();
		long loginId=getUserSecurity().getLoginId();
		try {
			  List<OpportunityBO> opportunityBOList=new ArrayList<>(); 
			  OpportunityBO opportunityBO=new OpportunityBO();
			  opportunityBOList=opportunityService.view(opportunityBO);
			  model.addAttribute("opportunitylist", opportunityBOList);
			 
			//account list

			Map<Integer,String> account= new HashMap<>();

			account=contactService.retrieveAccounttList();
			if(null!=account){
				model.addAttribute("accountlist", account);
			}

			//user list
			AdminUserBO adminuserBO =new AdminUserBO();
			if (0 < loginId && userType.contains("ROLE_ADMIN")) {
				List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}
			if (0 < loginId &&! userType.contains("ROLE_ADMIN")) {
				List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}

			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}

			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}

			model.addAttribute("contactBOs",new ContactBO());
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add contact has failed:" + ex.getMessage());
			}
			LOGGER.info("Add contact has failed:" + ex.getMessage());


		}
		finally{
			LOGGER.entry();
		}
		return "create-contacts";


	}


	@RequestMapping(value = "/create-contacts", method = RequestMethod.POST)
	public String usercreate(@Valid @ModelAttribute("contactBOs") ContactBO contactBO, BindingResult result,
			HttpServletRequest request, Model model) throws MySalesException {
		LOGGER.entry();
		try {

			if (result.hasErrors()) {
				return "create-contacts";
			}
			//account
			AccountBO accountBO=new AccountBO();
			if(null!=contactBO && null!=contactBO.getAccountBO() && null!=contactBO.getAccountBO().getAccountId()) {
				accountBO.setAccountId(contactBO.getAccountBO().getAccountId());
			}
			contactBO.setAccountBO(accountBO);
			//opportunity
			OpportunityBO cclientBO=new OpportunityBO();
			if(null!=contactBO && null!=contactBO.getOpportunityBO() && null!=contactBO.getOpportunityBO().getFirstName() 
					&& !contactBO.getOpportunityBO().getFirstName().isEmpty()) {
				String id=contactBO.getOpportunityBO().getFirstName();
				long ids=Long.parseLong(id);

				cclientBO.setOpportunityId(ids);
			}
			contactBO.setOpportunityBO(cclientBO);
			//user
			AdminUserBO adminUserBO=new AdminUserBO();
			if(null!=contactBO.getAssignedTo().getName()) {
				String adminId =contactBO.getAssignedTo().getName();
				long userId = 0;
				if (null != adminId) {
					userId = Long.parseLong(adminId);

					adminUserBO.setId(userId);
				}
			}
			contactBO.setAssignedTo(adminUserBO);

			contactBO.setDeleted(false);
			long count = contactService.createContact(contactBO);
			if(count>0) {
				model.addAttribute("successMessage", "Contact created successfully ");

			}
			model.addAttribute("functionType", "add");

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add contact has failed:" + ex.getMessage());
			}
			LOGGER.info("Add contact has failed:" + ex.getMessage());
			return "create-contacts";
		}
		finally{
			LOGGER.entry();
		}
		return "redirect:/view-contact";
	}


	@RequestMapping(value="/view-contact",method=RequestMethod.GET)
	public String viewContacts(Model model,HttpServletRequest request) {

		try {
			LOGGER.entry();
			String paging=null;


			if(null!=request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			if(null!=request.getParameter("page")){
				paging=request.getParameter("page");
			}
			ContactBO contactbO=new ContactBO();
			contactPagination(contactbO,paging,request, model);
			model.addAttribute("searchContacts",new ContactBO());

			 List<OpportunityBO> opportunityBOList=new ArrayList<>(); 
			  
			  OpportunityBO opportunityBO=new OpportunityBO();
			  opportunityBOList=opportunityService.view(opportunityBO);
			  model.addAttribute("opportunitylist", opportunityBOList);
			 
		}catch(Exception e) {
			e.printStackTrace();
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("view failed"+ e);
			}
			LOGGER.info("view Request has failed:" + e.getMessage());
		}
		finally {
			LOGGER.exit();
		}



		return "view-contacts";
	}
	private void contactPagination(ContactBO contact,String paging,HttpServletRequest request, Model model) throws MySalesException {
		HttpSession session=request.getSession();
		ContactBO ccontactBO=(ContactBO) session.getAttribute("contactbOLists");
		long count=0;
		long totalcampaignCount=0;
		int page=1;
		int maxRecord=10;
		if(null!=paging){
			page=Integer.parseInt(paging);
		}
		String num=	request.getParameter("number");


		if(null!=ccontactBO) {

			if(null!=num) {
				session.invalidate(); 
				//view pagination
				List<ContactBO> contactbOlist= new ArrayList<ContactBO>();
				count= contactService.contactCount(contact);
				if(0!=count){
					totalcampaignCount=count;
				}
				int startingRecordIndex = paginationPageValues(page, maxRecord);	
				contact.setRecordIndex(startingRecordIndex);
				contact.setMaxRecord(maxRecord);
				contact.setPagination("pagination");

				contactbOlist=contactService.retrieveContact(contact);



				if(null!=contactbOlist && !contactbOlist.isEmpty() && contactbOlist.size()>0){

					model.addAttribute("contactlist",PaginationClass.paginationLimitedRecords(page, contactbOlist, maxRecord, totalcampaignCount));
				}
				if(null!=contactbOlist && !contactbOlist.isEmpty() && contactbOlist.size()>0){
					model.addAttribute("successMessage", "successfully created contact");
				}
				else {
					model.addAttribute("errorMessage", "NO Records Found");
				}

			}
			else {
				//opportunity
				OpportunityBO cclientBO=new OpportunityBO();
				if(null!=ccontactBO.getOpportunityBO() && null!=ccontactBO.getOpportunityBO().getFirstName()&&!ccontactBO.getOpportunityBO().getFirstName().isEmpty()) {
					String id=ccontactBO.getOpportunityBO().getFirstName();
					long ids=Long.parseLong(id);

					cclientBO.setOpportunityId(ids);
				}
				ccontactBO.setOpportunityBO(cclientBO);
				count= contactService.contactssCount(ccontactBO);
				if(0!=count){
					totalcampaignCount=count;
				}

				int startingRecordIndex = paginationPageValues(page, maxRecord);	
				ccontactBO.setRecordIndex(startingRecordIndex);
				ccontactBO.setMaxRecord(maxRecord);
				ccontactBO.setPagination("pagination");
				List<ContactBO> contactbOList = new ArrayList<ContactBO>();

				contactbOList = contactService.searchContactsList(ccontactBO);

				if(null!=contactbOList && !contactbOList.isEmpty() && contactbOList.size()>0){
					model.addAttribute("contactlist",PaginationClass.paginationLimitedRecords(page, contactbOList, maxRecord, totalcampaignCount));
				}
			}
		}else {

			//view pagination
			List<ContactBO> contactbOlist= new ArrayList<ContactBO>();
			count= contactService.contactCount(contact);
			if(0!=count){
				totalcampaignCount=count;
			}
			int startingRecordIndex = paginationPageValues(page, maxRecord);	
			contact.setRecordIndex(startingRecordIndex);
			contact.setMaxRecord(maxRecord);
			contact.setPagination("pagination");

			contactbOlist=contactService.retrieveContact(contact);



			if(null!=contactbOlist && !contactbOlist.isEmpty() && contactbOlist.size()>0){

				model.addAttribute("contactlist",PaginationClass.paginationLimitedRecords(page, contactbOlist, maxRecord, totalcampaignCount));
			}
			if(null!=contactbOlist && !contactbOlist.isEmpty() && contactbOlist.size()>0){
				model.addAttribute("successMessage", "successfully created contact");
			}
			else {
				model.addAttribute("errorMessage", "NO Records Found");
			}
		}
	}


	private int paginationPageValues(int pageid, int recordPerPage) {
		int pageRecords = 0;
		if (pageid == 1) {
			pageRecords = 0;
		} else {
			pageRecords = (pageid - 1) * recordPerPage+1;
			pageRecords = pageRecords - 1;
		}
		return pageRecords; 

	}	

	@RequestMapping(value = "/search-contacts", method = RequestMethod.POST)
	public String searchContacts(@Valid @ModelAttribute("searchContacts") ContactBO contactbo, BindingResult result,HttpServletRequest request, Model model){

		HttpSession session=request.getSession();
		session.setAttribute("contactbOLists", contactbo);
		try {
			LOGGER.entry();
			long loginId = getUserSecurity().getLoginId();
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}
			String paging=null;


			if(null!=request.getParameter("page")){
				paging=request.getParameter("page");
			}
			long count=0;
			long totalcampaignCount=0;
			int page=1;
			int maxRecord=10;
			if(null!=paging){
				page=Integer.parseInt(paging);
			}

			//opportunity
			OpportunityBO cclientBO=new OpportunityBO();
			if(null!=contactbo.getOpportunityBO() && !contactbo.getOpportunityBO().getFirstName().isEmpty()) {
				String id=contactbo.getOpportunityBO().getFirstName();
				long ids=Long.parseLong(id);

				cclientBO.setOpportunityId(ids);
			}
			contactbo.setOpportunityBO(cclientBO);
			count= contactService.contactssCount(contactbo);
			if(0!=count){
				totalcampaignCount=count;
			}

			int startingRecordIndex = paginationPageValues(page, maxRecord);	
			contactbo.setRecordIndex(startingRecordIndex);
			contactbo.setMaxRecord(maxRecord);
			contactbo.setPagination("pagination");
			List<ContactBO> contactbOList = new ArrayList<ContactBO>();

			contactbOList = contactService.searchContactsList(contactbo);

			if(null!=contactbOList && !contactbOList.isEmpty() && contactbOList.size()>0){
				model.addAttribute("contactlist",PaginationClass.paginationLimitedRecords(page, contactbOList, maxRecord, totalcampaignCount));
			}
			if(null==contactbOList && contactbOList.size()==0){
				model.addAttribute("errorMessage", "NO Records Found");
			}
			
			 List<OpportunityBO> opportunityBOList=new ArrayList<>(); 
			  
			  OpportunityBO opportunityBO=new OpportunityBO();
			  opportunityBOList=opportunityService.view(opportunityBO);
			  model.addAttribute("opportunitylist", opportunityBOList);
			 
		}catch(Exception e) {
			e.printStackTrace();
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("view failed"+ e);
			}
			LOGGER.info("view Request has failed:" + e.getMessage());
		}
		finally {
			LOGGER.exit();
		}


		return "view-contacts";


	}
	@RequestMapping(value="/view-contact-details", method=RequestMethod.GET)
	public String viewcontactDODetails(Model model,HttpServletRequest request){
		ContactBO contact=new ContactBO();
		LOGGER.entry();
		try{


			String id=request.getParameter("id");
			Long contactId=Long.parseLong(id);



			contact=contactService.findById(contactId);
			if(0!= contact.getContactId()){	
				model.addAttribute("contactobj",contact);
			}
		}catch(Exception e){
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("view contactDO details: Exception \t"+e);
			}
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("view contactDO details: Exception \t"+e);
			}
		}
		finally{
			LOGGER.exit();
		}
		return "view-contact-details";
	}
	@RequestMapping(value="/edit-contact",method=RequestMethod.GET)
	public String editcontact(Model model, HttpServletRequest request)  {

		LOGGER.entry();
		ContactBO contact=new ContactBO();

		try {
			List<String> userType = getUserSecurity().getRole();
			long loginId=getUserSecurity().getLoginId();

			//account list
			Map<Integer,String> account= new HashMap<>();
			account=contactService.retrieveAccounttList();
			if(null!=account){
				model.addAttribute("accountlist", account);
			}

			 List<OpportunityBO> opportunityBOList=new ArrayList<>(); 
			  
			  OpportunityBO opportunityBO=new OpportunityBO();
			  opportunityBOList=opportunityService.view(opportunityBO);
			  model.addAttribute("opportunitylist", opportunityBOList);
			 
			//user
			  AdminUserBO adminuserBO= new AdminUserBO();
			if (0 < loginId && userType.contains("ROLE_ADMIN")) {

				List<AdminUserBO> userBOList = adminService.retrieveUser(adminuserBO);
				model.addAttribute("userBOList", userBOList);
			}

			if (0 < loginId &&! userType.contains("ROLE_ADMIN")) { List<AdminUserBO>
			userBOList = adminService.retrieveUser(adminuserBO); model.addAttribute("userBOList",	userBOList);
			}



			String id = request.getParameter("id");
			if (null != id) {
				Long conId = (long) Integer.parseInt(id);

				contact = contactService.retrievecontact(conId);

				model.addAttribute("contacto", contact);

			}
		}catch(Exception e){
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("edit contactDO details: Exception \t"+e);
			}
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("edit contactDO details: Exception \t"+e);
			}
		}
		finally{
			LOGGER.exit();
		}
		return "edit-contact";
	}

	@RequestMapping(value = "/edit-contact", method = RequestMethod.POST)
	public String updateRequest(@Valid @ModelAttribute("contacto") ContactBO contact, HttpServletRequest request,
			Model model, BindingResult result, HttpServletResponse response, HttpSession session)
	{
		LOGGER.entry();

		MySalesUser mySalesUser = getUserSecurity();
		if (null != mySalesUser && null != mySalesUser.getLoginId() && 0 == mySalesUser.getLoginId()) {
			return "redirect:/admin-sign-in";
		}
		long loginId = mySalesUser.getLoginId();

		try {
			if (result.hasErrors()) {
				return "edit-contact";
			}
			contact.setCreatedBy(loginId);
			contact.setModifiedBy(loginId);
			AccountBO accountBO=new AccountBO();
			if( 0<contact.getAccountBO().getAccountId()) {
				accountBO.setAccountId(contact.getAccountBO().getAccountId());
			}
			contact.setAccountBO(accountBO);
			//opportunity
			OpportunityBO cclientBO=new OpportunityBO();
			if(null!=contact.getOpportunityBO() && !contact.getOpportunityBO().getFirstName().isEmpty()) {
				String id=contact.getOpportunityBO().getFirstName();
				long ids=Long.parseLong(id);

				cclientBO.setOpportunityId(ids);
			}
			contact.setOpportunityBO(cclientBO);
			//user
			AdminUserBO adminUserBO=new AdminUserBO();
			if(0<contact.getAssignedTo().getId()) {
				long adminId =contact.getAssignedTo().getId();
				adminUserBO.setId(adminId);
			}
			contact.setAssignedTo(adminUserBO);

			boolean status = contactService.updateRequest(contact);

			if (status) {
				model.addAttribute("successMessage", "Contact has been updated Successfully");

			} else {
				model.addAttribute("errorMessages", "Faild to update Contact");
			}

		} catch (Exception e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("update Request has failed:" + e.getMessage());
			}
			LOGGER.info("update Request has failed:" + e.getMessage());
			model.addAttribute("errorMessages", "Faild to update contact");
		} finally {

			LOGGER.exit();
		}

		return "redirect:/view-contact";
	}

	@RequestMapping(value = "/delete-contact", method = RequestMethod.GET)
	public String contactDelete(Model model, HttpServletRequest request, HttpSession session) {

		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			if (0 == loginId) {
				return "redirect:/admin-sign-in";
			}

			boolean status=false;
			String id = request.getParameter("id");

			if (id != null) {
				Long ids = Long.parseLong(id);
				if(ids>0) {
					status=contactService.softDeleted(ids);
					model.addAttribute("successmessages", "deleted succesfully");
				}
			}
			if(!status) {
				model.addAttribute("infoMessagemessage", "deleted failed");
			}



		} catch (Exception e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("delete Request has failed:" + e.getMessage());
			}
			LOGGER.info("delete Request has failed:" + e.getMessage());
			model.addAttribute("errorMessages", "Faild to delete contact");
		} finally {

			LOGGER.exit();
		}
		return "redirect:/view-contact";
	}


}
