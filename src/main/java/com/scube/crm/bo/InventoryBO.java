package com.scube.crm.bo;

public class InventoryBO extends BaseBO {

		private static final long serialVersionUID = 1L;
		private long serviceId;
	/*
	 * @NotEmpty(message = "ServiceName  may not be empty")
	 * 
	 * @Pattern(regexp = "^[a-zA-Z0-9-\\.\\s]*$", message =
	 * "Enter the serviceName valid format")
	 */
	private String serviceName;
	private double fees;
	private int rupees;
	/*
	 * @NotNull
	 * 
	 * @DateTimeFormat(pattern = "MM/dd/yyyy")
	 */
	private String startDate;
	/*
	 * @NotNull
	 * 
	 * @DateTimeFormat(pattern = "MM/dd/yyyy")
	 */
	private String endDate;
	/*
	 * @NotNull(message = " ServiceSpecification  may not be empty")
	 * 
	 * @Pattern(regexp = "^[a-zA-Z0-9-\\.\\s]*$", message =
	 * "Enter the ServiceSpecification valid format")
	 */
	private String serviceSpecification;
	private int sNo;
	private String beginDate;
	// @NotNull(message = " minimumStocks may not be empty")
	private Long minimumStocks;
	//@NotNull(message = " maximumStocks  may not be empty")
	private Long maximumStocks;
	//@NotNull(message = " availableStocks  may not be empty")
	private Long availableStocks;
	private String lastDate;
	private AdminLoginBO adminLoginBO;

	private ProductTypesBO productTypesbO;

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public int getRupees() {
		return rupees;
	}

	public void setRupees(int rupees) {
		this.rupees = rupees;
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

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

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
		return serviceSpecification;
	}

	public void setServiceSpecification(String serviceSpecification) {
		this.serviceSpecification = serviceSpecification;
	}

	public int getsNo() {
		return sNo;
	}

	public void setsNo(int sNo) {
		this.sNo = sNo;
	}

	public AdminLoginBO getAdminLoginBO() {
		return adminLoginBO;
	}

	public void setAdminLoginBO(AdminLoginBO adminLoginBO) {
		this.adminLoginBO = adminLoginBO;
	}

	public Long getMinimumStocks() {
		return minimumStocks;
	}

	public void setMinimumStocks(Long minimumStocks) {
		this.minimumStocks = minimumStocks;
	}

	public ProductTypesBO getProductTypesbO() {
		return productTypesbO;
	}

	public void setProductTypesbO(ProductTypesBO productTypesbO) {
		this.productTypesbO = productTypesbO;
	}

}
