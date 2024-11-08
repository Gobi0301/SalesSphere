package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.CampaignBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.utils.ErrorCodes;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.Campaign;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.User;

@Repository("campaignDAOImpl")
public class CampaignDAOImpl extends BaseDao implements CampaignDAO {

	public CampaignDAOImpl() throws MySalesException {
		super();
	}

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(CampaignDAOImpl.class);

	public User retrieveParticularUser(User user) {
		LOGGER.entry();
		User users = new User();
		try {
			Criteria criteria = getSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("id", user.getId()));
			criteria.add(Restrictions.eq("isDelete", false));
			users = (User) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return users;
	}

	// Campaign create Part
	@Override
	public Campaign saveCompaign(Campaign campaign) {
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		try {
			int id = (int) session.save(campaign);
//			session.flush();
			if (0 < id) {
				campaign.setCampaignId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Campaign creation failed:" + e.getMessage());
			}
			LOGGER.info("Campaign creation failed:" + e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return campaign;
	}

	// record counting purpose - Campaign View part
	@Override
	public long getListOfCompanyCampaign(Campaign campaignVo) {
		LOGGER.entry();
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Campaign.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.setProjection(Projections.rowCount());
			long count = (long) criteria.uniqueResult();
			if (0 != count) {
				return count;
			}
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("FROM INFO: Exception \t" + e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("FROM DEBUG : Exception \t" + e);
			}
		} finally {
			LOGGER.exit();
		}
		return 0;
	}

	// Total campaign list purpose call - Campaign View part & Search part
	@Override
	public List<Campaign> listOfCampaigns(Campaign campaignVO) {
		LOGGER.entry();
		List<Campaign> pageCampaignList = new ArrayList<Campaign>();
		try {
			Criteria criteria = getSession().createCriteria(Campaign.class);
			criteria.add(Restrictions.eq("isDelete", false));
			if (null != campaignVO.getCompanyId() && 0< campaignVO.getCompanyId()) {
			criteria.add(companyValidation(campaignVO.getCompanyId()));  // Company base retriew
			}
			criteria.setFirstResult(campaignVO.getRecordIndex());
			criteria.setMaxResults(campaignVO.getMaxRecord());
			if (null != campaignVO.getCampaignName() && !campaignVO.getCampaignName().isEmpty()) {
				criteria.add(Restrictions.ilike("campaignName", campaignVO.getCampaignName().trim(), MatchMode.ANYWHERE));
			}
			if (null != campaignVO.getCampaignMode() && !campaignVO.getCampaignMode().isEmpty()) {
				criteria.add(Restrictions.ilike("campaignMode", campaignVO.getCampaignMode().trim(), MatchMode.ANYWHERE));
			}
			if (null != campaignVO && null != campaignVO.getProductServiceVO() && 0<campaignVO.getProductServiceVO().getServiceId()) {
				criteria.add(Restrictions.eq("productServiceVO.serviceId",campaignVO.getProductServiceVO().getServiceId())); 
			}
			
//			if (null != campaignVO && null != campaignVO.getUser() && campaignVO.getUser().getId()>0) {
//				criteria.add(Restrictions.eq("user.id",campaignVO.getUser().getId())); 
//			}
			/*
			 * // LodinId based retriew if (null != campaignVO &&
			 * campaignVO.getCreatedBy()>0) {
			 * criteria.add(Restrictions.eq("createdBy",campaignVO.getCreatedBy())); }
			 */
			
			criteria.addOrder(Order.desc("campaignId"));
			pageCampaignList = criteria.list();
 			if(null!=pageCampaignList && !pageCampaignList.isEmpty() && pageCampaignList.size()>0) {
				return pageCampaignList;
			}
		}catch(Exception ex){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("List of campaigns has failed:" + ex.getMessage());
			}
			LOGGER.info("List of campaigns has failed:" + ex.getMessage());
		}finally{
			LOGGER.exit();
		}
		return pageCampaignList;
	}

	// Campaign Search Part - count 
	@Override
	public long campaignforObject(Campaign campaignVo) {
		LOGGER.entry();
		try{
			Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Campaign.class);
			criteria.add(Restrictions.eq("isDelete",false));
			if(null!=campaignVo.getCompanyId()&& 0< campaignVo.getCompanyId()){
			criteria.add(companyValidation(campaignVo.getCompanyId()));  // CompanyId
			}
			if(null!=campaignVo) {
				if(null!=campaignVo.getCampaignName()&&!campaignVo.getCampaignName().isEmpty()){
					criteria.add(Restrictions.ilike("campaignName", campaignVo.getCampaignName(),MatchMode.ANYWHERE));
				}
				if(null!=campaignVo.getCampaignMode()&&!campaignVo.getCampaignMode().isEmpty()){
					criteria.add(Restrictions.ilike("campaignMode", campaignVo.getCampaignMode(),MatchMode.ANYWHERE));
				}
				if(null!=campaignVo.getProductServiceVO()&&0 <campaignVo.getProductServiceVO().getServiceId()){
					criteria.add(Restrictions.eq("productServiceVO.serviceId", campaignVo.getProductServiceVO().getServiceId()));
				}
			}
			criteria.setProjection(Projections.rowCount());
			long count= (long) criteria.uniqueResult();
			if(0!=count){
				return count;
			}
		}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search Campaign counting has failed:" + ex.getMessage());
			}
			LOGGER.info("Search Campaign counting has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return 0;
	}

	@Override
	public Campaign getObject(int campaignId) {
		LOGGER.entry();
		Campaign campaign = new Campaign();
		try {
			Criteria criteria = getSession().createCriteria(Campaign.class);
			criteria.add(Restrictions.eq("campaignId", campaignId));
			campaign = (Campaign) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieve user has failed:" + ex.getMessage());
			}
			LOGGER.info("Retrieve user has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return campaign;
	}

	@Override
	public Campaign getCampaignId(CampaignBO campaignBO) {
		Campaign campaign = (Campaign) getSession().get(Campaign.class, campaignBO.getCampaignId());
		return campaign;
	}
	// Campaign Edit - Update Part
	@Override
	public boolean updateCampaign(Campaign campaign) {
		LOGGER.entry();
		try {
			Session session = getSession();
			session.saveOrUpdate(campaign);
			getSession().flush();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit-update Campaign has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit-update Campaign has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return true;
	}

	// Campaign delete part 
	@Override
	public boolean deleteCampaign(int idcam) {
		LOGGER.entry();
		boolean status = false;
		try {
			int result = 0;
			final String deleteQuery = "UPDATE Campaign S set" + " S.isDelete = :isDelete"
					+ " WHERE S.campaignId = :campaignId";
			final Query query = getSession().createQuery(deleteQuery);
			query.setParameter("isDelete", true);
			query.setParameter("campaignId", idcam);
			result = query.executeUpdate();
			if (0 < result) {
				status = true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Campaign has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Campaign has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();

		}
		return status;

	}

	// Campaign view detail part
	@Override
	public Campaign viewCampaignDetails(Campaign campaignVO) {
		LOGGER.entry();
		try{
			Criteria cr= getSession().createCriteria(Campaign.class);
			cr.add(Restrictions.eq("isDelete", false));
			cr.add(Restrictions.eq("campaignId", campaignVO.getCampaignId()));
			campaignVO=(Campaign) cr.uniqueResult();
		}catch(Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Campaign view detail has failed:" + ex.getMessage());
			}LOGGER.info("Campaign view detail has failed:" + ex.getMessage());
		}
		finally{
			LOGGER.exit();
		}
		return campaignVO;
	}





	@Override
	public User authendicate(String string, String emailAddress) throws MySalesException {
		CampaignDAOImpl.LOGGER.entry();
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
			if (CampaignDAOImpl.LOGGER.isDebugEnabled()) {
				CampaignDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			CampaignDAOImpl.LOGGER.exit();
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

		CampaignDAOImpl.LOGGER.entry();
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
			CampaignDAOImpl.LOGGER.exit();
		}

		return accessLogStatus;

	}

	@Override
	public Campaign getCampaignObjectByName(Campaign campaignVO) {

		Session session=sessionFactory.getCurrentSession();
		try {
			Criteria criteria=session.createCriteria(Campaign.class);
			if(null!=campaignVO.getCampaignName()) {
				criteria.add(Restrictions.eq("campaignName", campaignVO.getCampaignName()));
			}
			campaignVO=(Campaign)criteria.uniqueResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		return campaignVO;
	}

	// Campaign Tracking status	
	@Override
	public ActivityVO saveTracking(ActivityVO activityvo) {
		LOGGER.entry();
		try{	
			Session session = getSession();
			long id=(long) session.save(activityvo);
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
	public List<ActivityVO> retrieveTracking(long campaignId,Long companyId) {
		LOGGER.entry();
		List<ActivityVO> campaignactivityList=null;
		try {
			Criteria criteria = getSession().createCriteria(ActivityVO.class);
			criteria.add(Restrictions.eq("entitytype","Campaign"));	
			criteria.add(Restrictions.eq("companyId",companyId));	
			criteria.add(Restrictions.eq("entityid",campaignId));
			if (null != criteria.list() && criteria.list().size() > 0) {
				campaignactivityList = criteria.list();
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
		return campaignactivityList;
	}
	

	@Override
	public long retriveCount() {
		Session session=sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(ActivityVO.class);
		cr.setProjection(Projections.rowCount());
		long activityd = (long) cr.uniqueResult();
		if(0<activityd) {
			return activityd;
		}
		return 0;
	}

	@Override
	public ActivityVO campaignTrackingStatus(long updatecampaignId) {
		Session session=sessionFactory.getCurrentSession();
		ActivityVO activityVO = (ActivityVO) getSession().get(ActivityVO.class, updatecampaignId);
		
		if(null!=activityVO) {
			
			return activityVO;
		}
		
		return null;
	}

	@Override
	public Campaign retriveCampaignById(Campaign campaignVO) {
		 LOGGER.entry();
		 try {
			 Criteria cr=sessionFactory.getCurrentSession().createCriteria(Campaign.class);
			 cr.add(Restrictions.eq("serviceId", campaignVO.getCampaignId()));
			 cr.add(companyValidation(campaignVO.getCompanyId())); // companyId
			 campaignVO=(Campaign)cr.uniqueResult();
		 }catch (Exception ex) {
			 if (LOGGER.isDebugEnabled()) {
				 LOGGER.debug("Product retriveServiceById has failed:" + ex.getMessage());
			 }
			 LOGGER.info("Product retriveServiceById has failed:" + ex.getMessage());
		 }finally {
			 LOGGER.exit();
		 }
		 return campaignVO;   
	}

	@Override
	public List<Campaign> findAllProducts(Campaign campaignVO) {
		 List<Campaign> list = new ArrayList<Campaign>();
		 Session session=sessionFactory.getCurrentSession();
		 try {
			 Criteria criteria=session.createCriteria(Campaign.class);
			 criteria.add(Restrictions.eq("isDelete", false));
			 
			 if(null!= campaignVO && null!= campaignVO.getCompanyId() && 0 < campaignVO.getCompanyId()) {
				 criteria.add(Restrictions.eq("companyId", campaignVO.getCompanyId()));
			 }
			 list = criteria.list();
				if(null!= list) {
					return list;
				}
			 
		 }catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


}