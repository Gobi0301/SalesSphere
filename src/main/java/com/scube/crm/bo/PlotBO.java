package com.scube.crm.bo;

public class PlotBO  extends BaseBO{
	private static final long serialVersionUID = 1L;
	
	private long plotId;
	private String plotNumbers;
	private Integer plotSquareFeet;
	private Integer length;
	private Integer width;
	private String status;
	private boolean isDelete;
	private int sNo;
	
	public long getPlotId() {
		return plotId;
	}
	public void setPlotId(long plotId) {
		this.plotId = plotId;
	}
	
	public String getPlotNumbers() {
		return plotNumbers;
	}
	public void setPlotNumbers(String plotNumbers) {
		this.plotNumbers = plotNumbers;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean getisDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getPlotSquareFeet() {
		return plotSquareFeet;
	}
	public void setPlotSquareFeet(Integer plotSquareFeet) {
		this.plotSquareFeet = plotSquareFeet;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
}
