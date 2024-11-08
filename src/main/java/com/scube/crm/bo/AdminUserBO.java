/**
 * 
 */
package com.scube.crm.bo;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author User
 *
 */
public class AdminUserBO extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long adminId;
	@NotNull(message = "Name should not be empty")
	@Pattern(regexp = "^[a-zA-Z\\s]*$", message = " Name Should be a Character")
	private String name;
	@NotNull(message = "Email should not be empty")
	@Pattern(regexp = ".+@.+\\.[a-z]+", message = "Invalid Email Format")
	private String emailAddress;
	@NotNull(message = "Password should not be empty")
	@Size(min = 4, max = 8, message = "Password minimum 4 value")
	private String password;
	@NotNull(message = "Confirm password should not be empty")
	@Size(min = 4, max = 8, message = "Password minimum 4 value")
	private String confirmPassword;
	@NotNull
	// @Size(min = 111111111,message = "Phone Number must be a 10 Digits")
	private String mobileNo;

	/*
	 * @Pattern(regexp = "^[a-zA-Z\\s]*$", message =
	 * "User Type Should be a Character")
	 */
	private String userType;
	private boolean isActive;
	private boolean isDelete;

	private InventoryBO productServiceBO;
	private String serviceName;

	private String primarySkill;

	public InventoryBO getProductServiceBO() {
		return productServiceBO;
	}

	public void setProductServiceBO(InventoryBO productServiceBO) {
		this.productServiceBO = productServiceBO;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	private int sNo;
	private String status;
	private long userId;

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	private long roleId;
	private String roleName;
	private List<Long> roleIdList;

	private List<TaskManagementBO> taskListBO;

	public List<TaskManagementBO> getTaskListBO() {
		return taskListBO;
	}

	public void setTaskListBO(List<TaskManagementBO> taskListBO) {
		this.taskListBO = taskListBO;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	private RoleBO rolebo;

	public RoleBO getRolebo() {
		return rolebo;
	}

	public void setRolebo(RoleBO rolebo) {
		this.rolebo = rolebo;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the isDelete
	 */
	public boolean getisDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setisDelete(boolean isDelete) {
		this.isDelete = isDelete;
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
	 * @return the sNo
	 */
	/**
	 * @return the sNo
	 */
	public int getsNo() {
		return sNo;
	}

	/**
	 * @param sNo the sNo to set
	 */
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the adminId
	 */
	public Long getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	/**
	 * @param adminLoginBO
	 */
	public void setAdminLoginBO(AdminLoginBO adminLoginBO) {

	}

	private List<RoleBO> listrlebo;

	public List<RoleBO> getListrlebo() {
		return listrlebo;
	}

	public void setListrlebo(List<RoleBO> listrlebo) {
		this.listrlebo = listrlebo;
	}

	public String getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}

	private SkillsBO skillsBO;
	private List<SkillsBO> skillsListBO;

	public SkillsBO getSkillsBO() {
		return skillsBO;
	}

	public List<SkillsBO> getSkillsListBO() {
		return skillsListBO;
	}

	public void setSkillsBO(SkillsBO skillsBO) {
		this.skillsBO = skillsBO;
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

	/*
	 * private String skillASString=null;
	 * 
	 * public String getSkillASString() { if(null!=skillsListBO &&
	 * skillsListBO.size()>0 && ! skillsListBO.isEmpty()) { for (SkillsBO skills :
	 * skillsListBO) {
	 * 
	 * skillASString=skillASString+","+skills.getDescriptions(); } } return
	 * skillASString; }
	 */
	private List<Long> skillsIds;

	public List<Long> getSkillsIds() {
		return skillsIds;
	}

	public void setSkillsIds(List<Long> skillsIds) {
		this.skillsIds = skillsIds;
	}

	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        AdminUserBO that = (AdminUserBO) o;
	        return Objects.equals(name, that.name) &&
	               Objects.equals(adminId, that.adminId);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(name, adminId);
	    }
}
