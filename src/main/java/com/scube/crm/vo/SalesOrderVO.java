package com.scube.crm.vo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "sales_order")
public class SalesOrderVO extends BasicEntity {

	/**
	 */
	private static final long serialVersionUID = 1L; 
	private long salesOrderId;
	private Opportunity opportunity;
	private AccountVO accountVO;
	private double price;
	private double totalPrice;
	private Long quantity;
	private Long totalInvoice;
	private Long grandTotal;
	private List<SalesOrderProductsVO> salesOrderProductsVO;
	private long invoiceId;
	private String salesOrderNo;
	private String invoiceName;

	@Id
	@GeneratedValue
	@Column(name="sales_id")
	public long getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}


	@OneToOne
	@JoinColumn(name="account_id")
	public AccountVO getAccountVO() {
		return accountVO;
	}
	public void setAccountVO(AccountVO accountVO) {
		this.accountVO = accountVO;
	}

	@Column(name="total_invoice")
	public Long getTotalInvoice() {
		return totalInvoice;
	}
	public void setTotalInvoice(Long totalInvoice) {
		this.totalInvoice = totalInvoice;
	}
	
	@Column(name="grand_total")
	public Long getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(Long grandTotal) {
		this.grandTotal = grandTotal;
	}
	@OneToMany(mappedBy = "salesOrderVo",orphanRemoval = true, cascade = CascadeType.ALL)
	public List<SalesOrderProductsVO> getSalesOrderProductsVO() {
		return salesOrderProductsVO;
	}
	public void setSalesOrderProductsVO(List<SalesOrderProductsVO> salesOrderProductsVO) {
		this.salesOrderProductsVO = salesOrderProductsVO;
	}
	@Transient
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	/* @Column(name="total_price") */
	@Transient
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name="quantity") 
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	@Column(name="invoice_id") 
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	@Column(name="sales_order_no") 
	public String getSalesOrderNo() {
		return salesOrderNo;
	}
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	@Transient
	@OneToOne
	@JoinColumn(name="opportunity_Id")
	public Opportunity getOpportunity() {
		return opportunity;
	}
	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
	}
	public String getInvoiceName() {
		return invoiceName;
	}
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	
}
