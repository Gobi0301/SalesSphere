package com.scube.crm.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AccessLogBO;
import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.CampaignBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.bo.TaskManagementBO;
import com.scube.crm.dao.ActivityHistoryDAO;
import com.scube.crm.dao.AdminDAO;
import com.scube.crm.dao.CampaignDAO;
import com.scube.crm.dao.TaskManagementDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.model.EmailModel;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.utils.SendEmailServiceImpl;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.Campaign;
import com.scube.crm.vo.EmailAccess;
import com.scube.crm.vo.HistoryVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.TaskManagementVO;
import com.scube.crm.vo.User;


@Service("campaignService")
@Transactional
public class CampaignServiceImpl extends ControllerUtils implements CampaignService  {

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(CampaignServiceImpl.class);

	@Autowired
	private AdminDAO adminDAO;

	static boolean isApproval = true;
	EmailModel model = new EmailModel();

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SendEmailServiceImpl emailManager;

	@Autowired
	private CampaignDAO campaignDAO;
	
	@Autowired
	private ActivityHistoryDAO activityhistoryDAO;
	
	@Autowired
	TaskManagementDao taskDao;


	public AdminUserBO retrieveParticularUser(long id)
	{
		User user=new User();
		user.setId(id);
		user= campaignDAO.retrieveParticularUser(user);
		AdminUserBO adminUserBO=new AdminUserBO();
		if(null!=user&&null!=user.getName()) {
			adminUserBO.setName(user.getName());
			adminUserBO.setId(user.getId());

		}
		return adminUserBO;
	}

	// Campaign create Part
	@Override
	public CampaignBO saveCompaign(CampaignBO campaignBO) {
		LOGGER.entry();
		try {
			Campaign campaign = new Campaign();
			campaign.setCampaignName(campaignBO.getCampaignName());
			campaign.setStartedTime(campaignBO.getStartedTime());
			campaign.setEndTime(campaignBO.getEndTime());
			campaign.setCampaignMode(campaignBO.getCampaignMode());
			campaign.setStatus(campaignBO.getStatus());
			campaign.setExpectedRevenue(campaignBO.getExpectedRevenue());
			campaign.setExpectedResponse(campaignBO.getExpectedResponse());
			campaign.setBudgetedCost(campaignBO.getBudgetedCost());
			campaign.setNumberSent(campaignBO.getNumberSent());
			campaign.setDescription(campaignBO.getDescription());
			campaign.setIsDelete(campaignBO.getisDelete());
			campaign.setCampaignOwner(campaignBO.getCampaignOwner());
			campaign.setCompanyId(campaignBO.getCompanyId()); // Companyid
			campaign.setCreatedBy(campaignBO.getCreatedBy()); // LoginId added
			// campaign owner dropdown
			User user = new User();
			if (null != campaignBO.getAdminLoginBO()) {
				user.setId(campaignBO.getUserId());
				campaign.setUser(user);
			}
			//product dropdown
			if (null!=campaignBO && null != campaignBO.getProductServiceBO() && !campaignBO.getProductServiceBO().getServiceName().isEmpty()) {
				String productIds= campaignBO.getProductServiceBO().getServiceName();
				long id = 0;
				if (null != productIds && !productIds.isEmpty()) {
					id = Long.parseLong(productIds);
				}
				InventoryVO productServiceVO = new InventoryVO();
				productServiceVO.setServiceId(id);
				campaign.setProductServiceVO(productServiceVO);
			}

			Campaign campaigns =campaignDAO.saveCompaign(campaign);
			if (0 < campaign.getCampaignId()) {
				campaignBO.setCampaignId(campaigns.getCampaignId());
			}
			
			//Activity log history create part dao call........
			if(null!=campaigns) {
		        HistoryVO historyvo = new HistoryVO();
		        historyvo.setEntityId(campaigns.getCampaignId());
		        historyvo.setEntityType("Campaign");
		        historyvo.setActionType("Create");
		        //for getting login id...
				long loginId=getUserSecurity().getLoginId();
				historyvo.setUser(loginId);
				//for getting company id..
		        historyvo.setCompanyId(campaignBO.getCompanyId());
			  activityhistoryDAO.activityLogHistory(historyvo);
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add Campaign has failed:" + ex.getMessage());
			}
			LOGGER.info("Add Campaign has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}

		return campaignBO;
	}


	// record counting purpose - Campaign View part
	@Override
	public long getListOfCampaign(CampaignBO campaignBO) {
		Campaign campaignVo=new Campaign();
		return campaignDAO.getListOfCompanyCampaign(campaignVo);
	}


	// Total campaign list purpose call - Campaign View part & Search part
	@Override
	public List<CampaignBO> listOfCampaigns(CampaignBO campaignBO) {
		List<CampaignBO> pagecampaignlistBO = new ArrayList<CampaignBO>();
		Campaign campaignVo=new Campaign();
		LOGGER.entry();
		try {
			campaignVo.setIsDelete(campaignBO.getisDelete());
			campaignVo.setMaxRecord(campaignBO.getMaxRecord());
			campaignVo.setRecordIndex(campaignBO.getRecordIndex());
			campaignVo.setCreatedBy(campaignBO.getCreatedBy());  // LoginId based
			campaignVo.setCompanyId(campaignBO.getCompanyId());   // CompanyId added
			campaignVo.setCampaignName(campaignBO.getCampaignName());
			
			if(null!=campaignBO) {
				if(null!=campaignBO && null!=campaignBO.getCampaignName()) {
					campaignVo.setCampaignName(campaignBO.getCampaignName());
				}
				if(null!=campaignBO.getCampaignMode()) {
					campaignVo.setCampaignMode(campaignBO.getCampaignMode());
				}
				if(null!=campaignBO.getProductServiceBO()){
					InventoryVO services =new InventoryVO();
					services.setServiceId(campaignBO.getProductServiceBO().getServiceId());
					campaignVo.setProductServiceVO(services);
				}

				if(null!=campaignBO.getAdminLoginBO()) {
					User user=new User();
					user.setId(campaignBO.getAdminLoginBO().getId());
					campaignVo.setUser(user);
				}
				
			}
			List<Campaign> pagecampaignlist = campaignDAO.listOfCampaigns(campaignVo);
			if(null!=pagecampaignlist && pagecampaignlist.size()>0 ) {

				int sNo=campaignBO.getRecordIndex();
				for (Campaign campaignVO : pagecampaignlist) {
					List<CampaignBO> campignbo = new ArrayList<CampaignBO>();
					CampaignBO campaignBo = new CampaignBO();
					campaignBo.setCampaignName(campaignVO.getCampaignName());
					campaignBo.setCampaignMode(campaignVO.getCampaignMode());
					campaignBo.setCampaignId(campaignVO.getCampaignId());
					campaignBo.setsNo(++sNo);

					if (null != campaignVO.getProductServiceVO()) {
						InventoryBO productServiceBO=new InventoryBO(); 
						productServiceBO.setServiceName(campaignVO.getProductServiceVO().getServiceName());
						productServiceBO.setServiceId(campaignVO.getProductServiceVO().getServiceId());
						campaignBo.setProductServiceBO(productServiceBO);
					}
					campignbo.add(campaignBo);
					pagecampaignlistBO.addAll(campignbo);
				}
			}
			
	}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("List of campaigns has failed:" + ex.getMessage());
			}
			LOGGER.info("List of campaigns has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return pagecampaignlistBO;
	}

	// Campaign Search Part - count 
	@Override
	public long campaignforObject(CampaignBO campaignBO) {
		Campaign campaignVo=new Campaign();
		
		//if(null!=campaignBO.getCampaignName()&&!campaignBO.getCampaignName().isEmpty()) 
		if (null!=campaignBO){
			campaignVo.setCompanyId(campaignBO.getCompanyId());  // companyId
			if(StringUtils.isNotEmpty(campaignBO.getCampaignName())) {
				campaignVo.setCampaignName(campaignBO.getCampaignName());
			}
			if(null!=campaignBO.getProductServiceBO()){
				InventoryVO productServiceVO=new InventoryVO();
				if(null!=campaignBO.getProductServiceBO() && 
						0<campaignBO.getProductServiceBO().getServiceId()){
					long productId=campaignBO.getProductServiceBO().getServiceId();
					productServiceVO.setServiceId(productId);
				} 
				campaignVo.setProductServiceVO(productServiceVO);
			}
			if(StringUtils.isNotEmpty(campaignBO.getCampaignMode())){
				campaignVo.setCampaignMode(campaignBO.getCampaignMode());
			}
		}
		return campaignDAO.campaignforObject(campaignVo);
	}

	@Override
	public CampaignBO getObject(String campaignId) {

		int id = 0;
		Campaign campaignVO = new Campaign();
		CampaignBO campaignBO = new CampaignBO();
		if (null != campaignId) {
			id = Integer.parseInt(campaignId);
		}
		campaignVO = campaignDAO.getObject(id);
		if (null != campaignVO) {
			campaignBO.setCampaignName(campaignVO.getCampaignName());
			campaignBO.setStartedTime(campaignVO.getStartedTime());
			campaignBO.setEndTime(campaignVO.getEndTime());
			campaignBO.setCampaignMode(campaignVO.getCampaignMode());
			campaignBO.setCampaignId(campaignVO.getCampaignId());
			campaignBO.setCampaignOwner(campaignVO.getCampaignOwner());
			campaignBO.setBudgetedCost(campaignVO.getBudgetedCost());
			campaignBO.setStatus(campaignVO.getStatus());
			campaignBO.setDescription(campaignVO.getDescription());
			campaignBO.setExpectedResponse(campaignVO.getExpectedResponse());
			campaignBO.setExpectedRevenue(campaignVO.getExpectedRevenue());
			campaignBO.setNumberSent(campaignVO.getNumberSent());
			// campaign owner
			if (null != campaignVO.getUser()) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(campaignVO.getUser().getId());
				adminLoginBO.setFirstName(campaignVO.getUser().getName());
				campaignBO.setUserId(campaignVO.getUser().getId());
				campaignBO.setUserName(campaignVO.getUser().getName());
				campaignBO.setAdminLoginBO(adminLoginBO);
			}
			// service - products 
			if (null != campaignVO.getProductServiceVO()) {
				InventoryBO productServiceBO=new InventoryBO();
				productServiceBO.setServiceId(campaignVO.getProductServiceVO().getServiceId());
				productServiceBO.setServiceName(campaignVO.getProductServiceVO().getServiceName());
				campaignBO.setProductServiceBO(productServiceBO);
			}
		}
		return campaignBO;
	}

	// Campaign Edit - Update part
	@Override
	public boolean updateCampaign(CampaignBO campaignBO) {

		Campaign campaign = new Campaign();
		User user = new User();
		campaign = campaignDAO.getCampaignId(campaignBO);
		if (0 != campaignBO.getCampaignId()) {
			campaign.setCampaignName(campaignBO.getCampaignName());
			campaign.setStartedTime(campaignBO.getStartedTime());
			campaign.setEndTime(campaignBO.getEndTime());
			campaign.setCampaignMode(campaignBO.getCampaignMode());
			campaign.setBudgetedCost(campaignBO.getBudgetedCost());
			campaign.setStatus(campaignBO.getStatus());
			campaign.setDescription(campaignBO.getDescription());
			campaign.setExpectedResponse(campaignBO.getExpectedResponse());
			campaign.setExpectedRevenue(campaignBO.getExpectedRevenue());
			campaign.setNumberSent(campaignBO.getNumberSent());
			campaign.setCampaignOwner(campaignBO.getCampaignOwner());
			campaign.setIsDelete(false);
			campaign.setCompanyId(campaignBO.getCompanyId());		// company
			campaign.setModified(getDateWithoutTime(new Date()));
			campaign.setModifiedBy(campaignBO.getModifiedBy());
			// campaign owners 
			if (null != campaignBO.getAdminLoginBO()) {
				user.setId(campaignBO.getAdminLoginBO().getId());
				campaign.setUser(user);
			}
			// service - products 
			if (null != campaignBO.getProductServiceBO() && 0<campaignBO.getProductServiceBO().getServiceId()) {
				InventoryVO productServiceVO = new InventoryVO();
				productServiceVO.setServiceId(campaignBO.getProductServiceBO().getServiceId());
				campaign.setProductServiceVO(productServiceVO);
			}
		}
		boolean status = campaignDAO.updateCampaign(campaign);
		
		//Activity History update Part (or) Audit log History...
		if(status = true) {
		HistoryVO historyvo = new HistoryVO();
		historyvo.setEntityId(campaignBO.getCampaignId());
		historyvo.setEntityType("Campaign");
		historyvo.setActionType("Update");
		//for getting login id...
		long loginId=getUserSecurity().getLoginId();
		historyvo.setUser(loginId);
		//for getting company id..
		historyvo.setCompanyId(campaignBO.getCompanyId());
		 activityhistoryDAO.activityLogHistory(historyvo);
		}
		return status;
	}

	// Campaign Delete Part
	@Override
	public boolean deleteCampaign(String campaignId) {
		int id = 0;
		if (null != campaignId) {
			id = Integer.parseInt(campaignId);
		}
		boolean status = campaignDAO.deleteCampaign(id);
		if(status=true) {
			HistoryVO historyvo = new HistoryVO();
			historyvo.setEntityId(id);
			historyvo.setEntityType("Campaign");
			historyvo.setActionType("Delete");
			//for getting login id..
			long loginId=getUserSecurity().getLoginId();
			historyvo.setUser(loginId);
			long CompanyId=getUserSecurity().getCompanyId();
			//for getting company id..
			historyvo.setCompanyId(CompanyId);
			activityhistoryDAO.activityLogHistory(historyvo);
		}
		return status;
	}
	// Campaign view details
	@Override
	public CampaignBO viewCampaignDetails(CampaignBO campaignBO) {
		Campaign campaignVO=new Campaign();
		campaignVO.setCampaignId(campaignBO.getCampaignId());
		campaignVO=campaignDAO.viewCampaignDetails(campaignVO);
		if(campaignVO!=null) {
			campaignBO.setCampaignId(campaignVO.getCampaignId());
			//campaignBO.setEntityid((int) campaignBO.getCampaignId());
			campaignBO.setCampaignName(campaignVO.getCampaignName());
			campaignBO.setStartedTime(campaignVO.getStartedTime());
			campaignBO.setEndTime(campaignVO.getEndTime());
			campaignBO.setCampaignMode(campaignVO.getCampaignMode());
			campaignBO.setCampaignOwner(campaignVO.getCampaignOwner());
			campaignBO.setBudgetedCost(campaignVO.getBudgetedCost());
			campaignBO.setStatus(campaignVO.getStatus());
			campaignBO.setDescription(campaignVO.getDescription());
			campaignBO.setExpectedResponse(campaignVO.getExpectedResponse());
			campaignBO.setExpectedRevenue(campaignVO.getExpectedRevenue());
			campaignBO.setNumberSent(campaignVO.getNumberSent());
			// campaign owner
			if (null != campaignVO.getUser()) {
				AdminLoginBO adminLoginBO = new AdminLoginBO();
				adminLoginBO.setId(campaignVO.getUser().getId());
				adminLoginBO.setFirstName(campaignVO.getUser().getName());
				campaignBO.setUserId(campaignVO.getUser().getId());
				campaignBO.setUserName(campaignVO.getUser().getName());
				campaignBO.setAdminLoginBO(adminLoginBO);
			}
			// service - products 
			if (null != campaignVO.getProductServiceVO()) {
				InventoryBO productServiceBO=new InventoryBO();
				productServiceBO.setServiceId(campaignVO.getProductServiceVO().getServiceId());
				productServiceBO.setServiceName(campaignVO.getProductServiceVO().getServiceName());
				campaignBO.setProductServiceBO(productServiceBO);
			}

			if(null!=campaignBO) {
				//retrieve campaign tracking details
				List<ActivityVO> campaignactivityList= campaignDAO.retrieveTracking(campaignBO.getCampaignId(),campaignBO.getCompanyId());
				campaignBO.setCampaignactivityList(campaignactivityList);
				
				System.out.println(".............TASK...........");
				List<TaskManagementBO> taskBOList = new ArrayList<TaskManagementBO>();
				TaskManagementVO taskVO = new TaskManagementVO();
				taskVO.setEntityId(campaignVO.getCampaignId());
				taskVO.setCompanyId(campaignVO.getCompanyId());
				taskVO.setEntityName("campaign");
				
				List<TaskManagementVO> taskmanagelist = taskDao.findAll(taskVO);

		//		Leads lead=new Leads();
				for (TaskManagementVO taskManagementVO : taskmanagelist) {
					TaskManagementBO taskBO = new TaskManagementBO();
					taskBO.setEntityId(taskManagementVO.getEntityId());
					taskBO.setEntityName(taskManagementVO.getEntityName());
					taskBO.setLeadName(taskManagementVO.getEntityName());
					
					AdminUserBO adminUser = new AdminUserBO();
					adminUser.setName(taskManagementVO.getTaskOwner().getName());
					taskBO.setAdminUserBO(adminUser);
					
					taskBO.setStatus(taskManagementVO.getStatus());
					taskBO.setPriority(taskManagementVO.getPriority());
					taskBO.setId(taskManagementVO.getTaskId());
					taskBO.setRelatedTo(taskManagementVO.getRelatedTo());
					taskBO.setDescription(taskManagementVO.getDescription());
				    taskBO.setWorkitem((taskManagementVO.getWorkItemVO().getWorkItem()));
				    taskBO.setDuedate(taskManagementVO.getDueDate());
		//		    taskBO.setDate(taskManagementVO.getDate());
					//taskBO.setWorkitem(taskManagementVO.getWorkItemVO().getWorkItemType());
					taskBOList.add(taskBO);
				}
				campaignBO.setTaskManagementBOList(taskBOList);
				
			}
			// Activity history dao call(or) Audit log History...
			if(null!=campaignVO) {
				HistoryVO historyvo= new HistoryVO();
				historyvo.setEntityId(campaignBO.getCampaignId());
				historyvo.setEntityType("Campaign");
				historyvo.setActionType("View");
				//for getting login id..
				long loginId=getUserSecurity().getLoginId();
				historyvo.setUser(loginId);
				long CompanyId=getUserSecurity().getCompanyId();
				//for getting company id..
				historyvo.setCompanyId(CompanyId);
				 activityhistoryDAO.activityLogHistory(historyvo);
			
			}
		}
		return campaignBO;
	}


	@Override
	public AdminLoginBO authendicate(AdminLoginBO adminLoginBO) throws MySalesException {
		CampaignServiceImpl.LOGGER.entry();

		final AdminLoginBO adminLogin = new AdminLoginBO();
		try {
			final User user = this.adminDAO.authendicate("emailAddress", adminLoginBO.getEmailAddress());

			/*
			 * if (null != adminLoginVO) { final String password =
			 * EncryptAndDecrypt.decrypt( adminLoginVO.getPassword(),
			 * EncryptAndDecrypt.TOKEN);
			 */
			if (adminLoginBO.getPassword().equals(user.getPassword())) {
				if (user.isActive()) {
					BeanUtils.copyProperties(user, adminLogin);
					String userName = adminLoginBO.getEmailAddress();
					addLoginStatus(userName);
					adminLogin.setActive(true);
				} else {
					adminLogin.setActive(false);
				}
			} else {
				adminLogin.setActive(false);
			}
			// adminLogin.setPassword(password);

			/* */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return adminLogin;
	}

	@Override
	public boolean editLoginStatus(LoginStatusVO loginStatusVO) {
		adminDAO.editLoginStatus(loginStatusVO);
		return false;

	}

	@Override
	public boolean addLoginStatus(String username) throws MySalesException {

		LoginStatusVO loginStatus = new LoginStatusVO();
		loginStatus.setUserName(username);
		loginStatus.setType("Admin");
		loginStatus.setStatus(true);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Date date = cal.getTime();
		loginStatus.setStartTime(date);
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.MONTH, 1);
		Date date1 = cal.getTime();
		loginStatus.setEndTime(date1);
		loginStatus.setActivity("login");
		adminDAO.addLoginStatus(loginStatus);

		return false;
	}

	@Override
	public boolean createAccessLog(AccessLogBO logBO) {
		boolean accessStatus = false;
		try {
			AccessLogVO logVO = new AccessLogVO();
			logVO.setAccessId(logBO.getAccessId());
			logVO.setAccessDate(logBO.getAccessDate());
			logVO.setClientIP(logBO.getClientIP());
			logVO.setSessionId(logBO.getSessionId());
			accessStatus = adminDAO.createAccessLog(logVO);
		} catch (Exception e) {

		}
		return accessStatus;
	}

	@Override
	public boolean sendClientMail(OpportunityBO employerRegisterBO) {
		boolean alertStatus = false;
		try {
			EmailModel model = new EmailModel();

			final String[] toaddress = employerRegisterBO.getEmailAddress().split(",");
			final String subject = messageSource.getMessage("Validate.RegisterConfirm", null, null);
			for (int i = 0; i < toaddress.length; i++) {
				String bodycontent = "employerVerificationMail";
				model.setUrl("www.myjobkart.com/find-jobs.html?companyId=");
				model.setFirstname(employerRegisterBO.getFirstName());
				model.setEmail(employerRegisterBO.getEmailAddress());
				alertStatus = emailManager.sendEmail(toaddress[i], subject, bodycontent, model);
			}
			if (alertStatus) {
				EmailAccess accessVO = new EmailAccess();
				accessVO.setDate(new Date());
				accessVO.setEmailAddress(employerRegisterBO.getEmailAddress());
				//accessVO.setMailedBy(employerRegisterBO.getEmployerId());
				accessVO.setStatus(alertStatus);
				accessVO.setMailTO(employerRegisterBO.getId());
				List<EmailAccess> accessVOList = new ArrayList<EmailAccess>();
				accessVOList.add(accessVO);

				if (null != accessVOList || accessVOList.size() > 0) {
					adminDAO.saveEmailList(accessVOList);
				}
			}

		} catch (final Exception ex) {
		}

		return alertStatus;
	}


	public static Date getDateWithoutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	@Override
	public CampaignBO getCampaignObjectByName(CampaignBO campaignBO) {

		Campaign campaignVO=new Campaign();

		if(null!=campaignBO.getCampaignName()) {
			campaignVO.setCampaignName(campaignBO.getCampaignName());
		}

		campaignVO=campaignDAO.getCampaignObjectByName(campaignVO);
		if(null!=campaignVO) {
			campaignBO.setCampaignId(campaignVO.getCampaignId());
			campaignBO.setCampaignName(campaignVO.getCampaignName());
		}

		return campaignBO;
	}
	//Campaign - tracking status part
	@Override
	public CampaignBO saveTracking(CampaignBO campaignBO) {
		
		ActivityVO activityvo = new ActivityVO();
		
		if(null !=campaignBO.getDescription() && !campaignBO.getDescription().isEmpty()){
			activityvo.setDescription(campaignBO.getDescription());
		}
		if(null !=campaignBO.getDate()){
			activityvo.setDate(campaignBO.getDate());
		}
		if(null !=campaignBO.getTimeSlot()){
			activityvo.setTimeSlot(campaignBO.getTimeSlot());
		}
		if(null !=campaignBO.getEndTimeSlot()){
			activityvo.setEndTimeSlot(campaignBO.getEndTimeSlot());
		}

		if(null!=campaignBO.getUploadfile()){
			activityvo.setUploadfile(campaignBO.getUploadfile());
			
		}
		if(0<campaignBO.getCampaignId()){
			activityvo.setEntityid(campaignBO.getCampaignId());
			
		}
		if(0<campaignBO.getCompanyId()){
			activityvo.setCompanyId(campaignBO.getCompanyId());
			
		}
		activityvo.setEntitytype("Campaign");
		
		activityvo=campaignDAO.saveTracking(activityvo);
		if(activityvo.getActivityid()>0){
			
			campaignBO.setActivityid(activityvo.getEntityid());
		}else{
			campaignBO = null;
		}
		return campaignBO;	
	}

	@Override
	public long retriveCount() {
		// TODO Auto-generated method stub
		return campaignDAO.retriveCount();
	}

	@Override
	public ActivityVO campaignTrackingStatus(long updatecampaignId) {
		ActivityVO activityVO = campaignDAO.campaignTrackingStatus(updatecampaignId);
		if(null!=activityVO) {
			
			return activityVO;
		}
		return null;
	}

	@Override
	public CampaignBO retriveCampaignById(CampaignBO campaignBO) {
		Campaign campaignVO = new Campaign();
		if (0 < campaignBO.getCampaignId()) {
			campaignVO.setCampaignId(campaignBO.getCampaignId());
		}
		campaignVO.setCompanyId(campaignBO.getCompanyId()); // companyId
		campaignVO = campaignDAO.retriveCampaignById(campaignVO);
		if (null != campaignVO) {
			campaignBO.setCampaignId(campaignVO.getCampaignId());
			campaignBO.setCampaignName(campaignVO.getCampaignName());
			
			
			/*
			 * productBO.setFees(productVO.getFees());
			 * productBO.setStartDate(productVO.getStartDate());
			 * productBO.setEndDate(productVO.getEndDate());
			 * productBO.setCreated(productVO.getCreated());
			 * productBO.setCreatedBy(productVO.getCreatedBy());
			 * productBO.setModified(productVO.getModified());
			 * productBO.setModifiedBy(productVO.getModifiedBy());
			 * 
			 * ProductTypesBO productTypesbO = new ProductTypesBO();
			 * productTypesbO.setProductTypes(productVO.getProductTypesvO().getProductTypes(
			 * )); productBO.setProductTypesbO(productTypesbO);
			 */

		}
		return campaignBO;
	}

	@Override
	public List<CampaignBO> findAllProducts(CampaignBO campaignBo1) {
		List<CampaignBO> campaignBOs = new ArrayList<CampaignBO>();
		List<Campaign> campaignVOs = new ArrayList<Campaign>();
		Campaign campaignVO = new Campaign();
		try {
			if(null!= campaignBo1.getCompanyId() && 0< campaignBo1.getCompanyId()) {
				campaignVO.setCompanyId(campaignBo1.getCompanyId());
			}
			campaignVOs = campaignDAO.findAllProducts(campaignVO);
			if(null!= campaignVOs && !campaignVOs.isEmpty()) {
				
				for(Campaign vo : campaignVOs) {
					CampaignBO bo = new CampaignBO();
					
					String serviceName = vo.getProductServiceVO().getServiceName();
					long serviceId = vo.getProductServiceVO().getServiceId();
					InventoryBO productBO = new InventoryBO();
					productBO.setServiceName(serviceName);
					productBO.setServiceId(serviceId);
					bo.setProductServiceBO(productBO);
					
					campaignBOs.add(bo);	
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return campaignBOs;
	}


}
