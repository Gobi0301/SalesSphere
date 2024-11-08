package com.scube.crm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.ClientBO;
import com.scube.crm.bo.GstBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.bo.PriceBookBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.bo.SalesOrderProductsBO;
import com.scube.crm.dao.ActivityHistoryDAO;
import com.scube.crm.dao.AdminDAO;
import com.scube.crm.dao.OpportunityDAO;
import com.scube.crm.dao.ProductServiceDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.GstVO;
import com.scube.crm.vo.HistoryVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.Leads;
import com.scube.crm.vo.Opportunity;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SalesOrderProductsVO;
import com.scube.crm.vo.SalesOrderVO;
import com.scube.crm.vo.User;

@Service("opportunityService")
@Transactional
public class OpportunityServiceImpl extends ControllerUtils implements OpportunityService {

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(OpportunityServiceImpl.class);
	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private ProductServiceDao productServiceDao;

	@Autowired
	private OpportunityDAO opportunityDAO;

	@Autowired
	private ActivityHistoryDAO activityhistoryDAO;


	@Override
	public List<User> retrieveUser(User user) throws MySalesException {

		OpportunityServiceImpl.LOGGER.entry();

		List<User> userBOList = new ArrayList<User>();
		try {
			userBOList = adminDAO.retrieveUser(user);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving User list has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieve User List has failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return userBOList;
	}

	@Override
	public List<LeadsBO> retrieveLeads(LeadsBO LeadsBO) throws MySalesException {
		OpportunityServiceImpl.LOGGER.entry();
		List<LeadsBO> leadList = new ArrayList<LeadsBO>();
		List<Leads> leadListDO = new ArrayList<Leads>();
		LeadsBO LeadsBo=new LeadsBO();
		LeadsBo.setCompanyId(LeadsBO.getCompanyId());

		try {
			leadListDO = opportunityDAO.retrieveLeads(LeadsBO);
			if(leadListDO != null && leadListDO.size() > 0 && !leadListDO.isEmpty() ) {
				leadList = leadListDO.stream().map(s -> {
					LeadsBO leadsBO = new LeadsBO();
					leadsBO.setLeadsId(s.getLeadsId());
					leadsBO.setFirstName(s.getFirstName());
					return leadsBO;
				}).collect(Collectors.toList());
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving Lead list has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieve Lead List has failed: " + ex.getMessage());
		}

		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}

		return leadList;
	}

	@Override
	public List<InventoryBO> getProductList(InventoryBO serviceBO) throws MySalesException {
		LOGGER.entry();
		List<InventoryVO> productVOList=new ArrayList<>();
		List<InventoryBO> productServiceBO=new ArrayList<>();
        InventoryVO  InventoryVO=new InventoryVO();
		InventoryVO.setCompanyId(serviceBO.getCompanyId());
		try {
			//List<Opportunity> opportunity= opportunityDAO.getProductListforSearch(InventoryVO);
			productVOList=productServiceDao.listOfProductName(InventoryVO);
			/*
			 * for(Opportunity opp:opportunity) { InventoryBO productServiceBo=new
			 * InventoryBO(); InventoryVO productvo
			 * =productServiceDao.getProducts(opp.getProductService().getServiceId());
			 * productServiceBo.setServiceName(productvo.getServiceName());
			 * productServiceBo.setServiceId(productvo.getServiceId());
			 * productServiceBO.add(productServiceBo); }
			 */
			for(InventoryVO in: productVOList) {
				InventoryBO productServiceBo=new InventoryBO();
				productServiceBo.setServiceName(in.getServiceName());
				productServiceBo.setServiceId(in.getServiceId());
				productServiceBO.add(productServiceBo);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Product List has been failed: " + ex.getMessage());
			}
			LOGGER.info("Product List has failed: " + ex.getMessage());
		}

		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return productServiceBO;
	}

	@Override
	public Long create(OpportunityBO opportunityBO) throws MySalesException {
		OpportunityServiceImpl.LOGGER.entry();
		Long status = null;
		try {
			if(opportunityBO != null) {
				Opportunity vo = new Opportunity();
				vo.setSalutation(opportunityBO.getSalutation());
				vo.setFirstName(opportunityBO.getFirstName());
				vo.setLastName(opportunityBO.getLastName());
				vo.setCreatedBy(opportunityBO.getCreatedBy());
				vo.setModifiedBy(opportunityBO.getModifiedBy());
				vo.setAmount(opportunityBO.getAmount());
				vo.setIsDelete(opportunityBO.getIsDelete());
				vo.setSalesStage(opportunityBO.getSalesStage());
				vo.setEndTime(opportunityBO.getEndTime());
				vo.setNextStep(opportunityBO.getNextStep());
				vo.setProbability(opportunityBO.getProbability());
				vo.setDescription(opportunityBO.getDescription());
				vo.setUser(opportunityBO.getUser());
				vo.setCompanyId(opportunityBO.getCompanyId());//comapny ID				
				InventoryVO pVO = new InventoryVO();
				pVO.setServiceId(opportunityBO.getProductService().getServiceId());
				vo.setProductService(pVO); 
				Leads lVO = new Leads();
				lVO.setLeadsId(opportunityBO.getLeads().getLeadsId());
				vo.setLeads(lVO);

				AccountVO accountVO = new AccountVO();
				accountVO.setAccountId(opportunityBO.getAccountBO().getAccountId());
				vo.setAccountVO(accountVO);
				status = opportunityDAO.create(vo);
				//ActivityLog History dao call....
				if(0<status) {
					HistoryVO historyvo= new HistoryVO();
					historyvo.setEntityId(status);
					historyvo.setEntityType("Opportunity");
					historyvo.setActionType("Create");
					//for getting login id...
					long loginId=getUserSecurity().getLoginId();
					historyvo.setUser(loginId);
					//for getting company id...
					historyvo.setCompanyId(opportunityBO.getCompanyId());
					activityhistoryDAO.activityLogHistory(historyvo);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Saving opportunity has been failed: " + ex.getMessage());
			}
			LOGGER.info("Saving opportunity has failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return status;
	}

	@Override
	public long oppocount(OpportunityBO opportunityBO) throws MySalesException {
		OpportunityServiceImpl.LOGGER.entry();
		long count = 0;
		Opportunity opportunityVO=new Opportunity();
		try {
			if(null != opportunityBO.getCompanyId()&& 0< opportunityBO.getCompanyId()) {
				opportunityVO.setCompanyId(opportunityBO.getCompanyId());// company based retrieve condition
			}
				if (null != opportunityBO.getFirstName() &&!opportunityBO.getFirstName().isEmpty()) {
					opportunityVO.setFirstName(opportunityBO.getFirstName());
				}
				
			count = opportunityDAO.Oppocount(opportunityVO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving count has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving count has failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return count;
	}

	@Override
	public List<OpportunityBO> view(OpportunityBO opportunityBO) throws MySalesException {
		OpportunityServiceImpl.LOGGER.entry();
		OpportunityBO OpportunityBO=new OpportunityBO();
		List<OpportunityBO> opportunityBOList = new ArrayList<OpportunityBO>();
		List<Opportunity> opportunityVOList = new ArrayList<Opportunity>();

		try {
			if(opportunityBO != null) {
				if(null != opportunityBO.getCompanyId()&& 0< opportunityBO.getCompanyId()) {
					OpportunityBO.setCompanyId(opportunityBO.getCompanyId());
					}
				opportunityVOList = opportunityDAO.view(opportunityBO);
				int count = opportunityBO.getRecordIndex();
				if(opportunityVOList != null && opportunityVOList.size() > 0 && ! opportunityVOList.isEmpty()) {
					for(Opportunity list : opportunityVOList) {
						OpportunityBO BO = new OpportunityBO();
						InventoryVO pVO = new InventoryVO();
						InventoryBO pBO = new InventoryBO();
						BO.setsNo(++count);
						BO.setOpportunityId(list.getOpportunityId());
						BO.setFirstName(list.getFirstName());
						BO.setLastName(list.getLastName());
						pBO.setServiceName(list.getProductService().getServiceName());
						BO.setProductService(pBO);
						if(null!=list.getEndTime()){
							BO.setEndTime(list.getEndTime());
						}
						if(null!=list.getSalesStage()){
							BO.setSalesStage(list.getSalesStage());
						}
						opportunityBOList.add(BO);
					}
				}
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving Opportunity List has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving Opportunity List has failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return opportunityBOList;
	}

	@Override
	public long searchCount(OpportunityBO opportunityBO) throws MySalesException {
		OpportunityServiceImpl.LOGGER.entry();
		long count = 0;
		try {
			if(opportunityBO != null) {
				Opportunity vo = new Opportunity();
				vo.setFirstName(opportunityBO.getFirstName());
				vo.setLastName(opportunityBO.getLastName());
				vo.setSalesStage(opportunityBO.getSalesStage());
				if(null != opportunityBO.getCompanyId()&& 0< opportunityBO.getCompanyId()) {
				vo.setCompanyId(opportunityBO.getCompanyId());
				}
				InventoryVO pVO = new InventoryVO();
				if(opportunityBO != null && 0 < opportunityBO.getProductService().getServiceId()) {
					pVO.setServiceId(opportunityBO.getProductService().getServiceId());
				}
				if (null != opportunityBO.getFirstName() &&!opportunityBO.getFirstName().isEmpty()) {
					vo.setFirstName(opportunityBO.getFirstName());
				}
				vo.setProductService(pVO);
				count = opportunityDAO.searchCount(vo);
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Failed to fetch the row count in opportunity Table: " + ex.getMessage());
			}
			LOGGER.info("Retrieving row count hs failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return count;
	}

	@Override
	public List<OpportunityBO> search(OpportunityBO opportunityBO) throws MySalesException {
		OpportunityServiceImpl.LOGGER.entry();
		List<OpportunityBO> opportunityBOList = new ArrayList<OpportunityBO>();
		List<Opportunity> opportunityVOList = new ArrayList<Opportunity>();

		try {
			if(opportunityBO != null) {
				Opportunity vo = new Opportunity();
				vo.setFirstName(opportunityBO.getFirstName());
				vo.setLastName(opportunityBO.getLastName());
				if(null != opportunityBO.getCompanyId()&& 0< opportunityBO.getCompanyId()) {
				vo.setCompanyId(opportunityBO.getCompanyId());
				}
				InventoryVO pVO = new InventoryVO();
				if(opportunityBO != null && 0 < opportunityBO.getProductService().getServiceId()) {
					pVO.setServiceId(opportunityBO.getProductService().getServiceId());
				}
				vo.setProductService(pVO);
				vo.setSalesStage(opportunityBO.getSalesStage());
				vo.setMaxRecord(opportunityBO.getMaxRecord());
				vo.setRecordIndex(opportunityBO.getRecordIndex());
				opportunityVOList = opportunityDAO.search(vo);
				if(opportunityVOList != null && ! opportunityVOList.isEmpty() && opportunityVOList.size() > 0) {
					int count = opportunityBO.getRecordIndex();
					for(Opportunity list : opportunityVOList) {
						OpportunityBO opportunityBo = new OpportunityBO();
						InventoryBO pBO = new InventoryBO();
						opportunityBo.setsNo(++count);
						opportunityBo.setOpportunityId(list.getOpportunityId());
						opportunityBo.setFirstName(list.getFirstName());
						opportunityBo.setLastName(list.getLastName());
						pBO.setServiceName(list.getProductService().getServiceName());
						pBO.setServiceId(list.getProductService().getServiceId());
						opportunityBo.setProductService(pBO);
						opportunityBo.setEndTime(list.getEndTime());
						opportunityBo.setSalesStage(list.getSalesStage());
						opportunityBOList.add(opportunityBo);
					}
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving Opportunity List has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving Opportunity List has failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return opportunityBOList;
	}

	@Override
	public OpportunityBO getById(long id) throws MySalesException {
		OpportunityServiceImpl.LOGGER.entry();
		OpportunityBO profile = new OpportunityBO();
		Opportunity opportunityVO = new Opportunity();

		try {
			opportunityVO = opportunityDAO.getById(id);
			if(opportunityVO != null) {
				profile.setOpportunityId(opportunityVO.getOpportunityId());
				profile.setSalutation(opportunityVO.getSalutation());
				profile.setFirstName(opportunityVO.getFirstName());
				profile.setLastName(opportunityVO.getLastName());

				AccountBO aBO = new AccountBO();
				AccountVO aVO = new AccountVO();
				aVO.setAccountId(opportunityVO.getAccountVO().getAccountId());
				aVO.setAccountName(opportunityVO.getAccountVO().getAccountName());
				aBO.setAccountId(aVO.getAccountId());
				aBO.setAccountName(aVO.getAccountName());
				profile.setAccountBO(aBO);
				InventoryVO pVO = new InventoryVO();
				pVO.setServiceId(opportunityVO.getProductService().getServiceId());
				pVO.setServiceName(opportunityVO.getProductService().getServiceName());
				InventoryBO pBO = new InventoryBO();
				pBO.setServiceId(pVO.getServiceId());
				pBO.setServiceName(pVO.getServiceName());
				profile.setProductService(pBO);
				profile.setUser(opportunityVO.getUser());
				Leads leads = new Leads();
				leads.setLeadsId(opportunityVO.getLeads().getLeadsId());
				LeadsBO leadsBO = new LeadsBO();
				leadsBO.setLeadsId(leads.getLeadsId());
				profile.setLeads(leadsBO);
				profile.setAmount(opportunityVO.getAmount());
				profile.setSalesStage(opportunityVO.getSalesStage());
				profile.setEndTime(opportunityVO.getEndTime());
				profile.setNextStep(opportunityVO.getNextStep());
				profile.setDescription(opportunityVO.getDescription());
				profile.setProbability(opportunityVO.getProbability());

			}
			//ActivityLog History dao call....
			HistoryVO historyvo= new HistoryVO();
			historyvo.setEntityId(id);
			historyvo.setEntityType("Opportunity");
			historyvo.setActionType("View");
			//for getting login id...
			long loginId=getUserSecurity().getLoginId();
			historyvo.setUser(loginId);
			//for getting company id..
			long companyId=getUserSecurity().getCompanyId();
			historyvo.setCompanyId(companyId);
			profile.setCompanyId(companyId);
			activityhistoryDAO.activityLogHistory(historyvo);
			
			//retrieve campaign tracking details
			List<ActivityVO> opportunityactivityList= opportunityDAO.retrieveTracking(profile.getOpportunityId(),companyId);
			profile.setOpportunityactivityList(opportunityactivityList);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving Opportunity Object has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving Opportunity object has failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return profile;
	}

	@Override
	public boolean update(OpportunityBO editable) throws MySalesException {
		OpportunityServiceImpl.LOGGER.entry();
		boolean status = false;
		try {
			if(editable != null) {
				Opportunity vo = new Opportunity();
				vo.setCreatedBy(editable.getCreatedBy());
				vo.setModifiedBy(editable.getModifiedBy());
				vo.setOpportunityId(editable.getOpportunityId());
				vo.setSalutation(editable.getSalutation());
				vo.setFirstName(editable.getFirstName());
				vo.setLastName(editable.getLastName());
				vo.setAmount(editable.getAmount());
				vo.setIsDelete(editable.getIsDelete());
				vo.setSalesStage(editable.getSalesStage());
				vo.setEndTime(editable.getEndTime());
				vo.setNextStep(editable.getNextStep());
				vo.setProbability(editable.getProbability());
				vo.setDescription(editable.getDescription());
				vo.setUser(editable.getUser());
				InventoryVO pVO = new InventoryVO();
				pVO.setServiceId(editable.getProductService().getServiceId());
				vo.setProductService(pVO); 
				Leads lVO = new Leads();
				lVO.setLeadsId(editable.getLeads().getLeadsId());
				AccountVO accountVO = new AccountVO();
				accountVO.setAccountId(editable.getAccountBO().getAccountId());
				vo.setAccountVO(accountVO);
				vo.setLeads(lVO);


				vo.setCompanyId(editable.getCompanyId());

				status = opportunityDAO.update(vo);
				//ActivityLog History dao call....
				if(status= true) {
					HistoryVO historyvo= new HistoryVO();
					historyvo.setEntityId(editable.getOpportunityId());
					historyvo.setEntityType("Opportunity");
					historyvo.setActionType("Update");
					//for getting login id...
					long loginId=getUserSecurity().getLoginId();
					historyvo.setUser(loginId);
					//for getting company id...
					historyvo.setCompanyId(editable.getCompanyId());
					activityhistoryDAO.activityLogHistory(historyvo);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Failed to update the opportunity: " + ex.getMessage());
			}
			LOGGER.info("Opportunity updation failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return status;
	}

	@Override
	public int delete(long id) throws MySalesException {
		OpportunityServiceImpl.LOGGER.entry();
		int status = 0;		
		try {
			if(0 < id) {
				status = opportunityDAO.delete(id);
			}
			//ActivityLog History dao call....
			if(0<status ) {
				HistoryVO historyvo= new HistoryVO();
				historyvo.setEntityId(id);
				historyvo.setEntityType("Opportunity");
				historyvo.setActionType("Delete");
				//for getting login id...
				long loginId=getUserSecurity().getLoginId();
				historyvo.setUser(loginId);
				//for getting company id...
				long companyId=getUserSecurity().getCompanyId();
				historyvo.setCompanyId(companyId);
				activityhistoryDAO.activityLogHistory(historyvo);

			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Failed to delete the opportunity: " + ex.getMessage());
			}
			LOGGER.info("Failed to delete the opportunity: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return status;
	}

	@Override
	public Map<Integer, String> retriveAccounts(AccountBO accountBO) throws MySalesException {
		OpportunityServiceImpl.LOGGER.entry();

		Map<Integer, String> dOMap = new HashMap<Integer , String>();
		AccountVO accountVO =new AccountVO();
		accountVO.setCompanyId(accountBO.getCompanyId());
		
		try {
			dOMap = opportunityDAO.retrieveAccounts(accountVO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving Account List has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving Account List has failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return dOMap;
	}

	@Override
	public String getSalesOrderNo(ClientBO clientBO) {
		
		return opportunityDAO.getSalesOrderNo(clientBO);
	}

	@Override
	public GstBO getGstValues() {
		LOGGER.entry();
		GstVO gstVO=new GstVO();
		GstBO gstBO=new GstBO();
		try {
			gstVO=opportunityDAO.getGstValues();
			if (null != gstVO) {
				gstBO.setSgst(gstVO.getSgst());
				gstBO.setCgst(gstVO.getCgst());
				gstBO.setGstId(gstVO.getGstId());
				return gstBO;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GstValues has been failed: " + ex.getMessage());
			}
			LOGGER.info("GstValues has failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return gstBO;
	}

	@Override
	public long createSalesOrder(SalesOrderBO salesOrderBOS) {
		LOGGER.entry();


		long status=0;
		try {
			if(null!=salesOrderBOS&&null!=salesOrderBOS.getSalesOrderProductBO()&&salesOrderBOS.getSalesOrderProductBO().size()>0) {
			
					SalesOrderVO salesOrderVO=new SalesOrderVO();

					AccountVO aVO = new AccountVO();
					salesOrderVO.setSalesOrderNo(salesOrderBOS.getSalesOrderNo());

					aVO.setAccountId(salesOrderBOS.getAccountBO().getAccountId());
					salesOrderVO.setAccountVO(aVO);					
					salesOrderVO.setCreatedBy(salesOrderBOS.getCreatedBy());
					salesOrderVO.setModifiedBy(salesOrderBOS.getModifiedBy());
					salesOrderVO.setCreated(salesOrderBOS.getCreated());
					// Company
					salesOrderVO.setCompanyId(salesOrderBOS.getCompanyId());
					salesOrderVO.setTotalInvoice(salesOrderBOS.getTotalInvoice());
					salesOrderVO.setGrandTotal(salesOrderBOS.getGrandTotal());
					List<SalesOrderProductsVO> salesOrderProductsVOList=new ArrayList<SalesOrderProductsVO>();
					
					for(SalesOrderProductsBO salesOrderBO:salesOrderBOS.getSalesOrderProductBO()) {
						SalesOrderProductsVO salesOrderProductsVO=new SalesOrderProductsVO();
						GstVO gstVO=new GstVO();	
						gstVO.setGstId(salesOrderBO.getGstBO().getGstId());
						salesOrderProductsVO.setGstVO(gstVO);
						
						InventoryVO inventoryVO=new InventoryVO();
						inventoryVO.setServiceId(salesOrderBO.getProduct().getServiceId());
						salesOrderProductsVO.setProduct(inventoryVO);
						
						PriceBookVO priceBookVO=new PriceBookVO();
						priceBookVO.setPriceBookId(salesOrderBO.getPriceBookBo().getPriceBookId());
						salesOrderProductsVO.setPriceBookVo(priceBookVO);
						
						salesOrderProductsVO.setPrice(salesOrderBO.getPrice());
						salesOrderProductsVO.setQuantity(salesOrderBO.getQuantity());
						salesOrderProductsVO.setQuantityPrice(salesOrderBO.getQuantityPrice());
						
						salesOrderProductsVO.setSalesOrderVo(salesOrderVO);
						salesOrderProductsVOList.add(salesOrderProductsVO);
					}
					
					salesOrderVO.setSalesOrderProductsVO(salesOrderProductsVOList);
					status=opportunityDAO.createSalesOrder(salesOrderVO);
				}
						
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createSalesOrder has been failed: " + ex.getMessage());
			}
			LOGGER.info("createSalesOrder has failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
	
		return status;
	}


	@Override
	public OpportunityBO saveTracking(OpportunityBO opportunityBO) {
		LOGGER.entry();
		try {
		ActivityVO activityvo = new ActivityVO();

		if(null !=opportunityBO.getDescription() && !opportunityBO.getDescription().isEmpty()){
			activityvo.setDescription(opportunityBO.getDescription());
		}
		if(null !=opportunityBO.getConvertedDate()){
			activityvo.setConvertedDate(opportunityBO.getConvertedDate());
		}
		if(null !=opportunityBO.getTimeSlot()){
			activityvo.setTimeSlot(opportunityBO.getTimeSlot());
		}
		if(null !=opportunityBO.getEndTimeSlot()){
			activityvo.setEndTimeSlot(opportunityBO.getEndTimeSlot());
		}
		if(null !=opportunityBO.getFollowupDate()){
			activityvo.setFollowupDate(opportunityBO.getFollowupDate());
		}
		if(0<opportunityBO.getOpportunityId()){
			activityvo.setEntityid(opportunityBO.getOpportunityId());
		}
		if(0<opportunityBO.getCompanyId()){
			activityvo.setCompanyId(opportunityBO.getCompanyId());
		}
		activityvo.setEntitytype("Opportunity");

		activityvo=opportunityDAO.saveTracking(activityvo);
		if(activityvo.getActivityid()>0){

			opportunityBO.setActivityid(activityvo.getEntityid());
		}else{
			opportunityBO = null;
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("saveTracking has been failed: " + ex.getMessage());
			}
			LOGGER.info("saveTracking has failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return opportunityBO;	
	}

	@Override
	public PriceBookBO getPriceBookPrice(Integer pricebook_Ids) {
        LOGGER.entry();
		PriceBookBO priceBookBO = new PriceBookBO();
		PriceBookVO priceBookVO = new PriceBookVO();
		try {
			priceBookVO= opportunityDAO.getPriceBookPrice(pricebook_Ids);
			priceBookBO.setPrice(priceBookVO.getPrice());
			priceBookBO.setPriceBookName(priceBookVO.getPriceBookName());
			return priceBookBO;}
	catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("PriceBookPrice has been failed: " + ex.getMessage());
		}
		LOGGER.info("PriceBookPrice has failed: " + ex.getMessage());
	}
	finally {
		OpportunityServiceImpl.LOGGER.exit();
	}
		return priceBookBO;
	}

	@Override
	public List<User> retrieveUser() throws MySalesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PriceBookBO getProductData(Integer product_Ids) {
		 LOGGER.entry();
			PriceBookBO priceBookBO = new PriceBookBO();
			PriceBookVO priceBookVO = new PriceBookVO();
			try {
				priceBookVO= opportunityDAO.getProductData(product_Ids);
				priceBookBO.setPrice(priceBookVO.getPrice());
				priceBookBO.setPriceBookName(priceBookVO.getPriceBookName());
				return priceBookBO;}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("PriceBookPrice has been failed: " + ex.getMessage());
			}
			LOGGER.info("PriceBookPrice has failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return priceBookBO;
	}

	@Override
	public GstBO getProductGst(long product_Ids) {
		LOGGER.entry();
		GstBO gstBO = new GstBO();
		GstVO gstVO = new GstVO();
		try {
			gstVO= opportunityDAO.getProductgst(product_Ids);
			gstBO.setGstId(gstVO.getGstId());
			gstBO.setSgst(gstVO.getSgst());
			gstBO.setCgst(gstVO.getCgst());
			return gstBO;}
	catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("PriceBookPrice has been failed: " + ex.getMessage());
		}
		LOGGER.info("PriceBookPrice has failed: " + ex.getMessage());
	}
	finally {
		OpportunityServiceImpl.LOGGER.exit();
	}
		return gstBO;
	}

	@Override
	public long activityCount(OpportunityBO profile) {
		long count = 0;
		try {
			if(profile != null) {
				Opportunity vo = new Opportunity();
				if(0<getUserSecurity().getCompanyId()) {
					long companyId=getUserSecurity().getCompanyId();  //company based create condition
					profile.setCompanyId(companyId); 
					}
				vo.setCompanyId(profile.getCompanyId());
				count = opportunityDAO.activityCount(vo);
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Failed to fetch the row count in opportunity Table: " + ex.getMessage());
			}
			LOGGER.info("Retrieving row count hs failed: " + ex.getMessage());
		}
		finally {
			OpportunityServiceImpl.LOGGER.exit();
		}
		return count;
	}




}
