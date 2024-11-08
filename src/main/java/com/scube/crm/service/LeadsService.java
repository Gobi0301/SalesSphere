/**
 * 
 */
package com.scube.crm.service;

import java.util.List;
import java.util.Map;

import com.scube.crm.bo.AccessLogBO;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.LoginStatusVO;

/**
 * @author Administrator
 * 
 */
public interface LeadsService {
	
	long saveLeads(LeadsBO leadsBO);
	
	long getcountOfLeads(LeadsBO leadsBO);
	
	List<LeadsBO> getListOfLeadsByPagination(LeadsBO leadsBO);
	
	LeadsBO getLeads(LeadsBO leadsBO)throws MySalesException;
	
	boolean updateLeads(LeadsBO leadsBO) throws MySalesException;
	
	boolean deleteLeads(Integer leadsId)throws MySalesException;
	
	long countOfLeadsBySearch(LeadsBO leadsBO);
	
	LeadsBO saveTracking(LeadsBO bO);
	
	LeadsBO saveLeadsByExcelSheet(LeadsBO leadsBO1);
	
	
	
	
	
	

	AdminLoginBO authendicate(AdminLoginBO adminLoginBO) throws MySalesException;

	boolean sendClientMail(OpportunityBO employerRegisterBO);

	boolean addLoginStatus(String username) throws MySalesException;

	public boolean createAccessLog(AccessLogBO logBO);

	boolean editLoginStatus(LoginStatusVO loginStatusVO);

	//List<CampaignBO> listOfCampaign(CampaignBO campaignBO);

	List<LeadsBO> getListLeads(LeadsBO leadsBO) throws MySalesException;

	List<Map<?,?>> getLeadsForOpportunityConversion(Integer leadsId,long companyId);

	

	

	boolean findByMobilenoLeads(String mobileNo);

	boolean findByEmailLeads(String emailAddress);

	List<AdminUserBO> retrieveUser()throws MySalesException;

	

	

	List<LeadsBO> searchRetrieveTracking(LeadsBO listLeadsBO);

	long getAnyAppointment(long leadsId);

	

	

	

	long countOfLeadsReportBySearch(LeadsBO leadsBO);

	boolean convertOpportunity(LeadsBO leadsBO)throws MySalesException;

	boolean updateOpportunityConversionLeads(LeadsBO leadsBO);

	boolean checkEmailAddress(String emailAddress, long companyId);

	List<LeadsBO> getListLeadsDropDown(LeadsBO leadsBO);

	

	

	
	
	
}
