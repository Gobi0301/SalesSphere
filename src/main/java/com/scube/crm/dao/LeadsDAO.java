package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.CampaignBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.Campaign;
import com.scube.crm.vo.Leads;
import com.scube.crm.vo.LeadsFollowup;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.User;

/**
 * @author Administrator
 * @param <T>
 * 
 */
public interface LeadsDAO {
	
	long saveLeads(Leads leads);
	
	long getcountOfLeads(LeadsBO leadsVO);
	
	List<Leads> getListOfLeadsByPagination(Leads leadsVO);
	
	Leads getLeads(Leads leads);
	
	List<ActivityVO> retrieveTracking(long id);
	
	Leads getLeadsId(LeadsBO leadsBO);
	
	boolean deleteLeads(long leadsid);
	
	long countOfLeadsBySearch(Leads leadsVO);
	
	ActivityVO saveTracking(ActivityVO activityvo);
	
	
	
	

	User authendicate(String string, String emailAddress) throws MySalesException;

	public boolean createAccessLog(AccessLogVO logVO);

	

	List<Leads> getListLeads(LeadsBO leadsBO);

	List<Campaign> listOfCampaign(CampaignBO campaignBO);

	LoginStatusVO editLoginStatus(LoginStatusVO loginStatusVO);

	long addLoginStatus(LoginStatusVO loginStatus);

	

	

	

	boolean updateLead(Leads leads);

	boolean findByMobilenoLeads(String mobileNo);

	boolean findByEmailLeads(String emailAddress);

	List<AdminUserBO> retrieveUser() throws MySalesException;

	

	

	List<LeadsFollowup> searchRetrieveTracking(LeadsFollowup leadsFollowup);

	long getAnyAppointment(long leadsId);

	

	

	

	long countOfLeadsReportBySearch(Leads leadsVO);

	Boolean leadtoopprtunityconversationcheck(long id);

	boolean checkEmailAddress(String emailAddress, long companyId);

	List<Leads> viewLead(LeadsBO leadsBO);

	
	

}
