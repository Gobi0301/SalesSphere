/**
 * 
 */
package com.scube.crm.bo;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.LeadsFollowup;

public class LeadsBO extends BaseBO {

	private static final long serialVersionUID = -2769612946293091952L;

	private long leadsId;
	@Pattern(regexp = "^[a-zA-Z\\s]*$", message = "First name shoud be a character only")
	@NotBlank(message = "Please Enter Name")
	private String firstName;
	@Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Last name shoud be a character only")
	private String lastName;
	private String companyName;
	private String industryType;
	private String website;
	private String mobileNo;
	private String contactNo;
	private String emailAddress;
	private boolean isDelete;
	private String status;
	private Long annualRevenue;
	private String designation;
	private String description;
	private int noOfEmployees;
	private String rating;
	private String fax;
	private String salutation;
	private String street;
	private String city;
	private String district;
	private String state;
	private String country;
	private Integer postalCode;
	private String timeSlot;
	private String endTimeSlot;
	private File uploadleads;

	private boolean isActive;
	private String userName;
	private long userId;
	private long adminId;
	private int sNo;
	private AdminUserBO adminUserBO;
	private AdminLoginBO adminLoginBO;
	// private boolean convertedCustomer;
	private List<LeadsBO> leadsList;
	private List<LeadsFollowup> leadsUpdateVOList;
	private long followupId;
	private String date;
	private Long followupCount;
	private CampaignBO campaignBO;
	private InventoryBO productServiceBO;
	private ProjectBO projectBO;

	private int productOrProjectId;

	public int getProductOrProjectId() {
		return productOrProjectId;
	}

	public void setProductOrProjectId(int productOrProjectId) {
		this.productOrProjectId = productOrProjectId;
	}

	public ProjectBO getProjectBO() {
		return projectBO;
	}

	public void setProjectBO(ProjectBO projectBO) {
		this.projectBO = projectBO;
	}

	private String serviceName;
	private String nextAppointmentDate;
	private String createdDate;
	private String modifiedDate;
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date startDate;
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date endDate;
	private String process;
	private Long assignedTo;

	private int entityid;
	private String entitytype;
	private long activityid;
	private List<ActivityVO> leadupdateList;
//	private Long campaignId;

	private List<TaskManagementBO> taskManagementBOList;

	public List<TaskManagementBO> getTaskManagementBOList() {
		return taskManagementBOList;
	}

	public void setTaskManagementBOList(List<TaskManagementBO> taskManagementBOList) {
		this.taskManagementBOList = taskManagementBOList;
	}

	public long getLeadsId() {
		return leadsId;
	}

	public void setLeadsId(long leadsId) {
		this.leadsId = leadsId;
	}

	public CampaignBO getCampaignBO() {
		return campaignBO;
	}

	public void setCampaignBO(CampaignBO campaignBO) {
		this.campaignBO = campaignBO;
	}

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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the adminLoginBO
	 */
	public AdminLoginBO getAdminLoginBO() {
		return adminLoginBO;
	}

	/**
	 * @param adminLoginBO the adminLoginBO to set
	 */
	public void setAdminLoginBO(AdminLoginBO adminLoginBO) {
		this.adminLoginBO = adminLoginBO;
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

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<LeadsBO> getLeadsList() {
		return leadsList;
	}

	public void setLeadsList(List<LeadsBO> leadsList) {
		this.leadsList = leadsList;
	}

	public List<LeadsFollowup> getLeadsUpdateVOList() {
		return leadsUpdateVOList;
	}

	public void setLeadsUpdateVOList(List<LeadsFollowup> leadsUpdateVOList) {
		this.leadsUpdateVOList = leadsUpdateVOList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public Long getFollowupCount() {
		return followupCount;
	}

	public void setFollowupCount(Long followupCount) {
		this.followupCount = followupCount;
	}

	public String getNextAppointmentDate() {
		return nextAppointmentDate;
	}

	public void setNextAppointmentDate(String nextAppointmentDate) {
		this.nextAppointmentDate = nextAppointmentDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public long getFollowupId() {
		return followupId;
	}

	public void setFollowupId(long followupId) {
		this.followupId = followupId;
	}

	public Long getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}

	public InventoryBO getProductServiceBO() {
		return productServiceBO;
	}

	public void setProductServiceBO(InventoryBO productServiceBO) {
		this.productServiceBO = productServiceBO;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public AdminUserBO getAdminUserBO() {
		return adminUserBO;
	}

	public void setAdminUserBO(AdminUserBO adminUserBO) {
		this.adminUserBO = adminUserBO;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAnnualRevenue() {
		return annualRevenue;
	}

	public void setAnnualRevenue(Long annualRevenue) {
		this.annualRevenue = annualRevenue;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(int noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
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

	public List<ActivityVO> getLeadupdateList() {
		return leadupdateList;
	}

	public void setLeadupdateList(List<ActivityVO> leadupdateList) {
		this.leadupdateList = leadupdateList;
	}

	public File getUplode() {
		return uploadleads;
	}

	public void setUplode(File uplode) {
		this.uploadleads = uplode;
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
