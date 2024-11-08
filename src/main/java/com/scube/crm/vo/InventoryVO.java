package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="manage_products")
public class InventoryVO extends BasicEntity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="service_id")
	private long serviceId;
	@Column(name="service_Name",nullable=false)
	private String serviceName;
	@Column(name="fees",nullable=false,columnDefinition="DOUBLE")
	private double fees;
	@Column(name="start_Date")
	private String startDate;
	@Column(name="end_Date")
	private String endDate;
	@Column(name="Service_Specification",nullable=false)
	private String ServiceSpecification;
	private boolean isActive;
	
	@Column(name="minimumStocks",nullable=false)
	private Long minimumStocks;
	@Column(name="maximumStocks",nullable=false)
	private Long maximumStocks;
	@Column(name="availableStocks",nullable=false)
	private Long availableStocks;
 
	@OneToOne
	@JoinColumn(name="product_type")
	private ProductTypesVO productTypesvO;

	private boolean isDelete;
  
	//@ManyToMany(mappedBy = "productServiceVO")
	//private SupplierVO supplierVO;

 
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userVO;
	
	public long getServiceId() {
		return serviceId;
	}
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	
	public double getFees() {
		return fees;
	}
	public void setFees(double fees) {
		this.fees = fees;
	}
 
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
 
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
 
	public String getServiceSpecification() {
		return ServiceSpecification;
	}
	public void setServiceSpecification(String serviceSpecification) {
		ServiceSpecification = serviceSpecification;
	}
	 
	public User getUserVO() {
		return userVO;
	}
	public void setUserVO(User userVO) {
		this.userVO = userVO;
	}

	public Long getMinimumStocks() {
		return minimumStocks;
	}

	public void setMinimumStocks(Long minimumStocks) {
		this.minimumStocks = minimumStocks;
	}

	public Long getMaximumStocks() {
		return maximumStocks;
	}
	public void setMaximumStocks(Long maximumStocks) {
		this.maximumStocks = maximumStocks;
	}

	public Long getAvailableStocks() {
		return availableStocks;
	}
	public void setAvailableStocks(Long availableStocks) {
		this.availableStocks = availableStocks;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
  
	 
	public ProductTypesVO getProductTypesvO() {
		return productTypesvO;
	}
	public void setProductTypesvO(ProductTypesVO productTypesvO) {
		this.productTypesvO = productTypesvO;
	}

}
