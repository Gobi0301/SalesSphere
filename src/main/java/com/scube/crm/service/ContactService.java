package com.scube.crm.service;

import java.util.List;
import java.util.Map;

import com.scube.crm.bo.ContactBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.User;

public interface ContactService {

	List<User> retrieveUser(User user) throws MySalesException;

	long createContact(ContactBO contactBO) throws MySalesException;

	long contactCount(ContactBO contact) throws MySalesException;

	List<ContactBO> retrieveContact(ContactBO contact) throws MySalesException;

	long contactssCount(ContactBO contactbo) throws MySalesException;

	List<ContactBO> searchContactsList(ContactBO contactbo) throws MySalesException;

	ContactBO findById(Long contactId) throws MySalesException;

	ContactBO retrievecontact(Long conId) throws MySalesException;

	boolean updateRequest(ContactBO contact) throws MySalesException;

	boolean softDeleted(Long ids) throws MySalesException;



	//List<ClientBO> listOfCustomerByPagination(ClientBO customerBO);

	

	Map<Integer, String> retrieveAccounttList();

	//List<ClientBO> listOfopportunityByPagination(ClientBO opportunityBO);

	//List<ClientBO> listOfOpportunityByPagination(ClientBO opportunityBO);

	//List<ClientBO> listOfByPagination(ClientBO opportunityBO);



	
	

}
