package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="activity_status")
public class ActivityVO extends BasicEntity {



	private static final long serialVersionUID = 1L;

	private long activityid;
	private long entityid;
	private String entitytype;
	private  String date;
	private String nextAppointmentDate;
	private String description;
	private String convertedDate;
	private String followupDate;
	private String uploadfile;
	private String timeSlot;
	private String endTimeSlot;
	private String createdDate;
    private String modifyDate;
	private String descriptions;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="activity_id")
	public long getActivityid() {
		return activityid;
	}
	public void setActivityid(long activityid) {
		this.activityid = activityid;
	}



	@Column(name="date")
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	@Column(name="next_action_date")
	public String getNextAppointmentDate() {
		return nextAppointmentDate;
	}
	public void setNextAppointmentDate(String nextAppointmentDate) {
		this.nextAppointmentDate = nextAppointmentDate;
	}

	@Column(name="description", columnDefinition="TEXT")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="converted_date")
	public String getConvertedDate() {
		return convertedDate;
	}
	public void setConvertedDate(String convertedDate) {
		this.convertedDate = convertedDate;
	}

	@Column(name="followup_date")
	public String getFollowupDate() {
		return followupDate;
	}
	public void setFollowupDate(String followupDate) {
		this.followupDate = followupDate;
	}

	@Column(name="upload_file")
	public String getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}
	@Column(name="entity_type")
	public String getEntitytype() {
		return entitytype;
	}
	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	 

	@Column(name="entity_id")
	public long getEntityid() {
		return entityid;
	}
	public void setEntityid(long entityid) {
		this.entityid = entityid;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	@Column(name="endTimeSlot")
	public String getEndTimeSlot() {
		return endTimeSlot;
	}
	public void setEndTimeSlot(String endTimeSlot) {
		this.endTimeSlot = endTimeSlot;
	}

}
