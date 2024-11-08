package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.User;




public interface AccountsService {

	public AccountBO createAccounts(AccountBO accountBO)throws MySalesException;

	public long accountCount(AccountBO accountBO)throws MySalesException;

	public List<AccountBO> viewAccount(AccountBO accountBO)throws MySalesException;

	public long searchAccountCount(AccountBO accountBO)throws MySalesException;

	public List<AccountBO> searchAccounts(AccountBO accountBO)throws MySalesException;

	public AccountVO retrieveaccountdetails(AccountVO accountVO)throws MySalesException;

	public AccountBO editRetrieve(AccountBO accountBO)throws MySalesException;

	public Boolean updateAccount(AccountBO account)throws MySalesException;

	public Boolean deleteAccount(AccountBO accountBO)throws MySalesException;

	public List<User> retrieveUser(User user)throws MySalesException;

	//Map<Long, String> retrieveOpportunity()throws MySalesException;


	boolean findByContactNo(long mobileNo);

	boolean findByEmailAccounts(String emailAddress);

	public boolean checkemails(String emails, long companyIdd);

	public boolean checkContactNumber(String contact, long icompanyIdd);

	public List<AccountBO> retrieveUser(AccountBO accountBO);

}

