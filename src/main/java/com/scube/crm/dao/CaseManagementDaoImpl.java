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

import com.scube.crm.bo.CaseManagementBO;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.CaseManagementVO;
import com.scube.crm.vo.Customer;


@Repository
public class CaseManagementDaoImpl extends BaseDao implements CaseManagementDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(CaseManagementDaoImpl.class);
	
	@Override
	public CaseManagementBO createCase(CaseManagementVO caseVO) {
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		CaseManagementBO caseBO=new CaseManagementBO();
		try {
			if (null != caseVO) {
				long caseId = (long) session.save(caseVO);
				if (0 < caseId) {
					caseBO.setCaseOrigin(caseVO.getCaseOrigin());
					caseBO.setCaseReason(caseVO.getCaseReason());
					caseBO.setCasesolution(caseVO.getCasesolution());
					caseBO.setCategory(caseVO.getCategory());
					caseBO.setClaimingDate(caseVO.getClaimingDate());
					caseBO.setDescription(caseVO.getDescription());
					caseBO.setExpireDate(caseVO.getExpireDate());
					caseBO.setEmail(caseVO.getEmail());
					caseBO.setPhoneNo(caseVO.getPhoneNo());
					caseBO.setStatus(caseVO.getStatus());
					caseBO.setType(caseVO.getType());
					caseBO.setPriority(caseVO.getPriority());
					caseBO.setCaseId(caseVO.getCaseId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return caseBO;
	}
	 

	@Override
	public List<CaseManagementVO> getListCase(CaseManagementVO caseVO) {
		List<CaseManagementVO> caseListVO = new ArrayList<CaseManagementVO>();
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(CaseManagementVO.class);
			criteria.add(Restrictions.eq("isDelete",false));
			if(null != caseVO.getCompanyId()&& 0< caseVO.getCompanyId()) {
		    criteria.add(companyValidation(caseVO.getCompanyId()));//company condition
			}
			criteria.setFirstResult(caseVO.getRecordIndex());
			criteria.setMaxResults(caseVO.getMaxRecord());
			caseListVO = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}


		return caseListVO;

	}

	@Override
	public CaseManagementVO getCaseValues(CaseManagementVO caseVO) {
		LOGGER.entry();
		 try { 
				
				  Session session = sessionFactory.getCurrentSession(); 
				  Criteria criteria =session.createCriteria(CaseManagementVO.class);
		          criteria.add(Restrictions.eq("caseId", caseVO.getCaseId()));
		          caseVO = (CaseManagementVO) criteria.uniqueResult();
		
		   
		    
			  } catch (Exception e) {
					e.printStackTrace();
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug(e.getMessage());
					}
					LOGGER.info(e.getMessage());
				}finally {
					LOGGER.exit();
				}
		  return caseVO; 
		  }

	@Override
	public boolean caseUpdateValues(CaseManagementVO caseVO) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null != caseVO) {
				session.saveOrUpdate(caseVO);
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return false;
	}


	@Override
	public int deleteCase(CaseManagementVO vo) {
		LOGGER.entry();
		try {
		Session session=sessionFactory.getCurrentSession();

		CaseManagementVO casevo =(CaseManagementVO) session.get(CaseManagementVO.class, vo.getCaseId());
		casevo.setIsDelete(true);
			session.saveOrUpdate(casevo);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return (int) vo.getCaseId();
	}

	@Override
	public Customer getProfile(long id) {
		Customer customerVO = new Customer();
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("id", id));
			 criteria.add(Restrictions.eq("isDelete", false));
			 customerVO = (Customer) criteria.uniqueResult();
			
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}

			return customerVO;
	 
		 
		 
		
	}

	@Override
	public long searchcount(CaseManagementVO vo) {
		LOGGER.entry();
		long count = 0;
		try {
			Criteria criteria = getSession().createCriteria(CaseManagementVO.class);
			criteria.add(Restrictions.eq("isDelete", false));
			if(null != vo.getCompanyId()&& 0< vo.getCompanyId()) {
			criteria.add(companyValidation(vo.getCompanyId()));// based on companyId..
			}
			if(null != vo.getCaseOrigin() && !vo.getCaseOrigin().isEmpty()) {
				criteria.add(Restrictions.ilike("caseOrigin", vo.getCaseOrigin().trim(), MatchMode.ANYWHERE));
		}
			criteria.setProjection(Projections.rowCount());
			count =  (long) criteria.uniqueResult();
			
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		
		return count;
	}

	@Override
	public List<CaseManagementVO> searchCase(CaseManagementVO caseVO) {
		
		List<CaseManagementVO> vo = new ArrayList<CaseManagementVO>();
		LOGGER.entry();
		try {
			Criteria cr=getSession().createCriteria(CaseManagementVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			if(null != caseVO.getCompanyId()&& 0< caseVO.getCompanyId()) {
			cr.add(companyValidation(caseVO.getCompanyId()));// based on companyId..
			}
			cr.setFirstResult(caseVO.getRecordIndex());
			cr.setMaxResults(caseVO.getMaxRecord());
			if(null != caseVO.getCaseOrigin() && !caseVO.getCaseOrigin().isEmpty()) {
				cr.add(Restrictions.ilike("caseOrigin", caseVO.getCaseOrigin().trim(), MatchMode.ANYWHERE));
			}
			vo = cr.list();

		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return vo;
	}
	}
