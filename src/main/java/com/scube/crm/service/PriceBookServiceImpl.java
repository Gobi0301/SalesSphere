package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.PriceBookBO;
import com.scube.crm.bo.SupplierBO;
import com.scube.crm.dao.PriceBookDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SupplierVO;
import com.scube.crm.vo.User;

@Service("PriceBookService")
@Transactional
public class PriceBookServiceImpl  implements PriceBookService{
	static final MySalesLogger LOGGER = MySalesLogger.getLogger(PriceBookServiceImpl .class);

	@Autowired
	private PriceBookDao priceBookDao;
	
	@Override
	public PriceBookBO createPriceBook(PriceBookBO priceBookBO) throws MySalesException {
		// TODO Auto-generated method stub
		PriceBookServiceImpl.LOGGER.entry();
		User users= new User();
		PriceBookBO priceBook = new PriceBookBO();
		PriceBookVO priceBookVO = new PriceBookVO();
		try {
	if(priceBookBO.getActive().equals(true)) {
		priceBookVO.setActive(true);
	}else {
		priceBookVO.setActive(false);
	}	
		users.setId(priceBookBO.getUser().getId());
		priceBookVO.setUser(users);
		priceBookVO.setIsDeleted(false);
		priceBookVO.setPriceBookId(priceBookBO.getPriceBookId());
		priceBookVO.setCreatedBy(priceBookBO.getCreatedBy());
		priceBookVO.setPriceBookOwner(priceBookBO.getPriceBookOwner());
		priceBookVO.setPriceBookName(priceBookBO.getPriceBookName());
		priceBookVO.setDescription(priceBookBO.getDescription());
		priceBookVO.setPrice(priceBookBO.getPrice());
		priceBookVO.setCompanyId(priceBookBO.getCompanyId());
		
		
		
		//product mapping
		InventoryVO Inventoryvo = new InventoryVO();
		Inventoryvo.setServiceId(Integer.parseInt(priceBookBO.getProductservicebo().getServiceName()));
		Inventoryvo.setServiceName(priceBookBO.getProductservicebo().getServiceName());
		priceBookVO.setProductservicevo(Inventoryvo);
		//supplier mapping
		SupplierVO suppliervo = new SupplierVO();
		suppliervo.setSupplierId(Integer.parseInt(priceBookBO.getSupplierBO().getSupplierName()));
		suppliervo.setSupplierName(priceBookBO.getSupplierBO().getSupplierName());
		priceBookVO.setSuppliervo(suppliervo);
		priceBookVO= priceBookDao.createPriceBook(priceBookVO);
		if(null!=priceBookVO) {
			priceBook.setPriceBookId(priceBookVO.getPriceBookId());
		}
		}catch(Exception e) {

			if(LOGGER.isInfoEnabled()) {
				LOGGER.info(" pricebook create: Exception \t"+e);
			}
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug(" pricebook create: Exception \t"+e);
			}
		}
		finally {
			PriceBookServiceImpl.LOGGER.exit();
		}
		return priceBook;
	}

	@Override
	public long reterivepricebook(PriceBookBO priceBookbo) throws MySalesException {
		// TODO Auto-generated method stub
		PriceBookVO priceBookVO=new PriceBookVO();
		if(0 < priceBookbo.getCompanyId()) {
		priceBookVO.setCompanyId(priceBookbo.getCompanyId());
		}
		if(null!=priceBookbo.getPriceBookName() && !priceBookbo.getPriceBookName().isEmpty()) {
			priceBookVO.setPriceBookName(priceBookbo.getPriceBookName());
		}
		return priceBookDao.reterivepricebook(priceBookVO);
	}

	@Override
	public List<PriceBookBO> reteriveprice(PriceBookBO priceBookbo) throws MySalesException {
		// TODO Auto-generated method stub
		PriceBookServiceImpl.LOGGER.entry();
		
		PriceBookVO priceBookVO=new PriceBookVO();
		List<PriceBookBO> pricebookbo=  new ArrayList<PriceBookBO>();
		List<PriceBookVO> pricebookvo=  new ArrayList<PriceBookVO>();
		priceBookVO.setRecordIndex(priceBookbo.getRecordIndex());
		priceBookVO.setMaxRecord(priceBookbo.getMaxRecord());
		if(0< priceBookbo.getCompanyId()) {
		priceBookVO.setCompanyId(priceBookbo.getCompanyId());
		}
		priceBookVO.setIsDeleted(false);
		priceBookVO.setActive(true);
		if(null!=priceBookbo.getPriceBookName() && !priceBookbo.getPriceBookName().isEmpty()) {
			priceBookVO.setPriceBookName(priceBookbo.getPriceBookName());
		}
		try {
			pricebookvo=priceBookDao.reteriveprice(priceBookVO);
			if(null!=pricebookvo && !pricebookvo.isEmpty()) {
				int sNo=priceBookbo.getRecordIndex();
				for(PriceBookVO obj:pricebookvo) {
					PriceBookBO priceBook = new PriceBookBO();
					InventoryBO inventory=new InventoryBO();
					SupplierBO supplier=new SupplierBO();
					priceBook.setsNo(++sNo);
					priceBook.setPriceBookId(obj.getPriceBookId());
					priceBook.setPriceBookName(obj.getPriceBookName());
					priceBook.setPriceBookOwner(obj.getPriceBookOwner());
					priceBook.setDescription(obj.getDescription());
					priceBook.setPrice(obj.getPrice());
					
					AdminUserBO user = new AdminUserBO();
					user.setName(obj.getUser().getName());
					priceBook.setAdmin(user);
					
					//product mapping
					inventory.setServiceId(obj.getProductservicevo().getServiceId());
					inventory.setServiceName(obj.getProductservicevo().getServiceName());
					
					//supplier mapping
					supplier.setSupplierId(obj.getSuppliervo().getSupplierId());
					supplier.setSupplierName(obj.getSuppliervo().getSupplierName());
				
					
					pricebookbo.add(priceBook);
					
				}
			}
		}catch(Exception e) {

			if(LOGGER.isInfoEnabled()) {
				LOGGER.info(" retrieve pricebook list: Exception \t"+e);
			}
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("  retrieve pricebook list: Exception \t"+e);
			}
		}
		finally {
			PriceBookServiceImpl.LOGGER.exit();
		}
		return pricebookbo;
	}

	@Override
	public PriceBookVO reterivepricebookdetails(PriceBookVO priceBookVO) throws MySalesException {
		// TODO Auto-generated method stub
		return priceBookDao.reterivepricebookdetails(priceBookVO);
	}

	@Override
	public PriceBookVO editpricebooks(PriceBookVO priceBookVO) throws MySalesException {
		// TODO Auto-generated method stub
		priceBookVO= priceBookDao.editpricebooks(priceBookVO);
		return priceBookVO;
		
		
	}

	@Override
	public Boolean updatepricebook(PriceBookVO priceBookVO) throws MySalesException {
		// TODO Auto-generated method stub
		return priceBookDao.updatepricebook(priceBookVO);
	}

	@Override
	public Boolean deletepricebook(PriceBookVO priceBookVO) throws MySalesException {
		// TODO Auto-generated method stub
		return priceBookDao.deletepricebook(priceBookVO);
	}

	@Override
	public long searchcount(PriceBookBO priceBO) throws MySalesException {
		
		PriceBookServiceImpl.LOGGER.entry();
		long count = 0;
		try {
			if(priceBO != null) {
				PriceBookVO price= new PriceBookVO();
				if(null!=priceBO.getPriceBookName())
				price.setPriceBookName(priceBO.getPriceBookName());
				if(null!=priceBO.getPriceBookOwner())
				price.setPriceBookOwner(priceBO.getPriceBookOwner());
				if(0< priceBO.getCompanyId()) {
				price.setCompanyId(priceBO.getCompanyId());
				}
				count = priceBookDao.searchcount(price);
			}
			
		}catch(Exception e) {

			if(LOGGER.isInfoEnabled()) {
				LOGGER.info(" search count pricebook : Exception \t"+e);
			}
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("  search count pricebook : Exception \t"+e);
			}
		}
		finally {
			PriceBookServiceImpl.LOGGER.exit();
		}
		
		return count;
	}

	@Override
	public List<PriceBookBO> search(PriceBookBO priceBO) throws MySalesException {
		
	 PriceBookServiceImpl.LOGGER.entry();
	  List<PriceBookBO> priceBo = new ArrayList<PriceBookBO>();
	  List<PriceBookVO> priceVo = new ArrayList<PriceBookVO>();
	  
	  try {
		  if(null!= priceBO) {
			  PriceBookVO priceVO = new PriceBookVO();
			  if(0<priceBO.getMaxRecord())
			  priceVO.setMaxRecord(priceBO.getMaxRecord());
			  if(0<priceBO.getRecordIndex())
			  priceVO.setRecordIndex(priceBO.getRecordIndex());
			  if(null!=priceBO.getPriceBookName())
			  priceVO.setPriceBookName(priceBO.getPriceBookName());
			  if(null!=priceBO.getPriceBookOwner())
			  priceVO.setPriceBookOwner(priceBO.getPriceBookOwner());
			  if(0<priceBO.getCompanyId()) {
			  priceVO.setCompanyId(priceBO.getCompanyId());//based on company Id..
			  }
		  priceVo = priceBookDao.search(priceVO);
		  if(null!=priceVo&&!priceVo.isEmpty()) {
			  int count = priceBO.getRecordIndex();
			  for(PriceBookVO vo :priceVo) {
				  PriceBookBO bo = new PriceBookBO();
				  InventoryBO inventory=new InventoryBO();
					SupplierBO supplier=new SupplierBO();
				  bo.setsNo(++count);
				  bo.setPriceBookId(vo.getPriceBookId());
				  bo.setPriceBookName(vo.getPriceBookName());
				  bo.setPriceBookOwner(vo.getPriceBookOwner());
				  bo.setPrice(vo.getPrice());
				  
				  AdminUserBO user = new AdminUserBO();
				  user.setName(vo.getUser().getName());
				  bo.setAdmin(user);
				  
				  user.setId(vo.getUser().getId());
				  bo.setAdmin(user);
				  
				//product mapping
					inventory.setServiceId(vo.getProductservicevo().getServiceId());
					inventory.setServiceName(vo.getProductservicevo().getServiceName());
					
					//supplier mapping
					supplier.setSupplierId(vo.getSuppliervo().getSupplierId());
					supplier.setSupplierName(vo.getSuppliervo().getSupplierName());
				
				  priceBo.add(bo);
			  }
		  }
		  }
	  }catch(Exception e) {

			if(LOGGER.isInfoEnabled()) {
				LOGGER.info(" search pricebook : Exception \t"+e);
			}
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("  search pricebook : Exception \t"+e);
			}
		}
		finally {
			PriceBookServiceImpl.LOGGER.exit();
		}
		return priceBo;
	}

	@Override
	public ArrayList<SupplierBO> getSupplier(long serviceId) {
		ArrayList<SupplierBO> supplierListBO=new ArrayList<>();
		List<SupplierVO> supplierListVO=new ArrayList<>();
		supplierListVO=priceBookDao.getSupplier(serviceId) ;
		
		for(SupplierVO suppilerVO : supplierListVO) {
			SupplierBO supplierBO=new SupplierBO();
			supplierBO.setSupplierId(suppilerVO.getSupplierId());
			supplierBO.setSupplierName(suppilerVO.getSupplierName());
			supplierListBO.add(supplierBO);
		}
		
		 return supplierListBO;
	}

	@Override
	public SupplierBO getSupplierPrice(Long supplierIds) {
		SupplierVO supplierVO=new SupplierVO();
		SupplierBO supplierBO=new SupplierBO();
		try {
			supplierVO= priceBookDao.getSupplierPrice(supplierIds);
			//supplierBO.setFinancialAmount(supplierVO.getFinancialAmount());
			supplierBO.setSupplierName(supplierVO.getSupplierName());
			return supplierBO;}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return supplierBO;
		
	}

	@Override
	public List<PriceBookBO> getPricebook(long serviceId) {
		List<PriceBookVO> priceBookListvo=new ArrayList<>();
		List<PriceBookBO> priceBookListbo=new ArrayList<>();
		
		try {
			priceBookListvo=priceBookDao.reteriveprice(serviceId);
			for(PriceBookVO vo:priceBookListvo) {
				PriceBookBO bo=new PriceBookBO();
				bo.setPriceBookId(vo.getPriceBookId());
				bo.setPriceBookName(vo.getPriceBookName());
				bo.setPrice(vo.getPrice());
				priceBookListbo.add(bo);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		return priceBookListbo;
	}


}
