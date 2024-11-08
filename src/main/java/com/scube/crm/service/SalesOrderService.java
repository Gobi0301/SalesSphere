package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.CompanyBO;
import com.scube.crm.bo.PaymentBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SalesOrderVO;

public interface SalesOrderService {

	List<SalesOrderBO> retriveSalesOrders(SalesOrderBO salesOrderBO);

	List<SalesOrderBO> getSalesOrderList(SalesOrderBO salesOrder);

	public PaymentBO savePayment(PaymentBO paymentbo);

	long salesCount(SalesOrderBO salesOrderBO) throws Exception;

	long searchCount(SalesOrderBO salesOrderBO) throws Exception;

	List<SalesOrderBO> search(SalesOrderBO salesOrderBO)throws Exception;

	Map<Integer, String> retriveAccounts( AccountBO accountBO)throws Exception;

	AccountBO getProfile(int accId)throws Exception;

	void saveInvoice(SalesOrderBO salesOrder);

	SalesOrderVO getSalesOrderById(long salesid);

	List<PriceBookVO> getPricebook(long serviceId);

	boolean getPaymentStatus(SalesOrderBO salesOrder);

	CompanyBO viewCompanyDetails(CompanyBO companyBO);

	SalesOrderBO getSalesOrder(long salesOrderId);

	SalesOrderBO calculateInvoiceValues(SalesOrderBO overAllVaue);

	double calculateCGST(SalesOrderBO salesOrder);

	double calculateSGST(SalesOrderBO salesOrder);

	
	
}
