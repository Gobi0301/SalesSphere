/**
 * 
 */
package com.scube.crm.vo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.scube.crm.bo.TaskManagementBO;

/**
 * @author User
 *
 */

@Entity
@Table(name = "login")
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email_Id")
	private String emailAddress;

	@Column(name = "isActive")
	private boolean isActive;

	@Column(name = "isDelete")
	private boolean isDelete;

	@Column(name = "password")
	private String password;

	@Column(name = "confirmpassword")
	private String confirmpassword;

	@Column(name = "mobileno")
	private String mobileNo;

	@Column(name = "primarySkill")
	private String primarySkill;

	public String getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}

	@OneToOne
	@JoinColumn(name = "Product_Id")
	private InventoryVO productServiceVO;

	@ManyToMany
	//@ManyToMany(fetch = FetchType.EAGER)  --> view employee pagination issue changed by sibi
	@JoinTable(name = "Employee_Skills", joinColumns = {
			@JoinColumn(name = "id", unique = false) }, inverseJoinColumns = {
					@JoinColumn(name = "skillsId", unique = false) })
	private List<SkillsVO> skillsListVO;

	@OneToMany(mappedBy = "taskOwner")
	private List<TaskManagementVO> taskListvO;

	public List<TaskManagementVO> getTaskListvO() {
		return taskListvO;
	}

	public void setTaskListvO(List<TaskManagementVO> taskListvO) {
		this.taskListvO = taskListvO;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	public List<SkillsVO> getSkillsListVO() {
		return skillsListVO;
	}

	public void setSkillsListVO(List<SkillsVO> skillsListVO) {
		this.skillsListVO = skillsListVO;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "roleId") })
	private List<RolesVO> listRoletypeVo;

	public List<RolesVO> getListRoletypeVo() {
		return listRoletypeVo;
	}

	public void setListRoletypeVo(List<RolesVO> listRoletypeVo) {
		this.listRoletypeVo = listRoletypeVo;
	}

	@OneToOne
	@JoinColumn(name = "companyId")
	private Company company;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the confirmpassword
	 */
	public String getConfirmpassword() {
		return confirmpassword;
	}

	/**
	 * @param confirmpassword the confirmpassword to set
	 */
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the isDelete
	 */
	public boolean isDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setisDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public InventoryVO getProductServiceVO() {
		return productServiceVO;
	}

	public void setProductServiceVO(InventoryVO productServiceVO) {
		this.productServiceVO = productServiceVO;
	}

	private SkillsVO skillsVO;

	public SkillsVO getSkillsVO() {
		return skillsVO;
	}

	public void setSkillsVO(SkillsVO skillsVO) {
		this.skillsVO = skillsVO;
	}

}
