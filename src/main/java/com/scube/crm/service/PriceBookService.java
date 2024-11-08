package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import com.scube.crm.bo.PriceBookBO;
import com.scube.crm.bo.SupplierBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SupplierVO;

public interface PriceBookService {

	PriceBookBO createPriceBook(PriceBookBO priceBookBO)throws MySalesException;

	long reterivepricebook(PriceBookBO priceBookbo)throws MySalesException;

	List<PriceBookBO> reteriveprice(PriceBookBO priceBookbo)throws MySalesException;

	PriceBookVO reterivepricebookdetails(PriceBookVO priceBookVO)throws MySalesException;

	PriceBookVO editpricebooks(PriceBookVO priceBookVO)throws MySalesException;

	Boolean updatepricebook(PriceBookVO priceBookVO)throws MySalesException;

	Boolean deletepricebook(PriceBookVO priceBookVO)throws MySalesException;

	long searchcount(PriceBookBO priceBO)throws MySalesException;

	List<PriceBookBO> search(PriceBookBO priceBO)throws MySalesException;

	ArrayList<SupplierBO> getSupplier(long serviceId);

	SupplierBO getSupplierPrice(Long supplierIds);

	List<PriceBookBO> getPricebook(long serviceId);

}
