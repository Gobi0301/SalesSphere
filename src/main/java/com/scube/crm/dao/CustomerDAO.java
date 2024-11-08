package com.scube.crm.dao;

import java.sql.SQLException;
import java.util.List;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.ClientBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.Customer;
import com.scube.crm.vo.EmailAccess;
import com.scube.crm.vo.FollowUp;
import com.scube.crm.vo.GstVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.SalesOrderVO;
import com.scube.crm.vo.User;

/**
 * @author Administrator
 * @param <T>
 * 
 */
public interface CustomerDAO {

	User authendicate(String string, String emailAddress) throws MySalesException;

	public boolean createAccessLog(AccessLogVO logVO);

	/**
	 * @param adminVO
	 * @return
	 */
	long createuser(User adminVO) throws MySalesException;

	User userStatus(User userVO) throws MySalesException;

	Customer deleteProfile(Customer vo) throws MySalesException;

	User updateuser(User loginVO) throws MySalesException;

	User getuserId(AdminUserBO adminBO) throws MySalesException;
	
	boolean findEmployerEmail(String emailAddress);

	
	EmailAccess saveEmailList(List<EmailAccess> accessVOList);

	
	List<String> getImageName();

	LoginStatusVO editLoginStatus(LoginStatusVO loginStatusVO);

	long addLoginStatus(LoginStatusVO loginStatus);


	boolean mobileNoVerification(String mobileNo);

	int deleteCustomer(Customer customerVO);

	Customer createCustomer(Customer customerVO);

	ClientBO retriveCustomerById(ClientBO registerBO) throws MySalesException,  SQLException;

	List<ClientBO> retriveCustomer(ClientBO reteriveprofile) throws MySalesException,  SQLException;

	Customer getuserId(ClientBO registerBO);

	Customer updateEmployer(Customer employerVO);

	FollowUp saveTracking(FollowUp vO);

	List<FollowUp> retrieveTracking(long customerId);

	List<FollowUp> searchRetrieveTracking(FollowUp leadsFollowup);
	List<InventoryVO> getProductList();

	InventoryVO getProductPrice(long staffId);

	GstVO getGstValues();

	long getProductServiceId(String serviceName);

	long createSalesOrder(SalesOrderVO salesOrderVO);

	String getSalesOrderNo();

	List<Customer> retriveClients();

	List<Long> retriveServiceByCustomerId(ClientBO reterive);

	long countOfCustomer(Customer customerVO);

	List<Customer> listOfCustomerByPagination(Customer customerVO);

	long countOfCustomerBySearch(Customer customerVO);

	Customer getCustomerDetails(long clientId)throws MySalesException;

	List<Customer> getCustomerProfile(long id);

	Customer getProfile(long id);

	boolean checkemail(String email, long id);

	boolean checkcontactNumber(String contact, long id);

	boolean checkmobileNumber(String mobile, long id);
}
