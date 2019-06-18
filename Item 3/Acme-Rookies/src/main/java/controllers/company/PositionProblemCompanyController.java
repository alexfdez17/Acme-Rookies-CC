
package controllers.company;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.PositionProblemService;
import services.PositionService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import domain.PositionProblem;

@Controller
@RequestMapping("/positionProblem/company")
public class PositionProblemCompanyController extends AbstractController {

	@Autowired
	private PositionProblemService	positionProblemService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private CompanyService			companyService;


	// Listing --------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<PositionProblem> problems;

		final Company principal = this.companyService.findByPrincipal();

		problems = this.positionProblemService.findByCompany(principal.getId());

		result = new ModelAndView("positionProblem/list");
		result.addObject("problems", problems);

		return result;
	}

	// Display --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int positionProblemId) {
		final ModelAndView result;
		PositionProblem problem;
		Collection<PositionProblem> problems;
		Company company;

		company = this.companyService.findByPrincipal();
		problems = this.positionProblemService.findByCompany(company.getId());
		problem = this.positionProblemService.findOne(positionProblemId);
		Assert.isTrue(problems.contains(problem));

		result = new ModelAndView("positionProblem/display");
		result.addObject("problem", problem);

		return result;
	}

	// Creating --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int positionId) {
		ModelAndView result;
		PositionProblem problem;
		Position position;

		position = this.positionService.findOne(positionId);
		problem = this.positionProblemService.create();
		problem.setPosition(position);

		result = new ModelAndView("positionProblem/create");
		result.addObject("positionProblem", problem);
		result.addObject("id", problem.getId());
		return result;
	}

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionProblemId) {
		ModelAndView result;
		PositionProblem problem;

		problem = this.positionProblemService.findOne(positionProblemId);

		result = this.createEditModelAndView(problem);
		return result;
	}

	// Save --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("positionProblem") PositionProblem positionProblem, final BindingResult binding) {
		ModelAndView result;

		positionProblem = this.positionProblemService.reconstruct(positionProblem, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(positionProblem);
		else
			try {
				this.positionProblemService.save(positionProblem);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(positionProblem, "positionProblem.commit.error");
			}
		return result;
	}

	// Delete --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PositionProblem positionProblem, final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.positionProblemService.delete(positionProblem);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(positionProblem, "positionProblem.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PositionProblem problem) {
		return this.createEditModelAndView(problem, null);
	}

	protected ModelAndView createEditModelAndView(final PositionProblem problem, final String message) {
		final ModelAndView result;

		result = new ModelAndView("positionProblem/edit");
		result.addObject("positionProblem", problem);
		result.addObject("id", problem.getId());
		result.addObject("message", message);
		return result;
	}

}
