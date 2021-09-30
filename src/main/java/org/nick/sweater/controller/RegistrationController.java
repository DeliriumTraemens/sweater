package org.nick.sweater.controller;

import org.nick.sweater.domain.Role;
import org.nick.sweater.domain.User;
import org.nick.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegistrationController {
	@Autowired
	UserRepo userRepo;
	
	@GetMapping("/registration")
		public String registrationPage(){
		return "registration";
	}
	
	@PostMapping("registration")
	public String addNewUser(@RequestParam String username, @RequestParam String password, Model model){
//		User userFromDB=userRepo.findByUsername(username);
//		if(userFromDB!=null){
		if(userRepo.findByUsername(username)!=null){
			model.addAttribute("message","User already exists");
			return "registration";
		} else{
			User user = new User(username,password);
			user.setActive(true);
			user.setRoles(Collections.singleton(Role.USER));
			userRepo.save(user);
		}
		return "login";
	}
}
