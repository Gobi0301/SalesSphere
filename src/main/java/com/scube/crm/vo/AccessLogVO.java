package com.scube.crm.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "access_log")
public class AccessLogVO extends BasicEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long logId;

	@Column(name = "clientIP")
	private String clientIP;
	
	@Column(name = "accessDate")
	private Date accessDate;

	@Column(name = "access_Id")
	private long accessId;

	@Column(name = "sessionId")
	private String sessionId;
	
	public long getAccessId() {
		return accessId;
	}

	public void setAccessId(long accessId) {
		this.accessId = accessId;
	}
	
	/**
	 * @return the logId
	 */
	public long getLogId() {
		return logId;
	}

	/**
	 * @param logId the logId to set
	 */
	public void setLogId(long logId) {
		this.logId = logId;
	}

	/**
	 * @return the clientIP
	 */
	public String getClientIP() {
		return clientIP;
	}

	/**
	 * @param clientIP the clientIP to set
	 */
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	/**
	 * @return the accessDate
	 */
	public Date getAccessDate() {
		return accessDate;
	}

	/**
	 * @param accessDate the accessDate to set
	 */
	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	
}
