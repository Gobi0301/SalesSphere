package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.ApproveProcurementBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.ProcurementBO;
import com.scube.crm.bo.RejectProcurementBO;
import com.scube.crm.bo.SupplierBO;
import com.scube.crm.dao.ActivityHistoryDAO;
import com.scube.crm.dao.ProcurementDao;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.ApproveProcurementVO;
import com.scube.crm.vo.HistoryVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.ProcurementVO;
import com.scube.crm.vo.RejectProcurementVO;
import com.scube.crm.vo.SupplierVO;
@Service
@Transactional
public class ProcurementServiceImpl extends ControllerUtils implements ProcurementService {
  
	static final MySalesLogger LOGGER = MySalesLogger.getLogger(ProcurementServiceImpl.class);

	@Autowired
	private ProcurementDao serviceDao;
	
	@Autowired
	private ActivityHistoryDAO activityhistoryDAO;

	@Override
	public ProcurementBO insertProcurement(ProcurementBO bo) {
		LOGGER.entry();
		ProcurementBO procurementbo = null;
		try {
		long id = 0;
		ProcurementVO procurementVO=new ProcurementVO();
		procurementVO.setMinimumStock(bo.getMinimumStock());
		procurementVO.setMaximumStock(bo.getMaximumStock());
		procurementVO.setAvailableStock(bo.getAvailableStock());
		procurementVO.setExpectedDate(bo.getExpectedDate());
		procurementVO.setQuantityOfProducts(bo.getQuantityOfProducts());
		procurementVO.setUnitOfCost(bo.getUnitOfCost());
		procurementVO.setTotalCost(bo.getTotalCost());
		procurementVO.setCreatedBy(bo.getCreatedBy());
		procurementVO.setIsDelete(false);

		if (null!=bo && null != bo.getProductServiceBO() && !bo.getProductServiceBO().getServiceName().isEmpty()) {
			String productIds= bo.getProductServiceBO().getServiceName();
			if (null != productIds && !productIds.isEmpty()) {
				id = Long.parseLong(productIds);
			}

			InventoryVO productServiceVO = new InventoryVO();
			productServiceVO.setServiceId(id);
			procurementVO.setProductServiceVO(productServiceVO);
			
			SupplierVO supplierVO=new SupplierVO();
			String supplierId=bo.getSupplierBO().getSupplierName();
			if(null!=supplierId) {
				id = Long.parseLong(supplierId);
				supplierVO.setSupplierId(id);
				procurementVO.setSupplierVO(supplierVO);
			}
		}
		if(0<bo.getCompanyId()) {
			procurementVO.setCompanyId(bo.getCompanyId());
		}
		
		 procurementbo=  serviceDao.createProcurement(procurementVO);
		if(null!=procurementbo) {
			HistoryVO historyvo= new HistoryVO();
			historyvo.setEntityId(procurementbo.getProcurementId());
			historyvo.setEntityType("Procurement");
			historyvo.setActionType("Create");
			//for getting login id...
			long loginId=getUserSecurity().getLoginId();
			historyvo.setUser(loginId);
			//for getting company id...
			historyvo.setCompanyId(bo.getCompanyId());
			activityhistoryDAO.activityLogHistory(historyvo);
			
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Add Procurement has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		
		return procurementbo;
	}


	@Override
	public List<ProcurementBO> getListProcurement(ProcurementBO bo) {
		ProcurementVO procurementVO=new ProcurementVO();
		List<ProcurementVO> procurementListVO=new ArrayList<ProcurementVO>();
		List<ProcurementBO> procurementListBO=new ArrayList<ProcurementBO>();

		try {
			InventoryVO productServiceVO = new InventoryVO();
				procurementVO.setCompanyId(bo.getCompanyId());
				procurementVO.setMaxRecord(bo.getMaxRecord());
				procurementVO.setRecordIndex(bo.getRecordIndex());
				if (null != bo.getProductServiceBO() && 0<bo.getProductServiceBO().getServiceId()) {
					productServiceVO.setServiceId(bo.getProductServiceBO().getServiceId());
					procurementVO.setProductServiceVO(productServiceVO);
				}
			procurementListVO=serviceDao.getListProcurement(procurementVO);

			if(null!=procurementListVO && procurementListVO.size()>0 && !procurementListVO.isEmpty()){

				for (ProcurementVO procurementVo : procurementListVO) {
					InventoryBO inventoryBO=new InventoryBO();
					SupplierBO supplierBO=new SupplierBO();
					ProcurementBO bo1=new ProcurementBO();
					if(null!=procurementVo.getApproveVO()) {
						ApproveProcurementBO approval=new ApproveProcurementBO();
						approval.setApproveId(procurementVo.getApproveVO().getApproveId());
						approval.setProcurementStatus(procurementVo.getApproveVO().getApprovalStatus());
						bo1.setApproveBO(approval);
					}
					else{
						bo1.setApproveBO(null);
					}
					if(null!=procurementVo.getRejectVO()) {
						RejectProcurementBO reject=new RejectProcurementBO();
						reject.setRejectId(procurementVo.getRejectVO().getRejectId());
						reject.setProcurementStatus(procurementVo.getRejectVO().getRejectStatus());
						bo1.setRejectBO(reject);
						
					}
					else {
						bo1.setRejectBO(null);
					}
					
					 
					bo1.setProcurementId(procurementVo.getProcurementId());
					bo1.setMaximumStock(procurementVo.getMaximumStock());
					bo1.setMinimumStock(procurementVo.getMinimumStock());
					bo1.setAvailableStock(procurementVo.getAvailableStock());
					bo1.setExpectedDate(procurementVo.getExpectedDate());
					bo1.setQuantityOfProducts(procurementVo.getQuantityOfProducts());
					bo1.setUnitOfCost(procurementVo.getUnitOfCost());
					bo1.setTotalCost(procurementVo.getTotalCost());
					if(null!=procurementVo.getProductServiceVO()
							&&null!=procurementVo.getProductServiceVO().getServiceName()) {
						inventoryBO.setServiceName(procurementVo.getProductServiceVO().getServiceName());
					}
					bo1.setProductServiceBO(inventoryBO);
					if(null!=procurementVo.getSupplierVO()
							&&null!=procurementVo.getSupplierVO().getSupplierName()) {
						supplierBO.setSupplierName(procurementVo.getSupplierVO().getSupplierName());
					}
					bo1.setSupplierBO(supplierBO);
					procurementListBO.add(bo1);
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return procurementListBO;
	}
	@Override public ProcurementBO getProcurementValues(ProcurementBO bo) {
		LOGGER.entry();
		try {
		ProcurementVO procurementVO=new ProcurementVO();
		procurementVO.setProcurementId(bo.getProcurementId()); 
		
		procurementVO=serviceDao.getProcurementValues(procurementVO); 
		if(null!=procurementVO){
			bo.setMinimumStock(procurementVO.getMinimumStock());
			bo.setMaximumStock(procurementVO.getMaximumStock());
			bo.setAvailableStock(procurementVO.getAvailableStock());
			bo.setExpectedDate(procurementVO.getExpectedDate()); 
			bo.setUnitOfCost(procurementVO.getUnitOfCost());
			bo.setQuantityOfProducts(procurementVO.getQuantityOfProducts());
			bo.setTotalCost(procurementVO.getTotalCost());
			
			InventoryBO productBO=new InventoryBO();
			productBO.setServiceName(procurementVO.getProductServiceVO().getServiceName());
			productBO.setServiceId(procurementVO.getProductServiceVO().getServiceId());
			bo.setProductServiceBO(productBO);
			
			SupplierBO supplierBO=new SupplierBO();
			supplierBO.setSupplierName(procurementVO.getSupplierVO().getSupplierName());
			supplierBO.setSupplierId(procurementVO.getSupplierVO().getSupplierId());
			bo.setSupplierBO(supplierBO);
		} 
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Procurement has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return bo; 
	}
	
	@Override
	public boolean procurementValueUpdate(ProcurementBO bo) {
		ProcurementVO procurementVO=new ProcurementVO();
		InventoryBO inventoryBO=new InventoryBO();
		try {
			String id=bo.getProductServiceBO().getServiceName();
			long serviceId=Long.parseLong(id);
			inventoryBO.setServiceId(serviceId);
			bo.setProductServiceBO(inventoryBO);
			procurementVO.setProcurementId(bo.getProcurementId());
			procurementVO.setMinimumStock(bo.getMinimumStock());
			procurementVO.setMaximumStock(bo.getMaximumStock());
			procurementVO.setAvailableStock(bo.getAvailableStock());
			procurementVO.setQuantityOfProducts(bo.getQuantityOfProducts());
			procurementVO.setUnitOfCost(bo.getUnitOfCost());
			procurementVO.setExpectedDate(bo.getExpectedDate());
			procurementVO.setTotalCost(bo.getTotalCost());
			// service - products 
			
			if (null != bo.getProductServiceBO() && 0<bo.getProductServiceBO().getServiceId()) {
				InventoryVO productServiceVO = new InventoryVO();
				productServiceVO.setServiceId(bo.getProductServiceBO().getServiceId());
				procurementVO.setProductServiceVO(productServiceVO);
			}
			
			SupplierVO supplierVO=new SupplierVO();
			String supplierId=bo.getSupplierBO().getSupplierName();
			if(null!=supplierId) {
				 long id1 = Long.parseLong(supplierId);
				supplierVO.setSupplierId(id1);
				procurementVO.setSupplierVO(supplierVO);
			}
			if(0<bo.getCompanyId()) {
				procurementVO.setCompanyId(bo.getCompanyId());
			}
			    
			boolean status= serviceDao.procurementUpdateValues(procurementVO);
			if(status=true) {
				HistoryVO historyvo= new HistoryVO();
				historyvo.setEntityId(bo.getProcurementId());
				historyvo.setEntityType("Procurement");
				historyvo.setActionType("Update");
				//for getting login id...
				long loginId=getUserSecurity().getLoginId();
				historyvo.setUser(loginId);
				//for getting company id...
				historyvo.setCompanyId(bo.getCompanyId());
				activityhistoryDAO.activityLogHistory(historyvo);
			}
			return status;

		}

		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit-update Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit-update Procurement has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return serviceDao.procurementUpdateValues(procurementVO);
	}

	@Override
	public int deleteProcurementValues(int procurementId) {
		LOGGER.entry();
		int id = 0;
		try {
		ProcurementVO vo=new ProcurementVO();
		vo.setIsDelete(true);
		vo.setProcurementId(procurementId);
		 id= serviceDao.deleteProject(vo);
		if(0<id) {
			HistoryVO historyvo= new HistoryVO();
			historyvo.setEntityId(procurementId);
			historyvo.setEntityType("Procurement");
			historyvo.setActionType("Delete");
			//for getting login id...
			long loginId=getUserSecurity().getLoginId();
			historyvo.setUser(loginId);
			//for getting company id..
			long companyId= getUserSecurity().getCompanyId();
			historyvo.setCompanyId(companyId);
		activityhistoryDAO.activityLogHistory(historyvo);
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Procurement has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return id;
	}
	
	@Override
	public ApproveProcurementBO approveProcurement(ApproveProcurementBO approveBO) {
		ApproveProcurementVO approveVO=new ApproveProcurementVO();
		ProcurementVO procurementVO=new ProcurementVO();
		InventoryVO inventoryVO = new InventoryVO();
		SupplierVO supplierVO = new SupplierVO();
		procurementVO.setProcurementId(approveBO.getProcurementBO().getProcurementId());
		procurementVO=serviceDao.getProcurementValues(procurementVO); 
		approveVO.setProcurement(procurementVO);
		approveVO.setSentTo(approveBO.getSentTo());
		approveVO.setDescription(approveBO.getDescription());
		approveVO.setIsDelete(approveBO.getIsDelete());
		approveVO.setExpectedDate(approveBO.getExpectedDate());
		//approveVO.setProcurementStatus(approveBO.getProcurementStatus());
		inventoryVO.setServiceId(approveBO.getProductServiceBO().getServiceId());
		supplierVO.setSupplierId(approveBO.getSupplierBO().getSupplierId());
		approveVO.setCompanyId(approveBO.getCompanyId());
		approveVO.setProductServiceVO(inventoryVO);
		approveVO.setSupplierVO(supplierVO); 
		approveVO.setApprovalStatus("Approved");
				approveVO=serviceDao.createApprove(approveVO);
				if(null!=approveVO) {
					approveBO.setApproveId(approveVO.getApproveId());	
				}
				return approveBO;
	}
	@Override
	public ProcurementBO selectParticularView(ProcurementBO bo2) {
		
		ProcurementVO vo=new ProcurementVO();
		vo.setProcurementId(bo2.getProcurementId());
	 vo=serviceDao.selectParticularView(vo);
	 
	 if(null!=vo) {
		 bo2.setProcurementId(vo.getProcurementId());
		 bo2.setMinimumStock(vo.getMinimumStock());
		 bo2.setMaximumStock(vo.getMaximumStock());
		 bo2.setAvailableStock(vo.getAvailableStock());
		 bo2.setQuantityOfProducts(vo.getQuantityOfProducts());
		 bo2.setExpectedDate(vo.getExpectedDate());
		 bo2.setUnitOfCost(vo.getUnitOfCost());
		 bo2.setTotalCost(vo.getTotalCost());
		 
		 if (null != vo.getProductServiceVO()) {
				InventoryBO productServiceBO=new InventoryBO();
				productServiceBO.setServiceId(vo.getProductServiceVO().getServiceId());
				productServiceBO.setServiceName(vo.getProductServiceVO().getServiceName());
				bo2.setProductServiceBO(productServiceBO);
			}
		 if (null != vo.getSupplierVO()) {
				SupplierBO supplierBo=new SupplierBO();
				supplierBo.setSupplierId(vo.getSupplierVO().getSupplierId());
				supplierBo.setSupplierName(vo.getSupplierVO().getSupplierName());
				bo2.setSupplierBO(supplierBo);
			}
		 
		 if(null!=bo2) {
				//retrieve procurement tracking details
				List<ActivityVO> procurementactivityList= serviceDao.retrieveTracking(bo2.getProcurementId(),bo2.getCompanyId());
					bo2.setProcurementactivityList(procurementactivityList);
				}
		 
		 
	 }
	 if(null!=vo) {
		 HistoryVO historyvo= new HistoryVO();
			historyvo.setEntityId(bo2.getProcurementId());
			historyvo.setEntityType("Procurement");
			historyvo.setActionType("View");
			//for getting login id...
			long loginId=getUserSecurity().getLoginId();
			historyvo.setUser(loginId);
			//for getting company id..
			long companyId= getUserSecurity().getCompanyId();
			historyvo.setCompanyId(companyId);
			activityhistoryDAO.activityLogHistory(historyvo);
	 }
		return bo2;
	}

	@Override
	public ProcurementBO getProfile(long id) {
		ProcurementBO profileBO = new ProcurementBO();
		ProcurementVO ProfileVO = new ProcurementVO();
		try {
			if(id > 0) {
				ProfileVO = serviceDao.getProfile(id);
				
			if(ProfileVO != null) {
				profileBO.setProcurementId(ProfileVO.getProcurementId());
				profileBO.setQuantityOfProducts(ProfileVO.getQuantityOfProducts());
				profileBO.setTotalCost(ProfileVO.getTotalCost());
				 
				 
			}
			}
		} catch(Exception ex) {
			  ex.printStackTrace();
		}
		return profileBO;
	}


	@Override
	public ProcurementBO saveTracking(ProcurementBO bo) {
		ActivityVO vo=new ActivityVO();
		
		
		vo.setDescription(bo.getDescription());
		vo.setCreatedDate(bo.getCreatedDate());
		vo.setModifyDate(bo.getModifyDate());
		vo.setTimeSlot(bo.getTimeSlot());
		vo.setEndTimeSlot(bo.getEndTimeSlot());
		vo.setEntitytype("procurement");
		vo.setEntityid(bo.getProcurementId());
		vo.setCompanyId(bo.getCompanyId());
		vo=serviceDao.saveTracking(vo);
		
		if(vo.getActivityid()>0){
			
			bo.setActivityid(vo.getEntityid());
		}else{
			bo = null;
		}
	 
	return bo;	
}


	@Override
	public RejectProcurementBO createReject(RejectProcurementBO bo1) {
		
		RejectProcurementVO rejectVO=new RejectProcurementVO();
		rejectVO.setReason(bo1.getReason());
		ProcurementVO procurementVO=new ProcurementVO();
		InventoryVO inventoryVO = new InventoryVO();
		SupplierVO supplierVO = new SupplierVO();
		procurementVO.setProcurementId(bo1.getProcurementBO().getProcurementId());
		procurementVO=serviceDao.getProcurementValues(procurementVO);
		rejectVO.setProcurementReject(procurementVO);
		rejectVO.setRejectStatus(bo1.getReason());
		//approveVO.setProcurementStatus(approveBO.getProcurementStatus());
		inventoryVO.setServiceId(bo1.getProductServiceBO().getServiceId());
		supplierVO.setSupplierId(bo1.getSupplierBO().getSupplierId());
		rejectVO.setSupplierVO(supplierVO);
		rejectVO.setProductServiceVO(inventoryVO);
		rejectVO.setRejectStatus("Rejected");
				if(null!=rejectVO) {
					bo1.setRejectId(rejectVO.getRejectId());	
				}
				rejectVO.setCompanyId(bo1.getCompanyId());
		return  serviceDao.createReject(rejectVO);
	}


	@Override
	public List<ProcurementBO> searchByValue(ProcurementBO procurementBO2) {
		ProcurementVO vo=new ProcurementVO();
		List<ProcurementVO> procurementListVO=new ArrayList<>();
		List<ProcurementBO>  procurementListBO=new ArrayList<>();
		try {
			vo.setIsDelete(false);
			if(null!=procurementBO2.getProductServiceBO()){
				InventoryVO services =new InventoryVO();
				services.setServiceId(procurementBO2.getProductServiceBO().getServiceId());
				services.setServiceName(procurementBO2.getProductServiceBO().getServiceName());
				vo.setProductServiceVO(services);
			}
			vo.setCompanyId(procurementBO2.getCompanyId()); 
		procurementListVO=serviceDao.searchByValue(vo);
 
  if(null!=procurementListVO && !procurementListVO.isEmpty() && 0 <
  procurementListVO.size()) {  
	  for(ProcurementVO procurement:procurementListVO) {
       ProcurementBO procurementBO1=new ProcurementBO();
       procurementBO1.setProcurementId(procurement.getProcurementId());
  
         if (null != procurement.getProductServiceVO()) {
		InventoryBO productServiceBO=new InventoryBO(); 
		productServiceBO.setServiceName(procurement.getProductServiceVO().getServiceName());
		productServiceBO.setServiceId(procurement.getProductServiceVO().getServiceId());
		procurementBO1.setProductServiceBO(productServiceBO);
		SupplierBO supplierBO=new SupplierBO();
		supplierBO.setSupplierName(procurement.getSupplierVO().getSupplierName());
		
		procurementBO1.setSupplierBO(supplierBO);
		procurementBO1.setAvailableStock(procurement.getAvailableStock());
		procurementBO1.setExpectedDate(procurement.getExpectedDate());
	}
   procurementListBO.add(procurementBO1);
  
  }
  }
		}
  catch (Exception ex) {
	 ex.printStackTrace();
  }
		return procurementListBO;
	 
}


	@Override
	public long procurment(ProcurementBO procurementBO2) {
		long count = 0;
		try {
			ProcurementVO procurementVO=new ProcurementVO();
			InventoryVO productServiceVO = new InventoryVO();
			procurementVO.setCompanyId(procurementBO2.getCompanyId());
			if (null != procurementBO2.getProductServiceBO() && 0<procurementBO2.getProductServiceBO().getServiceId()) {
				productServiceVO.setServiceId(procurementBO2.getProductServiceBO().getServiceId());
				procurementVO.setProductServiceVO(productServiceVO);
			}
			count = serviceDao.ProcurementCount(procurementVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}


	@Override
	public List<ApproveProcurementBO> getapprovedprocurementlist(long companyId) {
		List<ApproveProcurementVO> procurementlistvo = new ArrayList<>();
		List<ApproveProcurementBO> procurementlistbo = new ArrayList<>();
		procurementlistvo=serviceDao.getapprovedprocurement(companyId);
		if(null !=procurementlistvo && !procurementlistvo.isEmpty() && procurementlistvo.size()>0) {
			for(ApproveProcurementVO vo: procurementlistvo) {
				ApproveProcurementBO approveProcurementBO = new ApproveProcurementBO();
				approveProcurementBO.setDescription(vo.getDescription());
				approveProcurementBO.setExpectedDate(vo.getExpectedDate());
			InventoryBO bo=new InventoryBO();
			bo.setServiceName(vo.getProductServiceVO().getServiceName());
			approveProcurementBO.setProductServiceBO(bo);
				approveProcurementBO.setSentTo(vo.getSentTo());
				ProcurementBO procurementBO=new ProcurementBO();
				procurementBO.setProcurementId(vo.getProcurement().getProcurementId());
				approveProcurementBO.setProcurementBO(procurementBO);
				SupplierBO sup=new SupplierBO();
				sup.setSupplierName(vo.getSupplierVO().getSupplierName());
				approveProcurementBO.setSupplierBO(sup);
				procurementlistbo.add(approveProcurementBO);
			}
		}
		return procurementlistbo;
	}


	@Override
	public List<RejectProcurementBO> getrejectedprocurementlist(long companyId) {
		List<RejectProcurementVO> procurementlistvo = new ArrayList<>();
		List<RejectProcurementBO> procurementlistbo = new ArrayList<>();
		procurementlistvo=serviceDao.getrejectedprocurement(companyId);
		if(null !=procurementlistvo && !procurementlistvo.isEmpty() && procurementlistvo.size()>0) {
			for(RejectProcurementVO vo: procurementlistvo) {
				RejectProcurementBO approveProcurementBO = new RejectProcurementBO();
				approveProcurementBO.setReason(vo.getReason());
			InventoryBO bo=new InventoryBO();
			bo.setServiceName(vo.getProductServiceVO().getServiceName());
			approveProcurementBO.setProductServiceBO(bo);
				ProcurementBO procurementBO=new ProcurementBO();
				procurementBO.setProcurementId(vo.getProcurementReject().getProcurementId());
				approveProcurementBO.setProcurementBO(procurementBO);
				SupplierBO sup=new SupplierBO();
				sup.setSupplierName(vo.getSupplierVO().getSupplierName());
				approveProcurementBO.setSupplierBO(sup);
				procurementlistbo.add(approveProcurementBO);
			}
		}
		return procurementlistbo;
	}
}