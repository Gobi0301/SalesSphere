package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.ProductTypesBO;
import com.scube.crm.exception.MySalesException;

public interface ProductTypeService {

	List<ProductTypesBO> selectAlltypes(ProductTypesBO producttypesbo);

	long createProducttypes(ProductTypesBO producttypesbo);
	
	ProductTypesBO updateType(ProductTypesBO productTypeBo);
	
	ProductTypesBO getTypeId(long productTypesId);

	boolean deleteTypes(long productTypesId);

	boolean deleteProductTypes(ProductTypesBO producttypesbo);

	boolean checkProductTypes(String roleName, long id)throws MySalesException;
	
	long countOfproductTypes(ProductTypesBO productTypesBO);     

	List<ProductTypesBO> listproductTypesBOByPagination(ProductTypesBO productTypesBO);

	long retrieveCount(ProductTypesBO producttypesbo);

	List<ProductTypesBO> listOfProductByPagination(ProductTypesBO producttypesbo);
	

}
