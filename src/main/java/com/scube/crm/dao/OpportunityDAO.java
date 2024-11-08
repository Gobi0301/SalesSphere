package com.scube.crm.dao;

import java.util.List;
import java.util.Map;

import com.scube.crm.bo.ClientBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.GstVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.Leads;
import com.scube.crm.vo.Opportunity;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SalesOrderVO;

public interface OpportunityDAO {

	List<Leads> retrieveLeads(LeadsBO LeadsBO) throws MySalesException;

	Long create(Opportunity vo)throws MySalesException;

	long Oppocount(Opportunity opportunityVO)throws MySalesException;

	List<Opportunity> view(OpportunityBO opportunityBO) throws MySalesException;

	long searchCount(Opportunity vo)throws MySalesException;

	List<Opportunity> search(Opportunity vo)throws MySalesException;

	Opportunity getById(long id)throws MySalesException;

	boolean update(Opportunity vo)throws MySalesException;

	int delete(long id)throws MySalesException;

	Map<Integer, String> retrieveAccounts(AccountVO accountVO)throws MySalesException;

	Opportunity createOpportunity(Opportunity opportunity)throws MySalesException;

	List<Opportunity> listOfopportunityByPagination(Opportunity opportunityVO);

	String getSalesOrderNo( ClientBO clientBO);

	List<InventoryVO> getProductList(InventoryVO InventoryVO);

	GstVO getGstValues();

	long createSalesOrder(SalesOrderVO salesOrderVO);

//	InventoryVO getProductPrice(Long productId);

	List<ActivityVO> retrieveTracking(long opportunityId, Long CompanyId );

	ActivityVO saveTracking(ActivityVO activityvo);

	PriceBookVO getPriceBookPrice(Integer pricebook_Ids);
	PriceBookVO getProductData(Integer product_Ids);

	GstVO getProductgst(long product_Ids);

	long activityCount(Opportunity vo) throws MySalesException;

	List<Opportunity> getProductListforSearch(InventoryVO InventoryVO);
	




}
