package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RolePrivileges implements java.io.Serializable {

	private static final long serialVersionUID = -3821484553334764174L;
	private int roleId;
	private int privilegeId;

	public RolePrivileges() {
	}

	public RolePrivileges(int roleId, int privilegeId) {
		this.roleId = roleId;
		this.privilegeId = privilegeId;
	}

	@Column(name = "roleId", nullable = false)
	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Column(name = "privilegeId", nullable = false)
	public int getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RolePrivileges))
			return false;
		RolePrivileges castOther = (RolePrivileges) other;

		return (this.getRoleId() == castOther.getRoleId()) && (this.getPrivilegeId() == castOther.getPrivilegeId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getRoleId();
		result = 37 * result + this.getPrivilegeId();
		return result;
	}

}
