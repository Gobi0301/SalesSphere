/**
 * 
 */
package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.AccessLogBO;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.CampaignBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.LoginStatusVO;


public interface CampaignService {

	AdminUserBO retrieveParticularUser(long loginId);

	CampaignBO saveCompaign(CampaignBO campaignBO);

	long getListOfCampaign(CampaignBO campaignBo);

	List<CampaignBO> listOfCampaigns(CampaignBO campaignBO);

	long campaignforObject(CampaignBO campaignBO);

	CampaignBO getObject(String campaignId);

	boolean updateCampaign(CampaignBO campaignBO);

	boolean deleteCampaign(String campaignId);

	CampaignBO viewCampaignDetails(CampaignBO campaignBO);





	AdminLoginBO authendicate(AdminLoginBO adminLoginBO) throws  MySalesException;

	boolean sendClientMail (OpportunityBO employerRegisterBO); 

	boolean  addLoginStatus(String username) throws MySalesException;

	public boolean createAccessLog(AccessLogBO logBO);

	boolean editLoginStatus(LoginStatusVO loginStatusVO);

	CampaignBO getCampaignObjectByName(CampaignBO campaignBO);

	CampaignBO saveTracking(CampaignBO campaignBO);

	long retriveCount();

	ActivityVO campaignTrackingStatus(long updatecampaignId);

	CampaignBO retriveCampaignById(CampaignBO campaignBO);

	List<CampaignBO> findAllProducts(CampaignBO campaignBo1);






}
