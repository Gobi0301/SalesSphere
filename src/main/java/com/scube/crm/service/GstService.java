package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.GstBO;
import com.scube.crm.exception.MySalesException;

public interface GstService {

	GstBO createGstValues(GstBO gstBO) throws MySalesException;

	List<GstBO> getListGst(GstBO gstBo) throws MySalesException;

	GstBO getGstValues(GstBO gstBO) throws MySalesException;

	boolean gstValueUpdate(GstBO gstBO) throws MySalesException;

	Boolean deleteGstValues(GstBO gstBO) throws MySalesException;

	long countOfGst( GstBO gstBO);

	List<GstBO> listOfGstByPagination(GstBO gstBO);

	long countOfGstBySearch(GstBO gstBo) throws MySalesException;
	
	boolean checkSgstValue(String sgst)throws MySalesException;

	boolean checkCgstValue(String cgst)throws MySalesException;

	GstBO getGst(long gstId);

	

	boolean checkProduct(Long productId, long id);
	
}
