package com.scube.crm.dao;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.CampaignBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.TaskManagementBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.utils.ErrorCodes;
//import com.scube.crm.utils.ErrorCodes;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.Campaign;
import com.scube.crm.vo.Leads;
import com.scube.crm.vo.LeadsFollowup;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.TaskManagementVO;
import com.scube.crm.vo.User;

/**
 * Owner : Scube Technologies Created Date: Nov-22-2014 Created by : Vinoth P
 * Description : JobSeekerDAOImpl is a Class which is responsible for storing
 * the data into the database Reviewed by :
 * 
 * 
 */

@Repository("leadsDAOImpl")
public class LeadsDAOImpl extends BaseDao implements LeadsDAO {

	public LeadsDAOImpl() throws MySalesException {
		super();
	}

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(LeadsDAOImpl.class);

	// Leads create part
	@Override
	public long saveLeads(Leads leads) {
		LOGGER.entry();
		long id = 0;
		try {
			Session session = getSession();
			id = (long) session.save(leads);
			if (0 < id) {
				leads.setLeadsId(id);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Save lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Save lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return id;
	}

	// Leads View Part - count
	@Override
	public long getcountOfLeads(LeadsBO leadsVO) {
		LOGGER.entry();
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Leads.class);
			criteria.add(Restrictions.eq("isDelete", false));
			if(null != leadsVO.getCompanyId()&& 0< leadsVO.getCompanyId()) {
				criteria.add(companyValidation(leadsVO.getCompanyId())); // companyId
			}

			if (null != leadsVO.getFirstName() && !leadsVO.getFirstName().isEmpty()) {
				criteria.add(Restrictions.ilike("firstName", leadsVO.getFirstName(), MatchMode.ANYWHERE));
			}
			if (null != leadsVO.getEmailAddress() && !leadsVO.getEmailAddress().isEmpty()) {
				criteria.add(Restrictions.ilike("emailAddress", leadsVO.getEmailAddress(), MatchMode.ANYWHERE));
			}
			if (null != leadsVO.getMobileNo() && !leadsVO.getMobileNo().isEmpty()) {
				criteria.add(Restrictions.ilike("mobileNo", leadsVO.getMobileNo(), MatchMode.ANYWHERE));
			}
			if (null != leadsVO.getCampaignBO() && 0 < leadsVO.getCampaignBO().getCampaignId()) {
				criteria.add(Restrictions.eq("campaignVO.campaignId", leadsVO.getCampaignBO().getCampaignId()));
			}

			criteria.setProjection(Projections.rowCount());
			long countOfLeads = (long) criteria.uniqueResult();
			if (0 < countOfLeads) {
				return countOfLeads;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Count lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Count lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return 0;
	}

	// Leads View Part
	@Override
	public List<Leads> getListOfLeadsByPagination(Leads leadsVO) {
		LOGGER.entry();
		List<Leads> leadsVOList = new ArrayList<>();
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Leads.class);
			criteria.setFirstResult(leadsVO.getRecordIndex());
			criteria.setMaxResults(leadsVO.getMaxRecord());
			criteria.add(Restrictions.eq("isDelete", false));
			if(null != leadsVO.getCompanyId()&& 0< leadsVO.getCompanyId()) {
				criteria.add(companyValidation(leadsVO.getCompanyId())); // companyId
			}
			if (leadsVO != null) {
				if (null != leadsVO.getFirstName() && !leadsVO.getFirstName().isEmpty()) {
					criteria.add(Restrictions.ilike("firstName", leadsVO.getFirstName().trim(), MatchMode.ANYWHERE));
				}
				if (null != leadsVO.getEmailAddress() && !leadsVO.getEmailAddress().isEmpty()) {
					criteria.add(Restrictions.ilike("emailAddress", leadsVO.getEmailAddress().trim(), MatchMode.ANYWHERE));
				}
				if (null != leadsVO.getMobileNo() && !leadsVO.getMobileNo().isEmpty()) {
					criteria.add(Restrictions.ilike("mobileNo", leadsVO.getMobileNo().trim(), MatchMode.ANYWHERE));
				}
				if (null != leadsVO.getCampaignVO() && 0 < leadsVO.getCampaignVO().getCampaignId()) {
					criteria.add(Restrictions.eq("campaignVO.campaignId", leadsVO.getCampaignVO().getCampaignId()));
				}
			}
			criteria.addOrder(Order.desc("leadsId"));
			leadsVOList = criteria.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View lead has failed:" + ex.getMessage());
			}
			LOGGER.info("View lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return leadsVOList;
	}

	@Override
	public Leads getLeads(Leads leads) {
		LOGGER.entry();
		// Leads leads = new Leads();
		try {
			Criteria criteria = getSession().createCriteria(Leads.class);
			criteria.add(companyValidation(leads.getCompanyId())); // companyId
			criteria.add(Restrictions.eq("leadsId", leads.getLeadsId()));
			leads = (Leads) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Get lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return leads;
	}

	@Override
	public List<ActivityVO> retrieveTracking(long leadsId) {
		LOGGER.entry();
		List<ActivityVO> leadupdateList = null;
		try {
			Criteria criteria = getSession().createCriteria(ActivityVO.class);
			criteria.add(Restrictions.eq("entitytype", "Leads"));
			criteria.add(Restrictions.eq("entityid", leadsId));
			if (null != criteria.list() && criteria.list().size() > 0) {
				leadupdateList = criteria.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking History failed:" + e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return leadupdateList;
	}

	@Override
	public Leads getLeadsId(LeadsBO leadsBO) {
		Leads leads = (Leads) getSession().get(Leads.class, leadsBO.getLeadsId());
		return leads;
	}

	// Leads - delete Part
	@Override
	public boolean deleteLeads(long leadsid) {
		LOGGER.entry();
		final String deleteQuery = "UPDATE Leads S set" + " S.isDelete = :isDelete" + " WHERE S.leadsId = :leadsId";
		boolean status = false;
		try {
			int result = 0;
			final Query query = getSession().createQuery(deleteQuery);
			query.setParameter("isDelete", true);
			query.setParameter("leadsId", leadsid);
			result = query.executeUpdate();
			if (0 < result) {
				status = true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return status;
	}

	// Leads Search count part
	@Override
	public long countOfLeadsBySearch(Leads leadsVO) {
		LOGGER.entry();
		long countOfLeads = 0;
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Leads.class);
			criteria.add(Restrictions.eq("isDelete", false));
			if(null != leadsVO.getCompanyId()&& 0< leadsVO.getCompanyId()) {
				criteria.add(companyValidation(leadsVO.getCompanyId())); // companyId
			}

			if (null != leadsVO.getFirstName() && !leadsVO.getFirstName().isEmpty()) {
				criteria.add(Restrictions.ilike("firstName", leadsVO.getFirstName().trim(), MatchMode.ANYWHERE));
			}
			if (null != leadsVO.getLastName() && !leadsVO.getLastName().isEmpty()) {
				criteria.add(Restrictions.ilike("firstName", leadsVO.getLastName() .trim(), MatchMode.ANYWHERE));
			}
			if (null != leadsVO.getEmailAddress() && !leadsVO.getEmailAddress().isEmpty()) {
				criteria.add(Restrictions.ilike("emailAddress", leadsVO.getEmailAddress().trim(), MatchMode.ANYWHERE));
			}
			if (null != leadsVO.getMobileNo() && !leadsVO.getMobileNo().isEmpty()) {
				criteria.add(Restrictions.ilike("mobileNo", leadsVO.getMobileNo().trim(), MatchMode.ANYWHERE));
			}
			if (0 < leadsVO.getCampaignVO().getCampaignId()) {
				criteria.add(Restrictions.eq("campaignVO.campaignId", leadsVO.getCampaignVO().getCampaignId()));
			}

			criteria.setProjection(Projections.rowCount());
			countOfLeads = (long) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search lead count has failed:" + ex.getMessage());
			}
			LOGGER.info("Search lead count has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return countOfLeads;
	}

	// Leads Tracking status
	@Override
	public ActivityVO saveTracking(ActivityVO activityvo) {
		LOGGER.entry();
		try {
			Session session = getSession();
			long id = (long) session.save(activityvo);
			activityvo.setActivityid(id);
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking save failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking save failed:" + e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return activityvo;

	}

	@Override
	public User authendicate(String string, String emailAddress) throws MySalesException {
		LeadsDAOImpl.LOGGER.entry();
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
			if (LeadsDAOImpl.LOGGER.isDebugEnabled()) {
				LeadsDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			LeadsDAOImpl.LOGGER.exit();
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

		LeadsDAOImpl.LOGGER.entry();
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
			LeadsDAOImpl.LOGGER.exit();
		}

		return accessLogStatus;

	}

	@Override
	public List<AdminUserBO> retrieveUser() {
		LOGGER.entry();
		AdminUserBO adminuserBO = new AdminUserBO();
		List<AdminUserBO> BOList = new ArrayList<AdminUserBO>();
		List<User> VOList = new ArrayList<User>();
		try {
			int count = 1;
			Criteria cr = getSession().createCriteria(User.class);
			cr.add(Restrictions.eq("isDelete", false));
			if (null != adminuserBO.getName() && !adminuserBO.getName().isEmpty()) {
				cr.add(Restrictions.ilike("name", adminuserBO.getName(), MatchMode.ANYWHERE));
			}
			if (null != adminuserBO.getEmailAddress() && !adminuserBO.getEmailAddress().isEmpty()) {
				cr.add(Restrictions.ilike("emailAddress", adminuserBO.getEmailAddress(), MatchMode.ANYWHERE));
			}
			if (null != adminuserBO.getUserType() && !adminuserBO.getUserType().isEmpty()) {
				cr.add(Restrictions.ilike("userType", adminuserBO.getUserType(), MatchMode.ANYWHERE));
			}
			// cr.add(Restrictions.eq("isActive", true));
			cr.addOrder(Order.desc("created"));
			VOList = cr.list();

			if (null != VOList && !VOList.isEmpty()) {
				int data;
				for (User vo : VOList) {
					data = count++;
					adminuserBO = new AdminUserBO();
					adminuserBO.setId(vo.getId());
					adminuserBO.setActive(vo.isActive());
					adminuserBO.setsNo(data);
					adminuserBO.setName(vo.getName());
					adminuserBO.setMobileNo(vo.getMobileNo());
					// adminuserBO.setUserType(vo.getUserType());
					adminuserBO.setPassword(vo.getPassword());
					adminuserBO.setEmailAddress(vo.getEmailAddress());
					adminuserBO.setConfirmPassword(vo.getConfirmpassword());
					if (vo.isActive()) {
						adminuserBO.setStatus("Active");
					} else {
						adminuserBO.setStatus("De-Active");
					}
					BOList.add(adminuserBO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieve user has failed:" + ex.getMessage());
			}
			LOGGER.info("Retrieve user has failed:" + ex.getMessage());
		}
		return BOList;
	}

	@Override
	public List<Leads> getListLeads(LeadsBO leadsBO) {
		LOGGER.entry();

		Leads leads = new Leads();
		leads.setCompanyId(leadsBO.getCompanyId()); // companyID

		List<Leads> getList = new ArrayList<Leads>();
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(Leads.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.add(Restrictions.eq("convertedCustomer", false));
			if (null != leadsBO && 0 != leadsBO.getMaxRecord()) {
				criteria.setFirstResult(leadsBO.getRecordIndex());
				criteria.setMaxResults(leadsBO.getMaxRecord());
			}
			criteria.add(companyValidation(leads.getCompanyId())); // companyId base retriew
			if (0 < leadsBO.getLeadsId()) {
				criteria.add(Restrictions.eq("leadsId", leadsBO.getLeadsId()));
			}
			if (null != leadsBO.getAdminLoginBO() && 0 < leadsBO.getAdminLoginBO().getId()
					&& 0 < leadsBO.getLeadsId()) {
				criteria.add(Restrictions.eq("leadeOwner.id", leadsBO.getAdminLoginBO().getId()));
			}

			if (null != leadsBO.getFirstName() && !leadsBO.getFirstName().isEmpty()) {
				criteria.add(Restrictions.ilike("firstName", leadsBO.getFirstName(), MatchMode.ANYWHERE));
			}
			if (null != leadsBO.getEmailAddress() && !leadsBO.getEmailAddress().isEmpty()) {
				criteria.add(Restrictions.ilike("emailAddress", leadsBO.getEmailAddress(), MatchMode.ANYWHERE));
			}
			if (null != leadsBO.getMobileNo() && !leadsBO.getMobileNo().isEmpty()) {
				criteria.add(Restrictions.ilike("mobileNo", leadsBO.getMobileNo(), MatchMode.ANYWHERE));
			}

			/*
			 * if(null!=leadsBO.getAdminUserBO().getName()) {
			 * criteria.createCriteria("leadeOwner", "userVO");
			 * criteria.add(Restrictions.ilike("userVO.firstName",
			 * leadsBO.getAdminUserBO().getName(), MatchMode.ANYWHERE)); }
			 */
			if (null != leadsBO && null != leadsBO.getCampaignBO() && 0 < leadsBO.getCampaignBO().getCampaignId()) {
				criteria.add(Restrictions.eq("campaignVO.campaignId", leadsBO.getCampaignBO().getCampaignId()));
			}

			// Reports search
			// startdate search all
			if (null != leadsBO.getStartDate() && leadsBO.getProcess().equalsIgnoreCase("all")
					&& null == leadsBO.getEndDate()) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startDate = df.format(leadsBO.getStartDate());
				Date fromDate = df.parse(startDate);
				if (null != fromDate) {
					criteria.add(Restrictions.eq("created", leadsBO.getStartDate()));
				}
			}
			// startDate create process
			if (null != leadsBO.getStartDate() && leadsBO.getProcess().equalsIgnoreCase("create")
					&& null == leadsBO.getEndDate()) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startDate = df.format(leadsBO.getStartDate());
				Date fromDate = df.parse(startDate);
				if (null != fromDate) {
					criteria.add(Restrictions.eq("created", fromDate));
				}
			}
			// enddate search all
			if (null != leadsBO.getEndDate() && leadsBO.getProcess().equalsIgnoreCase("all")) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String toDate = df.format(leadsBO.getEndDate());
				Date fromDate = df.parse(toDate);
				if (null != fromDate) {
					criteria.add(Restrictions.eq("modified", fromDate));
				}
			}
			// enddate search process create
			if (null != leadsBO.getEndDate() && leadsBO.getProcess().equalsIgnoreCase("create")) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String toDate = df.format(leadsBO.getEndDate());
				Date fromDate = df.parse(toDate);
				if (null != fromDate) {
					criteria.add(Restrictions.eq("modified", fromDate));
				}
			}

			// startdate and enddate all

			if (null != leadsBO.getStartDate() && null != leadsBO.getEndDate()
					&& leadsBO.getProcess().equalsIgnoreCase("all")) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startDate = df.format(leadsBO.getStartDate());
				String endDate = df.format(leadsBO.getEndDate());
				Date fromDate = df.parse(startDate);
				Date toDate = df.parse(endDate);

				if (fromDate.equals(toDate)) {
					// cr.add(Restrictions.ge("created",clientBO.getStarDate()));
					criteria.add(Restrictions.eq("created", leadsBO.getEndDate()));
				}
			}

			if (1 < leadsBO.getUserId()) {
				criteria.add(Restrictions.eq("leadeOwner.id", leadsBO.getUserId()));
			}

			if (null != leadsBO.getAdminUserBO()) {
				criteria.createCriteria("leadeOwner", "userVO");
				criteria.add(Restrictions.ilike("userVO.name", leadsBO.getAdminUserBO().getName(), MatchMode.ANYWHERE));
			}

			criteria.addOrder(Order.desc("created"));
			//criteria.add(companyValidation(leadsBO.getCompanyId()));// company condition
			getList = criteria.list();
			if (null != getList && !getList.isEmpty()) {
				return getList;
			}
		}

		catch (final HibernateException he) {
			he.printStackTrace();
			if (LeadsDAOImpl.LOGGER.isDebugEnabled()) {
				LeadsDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Campaign> listOfCampaign(CampaignBO campaignBO) {

		List<Campaign> pagecampaignlist = new ArrayList<Campaign>();
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(Campaign.class);
			criteria.add(Restrictions.eq("isDelete", false));
			if (null != campaignBO && 1 < campaignBO.getAdminLoginBO().getId()) {
				criteria.add(Restrictions.eq("user.id", campaignBO.getAdminLoginBO().getId()));
			}
			/*
			 * if(null!=campaignBO.getAdminLoginBO().getName() &&
			 * !campaignBO.getAdminLoginBO().getName().isEmpty()) {
			 * criteria.add(Restrictions.ilike("name",
			 * campaignBO.getAdminLoginBO().getName(),MatchMode.ANYWHERE)); }
			 */
			if (null != campaignBO.getCampaignName() && !campaignBO.getCampaignName().isEmpty()) {
				criteria.add(Restrictions.ilike("campaignName", campaignBO.getCampaignName(), MatchMode.ANYWHERE));
			}
			if (null != campaignBO.getCampaignMode() && !campaignBO.getCampaignMode().isEmpty()) {
				criteria.add(Restrictions.ilike("campaignMode", campaignBO.getCampaignMode(), MatchMode.ANYWHERE));
			}
			if (1 < campaignBO.getUserId()) {
				criteria.add(Restrictions.eq("user.id", campaignBO.getUserId()));
			}
			pagecampaignlist = criteria.list();
		}

		catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking History failed:" + e.getMessage());

		}
		return pagecampaignlist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scube.crm.dao.AdminDAO#updateLead(com.scube.crm.vo.Leads)
	 */
	/*
	 * @Override public Leads getLeadsId(LeadsBO leadsBO) { Leads leads = (Leads)
	 * getSession().get(Leads.class, leadsBO.getLeadsId()); return leads; }
	 */
	// Leads - Edit Update Part
	@Override
	public boolean updateLead(Leads leads) {
		LOGGER.entry();
		try {
			Session session = getSession();
			session.update(leads);
			return true;
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Update lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scube.crm.dao.AdminDAO#deleteLeads(int)
	 */

	@Override
	public boolean findByEmailLeads(String emailAddress) {
		LeadsDAOImpl.LOGGER.entry();
		Leads leads = new Leads();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria cr = session.createCriteria(Leads.class);
			cr.add(Restrictions.eq("emailAddress", emailAddress));
			leads = (Leads) cr.uniqueResult();
			if (null!=leads && null != leads.getEmailAddress() && !leads.getEmailAddress().isEmpty()) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("leads findbyemail has failed:" + ex.getMessage());
			}
			LOGGER.info(" leads findbyemail has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public boolean findByMobilenoLeads(String mobileNo) {
		LOGGER.entry();
		List<Leads> leads = null;
		try {

			Session session = getSession();
			Criteria criteria = session.createCriteria(Leads.class);
			criteria.add(Restrictions.eq("mobileNo", mobileNo));
			leads = (List<Leads>) criteria.list();
			if (null != leads && leads.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking History failed:" + e.getMessage());

		} finally {
			LeadsDAOImpl.LOGGER.exit();
		}
		return false;
	}

	@Override
	public List<LeadsFollowup> searchRetrieveTracking(LeadsFollowup leadsFollowup) {
		LOGGER.entry();

		List<LeadsFollowup> leadsFollowupList = new ArrayList<LeadsFollowup>();
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(LeadsFollowup.class);
			// criteria.add(Restrictions.eq("isDelete", false));
			if (null != leadsFollowup && 1 < leadsFollowup.getUserVO().getId()) {
				criteria.add(Restrictions.eq("userVO.id", leadsFollowup.getUserVO().getId()));
			}
			// start date created
			if (null != leadsFollowup.getCreated()) {
				criteria.add(Restrictions.eq("created", leadsFollowup.getCreated()));
			}
			if (null != leadsFollowup.getModified()) {
				criteria.add(Restrictions.eq("modified", leadsFollowup.getModified()));
			}
			leadsFollowupList = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking History failed:" + e.getMessage());

		}
		return leadsFollowupList;
	}

	@Override
	public long getAnyAppointment(long leadsId) {
		LOGGER.entry();
		long count = 0;
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(LeadsFollowup.class);
			criteria.add(Restrictions.eq("leads.leadsId", leadsId));
			count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			if (0 < count) {
				return count;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No Leads Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("No Leads  Tracking History failed:" + e.getMessage());

		}
		return count;
	}

	@Override
	public long countOfLeadsReportBySearch(Leads leadsVO) {
		// TODO Auto-generated method stub

		long countOfLeads = 0;
		Session session = sessionFactory.getCurrentSession();
		try {

			Criteria criteria = session.createCriteria(Leads.class);
			criteria.add(Restrictions.eq("isDelete", leadsVO.getIsDelete()));
			criteria.setProjection(Projections.rowCount());
			countOfLeads = (long) criteria.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		return countOfLeads;
	}

	@Override
	public Boolean leadtoopprtunityconversationcheck(long id) {


		try {
			Criteria criteria = getSession().createCriteria(TaskManagementVO.class);
			criteria.add(Restrictions.eq("taskOwner.id", id));
			criteria.add(Restrictions.eq("status","Completed"));
			criteria.add(Restrictions.eq("isDelete", false));
			List<TaskManagementVO> retrivelist = criteria.list();
			if (null != retrivelist && retrivelist.size() > 0) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkEmailAddress(String emailAddress, long companyId) {
		LOGGER.entry();
		List<User> user = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Leads.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.add(Restrictions.eq("emailAddress", emailAddress));
			criteria.add(Restrictions.eq("companyId", companyId));
			user = (List<User>) criteria.list();

			if (null != user && user.size() > 0) {
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
	public List<Leads> viewLead(LeadsBO leadsBO) {
		List<Leads> leadsVOlist=new ArrayList<Leads>();
		try{
			
			Criteria cr = getSession().createCriteria(Leads.class);
			cr.add(Restrictions.eq("isDelete", false));
			if(null != leadsBO.getCompanyId()&& 0< leadsBO.getCompanyId()) {
			cr.add(companyValidation(leadsBO.getCompanyId()));
			}
			cr.setFirstResult(leadsBO.getRecordIndex());
		    cr.setMaxResults(leadsBO.getMaxRecord());
		    leadsVOlist=cr.list();
			
		}	
		catch(Exception ex){
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Retrieve account has failed:" + ex.getMessage());
			
			LOGGER.info("Retrieve account has failed:" + ex.getMessage());
			}
		
	}
		return leadsVOlist;

}

	}

	/*
	 * @Override public LeadsFollowup edittracking(long id) { // TODO Auto-generated
	 * method stub
	 * 
	 * LeadsFollowup leadsVoObj=new LeadsFollowup(); Session session=getSession();
	 * Criteria crit=session.createCriteria(LeadsFollowup.class);
	 * crit.add(Restrictions.eq("leadsupdateid", id));
	 * leadsVoObj=(LeadsFollowup)crit.uniqueResult();
	 * 
	 * return leadsVoObj; }
	 */
	/*
	 * @Override public LeadsFollowup postedittrack(LeadsFollowup leadsVOobj) { //
	 * TODO Auto-generated method stub
	 * 
	 * Session session=sessionFactory.openSession();
	 * session.saveOrUpdate(leadsVOobj); session.flush(); return leadsVOobj; }
	 */

