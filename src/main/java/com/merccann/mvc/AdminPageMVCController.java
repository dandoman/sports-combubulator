package com.merccann.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminPageMVCController {
	@RequestMapping(value = "/admin")
	public String user(Model model) {
		return "admin";
	}
}
