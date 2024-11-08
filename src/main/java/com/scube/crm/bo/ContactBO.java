package com.scube.crm.bo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ContactBO  extends BaseBO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long contactId;
	
	@NotNull(message="Salutation should not be empty")
	private String salutation;
	
	@NotNull(message="Firstname should not be empty")
	private String firstname;
	
	@NotNull(message="Lastname should not be empty")
	private String lastname;
	
	
	private String leadsourse;
	
	
	private String accountname;
	
	@NotNull(message="Email should not be empty")
	@Pattern(regexp = ".+@.+\\.[a-z]+", message = "Invalid Email Format")
	private String email;
	

	private long phone;
	
	private int sNo;
	@NotNull(message="Email should not be empty")
	private String street;
	@NotNull(message="State should not be empty")
	private String state;
	
	@NotNull(message="City should not be empty")
	private String city;
	@NotNull(message="Country should not be empty")
	private String country;

	private AdminUserBO assignedTo;
	
	//private ClientBO clientBO;
	
	private boolean isDeleted;
	private AccountBO accountBO;


	public AccountBO getAccountBO() {
		return accountBO;
	}



	public void setAccountBO(AccountBO accountBO) {
		this.accountBO = accountBO;
	}



	/*
	 * public ClientBO getClientBO() { return clientBO; }
	 * 
	 * 
	 * 
	 * public void setClientBO(ClientBO clientBO) { this.clientBO = clientBO; }
	 */


	public Long getContactId() {
		return contactId;
	}



	public String getSalutation() {
		return salutation;
	}



	public String getFirstname() {
		return firstname;
	}



	public String getLastname() {
		return lastname;
	}



	public String getLeadsourse() {
		return leadsourse;
	}



	public String getAccountname() {
		return accountname;
	}



	public String getEmail() {
		return email;
	}



	public long getPhone() {
		return phone;
	}



	public int getsNo() {
		return sNo;
	}



	public String getStreet() {
		return street;
	}



	public String getState() {
		return state;
	}



	public String getCity() {
		return city;
	}



	public String getCountry() {
		return country;
	}



	public AdminUserBO getAssignedTo() {
		return assignedTo;
	}



	public boolean isDeleted() {
		return isDeleted;
	}



	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}



	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public void setLeadsourse(String leadsourse) {
		this.leadsourse = leadsourse;
	}



	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public void setPhone(long phone) {
		this.phone = phone;
	}



	public void setsNo(int sNo) {
		this.sNo = sNo;
	}



	public void setStreet(String street) {
		this.street = street;
	}



	public void setState(String state) {
		this.state = state;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public void setAssignedTo(AdminUserBO assignedTo) {
		this.assignedTo = assignedTo;
	}



	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	private OpportunityBO  opportunityBO;
	
	public OpportunityBO getOpportunityBO() {
		return opportunityBO;
	}



	public void setOpportunityBO(OpportunityBO opportunityBO) {
		this.opportunityBO = opportunityBO;
	}


	
	
	
}
