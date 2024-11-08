package com.scube.crm.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="gst")
public class GstVO extends BasicEntity{

	private static final long serialVersionUID = 1L;

	private long gstId;
	private String cgst;
	private String sgst;
	private Date startDate;
	
	private boolean isActive;
	private boolean isDelete;
	private User userVO;
	private InventoryVO product;  //Product mapping
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="gst_id")
	public long getGstId() {
		return gstId;
	}
	public void setGstId(long gstId) {
		this.gstId = gstId;
	}
	@Column(name="cgst",nullable=false)
	public String getCgst() {
		return cgst;
	}
	public void setCgst(String cgst) {
		this.cgst = cgst;
	}
	
	@Column(name="sgst",nullable=false)
	public String getSgst() {
		return sgst;
	}
	public void setSgst(String sgst) {
		this.sgst = sgst;
	}
	
	@Column(name="isActive")
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	@Column(name="isDelete")
	public boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	@OneToOne
	@JoinColumn(name="user_id")
	public User getUserVO() {
		return userVO;
	}
	public void setUserVO(User userVO) {
		this.userVO = userVO;
	}
	
	@Column(name="start_Date",nullable=false,columnDefinition="DATETIME")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date date) {
		this.startDate = date;
	}
	@ManyToOne
	@JoinColumn(name="product_id")
	public InventoryVO getProduct() {
		return product;
	}
	public void setProduct(InventoryVO product) {
		this.product = product;
	}
}
