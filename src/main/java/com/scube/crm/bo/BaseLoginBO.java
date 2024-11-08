package com.scube.crm.bo;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * THis BO class contains the required fields for BaseLogin class
 * 
 * @author Administrator
 * 
 */
public class BaseLoginBO extends BaseBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Email Address variable declaration
	 */
	@NotNull
	@Valid
	private String emailAddress;

	/**
	 * Password Variable Declaration
	 */

	@NotNull
	@Size(min = 4, max = 8)
	private String password;

	/**
	 * ConfirmPassword Variable Declaration
	 */

	@NotNull
	@Size(min = 4, max = 8)
	private String confirmPassword;

	/**
	 * IsActive Variable Declaration
	 */
	private Boolean isActive;

	/**
	 * Register ID Variable Declaration
	 */
	private long registerId;

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
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return this.isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the registerId
	 */
	public long getRegisterId() {
		return this.registerId;
	}

	/**
	 * @param registerId
	 *            the registerId to set
	 */
	public void setRegisterId(long registerId) {
		this.registerId = registerId;
	}

}
