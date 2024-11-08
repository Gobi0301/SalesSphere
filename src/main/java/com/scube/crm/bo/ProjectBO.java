package com.scube.crm.bo;

import java.util.List;

import com.scube.crm.vo.ProjectTrackingStatusVO;
import com.scube.crm.vo.ProjectVO;

public class ProjectBO extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long projectId;
	private String projectName;
	private String projectType;
	private String projectStatus;
	private String approval;
	private String projectLocation;
	private String projectAreaInSqfts;
	private String startDate;
	private String endDate;
	private String amenities;
	private String nearByLocalities;
	private List<ProjectVO> projectactivityList;
	
	private List<ProjectTrackingStatusVO> projectupdateList;
	private String unit;
	private int sNo;
	private boolean isDeleted;
	
	private int entityid;
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public String getProjectLocation() {
		return projectLocation;
	}
	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getNearByLocalities() {
		return nearByLocalities;
	}
	public void setNearByLocalities(String nearByLocalities) {
		this.nearByLocalities = nearByLocalities;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProjectAreaInSqfts() {
		return projectAreaInSqfts;
	}
	public void setProjectAreaInSqfts(String projectAreaInSqfts) {
		this.projectAreaInSqfts = projectAreaInSqfts;
	}
	public String getAmenities() {
		return amenities;
	}
	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public List<ProjectVO> getProjectactivityList() {
		return projectactivityList;
	}
	public void setProjectactivityList(List<ProjectVO> projectactivityList) {
		this.projectactivityList = projectactivityList;
	}
	public int getEntityid() {
		return entityid;
	}
	public void setEntityid(int entityid) {
		this.entityid = entityid;
	}
	public List<ProjectTrackingStatusVO> getProjectupdateList() {
		return projectupdateList;
	}
	public void setProjectupdateList(List<ProjectTrackingStatusVO> projectupdateList) {
		this.projectupdateList = projectupdateList;
	}
	
	
	
}
