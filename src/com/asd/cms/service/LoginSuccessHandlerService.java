package com.asd.cms.service;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginSuccessHandlerService implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {

		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());


		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/CMS/main/admin");
			return;
		}if (roles.contains("ROLE_HR")) {
			response.sendRedirect("/CMS/main/hr");
			return;
		}if (roles.contains("ROLE_EMPLOYEE")) {
			response.sendRedirect("/CMS/main/expense");
			return;
		}

	}

}
