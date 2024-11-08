package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "price_book")
public class PriceBookVO  extends  BasicEntity  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer priceBookId;
	private String priceBookOwner;
	private String priceBookName;
	private long price;
	private Boolean active;
	private String description;
	private Boolean isDeleted;
	private User user;
	
	private InventoryVO productservicevo;  // product mapping
	
	private SupplierVO suppliervo;  // supplier mapping
	
	
	@Id
	@GeneratedValue
	@Column(name = "PRICE_BOOK_ID", unique = true, nullable = false)	
	public Integer getPriceBookId() {
		return priceBookId;
	}
	public void setPriceBookId(Integer priceBookId) {
		this.priceBookId = priceBookId;
	}
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "PRICE_BOOK_OWNER")
	public String getPriceBookOwner() {
		return priceBookOwner;
	}
	public void setPriceBookOwner(String priceBookOwner) {
		this.priceBookOwner = priceBookOwner;
	}
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "PRICE_BOOK_NAME")
	public String getPriceBookName() {
		return priceBookName;
	}
	public void setPriceBookName(String priceBookName) {
		this.priceBookName = priceBookName;
	}
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "ACTIVE")
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "IS_DELETED")
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
 	// Mapping with user(for pricebook owner)
	@OneToOne
	@JoinColumn(name="loginId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	//mapping with product
	@OneToOne
	@JoinColumn(name="product_id")
	public InventoryVO getProductservicevo() {
		return productservicevo;
	}
	public void setProductservicevo(InventoryVO productservicevo) {
		this.productservicevo = productservicevo;
	}
	@OneToOne
	@JoinColumn(name="supplier_id")
	public SupplierVO getSuppliervo() {
		return suppliervo;
	}
	public void setSuppliervo(SupplierVO suppliervo) {
		this.suppliervo = suppliervo;
	}
	@Column(name="price")
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
	
	
	
}
