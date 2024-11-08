package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.WorkItemSkillsVO;

@Repository
public class Manage_WI_Skills_DaoImpl implements Manage_WI_Skills_Dao {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(Manage_WI_Skills_DaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public long create(WorkItemSkillsVO manageVO) {
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
	public long retrieveCount(WorkItemSkillsVO manageVo) {
		LOGGER.entry();
		long count = 0;
		try {

			Criteria cr = getSession().createCriteria(WorkItemSkillsVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			if (null != manageVo && null != manageVo.getWorkitemVO() && 0 < manageVo.getWorkitemVO().getWorkItemId()) {
				cr.add(Restrictions.eq("workitemVO.workItemId", manageVo.getWorkitemVO().getWorkItemId()));
			}
			if (null != manageVo && null != manageVo.getCompanyId() && 0 < manageVo.getCompanyId()) {
				cr.add(Restrictions.eq("companyId", manageVo.getCompanyId()));
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
	public List<WorkItemSkillsVO> findAll(WorkItemSkillsVO manageVO) {
		List<WorkItemSkillsVO> list = new ArrayList<WorkItemSkillsVO>();
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(WorkItemSkillsVO.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.setFirstResult(manageVO.getRecordIndex());
			criteria.setMaxResults(manageVO.getMaxRecord());
			// criteria.add(Restrictions.eq("workitemVO.workItemId",
			// manageVO.getWorkitemVO().getWorkItemId()));
			// list = (List<WorkItemSkillsVO>) criteria.list().stream().distinct();

			if (null != manageVO && null != manageVO.getWorkitemVO() && 0 < manageVO.getWorkitemVO().getWorkItemId()) {
				criteria.add(Restrictions.eq("workitemVO.workItemId", manageVO.getWorkitemVO().getWorkItemId()));
			}
			if (null != manageVO && null != manageVO.getCompanyId() && 0 < manageVO.getCompanyId()) {
				criteria.add(Restrictions.eq("companyId", manageVO.getCompanyId()));
			}
			
			// criteria.setFirstResult(manageVO.getRecordIndex());
			// criteria.setMaxResults(manageVO.getMaxRecord());
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
	public WorkItemSkillsVO getFindById(WorkItemSkillsVO manageVO) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(WorkItemSkillsVO.class);
			criteria.add(Restrictions.eq("workItemSkillId", manageVO.getWorkItemSkillId()));
			criteria.add(Restrictions.eq("companyId", manageVO.getCompanyId()));
			manageVO = (WorkItemSkillsVO) criteria.uniqueResult();

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Manage_WI_Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Manage_WI_Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return manageVO;
	}

	@Override
	public Boolean delete(WorkItemSkillsVO manageVo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			WorkItemSkillsVO vo = (WorkItemSkillsVO) session.get(WorkItemSkillsVO.class, manageVo.getWorkItemSkillId());
			vo.setDelete(true);
			if (0 < vo.getWorkItemSkillId()) {
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
	public WorkItemSkillsVO update(WorkItemSkillsVO manageVO) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null != manageVO) {

				session.saveOrUpdate(manageVO);
				if (null != manageVO) {
					System.out.println("success");
				}
			}
			return manageVO;
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update Manage_WI_Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Update Task Manage_WI_Skills failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return manageVO;

	}

	@Override
	public List<Long> getSkillIdList(WorkItemSkillsVO manageVO) {

		List<Long> skillsId = new ArrayList<Long>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(WorkItemSkillsVO.class);
			criteria.createCriteria("skillsListVO", "ref");
			criteria.setProjection(Projections.groupProperty("ref.skillsId"));
			criteria.add(Restrictions.eq("workItemSkillId", manageVO.getWorkItemSkillId()));
			criteria.add(Restrictions.eq("companyId", manageVO.getCompanyId()));
			skillsId = (List<Long>) criteria.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getPrivelegeIdList has failed:" + ex.getMessage());
			}
			LOGGER.info("getPrivelegeIdList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return skillsId;
	}

	@Override
	public List<WorkItemSkillsVO> findSLAByWorkItemId(long workItemId) {

		Session session = sessionFactory.getCurrentSession();
		Long targetWorkItemId = workItemId;
		Criteria criteria = session.createCriteria(WorkItemSkillsVO.class, "skill");
		criteria.createAlias("skill.skillsListVO", "skillsListVO", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("skillsListVO.skillsId", targetWorkItemId));
		List<WorkItemSkillsVO> skilllist = criteria.list();

		return skilllist;
	}

	@Override
	public List<WorkItemSkillsVO> findAllList(WorkItemSkillsVO manageVO) {
		List<WorkItemSkillsVO> list = new ArrayList<WorkItemSkillsVO>();
		Session session = sessionFactory.getCurrentSession();
		try {	
			Criteria criteria = session.createCriteria(WorkItemSkillsVO.class);
			criteria.add(Restrictions.eq("isDelete", false));
			
			if (null != manageVO && null != manageVO.getCompanyId() && 0 < manageVO.getCompanyId()) {
				criteria.add(Restrictions.eq("companyId", manageVO.getCompanyId()));
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