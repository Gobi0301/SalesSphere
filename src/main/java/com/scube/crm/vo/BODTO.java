package com.scube.crm.vo;

import java.io.Serializable;

public class BODTO<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	/** database id for the referenced Entity */
	private Long id;
	/** A unique Name */
	private String name;
	/** entity version in database */
	
	private int version;
	/** entity classname */
	private String className;

	public BODTO() {
		//
	}

	public BODTO(Long id, int version, String name) {
		this.id = id;
		this.version = version;
		this.name = name;
	}

	public BODTO(Long id, int version, Long uniqueId) {
		this.id = id;
		this.version = version;
		this.name = Long.toString(uniqueId);
	}

	/**
	 * database id for the referenced Entity
	 */
	public Long getId() {
		return this.id;
	}

	/** A unique Name */
	public String getName() {
		return this.name;
	}

	/** entity version in database */
	public int getVersion() {
		return this.version;
	}

	/** entity classname */
	public String getClassName() {
		return this.className;
	}

	/** entity classname */
	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		BODTO<BasicEntity> to;

		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj instanceof BODTO) {
			to = (BODTO<BasicEntity>) obj;
		} else {
			return false;
		}

		if (this.id == null || to.id == null) {
			return false;
		}
		return this.id.equals(to.id) && this.version == to.version;

	}

	@Override
	public int hashCode() {
		return this.id == null ? 0 : Long.valueOf(this.id + this.version)
				.hashCode();
	}

}
