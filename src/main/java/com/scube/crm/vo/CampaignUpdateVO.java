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
@Table(name="campaign_update_status")
public class CampaignUpdateVO extends BasicEntity {

	private static final long serialVersionUID = 1L;

	private long campaignupdateid;
	private String descriptions;
	private String date;
	private Campaign campaign;
	private User userVO;
	private boolean isDelete;
	private String uploadfile;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="updatecampaign_id")
	public long getCampaignupdateid() {
		return campaignupdateid;
	}
	public void setCampaignupdateid(long campaignupdateid) {
		this.campaignupdateid = campaignupdateid;
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
	@JoinColumn(name="campaign_id")
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUserVO() {
		return userVO;
	}
	public void setUserVO(User userVO) {
		this.userVO = userVO;
	}

	@Column(name="isDelete")
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	@Column(name="upload_files")
	public String getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}
}
