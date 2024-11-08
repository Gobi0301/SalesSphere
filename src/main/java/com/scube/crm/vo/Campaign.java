
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
@Table(name="campaign")
public class Campaign extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	private int campaignId;
	private String campaignName;
	private String campaignMode;
	private String status;
	private long expectedRevenue;
	private long actualCost;
	private long budgetedCost;
	private String expectedResponse;
	private Integer numberSent;
	private String description;
	private String startedTime;
	private String endTime;
	//private String productName; (Mapped this)
	private boolean isDelete;
	private String campaignOwner;
	
	
	private User user;
	
	private InventoryVO productServiceVO;

	/*
	 * // View DAO value persist purpose private String userName; private long
	 * userId;
	 * 
	 * public String getUserName() { return userName; } public void
	 * setUserName(String userName) { this.userName = userName; }
	 * 
	 * public long getUserId() { return userId; } public void setUserId(long userId)
	 * { this.userId = userId; }
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CampaignId")
	public int getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}
	
	@Column(name="Campaign_Name")
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	
	@Column(name="Started_Time")
	public String getStartedTime() {
		return startedTime;
	}
	public void setStartedTime(String startedTime) {
		this.startedTime = startedTime;
	}
	
	@Column(name="End_Time")
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Column(name="Campaign_Status",nullable=false)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	public long getExpectedRevenue() {
		return expectedRevenue;
	}
	public void setExpectedRevenue(long expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}
	public long getActualCost() {
		return actualCost;
	}
	public void setActualCost(long actualCost) {
		this.actualCost = actualCost;
	}
	public long getBudgetedCost() {
		return budgetedCost;
	}
	public void setBudgetedCost(long budgetedCost) {
		this.budgetedCost = budgetedCost;
	}
	public String getExpectedResponse() {
		return expectedResponse;
	}
	public void setExpectedResponse(String expectedResponse) {
		this.expectedResponse = expectedResponse;
	}
	@Column(name="Number_Sent")
	public Integer getNumberSent() {
		return numberSent;
	}
	public void setNumberSent(Integer numberSent) {
		this.numberSent = numberSent;
	}
	
	@Column(name="Description", nullable=false)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="IsDelete")
	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	@Column(name="Campaign_Mode")
	public String getCampaignMode() {
		return campaignMode;
	}
	public void setCampaignMode(String campaignMode) {
		this.campaignMode = campaignMode;
	}
	
	@Column(name="Campaign_Owner")
	public String getCampaignOwner() {
		return campaignOwner;
	}
	public void setCampaignOwner(String campaignOwner) {
		this.campaignOwner = campaignOwner;
	}
	// Mapping with user (for campaign owner)
    @OneToOne
	@JoinColumn(name="loginId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	// Mapping with Product Service (for Products)
	@OneToOne
	@JoinColumn(name="product_id")
	public InventoryVO getProductServiceVO() {
		return productServiceVO;
	}
	public void setProductServiceVO(InventoryVO productServiceVO) {
		this.productServiceVO = productServiceVO;
	}
	 
}
