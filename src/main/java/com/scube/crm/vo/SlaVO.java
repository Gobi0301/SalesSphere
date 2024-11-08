package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sla")
public class SlaVO extends BasicEntity{
	
	private static final long serialVersionUID = -9047761984248511096L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long slaId;
	@Column(name="slaName")
	private String slaName;
	@Column(name="slaCode")
	private String slaCode;
	@Column(name="isActive")
	private boolean isActive;
	@Column(name="isDelete")
	private boolean isDelete;
	@Column(name="date")
	private String date;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	@Column(name="descriptions")
	private String descriptions;
	
	
	
	public long getSlaId() {
		return slaId;
	}
	public void setSlaId(long slaId) {
		this.slaId = slaId;
	}
	public String getSlaName() {
		return slaName;
	}
	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	public String getSlaCode() {
		return slaCode;
	}
	public void setSlaCode(String slaCode) {
		this.slaCode = slaCode;
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
	 
}
