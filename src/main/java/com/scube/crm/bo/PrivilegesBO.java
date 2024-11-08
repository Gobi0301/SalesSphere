package com.scube.crm.bo;

import java.util.ArrayList;
import java.util.List;

public class PrivilegesBO extends BaseBO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long privilegeId;
	//@NotNull(message="Privilege name cannot be empty")
	//@Pattern(regexp="^[a-zA-Z\\s]*$",message="Privileges name shoud be a character only")
	private String privilegename;
	private int sNo;
	private boolean isDeleted;
	private String accessName;
	private AccessBo accessbo;
	private List<Long> accessIds;

	private List<AccessBo> accessBOlist=new ArrayList<AccessBo>();
	
	public List<AccessBo> getAccessBOlist() {
		return accessBOlist;
	}
	public void setAccessBOlist(List<AccessBo> accessBOlist) {
		this.accessBOlist = accessBOlist;
	}
	public AccessBo getAccessbo() {
		return accessbo;
	}
	public void setAccessbo(AccessBo accessbo) {
		this.accessbo = accessbo;
	}
	public String getAccessName() {
		return accessName;
	}
	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}
	private List<PrivilegesBO> privilegeslis ;
	public List<PrivilegesBO> getPrivilegeslis() {
		return privilegeslis;
	}
	public void setPrivilegeslis(List<PrivilegesBO> privilegeslis) {
		this.privilegeslis = privilegeslis;
	}
	public int getsNo() {
		return sNo;
	}
	public boolean getDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public long getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(long privilegeId) {
		this.privilegeId = privilegeId;
	}
	public String getPrivilegename() {
		return privilegename;
	}
	public void setPrivilegename(String privilegename) {
		this.privilegename = privilegename;
	}
	public List<Long> getAccessIds() {
		return accessIds;
	}
	public void setAccessIds(List<Long> accessIds) {
		this.accessIds = accessIds;
	}
	

}
