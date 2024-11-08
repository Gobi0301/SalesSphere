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

import com.scube.crm.bo.SupplierBO;
import com.scube.crm.bo.SupplierProductBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.Company;
import com.scube.crm.vo.PrivilegesVO;
import com.scube.crm.vo.SupplierProductVO;
import com.scube.crm.vo.SupplierVO;

@Repository
public class SupplierDaoImpl extends BaseDao implements SupplierDao {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(SupplierDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public SupplierBO createSupplier(SupplierVO supplierVo) {
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		SupplierBO sbo=new SupplierBO();
		try {
			if (null!=supplierVo) {
				session.save(supplierVo);
				sbo.setSupplierId(supplierVo.getSupplierId());
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
		return sbo;
	}


	@Override
	public List<SupplierVO> getlistSupplier(SupplierVO supplierVO) {

		LOGGER.entry();

		List<SupplierVO> voList = new ArrayList<>();

		try {
			Criteria cr=getSession().createCriteria(SupplierVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			if(null!= supplierVO.getCompanyId() && 0<supplierVO.getCompanyId()) {
			cr.add(companyValidation(supplierVO.getCompanyId()));
			}// based on companyId..
			cr.setFirstResult(supplierVO.getRecordIndex());
			cr.setMaxResults(supplierVO.getMaxRecord());
			if(null != supplierVO.getSupplierName() && !supplierVO.getSupplierName().isEmpty()) {
				cr.add(Restrictions.ilike("supplierName", supplierVO.getSupplierName().trim(), MatchMode.ANYWHERE));
			}
			cr.addOrder(Order.desc("supplierId"));
			voList = cr.list();

		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return voList;

	}


	@Override
	public SupplierVO selectsupplier(SupplierVO suppliervo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(SupplierVO.class);
			criteria.add(Restrictions.eq("supplierId", suppliervo.getSupplierId()));
			suppliervo = (SupplierVO) criteria.uniqueResult();
			
			if(null!=suppliervo && suppliervo.getSupplierProducts().size()>0) {
				return suppliervo;
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
		return suppliervo;
	}

	@Override
	public SupplierBO supplierValueUpdate(SupplierVO supplierVO) {
		LOGGER.entry();
		SupplierBO sBO=new SupplierBO();
		try {
			Session session=sessionFactory.getCurrentSession();
			if(null!=supplierVO) {
				session.update(supplierVO);
				sBO.setSupplierId(supplierVO.getSupplierId());

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

		return sBO;
	}

	@Override
	public Boolean deleteSupplier(SupplierVO suppliervo) {
		LOGGER.entry();
		try {
			Session session=sessionFactory.getCurrentSession();
			SupplierVO vo=(SupplierVO) session.get(SupplierVO.class, suppliervo.getSupplierId());
			vo.setDelete(true);
			if(0<vo.getSupplierId()){
				session.saveOrUpdate(vo);
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
	public List<SupplierVO> searchByValue(SupplierVO suppliervo) {
		LOGGER.entry();
		List<SupplierVO> supplierlistVO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(SupplierVO.class);
			criteria.add(Restrictions.eq("isDelete", suppliervo.isDelete()));
			criteria.add(Restrictions.eq("isActive", suppliervo.isActive()));
			criteria.add(companyValidation(suppliervo.getCompanyId()));
			if(null!=suppliervo.getSupplierName()&& !suppliervo.getSupplierName().isEmpty()) {
				criteria.add(Restrictions.ilike("supplierName",suppliervo.getSupplierName(), MatchMode.ANYWHERE));
			}
			supplierlistVO = criteria.list();
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return supplierlistVO;
	}


	@Override
	public long supplierCount(SupplierVO supplierVo) throws MySalesException {
		LOGGER.entry();
		long count = 0;
		try {

			Criteria cr = getSession().createCriteria(SupplierVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			if(null != supplierVo.getCompanyId()&& 0< supplierVo.getCompanyId()) {
			cr.add(companyValidation(supplierVo.getCompanyId()));
			}
			if( null!=supplierVo.getSupplierName()&& !supplierVo.getSupplierName().isEmpty()) {
				cr.add(Restrictions.ilike("supplierName", supplierVo.getSupplierName().trim(), MatchMode.ANYWHERE));
			}
			cr.setProjection(Projections.rowCount());
			count = (long) cr.uniqueResult();

		} catch (Exception e) {
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
	public boolean checksupplieremails(String emails,long id) {
		LOGGER.entry();
		SupplierVO supplier=null;
		try {
			Criteria cr = getSession().createCriteria(SupplierVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			cr.add(Restrictions.eq("emailId",emails));
			cr.add(Restrictions.eq("createdBy",id));
			supplier = (SupplierVO) cr.uniqueResult();
			if(null!=supplier) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
}


	@Override
	public SupplierProductBO addProduct(SupplierProductVO supplierProductVO) {
		// TODO Auto-generated method stub
		LOGGER.entry();
		Session session=sessionFactory.getCurrentSession();
		SupplierProductBO sproductBO=new SupplierProductBO();
		try {
		if(null!=supplierProductVO) {
			session.save(supplierProductVO);
			sproductBO.setSupplierproductId(supplierProductVO.getSupplierproductId());
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
		
		return sproductBO;
	}


	@Override
	public List<SupplierProductVO> retrieveSupplierProduct(long supplierId, Long companyId) {
		LOGGER.entry();
		List<SupplierProductVO> supplierproductList=null;
		try {
			Criteria criteria = getSession().createCriteria(SupplierProductVO.class);
		//	criteria.add(Restrictions.eq("companyId",companyId));	
			criteria.createAlias("supplierVO", "supplier");
			criteria.add(Restrictions.eq("supplier.supplierId",supplierId));
		if (null != criteria.list() && criteria.list().size() > 0) {
				supplierproductList = criteria.list();
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
		return supplierproductList;
	}
}
