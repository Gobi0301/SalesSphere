package com.scube.crm.bo;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.User;

public class OpportunityBO extends BaseBO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long opportunityId;
	
	@Pattern(regexp="^[a-zA-Z\\s]*$",message="First name shoud be a character only")
	private String firstName;
	
	@Pattern(regexp="^[a-zA-Z\\s]*$",message="Last name shoud be a character only")
	private String lastName;
	
	@NotNull(message="Salutation can not be empty")
	private String salutation;
	private int sNo;
	private String timeSlot;
	private String endTimeSlot;
	
	private Long amount;
	
	@NotNull(message="Sales Stage can not be empty")
	private String salesStage;
	
//	@NotNull(message = "Start Date can not be Empty")
	//@DateTimeFormat(pattern = "MM/dd/yyyy")
	private String endTime;
	private boolean isActive;
	
	
	
	/*
	 * private String address;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public String getAddress() { return address; }
	 * 
	 * public void setAddress(String address) { this.address = address; }
	 * 
	 * private String contactNumber; public String getContactNumber() { return
	 * contactNumber; }
	 * 
	 * public void setContactNumber(String contactNumber) { this.contactNumber =
	 * contactNumber; }
	 * 
	 * private long mobileNumber;
	 * 
	 * public long getMobileNumber() { return mobileNumber; }
	 * 
	 * public void setMobileNumber(long mobileNumber) { this.mobileNumber =
	 * mobileNumber; }
	 * 
	  private String setemailAddress;
	 
	 * 
	 * public String getSetemailAddress() { return setemailAddress; }
	 * 
	 * public void setSetemailAddress(String setemailAddress) { this.setemailAddress
	 * = setemailAddress; }
	 */
	private boolean isDelete;
	
	private AdminUserBO assignedTo;
	
	
	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	private String nextStep;
	
	private Long probability;
	private String description;
	
//	public Long getOpportunityId() {
//		return opportunityId;
//	}
//	public void setOpportunityId(Long opportunityId) {
//		this.opportunityId = opportunityId;
//	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	
	 private String emailAddress;
	
	
	
	
	public String getSalesStage() {
		return salesStage;
	}
	public void setSalesStage(String salesStage) {
		this.salesStage = salesStage;
	}
	
	public String getNextStep() {
		return nextStep;
	}
	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}
	
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getProbability() {
		return probability;
	}

	public void setProbability(Long probability) {
		this.probability = probability;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	private User user;
	private LeadsBO leads;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public LeadsBO getLeads() {
		return leads;
	}
	public void setLeads(LeadsBO leads) {
		this.leads = leads;
	}

	private InventoryBO productService;

	public InventoryBO getProductService() {
		return productService;
	}
	public void setProductService(InventoryBO productService) {
		this.productService = productService;
	}

	private AccountBO accountBO;

	public AccountBO getAccountBO() {
		return accountBO;
	}

	public void setAccountBO(AccountBO accountBO) {
		this.accountBO = accountBO;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public AdminUserBO getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(AdminUserBO assignedTo) {
		this.assignedTo = assignedTo;
	}
	
	private String descriptions;
	private String convertedDate;
	private String followupDate;
	private long activityid;
	private int entityid;
	private String entitytype;
	private List<ActivityVO> opportunityactivityList;


	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getConvertedDate() {
		return convertedDate;
	}

	public void setConvertedDate(String convertedDate) {
		this.convertedDate = convertedDate;
	}

	public String getFollowupDate() {
		return followupDate;
	}

	public void setFollowupDate(String followupDate) {
		this.followupDate = followupDate;
	}

	public long getActivityid() {
		return activityid;
	}

	public void setActivityid(long activityid) {
		this.activityid = activityid;
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

	public long getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(long opportunityId) {
		this.opportunityId = opportunityId;
	}

	public List<ActivityVO> getOpportunityactivityList() {
		return opportunityactivityList;
	}

	public void setOpportunityactivityList(List<ActivityVO> opportunityactivityList) {
		this.opportunityactivityList = opportunityactivityList;
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
