package com.scube.crm.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Resolution;

@Entity
@Table(name = "login_status")
public class LoginStatusVO extends BasicEntity
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "login_status_id")
	private long loginStatusId;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_type")
	private String type;
	
	@Column(name = "status")
	private Boolean status;
	
	
	@Column(name = "start_time")
	private Date startTime = new Date();
	
	@Column(name = "end_time")
	private Date endTime = new Date();
	
	@Column(name = "activity")
	private String activity;
	
	
	
	

	public long getLoginStatusId() {
		return loginStatusId;
	}

	public void setLoginStatusId(long loginStatusId) {
		this.loginStatusId = loginStatusId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
    
	
	@DateBridge(resolution = Resolution.DAY)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
    
	
	@DateBridge(resolution = Resolution.DAY)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	
	

}
