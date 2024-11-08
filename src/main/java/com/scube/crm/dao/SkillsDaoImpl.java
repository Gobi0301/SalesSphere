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

import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.Customer;
import com.scube.crm.vo.PrivilegesVO;
import com.scube.crm.vo.SkillsVO;
import com.scube.crm.vo.SlaVO;
import com.scube.crm.vo.User;

@Repository
public class SkillsDaoImpl implements SkillsDao {

	
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(SkillsDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public long create(SkillsVO skillsVO) {
		LOGGER.entry();

		long id = 0;
		Session session = sessionFactory.getCurrentSession();
		try {
			if (null != skillsVO) {
				id = (long) session.save(skillsVO);
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
	public long retrieveCount(SkillsVO skillsVO) {
		LOGGER.entry();
		long count = 0;
		try {

			Criteria cr = getSession().createCriteria(SkillsVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			if(null != skillsVO.getSkillsCode() && !skillsVO.getSkillsCode() .isEmpty()) {
				cr.add(Restrictions.ilike("skillsCode", skillsVO.getSkillsCode(), MatchMode.ANYWHERE));
			}
			if(null != skillsVO.getCompanyId()&& 0< skillsVO.getCompanyId()) {
			cr.add(Restrictions.eq("companyId", skillsVO.getCompanyId()));
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
	public List<SkillsVO> findAll(SkillsVO skillsVO) {
		List<SkillsVO> list = new ArrayList<SkillsVO>();
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(SkillsVO.class);
			criteria.add(Restrictions.eq("isDelete", false));
			if (null != skillsVO.getSkillsCode() && !skillsVO.getSkillsCode().isEmpty()) {
				criteria.add(Restrictions.ilike("skillsCode", skillsVO.getSkillsCode().trim(), MatchMode.ANYWHERE));
			}if(null != skillsVO.getCompanyId()&& 0< skillsVO.getCompanyId()) {
			criteria.add(Restrictions.eq("companyId", skillsVO.getCompanyId()));
			}
			if(0<skillsVO.getRecordIndex()){
			criteria.setFirstResult(skillsVO.getRecordIndex());
		    }if(0<skillsVO.getMaxRecord()) {
			criteria.setMaxResults(skillsVO.getMaxRecord());
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
	public SkillsVO getfindById(SkillsVO skillsVo) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(SkillsVO.class);
			criteria.add(Restrictions.eq("skillsId", skillsVo.getSkillsId()));
			skillsVo = (SkillsVO) criteria.uniqueResult();

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return skillsVo;
	}

	@Override
	public SkillsVO update(SkillsVO skillsVO) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null != skillsVO) {

				session.saveOrUpdate(skillsVO);
				if (null != skillsVO) {
					System.out.println("success");
				}
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Update Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return skillsVO;

	}

	@Override
	public Boolean delete(SkillsVO skillsVo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			SkillsVO vo = (SkillsVO) session.get(SkillsVO.class, skillsVo.getSkillsId());
			vo.setDelete(true);
			if (0 < vo.getSkillsId()) {
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
	public boolean checkskillCode(String skillcode,long companyId) {
		LOGGER.entry();
		List<SkillsVO> skills = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(SkillsVO.class);
			criteria.add(Restrictions.eq("skillsCode", skillcode));
			criteria.add(Restrictions.eq("companyId", companyId));
			skills = (List<SkillsVO>) criteria.list();

			if (null != skills && skills.size() > 0) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckSkillCode has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckSkillCode has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public boolean checkskillName(String skillname,long companyId) {
		LOGGER.entry();
		List<SkillsVO> skills = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(SkillsVO.class);
			criteria.add(Restrictions.eq("descriptions", skillname));
			criteria.add(Restrictions.eq("companyId", companyId));
			skills = (List<SkillsVO>) criteria.list();

			if (null != skills && skills.size() > 0) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckSkillName has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckSkillName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

}
