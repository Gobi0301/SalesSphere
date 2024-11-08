package com.scube.crm.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.ClientBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.utils.ErrorCodes;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.Customer;
import com.scube.crm.vo.EmailAccess;
import com.scube.crm.vo.FollowUp;
import com.scube.crm.vo.GstVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.SalesOrderVO;
import com.scube.crm.vo.User;

/**
 * Owner : Scube Technologies Created Date: Nov-22-2014 Created by : Vinoth P
 * Description : JobSeekerDAOImpl is a Class which is responsible for storing
 * the data into the database Reviewed by :
 * 
 * 
 */

@Repository("customerDAOImpl")
public class CustomerDAOImpl extends BaseDao implements CustomerDAO {


	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {

		return sessionFactory.getCurrentSession();

	}

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(CustomerDAOImpl.class);

	@Override
	public User authendicate(String string, String emailAddress) throws MySalesException {
		CustomerDAOImpl.LOGGER.entry();
		User user = null;
		final String loginQuery = "FROM User E WHERE E.emailAddress = :emailAddress";
		try {
			Session session = getSession();
			final Query query = session.createQuery(loginQuery);
			session.flush();
			query.setParameter("emailAddress", emailAddress);
			if (null != query.list() && query.list().size() > 0) {
				user = (User) query.list().get(0);
			}

		} catch (final HibernateException he) {
			he.printStackTrace();
			if (CustomerDAOImpl.LOGGER.isDebugEnabled()) {
				CustomerDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			CustomerDAOImpl.LOGGER.exit();
		}

		return user;

	}

	@Override
	public LoginStatusVO editLoginStatus(LoginStatusVO loginStatusVO) {

		Criteria criteria = getSession().createCriteria(LoginStatusVO.class);
		criteria.add(Restrictions.eq("userName", loginStatusVO.getUserName()));
		criteria.add(Restrictions.eq("status", true));
		if (null != criteria.list() && criteria.list().size() > 0) {
			LoginStatusVO statusvo = (LoginStatusVO) criteria.list().get(0);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			Date date = cal.getTime();
			statusvo.setEndTime(date);
			statusvo.setStatus(false);
			statusvo.setActivity("logout");
			getSession().update(statusvo);
			return statusvo;
		}
		return new LoginStatusVO();
	}

	@Override
	public long addLoginStatus(LoginStatusVO loginStatus) {

		long id = 0;
		try {

			Session session = getSession();
			session.saveOrUpdate(loginStatus);
			session.flush();
		} catch (Exception he) {
			he.printStackTrace();
			LOGGER.debug(he.getMessage() + he);
		}

		return id;
	}

	@Override
	public boolean createAccessLog(AccessLogVO logVO) {

		CustomerDAOImpl.LOGGER.entry();
		boolean accessLogStatus = false;
		try {
			Session session = getSession();
			long logId = (Long) session.save(logVO);
			session.flush();
			if (0 != logId) {
				accessLogStatus = true;
			}
		} catch (HibernateException he) {
			he.printStackTrace();

		} finally {
			CustomerDAOImpl.LOGGER.exit();
		}

		return accessLogStatus;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scube.crm.dao.AdminDAO#createuser(com.scube.crm.vo.AdminUserVO)
	 */
	@Override
	public long createuser(User adminVO) {
		CustomerDAOImpl.LOGGER.entry();

		try {
			Session session = getSession();
			long logId = (Long) session.save(adminVO);
			session.flush();
			if (0 != logId) {
				return logId;
			}
		} catch (HibernateException he) {
			he.printStackTrace();

		} finally {
			CustomerDAOImpl.LOGGER.exit();
		}
		return 0;
	}


	@Override
	public User updateuser(User loginVO) throws MySalesException {

		CustomerDAOImpl.LOGGER.entry();
		try {
			Session session = getSession();
			session.saveOrUpdate(loginVO);
			getSession().flush();
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (CustomerDAOImpl.LOGGER.isDebugEnabled()) {
				CustomerDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_UPDATE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_UPDATE_FAIL, ErrorCodes.ENTITY_UPDATE_FAIL_MSG);
		} finally {
			CustomerDAOImpl.LOGGER.exit();
		}
		return loginVO;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scube.crm.dao.AdminDAO#userStatus(com.scube.crm.vo.AdminUserVO)
	 */
	@Override
	public User userStatus(User userVO) {
		final String changeLoginQuery = "UPDATE User E set E.isActive = :isActive WHERE E.id= :id";
		try {
			// session = getSession();
			final Query query = getSession().createQuery(changeLoginQuery);
			query.setParameter("isActive", userVO.isActive());
			query.setParameter("id", userVO.getId());
			final int result = query.executeUpdate();
			if (0 != result) {
				return userVO;
			}
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (CustomerDAOImpl.LOGGER.isDebugEnabled()) {
				CustomerDAOImpl.LOGGER.debug(he.getMessage() + he);
			}
		} finally {

			CustomerDAOImpl.LOGGER.exit();
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scube.crm.dao.AdminDAO#DeleteProfile(com.scube.crm.vo.AdminUserVO)
	 */
	@Override
	public int deleteCustomer(Customer customer) {
		CustomerDAOImpl.LOGGER.entry();
		int result = 0;

		final String deleteQuery = "UPDATE Customer S set" + " S.isDelete = :isDelete" + " WHERE S.id = :id";
		try {

			final Query query = getSession().createQuery(deleteQuery);
			query.setParameter("isDelete", customer.getIsDelete());
			query.setParameter("id", customer.getId());
			result = query.executeUpdate();
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (CustomerDAOImpl.LOGGER.isDebugEnabled()) {
				CustomerDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}
		}
		return result;
	}

	@Override
	public User getuserId(AdminUserBO loginBO) {
		Session session = getSession();

		User user = (User) session.get(User.class, loginBO.getId());
		return user;
	}

	@Override
	public boolean findEmployerEmail(String emailAddress) {
		try {
			Customer customerVO = new Customer();
			Session session = getSession();
			Criteria criteria = session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("emailAddress", emailAddress));
//			if(null != customer.getCompanyId()&& 0< customer.getCompanyId()) {
//				cr.add(companyValidation(customer.getCompanyId()));
//				}
			
			if (null != criteria.list() && criteria.list().size() > 0) {
				customerVO = (Customer) criteria.list().get(0);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking History failed:" + e.getMessage());

		} finally {
			CustomerDAOImpl.LOGGER.exit();
		}
		return false;
		/* List<Customer> employerRegisteration = criteria.list(); */
		/*
		 * long emailvalidation = 0; boolean validation; if (null !=
		 * employerRegisteration) { emailvalidation = employerRegisteration.size(); } if
		 * (emailvalidation != 0) { validation = true; return validation; } else {
		 * validation = false; return validation; }
		 */

	}

	@Override
	public List<String> getImageName() {
		List<String> imageNameList = new ArrayList<String>();
		try {
			String imageName = null;
			String sqlQuery = "SELECT imageName,companiesId FROM company WHERE isDeleted = false and adminChecked=true"
					+ " ORDER BY rank ASC LIMIT 60";
			Query query = getSession().createSQLQuery(sqlQuery);
			List<Object[]> rows = query.list();
			if (null != rows && !rows.isEmpty()) {
				for (Object[] obj : rows) {
					if (null != obj) {
						imageName = obj[0].toString() + "," + obj[1];
					} else {
						imageName = "companylogo.jpg";
					}

					if (null != imageName && !imageName.isEmpty()) {
						imageNameList.add(imageName);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return imageNameList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scube.crm.dao.AdminDAO#saveEmailList(java.util.List)
	 */
	@Override
	public EmailAccess saveEmailList(List<EmailAccess> accessVOList) {
		CustomerDAOImpl.LOGGER.entry();
		EmailAccess accessVO = new EmailAccess();
		try {
			long sendId = 0;
			Session session = getSession();
			for (EmailAccess emailaccessVO : accessVOList) {
				sendId = (Long) session.save(emailaccessVO);
				session.flush();
				session.clear();
			}
			accessVO.setSendId(sendId);
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Email Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("Email Tracking History failed:" + e.getMessage());

		} finally {
			CustomerDAOImpl.LOGGER.exit();
		}
		return accessVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scube.crm.dao.AdminDAO#mobileNoVerification(java.lang.String)
	 */
	@Override
	public boolean mobileNoVerification(String mobileNo) {
		Customer employerVO = null;
		try {

			Session session = getSession();
			Criteria criteria = session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("mobileNumber", mobileNo));
			/*
			 * long recordCount= (long) criteria.uniqueResult(); if(0!=recordCount ) {
			 * return true; }
			 */
			if (null != criteria.list() && criteria.list().size() > 0) {
				employerVO = (Customer) criteria.list().get(0);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking History failed:" + e.getMessage());

		} finally {
			CustomerDAOImpl.LOGGER.exit();
		}
		return false;
	}

	@Override
	public Customer createCustomer(Customer Customer) {
		LOGGER.entry();
		try
		{
			Session session = getSession();
			session.saveOrUpdate(Customer);
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Customer creation failed:" + e.getMessage());
			}
			LOGGER.info("Customer creation failed:" + e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return Customer;
	}

	@Override
	public ClientBO retriveCustomerById(ClientBO registerBO) throws MySalesException, SQLException {
		LOGGER.entry();
		ClientBO clientBO = new ClientBO();
		try {
			long sno = 1;
			List<Customer> employerList = new ArrayList<Customer>();
			Session session = getSession();
			final Criteria cr = session.createCriteria(Customer.class);
			if (0 != registerBO.getId()) {
				/*
				 * Customer employerVO=(Customer)
				 * session.get(Customer.class,clientBO.getId(),new
				 * LockOptions(LockMode.PESSIMISTIC_WRITE));
				 */
				cr.add((Restrictions.eq("id", registerBO.getId())));
				Customer employerVO = (Customer) cr.uniqueResult();
				employerList.add(employerVO);
			} else {
				if (null != registerBO.getPagination() && !registerBO.getPagination().isEmpty()) {
					cr.setFirstResult(registerBO.getRecordIndex());
					cr.setMaxResults(registerBO.getMaxRecord());
				}
				employerList = cr.list();
			}

			if (null != employerList && 0 != employerList.size() && null != employerList) {

				for (Customer profileVO : employerList) {

					registerBO = new ClientBO();
					clientBO.setsNo(sno++);
					clientBO.setCreatedDate(format.format(new Date()));
					clientBO.setCreated(profileVO.getCreated());
					clientBO.setId(profileVO.getId());
					if (null != profileVO.getFirstName()) {
						clientBO.setFirstName(profileVO.getFirstName());
					}
					if (null != profileVO.getEmailAddress()) {
						clientBO.setEmailAddress(profileVO.getEmailAddress());
					}
					if (null != profileVO.getLastName()) {
						clientBO.setLastName(profileVO.getLastName());
					}
					/*
					 * if(null !=profileVO.getPassword()){
					 * registerBO.setPassword(profileVO.getPassword()); }
					 */
					/*
					 * if(null !=profileVO.getConfirmPassword()){
					 * registerBO.setConfirmPassword(profileVO.getConfirmPassword()); }
					 */
					if (null != profileVO.getMobileNumber()) {
						clientBO.setMobileNo(profileVO.getMobileNumber());
					}
					if (null != profileVO.getContactNumber()) {
						clientBO.setContactNo(profileVO.getContactNumber());
					}
					/*
					 * if(null !=profileVO.getConfirmEmailAddress()){
					 * registerBO.setConfirmEmailAddress(profileVO.getConfirmEmailAddress()); }
					 */
					if (null != profileVO.getWebSite()) {
						clientBO.setWebsite(profileVO.getWebSite());
					}
					if (null != profileVO.getCompanyName()) {
						clientBO.setCompanyName(profileVO.getCompanyName());
					}
					if (null != profileVO.getIndustryType()) {
						clientBO.setIndustryType(profileVO.getIndustryType());
					}
					if (0 != profileVO.getId()) {
						clientBO.setsNo(profileVO.getId());
					}
					if (null != profileVO.getAddress()) {
						clientBO.setAddress(profileVO.getAddress());
					}
					if (null != profileVO.getStatus()) {
						clientBO.setStatus(profileVO.getStatus());
					}

					if (null != profileVO.getLoginVO()) {
						AdminLoginBO adminLoginBO = new AdminLoginBO();
						adminLoginBO.setId(profileVO.getLoginVO().getId());
						adminLoginBO.setFirstName(profileVO.getLoginVO().getName());
						clientBO.setUserId(profileVO.getLoginVO().getId());
						clientBO.setUserName(profileVO.getLoginVO().getName());
						clientBO.setLoginBO(adminLoginBO);
					}
					List<InventoryBO> productServiceBOList=new ArrayList<>();
					if(null!=profileVO) {
						for (Customer customer : employerList) {
							for (InventoryVO serviceVO : customer.getProductServiceVO()) {
								InventoryBO productServiceBO=new InventoryBO();
								productServiceBO.setServiceId(serviceVO.getServiceId());
								productServiceBO.setServiceName(serviceVO.getServiceName());
								productServiceBOList.add(productServiceBO);
								clientBO.setProductServiceList(productServiceBOList);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieve ById has failed:" + ex.getMessage());
			}
			LOGGER.info("Retrieve ById has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return clientBO;
	}

	@Override
	public List<ClientBO> retriveCustomer(ClientBO clientBO) {
		LOGGER.entry();
		Customer customer=new Customer();
		if(null != clientBO.getCompanyId()&& 0< clientBO.getCompanyId() ) {
		customer.setCompanyId(clientBO.getCompanyId());
		}
		ClientBO profileBO = new ClientBO();
		ClientBO clientRes = new ClientBO();
		List<ClientBO> customerList = new ArrayList<ClientBO>();
		try {
			List<Customer> employerProfilesList = new ArrayList<Customer>();
			Session session = getSession();
			final Criteria cr = session.createCriteria(Customer.class);
			
			if(null != customer.getCompanyId()&& 0< customer.getCompanyId()) {
			cr.add(companyValidation(customer.getCompanyId()));
			}
			if (null != clientBO.getFirstName() && !clientBO.getFirstName().isEmpty()) {
				cr.add(Restrictions.ilike("firstName",clientBO.getFirstName().trim(),MatchMode.ANYWHERE));
			}
			  if(null!=clientBO.getMobileNo() && !clientBO.getMobileNo().isEmpty()){
			  cr.add(Restrictions.ilike("mobileNumber",clientBO.getMobileNo().trim(),MatchMode.ANYWHERE)); 
			  }
			if(null !=clientBO.getEmailAddress() && !clientBO.getEmailAddress().isEmpty()){
				cr.add(Restrictions.ilike("emailAddress",clientBO.getEmailAddress().trim(),MatchMode.ANYWHERE));
			}
			if(null !=clientBO.getCompanyName() && !clientBO.getCompanyName().isEmpty()){
				cr.add(Restrictions.ilike("companyName",clientBO.getCompanyName().trim(),MatchMode.ANYWHERE));
			}
			if(null !=clientBO.getIndustryType() && !clientBO.getIndustryType().isEmpty()){
				cr.add(Restrictions.ilike("industryType",clientBO.getIndustryType().trim(),MatchMode.ANYWHERE));
			}

			//user based on reterive customer
			if(null!= clientBO.getAssignedTo()){
				cr.add(Restrictions.eq("loginVO.id",clientBO.getAssignedTo())); 
			}

			//created By 

			if(0<clientBO.getCreatedBy() && (null!=clientBO.getStarDate() && null!=clientBO.getEndDate())){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startDate=df.format(clientBO.getStarDate());
				String endDate=df.format(clientBO.getEndDate());
				Date fromDate = df.parse(startDate);
				Date toDate = df.parse(endDate);
				if(fromDate.equals(toDate)){
					cr.add(Restrictions.eq("created",fromDate));

				}else{
					cr.add(Restrictions.between("created",fromDate,toDate));	
				}
				cr.add(Restrictions.eq("createdBy",clientBO.getCreatedBy()));
			}
			//start date created 
			else if(0<clientBO.getCreatedBy() && null!=clientBO.getStarDate()  
					&& clientBO.getProcess().equalsIgnoreCase("create") ){
				cr.add(Restrictions.eq("createdBy",clientBO.getCreatedBy()));
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startDate=df.format(clientBO.getStarDate());
				Date fromDate = df.parse(startDate);
				if(null!=fromDate ) {
					cr.add(Restrictions.eq("created",clientBO.getStarDate()));
				}
			}
			//end date created 
			else if(0<clientBO.getCreatedBy() && null!=clientBO.getEndDate()  
					&& clientBO.getProcess().equalsIgnoreCase("create") ){
				cr.add(Restrictions.eq("createdBy",clientBO.getCreatedBy()));
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String endDate=df.format(clientBO.getEndDate());
				Date toDate = df.parse(endDate);
				if(null!=toDate ) {
					cr.add(Restrictions.eq("created",clientBO.getEndDate()));
				}
			}
			if(0!=clientBO.getModifiedBy() && (null!=clientBO.getStarDate() && null!=clientBO.getEndDate())){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startDate=df.format(clientBO.getStarDate());
				String endDate=df.format(clientBO.getEndDate());
				Date fromDate = df.parse(startDate);
				Date toDate = df.parse(endDate);
				if(fromDate.equals(toDate)){

					cr.add(Restrictions.eq("modified",fromDate));	
				}else{
					cr.add(Restrictions.between("modified",fromDate,toDate));
				}
				cr.add(Restrictions.eq("modifiedBy",clientBO.getModifiedBy()));

			}
			//end date modified edit
			else if(0<clientBO.getModifiedBy() && null!=clientBO.getEndDate() 
					&& clientBO.getProcess().equalsIgnoreCase("edit")){
				cr.add(Restrictions.eq("modifiedBy",clientBO.getModifiedBy()));
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String endDate=df.format(clientBO.getEndDate());
				Date  toDate = df.parse(endDate);
				if(null!=toDate){
					cr.add(Restrictions.eq("modified", clientBO.getEndDate()));	
				}
			}

			if(0!=clientBO.getAdminId() && (0==clientBO.getModifiedBy()) && (0==clientBO.getCreatedBy())){
				cr.add(Restrictions.or(Restrictions.eq("createdBy",clientBO.getAdminId())
						,Restrictions.eq("modifiedBy",clientBO.getAdminId()))); 

			} 

			if(null!=clientBO.getStarDate() && null!=clientBO.getEndDate() && clientBO.getProcess().equalsIgnoreCase("all")){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startDate=df.format(clientBO.getStarDate());
				String endDate=df.format(clientBO.getEndDate());
				Date fromDate = df.parse(startDate);
				Date toDate = df.parse(endDate);

				if(fromDate.equals(toDate)){
					//cr.add(Restrictions.ge("created",clientBO.getStarDate()));
					cr.add(Restrictions.eq("created", clientBO.getEndDate()));	
				}
				else if(null!=clientBO.getStarDate() && null!=clientBO.getEndDate() && clientBO.getProcess().equalsIgnoreCase("create")){
					cr.add(Restrictions.between("created",fromDate,toDate));	
				}
				else if(null!=clientBO.getStarDate() && null!=clientBO.getEndDate() && clientBO.getProcess().equalsIgnoreCase("edit")) {
					cr.add(Restrictions.eq("created", clientBO.getEndDate()));	
				}
			}

			//start date all 
			if(null!=clientBO.getStarDate()  && clientBO.getProcess().equalsIgnoreCase("all")
					&&null==clientBO.getEndDate()){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startDate=df.format(clientBO.getStarDate());
				Date fromDate = df.parse(startDate);
				if(null!=fromDate ) {
					cr.add(Restrictions.eq("created",clientBO.getStarDate()));
				}
			}
			//end date  all
			else if(null!=clientBO.getEndDate() && clientBO.getProcess().equalsIgnoreCase("all")){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String endDate=df.format(clientBO.getEndDate());
				Date  toDate = df.parse(endDate);
				if(null!=toDate){
					cr.add(Restrictions.eq("created", clientBO.getEndDate()));	
				}
			}
			//start date modified 
			else if(0<clientBO.getModifiedBy() && null!=clientBO.getStarDate() 
					&& clientBO.getProcess().equalsIgnoreCase("edit")){
				cr.add(Restrictions.eq("modifiedBy",clientBO.getModifiedBy()));
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startDate=df.format(clientBO.getStarDate());
				Date  toDate = df.parse(startDate);
				if(null!=toDate){
					cr.add(Restrictions.eq("modified", clientBO.getStarDate()));	
				}
			}

			cr.add(Restrictions.eq("isDelete",false));
			cr.add(Restrictions.eq("isActive", true));
			
			cr.setFirstResult(clientBO.getRecordIndex());
			cr.setMaxResults(clientBO.getMaxRecord());
			cr.addOrder(Order.desc("id"));

			if (null!=clientBO.getLoginBO() && 1< clientBO.getLoginBO().getId()) {
				cr.add(Restrictions.eq("loginVO.id", clientBO.getLoginBO().getId())); 
			}
			employerProfilesList = cr.list();

			int sNo=clientBO.getRecordIndex();
			if (null != employerProfilesList &&  employerProfilesList.size()>0) {

				for (Customer profileVO : employerProfilesList) {
					profileBO = new ClientBO();
					profileBO.setsNo(++sNo);
					if(null!= profileVO && null!= profileVO
							.getCreated()) {
						profileBO.setCreatedDate(format.format(profileVO
								.getCreated()));
					}
					if(null!= profileVO && null!= profileVO
							.getModified()) {
						
						
						profileBO.setModifiedDate(format.format(profileVO.getModified()));
					}
					profileBO.setCreated(profileVO.getCreated());
					profileBO.setId(profileVO.getId());
					if(null !=profileVO.getFirstName()){
						profileBO.setFirstName(profileVO.getFirstName());
					}
					if(null !=profileVO.getEmailAddress()){
						profileBO.setEmailAddress(profileVO.getEmailAddress());
					}
					if(null !=profileVO.getMobileNumber()){
						profileBO.setMobileNo(profileVO.getMobileNumber());
					}
					if(null !=profileVO.getCompanyName()){
						profileBO.setCompanyName(profileVO.getCompanyName());
					}
					if(null !=profileVO.getAddress()){
						profileBO.setAddress(profileVO.getAddress());
					}
					if(null !=profileVO.getContactNumber()){
						profileBO.setContactNo(profileVO.getContactNumber());
					}
					if(null !=profileVO.getWebSite()){
						profileBO.setWebsite(profileVO.getWebSite());
					}
					if(null !=profileVO.getIndustryType()){
						profileBO.setIndustryType(profileVO.getIndustryType());
					}
					if(null !=profileVO.getStatus()){
						profileBO.setStatus(profileVO.getStatus());
					}

					if(null!=profileVO.getLoginVO()) {
						AdminUserBO adminUserBO=new AdminUserBO();
						adminUserBO.setName(profileVO.getLoginVO().getName());
						adminUserBO.setUserId(profileVO.getLoginVO().getId());
						profileBO.setAdminUserBO(adminUserBO);

					}
					if(null!=profileVO) {
						for (InventoryVO productVO : profileVO.getProductServiceVO()) {
							List<InventoryBO> productBOList=new ArrayList<>();
							InventoryBO productBO=new InventoryBO();
							productBO.setServiceId(productVO.getServiceId());
							productBO.setServiceName(productVO.getServiceName());
							productBOList.add(productBO);
							profileBO.setProductServiceList(productBOList);
						}
					}
					profileBO.setCreatedBy(profileVO.getCreatedBy());
					profileBO.setModifiedBy(profileVO.getModifiedBy());

					customerList.add(profileBO);					
				}
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("View Customer has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return customerList;
	}
	@Override
	public Customer getuserId(ClientBO registerBO) {

		return null;
	}

	@Override
	public Customer updateEmployer(Customer employerVO) {
		CustomerDAOImpl.LOGGER.entry();
		try {
			Session session = getSession();
			session.saveOrUpdate(employerVO);
			getSession().flush();
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (CustomerDAOImpl.LOGGER.isDebugEnabled()) {
				CustomerDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_UPDATE_FAIL + he);
			}

		}
		return employerVO;
	}

	@Override
	public Customer deleteProfile(Customer vo) throws MySalesException {
		LOGGER.entry();
		Session session = getSession();
		Customer employerVO = (Customer) session.get(Customer.class, vo.getId());

		try {
			employerVO.setIsDelete(true);
			employerVO.setIsActive(false);
			session.saveOrUpdate(employerVO);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Customer has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
			return employerVO;

	}

	@Override
	public FollowUp saveTracking(FollowUp vO) {
		LOGGER.entry();
		try {
			Session session = getSession();
			long id = (long) session.save(vO);
			vO.setUpdateid(id);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("FollowUp Tracking create has failed:" + ex.getMessage());
			}
			LOGGER.info("FollowUp Tracking create has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return vO;
	}

	@Override
	public List<FollowUp> retrieveTracking(long customerId) {
		LOGGER.entry();
		List<FollowUp> trackingList = null;
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(FollowUp.class);
			criteria.add(Restrictions.eq("customer.id", customerId));
			if (null != criteria.list() && criteria.list().size() > 0) {
				trackingList = criteria.list();
			}
		} catch (Exception e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking History failed:" + e.getMessage());

		}
		return trackingList;
	}

	@Override
	public List<FollowUp> searchRetrieveTracking(FollowUp leadsFollowup) {
		List<FollowUp> leadsFollowupList=new ArrayList<FollowUp>();
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(FollowUp.class);
			//criteria.add(Restrictions.eq("isDelete", false));
			if (null != leadsFollowup && 1< leadsFollowup.getUser().getId()) {
				criteria.add(Restrictions.eq("user.id", leadsFollowup.getUser().getId()));
			}
			//start date created 
			if (null != leadsFollowup.getCreated() ) {
				criteria.add(Restrictions.eq("created",leadsFollowup.getCreated()));
			}
			if (null != leadsFollowup.getModified() ) {
				criteria.add(Restrictions.eq("modified",leadsFollowup.getModified()));
			}
			leadsFollowupList = criteria.list();
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking History failed:" + e.getMessage());

		}
		return leadsFollowupList;
	}
	/*
	 * public List<ProductServiceVO> getProductList(){ List<ProductServiceVO>
	 * productServiceVO=new ArrayList<>(); try {
	 * 
	 * Session session = getSession(); Criteria criteria =
	 * session.createCriteria(ProductServiceVO.class);
	 * productServiceVO=criteria.list(); if (null != productServiceVO
	 * &&!productServiceVO.isEmpty()&&productServiceVO.size()>0) { productServiceVO
	 * = criteria.list();
	 * 
	 * } } catch (Exception e) { e.printStackTrace(); if (LOGGER.isDebugEnabled()) {
	 * LOGGER.debug("Product List failed:"+ e.getMessage()); }
	 * LOGGER.info("Product List failed:"+ e.getMessage());
	 * 
	 * } return productServiceVO; }
	 */

	public InventoryVO getProductPrice(long productId) {
		InventoryVO productServiceVO=new InventoryVO();
		try {

			Session session = getSession();
			Criteria criteria = session.createCriteria(InventoryVO.class);
			criteria.add(Restrictions.eq("serviceId", productId));
			productServiceVO=(InventoryVO) criteria.uniqueResult();
			if (null != productServiceVO) {
				return productServiceVO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Product List failed:"+ e.getMessage());
			}
			LOGGER.info("Product List failed:"+ e.getMessage());

		} 
		return productServiceVO;
	}

	public List<InventoryVO> getProductList(){
		List<InventoryVO> productServiceVO=new ArrayList<>();
		try {

			Session session = getSession();
			Criteria criteria = session.createCriteria(InventoryVO.class);
			criteria.add(Restrictions.eq("isDelete", false));
			productServiceVO=criteria.list();
			if (null != productServiceVO &&!productServiceVO.isEmpty()&&productServiceVO.size()>0) {
				productServiceVO = criteria.list();

			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Product List failed:"+ e.getMessage());
			}
			LOGGER.info("Product List failed:"+ e.getMessage());

		} 
		return productServiceVO;
	}

	/*
	 * public ProductServiceVO getProductPrice(long productId) { ProductServiceVO
	 * productServiceVO=new ProductServiceVO(); try {
	 * 
	 * Session session = getSession(); Criteria criteria =
	 * session.createCriteria(ProductServiceVO.class);
	 * criteria.add(Restrictions.eq("serviceId", productId));
	 * productServiceVO=(ProductServiceVO) criteria.uniqueResult(); if (null !=
	 * productServiceVO) { return productServiceVO; } } catch (Exception e) {
	 * e.printStackTrace(); if (LOGGER.isDebugEnabled()) {
	 * LOGGER.debug("Product List failed:"+ e.getMessage()); }
	 * LOGGER.info("Product List failed:"+ e.getMessage());
	 * 
	 * } return productServiceVO; }
	 */

	public GstVO getGstValues() {
		List<GstVO> gstVO=new ArrayList<>();
		GstVO gstObj=new GstVO();
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(GstVO.class);
			criteria.addOrder(Order.desc("gstId"));
			gstVO=(List<GstVO>) criteria.list();
			if (null != gstVO && gstVO.size() > 0&&!gstVO.isEmpty()) {
				gstObj=gstVO.get(0);
			}
			return gstObj;}
		catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Gst List failed:"+ e.getMessage());
			}
			LOGGER.info("Gst List failed:"+ e.getMessage());

		} 
		return gstObj;
	}
	public long getProductServiceId(String serviceName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(InventoryVO.class);
		criteria.add(Restrictions.ilike("serviceName",serviceName,MatchMode.ANYWHERE));
		criteria.uniqueResult();
		return 0;

	}
	public long createSalesOrder(SalesOrderVO salesOrderVO) {
		CustomerDAOImpl.LOGGER.entry();
		long status = 0;
		try {
			Session sessions = sessionFactory.openSession();
			if(salesOrderVO != null) {
				 status=(long) sessions.save(salesOrderVO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			CustomerDAOImpl.LOGGER.exit();
		}
		return status;
	}

	public String getSalesOrderNo() {
		
		SalesOrderVO salesVO=null;
		String sNo="SONo1";
		Session session = getSession();
		Criteria criteria = session.createCriteria(SalesOrderVO.class);
		criteria.addOrder(Order.desc("salesOrderId"));
		List<SalesOrderVO> salesOrderVO = criteria.list();
		if(salesOrderVO !=null && !salesOrderVO.isEmpty() && salesOrderVO.size()!=0) {
			salesVO = salesOrderVO.get(0);
			if (null != salesVO&&null!=salesVO.getSalesOrderNo() &&!salesVO.equals(null)) {
				long add=1;
				String ss=salesVO.getSalesOrderNo();
				String s[]=	ss.split("o");
				String val=s[1];
				long merge=Long.parseLong(val)+add;
				sNo="SONo"+String.valueOf(merge);
				return sNo;
			}
		}

		return sNo;

	}

	public List<Customer> retriveClients() {

		List<Customer> customerVOList= new ArrayList<Customer>();

		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("isDelete" , false));
			customerVOList=criteria.list();
		  if(null!=customerVOList && !customerVOList.isEmpty() && customerVOList.size()>0) {
			  return customerVOList;
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerVOList;

	}

	@Override
	public List<Long> retriveServiceByCustomerId(ClientBO reterive) {

		List<Long> serviceIds=new ArrayList<>();
     try {
		Session session=sessionFactory.getCurrentSession();
		Criteria cr=session.createCriteria(Customer.class);
		cr.createCriteria("productServiceVO", "productRef");
		cr.setProjection(Projections.groupProperty("productRef.serviceId"));
		cr.add(companyValidation(reterive.getCompanyId()));
		cr.add(Restrictions.eq("id", reterive.getId()));
		serviceIds=cr.list();
     }catch(DataException e) {
    	 e.printStackTrace();
     }
		return serviceIds;
	}

	@Override
	public long countOfCustomer(Customer customerVO) {
		LOGGER.entry();
		long countOfCustomer=0;
		try
		{
			Session session=sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(Customer.class);
			if(null != customerVO.getCompanyId()&& 0< customerVO.getCompanyId()) {
			criteria.add(companyValidation(customerVO.getCompanyId()));
			}
			criteria.add(Restrictions.eq("isDelete", customerVO.getIsDelete()));
			criteria.setProjection(Projections.rowCount());
			countOfCustomer=(long)criteria.uniqueResult();

		}
		catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Customer count failed:" + e.getMessage());
			}
			LOGGER.info("Customer count failed:" + e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return countOfCustomer;
	}

	@Override
	public List<Customer> listOfCustomerByPagination(Customer customerVO) {
		List<Customer> customerVOList=new ArrayList<>();
		// TODO Auto-generated method stub
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(Customer.class);
			
			criteria.add(Restrictions.eq("isDelete", customerVO.getIsDelete()));
			if(null != customerVO.getCompanyId()&& 0< customerVO.getCompanyId()) {
			criteria.add(companyValidation(customerVO.getCompanyId()));
			}
			criteria.setFirstResult(customerVO.getRecordIndex());
			criteria.setMaxResults(customerVO.getMaxRecord());
			criteria.addOrder(Order.desc("id"));
			criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
			customerVOList=criteria.list();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		return customerVOList;
	}

	@Override
	public long countOfCustomerBySearch(Customer customerVO) {
		// TODO Auto-generated method stub

		long countOfCustomer=0;
		Session session=sessionFactory.getCurrentSession();
		try {
			Criteria criteria=session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("isDelete", customerVO.getIsDelete()));
			if(null != customerVO.getCompanyId()&& 0< customerVO.getCompanyId()) {
			criteria.add(companyValidation(customerVO.getCompanyId()));  // CompanyId
			}
			if(null!=customerVO.getCompanyName()&&!customerVO.getCompanyName().isEmpty()) {
				criteria.add(Restrictions.ilike("companyName", customerVO.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if(null!=customerVO.getEmailAddress()&&!customerVO.getEmailAddress().isEmpty()) {
				criteria.add(Restrictions.ilike("emailAddress", customerVO.getEmailAddress().trim(), MatchMode.ANYWHERE));
			}
			if(null!=customerVO.getMobileNumber()&&!customerVO.getMobileNumber().isEmpty()) {
				criteria.add(Restrictions.ilike("mobileNumber", customerVO.getMobileNumber().trim(), MatchMode.ANYWHERE));
			}
			criteria.setProjection(Projections.rowCount());
			countOfCustomer=(long)criteria.uniqueResult();
		}
		catch (Exception e) {
			// TODO: handle exception
		}

		return countOfCustomer;
	}

	@Override
	public Customer getCustomerDetails(long clientId) throws MySalesException {
		CustomerDAOImpl.LOGGER.entry();
		Customer profile = new Customer();

		try {
			Session session=sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("id", clientId));
			criteria.add(Restrictions.eq("isDelete", false));
			profile=(Customer) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			CustomerDAOImpl.LOGGER.exit();
		}
		return profile;
	}

	@Override
	public List<Customer> getCustomerProfile(long id) {
		List<Customer> listVO=new ArrayList<Customer>();
		
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("id", id));
			 criteria.add(Restrictions.eq("isDelete", false));
			 listVO = criteria.list();
			
		}catch (Exception he) {
			he.printStackTrace();
		} 

			return listVO;
	 
		 
		 
		
	}

	@Override
	public Customer getProfile(long id) {
		
		
		return null;
	}

	@Override
	public boolean checkemail(String email,long id) {
		LOGGER.entry();
		List<Customer> customer = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("emailAddress", email));
			criteria.add(Restrictions.eq("createdBy", id));
			customer = (List<Customer>) criteria.list();

			if (null != customer && customer.size() > 0) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("checkEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
}

	@Override
	public boolean checkcontactNumber(String contact,long id) {
		LOGGER.entry();
		List<Customer> customer = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("contactNumber", contact)); 
			criteria.add(Restrictions.eq("createdBy", id));
			customer = (List<Customer>) criteria.list();

			if (null != customer && customer.size() > 0) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("cheakContactNumber has failed:" + ex.getMessage());
			}
			LOGGER.info("cheakContactNumber has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public boolean checkmobileNumber(String mobile,long id) {
		LOGGER.entry();
		List<Customer> customer = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("mobileNumber", mobile));
			criteria.add(Restrictions.eq("createdBy", id));
			customer = (List<Customer>) criteria.list();

			if (null != customer && customer.size() > 0) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkMobileNumber has failed:" + ex.getMessage());
			}
			LOGGER.info("checkMobileNumber has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}
}
	 
  