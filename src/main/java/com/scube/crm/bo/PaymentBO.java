package com.scube.crm.bo;

import java.util.Date;

public class PaymentBO {
	
	
	
	private Date date;
	  
	 private String paymentmode;
	  
	 private long transactionId;
	 
	 private Long checkNo;
	 
	 private String salesOrderNo;
	 
	 private long salesOrderId;
	 
	 
	
  public long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

public Long getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(Long checkNo) {
		this.checkNo = checkNo;
	}

public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getSalesOrderNo() {
		return salesOrderNo;
	}

	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	
	
	

}
