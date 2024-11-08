package com.scube.crm.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AccessLogBO;
import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.CampaignBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.WorkItemSLABO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.bo.ProjectBO;
import com.scube.crm.bo.TaskManagementBO;
import com.scube.crm.bo.WorkItemBO;
import com.scube.crm.dao.ActivityHistoryDAO;
import com.scube.crm.dao.AdminDAO;
import com.scube.crm.dao.LeadsDAO;
import com.scube.crm.dao.Manage_WI_SLA_Dao;
import com.scube.crm.dao.OpportunityDAO;
import com.scube.crm.dao.ProductServiceDao;
import com.scube.crm.dao.TaskManagementDao;
import com.scube.crm.dao.WorkItemDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.model.EmailModel;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.Campaign;
import com.scube.crm.vo.HistoryVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.Leads;
import com.scube.crm.vo.LeadsFollowup;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.WorkItemSLAVO;
import com.scube.crm.vo.Opportunity;
import com.scube.crm.vo.ProjectVO;
import com.scube.crm.vo.SkillsVO;
import com.scube.crm.vo.SlaVO;
import com.scube.crm.vo.TaskManagementVO;
import com.scube.crm.vo.User;
import com.scube.crm.vo.WorkItemVO;

@Service("leadsService")
@Transactional
public class LeadsServiceImpl extends ControllerUtils implements LeadsService {

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(LeadsServiceImpl.class);
	// DAo layer annotations
	@Autowired
	private LeadsDAO leadsDAO;

	@Autowired
	TaskManagementService taskManagementService;

	@Autowired
	WorkItemDao workItemDao;

	@Autowired
	TaskManagementDao taskDao;

	@Autowired
	private AdminDAO adminDAO;

	@Autowired
	private ProductServiceDao productServiceDao;

	@Autowired
	private OpportunityDAO opportunityDAO;

	@Autowired
	private ActivityHistoryDAO activityhistoryDAO;

	@Autowired
	WorkItemSLAService workItemSLAService;

	@Autowired
	Manage_WI_SLA_Dao manage_WI_SLA_Dao;

	static boolean isApproval = true;
	EmailModel model = new EmailModel();

	// Leads create part
	@Override
	public long saveLeads(LeadsBO leadsBO) {
		User user = new User();
		Leads leads = new Leads();
		long id2 = 0;
		long userId = 0;
		long serviceId = 0;
		long projectId = 0;
		Date currentdate = null;
		String leadName = null;
		String projectName = null;
		LOGGER.entry();
		try {
			if (null != leadsBO) {
				projectName = leadsBO.getServiceName();
				leadName = leadsBO.getFirstName();
				leads.setFirstName(leadsBO.getFirstName());
				leads.setLastName(leadsBO.getLastName());
				leads.setEmailAddress(leadsBO.getEmailAddress());

				if (null != leadsBO && !leadsBO.getCompanyName().isEmpty()) {
					leads.setCompanyName(leadsBO.getCompanyName());
				} else {
					leads.setCompanyName("N/A");
				}
				if (null != leadsBO.getIndustryType() && !leadsBO.getIndustryType().isEmpty()) {
					leads.setIndustryType(leadsBO.getIndustryType());
				} else {
					leads.setIndustryType("N/A");
				}

				if (null != leadsBO.getWebsite() && !leadsBO.getWebsite().isEmpty()) {
					leads.setWebsite(leadsBO.getWebsite());
				} else {
					leads.setWebsite("N/A");
				}
				leads.setMobileNo(leadsBO.getMobileNo());
				leads.setContactNo(leadsBO.getContactNo());
				leads.setAnnualRevenue(leadsBO.getAnnualRevenue());
				leads.setCity(leadsBO.getCity());
				leads.setCountry(leadsBO.getCountry());
				leads.setDescription(leadsBO.getDescription());
				leads.setDesignation(leadsBO.getDesignation());
				leads.setDistrict(leadsBO.getDistrict());

				if (null != leadsBO.getFax() && !leadsBO.getFax().isEmpty()) {
					leads.setFax(leadsBO.getFax());
				} else {
					leads.setFax("N/A");
				}
				leads.setNoOfEmployees(leadsBO.getNoOfEmployees());
				leads.setPostalCode(leadsBO.getPostalCode());

				if (null != leadsBO.getRating() && !leadsBO.getRating().isEmpty()) {
					leads.setRating(leadsBO.getRating());
				} else {
					leads.setRating("6");
				}
				leads.setSalutation(leadsBO.getSalutation());
				leads.setState(leadsBO.getState());
				leads.setStatus(leadsBO.getStatus());
				leads.setStreet(leadsBO.getStreet());
				leads.setCompanyId(leadsBO.getCompanyId()); // companyId
				leads.setIsDelete(false);
				leadsBO.setCreated(new Date());
				currentdate = leadsBO.getCreated();

				if (null != leadsBO.getAdminLoginBO()) {
					user.setId(leadsBO.getAdminLoginBO().getId());
					leads.setLeadeOwner(user);
				}
				if (null != leadsBO.getUserName()) {
					String id = leadsBO.getUserName();

					if (null != id && !id.isEmpty()) {
						userId = Long.parseLong(id);
					}
					user.setId(userId);
					leads.setLeadeOwner(user);
				}
				if (null != leadsBO.getCampaignBO() && !leadsBO.getCampaignBO().getCampaignName().isEmpty()) {
					String campids = leadsBO.getCampaignBO().getCampaignName();
					int id = 0;
					if (null != campids && !campids.isEmpty()) {
						id = Integer.parseInt(campids);
					}
					Campaign campaign = new Campaign();
					campaign.setCampaignId(id);
					leads.setCampaignVO(campaign);
				}

				if (null != leadsBO.getProductServiceBO() && !leadsBO.getServiceName().isEmpty()) {

					serviceId = Long.parseLong(leadsBO.getServiceName());
					InventoryVO productServiceVO = new InventoryVO();
					productServiceVO.setServiceId(serviceId);
					leads.setProductServiceVO(productServiceVO);
				}
				if (null != leadsBO.getProjectBO() && !leadsBO.getProjectBO().getProjectName().isEmpty()) {

					projectId = Long.parseLong(leadsBO.getProjectBO().getProjectName());
					ProjectVO projectVO = new ProjectVO();
					projectVO.setProjectId(projectId);
					leads.setProjectVO(projectVO);
				}
				if (0 < leadsBO.getCompanyId()) {
					leads.setCompanyId(leadsBO.getCompanyId());
				}
			}
			id2 = leadsDAO.saveLeads(leads);
			/*
			 * if(0<id2) { TaskManagementBO taskBO = new TaskManagementBO();
			 * taskBO.setEntityName("Leads"); taskBO.setEntityId(id2);
			 * taskBO.setLeadName(leadName); taskBO.setRelatedTo(projectName);
			 * taskManagementService.save(taskBO); }
			 */
			// For Automatically assign Task to Leads..
			if (id2 > 0) {
				createTasksForLead(id2, leads);
			}
			// ActivityLog History dao call.....
			HistoryVO historyvo = new HistoryVO();
			historyvo.setEntityId(leads.getLeadsId());
			historyvo.setEntityType("Leads");
			historyvo.setActionType("Create");
			// for getting login id...
			long loginId = getUserSecurity().getLoginId();
			historyvo.setUser(loginId);
			historyvo.setCompanyId(leadsBO.getCompanyId());
			activityhistoryDAO.activityLogHistory(historyvo);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add leads creation has failed:" + ex.getMessage());
			}
			LOGGER.info("Add leads creation has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return id2;
	}

	private void createTasksForLead(long leadsId, Leads leadsVO) {
		User user = new User();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// Task 1: Initial Contact
		TaskManagementVO task1 = new TaskManagementVO();
		task1.setEntityName("Leads");
		task1.setEntityId(leadsId);
		task1.setLeadsVO(leadsVO);
		if (leadsVO.getLeadeOwner() != null) {
			task1.setTaskOwner(leadsVO.getLeadeOwner());
		}
		WorkItemVO workItem1 = new WorkItemVO();
		workItem1.setWorkItemId(generateWorkItemId("Initial Contact"));
		task1.setWorkItemVO(workItem1);
		task1.setRelatedTo("Initial Contact");
		task1.setSubject("Initial Contact with Lead");
		task1.setStatus("Open");
		task1.setPriority("High");
		task1.setDescription("Reach out to the new lead for an initial introduction.");
		task1.setDate(dateFormat.format(new Date()));
		task1.setDueDate(calculateDueDate(1));
		task1.setActive(true);
		task1.setisDelete(false);
		task1.setCompanyId(leadsVO.getCompanyId());

		// Task 2: Send Proposal
		TaskManagementVO task2 = new TaskManagementVO();
		task2.setEntityName("Leads");
		task2.setEntityId(leadsId);
		task2.setLeadsVO(leadsVO);
		if (leadsVO.getLeadeOwner() != null) {
			task2.setTaskOwner(leadsVO.getLeadeOwner());
		}
		WorkItemVO workItem2 = new WorkItemVO();
		workItem2.setWorkItemId(generateWorkItemId("Proposal"));
		task2.setWorkItemVO(workItem2);
		task2.setRelatedTo("Proposal");
		task2.setSubject("Send Proposal to Lead");
		task2.setStatus("Open");
		task2.setPriority("Medium");
		task2.setDescription("Send the business proposal to the lead.");
		task2.setDate(dateFormat.format(new Date()));
		task2.setDueDate(calculateDueDate(3));
		task2.setActive(true);
		task2.setisDelete(false);
		task2.setCompanyId(leadsVO.getCompanyId());

		// Task 3: Follow-Up
		TaskManagementVO task3 = new TaskManagementVO();
		task3.setEntityName("Leads");
		task3.setEntityId(leadsId);
		task3.setLeadsVO(leadsVO);
		if (leadsVO.getLeadeOwner() != null) {
			task3.setTaskOwner(leadsVO.getLeadeOwner());
		}
		WorkItemVO workItem3 = new WorkItemVO();
		workItem3.setWorkItemId(generateWorkItemId("Follow-Up"));
		task3.setWorkItemVO(workItem3);
		task3.setRelatedTo("Follow-Up");
		task3.setSubject("Follow-Up with Lead");
		task3.setStatus("Open");
		task3.setPriority("Low");
		task3.setDescription("Follow up with the lead after the proposal.");
		task3.setDate(dateFormat.format(new Date()));
		task3.setDueDate(calculateDueDate(7));
		task3.setActive(true);
		task3.setisDelete(false);
		task3.setCompanyId(leadsVO.getCompanyId());

		// Save tasks
		taskDao.saveNewTask(task1);
		taskDao.saveNewTask(task2);
		taskDao.saveNewTask(task3);

	}

	// Example method to generate or get WorkItem ID
	private long generateWorkItemId(String workItemType) {
		// This could be a lookup or creation logic based on the workItemType
		// For now, let's return a mock ID based on the type
		// In a real scenario, you'd query your database or service to get the ID
		switch (workItemType) {
		case "Initial Contact":
			return 1L;
		case "Proposal":
			return 2L;
		case "Follow-Up":
			return 3L;
		default:
			return 0L; // Default or error case
		}
	}

	// Example method to calculate due date
	private String calculateDueDate(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	/*
	 * private void createTasksForLead(long leadsId, LeadsBO leadsBO) { // List to
	 * hold all tasks List<TaskManagementBO> tasks = new ArrayList<>();
	 * 
	 * // Create WorkItemBO instances or IDs long initialContactWorkItemId =
	 * generateWorkItemId("Initial Contact"); long proposalWorkItemId =
	 * generateWorkItemId("Proposal"); long followUpWorkItemId =
	 * generateWorkItemId("Follow-Up");
	 * 
	 * // Task 1: Initial Contact TaskManagementBO task1 = new TaskManagementBO();
	 * task1.setEntityName("Leads"); task1.setEntityId(leadsId);
	 * task1.setLeadName(leadsBO.getFirstName());
	 * task1.setRelatedTo("Initial Contact");
	 * task1.setSubject("Initial Contact with Lead"); task1.setStatus("Open");
	 * task1.setPriority("High");
	 * task1.setDescription("Reach out to the new lead for an initial introduction."
	 * ); task1.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	 * task1.setDuedate(calculateDueDate(1)); // Due in 1 day
	 * task1.setWorkitem(String.valueOf(initialContactWorkItemId));
	 * tasks.add(task1);
	 * 
	 * // Task 2: Send Proposal TaskManagementBO task2 = new TaskManagementBO();
	 * task2.setEntityName("Leads"); task2.setEntityId(leadsId);
	 * task2.setLeadName(leadsBO.getFirstName()); task2.setRelatedTo("Proposal");
	 * task2.setSubject("Send Proposal to Lead"); task2.setStatus("Open");
	 * task2.setPriority("Medium");
	 * task2.setDescription("Send the business proposal to the lead.");
	 * task2.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	 * task2.setDuedate(calculateDueDate(3)); // Due in 3 days
	 * task2.setWorkitem(String.valueOf(proposalWorkItemId)); tasks.add(task2);
	 * 
	 * // Task 3: Follow-Up TaskManagementBO task3 = new TaskManagementBO();
	 * task3.setEntityName("Leads"); task3.setEntityId(leadsId);
	 * task3.setLeadName(leadsBO.getFirstName()); task3.setRelatedTo("Follow-Up");
	 * task3.setSubject("Follow-Up with Lead"); task3.setStatus("Open");
	 * task3.setPriority("Low");
	 * task3.setDescription("Follow up with the lead after the proposal.");
	 * task3.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	 * task3.setDuedate(calculateDueDate(7)); // Due in 7 days
	 * task3.setWorkitem(String.valueOf(followUpWorkItemId)); tasks.add(task3);
	 * 
	 * // Save all tasks in a single call taskManagementService.saveAll(tasks); }
	 */

	/*
	 * private String calculateDueDate(int days) { Calendar calendar =
	 * Calendar.getInstance(); calendar.add(Calendar.DAY_OF_MONTH, days); return new
	 * SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()); }
	 */

	// Leads View Part - count
	@Override
	public long getcountOfLeads(LeadsBO leadsBO) {

		return leadsDAO.getcountOfLeads(leadsBO);
	}

	// Leads View Part
	@Override
	public List<LeadsBO> getListOfLeadsByPagination(LeadsBO leadsBO) {
		List<Leads> leadsVOList = new ArrayList<>();
		List<LeadsBO> leadsBOList = new ArrayList<>();

		Leads leadsVO = new Leads();
		LOGGER.entry();
		try {
			if (null != leadsBO) {
				if (null != leadsBO.getCompanyId() && 0 < leadsBO.getCompanyId()) {
					leadsVO.setCompanyId(leadsBO.getCompanyId()); // comapnyId
				}
				leadsVO.setRecordIndex(leadsBO.getRecordIndex());
				leadsVO.setMaxRecord(leadsBO.getMaxRecord());
				if (leadsBO.getFirstName() != null) {
					leadsVO.setFirstName(leadsBO.getFirstName());
				}
				if (leadsBO.getEmailAddress() != null) {
					leadsVO.setEmailAddress(leadsBO.getEmailAddress());
				}
				if (leadsBO.getMobileNo() != null) {
					leadsVO.setMobileNo(leadsBO.getMobileNo());
				}
				if (null != leadsBO.getCampaignBO()) {
					Campaign campaigns = new Campaign();
					campaigns.setCampaignId(leadsBO.getCampaignBO().getCampaignId());
					leadsVO.setCampaignVO(campaigns);
				}

			}

			leadsVOList = leadsDAO.getListOfLeadsByPagination(leadsVO);
			int sNo = leadsBO.getRecordIndex();
			for (Leads leads : leadsVOList) {
				LeadsBO leadsBOObj = new LeadsBO();
				leadsBOObj.setsNo(++sNo);
				leadsBOObj.setLeadsId(leads.getLeadsId());
				leadsBOObj.setFirstName(leads.getFirstName());
				leadsBOObj.setEmailAddress(leads.getEmailAddress());
				leadsBOObj.setMobileNo(leads.getMobileNo());
				if (null != leads.getLeadeOwner()) {
					AdminLoginBO adminBO = new AdminLoginBO();
					adminBO.setName(leads.getLeadeOwner().getName());
					adminBO.setId(leads.getLeadeOwner().getId());
					leadsBOObj.setAdminLoginBO(adminBO);

					Boolean convertionCheck = leadsDAO.leadtoopprtunityconversationcheck(leads.getLeadeOwner().getId());
					if (convertionCheck) {
						leadsBOObj.setStatus("Completed");

					} else {
						leadsBOObj.setStatus("Not-Completed");
					}

				}
				if (null != leads.getCampaignVO()) {
					CampaignBO campaignBO = new CampaignBO();
					campaignBO.setCampaignId(leads.getCampaignVO().getCampaignId());
					campaignBO.setCampaignName(leads.getCampaignVO().getCampaignName());
					leadsBOObj.setCampaignBO(campaignBO);
				} else {
					CampaignBO campaignBO = new CampaignBO();
					campaignBO.setCampaignName("N/A");
					leadsBOObj.setCampaignBO(campaignBO);
				}
				if (null != leads.getProductServiceVO()) {
					InventoryBO productBO = new InventoryBO();
					productBO.setServiceId(leads.getProductServiceVO().getServiceId());
					productBO.setServiceName(leads.getProductServiceVO().getServiceName());
					leadsBOObj.setProductServiceBO(productBO);
				}
				if (null != leads.getProjectVO()) {
					ProjectBO projectBO = new ProjectBO();
					projectBO.setProjectId(leads.getProjectVO().getProjectId());
					projectBO.setProjectName(leads.getProjectVO().getProjectName());
					leadsBOObj.setProjectBO(projectBO);
				}
				leadsBOList.add(leadsBOObj);
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Leads view has faild :" + ex.getMessage());
			}
			LOGGER.info("Leads view has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();

		}
		return leadsBOList;
	}

	// Leads - Edit Update part
	@Override
	public boolean updateLeads(LeadsBO leadsBO) throws MySalesException {
		Leads leads = new Leads();
		User user = new User();
		Campaign campaign = new Campaign();
		boolean status = false;

		LOGGER.entry();
		try {
			leads = leadsDAO.getLeadsId(leadsBO);
			if (0 != leadsBO.getLeadsId()) {
				leads.setFirstName(leadsBO.getFirstName());
				leads.setLastName(leadsBO.getLastName());
				leads.setEmailAddress(leadsBO.getEmailAddress());
				leads.setCompanyName(leadsBO.getCompanyName());
				leads.setIndustryType(leadsBO.getIndustryType());
				leads.setWebsite(leadsBO.getWebsite());
				leads.setStatus(leadsBO.getStatus());
				leads.setAnnualRevenue(leadsBO.getAnnualRevenue());
				leads.setDesignation(leadsBO.getDesignation());
				leads.setDescription(leadsBO.getDescription());
				leads.setNoOfEmployees(leadsBO.getNoOfEmployees());
				leads.setRating(leadsBO.getRating());
				leads.setFax(leadsBO.getFax());
				leads.setSalutation(leadsBO.getSalutation());
				leads.setStreet(leadsBO.getStreet());
				leads.setCity(leadsBO.getCity());
				leads.setDistrict(leadsBO.getDistrict());
				leads.setState(leadsBO.getState());
				leads.setCountry(leadsBO.getCountry());
				leads.setPostalCode(leadsBO.getPostalCode());
				leads.setMobileNo(leadsBO.getMobileNo());
				leads.setContactNo(leadsBO.getContactNo());
				leads.setCompanyId(leadsBO.getCompanyId()); // companyId
				leads.setIsDelete(leadsBO.getIsDelete());
				leads.setModified(getDateWithoutTime(new Date()));
				leads.setModifiedBy(leadsBO.getModifiedBy());
				if (null != leadsBO.getAdminLoginBO()) {
					user.setId(leadsBO.getAdminLoginBO().getId());
					leads.setAdminLoginVO(user);
				}
				if (null != leadsBO.getUserName()) {
					String id = leadsBO.getUserName();
					long id1 = 0;
					if (null != id && !id.isEmpty()) {
						id1 = Long.parseLong(id);
					}
					user.setId(id1);
					leads.setLeadeOwner(user);

				}
				if (null != leadsBO && leadsBO.getAdminLoginBO().getId() > 0) {
					user.setId(leadsBO.getAdminLoginBO().getId());
					leads.setLeadeOwner(user);
				}
			}
			if (null != leadsBO.getCampaignBO() && 0 < leadsBO.getCampaignBO().getCampaignId()) {
				Campaign campaignVO = new Campaign();
				campaignVO.setCampaignId(leadsBO.getCampaignBO().getCampaignId());
				leads.setCampaignVO(campaignVO);

			}
			if (null != leadsBO.getProjectBO() && 0 < leadsBO.getProjectBO().getProjectId()) {
				ProjectVO projectVO = new ProjectVO();
				projectVO.setProjectId(leadsBO.getProjectBO().getProjectId());
				leads.setProjectVO(projectVO);

			}
			if (null != leadsBO.getProductServiceBO() && 0 < leadsBO.getProductServiceBO().getServiceId()) {
				InventoryVO productVO = new InventoryVO();
				productVO.setServiceId(leadsBO.getProductServiceBO().getServiceId());
				productVO.setServiceName(leadsBO.getProductServiceBO().getServiceName());
				productVO.setServiceSpecification(leadsBO.getProductServiceBO().getServiceSpecification());
				productVO.setFees(leadsBO.getProductServiceBO().getFees());
				productVO.setStartDate(leadsBO.getProductServiceBO().getStartDate());
				productVO.setEndDate(leadsBO.getProductServiceBO().getEndDate());
				productVO.setModified(leadsBO.getProductServiceBO().getModified());
				productVO.setModifiedBy(leadsBO.getProductServiceBO().getModifiedBy());
				productVO.setCreated(leadsBO.getProductServiceBO().getCreated());
				productVO.setCreatedBy(leadsBO.getProductServiceBO().getCreatedBy());
				leads.setProductServiceVO(productVO);
			}
			status = leadsDAO.updateLead(leads);
			// ActivityLog History dao call.....
			if (status = true) {
				HistoryVO historyvo = new HistoryVO();
				historyvo.setEntityId(leadsBO.getLeadsId());
				historyvo.setEntityType("Leads");
				historyvo.setActionType("Update");
				// for getting login id...
				long loginId = getUserSecurity().getLoginId();
				historyvo.setUser(loginId);
				// for getting company id...
				historyvo.setCompanyId(leadsBO.getCompanyId());

				activityhistoryDAO.activityLogHistory(historyvo);

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Leads edit&update has faild:" + ex.getMessage());
			}
			LOGGER.info("Leads edit&update has faild:" + ex.getMessage());

		} finally {
			LOGGER.exit();

		}
		return status;

	}

	// Leads -delete part
	@Override
	public boolean deleteLeads(Integer leadsId) {
		long leadsid = 0;
		LOGGER.entry();
		boolean status = false;
		try {
			if (null != leadsId) {
				leadsid = Long.valueOf(leadsId);
			}
			status = leadsDAO.deleteLeads(leadsid);
			// Activitylog History dao call....

			if (status = true) {
				HistoryVO historyvo = new HistoryVO();
				historyvo.setEntityId(leadsid);
				historyvo.setEntityType("Leads");
				historyvo.setActionType("Delete");
				// for getting login id...
				long loginId = getUserSecurity().getLoginId();
				historyvo.setUser(loginId);
				// for getting company id..
				long companyId = getUserSecurity().getCompanyId();
				historyvo.setCompanyId(companyId);
				activityhistoryDAO.activityLogHistory(historyvo);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Leads Delete has faild:" + ex.getMessage());
			}
			LOGGER.info("Leads Delete has  faild:" + ex.getMessage());

		} finally {
			LOGGER.exit();
		}
		return status;
	}

	// Leads Search count part
	@Override
	public long countOfLeadsBySearch(LeadsBO leadsBO) {
		LOGGER.entry();
		long count = 0;
		try {

			Leads leadsVO = new Leads();
			if (null != leadsBO.getCompanyId() && 0 < leadsBO.getCompanyId()) {
				leadsVO.setCompanyId(leadsBO.getCompanyId()); // companyId
			}
			leadsVO.setFirstName(leadsBO.getFirstName());
			leadsVO.setEmailAddress(leadsBO.getEmailAddress());
			leadsVO.setMobileNo(leadsBO.getMobileNo());
			Campaign campaigns = new Campaign();
			if (null != leadsBO.getCampaignBO() && 0 < leadsBO.getCampaignBO().getCampaignId()) {
				campaigns.setCampaignId(leadsBO.getCampaignBO().getCampaignId());
			}
			if (null != leadsBO.getFirstName() && !leadsBO.getFirstName().isEmpty()) {
				leadsVO.setLastName(leadsBO.getLastName());
			}
			leadsVO.setCampaignVO(campaigns);
			count = leadsDAO.countOfLeadsBySearch(leadsVO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Leads search has faild:" + ex.getMessage());
			}
			LOGGER.info("Leads search has faild:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return count;
	}

	// Leads - tracking status part
	@Override
	public LeadsBO saveTracking(LeadsBO bO) {
		LOGGER.entry();
		try {
			ActivityVO activityvo = new ActivityVO();

			if (null != bO.getDescription() && !bO.getDescription().isEmpty()) {
				activityvo.setDescription(bO.getDescription());
			}
			if (null != bO.getDate()) {
				activityvo.setDate(bO.getDate());
			}
			if (null != bO.getTimeSlot()) {
				activityvo.setTimeSlot(bO.getTimeSlot());
			}
			if (null != bO.getNextAppointmentDate()) {
				activityvo.setNextAppointmentDate(bO.getNextAppointmentDate());
			}

			if (0 != bO.getLeadsId()) {
				activityvo.setEntityid(bO.getLeadsId());
			}
			activityvo.setCompanyId(bO.getCompanyId()); // companyId include
			activityvo.setEntitytype("Leads");
			activityvo = leadsDAO.saveTracking(activityvo);
			if (activityvo.getActivityid() > 0) {
				bO.setActivityid(activityvo.getEntityid());

			} else {
				bO = null;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Leads treacking has faild:" + ex.getMessage());
			}
			LOGGER.info("Leads tracking has faild:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return bO;
	}

	// Leads Upload files part
	@Override
	public LeadsBO saveLeadsByExcelSheet(LeadsBO leadsBO1) {
		LOGGER.entry();
		try {
			Leads leadsVO = new Leads();
			if (null != leadsBO1.getFirstName()) {
				leadsVO.setFirstName(leadsBO1.getFirstName());
			}
			if (null != leadsBO1.getLastName()) {
				leadsVO.setLastName(leadsBO1.getLastName());
			}
			if (null != leadsBO1.getCompanyName()) {
				leadsVO.setCompanyName(leadsBO1.getCompanyName());
			}
			if (null != leadsBO1.getEmailAddress()) {
				leadsVO.setEmailAddress(leadsBO1.getEmailAddress());
			}
			if (null != leadsBO1.getContactNo()) {
				leadsVO.setContactNo(leadsBO1.getContactNo());
			}
			if (null != leadsBO1.getIndustryType()) {
				leadsVO.setIndustryType(leadsBO1.getIndustryType());
			}
			if (null != leadsBO1.getMobileNo()) {
				leadsVO.setMobileNo(leadsBO1.getMobileNo());
			}
			if (null != leadsBO1.getWebsite()) {
				leadsVO.setWebsite(leadsBO1.getWebsite());
			}

			if (null != leadsBO1.getStatus()) {
				leadsVO.setStatus(leadsBO1.getStatus());
			}
			if (null != leadsBO1.getSalutation()) {
				leadsVO.setSalutation(leadsBO1.getSalutation());

			}

			if (null != leadsBO1.getStreet()) {
				leadsVO.setStreet(leadsBO1.getStreet());

			}

			if (null != leadsBO1.getState()) {
				leadsVO.setState(leadsBO1.getState());

			}

			if (null != leadsBO1.getDistrict()) {
				leadsVO.setDistrict(leadsBO1.getDistrict());

			}
			if (null != leadsBO1.getDesignation()) {
				leadsVO.setDesignation(leadsBO1.getDesignation());

			}
			if (null != leadsBO1.getDescription()) {
				leadsVO.setDescription(leadsBO1.getDescription());

			}
			if (null != leadsBO1.getCountry()) {
				leadsVO.setCountry(leadsBO1.getCountry());

			}
			if (null != leadsBO1.getCity()) {
				leadsVO.setCity(leadsBO1.getCity());

			}
			if (null != leadsBO1.getFax()) {
				leadsVO.setFax(leadsBO1.getFax());
			}

			if (null != leadsBO1.getRating()) {

				leadsVO.setRating(leadsBO1.getRating());
			}

			if (0 != leadsBO1.getPostalCode()) {
				leadsVO.setPostalCode(leadsBO1.getPostalCode());
			}

			if (0 != leadsBO1.getNoOfEmployees()) {
				leadsVO.setNoOfEmployees(leadsBO1.getNoOfEmployees());
			}

			if (null != leadsBO1.getAnnualRevenue()) {
				leadsVO.setAnnualRevenue(leadsBO1.getAnnualRevenue());

			}

			if (0 < leadsBO1.getCampaignBO().getCampaignId()) {
				Campaign campaignVO = new Campaign();
				campaignVO.setCampaignId(leadsBO1.getCampaignBO().getCampaignId());
				leadsVO.setCampaignVO(campaignVO);
			}
			if (0 < leadsBO1.getAdminLoginBO().getId()) {
				User userVO = new User();
				userVO.setId(leadsBO1.getAdminLoginBO().getId());
				leadsVO.setLeadeOwner(userVO);
			}
			if (0 < leadsBO1.getProductServiceBO().getServiceId()) {
				InventoryVO productServiceVO = new InventoryVO();
				productServiceVO.setServiceId(leadsBO1.getProductServiceBO().getServiceId());
				leadsVO.setProductServiceVO(productServiceVO);
			}
			if (0 < leadsBO1.getProjectBO().getProjectId()) {
				ProjectVO projectVo = new ProjectVO();
				projectVo.setProjectId(leadsBO1.getProjectBO().getProjectId());
				leadsVO.setProjectVO(projectVo);
			}
			if (0 < leadsBO1.getCompanyId()) {
				leadsVO.setCompanyId(leadsBO1.getCompanyId());
			}

			long leadsId = leadsDAO.saveLeads(leadsVO);
			if (0 < leadsId) {
				leadsBO1.setLeadsId(leadsId);
				System.out.println("Successfully uploaded");
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Leads by excelsheet has faild:" + ex.getMessage());
			}
			LOGGER.info("Leads  by excelsheet has faild:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return leadsBO1;
	}

	@Override
	public AdminLoginBO authendicate(AdminLoginBO adminLoginBO) throws MySalesException {
		LeadsServiceImpl.LOGGER.entry();

		final AdminLoginBO adminLogin = new AdminLoginBO();
		try {
			final User user = this.leadsDAO.authendicate("emailAddress", adminLoginBO.getEmailAddress());

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

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Leads authendicate has faild:" + ex.getMessage());
			}
			LOGGER.info("Leads authendicate has faild:" + ex.getMessage());
		}
		return adminLogin;
	}

	@Override
	public boolean editLoginStatus(LoginStatusVO loginStatusVO) {
		LOGGER.entry();
		try {
			leadsDAO.editLoginStatus(loginStatusVO);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Leads edit login status has faild:" + ex.getMessage());
			}
			LOGGER.info("Leads edit login status has faild:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;

	}

	@Override
	public boolean addLoginStatus(String username) throws MySalesException {
		LOGGER.entry();
		try {
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
			leadsDAO.addLoginStatus(loginStatus);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Leads add login status has faild:" + ex.getMessage());
			}
			LOGGER.info("Leads add login status has faild:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public boolean createAccessLog(AccessLogBO logBO) {
		LOGGER.entry();
		boolean accessStatus = false;
		try {
			AccessLogVO logVO = new AccessLogVO();
			logVO.setAccessId(logBO.getAccessId());
			logVO.setAccessDate(logBO.getAccessDate());
			logVO.setClientIP(logBO.getClientIP());
			logVO.setSessionId(logBO.getSessionId());
			accessStatus = leadsDAO.createAccessLog(logVO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Leads create access has faild:" + ex.getMessage());
			}
			LOGGER.info("Leads create access has faild:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return accessStatus;
	}

	@Override
	public List<LeadsBO> getListLeads(LeadsBO leadsBOs) throws MySalesException {
		LOGGER.entry();
		List<LeadsBO> listLeadsBo = new ArrayList<LeadsBO>();

		try {

			List<Leads> listLeadsVO = leadsDAO.getListLeads(leadsBOs);
			if (null != listLeadsVO && listLeadsVO.size() > 0) {
				int sNo = leadsBOs.getRecordIndex();
				for (Leads leads : listLeadsVO) {

					CampaignBO campaignBO = new CampaignBO();
					LeadsBO leadsBO = new LeadsBO();
					SimpleDateFormat simpleformat = new SimpleDateFormat("dd-MM-yyyy");
					if (null != leads.getCreated() && null != leads.getModified()) {
						leadsBO.setCreatedDate(simpleformat.format(leads.getCreated()));
						leadsBO.setModifiedDate(simpleformat.format(leads.getModified()));
						leadsBO.setCreated(leads.getCreated());
					}
					leadsBO.setLeadsId(leads.getLeadsId());
					leadsBO.setFirstName(leads.getFirstName());
					leadsBO.setLastName(leads.getLastName());
					leadsBO.setEmailAddress(leads.getEmailAddress());
					leadsBO.setCompanyName(leads.getCompanyName());
					leadsBO.setIndustryType(leads.getIndustryType());
					leadsBO.setWebsite(leads.getWebsite());
					leadsBO.setMobileNo(leads.getMobileNo());
					leadsBO.setContactNo(leads.getContactNo());
					leadsBO.setsNo(++sNo);
					if (null != leads.getCampaignVO()) {
						int campid = leads.getCampaignVO().getCampaignId();
						campaignBO.setCampaignId(campid);
						campaignBO.setCampaignName(leads.getCampaignVO().getCampaignName());
						leadsBO.setCampaignBO(campaignBO);
					}
					if (null != leads.getLeadeOwner()) {
						AdminLoginBO adminUserBO = new AdminLoginBO();
						adminUserBO.setName(leads.getLeadeOwner().getName());
						adminUserBO.setId(leads.getLeadeOwner().getId());
						leadsBO.setAdminLoginBO(adminUserBO);
					}

					if (null != leads.getProductServiceVO().getServiceName()) {
						InventoryBO productServiceBO = new InventoryBO();
						productServiceBO.setServiceName(leads.getProductServiceVO().getServiceName());
						leadsBO.setProductServiceBO(productServiceBO);
					}

					listLeadsBo.add(leadsBO);
				}
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Leads list has failed:" + ex.getMessage());
			}
			LOGGER.info("Leads list has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return listLeadsBo;
	}

	@Override
	public LeadsBO getLeads(LeadsBO leadsBO) {
		LOGGER.entry();
		try {
			Leads leads = new Leads();
			InventoryBO serviceBO = new InventoryBO();
			leads.setLeadsId(leadsBO.getLeadsId());
			leads.setCompanyId(leadsBO.getCompanyId()); // company

			leads = leadsDAO.getLeads(leads);
			if (null != leads) {
				leadsBO.setLeadsId(leads.getLeadsId());
				leadsBO.setEntityid((int) leadsBO.getLeadsId());
				leadsBO.setFirstName(leads.getFirstName());
				leadsBO.setLastName(leads.getLastName());
				leadsBO.setEmailAddress(leads.getEmailAddress());
				leadsBO.setCompanyName(leads.getCompanyName());
				leadsBO.setIndustryType(leads.getIndustryType());
				leadsBO.setWebsite(leads.getWebsite());
				leadsBO.setMobileNo(leads.getMobileNo());
				leadsBO.setContactNo(leads.getContactNo());
				leadsBO.setStatus(leads.getStatus());
				leadsBO.setAnnualRevenue(leads.getAnnualRevenue());
				leadsBO.setDesignation(leads.getDesignation());
				leadsBO.setDescription(leads.getDescription());
				if (null != leads.getNoOfEmployees()) {
					leadsBO.setNoOfEmployees(leads.getNoOfEmployees());
				}
				if (null != leads.getRating()) {
					leadsBO.setRating(leads.getRating());
				} else {
					leadsBO.setRating("N/A");
				}
				leadsBO.setFax(leads.getFax());
				leadsBO.setSalutation(leads.getSalutation());
				leadsBO.setStreet(leads.getStreet());
				leadsBO.setCity(leads.getCity());
				leadsBO.setDistrict(leads.getDistrict());
				leadsBO.setState(leads.getState());
				leadsBO.setCountry(leads.getCountry());
				if (null != leads.getPostalCode()) {
					leadsBO.setPostalCode(leads.getPostalCode());
				}
				if (null != leads.getLeadeOwner() && 0 != leads.getLeadeOwner().getId()) {
					AdminLoginBO adminLoginBO = new AdminLoginBO();
					adminLoginBO.setId(leads.getLeadeOwner().getId());
					adminLoginBO.setName(leads.getLeadeOwner().getName());
					leadsBO.setAdminLoginBO(adminLoginBO);
				}
				// campaign owner
				if (null != leads.getCampaignVO()) {
					CampaignBO campaignBO = new CampaignBO();
					campaignBO.setCampaignId(leads.getCampaignVO().getCampaignId());
					campaignBO.setCampaignName(leads.getCampaignVO().getCampaignName());
					leadsBO.setCampaignBO(campaignBO);
				}
				// product - services
				if (null != leads.getProductServiceVO() && null != leads.getProductServiceVO().getServiceName()) {
					serviceBO.setServiceId(leads.getProductServiceVO().getServiceId());
					serviceBO.setServiceName(leads.getProductServiceVO().getServiceName());
					serviceBO.setServiceSpecification(leads.getProductServiceVO().getServiceSpecification());
					serviceBO.setFees(leads.getProductServiceVO().getFees());
					leadsBO.setProductServiceBO(serviceBO);
				}

				if (null != leads.getProjectVO() && null != leads.getProjectVO().getProjectName()) {
					ProjectBO projectBO = new ProjectBO();
					projectBO.setProjectId(leads.getProjectVO().getProjectId());
					projectBO.setProjectName(leads.getProjectVO().getProjectName());
					leadsBO.setProjectBO(projectBO);
				}

				if (null != leadsBO) {
					List<TaskManagementBO> taskBOList = new ArrayList<TaskManagementBO>();
					TaskManagementVO taskVO = new TaskManagementVO();
					taskVO.setEntityId(leads.getLeadsId());
					taskVO.setCompanyId(leads.getCompanyId());
					taskVO.setLeadsVO(leads);
					// List<TaskManagementVO> taskmanagelist=taskDao.findAll(taskVO);
					List<TaskManagementVO> taskmanagelist = taskDao.findTaskById(taskVO);

					for (TaskManagementVO taskManagementVO : taskmanagelist) {
						TaskManagementBO taskBO = new TaskManagementBO();
						taskBO.setEntityId(taskManagementVO.getEntityId());
						taskBO.setEntityName(taskManagementVO.getEntityName());
						taskBO.setLeadName(taskManagementVO.getEntityName());
						AdminUserBO adminUser = new AdminUserBO();
						adminUser.setName(taskManagementVO.getTaskOwner().getName());
						taskBO.setAdminUserBO(adminUser);
						WorkItemBO workitem = new WorkItemBO();
						workitem.setWorkItem(taskManagementVO.getWorkItemVO().getWorkItem());
						taskBO.setWorkItemBO(workitem);
						taskBO.setStatus(taskManagementVO.getStatus());
						taskBO.setPriority(taskManagementVO.getPriority());
						taskBO.setTaskId(taskManagementVO.getTaskId());
						taskBO.setSubject(taskManagementVO.getSubject());
						taskBOList.add(taskBO);
					}
					leadsBO.setTaskManagementBOList(taskBOList);
				}

				/*
				 * List<TaskManagementBO> taskBOList= new ArrayList<TaskManagementBO>();
				 * TaskManagementVO taskVO=new TaskManagementVO();
				 * taskVO.setEntityId(leads.getLeadsId()); List<TaskManagementVO>
				 * taskmanagelist=taskDao.findAll(taskVO);
				 * 
				 * for (TaskManagementVO taskManagementVO : taskmanagelist) { TaskManagementBO
				 * taskBO=new TaskManagementBO();
				 * taskBO.setEntityId(taskManagementVO.getEntityId());
				 * taskBO.setEntityName(taskManagementVO.getEntityName());
				 * taskBO.setLeadName(taskManagementVO.getEntityName()); AdminUserBO
				 * adminUser=new AdminUserBO();
				 * adminUser.setName(taskManagementVO.getTaskOwner().getName());
				 * taskBO.setAdminUserBO(adminUser);
				 * taskBO.setStatus(taskManagementVO.getStatus());
				 * taskBO.setPriority(taskManagementVO.getPriority());
				 * taskBO.setTaskId(taskManagementVO.getTaskId()); taskBOList.add(taskBO); }
				 * leadsBO.setTaskManagementBOList(taskBOList);
				 */

				if (null != leadsBO) {
					// retrieve leads tracking details
					List<ActivityVO> leadupdateList = leadsDAO.retrieveTracking(leadsBO.getEntityid());
					leadsBO.setLeadupdateList(leadupdateList);
				}
				// for Activitylog History dao call....
				if (null != leads) {
					HistoryVO historyvo = new HistoryVO();
					historyvo.setEntityId(leads.getLeadsId());
					historyvo.setEntityType("Leads");
					historyvo.setActionType("View");
					// for getting login id...
					long loginId = getUserSecurity().getLoginId();
					historyvo.setUser(loginId);
					// for getting company id..
					historyvo.setCompanyId(leadsBO.getCompanyId());
					activityhistoryDAO.activityLogHistory(historyvo);
				}
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("leads getleads has failed:" + ex.getMessage());
			}
			LOGGER.info("leads getleads has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return leadsBO;
	}

	public List<Map<?, ?>> getLeadsForOpportunityConversion(Integer leadsId, long companyId) {
		LOGGER.entry();
		List<Map<?, ?>> mapList = new ArrayList<>();
		try {
			Map<String, AccountBO> accountMap = new HashMap<>();
			Map<String, OpportunityBO> opportunityMap = new HashMap<>();
			OpportunityBO opportunityBo = new OpportunityBO();
			AccountBO accountBO = new AccountBO();
			Leads leads = new Leads();
			leads.setLeadsId(leadsId); // Converted in id to object
			leads.setCompanyId(companyId);// companyId
			leads = leadsDAO.getLeads(leads);
			if (null != leads) {
				// account--account information
				accountBO.setSalutation(leads.getSalutation());
				accountBO.setAccountName(leads.getFirstName());
				accountBO.setEmail(leads.getEmailAddress());
				accountBO.setContactNo(Long.valueOf(leads.getContactNo()));
				accountBO.setAnnualRevenue(leads.getAnnualRevenue()); // floatValue convert double to float

				// assigned to
				if (null != leads.getLeadeOwner() && 0 != leads.getLeadeOwner().getId()) {

					AdminUserBO adminUser = new AdminUserBO();
					adminUser.setAdminId(leads.getLeadeOwner().getId());
					adminUser.setName(leads.getLeadeOwner().getName());
					accountBO.setAssignedTo(adminUser);
				}

				// account--additional information
				accountBO.setIndustry(leads.getIndustryType());
				accountBO.setNoOfEmployess(leads.getNoOfEmployees());
				accountBO.setStreet(leads.getStreet());
				accountBO.setCity(leads.getCity());
				accountBO.setState(leads.getState());
				accountBO.setCountry(leads.getCountry());
				accountBO.setPostalCode(String.valueOf(leads.getPostalCode()));

				// account--description information
				accountMap.put("account", accountBO);

				// opportunity--personal information
				opportunityBo.setSalutation(leads.getSalutation());
				opportunityBo.setFirstName(leads.getFirstName());
				opportunityBo.setLastName(leads.getLastName());
				// opportunity--account information

				// opportunity--sales information
				if (null != leads.getProductServiceVO() && 0 != leads.getProductServiceVO().getServiceId()) {
					InventoryBO inventryBo = new InventoryBO();
					inventryBo.setServiceId(leads.getProductServiceVO().getServiceId());
					inventryBo.setServiceName(leads.getProductServiceVO().getServiceName());
					opportunityBo.setProductService(inventryBo);
				}

				if (null != leads.getLeadeOwner() && 0 != leads.getLeadeOwner().getId()) {
					AdminUserBO adminUser = new AdminUserBO();
					adminUser.setAdminId(leads.getLeadeOwner().getId());
					adminUser.setName(leads.getLeadeOwner().getName());

					User user = new User();
					user.setId(leads.getLeadeOwner().getId());
					user.setName(leads.getLeadeOwner().getName());
					opportunityBo.setUser(user);
					opportunityBo.setAssignedTo(adminUser);
				}

				LeadsBO leadsBo = new LeadsBO();
				leadsBo.setLeadsId(leads.getLeadsId());
				leadsBo.setFirstName(leads.getFirstName());
				opportunityBo.setLeads(leadsBo);

				opportunityMap.put("opportunity", opportunityBo);
			}
			mapList.add(accountMap);
			mapList.add(opportunityMap);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("leads OpportunityConversion has failed:" + ex.getMessage());
			}
			LOGGER.info("leads OpportunityConversion  has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return mapList;

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
	public boolean findByMobilenoLeads(String mobileNo) {
		return leadsDAO.findByMobilenoLeads(mobileNo);
	}

	@Override
	public boolean findByEmailLeads(String emailAddress) {
		return leadsDAO.findByEmailLeads(emailAddress);

	}

	@Override
	public List<AdminUserBO> retrieveUser() throws MySalesException {
		List<AdminUserBO> adminUserBOList = new ArrayList<AdminUserBO>();
		adminUserBOList = leadsDAO.retrieveUser();
		return adminUserBOList;
	}

	@Override
	public boolean sendClientMail(OpportunityBO employerRegisterBO) {

		return false;
	}

	@Override
	public List<LeadsBO> searchRetrieveTracking(LeadsBO listLeadsBO) {
		LOGGER.entry();

		List<LeadsFollowup> leadsFollowuplist = new ArrayList<LeadsFollowup>();
		LeadsFollowup leadsFollowup = new LeadsFollowup();
		List<LeadsBO> leadsListBO = new ArrayList<LeadsBO>();
		User userVO = new User();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (null != listLeadsBO.getStartDate()) {
				String startDate = df.format(listLeadsBO.getStartDate());
				Date toDate = df.parse(startDate);
				leadsFollowup.setCreated(toDate);
				leadsFollowup.setModified(null);
			}
			if (null != listLeadsBO.getEndDate()) {
				leadsFollowup.setCreated(null);
				String endDate = df.format(listLeadsBO.getEndDate());
				Date toDate = df.parse(endDate);
				leadsFollowup.setModified(toDate);
			}
			userVO.setId(listLeadsBO.getAdminLoginBO().getId());
			leadsFollowup.setUserVO(userVO);
			leadsFollowuplist = leadsDAO.searchRetrieveTracking(leadsFollowup);
			AtomicInteger sNo = new AtomicInteger(0);
			if (null != leadsFollowuplist && !leadsFollowuplist.isEmpty() && 0 < leadsFollowuplist.size()) {
				leadsFollowuplist.forEach(leadsFollow -> {
					AdminLoginBO adminLoginBO = new AdminLoginBO();
					CampaignBO campaignBO = new CampaignBO();
					LeadsBO leadsBO = new LeadsBO();
					leadsBO.setLeadsId(leadsFollow.getLeads().getLeadsId());
					leadsBO.setFirstName(leadsFollow.getLeads().getFirstName());
					leadsBO.setEmailAddress(leadsFollow.getLeads().getEmailAddress());
					leadsBO.setMobileNo(leadsFollow.getLeads().getMobileNo());
					if (0 < leadsFollow.getLeads().getLeadeOwner().getId()) {
						adminLoginBO.setId(leadsFollow.getLeads().getLeadeOwner().getId());
						leadsBO.setAdminLoginBO(adminLoginBO);
					}

					SimpleDateFormat simpleformat = new SimpleDateFormat("dd-MM-yyyy");
					leadsBO.setCreatedDate(simpleformat.format(leadsFollow.getLeads().getCreated()));
					leadsBO.setModifiedDate(simpleformat.format(leadsFollow.getLeads().getModified()));
					leadsBO.setCreated(leadsFollow.getCreated());
					leadsBO.setsNo(sNo.incrementAndGet());
					leadsListBO.add(leadsBO);
				});
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("leads searchRetrieveTracking has failed:" + ex.getMessage());
			}
			LOGGER.info("leads searchRetrieveTracking  has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return leadsListBO;
	}

	@Override
	public long getAnyAppointment(long leadsId) {
		return leadsDAO.getAnyAppointment(leadsId);
	}

	@Override
	public long countOfLeadsReportBySearch(LeadsBO leadsBO) {
		// TODO Auto-generated method stub

		long countOfLeadsReport = 0;
		Leads leadsVO = new Leads();
		leadsVO.setIsDelete(false);
		countOfLeadsReport = leadsDAO.countOfLeadsReportBySearch(leadsVO);
		if (0 < countOfLeadsReport) {
			return countOfLeadsReport;
		}
		return countOfLeadsReport;
	}

	@Override
	public boolean convertOpportunity(LeadsBO leadsBO) throws MySalesException {
		LOGGER.entry();
		try {
			Opportunity opportunity = new Opportunity();
			Leads leadsVO = new Leads();
			InventoryVO productServiceVO = new InventoryVO();
			InventoryBO productServiceBO = new InventoryBO();
			OpportunityBO opportunityBO = new OpportunityBO();

			opportunity.setFirstName(leadsBO.getFirstName());
			opportunity.setLastName(leadsBO.getLastName());
			opportunity.setDescription(leadsBO.getDescription());

			opportunity.setSalutation(leadsBO.getSalutation());
			opportunity.setIsDelete(false);
			opportunity.setCreatedBy(getUserSecurity().getLoginId());
			opportunity.setCreated(new Date());

			opportunity.setModified(new Date());
			opportunity.setModifiedBy(getUserSecurity().getLoginId());
			opportunity.setIsActive(true);
			long id = leadsBO.getLeadsId();
			leadsVO.setLeadsId(id);
			opportunity.setLeads(leadsVO);

			leadsBO.getProductServiceBO().getServiceId();
			productServiceVO.setServiceId(leadsBO.getProductServiceBO().getServiceId());

			opportunity.setProductService(productServiceVO);

			if (null != leadsBO.getAdminLoginBO() && 0 != leadsBO.getAdminLoginBO().getId()) {
				User user = new User();

				user.setId(leadsBO.getAdminLoginBO().getId());
				opportunity.setUser(user);

			}
			Opportunity convertopportunity = opportunityDAO.createOpportunity(opportunity);
			if (convertopportunity.getOpportunityId() > 0) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("leads convertoppotunity has failed:" + ex.getMessage());
			}
			LOGGER.info("leads convertoppotunity  has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public boolean updateOpportunityConversionLeads(LeadsBO leadsBO) {
		LOGGER.entry();
		boolean status = false;
		try {

			Leads leads = new Leads();
			leads.setCompanyId(leadsBO.getCompanyId()); // companyId
			leads = leadsDAO.getLeads(leads);
			if (null != leads) {
				leads.setIsDelete(leadsBO.getIsDelete());

				status = leadsDAO.updateLead(leads);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("leads update opportunity has failed:" + ex.getMessage());
			}
			LOGGER.info("leads update opportunity  has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return status;
	}

	@Override
	public boolean checkEmailAddress(String emailAddress, long companyId) {
		LOGGER.entry();
		boolean checkEmailId = false;
		try {
			checkEmailId = leadsDAO.checkEmailAddress(emailAddress, companyId);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("checkEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkEmailId;
	}

	@Override
	public List<LeadsBO> getListLeadsDropDown(LeadsBO leadsBO) {
	    
	    List<Leads> LeadsVOs = new ArrayList<>();
	    Map<Long, LeadsBO> leadsBOMap = new HashMap<>(); // Use Map to ensure uniqueness
	    try {
	        LeadsVOs = leadsDAO.viewLead(leadsBO);
	        if (LeadsVOs != null && !LeadsVOs.isEmpty()) {
	            for (Leads vo : LeadsVOs) {
	                LeadsBO bo = new LeadsBO();
	                CampaignBO campaignBO = new CampaignBO();

	                // Null check for CampaignVO
	                if (vo.getCampaignVO() != null) {
	                    campaignBO.setCampaignName(vo.getCampaignVO().getCampaignName());
	                    campaignBO.setCampaignId(vo.getCampaignVO().getCampaignId());
	                } else {
	                    // Handle the case where CampaignVO is null, if needed
	                    campaignBO.setCampaignName("Unknown"); // Or some default value
	                    campaignBO.setCampaignId(0); // Or some default value
	                }
	                
	                bo.setCampaignBO(campaignBO);
	                
	                // Use campaignId as the key to ensure uniqueness
	                leadsBOMap.put((long) campaignBO.getCampaignId(), bo);
	            }
	        }
	    } catch (Exception ex) {
	        if (LOGGER.isDebugEnabled()) {
	            LOGGER.debug("Retrieving Account List has been failed: " + ex.getMessage());
	        }
	        LOGGER.info("Retrieving Account List has failed: " + ex.getMessage());
	    }
	    
	    // Convert the map values to a list
	    return new ArrayList<>(leadsBOMap.values());
	}

}
