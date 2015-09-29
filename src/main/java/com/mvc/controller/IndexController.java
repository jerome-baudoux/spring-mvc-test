package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A simple HTML template base controller
 * @author Jerome
 */
@Controller
public class IndexController {

	/**
	 * Index
	 * @param name optional
	 * @param model model
	 * @return page name
	 */
	@RequestMapping("/")
	public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "index";
	}

	/**
	 * Index
	 * @param name optional
	 * @param model model
	 * @return page name
	 */
	@RequestMapping("/ajax")
	public String greeting(Model model) {
		return "ajax";
	}

}
