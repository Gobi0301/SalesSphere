package com.scube.crm.bo;

import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.scube.crm.model.User;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.Campaign;
import com.scube.crm.vo.CampaignUpdateVO;

public class CampaignBO extends BaseBO {

	private static final long serialVersionUID = 1L;

	private int campaignId;
	@Size(message="{campaign.name.empty}")
	//@Pattern(regexp="^[a-zA-Z\\s]*$",message="Campaign Name should be a character only")
	private String campaignName;
	@Size(message="Campaign Mode can not be empty")
	private String campaignMode;
	@Transient
	@Size(message="Campaign Owner can not be empty")
	private String campaignOwner;
	@NotNull(message = "Start Date can not be Empty")
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private String startedTime;
	@NotNull(message = "End Date can not be Empty")
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private String endTime;
	private boolean isDelete;
	private int sNo;
	@Size(message="Campaign Status can not be empty")
	private String status;
	@NotNull(message="Expected Revenue can not be empty")
	private Long expectedRevenue;
	
	private long actualCost;
	@NotNull(message="Budgeted Cost can not be empty")
	private Long budgetedCost;
	@NotNull(message="Expected Response can not be empty")
	private String expectedResponse;
	@NotNull(message="Number of Employees can not be empty")
	private Integer numberSent;
	@Size(message="Description can not be empty")
	private String description;
	
	private String endTimeSlot;
	private String timeSlot;
	private long updatecampaignId;
	
	 public long getUpdatecampaignId() {
			return updatecampaignId;
		}
		public void setUpdatecampaignId(long updatecampaignId) {
			this.updatecampaignId = updatecampaignId;
		}
		
	private String uploadfile;
	public String getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}
	private long campaignupdateid;
	private String descriptions;
	private String date;
	private Campaign campaign;
	private User userVO;
	private List<CampaignUpdateVO> campaignUpdateVOList;
	
	private int entityid;
	private String entitytype;
	private long activityid;
	private List<ActivityVO> campaignactivityList;
	
	private List<TaskManagementBO> taskManagementBOList;

	public List<TaskManagementBO> getTaskManagementBOList() {
	return taskManagementBOList;
    }
    public void setTaskManagementBOList(List<TaskManagementBO> taskManagementBOList) {
	this.taskManagementBOList = taskManagementBOList;
    }
	
	
	public int getEntityid() {
		return entityid;
	}
	public void setEntityid(int entityid) {
		this.entityid = entityid;
	}
	public String getEntitytype() {
		return entitytype;
	}
	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}
	public long getCampaignupdateid() {
		return campaignupdateid;
	}
	public void setCampaignupdateid(long campaignupdateid) {
		this.campaignupdateid = campaignupdateid;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	public User getUserVO() {
		return userVO;
	}
	public void setUserVO(User userVO) {
		this.userVO = userVO;
	}
	public List<CampaignUpdateVO> getCampaignUpdateVOList() {
		return campaignUpdateVOList;
	}
	public void setCampaignUpdateVOList(List<CampaignUpdateVO> campaignUpdateVOList) {
		this.campaignUpdateVOList = campaignUpdateVOList;
	}
	
	
	private AdminLoginBO adminLoginBO;
	private InventoryBO productServiceBO;
	
	private String userName;
	private long userId;

	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	public long getActualCost() {
		return actualCost;
	}
	public void setActualCost(long actualCost) {
		this.actualCost = actualCost;
	}
	
	public Long getExpectedRevenue() {
		return expectedRevenue;
	}
	public void setExpectedRevenue(Long expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}
	public Long getBudgetedCost() {
		return budgetedCost;
	}
	public void setBudgetedCost(Long budgetedCost) {
		this.budgetedCost = budgetedCost;
	}
	public String getExpectedResponse() {
		return expectedResponse;
	}
	public void setExpectedResponse(String expectedResponse) {
		this.expectedResponse = expectedResponse;
	}
	public Integer getNumberSent() {
		return numberSent;
	}
	public void setNumberSent(Integer numberSent) {
		this.numberSent = numberSent;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public String getStartedTime() {
		return startedTime;
	}
	public void setStartedTime(String startedTime) {
		this.startedTime = startedTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}
	
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	
	
	public String getCampaignMode() {
		return campaignMode;
	}
	public void setCampaignMode(String campaignMode) {
		this.campaignMode = campaignMode;
	}
	@Transient
	public String getCampaignOwner() {
		return campaignOwner;
	}
	public void setCampaignOwner(String campaignOwner) {
		this.campaignOwner = campaignOwner;
	}
	public AdminLoginBO getAdminLoginBO() {
		return adminLoginBO;
	}
	public void setAdminLoginBO(AdminLoginBO adminLoginBO) {
		this.adminLoginBO = adminLoginBO;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	/**
	 * @return the sNo
	 */
	public int getsNo() {
		return sNo;
	}
	/**
	 * @param sNo the sNo to set
	 */
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public InventoryBO getProductServiceBO() {
		return productServiceBO;
	}
	public void setProductServiceBO(InventoryBO productServiceBO) {
		this.productServiceBO = productServiceBO;
	}
	public boolean getisDelete() {
		return isDelete;
	}
	public void setisDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public long getActivityid() {
		return activityid;
	}
	public void setActivityid(long activityvo) {
		this.activityid = activityvo;
	}
	public List<ActivityVO> getCampaignactivityList() {
		return campaignactivityList;
	}
	public void setCampaignactivityList(List<ActivityVO> campaignactivityList) {
		this.campaignactivityList = campaignactivityList;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public String getEndTimeSlot() {
		return endTimeSlot;
	}
	public void setEndTimeSlot(String endTimeSlot) {
		this.endTimeSlot = endTimeSlot;
	}



}
