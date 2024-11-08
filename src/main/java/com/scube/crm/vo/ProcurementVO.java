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
@Table(name="procurement")
public class ProcurementVO extends BasicEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="procurementId") 
	private long procurementId;
	@Column(name = "minimumStock")
	private Integer minimumStock;
	@Column(name = "maximumStock")
	private Integer maximumStock;
	@Column(name = "availableStock")
	private Integer availableStock;
	@Column(name = "quantityOfProducts")
	private String quantityOfProducts;
	@Column(name = "unitOfCost")
	private Integer unitOfCost;
	@Column(name = "totalCost")
	private Integer totalCost;
	@Column(name = "expectedDate")
	private String expectedDate;
	@Column(name = "reject")
	private String reject;
 
	@Column(name="isdelete")
	private boolean isDelete; 

	 
 
 
	

 
	//	@Transient
//	private User userVO;
 
 	public boolean isDelete() {
		return isDelete;
	}
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	@OneToOne
	@JoinColumn(name="product_id") 
	private InventoryVO productServiceVO;
	
	@OneToOne
	@JoinColumn(name="supplier_id") 
	private SupplierVO supplierVO;
	
	@OneToOne(mappedBy="procurement",cascade = CascadeType.ALL) 
	@JoinColumn(name="approveId")
	private ApproveProcurementVO approveVO;
	
	@OneToOne(mappedBy="procurementReject",cascade = CascadeType.ALL) 
	@JoinColumn(name="rejectId")
	private RejectProcurementVO rejectVO;
	
	
	public RejectProcurementVO getRejectVO() {
		return rejectVO;
	}
	public void setRejectVO(RejectProcurementVO rejectVO) {
		this.rejectVO = rejectVO;
	}
	 
		public InventoryVO getProductServiceVO() {
		return productServiceVO;
	}
	public void setProductServiceVO(InventoryVO productServiceVO) {
		this.productServiceVO = productServiceVO;
	}
 
	 public ApproveProcurementVO getApproveVO() {
		return approveVO;
	}
	public void setApproveVO(ApproveProcurementVO approveVO) {
		this.approveVO = approveVO;
	}
 
 
	public SupplierVO getSupplierVO() {
		return supplierVO;
	}
	public void setSupplierVO(SupplierVO supplierVO) {
		this.supplierVO = supplierVO;
	}
	public long getProcurementId() {
		return procurementId;
	}
	public void setProcurementId(long procurementId) {
		this.procurementId = procurementId;
	}
//	@Transient
//	public User getUserVO() {
//		return userVO;
//	}
//	public void setUserVO(User userVO) {
//		this.userVO = userVO;
//	}
	public String getExpectedDate() {
		return expectedDate;
	}
	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}
	public String getReject() {
		return reject;
	}
	public void setReject(String reject) {
		this.reject = reject;
	}
	public Integer getMinimumStock() {
		return minimumStock;
	}

	public void setMinimumStock(Integer minimumStock) {
		this.minimumStock = minimumStock;
	}

	public Integer getMaximumStock() {
		return maximumStock;
	}

	public void setMaximumStock(Integer maximumStock) {
		this.maximumStock = maximumStock;
	}

	public Integer getAvailableStock() {
		return availableStock;
	}

	public void setAvailableStock(Integer availableStock) {
		this.availableStock = availableStock;
	}

	public String getQuantityOfProducts() {
		return quantityOfProducts;
	}

	public void setQuantityOfProducts(String quantityOfProducts) {
		this.quantityOfProducts = quantityOfProducts;
	}

	public Integer getUnitOfCost() {
		return unitOfCost;
	}

	public void setUnitOfCost(Integer unitOfCost) {
		this.unitOfCost = unitOfCost;
	}

	public Integer getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Integer totalCost) {
		this.totalCost = totalCost;
	}

}