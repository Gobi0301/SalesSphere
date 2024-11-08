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
@Table(name="opportunity_list")
public class Opportunity extends BasicEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long opportunityId;
	private String firstName;
	private String lastName;
	
		
		private boolean isDelete;
		private String salutation;
		private Long amount;
		private String salesStage;
		private String nextStep;
		private Long probability;
		private String description;
		private String endTime;
		private boolean isActive;
		 private String emailAddress;
			
		private User user;
		private Leads leads;
	
		
		private InventoryVO productService;
		private AccountVO accountVO;
	/*
	 * private String address; private String emailAddress; private String
	 * mobileNumber; private String contactNumber;
	 */
	
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="OPPORTUNITY_ID" , unique=true,nullable=false)
		public Long getOpportunityId() {
			return opportunityId;
		}
		public void setOpportunityId(Long opportunityId) {
			this.opportunityId = opportunityId;
		}
		
		@Column(name="FIRST_NAME")
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		
		@Column(name="LAST_NAME")
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		
		
		@Column(name="isDelete")
		public boolean getIsDelete() {
			return isDelete;
		}

		public void setIsDelete(boolean isDelete) {
			this.isDelete = isDelete;
		}
		
		@Column(name="SALUTATION")
		public String getSalutation() {
			return salutation;
		}
		public void setSalutation(String salutation) {
			this.salutation = salutation;
		}
		
		
		@Column(name="SALES_STAGE")
		public String getSalesStage() {
			return salesStage;
		}
		public void setSalesStage(String salesStage) {
			this.salesStage = salesStage;
		}
		
		@Column(name="NEXT_STEP")
		public String getNextStep() {
			return nextStep;
		}
		public void setNextStep(String nextStep) {
			this.nextStep = nextStep;
		}
		
		
	/*
	 * @Column(name="ADDRESS") public String getAddress() { return address; } public
	 * void setAddress(String address) { this.address = address; }
	 * 
	 * @Column(name="EMAIL_ADDRESS") public String getEmailAddress() { return
	 * emailAddress; } public void setEmailAddress(String emailAddress) {
	 * this.emailAddress = emailAddress; }
	 * 
	 * @Column(name="MOBILE_NUMBER") public String getMobileNumber() { return
	 * mobileNumber; } public void setMobileNumber(String mobileNumber) {
	 * this.mobileNumber = mobileNumber; }
	 * 
	 * @Column(name="CONTACT_NUMBER") public String getContactNumber() { return
	 * contactNumber; } public void setContactNumber(String contactNumber) {
	 * this.contactNumber = contactNumber; }
	 */
		
		
		@Column(name="DESCRIPTION")
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		@Column(name="AMOUNT")
		public Long getAmount() {
			return amount;
		}
		public void setAmount(Long amount) {
			this.amount = amount;
		}
		@Column(name="PROBABILITY")
		public Long getProbability() {
			return probability;
		}
		public void setProbability(Long probability) {
			this.probability = probability;
		}
		
		@Column(name="CLOSING_DATE")
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		
		@OneToOne
		@JoinColumn(name="USER_ID")
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		
		@OneToOne
		@JoinColumn(name="LEAD_ID")
		public Leads getLeads() {
			return leads;
		}
		public void setLeads(Leads leads) {
			this.leads = leads;
		}
		
		@OneToOne
		@JoinColumn(name="PRODUCT_ID")
		public InventoryVO getProductService() {
			return productService;
		}
		public void setProductService(InventoryVO productService) {
			this.productService = productService;
		}
		
		@OneToOne
		@JoinColumn(name="ACCOUNT_SOURCE")
		public AccountVO getAccountVO() {
			return accountVO;
		}
		public void setAccountVO(AccountVO accountVO) {
			this.accountVO = accountVO;
		}
		public boolean getIsActive() {
			return isActive;
		}
		
		public void setIsActive(boolean isActive) {
			this.isActive = isActive;
		}
		
		
		@ManyToOne
		@JoinColumn(name="user_id")
		private User loginVO;


		public User getLoginVO() {
			return loginVO;
		}
		public void setLoginVO(User loginVO) {
			this.loginVO = loginVO;
		}
		@Transient
		@Column(name="emailAddress")
		public String getEmailAddress() {
			return emailAddress;
		}
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
		
}
