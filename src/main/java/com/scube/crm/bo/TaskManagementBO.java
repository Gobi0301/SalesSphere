package com.scube.crm.bo;

import java.util.List;

import com.scube.crm.vo.TaskManagementVO;
import com.scube.crm.vo.TaskTrackingStatusVO;

public class TaskManagementBO extends BaseBO{

	private static final long serialVersionUID = 1L;
	
	private long taskId;
	private String workitem;
	private String subject;
	private String status;
	private String priority;
	private String relatedTo;
	private String leadName;
	private String closedTime;
	private String Description;
	private String descriptions;
	
	private WorkItemBO workItemBO;
	private ProjectBO projectBo;
	private LeadsBO leadsBO;

	private int sNo;
	private boolean isActive;
	private boolean isDelete;
	private long entityId;
	private String entityName;
	private String date;
	private String nextAppointmentDate;
	private String timeSlot;
	private String endTimeSlot;
	private String report;
	private  String uploadfile;
	private  String duedate;
	
	
	
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	private List<TaskTrackingStatusVO> taskupdateList;
	
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}
	public List<TaskTrackingStatusVO> getTaskupdateList() {
		return taskupdateList;
	}
	public void setTaskupdateList(List<TaskTrackingStatusVO> taskupdateList) {
		this.taskupdateList = taskupdateList;
	}
	public List<TaskManagementVO> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<TaskManagementVO> taskList) {
		this.taskList = taskList;
	}

	private List<TaskManagementVO> taskList;
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

	private AdminUserBO adminUserBO;
	
	public AdminUserBO getAdminUserBO() {
		return adminUserBO;
	}
	public void setAdminUserBO(AdminUserBO adminUserBO) {
		this.adminUserBO = adminUserBO;
	}

	private AdminLoginBO adminLoginBO;
	
	public AdminLoginBO getAdminLoginBO() {
		return adminLoginBO;
	}
	public void setAdminLoginBO(AdminLoginBO adminLoginBO) {
		this.adminLoginBO = adminLoginBO;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
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
	public String getRelatedTo() {
		return relatedTo;
	}
	public void setRelatedTo(String relatedTo) {
		this.relatedTo = relatedTo;
	}
	public String getLeadName() {
		return leadName;
	}
	public void setLeadName(String leadName) {
		this.leadName = leadName;
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
	public String getDate() {
		return date;
	}
	public String getNextAppointmentDate() {
		return nextAppointmentDate;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public String getEndTimeSlot() {
		return endTimeSlot;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setNextAppointmentDate(String nextAppointmentDate) {
		this.nextAppointmentDate = nextAppointmentDate;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public void setEndTimeSlot(String endTimeSlot) {
		this.endTimeSlot = endTimeSlot;
	}
	 
	
	public WorkItemBO getWorkItemBO() {
		return workItemBO;
	}
	public void setWorkItemBO(WorkItemBO workItemBO) {
		this.workItemBO = workItemBO;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public ProjectBO getProjectBo() {
		return projectBo;
	}
	public void setProjectBo(ProjectBO projectBo) {
		this.projectBo = projectBo;
	}
	public String getWorkitem() {
		return workitem;
	}
	public void setWorkitem(String workitem) {
		this.workitem = workitem;
	}
	public LeadsBO getLeadsBO() {
		return leadsBO;
	}
	public void setLeadsBO(LeadsBO leadsBO) {
		this.leadsBO = leadsBO;
	}

	
	
}