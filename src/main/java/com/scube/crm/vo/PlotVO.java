package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="plot")
public class PlotVO extends BasicEntity{
private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long plotId;
	@Column(name = "plotNumbers")
	private String plotNumbers;
	@Column(name = "plotSquareFeet")
	private int plotSquareFeet;
	@Column(name = "length")
	private int length;
	@Column(name = "width")
	private int width;
	@Column(name = "status")
	private String status;
	@Column(name = "isDelete")
	private boolean isDelete;
	private int sNo;
	
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
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
	public int getPlotSquareFeet() {
		return plotSquareFeet;
	}
	public void setPlotSquareFeet(int plotSquareFeet) {
		this.plotSquareFeet = plotSquareFeet;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

}
