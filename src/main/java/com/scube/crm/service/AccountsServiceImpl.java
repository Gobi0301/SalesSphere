package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.dao.AccountsDao;
import com.scube.crm.dao.AdminDAO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.ContactVO;
import com.scube.crm.vo.Opportunity;
import com.scube.crm.vo.User;

@Service("accountsService")
@Transactional
public class AccountsServiceImpl implements AccountsService {

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(AccountsServiceImpl.class);
	@Autowired
	private AccountsDao accountsDao;

	@Autowired
	private AdminDAO adminDAO;

	@Override
	public List<User> retrieveUser(User user) throws MySalesException {

		return adminDAO.retrieveUser(user);
	}

	@Override
	public AccountBO createAccounts(AccountBO accountBO) throws MySalesException {
		// TODO Auto-generated method stub
		AccountsServiceImpl.LOGGER.entry();
		AccountVO accountVO = new AccountVO();
		ContactVO contact = new ContactVO();

		List<ContactVO> contactList = new ArrayList<ContactVO>();
		try {

			accountVO.setAccountName(accountBO.getAccountName());
			accountVO.setSalutation(accountBO.getSalutation());
			accountVO.setIsDeleted(accountBO.getIsDeleted());
			accountVO.setCreatedBy(accountBO.getCreatedBy());
			accountVO.setAnnualRevenue(accountBO.getAnnualRevenue());
			accountVO.setParentAccount(accountBO.getParentAccount());
			accountVO.setAccountOwner(accountBO.getAccountOwner());
			accountVO.setPhone(accountBO.getContactNo());
			accountVO.setType(accountBO.getType());
			accountVO.setIndustry(accountBO.getIndustry());
			accountVO.setNumberOfEmployees(accountBO.getNoOfEmployess());
			accountVO.setBillingCity(accountBO.getCity());
			accountVO.setBillingStreet(accountBO.getStreet());
			accountVO.setBillingState(accountBO.getState());
			accountVO.setBillingCountry(accountBO.getCountry());
			accountVO.setBillingPostalCode(accountBO.getPostalCode());
			accountVO.setBillingCountryCode(accountBO.getCountryCode());
			accountVO.setDescription(accountBO.getDescription());
			accountVO.setEmail(accountBO.getEmail());

			contact.setSalutation(accountBO.getSalutation());
			contact.setEmail(accountBO.getEmail());
			contact.setPhone(accountBO.getContactNo());
			contact.setStreet(accountBO.getStreet());
			contact.setCity(accountBO.getCity());
			contact.setState(accountBO.getState());
			contact.setCountry(accountBO.getCountry());
			contact.setFirstname(accountBO.getAccountName());
			contact.setCompanyId(accountBO.getCompanyId());
			contact.setCreatedBy(accountBO.getCreatedBy());
			contact.setAccount(accountVO);
			contactList.add(contact);
			accountVO.setContact(contactList);

			User user = new User();
			if (0 < accountBO.getCompanyId()) {
				accountVO.setCompanyId(accountBO.getCompanyId());
			}

			user.setId(accountBO.getAssignedTo().getUserId());
			accountVO.setUser(user);
			accountVO = accountsDao.createAccounts(accountVO);
			if (null != accountVO) {
				accountBO.setAccountId(accountVO.getAccountId());
			}

		} catch (Exception e) {

			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(" account create: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" account create: Exception \t" + e);
			}
		} finally {
			AccountsServiceImpl.LOGGER.exit();
		}
		return accountBO;
	}

	@Override
	public long accountCount(AccountBO accountBO) throws MySalesException {
		AccountVO accountVO = new AccountVO();
		if (null != accountBO.getCompanyId() && 0 < accountBO.getCompanyId()) {
			accountVO.setCompanyId(accountBO.getCompanyId());
		}
		accountVO.setAccountName(accountBO.getAccountName());
		return accountsDao.accountCount(accountVO);
	}

	@Override
	public List<AccountBO> viewAccount(AccountBO accountBO) throws MySalesException {
		AccountsServiceImpl.LOGGER.entry();
		List<AccountVO> accountVO = new ArrayList<AccountVO>();
		List<AccountBO> accountbO = new ArrayList<AccountBO>();

		try {
			accountVO = accountsDao.viewAccount(accountBO);
			if (null != accountVO && !accountVO.isEmpty()) {
				int sNo = accountBO.getRecordIndex();
				for (AccountVO obj : accountVO) {
					AccountBO account = new AccountBO();

					account.setsNO(++sNo);
					account.setAccountId(obj.getAccountId());
					account.setAccountName(obj.getAccountName());
					account.setPhone(obj.getPhone());
					AdminUserBO user = new AdminUserBO();
					user.setName(obj.getUser().getName());
					account.setAssignedTo(user);

					accountbO.add(account);
				}

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving Account List has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving Account List has failed: " + ex.getMessage());
		} finally {
			AccountsServiceImpl.LOGGER.exit();
		}
		return accountbO;
	}

	@Override
	public long searchAccountCount(AccountBO accountBO) throws MySalesException {
		AccountBO account = new AccountBO();
		AccountVO accountV = new AccountVO();
		account.setAccountName(accountV.getAccountName());
		return accountsDao.searchAccountCount(accountBO);
	}

	@Override
	public List<AccountBO> searchAccounts(AccountBO accountBO) throws MySalesException {
		AccountsServiceImpl.LOGGER.entry();
		List<AccountVO> accountVO = new ArrayList<AccountVO>();
		List<AccountBO> accountbO = new ArrayList<AccountBO>();

		try {
			accountVO = accountsDao.searchAccounts(accountBO);
			if (null != accountVO && !accountVO.isEmpty()) {
				int sNo = accountBO.getRecordIndex();
				for (AccountVO obj : accountVO) {
					AccountBO account = new AccountBO();
					account.setsNO(++sNo);
					account.setAccountId(obj.getAccountId());
					account.setAccountName(obj.getAccountName());
					account.setPhone(obj.getPhone());
					AdminUserBO user = new AdminUserBO();
					user.setName(obj.getUser().getName());
					account.setAssignedTo(user);
					user.setId(obj.getUser().getId());
					account.setAssignedTo(user);
					accountbO.add(account);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving Account List has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving Account List has failed: " + ex.getMessage());
		} finally {
			AccountsServiceImpl.LOGGER.exit();
		}
		return accountbO;
	}

	@Override
	public AccountVO retrieveaccountdetails(AccountVO accountVO) throws MySalesException {
		AccountBO accountBo = new AccountBO();
		AccountVO account = new AccountVO();

		account = accountsDao.retrieveaccountdetails(accountVO);
		if (account.getParentAccount().isEmpty()) {
			account.setParentAccount("NA");

		}
		return account;
	}

	@Override
	public AccountBO editRetrieve(AccountBO accountBO) throws MySalesException {
		AccountsServiceImpl.LOGGER.entry();
		AccountBO accountBo = new AccountBO();
		AccountVO account = new AccountVO();

		account.setAccountId(accountBO.getAccountId());
		account = accountsDao.editRetrieve(account);
		try {
			if (null != account) {
				accountBo.setAccountId(account.getAccountId());
				accountBo.setAccountName(account.getAccountName());
				accountBo.setSalutation(account.getSalutation());
				accountBo.setParentAccount(account.getParentAccount());
				accountBo.setAnnualRevenue(account.getAnnualRevenue());
				accountBo.setAccountOwner(account.getAccountOwner());
				accountBo.setContactNo(account.getPhone());
				accountBo.setType(account.getType());
				accountBo.setIndustry(account.getIndustry());
				accountBo.setNoOfEmployess(account.getNumberOfEmployees());
				accountBo.setStreet(account.getBillingStreet());
				accountBo.setCity(account.getBillingCity());
				accountBo.setState(account.getBillingState());
				accountBo.setCountry(account.getBillingCountry());
				accountBo.setPostalCode(account.getBillingPostalCode());
				accountBo.setCountryCode(account.getBillingCountryCode());
				accountBo.setDescription(account.getDescription());
				accountBo.setEmail(account.getEmail());
				accountBo.setContactId(account.getContact().get(0).getContactId());
				AdminUserBO adminUserBO = new AdminUserBO();
				adminUserBO.setId(account.getUser().getId());
				adminUserBO.setName(account.getUser().getName());
				accountBo.setAssignedTo(adminUserBO);

			}
		} catch (Exception e) {

			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(" account create: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" account create: Exception \t" + e);
			}
		} finally {
			AccountsServiceImpl.LOGGER.exit();
		}
		return accountBo;
	}

	@Override
	public Boolean updateAccount(AccountBO account) throws MySalesException {
		AccountsServiceImpl.LOGGER.entry();
		AccountVO accountVO = new AccountVO();
		ContactVO contact = new ContactVO();
		Opportunity opportunitys = new Opportunity();
		List<ContactVO> contactList = new ArrayList<ContactVO>();
		try {
			opportunitys.setOpportunityId(account.getAccountSource());
			accountVO.setAccountId(account.getAccountId());
			accountVO.setAccountName(account.getAccountName());
			accountVO.setSalutation(account.getSalutation());
			accountVO.setIsDeleted(false);
			accountVO.setCreatedBy(account.getCreatedBy());
			accountVO.setAnnualRevenue(account.getAnnualRevenue());
			accountVO.setParentAccount(account.getParentAccount());
			accountVO.setAccountOwner(account.getAccountOwner());
			accountVO.setPhone(account.getContactNo());
			accountVO.setType(account.getType());
			accountVO.setIndustry(account.getIndustry());
			accountVO.setNumberOfEmployees(account.getNoOfEmployess());
			accountVO.setBillingCity(account.getCity());
			accountVO.setBillingCountry(account.getCountry());
			accountVO.setBillingCountryCode(account.getCountryCode());
			accountVO.setBillingPostalCode(account.getPostalCode());
			accountVO.setBillingState(account.getState());
			accountVO.setBillingStreet(account.getStreet());
			accountVO.setDescription(account.getDescription());
			accountVO.setEmail(account.getEmail());

			contact.setSalutation(account.getSalutation());
			contact.setContactId(account.getContactId());
			contact.setEmail(account.getEmail());
			contact.setPhone(account.getContactNo());
			contact.setStreet(account.getStreet());
			contact.setCity(account.getCity());
			contact.setState(account.getState());
			contact.setCountry(account.getCountry());
			contact.setFirstname(account.getAccountName());
			contact.setCompanyId(account.getCompanyId());
			contact.setAccount(accountVO);
			contactList.add(contact);
			accountVO.setContact(contactList);

			User user = new User();
			user.setId(account.getAssignedTo().getUserId());
			accountVO.setUser(user);
			accountVO.setCompanyId(account.getCompanyId());
			return accountsDao.updateAccounts(accountVO);

		} catch (Exception e) {

			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(" account create: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" account create: Exception \t" + e);
			}
		} finally {
			AccountsServiceImpl.LOGGER.exit();
		}
		return true;
	}

	@Override
	public Boolean deleteAccount(AccountBO accountBO) throws MySalesException {
		AccountVO account = new AccountVO();
		account.setAccountId(accountBO.getAccountId());
		account.setCompanyId(accountBO.getCompanyId());
		account.setIsDeleted(true);
		return accountsDao.deleteAccount(account);
	}

	@Override
	public boolean findByContactNo(long mobileNo) {
		return accountsDao.findByContactNo(mobileNo);
	}

	@Override
	public boolean findByEmailAccounts(String emailAddress) {
		return accountsDao.findByEmailAccounts(emailAddress);

	}

	@Override
	public boolean checkemails(String emails, long companyId) {
		LOGGER.entry();
		boolean checkEmail = false;
		try {
			checkEmail = accountsDao.checkemail(emails, companyId);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkEmail;
	}

	@Override
	public boolean checkContactNumber(String contact, long companyId) {
		LOGGER.entry();
		boolean checkContact = false;
		try {
			checkContact = accountsDao.checkcontact(contact, companyId);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkContact;
	}

	@Override
	public List<AccountBO> retrieveUser(AccountBO accountBO) {
		List<AccountVO> accountVO = new ArrayList<AccountVO>();
		List<AccountBO> accountbO = new ArrayList<AccountBO>();

		try {
			accountVO = accountsDao.viewAccount(accountBO);
			if (null != accountVO && !accountVO.isEmpty()) {
				
				 accountbO = accountVO.stream()
			                .map(obj -> {
			                    AccountBO account = new AccountBO();
			                    AdminUserBO user = new AdminUserBO();
			                    user.setName(obj.getUser().getName());
			                    user.setId(obj.getUser().getId());
			                    account.setAssignedTo(user);
			                    return account;
			                })
			                .distinct()
			                .collect(Collectors.toList());
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving Account List has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving Account List has failed: " + ex.getMessage());
		} finally {
			AccountsServiceImpl.LOGGER.exit();
		}
		return accountbO;
	}
}