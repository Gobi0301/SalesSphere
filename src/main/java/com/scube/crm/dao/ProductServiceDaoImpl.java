package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
/*import org.hibernate.Criteria;
 */import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.InventoryBO;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.ProductTypesVO;
import com.scube.crm.vo.SupplierVO;

 @Repository
 public class ProductServiceDaoImpl extends BaseDao implements ProductServiceDao {

	 private static final MySalesLogger LOGGER = MySalesLogger.getLogger(ProductServiceDaoImpl.class);
	 @Autowired
	 private SessionFactory sessionFactory;

	 @Override
	 public InventoryBO createServices(InventoryVO productServiceVO) {
		 // TODO Auto-generated method stub
		 InventoryBO productServiceBo = new InventoryBO(); 
		 InventoryBO companyService = new InventoryBO();
		 Session session = sessionFactory.getCurrentSession();
		 try {
			 if (null != productServiceVO) {
				 long serviceid = (long) session.save(productServiceVO);
				 if (0 != serviceid) {
					 productServiceBo.setServiceName(productServiceVO.getServiceName());
					 productServiceBo.setServiceId(serviceid);
					 return productServiceBo;
				 }
			 }
		 } catch (Exception e) {
			 if (LOGGER.isInfoEnabled()) {
				 LOGGER.info("FROM INFO: Exception \t" + e);
			 }
			 if (LOGGER.isDebugEnabled()) {
				 LOGGER.debug("FROM DEBUG : Exception \t" + e);
			 }
		 }
		 return companyService;
	 }
	 // retrieve products
	 @Override
	 public List<InventoryVO> listViewService(InventoryVO productServiceVO) {
		 LOGGER.entry();
		 List<InventoryVO> listVO = new ArrayList<InventoryVO>();

		 try {
			 // SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
			 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InventoryVO.class);
			 criteria.add(Restrictions.eq("isDelete", productServiceVO.getIsDelete()));
			 if(null!=productServiceVO && null!=productServiceVO.getCompanyId()) {
			 criteria.add(companyValidation(productServiceVO.getCompanyId())); // COMPANY
			 }
			 // criteria.add(Restrictions.eq("isActive", productServiceVO.getIsActive()));
			 if(0<productServiceVO.getMaxRecord()) {
			 criteria.setFirstResult(productServiceVO.getRecordIndex());
			 criteria.setMaxResults(productServiceVO.getMaxRecord());
			 }
			 if (null != productServiceVO.getServiceName() && !productServiceVO.getServiceName().isEmpty()) {
				 criteria.add(Restrictions.ilike("serviceName", productServiceVO.getServiceName().trim(), MatchMode.ANYWHERE));
			 }
			 //			 if (null != productServiceVO && null != productServiceVO.getProductTypesvO() && 0<productServiceVO.getProductTypesvO().getId()) {
			 //					criteria.add(Restrictions.eq("productTypesvO.id",productServiceVO.getProductTypesvO().getId())); 
			 //				}
			 if(0<productServiceVO.getProductTypesvO().getId()) {
				 criteria.add(Restrictions.eq("productTypesvO.id", productServiceVO.getProductTypesvO().getId()));
			 }
			 if((null != productServiceVO.getServiceName() && !productServiceVO.getServiceName().isEmpty())&&(0<productServiceVO.getProductTypesvO().getId())) {
				 criteria.add(Restrictions.ilike("serviceName", productServiceVO.getServiceName().trim(), MatchMode.ANYWHERE));
				 criteria.add(Restrictions.eq("productTypesvO.id", productServiceVO.getProductTypesvO().getId()));
			 }

			 listVO = criteria.list();
		 } catch (Exception e) {
			 if (LOGGER.isInfoEnabled()) {
				 LOGGER.info("FROM INFO: Exception \t" + e);
			 }
			 if (LOGGER.isDebugEnabled()) {
				 LOGGER.debug("FROM DEBUG : Exception \t" + e);
			 }
		 }finally {
			 LOGGER.exit();
		 }

		 return listVO;
	 }

	 @Override
	 public InventoryVO getServiceObject(InventoryVO productServiceVO) {
		 // TODO Auto-generated method stub
		 InventoryVO productService = new InventoryVO();
		 try {
			 if (0 != productServiceVO.getServiceId()) {
				 Session session = sessionFactory.getCurrentSession();
				 Criteria criteria = session.createCriteria(InventoryVO.class);
				 criteria.add(Restrictions.eq("serviceId", productServiceVO.getServiceId()));
				 productService = (InventoryVO) criteria.uniqueResult();

			 }
		 } catch (Exception e) {
			 if (LOGGER.isInfoEnabled()) {
				 LOGGER.info("FROM INFO: Exception \t" + e);
			 }
			 if (LOGGER.isDebugEnabled()) {
				 LOGGER.debug("FROM DEBUG : Exception \t" + e);
			 }
		 }
		 return productService;
	 }

	 @Override
	 public Boolean serviceUpdateDao(InventoryVO productServiceVo) {
		 // TODO Auto-generated method stub

		 try {
			 Session session = sessionFactory.getCurrentSession();
			 if (null != productServiceVo) {
				 session.update(productServiceVo);
				 return true;
			 }
		 } catch (Exception e) {
			 if (LOGGER.isInfoEnabled()) {
				 LOGGER.info("FROM INFO: Exception \t" + e);
			 }
			 if (LOGGER.isDebugEnabled()) {
				 LOGGER.debug("FROM DEBUG : Exception \t" + e);
			 }
		 }

		 return false;
	 }

	 @Override
	 public Boolean deleteService(InventoryVO productServiceVo) {

		 int result;
		 try {
			 String deleteQuery = "UPDATE InventoryVO E set E.isDelete = :isDelete WHERE E.serviceId = :serviceId";
			 final Query query = sessionFactory.getCurrentSession().createQuery(deleteQuery);
			 query.setParameter("isDelete", productServiceVo.getIsDelete());
			 // query.setParameter("isActive", productServiceVo.getIsActive());
			 query.setParameter("serviceId", productServiceVo.getServiceId());
			 result = query.executeUpdate();
			 if (0 != result) {

				 return true;
			 }
		 } catch (Exception e) {
			 if (LOGGER.isInfoEnabled()) {
				 LOGGER.info("FROM INFO: Exception \t" + e);
			 }
			 if (LOGGER.isDebugEnabled()) {
				 LOGGER.debug("FROM DEBUG : Exception \t" + e);
			 }
		 }
		 return false;
	 }

	 @Override
	 public InventoryVO retrieveService(InventoryVO productServiceVo) {

		 InventoryVO service = new InventoryVO();
		 Session session = sessionFactory.openSession();
		 try {
			 if (0 != productServiceVo.getServiceId()) {
				 Criteria criteria = session.createCriteria(InventoryVO.class);
				 criteria.add(Restrictions.eq("serviceId", productServiceVo.getServiceId()));
				 criteria.add(companyValidation(productServiceVo.getCompanyId()));  // Company 
				 InventoryVO productService = (InventoryVO) criteria.uniqueResult();
				 if (null != productService) {
					 service.setIsDelete(productService.getIsDelete());
					 service.setIsActive(productService.getIsActive());
					 return service;
				 }
			 }
		 } catch (Exception e) {
			 if (LOGGER.isInfoEnabled()) {
				 LOGGER.info("FROM INFO: Exception \t" + e);
			 }
			 if (LOGGER.isDebugEnabled()) {
				 LOGGER.debug("FROM DEBUG : Exception \t" + e);
			 }
		 } finally {
			 session.flush();
			 session.close();
		 }
		 return service;
	 }

	 @Override
	 public boolean isValidServiceName(InventoryVO productServiceVO) {
		 // TODO Auto-generated method stub
		 try {
			 Session session = sessionFactory.getCurrentSession();
			 Criteria criteria = session.createCriteria(InventoryVO.class);
			 criteria.add(Restrictions.eq("serviceName", productServiceVO.getServiceName()));
			 criteria.add(Restrictions.eq("isDelete", productServiceVO.getIsDelete()));
			 criteria.add(Restrictions.eq("isActive", productServiceVO.getIsActive()));
			 List<InventoryVO> ServiceList = criteria.list();
			 if (null != ServiceList && !ServiceList.isEmpty() && ServiceList.size() > 0) {
				 return true;
			 }
		 } catch (Exception e) {
			 if (LOGGER.isInfoEnabled()) {
				 LOGGER.info("FROM INFO: Exception \t" + e);
			 }
			 if (LOGGER.isDebugEnabled()) {
				 LOGGER.debug("FROM DEBUG : Exception \t" + e);
			 }
		 }
		 return false;
	 }
	 // product service list part
	 @Override
	 public List<InventoryVO> listOfProduct() {
		 LOGGER.entry();
		 List<InventoryVO> productVOList=new ArrayList<>();
		 try {
			 Criteria cr=sessionFactory.getCurrentSession().createCriteria(InventoryVO.class);
			 cr.add(Restrictions.eq("isDelete",false));
			 productVOList=cr.list();
		 }
		 catch(Exception e){
			 if(LOGGER.isInfoEnabled()) {
				 LOGGER.info("product list details: Exception \t"+e);
			 }
			 if(LOGGER.isDebugEnabled()) {
				 LOGGER.debug("product list details: Exception \t"+e);
			 }
		 }
		 finally{
			 LOGGER.exit();
		 }
		 return productVOList;
	 }

	 @Override
	 public List<InventoryVO> listOfProductName(InventoryVO productVO) {
		 // TODO Auto-generated method stub
		 List<InventoryVO> productServiceVO=new ArrayList<>();

		 Session session=sessionFactory.getCurrentSession();

		 Criteria cr=session.createCriteria(InventoryVO.class);
		 
		 cr.add(Restrictions.eq("isDelete",productVO.getIsDelete() ));
		 cr.add(companyValidation(productVO.getCompanyId()));
	//	  cr.addOrder(Order.desc("created"));
		 productServiceVO=cr.list();

		 return productServiceVO;
	 }

	 @Override
	 public List<InventoryVO> listOfServiceId(InventoryVO productVO) {
		 // TODO Auto-generated method stub
		 List<InventoryVO> productVOList=new ArrayList<>();

		 Session session=sessionFactory.getCurrentSession();
		 Criteria criteria=session.createCriteria(InventoryVO.class);
		 criteria.add(Restrictions.eq("serviceId", productVO.getServiceId()));
		 productVO=(InventoryVO)criteria.uniqueResult();
		 if(null!=productVO) {
			 productVOList.add(productVO);
		 }
		 return productVOList;
	 }

	 @Override
	 public InventoryVO retriveServiceById(InventoryVO productVO) {
		 LOGGER.entry();
		 try {
			 Criteria cr=sessionFactory.getCurrentSession().createCriteria(InventoryVO.class);
			 cr.add(Restrictions.eq("serviceId", productVO.getServiceId()));
			 cr.add(companyValidation(productVO.getCompanyId())); // companyId
			 productVO=(InventoryVO)cr.uniqueResult();
		 }catch (Exception ex) {
			 if (LOGGER.isDebugEnabled()) {
				 LOGGER.debug("Product retriveServiceById has failed:" + ex.getMessage());
			 }
			 LOGGER.info("Product retriveServiceById has failed:" + ex.getMessage());
		 }finally {
			 LOGGER.exit();
		 }
		 return productVO;   
	 }

	 @Override
	 public long getCountOfProduct(InventoryVO productServiceVO) {
		 // TODO Auto-generated method stub

		 long countOfProduct=0;
		 Session session=sessionFactory.getCurrentSession();
		 try {
			 Criteria criteria=session.createCriteria(InventoryVO.class);
			 criteria.add(Restrictions.eq("isDelete", productServiceVO.getIsDelete()));
			 if(null != productServiceVO.getCompanyId()&& 0< productServiceVO.getCompanyId()) {
			 criteria.add(companyValidation(productServiceVO.getCompanyId()));// company based
			 }
			 if(null != productServiceVO.getProductTypesvO() && 0<productServiceVO.getProductTypesvO().getId()) {
				 criteria.add(Restrictions.eq("productTypesvO.id", productServiceVO.getProductTypesvO().getId()));
				}
			 if(null != productServiceVO.getServiceName()&& !productServiceVO.getServiceName().isEmpty()) {
				 criteria.add(Restrictions.ilike("serviceName", productServiceVO.getServiceName().trim(),MatchMode.ANYWHERE));
				}
			 criteria.setProjection(Projections.rowCount());
			 countOfProduct=(long)criteria.uniqueResult();
		 }
		 catch (Exception e) {
			 // TODO: handle exception
			 System.out.println(e);
		 }

		 return countOfProduct;
	 }

	 @Override
	 public List<InventoryVO> listOfProductByPagination(InventoryVO productServiceVO) {
		 // TODO Auto-generated method stub

		 List<InventoryVO> productServiceVOList=new ArrayList<>();

		 Session session=sessionFactory.getCurrentSession();
		 try {

			 Criteria criteria=session.createCriteria(InventoryVO.class);
			 criteria.add(Restrictions.eq("isDelete",false));
			 if(null!=productServiceVO && null!=productServiceVO.getCompanyId()) {
			 criteria.add(companyValidation(productServiceVO.getCompanyId())); // Company
			 }
			 if(null != productServiceVO.getProductTypesvO() && 0<productServiceVO.getProductTypesvO().getId()) {
				 criteria.add(Restrictions.eq("productTypesvO.id", productServiceVO.getProductTypesvO().getId()));
			 }
			 if(null != productServiceVO.getServiceName()&& !productServiceVO.getServiceName().isEmpty()) {
				 criteria.add(Restrictions.ilike("serviceName", productServiceVO.getServiceName().trim(),MatchMode.ANYWHERE));
			 }
			 if(null != productServiceVO.getCompanyId()&& 0< productServiceVO.getCompanyId()) {
					criteria.add(companyValidation(productServiceVO.getCompanyId())); //company condition
					}
			 criteria.addOrder(Order.desc("serviceId"));  //Desding order by list
			 if(0<productServiceVO.getMaxRecord()) {
			 criteria.setFirstResult(productServiceVO.getRecordIndex());
			 criteria.setMaxResults(productServiceVO.getMaxRecord());
			 }
			 productServiceVOList=criteria.list();
		 }
		 catch (Exception e) {
			 System.out.println(e);
		 }


		 return productServiceVOList;
	 }

	 @Override
	 public long countOfProductBySearch(InventoryVO productVO) {
		 // TODO Auto-generated method stub
		 long countOfProduct=0;

		 Session session=sessionFactory.getCurrentSession();
		 try {
			 Criteria criteria=session.createCriteria(InventoryVO.class);
			 criteria.add(Restrictions.eq("isDelete", productVO.getIsDelete()));
			 if(null != productVO.getCompanyId()&& 0< productVO.getCompanyId()) {
			 criteria.add(companyValidation(productVO.getCompanyId()));
			 }
			 if(null!=productVO.getServiceName()) {
				 criteria.add(Restrictions.ilike("serviceName", productVO.getServiceName(), MatchMode.ANYWHERE));
			 }
			 if(null!=productVO.getProductTypesvO() && 0<productVO.getProductTypesvO().getId()) {
				 criteria.add(Restrictions.eq("productTypesvO.id",productVO.getProductTypesvO().getId()));
			 }
			 if(null != productVO.getServiceName() && !productVO.getServiceName().isEmpty()) {
				 criteria.add(Restrictions.ilike("serviceName", productVO.getServiceName().trim(), MatchMode.ANYWHERE));
			 }
			 criteria.setProjection(Projections.rowCount());
			 countOfProduct=(long)criteria.uniqueResult();
		 }
		 catch (Exception e) {
			 // TODO: handle exception
			 System.out.println(e);
		 }

		 return countOfProduct;
	 }

	 @Override
	 public InventoryVO getProductObjectByName(InventoryVO serviceVO) {
		 // TODO Auto-generated method stub

		 Session session=sessionFactory.getCurrentSession();
		 try {
			 Criteria criteria=session.createCriteria(InventoryVO.class);
			 if(null!=serviceVO.getServiceName()) {
				 criteria.add(Restrictions.eq("serviceName", serviceVO.getServiceName()));
			 }
			 //			 if(0<serviceVO.getProductTypesvO().getId()) {
			 //				 criteria.add(Restrictions.eq("productTypesvO.id", serviceVO.getProductTypesvO().getId()));
			 //			 }
			 serviceVO=(InventoryVO)criteria.uniqueResult();
		 }
		 catch (Exception e) {
			 // TODO: handle exception
			 System.out.println(e);
		 }

		 return serviceVO;
	 }
	 @Override
	 public InventoryVO getProducts(long serviceId) {
		 LOGGER.entry();
		 InventoryVO inventoryVO=new InventoryVO();
		 try {
			 Session session=sessionFactory.getCurrentSession();
			 Criteria criteria=session.createCriteria(InventoryVO.class);
			 criteria.add(Restrictions.eq("serviceId", Long.valueOf(serviceId)));
			 inventoryVO=(InventoryVO) criteria.uniqueResult();
		 }catch (Exception ex) {
			 if (LOGGER.isDebugEnabled()) {
				 LOGGER.debug("Get lead has failed:" + ex.getMessage());
			 }
			 LOGGER.info("Get lead has failed:" + ex.getMessage());
		 }finally {
			 LOGGER.exit();
		 }
		 return inventoryVO;
	 }
	@Override
	public boolean checkProductName(String serviceName, long id) {
		try {
			Session session=sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(InventoryVO.class);
			criteria.add(Restrictions.eq("serviceName",serviceName));
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
 }


