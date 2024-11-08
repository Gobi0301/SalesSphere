package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.ApproveProcurementBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.exception.MySalesException;

public interface ProductService {

	InventoryBO createServices(InventoryBO productServiceBO);

	List<InventoryBO> listservice(InventoryBO serviceBO);

	InventoryBO getServiceObject(InventoryBO productServiceBo);

	Boolean serviceUpdate(InventoryBO productServiceBo);

	Boolean deleteService(InventoryBO service);

	boolean isValidServiceName(InventoryBO productServiceBO);

	List<InventoryBO> listOfProduct();

	List<InventoryBO> listOfProductName( InventoryBO inventoryBO);

	List<InventoryBO> listOfServiceId(List<InventoryBO> productServiceBO);

	InventoryBO retriveServiceById(InventoryBO productBO);

	long getCountOfProduct(InventoryBO productServiceBO);

	List<InventoryBO> listOfProductByPagination(InventoryBO productServiceBO);

	long countOfProductBySearch(InventoryBO productServiceBO);

	InventoryBO getProductObjectByName(InventoryBO serviceBO);

	InventoryBO getProducts(long serviceId)throws MySalesException;

	boolean checkProductName(String serviceName, long id);




	


}
