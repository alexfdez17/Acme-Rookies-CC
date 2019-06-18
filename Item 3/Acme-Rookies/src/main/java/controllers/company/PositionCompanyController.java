
package controllers.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.AuditService;
import services.CompanyService;
import services.PositionProblemService;
import services.PositionService;
import controllers.AbstractController;
import domain.Application;
import domain.Audit;
import domain.Company;
import domain.Position;
import domain.PositionProblem;

@Controller
@RequestMapping("/position/company")
public class PositionCompanyController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public PositionCompanyController() {
		super();
	}


	@Autowired
	public PositionService			positionService;

	@Autowired
	public CompanyService			companyService;

	@Autowired
	public PositionProblemService	positionProblemService;

	@Autowired
	public AuditService				auditService;
	@Autowired
	public ApplicationService		applicationService;


	// Positions

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Position> positions;
		final Company company = this.companyService.findByPrincipal();
		positions = this.positionService.findByCompany(company);
		result = new ModelAndView("position/company/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/company/list.do");

		return result;
	}

	@RequestMapping(value = "/display")
	public ModelAndView display(final Integer positionId) {
		ModelAndView result;
		Position position;
		final Company company = this.companyService.findByPrincipal();
		final String role = "company";
		position = this.positionService.findOne(positionId);

		result = new ModelAndView("position/company/display");
		result.addObject("position", position);

		final Collection<Audit> audits = this.auditService.findByPosition(positionId);

		final Collection<Audit> finals = new ArrayList<Audit>();
		for (final Audit a : audits)
			if (!a.isDraftMode())
				finals.add(a);

		if (position.getCompany().equals(company)) {
			final Collection<PositionProblem> problems = this.positionProblemService.findByPosition(position);
			result.addObject("problems", problems);
			final String role2 = "owner";
			result.addObject("role2", role2);
			if (position.getStatus().equals("draft") && problems.size() == 0) {
				final String deletable = "yes";
				result.addObject("deletable", deletable);
			}
			if (position.getStatus().equals("final")) {
				final Collection<Application> applications = this.applicationService.findAcceptedByPosition(position);
				if (applications.isEmpty() && finals.isEmpty()) {
					final String cancelable = "yes";
					result.addObject("cancelable", cancelable);
				}
			}
		}

		result.addObject("role", role);
		result.addObject("audits", audits);
		result.addObject("requestURI", "position/company/display.do");

		return result;
	}
	// Creating --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Position res;

		res = this.positionService.create();

		result = new ModelAndView("position/company/edit");
		result.addObject("position", res);
		result.addObject("id", res.getId());
		return result;
	}
	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId) {
		final ModelAndView result;
		Position res;

		res = this.positionService.findOne(positionId);
		final String st1 = "draft";
		final String st2 = "final";
		final List<String> positionStatus = new ArrayList<String>();
		positionStatus.add(st1);
		positionStatus.add(st2);

		result = this.createEditModelAndView(res);
		result.addObject("positionStatus", positionStatus);
		result.addObject("requestURI", "position/company/edit.do");
		return result;
	}

	// Save --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("position") @Valid final Position res, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(res);
		else
			try {
				this.positionService.save(res);
				result = new ModelAndView("redirect:list.do");
				return result;

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(res, "position.commit.error");
			}
		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int positionId) {
		ModelAndView result;
		final Position position = this.positionService.findOne(positionId);
		try {

			final Company company = this.companyService.findByPrincipal();
			this.positionService.delete(position);
			result = new ModelAndView("position/company/list");
			final Collection<Position> positions = this.positionService.findByCompany(company);
			result.addObject("positions", positions);
			result.addObject("role", "company");
			result.addObject("requestURI", "position/company/list.do");
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/position/company/display.do?positionId=" + positionId);

		}
		return result;
	}

	//Delete
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int positionId) {
		ModelAndView result;
		final Position position = this.positionService.findOne(positionId);
		try {

			final Company company = this.companyService.findByPrincipal();
			this.positionService.cancel(position);
			result = new ModelAndView("position/company/list");
			final Collection<Position> positions = this.positionService.findByCompany(company);
			result.addObject("positions", positions);
			result.addObject("role", "company");
			result.addObject("requestURI", "position/company/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("position/company/display");
			result.addObject("position", position);
			result.addObject("requestURI", "position/company/display.do");
			result.addObject("positionId", position.getId());

		}
		return result;
	}

	// Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Position res) {
		return this.createEditModelAndView(res, null);
	}

	protected ModelAndView createEditModelAndView(final Position res, final String message) {
		final ModelAndView result;

		result = new ModelAndView("position/company/edit");
		result.addObject("position", res);
		result.addObject("id", res.getId());
		result.addObject("message", message);
		return result;
	}
}
