package com.scube.crm.vo;

         // Generated 5 Feb, 2015 10:16:28 AM by Hibernate Tools 4.0.0


import java.sql.Blob;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * JobSeekerProfile generated by hbm2java
 */

@Entity
@Table(name = "customer")
public class Customer extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name = "first_Name")
	private String firstName;

	@Column(name = "last_Name")
	private String lastName;

	@Column(name = "email_Id")
	private String emailAddress;	

	@Column(name = "contact_Number")
	private String contactNumber;

	@Column(name = "mobile_Number")
	private String mobileNumber;	

	@Column(name = "company_Name")
	private String companyName;

	@Column(name = "company_Type")
	private String companyType;

	@Column(name = "industry_Type")
	private String industryType;

	@Column(name = "website")
	private String webSite;
	
	@Column(name = "warrantyDate")
	private String warrantyDate;

	public String getWarrantyDate() {
		return warrantyDate;
	}
	public void setWarrantyDate(String warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	@Column(name="isActive")
	private boolean isActive;

	private Boolean termsConditionsAgreed;

	private Blob companyLogo;

	private long deletedBy;

	private Date deletedDate;

	@Column(name="isDelete")
	private boolean isDelete;

	@Column(name="address")
	private String address;

	@Column(name="update_status")
	private boolean updateStatus;

	@Column(name="assigned_to")
	private long assigned;

	@Column(name="status")
	private String status;

	private boolean migrationStatus;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User loginVO;
	public User getLoginVO() {
		return loginVO;
	}
	public void setLoginVO(User loginVO) {
		this.loginVO = loginVO;
	}

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="customers_products",joinColumns= {@JoinColumn(name="customer_Id")},inverseJoinColumns={@JoinColumn(name="product_Id",unique=false)})
	private List<InventoryVO> productServiceVO;



	/*private List<EmployerProfileVO> employerProfileVO;
	private List<EmployerLoginVO> employerLoginVO;*/

	public List<InventoryVO> getProductServiceVO() {
		return productServiceVO;
	}

	public void setProductServiceVO(List<InventoryVO> productServiceVO) {
		this.productServiceVO = productServiceVO;
	}


	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */

	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */

	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the emailAddress
	 */

	public String getEmailAddress() {
		return this.emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}



	/**
	 * @return the contactNumber
	 */

	public String getContactNumber() {
		return this.contactNumber;
	}

	/**
	 * @param contactNumber
	 *            the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the mobileNumber
	 */

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	/**
	 * @param mobileNumber
	 *            the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}





	/**
	 * @return the companyName
	 */

	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the companyType
	 */

	public String getCompanyType() {
		return this.companyType;
	}

	/**
	 * @param companyType
	 *            the companyType to set
	 */
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	/**
	 * @return the industryType
	 */

	public String getIndustryType() {
		return this.industryType;
	}

	/**
	 * @param industryType
	 *            the industryType to set
	 */
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	/**
	 * @return the webSite
	 */

	public String getWebSite() {
		return this.webSite;
	}

	/**
	 * @param webSite
	 *            the webSite to set
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}



	/**
	 * @return the termsConditionsAgreed
	 */
	public Boolean getTermsConditionsAgreed() {
		return this.termsConditionsAgreed;
	}

	/**
	 * @param termsConditionsAgreed
	 *            the termsConditionsAgreed to set
	 */
	public void setTermsConditionsAgreed(Boolean termsConditionsAgreed) {
		this.termsConditionsAgreed = termsConditionsAgreed;
	}

	/**
	 * @return the companyLogo
	 */
	public Blob getCompanyLogo() {
		return this.companyLogo;
	}

	/**
	 * @param companyLogo
	 *            the companyLogo to set
	 */
	public void setCompanyLogo(Blob companyLogo) {
		this.companyLogo = companyLogo;
	}


	public Date getDeletedDate() {
		return this.deletedDate;
	}

	/**
	 * @param deletedDate
	 *            the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @return the deletedBy
	 */
	public long getDeletedBy() {
		return this.deletedBy;
	}

	/**
	 * @param deletedBy
	 *            the deletedBy to set
	 */
	public void setDeletedBy(long deletedBy) {
		this.deletedBy = deletedBy;
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
	 * @return the updateStatus
	 */

	public boolean isUpdateStatus() {
		return updateStatus;
	}

	/**
	 * @param updateStatus the updateStatus to set
	 */
	public void setUpdateStatus(boolean updateStatus) {
		this.updateStatus = updateStatus;
	}

	/**
	 * @return the loginVO
	 */

	/**
	 * @return the isActive
	 */

	public boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the isDelete
	 */

	public boolean getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the assigned
	 */

	public long getAssigned() {
		return assigned;
	}

	/**
	 * @param assigned the assigned to set
	 */
	public void setAssigned(long assigned) {
		this.assigned = assigned;
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
	 * @return the migrationStatus
	 */
	@Column(name="migration")
	public boolean isMigrationStatus() {
		return migrationStatus;
	}

	/**
	 * @param migrationStatus the migrationStatus to set
	 */
	public void setMigrationStatus(boolean migrationStatus) {
		this.migrationStatus = migrationStatus;
	}



}
