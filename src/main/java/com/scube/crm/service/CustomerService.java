/**
 * 
 */
package com.scube.crm.service;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import com.scube.crm.bo.AccessLogBO;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.ClientBO;
import com.scube.crm.bo.GstBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.Customer;
import com.scube.crm.vo.LoginStatusVO;

/**
 * @author Administrator
 * 
 */
public interface CustomerService {

	AdminLoginBO authendicate(AdminLoginBO adminLoginBO) throws MySalesException;

	boolean sendClientMail(ClientBO employerRegisterBO);

	boolean addLoginStatus(String username) throws MySalesException;

	public boolean createAccessLog(AccessLogBO logBO);

	boolean editLoginStatus(LoginStatusVO loginStatusVO);

	boolean mobileNoVerification(String mobileNo);

	ClientBO createCustomer(ClientBO profileBO);
	
	Customer createCustomer(Customer customer)throws MySalesException;

	AdminUserBO deleteProfile(AdminUserBO deleteProfile) throws MySalesException;

	ClientBO updateEmployer(ClientBO registerBO);

	List<ClientBO> retriveCustomer(ClientBO reteriveprofile) throws MySalesException;

	boolean findEmployerEmail(String emailAddress);

	ClientBO deleteCustomer(ClientBO deleteProfile)  throws MySalesException;

	boolean userStatus(AdminUserBO userBO) throws MySalesException;

	ClientBO deleteProfile(ClientBO deleteProfile);

	ClientBO retriveCustomerById(ClientBO registerBO) throws MySalesException, SerialException, SQLException;

	ClientBO saveTracking(ClientBO bO);

	List<ClientBO> searchRetrieveTracking(ClientBO registerBO);
	List<InventoryBO> getProductList();

	InventoryBO getProductPrice(Long price);

	GstBO getGstValues();

	long createSalesOrder(SalesOrderBO salesOrderBOS);

	String getSalesOrderNo();

	List<ClientBO> retriveClients();

	long countOfCustomer(ClientBO customerBO);

	List<ClientBO> listOfCustomerByPagination(ClientBO customerBO);

	long countOfCountBySearch(ClientBO registerBO);

	ClientBO getCustomerDetails(long clientId)throws MySalesException, SerialException, SQLException;

	List<ClientBO> getCustomerProfile(long id);

	ClientBO getProduct(long id);

	boolean checkemail(String email, long id);

	boolean checkcontactNumber(String contact, long id);

	boolean checkmobileNumber(String mobile, long id);

	

}
