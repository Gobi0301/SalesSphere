/**
 * 
 */
package com.scube.crm.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author User
 *
 */
@Entity
@Table(name="email_management")
public class EmailAccess extends BasicEntity {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long sendId;
	
	/**
	 * @return the sendId
	 */
	public long getSendId() {
		return sendId;
	}

	/**
	 * @param sendId the sendId to set
	 */
	public void setSendId(long sendId) {
		this.sendId = sendId;
	}

	@Column(name="emailAddress")
	private String emailAddress;

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
	 * @return the mailTO
	 */
	public long getMailTO() {
		return mailTO;
	}

	/**
	 * @param mailTO the mailTO to set
	 */
	public void setMailTO(long mailTO) {
		this.mailTO = mailTO;
	}

	/**
	 * @return the mailedBy
	 */
	public long getMailedBy() {
		return mailedBy;
	}

	/**
	 * @param mailedBy the mailedBy to set
	 */
	public void setMailedBy(long mailedBy) {
		this.mailedBy = mailedBy;
	}


	
	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name="mail_to")
	private long mailTO;
	
	@Column(name="mail_by")
	private long mailedBy;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="status")
	private boolean status; 
	
	
	
	
	

}
