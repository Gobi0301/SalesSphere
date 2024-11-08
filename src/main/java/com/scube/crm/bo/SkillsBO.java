package com.scube.crm.bo;

public class SkillsBO extends BaseBO{

	private static final long serialVersionUID = 1L;
	
	
	private long skillsId;
	private String skillsCode;
	private String descriptions;
	private int sNo;
	
	
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public long getSkillsId() {
		return skillsId;
	}
	public void setSkillsId(long skillsId) {
		this.skillsId = skillsId;
	}
	 
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public String getSkillsCode() {
		return skillsCode;
	}
	public void setSkillsCode(String skillsCode) {
		this.skillsCode = skillsCode;
	}
	
	
}
