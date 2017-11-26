package com.sps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sps.entity.Person;
import com.sps.service.PersonService;

@Controller
@SessionAttributes("person")
public class PersonUpdateController {

	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/updatePerson/{id}", method = RequestMethod.GET)
	public String selectForUpdate(@PathVariable Long id, Model model) {
		model.addAttribute("person", personService.findById(id));
		return "personUpdate";
	}

	@RequestMapping(value = "/updatePersonFailed", method = RequestMethod.GET)
	public String updatePersonFailed() {
		return "personUpdate";
	}

	@RequestMapping(value = "/updatePerson", method = RequestMethod.POST)
	public String performUpdate(@ModelAttribute Person person, RedirectAttributes redirectAttributes,
			SessionStatus sessionStatus) {
		String message = null;
		String viewName = null;
		try {
			personService.update(person);
			message = "Person updated. Person id :" + person.getId();
			viewName = "redirect:/mvc/listPersons";
			sessionStatus.setComplete();
		} catch (Exception ex) {
			message = "Person update failed. ";
			viewName = "redirect:/mvc/updatePersonFailed";
		}
		redirectAttributes.addFlashAttribute("message", message);
		return viewName;
	}
}
