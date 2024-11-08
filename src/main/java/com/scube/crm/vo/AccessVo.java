package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accesss")
public class AccessVo extends BaseEntity {
	
	@Id
	@GeneratedValue
	
	private long accessId;
	
	@Column(name="accessName")
	private String accessName;
	/* private Set<PrivilegesVO>privilegesobj=new HashSet<>(); */
	private boolean isDeleted;

	public long getAccessId() {
		return accessId;
	}

	public void setAccessId(long accessId) {
		this.accessId = accessId;
	}

	public String getAccessName() {
		return accessName;
	}

	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}

	/*
	 * public Set<PrivilegesVO> getPrivilegesobj() { return privilegesobj; }
	 * 
	 * public void setPrivilegesobj(Set<PrivilegesVO> privilegesobj) {
	 * this.privilegesobj = privilegesobj; }
	 */
	
	


	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}



}
