package com.scube.crm.bo;

import javax.validation.constraints.NotNull;

public class AccessBo extends BaseBO {
	
	private long accessId;
	@NotNull(message="Access name cannot be empty")
	private String accessName;
	private int sNo;
	
	private AdminLoginBO adminLoginBO; 
	
	public AdminLoginBO getAdminLoginBO() {
		return adminLoginBO;
	}

	public void setAdminLoginBO(AdminLoginBO adminLoginBO) {
		this.adminLoginBO = adminLoginBO;
	}
	
	private boolean isDeleted;
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

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

	public int getsNo() {
		return sNo;
	}

	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	

}
