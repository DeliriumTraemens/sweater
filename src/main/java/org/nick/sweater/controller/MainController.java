package org.nick.sweater.controller;

import org.nick.sweater.domain.Message;
import org.nick.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
//@RequestMapping("/")
public class MainController {

	@Autowired
	MessageRepo messageRepo;
	
//	@Autowired
//	UserRepo userRepo;
	
	
	@GetMapping("/")
	public String startPage(Model model){
//		userRepo.deleteAll();
		model.addAttribute("messages",messageRepo.findAll());
		
		return "greetings";
	}
	
	@GetMapping("/login")
		public String loginPage(){
		return "login";
	}
	
	// TODO: 30.09.2021 Разобраться с инжектом Principal
	@GetMapping("/main")
	public String main(/*Principal principal, */Model model){
//		Authentication a = SecurityContextHolder.getContext().getAuthentication();
//		String username = principal.getName();
//			model.addAttribute("auth",a);
//			model.addAttribute("usrdetailes",principal);
			model.addAttribute("messages",messageRepo.findAll());
		return "main";
	}
	
	@PostMapping("/main")
	public String addMessage(@RequestParam String messagetext, @RequestParam String messagetag){
		Message newMessage = new Message(messagetext,messagetag);
		messageRepo.save(newMessage);
		return "redirect:main";
	}
	
//	<form method="post"  action="filter"> action="filter" -- Аргумент для маппинга
	@PostMapping("/filter")
//	               <input type="text" name="filter"> || name="filter" Ловим в @RequestParam String filter
	public String filter(@RequestParam String filter, Model model){
		
		if (filter!=null && !filter.isEmpty()) {
			model.addAttribute("messages", messageRepo.findByTag(filter));
		} else {
			model.addAttribute("messages", messageRepo.findAll());
		}
		return "main";
	}
	
//	@GetMapping("/filter")
//	public String filter(){
//		return "main";
//	}
	
	@PostMapping("showall")
	public String showAll(Model model){
		model.addAttribute("messages", messageRepo.findAll());
		return "main";
	}
	

}
