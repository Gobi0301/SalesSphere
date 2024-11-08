package com.scube.crm.bo;

public class WorkItemSLABO extends BaseBO{
	
	private static final long serialVersionUID = 1L;

	
	 private long manageId;
	 private String wISLACode;
	 private WorkItemBO workItemBO;
	 private SlaBO slaBO;
	 private boolean isActive;
	 private boolean isDelete;
	 private int sNo;
	 
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public String getwISLACode() {
		return wISLACode;
	}
	public void setwISLACode(String wISLACode) {
		this.wISLACode = wISLACode;
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
	public long getManageId() {
		return manageId;
	}
	public void setManageId(long manageId) {
		this.manageId = manageId;
	}
	public String getSlaCode() {
		return wISLACode;
	}
	public void setSlaCode(String slaCode) {
		this.wISLACode = slaCode;
	}
	public WorkItemBO getWorkItemBO() {
		return workItemBO;
	}
	public void setWorkItemBO(WorkItemBO workItemBO) {
		this.workItemBO = workItemBO;
	}
	public SlaBO getSlaBO() {
		return slaBO;
	}
	public void setSlaBO(SlaBO slaBO) {
		this.slaBO = slaBO;
	}
}
