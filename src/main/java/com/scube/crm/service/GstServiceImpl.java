package com.scube.crm.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.crm.bo.GstBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.PriceBookBO;
import com.scube.crm.dao.GstDAO;
import com.scube.crm.dao.ProductServiceDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.Company;
import com.scube.crm.vo.GstVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.User;

@Service
@Transactional
public class GstServiceImpl implements GstService{

	@Autowired
	private ProductServiceDao productServiceDao;
	@Autowired 
	private GstDAO gstDao;

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(GstServiceImpl.class);

	@Override
	public GstBO createGstValues(GstBO gstBO) throws MySalesException{
		LOGGER.entry();
		GstVO gstVO=new GstVO();
		InventoryVO serviceVO = new InventoryVO();
		try {
		User user = new User();
		gstVO.setSgst(gstBO.getSgst());
		gstVO.setCgst(gstBO.getCgst());
		gstVO.setStartDate(gstBO.getStartDate());
		gstVO.setCompanyId(gstBO.getCompanyId());
		String product = gstBO.getProduct().getServiceName();
		Integer ids=Integer.parseInt(product);
		serviceVO.setServiceId(ids);
		serviceVO.setServiceName(product);
		gstVO.setProduct(serviceVO);
		gstVO.setIsDelete(false);
		gstVO.setIsActive(true);
		gstVO.setCreatedBy(gstBO.getCreatedBy());
		if (null != gstBO.getAdminLoginBO()) {
			user.setId(gstBO.getAdminLoginBO().getId());
			gstVO.setUserVO(user);
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Create GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return  gstDao.createGstValues(gstVO);
	}

	@Override
	public List<GstBO> getListGst(GstBO gstBo)throws MySalesException {
	LOGGER.entry();
		GstVO gstVO=new GstVO();
		List<GstVO> gstListVO=new ArrayList<GstVO>();
		List<GstBO> gstListBO=new ArrayList<GstBO>();
		InventoryBO serviceBO=new InventoryBO();
		try {
			gstVO.setIsDelete(false);
			gstVO.setIsActive(true);
			gstVO.setCgst(gstBo.getCgst());
			gstVO.setSgst(gstBo.getSgst());
			gstVO.setRecordIndex(gstBo.getRecordIndex());
			gstVO.setMaxRecord(gstBo.getMaxRecord());
			if(null != gstBo.getCompanyId()&& 0< gstBo.getCompanyId()) {
			gstVO.setCompanyId(gstBo.getCompanyId());
			}
			if(null!=gstBo.getStartDate()) {
				gstVO.setStartDate(gstBo.getStartDate());
			}
			gstListVO=gstDao.getListGst(gstVO);

			if(null!=gstListVO && gstListVO.size()>0 && !gstListVO.isEmpty()){
				int data=gstBo.getRecordIndex();
				for (GstVO gstVo : gstListVO) {
					InventoryBO inventoryBO=new InventoryBO();
					GstBO gstBo1=new GstBO();
					gstBo1.setGstId(gstVo.getGstId());
					gstBo1.setsNo(++data);
					gstBo1.setCgst(gstVo.getCgst());
					gstBo1.setSgst(gstVo.getSgst());
					/*
					 * if(0<=gstVo.getProduct().getServiceId()){ InventoryBO productServiceBO = new
					 * InventoryBO(); InventoryVO productServiceVO=new InventoryVO(); long productId
					 * = gstVo.getProduct().getServiceId();
					 * 
					 * productServiceVO.setServiceId(gstVo.getProduct().getServiceId());
					 * productServiceVO = productServiceDao.getServiceObject(productServiceVO);
					 * String name=productServiceVO.getServiceName();
					 * productServiceBO.setServiceId(productId);
					 * productServiceBO.setServiceName(name); gstBo1.setProduct(productServiceBO); }
					 */
					if(null!=gstVo.getProduct()
							&&null!=gstVo.getProduct().getServiceName()) {
						inventoryBO.setServiceName(gstVo.getProduct().getServiceName());
					}
					gstBo1.setProduct(inventoryBO);
					
					// ProductTypesVO productTypeVO = new ProductTypesVO();
					SimpleDateFormat sim1=new SimpleDateFormat("yyyy-MM-dd");
					String startDate1=sim1.format(gstVo.getStartDate());
					gstBo1.setBeginDate(startDate1);
					gstListBO.add(gstBo1);
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view GST has failed:" + ex.getMessage());
			}
			LOGGER.info("view GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return gstListBO;
	}

	@Override
	public GstBO getGstValues(GstBO gstBO)throws MySalesException {
		LOGGER.entry();
		try {
		GstVO gstVO=new GstVO();
		gstVO.setGstId(gstBO.getGstId());
		
		gstVO= gstDao.getGstValues(gstVO);
		if(null!=gstVO){
			SimpleDateFormat sim=new SimpleDateFormat("MM/dd/yyyy");
			String startDate=sim.format(gstVO.getStartDate());
			gstBO.setBeginDate(startDate);
			gstBO.setGstId(gstVO.getGstId());
			gstBO.setCgst(gstVO.getCgst());
			gstBO.setSgst(gstVO.getSgst());
			InventoryBO productServiceBO = new InventoryBO();
			InventoryVO productServiceVO=new InventoryVO();
		long productId = gstVO.getProduct().getServiceId();
		productServiceVO.setServiceId(gstVO.getProduct().getServiceId());
		productServiceVO = productServiceDao.getServiceObject(productServiceVO);
		String name=productServiceVO.getServiceName();
		productServiceBO.setServiceId(productId);
		productServiceBO.setServiceName(name);
		gstBO.setProduct(productServiceBO);
		
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return gstBO;
	}

	@Override
	public boolean gstValueUpdate(GstBO gstBO)throws MySalesException {
		LOGGER.entry();
		GstVO gstVO=new GstVO();
		GstVO gstVo=new GstVO();
		User user=new User();
		InventoryVO serviceVO = new InventoryVO();
		try {
			if(null!=gstBO && 0<gstBO.getGstId())
				gstVO.setSgst(gstBO.getSgst());
			gstVO.setCgst(gstBO.getCgst());
			gstVO.setGstId(gstBO.getGstId());
//			String product = gstBO.getProduct().getServiceName();
//			Integer ids=Integer.parseInt(product);
//			serviceVO.setServiceId(ids);
//			serviceVO.setServiceName(product);
//			gstVO.setProduct(serviceVO);
			gstVO.setCompanyId(gstBO.getCompanyId());//company
			SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date startDate = inputFormat.parse(gstBO.getBeginDate());

			gstVO.setStartDate(startDate);
			gstVO.setIsDelete(false);
			gstVO.setIsActive(true);
			InventoryVO productServiceVO=new InventoryVO();
		long productId = gstBO.getProduct().getServiceId();
		productServiceVO.setServiceId(productId);
		productServiceVO = productServiceDao.getServiceObject(productServiceVO);
		String name=productServiceVO.getServiceName();
		productServiceVO.setServiceName(name);
		gstVO.setProduct(productServiceVO);
			gstVO.setModifiedBy(gstBO.getAdminLoginBO().getId());
			if (null != gstBO.getAdminLoginBO()) {
				user.setId(gstBO.getAdminLoginBO().getId());
				gstVO.setUserVO(user);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Update GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return gstDao.gstUpdateValues(gstVO);
	}

	@Override
	public Boolean deleteGstValues(GstBO gstBO)throws MySalesException {
		LOGGER.entry();
		GstVO gstVO=new GstVO();
		try {
		gstVO.setGstId(gstBO.getGstId());
		gstVO.setIsActive(false);
		gstVO.setIsDelete(true);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deleted GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Deleted GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return gstDao.deleteGstValues(gstVO);

	}

	@Override
	public long countOfGst(GstBO gstBO) {
		// TODO Auto-generated method stub

		long countOfGst=0;
		GstVO gstVO=new GstVO();
		gstVO.setIsDelete(false);
		if (null != gstBO.getCompanyId()) { // company based retrieve condition
			gstVO.setCompanyId(gstBO.getCompanyId());
		}
		countOfGst=gstDao.countOfGst(gstVO);

		return countOfGst;
	}

	@Override
	public List<GstBO> listOfGstByPagination(GstBO gstBO) {
		// TODO Auto-generated method stub

		GstVO gstVO=new GstVO();
		List<GstVO> gstVOList=new ArrayList<>();
		List<GstBO> gstBOList=new ArrayList<>();

		gstVO.setRecordIndex(gstBO.getRecordIndex());
		gstVO.setMaxRecord(gstBO.getMaxRecord());
		gstVO.setIsDelete(false);
		if(null != gstBO.getCompanyId()&& 0< gstBO.getCompanyId() ) {
		gstVO.setCompanyId(gstBO.getCompanyId());
		}
		if(null != gstBO.getSgst()&&! gstBO.getSgst().isEmpty() ) {
			gstVO.setSgst(gstBO.getSgst());
			}
		if(null != gstBO.getCgst()&&!gstBO.getCgst().isEmpty() ) {
			gstVO.setCgst(gstBO.getCgst());
			}
		if(null != gstBO.getStartDate() ) {
			gstVO.setStartDate(gstBO.getStartDate());
			}

		
		gstVOList=gstDao.listOfGstByPagination(gstVO);
		if(null!=gstVOList&&!gstVOList.isEmpty()&&0<gstVOList.size()) {
			int sNo=gstBO.getRecordIndex();
			for (GstVO gstVO2 : gstVOList) {
				GstBO gstBO1=new GstBO();
				InventoryBO productServiceBO = new InventoryBO();
				InventoryVO productServiceVO=new InventoryVO();
				gstBO1.setGstId(gstVO2.getGstId());
				gstBO1.setCgst(gstVO2.getCgst());
				gstBO1.setSgst(gstVO2.getSgst());
				gstBO1.setsNo(++sNo);
			//	gstBO1.setProduct(gstVO2.getProduct());
				SimpleDateFormat simple=new SimpleDateFormat("yyyy/MM/dd");
				String sDate=simple.format(gstVO2.getStartDate());
				
				  if(null!= gstVO2.getProduct() && 0<productServiceVO.getServiceId()) {
				  productServiceVO.setServiceId(gstVO2.getProduct().getServiceId()); }
				  productServiceVO = productServiceDao.getServiceObject(productServiceVO); long
				  productId = gstVO2.getProduct().getServiceId(); String
				  name=productServiceVO.getServiceName();
				  productServiceBO.setServiceId(productId);
				  productServiceBO.setServiceName(name); gstBO1.setProduct(productServiceBO);
				 
				if(null!=gstVO2.getProduct()
						&&null!=gstVO2.getProduct().getServiceName()) {
					productServiceBO.setServiceName(gstVO2.getProduct().getServiceName());
				}
				gstBO1.setProduct(productServiceBO);
				gstBO1.setBeginDate(sDate);
				gstBOList.add(gstBO1);
			}
		}

		return gstBOList;
	}

	@Override
	public long countOfGstBySearch(GstBO gstBo)throws MySalesException {
		LOGGER.entry();
		long countOfGst=0;
		try {
		GstVO gstVO=new GstVO();
		if(null!=gstBo) {
			gstVO.setSgst(gstBo.getSgst());
			gstVO.setCgst(gstBo.getCgst());
			gstVO.setStartDate(gstBo.getStartDate());
			if(null != gstBo.getCompanyId() && 0< gstBo.getCompanyId()) {
			gstVO.setCompanyId(gstBo.getCompanyId());
			}
			if(null!=gstBo.getProduct()){
				InventoryVO productServiceVO=new InventoryVO();
				if(null!=gstBo.getProduct() && 
						0<gstBo.getProduct().getServiceId()){
					long productId=gstBo.getProduct().getServiceId();
					productServiceVO.setServiceId(productId);
				} 
				gstVO.setProduct(productServiceVO);
			}
			gstVO.setIsDelete(false);
		}

		countOfGst=gstDao.countOfGstBySearch(gstVO);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search GST has failed:" + ex.getMessage());
			}
			LOGGER.info("Search GST has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return countOfGst;
	}

	@Override
	public boolean checkSgstValue(String sgst) throws MySalesException {
		boolean checkSgstValue=false;
		checkSgstValue=gstDao.checkSgstValue(sgst);
		return checkSgstValue;
	}

	@Override
	public boolean checkCgstValue(String cgst) throws MySalesException {
		boolean checkCgstValue=false;
		checkCgstValue=gstDao.checkCgstValue(cgst);
		return checkCgstValue;
	}

	@Override
	public GstBO getGst(long gstId) {
		GstBO gstBO=new GstBO();
		GstVO gstVO = gstDao.getGst(gstId);
		if(null!=gstVO){
			gstBO.setGstId(gstVO.getGstId());
			gstBO.setCgst(gstVO.getCgst());
			gstBO.setSgst(gstVO.getSgst());
			
		}
		return gstBO; 
	}

	@Override
	public boolean checkProduct(Long productId, long id) {
		boolean checkProductName=false;
		checkProductName=gstDao.checkProduct(productId,id);
		return checkProductName;
	}

}
