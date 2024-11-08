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
import com.scube.crm.vo.PlotVO;
import com.scube.crm.vo.SlaVO;

@Repository
public class SlaDaoImpl implements SlaDao {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(SlaDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public long create(SlaVO slaVO) {
		LOGGER.entry();

		long id = 0;
		Session session = sessionFactory.getCurrentSession();
		try {
			if (null != slaVO) {
				id = (long) session.save(slaVO);
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
	public long retrieveCount(SlaVO slaVO) {
		LOGGER.entry();
		long count = 0;
		try {

			Criteria cr = getSession().createCriteria(SlaVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			if(null!= slaVO.getCompanyId()&& 0<slaVO.getCompanyId() ) {
				cr.add(Restrictions.eq("companyId", slaVO.getCompanyId()));
			}
			if( null!=slaVO.getSlaCode()&& !slaVO.getSlaCode().isEmpty()) {
				cr.add(Restrictions.ilike("slaCode", slaVO.getSlaCode().trim(), MatchMode.ANYWHERE));
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
	public List<SlaVO> findAll(SlaVO slaVO) {
		List<SlaVO> list = new ArrayList<SlaVO>();
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(SlaVO.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.setFirstResult(slaVO.getRecordIndex());
			criteria.setMaxResults(slaVO.getMaxRecord());
			if (null != slaVO.getSlaCode() && !slaVO.getSlaCode().isEmpty()) {
				criteria.add(Restrictions.ilike("slaCode", slaVO.getSlaCode().trim(), MatchMode.ANYWHERE));
			}
			if(null!= slaVO.getCompanyId()&& 0<slaVO.getCompanyId() ) {
				criteria.add(Restrictions.eq("companyId", slaVO.getCompanyId()));
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
	public SlaVO getfindById(SlaVO slaVo) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(SlaVO.class);
			criteria.add(Restrictions.eq("slaId", slaVo.getSlaId()));
			slaVo = (SlaVO) criteria.uniqueResult();

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Sla has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Sla has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return slaVo;
	}

	@Override
	public SlaVO update(SlaVO slaVO) {

		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null != slaVO) {

				session.saveOrUpdate(slaVO);
				if (null != slaVO) {
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
		return slaVO;

	}

	@Override
	public Boolean delete(SlaVO slaVo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			SlaVO vo = (SlaVO) session.get(SlaVO.class, slaVo.getSlaId());
			vo.setDelete(true);
			if (0 < vo.getSlaId()) {
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
	public boolean checkSlaCode(String slaCode) {
		LOGGER.entry();
		SlaVO slaVO=null;
			try {
				Session session=sessionFactory.getCurrentSession();
				Criteria criteria = session.createCriteria(SlaVO.class);
				criteria.add(Restrictions.eq("slaCode",slaCode));
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
