package com.asd.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;






import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import com.asd.cms.dao.CustomUserDetailsDao;
import com.asd.cms.model.TUser;

import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("deprecation")
@Transactional(readOnly = true)
public class CustomUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	CustomUserDetailsDao customUserDetailsDao;

	@Override
	public UserDetails loadUserByUsername(String loginID)
			throws UsernameNotFoundException {
		
		UserDetails user = null;		
		TUser dbUser = null;
		String password = null;
		boolean isEnable = false;

		try {
			dbUser = customUserDetailsDao.getUserByLoginID(loginID);
			if (dbUser != null) {
				password = dbUser.getUserPassword();
				isEnable = dbUser.isUserStatus();
			} else {
				return null;
			}
			user = new org.springframework.security.core.userdetails.User(
					dbUser.getUserLoginId(),
					password, 
					isEnable, 
					true, 
					true, 
					true, 
					getAuthorities(dbUser.getUserRole()));

		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Error in retrieving user");
		}
		return user;
	}

	public Collection<GrantedAuthority> getAuthorities(String access){

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
		//System.out.println("acces: "+access);
		authList.add(new GrantedAuthorityImpl("ROLE_EMPLOYEE"));
		
		if ( access.compareTo("Admin") == 0){
			authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		}else if ( access.compareTo("HR") == 0) {
			authList.add(new GrantedAuthorityImpl("ROLE_HR"));
		}else {
			authList.add(new GrantedAuthorityImpl("ROLE_EMPLOYEE"));
		}
		return authList;

	}

}
