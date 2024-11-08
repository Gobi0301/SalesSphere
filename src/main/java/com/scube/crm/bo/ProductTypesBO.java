package com.scube.crm.bo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ProductTypesBO extends BaseBO{

	//Below all variables are using in manage products 

	/**
	 * 
	 */
	private static final long serialVersionUID = -4148036502561994703L;

	private  int sNO;
	private long productTypesId;
	
	@NotNull(message="productTypes may not be empty")
	@NotEmpty (message="productTypes may not be empty")
	private String productTypes;
	private boolean isDeleted;
	public int getsNO() {
		return sNO;
	}
	public void setsNO(int sNO) {
		this.sNO = sNO;
	}
	private AdminLoginBO adminLoginBO; 
	
	public String getProductTypes() {
		return productTypes;
	}
	public void setProductTypes(String productTypes) {
		this.productTypes = productTypes;
	}
	public boolean getisDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public long getProductTypesId() {
		return productTypesId;
	}
	public void setProductTypesId(long productTypesId) {
		this.productTypesId = productTypesId;
	}
	public AdminLoginBO getAdminLoginBO() {
		return adminLoginBO;
	}
	public void setAdminLoginBO(AdminLoginBO adminLoginBO) {
		this.adminLoginBO = adminLoginBO;
	}
	

	
	

}
