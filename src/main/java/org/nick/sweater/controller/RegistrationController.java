package org.nick.sweater.controller;

import org.nick.sweater.domain.Role;
import org.nick.sweater.domain.User;
import org.nick.sweater.repos.UserRepo;
import org.nick.sweater.service.MailSender;
import org.nick.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.UUID;

@Controller
public class RegistrationController {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/registration")
	public String registrationPage() {
		return "registration";
	}
	
	@PostMapping("registration")
	public String addNewUser(@RequestParam String username, @RequestParam String password, Model model) {
//		User userFromDB=userRepo.findByUsername(username);
//		if(userFromDB!=null){
		if (userRepo.findByUsername(username) != null) {
			model.addAttribute("message", "User already exists");
			return "registration";
		} else {
			User user = new User(username, password);
			user.setActive(true);
			user.setRoles(Collections.singleton(Role.USER));
			user.setActivationCode(UUID.randomUUID().toString());
			if (!StringUtils.isEmpty(user.getEmail())) {
				String message = String.format(
						"Hello, %s!\n " +
								"Welcome to Sweater. \n" +
								"Please visit next link http://localhost/activate/%s",
						user.getUsername(),
						user.getActivationCode()
				                              );
				mailSender.send(user.getEmail(),"Activation code", message);
			}
			
			userRepo.save(user);
		}
		return "login";
	}
	
	@GetMapping("/activate/{code}")
	public String activate(@PathVariable String code, Model model){
		
		boolean isActivated = userService.activateUser(code);
		
		return "login";
	}
	
}//class end
