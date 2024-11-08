package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.CompanyBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.Company;

public interface CompanyDao {
	List<Company> listCompany(Company company) throws MySalesException;

	Boolean deleteCompany(Company company) throws MySalesException;

	boolean updateCompany(Company company) throws MySalesException;

	Company getCompanyId(CompanyBO companyBO) throws MySalesException;

	Company retriveCompanybyId(Company companyVO) throws MySalesException;

	long retriveCompanyCount(Company company) throws MySalesException;

	Company createCompany(Company company) throws MySalesException;

	Company viewCompanyDetails(Company company) throws MySalesException;

	boolean checkPhonenumber(String phoneNumber);

	boolean checkmobileNumber(String mobilenumber);

	boolean checkcompanyemail(String companyemail);

	boolean checkcompanywebsite(String companywebsite);

	boolean checkcompanygst(String companygst);

	long retriveCount(Company company);

	long countOfCompanyBySearch(Company companyVO);

	List<Company> listOfCompanyByPagination(Company companyVO);
}
