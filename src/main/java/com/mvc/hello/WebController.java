package com.mvc.hello;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mvc.dto.PersonDto;

/**
 * A controller that handle a form
 * @author Jerome
 */
@Controller
public class WebController extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
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
		return "redirect:/results?name="+URLEncoder.encode(person.getFirstName()+" "+person.getLastName(), "UTF-8");
    }

    @RequestMapping(value="/hibernate", method=RequestMethod.GET)
    public String hibernate(Model model) {
        return "hibernate";
    }
}