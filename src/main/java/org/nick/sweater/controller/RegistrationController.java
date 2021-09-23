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
	public String registration(){
		return "registration";
	}
	
	@PostMapping("/registration")
//	public String addUser(User user, Model model){
	public String addUser(@RequestParam String username, @RequestParam String password,Model model){
		User userFromDb = userRepo.findByUsername(username);
		if(userFromDb != null){
			model.addAttribute("message", "User exists");
			return "registration";
		}
//		User newUser = new User(username,password,true,Collections.singleton(Role.USER));
//		user.setActive(true);
//		user.setRoles(Collections.singleton(Role.USER));
		userRepo.save(new User(username,password,true,Collections.singleton(Role.USER)));
		return "redirect:/login";
	}
	
}
