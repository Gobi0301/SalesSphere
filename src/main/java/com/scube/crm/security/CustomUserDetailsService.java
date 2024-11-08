package com.scube.crm.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scube.crm.dao.AdminDAO;
import com.scube.crm.utils.EncryptAndDecrypt;
import com.scube.crm.vo.AccessVo;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.PrivilegesVO;
import com.scube.crm.vo.RolesVO;
import com.scube.crm.vo.User;

@Service("customUserDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminDAO dao;

	private boolean pocOfCompany = false;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	String salt = "this is a simple clear salt";
	private final Logger LOGGER = Logger.getLogger(CustomUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		List<String> userType = new ArrayList<String>();
		if (null != username) {
			try {
				user = dao.authendicate(username);
//				userType.add(user.getListRoletypeVo().get(0).getRoleName());
//				System.out.println(user.getListRoletypeVo().get(0).getRoleName());
				isPrimaryContact(username, user);
				if (null != user) {
					return new MySalesUser(user.getEmailAddress(), getPwd(user.getPassword()), true, true, true, true,
							getAuthorities(user.getListRoletypeVo(),userType,user.getCompany().isCompanyOwner()), user.getId(), user.getName(), userType,
							user.getCompany().getCompanyId(), user.getCompany().getIndustryType());
				}

			} catch (Exception he) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("FROM INFO: Exception \t" + he);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("FROM DEBUG : Exception \t" + he);
				}

			}
		}

		return new org.springframework.security.core.userdetails.User(null, null, null);
	}

	private String getPwd(String pwd) throws Exception {
		String decrypted = EncryptAndDecrypt.decrypt(pwd);
		System.out.println(decrypted);
		return passwordEncoder().encode(decrypted);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(List<RolesVO> userRole, List<String> userType,boolean isCompanyOwner) {

		List<PrivilegesVO> privillegesList = new ArrayList<PrivilegesVO>();
		for (RolesVO rolesVO : userRole) {
			privillegesList.addAll(rolesVO.getListPrivilegesvo());
			userType.add(rolesVO.getRoleName());
		}
		return getGrantedAuthorities(getPrivileges(privillegesList),userType,isCompanyOwner);
	}

	private List<PrivilegesVO> getPrivileges(List<PrivilegesVO> privillegesList) {
		List<PrivilegesVO> privileges = new ArrayList<PrivilegesVO>();

		for (PrivilegesVO privillege : privillegesList) {
			privileges.add(privillege);
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<PrivilegesVO> privileges, List<String> userType,boolean isCompanyOwner) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		for (PrivilegesVO privilege : privileges) {
			for(AccessVo access:privilege.getAccessList()) {
			authorities.add(new SimpleGrantedAuthority(access.getAccessName()));
			}
			
		}
		return authorities;
	}

	private void isPrimaryContact(String userName, User user) {
		

	}

}
