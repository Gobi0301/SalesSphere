package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "skills")
public class SkillsVO extends BasicEntity {

	private static final long serialVersionUID = -9047761984248511096L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long skillsId;
	@Column(name="skillsCode")
	private String skillsCode;
	@Column(name="descriptions")
	private String descriptions;
	@Column(name="isActive")
	private boolean isActive;
	@Column(name="isDelete")
	private boolean isDelete;
	
	@Transient
	private int sNo;
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
	public long getSkillsId() {
		return skillsId;
	}
	public void setSkillsId(long skillsId) {
		this.skillsId = skillsId;
	}
	public String getSkillsCode() {
		return skillsCode;
	}
	public void setSkillsCode(String skillsCode) {
		this.skillsCode = skillsCode;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
}
