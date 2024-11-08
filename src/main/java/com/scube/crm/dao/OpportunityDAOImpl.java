package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.ClientBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.utils.ErrorCodes;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.GstVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.Leads;
import com.scube.crm.vo.Opportunity;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SalesOrderVO;


@Repository
public class OpportunityDAOImpl extends BaseDao  implements OpportunityDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(OpportunityDAOImpl.class);

	@Override
	public List<Leads> retrieveLeads(LeadsBO LeadsBO) throws MySalesException {
		OpportunityDAOImpl.LOGGER.entry();
		List<Leads> leadListDO = new ArrayList<Leads>();
		
		try {
			Criteria cr = getSession().createCriteria(Leads.class);
			cr.add(Restrictions.eq("isDelete", false));
			cr.add(companyValidation(LeadsBO.getCompanyId()));
			leadListDO = cr.list();
			
		}catch (final HibernateException he) {
			he.printStackTrace();
			if (OpportunityDAOImpl.LOGGER.isDebugEnabled()) {
				OpportunityDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			OpportunityDAOImpl.LOGGER.exit();
		}

		return leadListDO;
	}

	@Override
	public Long create(Opportunity opportunityVO) throws MySalesException {
		OpportunityDAOImpl.LOGGER.entry();
		Long status = null;
		
		try {
			Session session = getSession();
			if(opportunityVO != null) {
				status = (Long) session.save(opportunityVO);
			}
		}catch (final HibernateException he) {
			he.printStackTrace();
			if (OpportunityDAOImpl.LOGGER.isDebugEnabled()) {
				OpportunityDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			OpportunityDAOImpl.LOGGER.exit();
		}

		return status;
	}

	public long Oppocount(Opportunity opportunityVO) throws MySalesException {
		OpportunityDAOImpl.LOGGER.entry();
		long count = 0;
		try {
			
			  Criteria cr = getSession().createCriteria(Opportunity.class);
			  cr.add(Restrictions.eq("isDelete", false));
			  if(null != opportunityVO.getCompanyId()&& 0< opportunityVO.getCompanyId()) {
			  cr.add(companyValidation(opportunityVO.getCompanyId())); //company condition
			  }
			  if (null != opportunityVO.getFirstName() && !opportunityVO.getFirstName().isEmpty()) {
				  cr.add(Restrictions.ilike("firstName", opportunityVO.getFirstName(), MatchMode.ANYWHERE));
				}
			  cr.setProjection(Projections.rowCount());
			  count = (long) cr.uniqueResult();
			 
		} catch (Exception he) {
			he.printStackTrace();
			if (OpportunityDAOImpl.LOGGER.isDebugEnabled()) {
				OpportunityDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			OpportunityDAOImpl.LOGGER.exit();
		}

		return count;
	}

	@Override
	public List<Opportunity> view(OpportunityBO opportunityBO) throws MySalesException {
		OpportunityDAOImpl.LOGGER.entry();
		List<Opportunity> VOList = new ArrayList<Opportunity>();
		try {
			Criteria criteria = getSession().createCriteria(Opportunity.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.setFirstResult(opportunityBO.getRecordIndex());
			criteria.setMaxResults(opportunityBO.getMaxRecord());
			if(null != opportunityBO.getCompanyId() && 0< opportunityBO.getCompanyId() ) {
			    criteria.add(companyValidation(opportunityBO.getCompanyId()));//company condition
				}
			if(null != opportunityBO.getFirstName() && !opportunityBO.getFirstName().isEmpty()) {
				criteria.add(Restrictions.ilike("firstName", opportunityBO.getFirstName().trim(), MatchMode.ANYWHERE));
			}
			VOList = criteria.list();
		}catch (final HibernateException he) {
			he.printStackTrace();
			if (OpportunityDAOImpl.LOGGER.isDebugEnabled()) {
				OpportunityDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			OpportunityDAOImpl.LOGGER.exit();
		}
		return VOList;
	}

	@Override
	public long searchCount(Opportunity vo) throws MySalesException {
		OpportunityDAOImpl.LOGGER.entry();
		long count = 0;
		try {
			Criteria criteria = getSession().createCriteria(Opportunity.class);
			criteria.add(Restrictions.eq("isDelete", false));
			if(null != vo.getFirstName() && !vo.getFirstName().isEmpty()) {
				criteria.add(Restrictions.ilike("firstName", vo.getFirstName().trim(), MatchMode.ANYWHERE));
			}
			if(null != vo.getLastName() && !vo.getLastName().isEmpty()) {
				criteria.add(Restrictions.ilike("lastName", vo.getLastName().trim(), MatchMode.ANYWHERE));
			}
			
			if(null != vo.getSalesStage() && !vo.getSalesStage().isEmpty()) {
				criteria.add(Restrictions.ilike("salesStage", vo.getSalesStage().trim(), MatchMode.ANYWHERE));
			}
			
			if(vo.getProductService().getServiceId() > 0) {
				criteria.add(Restrictions.eq("productService.serviceId", vo.getProductService().getServiceId()));
				
			}
			if(null != vo.getCompanyId() && 0 < vo.getCompanyId()) {
				criteria.add(Restrictions.eq("companyId", vo.getCompanyId()));
			}
			
			criteria.setProjection(Projections.rowCount());
			
			count = (long) criteria.uniqueResult();
 		} catch (final HibernateException he) {
			he.printStackTrace();
			if (OpportunityDAOImpl.LOGGER.isDebugEnabled()) {
				OpportunityDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			OpportunityDAOImpl.LOGGER.exit();
		}
		return count;
	}

	@Override
	public List<Opportunity> search(Opportunity vo) throws MySalesException {
		OpportunityDAOImpl.LOGGER.entry();
		List<Opportunity> VOList = new ArrayList<Opportunity>();
		try {
			Session session = sessionFactory.getCurrentSession();
		    Criteria criteria=session.createCriteria(Opportunity.class);
		    criteria.add(Restrictions.eq("isDelete", false));
		    criteria.setFirstResult(vo.getRecordIndex());
			criteria.setMaxResults(vo.getMaxRecord());	
			if(null != vo.getFirstName() && !vo.getFirstName().isEmpty()) {
				criteria.add(Restrictions.ilike("firstName", vo.getFirstName().trim(), MatchMode.ANYWHERE));
			}
			if(null != vo.getLastName() && !vo.getLastName().isEmpty()) {
				criteria.add(Restrictions.ilike("lastName", vo.getLastName().trim(), MatchMode.ANYWHERE));
			}
			
			if(0 < vo.getProductService().getServiceId()) {
				criteria.add(Restrictions.eq("productService.serviceId", vo.getProductService().getServiceId()));
			}
			if(null != vo.getSalesStage() && !vo.getSalesStage().isEmpty()) {
				criteria.add(Restrictions.ilike("salesStage", vo.getSalesStage().trim(), MatchMode.ANYWHERE));
			}
			if(null != vo.getCompanyId() && 0< vo.getCompanyId()) {
				criteria.add(Restrictions.eq("companyId", vo.getCompanyId()));
			}
			VOList = criteria.list();
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (OpportunityDAOImpl.LOGGER.isDebugEnabled()) {
				OpportunityDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			OpportunityDAOImpl.LOGGER.exit();
		}
		return VOList;
	}

	@Override
	public Opportunity getById(long id) throws MySalesException {
		OpportunityDAOImpl.LOGGER.entry();
		Opportunity DOProfile = new Opportunity();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Opportunity.class);
			criteria.add(Restrictions.eq("opportunityId", id));
			DOProfile = (Opportunity) criteria.uniqueResult();
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (OpportunityDAOImpl.LOGGER.isDebugEnabled()) {
				OpportunityDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			OpportunityDAOImpl.LOGGER.exit();
		}
		return DOProfile;
	}

	@Override
	public boolean update(Opportunity vo) throws MySalesException {
		OpportunityDAOImpl.LOGGER.entry();
		
		try {
			Session session = getSession();
			if(vo != null) {
				session.update(vo);
			}
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (OpportunityDAOImpl.LOGGER.isDebugEnabled()) {
				OpportunityDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			OpportunityDAOImpl.LOGGER.exit();
		}
		return true;
	}

	@Override
	public int delete(long id) throws MySalesException {
		OpportunityDAOImpl.LOGGER.entry();
		int status = 0;
		try {
			Session session = getSession();
			Query query = session.createQuery("update Opportunity set isDelete=:isDelete where OPPORTUNITY_ID=:OPPORTUNITY_ID");
			query.setParameter("isDelete", true);
			query.setParameter("OPPORTUNITY_ID" , id);
			status = query.executeUpdate();
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (OpportunityDAOImpl.LOGGER.isDebugEnabled()) {
				OpportunityDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			OpportunityDAOImpl.LOGGER.exit();
		}
		return status;
	}


	@Override
	public Map<Integer, String> retrieveAccounts(AccountVO accountVO) throws MySalesException {
		OpportunityDAOImpl.LOGGER.entry();
		Map<Integer, String> DOMap = new HashMap<Integer , String>();
		try {
			Criteria criteria = getSession().createCriteria(AccountVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			criteria.add(companyValidation(accountVO.getCompanyId())); //company condition
			Projection p1 = Projections.property("accountId");
			Projection p2 = Projections.property("accountName");
			ProjectionList list = Projections.projectionList();
			list.add(p1);
			list.add(p2);
			criteria.setProjection(list);
			List<Object[]> List = criteria.list();
			for(Object[] x : List) {
				Integer id = (Integer) x[0];
				String name = (String) x[1];
				DOMap.put(id, name);
			}
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (OpportunityDAOImpl.LOGGER.isDebugEnabled()) {
				OpportunityDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			OpportunityDAOImpl.LOGGER.exit();
		}
		return DOMap;
	}

	@Override
	public Opportunity createOpportunity(Opportunity opportunity) throws MySalesException {
		
		LOGGER.entry();
		try
		{
			Session session = getSession();
			session.saveOrUpdate(opportunity);
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Opportunity creation failed:" + e.getMessage());
			}
			LOGGER.info("Opportunity creation failed:" + e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return opportunity;
	}

	
	@Override
	public List<Opportunity> listOfopportunityByPagination(Opportunity opportunityVO) {
		// TODO Auto-generated method stub

				List<Opportunity> opportunityVOList=new ArrayList<>();
				Session session=sessionFactory.getCurrentSession();
				try {
					Criteria criteria=session.createCriteria(Opportunity.class);
					criteria.add(Restrictions.eq("isDelete", opportunityVO.getIsDelete()));
					criteria.setFirstResult(opportunityVO.getRecordIndex());
					criteria.setMaxResults(opportunityVO.getMaxRecord());
					criteria.addOrder(Order.desc("id"));
					opportunityVOList=criteria.list();
				}
				catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				}

				return opportunityVOList;
			}

	@Override
	public String getSalesOrderNo(ClientBO clientBO) {
		String sNo="SONo1";
		Session session = getSession();
		Criteria criteria = session.createCriteria(SalesOrderVO.class);
		criteria.add(companyValidation(clientBO .getCompanyId())); //company condition
		criteria.addOrder(Order.desc("salesOrderId"));
		List<SalesOrderVO> salesOrderVO = criteria.list();
		if(salesOrderVO !=null && !salesOrderVO.isEmpty() && salesOrderVO.size()!=0) {
			SalesOrderVO salesVO = salesOrderVO.get(0);
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

	@Override
	public List<InventoryVO> getProductList(InventoryVO InventoryVO) {
		List<InventoryVO> productServiceVO=new ArrayList<>();
		try {

			Session session = getSession();
			Criteria criteria = session.createCriteria(InventoryVO.class);
			criteria.add(Restrictions.eq("isDelete", false));
			/* criteria.add(companyMappingValidation (InventoryVO.getCompanyId())); */
			if(InventoryVO.getCompanyId() != null) {
				criteria.add(Restrictions.eq("companyId",InventoryVO.getCompanyId() ));
				}
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
	
	@Override
	public List<Opportunity> getProductListforSearch(InventoryVO InventoryVO) {
		List<Opportunity> opportunityVO=new ArrayList<>();
		try {

			Session session = getSession();
			Criteria criteria = session.createCriteria(Opportunity.class);
			criteria.add(Restrictions.eq("isDelete", false));
			/* criteria.add(companyMappingValidation (InventoryVO.getCompanyId())); */
			if(InventoryVO.getCompanyId() != null) {
				criteria.add(Restrictions.eq("companyId",InventoryVO.getCompanyId() ));
				}
			opportunityVO=criteria.list();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Product List failed:"+ e.getMessage());
			}
			LOGGER.info("Product List failed:"+ e.getMessage());

		} 
		return opportunityVO;
	}

	@Override
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

	@Override
	public long createSalesOrder(SalesOrderVO salesOrderVO) {
		LOGGER.entry();
		long status = 0;
		try {
			Session sessions = sessionFactory.openSession();
			if(null!= salesOrderVO) {
				 status=(long) sessions.save(salesOrderVO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			LOGGER.exit();
		}
		return status;
	}



	@Override
	public List<ActivityVO> retrieveTracking(long opportunityId,Long CompanyId) {
		LOGGER.entry();
		List<ActivityVO> opportunityactivityList=null;
		try {
			Criteria criteria = getSession().createCriteria(ActivityVO.class);
			criteria.add(Restrictions.eq("entitytype", "Opportunity"));
			criteria.add(Restrictions.eq("entityid", opportunityId));
			criteria.add(Restrictions.eq("companyId", CompanyId));
			if (null != criteria.list() && criteria.list().size() > 0) {
				opportunityactivityList = criteria.list();
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
		return opportunityactivityList;
	}

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
	public PriceBookVO getPriceBookPrice(Integer pricebook_Ids) {
		PriceBookVO priceBookVO=new PriceBookVO();
		try {

			Session session = getSession();
			Criteria criteria = session.createCriteria(PriceBookVO.class);
			criteria.add(Restrictions.eq("priceBookId", pricebook_Ids));
			priceBookVO=(PriceBookVO) criteria.uniqueResult();
			if (null != priceBookVO) {
				return priceBookVO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Product List failed:"+ e.getMessage());
			}
			LOGGER.info("Product List failed:"+ e.getMessage());

		} 
		return priceBookVO;
	}

	@Override
	public PriceBookVO getProductData(Integer product_Ids) {
		PriceBookVO priceBookVO=new PriceBookVO();
		try {

			Session session = getSession();
			Criteria criteria = session.createCriteria(PriceBookVO.class);
			criteria.add(Restrictions.eq("serviceId", product_Ids));
			priceBookVO=(PriceBookVO) criteria.uniqueResult();
			if (null != priceBookVO) {
				return priceBookVO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Product List failed:"+ e.getMessage());
			}
			LOGGER.info("Product List failed:"+ e.getMessage());

		} 
		return priceBookVO;
	}

	@Override
	public GstVO getProductgst(long product_Ids) {
		GstVO gstVO=new GstVO();
		try {

			Session session = getSession();
			Criteria criteria = session.createCriteria(GstVO.class);
			criteria.add(Restrictions.eq("product.serviceId", product_Ids));
			gstVO= (GstVO) criteria.list().get(0);
			if (null != gstVO) {
				return gstVO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Product List failed:"+ e.getMessage());
			}
			LOGGER.info("Product List failed:"+ e.getMessage());

		} 
		return gstVO;

	}

	@Override
	public long activityCount(Opportunity vo) throws MySalesException {
		long count = 0;
		try {
			Criteria criteria = getSession().createCriteria(ActivityVO.class);
			//criteria.add(Restrictions.eq("isDelete", false));
			criteria.add(Restrictions.eq("entitytype", "Opportunity"));
			if(null != vo.getCompanyId() && 0 < vo.getCompanyId()) {
				criteria.add(Restrictions.eq("companyId", vo.getCompanyId()));
			}
			
			criteria.setProjection(Projections.rowCount());
			
			count = (long) criteria.uniqueResult();
 		} catch (final HibernateException he) {
			he.printStackTrace();
			if (OpportunityDAOImpl.LOGGER.isDebugEnabled()) {
				OpportunityDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			OpportunityDAOImpl.LOGGER.exit();
		}
		return count;
	}
}
