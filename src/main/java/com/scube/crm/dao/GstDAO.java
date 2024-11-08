package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.GstBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.GstVO;

public interface GstDAO {

	GstBO createGstValues(GstVO gstVO) throws MySalesException;

	List<GstVO> getListGst(GstVO gstVO) throws MySalesException;

	GstVO getGstValues(GstVO gstVO) throws MySalesException;

	boolean gstUpdateValues(GstVO gstVO) throws MySalesException;

	Boolean deleteGstValues(GstVO gstVO) throws MySalesException;

	long countOfGst(GstVO gstVO);

	List<GstVO> listOfGstByPagination(GstVO gstVO);

	long countOfGstBySearch(GstVO gstVO) throws MySalesException;
	
	boolean checkSgstValue(String sgst)throws MySalesException;

	boolean checkCgstValue(String cgst)throws MySalesException;

	GstVO getGst(long gstId);

	

	boolean checkProduct(Long productId, long id);

}
