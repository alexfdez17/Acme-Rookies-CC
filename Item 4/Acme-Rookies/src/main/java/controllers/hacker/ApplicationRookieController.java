
package controllers.hacker;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ApplicationService;
import services.CompanyService;
import services.PositionProblemService;
import services.PositionService;
import controllers.AbstractController;
import domain.Application;
import domain.Position;

@Controller
@RequestMapping("/application/rookie")
public class ApplicationRookieController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ApplicationRookieController() {
		super();
	}


	@Autowired
	public ActorService				actorService;
	@Autowired
	public PositionService			positionService;
	@Autowired
	public CompanyService			companyService;
	@Autowired
	public PositionProblemService	positionProblemService;
	@Autowired
	public ApplicationService		applicationService;


	// Applications

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@Valid final String Status) {
		final ModelAndView result;
		Collection<Application> applications;
		final String role = "rookie";

		applications = this.applicationService.findCurrentRookieApplicationsStatus(Status);
		result = new ModelAndView("application/rookie/list");
		result.addObject("applications", applications);
		result.addObject("role", role);
		result.addObject("requestURI", "application/rookie/list.do?Status=");

		return result;
	}

	@RequestMapping(value = "/display")
	public ModelAndView display(final Integer ApplicationId) {

		ModelAndView result;
		Application application;
		application = this.applicationService.findOne(ApplicationId);

		result = new ModelAndView("application/rookie/display");
		result.addObject("application", application);
		result.addObject("requestURI", "application/rookie/display.do");

		return result;
	}
	//CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@Valid final Integer PositionId) {
		ModelAndView result;
		Position position;

		position = this.positionService.findOne(PositionId);
		this.applicationService.createApplication(position);

		result = new ModelAndView("redirect:/application/rookie/list.do?Status=");

		return result;
	}
	//UPDATE

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView updateApplication(@RequestParam final int applicationId) {
		final ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);

		result = this.createEditModelAndView(application);
		result.addObject("requestURI", "application/rookie/edit.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateApplication(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;
		final Application app;
		final Authority authH = new Authority();
		authH.setAuthority(Authority.ROOKIE);

		try {
			if (binding.hasErrors())
				result = this.updateApplicationModelAndView("actor.commit.error");
			else {
				if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authH)) {
					Assert.isTrue(application.getStatus().equals("PENDING"));
					Assert.isTrue(application.getAnswerExplanation() != "");
					Assert.notNull(application.getAnswerCode() != "");

					app = this.applicationService.findOne(application.getId());

					app.setAnswerCode(application.getAnswerCode());
					app.setAnswerExplanation(application.getAnswerExplanation());
					app.setSubmittedMoment(new Date());
					app.setStatus("SUBMITTED");

					this.applicationService.save(app);
				}

				result = new ModelAndView("redirect:/application/rookie/list.do?Status=");
			}
		} catch (final Throwable oops) {
			result = this.updateApplicationModelAndView("actor.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Application res) {
		return this.createEditModelAndView(res, null);
	}

	protected ModelAndView createEditModelAndView(final Application res, final String message) {
		final ModelAndView result;

		result = new ModelAndView("application/rookie/edit");
		result.addObject("application", res);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView updateApplicationModelAndView(final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/rookie/edit");

		result.addObject("message", messageCode);

		return result;
	}
}
