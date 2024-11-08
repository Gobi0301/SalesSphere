package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.AccountVO;



public interface AccountsDao {

	AccountVO createAccounts(AccountVO accountVO)throws MySalesException;

	long accountCount(AccountVO accountVO)throws MySalesException;

	List<AccountVO> viewAccount(AccountBO accountBO)throws MySalesException;

	long searchAccountCount(AccountBO accountBO)throws MySalesException;

	List<AccountVO> searchAccounts(AccountBO accountBO)throws MySalesException;

	AccountVO retrieveaccountdetails(AccountVO accountVO)throws MySalesException;

	AccountVO editRetrieve(AccountVO account)throws MySalesException;

	Boolean updateAccounts(AccountVO accountDO)throws MySalesException;

	Boolean deleteAccount(AccountVO account)throws MySalesException;

	//Map<Long, String> retrieveOpportunity() throws MySalesException;
	
	boolean findByContactNo(long mobileNo);

	boolean findByEmailAccounts(String emailAddress);

	boolean checkemail(String emails, long companyId);

	boolean checkcontact(String contact, long companyId);

	List<AccountVO> findAllList(AccountBO accountBO);
}
