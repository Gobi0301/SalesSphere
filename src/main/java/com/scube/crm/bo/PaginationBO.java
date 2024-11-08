/**
 * 
 */
package com.scube.crm.bo;

import java.util.List;

import javax.persistence.Transient;

import com.scube.crm.vo.User;

/**
 * @author User
 * 
 */
public class PaginationBO<T> extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1666336214095587613L;

	private List<T> list;
	private long totalRecords;
	private long records;
	private long start;
	private long page;
	private long listSize;
	private List<Long> noOfPages;
	private long lastRecordValue;
	private User user;
	
	@Transient
	private int sNo;

	/**
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
	/**
	 * @return the totalRecords
	 */
	public long getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * @return the records
	 */
	public long getRecords() {
		return records;
	}
	/**
	 * @param records the records to set
	 */
	public void setRecords(long records) {
		this.records = records;
	}
	/**
	 * @return the start
	 */
	public long getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}
	/**
	 * @return the page
	 */
	public long getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(long page) {
		this.page = page;
	}
	/**
	 * @return the listSize
	 */
	public long getListSize() {
		return listSize;
	}
	/**
	 * @param listSize the listSize to set
	 */
	public void setListSize(long listSize) {
		this.listSize = listSize;
	}
	/**
	 * @return the noOfPages
	 */
	public List<Long> getNoOfPages() {
		return noOfPages;
	}
	/**
	 * @param noOfPages the noOfPages to set
	 */
	public void setNoOfPages(List<Long> noOfPages) {
		this.noOfPages = noOfPages;
	}
	/**
	 * @return the lastRecordValue
	 */
	public long getLastRecordValue() {
		return lastRecordValue;
	}
	/**
	 * @param lastRecordValue the lastRecordValue to set
	 */
	public void setLastRecordValue(long lastRecordValue) {
		this.lastRecordValue = lastRecordValue;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
