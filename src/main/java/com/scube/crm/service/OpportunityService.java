package com.scube.crm.service;

import java.util.List;
import java.util.Map;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.ClientBO;
import com.scube.crm.bo.GstBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.bo.PriceBookBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.User;

public interface OpportunityService {

	public List<User> retrieveUser() throws  MySalesException;

	public List<LeadsBO> retrieveLeads(LeadsBO leadsBO) throws  MySalesException;

	public List<InventoryBO> getProductList(InventoryBO serviceBO)throws  MySalesException;

	public Long create(OpportunityBO opportunityBO)throws  MySalesException;

	public long oppocount(OpportunityBO opportunityBO) throws MySalesException;

	public List<OpportunityBO> view(OpportunityBO opportunityBO) throws MySalesException;

	public long searchCount(OpportunityBO opportunityBO)throws MySalesException;

	public List<OpportunityBO> search(OpportunityBO opportunityBO)throws MySalesException;

	public OpportunityBO getById(long id)throws MySalesException;

	public boolean update(OpportunityBO editable)throws MySalesException;

	public int delete(long id)throws MySalesException;

	public Map<Integer, String> retriveAccounts(AccountBO accountBO)throws MySalesException;

	//public List<ClientBO> listOfOpportunityByPagination(OpportunityBO opportunityBO);

	public String getSalesOrderNo(ClientBO clientBO);

	public GstBO getGstValues();

	public long createSalesOrder(SalesOrderBO salesOrderBOS);

//	public InventoryBO getProductPrice(Integer pricebook_Id);

	public OpportunityBO saveTracking(OpportunityBO opportunityBO);

	public PriceBookBO getPriceBookPrice(Integer pricebook_Ids);

	List<User> retrieveUser(User user) throws MySalesException;

	public PriceBookBO getProductData(Integer product_Ids);

	public GstBO getProductGst(long product_Ids);

	public long activityCount(OpportunityBO profile);

	

	
}