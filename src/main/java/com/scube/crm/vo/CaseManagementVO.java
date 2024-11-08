package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="casemanagement")
public class CaseManagementVO extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long caseId;
	@Column(name = "caseReason")
	private String caseReason;
	@Column(name = "expireDate")
    private String expireDate;
	@Column(name = "claimingDate")
    private String claimingDate;
	@Column(name = "description")
    private String description;
	@Column(name = "casesolution")
    private String casesolution;
	@Column(name = "warrantyDate")
    private String warrantyDate; 
	 
	@Column(name = "email")
    private String email;
	@Column(name = "phoneNo")
    private Long phoneNo;
	@Column(name = "caseOrigin")
    private String caseOrigin;
	@Column(name = "Type")
    private String Type;
	@Column(name = "category")
    private String category;
	@Column(name = "status")
    private String status;
	@Column(name = "priority")
    private String priority; 
	@Column(name = "commend")
	private String commend;

	@Column(name="isdelete")
	private boolean isDelete; 

	public boolean isDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	 
	
	

	@OneToOne
	@JoinColumn(name="product_id") 
	private InventoryVO productServiceVO;
	 
	@OneToOne
	@JoinColumn(name="customer_id") 
	private Customer customerVO;
	  
	  
    public String getCommend() {
			return commend;
		}

    public void setCommend(String commend) {
			this.commend = commend;
		}
	 
	public Customer getCustomerVO() {
		return customerVO;
	}

	public void setCustomerVO(Customer customerVO) {
		this.customerVO = customerVO;
	}

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public String getCaseReason() {
		return caseReason;
	}

	public void setCaseReason(String caseReason) {
		this.caseReason = caseReason;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getClaimingDate() {
		return claimingDate;
	}

	public void setClaimingDate(String claimingDate) {
		this.claimingDate = claimingDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCasesolution() {
		return casesolution;
	}

	public void setCasesolution(String casesolution) {
		this.casesolution = casesolution;
	}

	public String getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(String warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	 
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCaseOrigin() {
		return caseOrigin;
	}

	public void setCaseOrigin(String caseOrigin) {
		this.caseOrigin = caseOrigin;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public InventoryVO getProductServiceVO() {
		return productServiceVO;
	}

	public void setProductServiceVO(InventoryVO productServiceVO) {
		this.productServiceVO = productServiceVO;
	}
}
