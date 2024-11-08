package com.scube.crm.bo;

public class ApproveProcurementBO extends BaseBO{

	private static final long serialVersionUID = 1L;
	
	public String getExpectedDate() {
		return expectedDate;
	}
	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}
	private String expectedDate;
	private long approveId;
	private String sentTo;
	private String Description;
	private boolean isDelete;
	private String ProcurementStatus;
	private SupplierBO supplierBO;
	public SupplierBO getSupplierBO() {
		return supplierBO;
	}
	public void setSupplierBO(SupplierBO supplierBO) {
		this.supplierBO = supplierBO;
	}
	public InventoryBO getProductServiceBO() {
		return productServiceBO;
	}
	public void setProductServiceBO(InventoryBO productServiceBO) {
		this.productServiceBO = productServiceBO;
	}
	private InventoryBO productServiceBO;
	
	private ProcurementBO procurementBO;
	 
	
	public ProcurementBO getProcurementBO() {
		return procurementBO;
	}
	public void setProcurementBO(ProcurementBO procurementBO) {
		this.procurementBO = procurementBO;
	}
	public String getProcurementStatus() {
		return ProcurementStatus;
	}
	public void setProcurementStatus(String procurementStatus) {
		ProcurementStatus = procurementStatus;
	}
	private long procurementId;
	
	 
	public boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public long getApproveId() {
		return approveId;
	}
	public void setApproveId(long approveId) {
		this.approveId = approveId;
	}
	public String getSentTo() {
		return sentTo;
	}
	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public long getProcurementId() {
		return procurementId;
	}
	public void setProcurementId(long procurementId) {
		this.procurementId = procurementId;
	}
	
	
}