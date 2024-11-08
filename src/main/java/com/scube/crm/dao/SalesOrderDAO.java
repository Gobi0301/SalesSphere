package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import com.scube.crm.bo.GstBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.Company;
import com.scube.crm.vo.PaymentVO;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SalesOrderVO;

public interface SalesOrderDAO {

	List<SalesOrderVO> retriveSalesOrders(SalesOrderBO salesOrderBO);

	List<SalesOrderVO> getSalesOrderList(SalesOrderBO salesOrder);

	GstBO getGstById(long gstId);

	public PaymentVO savePayment(PaymentVO paymentvo);

	long salesCount(SalesOrderVO salesOrderVO) throws Exception;

	long searchCount(SalesOrderVO vo, String date)throws Exception;

	List<SalesOrderVO> search(SalesOrderVO vo, String date)throws Exception;

	AccountVO getProfile(int accId)throws Exception;

	void saveInvoice(SalesOrderVO salesOrderVo);

	SalesOrderVO getSalesOrderById(long salesId);

	//ArrayList<PriceBookVO> getPricebook(long  serviceId);

	List<PriceBookVO> getPricebook(PriceBookVO priceBookVO);

	boolean getPaymentStatus(SalesOrderBO salesOrders);

	Company viewCompanyDetails(Company company);

	SalesOrderVO getSalesOrder(long salesOrderId) throws MySalesException;
	
}
