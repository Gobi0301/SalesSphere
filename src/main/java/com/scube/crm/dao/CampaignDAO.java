package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.CampaignBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.Campaign;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.User;

public interface CampaignDAO {

	User retrieveParticularUser(User user);

	Campaign saveCompaign(Campaign campaign);

	long getListOfCompanyCampaign(Campaign campaignVo);

	List<Campaign> listOfCampaigns(Campaign campaignVo);

	long campaignforObject(Campaign campaignVo);

	Campaign getObject(int campaignId);

	Campaign getCampaignId(CampaignBO campaignBO);

	boolean updateCampaign(Campaign campaign);

	boolean deleteCampaign(int id);

	Campaign viewCampaignDetails(Campaign campaignVO);





	User authendicate(String string, String emailAddress) throws MySalesException;

	public boolean createAccessLog(AccessLogVO logVO);

	long addLoginStatus(LoginStatusVO loginStatus);

	LoginStatusVO editLoginStatus(LoginStatusVO loginStatusVO);

	Campaign getCampaignObjectByName(Campaign campaignVO);

	ActivityVO saveTracking(ActivityVO activityvo);

	List<ActivityVO> retrieveTracking(long campaignId, Long companyId);

	long retriveCount();

	ActivityVO campaignTrackingStatus(long updatecampaignId);

	Campaign retriveCampaignById(Campaign campaignVO);

	List<Campaign> findAllProducts(Campaign campaignVO);




}
