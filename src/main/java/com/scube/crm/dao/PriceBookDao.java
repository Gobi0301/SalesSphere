package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import com.scube.crm.bo.PriceBookBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SupplierVO;

public interface PriceBookDao {

	PriceBookVO createPriceBook(PriceBookVO priceBookVO)throws MySalesException;

	long reterivepricebook(PriceBookVO priceBookVO)throws MySalesException;

	List<PriceBookVO> reteriveprice(PriceBookVO priceBookVO)throws MySalesException;

	PriceBookVO reterivepricebookdetails(PriceBookVO priceBookVO)throws MySalesException;

	PriceBookVO editpricebooks(PriceBookVO priceBookVO)throws MySalesException;

	Boolean updatepricebook(PriceBookVO priceBookVO) throws MySalesException;

	Boolean deletepricebook(PriceBookVO priceBookVO) throws MySalesException;

	long searchcount(PriceBookVO price) throws MySalesException;


	List<PriceBookVO> search(PriceBookVO priceBookVO) throws MySalesException;

	ArrayList<SupplierVO> getSupplier(long serviceId);

	SupplierVO getSupplierPrice(Long supplierIds);

	List<PriceBookVO> reteriveprice(long serviceId);

}
