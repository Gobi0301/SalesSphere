/**
 * 
 */
package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * @author User
 *
 */
@Entity
@Table(name="customer_update_status")
public class FollowUp extends BasicEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long updateid;
	
	@Column(name="description", columnDefinition="TEXT")
	private String description;
	@Column(name="timeSlot")
	private String timeSlot;
	@Column(name="endTimeSlot")
	private String endTimeSlot;
	@Column(name="date")
	private String date;
	public String getEndTimeSlot() {
		return endTimeSlot;
	}
	public void setEndTimeSlot(String endTimeSlot) {
		this.endTimeSlot = endTimeSlot;
	}
	@Column(name="next_action_date")
	 private String nextAppointmentDate;
	
	 public String getNextAppointmentDate() {
		return nextAppointmentDate;
	}
	public void setNextAppointmentDate(String nextAppointmentDate) {
		this.nextAppointmentDate = nextAppointmentDate;
	}
	@ManyToOne 
	 @JoinColumn(name="customer_id") 
	private Customer customer;
	
	 @ManyToOne
		@JoinColumn(name="user_id")
		private User user;
	
	/**
	 * @return the adminVO
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param adminVO the adminVO to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	/**
	 * @return the updateid
	 */
	public long getUpdateid() {
		return updateid;
	}
	/**
	 * @param updateid the updateid to set
	 */
	public void setUpdateid(long updateid) {
		this.updateid = updateid;
	}
	
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
}
