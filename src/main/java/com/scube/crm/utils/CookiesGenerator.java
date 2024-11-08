/**
 * 
 */
package com.scube.crm.utils;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scube.crm.bo.AdminLoginBO;


public class CookiesGenerator {

	public void addCookies(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> cookiesObject,
			String type, boolean rememberMe) {

		if (type.equalsIgnoreCase("jobSeeker")) {
			if (rememberMe) {
				Cookie jEmailCookie = new Cookie("j_cookieEmail",
						cookiesObject.get("email"));
				Cookie jPasswordCookie = new Cookie("j_cookiePass",
						cookiesObject.get("password"));
				Cookie jmobileCookie = new Cookie("j_cookiemobile",
						cookiesObject.get("mobileNo"));
				Cookie jRememerCookie = new Cookie("j_cookieRemember", "1");
				jEmailCookie.setMaxAge(60 * 60 * 24 * 15);// 15 days
				jPasswordCookie.setMaxAge(60 * 60 * 24 * 15);
				jmobileCookie.setMaxAge(60 * 60 * 24 * 15);
				jRememerCookie.setMaxAge(60 * 60 * 24 * 15);
				response.addCookie(jEmailCookie);
				response.addCookie(jPasswordCookie);
				response.addCookie(jRememerCookie);
				response.addCookie(jmobileCookie);
			} else {
				Cookie jEmailCookie = new Cookie("j_cookieEmail", null);
				Cookie jPasswordCookie = new Cookie("j_cookiePass", null);
				Cookie jRememerCookie = new Cookie("j_cookieRemember", null);
				jEmailCookie.setMaxAge(0);
				jPasswordCookie.setMaxAge(0);
				jRememerCookie.setMaxAge(0);
				response.addCookie(jEmailCookie);
				response.addCookie(jPasswordCookie);
				response.addCookie(jRememerCookie);
			}
		}
		if (type.equalsIgnoreCase("employer")) {
			if (rememberMe) {
				Cookie eEmailCookie = new Cookie("e_cookieEmail",
						cookiesObject.get("email"));
				Cookie ePasswordCookie = new Cookie("e_cookiePass",
						cookiesObject.get("password"));
				Cookie eRememerCookie = new Cookie("e_cookieRemember", "1");
				eEmailCookie.setMaxAge(60 * 60 * 24 * 15);// 15 days
				ePasswordCookie.setMaxAge(60 * 60 * 24 * 15);
				eRememerCookie.setMaxAge(60 * 60 * 24 * 15);
				response.addCookie(eEmailCookie);
				response.addCookie(ePasswordCookie);
				response.addCookie(eRememerCookie);
			} else {
				Cookie eEmailCookie = new Cookie("e_cookieEmail", null);
				Cookie ePasswordCookie = new Cookie("e_cookiePass", null);
				Cookie eRememerCookie = new Cookie("e_cookieRemember", null);
				eEmailCookie.setMaxAge(0);
				ePasswordCookie.setMaxAge(0);
				eRememerCookie.setMaxAge(0);
				response.addCookie(eEmailCookie);
				response.addCookie(ePasswordCookie);
				response.addCookie(eRememerCookie);
			}
		}
		if (type.equalsIgnoreCase("admin")) {
			if (rememberMe) {
				Cookie aEmailCookie = new Cookie("a_cookieEmail",
						cookiesObject.get("email"));
				Cookie aPasswordCookie = new Cookie("a_cookiePass",
						cookiesObject.get("password"));
				Cookie aRememerCookie = new Cookie("a_cookieRemember", "1");
				aEmailCookie.setMaxAge(60 * 60 * 24 * 15);// 15 days
				aPasswordCookie.setMaxAge(60 * 60 * 24 * 15);
				aRememerCookie.setMaxAge(60 * 60 * 24 * 15);
				response.addCookie(aEmailCookie);
				response.addCookie(aPasswordCookie);
				response.addCookie(aRememerCookie);
			} else {
				Cookie aEmailCookie = new Cookie("a_cookieEmail", null);
				Cookie aPasswordCookie = new Cookie("a_cookiePass", null);
				Cookie aRememerCookie = new Cookie("a_cookieRemember", null);
				aEmailCookie.setMaxAge(0);
				aPasswordCookie.setMaxAge(0);
				aRememerCookie.setMaxAge(0);
				response.addCookie(aEmailCookie);
				response.addCookie(aPasswordCookie);
				response.addCookie(aRememerCookie);
			}
		}
	}

	public Object cookiesVerifications(HttpServletRequest request,
			Object formObject, String type) {

		Cookie[] cookies = request.getCookies();
			if (type.equalsIgnoreCase("admin")) {
				AdminLoginBO adminLoginBO = (AdminLoginBO) formObject;
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("a_cookieEmail")) {
						adminLoginBO.setEmailAddress(cookie.getValue());
					}
					if (cookie.getName().equals("a_cookiePass")) {
						adminLoginBO.setPassword(cookie.getValue());
					}
					if (cookie.getName().equals("a_cookieRemember")) {
						adminLoginBO.setRememberMe(true);
					}
				}
				return adminLoginBO;
			}
		
		return formObject;
	}
}
