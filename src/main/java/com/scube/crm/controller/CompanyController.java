package com.scube.crm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.CompanyBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.service.CompanyService;
import com.scube.crm.service.ProductService;
import com.scube.crm.utils.PaginationClass;
import com.scube.crm.utils.UserRoles;

@Controller
public class CompanyController extends ControllerUtils {
	public static final String companyLogoUploads = "C:\\usr\\local\\mysales\\companylogo\\";
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(CompanyController.class);
	@Autowired
	CompanyService companyService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/create-company", method = RequestMethod.GET)
	public String createCompany(Model model, HttpServletRequest request, HttpServletResponse response)
			throws MySalesException {
		CompanyBO companyBO = new CompanyBO();
		InventoryBO serviceBO = new InventoryBO();

		model.addAttribute("Company", companyBO);
		List<InventoryBO> productBOList = new ArrayList<>();
		productBOList = productService.listOfProductByPagination(serviceBO);
		model.addAttribute("productList", productBOList);
		return "create-company";
	}

	@RequestMapping(value = "/create-company", method = RequestMethod.POST)
	public String createCompany(@ModelAttribute("Company") CompanyBO companyBO, Model model)
			throws IOException, MySalesException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			if (0 == loginId) {
				return "redirect:/create-company";
			}

			if (0 < loginId && userType.contains("ROLE_ADMIN")) {
				companyBO.setCreatedBy(loginId);

				if (null != companyBO) {

					if (!companyBO.getPassword().equals(companyBO.getConfirmPassword())) {
						model.addAttribute("errorMessage", "Please verify password and Confirm Password");
						return "redirect:/create-company";
					}

					CompanyBO company = companyService.createCompany(companyBO);
					if (null != company && 0 < company.getCompanyId()) {
						model.addAttribute("successMessage", messageSource.getMessage("company.Creation", null, null));
						return "redirect:/view-company";
					}

				}

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create Company has failed:" + ex.getMessage());
			}
			LOGGER.info("Create Company has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-company";
	}

	@RequestMapping(value = "view-company", method = RequestMethod.GET)
	public String listCompany(Model model, HttpServletRequest request, HttpSession session) throws MySalesException {
		LOGGER.entry();
		try {
			String paging = null;
			CompanyBO companyBO = new CompanyBO();
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			long companyId=getUserSecurity().getCompanyId();
		//	model.addAttribute("searchCompany",new CompanyBO());
			
			
			if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
				companyBO.setCompanyId(companyId);       //for companyId based retrieve purpose....
			}
		
			if (null != request.getParameter("page")) {
				paging = request.getParameter("page");
			}
			if (null != request.getParameter("successMessage")) {
				model.addAttribute("successMessage", request.getParameter("successMessage"));
			}
			if (null != request.getParameter("errorMessage")) {
				model.addAttribute("errorMessage", request.getParameter("errorMessage"));
			}
			if(null!=request.getParameter("searchCompanyName")) {
	            String companyName = request.getParameter("searchCompanyName");
	            companyBO.setCompanyName(companyName);
	            model.addAttribute("searchCompanyName", request.getParameter("searchCompanyName"));
	        }
			
			companyPagination(companyBO, paging, session, request, model);
//			model.addAttribute("companyBO", companyBO);
	        model.addAttribute("searchCompany", companyBO);
			
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View Company has failed:" + ex.getMessage());
			}
			LOGGER.info("View Company has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "view-company";

	}

	private void companyPagination(CompanyBO companyBO, String paging, HttpSession session, HttpServletRequest request,
			Model model) throws MySalesException {
//		long loginId = getUserSecurity().getLoginId();
//		List<String> userType = getUserSecurity().getRole();
		long countOfCompany = 0;
		long totalCountOfCompany = 0;
		int page = 1;
		int maxRecord = 10;

		if (null != paging) {
			page = Integer.parseInt(paging);
		}
//		if(null!=request.getParameter("searchCompanyName")) {
//            String company = request.getParameter("searchCompanyName");
//            companyBO.setCompanyName(company);
//            model.addAttribute("searchCompanyName", request.getParameter("searchPrivilegesName"));
//        }
		//Through Admin login company count purpose..
		//if (0 < loginId && userType.contains("ROLE_ADMIN")) {
			countOfCompany = companyService.countOfCompanyBySearch(companyBO);
			if (0 < countOfCompany) {
				totalCountOfCompany = countOfCompany;
				model.addAttribute("totalCountOfCompany", totalCountOfCompany);
			}else {
				model.addAttribute("errorMessage", "Record Not Found!!");  
			}
//		}
//		//Through other login company count purpose..
//		if (0 < loginId && !userType.contains("ROLE_ADMIN")) {
//			countOfCompany = companyService.retriveCompanyCount(companyBO);
//
//			if (0 < countOfCompany) {
//				totalCountOfCompany = countOfCompany;
//				model.addAttribute("totalCountOfCompany", totalCountOfCompany);
//			}else {
//				model.addAttribute("errorMessage", "Record Not Found!!");
//			}
//		}
		
			int startingRecordOfProduct = paginationPageValues(page, maxRecord);
			companyBO.setRecordIndex(startingRecordOfProduct);
			companyBO.setMaxRecord(maxRecord);
			companyBO.setPagination("pagination");
			List<CompanyBO> companyBOList = new ArrayList<>();
			companyBOList = companyService.listOfCompanyByPagination(companyBO);
			if (null != companyBOList && !companyBOList.isEmpty() && 0 < companyBOList.size()) {

				model.addAttribute("companyList",
						PaginationClass.paginationLimitedRecords(page, companyBOList, maxRecord, totalCountOfCompany));
			}
	}

	private int paginationPageValues(int pageNo, int maxOfRecord) {

		int recordsOfPage = 0;
		if (pageNo == 1) {
			recordsOfPage = 0;
		} else {
			recordsOfPage = (pageNo - 1) * maxOfRecord + 1;
			recordsOfPage = recordsOfPage - 1;
		}
		return recordsOfPage;
	}
	@RequestMapping(value = "edit-company", method = RequestMethod.GET)
	public String editCompany(Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException, MySalesException {
		LOGGER.entry();
		try {
			CompanyBO companyBO = new CompanyBO();
			long companyId = Integer.parseInt(request.getParameter("companyId"));
			companyBO.setCompanyId(companyId);
			companyBO = companyService.retriveCompanybyId(companyBO);
			if (null != companyBO) {
				model.addAttribute("CompanyBO", companyBO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit get Company has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit  get Company has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "edit-company";
	}

	@RequestMapping(value = "edit-company", method = RequestMethod.POST)
	public String editCompany(@Valid @ModelAttribute("CompanyBO") CompanyBO companyBO, BindingResult bindingResult,
			Model model, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException, MySalesException {
		LOGGER.entry();
		try {
			long loginId = getUserSecurity().getLoginId();
			List<String> userType = getUserSecurity().getRole();
			if (bindingResult.hasErrors()) {
				return "edit-Company";
			}
			if (0 < loginId && !userType.contains(UserRoles.ROLE_ADMIN.toString())) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(loginId);
				companyBO.setAdminLoginBO(adminLoginBO);
			} else {
				long adminId = loginId;
				long userId = 0;
				if (0 != adminId) {
					userId = loginId;
				}
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(userId);
				companyBO.setAdminLoginBO(adminLoginBO);
			}
			companyBO.setModifiedBy(getUserSecurity().getLoginId());
			boolean status = companyService.updateCompany(companyBO);
			if (status) {
				model.addAttribute("successMessage", messageSource.getMessage("Company.Update", null, null));
				return "redirect:/view-company";
			} else {
				model.addAttribute("errorMessage", "Company Edit Failed!");
			}

			model.addAttribute("companyBO", companyBO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit-update Company has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit-update Company has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-company";
	}

	@RequestMapping(value = "/delete-company", method = RequestMethod.GET)
	public String deleteProduct(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam("companyId") Long companyId) throws FileNotFoundException, MySalesException {
		LOGGER.entry();
		CompanyBO companyBO = new CompanyBO();
		companyBO.setCompanyId(companyId);
		try {
			if (null != companyBO) {
				Boolean status = companyService.deleteCompany(companyBO);
				if (status) {
					model.addAttribute("successMessage", messageSource.getMessage("Company.Delete", null, null));
					return "redirect:/view-company";
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("delete- Company has failed:" + ex.getMessage());
			}
			LOGGER.info("delete- Company has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return "redirect:/view-company";
	}

	//@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/search-company", method = RequestMethod.POST)
    public String searchPrivileges(@Valid @ModelAttribute("searchCompany") CompanyBO bo, BindingResult results,
          HttpServletRequest request, HttpSession session,  Model model) throws MySalesException {
        LOGGER.entry();
        try {
             long companyId = getUserSecurity().getCompanyId();
             long loginId = getUserSecurity().getLoginId();
 			List<String> userType = getUserSecurity().getRole();
//            CompanyBO companyBO = new CompanyBO();
//            companyBO.setCompanyName(bo.getCompanyName());
//            companyBO.setCompanyId(companyId);
           // long loginid = getUserSecurity().getLoginId();
            if (0 == loginId) {
                return "view-company";
            }
            long countOfCompany=0;
            long totalSearchCount=0;
            int page=1;
            int maxRecord=10;
            //String paging = null;
      
            if(0 < loginId && !userType.contains("ROLE_ADMIN")) {
            	bo.setCompanyId(companyId);
            }
            
//            if(null!=request.getParameter(paging)) {
//            	paging = request.getParameter(paging);
//            	page = Integer.parseInt(paging);
//            }
            
            if(null!=bo.getCompanyName()&&!bo.getCompanyName().isEmpty()) {
	            model.addAttribute("searchCompanyName", bo.getCompanyName());
	        }
            
           countOfCompany=companyService.countOfCompanyBySearch(bo);
//            if (null !=companyBO.getCompanyName()) {
//                model.addAttribute("companyName", companyBO.getCompanyName());
//            }
//            if(null!=companyBO.getCompanyName()&& !companyBO.getCompanyName().isEmpty()) {
//                model.addAttribute("searchCompanyName", companyBO.getCompanyName()); 
//            }
            if(0<countOfCompany) {
            totalSearchCount=countOfCompany;
            model.addAttribute("totalSearchCount", totalSearchCount);
        }
//            List<PrivilegesBO> bolist = new ArrayList<PrivilegesBO>();
//            listbo = adminService.searchPrivilegename(privbo);
            
             int startingValueOfRole=paginationPageValues(page, maxRecord);
                bo.setRecordIndex(startingValueOfRole);
                bo.setMaxRecord(maxRecord);
               bo.setPagination("Pagination");
                
                
                List<CompanyBO> companybolist = new ArrayList<CompanyBO>();
                companybolist = companyService.listOfCompanyByPagination(bo);
                if(null!=companybolist&&!companybolist.isEmpty()){
                    
                    model.addAttribute("companyList",  PaginationClass.paginationLimitedRecords(page,  companybolist, maxRecord, totalSearchCount));
                    
                    return "view-company";
                }else {
                    CompanyBO companyBo = new CompanyBO();
                    model.addAttribute("company", companyBo);
                    model.addAttribute("errorMessage", "No Record Found!");
                    return "view-company";
                }
                
               } catch (Exception ex) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("viewCompany has failed:" + ex.getMessage());
            }
            LOGGER.info("viewCompany has failed:" + ex.getMessage());
        } finally {
            LOGGER.exit();
        }
        return null;
    }
    
	@RequestMapping(value = "/view-company-details", method = RequestMethod.GET)
	public String viewParticularCompany(HttpServletRequest request, Model model) throws MySalesException {
		LOGGER.entry();
		try {
			CompanyBO companyBO = new CompanyBO();
			if (null != request.getParameter("companyId")) {
				String id = request.getParameter("companyId");
				long companyId = Long.parseLong(id);
				companyBO.setCompanyId(companyId);
			}

			companyBO = companyService.viewCompanyDetails(companyBO);
			if (0 != companyBO.getCompanyId()) {
				model.addAttribute("companyList", companyBO);
			}
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("view company details: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view company details: Exception \t" + e);
			}
		} finally {
			LOGGER.exit();
		}
		return "view-company-details";

	}

	@RequestMapping(value = "/check_phonenumber", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkPhonenumber(@RequestParam String phoneNumber) throws Exception {

		boolean checkPhoneNumber = false;
		
		try {
			checkPhoneNumber = companyService.checkPhonenumber(phoneNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkPhoneNumber;
	}
	
	@RequestMapping(value = "/check_mobilenumber", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkMobileNumberdress(@RequestParam String mobilenumber)throws MySalesException {
		LOGGER.entry();
		boolean CheckMobileNumberdress = false;
		try {
			CheckMobileNumberdress = companyService.checkcompanymobileNumber(mobilenumber);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckMobileNumber has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckMobileNumber has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return CheckMobileNumberdress;
	}

	@RequestMapping(value = "/check_companyemail", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkemail(@RequestParam String companyemail)throws MySalesException {
		LOGGER.entry();
		boolean CheckEmailAddress = false;
		try {
			CheckEmailAddress = companyService.checkcompanyemail(companyemail);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return CheckEmailAddress;
	}
	
	@RequestMapping(value = "/check_companywebsite", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkwebsite(@RequestParam String companywebsite)throws MySalesException {
		LOGGER.entry();
		boolean CheckWebsite = false;
		try {
			CheckWebsite = companyService.checkcompanywebsite(companywebsite);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckCompanyWebsite has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckCompanyWebsite has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return CheckWebsite;
	}
	
	@RequestMapping(value = "/check_companygst", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkgst(@RequestParam String companygst)throws MySalesException {
		LOGGER.entry();
		boolean CheckGst = false;
		try {
			CheckGst = companyService.checkcompanygst(companygst);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckCompanyGst has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckCompanyGst has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return CheckGst;
	}
}
