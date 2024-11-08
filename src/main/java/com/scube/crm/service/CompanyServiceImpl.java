package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.CompanyBO;
import com.scube.crm.dao.ActivityHistoryDAO;
import com.scube.crm.dao.AdminDAO;
import com.scube.crm.dao.CompanyDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.utils.EncryptAndDecrypt;
import com.scube.crm.vo.Campaign;
import com.scube.crm.vo.Company;
import com.scube.crm.vo.HistoryVO;
import com.scube.crm.vo.User;

@Service
@Transactional
public class CompanyServiceImpl extends ControllerUtils implements CompanyService {
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(CompanyServiceImpl.class);
	@Autowired
	CompanyDao companyDao;
	@Autowired
	AdminDAO adminDao;
	@Autowired
	private ActivityHistoryDAO activityhistoryDAO;

	@Override
	public CompanyBO createCompany(CompanyBO companyBO)throws MySalesException {
		CompanyBO companyBo = new CompanyBO();
		Company companyserviceVO = new Company();
		LOGGER.entry();
		try {
			companyserviceVO.setCompanyName(companyBO.getCompanyName());
			companyserviceVO.setContactPerson(companyBO.getContactPerson());
			companyserviceVO.setCompanyGstNo(companyBO.getCompanyGstNo());
			companyserviceVO.setEmail(companyBO.getCompanyEmail());
			companyserviceVO.setContactNo(companyBO.getContactNumber());
			companyserviceVO.setMobileNo(companyBO.getMobileNumber());
			companyserviceVO.setIndustryType(companyBO.getIndustryType());
			companyserviceVO.setWebsite(companyBO.getWebsite());
			companyserviceVO.setPassword(EncryptAndDecrypt.encrypt(companyBO.getPassword()));
			companyserviceVO.setConfirmPassword(EncryptAndDecrypt.encrypt(companyBO.getConfirmPassword()));
			companyserviceVO.setStreet(companyBO.getStreet());
			companyserviceVO.setCity(companyBO.getCity());
			companyserviceVO.setDistrict(companyBO.getDistrict());
			companyserviceVO.setState(companyBO.getState());
			companyserviceVO.setCountry(companyBO.getCountry());
			companyserviceVO.setPostalCode(companyBO.getPostalCode());
			companyserviceVO.setCreatedBy(companyBO.getCreatedBy());			
			companyserviceVO.setCompanyOwner(true);
			companyserviceVO = companyDao.createCompany(companyserviceVO);
			if (0 < companyserviceVO.getCompanyId()) {
				User user = new User();
				user.setCompany(companyserviceVO);
				user.setName(companyBO.getContactPerson());
				user.setEmailAddress(companyBO.getCompanyEmail());
				user.setPassword(EncryptAndDecrypt.encrypt(companyBO.getPassword()));
				user.setConfirmpassword(EncryptAndDecrypt.encrypt(companyBO.getConfirmPassword()));
				user.setMobileNo(String.valueOf(companyBO.getMobileNumber()));
				
				user.setActive(true);				 
				user.setisDelete(false);
				long id = adminDao.createuser(user);
				if (0 < id) {
					companyBo.setCompanyId(companyserviceVO.getCompanyId());
					
					return companyBo;
				}
			}
			//Activity log history create part dao call........
			if(null!=companyserviceVO) {
				HistoryVO historyvo = new HistoryVO();
		        historyvo.setEntityId(companyserviceVO.getCompanyId());
		        historyvo.setEntityType("Company");
		        historyvo.setActionType("Create");
		        long loginId=getUserSecurity().getLoginId();
				historyvo.setUser(loginId);
				 historyvo.setCompanyId(companyserviceVO.getCompanyId());
		       activityhistoryDAO.activityLogHistory(historyvo);
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add company has failed:" + ex.getMessage());
			}
			LOGGER.info("Add Company has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return null;
	}

	@Override
	public List<CompanyBO> listCompany(CompanyBO companyBO) throws MySalesException{
		LOGGER.entry();
		List<CompanyBO> companyBoList = new ArrayList<>();
		try {
		Company comp = new Company();
		comp.setIsDeleted(false);
		if(null != companyBO.getCompanyId()&& 0< companyBO.getCompanyId() ) {
		comp.setCompanyId(companyBO.getCompanyId());
		}
		comp.setRecordIndex(companyBO.getRecordIndex());
		comp.setMaxRecord(companyBO.getMaxRecord());
		List<Company> companyVoList = companyDao.listCompany(comp);
		if (null != companyVoList && companyVoList.size() > 0) {
			int sNo = companyBO.getRecordIndex() + 1;
			for (Company company : companyVoList) {
				CompanyBO companyBo = new CompanyBO();
				companyBo.setsNo(sNo);
				companyBo.setCompanyId(company.getCompanyId());
				companyBo.setContactPerson(company.getContactPerson());
				companyBo.setCompanyGstNo(company.getCompanyGstNo());
				companyBo.setCompanyName(company.getCompanyName());
				companyBo.setContactNumber(company.getContactNo());
				companyBo.setWebsite(company.getWebsite());
				companyBo.setCompanyEmail(company.getEmail());
				companyBo.setStreet(company.getStreet());
				companyBo.setIndustryType(company.getIndustryType());
				++sNo;
				companyBoList.add(companyBo);
			}
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("List company has failed:" + ex.getMessage());
			}
			LOGGER.info("List Company has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return companyBoList;
	}

	@Override
	public CompanyBO retriveCompanybyId(CompanyBO companyBO)throws MySalesException {
		LOGGER.entry();
		Company companyVO = new Company();
		try {
			companyVO.setCompanyId(companyBO.getCompanyId());
			companyVO = companyDao.retriveCompanybyId(companyVO);
			CompanyBO bo = new CompanyBO();
			if (null != companyVO) {
				bo.setCompanyId(companyVO.getCompanyId());
				bo.setCompanyName(companyVO.getCompanyName());
				bo.setContactPerson(companyVO.getContactPerson());
				bo.setCompanyGstNo(companyVO.getCompanyGstNo());
				bo.setCompanyEmail(companyVO.getEmail());
				bo.setContactNumber(companyVO.getContactNo());
				bo.setMobileNumber(companyVO.getMobileNo());
				bo.setWebsite(companyVO.getWebsite());

				bo.setPassword(EncryptAndDecrypt.decrypt(companyVO.getPassword()));
				bo.setConfirmPassword(EncryptAndDecrypt.decrypt(companyVO.getConfirmPassword()));
				bo.setIndustryType(companyVO.getIndustryType());
				bo.setStreet(companyVO.getStreet());
				bo.setCity(companyVO.getCity());
				bo.setDistrict(companyVO.getDistrict());
				bo.setState(companyVO.getState());
				bo.setCountry(companyVO.getCountry());
				bo.setPostalCode(companyVO.getPostalCode());
				bo.setCompanyLogo(companyVO.getCompanyLogo());
				bo.setCachedata(companyVO.getEmail());
				return bo;
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("retrive of company has failed:" + ex.getMessage());
			}
			LOGGER.info("retrive of company has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return null;
	}

	@Override
	public boolean updateCompany(CompanyBO companyBO)throws MySalesException {
		LOGGER.entry();
		Company company = new Company();
		Company companyserviceVO = new Company();
		try {
			if (null != companyBO) {
				company.setCompanyId(companyBO.getCompanyId());
				company.setCompanyName(companyBO.getCompanyName());
				company.setContactPerson(companyBO.getContactPerson());
				company.setCompanyGstNo(companyBO.getCompanyGstNo());
				company.setEmail(companyBO.getCompanyEmail());
				company.setContactNo(companyBO.getContactNumber());
				company.setMobileNo(companyBO.getMobileNumber());
				company.setIndustryType(companyBO.getIndustryType());
				company.setWebsite(companyBO.getWebsite());

				company.setPassword(EncryptAndDecrypt.encrypt(companyBO.getPassword()));
				company.setConfirmPassword(EncryptAndDecrypt.encrypt(companyBO.getConfirmPassword()));
				company.setStreet(companyBO.getStreet());
				company.setCity(companyBO.getCity());
				company.setDistrict(companyBO.getDistrict());
				company.setState(companyBO.getState());
				company.setCountry(companyBO.getCountry());
				company.setPostalCode(companyBO.getPostalCode());
				company.setModifiedBy(companyBO.getModifiedBy());

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("update of company has failed:" + ex.getMessage());
			}
			LOGGER.info("update of company has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		boolean status = companyDao.updateCompany(company);
		User loginVO = new User();
		User loginVOemail = new User();
		loginVOemail.setEmailAddress(companyBO.getCachedata());
			try {
				if (0 < companyBO.getAdminLoginBO().getId()) {
					loginVO.setId(companyBO.getAdminLoginBO().getId());
				}
				loginVO =adminDao.retrieveusersemail(loginVOemail);
				//loginVO = adminDao.retrieveusers(loginVO);
				companyserviceVO.setCompanyId(companyBO.getCompanyId());
				loginVO.setCompany(companyserviceVO);
				//loginVO.setId(companyBO.getAdminLoginBO().getId());
				loginVO.setEmailAddress(companyBO.getCompanyEmail());
				//System.out.println("New password : "+companyBO.getPassword());
				//System.out.println("New password : "+companyBO.getConfirmPassword());

				loginVO.setPassword(EncryptAndDecrypt.encrypt(companyBO.getPassword()));
				loginVO.setConfirmpassword(EncryptAndDecrypt.encrypt(companyBO.getConfirmPassword()));
				loginVO.setName(companyBO.getContactPerson());
				loginVO.setMobileNo(String.valueOf(companyBO.getMobileNumber()));
				loginVO.setActive(true);				 
				loginVO.setisDelete(false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		//Activity log history create part dao call........
		if(status=true) {
			HistoryVO historyvo = new HistoryVO();
	        historyvo.setEntityId(companyBO.getCompanyId());
	        historyvo.setEntityType("Company");
	        historyvo.setActionType("Update");
	        long loginId=getUserSecurity().getLoginId();
			historyvo.setUser(loginId);
			 historyvo.setCompanyId(companyBO.getCompanyId());
	       activityhistoryDAO.activityLogHistory(historyvo);
		}

		return status;
	}

	@Override
	public Boolean deleteCompany(CompanyBO companyBO)throws MySalesException {
		LOGGER.entry();
		Company company = new Company();
		try {

			company = companyDao.getCompanyId(companyBO);
			if (null != company && 0 < company.getCompanyId()) {

				company.setIsDeleted(true);

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deleted of company has failed:" + ex.getMessage());
			}
			LOGGER.info("deleted of company has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		boolean status= companyDao.deleteCompany(company);
		//Activity log history create part dao call........
				if(status=true) {
					HistoryVO historyvo = new HistoryVO();
			        historyvo.setEntityId(companyBO.getCompanyId());
			        historyvo.setEntityType("Company");
			        historyvo.setActionType("Delete");
			        long loginId=getUserSecurity().getLoginId();
					historyvo.setUser(loginId);
					 historyvo.setCompanyId(companyBO.getCompanyId());
			       activityhistoryDAO.activityLogHistory(historyvo);
				}
		return status;
	}

	@Override
    public long countOfCompanyBySearch(CompanyBO companyBO) {
        try {
            Company companyVO=new Company();
            long countOfCompany = 0;

//            if (null != companyBO.getCompanyName()) {
//                companyVO.setCompanyName(companyBO.getCompanyName());
//            }
            
            if (null != companyBO.getCompanyId()) {
                companyVO.setCompanyId(companyBO.getCompanyId());
            }
            
            countOfCompany= companyDao.countOfCompanyBySearch(companyVO);
            if (0 < countOfCompany) {
                return countOfCompany;
            }
            }catch (Exception ex) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("listOfUsersBySearchPagination has failed:" + ex.getMessage());
                }
                LOGGER.info("listOfUsersBySearchPagination has failed:" + ex.getMessage());
            } finally {
                LOGGER.exit();
            }
            return 0;
    }

    @Override
    public List<CompanyBO> listOfCompanyByPagination(CompanyBO companyBO) {
        // TODO Auto-generated method stub
                Company companyVO = new Company();
                List<CompanyBO> companyBOList = new ArrayList<>();
                try {
                    List<Company> companyVOList = new ArrayList<>();
                    //privilegesVO.setCompanyId(privilegesBO.getCompanyId());
                    companyVO.setRecordIndex(companyBO.getRecordIndex());
                    companyVO.setMaxRecord(companyBO.getMaxRecord());
                    companyVO.setCompanyName(companyBO.getCompanyName());
                    
                    if (null != companyBO.getCompanyId()) {
                        companyVO.setCompanyId(companyBO.getCompanyId());
                    }

                    companyVOList = companyDao.listOfCompanyByPagination(companyVO);

                    if (null != companyVOList && !companyVOList.isEmpty()) {

                        int sno = companyBO.getRecordIndex();
                        for (Company company : companyVOList) {
                        	CompanyBO companyBo = new CompanyBO();
            				companyBo.setsNo(++sno);
            				companyBo.setCompanyId(company.getCompanyId());
            				companyBo.setContactPerson(company.getContactPerson());
            				companyBo.setCompanyGstNo(company.getCompanyGstNo());
            				companyBo.setCompanyName(company.getCompanyName());
            				companyBo.setContactNumber(company.getContactNo());
            				companyBo.setWebsite(company.getWebsite());
            				companyBo.setCompanyEmail(company.getEmail());
            				companyBo.setStreet(company.getStreet());
            				companyBo.setIndustryType(company.getIndustryType());
            			
            				companyBOList.add(companyBo);

                        }
                    }
                } catch (Exception ex) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("retrive User Pagination  has failed:" + ex.getMessage());
                    }
                    LOGGER.info(" retrive User Pagination User has failed:" + ex.getMessage());
                }
                return companyBOList;
    }



	
	
	public long retriveCompanyCount(CompanyBO companyBO) throws MySalesException {
		Company company =new Company();
		company.setIsDeleted(false);
		company.setCompanyId(companyBO.getCompanyId());
		company.setCompanyName(companyBO.getCompanyName());
		return companyDao.retriveCompanyCount(company);
	}


	@Override
	public CompanyBO viewCompanyDetails(CompanyBO companyBO)throws MySalesException {
		LOGGER.entry();
		try {
		Company company = new Company();
		company.setCompanyId(companyBO.getCompanyId());
		company = companyDao.viewCompanyDetails(company);
		if (company != null) {
			companyBO.setCompanyId(company.getCompanyId());
			companyBO.setCompanyName(company.getCompanyName());
			companyBO.setCompanyEmail(company.getEmail());
			companyBO.setContactPerson(company.getContactPerson());
			companyBO.setWebsite(company.getWebsite());
			companyBO.setCompanyGstNo(company.getCompanyGstNo());
			companyBO.setContactNumber(company.getContactNo());
			companyBO.setMobileNumber(company.getMobileNo());
			companyBO.setIndustryType(company.getIndustryType());
			companyBO.setStreet(company.getStreet());
			companyBO.setCity(company.getCity());
			companyBO.setDistrict(company.getDistrict());
			companyBO.setState(company.getState());
			companyBO.setCountry(company.getCountry());
			companyBO.setCompanyLogo(company.getCompanyLogo());
			companyBO.setPostalCode(company.getPostalCode());
			
		}
		
		//Activity log history create part dao call........
				if(null!=company) {
					HistoryVO historyvo = new HistoryVO();
			        historyvo.setEntityId(companyBO.getCompanyId());
			        historyvo.setEntityType("Company");
			        historyvo.setActionType("View");
			        long loginId=getUserSecurity().getLoginId();
					historyvo.setUser(loginId);
					 historyvo.setCompanyId(companyBO.getCompanyId());
			       activityhistoryDAO.activityLogHistory(historyvo);
				}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view of company has failed:" + ex.getMessage());
			}
			LOGGER.info("view of company has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return companyBO;

	}

	@Override
	public boolean checkPhonenumber(String phoneNumber) {
		boolean checkPhoneNumber=false;
		checkPhoneNumber=companyDao.checkPhonenumber(phoneNumber);
		return checkPhoneNumber;
	}

	@Override
	public boolean checkcompanymobileNumber(String mobilenumber) {
		LOGGER.entry();
		boolean CheckMobileNumber = false;
		try {
			CheckMobileNumber = companyDao.checkmobileNumber(mobilenumber);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckMobileNumber has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckMobileNumber has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return CheckMobileNumber;
	}

	@Override
	public boolean checkcompanyemail(String companyemail) {
		LOGGER.entry();
		boolean CheckCompanyEmail = false;
		try {
			CheckCompanyEmail = companyDao.checkcompanyemail(companyemail);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return CheckCompanyEmail;
	}

	@Override
	public boolean checkcompanywebsite(String companywebsite) {
		LOGGER.entry();
		boolean CheckCompanywebsite = false;
		try {
			CheckCompanywebsite = companyDao.checkcompanywebsite(companywebsite);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckCompanyWebsite has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckCompanyWebsite has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return CheckCompanywebsite;
	}

	@Override
	public boolean checkcompanygst(String companygst) {
		LOGGER.entry();
		boolean CheckCompanyGst = false;
		try {
			CheckCompanyGst = companyDao.checkcompanygst(companygst);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckCompanyGst has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckCompanyGst has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return CheckCompanyGst;
	}

	@Override
	public long retriveCompanyCount() throws MySalesException {
		Company company =new Company();
		company.setIsDeleted(false);
		return companyDao.retriveCount(company);
	}

}
