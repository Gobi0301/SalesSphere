package com.scube.crm.vo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.scube.crm.bo.InventoryBO;

@Entity
@Table(name="supplierProductsInfo")

public class SupplierProductVO extends BasicEntity{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="supplierProduct_id")
	private long supplierproductId;

	/*	@Column(name="supplier_id")
	private long supplierId; */

	
	@Column(name = "tech_ord")
	private String techOriented;

	@Column(name = "buying_price")
	private long buyingPrice;
	
	@Column(name="isActive")
	private boolean isActive;
	
	@Column(name="isdelete")
	private boolean isDelete;

	@ManyToOne
	@JoinColumn(name="supplier_id")
	private SupplierVO supplierVO;
	
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private InventoryVO productServiceVO;
	
	@Transient
	private List<InventoryVO> productServiceListVO;
	
	public List<InventoryVO> getProductServiceListVO() {
		return productServiceListVO;
	}

	public void setProductServiceListVO(List<InventoryVO> productServiceListVO) {
		this.productServiceListVO = productServiceListVO;
	}

	public InventoryVO getProductServiceVO() {
		return productServiceVO;
	}

	public void setProductServiceVO(InventoryVO productServiceVO) {
		this.productServiceVO = productServiceVO;
	}

	public long getSupplierproductId() {
		return supplierproductId;
	}

	public void setSupplierproductId(long supplierproductId) {
		this.supplierproductId = supplierproductId;
	}

	public SupplierVO getSupplierVO() {
		return supplierVO;
	}

	public void setSupplierVO(SupplierVO supplierVO) {
		this.supplierVO = supplierVO;
	}

	
	public String getTechOriented() {
		return techOriented;
	}

	public void setTechOriented(String techOriented) {
		this.techOriented = techOriented;
	}


	public long getBuyingPrice() {
		return buyingPrice;
	}

	public void setBuyingPrice(long buyingPrice) {
		this.buyingPrice = buyingPrice;
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


}
