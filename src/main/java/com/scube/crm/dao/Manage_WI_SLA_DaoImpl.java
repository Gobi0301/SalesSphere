package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

 

import org.hibernate.Criteria;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.WorkItemSLAVO;
import com.scube.crm.vo.SlaVO;
import com.scube.crm.vo.TaskManagementVO;

@Repository
public class Manage_WI_SLA_DaoImpl implements Manage_WI_SLA_Dao{

	
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(Manage_WI_SLA_DaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public long create(WorkItemSLAVO manageVO) {
		LOGGER.entry();

		long id = 0;
		Session session = sessionFactory.getCurrentSession();
		try {
			if (null != manageVO) {
				id = (long) session.save(manageVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return id;
	}

	@Override
	public long retrieveCount(WorkItemSLAVO manageVo) {
		LOGGER.entry();
		long count = 0;
		try {
			List<WorkItemSLAVO> list = new ArrayList<WorkItemSLAVO>();
			Criteria cr = getSession().createCriteria(WorkItemSLAVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			//cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			if(null != manageVo.getCompanyId()&& 0< manageVo.getCompanyId()) {
				cr.add(Restrictions.eq("companyId", manageVo.getCompanyId()));
			}
			if( null!=manageVo.getSlaCode()&& !manageVo.getSlaCode().isEmpty()) {
				cr.add(Restrictions.ilike("wISLACode", manageVo.getSlaCode().trim(), MatchMode.ANYWHERE));
			}
			
			
			
			
		    cr.setProjection(Projections.count("wISLACode"));
		//	cr.setProjection(Projections.projectionList().add(Projections.distinct(Projections.property("wISLACode"))));
			count = (long) cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return count;
	}
	
	@Override
	public List<WorkItemSLAVO> findAll(WorkItemSLAVO manageVO) {
		List<WorkItemSLAVO> list = new ArrayList<WorkItemSLAVO>();
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(WorkItemSLAVO.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.setFirstResult(manageVO.getRecordIndex());
			criteria.setMaxResults(manageVO.getMaxRecord());
			if (null != manageVO.getwISLACode() && !manageVO.getwISLACode().isEmpty()) {
				criteria.add(Restrictions.ilike("wISLACode", manageVO.getwISLACode().trim(), MatchMode.ANYWHERE));
			}
			if(null != manageVO.getCompanyId()&& 0< manageVO.getCompanyId()) {
				criteria.add(Restrictions.eq("companyId", manageVO.getCompanyId()));
			}

//        ProjectionList projection1 = Projections.projectionList();
//        ProjectionList projection2 = Projections.projectionList();
//       
//        projection2.add(Projections.distinct(projection1.add(Projections.property("wISLACode"),"wISLACode")));
//        projection2.add(Projections.property("slaVO"),"slaVO");
//        projection2.add(Projections.property("workItemVO"),"workItemVO");
//        criteria.setProjection(projection2);
//        //  projection.add(Projections.property("slaVO.slaId"));
//        //Add as many columns as you want using Projection
//        //projection.add(Projections.property("msgTo"));
//        // criteria.setProjection(Projections.distinct(projection));
//			//list = criteria.setProjection(Projections.projectionList().add(Projections.distinct(Projections.property("wISLACode")))).list();
//			//criteria.setProjection(Projections.distinct(Projections.property("wISLACode")));
//			criteria.setResultTransformer(Transformers.aliasToBean(WorkItemSLAVO.class));
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}

		return list;
	}

	@Override
	public WorkItemSLAVO getFindById(WorkItemSLAVO manageVO) {

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(WorkItemSLAVO.class);
			criteria.add(Restrictions.eq("manageId", manageVO.getManageId()));
			manageVO = (WorkItemSLAVO) criteria.uniqueResult();

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Manage_WI_SLA_ has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Manage_WI_SLA_ has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return manageVO;
	}

	@Override
	public WorkItemSLAVO update(WorkItemSLAVO manageVO) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null != manageVO) {

				session.saveOrUpdate(manageVO);
				if (null != manageVO) {
					System.out.println("success");
				}
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update Manage_WI_SLA has failed:" + ex.getMessage());
			}
			LOGGER.info("Update Task Manage_WI_SLA failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return manageVO;

	}

	@Override
	public Boolean delete(WorkItemSLAVO manageVo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			WorkItemSLAVO vo = (WorkItemSLAVO) session.get(WorkItemSLAVO.class, manageVo.getManageId());
			vo.setisDelete(true);
			if (0 < vo.getManageId()) {
				session.saveOrUpdate(vo);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}

		return false;
	}

	@Override
	public List<WorkItemSLAVO> findSLAByWorkItemId(long workItemId) {
		
		Session session = sessionFactory.getCurrentSession();
		Long targetWorkItemId = workItemId;
		Criteria criteria = session.createCriteria(WorkItemSLAVO.class, "sla");
		criteria.createAlias("sla.workItemVO", "workItemVO", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("workItemVO.workItemId", targetWorkItemId));
		List<WorkItemSLAVO> slalist =criteria.list();
		
		
		return slalist;
		
		 
	}

	@Override
	public boolean checkWISLACode(String slacode) {
		LOGGER.entry();
		WorkItemSLAVO slaVO=null;
			try {
				Session session=sessionFactory.getCurrentSession();
				Criteria criteria = session.createCriteria(WorkItemSLAVO.class);
				criteria.add(Restrictions.eq("wISLACode",slacode));
				List slaVOList = criteria.list();
			
			if(null!=slaVOList && 0<slaVOList.size()) {
				return true;
			}else {
				return false;
			}
			}catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("checkSlaCode has failed:" + ex.getMessage());
				}
				LOGGER.info("checkSlaCode has failed:" + ex.getMessage());
			} finally {
				LOGGER.exit();
			}
			return false;
		}
}
