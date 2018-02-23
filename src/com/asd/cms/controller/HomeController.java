package com.asd.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*@Configuration
@ComponentScan("com.asd.cms.service")*/

@Controller
public class HomeController {
		
	@RequestMapping("/")
	public String home(){
		//System.out.println("This is from HomeController.");
		return "redirect:/auth/login";
	}
}