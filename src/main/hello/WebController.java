package main.hello;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
    public String showForm(Person person, Model model) {
        model.addAttribute("person",new Person());
        return "form";
    }

    @RequestMapping(value="/person", method=RequestMethod.POST)
    public String checkPersonInfo(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        return "redirect:/results?name="+person.getName();
    }

    /**
     * A simple POJO representing a person
     * @author Jerome
     */
    public static class Person {
    	
        @Size(min=2, max=30)
        private String name;

        @NotNull
        @Min(18)
        private Integer age;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}