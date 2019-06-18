
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ApplicationService;
import domain.Application;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ApplicationController() {
		super();
	}


	@Autowired
	public ApplicationService	applicationService;
	@Autowired
	public ActorService			actorService;


	// Applications

	@RequestMapping(value = "/display")
	public ModelAndView display(final Integer ApplicationId) {

		ModelAndView result;
		Application application;
		application = this.applicationService.findOne(ApplicationId);
		String role = "";
		final Authority authH = new Authority();
		authH.setAuthority(Authority.ROOKIE);
		final Authority authC = new Authority();
		authC.setAuthority(Authority.COMPANY);

		if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authH))
			role = "rookie";
		else if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authC))
			role = "company";

		result = new ModelAndView("application/display");
		result.addObject("application", application);
		result.addObject("role", role);
		result.addObject("requestURI", "application/display.do");

		return result;
	}
}
