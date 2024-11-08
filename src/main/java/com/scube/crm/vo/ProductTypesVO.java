package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_types")
public class ProductTypesVO extends BasicEntity{

	// Below all variables are duplicates not used

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String productTypes;
	private boolean isDeleted;


	@Id
	@GeneratedValue
	@Column(name="Id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="product_type")
	public String getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(String productTypes) {
		this.productTypes = productTypes;
	}
	@Column(name="isDeleted")
	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


}
