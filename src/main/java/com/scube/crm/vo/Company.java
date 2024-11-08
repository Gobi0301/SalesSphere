package com.scube.crm.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "company")
public class Company {

	private long companyId;
	private String companyName;
	private String contactPerson;
	private String companyGstNo;
	private boolean isActiveInvitation = false;
	private String email;
	private boolean isDeleted = false;
	private String contactNo;
	private String mobileNo;
	/* private Blob companyLogo; */
	private String companyProfile;
	private int rank;
	private boolean adminChecked = false;
	// private String imageName;
	private String companyLogo;
	private String password;
	private String industryType;
	private String website;
	private String street;
	private String city;
	private String district;
	private String state;
	private String country;
	private String postalCode;
	private String confirmPassword;
	private boolean companyOwner;
	
	@Column(name = "modifiedBy")
	private long modifiedBy;
	@Column(name = "createdBy")
	private long createdBy;
	@Column(name = "created")
	private Date created = new Date();
	@Column(name = "modified")
	private Date modified = new Date();
	@Transient
	private int recordIndex;
	@Transient
	private int maxRecord;

	/**
	 * @return the email
	 */
	@Column(name = "email_address")
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the companiesId
	 */
	@Id
	@GeneratedValue
	public long getCompanyId() {
		return companyId;
	}

	/**
	 * @param companiesId the companiesId to set
	 */
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the companiesName
	 */
	/**
	 * @return the companiesName
	 */
	@Column(name = "companies_name")
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companiesName the companiesName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "is_active_invitation")
	public boolean getIsActiveInvitation() {
		return isActiveInvitation;
	}

	/**
	 * @param isActiveInvitation the isActiveInvitation to set
	 */
	public void setIsActiveInvitation(boolean isActiveInvitation) {
		this.isActiveInvitation = isActiveInvitation;
	}

	/**
	 * @return the isDeleted
	 */
	public boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the contactNo
	 */
	@Column(name = "contact_no")
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
	 * @return the mobileNo
	 */
	@Column(name = "mobile_no")
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
	 * @return the companyLogo
	 */
	/*
	 * @Column(name = "company_logo") public Blob getCompanyLogo() { return
	 * companyLogo; }
	 * 
	 *//**
		 * @param companyLogo the companyLogo to set
		 *//*
			 * public void setCompanyLogo(Blob companyLogo) { this.companyLogo =
			 * companyLogo; }
			 */

	/**
	 * @return the companyProfile
	 */
	@Column(name = "company_profile", columnDefinition = "TEXT")
	public String getCompanyProfile() {
		return companyProfile;
	}

	/**
	 * @param companyProfile the companyProfile to set
	 */
	public void setCompanyProfile(String companyProfile) {
		this.companyProfile = companyProfile;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Column(name = "adminChecked")
	public boolean isAdminChecked() {
		return adminChecked;
	}

	public void setAdminChecked(boolean adminChecked) {
		this.adminChecked = adminChecked;
	}

	@Column(name = "companyLogo")
	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/*
	 * public String getImageName() { return imageName; }
	 * 
	 * public void setImageName(String imageName) { this.imageName = imageName; }
	 */

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Column(name = "modifiedBy")
	public long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	

	

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getCompanyGstNo() {
		return companyGstNo;
	}

	public void setCompanyGstNo(String companyGstNo) {
		this.companyGstNo = companyGstNo;
	}

	public long getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * @param created_By the created_By to set
	 */
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated() {
		return this.created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the modified
	 */
	public Date getModified() {
		return this.modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	@Transient
	public int getRecordIndex() {
		return recordIndex;
	}

	public void setRecordIndex(int recordIndex) {
		this.recordIndex = recordIndex;
	}
	@Transient
	public int getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(int maxRecord) {
		this.maxRecord = maxRecord;
	}

	public boolean isCompanyOwner() {
		return companyOwner;
	}

	public void setCompanyOwner(boolean companyOwner) {
		this.companyOwner = companyOwner;
	}
	
	

}
