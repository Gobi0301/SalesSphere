package com.scube.crm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.SupplierBO;
import com.scube.crm.bo.SupplierProductBO;
import com.scube.crm.dao.ActivityHistoryDAO;
import com.scube.crm.dao.SupplierDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.HistoryVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.SupplierProductVO;
import com.scube.crm.vo.SupplierVO;

@Service

@Transactional
public class SupplierServiceImpl extends ControllerUtils implements SupplierService {

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(SupplierServiceImpl.class);

	@Autowired
	SupplierDao supplierDao;
	@Autowired
	ActivityHistoryDAO activityhistoryDAO;

	// Create supplier
	@Override
	public SupplierBO createSupplier(SupplierBO supplier) {
		LOGGER.entry();
		try {
			SupplierVO supplierVo = new SupplierVO();

			// supplierVo.setSupplierId(supplier.getSupplierId());
			supplierVo.setSupplierName(supplier.getSupplierName());
			supplierVo.setEmailId(supplier.getEmailId());
			supplierVo.setMobileNo(supplier.getMobileNo());
			supplierVo.setAddress(supplier.getAddress());
			supplierVo.setDelete(false);
			supplierVo.setCity(supplier.getCity());
			supplierVo.setCountry(supplier.getCountry());
			supplierVo.setState(supplier.getState());
			supplierVo.setWebSite(supplier.getWebSite());
			supplierVo.setAddress(supplier.getAddress());
			supplierVo.setTechOriented(supplier.getTechOriented());
			supplierVo.setFinancialAmount(supplier.getFinancialAmount());
			supplierVo.setRating(supplier.getRating());
			supplierVo.setLocation(supplier.getLocation());
			supplierVo.setAddress(supplier.getAddress());
			supplierVo.setCreatedBy(supplier.getCreatedBy());
			if (null != supplier && null != supplier.getProductServiceBO()
					&& !(supplier.getProductServiceBO()).getServiceName().isEmpty()) {

				List<InventoryVO> productServiceList = new ArrayList<>();
				List<String> productStringList = new ArrayList<String>(
						Arrays.asList(supplier.getProductServiceBO().getServiceName().split(",")));

				for (String string : productStringList) {
					InventoryVO productServiceVO = new InventoryVO();
					Long serviceId = Long.parseLong(string);
					productServiceVO.setServiceId(serviceId);
					productServiceList.add(productServiceVO);
				}
				supplierVo.setProductServiceVO(productServiceList);

			}
			if (0 < supplier.getCompanyId()) {
				supplierVo.setCompanyId(supplier.getCompanyId());
			}

			supplier = supplierDao.createSupplier(supplierVo);
			if (null != supplierVo) {
				BeanUtils.copyProperties(supplierVo, supplier);
			}
			// ActivityLog History Dao call...
			if (null != supplier) {
				HistoryVO historyvo = new HistoryVO();
				historyvo.setEntityId(supplier.getSupplierId());
				historyvo.setEntityType("Supplier");
				historyvo.setActionType("Create");
				// for getting login id...
				long loginId = getUserSecurity().getLoginId();
				historyvo.setUser(loginId);
				// for getting company id...
				long companyId = getUserSecurity().getCompanyId();
				historyvo.setCompanyId(companyId);
				activityhistoryDAO.activityLogHistory(historyvo);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add Supplier has failed:" + ex.getMessage());
			}
			LOGGER.info("Add Supplier has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return supplier;

	}

	// List Supplier
    public List<SupplierBO> listSupplier(SupplierBO supplierBO) {
        LOGGER.entry();
        SupplierVO supplierVO = new SupplierVO();
        InventoryBO InventoryBO = new InventoryBO();
        List<SupplierVO> voList = new ArrayList<>();
        List<SupplierBO> boList = new ArrayList<>();
        try {
            supplierVO.setSupplierId(supplierBO.getSupplierId());
            supplierVO.setRecordIndex(supplierBO.getRecordIndex());
            supplierVO.setMaxRecord(supplierBO.getMaxRecord());
            supplierVO.setSupplierName(supplierBO.getSupplierName());
            if(null != supplierBO.getCompanyId()&& 0< supplierBO.getCompanyId()) {
                supplierVO.setCompanyId(supplierBO.getCompanyId());
            }
            voList = supplierDao.getlistSupplier(supplierVO);
            if (null != voList && voList.size() > 0 && !voList.isEmpty()) {
                int sNo = supplierBO.getRecordIndex();
                for (SupplierVO sVO : voList) {
                    SupplierBO sBO = new SupplierBO();
                    sBO.setsNO(++sNo);
                    sBO.setSupplierId(sVO.getSupplierId());
                    sBO.setSupplierName(sVO.getSupplierName());
                    sBO.setEmailId(sVO.getEmailId());
                    sBO.setAddress(sVO.getAddress());
                    sBO.setMobileNo(sVO.getMobileNo());
                    sBO.setCity(sVO.getCity());
                    sBO.setState(sVO.getState());
                    sBO.setCountry(sVO.getCountry());
                    sBO.setWebSite(sVO.getWebSite());
                    sBO.setTechOriented(sVO.getTechOriented());
                    sBO.setFinancialAmount(sVO.getFinancialAmount());
                    sBO.setRating(sVO.getRating());
                    sBO.setLocation(sVO.getLocation());
                    if(null != sVO.getProductServiceVO() && !sVO.getProductServiceVO().isEmpty()) {
                        InventoryBO inventoryBO = new InventoryBO();
                    inventoryBO.setServiceName(sVO.getProductServiceVO().get(0).getServiceName());
                        sBO.setProductServiceBO(inventoryBO);
                    }
                    boList.add(sBO);

                }
            }
        } catch (Exception ex) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("List Supplier has failed:" + ex.getMessage());
            }
            LOGGER.info("List Supplier has failed:" + ex.getMessage());
        } finally {
            LOGGER.exit();
        }

        return boList;
    }

	// View Based on Id
		@Override
		public SupplierBO selectsupplier(SupplierBO supplierB) {
			LOGGER.entry();
			try {
				SupplierVO suppliervo = new SupplierVO();
				suppliervo.setSupplierId(supplierB.getSupplierId());
				suppliervo = supplierDao.selectsupplier(suppliervo);
				if (null != suppliervo) {
					supplierB.setSupplierId(suppliervo.getSupplierId());
					supplierB.setSupplierName(suppliervo.getSupplierName());
					supplierB.setEmailId(suppliervo.getEmailId());
					supplierB.setAddress(suppliervo.getAddress());
					supplierB.setMobileNo(suppliervo.getMobileNo());
					supplierB.setCity(suppliervo.getCity());
					supplierB.setState(suppliervo.getState());
					supplierB.setCountry(suppliervo.getCountry());
					supplierB.setWebSite(suppliervo.getWebSite());
					supplierB.setLocation(suppliervo.getLocation());
					// supplierB.setLocation(suppliervo.getProductServiceVO().get(index));

					/*
					 * List<InventoryBO> inventoryList = new ArrayList<>(); for (InventoryVO
					 * supplierv : suppliervo.getProductServiceVO()) { InventoryBO inventorybO = new
					 * InventoryBO(); inventorybO.setServiceName(supplierv.getServiceName());
					 * inventorybO.setServiceId(supplierv.getServiceId());
					 * inventoryList.add(inventorybO); supplierB.setProductServiceBO(inventorybO); }
					 * supplierB.setProductServiceLisBO(inventoryList);
					 */
					
					List<SupplierProductBO> supplierProductList=new ArrayList<>();
					if(suppliervo.getSupplierProducts().size()>0) {
						for(SupplierProductVO supplierProduct: suppliervo.getSupplierProducts()) {
							SupplierProductBO supplierproductbo=new SupplierProductBO();
							supplierproductbo.setBuyingPrice(supplierProduct.getBuyingPrice());
							supplierproductbo.setTechOriented(supplierProduct.getTechOriented());
							supplierproductbo.setServiceName(supplierProduct.getProductServiceVO().getServiceName());
							supplierProductList.add(supplierproductbo);
							
						}
						supplierB.setSupplierProductBOList(supplierProductList);
						
					}

				}
				if (null != suppliervo) {
					HistoryVO historyvo = new HistoryVO();
					historyvo.setEntityId(supplierB.getSupplierId());
					historyvo.setEntityType("Supplier");
					historyvo.setActionType("View");
					// for getting login id...
					long loginId = getUserSecurity().getLoginId();
					historyvo.setUser(loginId);
					// for getting company id...
					long companyId = getUserSecurity().getCompanyId();
					historyvo.setCompanyId(companyId);
					activityhistoryDAO.activityLogHistory(historyvo);

				}
			} catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Edit Supplier has failed:" + ex.getMessage());
				}
				LOGGER.info("Edit Supplier has failed:" + ex.getMessage());
			} finally {
				LOGGER.exit();
			}
			return supplierB;

		}

	// Update Supplier
	@Override
	public SupplierBO supplierValueUpdate(SupplierBO sbo) {
		LOGGER.entry();
		SupplierVO supplierVO = new SupplierVO();
		InventoryBO inventory = new InventoryBO();
		try {
			if (null != sbo && 0 < sbo.getSupplierId());
			supplierVO.setSupplierId(sbo.getSupplierId());
			supplierVO.setSupplierName(sbo.getSupplierName());
			supplierVO.setEmailId(sbo.getEmailId());
			supplierVO.setAddress(sbo.getAddress());
			supplierVO.setDelete(false);
			supplierVO.setCity(sbo.getCity());
			supplierVO.setCountry(sbo.getCountry());
			supplierVO.setMobileNo(sbo.getMobileNo());
			supplierVO.setLocation(sbo.getLocation());
			supplierVO.setTechOriented(sbo.getTechOriented());
			supplierVO.setState(sbo.getState());
			supplierVO.setWebSite(sbo.getWebSite());
			supplierVO.setRating(sbo.getRating());
			supplierVO.setFinancialAmount(sbo.getFinancialAmount());

			if (null != sbo && null != sbo.getProductServiceBO()
					&& !(sbo.getProductServiceBO()).getServiceName().isEmpty()) {

				List<InventoryVO> productServiceList = new ArrayList<>();
				List<String> productStringList = new ArrayList<String>(
						Arrays.asList(sbo.getProductServiceBO().getServiceName().split(",")));

				for (String string : productStringList) {
					InventoryVO productServiceVO = new InventoryVO();
					Long serviceId = Long.parseLong(string);
					productServiceVO.setServiceId(serviceId);
					productServiceList.add(productServiceVO);
				}
				supplierVO.setProductServiceVO(productServiceList);

			}
			if (0 < sbo.getCompanyId()) {
				supplierVO.setCompanyId(sbo.getCompanyId());
			}

			sbo = supplierDao.supplierValueUpdate(supplierVO);
			if (null != supplierVO) {
				BeanUtils.copyProperties(supplierVO, sbo);
			}
			// ActivityLog History Dao Call.....
			if (null != sbo) {
				HistoryVO historyvo = new HistoryVO();
				historyvo.setEntityId(sbo.getSupplierId());
				historyvo.setEntityType("Supplier");
				historyvo.setActionType("Update");
				// for getting login id...
				long loginId = getUserSecurity().getLoginId();
				historyvo.setUser(loginId);
				// for getting company id...
				long companyId = getUserSecurity().getCompanyId();
				historyvo.setCompanyId(companyId);
				activityhistoryDAO.activityLogHistory(historyvo);
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit-update Supplier has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit-update Supplier has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return sbo;
	}

	// Delete Supplier
	@Override
	public Boolean deleteSupplier(SupplierBO supplierbo) {
		LOGGER.entry();
		Boolean status = false;
		SupplierVO suppliervo = new SupplierVO();
		try {
			suppliervo.setSupplierId(supplierbo.getSupplierId());
			suppliervo.setDelete(true);
			status = supplierDao.deleteSupplier(suppliervo);
			// ActivityLog History Dao call...
			if (status = true) {
				HistoryVO historyvo = new HistoryVO();
				historyvo.setEntityId(supplierbo.getSupplierId());
				historyvo.setEntityType("Supplier");
				historyvo.setActionType("Delete");
				// for getting login id...
				long loginId = getUserSecurity().getLoginId();
				historyvo.setUser(loginId);
				// for getting company id...
				long companyId = getUserSecurity().getCompanyId();
				historyvo.setCompanyId(companyId);
				activityhistoryDAO.activityLogHistory(historyvo);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Supplier has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Supplier has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return status;
	}

	// Search Supplier
	@Override
	public List<SupplierBO> searchByValue(SupplierBO supplierB) {
		SupplierVO suppliervo = new SupplierVO();
		List<SupplierVO> supplierListVO = new ArrayList<>();
		List<SupplierBO> supplierListBO = new ArrayList<>();
		try {
			suppliervo.setDelete(false);
			suppliervo.setActive(true);
			suppliervo.setSupplierName(supplierB.getSupplierName());

			suppliervo.setCompanyId(supplierB.getCompanyId());

			supplierListVO = supplierDao.searchByValue(suppliervo);
			if (null != supplierListVO && !supplierListVO.isEmpty() && 0 < supplierListVO.size()) {
				for (SupplierVO supplier : supplierListVO) {
					SupplierBO supplierbo = new SupplierBO();
					supplierbo.setSupplierId(supplier.getSupplierId());
					supplierbo.setSupplierName(supplier.getSupplierName());
					supplierbo.setEmailId(supplier.getEmailId());
					supplierbo.setWebSite(supplier.getWebSite());
					supplierListBO.add(supplierbo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierListBO;
	}

	@Override
	public long supplierCount(SupplierBO supplierBO) throws MySalesException {
		long count = 0;
		try {
			SupplierVO supplierVo = new SupplierVO();
			if(null != supplierBO.getCompanyId()&& 0< supplierBO.getCompanyId()) {
				supplierVo.setCompanyId(supplierBO.getCompanyId());
			}
			if(null!=supplierBO.getSupplierName()&& !supplierBO.getSupplierName().isEmpty()) {
				supplierVo.setSupplierName(supplierBO.getSupplierName());
			}
			count = supplierDao.supplierCount(supplierVo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean checksupplieremails(String emails,long id) {
		LOGGER.entry();
		boolean SupplierEmailCheck=false;
		try {
			SupplierEmailCheck=supplierDao.checksupplieremails(emails,id);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckSupplierEmail has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckSupplierEmail has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return SupplierEmailCheck; 
	}


	@Override
	public SupplierProductBO addProduct(SupplierProductBO supplierproductBO) {
		// TODO Auto-generated method stub
		SupplierProductVO supplierProductVO=new SupplierProductVO();
		
		supplierProductVO.setBuyingPrice(supplierproductBO.getBuyingPrice());;
		supplierProductVO.setTechOriented(supplierproductBO.getTechOriented());
		supplierProductVO.setDelete(false);
		
		SupplierVO supplierVO=new SupplierVO();
		supplierVO.setSupplierId(supplierproductBO.getSupplierId());
		supplierProductVO.setSupplierVO(supplierVO);
		
		if (null != supplierproductBO && null != supplierproductBO.getProductServiceBO()
				&& !(supplierproductBO.getProductServiceBO()).getServiceName().isEmpty()) {

			//List<InventoryVO> productServiceList = new ArrayList<>();
			InventoryVO productType=new InventoryVO();
			List<String> productStringList = new ArrayList<String>(
					Arrays.asList(supplierproductBO.getProductServiceBO().getServiceName().split(",")));

			for (String string : productStringList) {
				InventoryVO productServiceVO = new InventoryVO();
				Long serviceId = Long.parseLong(string);
				productServiceVO.setServiceId(serviceId);
				//productServiceList.add(productServiceVO);
				productType.setServiceId(productServiceVO.getServiceId());
			}
			//supplierProductVO.setProductServiceListVO(productServiceList);
			supplierProductVO.setProductServiceVO(productType);
		}
		if (0 < supplierproductBO.getCompanyId()) {
			supplierProductVO.setCompanyId(supplierproductBO.getCompanyId());
		}

		supplierproductBO = supplierDao.addProduct(supplierProductVO);
 		
		return supplierproductBO;
	}
}

