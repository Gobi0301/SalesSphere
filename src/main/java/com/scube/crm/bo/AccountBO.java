package com.scube.crm.bo;


import java.io.Serializable;
import java.util.Objects;

import com.scube.crm.vo.Opportunity;
import com.scube.crm.vo.User;

public class AccountBO  implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer accountId;
	private Integer addressId;
	private String street;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String countryCode;
	private Long contactNo; 
	private long secondaryContactNo;
	private String accountName;
	private Boolean isDeleted;
	 private String email;
	 private String parentAccount;
	 private Long accountSource;
	 private long createdBy;
	 private String salutation;
	 private String firstName;
     private String lastName;
     private Long annualRevenue;
     private Long contactId;
     private String accountOwner;
     private String type;
     private String industry;
     private Integer noOfEmployess;
     private String description;
     private int recordIndex;
 	private int maxRecord;
 	private String pagination = null;
 	private long phone;
 	private String user;
 	private Opportunity opportunitys;
 	private Long id;
 	private User users;
 	private AdminUserBO assignedTo;
 	private int sNO;
 	private String salesOrder;
 	private OpportunityBO opportunityBO;
   //  private ContactBO contact;
    // private AddressBO address;
     private String origin;  //for lead to opportunity convert
	private int leadId;	 //for lead to opportunity convert
	private Long companyId;
	
	public int getRecordIndex() {
		return recordIndex;
	}
	public String getSalesOrder() {
		return salesOrder;
	}
	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}
	public void setRecordIndex(int recordIndex) {
		this.recordIndex = recordIndex;
	}
	public int getMaxRecord() {
		return maxRecord;
	}
	public void setMaxRecord(int maxRecord) {
		this.maxRecord = maxRecord;
	}
	public String getPagination() {
		return pagination;
	}
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	/*
	 * public AddressBO getAddress() { return address; } public void
	 * setAddress(AddressBO address) { this.address = address; }
	 */
	public String getSalutation() {
			return salutation;
		}
		public void setSalutation(String salutation) {
			this.salutation = salutation;
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
//	private OpportunityDO opportunity;
	    
	
	public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		public Boolean getIsDeleted() {
			return isDeleted;
		}
		public void setIsDeleted(Boolean isDeleted) {
			this.isDeleted = isDeleted;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getParentAccount() {
			return parentAccount;
		}
		public void setParentAccount(String parentAccount) {
			this.parentAccount = parentAccount;
		}
		public Long getAccountSource() {
			return accountSource;
		}
		public void setAccountSource(Long accountSource) {
			this.accountSource = accountSource;
		}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
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
	public Long getContactNo() {
		return contactNo;
	}
	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}
	public long getSecondaryContactNo() {
		return secondaryContactNo;
	}
	public void setSecondaryContactNo(long secondaryContactNo) {
		this.secondaryContactNo = secondaryContactNo;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/*
	 * public OpportunityDO getOpportunity() { return opportunity; } public void
	 * setOpportunity(OpportunityDO opportunity) { this.opportunity = opportunity; }
	 */
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Long getAnnualRevenue() {
		return annualRevenue;
	}
	public void setAnnualRevenue(Long annualRevenue) {
		this.annualRevenue = annualRevenue;
	}

	/*
	 * public ContactBO getContact() { return contact; } public void
	 * setContact(ContactBO contact) { this.contact = contact; }
	 */
	public Long getContactId() {
		return contactId;
	}
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	public String getAccountOwner() {
		return accountOwner;
	}
	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public Integer getNoOfEmployess() {
		return noOfEmployess;
	}
	public void setNoOfEmployess(Integer noOfEmployess) {
		this.noOfEmployess = noOfEmployess;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public AdminUserBO getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(AdminUserBO assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Opportunity getOpportunitys() {
		return opportunitys;
	}
	public void setOpportunitys(Opportunity opportunitys) {
		this.opportunitys = opportunitys;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUsers() {
		return users;
	}
	public void setUsers(User users) {
		this.users = users;
	}
	public int getsNO() {
		return sNO;
	}
	public void setsNO(int sNO) {
		this.sNO = sNO;
	}
	public OpportunityBO getOpportunityBO() {
		return opportunityBO;
	}
	public void setOpportunityBO(OpportunityBO opportunityBO) {
		this.opportunityBO = opportunityBO;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public int getLeadId() {
		return leadId;
	}
	public void setLeadId(int leadId) {
		this.leadId = leadId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	  @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        AccountBO accountBO = (AccountBO) o;
	        return Objects.equals(assignedTo, accountBO.assignedTo);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(assignedTo);
	    }
}
