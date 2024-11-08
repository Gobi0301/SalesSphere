package com.scube.crm.utils;

import javax.mail.MessagingException;

import com.scube.crm.model.EmailModel;

public interface SendEmailService {

	
	public boolean sendEmail(String emailTo, String subject, String message,EmailModel model) throws MessagingException;

	/**
	 * @param bodyContent
	 * @param subject
	 * @param toaddress
	 * @return
	 */
	

	/**
	 * @param bodyContent
	 * @param subject
	 * @param toaddress
	 * @param model
	 * @return
	 */
	public  boolean sendloginmail(String bodyContent, String subject,
			String toaddress, EmailModel model) throws MessagingException;
	

	
	

}
