package com.scube.crm.bo;

import java.util.List;


public class WorkItemSkillsBO extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long manageId;
	private WorkItemBO workitemBO;
	private SkillsBO skillsBO;
	private List<SkillsBO> skillsListBO; 
	
	private boolean isActive;
	private boolean isDelete;
	private int sNO;
	
	
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
	public int getsNO() {
		return sNO;
	}
	public void setsNO(int sNO) {
		this.sNO = sNO;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public long getManageId() {
		return manageId;
	}
	public void setManageId(long manageId) {
		this.manageId = manageId;
	}
	// Getter method
    public WorkItemBO getWorkitemBO() {
        return workitemBO;
    }
    public void setWorkitemBO(WorkItemBO workitemBO) {
		this.workitemBO = workitemBO;
	}
	public SkillsBO getSkillsBO() {
		return skillsBO;
	}
	public void setSkillsBO(SkillsBO skillsBO) {
		this.skillsBO = skillsBO;
	}
	public List<SkillsBO> getSkillsListBO() {
		return skillsListBO;
	}
	public void setSkillsListBO(List<SkillsBO> skillsListBO) {
		this.skillsListBO = skillsListBO;
	}
	private String skillASString = null;

	public String getSkillASString() {
		if (null != skillsListBO && !skillsListBO.isEmpty()) {
			StringBuilder stringBuilder = new StringBuilder();
			for (SkillsBO skills : skillsListBO) {
				if (skills.getDescriptions() != null) {
					if (stringBuilder.length() > 0) {
						stringBuilder.append(", ");
					}
					stringBuilder.append(skills.getDescriptions());
				}
			}
			skillASString = stringBuilder.toString();
		}
		return skillASString;
	}

//    private String skillASString;
//   
//    public String getSkillASString() {
//	if(null!=skillsListBO && skillsListBO.size()>0) {
//	for (SkillsBO skills : skillsListBO)   
//	{  
//	skillASString=skillASString+","+skills.getDescriptions();
//	}
//	}
//	return skillASString;

    public List<Long> getSkillsIds() {
		return skillsIds;
	}
	public void setSkillsIds(List<Long> skillsIds) {
		this.skillsIds = skillsIds;
	}

	private List<Long> skillsIds;
    
    
}
