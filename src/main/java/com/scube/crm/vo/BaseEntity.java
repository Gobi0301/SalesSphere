package com.scube.crm.vo;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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

@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = -9169880638508556045L;

	// private Long id;
	/*
	 * @Transient private int version;
	 */
	@Column(name = "created")
	private Date created = new Date();
	@Column(name = "modified")
	private Date modified = new Date();
	@Column(name = "createdBy")
	private long createdBy;
	@Column(name = "modifiedBy")
	private long modifiedBy;
	@Transient
	private int recordIndex;
	@Transient
	private int maxRecord;
	// private String additionalContent = null;

	
	public BaseEntity() {

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
	 * The version is a technical feature, used by the underlying persinstance
	 * engines, to support optimistic transactional behaviour. Whenever a Entity is
	 * merged back to from outer changes, the change-operation fails, if the version
	 * number has been changed meanwhile. On the other hand: if the version of the
	 * entity is the same as before the change, it is for shure, that the entity has
	 * not been changed meanwhile.
	 * 
	 * @return Returns the version.
	 */
	// @Version
	/*
	 * public int getVersion() { return this.version; }
	 * 
	 * public void setVersion(int version) { this.version = version; }
	 */

	/**
	 * @see #setLock(int)
	 * @return Returns the lock.
	 */

	/**
	 * Checks if the entity is locked.
	 * 
	 * @return true, if the entity is locked
	 */

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

	/**
	 * A callback method, used to update the modified date during update operations
	 * regarding this entity.
	 */
	@PrePersist
	@PreUpdate
	protected void updateModifiedDate() {
		this.modified = new Date();
	}

	/**
	 * Returns some additional content stored to this entity, if available. Content
	 * should be stored and expected using a XML format.
	 * 
	 * @return Returns the additionalContent.
	 */
	/*
	 * public String getAdditionalContent() { return this.additionalContent; }
	 *//**
		 * @see #getAdditionalContent()
		 * @param additionalContent The additionalContent to set.
		 */
	/*
	 * public void setAdditionalContent(String additionalContent) {
	 * this.additionalContent = additionalContent; }
	 */

	/*
	 * @Override public String toString() { return toUniqueString(); }
	 *//**
		 * Override in extended classes to return a unique string representation of the
		 * entity that is human readable, e.g. an order number. Typically this is a
		 * property annotated with <code>@Column(nullable=false, unique=true)</code>.
		 * 
		 * @return a unique string representation of the entity
		 */
	/*
	 * public String toUniqueString() { return getId().toString(); }
	 */

	/**
	 * Returns a description of this entity, basically all properties as
	 * key-value-pairs. Override in extended classes to gain performance. This
	 * method uses reflections.
	 * 
	 * @return a description of this entity
	 */
	public String toDescriptiveString() {
		final StringBuffer b = new StringBuffer();
		try {
			final BeanInfo info = Introspector.getBeanInfo(this.getClass());
			final java.beans.PropertyDescriptor[] d = info.getPropertyDescriptors();

			b.append(this.getClass().getSimpleName());
			b.append(": ");

			for (final PropertyDescriptor element : d) {
				try {

					if (element.getName().equals("class")) {
						continue;
					}
					b.append("[");
					b.append(element.getName());
					b.append("=");
					try {
						b.append(element.getReadMethod().invoke(this, new Object[0]).toString());
					} catch (final Throwable t) {
						b.append("?");
					}
					b.append("]");
				} catch (final Throwable ex) {
					continue;
				}
			}
			return new String(b);
		} catch (final Throwable t) {
			return super.toString();
		}
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

	/**
	 * Returns a shot description of this entity, basically no properties as
	 * key-value-pairs. Override in extended classes to gain performance. This
	 * method gives not much information.
	 * 
	 * @return a description of this entity
	 */
	/*
	 * public String toShortString() { return getClass().getSimpleName() + ": [id="
	 * + getId() + "]"; }
	 */

	/**
	 * @return true if obj has the same id as this instance
	 */
	/*
	 * public boolean equals(Ob 
	 * ject obj) { if (obj == this) { return true; } else if
	 * (obj == null) { return false; }
	 * 
	 * // In list operations with lazy loaded entities, you may get a proxy with the
	 * correct id but a different class else if ( obj instanceof BasicEntity ) {
	 * BasicEntity e = (BasicEntity) obj; if (e.getId().equals(getId())) { return
	 * true; } else { return false; } }
	 * 
	 * else { return false; } }
	 */

}
