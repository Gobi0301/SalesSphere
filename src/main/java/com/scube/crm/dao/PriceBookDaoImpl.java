package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SupplierVO;

@Repository
public class PriceBookDaoImpl extends BaseDao implements PriceBookDao{
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(PriceBookDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {

		return sessionFactory.getCurrentSession();

	}

	@Override
	public PriceBookVO createPriceBook(PriceBookVO priceBookVO) throws MySalesException {
		// TODO Auto-generated method stub
		PriceBookDaoImpl.LOGGER.entry();
		PriceBookVO priceBookV0 = new PriceBookVO();
		try{
			Session session = getSession();
			Integer priceBookId=  (Integer) session.save(priceBookVO);
			session.flush();
			if(0!=priceBookId){
				priceBookV0.setPriceBookId(priceBookId);
				return priceBookV0;
			}
		}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create pricebook has failed:" + ex.getMessage());
			}
			LOGGER.info("create pricebook has failed:" + ex.getMessage());
		}finally {
			PriceBookDaoImpl.LOGGER.exit();
		}
		return priceBookV0;
	}

	@Override
	public long reterivepricebook(PriceBookVO priceBookVO) throws MySalesException {
		// TODO Auto-generated method stub
		PriceBookDaoImpl.LOGGER.entry();
		long pricebookcount=0;
		try { 
			Criteria cr=getSession().createCriteria(PriceBookVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("active", true));
			if(null != priceBookVO.getCompanyId()&& 0< priceBookVO.getCompanyId()) {
			cr.add(companyValidation(priceBookVO.getCompanyId()));  // Company base retriew
			}
			if(null != priceBookVO.getPriceBookName() && !priceBookVO.getPriceBookName().isEmpty()) {
				cr.add(Restrictions.ilike("priceBookName", priceBookVO.getPriceBookName().trim(), MatchMode.ANYWHERE));
			}     
			cr.setProjection(Projections.rowCount());
			pricebookcount=(long) cr.uniqueResult();
		
	}catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("accountCount retrive has failed:" + ex.getMessage());
		}
		LOGGER.info("accountCount retrive has failed:" + ex.getMessage());
	}finally {
		PriceBookDaoImpl.LOGGER.exit();
	}
		return pricebookcount;
	}

	@Override
	public List<PriceBookVO> reteriveprice(PriceBookVO priceBookVO) throws MySalesException {
		// TODO Auto-generated method stub
		PriceBookDaoImpl.LOGGER.entry();
		List<PriceBookVO> pricebooklist=new ArrayList<PriceBookVO>();
		try{
			//int count = 1;
			Criteria cr = getSession().createCriteria(PriceBookVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("active", true));
			if(null != priceBookVO.getCompanyId()&& 0< priceBookVO.getCompanyId() ) {
			cr.add(companyValidation(priceBookVO.getCompanyId()));// based on companyId..
			}
			if(null != priceBookVO.getPriceBookName() && !priceBookVO.getPriceBookName().isEmpty()) {
				cr.add(Restrictions.ilike("priceBookName", priceBookVO.getPriceBookName().trim(), MatchMode.ANYWHERE));
			}
			cr.setFirstResult(priceBookVO.getRecordIndex());
		    cr.setMaxResults(priceBookVO.getMaxRecord());
		    cr.addOrder(Order.desc("priceBookId"));  //Desding order by list
				
		//	List<AccountDO> listaccount = new ArrayList<AccountDO>(); 
		    pricebooklist=cr.list();
			
		}	
		catch(Exception ex){
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Retrieve pricebook has failed:" + ex.getMessage());
			
			LOGGER.info("Retrieve pricebook has failed:" + ex.getMessage());
			}
		
	}	finally {
		PriceBookDaoImpl.LOGGER.exit();
	}
		return pricebooklist;
	}

	@Override
	public PriceBookVO reterivepricebookdetails(PriceBookVO priceBookVO) throws MySalesException {
		// TODO Auto-generated method stub
		PriceBookDaoImpl.LOGGER.entry();
		PriceBookVO pricebook = new PriceBookVO();
		try {
			Criteria cr = getSession().createCriteria(PriceBookVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("active", true));
			cr.add(Restrictions.eq("priceBookId", priceBookVO.getPriceBookId()));
			pricebook=(PriceBookVO) cr.uniqueResult();
		}catch(Exception ex){
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Retrieve pricebook details has failed:" + ex.getMessage());
			
			LOGGER.info("Retrieve pricebook details has failed:" + ex.getMessage());
			}
		
	}	finally {
		PriceBookDaoImpl.LOGGER.exit();
	}
		return pricebook;
	}

	@Override
	public PriceBookVO editpricebooks(PriceBookVO priceBookVO) throws MySalesException {
		// TODO Auto-generated method stub
		PriceBookDaoImpl.LOGGER.entry();
		PriceBookVO pricebook = new PriceBookVO();
		
		try {
			Criteria cr = getSession().createCriteria(PriceBookVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("active", true));
			cr.add(Restrictions.eq("priceBookId", priceBookVO.getPriceBookId()));
			pricebook=(PriceBookVO) cr.uniqueResult();
			}catch(Exception ex){
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("edit pricebook  has failed:" + ex.getMessage());
			
			LOGGER.info("edit pricebook details has failed:" + ex.getMessage());
			}
		
	}	finally {
		PriceBookDaoImpl.LOGGER.exit();
	}
		return pricebook;
	}

	@Override
	public Boolean updatepricebook(PriceBookVO priceBookVO) throws MySalesException {
		// TODO Auto-generated method stub
		PriceBookDaoImpl.LOGGER.entry();
		try{
			if(null!=priceBookVO) {
			Session session = getSession();
			session.saveOrUpdate(priceBookVO);
//			session.flush();
			return true;
			}else {
				return false;
			}
		}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("update pricebook has failed:" + ex.getMessage());
			}
			LOGGER.info("update pricebook has failed:" + ex.getMessage());
		}finally {
			PriceBookDaoImpl.LOGGER.exit();
		}
		return false;
	}

	@Override
	public Boolean deletepricebook(PriceBookVO priceBookVO) throws MySalesException {
		// TODO Auto-generated method stub
		PriceBookDaoImpl.LOGGER.entry();
		int result = 0;
		try {
			Query query = getSession().
		    createQuery("UPDATE PriceBookVO P SET P.isDeleted = :isDeleted WHERE P.priceBookId = :priceBookId ");
			query.setParameter("isDeleted", true);
			query.setParameter("priceBookId", priceBookVO.getPriceBookId());
			result=query.executeUpdate();
			if(0!=result) {
				return true;
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete pricebook has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete pricebook has failed:" + ex.getMessage());
		}finally {
			PriceBookDaoImpl.LOGGER.exit();
		}
		return false;
	}

	@Override
	public long searchcount(PriceBookVO priceBookVO) throws MySalesException {
		
		PriceBookDaoImpl.LOGGER.entry();
		long count = 0;
		try {
			Criteria criteria = getSession().createCriteria(PriceBookVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			if(null != priceBookVO.getCompanyId()&& 0< priceBookVO.getCompanyId()) {
			criteria.add(companyValidation(priceBookVO.getCompanyId()));// based on companyId..
			}
			if(null != priceBookVO.getPriceBookName() && !priceBookVO.getPriceBookName().isEmpty()) {
				criteria.add(Restrictions.ilike("priceBookName", priceBookVO.getPriceBookName().trim(), MatchMode.ANYWHERE));
			}
			if(null != priceBookVO.getPriceBookOwner() && !priceBookVO.getPriceBookOwner().isEmpty()) {
				criteria.add(Restrictions.ilike("priceBookOwner", priceBookVO.getPriceBookOwner().trim(), MatchMode.ANYWHERE));
			}
			criteria.setProjection(Projections.rowCount());
			
			count =  (long) criteria.uniqueResult();
			
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("search count pricebook has failed:" + ex.getMessage());
			}
			LOGGER.info("search count pricebook has failed:" + ex.getMessage());
		}finally {
			PriceBookDaoImpl.LOGGER.exit();
		}
		
		return count;
	}

	@Override
	public List<PriceBookVO> search(PriceBookVO priceBookVO) throws MySalesException {
		PriceBookDaoImpl.LOGGER.entry();
		List<PriceBookVO> vo = new ArrayList<PriceBookVO>();
		try {
			Criteria cr=getSession().createCriteria(PriceBookVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			if(null != priceBookVO.getCompanyId()&& 0< priceBookVO.getCompanyId()) {
			cr.add(companyValidation(priceBookVO.getCompanyId()));// based on companyId..
			}
			cr.setFirstResult(priceBookVO.getRecordIndex());
			cr.setMaxResults(priceBookVO.getMaxRecord());
			if(null != priceBookVO.getPriceBookName() && !priceBookVO.getPriceBookName().isEmpty()) {
				cr.add(Restrictions.ilike("priceBookName", priceBookVO.getPriceBookName().trim(), MatchMode.ANYWHERE));
			}
			if(null != priceBookVO.getPriceBookOwner() && !priceBookVO.getPriceBookOwner().isEmpty()) {
				cr.add(Restrictions.ilike("priceBookOwner", priceBookVO.getPriceBookOwner().trim(), MatchMode.ANYWHERE));
			}
			
			vo = cr.list();

		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("search pricebook has failed:" + ex.getMessage());
			}
			LOGGER.info("search pricebook has failed:" + ex.getMessage());
		}finally {
			PriceBookDaoImpl.LOGGER.exit();
		}
		return vo;
	}

	@Override
	public ArrayList<SupplierVO> getSupplier(long serviceId) {
		//ArrayList<SupplierVO> vo=new ArrayList<SupplierVO>();
		try {
			Session session=sessionFactory.openSession();
			Criteria criteria=session.createCriteria(SupplierVO.class);
//			InventoryVO c=new InventoryVO();
//			c.setServiceId(serviceId);
			criteria.createAlias("supplierProducts", "users");
			criteria.add(Restrictions.eq("users.productServiceVO.serviceId", serviceId));
			
			ArrayList<SupplierVO> vo= (ArrayList<SupplierVO>) criteria.list();
			if(null!=vo) {
				return vo;
			}
			}catch(Exception e) {
				e.printStackTrace();
				if(LOGGER.isInfoEnabled()) {
					LOGGER.info("FROM INFO: Exception \t"+e);
					
				}
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("FROM DEBUG : Exception \t"+e);
				}
			}
		return null;
	}

	@Override
	public SupplierVO getSupplierPrice(Long supplierIds) {
		SupplierVO supplierVO=new SupplierVO();
		try {

			Session session = getSession();
			Criteria criteria = session.createCriteria(SupplierVO.class);
			criteria.add(Restrictions.eq("supplierId", supplierIds));
			supplierVO=(SupplierVO) criteria.uniqueResult();
			if (null != supplierVO) {
				return supplierVO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Supplier List failed:"+ e.getMessage());
			}
			LOGGER.info("Supplier List failed:"+ e.getMessage());

		} 
		return supplierVO;
	}

	@Override
	public List<PriceBookVO> reteriveprice(long serviceId) {
	
		PriceBookDaoImpl.LOGGER.entry();
		List<PriceBookVO> pricebooklist=new ArrayList<PriceBookVO>();
		try{
			Criteria criteria = getSession().createCriteria(PriceBookVO.class, "priceBookVO");
			criteria.add(Restrictions.eq("isDeleted", false));
	        criteria.add(Restrictions.eq("active", true));
            criteria.createAlias("priceBookVO.productservicevo", "productservicevo");
            criteria.add(Restrictions.eq("productservicevo.serviceId", serviceId));
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            pricebooklist= criteria.list();
            return pricebooklist;
			
			
          
		
	
				
		
			
		}	
		catch(Exception ex){
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Retrieve pricebook has failed:" + ex.getMessage());
			
			LOGGER.info("Retrieve pricebook has failed:" + ex.getMessage());
			}
		
	}	finally {
		PriceBookDaoImpl.LOGGER.exit();
	}
		return pricebooklist;
		
		
	}

	}

	

	
	


