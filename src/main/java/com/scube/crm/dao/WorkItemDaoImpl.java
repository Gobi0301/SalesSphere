package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.AccessVo;
import com.scube.crm.vo.WorkItemSkillsVO;
import com.scube.crm.vo.WorkItemVO;

@Repository
public class WorkItemDaoImpl implements WorkItemDao {
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(WorkItemDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public long create(WorkItemVO workItemVO) throws MySalesException {
		LOGGER.entry();

		long id = 0;
		Session session = sessionFactory.getCurrentSession();
		try {
			if (null != workItemVO) {
				id = (long) session.save(workItemVO);
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
	public long retrieveCount(WorkItemVO workItemVO) throws MySalesException {
		LOGGER.entry();
		long count = 0;
		try {

			Criteria cr = getSession().createCriteria(WorkItemVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			if(null != workItemVO.getCompanyId()&& 0< workItemVO.getCompanyId()) {
			cr.add(Restrictions.eq("companyId", workItemVO.getCompanyId()));
			}
			if(null != workItemVO.getWorkItemCode() && !workItemVO.getWorkItemCode() .isEmpty()) {
				cr.add(Restrictions.ilike("workItemCode", workItemVO.getWorkItemCode().trim(), MatchMode.ANYWHERE));
			}
			cr.setProjection(Projections.rowCount());
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
	public List<WorkItemVO> findAll(WorkItemVO workItemVO) throws MySalesException {

		List<WorkItemVO> list = new ArrayList<WorkItemVO>();
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(WorkItemVO.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.setFirstResult(workItemVO.getRecordIndex());
			criteria.setMaxResults(workItemVO.getMaxRecord());
			
			if (null != workItemVO.getWorkItemCode() && !workItemVO.getWorkItemCode().isEmpty()) {
				criteria.add(Restrictions.ilike("workItemCode", workItemVO.getWorkItemCode().trim(), MatchMode.ANYWHERE));
			}
			
			if (0< workItemVO.getWorkItemId()) {
				criteria.add(Restrictions.eq("workItemId", workItemVO.getWorkItemId()));
			}
			if(null != workItemVO.getCompanyId()&& 0< workItemVO.getCompanyId()) {
			criteria.add(Restrictions.eq("companyId", workItemVO.getCompanyId()));
			}
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
	public WorkItemVO getfindById(WorkItemVO workItemVO) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(WorkItemVO.class);
			criteria.add(Restrictions.eq("workItemId", workItemVO.getWorkItemId()));
			workItemVO = (WorkItemVO) criteria.uniqueResult();

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit WorkItem has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit WorkItem has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return workItemVO;
	}

	@Override
	public WorkItemVO update(WorkItemVO workItemVO) {

		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null != workItemVO) {

				session.saveOrUpdate(workItemVO);
				if (null != workItemVO) {
					System.out.println("success");
				}
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update workItem has failed:" + ex.getMessage());
			}
			LOGGER.info("Update workItem has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return workItemVO;
	}

	@Override
	public Boolean delete(WorkItemVO workItemVO) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			WorkItemVO vo = (WorkItemVO) session.get(WorkItemVO.class, workItemVO.getWorkItemId());
			vo.setDelete(true);
			if (0 < vo.getWorkItemId()) {
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
	public boolean checkWorkitemCode(String workItemCode,long id) throws MySalesException {
		LOGGER.entry();
		List<WorkItemVO> workItemVO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(WorkItemVO.class);
			criteria.add(Restrictions.eq("workItemCode", workItemCode));
			//criteria.add(Restrictions.eq("createdBy", id));
			workItemVO = (List<WorkItemVO>) criteria.list();

			if (null != workItemVO && !workItemVO.isEmpty() && workItemVO.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkAccessName has failed:" + ex.getMessage());
			}
			LOGGER.info("checkAccessName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public List<WorkItemVO> findAllWorkitem(WorkItemVO vo) {
		
		List<WorkItemVO> list = new ArrayList<WorkItemVO>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(WorkItemVO.class);
			criteria.add(Restrictions.eq("isDelete", false));
			if(null != vo.getCompanyId() && 0< vo.getCompanyId()) {
				criteria.add(Restrictions.eq("companyId", vo.getCompanyId()));
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
