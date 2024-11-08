package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.ApproveProcurementBO;
import com.scube.crm.bo.ProcurementBO;
import com.scube.crm.bo.RejectProcurementBO;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.ApproveProcurementVO;
import com.scube.crm.vo.ProcurementVO;
import com.scube.crm.vo.RejectProcurementVO;

@Repository
public class ProcurementDaoImpl extends BaseDao implements ProcurementDao {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(ProcurementDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ProcurementBO createProcurement(ProcurementVO procurementVO) {
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		ProcurementBO bo = new ProcurementBO();
		try {
			if (null != procurementVO) {
				long procurementId = (long) session.save(procurementVO);
				if (0 < procurementId) {
					bo.setMinimumStock(procurementVO.getMinimumStock());
					bo.setMaximumStock(procurementVO.getMaximumStock());
					bo.setAvailableStock(procurementVO.getAvailableStock());
					bo.setExpectedDate(procurementVO.getExpectedDate());
					bo.setQuantityOfProducts(procurementVO.getQuantityOfProducts());
					bo.setUnitOfCost(procurementVO.getUnitOfCost());
					bo.setTotalCost(procurementVO.getTotalCost());
					bo.setProcurementId(procurementVO.getProcurementId());

				}
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
		return bo;
	}

	@Override
	public List<ProcurementVO> getListProcurement(ProcurementVO procurementVO) {

		List<ProcurementVO> listVO = new ArrayList<ProcurementVO>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ProcurementVO.class);
			if(null != procurementVO.getCompanyId()&& 0< procurementVO.getCompanyId()) {
				criteria.add(companyValidation(procurementVO.getCompanyId()));// company condition
			}
			if(null!=procurementVO.getProductServiceVO()&&0<procurementVO.getProductServiceVO().getServiceId()) {
				criteria.add(Restrictions.eq("productServiceVO.serviceId", procurementVO.getProductServiceVO().getServiceId()));
				}
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.setFirstResult(procurementVO.getRecordIndex());
			criteria.setMaxResults(procurementVO.getMaxRecord());

			listVO = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listVO;

	}

	@Override
	public ProcurementVO getProcurementValues(ProcurementVO procurementVO) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ProcurementVO.class);
			criteria.add(Restrictions.eq("procurementId", procurementVO.getProcurementId()));
			procurementVO = (ProcurementVO) criteria.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return procurementVO;
	}

	@Override
	public boolean procurementUpdateValues(ProcurementVO procurementVO) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null != procurementVO) {
				session.saveOrUpdate(procurementVO);
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
	public int deleteProject(ProcurementVO vo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			ProcurementVO procurementVO = (ProcurementVO) session.get(ProcurementVO.class, vo.getProcurementId());
			procurementVO.setIsDelete(true);
			session.saveOrUpdate(vo);

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return (int) vo.getProcurementId();
	}

	@Override
	public ApproveProcurementVO createApprove(ApproveProcurementVO approveVO) {
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		ApproveProcurementBO approveBO = new ApproveProcurementBO();
		try {
			if (null != approveVO) {
				long approveId = (long) session.save(approveVO);
				if (0 < approveId) {
					approveBO.setSentTo(approveVO.getSentTo());
					approveBO.setDescription(approveVO.getDescription());
					approveBO.setApproveId(approveId);
				}
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
		return approveVO;
	}

	@Override
	public ProcurementVO selectParticularView(ProcurementVO vo) {
		LOGGER.entry();
		try {

			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ProcurementVO.class);
			criteria.add(Restrictions.eq("procurementId", vo.getProcurementId()));
			vo = (ProcurementVO) criteria.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return vo;
	}

	@Override
	public ProcurementVO getProfile(long id) {
		LOGGER.entry();
		ProcurementVO ProfileVO = new ProcurementVO();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ProcurementVO.class);
			criteria.add(Restrictions.eq("procurementId", id));
			criteria.add(Restrictions.eq("isDelete", false));
			ProfileVO = (ProcurementVO) criteria.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}

		return ProfileVO;

	}

	@Override
	public ActivityVO saveTracking(ActivityVO vo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			long id = (long) session.save(vo);
			vo.setActivityid(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public List<ActivityVO> retrieveTracking(long procurementId,Long companyId) {
		LOGGER.entry();
		List<ActivityVO> procurementactivityList = null;
		try {
			Criteria criteria = getSession().createCriteria(ActivityVO.class);
			criteria.add(Restrictions.eq("entitytype", "procurement"));
			criteria.add(Restrictions.eq("companyId",companyId));
			criteria.add(Restrictions.eq("entityid",procurementId));
			procurementactivityList = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return procurementactivityList;
	}

	@Override
	public RejectProcurementBO createReject(RejectProcurementVO rejectVO) {
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		RejectProcurementBO bo2 = new RejectProcurementBO();
		try {
			if (null != rejectVO) {
				long rejecId = (long) session.save(rejectVO);
				if (0 < rejecId) {
					bo2.setReason(rejectVO.getReason());
					bo2.setRejectId(rejectVO.getRejectId());

				}
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
		return bo2;
	}

	@Override
	public List<ProcurementVO> searchByValue(ProcurementVO vo) {
		LOGGER.entry();
		List<ProcurementVO> procurementlistVO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ProcurementVO.class);
			if(null != vo.getCompanyId()&& 0< vo.getCompanyId()) {
			criteria.add(companyValidation(vo.getCompanyId()));
			}
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.add(Restrictions.eq("productServiceVO.serviceId", vo.getProductServiceVO().getServiceId()));

			procurementlistVO = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return procurementlistVO;
	}

	@Override
	public long ProcurementCount(ProcurementVO procurementVO) {
		LOGGER.entry();
		long count = 0;
		try {

			Criteria cr = getSession().createCriteria(ProcurementVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			if(null != procurementVO.getCompanyId()&& 0< procurementVO.getCompanyId()) {
				cr.add(companyValidation(procurementVO.getCompanyId()));
			}
			if(null!=procurementVO.getProductServiceVO()&&0<procurementVO.getProductServiceVO().getServiceId()) {
			cr.add(Restrictions.eq("productServiceVO.serviceId", procurementVO.getProductServiceVO().getServiceId()));
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
	public List<ApproveProcurementVO> getapprovedprocurement(long companyId) {
		List<ApproveProcurementVO> approvedprocurementlistVO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ApproveProcurementVO.class);
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.add(Restrictions.eq("approvalStatus", "Approved"));
			if( 0< companyId ) {
				criteria.add(Restrictions.eq("companyId", companyId));
			}
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			approvedprocurementlistVO = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return approvedprocurementlistVO;
	}


	@Override
	public List<RejectProcurementVO> getrejectedprocurement(long companyId) {
		List<RejectProcurementVO> approvedprocurementlistVO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(RejectProcurementVO.class);
			criteria.add(Restrictions.eq("rejectStatus", "Rejected"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			if( 0< companyId ) {
				criteria.add(Restrictions.eq("companyId", companyId));
			}
			approvedprocurementlistVO = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return approvedprocurementlistVO;
	}
}

