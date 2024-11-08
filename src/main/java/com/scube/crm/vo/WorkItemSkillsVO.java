package com.scube.crm.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "manage_wi_skills")
public class WorkItemSkillsVO extends BasicEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long workItemSkillId;

	public long getWorkItemSkillId() {
		return workItemSkillId;
	}

	public void setWorkItemSkillId(long workItemSkillId) {
		this.workItemSkillId = workItemSkillId;
	}

	@OneToOne
	@JoinColumn(name = "Work_Item_Id")
	private WorkItemVO workitemVO;

    @ManyToMany
	@JoinColumn(name = "skillsId")
    private List<SkillsVO> skillsListVO=new ArrayList<SkillsVO>();
	

	public List<SkillsVO> getSkillsListVO() {
		return skillsListVO;
	}

	public void setSkillsListVO(List<SkillsVO> skillsListVO) {
		this.skillsListVO = skillsListVO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "is_Active")
	private boolean isActive;

	@Column(name = "is_Delete")
	private boolean isDelete;

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

	public WorkItemVO getWorkitemVO() {
		return workitemVO;
	}

	public void setWorkitemVO(WorkItemVO workitemVO) {
		this.workitemVO = workitemVO;
	}

}
