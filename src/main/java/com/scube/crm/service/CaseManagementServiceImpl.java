package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.crm.bo.CaseManagementBO;
import com.scube.crm.bo.ClientBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.SupplierBO;
import com.scube.crm.dao.CaseManagementDao;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.CaseManagementVO;
import com.scube.crm.vo.Customer;
import com.scube.crm.vo.InventoryVO;


@Service
@Transactional
public class CaseManagementServiceImpl implements CaseManagementService{
	
	static final MySalesLogger LOGGER = MySalesLogger.getLogger(CaseManagementServiceImpl.class);
	
	@Autowired
	private CaseManagementDao serviceDao;

	@Override
	public CaseManagementBO createCase(CaseManagementBO caseBO) {
		LOGGER.entry();
		try {
		long id = 0;
		CaseManagementVO caseVO=new CaseManagementVO();
		caseVO.setCaseOrigin(caseBO.getCaseOrigin());
		caseVO.setCaseReason(caseBO.getCaseReason());
		caseVO.setCasesolution(caseBO.getCasesolution());
		caseVO.setCategory(caseBO.getCategory());
		caseVO.setClaimingDate(caseBO.getClaimingDate());
		caseVO.setDescription(caseBO.getDescription());
		caseVO.setExpireDate(caseBO.getExpireDate());
		caseVO.setWarrantyDate(caseBO.getWarrantyDate());
		caseVO.setEmail(caseBO.getEmail());
		caseVO.setPhoneNo(caseBO.getPhoneNo());
		caseVO.setStatus(caseBO.getStatus());
		caseVO.setType(caseBO.getType());
		caseVO.setIsDelete(false);
		caseVO.setPriority(caseBO.getPriority());
		caseVO.setCommend(caseBO.getCommend());
		
		if (null!=caseBO && null != caseBO.getProductServiceBO() && !caseBO.getProductServiceBO().getServiceName().isEmpty()) {
			String productIds= caseBO.getProductServiceBO().getServiceName();
			if (null != productIds && !productIds.isEmpty()) {
				id = Long.parseLong(productIds);
			}

			InventoryVO productServiceVO = new InventoryVO();
			productServiceVO.setServiceId(id);
			caseVO.setProductServiceVO(productServiceVO);
			
			Customer customerVO=new Customer();
			String clientId=caseBO.getClientBO().getFirstName();
			if(null!=clientId) {
				id = Long.parseLong(clientId);
				customerVO.setId(id);
				caseVO.setCustomerVO(customerVO);
		}
			if(0<caseBO.getCompanyId()) {
				caseVO.setCompanyId(caseBO.getCompanyId());
			}
		return  serviceDao.createCase(caseVO);
	}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add CaseManagement has failed:" + ex.getMessage());
			}
			LOGGER.info("Add CaseManagement has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return caseBO;
	}
	@Override
	public List<CaseManagementBO> getListCase(CaseManagementBO caseBO) {
		
		int count = 1;
		CaseManagementVO caseVO=new CaseManagementVO();
		List<CaseManagementVO> caseListVO=new ArrayList<CaseManagementVO>();
		List<CaseManagementBO> caseList=new ArrayList<CaseManagementBO>();
		try {
			 if(null != caseBO.getCompanyId()&& 0< caseBO.getCompanyId()) {
			caseVO.setCompanyId(caseBO.getCompanyId());
			 }
			 
			 caseVO.setRecordIndex(caseBO.getRecordIndex());
			 caseVO.setMaxRecord(caseBO.getMaxRecord());
			caseListVO=serviceDao.getListCase(caseVO);
			int sno=caseVO.getRecordIndex();
			if(null!=caseListVO && caseListVO.size()>0 && !caseListVO.isEmpty()){
				for (CaseManagementVO caseVo : caseListVO) {
					
					InventoryBO inventoryBO=new InventoryBO();
					CaseManagementBO bo1=new CaseManagementBO();
					bo1.setsNo(++sno);
					bo1.setCaseId(caseVo.getCaseId());
					bo1.setCaseOrigin(caseVo.getCaseOrigin());
					bo1.setCaseReason(caseVo.getCaseReason());
					bo1.setCasesolution(caseVo.getCasesolution());
					bo1.setCategory(caseVo.getCategory());
					bo1.setClaimingDate(caseVo.getClaimingDate());
					bo1.setDescription(caseVo.getDescription());
					bo1.setExpireDate(caseVo.getExpireDate());
					bo1.setWarrantyDate(caseVo.getWarrantyDate());
					bo1.setEmail(caseVo.getEmail());
					bo1.setPhoneNo(caseVo.getPhoneNo());
					bo1.setStatus(caseVo.getStatus());
					bo1.setType(caseVo.getType());
					bo1.setPriority(caseVo.getPriority());
					bo1.setCommend(caseVo.getCommend());
					
					
					if(null!=caseVo.getProductServiceVO()
							&&null!=caseVo.getProductServiceVO().getServiceName()) {
						inventoryBO.setServiceName(caseVo.getProductServiceVO().getServiceName());
					}
					bo1.setProductServiceBO(inventoryBO);
					caseList.add(bo1);
			}
		}
		}
	 catch (Exception ex) {
		ex.printStackTrace();
	}
		return caseList;
	}

	@Override
	public CaseManagementBO getCaseValue(CaseManagementBO caseBO) {
		LOGGER.entry();
		try {
		CaseManagementVO caseVO=new CaseManagementVO();
		caseVO.setCaseId(caseBO.getCaseId()); 
		caseVO=serviceDao.getCaseValues(caseVO); 
		if(null!=caseVO){
			caseBO.setCaseOrigin(caseVO.getCaseOrigin());
			caseBO.setCaseReason(caseVO.getCaseReason());
			caseBO.setCasesolution(caseVO.getCasesolution());
			caseBO.setCategory(caseVO.getCategory());
			caseBO.setClaimingDate(caseVO.getClaimingDate());
			caseBO.setDescription(caseVO.getDescription());
			caseBO.setExpireDate(caseVO.getExpireDate());
			caseBO.setWarrantyDate(caseVO.getWarrantyDate());
			caseBO.setEmail(caseVO.getEmail());
			caseBO.setPhoneNo(caseVO.getPhoneNo());
			caseBO.setStatus(caseVO.getStatus());
			caseBO.setType(caseVO.getType());
			caseBO.setPriority(caseVO.getPriority());
			caseBO.setCommend(caseVO.getCommend());
			
			InventoryBO productBO=new InventoryBO();
			productBO.setServiceName(caseVO.getProductServiceVO().getServiceName());
			productBO.setServiceId(caseVO.getProductServiceVO().getServiceId());
			caseBO.setProductServiceBO(productBO);
			
			ClientBO clientBO=new ClientBO();
			clientBO.setFirstName(caseVO.getCustomerVO().getFirstName());
			clientBO.setClientId(caseVO.getCustomerVO().getId());
			caseBO.setClientBO(clientBO);
		} 
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit CaseManagement has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit CaseManagement has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return caseBO; 
	}
	@Override
	public boolean caseValueUpdate(CaseManagementBO caseBO) {
		CaseManagementVO  caseVO=new CaseManagementVO ();
		InventoryBO inventoryBO=new InventoryBO();
		ClientBO clientBO=new ClientBO();
	LOGGER.entry();
		try {
			String id=caseBO.getProductServiceBO().getServiceName();
			long serviceId=Long.parseLong(id);
			inventoryBO.setServiceId(serviceId);
			caseBO.setProductServiceBO(inventoryBO);
			
			String id1=caseBO.getClientBO().getFirstName();
			long ClientId=Long.parseLong(id1);
			clientBO.setClientId(ClientId);
			caseBO.setClientBO(clientBO);
			
			if(null!=caseBO && 0<caseBO.getCaseId());
			caseVO.setCaseId(caseBO.getCaseId());
			caseVO.setCaseOrigin(caseBO.getCaseOrigin());
			caseVO.setCaseReason(caseBO.getCaseReason());
			caseVO.setCasesolution(caseBO.getCasesolution());
			caseVO.setCategory(caseBO.getCategory());
			caseVO.setClaimingDate(caseBO.getClaimingDate());
			caseVO.setDescription(caseBO.getDescription());
			caseVO.setExpireDate(caseBO.getExpireDate());
			caseVO.setEmail(caseBO.getEmail());
			caseVO.setPhoneNo(caseBO.getPhoneNo());
			caseVO.setWarrantyDate(caseBO.getWarrantyDate());
			caseVO.setStatus(caseBO.getStatus());
			caseVO.setType(caseBO.getType());
			caseVO.setPriority(caseBO.getPriority());
			caseVO.setCommend(caseBO.getCommend());
			
			if (null != caseBO.getProductServiceBO() && 0<caseBO.getProductServiceBO().getServiceId()) {
				InventoryVO productServiceVO = new InventoryVO();
				productServiceVO.setServiceId(caseBO.getProductServiceBO().getServiceId());
				caseVO.setProductServiceVO(productServiceVO);
			}
			Customer customerVO=new Customer();
			customerVO.setId(caseBO.getClientBO().getClientId());
			
				caseVO.setCustomerVO(customerVO);
		 
			if(0<caseBO.getCompanyId()) {
				caseVO.setCompanyId(caseBO.getCompanyId());
			}
			

		}

		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update CaseManagement has failed:" + ex.getMessage());
			}
			LOGGER.info("Update CaseManagement has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return serviceDao.caseUpdateValues(caseVO);
	}


	@Override
	public int deleteCaseValues(int caseId) {
		LOGGER.entry();
		int id=0; 
	try {
		CaseManagementVO vo=new CaseManagementVO();
		vo.setIsDelete(true);
		vo.setCaseId(caseId);
		 id= serviceDao.deleteCase(vo);
	}catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Delete CaseManagement has failed:" + ex.getMessage());
		}
		LOGGER.info("Delete CaseManagement has failed:" + ex.getMessage());
	}finally {
		LOGGER.exit();
	}
		return id;
	
	}
	@Override
	public ClientBO getProduct(long id) {
		ClientBO clientBO = new ClientBO();
		Customer clientVO = new Customer();
		try {
			if(id > 0) {
				clientVO = serviceDao.getProfile(id);
				
			if(clientVO != null) {
				clientBO.setId(clientVO.getId());
				clientBO.setWarrantyDate(clientVO.getWarrantyDate());
				 
				 
			}
			}
		} catch(Exception ex) {
			  ex.printStackTrace();
		}
		return clientBO;
	}
	@Override
	public long searchcount(CaseManagementBO caseBO) {
		LOGGER.entry();
		long count = 0;
		try {
			if(caseBO != null) {
		CaseManagementVO vo=new CaseManagementVO();
				vo.setCaseOrigin(caseBO.getCaseOrigin());
				if(null != caseBO.getCompanyId()&& 0< caseBO.getCompanyId()) {
				vo.setCompanyId(caseBO.getCompanyId());
				}
				if (null != caseBO.getCaseOrigin() &&!caseBO.getCaseOrigin().isEmpty()) {
					vo.setCaseOrigin(caseBO.getCaseOrigin()); //ss
				}
				count = serviceDao.searchcount(vo);
			}
			
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchcount CaseManagement has failed:" + ex.getMessage());
			}
			LOGGER.info("searchcount CaseManagement has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		
		return count;
	}
	@Override
	public List<CaseManagementBO> searchCase(CaseManagementBO caseBO) {
		List<CaseManagementBO> caseBo = new ArrayList<CaseManagementBO>();
		  List<CaseManagementVO> caseVo = new ArrayList<CaseManagementVO>();
		  LOGGER.entry();
		  try {
			  if(null!= caseBO) {
				  CaseManagementVO caseVO = new CaseManagementVO();
				  caseVO.setMaxRecord(caseBO.getMaxRecord());
				  caseVO.setRecordIndex(caseBO.getRecordIndex());
				  caseVO.setCaseOrigin(caseBO.getCaseOrigin());
				  if(null != caseBO.getCompanyId()&& 0< caseBO.getCompanyId()) {
				  caseVO.setCompanyId(caseBO.getCompanyId());
				  }
				  caseVo = serviceDao.searchCase(caseVO);
			  if(null!=caseVo&&!caseVo.isEmpty()) {
				  int count = caseBO.getRecordIndex();
				  for(CaseManagementVO vo :caseVo) {
					  CaseManagementBO bo = new CaseManagementBO();
					  InventoryBO inventory=new InventoryBO();
						SupplierBO supplier=new SupplierBO();
					  bo.setsNo(++count);
					  bo.setCaseId(vo.getCaseId());
					  bo.setCaseOrigin(vo.getCaseOrigin());
					  bo.setCaseReason(vo.getCaseReason());
					  bo.setCasesolution(vo.getCasesolution());
					  bo.setPriority(vo.getPriority());
					  bo.setStatus(vo.getStatus());
					  bo.setCommend(vo.getCommend());
					  caseBo.add(bo);
				  }
			  }
			  }
		  }catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Search CaseManagement has failed:" + ex.getMessage());
				}
				LOGGER.info("Search CaseManagement has failed:" + ex.getMessage());
			}finally {
				LOGGER.exit();
			}
			
			return caseBo;
	}	 
	}

 
