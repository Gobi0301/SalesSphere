/**
 * 
 */
package com.scube.crm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.scube.crm.vo.BasicEntity;

/**
 * @author User
 *
 */

	@Entity
	@Table(name = "em_login")
	public class EmployerLoginVO extends BasicEntity {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue
		private long id;

		/**
		 * @return the id
		 */
		public long getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(long id) {
			this.id = id;
		}

		/**
		 * @return the emailAddress
		 */
		public String getEmailAddress() {
			return emailAddress;
		}

		/**
		 * @param emailAddress the emailAddress to set
		 */
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}

		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		 * @return the confirmPassword
		 */
		public String getConfirmPassword() {
			return confirmPassword;
		}

		/**
		 * @param confirmPassword the confirmPassword to set
		 */
		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}

		/**
		 * @return the isActive
		 */
		public boolean isActive() {
			return isActive;
		}

		/**
		 * @param isActive the isActive to set
		 */
		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}

		/**
		 * @return the employerRegistration
		 */
		public EmployerVO getEmployerRegistration() {
			return employerRegistration;
		}

		/**
		 * @param employerRegistration the employerRegistration to set
		 */
		public void setEmployerRegistration(EmployerVO employerRegistration) {
			this.employerRegistration = employerRegistration;
		}

		@Column(name = "email_id")
		private String emailAddress;

		@Column(name = "password")
		private String password;

		@Column(name = "confirmPassword")
		private String confirmPassword;

		@Column(name = "active")
		private boolean isActive;

		@ManyToOne
		@JoinColumn(name = "em_Id")
		private EmployerVO employerRegistration;

}
