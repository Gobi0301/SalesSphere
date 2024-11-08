package com.scube.crm.bo;
import java.util.List;

public class SalesOrderBO extends BaseBO {
	private static final long serialVersionUID = -1858124723519342841L;
	private long salesOrderId;
	private OpportunityBO opportunityBO;
	private InventoryBO product;   //Product Mapping
	private AccountBO accountBO;
	private Long grandTotal;
	private	List<SalesOrderProductsBO> salesOrderProductBO;
	private long invoiceId;
	private String salesOrderNo;
	private long sNo;
	private String invoiceName;
	private double price;
	private Long totalPrice;
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	private Long quantity;
	private Long totalInvoice;
	private String date;
	private GstBO gstBO;
	
	//priceBook mapping
	private PriceBookBO pricebookbo;

	
	
	public PriceBookBO getPricebookbo() {
		return pricebookbo;
	}
	public void setPricebookbo(PriceBookBO pricebookbo) {
		this.pricebookbo = pricebookbo;
	}
	public AccountBO getAccountBO() {
		return accountBO;
	}
	public void setAccountBO(AccountBO accountBO) {
		this.accountBO = accountBO;
	}
	
	public List<SalesOrderProductsBO> getSalesOrderProductBO() {
		return salesOrderProductBO;
	}
	public void setSalesOrderProductBO(List<SalesOrderProductsBO> salesOrderProductBO) {
		this.salesOrderProductBO = salesOrderProductBO;
	}
	public Long getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(Long grandTotal) {
		this.grandTotal = grandTotal;
	}
	public InventoryBO getProduct() {
		return product;
	}
	public void setProduct(InventoryBO product) {
		this.product = product;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public GstBO getGstBO() {
		return gstBO;
	}
	public void setGstBO(GstBO gstBO) {
		this.gstBO = gstBO;
	}
	public Long getTotalInvoice() {
		return totalInvoice;
	}
	public void setTotalInvoice(Long totalInvoice) {
		this.totalInvoice = totalInvoice;
	}
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getSalesOrderNo() {
		return salesOrderNo;
	}
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}
	public long getsNo() {
		return sNo;
	}
	public void setsNo(long sNo) {
		this.sNo = sNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public OpportunityBO getOpportunityBO() {
		return opportunityBO;
	}
	public void setOpportunityBO(OpportunityBO opportunityBO) {
		this.opportunityBO = opportunityBO;
	}
	public String getInvoiceName() {
		return invoiceName;
	}
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	}