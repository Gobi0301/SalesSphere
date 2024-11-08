package com.scube.crm.vo;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles_privileges")
public class RolePrivilegesVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3144762174696285400L;

	private RolePrivileges id;		

	public RolePrivilegesVO(){

	}

	public RolePrivilegesVO(RolePrivileges id, PrivilegesVO privileges, RolesVO roles) {
		this.id = id;		
	}


	public RolePrivilegesVO(RolePrivileges id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "roleId", column = @Column(name = "roleId", nullable = false)),
		@AttributeOverride(name = "privilegeId", column = @Column(name = "privilegeId", nullable = false)) })
	public RolePrivileges getId() {
		return this.id;
	}

	public void setId(RolePrivileges id) {
		this.id = id;
	}


}


