package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.crm.bo.ProductTypesBO;
import com.scube.crm.dao.ProductTypeDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.ProductTypesVO;
@Service
@Transactional
public class ProductTypeServiceimpl implements ProductTypeService {
	
	static final MySalesLogger LOGGER = MySalesLogger.getLogger(ProductTypeServiceimpl.class);

	@Autowired
	ProductTypeDao productTypeDao;
	
	
	@Override
	public long createProducttypes(ProductTypesBO producttypesbo) {
		LOGGER.entry();
		long id=0;
		try {
		ProductTypesVO vo =new ProductTypesVO();
		vo.setProductTypes(producttypesbo.getProductTypes());
		vo.setIsDeleted(false);
		vo.setCompanyId(producttypesbo.getCompanyId());
		vo.setCreatedBy(producttypesbo.getCreatedBy());
		 id= productTypeDao.createProducttypes(vo);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create Producttypes has failed:" + ex.getMessage());
			}
			LOGGER.info("create Producttypes has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		
		return id;
		
	}
		
	@Override
	public List<ProductTypesBO> selectAlltypes(ProductTypesBO producttypesbo) {
		LOGGER.entry();
		
		List<ProductTypesBO> listbo= new ArrayList<ProductTypesBO>();
		List<ProductTypesVO> listvo= new ArrayList<ProductTypesVO>();
		ProductTypesVO productTypesVO1 = new ProductTypesVO();
		
		if(null!= producttypesbo.getCompanyId()&& 0< producttypesbo.getCompanyId()) {
		productTypesVO1.setCompanyId(producttypesbo.getCompanyId()); // Company based retrieve purpose
		}
		int count=1;
		listvo=productTypeDao.selectAllTypes(productTypesVO1);
		try {
		for(ProductTypesVO productTypesVO:listvo) {
		ProductTypesBO productTypesBO = new ProductTypesBO();
		
		int i=count++;
		productTypesBO.setsNO(i);
		productTypesBO.setProductTypesId(productTypesVO.getId());
		productTypesBO.setProductTypes(productTypesVO.getProductTypes());
		listbo.add(productTypesBO);
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View ProductTypes has failed:" + ex.getMessage());
			}
			LOGGER.info("View ProductTypes has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return listbo;
	}

	@Override
	public boolean deleteTypes(long productTypesId) {
		
		return productTypeDao.deletetypes(productTypesId);
	}

	@Override
	public boolean deleteProductTypes(ProductTypesBO producttypesbo) {
		LOGGER.entry();
		Boolean status= false;
		ProductTypesVO productTypesVO = new ProductTypesVO();
		try {
			productTypesVO.setId(producttypesbo.getProductTypesId());
			productTypesVO.setIsDeleted(true);
			status= productTypeDao.deleteProductTypes(productTypesVO);
			
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete ProductTypes has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete ProductTypes has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return status;
	}

	

	@Override
	public ProductTypesBO getTypeId(long productTypesId) {
		// TODO Auto-generated method stub
		LOGGER.entry();
		ProductTypesBO typeBo=new ProductTypesBO();
		try {
		ProductTypesVO typeVo=new ProductTypesVO();
		typeVo.setId(productTypesId);
				typeVo=productTypeDao.getTypeId(typeVo);
		if(null!=typeVo) {
			typeBo.setProductTypesId(typeVo.getId());
			typeBo.setProductTypes(typeVo.getProductTypes());
			//typeBo.setDeleted(false);
			typeBo.setCompanyId(typeVo.getCompanyId());

		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit ProductType has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit ProductType has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
	}
		return typeBo;
	}

	/*
	 * public ProductTypesBO updateType(long productTypesId) { LOGGER.entry();
	 * ProductTypesVO typeVo=new ProductTypesVO(); ProductTypesBO productTypeBo=new
	 * ProductTypesBO(); try { if(null!=productTypeBo &&
	 * 0<productTypeBo.getProductTypesId());
	 * typeVo.setId(productTypeBo.getProductTypesId());
	 * typeVo.setProductTypes(productTypeBo.getProductTypes());
	 * typeVo.setIsDeleted(false); productTypeDao.getTypeId(productTypesId); }catch
	 * (Exception ex) { if (LOGGER.isDebugEnabled()) {
	 * LOGGER.debug("Update GST has failed:" + ex.getMessage()); }
	 * LOGGER.info("Update GST has failed:" + ex.getMessage()); }finally {
	 * LOGGER.exit(); }
	 * 
	 * return productTypeBo; }
	 */

	@Override
	public ProductTypesBO updateType(ProductTypesBO productTypeBo) {
		LOGGER.entry();
		ProductTypesVO typeVo=new ProductTypesVO();
		ProductTypesBO typeBo=new ProductTypesBO();
		try {
			if(null!=productTypeBo && 0<productTypeBo.getProductTypesId());
			typeVo.setId(productTypeBo.getProductTypesId());
			typeVo.setProductTypes(productTypeBo.getProductTypes());
			typeVo.setCompanyId(productTypeBo.getCompanyId());
			typeVo.setIsDeleted(false);
			productTypeDao.updateType(typeVo);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Update GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		typeBo.setProductTypesId(typeVo.getId());
		return typeBo;
	}

	@Override
	public boolean checkProductTypes(String productTypes,long id) throws MySalesException {
		boolean checkProductTypes=false;
		checkProductTypes=productTypeDao.checkProductTypes(productTypes,id);
		return checkProductTypes;
	}
	
	@Override
	public long countOfproductTypes(ProductTypesBO productTypesBO) {
		LOGGER.entry();
		try {
			ProductTypesBO ProductTypesBO=new ProductTypesBO();
			ProductTypesVO ProductTypesVO=new ProductTypesVO();
		long countOfProductTypes = 0;
		
		ProductTypesVO.setIsDeleted(ProductTypesBO.getisDeleted());
		ProductTypesVO.setProductTypes(null);
		countOfProductTypes = productTypeDao.countOfProductTypesBO(ProductTypesVO);
		if (0 < countOfProductTypes) {
			return countOfProductTypes;
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("countOfprivilegesVO has failed:" + ex.getMessage());
			}
			LOGGER.info("countOfprivilegesVO has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return 0;
	}

	@Override
	public List<ProductTypesBO> listproductTypesBOByPagination(ProductTypesBO productTypesBO) {
		 LOGGER.entry();
		    List<ProductTypesBO> ProductTypesBOList = new ArrayList<>();
		    try {
		        // Initialize PrivilegesVO object
		    	ProductTypesVO ProductTypesVO = new ProductTypesVO();
		        
		        // Set pagination details from PrivilegesBO
		    	ProductTypesVO.setRecordIndex(productTypesBO.getRecordIndex());
		    	ProductTypesVO.setMaxRecord(productTypesBO.getMaxRecord());
		  //      privilegesVO.setPrivilegeId(privilegesBO.getPrivilegeId());
		        if(null!= productTypesBO.getProductTypes()) {
		        	ProductTypesVO.setProductTypes(productTypesBO.getProductTypes());
		        }
		        // Retrieve paginated list of users from DAO
		        List<ProductTypesVO>  ProductTypesVOList = productTypeDao.listOfProductTypes(ProductTypesVO);

		        if (ProductTypesVOList != null && !ProductTypesVOList.isEmpty()) {
		            int sNo = productTypesBO.getRecordIndex() + 1;
		            for (ProductTypesVO vo : ProductTypesVOList) {
		            	ProductTypesBO bo = new ProductTypesBO();

		                // Map user details to PrivilegesBO
		                bo.setProductTypesId(vo.getId());
		                bo.setProductTypes(vo.getProductTypes());
		                bo.setsNO(sNo);
		                ++sNo;

		                // Add PrivilegesBO to the list
		                ProductTypesBOList.add(bo);
		            }
		        }
		    } catch (Exception ex) {
		        if (LOGGER.isDebugEnabled()) {
		            LOGGER.debug("listOfUsersByPagination has failed: " + ex.getMessage());
		        }
		        LOGGER.error("listOfUsersByPagination has failed: " + ex.getMessage(), ex);
		    } finally {
		        LOGGER.exit();
		    }
		    return ProductTypesBOList;
		}
	
	@Override
	public long retrieveCount(ProductTypesBO producttypesbo) {
		long count = 0;
		try {
			ProductTypesVO productTypeVo = new ProductTypesVO();
			if (null != producttypesbo.getProductTypes()) {
				productTypeVo.setProductTypes(producttypesbo.getProductTypes());
			}
			if (null != producttypesbo.getCompanyId() && 0 < producttypesbo.getCompanyId()) {
				productTypeVo.setCompanyId(producttypesbo.getCompanyId());
			}
			count = productTypeDao.retrieveCount(productTypeVo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}
	
	@Override
	public List<ProductTypesBO> listOfProductByPagination(ProductTypesBO producttypesbo) {

		List<ProductTypesBO> listbo = new ArrayList<ProductTypesBO>();
		List<ProductTypesVO> listvo = new ArrayList<ProductTypesVO>();
		ProductTypesVO productTypesVO1 = new ProductTypesVO();

		productTypesVO1.setRecordIndex(producttypesbo.getRecordIndex());
		productTypesVO1.setMaxRecord(producttypesbo.getMaxRecord());
		productTypesVO1.setIsDeleted(false);
		if (null != producttypesbo.getCompanyId() && 0 < producttypesbo.getCompanyId()) {
			productTypesVO1.setCompanyId(producttypesbo.getCompanyId()); // Company based retrieve purpose
		}
		if (null != producttypesbo.getProductTypes()) {
			productTypesVO1.setProductTypes(producttypesbo.getProductTypes());
		}
		
		listvo = productTypeDao.listOfProductByPagination(productTypesVO1);
		if (null != listvo && !listvo.isEmpty() && 0 < listvo.size()) {
			int sNo = producttypesbo.getRecordIndex();
			for (ProductTypesVO productTypesVO : listvo) {
				ProductTypesBO productTypesBO = new ProductTypesBO();
				productTypesBO.setsNO(++sNo);
				productTypesBO.setProductTypesId(productTypesVO.getId());
				productTypesBO.setProductTypes(productTypesVO.getProductTypes());
				listbo.add(productTypesBO);
			}
		}
		return listbo;
	}
}

