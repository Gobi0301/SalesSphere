package com.scube.crm.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

/**
 * It contains a created time stamp and a system unique id. It also includes a
 * version, used by the persistence layer to support optimistic transactions.
 */

@MappedSuperclass
public class BasicEntity implements Serializable {

	private static final long serialVersionUID = -9169880638508556045L;

	// private Long id;
	/*
	 * @Transient private int version;
	 */
	@Column(name = "created")
	private Date created;
	@Column(name = "modified")
	private Date modified;
	@Column(name = "createdBy")
	private long createdBy;
	@Column(name = "modifiedBy")
	private long modifiedBy;
	
	@Transient
	private int recordIndex;
	@Transient
	private int maxRecord;

	public BasicEntity() {

	}

	@Column(name = "companyId")
	private Long companyId;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the created_By
	 */
	@Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
	public long getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * @param created_By the created_By to set
	 */
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the modified_By
	 */
	@Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
	public long getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * @param modified_By the modified_By to set
	 */
	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * The creation date is an imutable date, filled during instantiation of the
	 * entity.
	 * 
	 * @return Returns the created.
	 */
	@Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
	@DateBridge(resolution = Resolution.DAY)
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	

	/**
	 * @return Returns the modified.
	 */
	@Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
	@DateBridge(resolution = Resolution.DAY)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public int getRecordIndex() {
		return recordIndex;
	}

	public void setRecordIndex(int recordIndex) {
		this.recordIndex = recordIndex;
	}

	public int getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(int maxRecord) {
		this.maxRecord = maxRecord;
	}


	
}
