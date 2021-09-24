package org.nick.sweater.controller;

import org.nick.sweater.domain.Message;
import org.nick.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	@Autowired
	MessageRepo messageRepo;
	
//	@Autowired
//	UserRepo userRepo;
	
	
	@GetMapping("/")
	public String startPage(Model model){
//		userRepo.deleteAll();
		model.addAttribute("messages",messageRepo.findAll());
		
		return "main";
	}
	
//	@GetMapping("/login")
//		public String loginPage(){
//		return "login";
//	}
	
	@GetMapping("/main")
	public String main(Model model){
			model.addAttribute("messages",messageRepo.findAll());
		return "main";
	}
	
	@PostMapping("/main")
	public String addMessage(@RequestParam String text, @RequestParam String tag){
			messageRepo.save(new Message(text,tag));
		return "main";
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
	
	@PostMapping("showall")
	public String showAll(Model model){
		model.addAttribute("messages", messageRepo.findAll());
		return "main";
	}
	

}
