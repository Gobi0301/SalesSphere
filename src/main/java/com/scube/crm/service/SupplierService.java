package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.SupplierBO;
import com.scube.crm.bo.SupplierProductBO;
import com.scube.crm.exception.MySalesException;

public interface SupplierService {

	SupplierBO createSupplier(SupplierBO supplier);



	List<SupplierBO> listSupplier(SupplierBO supplierBO);



	SupplierBO selectsupplier(SupplierBO supplierB);



	SupplierBO supplierValueUpdate(SupplierBO sbo);



	Boolean deleteSupplier(SupplierBO supplierbo);



	List<SupplierBO> searchByValue(SupplierBO supplierBO);



	public long supplierCount(SupplierBO supplierBO)throws MySalesException;



	boolean checksupplieremails(String emails, long id);




	SupplierProductBO addProduct(SupplierProductBO supplierproductBO);

}



