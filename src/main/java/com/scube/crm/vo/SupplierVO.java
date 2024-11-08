package com.scube.crm.vo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "supplier_info")

public class SupplierVO extends BasicEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7492261469081350986L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="supplier_id")
	private long supplierId;

	@Column(name = "supplier_name")
	private String supplierName;

	@Column(name = "email")
	private String emailId;

	@Column(name = "mobile_no")
	private Long mobileNo;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "web_site")
	private String webSite;

	@Column(name = "tech_ord")
	private String techOriented;
    
	@Column(name = "financial_amount")

	private Long financialAmount;


	@Column(name = "rating")
	private String rating;
    @Column(name="location")
	private String location;
	@Column(name="isActive")
	private boolean isActive;
	
	@Column(name="isdelete")
	private boolean isDelete;
	
	@Transient
	private List<InventoryVO> productServiceVO;
	
	@OneToMany(mappedBy = "supplierVO", cascade = CascadeType.ALL)
    private List<SupplierProductVO> supplierProducts;

	public List<SupplierProductVO> getSupplierProducts() {
		return supplierProducts;
	}

	public void setSupplierProducts(List<SupplierProductVO> supplierProducts) {
		this.supplierProducts = supplierProducts;
	}

	public List<InventoryVO> getProductServiceVO() {
		return productServiceVO;
	}

	public void setProductServiceVO(List<InventoryVO> productServiceVO) {
		this.productServiceVO = productServiceVO;
	}
	
	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
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

	public Long getFinancialAmount() {
		return financialAmount;
	}

	public void setFinancialAmount(Long financialAmount) {
		this.financialAmount = financialAmount;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
