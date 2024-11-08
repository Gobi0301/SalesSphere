package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.ContactBO;
import com.scube.crm.bo.OpportunityBO;
import com.scube.crm.dao.AdminDAO;
import com.scube.crm.dao.ContactDAO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.ContactVO;
import com.scube.crm.vo.Opportunity;
import com.scube.crm.vo.User;
@Service
@Transactional
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private AdminDAO adminDAO;

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(ContactServiceImpl.class);
	@Autowired
	private ContactDAO contactDAO;
	
	@Autowired
	private OpportunityService opportunityService;
	
	
	
	@Override
      public List<User> retrieveUser(User user) throws MySalesException {
		// TODO Auto-generated method stub
		return adminDAO.retrieveUser(user);
	}
	@Override
	public long createContact(ContactBO contactBO) throws MySalesException {
		// TODO Auto-generated method stub
		ContactVO contactVO=new ContactVO();
		if(null!=contactBO) {

			//contactVO.setAccountname(contactBO.getAccountname());
			contactVO.setSalutation(contactBO.getSalutation());
			contactVO.setPhone(contactBO.getPhone());
			contactVO.setEmail(contactBO.getEmail());
			contactVO.setFirstname(contactBO.getFirstname());
			contactVO.setLastname(contactBO.getLastname());
			//contactVO.setLeadsourse(contactBO.getLeadsourse());
			//account
			AccountVO accountVO=new AccountVO();
			if(null!=contactBO.getAccountBO()  && 0<contactBO.getAccountBO().getAccountId()) {
				accountVO.setAccountId(contactBO.getAccountBO().getAccountId());
			}
			contactVO.setAccount(accountVO);

			Opportunity ccustomer=new Opportunity();
			if(null!=contactBO.getOpportunityBO() && 0<contactBO.getOpportunityBO().getOpportunityId()) {
				ccustomer.setOpportunityId(contactBO.getOpportunityBO().getOpportunityId());

			}
			contactVO.setOpportunity(ccustomer);
			//user
			User user=new User();
			if(null!=contactBO.getAssignedTo() && 0<contactBO.getAssignedTo().getId()) {
				user.setId(contactBO.getAssignedTo().getId());
			}
			contactVO.setAssignedTo(user);

			contactVO.setDeleted(contactBO.isDeleted());
			contactVO.setState(contactBO.getState());
			contactVO.setStreet(contactBO.getStreet());
			contactVO.setCity(contactBO.getCity());
			contactVO.setCountry(contactBO.getCountry());

		}



		return contactDAO.createContact(contactVO);
	}
	@Override
	public long contactCount(ContactBO contact) throws MySalesException {
		// TODO Auto-generated method stub
		return contactDAO.contactCount(contact);
	}
	@Override
	public List<ContactBO> retrieveContact(ContactBO contact) throws MySalesException {
		// TODO Auto-generated method stub


		List<ContactBO> listBo=new ArrayList<>();

		List<ContactVO> listVo=new ArrayList<>();


		listVo=contactDAO.retrieveContact(contact);

		listBo=listVo.stream().map(contactVO->{

			ContactBO contactBO=new ContactBO();
			contactBO.setContactId(contactVO.getContactId());
			if(null!=contactVO.getFirstname()) {
				contactBO.setFirstname(contactVO.getFirstname());	
			}
			if(null!=contactVO.getEmail()) {
				contactBO.setEmail(contactVO.getEmail());
			}
			if(0<contactVO.getPhone()) {
				contactBO.setPhone(contactVO.getPhone());
			}
			if(null!=contactVO.getCountry()) {
				contactBO.setCountry(contactVO.getCountry());
			}
			if(null!=contactVO.getLastname()) {
				contactBO.setLastname(contactVO.getLastname());
			}
			//account
			AccountBO accountBO=new AccountBO();
			if(null!=contactVO.getAccount() && !contactVO.getAccount().getAccountName().isEmpty()) {

				accountBO.setAccountName(contactVO.getAccount().getAccountName());
			}
			contactBO.setAccountBO(accountBO);

			OpportunityBO ccustomer=new OpportunityBO();
			if(null!=contactVO.getOpportunity() && !contactVO.getOpportunity().getFirstName().isEmpty()) {
				ccustomer.setFirstName(contactVO.getOpportunity().getFirstName());

			}
			contactBO.setOpportunityBO(ccustomer);
			return contactBO;
		}).collect(Collectors.toList());


		return listBo;
	}
	@Override
	public long contactssCount(ContactBO contactbo) throws MySalesException {
		// TODO Auto-generated method stub
		return contactDAO.contactssCount(contactbo);
	}
	@Override
	public List<ContactBO> searchContactsList(ContactBO contactbo) throws MySalesException {
		// TODO Auto-generated method stub

		List<ContactBO> contactList = new ArrayList<ContactBO>();
		List<ContactVO> contactvoList = new ArrayList<ContactVO>();
		ContactVO contactdos = new ContactVO();

		contactdos.setFirstname(contactbo.getFirstname());	
		contactdos.setEmail(contactbo.getEmail());
		//customer
		Opportunity ccustomer=new Opportunity();
		if(null!=contactbo.getOpportunityBO() && 0!=contactbo.getOpportunityBO().getOpportunityId()) {
			ccustomer.setOpportunityId(contactbo.getOpportunityBO().getOpportunityId());

		}
		contactdos.setOpportunity(ccustomer);
		contactdos.setRecordIndex(contactbo.getRecordIndex());
		contactdos.setMaxRecord(contactbo.getMaxRecord());
		contactvoList = contactDAO.searchContactsList(contactdos);

		if(contactvoList!=null && contactvoList.size()>0)
		{


			contactvoList.forEach(contacts->{

				ContactBO contactdo = new ContactBO();
				contactdo.setContactId(contacts.getContactId());
				if(null!=contacts.getFirstname()) {
					contactdo.setFirstname(contacts.getFirstname());	
				}
				if(null!=contacts.getEmail()) {
					contactdo.setEmail(contacts.getEmail());
				}
				if(0<contacts.getPhone()) {
					contactdo.setPhone(contacts.getPhone());
				}
				if(null!=contacts.getCountry()) {
					contactdo.setCountry(contacts.getCountry());
				}
				if(null!=contacts.getLastname()) {
					contactdo.setLastname(contacts.getLastname());
				}
				//account
				AccountBO accountBO=new AccountBO();
				if(null!=contacts.getAccount() && !contacts.getAccount().getAccountName().isEmpty()) {

					accountBO.setAccountName(contacts.getAccount().getAccountName());
				}
				contactdo.setAccountBO(accountBO);

				OpportunityBO customer=new OpportunityBO();
				if(null!=contacts.getOpportunity() && !contacts.getOpportunity().getFirstName().isEmpty()) {
					customer.setFirstName(contacts.getOpportunity().getFirstName());

				}
				contactdo.setOpportunityBO(customer);
				contactList.add(contactdo);

			});
		}
	

		return contactList;
	}
	@Override
	public ContactBO findById(Long contactId) throws MySalesException {
		// TODO Auto-generated method stub
		ContactVO contacts = new ContactVO();
		contacts =contactDAO.findById(contactId);


		if(null!=contacts) {
			ContactBO contactdo = new ContactBO();
			contactdo.setContactId(contacts.getContactId());
			contactdo.setFirstname(contacts.getFirstname());	
			contactdo.setEmail(contacts.getEmail());
			contactdo.setPhone(contacts.getPhone());
			//	contactdo.setAccountname(contacts.getAccountname());
			contactdo.setCountry(contacts.getCountry());
			contactdo.setLastname(contacts.getLastname());

			contactdo.setCity(contacts.getCity());
			contactdo.setState(contacts.getState());
			contactdo.setStreet(contacts.getStreet());
			contactdo.setSalutation(contacts.getSalutation());

			//account
			AccountBO accountBO=new AccountBO();
			if(null!=contacts.getAccount() && !contacts.getAccount().getAccountName().isEmpty()) {

				accountBO.setAccountName(contacts.getAccount().getAccountName());
			}
			contactdo.setAccountBO(accountBO);

			OpportunityBO ccustomer=new OpportunityBO();
			if(null!=contacts.getOpportunity() && 0<contacts.getOpportunity().getOpportunityId()) {
				ccustomer.setOpportunityId(contacts.getOpportunity().getOpportunityId());

			}
			contactdo.setOpportunityBO(ccustomer);
			//user
			User user=new User();
			AdminUserBO adminUserBO=new AdminUserBO();
			if(null!=contacts.getAssignedTo()&&!contacts.getAssignedTo().getName().isEmpty()) {
				user.setName(contacts.getAssignedTo().getName());

				adminUserBO.setName(user.getName());
			}
			contactdo.setAssignedTo(adminUserBO);

			return contactdo;
		}
		return null;

	}
	@Override
	public ContactBO retrievecontact(Long conId) throws MySalesException {
		// TODO Auto-generated method stub
		ContactVO contacts = new ContactVO();
		contacts =contactDAO.retrievecontact(conId);


		if(null!=contacts) {
			ContactBO contactdo = new ContactBO();
			contactdo.setContactId(contacts.getContactId());
			contactdo.setFirstname(contacts.getFirstname());	
			contactdo.setEmail(contacts.getEmail());
			contactdo.setPhone(contacts.getPhone());

			contactdo.setCountry(contacts.getCountry());
			contactdo.setLastname(contacts.getLastname());
			//contactdo.setLeadsourse(contacts.getLeadsourse());
			contactdo.setCity(contacts.getCity());
			contactdo.setState(contacts.getState());
			contactdo.setStreet(contacts.getStreet());
			contactdo.setSalutation(contacts.getSalutation());
			//account
			AccountBO accountBO= new AccountBO ();
			if(null!=contacts.getAccount()&& 0<contacts.getAccount().getAccountId()) {

				accountBO.setAccountId(contacts.getAccount().getAccountId());

			}
			contactdo.setAccountBO(accountBO);



			OpportunityBO ccustomer=new OpportunityBO();
			if(null!=contacts.getOpportunity() && 0<contacts.getOpportunity().getOpportunityId()) {
				ccustomer.setOpportunityId(contacts.getOpportunity().getOpportunityId());

			}
			contactdo.setOpportunityBO(ccustomer);

			//user
			User user=new User();
			AdminUserBO adminUserBO=new AdminUserBO();
			if(null!=contacts.getAssignedTo() && 0<contacts.getAssignedTo().getId()) {
				user.setId(contacts.getAssignedTo().getId());

				adminUserBO.setId(user.getId());
			}
			contactdo.setAssignedTo(adminUserBO);


			return contactdo;
		}
		return null;


	}
	@Override
	public boolean updateRequest(ContactBO contact) throws MySalesException {
		// TODO Auto-generated method stub
		ContactVO contactVO=new ContactVO();
		if(null!=contact) {
			contactVO.setDeleted(false);
			contactVO.setContactId(contact.getContactId());

			contactVO.setSalutation(contact.getSalutation());
			contactVO.setPhone(contact.getPhone());
			contactVO.setEmail(contact.getEmail());
			contactVO.setFirstname(contact.getFirstname());
			contactVO.setLastname(contact.getLastname());
			//contactVO.setLeadsourse(contact.getLeadsourse());
			contactVO.setCreatedBy(contact.getCreatedBy());
			contactVO.setModifiedBy(contact.getModifiedBy());

			//account
			AccountVO accountVO=new AccountVO();
			if(null!=contact.getAccountBO()  && 0<contact.getAccountBO().getAccountId()) {
				accountVO.setAccountId(contact.getAccountBO().getAccountId());
			}
			contactVO.setAccount(accountVO);


			Opportunity ccustomer=new Opportunity();
			if(null!=contact.getOpportunityBO() && 0<contact.getOpportunityBO().getOpportunityId()) {
				ccustomer.setOpportunityId(contact.getOpportunityBO().getOpportunityId());

			}
			contactVO.setOpportunity(ccustomer);
			User user=new User();
			if(null!=contact.getAssignedTo() && 0<contact.getAssignedTo().getId()) {
				user.setId(contact.getAssignedTo().getId());
			}
			contactVO.setAssignedTo(user);

			contactVO.setDeleted(contact.isDeleted());
			contactVO.setState(contact.getState());
			contactVO.setStreet(contact.getStreet());
			contactVO.setCity(contact.getCity());
			contactVO.setCountry(contact.getCountry());

		}



		return contactDAO.updateContact(contactVO);
	}
	@Override
	public boolean softDeleted(Long ids) throws MySalesException {
		// TODO Auto-generated method stub


		return contactDAO.softDeleted(ids);
	}

	
	@Override
	public Map<Integer, String> retrieveAccounttList() {
		// TODO Auto-generated method stub
		return contactDAO.retrieveAccounttList();
	}
	
	
	
		
}