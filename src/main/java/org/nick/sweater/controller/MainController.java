package org.nick.sweater.controller;

import org.nick.sweater.domain.Message;
import org.nick.sweater.domain.User;
import org.nick.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
//@RequestMapping("/")
public class MainController {
	
	@Autowired
	MessageRepo messageRepo;
	
	@Value("${upload.path}")
	private String uploadPath;


//	@Autowired
//	UserRepo userRepo;
	
	
	@GetMapping("/")
	public String startPage(Model model) {
//		userRepo.deleteAll();
		model.addAttribute("messages", messageRepo.findAll());
		
		return "greetings";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/main")
	public String main(@RequestParam(required = false) String filter, Model model) {
		
		if (filter != null && ! filter.isEmpty()) {
			model.addAttribute("messages", messageRepo.findByTag(filter));
		} else {
			model.addAttribute("messages", messageRepo.findAll());
		}
		
		model.addAttribute("filter", filter);
		return "main";
	}
	
	@PostMapping("/main")
	public String addMessage(
			@AuthenticationPrincipal User user,
			@RequestParam String messagetext,
			@RequestParam String messagetag,
			@RequestParam("file") MultipartFile file
	                        ) throws IOException {
		
		Message newMessage = new Message(messagetext, messagetag, user);
		
		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);
			if (! uploadDir.exists()) {
				uploadDir.mkdir();
			}
			String uuidFile = UUID.randomUUID().toString();
			String resultFileName = uuidFile + "-" + file.getOriginalFilename();
			
			file.transferTo(new File(uploadPath + "/" + resultFileName));
			newMessage.setFilename(resultFileName);
		}
		
//		messageRepo.save(new Message(messagetext, messagetag, user));
		messageRepo.save(newMessage);
		return "redirect:main";
	}


//	<form method="post"  action="filter"> action="filter" -- Аргумент для маппинга
//	@PostMapping("/filter")
////	               <input type="text" name="filter"> || name="filter" Ловим в @RequestParam String filter
//	public String filter(@RequestParam String filter, Model model){
//
//		if (filter!=null && !filter.isEmpty()) {
//			model.addAttribute("messages", messageRepo.findByTag(filter));
//		} else {
//			model.addAttribute("messages", messageRepo.findAll());
//		}
//		return "main";
//	}

//	@GetMapping("/filter")
//	public String filter(){
//		return "main";
//	}
	
	@PostMapping("showall")
	public String showAll(Model model) {
		model.addAttribute("messages", messageRepo.findAll());
		return "main";
	}
	
	
}
