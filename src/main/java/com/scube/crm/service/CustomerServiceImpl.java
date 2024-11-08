package com.scube.crm.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AccessLogBO;
import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.AdminLoginBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.ClientBO;
import com.scube.crm.bo.GstBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.bo.SalesOrderProductsBO;
import com.scube.crm.dao.CustomerDAO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.model.EmailModel;
import com.scube.crm.security.ControllerUtils;
import com.scube.crm.utils.DateHelper;
import com.scube.crm.utils.SendEmailServiceImpl;
import com.scube.crm.utils.SuccessMsg;
import com.scube.crm.vo.AccessLogVO;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.Customer;
import com.scube.crm.vo.EmailAccess;
import com.scube.crm.vo.FollowUp;
import com.scube.crm.vo.GstVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.LoginStatusVO;
import com.scube.crm.vo.SalesOrderVO;
import com.scube.crm.vo.User;

@Service("customerService")
@Transactional
public class CustomerServiceImpl extends ControllerUtils implements CustomerService {

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerDAO customerDAO;

	static boolean isApproval = true;
	EmailModel model = new EmailModel();

	@Autowired
	private SendEmailServiceImpl emailManager;

	@Autowired
	private MessageSource messageSource;

	@Override
	public AdminLoginBO authendicate(AdminLoginBO adminLoginBO) throws MySalesException {
		CustomerServiceImpl.LOGGER.entry();

		final AdminLoginBO adminLogin = new AdminLoginBO();
		try {
			final User user = this.customerDAO.authendicate("emailAddress", adminLoginBO.getEmailAddress());

			if (adminLoginBO.getPassword().equals(user.getPassword())) {
				if (user.isActive()) {
					BeanUtils.copyProperties(user, adminLogin);
					String userName = adminLoginBO.getEmailAddress();
					addLoginStatus(userName);
					adminLogin.setActive(true);
				} else {
					adminLogin.setActive(false);
				}
			} else {
				adminLogin.setActive(false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return adminLogin;
	}

	@Override
	public boolean editLoginStatus(LoginStatusVO loginStatusVO) {
		customerDAO.editLoginStatus(loginStatusVO);
		return false;

	}

	@Override
	public boolean addLoginStatus(String username) throws MySalesException {

		LoginStatusVO loginStatus = new LoginStatusVO();
		loginStatus.setUserName(username);
		loginStatus.setType("Admin");
		loginStatus.setStatus(true);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Date date = cal.getTime();
		loginStatus.setStartTime(date);
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.MONTH, 1);
		Date date1 = cal.getTime();
		loginStatus.setEndTime(date1);
		loginStatus.setActivity("login");
		customerDAO.addLoginStatus(loginStatus);

		return false;
	}

	@Override
	public boolean createAccessLog(AccessLogBO logBO) {
		boolean accessStatus = false;
		try {
			AccessLogVO logVO = new AccessLogVO();
			logVO.setAccessId(logBO.getAccessId());
			logVO.setAccessDate(logBO.getAccessDate());
			logVO.setClientIP(logBO.getClientIP());
			logVO.setSessionId(logBO.getSessionId());
			accessStatus = customerDAO.createAccessLog(logVO);
		} catch (Exception e) {

		}
		return accessStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.scube.crm.service.AdminService#userStatus(com.scube.crm.bo.AdminUserBO)
	 */
	@Override
	public boolean userStatus(AdminUserBO userBO) throws MySalesException {
		boolean loginChanged = false;

		User userVO = new User();

		if (0 != userBO.getId()) {
			userVO.setId(userBO.getId());
			userVO.setActive(userBO.isActive());
			userVO = customerDAO.userStatus(userVO);
			if (0 != userVO.getId()) {
				loginChanged = true;
			}
		}
		return loginChanged;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scube.crm.service.AdminService#DeleteProfile(com.scube.crm.bo.
	 * AdminUserBO)
	 */
	@Override
	public AdminUserBO deleteProfile(AdminUserBO deleteProfile) throws MySalesException {
		CustomerServiceImpl.LOGGER.entry();

		Customer VO = new Customer();
		try {
			VO.setId(deleteProfile.getId());
			VO.setIsDelete(true);
			VO = customerDAO.deleteProfile(VO);

		} catch (MySalesException e) {

			e.printStackTrace();
		}

		return deleteProfile;
	}

	@Override
	public boolean findEmployerEmail(String emailAddress) {

		boolean validationEmail = customerDAO.findEmployerEmail(emailAddress);
		return validationEmail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.scube.crm.service.AdminService#sendClientMail(com.scube.crm.bo.ClientBO)
	 */
	@Override
	public boolean sendClientMail(ClientBO employerRegisterBO) {
		boolean alertStatus = false;
		try {
			EmailModel model = new EmailModel();

			final String[] toaddress = employerRegisterBO.getEmailAddress().split(",");
			final String subject = messageSource.getMessage("Validate.RegisterConfirm", null, null);
			for (int i = 0; i < toaddress.length; i++) {
				String bodycontent = "employerVerificationMail";
				model.setUrl("www.myjobkart.com/find-jobs.html?companyId=");
				model.setFirstname(employerRegisterBO.getFirstName());
				model.setEmail(employerRegisterBO.getEmailAddress());
				alertStatus = emailManager.sendEmail(toaddress[i], subject, bodycontent, model);
			}
			if (alertStatus) {
				EmailAccess accessVO = new EmailAccess();
				accessVO.setDate(new Date());
				accessVO.setEmailAddress(employerRegisterBO.getEmailAddress());
				accessVO.setMailedBy(employerRegisterBO.getEmployerId());
				accessVO.setStatus(alertStatus);
				accessVO.setMailTO(employerRegisterBO.getId());
				List<EmailAccess> accessVOList = new ArrayList<EmailAccess>();
				accessVOList.add(accessVO);

				if (null != accessVOList || accessVOList.size() > 0) {
					customerDAO.saveEmailList(accessVOList);
				}
			}

		} catch (final Exception ex) {
		}

		return alertStatus;
	}

	@Override
	public boolean mobileNoVerification(String mobileNo) {

		return customerDAO.mobileNoVerification(mobileNo);
	}

	public static Date getDateWithoutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	@Override
	public ClientBO createCustomer(ClientBO customerBO) {
		Customer customerVO = new Customer();

		if (null != customerBO.getCompanyName() && !customerBO.getCompanyName().isEmpty()) {
			customerVO.setCompanyName(customerBO.getCompanyName());
		}
		if (null != customerBO.getFirstName() && !customerBO.getFirstName().isEmpty()) {
			customerVO.setFirstName(customerBO.getFirstName());
		}
		if (null != customerBO.getLastName() && !customerBO.getLastName().isEmpty()) {
			customerVO.setLastName(customerBO.getLastName());
		}
		if (null != customerBO.getWebsite() && !customerBO.getWebsite().isEmpty()) {
			customerVO.setWebSite(customerBO.getWebsite());
		}
		/*
		 * if(null != profileBO.getConfirmEmailAddress() &&
		 * !profileBO.getConfirmEmailAddress().isEmpty()){
		 * customerVO.setConfirmEmailAddress( profileBO.getConfirmEmailAddress()); }
		 */
		if (null != customerBO.getEmailAddress() && !customerBO.getEmailAddress().isEmpty()) {
			customerVO.setEmailAddress(customerBO.getEmailAddress());
		}
		if (null != customerBO.getMobileNo()) {
			customerVO.setMobileNumber(customerBO.getMobileNo());
		}
		if (null != customerBO.getContactNo()) {
			customerVO.setContactNumber(customerBO.getContactNo());
		}

		if (null != customerBO.getAddress() && !customerBO.getAddress().isEmpty()) {
			customerVO.setAddress(customerBO.getAddress());
		}
		if (null != customerBO.getIndustryType() && !customerBO.getIndustryType().isEmpty()) {
			customerVO.setIndustryType(customerBO.getIndustryType());
		}
		if (0 != customerBO.getEmployerId()) {
			customerVO.setAssigned(customerBO.getEmployerId());
		}

		User adminLogin = new User();
		adminLogin.setId(customerBO.getAdminId());
		customerVO.setLoginVO(adminLogin);
		customerVO.setCreatedBy(customerBO.getCreatedBy());
		customerVO.setModifiedBy(customerBO.getModifiedBy());
		customerVO.setIsActive(customerBO.isActive());
		customerVO.setIsDelete(customerBO.isDelete());
		customerVO.setStatus("opened");
		customerVO.setMigrationStatus(false);
		customerVO.setWarrantyDate(customerBO.getWarrantyDate());
		customerVO.setCreated(DateHelper.beginningOfDay(new Date()));
		customerVO.setModified(DateHelper.beginningOfDay(new Date()));
		customerVO.setCompanyId(customerBO.getCompanyId());
		customerVO.setCreatedBy(customerBO.getCreatedBy());
		customerVO.setIsActive(customerBO.isActive());
		List<InventoryVO> productVOList = new ArrayList<>();
		if (null != customerBO) {
			for (InventoryBO productBO : customerBO.getProductServiceList()) {
				InventoryVO productVO = new InventoryVO();
				productVO.setServiceId(productBO.getServiceId());
				productVO.setServiceName(productBO.getServiceName());
				productVO.setServiceSpecification(productBO.getServiceSpecification());
				productVO.setFees(productBO.getFees());
				// productVO.setDuration(productBO.getDuration());
				productVO.setModified(productBO.getModified());
				productVO.setModified(productBO.getModified());
				productVO.setModifiedBy(productBO.getModifiedBy());
				productVO.setStartDate(productBO.getStartDate());
				productVO.setEndDate(productBO.getEndDate());
				productVOList.add(productVO);
				customerVO.setProductServiceVO(productVOList);

			}
		}

		customerVO = customerDAO.createCustomer(customerVO);
		if (null != customerVO) {
			BeanUtils.copyProperties(customerVO, customerBO);
		}

		return customerBO;
	}

	@Override
	public ClientBO updateEmployer(ClientBO registerBO) {
		try {
			Customer employerVO = new Customer();
			// employerVO = customerDAO.getuserId(registerBO);
			if (null != registerBO.getFirstName()) {
				employerVO.setFirstName(registerBO.getFirstName());
			}
			if (null != registerBO.getLastName()) {
				employerVO.setLastName(registerBO.getLastName());
			}
			if (null != registerBO.getMobileNo()) {
				employerVO.setMobileNumber(registerBO.getMobileNo());
			}
			if (null != registerBO.getEmailAddress()) {
				employerVO.setEmailAddress(registerBO.getEmailAddress());
			}
			if (0 != registerBO.getsNo()) {
				employerVO.setId(registerBO.getsNo());
			}
			/*
			 * if(null !=registerBO.getConfirmEmailAddress()){
			 * employerVO.setConfirmEmailAddress(registerBO.getConfirmEmailAddress()); }
			 */
			if (null != registerBO.getAddress()) {
				employerVO.setAddress(registerBO.getAddress());
			}
			/*
			 * if(null !=registerBO.getPassword()){
			 * employerVO.setPassword(registerBO.getPassword()); }
			 */
			if (null != registerBO.getCompanyName()) {
				employerVO.setCompanyName(registerBO.getCompanyName());
			}
			/*
			 * if(null !=registerBO.getConfirmPassword()){
			 * employerVO.setConfirmPassword(registerBO.getConfirmPassword()); }
			 */
			if (null != registerBO.getIndustryType()) {
				employerVO.setIndustryType(registerBO.getIndustryType());
			}
			if (null != registerBO.getWebsite()) {
				employerVO.setWebSite(registerBO.getWebsite());
			}

			if (null != registerBO.getContactNo()) {
				employerVO.setContactNumber(registerBO.getContactNo());
			}

			if (null != registerBO.getStatus()) {
				employerVO.setStatus(registerBO.getStatus());
			}
			employerVO.setCompanyId(registerBO.getCompanyId());
			employerVO.setModifiedBy(registerBO.getModifiedBy());
			employerVO.setModified(getDateWithoutTime(new Date()));
			employerVO.setIsActive(true);
			employerVO.setStatus("opened");
			if (null != registerBO && null != registerBO.getLoginBO()) {
				long id = registerBO.getLoginBO().getId();
				User adminLoginVO = new User();
				adminLoginVO.setId(id);
				employerVO.setLoginVO(adminLoginVO);

			}

			List<InventoryVO> productVOList = new ArrayList<>();

			if (null != registerBO) {
				for (InventoryBO productBOObj : registerBO.getProductServiceList()) {
					InventoryVO productVO = new InventoryVO();
					productVO.setServiceId(productBOObj.getServiceId());
					productVO.setServiceName(productBOObj.getServiceName());
					productVO.setServiceSpecification(productBOObj.getServiceSpecification());
					// productVO.setDuration(productBOObj.getDuration());
					productVO.setFees(productBOObj.getFees());
					productVOList.add(productVO);
				}
			}

			if (null != productVOList) {
				employerVO.setProductServiceVO(productVOList);
			}

			employerVO = customerDAO.updateEmployer(employerVO);

			if (0 != employerVO.getId()) {
				registerBO.setId(employerVO.getId());
			} else {
				registerBO = new ClientBO();
			}

			if (null != employerVO) {
				registerBO.setResponse(SuccessMsg.UPDATE_SUCCESS);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update user has failed:" + ex.getMessage());
			}
			LOGGER.info("Update user has failed:" + ex.getMessage());
		}
		return registerBO;

	}

	@Override
	public List<ClientBO> retriveCustomer(ClientBO clientBO) throws MySalesException {
		LOGGER.entry();
		ClientBO client = new ClientBO();

		List<ClientBO> clientBOList = new ArrayList<>();
		try {
			clientBOList = customerDAO.retriveCustomer(clientBO);

			if (null != clientBOList) {
				// retrieve Customer tracking details
				List<FollowUp> trackingList = customerDAO.retrieveTracking(clientBO.getId());

				if (null != trackingList) {
					client.setCustomerUpdateVOList(trackingList);
					clientBOList.add(client);
				}
			}

			if (null != clientBOList) {
				// retrieve Customer tracking details
				List<FollowUp> trackingList = customerDAO.retrieveTracking(clientBO.getId());

				if (null != trackingList && 0 < trackingList.size()) {
					client.setCustomerUpdateVOList(trackingList);
					clientBOList.add(client);
				}
			}

			/*
			 * if(null!=clientBOList) { //retrieve Customer tracking details List<FollowUp>
			 * trackingList= customerDAO.retrieveTracking(clientBO.getId());
			 * client.setCustomerUpdateVOList(trackingList); clientBOList.add(client); }
			 */

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("View Customer has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return clientBOList;
	}

	@Override
	public ClientBO deleteProfile(ClientBO deleteProfile) {
		LOGGER.entry();
		Customer customerVO = new Customer();
		try {
			customerVO.setId(deleteProfile.getId());
			customerVO.setIsDelete(true);
			customerVO = customerDAO.deleteProfile(customerVO);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Customer has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		deleteProfile.setsNo(customerVO.getId());
		return deleteProfile;
	}

	@Override
	public ClientBO deleteCustomer(ClientBO deleteProfile) throws MySalesException {

		return null;
	}

	@Override
	public ClientBO retriveCustomerById(ClientBO registerBO) throws MySalesException, SerialException, SQLException {
		LOGGER.entry();
		try {
			ClientBO reterive = null;
			reterive = customerDAO.retriveCustomerById(registerBO);

			if (null != reterive) {
				List<Long> serviceIdList = customerDAO.retriveServiceByCustomerId(reterive);
				reterive.setServiceIdList(serviceIdList);
			}
			return reterive;
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Customer has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return null;
	}

	@Override
	public ClientBO saveTracking(ClientBO bo) {

		FollowUp followUp = new FollowUp();
		Customer customer = new Customer();
		User loginVO = new User();

		customer.setId(bo.getId());
		followUp.setCustomer(customer);

		loginVO.setId(bo.getAdminId());
		followUp.setUser(loginVO);

		if (null != bo.getDescription() && !bo.getDescription().isEmpty()) {
			followUp.setDescription(bo.getDescription());
		}
		if (null != bo.getDate()) {
			followUp.setDate(bo.getDate());
		}
		if (null != bo.getTimeSlot()) {
			followUp.setTimeSlot(bo.getTimeSlot());
		}
		if (null != bo.getEndTimeSlot()) {
			followUp.setEndTimeSlot(bo.getEndTimeSlot());
		}
		if (null != bo.getNextAppointmentDate()) {
			followUp.setNextAppointmentDate(bo.getNextAppointmentDate());
		}
		if (null != bo.getStatus()) {
			customer.setStatus(bo.getStatus());
			followUp.setCustomer(customer);
		}
		followUp = customerDAO.saveTracking(followUp);
		if (followUp.getUpdateid() > 0) {
			bo.setId(followUp.getCustomer().getId());
		} else {
			bo = null;
		}
		return bo;
	}

	@Override
	public Customer createCustomer(Customer customer) throws MySalesException {
		return customerDAO.createCustomer(customer);
	}

	@Override
	public List<ClientBO> searchRetrieveTracking(ClientBO registerBO) {
		List<FollowUp> leadsFollowuplist = new ArrayList<FollowUp>();
		FollowUp leadsFollowup = new FollowUp();
		List<ClientBO> leadsListBO = new ArrayList<ClientBO>();
		User userVO = new User();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (null != registerBO.getStarDate()) {
				String startDate = df.format(registerBO.getStarDate());
				Date toDate = df.parse(startDate);
				leadsFollowup.setCreated(toDate);
				leadsFollowup.setModified(null);
			}
			if (null != registerBO.getEndDate()) {
				leadsFollowup.setCreated(null);
				String endDate = df.format(registerBO.getEndDate());
				Date toDate = df.parse(endDate);
				leadsFollowup.setModified(toDate);
			}
			userVO.setId(registerBO.getLoginBO().getId());
			leadsFollowup.setUser(userVO);
			leadsFollowuplist = customerDAO.searchRetrieveTracking(leadsFollowup);
			AtomicInteger sNo = new AtomicInteger(0);
			if (null != leadsFollowuplist && !leadsFollowuplist.isEmpty() && 0 < leadsFollowuplist.size()) {
				leadsFollowuplist.forEach(leadsFollow -> {
					AdminLoginBO adminLoginBO = new AdminLoginBO();
					ClientBO clientBO = new ClientBO();
					clientBO.setsNo(sNo.incrementAndGet());
					clientBO.setId(leadsFollow.getCustomer().getId());
					clientBO.setName(leadsFollow.getCustomer().getFirstName());
					clientBO.setCompanyName(leadsFollow.getCustomer().getCompanyName());
					clientBO.setEmailAddress(leadsFollow.getCustomer().getEmailAddress());
					SimpleDateFormat simpleformat = new SimpleDateFormat("dd-MM-yyyy");
					clientBO.setCreatedDate(simpleformat.format(leadsFollow.getCustomer().getCreated()));
					clientBO.setModifiedDate(simpleformat.format(leadsFollow.getCustomer().getModified()));
					clientBO.setCreated(leadsFollow.getCreated());
					clientBO.setStatus(leadsFollow.getCustomer().getStatus());
					if (null != leadsFollow.getCustomer().getLoginVO()) {
						adminLoginBO.setId(leadsFollow.getCustomer().getLoginVO().getId());
						clientBO.setLoginBO(adminLoginBO);
					}
					leadsListBO.add(clientBO);
				});
			}
		} catch (Exception he) {
			he.printStackTrace();
			LeadsServiceImpl.LOGGER.debug(he, he.getMessage());
		}
		return leadsListBO;
	}

	public String getSalesOrderNo() {
		return customerDAO.getSalesOrderNo();

	}

	public List<ClientBO> retriveClients() {
		LOGGER.entry();
		List<Customer> listVO = new ArrayList<Customer>();
		List<ClientBO> listBO = new ArrayList<ClientBO>();
		try {
			listVO = customerDAO.retriveClients();
			if (listVO != null && listVO.size() > 0 && !listVO.isEmpty()) {
				for (Customer x : listVO) {
					ClientBO cBO = new ClientBO();
					cBO.setClientId(x.getId());
					cBO.setFirstName(x.getFirstName());
					listBO.add(cBO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LOGGER.exit();
		}
		return listBO;

	}

	public GstBO getGstValues() {
		GstVO gstVO = new GstVO();
		GstBO gstBO = new GstBO();
		try {
			gstVO = customerDAO.getGstValues();
			if (null != gstVO) {
				gstBO.setSgst(gstVO.getSgst());
				gstBO.setCgst(gstVO.getCgst());
				gstBO.setGstId(gstVO.getGstId());
				return gstBO;
			}
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}
		return gstBO;

	}

	public long createSalesOrder(SalesOrderBO salesOrderBOS) {
		CustomerServiceImpl.LOGGER.entry();
		long status = 0;
		try {
			if (null != salesOrderBOS && null != salesOrderBOS.getSalesOrderProductBO()
					&& salesOrderBOS.getSalesOrderProductBO().size() > 0) {

				for (SalesOrderProductsBO SalesOrderBo : salesOrderBOS.getSalesOrderProductBO()) {
					SalesOrderVO salesOrderVO = new SalesOrderVO();

					AccountVO aVO = new AccountVO();
					AccountBO aBO = new AccountBO();
					InventoryVO productServiceVO = new InventoryVO();
					GstVO gstVO = new GstVO();

//					salesOrderVO.setQuantity(SalesOrderBo.getQuantity());
//
//					salesOrderVO.setSalesOrderNo(SalesOrderBo.getSalesOrderNo());

					long productId = SalesOrderBo.getProduct().getServiceId();
					productServiceVO.setServiceId(productId);
					long gstId = SalesOrderBo.getGstBO().getGstId();
					gstVO.setGstId(gstId);

//					salesOrderVO.setProduct(productServiceVO);
					// gstVO.setGstId(gstId);
//					salesOrderVO.setGstVO(gstVO);
					aBO.setAccountId(salesOrderBOS.getAccountBO().getAccountId());
					aBO.setAccountName(salesOrderBOS.getAccountBO().getAccountName());
					aVO.setAccountId(aBO.getAccountId());
					aVO.setAccountName(aBO.getAccountName());
					salesOrderVO.setAccountVO(aVO);
					salesOrderVO.setCreatedBy(salesOrderBOS.getCreatedBy());
					salesOrderVO.setModifiedBy(salesOrderBOS.getModifiedBy());
					status = customerDAO.createSalesOrder(salesOrderVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CustomerServiceImpl.LOGGER.exit();
		}

		return status;

	}

	public List<InventoryBO> getProductList() {
		List<InventoryVO> ProductServiceVO = new ArrayList<>();
		List<InventoryBO> productServiceBO = new ArrayList<>();
		try {
			ProductServiceVO = customerDAO.getProductList();
			for (InventoryVO productServiceVO : ProductServiceVO) {
				InventoryBO ProductServiceBo = new InventoryBO();
				ProductServiceBo.setServiceName(productServiceVO.getServiceName());
				ProductServiceBo.setServiceId(productServiceVO.getServiceId());
				productServiceBO.add(ProductServiceBo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return productServiceBO;
	}

	public InventoryBO getProductPrice(Long staffId) {
		InventoryVO productServiceVO = new InventoryVO();
		InventoryBO productServiceBO = new InventoryBO();
		try {
			productServiceVO = customerDAO.getProductPrice(staffId);
			productServiceBO.setFees(productServiceVO.getFees());
			productServiceBO.setServiceName(productServiceVO.getServiceName());
			return productServiceBO;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return productServiceBO;
	}

	@Override
	public long countOfCustomer(ClientBO customerBO) {

		long countOfCustomer = 0;

		Customer customerVO = new Customer();
		customerVO.setIsDelete(false);
		if(null != customerBO.getCompanyId()&& 0< customerBO.getCompanyId()) {
		customerVO.setCompanyId(customerBO.getCompanyId());
		}
		countOfCustomer = customerDAO.countOfCustomer(customerVO);

		return countOfCustomer;
	}

	@Override
	public List<ClientBO> listOfCustomerByPagination(ClientBO customerBO) {
		// TODO Auto-generated method stub
		int maxRecord = 10;
		Customer customerVO = new Customer();
		List<Customer> customerVOList = new ArrayList<>();
		List<ClientBO> customerBOList = new ArrayList<>();

		customerVO.setRecordIndex(customerBO.getRecordIndex());
		if(null != customerBO.getCompanyId()&& 0< customerBO.getCompanyId()) {
		customerVO.setCompanyId(customerBO.getCompanyId());
		}
		customerVO.setMaxRecord(maxRecord);
		customerVO.setIsDelete(false);

		customerVOList = customerDAO.listOfCustomerByPagination(customerVO);
		if (null != customerVOList && !customerVOList.isEmpty() && 0 < customerVOList.size()) {
			int sNo = customerBO.getRecordIndex();
			for (Customer customerVO1 : customerVOList) {
				ClientBO customerBO1 = new ClientBO();
				customerBO1.setId(customerVO1.getId());
				customerBO1.setsNo(++sNo);
				customerBO1.setCompanyName(customerVO1.getCompanyName());
				customerBO1.setFirstName(customerVO1.getFirstName());
				customerBO1.setEmailAddress(customerVO1.getEmailAddress());
				customerBO1.setStatus(customerVO1.getStatus());
				customerBO1.setCreated(customerVO1.getCreated());
				customerBO1.setModified(customerVO1.getModified());
				if (null != customerVO1.getLoginVO()) {
					AdminUserBO adminBO = new AdminUserBO();
					adminBO.setName(customerVO1.getLoginVO().getName());
					customerBO1.setAdminUserBO(adminBO);
				}
				customerBO1.setMobileNo(customerVO1.getMobileNumber());
				customerBOList.add(customerBO1);
			}
		}

		return customerBOList;
	}

	@Override
	public long countOfCountBySearch(ClientBO registerBO) {
		LOGGER.entry();
		long countOfCustomer = 0;
		try {
			Customer customerVO = new Customer();
			if (null != registerBO) {
				customerVO.setCompanyName(registerBO.getCompanyName());
				customerVO.setEmailAddress(registerBO.getEmailAddress());
				customerVO.setMobileNumber(registerBO.getMobileNo());
				customerVO.setIsDelete(false);
				if(null != registerBO.getCompanyId()&& 0< registerBO.getCompanyId()) {
				customerVO.setCompanyId(registerBO.getCompanyId());
				}
			}
			countOfCustomer = customerDAO.countOfCustomerBySearch(customerVO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Customer has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Customer has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return countOfCustomer;
	}

	@Override
	public ClientBO getCustomerDetails(long clientId) throws MySalesException, SerialException, SQLException {
		CustomerServiceImpl.LOGGER.entry();
		Customer profile = new Customer();
		ClientBO clientBO = new ClientBO();
		try {
			if (clientId > 0) {
				profile = customerDAO.getCustomerDetails(clientId);
				if (profile != null) {
					clientBO.setId(profile.getId());
					clientBO.setClientId(profile.getId());
					clientBO.setCompanyName(profile.getCompanyName());
					clientBO.setIndustryType(profile.getIndustryType());
					clientBO.setMobileNo(profile.getMobileNumber());
					clientBO.setStatus(profile.getStatus());
					clientBO.setEmailAddress(profile.getEmailAddress());
					clientBO.setWebsite(profile.getWebSite());
					clientBO.setAddress(profile.getAddress());
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch Customer has failed:" + ex.getMessage());
			}
		} finally {
			CustomerServiceImpl.LOGGER.exit();
		}
		return clientBO;
	}

	@Override
	public List<ClientBO> getCustomerProfile(long id) {
		List<ClientBO> clientList = new ArrayList<ClientBO>();
		List<Customer> listVO = new ArrayList<Customer>();

		try {
			if (id > 0) {
				listVO = customerDAO.getCustomerProfile(id);

				if (null != listVO && !listVO.isEmpty() && 0 < listVO.size()) {
					for (Customer customerVO1 : listVO) {
						ClientBO customerBO1 = new ClientBO();
						customerBO1.setId(customerVO1.getId());
						customerVO1.getProductServiceVO();

						for (InventoryVO ivo : customerVO1.getProductServiceVO()) {

							InventoryBO productBO = new InventoryBO();
							// ClientBO customerBO2=new ClientBO();
							productBO.setServiceId(ivo.getServiceId());
							productBO.setServiceName(ivo.getServiceName());
//							customerBO1.setProductServieBO(productBO);
							customerBO1.setProductServieBO(productBO);
							// customerBO1.setServiceId(ivo.getServiceId());
//							List<Long> serviceIdList = new ArrayList<>();
//							serviceIdList.add(ivo.getServiceId());
							/* customerBO1.setServiceIdList(serviceIdList); */
						}

						customerBO1.setWarrantyDate(customerVO1.getWarrantyDate());
						clientList.add(customerBO1);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return clientList;

	}

	@Override
	public ClientBO getProduct(long id) {

		ClientBO clientBO = new ClientBO();
		Customer clientVO = new Customer();
		try {
			if (id > 0) {
				clientVO = customerDAO.getProfile(id);

				if (clientVO != null) {
					clientBO.setId(clientVO.getId());
					clientBO.setWarrantyDate(clientVO.getWarrantyDate());

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return clientBO;
	}

	@Override
	public boolean checkemail(String email,long id) {
		LOGGER.entry();
		boolean checkEmailId = false;
		try {
		checkEmailId = customerDAO.checkemail(email,id);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("checkEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkEmailId;
}

	@Override
	public boolean checkcontactNumber(String contact,long id) {
		LOGGER.entry();
		boolean checkContactNumber = false;
		try {
			checkContactNumber = customerDAO.checkcontactNumber(contact,id);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("cheakContactNumber has failed:" + ex.getMessage());
			}
			LOGGER.info("cheakContactNumber has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkContactNumber;
	}

	@Override
	public boolean checkmobileNumber(String mobile,long id) {
		LOGGER.entry();
		boolean checkMobileNumber = false;
		try {
			checkMobileNumber = customerDAO.checkmobileNumber(mobile,id);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkMobileNumber has failed:" + ex.getMessage());
			}
			LOGGER.info("checkMobileNumber has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkMobileNumber;
	}
}
