package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.SupplierBO;
import com.scube.crm.bo.SupplierProductBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.SupplierProductVO;
import com.scube.crm.vo.SupplierVO;

public interface SupplierDao {

	SupplierBO createSupplier(SupplierVO supplierVo);

	List<SupplierVO> getlistSupplier(SupplierVO supplierVO);

	SupplierVO selectsupplier(SupplierVO suppliervo);

	SupplierBO supplierValueUpdate(SupplierVO supplierVO);

	Boolean deleteSupplier(SupplierVO suppliervo);

	List<SupplierVO> searchByValue(SupplierVO suppliervo);

	public long supplierCount(SupplierVO supplierVo)throws MySalesException ;

	boolean checksupplieremails(String emails, long id);

	SupplierProductBO addProduct(SupplierProductVO supplierProductVO);

	List<SupplierProductVO> retrieveSupplierProduct(long supplierId, Long companyId);
}
