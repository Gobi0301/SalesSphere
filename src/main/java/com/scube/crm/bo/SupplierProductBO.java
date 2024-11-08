package com.scube.crm.bo;

import java.util.List;

public class SupplierProductBO extends BaseBO{

	
	private long supplierproductId;
	private long supplierId;
	private String techOriented;
	private Long buyingPrice;
	private boolean isActive;
    private boolean isDelete;
    private String serviceName;
    
	 public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	private InventoryBO productServiceBO;
     private List<InventoryBO> productServiceListBO;
	
     public long getSupplierproductId() {
 		return supplierproductId;
 	}
 	public void setSupplierproductId(long supplierproductId) {
 		this.supplierproductId = supplierproductId;
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
	public void setProductServiceListBO(List<InventoryBO> productServiceListBO) {
		this.productServiceListBO = productServiceListBO;
	}

	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	public String getTechOriented() {
		return techOriented;
	}
	public void setTechOriented(String techOriented) {
		this.techOriented = techOriented;
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
	public Long getBuyingPrice() {
		return buyingPrice;
	}
	public void setBuyingPrice(Long buyingPrice) {
		this.buyingPrice = buyingPrice;
	}
	
	

}
