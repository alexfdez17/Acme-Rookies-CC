/*
 * WelcomeController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SystemConfigService;
import domain.Actor;
import domain.SystemConfig;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private ActorService		actorService;
	@Autowired
	private SystemConfigService	systemConfigService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		Actor actor = new Actor();

		final SystemConfig sysConf = this.systemConfigService.findSystemConfiguration();

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		try {
			actor = this.actorService.findByPrincipal();
			final String nameuser = actor.getName() + " " + actor.getSurname();
			result.addObject("name", nameuser);

		} catch (final Throwable oops) {
			actor = null;
		}

		result.addObject("moment", moment);
		result.addObject("welcomeEN", sysConf.getWelcomeMessage());
		result.addObject("welcomeES", sysConf.getSpanishWelcomeMessage());

		return result;
	}
}
