package com.merccann.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminPageMVCController {
	@RequestMapping(value = "/admin/create-match")
	public String user(Model model) {
		return "admin-create-game";
	}
}
