package com.scube.crm.bo;

public class SalesOrderProductsBO {
	
	private int salesOderProductsId;
	private InventoryBO product;
	private PriceBookBO priceBookBo;
	private GstBO gstBO;
	private Integer quantity;
	private double price;
	private double quantityPrice;
	private int sNo;
	
	public int getSalesOderProductsId() {
		return salesOderProductsId;
	}
	public void setSalesOderProductsId(int salesOderProductsId) {
		this.salesOderProductsId = salesOderProductsId;
	}
	public InventoryBO getProduct() {
		return product;
	}
	public void setProduct(InventoryBO product) {
		this.product = product;
	}
	public PriceBookBO getPriceBookBo() {
		return priceBookBo;
	}
	public void setPriceBookBo(PriceBookBO priceBookBo) {
		this.priceBookBo = priceBookBo;
	}
	public GstBO getGstBO() {
		return gstBO;
	}
	public void setGstBO(GstBO gstBO) {
		this.gstBO = gstBO;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getQuantityPrice() {
		return quantityPrice;
	}
	public void setQuantityPrice(double quantityPrice) {
		this.quantityPrice = quantityPrice;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	
		

}
