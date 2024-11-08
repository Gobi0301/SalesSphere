package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "salesorder_product")
public class SalesOrderProductsVO {

	private int salesOderProductsId;
	private InventoryVO product;
	private PriceBookVO priceBookVo;
	private GstVO gstVO;
	private Integer quantity;
	private double price;
	private double quantityPrice;
	private SalesOrderVO salesOrderVo;
	
	@ManyToOne
	@JoinColumn(name = "salesorder_id")
	public SalesOrderVO getSalesOrderVo() {
		return salesOrderVo;
	}
	public void setSalesOrderVo(SalesOrderVO salesOrderVo) {
		this.salesOrderVo = salesOrderVo;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sales_order_product_id")
	public int getSalesOderProductsId() {
		return salesOderProductsId;
	}
	public void setSalesOderProductsId(int salesOderProductsId) {
		this.salesOderProductsId = salesOderProductsId;
	}
		
	@ManyToOne
	@JoinColumn(name="product_id")
	public InventoryVO getProduct() {
		return product;
	}
	public void setProduct(InventoryVO product) {
		this.product = product;
	}
	@OneToOne
	@JoinColumn(name="pricebook_id")
	public PriceBookVO getPriceBookVo() {
		return priceBookVo;
	}
	public void setPriceBookVo(PriceBookVO priceBookVo) {
		this.priceBookVo = priceBookVo;
	}
	
	@OneToOne
	@JoinColumn(name="gst_id")
	public GstVO getGstVO() {
		return gstVO;
	}
	public void setGstVO(GstVO gstVO) {
		this.gstVO = gstVO;
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
	
	
	
}
