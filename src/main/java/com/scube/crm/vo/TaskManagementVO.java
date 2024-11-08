package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "task_management")
public class TaskManagementVO extends BasicEntity{
	
	public WorkItemVO getWorkItemVO() {
		return WorkItemVO;
	}
	public void setWorkItemVO(WorkItemVO workItemVO) {
		WorkItemVO = workItemVO;
	}

	private static final long serialVersionUID = -9047761984248511096L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long taskId;
	
	@Column(name="subject")
	private String subject;

	@Column(name="status")
	private String status;
	
	@Column(name="isActive")
	private boolean isActive;
	
	@Column(name="isDelete")
	private boolean isDelete;
	
	@Column(name="priority")
	private String priority;
		
	@Column(name="entityId")
	private long entityId;
	
	@Column(name="entityName")
	private String entityName;
	
	@OneToOne
	@JoinColumn(name="projectId")	
	private ProjectVO projectVo;
	
	@Column(name = "date")
	private String date;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	@Column(name = "dueDate")
	private String dueDate; 
	
	@Column(name = "relatedTo")
	private String relatedTo;
	
	
	
	@Transient
	private int sNo;
	
	
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public String getRelatedTo() {
		return relatedTo;
	}
	public void setRelatedTo(String relatedTo) {
		this.relatedTo = relatedTo;
	}

	
	@ManyToOne
	@JoinColumn(name="loginId")
	private User taskOwner;
	
	@OneToOne
	@JoinColumn(name="workItemsId")	
	private WorkItemVO WorkItemVO;
	
	

	public Leads getLeadsVO() {
		return leadsVO;
	}
	public void setLeadsVO(Leads leadsVO) {
		this.leadsVO = leadsVO;
	}

	@OneToOne
	@JoinColumn(name="leadsId")	
	private Leads leadsVO;
	
	public long getEntityId() {
		return entityId;
	}
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
		
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
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

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setisDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	@Column(name="closedTime")
	private String closedTime;
	@Column(name="Description")
	private String Description;
		
	
	
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getClosedTime() {
		return closedTime;
	}
	public void setClosedTime(String closedTime) {
		this.closedTime = closedTime;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	 
	public User getTaskOwner() {
		return taskOwner;
	}
	public void setTaskOwner(User taskOwner) {
		this.taskOwner = taskOwner;
	}
	public ProjectVO getProjectVo() {
		return projectVo;
	}
	public void setProjectVo(ProjectVO projectVo) {
		this.projectVo = projectVo;
	}
	
	
}
