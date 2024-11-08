package com.scube.crm.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scube.crm.bo.BaseBO;
@Entity
@Table(name = "workitem")
public class WorkItemVO extends BasicEntity {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 3640609289106344222L;
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long workItemId;
		
		@Column(name="Work_Item_Code")
		private String workItemCode;
		
	
		public long getWorkItemId() {
			return workItemId;
		}
		public void setWorkItemId(long workItemId) {
			this.workItemId = workItemId;
		}
		public String getWorkItemCode() {
			return workItemCode;
		}
		public void setWorkItemCode(String workItemCode) {
			this.workItemCode = workItemCode;
		}
		public String getWorkItemType() {
			return workItemType;
		}
		public void setWorkItemType(String workItemType) {
			this.workItemType = workItemType;
		}
		public String getWorkItem() {
			return workItem;
		}
		public void setWorkItem(String workItem) {
			this.workItem = workItem;
		}
		@Column(name="Work_Item_Type")
		private String workItemType;
		
		@Column(name="Work_Item")
		private String workItem;
		
		@Column(name="is_Active")
		private boolean isActive;
		
		@Column(name="is_Delete")
		private boolean isDelete;
		
		private int sNo;
		
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
		
		
		
		
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public int getsNo() {
			return sNo;
		}
		public void setsNo(int sNo) {
			this.sNo = sNo;
		}
		
		

	}


