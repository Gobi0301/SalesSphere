package com.scube.crm.bo;

public class SlaBO extends BaseBO{
	
	private static final long serialVersionUID = 1L;
	
	
	private long slaId;
	private String slaName;
	private String slaCode;
	private String date;
	private String descriptions;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	private int sNo;
	
	
	
	public long getSlaId() {
		return slaId;
	}
	public void setSlaId(long slaId) {
		this.slaId = slaId;
	}
	public String getSlaName() {
		return slaName;
	}
	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	public String getSlaCode() {
		return slaCode;
	}
	public void setSlaCode(String slaCode) {
		this.slaCode = slaCode;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	
	 
	
	
}
