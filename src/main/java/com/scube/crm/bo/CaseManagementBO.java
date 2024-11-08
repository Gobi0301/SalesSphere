package com.scube.crm.bo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CaseManagementBO extends BaseBO {

	private static final long serialVersionUID = 1L;
	
	
	private long caseId;
	@NotNull(message="caseReason cannot be empty")
	@Pattern(regexp="^[a-zA-Z\\s]*$",message="Case Reason should be a character only")
	private String caseReason;
	@NotNull(message="expireDate cannot be empty") 
	private String expireDate;
	@NotNull(message="claimingDate cannot be empty")
	private String claimingDate;
	@NotNull(message="description cannot be empty")
	private String description;
	@NotNull(message="casesolution cannot be empty")
	private String casesolution;
	@NotNull(message="warrantyDate cannot be empty")
	private String warrantyDate; 
	@NotNull(message="email cannot be empty")
	private String email;
	@NotNull(message="phoneNo cannot be empty")
	private Long phoneNo;
	@NotNull(message="caseOrigin cannot be empty")
	private String caseOrigin;
	@NotNull(message="Type cannot be empty")
	private String Type;
	@NotNull(message="category cannot be empty")
	private String category;
	@NotNull(message="status cannot be empty")
	private String status;
	@NotNull(message="priority cannot be empty")
	private String priority; 
	@NotNull(message="commend cannot be empty")
	private String commend;
	  
     private boolean isDelete;


	 
	public boolean isDelete() {
		return isDelete;
	}
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	private ClientBO clientBO;
	private InventoryBO productServiceBO;


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
	public ClientBO getClientBO() {
		return clientBO;
	}
	public void setClientBO(ClientBO clientBO) {
		this.clientBO = clientBO;
	}
	public InventoryBO getProductServiceBO() {
		return productServiceBO;
	}
	public void setProductServiceBO(InventoryBO productServiceBO) {
		this.productServiceBO = productServiceBO;
	}
	public String getCommend() {
		return commend;
	}
	public void setCommend(String commend) {
		this.commend = commend;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	private int sNo;
}