package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.InventoryBO;
import com.scube.crm.vo.InventoryVO;


public interface ProductServiceDao {

	InventoryBO createServices(InventoryVO productServiceVO);

	List<InventoryVO> listViewService(InventoryVO productServiceVO);

	InventoryVO getServiceObject(InventoryVO productServiceVO);

	Boolean serviceUpdateDao(InventoryVO productServiceVo);

	Boolean deleteService(InventoryVO productServiceVo);

	InventoryVO retrieveService(InventoryVO productServiceVo);

	boolean isValidServiceName(InventoryVO productServiceVO);

	List<InventoryVO> listOfProduct();

	List<InventoryVO> listOfProductName(InventoryVO productVO);

	List<InventoryVO> listOfServiceId(InventoryVO productVO);

	InventoryVO retriveServiceById(InventoryVO productVO);

	long getCountOfProduct(InventoryVO productServiceVO);

	List<InventoryVO> listOfProductByPagination(InventoryVO productServiceVO);

	long countOfProductBySearch(InventoryVO productVO);

	InventoryVO getProductObjectByName(InventoryVO serviceVO);

	InventoryVO getProducts(long serviceId);

	boolean checkProductName(String serviceName, long id);

	

}
