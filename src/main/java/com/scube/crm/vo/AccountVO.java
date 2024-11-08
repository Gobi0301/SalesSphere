package com.scube.crm.vo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "accounts")
public class AccountVO extends BasicEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int masterRecordId;
	private Integer accountId;
	private String accountName;
	private int recordTypeId;
	private String site;
	private Long accountSource;
	private Long annualRevenue;
	private String billingStreet;
	private String billingCity;
	private String billingStateCode;
	private String billingState;
	private String billingPostalCode;
	private String billingCountryCode;
	private String billingCountry;
	private String jigsaw;
	// private Boolean iscustomerPortal;
	private Boolean isPartner;
	private Boolean isParsonAccount;
	private String description;
	private Integer numberOfEmployees;
	private long fax;
	private String industry;
	private String lastActivityDate;
	private String ownership;
	private int parentId;
	private Long phone;
	private String rating;
	private int connectionReceivedId;
	private String sic;
	private String sicDesc;
	private int connectionSentId;
	private String shippingStreet;
	private String shippingCity;
	private String shippingStateCode;
	private String shippingState;
	private String shippingPostalCode;
	private String shippingCountryCode;
	private String shippingCountry;
	private String tickerSymbol;
	private String type;
	private String website;
	private String cleanStatus;
	private String dunsNumber;
	private String naicsCode;
	private String naicsDesc;
	private String salutation;
	private String tradeStyle;
	private String yearStarted;
	// private UserDO userDO;
	private int ownerId;
	private String street;
	private Boolean isDeleted;
	private String role;
	private String groupBy;
	private String parentAccount;
	private String accountOwner;
	private String email;
	private int SICCode;
	private String pagination = null;
	private User user;
	private List<ContactVO> contact;
	private int sNO;
	//private Opportunity opportunitys;
	
	private long id;

	
	@Transient
	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	@Column(name = "ACCOUNT_OWNER")
	public String getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}

	@Column(name = "E_MAIL", unique = true, nullable = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "SIC_CODE")
	public int getSICCode() {
		return SICCode;
	}

	public void setSICCode(int sICCode) {
		SICCode = sICCode;
	}

	@Column(name = "PARENT_ACCOUNT")
	public String getParentAccount() {
		return parentAccount;
	}

	public void setParentAccount(String parentAccount) {
		this.parentAccount = parentAccount;
	}

	/**
	 * @return the masterRecordId
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "MASTER_RECORD_ID")
	public int getMasterRecordId() {
		return masterRecordId;
	}

	/**
	 * @param masterRecordId the masterRecordId to set
	 */
	public void setMasterRecordId(int masterRecordId) {
		this.masterRecordId = masterRecordId;
	}

	/**
	 * @return the accountId
	 */
	@Id
	@GeneratedValue
	@Column(name = "ACCOUNT_ID", unique = true, nullable = false)
	public Integer getAccountId() {

		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the accountName
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Analyzer(impl = WhitespaceAnalyzer.class)
	@Column(name = "ACCOUNT_NAME")
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the recordTypeId
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "RECORD_TYPE_ID")
	public int getRecordTypeId() {
		return recordTypeId;
	}

	/**
	 * @param recordTypeId the recordTypeId to set
	 */
	public void setRecordTypeId(int recordTypeId) {
		this.recordTypeId = recordTypeId;
	}

	/**
	 * @return the site
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "SITE")
	public String getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(String site) {
		this.site = site;
	}

	/**
	 * @return the accountSource
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "ACCOUNT_SOURCE")
	public Long getAccountSource() {
		return accountSource;
	}

	/**
	 * @param accountSource the accountSource to set
	 */
	public void setAccountSource(Long accountSource) {
		this.accountSource = accountSource;
	}

	/**
	 * @return the annualRevenue
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "ANNUAL_REVENUE")
	public Long getAnnualRevenue() {
		return annualRevenue;
	}

	/**
	 * @param annualRevenue the annualRevenue to set
	 */
	public void setAnnualRevenue(Long annualRevenue) {
		this.annualRevenue = annualRevenue;
	}

	/**
	 * @return the billingStreet
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "BILLING_STREET")
	public String getBillingStreet() {
		return billingStreet;
	}

	/**
	 * @param billingStreet the billingStreet to set
	 */
	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}

	/**
	 * @return the billingCity
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "BILLING_CITY")
	public String getBillingCity() {
		return billingCity;
	}

	/**
	 * @param billingCity the billingCity to set
	 */
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	/**
	 * @return the billingStateCode
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "BILLING_STATE_CODE")
	public String getBillingStateCode() {
		return billingStateCode;
	}

	/**
	 * @param billingStateCode the billingStateCode to set
	 */
	public void setBillingStateCode(String billingStateCode) {
		this.billingStateCode = billingStateCode;
	}

	/**
	 * @return the billingState
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "BILLING_STATE")
	public String getBillingState() {
		return billingState;
	}

	/**
	 * @param billingState the billingState to set
	 */
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	/**
	 * @return the billingPostalCode
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "BILLING_POSTAL_CODE")
	public String getBillingPostalCode() {
		return billingPostalCode;
	}

	/**
	 * @param billingPostalCode the billingPostalCode to set
	 */
	public void setBillingPostalCode(String billingPostalCode) {
		this.billingPostalCode = billingPostalCode;
	}

	/**
	 * @return the billingCountryCode
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "BILLING_COUNTRY_CODE")
	public String getBillingCountryCode() {
		return billingCountryCode;
	}

	/**
	 * @param billingCountryCode the billingCountryCode to set
	 */
	public void setBillingCountryCode(String billingCountryCode) {
		this.billingCountryCode = billingCountryCode;
	}

	/**
	 * @return the billingCountry
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "BILLING_COUNTRY")
	public String getBillingCountry() {
		return billingCountry;
	}

	/**
	 * @param billingCountry the billingCountry to set
	 */
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}

	/**
	 * @return the jigsaw
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "JIGSAW")
	public String getJigsaw() {
		return jigsaw;
	}

	/**
	 * @param jigsaw the jigsaw to set
	 */
	public void setJigsaw(String jigsaw) {
		this.jigsaw = jigsaw;
	}

	/**
	 * @return the description
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "DESCRIPTION")
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
	 * @return the numberOfEmployees
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "NUMBER_OF_EMPL0YEES")
	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	/**
	 * @param numberOfEmployees the numberOfEmployees to set
	 */
	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	/**
	 * @return the fax
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "FAX")
	public long getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(long fax) {
		this.fax = fax;
	}

	/**
	 * @return the industry
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "INDUSTRY")
	public String getIndustry() {
		return industry;
	}

	/**
	 * @param industry the industry to set
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * @return the iscustomerPortal
	 */

	/*
	 * @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	 * 
	 * @Column(name = "IS_CUSTOMER_PORTAL") public Boolean getIscustomerPortal() {
	 * return iscustomerPortal; }
	 * 
	 *//**
		 * @param iscustomerPortal the iscustomerPortal to set
		 *//*
			 * public void setIscustomerPortal(Boolean iscustomerPortal) {
			 * this.iscustomerPortal = iscustomerPortal; }
			 */

	/**
	 * @return the isPartner
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "IS_PARTNER")
	public Boolean getIsPartner() {
		return isPartner;
	}

	/**
	 * @param isPartner the isPartner to set
	 */
	public void setIsPartner(Boolean isPartner) {
		this.isPartner = isPartner;
	}

	/**
	 * @return the isParsonAccount
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "IS_PERSON_ACCOUNT")
	public Boolean getIsParsonAccount() {
		return isParsonAccount;
	}

	/**
	 * @param isParsonAccount the isParsonAccount to set
	 */
	public void setIsParsonAccount(Boolean isParsonAccount) {
		this.isParsonAccount = isParsonAccount;
	}

	/**
	 * @return the lastActivityDate
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "LAST_ACTIVITY_DATE")
	public String getLastActivityDate() {
		return lastActivityDate;
	}

	/**
	 * @param lastActivityDate the lastActivityDate to set
	 */
	public void setLastActivityDate(String lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	/**
	 * @return the ownership
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "OWNER_SHIP")
	public String getOwnership() {
		return ownership;
	}

	/**
	 * @param ownership the ownership to set
	 */
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	/**
	 * @return the parentId
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "PARENT_ID")
	public int getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the phone
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "PHONE")
	public Long getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(Long phone) {
		this.phone = phone;
	}

	/**
	 * @return the rating
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "RATING")
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the connectionReceivedId
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "CONNECTION_RECEIVED_ID")
	public int getConnectionReceivedId() {
		return connectionReceivedId;
	}

	/**
	 * @param connectionReceivedId the connectionReceivedId to set
	 */
	public void setConnectionReceivedId(int connectionReceivedId) {
		this.connectionReceivedId = connectionReceivedId;
	}

	/**
	 * @return the sic
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "SIC")
	public String getSic() {
		return sic;
	}

	/**
	 * @param sic the sic to set
	 */
	public void setSic(String sic) {
		this.sic = sic;
	}

	/**
	 * @return the sicDesc
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "SIC_DESC")
	public String getSicDesc() {
		return sicDesc;
	}

	/**
	 * @param sicDesc the sicDesc to set
	 */
	public void setSicDesc(String sicDesc) {
		this.sicDesc = sicDesc;
	}

	/**
	 * @return the connectionSentId
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "CONNECTION_SENT_ID")
	public int getConnectionSentId() {
		return connectionSentId;
	}

	/**
	 * @param connectionSentId the connectionSentId to set
	 */
	public void setConnectionSentId(int connectionSentId) {
		this.connectionSentId = connectionSentId;
	}

	/**
	 * @return the shippingStreet
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "SHIPPING_STREET")
	public String getShippingStreet() {
		return shippingStreet;
	}

	/**
	 * @param shippingStreet the shippingStreet to set
	 */
	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}

	/**
	 * @return the shippingCity
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "SHIPPING_CITY")
	public String getShippingCity() {
		return shippingCity;
	}

	/**
	 * @param shippingCity the shippingCity to set
	 */
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	/**
	 * @return the shippingStateCode
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "SHIPPING_STATE_CODE")
	public String getShippingStateCode() {
		return shippingStateCode;
	}

	/**
	 * @param shippingStateCode the shippingStateCode to set
	 */
	public void setShippingStateCode(String shippingStateCode) {
		this.shippingStateCode = shippingStateCode;
	}

	/**
	 * @return the shippingState
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "SHIPPING_STATE")
	public String getShippingState() {
		return shippingState;
	}

	/**
	 * @param shippingState the shippingState to set
	 */
	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	/**
	 * @return the shippingPostalCode
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "SHIPPING_POSTAL_CODE")
	public String getShippingPostalCode() {
		return shippingPostalCode;
	}

	/**
	 * @param shippingPostalCode the shippingPostalCode to set
	 */
	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}

	/**
	 * @return the shippingCountryCode
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "SHIPPING_COUNTRY_CODE")
	public String getShippingCountryCode() {
		return shippingCountryCode;
	}

	/**
	 * @param shippingCountryCode the shippingCountryCode to set
	 */
	public void setShippingCountryCode(String shippingCountryCode) {
		this.shippingCountryCode = shippingCountryCode;
	}

	/**
	 * @return the shippingCountry
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "SHIPPING_COUNTRY")
	public String getShippingCountry() {
		return shippingCountry;
	}

	/**
	 * @param shippingCountry the shippingCountry to set
	 */
	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	/**
	 * @return the tickerSymbol
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "TICKER_SYMBOL")
	public String getTickerSymbol() {
		return tickerSymbol;
	}

	/**
	 * @param tickerSymbol the tickerSymbol to set
	 */
	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}

	/**
	 * @return the type
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the website
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "WEB_SITE")
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
	 * @return the cleanStatus
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "CLEAN_STATUS")
	public String getCleanStatus() {
		return cleanStatus;
	}

	/**
	 * @param cleanStatus the cleanStatus to set
	 */
	public void setCleanStatus(String cleanStatus) {
		this.cleanStatus = cleanStatus;
	}

	/**
	 * @return the dunsNumber
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "DUNS_NUMBER")
	public String getDunsNumber() {
		return dunsNumber;
	}

	/**
	 * @param dunsNumber the dunsNumber to set
	 */
	public void setDunsNumber(String dunsNumber) {
		this.dunsNumber = dunsNumber;
	}

	/**
	 * @return the naicsCode
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "NAICS_CODE")
	public String getNaicsCode() {
		return naicsCode;
	}

	/**
	 * @param naicsCode the naicsCode to set
	 */
	public void setNaicsCode(String naicsCode) {
		this.naicsCode = naicsCode;
	}

	/**
	 * @return the naicsDesc
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "NAICS_DESC")
	public String getNaicsDesc() {
		return naicsDesc;
	}

	/**
	 * @param naicsDesc the naicsDesc to set
	 */
	public void setNaicsDesc(String naicsDesc) {
		this.naicsDesc = naicsDesc;
	}

	/**
	 * @return the salutation
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "SALUTATION")
	public String getSalutation() {
		return salutation;
	}

	/**
	 * @param salutation the salutation to set
	 */
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	/**
	 * @return the tradeStyle
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "TRADE_STYLE")
	public String getTradeStyle() {
		return tradeStyle;
	}

	/**
	 * @param tradeStyle the tradeStyle to set
	 */
	public void setTradeStyle(String tradeStyle) {
		this.tradeStyle = tradeStyle;
	}

	/**
	 * @return the yearStarted
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "YEAR_STARTED")
	public String getYearStarted() {
		return yearStarted;
	}

	/**
	 * @param yearStarted the yearStarted to set
	 */
	public void setYearStarted(String yearStarted) {
		this.yearStarted = yearStarted;
	}

	/**
	 * @return the ownerId
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "OWNER_ID")
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the street
	 */

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "Street")
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the isDeleted
	 */
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "IS_DELETED")
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the role
	 */
	@Column(name = "ROLE")
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the groupBy
	 */
	@Column(name = "GROUP_BY")
	public String getGroupBy() {
		return groupBy;
	}

	/**
	 * @param groupBy the groupBy to set
	 */
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	public List<ContactVO> getContact() {
		return contact;
	}

	public void setContact(List<ContactVO> contact) {
		this.contact = contact;
	}

	@Transient
	public int getsNO() {
		return sNO;
	}

	public void setsNO(int sNO) {
		this.sNO = sNO;
	}

	/*@OneToOne
	@JoinColumn(name = "OPPORTUNITY_ID")
	public Opportunity getOpportunitys() {
		return opportunitys;
	}

	public void setOpportunitys(Opportunity opportunitys) {
		this.opportunitys = opportunitys;
	}*/

	@Transient
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
