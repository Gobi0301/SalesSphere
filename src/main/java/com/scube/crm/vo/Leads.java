
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
import javax.persistence.Transient;

@Entity
@Table(name = "leads")
public class Leads extends BasicEntity {

	private static final long serialVersionUID = 1L;

	private long leadsId;
	private String firstName;
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
	private Integer noOfEmployees;
	private String rating;
	private String fax;
	private String salutation;
	private String street;
	private String city;
	private String district;
	private String state;
	private String country;
	private Integer postalCode;
	 
	private Campaign campaignVO;

	private InventoryVO productServiceVO;
	
    private ProjectVO projectVO;
    
	

	private User user;

	private boolean convertedCustomer;

	private User leadeOwner;

	/**
	 * @return the leadsId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Leads_Id")
	public long getLeadsId() {
		return leadsId;
	}

	/**
	 * @param leadsId the leadsId to set
	 */
	public void setLeadsId(long leadsId) {
		this.leadsId = leadsId;
	}

	/**
	 * @return the firstName
	 */
	@Column(name = "First_Name")
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
	@Column(name = "Last_Name")
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
	@Column(name = "Company_Name")
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
	 * @return the industryType
	 */
	@Column(name = "Industry_Type")
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
	 * @return the website
	 */
	@Column(name = "Website")
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
	 * @return the mobileNo
	 */
	@Column(name = "Mobile_No")
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
	@Column(name = "Contact_No", nullable = false)
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * @return the emailAddress
	 */
	@Column(name = "Email_Address")
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name = "IsDelete")
	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the user
	 */
	@Transient
	public User getAdminLoginVO() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setAdminLoginVO(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "Lead_Owner")
	public User getLeadeOwner() {
		return leadeOwner;
	}

	public void setLeadeOwner(User leadeOwner) {
		this.leadeOwner = leadeOwner;
	}
	
	  
	  @Column(name = "Converted_Cusotmer") public boolean isConvertedCustomer() {
	  return convertedCustomer; }
	  
	  public void setConvertedCustomer(boolean convertedCustomer) {
	  this.convertedCustomer = convertedCustomer; }
	 

	@Column(name = "Status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToOne
	@JoinColumn(name = "Campaign_Id")
	//private Campaign campaign;
	
	public Campaign getCampaignVO() {
		return campaignVO;
	}

	public void setCampaignVO(Campaign campaignVO) {
		this.campaignVO = campaignVO;
	}
	@OneToOne
	@JoinColumn(name = "Project_Id")
	public ProjectVO getProjectVO() {
		return projectVO;
	}

	public void setProjectVO(ProjectVO projectVO) {
		this.projectVO = projectVO;
	}

	@OneToOne
	@JoinColumn(name = "Product_Service_Id")
	public InventoryVO getProductServiceVO() {
		return productServiceVO;
	}

	public void setProductServiceVO(InventoryVO productServiceVO) {
		this.productServiceVO = productServiceVO;
	}

	@Column(name = "Annual_Revenue")
	public Long getAnnualRevenue() {
		return annualRevenue;
	}

	public void setAnnualRevenue(Long annualRevenue) {
		this.annualRevenue = annualRevenue;
	}

	@Column(name = "Designation")
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Column(name = "Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "No_Of_Employees")
	public Integer getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(Integer noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

	@Column(name = "Rating")
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@Column(name = "Fax")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "Salutation")
	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	@Column(name = "Street")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "City")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "District")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "State")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "Country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "Postal_Code")
	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}

}
