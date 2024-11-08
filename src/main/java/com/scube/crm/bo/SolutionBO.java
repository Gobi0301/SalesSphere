package com.scube.crm.bo;

import org.hibernate.validator.constraints.NotBlank;

public class SolutionBO extends BaseBO{
	
	private static final long serialVersionUID =1L;
	
    private int solutionId;
   
	@NotBlank (message="solutionTitle may not be empty")
	private String  solutionTitle;
	@NotBlank (message="category may not be empty")
	private String  category;
	@NotBlank (message="Question may not be empty")
	private String  question;
	@NotBlank (message="Answer may not be empty")
	private String  answer;
	@NotBlank (message="Status may not be empty")
	private String  status;
	@NotBlank (message="description may not be empty")
	private String  description;
	@NotBlank (message="Comments may not be empty")
	private String  comments;
	
	private InventoryBO inventoryBo;
	
	private AdminUserBO adminUserBo;
	
	public int getSolutionId() {
		return solutionId;
	}
	public void setSolutionId(int solutionId) {
		this.solutionId = solutionId;
	}
	 

	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
		
	}
	public InventoryBO getInventoryBo() {
		return inventoryBo;
	}
	public void setInventoryBo(InventoryBO inventoryBo) {
		this.inventoryBo = inventoryBo;
	}
	public AdminUserBO getAdminUserBo() {
		return adminUserBo;
	}
	public void setAdminUserBo(AdminUserBO adminUserBo) {
		this.adminUserBo = adminUserBo;
	}
	public String getSolutionTitle() {
		return solutionTitle;
	}
	public void setSolutionTitle(String solutionTitle) {
		this.solutionTitle = solutionTitle;
	}
	
	
	

}
