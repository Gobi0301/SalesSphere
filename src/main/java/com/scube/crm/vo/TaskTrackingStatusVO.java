package com.scube.crm.vo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="task_updates")
public class TaskTrackingStatusVO extends BasicEntity{
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	private long taskTrackingId;
	
	@Column(name="upload_file")
	public String getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}
	private String uploadfile;
	private String subject;
	 
	private String status;
	private String date;
	 
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private String priority;
	 
	private long entityid;
 
	private String entitytype;
	
	private String descriptions;
	
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	private String report;
	
	public long getEntityid() {
		return entityid;
	}
	public void setEntityid(long entityid) {
		this.entityid = entityid;
	}
	public String getEntitytype() {
		return entitytype;
	}
	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}
	public long getTaskTrackingId() {
		return taskTrackingId;
	}
	public void setTaskTrackingId(long taskTrackingId) {
		this.taskTrackingId = taskTrackingId;
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

}
