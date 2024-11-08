package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activity_history_status")
public class HistoryVO {
	
	/**
	 * 
	 */

	private long logId;
	private long entityId;
	private String entityType;
	private String actionType;
	private long user;
	private Long companyId;
	
	@Column(name="companyId")
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="log_id")
	public long getLogId() {
		return logId;
	}
	public void setLogId(long logId) {
		this.logId = logId;
	}
	
	@Column(name="entity_id")
	public long getEntityId() {
		return entityId;
	}
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	@Column(name="entity_type")
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	@Column(name="action_type")
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	@Column(name="user_id")
	public long getUser() {
		return user;
	}
	public void setUser(long user) {
		this.user = user;
	}
	
	}
