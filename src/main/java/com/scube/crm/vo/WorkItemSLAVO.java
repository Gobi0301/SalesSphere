package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="manage_WI_SLA")
public class WorkItemSLAVO extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="WI_SLA_Id")
	 private long manageId;
	@Column(name="wISLACode")
	 private String wISLACode;
	@Column(name="isActive")
	private boolean isActive;
	@Transient
	 private int sNo;
	 
	public int getsNo() {
		return sNo;
	}

	public void setsNo(int sNo) {
		this.sNo = sNo;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
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

	public void setisDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name="isDelete")
	private boolean isDelete;
	
	@OneToOne
	@JoinColumn(name="workItem_id") 
	private WorkItemVO workItemVO;
	
	@OneToOne
	@JoinColumn(name="sla_id") 
	private SlaVO slaVO;

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

	public WorkItemVO getWorkItemVO() {
		return workItemVO;
	}

	public void setWorkItemVO(WorkItemVO workItemVO) {
		this.workItemVO = workItemVO;
	}

	public SlaVO getSlaVO() {
		return slaVO;
	}

	public void setSlaVO(SlaVO slaVO) {
		this.slaVO = slaVO;
	}

}
