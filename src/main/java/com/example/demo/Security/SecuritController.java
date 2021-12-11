package com.example.demo.Security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecuritController {
	@GetMapping("/403")
	public String error() {
		return "403";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
