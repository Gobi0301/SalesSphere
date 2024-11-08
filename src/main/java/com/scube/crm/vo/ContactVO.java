package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="contact")
public class ContactVO extends BasicEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	@Column(name="contact_id")
	private Long contactId;

	@Column(name="salutation")
	private String salutation;
	@Column(name="firstname")
	private String firstname;
	@Column(name="lastname")
	private String lastname;

	@Column(name="email")
	private String email;
	@Column(name="phone")
	private long phone;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User assignedTo;

	@Column(name="street")
	private String street;
	@Column(name="state")
	private String state;

	@Column(name="city")
	private String city;
	@Column(name="country")
	private String country;
	@Column(name="isDeleted")
	private boolean isDeleted;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private AccountVO account;


	@ManyToOne
	@JoinColumn(name="opportunityId") 
	private Opportunity opportunity;


	public Opportunity getOpportunity() {
		return opportunity;
	}
	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

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



	public String getEmail() {
		return email;
	}

	public long getPhone() {
		return phone;
	}



	public User getAssignedTo() {
		return assignedTo;
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

	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
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


	public AccountVO getAccount() {
		return account;
	}
	public void setAccount(AccountVO account) {
		this.account = account;
	}
}
