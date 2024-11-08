package com.scube.crm.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.GstBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.GstVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.RolesVO;

import bsh.ParseException;

@Repository
public class GstDAOImpl extends BaseDao implements GstDAO{

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(GstDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public GstBO createGstValues(GstVO gstVO)throws MySalesException {
		LOGGER.entry();
		GstBO gstBO=new GstBO();
		InventoryBO serviceBO = new InventoryBO();
		Session session = sessionFactory.getCurrentSession();
		try {
			if (null != gstVO) {
				long gstId = (long) session.save(gstVO);
				if (0 < gstId) {
					gstBO.setSgst(gstVO.getSgst());
					gstBO.setCgst(gstVO.getCgst());
					gstBO.setGstId(gstId);
					gstBO.setStartDate(gstVO.getStartDate());
					serviceBO.setServiceId(gstVO.getProduct().getServiceId());
					gstBO.setProduct(serviceBO);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Create GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return gstBO;
	}

	@Override
	public List<GstVO> getListGst(GstVO gstVO) throws MySalesException {
		LOGGER.entry();
		List<GstVO> listVO = new ArrayList<GstVO>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(GstVO.class);
			criteria.add(Restrictions.eq("isDelete", gstVO.getIsDelete()));
			criteria.add(Restrictions.eq("isActive", gstVO.getIsActive()));
			if(null!=gstVO.getCgst()&& !gstVO.getCgst().isEmpty()) {
				criteria.add(Restrictions.ilike("cgst",gstVO.getCgst().trim(), MatchMode.ANYWHERE));
			}
			if(null!=gstVO.getSgst()&& !gstVO.getSgst().isEmpty()) {
				criteria.add(Restrictions.ilike("sgst",gstVO.getSgst().trim(), MatchMode.ANYWHERE));
			}
			if(null!=gstVO.getStartDate() ) {
				criteria.add(Restrictions.eq("startDate",gstVO.getStartDate()));
			}
			criteria.setFirstResult(gstVO.getRecordIndex());
			criteria.setMaxResults(gstVO.getMaxRecord());
			if(0<gstVO.getCompanyId() && null != gstVO.getCompanyId() ) {
			    criteria.add(companyValidation(gstVO.getCompanyId()));//company condition
				}
			
			listVO = criteria.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("List GST has failed:" + ex.getMessage());
			}
			LOGGER.info("List GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return listVO;
	}

	@Override
	public GstVO getGstValues(GstVO gstVO)throws MySalesException {
		LOGGER.entry();
		GstVO gstVo = new GstVO();
		try {
			if (0<gstVO.getGstId()) {
				Session session = sessionFactory.getCurrentSession();
				Criteria criteria = session.createCriteria(GstVO.class);
				criteria.add(Restrictions.eq("gstId", gstVO.getGstId()));
				gstVo = (GstVO) criteria.uniqueResult();
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}		
		return gstVo;
	}

	@Override
	public boolean gstUpdateValues(GstVO gstVO)throws MySalesException {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null != gstVO) {
				session.update(gstVO);
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Update GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public Boolean deleteGstValues(GstVO gstVO)throws MySalesException {
		LOGGER.entry();
		int result;
		try {
			String deleteQuery = "UPDATE GstVO E set E.isDelete = :isDelete,E.isActive= :isActive"
					+ "  WHERE E.gstId = :gstId";
			final Query query = sessionFactory.getCurrentSession().createQuery(deleteQuery);
			query.setParameter("isDelete", gstVO.getIsDelete());
			query.setParameter("isActive", gstVO.getIsActive());
			query.setParameter("gstId", gstVO.getGstId());
			result = query.executeUpdate();
			if (0 != result) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deleted GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Deleted GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return false;

	}

	@Override
	public long countOfGst(GstVO gstVO) {
		// TODO Auto-generated method stub
		
		long countOfGst=0;
		
		Session session=sessionFactory.getCurrentSession();
		try {
			Criteria criteria=session.createCriteria(GstVO.class);
			if(null != gstVO.getCompanyId()&& 0< gstVO.getCompanyId()) {
			criteria.add(companyValidation(gstVO.getCompanyId())); //company condition
			}
			criteria.add(Restrictions.eq("isDelete", gstVO.getIsDelete()));
			criteria.setProjection(Projections.rowCount());
			countOfGst=(long)criteria.uniqueResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return countOfGst;
	}

	@Override
	public List<GstVO> listOfGstByPagination(GstVO gstVO) {
		// TODO Auto-generated method stub
		List<GstVO> gstVOList=new ArrayList<>();
		Session session=sessionFactory.getCurrentSession();
		try {
		Criteria criteria=session.createCriteria(GstVO.class);
		if(null != gstVO.getCompanyId()&& 0< gstVO.getCompanyId()) {
		criteria.add(companyValidation(gstVO.getCompanyId())); //company condition
		}
		criteria.add(Restrictions.eq("isDelete", gstVO.getIsDelete()));
		criteria.setFirstResult(gstVO.getRecordIndex());
		criteria.setMaxResults(gstVO.getMaxRecord());
		if(null!=gstVO.getSgst()&& !gstVO.getSgst().isEmpty()) {
			criteria.add(Restrictions.ilike("sgst", gstVO.getSgst(), MatchMode.ANYWHERE));
		}
		if(null!=gstVO.getCgst()&& !gstVO.getCgst().isEmpty()) {
			criteria.add(Restrictions.ilike("cgst", gstVO.getCgst(), MatchMode.ANYWHERE));
		}
		if (null != gstVO && null != gstVO.getProduct() && 0<gstVO.getProduct().getServiceId()) {
			criteria.add(Restrictions.eq("productServiceVO.serviceId",gstVO.getProduct().getServiceId())); 
		}
		
		
		gstVOList=criteria.list();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return gstVOList;
	}
	@Override
	public long countOfGstBySearch(GstVO gstVO) throws MySalesException {
	    LOGGER.entry();
	    long countOfGst = 0;
	    Session session = sessionFactory.getCurrentSession();
	    try {
	        Criteria criteria = session.createCriteria(GstVO.class);
	        criteria.add(Restrictions.eq("isDelete", gstVO.getIsDelete()));
	        
	        if (gstVO.getSgst() != null && !gstVO.getSgst().isEmpty()) {
	            criteria.add(Restrictions.ilike("sgst", gstVO.getSgst(), MatchMode.ANYWHERE));
	        }
	        
	        if (gstVO.getCgst() != null && !gstVO.getCgst().isEmpty()) {
	            criteria.add(Restrictions.ilike("cgst", gstVO.getCgst(), MatchMode.ANYWHERE));
	        }
	        
	        if (gstVO.getStartDate() != null) {
	            criteria.add(Restrictions.eq("startDate", gstVO.getStartDate()));
	        }
	        
	        if (gstVO.getCompanyId() != null && gstVO.getCompanyId() > 0) {
	            criteria.add(companyValidation(gstVO.getCompanyId())); // company condition
	        }
	        if(null!=gstVO.getProduct()&&0 <gstVO.getProduct().getServiceId()){
				criteria.add(Restrictions.eq("productServiceVO.serviceId", gstVO.getProduct().getServiceId()));
			}
	        
	        criteria.setProjection(Projections.rowCount());
	        countOfGst = (long) criteria.uniqueResult();
	    } catch (Exception ex) {
	        if (LOGGER.isDebugEnabled()) {
	            LOGGER.debug("Search GST has failed: " + ex.getMessage());
	        }
	        LOGGER.info("Search GST has failed: " + ex.getMessage());
	    } finally {
	        LOGGER.exit();
	    }
	    
	    return countOfGst;
	}


	@Override
	public boolean checkSgstValue(String sgst) throws MySalesException {
		try {
			Session session=sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(GstVO.class);
			criteria.add(Restrictions.eq("sgst",sgst));
			List sgstVOList = criteria.list();
		
		if(null!=sgstVOList && 0<sgstVOList.size()) {
			return true;
		}
		}catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

	@Override
	public boolean checkCgstValue(String cgst) throws MySalesException {
		try {
			Session session=sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(GstVO.class);
			criteria.add(Restrictions.eq("cgst",cgst));
			List cgstVOList = criteria.list();
		
		if(null!=cgstVOList && 0<cgstVOList.size()) {
			return true;
		}
		}catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

	@Override
	public GstVO getGst(long gstId) {
		LOGGER.entry();
		GstVO gstVo = new GstVO();
		try {
			if (0<gstId) {
				Session session = sessionFactory.getCurrentSession();
				gstVo = (GstVO) session.get(GstVO.class, gstId);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}		
		return gstVo;
	}

	@Override
	public boolean checkProduct(Long productId, long id) {
		List<GstVO> productServiceVOList=new ArrayList<>();
		try {
			Session session=sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(GstVO.class);
			criteria.add(Restrictions.eq("product.serviceId",productId));
			criteria.add(Restrictions.eq("companyId",id));
			//List productTypesVOList = (List) criteria.list();
			productServiceVOList=(List<GstVO>) criteria.list();
		
		if(null!=productServiceVOList && 0<productServiceVOList.size()) {
			return true;
		}
		}catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}
	}

