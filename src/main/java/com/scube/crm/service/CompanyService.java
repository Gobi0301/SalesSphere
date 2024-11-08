package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.CompanyBO;
import com.scube.crm.exception.MySalesException;

public interface CompanyService {
	CompanyBO createCompany(CompanyBO companyserviceBO) throws MySalesException;

	List<CompanyBO> listCompany(CompanyBO companyBO) throws MySalesException;

	CompanyBO retriveCompanybyId(CompanyBO companyBO) throws MySalesException;

	boolean updateCompany(CompanyBO companyBO) throws MySalesException;

	Boolean deleteCompany(CompanyBO companyBO) throws MySalesException;

	long retriveCompanyCount(CompanyBO companyBO) throws MySalesException;

	CompanyBO viewCompanyDetails(CompanyBO companyBO) throws MySalesException;

	boolean checkPhonenumber(String phoneNumber);

	boolean checkcompanymobileNumber(String mobilenumber);

	boolean checkcompanyemail(String companyemail);

	boolean checkcompanywebsite(String companywebsite);

	boolean checkcompanygst(String companygst);

	long retriveCompanyCount() throws MySalesException;

	List<CompanyBO> listOfCompanyByPagination(CompanyBO companyBO);

	long countOfCompanyBySearch(CompanyBO companyBO);

	
}
