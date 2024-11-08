package com.scube.crm.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public AuthenticationManager getAuthenticationManagerBean() throws Exception {
		return authenticationManagerBean();
	}
	

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
		
	
	 
	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception { http
	 * .authorizeRequests() .antMatchers("/","/admin-sign-in").permitAll()
	 * .antMatchers("/resources/**").permitAll() .and() .formLogin()
	 * .loginPage("/admin-sign-in").permitAll()
	 * .loginProcessingUrl("/admin-sign-in").usernameParameter("emailAddress").
	 * passwordParameter("password").permitAll() .defaultSuccessUrl("/admin-home")
	 * .failureUrl("/admin-sign-in") .and() .logout().logoutSuccessUrl("/logout")
	 * .permitAll() .and().exceptionHandling().accessDeniedPage("/admin-sign-in"); }
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http		
		.authorizeRequests()
		.antMatchers("/","/admin-sign-in").permitAll()
		.antMatchers("/resources/**").permitAll()		 
		.and()
		.formLogin()
		.loginPage("/admin-sign-in").permitAll()
		.loginProcessingUrl("/admin-sign-in").usernameParameter("emailAddress").passwordParameter("password").permitAll()
		.defaultSuccessUrl("/admin-home")
		.failureUrl("/admin-sign-in?error=true")	
		.and()
		.logout().logoutSuccessUrl("/logout")
		.permitAll()
		.and().exceptionHandling().accessDeniedPage("/admin-sign-in");
	}


}