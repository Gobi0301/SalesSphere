package com.scube.crm.bo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.scube.crm.vo.FollowUp;



public class ClientBO extends BaseBO {

	private static final long serialVersionUID = -1858124723519342841L;


	/*private long id;*/
	private long sNo;
	//@Size(min = 0, max = 20)
	//@Pattern(regexp = "^[a-zA-Z/s]*$", message = "Invalid Format")
	@NotNull
	@Pattern(regexp ="^[a-zA-Z,.\\-\\:\\(\\)\\s]*$",message="Invalid Format")
	private String firstName; 
	//@Size(min = 0, max = 20)
	//@Pattern(regexp = "^[a-zA-Z/s]*$", message = "Invalid Format")
	@NotNull
	@Pattern(regexp ="^[a-zA-Z,.\\-\\:\\(\\)\\s]*$",message="Invalid Format")
	private String lastName;

	@NotNull
	private String companyName;
	@NotNull
	private String industryType;
	@NotNull
//	@Pattern(regexp = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]", message = "Invalid Format")
	private String website;
    private String timeSlot;
    private String endTimeSlot;
	@NotNull
	private String address;

	@NotNull
	@Pattern(regexp = ".+@.+\\.[a-z]+", message = "Invalid Format")
	private String emailAddress;
	@NotNull
	//@Size(min = 1111111111, message = "Mobile Number must be a 10 Digits")
	private String mobileNo;

	@NotNull
	//@Size(min = 1111111111, message = "Mobile Number must be a 10 Digits")
	private String contactNo;
	private String createdDate;
	private String nextAppointmentDate;
	
	private String warrantyDate;
	

	public String getWarrantyDate() {
		return warrantyDate;
	}
	public void setWarrantyDate(String warrantyDate) {
		this.warrantyDate = warrantyDate;
	}
	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	/**
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}
	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	private String modifiedDate;
	private List<ClientBO> customersList;
	private List<FollowUp> customerUpdateVOList;
	private AdminLoginBO loginBO;
	private boolean isDelete;
	private String description;
	private String date;
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date starDate ;
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date endDate;
	private String process;
	private long userId;
	private String userName;

	private String status;
	private AdminUserBO adminUserBO;
	private InventoryBO productServieBO;
	private List<InventoryBO> productServiceList;
	private String name;
	private String serviceName;
	
	
	List<String>emaildateList;
	private List<Long> serviceIdList;

	private Long assignedTo;
	/**
	 * @return the emailList
	 */
	public List<String> getEmaildateList() {
		return emaildateList;
	}
	public List<InventoryBO> getProductServiceList() {
		return productServiceList;
	}
	public void setProductServiceList(List<InventoryBO> productServiceList) {
		this.productServiceList = productServiceList;
	}
	/**
	 * @param emailList the emailList to set
	 */
	public void setEmaildateList(List<String> emaildateList) {
		this.emaildateList = emaildateList;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	private long updateId;

	private long clientId;
	List<String>descriptionList;
	List<String>dateList;
	/**
	 * @return the dateList
	 */
	public List<String> getDateList() {
		return dateList;
	}
	/**
	 * @param dateList the dateList to set
	 */
	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}
	/**
	 * @return the descriptionList
	 */
	public List<String> getDescriptionList() {
		return descriptionList;
	}
	/**
	 * @param descriptionList the descriptionList to set
	 */
	public void setDescriptionList(List<String> descriptionList) {
		this.descriptionList = descriptionList;
	}


	/**
	 * @return the updateId
	 */
	public long getUpdateId() {
		return updateId;
	}
	/**
	 * @param updateId the updateId to set
	 */
	public void setUpdateId(long updateId) {
		this.updateId = updateId;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the adminId
	 */
	public long getAdminId() {
		return adminId;
	}
	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	private boolean isActive;
	private long adminId;

	private String  assignedname;
	private long employerId;
	private String userType;
	/**
	 * @return the id
	 */
	/*public long getId() {
		return id;
	}
	 *//**
	 * @param id the id to set
	 *//*
	public void setId(long id) {
		this.id = id;
	}*/
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	/**
	 * @return the sNo
	 */
	public long getsNo() {
		return sNo;
	}
	/**
	 * @param sNo the sNo to set
	 */
	public void setsNo(long sNo) {
		this.sNo = sNo;
	}
	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the industryType
	 */
	public String getIndustryType() {
		return industryType;
	}
	/**
	 * @param industryType the industryType to set
	 */
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	/**
	 * @return the loginBO
	 */
	public AdminLoginBO getLoginBO() {
		return loginBO;
	}
	/**
	 * @param loginBO the loginBO to set
	 */
	public void setLoginBO(AdminLoginBO loginBO) {
		this.loginBO = loginBO;
	}
	/**
	 * @return the jobseekerProfileList
	 */
	public List<ClientBO> getCustomersList() {
		return customersList;
	}
	/**
	 * @param jobseekerProfileList the jobseekerProfileList to set
	 */
	public void setCustomersList(List<ClientBO> customersList) {
		this.customersList = customersList;
	}
	/**
	 * @return the isDelete
	 */
	public boolean isDelete() {
		return isDelete;
	}
	/**
	 * @param isDelete the isDelete to set
	 */
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the clientId
	 */
	public long getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the starDate
	 */
	public Date getStarDate() {
		return starDate;
	}
	/**
	 * @param starDate the starDate to set
	 */
	public void setStarDate(Date starDate) {
		this.starDate = starDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the process
	 */
	public String getProcess() {
		return process;
	}
	/**
	 * @param process the process to set
	 */
	public void setProcess(String process) {
		this.process = process;
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
	 * @return the modifiedDate
	 */
	public String getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the employerId
	 */
	public long getEmployerId() {
		return employerId;
	}
	/**
	 * @param employerId the employerId to set
	 */
	public void setEmployerId(long employerId) {
		this.employerId = employerId;
	}
	/**
	 * @return the assignedname
	 */
	public String getAssignedname() {
		return assignedname;
	}
	/**
	 * @param assignedname the assignedname to set
	 */
	public void setAssignedname(String assignedname) {
		this.assignedname = assignedname;
	}
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AdminUserBO getAdminUserBO() {
		return adminUserBO;
	}
	public void setAdminUserBO(AdminUserBO adminUserBO) {
		this.adminUserBO = adminUserBO;
	}
	public List<FollowUp> getCustomerUpdateVOList() {
		return customerUpdateVOList;
	}
	public void setCustomerUpdateVOList(List<FollowUp> customerUpdateVOList) {
		this.customerUpdateVOList = customerUpdateVOList;
	}
	public Long getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getNextAppointmentDate() {
		return nextAppointmentDate;
	}
	public void setNextAppointmentDate(String nextAppointmentDate) {
		this.nextAppointmentDate = nextAppointmentDate;
	}

	public InventoryBO getProductServieBO() {
		return productServieBO;
	}
	public void setProductServieBO(InventoryBO productServieBO) {
		this.productServieBO = productServieBO;
	}

	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<Long> getServiceIdList() {
		return serviceIdList;
	}
	public void setServiceIdList(List<Long> serviceIdList) {
		this.serviceIdList = serviceIdList;
	}

	private String salesOrder;
	public String getSalesOrder() {
		return salesOrder;
	}
	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
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
