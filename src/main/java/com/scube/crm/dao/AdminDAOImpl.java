package com.scube.crm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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
import org.hibernate.ejb.criteria.expression.function.CastFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.utils.DateHelper;
import com.scube.crm.utils.ErrorCodes;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.AccessVo;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.Campaign;
import com.scube.crm.vo.Company;
import com.scube.crm.vo.ContactVO;
import com.scube.crm.vo.Customer;
import com.scube.crm.vo.EmailAccess;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.Leads;
import com.scube.crm.vo.LeadsFollowup;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.Opportunity;
import com.scube.crm.vo.PrivilegesVO;
import com.scube.crm.vo.RolePrivileges;
import com.scube.crm.vo.RolePrivilegesVO;
import com.scube.crm.vo.RolesVO;
import com.scube.crm.vo.SalesOrderVO;
import com.scube.crm.vo.User;

@Repository("adminDAOImpl")
public class AdminDAOImpl extends BaseDao implements AdminDAO {

	public AdminDAOImpl() throws MySalesException {
		super();

	}

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {

		return sessionFactory.getCurrentSession();

	}

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(AdminDAOImpl.class);

	@Override
	public User authendicate(String string, String emailAddress) throws MySalesException {
		AdminDAOImpl.LOGGER.entry();
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
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			AdminDAOImpl.LOGGER.exit();
		}

		return user;

	}

	@Override
	public User authendicate(String emailAddress) throws MySalesException {
		AdminDAOImpl.LOGGER.entry();
		User user = null;
		final String loginQuery = "FROM User E WHERE E.emailAddress = :emailAddress and E.isDelete = :isDelete and E.isActive=:isActive";
		try {
			Session session = getSession();
			final Query query = session.createQuery(loginQuery);
			session.flush();
			query.setParameter("emailAddress", emailAddress);
			query.setParameter("isDelete", false);
			query.setParameter("isActive", true);
			if (null != query.list() && query.list().size() > 0) {
				user = (User) query.list().get(0);
			}

		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			AdminDAOImpl.LOGGER.exit();
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

		AdminDAOImpl.LOGGER.entry();
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
			AdminDAOImpl.LOGGER.exit();
		}

		return accessLogStatus;

	}

	@Override
	public long createuser(User adminVO) throws MySalesException {
		LOGGER.entry();
		try {
			Session session = getSession();
			long logId = (Long) session.save(adminVO);
			if (0 != logId) {
				return logId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("User creation failed:" + e.getMessage());
			}
			LOGGER.info("User creation failed:" + e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return 0;
	}

	@Override
	public List<User> retrieveUser(User user) {

		List<User> userVOList = new ArrayList<User>();
		LOGGER.entry();
		try {
			Criteria cr = getSession().createCriteria(User.class);
			cr.add(Restrictions.eq("isDelete", false));
			if (null != user && null != user.getCompany() && 0 < user.getCompany().getCompanyId()) {
				cr.add(companyMappingValidation(user.getCompany().getCompanyId())); // companyId
			}
			cr.addOrder(Order.desc("created"));
			userVOList = cr.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieve user has failed:" + ex.getMessage());
			}
			LOGGER.info("Retrieve user has failed:" + ex.getMessage());
		}
		return userVOList;
	}

	@Override
	public User userStatus(User userVO) throws MySalesException {
		LOGGER.entry();
		final String changeLoginQuery = "UPDATE User E set E.isActive = :isActive WHERE E.id= :id";
		try {
			final Query query = getSession().createQuery(changeLoginQuery);
			query.setParameter("isActive", userVO.isActive());
			query.setParameter("id", userVO.getId());
			final int result = query.executeUpdate();
			if (0 != result) {
				return userVO;
			}
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(he.getMessage() + he);
			}
		} finally {

			AdminDAOImpl.LOGGER.exit();
		}
		return null;

	}

	@Override
	public User updateuser(User loginVO) throws MySalesException {

		AdminDAOImpl.LOGGER.entry();
		try {
			Session session = getSession();
			session.merge(loginVO);
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_UPDATE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_UPDATE_FAIL, ErrorCodes.ENTITY_UPDATE_FAIL_MSG);
		} finally {

			AdminDAOImpl.LOGGER.exit();
		}
		return loginVO;

	}

	@Override
	public User retrieveusers(User userVO) throws MySalesException {
		LOGGER.entry();
		AdminUserBO adminuserBO = new AdminUserBO();
		List<AdminUserBO> BOList = new ArrayList<AdminUserBO>();
		List<User> VOList = new ArrayList<User>();
		User userVOobj = new User();
		try {
			Criteria cr = getSession().createCriteria(User.class);
			cr.add(Restrictions.eq("isDelete", false));
			cr.add(Restrictions.eq("id", userVO.getId()));
			userVOobj = (User) cr.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieve user has failed:" + ex.getMessage());
			}
			LOGGER.info("Retrieve user has failed:" + ex.getMessage());
		}

		return userVOobj;
	}
	
	@Override
	public User retrieveusersemail(User userVO) {
		LOGGER.entry();
		AdminUserBO adminuserBO = new AdminUserBO();
		List<AdminUserBO> BOList = new ArrayList<AdminUserBO>();
		List<User> VOList = new ArrayList<User>();
		User userVOobj = new User();
		try {
			Criteria cr = getSession().createCriteria(User.class);
			cr.add(Restrictions.eq("isDelete", false));
			cr.add(Restrictions.eq("emailAddress", userVO.getEmailAddress()));
			userVOobj = (User) cr.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieve user has failed:" + ex.getMessage());
			}
			LOGGER.info("Retrieve user has failed:" + ex.getMessage());
		}

		return userVOobj;
	}

	@Override
	public User getuserId(AdminUserBO loginBO) {
		Session session = getSession();

		User user = (User) session.get(User.class, loginBO.getId());
		return user;
	}

	@Override
	public User findByEmail(String string, String emailAddress) {
		AdminDAOImpl.LOGGER.entry();
		User loginVO = null;
		final String loginQuery = "FROM User E WHERE E.emailAddress = :emailAddress";
		try {
			Session session = getSession();
			final Query query = session.createQuery(loginQuery);
			query.setParameter("emailAddress", emailAddress);
			if (null != query.list() && query.list().size() > 0) {
				loginVO = (User) query.list().get(0);
			}
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(he, he.getMessage());
			}
		} finally {

			AdminDAOImpl.LOGGER.exit();
		}
		return loginVO;
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

	@Override
	public EmailAccess saveEmailList(List<EmailAccess> accessVOList) {
		AdminDAOImpl.LOGGER.entry();
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
			AdminDAOImpl.LOGGER.exit();
		}
		return accessVO;
	}

	@Override
	public List<User> searchEmployeeList(AdminUserBO adminBO) {
		List<User> userVOList = new ArrayList<User>();
		try {

			Criteria cr = getSession().createCriteria(User.class);
			cr.add(Restrictions.eq("isDelete", false));
			cr.setFirstResult(adminBO.getRecordIndex());
			cr.setMaxResults(adminBO.getMaxRecord());
			if (null != adminBO.getName() && !adminBO.getName().isEmpty()) {
				cr.add(Restrictions.ilike("name", adminBO.getName(), MatchMode.ANYWHERE));
			}
			if (null != adminBO.getEmailAddress() && !adminBO.getEmailAddress().isEmpty()) {
				cr.add(Restrictions.ilike("emailAddress", adminBO.getEmailAddress(), MatchMode.ANYWHERE));
			}
			if (null != adminBO.getUserType() && !adminBO.getUserType().isEmpty()) {
				cr.add(Restrictions.ilike("userType", adminBO.getUserType(), MatchMode.ANYWHERE));
			}
			// cr.add(Restrictions.eq("isActive", true));
			cr.addOrder(Order.desc("created"));
			userVOList = cr.list();

		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}
		}
		return userVOList;
	}

	@Override
	public boolean findByMobilenoEmployee(User userVO) {

		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("mobileNo", userVO.getMobileNo()));
			userVO = (User) criteria.uniqueResult();
			if (null != userVO.getMobileNo()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking History failed:" + e.getMessage());
		} finally {
			AdminDAOImpl.LOGGER.exit();
		}
		return false;
	}

	@Override
	public long campaignCount(AdminLoginBO adminLoginBO) {
		long count = 0;
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(Campaign.class);
			criteria.add(Restrictions.eq("isDelete", false));

			if (null != adminLoginBO && 0 < adminLoginBO.getCompany().getCompanyId()) {
				criteria.add(companyValidation(adminLoginBO.getCompany().getCompanyId()));
			}
			/*
			 * if (1 < adminLoginBO.getId()) { criteria.add(Restrictions.eq("user.id",
			 * adminLoginBO.getId())); }
			 */
			count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}
		}
		return count;
	}

	@Override
	public long leadsCount(AdminLoginBO adminLoginBO) {
		long leadsCount = 0;
		try {
			Session session = getSession();
			Criteria cri = session.createCriteria(Leads.class);
			if (null != adminLoginBO && 0 < adminLoginBO.getCompany().getCompanyId()) {
				cri.add(companyValidation(adminLoginBO.getCompany().getCompanyId()));
			}
			cri.add(Restrictions.eq("isDelete", false));
		
			cri.setProjection(Projections.rowCount());
			leadsCount = (long) cri.uniqueResult();

		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}
		}
		return leadsCount;
	}

	@Override
	public long customerCount(AdminLoginBO adminLoginBO) {

		long customersCount = 0;
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(Customer.class);
			if (null != adminLoginBO && 0 < adminLoginBO.getCompany().getCompanyId()) {
				criteria.add(companyValidation(adminLoginBO.getCompany().getCompanyId()));
			}
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.setProjection(Projections.rowCount());
			customersCount = (long) criteria.uniqueResult();

		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}
		}
		return customersCount;
	}

	@Override
	public long employeesCount(AdminLoginBO adminLoginBO) {

		long employeeCount = 0;
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.add(Restrictions.eq("isActive", true));

			if (null != adminLoginBO && !adminLoginBO.getUserType().equalsIgnoreCase("ROLE_ADMIN") && 0 < adminLoginBO.getCompany().getCompanyId()) {
				criteria.add(companyMappingValidation(adminLoginBO.getCompany().getCompanyId()));
			}
		
			employeeCount = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}
		}
		return employeeCount;
	}

	@Override
	public boolean findEmployerEmail(String emailAddress) {

		return false;
	}

	@Override
	public boolean mobileNoVerification(String mobileNo) {

		return false;
	}

	public long getCurrentActionCustomers(AdminLoginBO adminLoginBO) {

		long employeeCount = 0;
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(LeadsFollowup.class);
			DateHelper.beginningOfDay(new Date());
			Date date = getDateWithoutTime(new Date());
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String startDate = df.format(date);
			criteria.add(Restrictions.eq("nextAppointmentDate", startDate));
			criteria.setProjection(Projections.rowCount());
			employeeCount = (long) criteria.uniqueResult();
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}
		}
		return employeeCount;

	}

	public static Date getDateWithoutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	@Override
	public long productCount(AdminLoginBO adminLoginBO) {
		long productCount = 0;
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(InventoryVO.class);
			if (null != adminLoginBO && 0 < adminLoginBO.getCompany().getCompanyId()) {
				criteria.add(companyValidation(adminLoginBO.getCompany().getCompanyId()));
			}
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.setProjection(Projections.rowCount());
			productCount = (long) criteria.uniqueResult();

		} catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}
		}
		return productCount;
	}

	@Override
	public PrivilegesVO savePrivileges(PrivilegesVO privilegesvo) throws MySalesException {
		LOGGER.entry();
		try {
			Session session = getSession();
			session.save(privilegesvo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createprivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("createprivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegesvo;
	}

	@Override
	public List<PrivilegesBO> retrivePrivileges(List<PrivilegesBO> privilegesbolist) throws MySalesException {
		LOGGER.entry();
		List<PrivilegesBO> bolist = new ArrayList<PrivilegesBO>();
		try {
			List<PrivilegesVO> volist = new ArrayList<PrivilegesVO>();
			Session session = getSession();
			int count = 1;
			Criteria criteria = session.createCriteria(PrivilegesVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			volist = criteria.list();
			if (volist != null && volist.size() > 0) {
				int data;
				for (PrivilegesVO vo2 : volist) {
					data = count++;
					PrivilegesBO bo = new PrivilegesBO();
					bo.setPrivilegeId(vo2.getPrivilegeId());
					bo.setPrivilegename(vo2.getPrivilegesName());
					bo.setsNo(data);
					bolist.add(bo);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("viewPrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("viewPrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return bolist;
	}

	@Override
	public int deletePrivilege(PrivilegesVO vo) throws MySalesException {
		LOGGER.entry();
		int result = 0;
		try {
			final String deleteQuery = "UPDATE PrivilegesVO S set S.isDeleted = :isDeleted   WHERE S.privilegeId = :privilegeId";
			final Query query = getSession().createQuery(deleteQuery);
			query.setParameter("isDeleted", vo.getisDeleted());
			query.setParameter("privilegeId", vo.getPrivilegeId());
			result = query.executeUpdate();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deletePrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("deletePrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return result;
	}

	@Override
	public PrivilegesVO editPrivileges(PrivilegesVO vo) throws MySalesException {
		LOGGER.entry();
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(PrivilegesVO.class);
			criteria.add(Restrictions.eq("privilegeId", vo.getPrivilegeId()));
			vo = (PrivilegesVO) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editPrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("editPrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return vo;
	}

	@Override
	public PrivilegesVO updatePrivileges(PrivilegesVO vo) throws MySalesException {
		LOGGER.entry();
		try {
			Session session = getSession();
			session.update(vo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("updatePrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("updatePrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return vo;
	}

	@Override
	public List<PrivilegesVO> searchPrivileges(PrivilegesVO vo) throws MySalesException {
		LOGGER.entry();
		List<PrivilegesVO> volist = new ArrayList<PrivilegesVO>();
		try {
			PrivilegesBO bo = new PrivilegesBO();
			Session session = getSession();
			Criteria criteria = session.createCriteria(PrivilegesVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			criteria.setFirstResult(bo.getRecordIndex());
			criteria.setMaxResults(bo.getMaxRecord());
			if (null != vo.getPrivilegesName() && !vo.getPrivilegesName().isEmpty()) {
				criteria.add(Restrictions.ilike("privilegesName", vo.getPrivilegesName().trim(), MatchMode.ANYWHERE));
			}
			criteria.addOrder(Order.desc("created"));
			volist = criteria.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchPrivilegename has failed:" + ex.getMessage());
			}
			LOGGER.info("searchPrivilegename has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return volist;
	}

	@Override
	public List<RoleBO> retriveRolePrivilege(List<RoleBO> roleboolist) {
		RolePrivilegesVO roleprivilegevo = new RolePrivilegesVO();
		List<RolePrivilegesVO> listroleprivilegevo = new ArrayList<RolePrivilegesVO>();
		int count = 1;
		List<RoleBO> listrolebo = new ArrayList<RoleBO>();
		if (null != roleboolist && roleboolist.size() > 0) {
			for (RoleBO bo : roleboolist) {
				RolesVO vo = new RolesVO();
				vo.setRoleId(bo.getRoleId());
				int roleid = (int) vo.getRoleId();
				RolePrivileges roleprivilege = new RolePrivileges();
				roleprivilege.setRoleId(roleid);
				roleprivilegevo.setId(roleprivilege);

				Session session = getSession();
				Criteria criteria = session.createCriteria(RolePrivilegesVO.class);
				criteria.add(Restrictions.eq("id.roleId", roleprivilegevo.getId().getRoleId()));
				listroleprivilegevo = criteria.list();

				for (RolePrivilegesVO assignvo : listroleprivilegevo) {
					RoleBO role = new RoleBO();
					int data = count++;
					long roleId = (long) assignvo.getId().getRoleId();
					long priviilegeId = (long) assignvo.getId().getPrivilegeId();
					role.setRoleId(roleId);
					role.setPrivilegeId(priviilegeId);
					role.setsNo(data);
					listrolebo.add(role);
				}
			}
		}
		return listrolebo;
	}

	@Override
	public PrivilegesVO getprivilege(PrivilegesVO privilegevo) throws MySalesException {
		LOGGER.entry();
		PrivilegesVO privilegesvo = new PrivilegesVO();
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(PrivilegesVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			criteria.add(Restrictions.eq("privilegeId", privilegevo.getPrivilegeId()));
			privilegesvo = (PrivilegesVO) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getprivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("getprivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegesvo;
	}

	@Override
	public RolePrivilegesVO assignRolePrivilege(RolePrivilegesVO vo) throws MySalesException {
		LOGGER.entry();
		try {
			Session session = getSession();
			session.saveOrUpdate(vo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("assignRolePrivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("assignRolePrivilege has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return vo;
	}

	@Override
	public RolePrivilegesVO getroleprivilege(RolePrivilegesVO roleprivilegevo) throws MySalesException {
		LOGGER.entry();
		RolePrivilegesVO assignvo = new RolePrivilegesVO();
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(RolePrivilegesVO.class);
			criteria.add(Restrictions.eq("id.roleId", roleprivilegevo.getId().getRoleId()))
					.add(Restrictions.eq("id.privilegeId", roleprivilegevo.getId().getPrivilegeId()));
			assignvo = (RolePrivilegesVO) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getroleprivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("getroleprivilege has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return assignvo;
	}

	@Override
	public RolePrivilegesVO removeRole(RolePrivilegesVO assignvo) throws MySalesException {
		LOGGER.entry();
		try {
			int roleId = (int) assignvo.getId().getRoleId();
			Session session = getSession();
			String hql = "delete from roles_privileges where roleId = :roleId";
			Query query = session.createSQLQuery(hql);
			query.setParameter("roleId", roleId);
			query.executeUpdate();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("removeRole has failed:" + ex.getMessage());
			}
			LOGGER.info("removeRole has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return assignvo;
	}

	/*
	 * @Override public List<AdminUserBO> retrieveUser(List<AdminUserBO> bOList) {
	 * 
	 * AdminUserBO adminuserBO = new AdminUserBO(); List<AdminUserBO>
	 * adminUserBOList = new ArrayList<AdminUserBO>(); List<User> userVOList = new
	 * ArrayList<User>(); try { int count = 1; Criteria cr =
	 * getSession().createCriteria(User.class); cr.add(Restrictions.eq("isDelete",
	 * false)); cr.add(Restrictions.eq("isActive", true)); if (null !=
	 * adminuserBO.getName() && !adminuserBO.getName().isEmpty()) {
	 * cr.add(Restrictions.ilike("name", adminuserBO.getName(),
	 * MatchMode.ANYWHERE)); } if (null != adminuserBO.getEmailAddress() &&
	 * !adminuserBO.getEmailAddress().isEmpty()) {
	 * cr.add(Restrictions.ilike("emailAddress", adminuserBO.getEmailAddress(),
	 * MatchMode.ANYWHERE)); } if (null != adminuserBO.getUserType() &&
	 * !adminuserBO.getUserType().isEmpty()) { cr.add(Restrictions.ilike("userType",
	 * adminuserBO.getUserType(), MatchMode.ANYWHERE)); } //
	 * cr.add(Restrictions.eq("isActive", true));
	 * cr.addOrder(Order.desc("created")); userVOList = cr.list();
	 * 
	 * if (null != userVOList && !userVOList.isEmpty()) { int data; for (User vo :
	 * userVOList) { List<RoleBO> listrole = new ArrayList<RoleBO>(); data =
	 * count++; User uservo = new User();
	 * uservo.setListRoletypeVo(vo.getListRoletypeVo()); adminuserBO = new
	 * AdminUserBO(); adminuserBO.setId(vo.getId());
	 * adminuserBO.setActive(vo.isActive()); adminuserBO.setsNo(data);
	 * adminuserBO.setName(vo.getName()); adminuserBO.setMobileNo(vo.getMobileNo());
	 * // adminuserBO.setUserType(vo.getUserType());
	 * adminuserBO.setPassword(vo.getPassword());
	 * adminuserBO.setEmailAddress(vo.getEmailAddress());
	 * adminuserBO.setConfirmPassword(vo.getConfirmpassword());
	 * adminuserBO.setListrlebo(listrole); if (vo.isActive()) {
	 * adminuserBO.setStatus("Active"); } else { adminuserBO.setStatus("De-Active");
	 * } System.out.println(adminuserBO.getName());
	 * adminUserBOList.add(adminuserBO); } } } catch (Exception ex) { if
	 * (LOGGER.isDebugEnabled()) { LOGGER.debug("Retrieve user has failed:" +
	 * ex.getMessage()); } LOGGER.info("Retrieve user has failed:" +
	 * ex.getMessage()); } return adminUserBOList; }
	 */
	
	@Override
	public List<AdminUserBO> retrieveUser(List<AdminUserBO> bOList) {

		AdminUserBO adminuserBO = new AdminUserBO();
		List<AdminUserBO> adminUserBOList = new ArrayList<AdminUserBO>();
		List<User> userVOList = new ArrayList<User>();
		long companyId = bOList.get(0).getCompanyId();
		try {
			int count = 1;
			Criteria cr = getSession().createCriteria(User.class);
			cr.add(Restrictions.eq("isDelete", false));
			cr.add(Restrictions.eq("isActive", true));
			cr.add(Restrictions.eq("company.companyId", companyId));
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
			userVOList = cr.list();

			if (null != userVOList && !userVOList.isEmpty()) {
				int data;
				for (User vo : userVOList) {
					List<RoleBO> listrole = new ArrayList<RoleBO>();
					data = count++;
					User uservo = new User();
					uservo.setListRoletypeVo(vo.getListRoletypeVo());
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
					adminuserBO.setListrlebo(listrole);
					if (vo.isActive()) {
						adminuserBO.setStatus("Active");
					} else {
						adminuserBO.setStatus("De-Active");
					}
					System.out.println(adminuserBO.getName());
					adminUserBOList.add(adminuserBO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieve user has failed:" + ex.getMessage());
			}
			LOGGER.info("Retrieve user has failed:" + ex.getMessage());
		}
		return adminUserBOList;
	}
	
	@Override
	public User retriveUserByname(User user) throws Exception {
		LOGGER.entry();
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.add(Restrictions.eq("name", user.getName()));
			user = (User) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("retriveUserByName has failed:" + ex.getMessage());
			}
			LOGGER.info("retriveUserByName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return user;
	}

	@Override
	public List<User> retriveuserById(User user) {
		User uservo = new User();
		List<User> userVOlist = new ArrayList<>();

		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("isDelete", false));
		criteria.add(Restrictions.eq("id", user.getId()));
		uservo = (User) criteria.uniqueResult();

		if (null != uservo) {
			userVOlist.add(uservo);
		}
		return userVOlist;
	}

	@Override
	public PrivilegesVO getprivilegeById(PrivilegesVO privilegesvo) throws MySalesException {
		LOGGER.entry();
		PrivilegesVO privilegevo = new PrivilegesVO();
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(PrivilegesVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			criteria.add(Restrictions.eq("privilegeId", privilegesvo.getPrivilegeId()));
			privilegevo = (PrivilegesVO) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getPrivilegesbyId has failed:" + ex.getMessage());
			}
			LOGGER.info("getPrivilegesbyId has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegevo;
	}

	@Override
	public List<PrivilegesVO> listOfPrivileges(List<PrivilegesVO> privilegeListVO) throws MySalesException {
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		try {
			if (null != privilegeListVO) {
				Criteria cr = session.createCriteria(PrivilegesVO.class);
				cr.add(Restrictions.eq("isDeleted", false));
				privilegeListVO = cr.list();
			}
			if (null != privilegeListVO && !privilegeListVO.isEmpty() && privilegeListVO.size() > 0) {
				return privilegeListVO;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfPrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfPrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeListVO;
	}

	@Override
	public List<AccessVo> listOfAccess() throws MySalesException {
		LOGGER.entry();
		List<AccessVo> accessVOList = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria cr = session.createCriteria(AccessVo.class);
			accessVOList = cr.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return accessVOList;
	}

	@Override
	public PrivilegesVO privilegeAccess(PrivilegesVO privilegeVo) throws MySalesException {
		LOGGER.entry();
		Session session = getSession();
		try {
			session.saveOrUpdate(privilegeVo);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("privilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("privilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeVo;
	}

	@Override
	public List<PrivilegesVO> listOfPrivilegeAccess() throws MySalesException {
		LOGGER.entry();
		List<PrivilegesVO> privilegeList = new ArrayList<>();
		Session session = getSession();
		try {
			Criteria cr = session.createCriteria(PrivilegesVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			privilegeList = cr.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfPrvielegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfPrvielegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeList;
	}

	@Override
	public List<PrivilegesVO> editPrivilegeAccess(PrivilegesVO privilegeVO) throws MySalesException {
		LOGGER.entry();
		List<PrivilegesVO> privilegeVOList = new ArrayList<>();
		Session session = getSession();
		try {
			if (0 < privilegeVO.getPrivilegeId()) {
				Criteria cr = session.createCriteria(PrivilegesVO.class);
				cr.add(Restrictions.eq("privilegeId", privilegeVO.getPrivilegeId()));
				privilegeVO = (PrivilegesVO) cr.uniqueResult();

				if (null != privilegeVO) {
					privilegeVOList.add(privilegeVO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editPrivilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("editPrivilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeVOList;
	}

	@Override
	public PrivilegesVO updatePrivilegeAccess(PrivilegesVO privilegeVO) throws MySalesException {
		LOGGER.entry();
		Session session = getSession();
		try {
			if (null != privilegeVO) {
				session.update(privilegeVO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("updatePrivilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("updatePrivilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return privilegeVO;
	}

	@Override
	public PrivilegesVO deletePrivilegeAccess(long privilegeId, long accessId) throws MySalesException {
		LOGGER.entry();
		int count = 0;
		PrivilegesVO privilegeVOobj = new PrivilegesVO();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			CastFunction con = (CastFunction) DriverManager.getConnection("jdbc:mysql://localhost:3306/mysalesliveapril24", "root",
					"admin");
			Statement stmt = (Statement) ((Connection) con).createStatement();
			String deleteSql = "delete from privileges_access where privilege_Id=" + privilegeId + " and access_Id="
					+ accessId + "";
			count = stmt.executeUpdate(deleteSql);

			if (0 < count) {
				System.out.println("privilegeId and accessId has been successfully deleted");
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deletePrivilegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("deletePrivilegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return privilegeVOobj;
	}

	@Override
	public List<Long> geAccessIdListIdList(PrivilegesVO privilegeVO) throws MySalesException {
		LOGGER.entry();
		List<Long> priviledgeIdList = new ArrayList<Long>();
		Session session = getSession();
		try {
			Criteria criteria = session.createCriteria(PrivilegesVO.class);
			criteria.createCriteria("accessList", "ref");
			criteria.setProjection(Projections.groupProperty("ref.privilegeId"));
			criteria.add(Restrictions.eq("privilegeId", privilegeVO.getPrivilegeId()));
			priviledgeIdList = (List<Long>) criteria.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("geAccessIdListIdList has failed:" + ex.getMessage());
			}
			LOGGER.info("geAccessIdListIdList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return priviledgeIdList;
	}

	@Override
	public List<RolesVO> getUserRoles(long userId) throws MySalesException {
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		List<RolesVO> accessVOList = null;
		try {
			Criteria cr = session.createCriteria(User.class);
			cr.createAlias("listRoletypeVo", "roles");
			cr.add(Restrictions.eq("roles.id", userId));
			accessVOList = cr.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getUserRoles has failed:" + ex.getMessage());
			}
			LOGGER.info("getUserRoles has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return accessVOList;
	}

	@Override
	public List<Long> getroleIdList(User userVO) {
		LOGGER.entry();

		List<Long> roleIdList = new ArrayList<>();

		Session session = getSession();
		try {
			Criteria cr = session.createCriteria(User.class);
			cr.createCriteria("listRoletypeVo", "roleIdref");
			cr.setProjection(Projections.groupProperty("roleIdref.roleId"));
			cr.add(Restrictions.eq("id", userVO.getId()));
			roleIdList = cr.list();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		return roleIdList;
	}

	@Override
	public List<PrivilegesVO> getPrivilegeId(PrivilegesVO privilegeVO) throws MySalesException {
		LOGGER.entry();
		List<PrivilegesVO> privilegeListVO = new ArrayList<>();
		try {
			PrivilegesVO privilegeVOObj = new PrivilegesVO();
			Session session = getSession();
			Criteria cr = session.createCriteria(PrivilegesVO.class);
			cr.add(Restrictions.eq("privilegeId", privilegeVO.getPrivilegeId()));
			privilegeVOObj = (PrivilegesVO) cr.uniqueResult();

			if (null != privilegeVOObj) {
				privilegeListVO.add(privilegeVOObj);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getPrivilegeId has failed:" + ex.getMessage());
			}
			LOGGER.info("getPrivilegeId has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeListVO;
	}

	@Override
	public List<User> searchUserName(User userVO) throws MySalesException {
		LOGGER.entry();
		List<User> userVOList = new ArrayList<>();
		Session session = getSession();
		try {
			Criteria cr = session.createCriteria(User.class);
			if (!userVO.getName().isEmpty()) {
				cr.add(Restrictions.ilike("name", userVO.getName().trim(), MatchMode.ANYWHERE));
			}
			cr.setFirstResult(userVO.getRecordIndex());
			cr.setMaxResults(userVO.getMaxRecord());
			cr.addOrder(Order.desc("created"));
			userVOList = cr.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchUserName has failed:" + ex.getMessage());
			}
			LOGGER.info("searchUserName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return userVOList;
	}

	@Override
	public List<RolesVO> searchRoleName(RolesVO roleVOObj) throws MySalesException {
		LOGGER.entry();
		List<RolesVO> roleVOList = new ArrayList<>();
		Session session = getSession();
		try {
			Criteria cr = session.createCriteria(RolesVO.class);
			if (!roleVOObj.getRoleName().isEmpty()) {
				cr.add(Restrictions.ilike("roleName", roleVOObj.getRoleName().trim()));
			}
			roleVOList = cr.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchRoleName has failed:" + ex.getMessage());
			}
			LOGGER.info("searchRoleName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return roleVOList;
	}

	@Override
	public List<PrivilegesVO> searchByPrivilegeName(PrivilegesVO privilegeVO) throws MySalesException {
		LOGGER.entry();
		List<PrivilegesVO> privilegeVOList = new ArrayList<>();
		Session session = getSession();
		try {
			Criteria cr = session.createCriteria(PrivilegesVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			if (!privilegeVO.getPrivilegesName().isEmpty()) {
				cr.add(Restrictions.ilike("privilegesName", privilegeVO.getPrivilegesName().trim(), MatchMode.ANYWHERE));
			}
			privilegeVOList = cr.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchByPrivilegeName has failed:" + ex.getMessage());
			}
			LOGGER.info("searchByPrivilegeName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return privilegeVOList;
	}

	@Override
	public long getListOfadminUser(User user) throws MySalesException {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("isDelete", false));
			if (null !=  user.getCompany() && 0< user.getCompany().getCompanyId()) {
			criteria.add(companyMappingValidation(user.getCompany().getCompanyId())); // company condition
			}
			/*
			 * if(null != priceBookVO.getPriceBookName() &&
			 * !priceBookVO.getPriceBookName().isEmpty()) {
			 * cr.add(Restrictions.ilike("priceBookName",
			 * priceBookVO.getPriceBookName().trim(), MatchMode.ANYWHERE)); }
			 */
			if (null != user.getEmailAddress() && !user.getEmailAddress().isEmpty()) {
				criteria.add(Restrictions.ilike("emailAddress", user.getEmailAddress(), MatchMode.ANYWHERE));
			}
			if (null != user.getName() && !user.getName().isEmpty()) {
				criteria.add(Restrictions.ilike("name", user.getName().trim(), MatchMode.ANYWHERE));
			}

			criteria.setProjection(Projections.rowCount());
			long count = (long) criteria.uniqueResult();
			if (0 != count) {
				return count;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" Getlistadmin User has failed:" + ex.getMessage());
			}
			LOGGER.info("Getlistadmin User has failed:" + ex.getMessage());
		}

		return 0;
	}

	@Override
	public List<User> retrieveUserByPagination(AdminUserBO adminuserBO) throws MySalesException {
		LOGGER.entry();
		List<User> userVOList = new ArrayList<User>();
		try {
			Criteria cr = getSession().createCriteria(User.class);
			cr.add(Restrictions.eq("isDelete", false));
			cr.add(Restrictions.eq("isActive", true));
			if (null != adminuserBO.getCompanyId() && 0 < adminuserBO.getCompanyId()) {
	        cr.add(companyMappingValidation(adminuserBO.getCompanyId()));//company condition
			}
			if (null != adminuserBO.getName() && !adminuserBO.getName().isEmpty()) {
				cr.add(Restrictions.ilike("name", adminuserBO.getName().trim(), MatchMode.ANYWHERE));
			}
			if (null != adminuserBO.getEmailAddress() && !adminuserBO.getEmailAddress().isEmpty()) {
				cr.add(Restrictions.ilike("emailAddress", adminuserBO.getEmailAddress().trim(), MatchMode.ANYWHERE));
			}
			if (null != adminuserBO.getUserType() && !adminuserBO.getUserType().isEmpty()) {
				cr.add(Restrictions.ilike("userType", adminuserBO.getUserType().trim(), MatchMode.ANYWHERE));
			}

			if (null != adminuserBO && null != adminuserBO.getSkillsBO()
					&& 0 < adminuserBO.getSkillsBO().getSkillsId()) {
				cr.createAlias("skillsListVO", "skills");
				cr.add(Restrictions.eq("skills.skillsId", adminuserBO.getSkillsBO().getSkillsId()));
			} 
//			cr.addOrder(Order.desc("id"));
			// cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			if (null != adminuserBO && adminuserBO.getMaxRecord() > 0) {
				cr.setFirstResult(adminuserBO.getRecordIndex());
				cr.setMaxResults(adminuserBO.getMaxRecord());
			}
			userVOList = cr.list();

			if (null != userVOList && !userVOList.isEmpty() && userVOList.size() > 0) {
				return userVOList;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" retrive user Pagination  has failed:" + ex.getMessage());
			}
			LOGGER.info("retrive User Pagination has failed:" + ex.getMessage());
		}
		return userVOList;
	}

	@Override
	public User getEmployeeObjectByName(User userVO) throws MySalesException {
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("name", userVO.getName()));
			userVO = (User) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getEmployeeObjectByName has failed:" + ex.getMessage());
			}
			LOGGER.info("getEmployeeObjectByName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return userVO;
	}

	@Override
	public long countOfUsers(User userVO,long companyId) throws MySalesException {
		// TODO Auto-generated method stub
		LOGGER.entry();
		long countOfUsers = 0;
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(User.class);
			criteria.setProjection(Projections.rowCount());
			if (0<companyId) {
				criteria.add(Restrictions.eq("company.companyId", companyId));
			}
			criteria.add(Restrictions.eq("isDelete", false));
			countOfUsers = (long) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("countOfUsers has failed:" + ex.getMessage());
			}
			LOGGER.info("countOfUsers has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return countOfUsers;
	}

	@Override
	public List<User> listOfUsersByPagination(User userVO) throws MySalesException {
		LOGGER.entry();
		List<User> userVOList = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(User.class);
			criteria.setFirstResult(userVO.getRecordIndex());
			criteria.setMaxResults(userVO.getMaxRecord());
			if (null!=userVO && null!=userVO.getCompany() && 0!=userVO.getCompany().getCompanyId()) {
				criteria.add(Restrictions.eq("company.companyId", userVO.getCompany().getCompanyId()));
			}
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.addOrder(Order.desc("name"));
			userVOList = criteria.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfUsersByPagination has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfUsersByPagination has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return userVOList;
	}

	@Override
	public long countOfUsersBySearch(User userVO) throws MySalesException {
		LOGGER.entry();
		long countOfUsers = 0;
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.ilike("name", userVO.getName(), MatchMode.ANYWHERE));
			criteria.setProjection(Projections.rowCount());
			countOfUsers = (long) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("countOfUsersBySearch has failed:" + ex.getMessage());
			}
			LOGGER.info("countOfUsersBySearch has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return countOfUsers;
	}

	@Override
	public List<User> listOfUsersBySearchPagination(User userVO) throws MySalesException {
		LOGGER.entry();
		List<User> userVOList = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(User.class);
			if (null != userVO.getName()) {
				criteria.add(Restrictions.ilike("name", userVO.getName(), MatchMode.ANYWHERE));
			}
			criteria.setFirstResult(userVO.getRecordIndex());
			criteria.setMaxResults(userVO.getMaxRecord());
			userVOList = criteria.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfUsersBySearchPagination has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfUsersBySearchPagination has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return userVOList;
	}

	@Override
	public long contactCounts(AdminLoginBO adminLoginBO) {
		// TODO Auto-generated method stub
		LOGGER.entry();
		long id = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ContactVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			if (1 < adminLoginBO.getId()) {
				criteria.add(Restrictions.eq("assignedTo.id", adminLoginBO.getId()));
			}
			criteria.setProjection(Projections.rowCount());
			id = (long) criteria.uniqueResult();

		} catch (Exception he) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view contact has failed:" + he.getMessage());
			}
			LOGGER.info("view contact has failed:" + he.getMessage());

		}
		return id;
	}

	@Override
	public long accountCount(AdminLoginBO adminLoginBO) {
		LOGGER.entry();
		long id = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(AccountVO.class);
			if (null != adminLoginBO && 0 < adminLoginBO.getCompany().getCompanyId()) {
				criteria.add(companyValidation(adminLoginBO.getCompany().getCompanyId()));
			}
			criteria.add(Restrictions.eq("isDeleted", false));
			criteria.setProjection(Projections.rowCount());
			id = (long) criteria.uniqueResult();

		} catch (Exception he) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view contact has failed:" + he.getMessage());
			}
			LOGGER.info("view contact has failed:" + he.getMessage());

		}
		return id;
	}

	@Override
	public long opportunityCount(AdminLoginBO adminLoginBO) throws MySalesException {
		AdminDAOImpl.LOGGER.entry();
		long id = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Opportunity.class);
			criteria.add(Restrictions.eq("isDelete", false));
			if (null != adminLoginBO && 0 < adminLoginBO.getCompany().getCompanyId()) {
				criteria.add(companyValidation(adminLoginBO.getCompany().getCompanyId()));
			}
			criteria.setProjection(Projections.rowCount());
			id = (long) criteria.uniqueResult();
		} catch (Exception he) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Unable to fetch opportunity count: " + he.getMessage());
			}
			LOGGER.info("Getting opportunity count has been failed: " + he.getMessage());

		} finally {
			AdminDAOImpl.LOGGER.exit();
		}
		return id;
	}

	@Override
	public boolean checkEmailAddress(String emailAddress) throws MySalesException {
		LOGGER.entry();
		List<User> user = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.add(Restrictions.eq("emailAddress", emailAddress));
			//criteria.add(Restrictions.eq("company.companyId", id));
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
	public boolean checkMobileNo(String mobileNo) throws MySalesException {
		LOGGER.entry();
		List<User> user = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.add(Restrictions.eq("mobileNo", mobileNo));
		  //  criteria.add(Restrictions.eq("company.companyId", id));
			user = (List<User>) criteria.list();
            
			if (null != user && user.size() > 0) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkMobileNo has failed:" + ex.getMessage());
			}
			LOGGER.info("checkMobileNo has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public boolean deleteEmployee(long deletedId) throws MySalesException {
		LOGGER.entry();
		boolean status = false;
		try {
			int result = 0;
			final String deleteQuery = "UPDATE User S set" + " S.isDelete = :isDelete" + " WHERE S.id = :id";
			final Query query = getSession().createQuery(deleteQuery);
			query.setParameter("isDelete", true);
			query.setParameter("id", deletedId);
			result = query.executeUpdate();
			if (0 < result) {
				status = true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deleteEmployee has failed:" + ex.getMessage());
			}
			LOGGER.info("deleteEmployee has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();

		}
		return status;
	}

	@Override
	public User getEmployee(long userId) {
		LOGGER.entry();

		User userVO = new User();

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", Long.valueOf(userId)));
			userVO = (User) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get lead has failed:" + ex.getMessage());
			}
			LOGGER.info("Get lead has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return userVO;
	}

	@Override
	public List<Long> getSkillIdList(User userVO) {
		List<Long> skillsIdIdList = new ArrayList<Long>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(User.class);
			criteria.createCriteria("skillsListVO", "ref");
			criteria.setProjection(Projections.groupProperty("ref.skillsId"));
			criteria.add(Restrictions.eq("id", userVO.getId()));
			skillsIdIdList = (List<Long>) criteria.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getPrivelegeIdList has failed:" + ex.getMessage());
			}
			LOGGER.info("getPrivelegeIdList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return skillsIdIdList;
	}

	@Override
	public List<User> findEmployeesBySkills(long skillsIds) {

		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(User.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("isDelete", false));
		criteria.createAlias("skillsListVO", "skills");
		criteria.add(Restrictions.eq("skills.skillsId", skillsIds));
		List<User> userVOList = criteria.list();

		return userVOList;

	}

	@Override
	public boolean checkPrivilegeName(String privilegename) {
		LOGGER.entry();
		PrivilegesVO privilegesVO = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(PrivilegesVO.class);
			criteria.add(Restrictions.eq("privilegesName", privilegename));
			List roleuserlist =  criteria.list();

			if (null != roleuserlist && 0<roleuserlist.size()) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("privilegeNameCheck has failed:" + ex.getMessage());
			}
			LOGGER.info("privilegeNameCheck has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return false;
	}

	@Override
	public List<User> retrieveEmployeeByPagination(AdminUserBO adminuserBO) throws MySalesException {
		LOGGER.entry();
		List<User> userVOList = new ArrayList<User>();
		try {
			Criteria cr = getSession().createCriteria(User.class);
			cr.add(Restrictions.eq("isDelete", false));
			//cr.add(Restrictions.eq("isActive", true));
			if (null != adminuserBO.getCompanyId() && 0 < adminuserBO.getCompanyId()) {
	        cr.add(companyMappingValidation(adminuserBO.getCompanyId()));//company condition
			}
			if (null != adminuserBO.getName() && !adminuserBO.getName().isEmpty()) {
				cr.add(Restrictions.ilike("name", adminuserBO.getName(), MatchMode.ANYWHERE));
			}
			if (null != adminuserBO.getEmailAddress() && !adminuserBO.getEmailAddress().isEmpty()) {
				cr.add(Restrictions.ilike("emailAddress", adminuserBO.getEmailAddress(), MatchMode.ANYWHERE));
			}
			if (null != adminuserBO.getUserType() && !adminuserBO.getUserType().isEmpty()) {
				cr.add(Restrictions.ilike("userType", adminuserBO.getUserType(), MatchMode.ANYWHERE));
			}

			if (null != adminuserBO && null != adminuserBO.getSkillsBO()
					&& 0 < adminuserBO.getSkillsBO().getSkillsId()) {
				cr.createAlias("skillsListVO", "skills");
				cr.add(Restrictions.eq("skills.skillsId", adminuserBO.getSkillsBO().getSkillsId()));
			} 
//			cr.addOrder(Order.desc("id"));
			// cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			if (null != adminuserBO && adminuserBO.getMaxRecord() > 0) {
				cr.setFirstResult(adminuserBO.getRecordIndex());
				cr.setMaxResults(adminuserBO.getMaxRecord());
			}
			userVOList = cr.list();

			if (null != userVOList && !userVOList.isEmpty() && userVOList.size() > 0) {
				return userVOList;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" retrive user Pagination  has failed:" + ex.getMessage());
			}
			LOGGER.info("retrive User Pagination has failed:" + ex.getMessage());
		}
		return userVOList;
	}

	@Override
	public List<InventoryVO> listOfProduct(long companyId) {
		LOGGER.entry();
		 List<InventoryVO> productVOList=new ArrayList<>();
		 try {
			 Criteria cr=sessionFactory.getCurrentSession().createCriteria(InventoryVO.class);
			 cr.add(Restrictions.eq("isDelete",false));
			 cr.add(Restrictions.eq("companyId",companyId));
			 productVOList=cr.list();
		 }
		 catch(Exception e){
			 if(LOGGER.isInfoEnabled()) {
				 LOGGER.info("product list details: Exception \t"+e);
			 }
			 if(LOGGER.isDebugEnabled()) {
				 LOGGER.debug("product list details: Exception \t"+e);
			 }
		 }
		 finally{
			 LOGGER.exit();
		 }
		 return productVOList;
	}


	@Override
    public long countOfPrivilege(PrivilegesVO privilegesVO) {
        // TODO Auto-generated method stub
        long countOfPrivileges=0;
        
        Session session=sessionFactory.getCurrentSession();
        try {
            Criteria criteria=session.createCriteria(PrivilegesVO.class);
			/*
			 * if(null != privilegesVO.getCompanyId()&& 0< privilegesVO.getCompanyId()) {
			 * privilegesVO.setCompanyId(privilegesVO.getCompanyId()); }
			 */
            criteria.add(Restrictions.eq("isDeleted",false));
            if( null!=privilegesVO.getPrivilegesName() && !privilegesVO.getPrivilegesName().isEmpty()) {
                criteria.add(Restrictions.ilike("privilegesName", privilegesVO.getPrivilegesName().trim(), MatchMode.ANYWHERE));
            }
            criteria.setProjection(Projections.rowCount());
            countOfPrivileges=(long)criteria.uniqueResult();
        }
        catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return countOfPrivileges;
    }



	@Override
    public List<PrivilegesVO> listOfPrivilegeByPagination(PrivilegesVO privilegesVO) {
        // TODO Auto-generated method stub
        List<PrivilegesVO> privilegeListVO = new ArrayList<>();
        Session session=sessionFactory.getCurrentSession();
        try {
            Criteria criteria=session.createCriteria(PrivilegesVO.class);
            criteria.add(Restrictions.eq("isDeleted", false));
			/*
			 * if(null != privilegesVO.getCompanyId()&& 0< privilegesVO.getCompanyId()) {
			 * criteria.add(companyValidation(privilegesVO.getCompanyId()));// company
			 * condition }
			 */
            if( null!=privilegesVO.getPrivilegesName() && !privilegesVO.getPrivilegesName().isEmpty()) {
                criteria.add(Restrictions.ilike("privilegesName", privilegesVO.getPrivilegesName().trim(), MatchMode.ANYWHERE));
            }
            if (null != privilegesVO && privilegesVO.getMaxRecord() > 0) {
                criteria.setFirstResult(privilegesVO.getRecordIndex());
                criteria.setMaxResults(privilegesVO.getMaxRecord());
			}
           
             privilegeListVO=criteria.list();
             if (null != privilegeListVO && !privilegeListVO.isEmpty() && privilegeListVO.size() > 0) {
 				return privilegeListVO;
 			}
        }
        catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return privilegeListVO;
    }
	
	@Override
	public long roleCount(RolesVO roleVo) throws MySalesException {
		// TODO Auto-generated method stub
		long count = 0;
		try {
			 Criteria cr = getSession().createCriteria(RolesVO.class);
			 cr.add(Restrictions.eq("isActive", true));
			 if(null != roleVo.getRoleName() && !roleVo.getRoleName().isEmpty()) {
				 cr.add(Restrictions.ilike("roleName", roleVo.getRoleName().trim(),MatchMode.ANYWHERE));
				 
			 }
			 cr.setProjection(Projections.rowCount());
			 count = (long)cr.uniqueResult();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<RolesVO> getRoleList(RolesVO roleVo) throws MySalesException {
		// TODO Auto-generated method stub
		List<RolesVO> rolevoList = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria cr = session.createCriteria(RolesVO.class);
			if(null != roleVo.getRoleName() && !roleVo.getRoleName().isEmpty()) {
				cr.add(Restrictions.ilike("roleName", roleVo.getRoleName().trim(),MatchMode.ANYWHERE));
			}
			cr.add(Restrictions.eq("isActive", true));
			cr.setFirstResult(roleVo.getRecordIndex());
			cr.setMaxResults(roleVo.getMaxRecord());
			
			rolevoList = cr.list();
			if(null != rolevoList && !rolevoList.isEmpty() && rolevoList.size() > 0) {
				return rolevoList;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rolevoList;
	}
	
	@Override
	public List<PrivilegesVO> listOfPrivilegeAccess(PrivilegesVO privilegesVO) throws MySalesException {
		LOGGER.entry();
		List<PrivilegesVO> privilegeList = new ArrayList<>();
		Session session = getSession();
		try {
			Criteria cr = session.createCriteria(PrivilegesVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.setFirstResult(privilegesVO.getRecordIndex());
			cr.setMaxResults(privilegesVO.getMaxRecord());
			if(null!= privilegesVO.getPrivilegesName() && !privilegesVO.getPrivilegesName().isEmpty()) {
				cr.add(Restrictions.ilike("privilegesName", privilegesVO.getPrivilegesName().trim(), MatchMode.ANYWHERE));
			}
			privilegeList = cr.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfPrvielegeAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfPrvielegeAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegeList;
	}

	@Override
	public long countOfprivilegesVO(PrivilegesVO privilegesVO) {
		LOGGER.entry();
		long countOfprivilegesVO = 0;
	
       
        try {
            Criteria cr = getSession().createCriteria(PrivilegesVO.class);
            if( null!=privilegesVO.getPrivilegesName() && !privilegesVO.getPrivilegesName().isEmpty()) {
                cr.add(Restrictions.ilike("PrivilegesName", privilegesVO.getPrivilegesName().trim(), MatchMode.ANYWHERE));
            }
            cr.add(Restrictions.eq("isDeleted", false));
           
            cr.setProjection(Projections.rowCount());
            countOfprivilegesVO = (long) cr.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e.getMessage());
            }
            LOGGER.info(e.getMessage());
        }finally {
            LOGGER.exit();
        }
        return countOfprivilegesVO;
	}

	@Override
	public long countOfPrivilegeBySearch(PrivilegesVO PrivilegesVO) {
		long countOfPrivilege=0;
		Session session=sessionFactory.getCurrentSession();
		try {
			Criteria criteria=session.createCriteria(PrivilegesVO.class);
			criteria.add(Restrictions.eq("isDeleted", PrivilegesVO.getisDeleted()));
			if(null!=PrivilegesVO.getPrivilegesName()&& !PrivilegesVO.getPrivilegesName().isEmpty()) {
				criteria.add(Restrictions.ilike("privilegesName", PrivilegesVO.getPrivilegesName(), MatchMode.ANYWHERE));
			}
			
			criteria.setProjection(Projections.rowCount());
			countOfPrivilege=(long)criteria.uniqueResult();
		}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search countOfPrivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("Search countOfPrivilege has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		
		return countOfPrivilege;
	}

	@Override
	public long companyCount(AdminLoginBO adminLoginBO) {
		long customersCount = 0;
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(Company.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			criteria.setProjection(Projections.rowCount());
			customersCount = (long) criteria.uniqueResult();
			
		}catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}
		}
		return customersCount;
	}

	@Override
	public long countOfPrivilegesBySearch(PrivilegesVO privilegesVO) {
		LOGGER.entry();
        long countOfPrivileges=0;
        Session session=sessionFactory.getCurrentSession();
        try {
            Criteria criteria=session.createCriteria(PrivilegesVO.class);
            criteria.add(Restrictions.eq("isDeleted", false));
            
//            if(null != rolesVO.getCompanyId() && 0< rolesVO.getCompanyId() ) {
//                criteria.add(companyValidation(rolesVO.getCompanyId())); //company condition
//                }
            if( null!=privilegesVO.getPrivilegesName() && !privilegesVO.getPrivilegesName().isEmpty()) {
                criteria.add(Restrictions.ilike("privilegesName", privilegesVO.getPrivilegesName().trim(), MatchMode.ANYWHERE));
            }
                criteria.setProjection(Projections.rowCount());
                countOfPrivileges=(long)criteria.uniqueResult();
                
                if(0< countOfPrivileges) {
                    return countOfPrivileges;
                }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }    
        return countOfPrivileges;
	}

	@Override
	public long salesCount(AdminLoginBO adminLoginBO) {
		// TODO Auto-generated method stub
		long salesCount = 0;
		SalesOrderVO salesOrderVO = new SalesOrderVO();
		try {
			Criteria cr = getSession().createCriteria(SalesOrderVO.class);
			if(null != salesOrderVO.getCompanyId()&& 0< salesOrderVO.getCompanyId() ) {
			cr.add(companyValidation(salesOrderVO.getCompanyId())); // Companey
			}
			if (null != adminLoginBO && 0 < adminLoginBO.getCompany().getCompanyId()) {
				cr.add(companyValidation(adminLoginBO.getCompany().getCompanyId()));
			}
			if(null != salesOrderVO.getSalesOrderNo()&& !salesOrderVO.getSalesOrderNo().isEmpty()) {
				cr.add(Restrictions.ilike("salesOrderNo", salesOrderVO.getSalesOrderNo().trim(),MatchMode.ANYWHERE));
			}
			cr.setProjection(Projections.rowCount());
			salesCount = (long) cr.uniqueResult();

			
		}catch (final HibernateException he) {
			he.printStackTrace();
			if (AdminDAOImpl.LOGGER.isDebugEnabled()) {
				AdminDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_DELETE_FAIL + he);
			}
		}
		return salesCount;
	}




}
