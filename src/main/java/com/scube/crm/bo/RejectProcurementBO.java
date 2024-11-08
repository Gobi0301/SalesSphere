package com.scube.crm.bo;

public class RejectProcurementBO extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	private long rejectId;
	private String reason;
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
	private ProcurementBO procurementBO;
    private InventoryBO productServiceBO;
	
	public String getProcurementStatus() {
		return ProcurementStatus;
	}
	public void setProcurementStatus(String procurementStatus) {
		ProcurementStatus = procurementStatus;
	}
	public ProcurementBO getProcurementBO() {
		return procurementBO;
	}
	public void setProcurementBO(ProcurementBO procurementBO) {
		this.procurementBO = procurementBO;
	}
	 
	public long getRejectId() {
		return rejectId;
	}
	public void setRejectId(long rejectId) {
		this.rejectId = rejectId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	

}
