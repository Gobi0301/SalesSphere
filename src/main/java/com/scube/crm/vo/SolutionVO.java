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
@Table(name = "solution")

public class SolutionVO extends BasicEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "solution_id")
	private int solutionId;

	@Column(name = "solution_title")
	private String solutionTitle;

	@Column(name = "category")
	private String category;

	@Column(name = "is_Delete")
	private boolean isDelete;

	@Column(name = "question")
	private String question;

	@Column(name = "answer")
	private String answer;

	@Column(name = "status")
	private String status;

	@Column(name = "description")
	private String description;

	@Column(name = "comments")
	private String comments;

	@ManyToOne
	@JoinColumn(name = "service_Id")
	private InventoryVO inventoryvo;

	@ManyToOne
	@JoinColumn(name = "user_Id")
	private User uservo;

	public int getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(int solutionId) {
		this.solutionId = solutionId;
	}

	public String getSolutionTitle() {
		return solutionTitle;
	}

	public void setSolutionTitle(String solutionTitle) {
		this.solutionTitle = solutionTitle;
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

	public InventoryVO getInventoryvo() {
		return inventoryvo;
	}

	public void setInventoryvo(InventoryVO inventoryvo) {
		this.inventoryvo = inventoryvo;
	}

	public User getUservo() {
		return uservo;
	}

	public void setUservo(User uservo) {
		this.uservo = uservo;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

}
