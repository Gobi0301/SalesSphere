package com.scube.crm.bo;


import com.scube.crm.vo.User;

/**
 * @author Administrator
 * 
 */
public class AdminLoginBO extends User  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4049514026397242479L;



	private boolean rememberMe;
	private boolean isActive;
	private long jobpostcount = 0;
	private long appliedjobcount = 0;
	private long activeJobCount = 0;
	private long inActiveJobCount = 0;
	private long savejobcount = 0;
	private long shortListCount = 0;
	private long unShortListCount = 0;
	private long jobseekerCount = 0;
	private long selectedResumeCount = 0;
	private long unSelectedResumeCount = 0;
	private long appliedcount = 0;
	private long employerCount = 0;
	private long deappliedcount = 0;
	private long totalappliedcount = 0;
	private long profilecount = 0;
	private long jobseekerActiveCount = 0;
	private long jobseekerInActiveCount = 0;
	private long employerActiveCount = 0;
	private long employerInActiveCount = 0;
	private String metaType;
	private String metaContent;
	private long metaId;
	private long metaSno;
	private boolean isMetaDelete;
	private String firstName;
	private String mobileNo;
	private long currentActionCustomers = 0;
	private long employeeCount;
	private long campaignCount;
	private long leadsCount;
	private long customerCount;
	private long productCount;
	private long contactCount;
	private long accountCount;
	private long opportunityCount;
	private long companyCount;
	private long salesCount;
   public long getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(long salesCount) {
		this.salesCount = salesCount;
	}

private String UserType;
	
	
	
	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
	}

	public long getOpportunityCount() {
		return opportunityCount;
	}

	public void setOpportunityCount(long opportunityCount) {
		this.opportunityCount = opportunityCount;
	}

	 public long getCompanyCount() {
			return companyCount;
		}

	public void setCompanyCount(long companyCount) {
			this.companyCount = companyCount;
		}
	public long getContactCount() {
		return contactCount;
	}

	public void setContactCount(long contactCount) {
		this.contactCount = contactCount;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the rememberMe
	 */
	public boolean getRememberMe() {
		return rememberMe;
	}

	/**
	 * @param rememberMe the rememberMe to set
	 */
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public long getJobseekerCount() {
		return jobseekerCount;
	}

	public void setJobseekerCount(long jobseekerCount) {
		this.jobseekerCount = jobseekerCount;
	}
	
	public long getJobpostcount() {
		return jobpostcount;
	}

	public void setJobpostcount(long jobpostcount) {
		this.jobpostcount = jobpostcount;
	}

	public long getAppliedjobcount() {
		return appliedjobcount;
	}

	public void setAppliedjobcount(long appliedjobcount) {
		this.appliedjobcount = appliedjobcount;
	}

	public long getActiveJobCount() {
		return activeJobCount;
	}

	public void setActiveJobCount(long activeJobCount) {
		this.activeJobCount = activeJobCount;
	}

	public long getInActiveJobCount() {
		return inActiveJobCount;
	}

	public void setInActiveJobCount(long inActiveJobCount) {
		this.inActiveJobCount = inActiveJobCount;
	}

	public long getSavejobcount() {
		return savejobcount;
	}

	public void setSavejobcount(long savejobcount) {
		this.savejobcount = savejobcount;
	}

	public long getShortListCount() {
		return shortListCount;
	}

	public void setShortListCount(long shortListCount) {
		this.shortListCount = shortListCount;
	}

	public long getUnShortListCount() {
		return unShortListCount;
	}

	public void setUnShortListCount(long unShortListCount) {
		this.unShortListCount = unShortListCount;
	}

	public long getSelectedResumeCount() {
		return selectedResumeCount;
	}

	public void setSelectedResumeCount(long selectedResumeCount) {
		this.selectedResumeCount = selectedResumeCount;
	}

	public long getUnSelectedResumeCount() {
		return unSelectedResumeCount;
	}

	public void setUnSelectedResumeCount(long unSelectedResumeCount) {
		this.unSelectedResumeCount = unSelectedResumeCount;
	}

	public long getAppliedcount() {
		return appliedcount;
	}

	public void setAppliedcount(long appliedcount) {
		this.appliedcount = appliedcount;
	}

	public long getDeappliedcount() {
		return deappliedcount;
	}

	public void setDeappliedcount(long deappliedcount) {
		this.deappliedcount = deappliedcount;
	}

	public long getTotalappliedcount() {
		return totalappliedcount;
	}

	public void setTotalappliedcount(long totalappliedcount) {
		this.totalappliedcount = totalappliedcount;
	}

	public long getProfilecount() {
		return profilecount;
	}

	public void setProfilecount(long profilecount) {
		this.profilecount = profilecount;
	}

	public long getJobseekerActiveCount() {
		return jobseekerActiveCount;
	}

	public void setJobseekerActiveCount(long jobseekerActiveCount) {
		this.jobseekerActiveCount = jobseekerActiveCount;
	}

	public long getJobseekerInActiveCount() {
		return jobseekerInActiveCount;
	}

	public void setJobseekerInActiveCount(long jobseekerInActiveCount) {
		this.jobseekerInActiveCount = jobseekerInActiveCount;
	}

	public long getEmployerActiveCount() {
		return employerActiveCount;
	}

	public void setEmployerActiveCount(long employerActiveCount) {
		this.employerActiveCount = employerActiveCount;
	}

	public long getEmployerInActiveCount() {
		return employerInActiveCount;
	}

	public void setEmployerInActiveCount(long employerInActiveCount) {
		this.employerInActiveCount = employerInActiveCount;
	}

	public long getEmployerCount() {
		return employerCount;
	}

	public void setEmployerCount(long employerCount) {
		this.employerCount = employerCount;
	}
	public String getMetaType() {
		return metaType;
	}

	public void setMetaType(String metaType) {
		this.metaType = metaType;
	}

	public String getMetaContent() {
		return metaContent;
	}

	public void setMetaContent(String metaContent) {
		this.metaContent = metaContent;
	}

	public long getMetaId() {
		return metaId;
	}

	public void setMetaId(long metaId) {
		this.metaId = metaId;
	}

	public long getMetaSno() {
		return metaSno;
	}

	public void setMetaSno(long metaSno) {
		this.metaSno = metaSno;
	}

	public boolean isMetaDelete() {
		return isMetaDelete;
	}

	public void setMetaDelete(boolean isMetaDelete) {
		this.isMetaDelete = isMetaDelete;
	}
	
	/**
	 * @return the employeeCount
	 */
	public long getEmployeeCount() {
		return employeeCount;
	}

	/**
	 * @param employeeCount the employeeCount to set
	 */
	public void setEmployeeCount(long employeeCount) {
		this.employeeCount = employeeCount;
	}

	/**
	 * @return the campaignCount
	 */
	public long getCampaignCount() {
		return campaignCount;
	}

	/**
	 * @param campaignCount the campaignCount to set
	 */
	public void setCampaignCount(long campaignCount) {
		this.campaignCount = campaignCount;
	}

	/**
	 * @return the leadsCount
	 */
	public long getLeadsCount() {
		return leadsCount;
	}

	/**
	 * @param leadsCount the leadsCount to set
	 */
	public void setLeadsCount(long leadsCount) {
		this.leadsCount = leadsCount;
	}

	/**
	 * @return the customerCount
	 */
	public long getCustomerCount() {
		return customerCount;
	}

	/**
	 * @param customerCount the customerCount to set
	 */
	public void setCustomerCount(long customerCount) {
		this.customerCount = customerCount;
	}

	public long getCurrentActionCustomers() {
		return currentActionCustomers;
	}

	public void setCurrentActionCustomers(long currentActionCustomers) {
		this.currentActionCustomers = currentActionCustomers;
	}

	public long getProductCount() {
		return productCount;
	}

	public void setProductCount(long productCount) {
		this.productCount = productCount;
	}

	public long getAccountCount() {
		return accountCount;
	}

	public void setAccountCount(long accountCount) {
		this.accountCount = accountCount;
	}
}
