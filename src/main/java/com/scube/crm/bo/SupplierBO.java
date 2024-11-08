package com.scube.crm.bo;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.scube.crm.vo.SupplierProductVO;

public class SupplierBO  extends BaseBO {
      /**
	 * 
	 */
	private static final long serialVersionUID = -5410772119106662578L;
	
	  private long supplierId;
	  private int sNO;
	  //@NotBlank(message="Please enter Supplier Name")
      private String supplierName;
      private String emailId;
      private String address;
      private Long mobileNo;
      private String city;
      private String state;
      private String country;
      private String webSite;
      private String techOriented;

      private Long financialAmount;

      private String rating;
      private String location;
      private boolean isActive;
      private boolean isDelete;
      
      private InventoryBO productServiceBO;
      private List<InventoryBO> productServiceListBO;
      private SupplierProductBO supplierProductBO;
      private List<SupplierProductBO> supplierProductBOList;
      
      public SupplierProductBO getSupplierProductBO() {
		return supplierProductBO;
      }
      public void setSupplierProductBO(SupplierProductBO supplierProductBO) {
		this.supplierProductBO = supplierProductBO;
      }
	
      
      public List<SupplierProductBO> getSupplierProductBOList() {
		return supplierProductBOList;
      }
      public void setSupplierProductBOList(List<SupplierProductBO> supplierProductBOList) {
		this.supplierProductBOList = supplierProductBOList;
      	}
	private List<SupplierProductVO> supplierproductList;
 

    
	public List<SupplierProductVO> getSupplierproductList() {
		return supplierproductList;
	}
	public void setSupplierproductList(List<SupplierProductVO> supplierproductList) {
		this.supplierproductList = supplierproductList;
	}
	public int getsNO() {
 		return sNO;	}
 	public void setsNO(int sNO) {
		this.sNO = sNO;
 	}
      
 	
 	
	
	
	
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAddress() {
		return address;
	}
	
	
	public Long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getTechOriented() {
		return techOriented;
	}
	public void setTechOriented(String techOriented) {
		this.techOriented = techOriented;
	}



	
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public InventoryBO getProductServiceBO() {
		return productServiceBO;
	}
	public void setProductServiceBO(InventoryBO productServiceBO) {
		this.productServiceBO = productServiceBO;
	}
	public List<InventoryBO> getProductServiceListBO() {
		return productServiceListBO;
	}
	public void setProductServiceLisBO(List<InventoryBO> productServiceListBO) {
		this.productServiceListBO = productServiceListBO;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Long getFinancialAmount() {
		return financialAmount;
	}
	public void setFinancialAmount(Long financialAmount) {
		this.financialAmount = financialAmount;
	}
	

	}
	
     
