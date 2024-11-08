package com.scube.crm.bo;

import java.util.Date;



public class AccessLogBO extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private String sessionId;
private String clientIP;
private Date accessDate;
private long accessId;
public long getAccessId() {
	return accessId;
}
public void setAccessId(long accessId) {
	this.accessId = accessId;
}
private long sNo;
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
 * @return the sNo
 */
public long getsNo() {
	return sNo;
}
/**
 * @param sNo the sNo to set
 */
public void setsNo(long sNo) {
	this.sNo = sNo;
}

}