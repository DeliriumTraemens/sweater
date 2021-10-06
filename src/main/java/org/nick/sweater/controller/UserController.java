package org.nick.sweater.controller;

import org.nick.sweater.domain.Role;
import org.nick.sweater.domain.User;
import org.nick.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('ADMIN','SUPERADMIN')")
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping
	public String userList(Model model){
		model.addAttribute("users", userRepo.findAll());
		return "userList";
	}
	
	@GetMapping("{user}")
	public String userEditForm(@PathVariable User user, Model model){
		model.addAttribute("currentUser",user);
		model.addAttribute("rolesList", Role.values());
	return "useredit";
	}
	
	@PostMapping
	public String userSave(@RequestParam String username,
	                       @RequestParam Map<String, String> form,
	                       @RequestParam("userId") User user){
		user.setUsername(username);
//		Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
		
		Set<String> roles = new HashSet<>();
		for (Role role : Role.values()) {
			String name = role.name();
			roles.add(name);
		}
		
		user.getRoles().clear();
		
		for(String key : form.keySet()){
			if(roles.contains(key)){
				user.getRoles().add(Role.valueOf(key));
			}
		}
		
		userRepo.save(user);
		
		return "redirect:/user";
	}
	
}//Class end
