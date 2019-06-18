
package controllers.company;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ApplicationService;
import services.CompanyService;
import services.MessageService;
import services.PositionProblemService;
import services.PositionService;
import controllers.AbstractController;
import domain.Application;

@Controller
@RequestMapping("/application/company")
public class ApplicationCompanyController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ApplicationCompanyController() {
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

	@Autowired
	private MessageService			messageService;


	// Applications

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@Valid final String Status) {
		final ModelAndView result;
		Collection<Application> applications;
		final String role = "company";

		applications = this.applicationService.findCurrentCompanyApplicationsStatus(Status);
		result = new ModelAndView("application/company/list");
		result.addObject("applications", applications);
		result.addObject("role", role);
		result.addObject("requestURI", "application/company/list.do?Status=");

		return result;
	}

	@RequestMapping(value = "/display")
	public ModelAndView display(final Integer ApplicationId) {

		ModelAndView result;
		Application application;
		application = this.applicationService.findOne(ApplicationId);

		result = new ModelAndView("application/company/display");
		result.addObject("application", application);
		result.addObject("requestURI", "application/company/display.do");

		return result;
	}

	@RequestMapping(value = "/acceptApplication", method = RequestMethod.GET)
	public ModelAndView acceptApplication(@Valid final Integer ApplicationId) {
		ModelAndView result;
		Application application;
		application = this.applicationService.findOne(ApplicationId);
		Collection<Application> applications;
		final Authority authC = new Authority();
		authC.setAuthority(Authority.COMPANY);

		try {

			if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authC)) {
				applications = this.applicationService.findCurrentCompanyApplications();
				Assert.isTrue(application.getStatus().equals("SUBMITTED"));
				Assert.isTrue(applications.contains(application));

				applications = this.applicationService.findByPositionId(application.getPositionProblem().getPosition().getId());
				applications.remove(application);

				for (final Application application2 : applications) {
					application2.setStatus("REJECTED");
					this.applicationService.save(application2);
					this.messageService.applicationNotification(application2);
				}

				application.setStatus("ACCEPTED");
				this.applicationService.save(application);
				this.messageService.applicationNotification(application);
			}

			result = new ModelAndView("redirect:/application/company/list.do?Status=");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/company/list.do?Status=");
		}
		return result;
	}

	@RequestMapping(value = "/rejectApplication", method = RequestMethod.GET)
	public ModelAndView rejectApplication(@Valid final Integer ApplicationId) {
		ModelAndView result;
		Application application;
		application = this.applicationService.findOne(ApplicationId);
		Collection<Application> applications;
		final Authority authC = new Authority();
		authC.setAuthority(Authority.COMPANY);

		try {
			if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authC)) {
				applications = this.applicationService.findCurrentCompanyApplications();
				Assert.isTrue(application.getStatus().equals("SUBMITTED"));
				Assert.isTrue(applications.contains(application));

				application.setStatus("REJECTED");
				this.applicationService.save(application);
				this.messageService.applicationNotification(application);
			}

			result = new ModelAndView("redirect:/application/company/list.do?Status=");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/company/list.do?Status=");
		}
		return result;
	}

	protected ModelAndView acceptApplicationModelAndView(final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/company/display");

		result.addObject("message", messageCode);

		return result;
	}
}
