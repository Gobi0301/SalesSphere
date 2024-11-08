package com.scube.crm.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="payment")
public class PaymentVO extends BasicEntity {
	
	/**
	 * 
	 */
	
	
	
	private static final long serialVersionUID = 1L;
	 @Id
	 @GeneratedValue
	private long transactionId;
	
	private Date date;
	  
	 private String paymentmode;
	 
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
