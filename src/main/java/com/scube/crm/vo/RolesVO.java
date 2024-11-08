package com.scube.crm.vo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name="roles")
public class RolesVO extends BasicEntity {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="roleId")
	private long roleId;
	
	@Column(name="roleName")
	private String roleName; 
	

	@Column(name="isDelete")
	private boolean isDelete;
	
	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	@Column(name="isActive")
	private boolean isActive;
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
	
	
	private PrivilegesVO privilegesVo;

	@Transient
	public PrivilegesVO getPrivilegesVo() {
		return privilegesVo;
	}

	public void setPrivilegesVo(PrivilegesVO privilegesVo) {
		this.privilegesVo = privilegesVo;
	}
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="role_privileges_role",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="privilegeId")})

	private List<PrivilegesVO> listPrivilegesvo;


	public List<PrivilegesVO> getListPrivilegesvo() {
		return listPrivilegesvo;
	} 

	public void setListPrivilegesvo(List<PrivilegesVO> listPrivilegesvo) {
		this.listPrivilegesvo = listPrivilegesvo;
	}

	
	
}
