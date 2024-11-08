package com.scube.crm.dao;

import java.util.List;
import java.util.Map;

import com.scube.crm.bo.ContactBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.ContactVO;

public interface ContactDAO {

	long createContact(ContactVO contactVO) throws MySalesException;

	long contactCount(ContactBO contact) throws MySalesException;

	List<ContactVO> retrieveContact(ContactBO contact) throws MySalesException;

	long contactssCount(ContactBO contactbo) throws MySalesException;

	List<ContactVO> searchContactsList(ContactVO contactdos) throws MySalesException;

	ContactVO findById(Long contactId) throws MySalesException;

	ContactVO retrievecontact(Long conId) throws MySalesException;

	boolean updateContact(ContactVO contactVO) throws MySalesException;

	boolean softDeleted(Long ids) throws MySalesException;

	

	Map<Integer, String> retrieveAccounttList();

	

}
