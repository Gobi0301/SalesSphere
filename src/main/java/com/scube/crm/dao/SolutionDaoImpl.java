package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.SolutionBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.SolutionVO;

@Repository
public class SolutionDaoImpl extends BaseDao implements SolutionDao {
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(SolutionDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public SolutionBO createSolution(SolutionVO solutionVo) {
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		SolutionBO solutionBo = new SolutionBO();
		try {
			if (null != solutionVo) {
				session.saveOrUpdate(solutionVo);
				solutionBo.setSolutionId(solutionVo.getSolutionId());
			}
			session.flush();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createSolution has failed:" + ex.getMessage());
			}
			LOGGER.info("createSolution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return solutionBo;
	}

	@Override
	public List<SolutionVO> lisOfSolution(SolutionVO solutionVo) {
        LOGGER.entry();
		List<SolutionVO> solutionVoList = new ArrayList<SolutionVO>();
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SolutionVO.class);
			if (solutionVo != null) {
				if (null != solutionVo.getSolutionTitle() && !solutionVo.getSolutionTitle().isEmpty()) {
					criteria.add(
							Restrictions.ilike("solutionTitle", solutionVo.getSolutionTitle().trim(), MatchMode.ANYWHERE));
				}
				if (null != solutionVo.getInventoryvo() && 0 < solutionVo.getInventoryvo().getServiceId()) {
					criteria.add(Restrictions.eq("inventoryVo.serviceId", solutionVo.getInventoryvo().getServiceId()));
				}
				if (null != solutionVo.getUservo() && 0 < solutionVo.getUservo().getId()) {
					criteria.add(Restrictions.eq("employee.id", solutionVo.getUservo().getId()));

				}
			}
			criteria.addOrder(Order.desc("solutionId"));
			criteria.add(Restrictions.eq("isDelete", false));
			if(null != solutionVo.getCompanyId()&& 0< solutionVo.getCompanyId()) {
			criteria.add(companyValidation(solutionVo.getCompanyId()));
			}
			criteria.setFirstResult(solutionVo.getRecordIndex());
			criteria.setMaxResults(solutionVo.getMaxRecord());
			solutionVoList = criteria.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfSolution has failed:" + ex.getMessage());
			}
			LOGGER.info("listOfSolution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return solutionVoList;
	}

	@Override
	public SolutionVO editSolution(SolutionVO solutionVo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(SolutionVO.class);
			criteria.add(Restrictions.eq("solutionId", solutionVo.getSolutionId()));
			solutionVo = (SolutionVO) criteria.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("editSolution has failed:" + ex.getMessage());
			}
			LOGGER.info("editSolution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return solutionVo;
	}
	

	@Override
	public SolutionBO updateSolution(SolutionVO solutionVo) {
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		SolutionBO solutionBo = new SolutionBO();
		try {
			if (null != solutionVo) {
				session.saveOrUpdate(solutionVo);
				solutionBo.setSolutionId(solutionVo.getSolutionId());
			}
			session.flush();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("updateSolution has failed:" + ex.getMessage());
			}
			LOGGER.info("updateSolution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return solutionBo;

	}


	@Override
	public boolean deleteSolution(SolutionVO svo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
            SolutionVO vo = (SolutionVO) session.get(SolutionVO.class, svo.getSolutionId());
			vo.setDelete(true);
			if (0 < vo.getSolutionId()) {
				session.saveOrUpdate(vo);
				return true;
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deleteSolution has failed:" + ex.getMessage());
			}
			LOGGER.info("deleteSolution has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}


		return false;

	}
	

	@Override
	public SolutionVO viewSoltion(int solutionId) {
		LOGGER.entry();
		SolutionVO vo = new SolutionVO();
     try {
            Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(SolutionVO.class);
			criteria.add(Restrictions.eq("solutionId", solutionId));
			vo = (SolutionVO) criteria.uniqueResult();

     } catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("viewSoltion has failed:" + ex.getMessage());
			}
			LOGGER.info("viewSoltion has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}


		return vo;

	}

	@Override
	public long Solcount(SolutionVO solutionVo) throws MySalesException {
		LOGGER.entry();
		long count = 0;
		try {
			
			  Criteria cr = getSession().createCriteria(SolutionVO.class);
			  cr.add(Restrictions.eq("isDelete", false));
			  if(null != solutionVo.getCompanyId()&& 0< solutionVo.getCompanyId()) {
			  cr.add(companyValidation(solutionVo.getCompanyId()));
			  }
			  if (null != solutionVo.getSolutionTitle() && !solutionVo.getSolutionTitle().isEmpty()) {
				  cr.add(
							Restrictions.ilike("solutionTitle", solutionVo.getSolutionTitle().trim(), MatchMode.ANYWHERE));
				}
			  cr.setProjection(Projections.rowCount());
			  count = (long) cr.uniqueResult();
			 
		 } catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Solcount has failed:" + ex.getMessage());
				}
				LOGGER.info("Solcount has failed:" + ex.getMessage());
			} finally {
				LOGGER.exit();
			}

		return count;
	}

	
}

