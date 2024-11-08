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
import org.springframework.http.HttpRequest;
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
import com.scube.crm.bo.WorkItemBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.WorkItemService;
import com.scube.crm.utils.PaginationClass;
@Controller
public class WorkItemController extends ControllerUtils {
	 private static final MySalesLogger LOGGER = MySalesLogger.getLogger(WorkItemController.class);
	 
	 @Autowired
	 WorkItemService workItemService;
	
	@RequestMapping (value = "/create-workitem", method = RequestMethod.GET)
	public String CreateWorkItem (Model model,HttpServletRequest request, HttpServletResponse response) 
			throws MySalesException {
		
		WorkItemBO workItemBO = new WorkItemBO();
		model.addAttribute("workItemBO", workItemBO);
		
		return "create-workitem";
	}
	
	@RequestMapping(value = "/create-workitem", method = RequestMethod.POST)
    public String CreateWorkItem(@ModelAttribute("workItemBO") WorkItemBO workItemBO, Model model)
            throws IOException, MySalesException {
        LOGGER.entry();
        long id=getUserSecurity().getLoginId();
        workItemBO.setCreatedBy(id);
      
        if(0<getUserSecurity().getCompanyId()) {
			long companyId = getUserSecurity().getCompanyId();   // Company create
			workItemBO.setCompanyId(companyId);
		}
        try {

        	workItemBO = workItemService.save(workItemBO);
            if (null != workItemBO) {
                model.addAttribute("successMessage", "workItem Created Successfully");
                model.addAttribute("workItemBO", workItemBO);
                return "redirect:/list-workitems";

            } else {
                model.addAttribute("Error Message", "workItem is not created");
            }
        } catch (Exception ex) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Create workItem has failed:" + ex.getMessage());
            }
            LOGGER.info("Create workItem has failed:" + ex.getMessage());
        } finally {
            LOGGER.exit();
        }

        return "redirect:/list-workitems";
       
    }
	
	 @RequestMapping(value = "/list-workitems", method = RequestMethod.GET)
	    public String viewSla(HttpServletRequest request, HttpSession session, Model model) throws Exception {
	        LOGGER.entry();
	        long companyId = getUserSecurity().getCompanyId();
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
	        String paging = null;
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
	        if(null!=request.getParameter("searchElement")) {
				String workItemCode = request.getParameter("searchElement");
				workItemBO.setWorkItemCode(workItemCode);
				model.addAttribute("searchElement", request.getParameter("searchElement"));
			}
	        AdminLoginBO adminLoginBo = new AdminLoginBO();
	        AdminUserBO adminuserBO=new AdminUserBO();
	       
	        if (0 < loginId && !userType.contains("ROLE_ADMIN"))  {
	            adminuserBO.setCompanyId(companyId);
	            workItemBO.setCompanyId(companyId);
	           
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
	        model.addAttribute("workItemBO", workItemBO);
	        WorkItemPagination(workItemBO, paging, model, session, request);
	        model.addAttribute("searchWorkItem", workItemBO);
	        return "list-workitems";
	    }

	    private void WorkItemPagination(WorkItemBO workItemBO, String paging, Model model, HttpSession session,
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
	            count = workItemService.retrieveCount(workItemBO);
	            if (0 != count) {
	                totalCount = count;
	                model.addAttribute("totalCount",totalCount );
	            } else {
	                model.addAttribute("errorMessage", "Record not Found!");
	            }
	            int startingRecordIndex = paginationPageValues(page, maxRecord);
	            workItemBO.setRecordIndex(startingRecordIndex);
	            workItemBO.setMaxRecord(maxRecord);
	            workItemBO.setPagination("pagination");
	            List<WorkItemBO> slaList = workItemService.findAll(workItemBO);
	            if (null != slaList && !slaList.isEmpty() && slaList.size() > 0) {
	                model.addAttribute("workItemsList",
	                        PaginationClass.paginationLimitedRecords(page, slaList, maxRecord, totalCount));
	            }
	        } catch (Exception ex) {
	            if (LOGGER.isDebugEnabled()) {
	                LOGGER.debug("workItem Pagination has failed:" + ex.getMessage());
	            }
	            LOGGER.info("workItem Pagination has failed:" + ex.getMessage());
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
	    
	    @RequestMapping(value = "/search-workitem", method = RequestMethod.POST)
	    public String searchTask(@ModelAttribute("searchWorkItem") WorkItemBO workItemBO, HttpServletRequest request,
	            HttpSession session, Model model) throws MySalesException, SerialException, SQLException {
	        LOGGER.entry();
	        try {
	        	long companyId = getUserSecurity().getCompanyId();
				long loginId = getUserSecurity().getLoginId();
				List<String> userType = getUserSecurity().getRole();
				
	            long count = 0;
	            long totalCount = 0;
	            int page = 1;
	            int maxRecord = 10;

	            if (null!= workItemBO.getWorkItemCode()) {
	                model.addAttribute("workItemCode", workItemBO.getWorkItemCode());
	            }
	            if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
		            workItemBO.setCompanyId(companyId);
		        }
	            if(null!=workItemBO.getWorkItemCode() && !workItemBO.getWorkItemCode().isEmpty()) {
					model.addAttribute("searchElement", workItemBO.getWorkItemCode());
				}
	            count =workItemService.retrieveCount(workItemBO);
	            if (0 != count) {
	                totalCount = count;
	                model.addAttribute("count", count);
	            }

	            int startingRecordIndex = paginationPageValues(page, maxRecord);
	            workItemBO.setRecordIndex(startingRecordIndex);
	            workItemBO.setMaxRecord(maxRecord);
	            workItemBO.setPagination("pagination");

	            List<WorkItemBO> workItemsList = workItemService.findAll(workItemBO); // search service call...
	            if (null != workItemsList && !workItemsList.isEmpty() && workItemsList.size() > 0) {
	                model.addAttribute("workItemsList",
	                        PaginationClass.paginationLimitedRecords(page, workItemsList, maxRecord, totalCount));
	            } else {
	                model.addAttribute("errorMessage", "No Records Found");
	            }
	        } catch (Exception ex) {
	            if (LOGGER.isDebugEnabled()) {
	                LOGGER.debug("Search workItemsList has failed:" + ex.getMessage());
	            }
	            LOGGER.info("Search workItemsList has failed:" + ex.getMessage());
	        } finally {
	            LOGGER.exit();
	        }
	        return "list-workitems";

	    }
	    @RequestMapping(value = "/edit-workitems", method = RequestMethod.GET)
	    public String editTask(@RequestParam("workItemId") long workItemId, HttpServletRequest request, HttpSession session,
	            Model model) throws Exception {

	        try {
	        	WorkItemBO workItemBO = new WorkItemBO();
	           
	            //AdminLoginBO adminLoginBo = new AdminLoginBO();
	            AdminUserBO adminuserBO=new AdminUserBO();
	           
	            if (0 < getUserSecurity().getCompanyId()) {
	                long companyId = getUserSecurity().getCompanyId(); // company based create condition
	                adminuserBO.setCompanyId(companyId);
	                workItemBO.setCompanyId(companyId);
	               
	            }
	           
	            workItemBO.setWorkItemId(workItemId);

	            workItemBO = workItemService.findById(workItemBO);

	            if (null != workItemBO) {
	                model.addAttribute("workItemBO", workItemBO);

	            } else {
	                model.addAttribute("workItemBO", workItemBO);
	            }
	        } catch (Exception ex) {
	            if (LOGGER.isDebugEnabled()) {
	                LOGGER.debug("Edit Task has failed:" + ex.getMessage());
	            }
	            LOGGER.info("Edit Task has failed:" + ex.getMessage());
	        } finally {
	            LOGGER.exit();
	        }
	        return "edit-workitems";

	    }
	    @RequestMapping(value = "/edit-workitems", method = RequestMethod.POST)
	    public String editSla(@ModelAttribute("workItemBO") WorkItemBO workItemBO, BindingResult bindingResult,
	            Model model, HttpServletRequest request, HttpSession session)
	            throws FileNotFoundException, MySalesException {
	        LOGGER.entry();

	        try {
	        	
	            if (0 < getUserSecurity().getCompanyId()) {
		            long companyId = getUserSecurity().getCompanyId(); // company based create condition
		            workItemBO.setCompanyId(companyId);
		           
		        }

	        	workItemBO = workItemService.update(workItemBO);
	            if (null != workItemBO) {
	                model.addAttribute("successMessage", "Update Successfully");
	                model.addAttribute("workItemBO", workItemBO);
	                return "redirect:/list-workitems";
	            } else {
	                model.addAttribute("errorMessage", "Does Not Exists");
	            }

	        } catch (Exception ex) {
	            if (LOGGER.isDebugEnabled()) {
	                LOGGER.debug("Update workItem has failed:" + ex.getMessage());
	            }
	            LOGGER.info("Update workItem has failed:" + ex.getMessage());
	        } finally {
	            LOGGER.exit();
	        }
	        return "redirect:/list-workItems";

	    }
	    @RequestMapping(value = "/delete-workItem", method = RequestMethod.GET)
	    public String deleteProject(@RequestParam("workItemId") long workItemId, HttpServletRequest request, HttpSession session,
	            Model model) throws Exception {

	        LOGGER.entry();
	        try {
	        	WorkItemBO workItemBO = new WorkItemBO();
	        	workItemBO.setWorkItemId(workItemId);
	            if (null != workItemBO) {
	                boolean value = workItemService.delete(workItemBO);
	                if (value == true) {
	                    System.out.println("WorkItem deleted successfully");

	                    model.addAttribute("successMessage", "WorkItem Deleted Successfully ");

	                    return "redirect:/list-workitems";
	                }
	            }
	        } catch (Exception ex) {
	            if (LOGGER.isDebugEnabled()) {
	                LOGGER.debug("Delete WorkItem has failed:" + ex.getMessage());
	            }
	            LOGGER.info("Delete WorkItem has failed:" + ex.getMessage());
	        } finally {
	            LOGGER.exit();
	        }
	        return "redirect:/list-workitems";

	    }
	    @RequestMapping(value = "/check_workitem", method = RequestMethod.GET)
		@ResponseBody
		public boolean checkWorkitemCode(@RequestParam String workItemCode)throws Exception {
			LOGGER.entry();
			boolean workitemCodeCheck = false;
			long id=getUserSecurity().getLoginId();
			try {
				workitemCodeCheck = workItemService.checkWorkitemCode(workItemCode,id);
			} catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("checkWorkitemCode has failed:" + ex.getMessage());
				}
				LOGGER.info("checkWorkitemCode has failed:" + ex.getMessage());
			} finally {
				LOGGER.exit();
			}
			return workitemCodeCheck;
		}
	} 

