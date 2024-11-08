package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.utils.ErrorCodes;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.ContactVO;
import com.scube.crm.vo.Leads;
import com.scube.crm.vo.SkillsVO;
import com.scube.crm.vo.WorkItemSkillsVO;

@Repository
public class AccountsDaoImpl extends BaseDao implements AccountsDao{

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(AccountsDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {

		return sessionFactory.getCurrentSession();

	}

	@Override
	public AccountVO createAccounts(AccountVO accountVO) throws MySalesException {
		AccountsDaoImpl.LOGGER.entry();
		AccountVO account=new AccountVO();
		try{
			Session session = getSession();
			Integer accountId=  (Integer) session.save(accountVO);
			session.flush();
			if(0!=accountId){
				account.setAccountId(accountId);
				return account;
			}
		}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create account has failed:" + ex.getMessage());
			}
			LOGGER.info("create account has failed:" + ex.getMessage());
		}finally {
			AccountsDaoImpl.LOGGER.exit();
		}
		return account;

	}

	@Override
	public long accountCount(AccountVO accountVO) throws MySalesException {
		AccountsDaoImpl.LOGGER.entry();
		long leadscount=0;
		AccountBO  accountBO=new AccountBO();
		try { 
			Criteria cr=getSession().createCriteria(AccountVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			if(null != accountVO.getCompanyId()&& 0< accountVO.getCompanyId()) {
			cr.add(companyValidation(accountVO.getCompanyId()));
			}
			cr.setProjection(Projections.rowCount());
			accountVO.setAccountName(accountBO.getAccountName());
			leadscount=(long) cr.uniqueResult();
		
	}catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("accountCount retrive has failed:" + ex.getMessage());
		}
		LOGGER.info("accountCount retrive has failed:" + ex.getMessage());
	}finally {
		AccountsDaoImpl.LOGGER.exit();
	}
		return leadscount;
	}

	@Override
	public List<AccountVO> viewAccount(AccountBO accountBO) throws MySalesException {
		AccountsDaoImpl.LOGGER.entry();
		List<AccountVO> accountlist=new ArrayList<AccountVO>();
		try{
			
			Criteria cr = getSession().createCriteria(AccountVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			if(null != accountBO.getCompanyId()&& 0< accountBO.getCompanyId()) {
			cr.add(companyValidation(accountBO.getCompanyId()));
			}
			cr.setFirstResult(accountBO.getRecordIndex());
		    cr.setMaxResults(accountBO.getMaxRecord());
		    accountlist=cr.list();
			
		}	
		catch(Exception ex){
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Retrieve account has failed:" + ex.getMessage());
			
			LOGGER.info("Retrieve account has failed:" + ex.getMessage());
			}
		
	}	finally {
		AccountsDaoImpl.LOGGER.exit();
	}
		return accountlist;

}

	@Override
	public long searchAccountCount(AccountBO accountBO) throws MySalesException {
		AccountsDaoImpl.LOGGER.entry();
		long count=0;
		try { 
			Criteria cr=getSession().createCriteria(AccountVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			
			  if (null != accountBO) {
				  if (null != accountBO.getAccountName()&& !accountBO.getAccountName().isEmpty()) {
			  cr.add(Restrictions.ilike("accountName", accountBO.getAccountName().trim(),
			  MatchMode.ANYWHERE));
			  } 
				  if (0 != accountBO.getPhone()) {
			  cr.add(Restrictions.eq("phone", accountBO.getPhone())); 
				  }
				  if (null!= accountBO.getId()) {
						cr.add(Restrictions.eq("user.id", accountBO.getId()));
					}
				  if(null != accountBO.getCompanyId()&& 0< accountBO.getCompanyId()) {
				  cr.add(Restrictions.eq("companyId", accountBO.getCompanyId()));
				  }
				/*
				 * if (null!= accountBO.getId()) { cr.add(Restrictions.eq("user.id",
				 * accountBO.getUsers().getId())); }
				 */
				/*
				 * if(0!=accountBO.getAccountSource()) {
				 * cr.add(Restrictions.eq("opportunitys.opportunityId",
				 * accountBO.getOpportunitys().getOpportunityId())); }
				 */}
			  cr.setProjection(Projections.rowCount());
			count=(long) cr.uniqueResult();
			  
	}catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("accountcount retrive has failed:" + ex.getMessage());
		}
		LOGGER.info("accountcount retrive has failed:" + ex.getMessage());
	}finally {
		AccountsDaoImpl.LOGGER.exit();
	}
		return count;

	}

	@Override
	public List<AccountVO> searchAccounts(AccountBO accountBO) throws MySalesException {
		AccountsDaoImpl.LOGGER.entry();
		List<AccountVO> accountlistDO=new ArrayList<AccountVO>();
		try { 
		//	int count=1;
			Criteria cr=getSession().createCriteria(AccountVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.setFirstResult(accountBO.getRecordIndex());
			cr.setMaxResults(accountBO.getMaxRecord());
			
			if (null != accountBO) {
				if (null != accountBO.getAccountName()&& !accountBO.getAccountName().isEmpty()) {
					cr.add(Restrictions.ilike("accountName", accountBO.getAccountName().trim(), MatchMode.ANYWHERE));
				}
				if (0!= accountBO.getPhone()) {
					cr.add(Restrictions.eq("phone", accountBO.getPhone()));
				}
				if (null!= accountBO.getId()) {
					cr.add(Restrictions.eq("user.id", accountBO.getId()));
				}
				/*
				 * if(0!=accountBO.getAccountSource()) {
				 * cr.add(Restrictions.eq("opportunitys.opportunityId",
				 * accountBO.getOpportunitys().getOpportunityId())); }
				 */
				if (null!= accountBO.getCompanyId()&& 0< accountBO.getCompanyId() ) {
					cr.add(Restrictions.eq("companyId", accountBO.getCompanyId()));
				}
				
			}
			accountlistDO=cr.list();
		//	List<AccountDO> values = new ArrayList<AccountDO>(); 
			
			/*if(null!=values&&!values.isEmpty()){
				int sNo=accountBO.getRecordIndex();
				for(AccountDO obj: values){
					AccountDO account=new AccountDO();
				     account.setsNO(++sNo);
						account.setAccountId(obj.getAccountId());
						account.setAccountName(obj.getAccountName());
						account.setPhone(obj.getPhone());
						if(null !=obj.getUser().getName() && !obj.getUser().getName().isEmpty()){
						user.setName(obj.getUser().getName());
						account.setUser(user);
						}
						if(0 !=obj.getUser().getId()) {
						user.setId(obj.getUser().getId());
						account.setUser(user);
						}
						if(null !=obj.getOpportunitys().getFirstName() && !obj.getOpportunitys().getFirstName().isEmpty()) {
						opportunitys.setFirstName(obj.getOpportunitys().getFirstName());
						account.setOpportunitys(opportunitys);
					}
						accountlistDO.add(account);
				}*/
		//	}
	}catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("searchaccount retrive has failed:" + ex.getMessage());
		}
		LOGGER.info("searchaccount retrive has failed:" + ex.getMessage());
	}finally {
		AccountsDaoImpl.LOGGER.exit();
	}
		return accountlistDO;

	}

	@Override
	public AccountVO retrieveaccountdetails(AccountVO accountVO) throws MySalesException {
	AccountsDaoImpl.LOGGER.entry();
		
		try { 
			Criteria cr=getSession().createCriteria(AccountVO.class);
		  cr.add(Restrictions.eq("isDeleted", false));
		  cr.add(Restrictions.eq("accountId",accountVO.getAccountId()));
		  cr.add(companyValidation(accountVO.getCompanyId()));
		  accountVO=(AccountVO) cr.uniqueResult();
		
	}catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Leadlist retrive has failed:" + ex.getMessage());
		}
		LOGGER.info("Leadlist retrive has failed:" + ex.getMessage());
	}finally {
		AccountsDaoImpl.LOGGER.exit();
	}
		return accountVO;

	}

	@Override
	public AccountVO editRetrieve(AccountVO account) throws MySalesException {
		AccountsDaoImpl.LOGGER.entry();
		try {
			Criteria cr=getSession().createCriteria(AccountVO.class);
			cr.add(Restrictions.eq("accountId", account.getAccountId()));
			cr.add(Restrictions.eq("isDeleted", false));
			account=(AccountVO) cr.uniqueResult();
			if(null!=account) {
			return account;	
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("edit retrive has failed:" + ex.getMessage());
			}
			LOGGER.info("edit retrive has failed:" + ex.getMessage());
		}finally {
			AccountsDaoImpl.LOGGER.exit();
		}
		return account;
	}

	@Override
	public Boolean updateAccounts(AccountVO accountVO) throws MySalesException {
		AccountsDaoImpl.LOGGER.entry();
		try {
			if(null!=accountVO) {
			Session session =getSession();
			session.saveOrUpdate(accountVO);
			session.flush();
			return true;
		}else {
			return false;
		}
	}catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("edit retrive has failed:" + ex.getMessage());
		}
		LOGGER.info("edit retrive has failed:" + ex.getMessage());
	}finally {
		AccountsDaoImpl.LOGGER.exit();
	}
	return false;
}

	@Override
	public Boolean deleteAccount(AccountVO account) throws MySalesException {
		AccountsDaoImpl.LOGGER.entry();
		int result = 0;
		int result1=0;
		try {
			Query query = getSession()
					.createQuery("update AccountVO S set S.isDeleted=:isDeleted where S.accountId=:accountId");
			query.setParameter("isDeleted", account.getIsDeleted());
			query.setParameter("accountId", account.getAccountId());
//			cr.add(companyValidation(accountDO.getCompanyId()));
			result = query.executeUpdate();
			if(0!=result) {
				ContactVO contact = new ContactVO();
				contact.setAccount(account);
				//contact.setDeleted(true);
				String deleteQuery1 = "UPDATE ContactVO C set C.isDeleted = :isDeleted WHERE C.account.accountId  = :accountId ";
				final Query query1 = getSession().createQuery(deleteQuery1);
				query1.setParameter("isDeleted", true);
				query1.setParameter("accountId", contact.getAccount().getAccountId());
				result1=query1.executeUpdate();
				
				return true;
			}
			/*
			 * if (result > 0) { return true; } else { return false; }
			 */
		} catch (final HibernateException he) {
			if (AccountsDaoImpl.LOGGER.isDebugEnabled()) {
				AccountsDaoImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}
		} finally {
			AccountsDaoImpl.LOGGER.exit();
		}
		return false;
	}

	private ContactVO contactVO() {
		return null;
	}

	@Override
	public boolean findByContactNo(long mobileNo) {
		AccountsDaoImpl.LOGGER.entry();
		List<AccountVO> account = null;
		try {

			Session session = getSession();
			Criteria criteria = session.createCriteria(AccountVO.class);
			criteria.add(Restrictions.eq("phone", mobileNo));
			account = (List<AccountVO>) criteria.list();
			if(null!=account && account.size()>0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking History failed:" + e.getMessage());

		} finally {
			AccountsDaoImpl.LOGGER.exit();
		}

		return false;
	}

	@Override
	public boolean findByEmailAccounts(String emailAddress) {
		AccountsDaoImpl.LOGGER.entry();
		AccountVO account = new AccountVO();
		Session session=sessionFactory.getCurrentSession();
		try {
			Criteria cr=session.createCriteria(AccountVO.class);
			cr.add(Restrictions.eq("email", emailAddress));
			account=(AccountVO)cr.uniqueResult();
			if(null!=account.getEmail()&&!account.getEmail().isEmpty()) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("leads findbyemail has failed:" + ex.getMessage());
			}
			LOGGER.info(" leads findbyemail has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public boolean checkemail(String emails,long companyId) {
		LOGGER.entry();
		List<AccountVO> accounts = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(AccountVO.class);
			criteria.add(Restrictions.eq("email", emails));
			criteria.add(Restrictions.eq("isDeleted", false));
			criteria.add(Restrictions.eq("companyId", companyId));
			accounts = (List<AccountVO>) criteria.list();

			if (null != accounts && accounts.size() > 0) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public boolean checkcontact(String contact,long companyId) {
		LOGGER.entry();
		List<AccountVO> accounts = null;
		long mobileNumber=Long.parseLong(contact);
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(AccountVO.class);
			criteria.add(Restrictions.eq("phone", mobileNumber));
			criteria.add(Restrictions.eq("isDeleted", false));
			criteria.add(Restrictions.eq("companyId", companyId));
			accounts = (List<AccountVO>) criteria.list();

			if (null != accounts && accounts.size() > 0) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	

	@Override
	public List<AccountVO> findAllList(AccountBO accountBO) {
		// TODO Auto-generated method stub
		AccountsDaoImpl.LOGGER.entry();
		List<AccountVO> accountlist=new ArrayList<AccountVO>();
		try{
			
			Criteria cr = getSession().createCriteria(AccountVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			if(null != accountBO.getCompanyId()&& 0< accountBO.getCompanyId()) {
			cr.add(companyValidation(accountBO.getCompanyId()));
			}
			cr.setFirstResult(accountBO.getRecordIndex());
		    cr.setMaxResults(accountBO.getMaxRecord());
		    accountlist=cr.list();
			
		}	
		catch(Exception ex){
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Retrieve account has failed:" + ex.getMessage());
			
			LOGGER.info("Retrieve account has failed:" + ex.getMessage());
			}
		
	}	finally {
		AccountsDaoImpl.LOGGER.exit();
	}
		return accountlist;
	}
	}	

	/*@Override
	public Map<Long, String> retrieveOpportunity() throws MySalesException {
		AccountsDaoImpl.LOGGER.entry();
		 Map<Long, String>  OpportunityList = new HashMap<Long, String>();
		try {
			Criteria cr = getSession().createCriteria(Opportunity.class);
			cr.add(Restrictions.eq("isDelete", false));
			Projection pro= Projections.property("opportunityId");
			Projection pro1= Projections.property("firstName");
			ProjectionList pList = Projections.projectionList();
			pList.add(pro);
			pList.add(pro1);
			cr.setProjection(pList);
			
			List<Object[]> keyValues = new ArrayList<Object[]>();
			keyValues=cr.list();
			if(null !=keyValues && !keyValues.isEmpty()) {
			for(Object[] obj :keyValues) {
				Long id= (Long) obj[0];
				String name= (String) obj[1];
				OpportunityList.put(id, name);
			}
			}
			
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AccountsDaoImpl.LOGGER.isDebugEnabled()) {
				AccountsDaoImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			AccountsDaoImpl.LOGGER.exit();
		}
		return OpportunityList;
	
	}*/

