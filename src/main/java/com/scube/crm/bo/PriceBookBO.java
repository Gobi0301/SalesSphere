package com.scube.crm.bo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.scube.crm.vo.User;

public class PriceBookBO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer priceBookId;
	private String priceBookOwner;
	@NotNull(message="Please Enter Valid name")
	@NotEmpty (message="Please Enter Valid name")
	private String priceBookName;
	@NotEmpty (message="Please Enter Valid Number")
	private Long price;
	private Boolean active;
	private String description;
	private Boolean isDeleted;
	private long createdBy;
	private int recordIndex;
	private int maxRecord;
	private String pagination = null;
	private int sNo;
	private User user;
	private Long id;

	private long companyId; //company id inizialise


	private AdminUserBO admin;
	
	private InventoryBO productservicebo;  // product mapping
	
	private SupplierBO supplierBO;   // Supplier mapping

	
	
	public int getRecordIndex() {
		return recordIndex;
	}
	public void setRecordIndex(int recordIndex) {
		this.recordIndex = recordIndex;
	}
	public int getMaxRecord() {
		return maxRecord;
	}
	public void setMaxRecord(int maxRecord) {
		this.maxRecord = maxRecord;
	}
	public String getPagination() {
		return pagination;
	}
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	public Integer getPriceBookId() {
		return priceBookId;
	}
	public void setPriceBookId(Integer priceBookId) {
		this.priceBookId = priceBookId;
	}
	public String getPriceBookOwner() {
		return priceBookOwner;
	}
	public void setPriceBookOwner(String priceBookOwner) {
		this.priceBookOwner = priceBookOwner;
	}
	public String getPriceBookName() {
		return priceBookName;
	}
	public void setPriceBookName(String priceBookName) {
		this.priceBookName = priceBookName;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public AdminUserBO getAdmin() {
		return admin;
	}
	public void setAdmin(AdminUserBO admin) {
		this.admin = admin;
	}

	
	public InventoryBO getProductservicebo() {
		return productservicebo;
	}
	public void setProductservicebo(InventoryBO productservicebo) {
		this.productservicebo = productservicebo;
	}
	public SupplierBO getSupplierBO() {
		return supplierBO;
	}
	public void setSupplierBO(SupplierBO supplierBO) {
		this.supplierBO = supplierBO;
	}
	

	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}




}
