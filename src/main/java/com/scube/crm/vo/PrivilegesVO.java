package com.scube.crm.vo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="privileges")
public class PrivilegesVO extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4006952138506665044L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="privilegeId")
	private long privilegeId;
	
	@Column(name="privileges_name",nullable=false)
	private String privilegesName;
	private boolean isDeleted;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="privileges_access",joinColumns={@JoinColumn(name="privilege_Id")},inverseJoinColumns={@JoinColumn(name="access_Id")})
	private List<AccessVo> accessList;
	
	public List<AccessVo> getAccessList() {
		return accessList;
	}

	public void setAccessList(List<AccessVo> accessList) {
		this.accessList = accessList;
	}

	@Column(name="is_Deleted")
	public boolean getisDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilegesName() {
		return privilegesName;
	}

	public void setPrivilegesName(String privilegesName) {
		this.privilegesName = privilegesName;
	}

	

}
