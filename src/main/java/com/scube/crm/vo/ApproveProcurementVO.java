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

import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.SupplierBO;

@Entity
@Table(name="approve_procurement")
public class ApproveProcurementVO extends BasicEntity{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="approveId") 
	private long approveId;
	@Column(name="Description") 
	private String Description;
	@Column(name="sentTo") 
	private String sentTo;
	@Column(name="isDelete")
	private boolean isDelete;
	@Column(name="approvalStatus") 
	private String approvalStatus;
	@Column(name="expectedDate")
	private String expectedDate;
	
	
	@OneToOne
	private SupplierVO supplierVO;
	@OneToOne
	private InventoryVO productServiceVO;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="procurementId")
	private ProcurementVO procurement;	 
	 
	public String getExpectedDate() {
		return expectedDate;
	}
	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}
	
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
	
	public ProcurementVO getProcurement() {
		return procurement;
	}
	public void setProcurement(ProcurementVO procurement) {
		this.procurement = procurement;
	}
	public boolean isDelete() {
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
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
}