package com.scube.crm.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.ProductTypesBO;
import com.scube.crm.dao.ActivityHistoryDAO;
import com.scube.crm.dao.ProductServiceDao;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.vo.HistoryVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.ProductTypesVO;
import com.scube.crm.vo.User;

@Service
@Transactional
public class ProductServiceImpl extends ControllerUtils implements ProductService {

	@Autowired
	private ProductServiceDao productServiceDao;
	@Autowired
	private ActivityHistoryDAO activityhistoryDAO;
	static final MySalesLogger LOGGER = MySalesLogger.getLogger(ProductServiceImpl.class);

	@Override
	public InventoryBO createServices(InventoryBO productServiceBO) {

		User user = new User();
		InventoryVO productServiceVO = new InventoryVO();
		productServiceVO.setServiceName(productServiceBO.getServiceName());
		productServiceVO.setFees(productServiceBO.getFees());
		// productServiceVO.setDuration(productServiceBO.getDuration());
		productServiceVO.setMinimumStocks(productServiceBO.getMinimumStocks());
		productServiceVO.setMaximumStocks(productServiceBO.getMaximumStocks());
		productServiceVO.setAvailableStocks(productServiceBO.getAvailableStocks());
		productServiceVO.setStartDate(productServiceBO.getStartDate());
		productServiceVO.setEndDate(productServiceBO.getEndDate());
		productServiceVO.setServiceSpecification(productServiceBO.getServiceSpecification());
		productServiceVO.setCompanyId(productServiceBO.getCompanyId()); // Company Create
		productServiceVO.setIsDelete(false);
		productServiceVO.setIsActive(true);
		productServiceVO.setCreatedBy(productServiceBO.getCreatedBy());
		if (null != productServiceBO.getAdminLoginBO()) {
			user.setId(productServiceBO.getAdminLoginBO().getId());
			productServiceVO.setUserVO(user);
		}
		// product Types dropDown
		if (null != productServiceBO && null != productServiceBO.getProductTypesbO()
				&& !productServiceBO.getProductTypesbO().getProductTypes().isEmpty()) {
			String producttypesIds = productServiceBO.getProductTypesbO().getProductTypes();
			long id = 0;
			if (null != producttypesIds && !producttypesIds.isEmpty()) {
				id = Long.parseLong(producttypesIds);
			}
			ProductTypesVO typesVO = new ProductTypesVO();
			typesVO.setId(id);
			productServiceVO.setProductTypesvO(typesVO);
		}

		InventoryBO inventorybo = productServiceDao.createServices(productServiceVO);
		// ActivityLog History Dao call....
		if (null != inventorybo) {
			HistoryVO historyvo = new HistoryVO();
			historyvo.setEntityId(inventorybo.getServiceId());
			historyvo.setEntityType("ProductService");
			historyvo.setActionType("Create");
			// for getting login id...
			long loginId = getUserSecurity().getLoginId();
			historyvo.setUser(loginId);
			// for getting company id...
			historyvo.setCompanyId(productServiceBO.getCompanyId());
			activityhistoryDAO.activityLogHistory(historyvo);

		}
		return inventorybo;

	}

	// retrieve products
	@Override
	public List<InventoryBO> listservice(InventoryBO serviceBO) {
		LOGGER.entry();
		InventoryVO productServiceVO = new InventoryVO();
		List<InventoryVO> serviceList = new ArrayList<InventoryVO>();
		List<InventoryBO> serviceListBO = new ArrayList<InventoryBO>();
		try {
			productServiceVO.setIsDelete(false);
			productServiceVO.setIsActive(true);
			productServiceVO.setRecordIndex(serviceBO.getRecordIndex());
			productServiceVO.setMaxRecord(serviceBO.getMaxRecord());
			productServiceVO.setServiceName(serviceBO.getServiceName());
			if(null != serviceBO.getCompanyId()&& 0< serviceBO.getCompanyId()) {
			productServiceVO.setCompanyId(serviceBO.getCompanyId()); // Company
			}
			// product Types dropDown

			ProductTypesVO ptypesvo = new ProductTypesVO();
			if (null != serviceBO.getServiceName()&&!serviceBO.getServiceName().isEmpty()) {
				productServiceVO.setServiceName(serviceBO.getServiceName());
			}

			if (null != serviceBO && null != serviceBO.getProductTypesbO()
					&& 0 < serviceBO.getProductTypesbO().getProductTypesId()) {

				ptypesvo.setId(serviceBO.getProductTypesbO().getProductTypesId());

			}
			productServiceVO.setProductTypesvO(ptypesvo);

			serviceList = productServiceDao.listViewService(productServiceVO);
			int i = 1;
			if (serviceList != null && serviceList.size() > 0 && !serviceList.isEmpty()) {
				int data = serviceBO.getRecordIndex();
				/* serviceList.forEach(ProductService->{ */
				for (InventoryVO productService : serviceList) {
					InventoryBO productServiceBO = new InventoryBO();
					ProductTypesBO productTypesBO = new ProductTypesBO();
					productServiceBO.setServiceId(productService.getServiceId());
					productServiceBO.setServiceName(productService.getServiceName());
					productServiceBO.setServiceSpecification(productService.getServiceSpecification());
					productServiceBO.setsNo(i);
					i++;
					// double to int
					double rs = productService.getFees();
					int rupees = (int) rs;
					productServiceBO.setRupees(rupees);
					// productServiceBO.setDuration(productService.getDuration());
					productServiceBO.setMinimumStocks(productService.getMinimumStocks());
					productServiceBO.setMaximumStocks(productService.getMaximumStocks());
					productServiceBO.setAvailableStocks(productService.getAvailableStocks());
					//					SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
					//					String startDate = sim.format(productService.getStartDate());
					productServiceBO.setBeginDate(productService.getStartDate());
					//					String enddate = sim.format(productService.getEndDate());
					productServiceBO.setLastDate(productService.getEndDate());
					// productType view

					productTypesBO.setProductTypes(productService.getProductTypesvO().getProductTypes());
					productTypesBO.setProductTypesId(productService.getProductTypesvO().getId());
					productServiceBO.setProductTypesbO(productTypesBO);
					serviceListBO.add(productServiceBO);

				}
			}
			// }
			// }
			return serviceListBO;
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("service list has failed:" + ex.getMessage());
			}
			LOGGER.info("Service list has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return serviceListBO;
	}

	@Override
	public InventoryBO getServiceObject(InventoryBO productServiceBO) {
		// TODO Auto-generated method stub
		InventoryVO productServiceVO = new InventoryVO();
		// ProductTypesVO productTypeVO = new ProductTypesVO();

		productServiceVO.setServiceId(productServiceBO.getServiceId());
		productServiceVO.setCompanyId(productServiceBO.getCompanyId());
		productServiceVO = productServiceDao.getServiceObject(productServiceVO);
		if (null != productServiceVO) {
			//			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			//			String startDate = sim.format(productServiceVO.getStartDate());
			//			productServiceBO.setBeginDate(startDate);
			//			String enddate = sim.format(productServiceVO.getEndDate());
			//			productServiceBO.setLastDate(enddate);

			productServiceBO.setStartDate(productServiceVO.getStartDate());
			productServiceBO.setEndDate(productServiceVO.getEndDate());
			productServiceBO.setServiceName(productServiceVO.getServiceName());
			productServiceBO.setServiceId(productServiceVO.getServiceId());
			productServiceBO.setFees(productServiceVO.getFees());
			// productServiceBO.setDuration(productServiceVO.getDuration());
			productServiceBO.setMinimumStocks(productServiceVO.getMinimumStocks());
			productServiceBO.setMaximumStocks(productServiceVO.getMaximumStocks());
			productServiceBO.setAvailableStocks(productServiceVO.getAvailableStocks());
			productServiceBO.setStartDate(productServiceVO.getStartDate());
			productServiceBO.setEndDate(productServiceVO.getEndDate());
			productServiceBO.setServiceSpecification(productServiceVO.getServiceSpecification());
			// ProductTypes
			ProductTypesBO productTypesBO = new ProductTypesBO();
			productTypesBO.setProductTypesId(productServiceVO.getProductTypesvO().getId());
			productTypesBO.setProductTypes(productServiceVO.getProductTypesvO().getProductTypes());
			productServiceBO.setProductTypesbO(productTypesBO);
		}

		return productServiceBO;
	}

	@Override
	public Boolean serviceUpdate(InventoryBO productServiceBo) {

		InventoryVO productServiceVO = new InventoryVO();
		InventoryVO serviceVO = new InventoryVO();
		productServiceVO.setCompanyId(productServiceBo.getCompanyId()); // Company
		User user = new User();
		if (0 != productServiceBo.getServiceId()) {
			productServiceVO.setServiceId(productServiceBo.getServiceId());
			serviceVO = productServiceDao.retrieveService(productServiceVO);
		}
		productServiceVO.setServiceName(productServiceBo.getServiceName());
		productServiceVO.setServiceSpecification(productServiceBo.getServiceSpecification());
		productServiceVO.setServiceId(productServiceBo.getServiceId());
		productServiceVO.setFees(productServiceBo.getFees());
		// productServiceVO.setDuration(productServiceBo.getDuration());
		productServiceVO.setMinimumStocks(productServiceBo.getMinimumStocks());
		productServiceVO.setMaximumStocks(productServiceBo.getMaximumStocks());
		productServiceVO.setAvailableStocks(productServiceBo.getAvailableStocks());
		productServiceVO.setStartDate(productServiceBo.getStartDate());
		productServiceVO.setEndDate(productServiceBo.getEndDate());
		productServiceVO.setIsDelete(serviceVO.getIsDelete());
		productServiceVO.setIsActive(serviceVO.getIsActive());
		productServiceVO.setCreatedBy(serviceVO.getCreatedBy());
		productServiceVO.setCompanyId(productServiceBo.getCompanyId()); // Company Update
		if (null != productServiceBo.getAdminLoginBO()) {
			user.setId(productServiceBo.getAdminLoginBO().getId());
			productServiceVO.setUserVO(user);
		}
		// productTypes

		/*
		 * if (null != productServiceBo && null != productServiceBo.getProductTypesbO()
		 * && !productServiceBo.getProductTypesbO().getProductTypes().isEmpty()) {
		 * String producttypesIds =
		 * productServiceBo.getProductTypesbO().getProductTypes(); long id = 0; if
		 * (null!= producttypesIds && !producttypesIds.isEmpty()){ id
		 * =Long.parseLong(producttypesIds); } ProductTypesVO typesVO = new
		 * ProductTypesVO(); typesVO.setId(id);
		 * productServiceVO.setProductTypesvO(typesVO); }
		 */
		
		// service - products 
					if (null != productServiceBo.getProductTypesbO() && 0<productServiceBo.getProductTypesbO().getProductTypesId()) {
						ProductTypesVO typesVO = new ProductTypesVO();
						typesVO.setId(productServiceBo.getProductTypesbO().getProductTypesId());
						productServiceVO.setProductTypesvO(typesVO);
					}

		Boolean status = productServiceDao.serviceUpdateDao(productServiceVO);
		// ActivityLog History Dao call...
		if (status = true) {
			HistoryVO historyvo = new HistoryVO();
			historyvo.setEntityId(productServiceBo.getServiceId());
			historyvo.setEntityType("ProductService");
			historyvo.setActionType("Update");
			// for getting login id...
			long loginId = getUserSecurity().getLoginId();
			historyvo.setUser(loginId);
			// for getting company id...
			historyvo.setCompanyId(productServiceBo.getCompanyId());
			activityhistoryDAO.activityLogHistory(historyvo);

		}
		return status;
	}

	@Override
	public Boolean deleteService(InventoryBO service) {

		InventoryVO productServiceVo = new InventoryVO();
		productServiceVo.setServiceId(service.getServiceId());
		// productServiceVo.setIsActive(false);
		productServiceVo.setIsDelete(true);
		Boolean status = productServiceDao.deleteService(productServiceVo);
		// ActivityLog History Dao call...
		if (status = true) {
			HistoryVO historyvo = new HistoryVO();
			historyvo.setEntityId(service.getServiceId());
			historyvo.setEntityType("ProductService");
			historyvo.setActionType("Delete");
			// for getting login id...
			long loginId = getUserSecurity().getLoginId();
			historyvo.setUser(loginId);
			// for getting company id...
			long companyId = getUserSecurity().getCompanyId();
			historyvo.setCompanyId(companyId);
			activityhistoryDAO.activityLogHistory(historyvo);
		}
		return status;

	}

	@Override
	public boolean isValidServiceName(InventoryBO productServiceBO) {

		InventoryVO productServiceVO = new InventoryVO();

		productServiceVO.setServiceName(productServiceBO.getServiceName());
		productServiceVO.setIsDelete(false);
		return productServiceDao.isValidServiceName(productServiceVO);
	}

	@Override
	public List<InventoryBO> listOfProduct() {

		List<InventoryBO> productBOList = new ArrayList<>();
		List<InventoryVO> productVOList = new ArrayList<>();

		productVOList = productServiceDao.listOfProduct();
		if (null != productVOList && !productVOList.isEmpty() && 0 < productVOList.size()) {
			for (InventoryVO productServiceVO : productVOList) {
				InventoryBO productServiceBO = new InventoryBO();
				productServiceBO.setServiceId(productServiceVO.getServiceId());
				productServiceBO.setServiceName(productServiceVO.getServiceName());
				productServiceBO.setServiceSpecification(productServiceVO.getServiceSpecification());
				double fs = productServiceVO.getFees();
				productServiceBO.setFees(fs);
				productServiceBO.setStartDate(productServiceVO.getStartDate());
				productServiceBO.setStartDate(productServiceVO.getEndDate());
				productBOList.add(productServiceBO);
			}
		}

		return productBOList;
	}

	@Override
	public List<InventoryBO> listOfProductName(InventoryBO inventoryBO) {

		List<InventoryBO> productServiceBOList = new ArrayList<>();
		List<InventoryVO> productServiceVOList = new ArrayList<>();
		InventoryVO productVO = new InventoryVO();
		productVO.setServiceName(inventoryBO.getServiceName());
		productVO.setCompanyId(inventoryBO.getCompanyId());
		productVO.setIsDelete(false);
		productServiceVOList = productServiceDao.listOfProductName(productVO);
		if (null != productServiceVOList && !productServiceVOList.isEmpty() && 0 < productServiceVOList.size()) {
			for (InventoryVO productServiceVO : productServiceVOList) {
				InventoryBO productBO = new InventoryBO();
				productBO.setServiceId(productServiceVO.getServiceId());
				productBO.setServiceName(productServiceVO.getServiceName());
				productServiceBOList.add(productBO);
			}
		}

		return productServiceBOList;
	}

	@Override
	public List<InventoryBO> listOfServiceId(List<InventoryBO> productServiceBO) {
		// TODO Auto-generated method stub
		InventoryVO productVO = new InventoryVO();
		List<InventoryBO> productBOList = new ArrayList<>();
		List<InventoryVO> productVOList = new ArrayList<>();
		for (InventoryBO productServicBO : productServiceBO) {
			productVO.setServiceId(productServicBO.getServiceId());
			productVOList = productServiceDao.listOfServiceId(productVO);
			for (InventoryVO productServiceVO : productVOList) {
				InventoryBO productBO = new InventoryBO();
				productBO.setServiceId(productServiceVO.getServiceId());
				productBO.setServiceName(productServiceVO.getServiceName());
				productBO.setServiceSpecification(productServiceVO.getServiceSpecification());
				productBO.setFees(productServiceVO.getFees());
				// productBO.setDuration(productServiceVO.getDuration());
				productBO.setStartDate(productServiceVO.getStartDate());
				productBO.setEndDate(productServiceVO.getEndDate());
				productBO.setCreated(productServiceVO.getCreated());
				productBO.setCreatedBy(productServiceVO.getCreatedBy());
				productBO.setModified(productVO.getModified());
				productBO.setModifiedBy(productVO.getModifiedBy());
				productBOList.add(productBO);
			}
		}
		return productBOList;
	}

	@Override
	public InventoryBO retriveServiceById(InventoryBO productBO) {

		InventoryVO productVO = new InventoryVO();
		if (0 < productBO.getServiceId()) {
			productVO.setServiceId(productBO.getServiceId());
		}
		productVO.setCompanyId(productBO.getCompanyId()); // companyId
		productVO = productServiceDao.retriveServiceById(productVO);
		if (null != productVO) {
			productBO.setServiceId(productVO.getServiceId());
			productBO.setServiceName(productVO.getServiceName());
			productBO.setServiceSpecification(productBO.getServiceSpecification());
			// productBO.setDuration(productVO.getDuration());
			productBO.setFees(productVO.getFees());
			productBO.setStartDate(productVO.getStartDate());
			productBO.setEndDate(productVO.getEndDate());
			productBO.setCreated(productVO.getCreated());
			productBO.setCreatedBy(productVO.getCreatedBy());
			productBO.setModified(productVO.getModified());
			productBO.setModifiedBy(productVO.getModifiedBy());

			ProductTypesBO productTypesbO = new ProductTypesBO();
			productTypesbO.setProductTypes(productVO.getProductTypesvO().getProductTypes());
			productBO.setProductTypesbO(productTypesbO);

		}
		return productBO;
	}

	@Override
	public long getCountOfProduct(InventoryBO productServiceBO) {

		long countOfProduct = 0;
		ProductTypesVO productTypesVO= new ProductTypesVO();
		InventoryVO productServiceVO = new InventoryVO();
		productServiceVO.setIsDelete(false);
		if(null != productServiceBO.getCompanyId()&& 0< productServiceBO.getCompanyId()) {
		productServiceVO.setCompanyId(productServiceBO.getCompanyId());// CompanyId related retrieve purpose...
		}
		if (null!=productServiceBO.getProductTypesbO() && 0< productServiceBO.getProductTypesbO().getProductTypesId()) {
			productTypesVO.setId(productServiceBO.getProductTypesbO().getProductTypesId());
			productServiceVO.setProductTypesvO(productTypesVO);
		}
		if (null != productServiceBO.getServiceName() &&!productServiceBO.getServiceName().isEmpty()) {
			productServiceVO.setServiceName( productServiceBO.getServiceName());
		}
		countOfProduct = productServiceDao.getCountOfProduct(productServiceVO);
		return countOfProduct;
	}

	@Override
	public List<InventoryBO> listOfProductByPagination(InventoryBO productServiceBO) {

		InventoryVO productServiceVO = new InventoryVO();
		ProductTypesVO productTypesVO= new ProductTypesVO();
		List<InventoryBO> productBOList = new ArrayList<>();
		List<InventoryVO> productVOList = new ArrayList<>();
		
		productServiceVO.setRecordIndex(productServiceBO.getRecordIndex());
		productServiceVO.setMaxRecord(productServiceBO.getMaxRecord());
		productServiceVO.setIsDelete(false);
		if(null != productServiceBO.getCompanyId() && 0< productServiceBO.getCompanyId() ) {
		productServiceVO.setCompanyId(productServiceBO.getCompanyId());// company Based retriew
	}
		if (null!=productServiceBO.getProductTypesbO() && 0< productServiceBO.getProductTypesbO().getProductTypesId()) {
			productTypesVO.setId(productServiceBO.getProductTypesbO().getProductTypesId());
			productServiceVO.setProductTypesvO(productTypesVO);
		}
		
		if (null != productServiceBO.getServiceName() &&!productServiceBO.getServiceName().isEmpty()) {
			productServiceVO.setServiceName( productServiceBO.getServiceName());
		}
		productVOList = productServiceDao.listOfProductByPagination(productServiceVO);
		if (null != productVOList && !productVOList.isEmpty() && 0 < productVOList.size()) {
			int sNo = productServiceBO.getRecordIndex();
			for (InventoryVO productServiceVO2 : productVOList) {
				InventoryBO productBO = new InventoryBO();
				productBO.setServiceId(productServiceVO2.getServiceId());
				productBO.setsNo(++sNo);
				productBO.setServiceName(productServiceVO2.getServiceName());
				productBO.setMaximumStocks(productServiceVO2.getMaximumStocks());
				productBO.setMinimumStocks(productServiceVO2.getMinimumStocks());
				productBO.setAvailableStocks(productServiceVO2.getAvailableStocks());

				// productBO.setDuration(productServiceVO2.getDuration());
				double d = (productServiceVO2.getFees());
				int fees = (int) d;
				productBO.setRupees(fees);

				// productType list
				if (null != productServiceVO2.getProductTypesvO()) {
					ProductTypesBO productTypesBO = new ProductTypesBO();
					productTypesBO.setProductTypes(productServiceVO2.getProductTypesvO().getProductTypes());
					productTypesBO.setProductTypesId(productServiceVO2.getProductTypesvO().getId());
					productBO.setProductTypesbO(productTypesBO);

				}
				productBOList.add(productBO);
			}
		}

		return productBOList;
	}

	@Override
	public long countOfProductBySearch(InventoryBO productServiceBO) {

		long countOfProduct = 0;

		InventoryVO productVO = new InventoryVO();
        if(null != productServiceBO.getCompanyId()&& 0< productServiceBO.getCompanyId()) {
		productVO.setCompanyId(productServiceBO.getCompanyId()); // company
        }
		// productTypes count
		ProductTypesVO productTypesVO = new ProductTypesVO();
		if (null != productServiceBO.getServiceName() &&!productServiceBO.getServiceName().isEmpty()) {
			productVO.setServiceName(productServiceBO.getServiceName());
		}
		if (null != productServiceBO.getProductTypesbO() && 0 < productServiceBO.getProductTypesbO().getProductTypesId()) {
			productTypesVO.setId(productServiceBO.getProductTypesbO().getProductTypesId());
			productVO.setProductTypesvO(productTypesVO);
		}

		productVO.setIsDelete(false);

		countOfProduct = productServiceDao.countOfProductBySearch(productVO);

		return countOfProduct;

	}

	@Override
	public InventoryBO getProductObjectByName(InventoryBO serviceBO) {
		ProductTypesVO vo = new ProductTypesVO();
		InventoryVO serviceVO = new InventoryVO();
		if (null != serviceBO.getServiceName()) {
			serviceVO.setServiceName(serviceBO.getServiceName());
		}
		//		if(null!=serviceBO.getProductTypesbO().getProductTypes()) {
		//			vo.setProductTypes(serviceBO.getProductTypesbO().getProductTypes());
		//			serviceVO.setProductTypesvO(vo);
		//		}
		serviceVO = productServiceDao.getProductObjectByName(serviceVO);
		if (null != serviceVO) {
			serviceBO.setServiceId(serviceVO.getServiceId());
			serviceBO.setServiceName(serviceVO.getServiceName());
		}

		return serviceBO;
	}

	@Override
	public InventoryBO getProducts(long serviceId) {
		InventoryVO productServiceVO = new InventoryVO();
		InventoryBO productServiceBo = new InventoryBO();

		productServiceVO = productServiceDao.getProducts(serviceId);
		if (null != productServiceVO) {
			productServiceBo.setServiceId(productServiceVO.getServiceId());
			productServiceBo.setServiceName(productServiceVO.getServiceName());
			productServiceBo.setServiceSpecification(productServiceVO.getServiceSpecification());
			productServiceBo.setMinimumStocks(productServiceVO.getMinimumStocks());
			productServiceBo.setMaximumStocks(productServiceVO.getMaximumStocks());
			productServiceBo.setAvailableStocks(productServiceVO.getAvailableStocks());
			productServiceBo.setStartDate(productServiceVO.getStartDate());
			productServiceBo.setEndDate(productServiceVO.getEndDate());
			if (null != productServiceVO.getProductTypesvO()) {
				ProductTypesBO productTypesBO = new ProductTypesBO();
				productTypesBO.setProductTypes(productServiceVO.getProductTypesvO().getProductTypes());
				productTypesBO.setProductTypesId(productServiceVO.getProductTypesvO().getId());
				productServiceBo.setProductTypesbO(productTypesBO);

			}

		}
		// ActivityLog History Dao call....
		if (null != productServiceVO) {
			HistoryVO historyvo = new HistoryVO();
			historyvo.setEntityId(serviceId);
			historyvo.setEntityType("ProductService");
			historyvo.setActionType("View");
			// for getting login id...
			long loginId = getUserSecurity().getLoginId();
			historyvo.setUser(loginId);
			// for getting company id....
			long companyId = getUserSecurity().getCompanyId();
			historyvo.setCompanyId(companyId);
			activityhistoryDAO.activityLogHistory(historyvo);
		}
		return productServiceBo;
	}

	@Override
	public boolean checkProductName(String serviceName, long id) {
		boolean checkProductName=false;
		checkProductName=productServiceDao.checkProductName(serviceName,id);
		return checkProductName;
	}

}
