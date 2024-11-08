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
import com.scube.crm.vo.ProductTypesVO;
import com.scube.crm.vo.RolesVO;

@Repository
public class ProductTypeDaoimpl extends BaseDao implements ProductTypeDao  {
	
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(ProductTypeDaoimpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public long createProducttypes(ProductTypesVO vo) {
		LOGGER.entry();
		
		long id=0;
		Session session=sessionFactory.getCurrentSession();
		try {
		if(null!=vo) {
		 id=(long) session.save(vo);
		}
		if(id>0) {
			return id;
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
		return id;
	}

	@Override
	public List<ProductTypesVO> selectAllTypes(ProductTypesVO productTypesVO1) {
		List<ProductTypesVO> list=new ArrayList<ProductTypesVO>();
		LOGGER.entry();
		try {
			Session session=sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(ProductTypesVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			if(null != productTypesVO1.getCompanyId()&& 0< productTypesVO1.getCompanyId()) {
			criteria.add(companyValidation(productTypesVO1.getCompanyId()));
			}
			list=criteria.list();
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		
		return list;
	}

	@Override
	public boolean deletetypes(long productTypesId) {
		LOGGER.entry();
		try {
		Session session=sessionFactory.getCurrentSession();
		ProductTypesVO productTypesVO	=(ProductTypesVO) session.get(ProductTypesVO.class, productTypesId);
		productTypesVO.setIsDeleted(true);
		session.saveOrUpdate(productTypesVO);
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
	public Boolean deleteProductTypes(ProductTypesVO productTypesVO) {
		LOGGER.entry();
		try {
			Session session=sessionFactory.getCurrentSession();
			ProductTypesVO vo=(ProductTypesVO) session.get(ProductTypesVO.class, productTypesVO.getId());
			vo.setIsDeleted(true);
			if(0<vo.getId()){
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
	public ProductTypesVO getTypeId(ProductTypesVO productTypesVO) {
		// TODO Auto-generated method stub
		ProductTypesVO producttypeVo=new ProductTypesVO();
		try {
//			producttypeVo.setId(productTypesId);
		if(0<productTypesVO.getId()) {
				/*
				 * Session session=sessionFactory.getCurrentSession(); Criteria
				 * criteria=session.createCriteria(ProductTypesVO.class);
				 * criteria.add(Restrictions.eq("productTypeId", producttypeVo.getId()));
				 * producttypeVo=(ProductTypesVO) criteria.uniqueResult();
				 */
	System.out.println(productTypesVO.getId());		
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ProductTypesVO.class);
			criteria.add(Restrictions.eq("id", productTypesVO.getId()));
			producttypeVo = (ProductTypesVO) criteria.uniqueResult();
					}
	} catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Edit ProductType has failed:" + ex.getMessage());
		}
		LOGGER.info("Edit ProductType has failed:" + ex.getMessage());
	}finally {
		LOGGER.exit();
	}
		
		return producttypeVo;
	}

	@Override
	public ProductTypesVO updateType(ProductTypesVO productTypesVO) {
		LOGGER.entry();
		//ProductTypesVO producttypeVo=new ProductTypesVO();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null!=productTypesVO) {
				
				session.update(productTypesVO);
				if(null!=productTypesVO) {
					System.out.println("success");
				}
			}
			
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update ProductTypes has failed:" + ex.getMessage());
			}
			LOGGER.info("Update ProductTypes has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return productTypesVO;
	}

	@Override
	public boolean checkProductTypes(String productTypes,long id) throws MySalesException {

		try {
			Session session=sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ProductTypesVO.class);
			criteria.add(Restrictions.eq("productTypes",productTypes));
			criteria.add(Restrictions.eq("createdBy",id));
			List productTypesVOList = criteria.list();
		
		if(null!=productTypesVOList && 0<productTypesVOList.size()) {
			return true;
		}
		}catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

	@Override
	public long countOfProductTypesBO(ProductTypesVO productTypesVO) {
		LOGGER.entry();
		long countOfProductTypesBO = 0;
	
       
        try {
        	Session session=sessionFactory.getCurrentSession();
            Criteria cr = session.createCriteria(ProductTypesVO.class);
            if( null!=productTypesVO.getProductTypes() && !productTypesVO.getProductTypes().isEmpty()) {
                cr.add(Restrictions.ilike("productTypes", productTypesVO.getProductTypes().trim(), MatchMode.ANYWHERE));
            }
            cr.add(Restrictions.eq("isDeleted", false));
           
            cr.setProjection(Projections.rowCount());
            countOfProductTypesBO = (long) cr.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e.getMessage());
            }
            LOGGER.info(e.getMessage());
        }finally {
            LOGGER.exit();
        }
        return countOfProductTypesBO;
	}

	@Override
	public List<ProductTypesVO> listOfProductTypes(ProductTypesVO productTypesVO) {
		LOGGER.entry();
		List<ProductTypesVO> ProductTypesVOList = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria cr = session.createCriteria(ProductTypesVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.setFirstResult(productTypesVO.getRecordIndex());
			cr.setMaxResults(productTypesVO.getMaxRecord());
			if(null!= productTypesVO.getProductTypes() && !productTypesVO.getProductTypes().isEmpty()) {
				cr.add(Restrictions.ilike("productTypes", productTypesVO.getProductTypes().trim(), MatchMode.ANYWHERE));
			}
			ProductTypesVOList = cr.list();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("ProductTypesVOList has failed:" + ex.getMessage());
			}
			LOGGER.info("ProductTypesVOList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return ProductTypesVOList;
	}

	
	@Override
	public long retrieveCount(ProductTypesVO productTypesVo) {

		LOGGER.entry();
		long count = 0;
		try {
			Session session=sessionFactory.getCurrentSession();
			Criteria cr = session.createCriteria(ProductTypesVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			if(null != productTypesVo.getProductTypes() && !productTypesVo.getProductTypes().isEmpty()) {
				cr.add(Restrictions.ilike("productTypes", productTypesVo.getProductTypes().trim(), MatchMode.ANYWHERE));
			}
			if(null != productTypesVo.getCompanyId()&& 0< productTypesVo.getCompanyId()) {
				cr.add(companyValidation(productTypesVo.getCompanyId()));
			}
		
			cr.setProjection(Projections.rowCount());
			count = (long) cr.uniqueResult();
	}catch (Exception e) {
		e.printStackTrace();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(e.getMessage());
		}LOGGER.info(e.getMessage());
	} finally {
		LOGGER.exit();
	}

		return count;	

}

	@Override
	public List<ProductTypesVO> listOfProductByPagination(ProductTypesVO productTypesVO1) {
		List<ProductTypesVO> list=new ArrayList<ProductTypesVO>();
		LOGGER.entry();
		try {
			Session session=sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(ProductTypesVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			if(null != productTypesVO1.getCompanyId()&& 0< productTypesVO1.getCompanyId()) {
			criteria.add(companyValidation(productTypesVO1.getCompanyId()));
			}
			if(null != productTypesVO1.getProductTypes() && !productTypesVO1.getProductTypes().isEmpty()) {
				criteria.add(Restrictions.ilike("productTypes", productTypesVO1.getProductTypes().trim(), MatchMode.ANYWHERE));
			}
			if(0<productTypesVO1.getMaxRecord()) {
				 criteria.setFirstResult(productTypesVO1.getRecordIndex());
				 criteria.setMaxResults(productTypesVO1.getMaxRecord());
				 }
			list=criteria.list();
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		
		return list;
	}



}
