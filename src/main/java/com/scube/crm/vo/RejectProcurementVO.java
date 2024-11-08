package com.scube.crm.vo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="reject_procurement")
public class RejectProcurementVO extends BasicEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="rejectId")
	private long rejectId;
	@Column(name="reason")
	private String reason;
	@Column(name="rejectStaus")
	private String rejectStatus;
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(name="procurementId")
	
	
	private ProcurementVO procurementReject;	
	public SupplierVO getSupplierVO() {
		return supplierVO;
	}
	public void setSupplierVO(SupplierVO supplierVO) {
		this.supplierVO = supplierVO;
	}
	public InventoryVO getProductServiceVO() {
		return productServiceVO;
	}
	public void setProductServiceVO(InventoryVO productServiceVO) {
		this.productServiceVO = productServiceVO;
	}
	@OneToOne
	private SupplierVO supplierVO;
	@OneToOne
	private InventoryVO productServiceVO;

	public String getRejectStatus() {
		return rejectStatus;
	}
	public void setRejectStatus(String rejectStatus) {
		this.rejectStatus = rejectStatus;
	}
	
	 
	public ProcurementVO getProcurementReject() {
		return procurementReject;
	}
	public void setProcurementReject(ProcurementVO procurementReject) {
		this.procurementReject = procurementReject;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public long getRejectId() {
		return rejectId;
	}
	public void setRejectId(long rejectId) {
		this.rejectId = rejectId;
	}
	 

}
