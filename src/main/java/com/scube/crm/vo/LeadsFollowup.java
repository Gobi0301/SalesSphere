package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="leads_update_status")
public class LeadsFollowup extends BasicEntity {

	private static final long serialVersionUID = 1L;
	
	private long leadsupdateid;
	private String descriptions;
	private String date;
	private Leads leads;
	private User userVO;
	private boolean isDelete;
	private boolean isActive;
	 private String nextAppointmentDate;
	@Column(name="isDelete")
	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name="isActive")
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="updateleads_id")
	public long getLeadsupdateid() {
		return leadsupdateid;
	}

	public void setLeadsupdateid(long leadsupdateid) {
		this.leadsupdateid = leadsupdateid;
	}

	@Column(name="description", columnDefinition="TEXT")
	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	@Column(name="date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@ManyToOne
	@JoinColumn(name="lead_id")
	public Leads getLeads() {
		return leads;
	}

	public void setLeads(Leads leads) {
		this.leads = leads;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUserVO() {
		return userVO;
	}

	public void setUserVO(User userVO) {
		this.userVO = userVO;
	}
	@Column(name="next_action_date")
	public String getNextAppointmentDate() {
		return nextAppointmentDate;
	}

	public void setNextAppointmentDate(String nextAppointmentDate) {
		this.nextAppointmentDate = nextAppointmentDate;
	}

}