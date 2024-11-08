package com.scube.crm.vo;

 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="project_status")
public class ProjectTrackingStatusVO extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
	
	private long projectActivityid;
	
	private long entityid;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="projectActivityid")
	public long getProjectActivityid() {
		return projectActivityid;
	}
	public void setProjectActivityid(long projectActivityid) {
		this.projectActivityid = projectActivityid;
	}
	public String getAmenities() {
		return amenities;
	}
	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}
	public String getNearByLocalities() {
		return nearByLocalities;
	}
	public void setNearByLocalities(String nearByLocalities) {
		this.nearByLocalities = nearByLocalities;
	}
	public long getEntityid() {
		return entityid;
	}
	public void setEntityid(long entityid) {
		this.entityid = entityid;
	}
	private String amenities;
	private String nearByLocalities;
}
