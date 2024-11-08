package com.scube.crm.bo;

public class WorkItemBO extends BaseBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3640609289106344222L;
	
	private long workItemId;
	private String workItemCode;
	private String workItemType;
	private String workItem;
	private boolean isActive;
	private boolean isDelete;
	private int sNo;
	public long getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(long workItemId) {
		this.workItemId = workItemId;
	}
	public String getWorkItemCode() {
		return workItemCode;
	}
	public void setWorkItemCode(String workItemCode) {
		this.workItemCode = workItemCode;
	}
	public String getWorkItemType() {
		return workItemType;
	}
	public void setWorkItemType(String workItemType) {
		this.workItemType = workItemType;
	}
	public String getWorkItem() {
		return workItem;
	}
	public void setWorkItem(String workItem) {
		this.workItem = workItem;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
