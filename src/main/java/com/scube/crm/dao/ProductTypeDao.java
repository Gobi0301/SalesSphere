package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.ProductTypesVO;

public interface ProductTypeDao {

	List<ProductTypesVO> selectAllTypes(ProductTypesVO productTypesVO1);

	long createProducttypes(ProductTypesVO vo);
	
	ProductTypesVO getTypeId(ProductTypesVO productTypesVO);
	
	ProductTypesVO updateType(ProductTypesVO productTypesVO);

	boolean deletetypes(long productTypesId);

	Boolean deleteProductTypes(ProductTypesVO productTypesVO);
	
	boolean checkProductTypes(String productTypes, long id)throws MySalesException;
	
	long countOfProductTypesBO(ProductTypesVO productTypesVO);

	List<ProductTypesVO> listOfProductTypes(ProductTypesVO productTypesVO);

	long retrieveCount(ProductTypesVO productTypeVo);

	List<ProductTypesVO> listOfProductByPagination(ProductTypesVO productTypesVO1);
}
