package com.mvc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.dto.PersonDto;

/**
 * A controller that handle a form
 * @author Jerome
 */
@Controller
public class WebController {

	@RequestMapping("/login")
	public String login(Model model, Principal principal) {
		
		// Go to index if already logged in
		if (principal!=null) {
			return "redirect:/";
		}
		
		return "login";
	}

	@RequestMapping("/")
	public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model, Principal principal) {
		
		// Check user
		if (principal!=null) {
			model.addAttribute("name", principal.getName());
		} else {
			model.addAttribute("name", name);
		}
		
		return "index";
	}

    @RequestMapping(value="/hibernate", method=RequestMethod.GET)
    public String hibernate(Model model) {
        return "hibernate";
    }

    @RequestMapping(value="/person", method=RequestMethod.GET)
    public String showForm(PersonDto person, Model model) {
        model.addAttribute("person", new PersonDto());
        return "form";
    }

    @RequestMapping(value="/person/check", method=RequestMethod.POST)
    public String checkPersonInfo(@Valid @ModelAttribute("person") PersonDto person, BindingResult bindingResult, Model model) throws UnsupportedEncodingException {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        // Test redirection
		return "redirect:/results?name="+URLEncoder.encode(person.getFirstName()+" "+person.getLastName(), "UTF-8");
    }
}