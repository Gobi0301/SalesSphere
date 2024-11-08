package com.scube.crm.bo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.scube.crm.vo.ActivityVO;



public class ProcurementBO extends BaseBO{
	
	private static final long serialVersionUID = 1L;
   
	    private long procurementId;
        @NotNull(message="minimumStock cannot be empty")
		private Integer minimumStock;
        @NotNull(message="maximumStock cannot be empty")
	    private Integer maximumStock;
        @NotNull(message="availableStock cannot be empty") 
	    private Integer availableStock;
        @NotNull(message="quantityOfProducts cannot be empty")
	    private String quantityOfProducts;
        @NotNull(message="unitOfCost cannot be empty")
	    private Integer unitOfCost;
        @NotNull(message="totalCost cannot be empty")
	    private Integer totalCost;
		private String expectedDate;
	    private String description;
	    private String reject;
        private boolean isDelete;
         
        private InventoryBO productServiceBO;
        private String createdDate;
        private String modifyDate;
        private String entityType;
     
        private long entityid;
        private long activityid;
         
        private String timeSlot;
        
        private String endTimeSlot;
        
        
        private List<ActivityVO> procurementactivityList;
		
		 
		 
		public String getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
		public String getModifyDate() {
			return modifyDate;
		}
		public void setModifyDate(String modifyDate) {
			this.modifyDate = modifyDate;
		}
		public String getEntityType() {
			return entityType;
		}
		public void setEntityType(String entityType) {
			this.entityType = entityType;
		}

		private SupplierBO supplierBO;
	    private AdminLoginBO adminLoginBO;
	    private ApproveProcurementBO approveBO;
	    private RejectProcurementBO rejectBO;
	    
	    
	    public RejectProcurementBO getRejectBO() {
			return rejectBO;
		}
		public void setRejectBO(RejectProcurementBO rejectBO) {
			this.rejectBO = rejectBO;
		}

		public boolean isDelete() {
			return isDelete;
		}
		public void setDelete(boolean isDelete) {
			this.isDelete = isDelete;
		}
	    public ApproveProcurementBO getApproveBO() {
			return approveBO;
		}
		public void setApproveBO(ApproveProcurementBO approveBO) {
			this.approveBO = approveBO;
		}
	    public AdminLoginBO getAdminLoginBO() {
 
			return adminLoginBO;
		}
		public String getReject() {
			return reject;
		}

		public void setReject(String reject) {
			this.reject = reject;
		}
	    public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		public String getExpectedDate() {
			return expectedDate;
		}

		public void setExpectedDate(String expectedDate) {
			this.expectedDate = expectedDate;
		}
	   public InventoryBO getProductServiceBO() {
			return productServiceBO;
		}

		public void setProductServiceBO(InventoryBO productServiceBO) {
			this.productServiceBO = productServiceBO;
		}
	    public long getProcurementId() {
			return procurementId;
		}

		public void setProcurementId(long procurementId) {
			this.procurementId = procurementId;
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

		public SupplierBO getSupplierBO() {
			return supplierBO;
		}

		public void setSupplierBO(SupplierBO supplierBO) {
			this.supplierBO = supplierBO;
		}

		public void setAdminLoginBO(AdminLoginBO adminLoginBO) {
			// TODO Auto-generated method stub
			
		}
		public long getEntityid() {
			return entityid;
		}
		public void setEntityid(long entityid) {
			this.entityid = entityid;
		}
		public long getActivityid() {
			return activityid;
		}
		public void setActivityid(long activityid) {
			this.activityid = activityid;
		}
		 
		public List<ActivityVO> getProcurementactivityList() {
			return procurementactivityList;
		}
		public void setProcurementactivityList(List<ActivityVO> procurementactivityList) {
			this.procurementactivityList = procurementactivityList;
		}
		public String getTimeSlot() {
			return timeSlot;
		}
		public void setTimeSlot(String timeSlot) {
			this.timeSlot = timeSlot;
		}
		public String getEndTimeSlot() {
			return endTimeSlot;
		}
		public void setEndTimeSlot(String endTimeSlot) {
			this.endTimeSlot = endTimeSlot;
		}
 
		 

		 
		}