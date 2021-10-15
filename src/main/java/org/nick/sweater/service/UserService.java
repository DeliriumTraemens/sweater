package org.nick.sweater.service;

import org.nick.sweater.domain.Role;
import org.nick.sweater.domain.User;
import org.nick.sweater.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
	
	private final UserRepo userRepo;
	
	public UserService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username);
	}
	
	public boolean activateUser(String code) {
		User user = userRepo.findByActivationCode(code);
		
			if (user == null) {
			return false;
			}
		
		user.setActivationCode(null); //означает, что юзер подтвердил свой ящик, и удаляем код, как более не нужный
		
		userRepo.save(user);
		
		return true;
	}

//	public boolean addUser(User user){
//		User userFromDb = userRepo.findByUsername(user.getUsername());
//		if(userFromDb!=null){
//			return false;
//		}
//
//		user.setActive(true);
//		user.setRoles(Collections.singleton(Role.USER));
//		userRepo.save(user);
//
//		return true;
//	}
	
}
